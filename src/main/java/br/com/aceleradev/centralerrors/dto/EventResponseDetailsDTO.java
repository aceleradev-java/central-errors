package br.com.aceleradev.centralerrors.dto;

import java.time.LocalDateTime;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseDetailsDTO {

    private Long id;
    private Level level;
    private String description;
    private String source;
    private String log;
    private LocalDateTime date;
    
    public static EventResponseDetailsDTO map(Event event) {
        return new EventResponseDetailsDTO(
                    event.getId(), 
                    event.getLevel(), 
                    event.getDescription(), 
                    event.getSource(), 
                    event.getLog(), 
                    event.getDate()
                );
    }
}
