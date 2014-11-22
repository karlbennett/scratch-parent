package scratch.spring.mustache.test.page.steps;

import org.junit.Assert;
import scratch.spring.mustache.test.page.UserMutablePage;

import static org.hamcrest.Matchers.hasItem;

class DefaultUserMutablePageThens implements UserMutablePageThens {

    private final UserMutablePage page;

    DefaultUserMutablePageThens(UserMutablePage page) {
        this.page = page;
    }

    @Override
    public void should_contain_an_email_error_of(String message) {
        Assert.assertThat("The email error message should be correct.", page.getEmailErrorMessages(), hasItem(message));
    }

    @Override
    public void should_contain_a_first_name_error_of(String message) {
        Assert.assertThat("The first name error message should be correct.", page.getFirstNameErrorMessages(),
                hasItem(message));
    }

    @Override
    public void should_contain_a_last_name_error_of(String message) {
        Assert.assertThat("The last name error message should be correct.", page.getLastNameErrorMessages(),
                hasItem(message));
    }

    @Override
    public void should_not_contain_an_email_error() {
        Then.element_should_not_exist(new Runnable() {
            @Override
            public void run() {
                page.getEmailErrorMessages();
            }
        });
    }

    @Override
    public void should_not_contain_a_first_name_error() {
        Then.element_should_not_exist(new Runnable() {
            @Override
            public void run() {
                page.getFirstNameErrorMessages();
            }
        });
    }

    @Override
    public void should_not_contain_a_last_name_error() {
        Then.element_should_not_exist(new Runnable() {
            @Override
            public void run() {
                page.getLastNameErrorMessages();
            }
        });
    }
}