package scratch.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import javax.validation.Valid;
import java.util.concurrent.Callable;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * A controller that maps the {@link Users} interface into RESTful requests.
 *
 * @author Karl Bennett
 */
@RestController
@RequestMapping("/rest/users")
public class UserController {

    @Autowired
    private Users users;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Callable<Id> create(@Valid @RequestBody final User user) {

        return new Callable<Id>() {

            @Override
            public Id call() throws Exception {

                return users.create(user);
            }
        };
    }

    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public Callable<User> retrieve(@PathVariable final Long id) {

        return new Callable<User>() {

            @Override
            public User call() throws Exception {

                return users.retrieve(id);
            }
        };
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Callable<Iterable<User>> retrieve() {

        return new Callable<Iterable<User>>() {

            @Override
            public Iterable<User> call() throws Exception {

                return users.retrieve();
            }
        };
    }

    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public Callable<String> update(@PathVariable Long id, @Valid @RequestBody final User user) {

        user.setId(id);

        return new Callable<String>() {

            @Override
            public String call() throws Exception {

                users.update(user);

                return "";
            }
        };
    }

    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public Callable<String> delete(@PathVariable final Long id) {

        return new Callable<String>() {

            @Override
            public String call() throws Exception {

                users.delete(id);

                return "";
            }
        };
    }

    @RequestMapping(method = DELETE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public Callable<String> deleteAll() {

        return new Callable<String>() {

            @Override
            public String call() throws Exception {

                users.deleteAll();

                return "";
            }
        };
    }

    public static class ErrorResponse {

        private final String error;

        private final String message;


        public ErrorResponse(String error, String message) {

            this.error = error;
            this.message = message;
        }


        public String getError() {

            return error;
        }

        public String getMessage() {

            return message;
        }
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleException(IllegalArgumentException e) {

        return new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleException(Exception e) {

        return new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
    }
}
