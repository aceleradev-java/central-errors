package br.com.aceleradev.centralerrors.service;

import java.util.List;
import java.util.Optional;

import br.com.aceleradev.centralerrors.entity.Event;


public interface EventServiceInterface {
    Event save(Event event);
    Optional<Event> findById(Long id);
    List<Event> findAll();
}
