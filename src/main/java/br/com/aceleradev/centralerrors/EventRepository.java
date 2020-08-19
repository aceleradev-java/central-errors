package br.com.aceleradev.centralerrors;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aceleradev.centralerrors.entity.Event;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long>{}
