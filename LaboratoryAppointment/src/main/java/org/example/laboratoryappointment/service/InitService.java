package org.example.laboratoryappointment.service;

import lombok.RequiredArgsConstructor;
import org.example.laboratoryappointment.dox.User;
import org.example.laboratoryappointment.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InitService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        long count = userRepository.count();
        if (count == 0) {
            User u = User.builder()
                    .account("nefu_admin_account")
                    .password(passwordEncoder.encode("nefu_admin_password"))
                    .name("admin")
                    .phone("19917661102")
                    .loginTime(LocalDateTime.now())
                    .status(1)
                    .profession("admin")
                    .role(User.admin)
                    .build();
            userRepository.save(u);
        }
    }
}
