package com.abhinash.shoppingdashboard.exception;

import com.abhinash.shoppingdashboard.util.CommonConstants;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice("com.abhinash")
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

    @ExceptionHandler(MyException.class)
    public final ResponseEntity<ApiResponse> handleMyException(MyException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        int code = ex.code > -1 ? ex.code : CommonConstants.ErrorCode.BAD_REQUEST_ERROR;
        return new ResponseEntity<>(new ApiResponse(code, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ApiResponse> handleNotFoundException(EntityNotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        ApiResponse apiResponse =
                new ApiResponse(CommonConstants.ErrorCode.ENTITY_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        ApiResponse travelkoshResponse =
                new ApiResponse(CommonConstants.ErrorCode.REQUEST_VALIDATION_ERROR, "validation failed");
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            travelkoshResponse.addError(violation.getLeafBean().toString(), violation.getMessage());
        }
        return new ResponseEntity<>(travelkoshResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MismatchedInputException.class})
    public ResponseEntity<ApiResponse> handleMismatchedInputException(MismatchedInputException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        ApiResponse apiResponse =
                new ApiResponse(CommonConstants.ErrorCode.REQUEST_VALIDATION_ERROR,
                        "Invalid input of type " + ex.getTargetType().getSimpleName());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<ApiResponse> handleDataAccessException(DataAccessException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        ApiResponse apiResponse =
                new ApiResponse(CommonConstants.ErrorCode.DB_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("Returning HTTP 400 Bad Request", ex);
        ex.printStackTrace(System.out);
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ex.printStackTrace(System.out);
        ApiResponse travelkoshResponse =
                new ApiResponse(CommonConstants.ErrorCode.REQUEST_ARGUMENT_INVALID,
                        "Error in input request");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if (null != error.getDefaultMessage()) {
                travelkoshResponse.addError(error.getField(), error.getDefaultMessage());
            }
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            travelkoshResponse.addError(error.getObjectName(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(travelkoshResponse, HttpStatus.BAD_REQUEST);
    }
}