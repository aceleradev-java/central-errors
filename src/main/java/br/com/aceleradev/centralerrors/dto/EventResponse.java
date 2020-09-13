package br.com.aceleradev.centralerrors.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.enums.Level;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponse {
    private Long id;
    private Level level;
    private String description;
    private String source;
    private LocalDateTime date;
    private Integer quantity;
    
    public static Page<EventResponse> map(Page<Event> events) {
        return events.map(EventResponse::convertToEventResponseDTO);
    }
    
    private static EventResponse convertToEventResponseDTO(Event event) {
        return new EventResponse(
                    event.getId(), 
                    event.getLevel(), 
                    event.getDescription(), 
                    event.getSource(), 
                    event.getDate(),
                    event.getQuantity()
                );
    }
}
