package com.github.order.user.application;

import com.github.order.user.application.dto.RegisterForm;
import com.github.order.user.domain.User;
import com.github.order.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public UUID login(String email, String password) throws NotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new NotFoundException());
//
//
//
//    }

    public Long register(RegisterForm form) {
        String email = form.getEmail();
        String password = form.getPassword();
        String name = form.getName();

        User user = User.create(name, email, password);
        userRepository.save(user);

        return user.getId();
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

}
