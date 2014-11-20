package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.user.User;

@Component
public class UserEditPage extends UserPage {

    private final Finders finders;

    @Autowired
    protected UserEditPage(WebDriver driver, BaseUrl baseUrl) {
        super(driver, baseUrl, "/view/users/edit/", "Edit User: ");
        this.finders = new Finders(driver);
    }

    public void setValues(User user) {
        setValues(new DataUser(user));
    }

    public void setValues(EqualityUser user) {
        setEmail(user.getEmail());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPhoneNumber(user.getPhoneNumber());
        setAddress(user.getAddress());
    }

    public void setEmail(String email) {

        setValue("email", email);
    }

    public void setFirstName(String firstName) {

        setValue("firstName", firstName);
    }

    public void setLastName(String lastName) {

        setValue("lastName", lastName);
    }

    public void setPhoneNumber(String phoneNumber) {

        setValue("phoneNumber", phoneNumber);
    }

    @Override
    public AddressEditElement getAddress() {
        return new AddressEditElement(finders.findById("address"));
    }

    public void setAddress(EqualityAddress address) {
        getAddress().setValues(address);
    }

    @Override
    protected String findValue(String id) {
        return finders.findValue(id);
    }

    private void setValue(String id, String value) {
        Finders.setValue(finders.findById(id), value);
    }

    public void clickSave() {
        finders.findByClassName("save").click();
    }
}
