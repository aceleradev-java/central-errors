package br.com.aceleradev.centralerrors.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseDTO {
    private Long id;
    private Level level;
    private String description;
    private String source;
    private LocalDateTime date;
    
    public static Page<EventResponseDTO> map(Page<Event> events) {
        return events.map(EventResponseDTO::convertToEventResponseDTO);
    }
    
    private static EventResponseDTO convertToEventResponseDTO(Event event) {
        return new EventResponseDTO(
                    event.getId(), 
                    event.getLevel(), 
                    event.getDescription(), 
                    event.getSource(), 
                    event.getDate()
                );
    }
}
