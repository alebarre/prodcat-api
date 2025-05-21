package com.prodcat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Component
public class SendEmail {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public void send (String toEmailSend, String mesage) throws MessagingException {

        SimpleMailMessage smm = new SimpleMailMessage();

        MimeMessage mimeMsg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, "UTF-8");
        helper.setFrom(fromAddress);
        helper.setTo(toEmailSend);
        helper.setSubject("PRODUCT & CATEGORIES API | Password change request");
        helper.setText(mesage, true);

        sender.send(mimeMsg);
    }

}
