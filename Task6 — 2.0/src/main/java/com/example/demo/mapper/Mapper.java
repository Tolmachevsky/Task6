package com.example.demo.mapper;

import com.example.demo.GrantedAuthority.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public
class Mapper {
    public UserDTO toDto(User user) {

        String password = user.getPassword();

        StringBuilder stringBuilder = new StringBuilder();

        for (Role role : user.getRoles()) {
            stringBuilder.append(role.name() + " ");
        }

        String roles = stringBuilder.toString();

        Integer id = user.getId();

        String name = user.getName();

        String surname = user.getSurname();

        Date dateOfBirth = user.getDateOfBirth();

        String email = user.getEmail();

        Boolean wannaBeAdmin = user.isWannaBeAdmin();


        return new UserDTO(password, roles, id, name, surname, dateOfBirth, email, wannaBeAdmin);
    }

    public User toUser(UserDTO userDTO) {
        List<Role> roles = new ArrayList<>();
        if (userDTO.getRoles().contains("ADMIN")) {
            roles.add(Role.ADMIN);
        }
        if (userDTO.getRoles().contains("USER")) {
            roles.add(Role.USER);
        }
        return new User(userDTO.getPassword(), roles, userDTO.getId(), userDTO.getName(), userDTO.getSurname(), userDTO.getDateOfBirth(), userDTO.getEmail(), userDTO.isWannaBeAdmin());
    }
}