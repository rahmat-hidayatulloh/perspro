package id.co.perspro.loginservice.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

  private String username;
  
  private String email;
  
  private String password;
  
  
}
