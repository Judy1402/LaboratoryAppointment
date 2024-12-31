package org.example.laboratoryappointment.service;

import lombok.RequiredArgsConstructor;
import org.example.laboratoryappointment.dox.User;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;
import org.example.laboratoryappointment.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User getUserByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Transactional
    public List<User> getUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    @Transactional
    public List<User> getUserByProfession(String profession) {
        return userRepository.findByProfession(profession);
    }

    @Transactional
    public List<User> getAllUsers(){
       return userRepository.findAllUsers();
    }

    @Transactional
    public void addUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public void addUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    @Transactional
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void deleteUserByAccount(String account) {
        userRepository.deleteByAccount(account);
    }

    @Transactional
    public void updatePasswordByAccount(String account, String password) {
        User user = getUserByAccount(account);
        if(user == null)
        {
            throw MyException.builder()
                    .code(Code.NO_USER)
                    .message(Code.NO_USER.getMessage())
                    .build();
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Transactional
    public void updateLoginTimeByAccount(String account) {
        userRepository.updateLoginTime(LocalDateTime.now(), account);
    }

    @Transactional
    public void updatePasswordById(String userId, String password) {
        User user = getUserById(userId);
        if(user == null)
        {
            throw MyException.builder()
                    .code(Code.NO_USER)
                    .message(Code.NO_USER.getMessage())
                    .build();
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Transactional
    public void updateInformationById(String userId, User user) {
        User u = getUserById(userId);
        u.setName(user.getName());
        u.setProfession(user.getProfession());
        u.setDescription(user.getDescription());
        u.setPhone(user.getPhone());

        userRepository.save(u);
    }

    @Transactional
    public void updateRoleById(String uid, String role) {
        User user = getUserById(uid);
        user.setRole(role);
        userRepository.save(user);
    }
}
