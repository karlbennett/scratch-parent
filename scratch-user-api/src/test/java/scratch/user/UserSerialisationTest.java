package scratch.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static scratch.user.test.AddressConstants.ADDRESS_ID;
import static scratch.user.test.UserConstants.USER_ID;
import static scratch.user.test.UserConstants.user;

public class UserSerialisationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void I_can_serialise_a_user() throws JsonProcessingException {

        final User user = user();
        user.setId(USER_ID);

        final Address address = user.getAddress();
        address.setId(ADDRESS_ID);

        assertEquals("the user should serialise correctly.",
                format("{" +
                        /**/"\"id\":%d," +
                        /**/"\"email\":\"%s\"," +
                        /**/"\"firstName\":\"%s\"," +
                        /**/"\"lastName\":\"%s\"," +
                        /**/"\"phoneNumber\":\"%s\"," +
                        /**/"\"address\":{" +
                        /*                */"\"id\":%d," +
                        /*                */"\"number\":%d," +
                        /*                */"\"street\":\"%s\"," +
                        /*                */"\"suburb\":\"%s\"," +
                        /*                */"\"city\":\"%s\"," +
                        /*                */"\"postcode\":\"%s\"" +
                        /**/"}" +
                        "}",
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhoneNumber(),
                        address.getId(),
                        address.getNumber(),
                        address.getStreet(),
                        address.getSuburb(),
                        address.getCity(),
                        address.getPostcode()
                ),
                MAPPER.writeValueAsString(user)
        );
    }

    @Test
    public void I_can_deserialise_a_user() throws IOException {

        final User user = user();
        user.setId(USER_ID);
        user.getAddress().setId(ADDRESS_ID);

        assertEquals("the user should deserialise correctly.", user, MAPPER.readValue(json(), User.class));
    }

    @SuppressWarnings("ConstantConditions")
    private static InputStream json() throws IOException {

        return Thread.currentThread().getContextClassLoader().getResource("user.json").openStream();
    }
}
