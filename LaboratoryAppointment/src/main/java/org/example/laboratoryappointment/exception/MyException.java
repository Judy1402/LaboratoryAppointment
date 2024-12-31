package org.example.laboratoryappointment.exception;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyException extends RuntimeException {
    private String message;
    private int number;
    private Code code;
}
