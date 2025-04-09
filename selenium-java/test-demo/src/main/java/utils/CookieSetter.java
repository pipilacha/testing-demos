package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

/**
 * Utility used to set cookies.
 */
public class CookieSetter {

    /**
     * Sets the cookie as if standard_user logged in
     * @param driver the driver of the session
     */
    public static void setStandardUser(WebDriver driver){
        driver.manage().addCookie(new Cookie("session-username", "standard_user", "/"));
    }
}
