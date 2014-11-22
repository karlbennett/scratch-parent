package scratch.spring.mustache.test.page;

import org.openqa.selenium.NoSuchElementException;
import scratch.user.User;
import scratch.user.Users;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static scratch.spring.mustache.test.UserConstants.containsAll;

public class Then {

    public static HomePageThens Then_the(HomePage page) {
        return new HomePageThens(page);
    }

    public static UserViewPageThens Then_the(UserViewPage page) {
        return new UserViewPageThens(page);
    }

    public static UserEditPageThens Then_the(UserEditPage page) {
        return new UserEditPageThens(page);
    }

    public static UsersThens Then_the_mock(Users users) {
        return new UsersThens(users);
    }

    public static User copyData(User from, User to) {

        final User copy = new User(to);
        copy.setEmail(from.getEmail());
        copy.setFirstName(from.getFirstName());
        copy.setLastName(from.getLastName());
        copy.setPhoneNumber(from.getPhoneNumber());
        copy.getAddress().setNumber(from.getAddress().getNumber());
        copy.getAddress().setStreet(from.getAddress().getStreet());
        copy.getAddress().setSuburb(from.getAddress().getSuburb());
        copy.getAddress().setCity(from.getAddress().getCity());
        copy.getAddress().setPostcode(from.getAddress().getPostcode());

        return copy;
    }

    public static void element_should_not_exist(Runnable runnable) {
        try {
            runnable.run();
            fail("The element should not exist.");
        } catch (NoSuchElementException e) {
        }
    }

    public static class PageThens {

        private final Page page;

        private PageThens(Page page) {
            this.page = page;
        }

        public void should_have_a_title_of(String title) {
            assertEquals("The title should be correct.", title, page.getTitle());
        }
    }

    public static class HomePageThens extends PageThens {

        private final HomePage page;

        private HomePageThens(HomePage page) {
            super(page);
            this.page = page;
        }

        public void should_contain_rows_for(User... users) {

            final List<EqualityUserRow> rows = new ArrayList<>(users.length);

            for (User user : users) {
                rows.add(new DataUserRow(user));
            }

            should_contain_a_row_for(rows);
        }

        public void should_contain_a_row_for(List<EqualityUserRow> rows) {

            assertThat("The correct users should be listed.", page.users(), containsAll(rows));
        }
    }

    public static class UserPageThens extends PageThens {

        private final UserPage page;
        private final String titlePrefix;

        private UserPageThens(UserPage page, String titlePrefix) {
            super(page);
            this.page = page;
            this.titlePrefix = titlePrefix;
        }

        public void should_have_a_title_containing_the_name_of(User user) {
            assertThat("The title should contain the correct user name.", page.getTitle(),
                    containsString(format("%s: %s %s", titlePrefix, user.getFirstName(), user.getLastName())));
        }

        public void should_contain_the_data_from(User user) {
            should_contain_the_data_from(new DataUser(user));
        }

        public void should_contain_the_data_from(EqualityUser user) {
            assertEquals("The page should contain the correct user data.", user, page);
        }
    }

    public static class UserViewPageThens extends UserPageThens {

        private UserViewPageThens(UserViewPage page) {
            super(page, "User");
        }
    }

    public static class UserEditPageThens extends UserPageThens {

        private final UserEditPage page;

        private UserEditPageThens(UserEditPage page) {
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
            element_should_not_exist(new Runnable() {
                @Override
                public void run() {
                    page.getEmailErrorMessages();
                }
            });
        }

        public void should_not_contain_a_first_name_error() {
            element_should_not_exist(new Runnable() {
                @Override
                public void run() {
                    page.getFirstNameErrorMessages();
                }
            });
        }

        public void should_not_contain_a_last_name_error() {
            element_should_not_exist(new Runnable() {
                @Override
                public void run() {
                    page.getLastNameErrorMessages();
                }
            });
        }
    }

    public static class UsersThens {

        private final Users users;

        private UsersThens(Users users) {
            this.users = users;
        }

        public void should_receive_an_update_with_data_from(User user) {
            verify(users).update(user);
        }

        public void should_not_have_received_an_update() {
            verify(users, never()).update(any(User.class));
        }
    }
}
