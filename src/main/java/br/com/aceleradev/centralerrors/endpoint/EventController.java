package br.com.aceleradev.centralerrors.endpoint;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleradev.centralerrors.dto.EventRequestDTO;
import br.com.aceleradev.centralerrors.dto.EventResponseDTO;
import br.com.aceleradev.centralerrors.dto.EventResponseDetailsDTO;
import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;
import br.com.aceleradev.centralerrors.exception.EntityNotFound;
import br.com.aceleradev.centralerrors.service.EventServiceInterface;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
public class EventController {
    
    private EventServiceInterface service;
    
    @PostMapping(path = "admin/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDetailsDTO save(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        Event event = eventRequestDTO.mapToEvent();
        return EventResponseDetailsDTO.map(service.save(event));
    }
    
    @GetMapping(path = "protected/events/{id}")
    public EventResponseDetailsDTO findById(@PathVariable("id") Long id) {
        Event event = service.findById(id).orElseThrow(() -> new EntityNotFound("Event not found"));
        return EventResponseDetailsDTO.map(event);
    }
    
    @GetMapping(path = "protected/events")
    public Page<EventResponseDTO> findAll(
            @RequestParam(name = "log", required = false, defaultValue = "") String log,
            @RequestParam(name = "level", required = false) Level level,
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam(name = "source", required = false, defaultValue = "") String source,
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            Pageable pageable) {
        Page<Event> events = service.findAll(log, description, level, source, date, pageable);
        return EventResponseDTO.map(events);
    }
    
}
