package com.hospital.management;

import com.hospital.management.model.User;
import com.hospital.management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            // check if admin already exists
            if (repo.findByUsername("administrator").isEmpty()) {

                User admin = new User();
                admin.setUsername("administrator");
                admin.setPassword(encoder.encode("administrator@123"));
                admin.setRole("ADMIN");

                repo.save(admin);

                System.out.println("✅ Admin user created");
            }
        };
    }
}