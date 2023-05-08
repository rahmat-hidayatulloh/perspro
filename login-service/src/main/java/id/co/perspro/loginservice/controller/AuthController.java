package id.co.perspro.loginservice.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import id.co.perspro.loginservice.exception.BusinessException;
import id.co.perspro.loginservice.model.entity.RoleEntity;
import id.co.perspro.loginservice.model.entity.UserEntity;
import id.co.perspro.loginservice.model.enums.UserRoleEnum;
import id.co.perspro.loginservice.model.request.LoginRequest;
import id.co.perspro.loginservice.model.request.SignupRequest;
import id.co.perspro.loginservice.model.response.MessageResponse;
import id.co.perspro.loginservice.model.response.UserInfoResponse;
import id.co.perspro.loginservice.repository.RoleRepository;
import id.co.perspro.loginservice.repository.UserRepository;
import id.co.perspro.loginservice.services.implement.UserDetailsImpl;
import id.co.perspro.loginservice.until.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder encoder;

  private final JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(),
            userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BusinessException("Username is already taken!");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BusinessException("Email is already in use!");
    }

    // Create new user's account
    UserEntity user = new UserEntity(request.getUsername(), request.getEmail(),
        encoder.encode(request.getPassword()));

    Set<String> strRoles = request.getRole();
    Set<RoleEntity> roles = new HashSet<>();

    if (strRoles == null) {
      RoleEntity userRole = roleRepository.findByRole(UserRoleEnum.ROLE_USER)
          .orElseThrow(() -> new BusinessException("Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            RoleEntity adminRole = roleRepository.findByRole(UserRoleEnum.ROLE_ADMIN)
                .orElseThrow(() -> new BusinessException("Role is not found."));
            roles.add(adminRole);

            break;
          case "mod":
            RoleEntity modRole = roleRepository.findByRole(UserRoleEnum.ROLE_MODERATOR)
                .orElseThrow(() -> new BusinessException("Role is not found."));
            roles.add(modRole);

            break;
          default:
            RoleEntity userRole = roleRepository.findByRole(UserRoleEnum.ROLE_USER)
                .orElseThrow(() -> new BusinessException("Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

  }

  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

}
