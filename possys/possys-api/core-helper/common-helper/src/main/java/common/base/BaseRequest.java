package common.base;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -2993479750264967685L;

}
