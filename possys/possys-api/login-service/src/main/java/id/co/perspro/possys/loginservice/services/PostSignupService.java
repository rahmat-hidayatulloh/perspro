package id.co.perspro.possys.loginservice.services;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import id.co.perspro.possys.loginservice.common.service.BaseService;
import id.co.perspro.possys.loginservice.exception.BusinessException;
import id.co.perspro.possys.loginservice.model.entity.RoleEntity;
import id.co.perspro.possys.loginservice.model.entity.UserEntity;
import id.co.perspro.possys.loginservice.model.enums.UserRoleEnum;
import id.co.perspro.possys.loginservice.model.request.SignupRequest;
import id.co.perspro.possys.loginservice.model.response.SignupResponse;
import id.co.perspro.possys.loginservice.repository.RoleRepository;
import id.co.perspro.possys.loginservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostSignupService implements BaseService<SignupRequest, SignupResponse> {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder encoder;

  @Override
  public SignupResponse execute(SignupRequest input) {

    if (userRepository.existsByUsername(input.getUsername())) {
      throw new BusinessException("Username is already taken!");
    }

    if (userRepository.existsByEmail(input.getEmail())) {
      throw new BusinessException("Email is already in use!");
    }

    UserEntity user =
        new UserEntity(input.getUsername(), input.getEmail(), encoder.encode(input.getPassword()));

    Set<String> strRoles = input.getRole();
    Set<RoleEntity> roles = new HashSet<>();
    setUserRoles(strRoles, roles);

    user.setRoles(roles);
    var message = userRepository.save(user) != null ? "User registered successfully!" : "";

    return SignupResponse.builder().message(message).build();
  }

  private void setUserRoles(Set<String> strRoles, Set<RoleEntity> roles) {

    if (strRoles == null) {
      RoleEntity userRole = roleRepository.findByRole(UserRoleEnum.ROLE_USER)
          .orElseThrow(() -> new BusinessException("Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        setRoles(role, roles);
      });
    }

  }

  private void setRoles(String role, Set<RoleEntity> roles) {
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

  }

}
