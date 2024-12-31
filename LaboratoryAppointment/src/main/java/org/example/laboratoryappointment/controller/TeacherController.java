package org.example.laboratoryappointment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.component.JWTComponent;
import org.example.laboratoryappointment.dox.Appointment;
import org.example.laboratoryappointment.dox.Course;
import org.example.laboratoryappointment.service.AppointmentService;
import org.example.laboratoryappointment.service.CourseService;
import org.example.laboratoryappointment.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher/")
@Slf4j
public class TeacherController {
    private final CourseService courseService;
    private final AppointmentService appointmentService;
    private final JWTComponent jwtComponent;

    @PostMapping("add/appointment")
    public ResultVO addAppointment(@RequestBody Appointment appointment) {
        appointmentService.addAppointment(appointment);
        return ResultVO.ok();
    }

    @DeleteMapping("delete/appointment/{id}")
    public ResultVO deleteAppointment(@PathVariable("id") String id, HttpServletRequest request) {
        String teacherId = request.getAttribute("uid").toString();
        log.info("tid is {}", teacherId);
        appointmentService.deleteAppointmentById(teacherId,id);
        return ResultVO.ok();
    }

    @GetMapping("get/appointment")
    public ResultVO getAppointment(HttpServletRequest request) {
        String teacherId = request.getAttribute("uid").toString();
        return ResultVO.success(appointmentService.getAppointmentByTeacherId(teacherId));
    }

    @GetMapping("get/appointments")
    public ResultVO getAppointments() {
        return ResultVO.success(appointmentService.getAllAppointments());
    }

    @GetMapping("get/appointmentDTO")
    public ResultVO getAppointmentDTO(HttpServletRequest request) {
        return ResultVO.success(appointmentService.getAppointmentDTO());
    }

    @PostMapping("add/course")
    public ResultVO addCourse(@RequestBody Course course, HttpServletRequest request) {
        log.info("add course {}", course);
        course.setTeacherId(request.getAttribute("uid").toString());
        courseService.addCourse(course);
        return ResultVO.ok();
    }

    @DeleteMapping("delete/course/{id}")
    public ResultVO deleteCourse(@PathVariable String id, HttpServletRequest request) {
        String tid = request.getAttribute("uid").toString();
        courseService.deleteCourseById(tid, id);
        appointmentService.deleteAppointmentByCourseId(id);
        return ResultVO.ok();
    }

    @GetMapping("get/course")
    public ResultVO getCourse(HttpServletRequest request) {
        String tid = request.getAttribute("uid").toString();
        return ResultVO.success(courseService.getCourseByTeacher(tid));
    }

    @GetMapping("get/courseDTO")
    public ResultVO getCourseDTO(HttpServletRequest request) {
        String tid = request.getAttribute("uid").toString();
        return ResultVO.success(courseService.getCourseDTOByTeacher(tid));
    }
}
