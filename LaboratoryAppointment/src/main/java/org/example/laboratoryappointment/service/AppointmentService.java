package org.example.laboratoryappointment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.example.laboratoryappointment.dox.Appointment;
import org.example.laboratoryappointment.dto.AppointmentDTO;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;
import org.example.laboratoryappointment.repository.AppointmentRepository;
import org.example.laboratoryappointment.tool.Temp;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AuditorAware<String> auditorAware;

    @Transactional
    public void addAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }


    @Transactional
    public void updateTime(String id, int week)
    {
        appointmentRepository.updateAppointmentById(week, id);
    }

    @Transactional
    public void updateTime(String id, int week, int dayOfWeek)
    {
        appointmentRepository.updateAppointmentById(week, dayOfWeek, id);
    }

    @Transactional
    public void updateTime(String id, int week, int dayOfWeek, int section)
    {
        appointmentRepository.updateAppointmentById(week, dayOfWeek, section, id);
    }

    @Transactional
    public void deleteAppointmentById(String tid, String aid) {
        Appointment a = getAppointmentById(aid);
        String teacherId = Temp.toObject(a.getTeacher()).getId();
        if(!tid.equals(teacherId))
        {
            throw MyException.builder()
                    .number(Code.ERROR)
                    .message("不是该预约的教师")
                    .build();
        }
        appointmentRepository.deleteById(aid);
    }

    @Transactional
    public void deleteAppointmentByCourseId(String cid){
        appointmentRepository.deleteByCourseId(cid);
    }

    @Transactional
    public List<AppointmentDTO> getAppointmentDTO(){
        return appointmentRepository.findAppointmentDTO();
    }

    @Transactional
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAllAppointments();
    }

    @Transactional
    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findAppointmentById(id);
    }

    @Transactional
    public List<Appointment> getAppointmentByTeacherId(String teacherId) {
        return appointmentRepository.findAppointmentByTeacherId(teacherId);
    }

    @Transactional
    public List<Appointment> getAppointmentByCourseId(String courseId) {
        return appointmentRepository.findAppointmentByCourseId(courseId);
    }

    @Transactional
    public List<Appointment> getAppointmentByInformation(Appointment appointment) {
        return appointmentRepository.findAppointmentByInfo(appointment);
    }

    @Transactional
    public void deleteAppointmentAdmin(String id) {
        appointmentRepository.deleteById(id);
    }

}
