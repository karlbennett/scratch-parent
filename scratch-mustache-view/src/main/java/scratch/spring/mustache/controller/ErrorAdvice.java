package scratch.spring.mustache.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public String error() {

        return "redirect:/view/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(NOT_FOUND)
    public String notFound() {

        return "not-found.mustache";
    }
}
