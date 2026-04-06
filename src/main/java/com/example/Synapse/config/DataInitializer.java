//package com.example.Synapse.config;
//
//
//import com.example.Synapse.models.Roles;
//import com.example.Synapse.models.User;
//import com.example.Synapse.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (userRepository.findByUsername("admin").isEmpty()) {
//            User admin = new User();
//            admin.setUsername("admin");
//
//            admin.setPassword(passwordEncoder.encode("1234"));
//            admin.setRole(Roles.ROLE_ADMIN);
//
//            userRepository.save(admin);
//            System.out.println(">>>> КУ-КУ! Админ создан: admin / 12345");
//
//        }
//    }
//}