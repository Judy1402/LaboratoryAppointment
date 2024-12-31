package org.example.laboratoryappointment.repository;

import org.example.laboratoryappointment.dox.Course;
import org.example.laboratoryappointment.dto.CourseDTO;
import org.example.laboratoryappointment.mapper.CourseDTORowMapper;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {

    @Query("select * from course")
    List<Course> findAllCourses();

    @Query("select * from course c where c.name =:name")
    List<Course> findByName(String name);

    @Query("select * from course c where c.teacher_id =:teacherId")
    List<Course> findByTeacherId(String teacherId);

    @Query("select * from course c where c.year =:year")
    List<Course> findByYear(String year);

    @Query("select * from course c where c.year >= :beg and c.year <= :end order by c.year, c.term")
    List<Course> findByYearBetween(String beg, String end);

    @Query("select * from course c where c.class_number =:number")
    List<Course> findByClassNumber(String number);

    @Query("select * from course c where c.quantity =:quantity")
    List<Course> findByQuantity(int quantity);

    @Query("""
        select * from course c where c.quantity >= :beg and c.quantity <= :end
        order by c.quantity
        """)
    List<Course> findByQuantityBetween(int beg, int end);

    @Query("select * from course c where c.profession =:profession")
    List<Course> findByProfession(String profession);


    @Query(value = "select * from course c, user u where c.teacher_id = u.id and c.teacher_id =:tid", rowMapperClass = CourseDTORowMapper.class)
    List<CourseDTO> findCourseDTOTeacher(String tid);

    @Query(value = "select * from course c, user u where c.teacher_id = u.id", rowMapperClass = CourseDTORowMapper.class)
    List<CourseDTO> findCourseDTO();
}
