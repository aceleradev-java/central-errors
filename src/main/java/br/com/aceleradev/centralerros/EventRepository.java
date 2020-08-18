package br.com.aceleradev.centralerros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import antlr.debug.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{}
