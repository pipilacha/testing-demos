package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotTaker {

    private static WebDriver driver;

    /**
     * Set the driver needed to take screenshots
     * @param driver driver used to take screenshots
     */
    public static void setDriver(WebDriver driver){
        ScreenshotTaker.driver = driver;
    }


    /**
     * Takes a screenshot of the browser. Must set driver before, using setDriver method.
     * @param captureName used to name the capture
     */
    public static void takeCapture(String captureName) {
        String fileName = String.format("src/test/resources/screenshots/%s.png", captureName);
        System.out.printf("TEST FAILED - screenshot: %s", captureName);
        var camera = (TakesScreenshot) driver;
        File screenshot = camera.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshot.toPath(), new File(fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
