package com.example.demo.model;


import com.example.demo.GrantedAuthority.Role;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class User implements UserDetails {

    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private List<Role> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String email;

    private boolean wannaBeAdmin;

    public User(String password, List<Role> roles, Integer id, String name, String surname, Date dateOfBirth, String email, boolean wannaBeAdmin) {
        this.password = password;
        this.roles = roles;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.wannaBeAdmin = wannaBeAdmin;
    }

    public User() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean WannaBeAdmin() {
        return wannaBeAdmin;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isWannaBeAdmin() {
        return wannaBeAdmin;
    }

    public void setWannaBeAdmin(boolean wannaBeAdmin) {
        this.wannaBeAdmin = wannaBeAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void makeAdmin() {
        roles.add(Role.ADMIN);
    }

    public void addRole(Role role) {
        if (roles == null) {
            ArrayList<Role> roleArrayList = new ArrayList<>();
            roles = roleArrayList;
        }
        roles.add(role);
    }

}