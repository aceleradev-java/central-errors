package br.com.aceleradev.centralerrors.endpoint;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.service.EventServiceInterface;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {
    
    private EventServiceInterface service;
    
    @PostMapping
    public Event save(@RequestBody Event event) {
        return service.save(event);
    }
    
    @GetMapping("/{id}")
    public List<Event> findById(@PathVariable("id") Long id) {
        return service.findAll();
    }
    
    @GetMapping
    public List<Event> findAll() {
        return service.findAll();
    }
}
