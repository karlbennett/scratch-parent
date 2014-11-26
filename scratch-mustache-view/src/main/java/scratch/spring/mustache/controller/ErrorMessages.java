package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class ErrorMessages {

    private final Properties properties;

    @Autowired
    public ErrorMessages(ResourceLoader resourceLoader) {

        final Resource resource = resourceLoader.getResource("classpath:form-error.properties");

        properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> map(List<String> errors) {

        if (null == errors) {
            return new ArrayList<>();
        }

        final List<String> messages = new ArrayList<>();

        for (String error : errors) {
            final String message = properties.getProperty(error);

            if (null != message) {
                messages.add(message);
            }
        }

        return messages;
    }

    public static List<String> findErrors(List<FieldError> errors, String fieldName) {
        final List<String> errorMessages = new ArrayList<>();

        for (FieldError error : errors) {
            if (fieldName.equals(error.getField())) {
                errorMessages.add(error.getDefaultMessage());
            }
        }

        return errorMessages;
    }
}
