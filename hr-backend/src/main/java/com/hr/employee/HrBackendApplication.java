package com.hr.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HrBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(HrBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HrBackendApplication.class);

        // 👇 Logs BEFORE the context is created (so it still runs even if DB init fails)
        app.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
            Environment env = event.getEnvironment();
            log.info("==============================================");
            log.info("==   Spring Boot Datasource Configuration   ==");
            log.info("Active profile(s): {}", String.join(", ", env.getActiveProfiles()));
            log.info("spring.datasource.url = {}", env.getProperty("spring.datasource.url"));
            log.info("spring.datasource.username = {}", env.getProperty("spring.datasource.username"));

            String pwd = env.getProperty("spring.datasource.password");
            log.info("spring.datasource.password = {}", mask(pwd));
            log.info("spring.datasource.driver-class-name = {}", env.getProperty("spring.datasource.driver-class-name"));
            log.info("==============================================");
        });

        app.run(args); // DB may fail after this point, but logs are already printed
    }

    private static String mask(String v) {
        if (v == null || v.isEmpty()) return "(not set)";
        return v.length() <= 2 ? "**" : "*".repeat(v.length() - 2) + v.substring(v.length() - 2);
    }
}
