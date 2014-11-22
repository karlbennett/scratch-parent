package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import scratch.user.User;
import scratch.user.Users;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Collections.singletonMap;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static scratch.spring.mustache.controller.ErrorMessages.findErrors;

/**
 * This controller contains the mappings for all the user CRUD pages.
 *
 * @author Karl Bennett
 */
@Controller
@RequestMapping("/view/users")
public class UserController {

    @Autowired
    private Users users;

    @Autowired
    private ErrorMessages errorMessages;

    @RequestMapping(method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> users() {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("users.mustache", "users", users.retrieve());
            }
        };
    }

    @RequestMapping(value = "/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> user(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("user.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/edit/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> editUser(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("user-edit.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/edit/{id}", method = POST,
            consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = TEXT_HTML_VALUE)
    public Callable<String> editUser(@PathVariable final Long id, @Valid final User user) {

        user.setId(id);

        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                users.update(user);

                return "redirect:/view/users/" + id;
            }
        };
    }

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
