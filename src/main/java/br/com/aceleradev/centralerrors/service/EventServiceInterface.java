package br.com.aceleradev.centralerrors.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;


public interface EventServiceInterface {
    Event save(Event event);
    Optional<Event> findById(Long id);
    Page<Event> findAll(String log, String description, Level level, String source, LocalDateTime date, Pageable pageable);
    void delete(Long id);
}
