package org.pg.medtag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedTagApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedTagApplication.class, args);
	}

}
