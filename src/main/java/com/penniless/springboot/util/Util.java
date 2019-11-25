package com.penniless.springboot.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class Util {

  public static String genExternalId() {
    long now = Instant.now().toEpochMilli();
    return String.format("UR-%s", Long.toString(now, 16).toUpperCase());
  }

  public static String saltAndHashPw(String plainText) {
    return BCrypt.hashpw(plainText, BCrypt.gensalt());
  }

  public static boolean authenticatePw(String plainTextPw, String hashedPw) {
    return BCrypt.checkpw(plainTextPw, hashedPw);
  }

  public static String createToken(String username, String externalId) {
    Date now = new Date();

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + 10 * 60 * 1000))
        .claim("ext_id", externalId)
        .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString("penny-wiser-key".getBytes()))
        .compact();
  }
}
