package com.github.karina_denisevich.app.web.advice;

import com.github.karina_denisevich.app.common.exception.model.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleEmptyResultException(UserNotFoundException ex,
                                                             HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalAndNullException(RuntimeException ex, HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

//    @ExceptionHandler(DuplicateEntityException.class)
//    public ResponseEntity<Object> handleDuplicateException(DuplicateEntityException ex, HttpServletRequest webRequest) {
//        logger.error(getLogMessage(ex, webRequest));
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
                                                            HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleAnyException(MethodArgumentNotValidException ex,
                                                     HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private String getLogMessage(Exception ex, HttpServletRequest webRequest) {
        return ("Request: " + webRequest.getRequestURI()) +
                " Cause : " + ex.getMessage() +
                " .Stack trace " + ex;
    }
}