package com.codewarsapi.model;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="mentors")
public class Mentor {

    @Id
    @GeneratedValue
    private Long id;

    @Column( unique = true )
    private String username;

    @NotNull
    @Column( unique = true )
    private String email;

    @NotNull
    @Column
    private String password;
    @Transient
    private String matchingPassword;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

//    public void addRole(Role role) {
//        System.out.println(role.getName());
//        this.roles.add(role);
//    }

    public Mentor() {}

    public Mentor(String username, String email, String password, Collection<Role> roles) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
