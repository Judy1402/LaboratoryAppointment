package org.example.laboratoryappointment.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.component.JWTComponent;
import org.example.laboratoryappointment.dox.User;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.service.ClassroomService;
import org.example.laboratoryappointment.service.UserService;
import org.example.laboratoryappointment.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@CrossOrigin(exposedHeaders = "token")
@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTComponent jwtComponent;
    private final ClassroomService classroomService;

    @PostMapping("login")
    public ResultVO login(@RequestBody User user, HttpServletResponse response) {
//        log.info("user is {}", user);
        User u = userService.getUserByAccount(user.getAccount());
        log.info("uuuu is {}", u);
        if(u == null || !passwordEncoder.matches(user.getPassword(), u.getPassword())){
            return ResultVO.error(Code.LOGIN_ERROR);
        }
        userService.updateLoginTimeByAccount(user.getAccount());
        String token = jwtComponent.encode(Map.of("uid", u.getId(), "role", u.getRole()));
        String role = u.getRole();
        response.setHeader("token", token);
        response.setHeader("role", role);
        log.info("{}", response.getHeader("token"));
        return ResultVO.success(u);
    }

    @PostMapping("signin")
    public ResultVO signIn(@RequestBody User user, HttpServletResponse response) {
        log.info("********************************************************************");
        log.info("user is {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.norm);
        user.setLoginTime(LocalDateTime.now());
        log.info("user ok {}", user);
        userService.addUser(user);
        String token = jwtComponent.encode(Map.of("uid", user.getId(), "role", user.getRole()));
        String role = user.getRole();
        response.setHeader("token", token);
        response.setHeader("role", role);
        return ResultVO.success(user);
    }
    @PatchMapping("change/password")
    public ResultVO changePassword(@RequestBody User user, @RequestAttribute String uid) {
        String password = user.getPassword();
        userService.updatePasswordById(uid, passwordEncoder.encode(password));
        return ResultVO.ok();
    }

    @PatchMapping("change/information")
    public ResultVO changeInformation(@RequestBody User user, @RequestAttribute String uid) throws IllegalAccessException{
        log.info("{}", user);
        userService.updateInformationById(uid, user);
        return ResultVO.ok();
    }

    @GetMapping("get/classrooms")
    public ResultVO getClassrooms() {
        return ResultVO.success(classroomService.getAllClassrooms());
    }
}
