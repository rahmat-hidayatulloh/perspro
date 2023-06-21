package id.co.perspro.possys.loginservice.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import common.base.ExampleEntity;
import id.co.perspro.possys.loginservice.common.service.BaseService;
import id.co.perspro.possys.loginservice.model.request.SigninRequest;
import id.co.perspro.possys.loginservice.model.response.SigninResponse;
import id.co.perspro.possys.loginservice.services.implement.UserDetailsImpl;
import id.co.perspro.possys.loginservice.until.JwtUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostSigninService implements BaseService<SigninRequest, SigninResponse> {

  private final AuthenticationManager authenticationManager;
  
  private final ExampleEntity exampleEntity;
  
  private final JwtUtils jwtUtils;

  @Override
  public SigninResponse execute(SigninRequest input) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    exampleEntity.setTestName("test");
    System.out.println(exampleEntity.getTestName());
    
    return SigninResponse.builder().id(userDetails.getId()).username(userDetails.getUsername())
        .email(userDetails.getEmail()).roles(roles).tokenType("Bearer")
        .token(jwtUtils.generateTokenAccess(userDetails)).build();
  }

}
