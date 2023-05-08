package id.co.perspro.loginservice.model.response;

import java.util.List;
import id.co.perspro.loginservice.common.base.BaseResponse;
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
public class LoginResponse extends BaseResponse {

  /**
   * 
   */
  private static final long serialVersionUID = -2250692443204307608L;

  private Long id;

  private String username;

  private String email;

  private List<String> roles;

  private String token;

  private String tokenType;

}
