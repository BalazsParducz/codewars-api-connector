package com.codewarsapi.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="mentors")
public class Mentor {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column( unique = true )
    private String email;

    @NotNull
    @Column
    private String password;
    @Transient
    private String matchingPassword;

//    @Column
//    private Set<Role> roles;

    public Mentor() {}

    public Mentor(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @AssertTrue(message="passVerify field should be equal than pass field")
    private boolean isValid() {

//        return this.password.equals(this.matchingPassword);
        return true;
    }
}
