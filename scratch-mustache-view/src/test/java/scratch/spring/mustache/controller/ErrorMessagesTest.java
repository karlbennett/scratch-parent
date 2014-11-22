package scratch.spring.mustache.controller;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.FieldError;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scratch.spring.mustache.controller.ErrorMessages.findErrors;

public class ErrorMessagesTest {

    private static final String PROPERTY_PATH = "classpath:form-error.properties";

    @Test
    public void I_can_map_an_error_message() throws IOException {

        // Given
        final String error = "test.message";
        final String message = "Test message.";

        final Resource resource = mock(Resource.class);
        when(resource.getInputStream())
                .thenReturn(new ByteArrayInputStream(format("%s=%s", error, message).getBytes()));

        final ResourceLoader resourceLoader = mock(ResourceLoader.class);
        when(resourceLoader.getResource(PROPERTY_PATH)).thenReturn(resource);

        // When
        final List<String> actual = new ErrorMessages(resourceLoader).map(singletonList(error));

        // Then
        assertEquals("The test message should be correct.", singletonList(message), actual);
    }

    @Test
    public void I_can_map_multiple_error_message() throws IOException {

        // Given
        final String errorOne = "test.message.one";
        final String messageOne = "Test message one.";
        final String errorTwo = "test.message.two";
        final String messageTwo = "Test message two.";
        final String errorThree = "test.message.three";
        final String messageThree = "Test message three.";

        final Resource resource = mock(Resource.class);
        when(resource.getInputStream())
                .thenReturn(new ByteArrayInputStream(
                        format("%s=%s\n%s=%s\n%s=%s",
                                errorOne, messageOne,
                                errorTwo, messageTwo,
                                errorThree, messageThree
                        ).getBytes()));

        final ResourceLoader resourceLoader = mock(ResourceLoader.class);
        when(resourceLoader.getResource(PROPERTY_PATH)).thenReturn(resource);

        // When
        final List<String> actual = new ErrorMessages(resourceLoader).map(asList(errorOne, errorTwo, errorThree));

        // Then
        assertEquals("The test messages should be correct.", asList(messageOne, messageTwo, messageThree), actual);
    }

    @Test
    public void I_can_map_no_error_message() throws IOException {

        // Given
        final Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));

        final ResourceLoader resourceLoader = mock(ResourceLoader.class);
        when(resourceLoader.getResource(PROPERTY_PATH)).thenReturn(resource);

        // When
        final List<String> actual = new ErrorMessages(resourceLoader).map(singletonList("some.error"));

        // Then
        assertThat("No error messages should be found.", actual, empty());
    }

    @Test
    public void I_can_map_null_error_message() throws IOException {

        // Given
        final Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));

        final ResourceLoader resourceLoader = mock(ResourceLoader.class);
        when(resourceLoader.getResource(PROPERTY_PATH)).thenReturn(resource);

        // When
        final List<String> actual = new ErrorMessages(resourceLoader).map(null);

        // Then
        assertThat("No error messages should be found.", actual, empty());
    }

    @Test(expected = RuntimeException.class)
    public void I_cannot_map_errors_if_the_properties_file_does_not_exist() throws IOException {

        // Given
        final Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenThrow(new FileNotFoundException("error map test file not found."));

        final ResourceLoader resourceLoader = mock(ResourceLoader.class);
        when(resourceLoader.getResource(PROPERTY_PATH)).thenReturn(resource);

        // When
        new ErrorMessages(resourceLoader).map(singletonList("some.error"));
    }

    @Test
    public void I_can_find_specific_errors() throws IOException {

        // Given
        final String fieldOne = "testFieldOne";
        final String fieldTwo = "testFieldTwo";
        final String messageOne = "Test message one.";
        final String messageTwo = "Test message two.";
        final String messageThree = "Test message three.";

        final FieldError errorOne = mock(FieldError.class);
        when(errorOne.getField()).thenReturn(fieldOne);
        when(errorOne.getDefaultMessage()).thenReturn(messageOne);
        final FieldError errorTwo = mock(FieldError.class);
        when(errorTwo.getField()).thenReturn(fieldTwo);
        when(errorTwo.getDefaultMessage()).thenReturn(messageTwo);
        final FieldError errorThree = mock(FieldError.class);
        when(errorThree.getField()).thenReturn(fieldOne);
        when(errorThree.getDefaultMessage()).thenReturn(messageThree);

        // When
        final List<String> actual = findErrors(asList(errorOne, errorTwo, errorThree), fieldOne);

        // Then
        assertEquals("The test errors should be correct.", asList(messageOne, messageThree), actual);
    }

    @Test
    public void I_cannot_find_an_unknown_error() throws IOException {

        // Given
        final String fieldOne = "testFieldOne";
        final String fieldTwo = "testFieldTwo";
        final String fieldThree = "testFieldThree";
        final String messageOne = "Test message one.";
        final String messageTwo = "Test message two.";
        final String messageThree = "Test message three.";

        final FieldError errorOne = mock(FieldError.class);
        when(errorOne.getField()).thenReturn(fieldOne);
        when(errorOne.getDefaultMessage()).thenReturn(messageOne);
        final FieldError errorTwo = mock(FieldError.class);
        when(errorTwo.getField()).thenReturn(fieldTwo);
        when(errorTwo.getDefaultMessage()).thenReturn(messageTwo);
        final FieldError errorThree = mock(FieldError.class);
        when(errorThree.getField()).thenReturn(fieldThree);
        when(errorThree.getDefaultMessage()).thenReturn(messageThree);

        // When
        final List<String> actual = findErrors(asList(errorOne, errorTwo, errorThree), "testFieldFour");

        // Then
        assertThat("No errors should be found.", actual, empty());
    }

    @Test
    public void I_cannot_find_an_error_when_none_exist() throws IOException {

        // When
        final List<String> actual = findErrors(Collections.<FieldError>emptyList(), "testField");

        // Then
        assertThat("No errors should be found.", actual, empty());
    }
}
