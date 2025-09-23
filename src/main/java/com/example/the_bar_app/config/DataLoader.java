package com.example.the_bar_app.config;

import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import com.example.the_bar_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setEmail("Dryxos@example.com");
            user.setUsername("Dryxos");
            user.setPassword(passwordEncoder.encode("Dryxos"));
            user.setRole(RoleName.ADMIN);
            userRepository.save(user);

            System.out.println("1 User created by default");
        }
        System.out.println("Swagger UI on http://localhost:8080/swagger-ui/index.html#/");
    }
}
