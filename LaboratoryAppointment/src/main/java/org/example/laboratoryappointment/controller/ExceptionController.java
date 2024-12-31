package org.example.laboratoryappointment.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;
import org.example.laboratoryappointment.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController{
    @ExceptionHandler(MyException.class)
    public ResultVO handleMyException(MyException e){
        log.error(e.getMessage());
        return ResultVO.builder()
                .code(Code.ERROR)
                .msg(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e){
        log.error(e.getMessage());
        return ResultVO.builder()
                .code(Code.ERROR)
                .msg(e.getMessage())
                .build();
    }
}
