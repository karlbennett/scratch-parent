package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import scratch.user.User;

import java.util.List;

import static scratch.spring.mustache.test.page.Finders.findTextsByClassName;

public class UserEditablePage extends UserPage {

    private final Finders finders;

    protected UserEditablePage(WebDriver driver) {
        super(driver);
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

    public List<String> getEmailErrorMessages() {
        return findErrorsById("emailFormErrors");
    }

    public void setFirstName(String firstName) {
        setValue("firstName", firstName);
    }

    public List<String> getFirstNameErrorMessages() {
        return findErrorsById("firstNameFormErrors");
    }

    public void setLastName(String lastName) {
        setValue("lastName", lastName);
    }

    public List<String> getLastNameErrorMessages() {
        return findErrorsById("lastNameFormErrors");
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

    public void clickSave() {
        finders.findByClassName("save").click();
    }

    @Override
    protected String findValue(String id) {
        return finders.findValue(id);
    }

    private void setValue(String id, String value) {
        Finders.setValue(finders.findById(id), value);
    }

    private List<String> findErrorsById(String id) {
        return findTextsByClassName(finders.findById(id), "form-error");
    }
}
