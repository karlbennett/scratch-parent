package scratch.user.test;

import scratch.user.Address;

/**
 * Data for user persistence tests.
 *
 * @author Karl Bennett
 */
public class AddressConstants {

    public static final Long ADDRESS_ID = 2L;
    public static final Integer NUMBER = 3;
    public static final String STREET = "That Road";
    public static final String SUBURB = "This Place";
    public static final String CITY = "Your City";
    public static final String POST_CODE = "ABC123";

    public static final Integer NUMBER_ONE = 5;
    public static final String STREET_ONE = "That Other Road";
    public static final String SUBURB_ONE = "This Other Place";
    public static final String CITY_ONE = "Your Other City";
    public static final String POST_CODE_ONE = "DEF456";

    public static Address address() {

        return new Address(NUMBER, STREET, SUBURB, CITY, POST_CODE);
    }

    public static Address addressOne() {

        return new Address(NUMBER_ONE, STREET_ONE, SUBURB_ONE, CITY_ONE, POST_CODE_ONE);
    }
}
