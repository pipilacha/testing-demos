package com.mycompany.app.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class Login extends BasePage {

    public Login(WebDriver driver){
        super(driver);
        this.driver.get("https://www.saucedemo.com/");
    }

    // Elements
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorContainer = By.xpath("//div[@class=\"error-message-container error\"]");

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
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorContainer)).getText();
    }

    public String getErrorContainerCSSValue(String property){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorContainer)).getCssValue(property);
    }
}
