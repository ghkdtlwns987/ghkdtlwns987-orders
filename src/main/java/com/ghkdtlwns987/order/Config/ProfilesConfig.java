package com.ghkdtlwns987.order.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ProfilesConfig implements ApplicationRunner {
    private final Environment environment;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("===================다중 프로파일 테스트===================");
        System.out.println("Active profiles : "+ Arrays.toString(environment.getActiveProfiles()));
        System.out.println("Datasource driver : " + environment.getProperty("spring.datasource.driver-class-name"));
        System.out.println("Datasource url : " + environment.getProperty("spring.datasource.url"));
        System.out.println("Datasource username : " + environment.getProperty("spring.datasource.username"));
        System.out.println("Datasource password : " + environment.getProperty("spring.datasource.password"));
        System.out.println("Server Port : " + environment.getProperty("server.port"));
        System.out.println("====================================================");
    }
}
