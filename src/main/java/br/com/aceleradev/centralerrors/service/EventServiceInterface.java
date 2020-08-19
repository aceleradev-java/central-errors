package br.com.aceleradev.centralerrors.service;

import org.springframework.data.domain.Page;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import br.com.aceleradev.centralerrors.entity.Event;


public interface EventServiceInterface {
    Event save(Event event);
    Optional<Event> findById(Long id);
    Page<Event> findAll(Pageable pageable);
}
