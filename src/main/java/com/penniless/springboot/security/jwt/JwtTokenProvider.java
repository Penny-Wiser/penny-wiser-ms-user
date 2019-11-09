package com.penniless.springboot.security.jwt;

import com.penniless.springboot.security.CustomUserDetailsService;
import com.penniless.springboot.security.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenProvider {

  private final CustomUserDetailsService customUserDetailsService;

  private String secretKey;

  public JwtTokenProvider(CustomUserDetailsService customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString("penny-wiser-key".getBytes());
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    return (!Objects.isNull(bearerToken) && bearerToken.startsWith("Bearer")) ?
        bearerToken.substring(7, bearerToken.length()) : null;
  }

  public boolean validateToken(String token) {

    try {
      Jws<Claims> claims = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token);

      return (!claims.getBody().getExpiration().before(new Date()));
    } catch (JwtException | IllegalArgumentException e) {
      throw new UnauthorizedException();
    }
  }
}
