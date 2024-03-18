package com.tag.backend.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {

    public String generateOtp() {
        Random random = new     Random();
        int randomNumber = random.nextInt(999999);
        String output = String.valueOf(randomNumber);
        while (output.length() < 6) {
            output = "0" + output;
        }
        return output;
    }
}
