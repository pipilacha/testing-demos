package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BasePage {

    /**
     * Path to the page, e.g: URL/home
     */
    protected String pagePath;
    
    protected final WebDriver driver;

    private final Wait<WebDriver> default_wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        int DEFAULT_TIMEOUT = 5;
        default_wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    protected WebElement waitForElementVisibility(By by) {
        return default_wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForElementVisibilityXSeconds(By by, int duration) {
        return new WebDriverWait(driver, Duration.ofSeconds(duration)).until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    /**
     * The path corresponding to the page object
     * @return String
     */
    public String getPagePath(){
        return this.pagePath;
    }
    
    public String getPageTitle(){
        return driver.getTitle();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    /**
     * The path in the URL of the current session
     * @return String
     */
    public String getUrlPath(){
        URI url = null;

        try {
            url = new URI(getCurrentUrl());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return url.getPath();
    }

    public Alert getAlert(){
        return driver.switchTo().alert();
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public BasePage acceptAlert() {
        driver.switchTo().alert().accept();
        return this;
    }

    public BasePage dismissAlert() {
        driver.switchTo().alert().dismiss();
        return this;
    }

    public BasePage sendKeysToAlert(String data) {
        driver.switchTo().alert().sendKeys(data);
        return this;
    }

    public BasePage switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
        return this;
    }

    public BasePage switchToFrameByNameOrId(String nameOrId) {
        driver.switchTo().frame(nameOrId);
        return this;
    }

    public BasePage switchToFrameByWebElement(WebElement element) {
        driver.switchTo().frame(element);
        return this;
    }

    public BasePage returnToParentFrame(){
        driver.switchTo().parentFrame();
        return this;
    }

    public void visit() {
        driver.get(System.getProperty("testEnv")+this.pagePath);
    }
}
