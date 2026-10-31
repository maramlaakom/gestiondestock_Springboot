package com.example.projet1;

import com.example.projet1.Service.AccountServiceImpl;
import com.example.projet1.entitice.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Projet1Application {

    public static void main(String[] args) {
        SpringApplication.run(Projet1Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountServiceImpl accountService) {
        return args -> {

            try {
                accountService.addNewUser(
                        "user1@gmail.com",
                        "1234",
                        "1234",
                        "user1@gmail.com",
                        "UserOne",
                        Role.employee
                );
            } catch (Exception e) {
                System.out.println("user1 déjà existe");
            }

            try {
                accountService.addNewUser(
                        "user2@gmail.com",
                        "1234",
                        "1234",
                        "user2@gmail.com",
                        "UserTwo",
                        Role.employee
                );
            } catch (Exception e) {
                System.out.println("user2 déjà existe");
            }

            try {
                accountService.addNewUser(
                        "admin@gmail.com",
                        "1234",
                        "1234",
                        "admin@gmail.com",
                        "Admin",
                        Role.admin
                );
            } catch (Exception e) {
                System.out.println("admin déjà existe");
            }

        };
    }
}