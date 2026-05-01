package com.example.student.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "app.config")
@Getter
@Setter
public class AppConfig {
    private long loginExpireTime;
    private int wrongCounterTime;
}
