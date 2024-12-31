package org.example.laboratoryappointment.User;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.component.SnowflakeGenerator;
import org.example.laboratoryappointment.dox.Appointment;
import org.example.laboratoryappointment.dox.User;
import org.example.laboratoryappointment.repository.AppointmentRepository;
import org.example.laboratoryappointment.repository.UserRepository;
import org.example.laboratoryappointment.service.AppointmentService;
import org.example.laboratoryappointment.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class UserTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AuditorAware<String> auditorAware;
    @Autowired
    private SnowflakeGenerator snowflakeGenerator;
//    @Test
    public void addUser()
    {
        User u = User.builder()
                .name("22")
                .account("test22")
                .password(passwordEncoder.encode("test"))
                .phone("19917661102")
                .status(1)
                .role(User.norm)
                .build();
//        userService.addUser(u);
        log.info("{}",u);
    }

//    @Test
    public void addAppointment()
    {
        Appointment appointment = Appointment.builder()
                .section(1)
                .week(1)
                .dayofweek(1)
                .course("{\"id\":\"1\",\"name\":\"Math\"}")
                .teacher("{\"id\":\"1\",\"name\":\"Saber\"}")
                .classroom("{\"id\":\"1\",\"name\":\"Test\"}")
                .build();
        appointmentService.addAppointment(appointment);
        log.info("{}",appointment);
    }

//    @Test
    public void addAppointment2()
    {
        Appointment appointment = Appointment.builder()
                .section(3)
                .week(3)
                .dayofweek(3)
                .course("{\"id\":\"1\",\"name\":\"Math\"}")
                .teacher("{\"id\":\"1\",\"name\":\"Saber\"}")
                .classroom("{\"id\":\"1\",\"name\":\"Test\"}")
                .build();
        appointmentRepository.save(appointment);
        log.info("{}",appointment);
    }

//    @Test
    public void addAppointment3(){
        Appointment appointment = Appointment.builder()
                .id("1111111111111111117")
                .teacher("{\"id\": \"1319655126345916416\", \"name\": \"SuiCheng\"}")
                .course("{\"id\": \"1319660638533787648\",\"name\": \"WEB技术框架\"}")
                .classroom("{\"id\": \"tet\",\"name\": \"tes3333t\"}")
                .dayofweek(4)
                .week(3)
                .section(3)
                .build();
        appointmentRepository.saveAppointment(appointment);
        log.info("{}",appointment);
    }
//    @Test
    public void addAppointment4(){
        Appointment appointment = Appointment.builder()
                .course("{\"id\":\"2\",\"name\":\"Math\"}")
                .classroom("{\"id\":\"2\",\"name\":\"Saber\"}")
                .teacher("{\"id\":\"2\",\"name\":\"Saber\"}")
                .dayofweek(5)
                .week(3)
                .section(3)
                .build();
        appointmentRepository.save(appointment);
    }
}
