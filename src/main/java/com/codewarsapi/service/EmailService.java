package com.codewarsapi.service;

import com.codewarsapi.model.Kata;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Ignore
//@Service
public class EmailService {

    private final Log log = LogFactory.getLog(this.getClass());

    @Value("${spring.mail.username}")
    private String MESSAGE_FROM;

    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(String emailAddress, String emailText) {
        SimpleMailMessage message;
        try {
            message = new SimpleMailMessage();
            message.setFrom(MESSAGE_FROM);
            message.setTo(emailAddress);
            message.setSubject("Codewars Mentor Aid");
            message.setText(emailText);
            javaMailSender.send(message);
        } catch (Exception e) {
            log.info("An error occurred while trying to send your email to" + emailAddress + " " + e);
        }
    }

    public String generateEmailText(String codewars_userame, int points, LocalDate ldFrom, LocalDate ldTo, List<Kata> katas) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear Reader,\n\n");
        sb.append(codewars_userame + " has achieved " + points + " points between "+ ldFrom +" and " + ldTo +"." );
        sb.append("\nThe following katas have been resolved:\n\n");
        for(Kata k: katas) {
            sb.append(k.getName()+"\tresolved at "+k.getCompletedAt() +".\t"+ k.getKyu()+"\t worth of "+k.getCherries()+" point(s).\n");
        }
        sb.append("\nThank you for choosing Mentor Aid.");
        return sb.toString();
    }
}

