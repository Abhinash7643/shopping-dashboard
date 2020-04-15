package com.abhinash.shoppingdashboard.exception;

import com.abhinash.shoppingdashboard.util.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice("com.mmf")
@RestController
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class.getName());

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse> handleAll(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        ApiResponse apiResponse =
                new ApiResponse(CommonConstants.ErrorCode.SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ApiResponse> handleNotFoundException(EntityNotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        ApiResponse apiResponse =
                new ApiResponse(CommonConstants.ErrorCode.ENTITY_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}