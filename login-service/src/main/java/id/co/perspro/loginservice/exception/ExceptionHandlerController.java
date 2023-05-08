package id.co.perspro.loginservice.exception;

import id.co.perspro.loginservice.model.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto<Object>> handlingBusinessException(BusinessException e) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", e.getMessage());

        ErrorResponseDto<Object> er = ErrorResponseDto.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errors(body)
                .build();

        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);

    }
}
