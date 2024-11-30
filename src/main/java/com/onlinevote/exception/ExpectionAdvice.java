package com.onlinevote.exception;

import com.onlinevote.entity.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExpectionAdvice extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(RecordNotInserted.class)
    public ResponseEntity<ApiResponse<String>> exceptionHandler(RecordNotInserted exception){

        ApiResponse<String> response = new ApiResponse<>(null,
                exception.getMessage(),false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }


    @ResponseBody
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ApiResponse<String>> exceptionHandler(UserNotFound exception){

        ApiResponse<String> response = new ApiResponse<>(null,
                exception.getMessage(),false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleRecordAlreadyExistsException(RecordAlreadyExistsException ex) {
        ApiResponse<String> response = new ApiResponse<>(null, ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);  // Return 409 Conflict
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> exceptionHandler(Exception exception){

        ApiResponse<String> response = new ApiResponse<>(null,
                exception.getMessage(),false);
//                "Server issue, Please wait...",false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
