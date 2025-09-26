package com.example.the_bar_app.config;

import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import com.example.the_bar_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            User user = new User();
            user.setEmail("Dryxos@example.com");
            user.setUsername("Dryxos");
            user.setPassword(encoder.encode("Dryxos"));
            user.setRole(RoleName.ADMIN);
            repo.save(user);

            System.out.println("1 User created by default");
        }
        System.out.println("Swagger UI on http://localhost:8080/swagger-ui/index.html#/");
    }
}
