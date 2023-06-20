package id.co.perspro.loginservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto<T> {

    private int errorCode;
    private T errors;

}
