package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Collections.singletonMap;
import static scratch.spring.mustache.controller.ErrorMessages.findErrors;

/**
 * This class contains the mapping methods that handle invalid form validation.
 *
 * @author Karl Bennett
 */
@ControllerAdvice(assignableTypes = UserController.class)
public class FormErrorAdvice {

    @Autowired
    private ErrorMessages errorMessages;

    @ModelAttribute
    public void emailErrors(@RequestParam(required = false) List<String> emailFormErrors, Model model) {
        model.addAttribute("emailFormErrors", errorMessages.map(emailFormErrors));
    }

    @ModelAttribute
    public void firstNameErrors(@RequestParam(required = false) List<String> firstNameFormErrors, Model model) {
        model.addAttribute("firstNameFormErrors", errorMessages.map(firstNameFormErrors));
    }

    @ModelAttribute
    public void lastNameErrors(@RequestParam(required = false) List<String> lastNameFormErrors, Model model) {
        model.addAttribute("lastNameFormErrors", errorMessages.map(lastNameFormErrors));
    }

    @ExceptionHandler
    public String handle(BindException e, HttpServletRequest request) {

        final UriComponentsBuilder builder = UriComponentsBuilder.fromPath(request.getRequestURI());

        final List<FieldError> errors = e.getFieldErrors();

        builder.queryParams(singletonMultiValueMap("emailFormErrors", findErrors(errors, "email")));
        builder.queryParams(singletonMultiValueMap("firstNameFormErrors", findErrors(errors, "firstName")));
        builder.queryParams(singletonMultiValueMap("lastNameFormErrors", findErrors(errors, "lastName")));

        return "redirect:" + builder.build();
    }

    private MultiValueMap<String, String> singletonMultiValueMap(String key, List<String> values) {
        return new LinkedMultiValueMap<>(singletonMap(key, values));
    }
}
