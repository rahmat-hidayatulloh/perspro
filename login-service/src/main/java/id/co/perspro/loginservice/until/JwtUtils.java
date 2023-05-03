package id.co.perspro.loginservice.until;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import id.co.perspro.loginservice.services.implement.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

  private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${perspro.app.jwtSecret}")
  private String jwtSecret;

  @Value("${perspro.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${perspro.app.jwtCookieName}")
  private String jwtCookie;

//  @Value("${perspro.app.jwtRefreshCookieName}")
//  private String jwtRefreshCookie;

  public String getJwtFromCookies(HttpServletRequest request) {

    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    return cookie != null ? cookie.getValue() : null;
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {

    String jwt = generateTokenFromUsername(userPrincipal.getUsername());
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60)
        .httpOnly(true).build();
    return cookie;

  }

  public ResponseCookie getCleanJwtCookie() {

    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
    return cookie;
  }

  public String getUsernameFromJwtToken(String token) {

    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {

    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;

    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }


  private String generateTokenFromUsername(String username) {

    return Jwts.builder().setSubject(username).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

  }

}
