package com.maciejBigos.poAwarii.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "deadlines")
public class Deadline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime date;
    private boolean isFree;
    private boolean isUsable;
    private Long malfunctionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMalfunctionId() {
        return malfunctionId;
    }

    public void setMalfunctionId(Long malfunctionId) {
        this.malfunctionId = malfunctionId;
    }

    public Deadline(LocalDateTime date, boolean isFree, boolean isUsable) {
        this.date = date;
        this.isFree = isFree;
        this.isUsable = isUsable;
    }

    public Deadline() {
    }

    public Deadline(Long id, LocalDateTime date, boolean isFree, boolean isUsable) {
        this.id = id;
        this.date = date;
        this.isFree = isFree;
        this.isUsable = isUsable;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

}
