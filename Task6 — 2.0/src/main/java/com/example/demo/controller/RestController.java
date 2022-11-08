package com.example.demo.controller;

import com.example.demo.GrantedAuthority.Role;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;
import java.util.List;


@org.springframework.web.bind.annotation.RestController

public class RestController {

    @Autowired
    private ShardManager shardManager;

    @Autowired
    private Mapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSender mailSender;

    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GetMapping(value = "/getUser/{id}")
    public String getUser(@PathVariable("id") Integer id) throws JsonProcessingException, InterruptedException {
        return objectWriter.writeValueAsString(mapper.toDto(userService.findById(id)));
    }


    @GetMapping(path = "/getUsers")
    public String getAllUsers() throws JsonProcessingException {
        return objectWriter.writeValueAsString(userService.findAll());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(UserDTO userDTO) {
        userService.save(mapper.toUser(userDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(UserDTO userDTO) {
        userService.save(mapper.toUser(userDTO));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/admin");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/request/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> requestAdminRole(@PathVariable("id") Integer id) {

        User user = userService.findById(id);
//        user.setWannaBeAdmin(true);
//        userService.save(user);

        JDA jda = shardManager.getShards().get(0);
        List <Guild> guilds = jda.getGuilds();
        List<Member> members = guilds.get(0).getMembers();
        for (Member member : members) {
            if (member.getRoles().stream().map(x -> x.getName()).toList().contains("Admin")) {
                Button reject = Button.danger("reject", "Reject");
                Button apply = Button.success("apply", "Apply");
                List<Button> buttons = new ArrayList<>();
                buttons.add(reject);
                buttons.add(apply);
                member.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(user.getEmail() + " wants to become an Admin, user's id = " + user.getId()).setActionRow(buttons)).queue();
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/make-admin/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> makeAdmin(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/reject-request/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> rejectRequest(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        user.setWannaBeAdmin(false);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
