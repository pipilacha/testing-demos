package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import com.mycompany.app.POM.Inventory;
import com.mycompany.app.POM.Login;

/**
 * Unit test for simple App.
 */
public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    public void SetUp(){
        driver = new FirefoxDriver(
            new GeckoDriverService.Builder().usingDriverExecutable(new File("/snap/firefox/current/usr/lib/firefox/geckodriver")).build(),        
            new FirefoxOptions().setBinary("/snap/firefox/current/usr/lib/firefox/firefox")
        );
    }

    @AfterEach
    public void TearDown(){
        driver.quit();
    }

    /**
     * Verify a valid user can login 
     */
    @Test
    public void UserCanLogin() {

        Login loginPage = new Login(driver);

        Inventory inventoryPage = loginPage.typeUsername("standard_user").typePassword("secret_sauce").submitLogin();

        assertEquals("/inventory.html", inventoryPage.getUrlPath());

    }

    /**
     * Verify a locked out user cannot login 
     */
    @Test
    public void BlockedUserCannotLogin() {

        Login loginPage = new Login(driver).typeUsername("locked_out_user").typePassword("secret_sauce").submitLoginExpectingFailures();

        assertEquals("/", loginPage.getUrlPath());
        assertEquals("Epic sadface: Sorry, this user has been locked out.", loginPage.getErrorContainerMessage());
        assertEquals("rgb(226, 35, 26)", loginPage.getErrorContainerCSSValue("background-color"));
    }

    /**
     * Verify a user with invalid credentials cannot login
     */
    @Test
    public void InvalidCredentialsCannotLogin() {

        Login loginPage = new Login(driver).typeUsername("standard_user").typePassword("arroz").submitLoginExpectingFailures();

        assertEquals("/", loginPage.getUrlPath());
        assertEquals("Epic sadface: Username and password do not match any user in this service", loginPage.getErrorContainerMessage());
        assertEquals("rgb(226, 35, 26)", loginPage.getErrorContainerCSSValue("background-color"));
    }

    /**
     * Verify login form cannot be submitted with empty fields
     * 
     */
    @Test
    public void CannotSendFormWithEmptyFields() {

        Login loginPage = new Login(driver).submitLoginExpectingFailures();
        assertEquals("/", loginPage.getUrlPath());
        assertEquals("Epic sadface: Username is required", loginPage.getErrorContainerMessage());
        assertEquals("rgb(226, 35, 26)", loginPage.getErrorContainerCSSValue("background-color"));

        loginPage = new Login(driver).typeUsername("standard_user").submitLoginExpectingFailures();
        assertEquals("/", loginPage.getUrlPath());
        assertEquals("Epic sadface: Password is required", loginPage.getErrorContainerMessage());
        assertEquals("rgb(226, 35, 26)", loginPage.getErrorContainerCSSValue("background-color"));

        loginPage = new Login(driver).typePassword("secret_sauce").submitLoginExpectingFailures();
        assertEquals("/", loginPage.getUrlPath());
        assertEquals("Epic sadface: Username is required", loginPage.getErrorContainerMessage());
        assertEquals("rgb(226, 35, 26)", loginPage.getErrorContainerCSSValue("background-color"));

    }
}
