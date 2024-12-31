package org.example.laboratoryappointment.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Temp {
    public static Temp toObject(String data){
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.readValue(data, Temp.class);
        }
        catch (JsonProcessingException e)
        {
            throw MyException.builder()
                    .number(Code.ERROR)
                    .message(e.getMessage())
                    .build();
        }
    }
    public static String toJson(Object data){
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writeValueAsString(data);
        }
        catch (JsonProcessingException e)
        {
            throw MyException.builder()
                    .number(Code.ERROR)
                    .message(e.getMessage())
                    .build();
        }
    }

    private String id;
    private String name;
}
