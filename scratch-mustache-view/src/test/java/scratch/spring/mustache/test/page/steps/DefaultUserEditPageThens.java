package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.UserEditPage;
import scratch.user.User;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class DefaultUserEditPageThens implements UserEditPageThens {

    private final UserEditPage page;
    private final UserPageTitleThens userPageTitleThens;
    private final UserPageThens userPageThens;

    public DefaultUserEditPageThens(UserEditPage page) {
        this.page = page;
        userPageTitleThens = new DefaultUserPageTitleThens(page, "Edit User");
        userPageThens = new DefaultUserPageThens(page);
    }

    @Override
    public void should_contain_an_email_error_of(String message) {
        assertThat("The email error message should be correct.", page.getEmailErrorMessages(), hasItem(message));
    }

    @Override
    public void should_contain_a_first_name_error_of(String message) {
        assertThat("The first name error message should be correct.", page.getFirstNameErrorMessages(),
                hasItem(message));
    }

    @Override
    public void should_contain_a_last_name_error_of(String message) {
        assertThat("The last name error message should be correct.", page.getLastNameErrorMessages(),
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

    @Override
    public void should_have_a_title_containing_the_name_of(User user) {
        userPageTitleThens.should_have_a_title_containing_the_name_of(user);
    }

    @Override
    public void should_contain_the_data_from(User user) {
        userPageThens.should_contain_the_data_from(user);
    }
}
