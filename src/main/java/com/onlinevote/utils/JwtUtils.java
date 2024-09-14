//package com.onlinevote.utils;
//
//import com.onlinevote.entity.Student;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//@Component
//public class JwtUtils {
//    private static String secret = "This_is_secret";
//    public String generateJwt(Student student){
//
//        Date issuedAt = new Date();
//        //create claims
//        Claims claims = Jwts.claims()
//                .setIssuer(student.getId().toString())
//                .setIssuedAt(issuedAt).build();
//                //.setExpiration(expiryAt);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .compact();
//    }
//}
