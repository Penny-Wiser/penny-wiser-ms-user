package com.penniless.springboot.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.Instant;

public class Util {

  public static String genExternalId() {
    int now = (int) Instant.now().toEpochMilli();
    return String.format("UR-%s", Integer.toString(now, 16).toUpperCase());
  }

  public static String saltAndHashPw(String plainText) {
    return BCrypt.hashpw(plainText, BCrypt.gensalt());
  }

  public static boolean authenticatePw(String plainTextPw, String hashedPw) {
    return BCrypt.checkpw(plainTextPw, hashedPw);
  }
}
