package com.maciejBigos.poAwarii.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private User creator;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
