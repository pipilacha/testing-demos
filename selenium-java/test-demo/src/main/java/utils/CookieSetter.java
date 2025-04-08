package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookieSetter {

    public static void setStandardUser(WebDriver driver){
        driver.manage().addCookie(new Cookie("session-username", "standard_user", "/"));
    }
}
