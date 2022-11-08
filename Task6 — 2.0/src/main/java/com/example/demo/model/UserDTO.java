package com.example.demo.model;

import com.example.demo.GrantedAuthority.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UserDTO {
    private String password;

    private String roles;

    private Integer id;

    private String name;

    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String email;

    private boolean wannaBeAdmin;

    public UserDTO(String password, String roles, Integer id, String name, String surname, Date dateOfBirth, String email, boolean wannaBeAdmin) {
        this.password = password;
        this.roles = roles;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.wannaBeAdmin = wannaBeAdmin;
    }

    public boolean isWannaBeAdmin() {
        return wannaBeAdmin;
    }

    public void setWannaBeAdmin(boolean wannaBeAdmin) {
        this.wannaBeAdmin = wannaBeAdmin;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }
}
