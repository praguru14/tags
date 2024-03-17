package com.tag.backend.controllers;

import com.tag.backend.config.EmailConfig;
import com.tag.backend.model.DataMessage;
import com.tag.backend.model.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/send-email")
    public ResponseEntity<DataMessage> emailSender(@RequestBody EmailModel emailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("tags0524@gmail.com");
        simpleMailMessage.setTo(emailModel.getTo());
        simpleMailMessage.setSubject(emailModel.getSubject());
        simpleMailMessage.setText(emailModel.getText());
        javaMailSender.send(simpleMailMessage);
        return new ResponseEntity<>( new DataMessage(HttpStatus.ACCEPTED, "Email sent",emailModel),HttpStatus.OK);

    }



}
