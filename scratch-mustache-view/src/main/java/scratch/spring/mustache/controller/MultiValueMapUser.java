package scratch.spring.mustache.controller;

import org.springframework.util.MultiValueMap;
import scratch.user.Address;
import scratch.user.User;

public class MultiValueMapUser extends User {

    private static final Address NULL_ADDRESS = new Address();

    public MultiValueMapUser(MultiValueMap<String, Object> map) {
        super(mapToUser(map));
    }

    private static User mapToUser(MultiValueMap<String, Object> userMap) {

        final Object id = userMap.getFirst("id");
        final Object email = userMap.getFirst("email");
        final Object firstName = userMap.getFirst("firstName");
        final Object lastName = userMap.getFirst("lastName");
        final Object phoneNumber = userMap.getFirst("phoneNumber");
        final Address address = new MultiValueMapAddress(userMap);

        return new User(
                null == id || id.toString().isEmpty() ? null : Long.valueOf(id.toString()),
                null == email ? null : email.toString(),
                null == firstName ? null : firstName.toString(),
                null == lastName ? null : lastName.toString(),
                null == phoneNumber ? null : phoneNumber.toString(),
                NULL_ADDRESS.equals(address) ? null : address
        );
    }
}
