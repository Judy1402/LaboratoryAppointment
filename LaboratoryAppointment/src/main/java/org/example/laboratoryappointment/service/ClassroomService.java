package org.example.laboratoryappointment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.component.JWTComponent;
import org.example.laboratoryappointment.dox.Classroom;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;
import org.example.laboratoryappointment.repository.ClassroomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    @Transactional
    public boolean addClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
        return true;
    }

    @Transactional
    public Classroom getClassroomById(String id) {
        return classroomRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Classroom> getAllClassrooms() {
        log.info("getAllClassrooms {}", classroomRepository.findAllClassrooms());
        return classroomRepository.findAllClassrooms();
    }

    @Transactional
    public List<Classroom> getClassroomsByClassroomName(String classroomName) {
        return classroomRepository.findClassroomByName(classroomName);
    }

    @Transactional
    public List<Classroom> getClassroomByRole(String role) {
        return classroomRepository.findClassroomByRole(role);
    }

    @Transactional
    public List<Classroom> getClassroomByQuantity(int quantity) {
        return classroomRepository.findClassroomByQuantity(quantity);
    }

    @Transactional
    public List<Classroom> getClassroomByQuantity(int begin, int end) {
        return classroomRepository.findClassroomBetween(begin, end);
    }

    @Transactional
    public List<Classroom> getClassroomByManager(String manager) {
        return classroomRepository.findClassroomByManager(manager);
    }

    @Transactional
    public List<Classroom> getClassroomByStatus(int status) {
        return classroomRepository.findClassroomByStatus(status);
    }

//    @Transactional
//    public List<Classroom> getFreeClassroomsAtTime(int week, int ){
//
//    }

    @Transactional
    public void updateClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
    }

    @Transactional
    public boolean updateStatusById(String id, int status) {
        Classroom classroom = getClassroomById(id);
        if(classroom != null) {
            classroom.setStatus(status);
            classroomRepository.save(classroom);
            return true;
        }
        throw MyException.builder()
                .code(Code.BAD_REQUEST)
                .message("classroom 不存在")
                .build();
    }

    @Transactional
    public void deleteClassroomById(String id, String mid) {
        Classroom classroom = getClassroomById(id);
        if(classroom.getManagerId()!=null){
            if(!mid.equals(classroom.getManagerId())){
                throw MyException.builder()
                        .code(Code.NO_PERMISSION)
                        .message("你没有权限删除该教室")
                        .build();
            }
        }
        classroomRepository.deleteById(id);
    }
}
