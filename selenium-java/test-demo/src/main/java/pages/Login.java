package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import utils.BasePage;


public class Login extends BasePage {
    private Wait<WebDriver> wait;

    public Login(WebDriver driver){
        super(driver);
        this.pagePath = "/";
        this.visit();
    }

    // Elements
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorContainer = By.xpath("//div[@class=\"error-message-container error\"]");

    // Actions
    public Login typeUsername(String username){
        driver.findElement(usernameField).sendKeys(username);
        return this;
    }   
    public Login typePassword(String password){
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public Inventory submitLogin(){
        driver.findElement(loginButton).click();
        return new Inventory(driver);
    }

    public Login submitLoginExpectingFailures(){
        driver.findElement(loginButton).click();
        return this;
    }

    public String getErrorContainerMessage(){
        return waitForElementVisibility(errorContainer).getText();
    }

    public String getErrorContainerBackGroundColor(){
        return waitForElementVisibility(errorContainer).getCssValue("background-color");
    }
}
