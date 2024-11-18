package com.mtvs.devlinkbackend.email.service;

import com.mtvs.devlinkbackend.email.model.dto.EmailRequestDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Transactional
    public String sendEmail(EmailRequestDTO emailRequestDTO, String nickName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false,"UTF-8");
            mimeMessageHelper.setTo(emailRequestDTO.getEmail());
            mimeMessageHelper.setSubject("Devlink에서 " + nickName + " 유저분이 보내신 메세지입니다.");
            mimeMessageHelper.setText(setContext(emailRequestDTO.getText()), true);
            mailSender.send(mimeMessage);
            return "success";
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public String setContext(String text) {
        Context context = new Context();
        context.setVariable("text", text);
        return templateEngine.process("template", context);
    }
}
