package com.mtvs.devlinkbackend.email.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfig {
    private static final String AUTH = "mail.smtp.auth";
    private static final String DEBUG = "mail.smtp.debug";
    private static final String TIMEOUT = "mail.smtp.timeout";
    private static final String STARTTLS = "mail.smtp.starttls.enable";

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.debug}")
    private boolean debug;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put(AUTH, auth);
        props.put(DEBUG, debug);
        props.put(TIMEOUT, timeout);
        props.put(STARTTLS, starttls);
        javaMailSender.setJavaMailProperties(props);
        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }

}
