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
public class Classroom {
    public static final String NORM = "norm";
    public static final String EXPIRE = "expi";

    @CreatedBy
    @Id
    private String id;

    private String name;
    private String role;
    private String managerName;
    private String managerId;
    private int quantity;
    private String description;
    private int status;
}
