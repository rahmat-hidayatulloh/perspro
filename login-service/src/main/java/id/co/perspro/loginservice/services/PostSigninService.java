package id.co.perspro.loginservice.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import id.co.perspro.loginservice.common.service.BaseService;
import id.co.perspro.loginservice.model.request.LoginRequest;
import id.co.perspro.loginservice.model.response.LoginResponse;
import id.co.perspro.loginservice.repository.RoleRepository;
import id.co.perspro.loginservice.repository.UserRepository;
import id.co.perspro.loginservice.services.implement.UserDetailsImpl;
import id.co.perspro.loginservice.until.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class PostSigninService implements BaseService<LoginRequest, LoginResponse> {

  private final AuthenticationManager authenticationManager;

  private final JwtUtils jwtUtils;

  @Override
  public LoginResponse execute(LoginRequest input) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return LoginResponse.builder()
        .id(userDetails.getId())
        .username(userDetails.getUsername())
        .email(userDetails.getEmail())
        .roles(roles)
        .tokenType("Bearer")
        .token(jwtUtils.generateTokenAccess(userDetails))
        .build();
  }

}
