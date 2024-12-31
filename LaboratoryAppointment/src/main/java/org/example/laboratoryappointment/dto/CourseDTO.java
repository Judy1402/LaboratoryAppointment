package org.example.laboratoryappointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {
    private String id;

    private String name;
    private int quantity;
    private String teacherId;
    private String teacherName;
    private String profession;

    private String year;
    private int term;
    private String classNumber;
    private int lesson;
    private float credit;
    private int theoreticalHours;
    private int experimentalHours;
}
