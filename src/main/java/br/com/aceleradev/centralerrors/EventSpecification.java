package br.com.aceleradev.centralerrors;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import br.com.aceleradev.centralerrors.entity.Event;
import br.com.aceleradev.centralerrors.enums.Level;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventSpecification {
    public static Specification<Event> hasLog(String log) {
      return (root, query, builder) -> 
          log.equals("")? 
          builder.conjunction() :
          builder.like(root.get("log"), "%" + log + "%");
    }

    public static Specification<Event> descriptionContains(String description) {
        return (root, query, builder) -> 
            description.equals("")?
            builder.conjunction() :
            builder.like(root.get("description"), "%" + description + "%");
    }
    
    public static Specification<Event> hasLevel(Level level) {
        return (root, query, builder) -> 
            level == null?
            builder.conjunction() :
            builder.equal(root.get("level"), level );
    }
    
    public static Specification<Event> sourceContains(String source) {
        return (root, query, builder) -> 
            source.equals("")?
            builder.conjunction() :
            builder.like(root.get("source"), "%" + source + "%" );
    }
    
    public static Specification<Event> hasDate(LocalDateTime date) {
        return (root, query, builder) -> 
            date == null ?
            builder.conjunction() :
            builder.equal(root.get("date"), date);
    }
}
