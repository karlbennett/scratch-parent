package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.UserEditPage;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class UserEditPageThens extends UserPageThens {

    private final UserEditPage page;

    public UserEditPageThens(UserEditPage page) {
        super(page, "Edit User");
        this.page = page;
    }

    public void should_contain_an_email_error_of(String message) {
        assertThat("The email error message should be correct.", page.getEmailErrorMessages(), hasItem(message));
    }

    public void should_contain_a_first_name_error_of(String message) {
        assertThat("The first name error message should be correct.", page.getFirstNameErrorMessages(),
                hasItem(message));
    }

    public void should_contain_a_last_name_error_of(String message) {
        assertThat("The last name error message should be correct.", page.getLastNameErrorMessages(),
                hasItem(message));
    }

    public void should_not_contain_an_email_error() {
        Then.element_should_not_exist(new Runnable() {
            @Override
            public void run() {
                page.getEmailErrorMessages();
            }
        });
    }

    public void should_not_contain_a_first_name_error() {
        Then.element_should_not_exist(new Runnable() {
            @Override
            public void run() {
                page.getFirstNameErrorMessages();
            }
        });
    }

    public void should_not_contain_a_last_name_error() {
        Then.element_should_not_exist(new Runnable() {
            @Override
            public void run() {
                page.getLastNameErrorMessages();
            }
        });
    }
}
