package com.penniless.springboot.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

  public PasswordUtil() {
  }

  public static String saltAndHash(String plainText) {
    return BCrypt.hashpw(plainText, BCrypt.gensalt());
  }

  public static boolean authenticatePw(String plainTextPw, String hashedPw) {
    return BCrypt.checkpw(plainTextPw, hashedPw);
  }
}
