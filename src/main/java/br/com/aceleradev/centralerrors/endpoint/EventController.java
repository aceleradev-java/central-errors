package br.com.aceleradev.centralerrors.endpoint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleradev.centralerrors.dto.EventRequestDTO;
import br.com.aceleradev.centralerrors.dto.EventResponseDTO;
import br.com.aceleradev.centralerrors.dto.EventResponseDetailsDTO;
import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;
import br.com.aceleradev.centralerrors.service.EventServiceInterface;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {
    
    private EventServiceInterface service;
    
    @PostMapping
    public EventResponseDetailsDTO save(@RequestBody EventRequestDTO eventRequestDTO) {
        Event event = eventRequestDTO.mapToEvent();
        return EventResponseDetailsDTO.map(service.save(event));
    }
    
    @GetMapping("/{id}")
    public EventResponseDetailsDTO findById(@PathVariable("id") Long id) {
        Event event = service.findById(id).orElse(new Event());
        return EventResponseDetailsDTO.map(event);
    }
    
    @GetMapping
    public Page<EventResponseDTO> findAll2(
            @RequestParam(name = "level", required = false) Level level, 
            Pageable pageable) {
        Page<Event> events;
        if (level != null) {
            events = service.findByLevel(level, pageable);
            return EventResponseDTO.map(events);
        }
        events = service.findAll(pageable);
        return EventResponseDTO.map(events);
    }
    
}
