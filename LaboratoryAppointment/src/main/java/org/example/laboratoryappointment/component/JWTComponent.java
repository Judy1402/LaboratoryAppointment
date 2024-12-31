package org.example.laboratoryappointment.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
public class JWTComponent {
    @Value("${my.secretkey}")
    private String secretKey;

    private Algorithm algorithm;
    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String encode(Map<String, Object> map)
    {
        LocalDateTime time = LocalDateTime.now().plusDays(7);
        return JWT.create()
                .withPayload(map)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
    }

    public DecodedJWT decode(String token)
    {
        try{
            return JWT.require(algorithm).build().verify(token);
        }
        catch (SignatureVerificationException | TokenExpiredException e){
            if( e instanceof TokenExpiredException){
                throw MyException.builder()
                        .code(Code.TOKEN_EXPIRED)
                        .build();
            }
//            签名验证异常
            throw MyException.builder().code(Code.NO_PERMISSION).build();
        }
    }
}
