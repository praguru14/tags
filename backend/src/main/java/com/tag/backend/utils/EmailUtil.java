package com.tag.backend.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email,String otp) throws MessagingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("tags0524@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verify OTP");
        simpleMailMessage.setText("Your OTP is : "+otp);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
        mimeMessageHelper.setFrom("tags0524@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText("""
        <div>
          <p> Your OTP is : %s</p>
        </div>
        """.formatted( otp), true);

        javaMailSender.send(message);
    }

    public void resetPassword(String email) throws MessagingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("tags0524@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Reset your password");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
        mimeMessageHelper.setFrom("tags0524@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText("""
        <div>
          <a href="http://localhost:8400/set-password?email=%s" target="_blank">click link to set password</a>
        </div>
        """.formatted(email), true);

        javaMailSender.send(message);
    }

}
