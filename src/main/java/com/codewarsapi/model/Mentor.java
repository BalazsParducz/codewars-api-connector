package com.codewarsapi.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="mentors")
public class Mentor {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Column( unique = true )
    private String email;

    @NotNull
    @NotEmpty
    @Column
    private String password;
    private String matchingPassword;

//    @Column
//    private Set<Role> roles;

    public Mentor() {}

    public Mentor(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    @Transient
    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getUsername() {
//        return email;
//    }
//
//    public void setUsername(String username) {
//        this.email = username;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @ManyToMany
//    @JoinTable(name = "mentor_role", joinColumns = @JoinColumn(name = "mentor_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    public Set<Role> getRoles() {
//        return roles;
//    }

//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
}
