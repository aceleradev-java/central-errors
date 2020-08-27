package br.com.aceleradev.centralerrors.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.enums.Level;
import lombok.Getter;

@Getter
public class EventRequestDTO {
    @NotNull
    private Level level;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String source;
    @NotNull
    @NotBlank
    private String log;
    @NotNull
    private Integer quantity;
    
    public Event mapToEvent() {
        return new Event(this.level, this.description, this.log, this.source, this.quantity);
    }

}
