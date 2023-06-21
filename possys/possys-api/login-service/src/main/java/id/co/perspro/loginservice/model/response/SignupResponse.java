package id.co.perspro.loginservice.model.response;


import common.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignupResponse extends BaseResponse {

  /**
   * 
   */
  private static final long serialVersionUID = -1460424484567673520L;

  private final String message;

}
