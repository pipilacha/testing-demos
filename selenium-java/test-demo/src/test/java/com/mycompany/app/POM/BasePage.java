package com.mycompany.app.POM;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
public abstract class BasePage {
    
    final WebDriver driver;
    Wait<WebDriver> wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }
    
    public void quit(){
        driver.quit();
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getUrlPath(){
        URI url = null;

        try {
            url = new URI(getCurrentUrl());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return url.getPath();
    }
}
