package com.example.demo.service;


import com.example.demo.model.User;

public interface UserService {
    void save(User user);

    Iterable<User> findAll();

    User findById(Integer id);

    void deleteUserById(Integer id);

    User findByEmail(String email);

}