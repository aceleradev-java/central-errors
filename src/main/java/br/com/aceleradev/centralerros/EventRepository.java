package br.com.aceleradev.centralerros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.aceleradev.centralerros.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{}
