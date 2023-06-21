package id.co.perspro.loginservice.model.request;

import common.base.BaseRequest;
import jakarta.validation.constraints.NotBlank;
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
public class SigninRequest extends BaseRequest{

  /**
   * 
   */
  private static final long serialVersionUID = 1442886010920541949L;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

}
