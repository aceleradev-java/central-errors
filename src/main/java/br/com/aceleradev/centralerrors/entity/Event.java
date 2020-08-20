package br.com.aceleradev.centralerrors.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
@Table(name = "events")
public class Event {

    public Event() {
        this.date = LocalDateTime.now();
    }

    public Event(Level level, String description, String log, String source) {
        this.level = level;
        this.description = description;
        this.log = log;
        this.source = source;
        this.date = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    @NotNull
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
}
