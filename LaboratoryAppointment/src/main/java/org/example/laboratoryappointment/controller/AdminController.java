package org.example.laboratoryappointment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.dox.Course;
import org.example.laboratoryappointment.dox.User;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.service.AppointmentService;
import org.example.laboratoryappointment.service.CourseService;
import org.example.laboratoryappointment.service.UserService;
import org.example.laboratoryappointment.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@CrossOrigin(exposedHeaders = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CourseService courseService;
    private final AppointmentService appointmentService;

    @PostMapping("add/user")
    public ResultVO addAdmin(@RequestBody User user) {
        log.info("{}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLoginTime(LocalDateTime.now());
        userService.addUser(user);
        return ResultVO.ok();
    }

    @PatchMapping("change/role")
    public ResultVO changeRole(@RequestBody User user) {
        userService.updateRoleById(user.getId(), user.getRole());
        return ResultVO.ok();
    }

    @PostMapping("set/user")
    public ResultVO setUser(@RequestBody User user) {
        if(user.getPassword()!=null) {
            userService.updatePasswordById(user.getId(), user.getPassword());
        }
        userService.updateRoleById(user.getId(), user.getRole());
        return ResultVO.ok();
    }

    @PostMapping("add/users")
    public ResultVO addUsers(@RequestBody List<User> users) {
        userService.addUsers(users);
        return ResultVO.ok();
    }

    @GetMapping("users")
    public ResultVO getUsers() {

        return ResultVO.success(userService.getAllUsers());
    }

    @DeleteMapping("delete/{account}")
    public ResultVO deleteUser(@PathVariable String account){
        if(account.equals("nefu_admin_account")){
            return ResultVO.error(Code.NO_PERMISSION);
        }
        userService.deleteUserByAccount(account);
        return ResultVO.ok();
    }

    @GetMapping("get/courses")
    public ResultVO getCourses() {
        return ResultVO.success(courseService.getAllCourses());
    }

    @GetMapping("get/courseDTO")
    public ResultVO getCourseDTO() {
        return ResultVO.success(courseService.getCourseDTO());
    }

    @GetMapping("get/appointmentDTO")
    public ResultVO getAppointments() {
        return ResultVO.success(appointmentService.getAppointmentDTO());
    }

    @DeleteMapping("delete/course/{id}")
    public ResultVO deleteCourse(@PathVariable String id) {
        courseService.deleteCourseAdmin(id);
        return ResultVO.ok();
    }

    @DeleteMapping("delete/appointment/{id}")
    public ResultVO deleteAppointment(@PathVariable String id) {
        appointmentService.deleteAppointmentAdmin(id);
        return ResultVO.ok();
    }
}
