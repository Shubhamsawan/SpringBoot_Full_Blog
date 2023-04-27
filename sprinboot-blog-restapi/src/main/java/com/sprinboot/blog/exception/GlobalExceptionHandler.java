package com.sprinboot.blog.exception;

import com.sprinboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.security.AccessControlException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //handle specific custom  exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webrequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webrequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception,
                                                                        WebRequest webrequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webrequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                                        WebRequest webrequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webrequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


   @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

       Map<String, String> errors = new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error) ->{
           String fieldName = ((FieldError) error).getField();
           String message = error.getDefaultMessage();
           errors.put(fieldName, message);
       });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessControlException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessControlException exception,
                                                                        WebRequest webrequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webrequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

}

// this is made for getting message in few word in handle not null exception response
// http://localhost:8080/api/posts
//    {
//        "description": "Description may not be empty",
//            "title": "Post title should have at least 2 character",
//            "content": "Content may not be empty"
//    }

//Output for custom exception
//{
//        "timeStamp": "2023-04-11T07:39:34.965+00:00",
//        "messagte": "Post not found with id : '20'",
//        "details": "uri=/api/posts/20"
//        }