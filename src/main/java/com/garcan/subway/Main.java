package com.garcan.subway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Main extends AsyncConfigurerSupport {

  public static void main(final String[] args) {
    try {
      SpringApplication.run(Main.class, args);
    } catch (final Exception e) {
      log.error("Exception into SpringApplication.run(Main.class):{}", e.getMessage(), e);
    }
  }
}
