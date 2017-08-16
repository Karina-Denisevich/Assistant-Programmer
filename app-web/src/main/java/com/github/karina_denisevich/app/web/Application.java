package com.github.karina_denisevich.app.web;


import com.github.karina_denisevich.app.git.AppGit;
import com.github.karina_denisevich.app.services.AppServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@Import({
        AppServices.class,
        AppGit.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
