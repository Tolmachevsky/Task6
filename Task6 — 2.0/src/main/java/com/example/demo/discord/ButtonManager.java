package com.example.demo.discord;

import com.example.demo.GrantedAuthority.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonManager extends ListenerAdapter {

    @Autowired
    private UserService userService;


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getButton().getId().equals("reject")) {
            String[] content = event.getMessage().getContentRaw().split(" ");
            Integer userId = Integer.valueOf(content[content.length - 1]);

            User user = userService.findById(userId);
            user.setWannaBeAdmin(false);
            userService.save(user);

            event.getMessage().editMessageEmbeds().setActionRow(Button.danger("rejected","This request is rejected")).queue();

            event.reply("The request was rejected").queue();
        }
        if (event.getButton().getId().equals("apply")) {
            String[] content = event.getMessage().getContentRaw().split(" ");
            Integer userId = Integer.valueOf(content[content.length - 1]);

            User user = userService.findById(userId);
            List<Role> roles = new ArrayList<>();
            roles.add(Role.ADMIN);
            user.setRoles(roles);
            userService.save(user);

            event.getMessage().editMessageEmbeds().setActionRow(Button.success("applied","This request is applied")).queue();

            event.reply("The request was accepted").queue();
        }
    }
}
