package org.example.laboratoryappointment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.laboratoryappointment.exception.Code;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultVO {
    private int code;
    private String msg;
    private String token;
    private Object data;
    private static final ResultVO EMPTY = ResultVO.builder().code(Code.SUCCESS).msg("ok").build();
    public static ResultVO ok(){
        return EMPTY;
    }
    public static ResultVO success(Object data) {
        return ResultVO.builder().code(Code.SUCCESS).data(data).build();
    }
    public static ResultVO error(Code code){
        return ResultVO.builder().code(code.getNumber()).msg(code.getMessage()).build();
    }
    public static ResultVO error(int num, String msg){
        return ResultVO.builder().code(num).msg(msg).build();
    }
    public static ResultVO error(Code code, String msg){
        return ResultVO.builder().code(code.getNumber()).msg(msg).build();
    }
}
