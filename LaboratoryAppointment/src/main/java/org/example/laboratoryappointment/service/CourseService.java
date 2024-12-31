package org.example.laboratoryappointment.service;

import lombok.RequiredArgsConstructor;
import org.example.laboratoryappointment.dox.Course;
import org.example.laboratoryappointment.dto.CourseDTO;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;
import org.example.laboratoryappointment.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    @Transactional
    public Course getCourseById(String id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Course> getAllCourses() {
        return courseRepository.findAllCourses();
    }

    @Transactional
    public List<Course> getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    @Transactional
    public List<Course> getCourseByTeacher(String teacherId){
        return courseRepository.findByTeacherId(teacherId);
    }

    @Transactional
    public List<Course> getCourseByYear(String year){
        return courseRepository.findByYear(year);
    }

    @Transactional
    public List<Course> getCourseByYear(String beg, String end){
        return courseRepository.findByYearBetween(beg,end);
    }

    @Transactional
    public List<Course> getCourseByClassNumber(String classNumber){
        return courseRepository.findByClassNumber(classNumber);
    }

    @Transactional
    public List<Course> getCourseByQuantity(int quantity){
        return courseRepository.findByQuantity(quantity);
    }

    @Transactional
    public List<Course> getCourseByQuantity(int begin, int end){
        return courseRepository.findByQuantityBetween(begin,end);
    }

    @Transactional
    public List<Course> getCourseByProfession(String profession){
        return courseRepository.findByProfession(profession);
    }

    @Transactional
    public void deleteCourseById(String tid, String cid) {
        Course c = getCourseById(cid);
//        水平越权
        if(!c.getTeacherId().equals(tid)){
            throw MyException.builder()
                    .code(Code.NO_PERMISSION)
                    .message("你没有权限删除该课程")
                    .build();
        }
        courseRepository.deleteById(cid);
    }

    @Transactional
    public void deleteCourseAdmin(String id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public List<CourseDTO> getCourseDTOByTeacher(String tid){
        return courseRepository.findCourseDTOTeacher(tid);
    }

    @Transactional
    public List<CourseDTO> getCourseDTO(){
        return courseRepository.findCourseDTO();
    }
}
