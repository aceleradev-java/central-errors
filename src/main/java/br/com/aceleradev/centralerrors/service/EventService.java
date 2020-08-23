package br.com.aceleradev.centralerrors.service;

import static br.com.aceleradev.centralerrors.EventSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public List<Event> query(String log, String description, Level level, String source, LocalDateTime date) {
        Specification<Event> specificationLog = hasLog(log);
        Specification<Event> specificationDescription = descriptionContains(description);
        Specification<Event> specificationLevel = hasLevel(level);
        Specification<Event> specificationSource = sourceContains(source);
        Specification<Event> specificationDate = hasDate(date);
        return repository.findAll(where(specificationLog)
                .and(specificationDescription)
                .and(specificationLevel)
                .and(specificationSource)
                .and(specificationDate)
        );
    }
    
}
