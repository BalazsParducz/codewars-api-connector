package com.codewarsapi.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Mentor> mentorCollection;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Mentor> getMentorCollection() {
        return mentorCollection;
    }

    public void setMentorCollection(Collection<Mentor> mentorCollection) {
        this.mentorCollection = mentorCollection;
    }
}