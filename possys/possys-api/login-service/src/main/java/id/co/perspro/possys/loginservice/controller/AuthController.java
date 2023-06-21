package id.co.perspro.possys.loginservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import id.co.perspro.possys.loginservice.model.request.SigninRequest;
import id.co.perspro.possys.loginservice.model.request.SignupRequest;
import id.co.perspro.possys.loginservice.model.response.MessageResponse;
import id.co.perspro.possys.loginservice.services.PostSigninService;
import id.co.perspro.possys.loginservice.services.PostSignupService;
import id.co.perspro.possys.loginservice.until.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final JwtUtils jwtUtils;

  private final PostSigninService postSigninService;

  private final PostSignupService postSignupService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody SigninRequest request) {
    return ResponseEntity.ok(postSigninService.execute(request));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
    return ResponseEntity.ok(postSignupService.execute(request));
  }

  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

}
