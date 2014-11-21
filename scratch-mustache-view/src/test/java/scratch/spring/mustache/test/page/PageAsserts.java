package scratch.spring.mustache.test.page;

import scratch.user.User;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static scratch.spring.mustache.test.UserConstants.containsAll;

public class PageAsserts {

    public static HomePageAssertions The(final HomePage page) {
        return new HomePageAssertions(page);
    }

    public static UserViewPageAssertions The(final UserViewPage page) {
        return new UserViewPageAssertions(page);
    }

    public static UserEditPageAssertions The(final UserEditPage page) {
        return new UserEditPageAssertions(page);
    }

    public static class PageAssertions {

        private final Page page;

        private PageAssertions(Page page) {
            this.page = page;
        }

        public void should_have_a_title_of(String title) {
            assertEquals("The title should be correct.", title, page.getTitle());
        }
    }

    public static class HomePageAssertions extends PageAssertions {

        private final HomePage page;

        private HomePageAssertions(HomePage page) {
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

    public static class UserPageAssertions extends PageAssertions {

        private final UserPage page;
        private final String titlePrefix;

        private UserPageAssertions(UserPage page, String titlePrefix) {
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

    public static class UserViewPageAssertions extends UserPageAssertions {

        private UserViewPageAssertions(UserViewPage page) {
            super(page, "User");
        }
    }

    public static class UserEditPageAssertions extends UserPageAssertions {

        private UserEditPageAssertions(UserEditPage page) {
            super(page, "Edit User");
        }
    }
}
