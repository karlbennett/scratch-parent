package scratch.spring.mustache.controller;

import org.springframework.util.MultiValueMap;
import scratch.user.Address;

public class MultiValueMapAddress extends Address {

    public MultiValueMapAddress(MultiValueMap<String, Object> map) {
        super(mapToAddress(map));
    }

    private static Address mapToAddress(MultiValueMap<String, Object> address) {

        final Object id = address.getFirst("address.id");
        final Object number = address.getFirst("address.number");
        final Object street = address.getFirst("address.street");
        final Object suburb = address.getFirst("address.suburb");
        final Object city = address.getFirst("address.city");
        final Object postcode = address.getFirst("address.postcode");

        return new Address(
                null == id || id.toString().isEmpty() ? null : Long.valueOf(id.toString()),
                null == number || number.toString().isEmpty() ? null : Integer.valueOf(number.toString()),
                null == street ? null : street.toString(),
                null == suburb ? null : suburb.toString(),
                null == city ? null : city.toString(),
                null == postcode ? null : postcode.toString()
        );
    }
}
