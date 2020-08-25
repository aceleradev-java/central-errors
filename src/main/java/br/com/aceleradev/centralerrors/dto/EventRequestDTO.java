package br.com.aceleradev.centralerrors.dto;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.entity.Level;
import lombok.Getter;

@Getter
public class EventRequestDTO {
    
    private Level level;
    private String description;
    private String source;
    private String log;
    private Integer quantity;
    
    public Event mapToEvent() {
        return new Event(this.level, this.description, this.log, this.source, this.quantity);
    }

}
