package br.com.aceleradev.centralerrors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long>{
    Page<Event> findByLevel( Level level, Pageable pageable);
    Page<Event> findByDescriptionContaining( String description, Pageable pageable);
    Page<Event> findByLogContaining( String log, Pageable pageable);
}
