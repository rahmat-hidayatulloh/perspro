package id.co.perspro.loginservice.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException {

  /**
  * 
  */
  private static final long serialVersionUID = -7031211454430968231L;

  private String message;

  public BusinessException(String message) {
    super(message);
    this.message = message;
  }
}
