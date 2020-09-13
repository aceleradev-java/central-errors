package br.com.aceleradev.centralerrors.endpoint;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleradev.centralerrors.dto.EventRequest;
import br.com.aceleradev.centralerrors.dto.EventResponse;
import br.com.aceleradev.centralerrors.dto.EventResponseDetails;
import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.enums.Level;
import br.com.aceleradev.centralerrors.service.EventServiceInterface;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
public class EventController {
    
    private EventServiceInterface service;
    
    @PostMapping(path = "admin/events")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Registra um novo evento")
    public EventResponseDetails save(@Valid @RequestBody EventRequest eventRequestDTO) {
        Event event = eventRequestDTO.mapToEvent();
        return EventResponseDetails.map(service.save(event));
    }
    
    @GetMapping(path = "protected/events/{id}")
    @ApiOperation(value = "Procura evento por id")
    public EventResponseDetails findById(@PathVariable("id") Long id) {
        Event event = service.findById(id);
        return EventResponseDetails.map(event);
    }
    
    @GetMapping(path = "protected/events")
    @ApiOperation(value = "Lista todos eventos")
    public Page<EventResponse> findAll(
            @RequestParam(name = "log", required = false, defaultValue = "") String log,
            @RequestParam(name = "level", required = false) Level level,
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam(name = "source", required = false, defaultValue = "") String source,
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            Pageable pageable) {
        Page<Event> events = service.findAll(log, description, level, source, date, pageable);
        return EventResponse.map(events);
    }
    
    @DeleteMapping(path = "admin/events/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove um evento")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
    
}
