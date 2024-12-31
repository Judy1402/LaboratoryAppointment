package org.example.laboratoryappointment.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @CreatedBy
    @Id
    private String id;

    private String name;
    private int quantity;
    private String teacherId;
    private String profession;
    private String year;
    private int term;
    private String classNumber;
    private int lesson;
    private float credit;
    private int theoreticalHours;
    private int experimentalHours;
}
