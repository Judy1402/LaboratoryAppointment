package org.example.laboratoryappointment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {
    private String appointmentId;
    private String teacherId;
    private String courseId;
    private String classroomId;

    private String teacherName;
    private String classroomName;
    private String courseName;

    private String year;
    private String classNumber;
    private String profession;

    private int week;
    private int dayofweek;
    private int section;
}
