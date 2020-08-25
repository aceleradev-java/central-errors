package br.com.aceleradev.centralerrors.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_EMPTY)
@AllArgsConstructor
@Getter
@Setter
public class Problem {
    private Integer status;
    private LocalDateTime date;
    private String title;
    private List<Field> fields;
    
    @AllArgsConstructor
    @Getter 
    @Setter
    public static class Field {
        private String name;
        private String message;
    }
}
