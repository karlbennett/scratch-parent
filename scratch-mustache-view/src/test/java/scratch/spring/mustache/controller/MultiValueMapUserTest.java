package scratch.spring.mustache.controller;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import scratch.user.User;

import static org.junit.Assert.assertEquals;
import static scratch.spring.mustache.test.UserConstants.userOne;

public class MultiValueMapUserTest {

    private static MultiValueMap<String, Object> buildMultiValueMap(User user) {

        final MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", user.getId());
        map.add("email", user.getEmail());
        map.add("firstName", user.getFirstName());
        map.add("lastName", user.getLastName());
        map.add("phoneNumber", user.getPhoneNumber());
        map.add("address.id", user.getAddress().getId());
        map.add("address.number", user.getAddress().getNumber());
        map.add("address.street", user.getAddress().getStreet());
        map.add("address.suburb", user.getAddress().getSuburb());
        map.add("address.city", user.getAddress().getCity());
        map.add("address.postcode", user.getAddress().getPostcode());

        return map;
    }

    @Test
    public void I_can_create_a_user_from_a_multi_value_map() {

        // Given
        final User expected = userOne();

        // When
        final User actual = new MultiValueMapUser(buildMultiValueMap(expected));

        // Then
        assertEquals("the user should be mapped correctly.", expected, actual);
    }

    @Test
    public void I_can_create_a_user_from_an_empty_multi_value_map() {

        // When
        final User actual = new MultiValueMapUser(new LinkedMultiValueMap<String, Object>());

        // Then
        assertEquals("the user should be mapped correctly.", new User(null, null, null, null, null), actual);
    }

    @Test
    public void I_can_create_a_user_from_a_multi_value_map_that_contains_empty_strings_in_the_number_values() {

        // Given
        final User expected = userOne();
        final MultiValueMap<String, Object> map = buildMultiValueMap(expected);

        expected.setId(null);
        map.set("id", "");

        // When
        final User actual = new MultiValueMapUser(map);

        // Then
        assertEquals("the user should be mapped correctly.", expected, actual);
    }
}
