package com.example.demo.config;

import com.example.demo.discord.ButtonManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

import java.util.Properties;

@Configuration
@ComponentScan("com.example.demo")
public class AppConfig {

    @Autowired
    ButtonManager buttonManager;

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("fortestpreproject3@gmail.com");
        javaMailSender.setPassword("uriyhqdcdcgnneof");

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

    @Bean
    ShardManager shardManager() {
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("MTAzOTI1Nzg3OTkwMDg2ODcxMA.GOeVMw.oIEMg9nkTiw8fLYCcjjzaBUf9EOzznr9cikuds");
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Requests"));
        builder.addEventListeners(buttonManager);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        return builder.build();
    }
}
