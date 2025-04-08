package utils;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.support.events.EventFiringDecorator;

/**
 * Class used to configure WebDriver
 */
public class CustomizeDriver {
    /**
     * Configures the WebDriver. Browser, headless, WebDriver listener, etc.
     * @return WebDriver configured
     */
    public static WebDriver startBrowser(){
        boolean headless =  Boolean.parseBoolean(System.getProperty("headless"));

        WebDriver driver = null;
        
        switch (System.getProperty("browser")) {
            case "chromium":
                
                break;
        
            default:
                GeckoDriverService service = new GeckoDriverService.Builder().usingDriverExecutable(new File("/snap/firefox/current/usr/lib/firefox/geckodriver")).build();
                FirefoxOptions options = new FirefoxOptions().setBinary("/snap/firefox/current/usr/lib/firefox/firefox");
                if (headless) {
                    options.addArguments("--headless");
                }
                driver = new FirefoxDriver(service, options);
                break;
        }

        ScreenshotTaker.setDriver(driver);

        MyListener listener = new MyListener();

        return new EventFiringDecorator(listener).decorate(driver);

    }
}
