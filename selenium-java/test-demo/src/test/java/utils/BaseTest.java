package utils;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

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
}
