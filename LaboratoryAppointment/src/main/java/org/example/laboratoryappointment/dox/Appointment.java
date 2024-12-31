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
public class Appointment {

    @CreatedBy
    @Id
    private String id;

    private String course;
    private String teacher;
    private String classroom;

    private int week;
    private int dayofweek;
    private int section;
}
