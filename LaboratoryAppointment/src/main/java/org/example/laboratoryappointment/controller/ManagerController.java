package org.example.laboratoryappointment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.laboratoryappointment.component.JWTComponent;
import org.example.laboratoryappointment.dox.Classroom;
import org.example.laboratoryappointment.service.ClassroomService;
import org.example.laboratoryappointment.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/")
public class ManagerController {
    private final ClassroomService classroomService;
    private final JWTComponent jwtComponent;

    @PostMapping("add/classroom")
    public ResultVO addClassroom (@RequestBody Classroom classroom) {
        classroom.setRole(Classroom.EXPIRE);
        classroomService.addClassroom(classroom);
        return ResultVO.ok();
    }

    @GetMapping("get/classroom/{cid}")
    public ResultVO getClassroom (@PathVariable String cid) {
        return ResultVO.success(classroomService.getClassroomById(cid));
    }

    @DeleteMapping("delete/classroom/{cid}")
    public ResultVO deleteClassroom (@PathVariable String cid, HttpServletRequest request) {
        String token = request.getAttribute("token").toString();
        String mid = jwtComponent.decode(token).getClaim("uid").asString();
        classroomService.deleteClassroomById(cid, mid);
        return ResultVO.ok();
    }

    @PostMapping("change/classroom")
    public ResultVO changeClassroom (@RequestBody Classroom classroom) {
        classroomService.updateClassroom(classroom);
        return ResultVO.ok();
    }
}
