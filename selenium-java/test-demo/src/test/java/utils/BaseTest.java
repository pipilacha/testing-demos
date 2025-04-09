package utils;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(AfterExecutionCallbacks.class)
public class BaseTest {

    protected WebDriver webDriver;

    @BeforeEach
	public void launchBrowser(){
		webDriver = CustomizeDriver.startBrowser();
	}

	@AfterEach
	public void closeBrowser(){
		webDriver.quit();
	}

	protected void takeScreenshot(String captureName){
		Screenshotter.takeCapture(webDriver, captureName);
	}
}
