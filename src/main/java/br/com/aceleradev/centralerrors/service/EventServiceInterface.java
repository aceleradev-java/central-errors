package br.com.aceleradev.centralerrors.service;

import org.springframework.data.domain.Page;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;


public interface EventServiceInterface {
    Event save(Event event);
    Optional<Event> findById(Long id);
    Page<Event> findAll(Pageable pageable);
    Page<Event> findByLevel(Level level,Pageable pageable);
    Page<Event> findByDescriptionContaining( String description, Pageable pageable);
    Page<Event> findByLogContaining( String log, Pageable pageable);
    Page<Event> findBySourceContaining( String source, Pageable pageable);
}
