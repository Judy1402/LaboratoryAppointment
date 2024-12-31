package org.example.laboratoryappointment.repository;

import org.example.laboratoryappointment.dox.Appointment;
import org.example.laboratoryappointment.dto.AppointmentDTO;
import org.example.laboratoryappointment.mapper.AppointmentDTORowMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, String> {

    @Query(value = "select * from appointment a, course c where a.course ->> '$.id'=c.id", rowMapperClass = AppointmentDTORowMapper.class)
    List<AppointmentDTO> findAppointmentDTO();

    @Query(value = "select * from appointment")
    List<Appointment> findAllAppointments();

    @Modifying
    @Query("""
        insert into appointment (id, course, teacher, classroom, week, dayOfWeek, section)
        values (:#{#a.id}, :#{#a.course}, :#{#a.teacher}, :#{#a.classroom}, :#{#a.week}, :#{#a.dayOfWeek}, :#{#a.section})
        """)
    void saveAppointment(Appointment a);

    @Query(value = "select * from appointment a where a.id =:id")
    Appointment findAppointmentById(String id);

    @Query(value = "select * from appointment a where a.teacher ->> '$.id' =:id")
    List<Appointment> findAppointmentByTeacherId(String id);

    @Query(value = "select * from appointment a where a.course ->> '$.id' =:id")
    List<Appointment> findAppointmentByCourseId(String id);

    @Modifying
    @Query("update appointment a set a.week =:week where a.id =:id")
    void updateAppointmentById(int week, String id);

    @Modifying
    @Query("update appointment a set a.week =:week, a.dayOfWeek =:dayOfWeek where a.id =:id")
    void updateAppointmentById(int week, int dayOfWeek, String id);

    @Modifying
    @Query("update appointment a set a.week =:week, a.dayOfWeek =:dayOfWeek, a.section =:section where a.id =: d")
    void updateAppointmentById(int week, int dayOfWeek, int section, String id);

    @Query(value = """
        select * from appointment a
        where (:#{#appointment.week} is null or a.week =:#{#appointment.week} )
        and (:#{#appointment.dayOfWeek} is null or a.dayOfWeek =:#{#appointment.dayOfWeek} )
        and (:#{#appointment.section} is null or a.section =:#{#appointment.section} )
        and (:#{#appointment.teacher} is null or a.teacher ->> '$.id' = :#{#appointment.teacher.id} )
        and (:#{#appointment.course} is null or a.course ->> '$.id' = :#{#appointment.course.id} )
        and (:#{#appointment.classroom} is null or a.classroom ->> '$.id' = :#{#appointment.classroom.id} )
        """)
    List<Appointment> findAppointmentByInfo(Appointment appointment);

    @Modifying
    @Query("delete from appointment a where a.course->>'$.id'=:cid")
    void deleteByCourseId(String cid);
}
