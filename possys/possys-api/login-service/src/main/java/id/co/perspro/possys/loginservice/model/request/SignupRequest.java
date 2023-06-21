package id.co.perspro.possys.loginservice.model.request;

import java.util.Set;
import id.co.perspro.possys.loginservice.common.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignupRequest extends BaseRequest {

  /**
   * 
   */
  private static final long serialVersionUID = -941102263792270894L;

  private String username;

  private String email;

  private String password;

  private Set<String> role;


}
