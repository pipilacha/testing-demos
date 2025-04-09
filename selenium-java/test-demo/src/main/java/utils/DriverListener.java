package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Listener for WebDriver actions. Used mostly for logging.
 */
public class DriverListener implements WebDriverListener {

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        System.out.printf("Looking for element: %s%n", locator.toString());
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        System.out.printf("Found element: %s%n", locator);
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        System.out.printf("WebDriver error - %s%n", e.getCause().getMessage());
    }
}
