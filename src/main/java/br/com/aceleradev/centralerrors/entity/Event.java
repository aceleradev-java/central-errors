package br.com.aceleradev.centralerrors.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.aceleradev.centralerrors.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
@Table(name = "events")
public class Event {

    public Event(Level level, String description, String log, String source, Integer quantity) {
        this.level = level;
        this.description = description;
        this.log = log;
        this.source = source;
        this.date = LocalDateTime.now();
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;
    
    @Column
    @NotNull
    @Size(max = 255)
    private String description;
    
    @Column
    @NotNull
    @Lob
    private String log;
    
    @Column
    @NotNull
    @Size(max = 255)
    private String source;
    
    @Column
    @CreatedDate
    private LocalDateTime date;
    
    @Column
    @NotNull
    private Integer quantity;
}
