package com.github.karina_denisevich.app.web.advice;


import com.github.karina_denisevich.app.common.exception.model.DuplicateEntityException;
import com.github.karina_denisevich.app.common.exception.model.ServiceException;
import com.github.karina_denisevich.app.common.exception.model.UserNotFoundException;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleEmptyResultException(final UserNotFoundException ex,
                                                     final HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity handleIllegalAndNullException(final RuntimeException ex, final HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity handleDuplicateException(final DuplicateEntityException ex, final HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex,
                                                      final HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodNotValidException(final MethodArgumentNotValidException ex,
                                                        final HttpServletRequest webRequest) {
        logger.error(getLogMessage(ex, webRequest));
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity handleMongoException(final MongoException exception, final HttpServletRequest webRequest) {
        logger.warn("Processing mongo exception:" + exception.getMessage());

        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleServiceException(final ServiceException exception, final HttpServletRequest webRequest) {
        logger.warn("Processing service exception:" + exception.getMessage());

        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAbstractException(final Exception exception, final HttpServletRequest webRequest) {
        logger.warn("Processing abstract exception:" + exception.getMessage());

        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    private String getLogMessage(final Exception ex, final HttpServletRequest webRequest) {
        return ("Re quest: " + webRequest.getRequestURI()) +
                " Cause : " + ex.getMessage() +
                " .Stack trace " + ex;
    }
}
