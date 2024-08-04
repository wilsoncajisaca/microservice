package com.wcajisaca.challengeAccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ChallengeAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeAccountApplication.class, args);
	}

}
