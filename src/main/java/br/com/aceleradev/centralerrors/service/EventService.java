package br.com.aceleradev.centralerrors.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.aceleradev.centralerrors.EventRepository;
import br.com.aceleradev.centralerrors.entity.Event;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService implements EventServiceInterface {
    
    private EventRepository repository;

    @Override
    public Event save(Event event) {
        return repository.save(event);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }

}
