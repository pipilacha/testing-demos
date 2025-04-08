import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import pages.Inventory;
import pages.Login;
import utils.BaseTest;
import utils.ColorsEnum;


/**
 * Automated tests for LogIn Page.
 */
@Tag("Login-Suite")
public class LoginTest extends BaseTest {

    /**
     * Verify a valid user can login 
     */
    @DisplayName("Verify valid Log In")
    @Test
    public void userCanLogin() {

        Login loginPage = new Login(webDriver);

        Inventory inventoryPage = loginPage.typeUsername("standard_user").typePassword("secret_sauce").submitLogin();

        assertEquals(inventoryPage.getPagePath(), inventoryPage.getUrlPath(), "Wrong URL path");

    }

    /**
     * Verify invalid users get errors
     */
    @DisplayName("Verify invalid Log In")
    @ParameterizedTest(name =  "Run [{index}]: {0}")
    @CsvFileSource(files = "src/test/resources/test-data/login/invalid-users.csv", numLinesToSkip = 1)
    public void invalidLogin(String CASE, String username, String password, String error) {

        Login loginPage = new Login(webDriver).typeUsername(username).typePassword(password).submitLoginExpectingFailures();

        assertEquals(loginPage.getPagePath(), loginPage.getUrlPath());
        assertEquals(error, loginPage.getErrorContainerMessage());
        assertEquals(ColorsEnum.ERROR_RED.getColorAsRGB(), loginPage.getErrorContainerBackGroundColor());
    }
}
