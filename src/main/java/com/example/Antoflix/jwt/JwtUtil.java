//package com.example.Antoflix.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    private final String SECRET_KEY = "G8fxJ!#34kL@zV90qwErT!B0o8SnXpWqeF%Uz$Xo71PbYm#Rk3^M";
//    private final long EXPIRATION_TIME = 3600000; // 1 hour till the toke expires
//
//    public String generateToken(String email){
//        return JWT.create()
//                .withSubject(email)
//                .withIssuedAt(new Date())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(Algorithm.HMAC256(SECRET_KEY));
//    }
//
//    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException{
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
//        DecodedJWT decodedJWT = verifier.verify(token);
//
//        return decodedJWT.getSubject();
//    }
//}
