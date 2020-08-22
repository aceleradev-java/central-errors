package br.com.aceleradev.centralerrors.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.aceleradev.centralerrors.EventRepository;
import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;
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
    public Page<Event> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    @Override
    public Page<Event> findByLevel(Level level, Pageable pageable) {
        return repository.findByLevel(level, pageable);
    }
    
    @Override
    public Page<Event> findByDescriptionContaining( String description, Pageable pageable) {
        return repository.findByDescriptionContaining(description, pageable);
    }
    
    @Override
    public Page<Event> findByLogContaining( String log, Pageable pageable) {
        return repository.findByLogContaining(log, pageable);
    }
    
    @Override
    public Page<Event> findBySourceContaining( String source, Pageable pageable) {
        return repository.findBySourceContaining(source, pageable);
    }
    
    @Override
    public     Page<Event> findByDate( LocalDateTime date, Pageable pageable) {
        return repository.findByDate(date, pageable);
    }

}
