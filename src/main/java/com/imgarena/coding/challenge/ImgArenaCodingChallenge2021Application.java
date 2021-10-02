package com.imgarena.coding.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImgArenaCodingChallenge2021Application {

  public static void main(final String[] args) {
    SpringApplication.run(ImgArenaCodingChallenge2021Application.class, args);
  }

}
