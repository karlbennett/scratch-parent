package scratch.spring.mustache.controller;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import scratch.user.Address;

import static org.junit.Assert.assertEquals;
import static scratch.spring.mustache.test.UserConstants.userOne;

public class MultiValueMapAddressTest {


    private static MultiValueMap<String, Object> buildMultiValueMap(Address address) {

        final MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.set("address.id", address.getId());
        map.set("address.number", address.getNumber());
        map.set("address.street", address.getStreet());
        map.set("address.suburb", address.getSuburb());
        map.set("address.city", address.getCity());
        map.set("address.postcode", address.getPostcode());

        return map;
    }

    @Test
    public void I_can_create_an_address_from_a_multi_value_map() {

        // Given
        final Address expected = userOne().getAddress();

        // When
        final Address actual = new MultiValueMapAddress(buildMultiValueMap(expected));

        // Then
        assertEquals("the user should be mapped correctly.", expected, actual);
    }

    @Test
    public void I_can_create_an_address_from_an_empty_multi_value_map() {

        // When
        final Address actual = new MultiValueMapAddress(new LinkedMultiValueMap<String, Object>());

        // Then
        assertEquals("the user should be mapped correctly.", new Address(), actual);
    }

    @Test
    public void I_can_create_an_address_from_a_multi_value_map_that_contains_empty_strings_in_the_number_values() {

        // Given
        final Address expected = userOne().getAddress();
        final MultiValueMap<String, Object> map = buildMultiValueMap(expected);

        expected.setId(null);
        expected.setNumber(null);
        map.set("address.id", "");
        map.set("address.number", "");

        // When
        final Address actual = new MultiValueMapAddress(map);

        // Then
        assertEquals("the user should be mapped correctly.", expected, actual);
    }
}
