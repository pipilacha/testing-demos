
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import utils.BasePage;
import utils.BaseTest;

import java.time.Duration;
import java.util.List;

public class PracticeTests extends BaseTest {

    @Test
    public void practice1() {

        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.findElement(By.cssSelector("a[href='/shifting_content']")).click();
        webDriver.findElement(By.linkText("Example 1: Menu Element")).click();
        List<WebElement> n_elements = webDriver.findElements(By.xpath("//div[@class='example']/ul/li/a"));
        System.out.println("Found " + n_elements.size() + " elements");
    }

    @Test
    public void practice2(){

        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.findElement(By.cssSelector("a[href='/forgot_password']")).click();
        webDriver.findElement(By.id("email")).sendKeys("mail@mail.com");

    }

    /**
     * Uses Keys to pass advanced ones like: right arrow.
     * You can use chord() to pass key combinations.
     * Keys.chord(Keys.ALT, "64")
     * @throws InterruptedException example for Keys
     */
    @Test
    public void advancedKeys() throws InterruptedException {

        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.findElement(By.cssSelector("a[href='/horizontal_slider']")).click();
        while(!webDriver.findElement(By.id("range")).getText().equals("4")){
            webDriver.findElement(By.cssSelector("input[type='range']")).sendKeys(Keys.ARROW_RIGHT);
        }

        Thread.sleep(500);

        // simulating copy paste
        webDriver.get("https://the-internet.herokuapp.com/key_presses");
        WebElement input =  webDriver.findElement(By.id("target"));
        new Actions(webDriver)
                .sendKeys(input, "Selenium!")
                .sendKeys(Keys.ARROW_LEFT)
                .keyDown(Keys.SHIFT)
                .sendKeys(Keys.ARROW_UP)
                .keyUp(Keys.SHIFT)
                .keyDown(Keys.LEFT_CONTROL)
                .sendKeys("xvv")
                .keyUp(Keys.LEFT_CONTROL)
                .perform();


        assertEquals("SeleniumSelenium!", input.getDomProperty("value"));

        // simulates selected all then delete
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys("jajaja!");

        assertEquals("jajaja!", input.getDomProperty("value"));

        Thread.sleep(2000);

    }

    @Test
    public void alerts() throws InterruptedException {

        BasePage page = new BasePage(webDriver);

        webDriver.get("https://the-internet.herokuapp.com/javascript_alerts");

        WebElement result = webDriver.findElement(By.id("result"));

        webDriver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        assertEquals("I am a JS Alert", page.getAlertText());
        page.dismissAlert();

        Thread.sleep(500);

        webDriver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        assertEquals("I am a JS Confirm", page.getAlertText());
        page.acceptAlert();
        assertEquals("You clicked: Ok", result.getText());

        webDriver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        assertEquals("I am a JS prompt", page.getAlertText());
        page.sendKeysToAlert("Juana la cubana");
        page.acceptAlert();
        assertEquals("You entered: Juana la cubana", result.getText());


    }

    @Test
    public void advancedActions() throws InterruptedException {

        webDriver.get("https://the-internet.herokuapp.com/context_menu");
        WebElement element = webDriver.findElement(By.id("hot-spot"));

        new Actions(webDriver).moveToElement(element).contextClick(element).perform();

        assertTrue(webDriver.switchTo().alert().getText().contains("context menu"));

        Thread.sleep(500);

    }

    @Test
    public void fileUpload() throws InterruptedException {

        webDriver.get("https://the-internet.herokuapp.com/upload");

        WebElement uploadInput = webDriver.findElement(By.id("file-upload"));
        uploadInput.sendKeys("/home/pipilacha/test-demos/selenium-java/test-demo/src/test/resources/test-data/login/valid-users.csv");
        assertTrue(uploadInput.getDomProperty("value").contains("valid-users.csv"));

        webDriver.findElement(By.id("file-submit")).click();

        assertTrue(webDriver.findElement(By.id("uploaded-files")).getText().contains("valid-users.csv"));

        Thread.sleep(500);

    }

    @Test
    public void frames(){

        BasePage page = new BasePage(webDriver);

        webDriver.get("https://the-internet.herokuapp.com/nested_frames");

        page.switchToFrameByWebElement(webDriver.findElement(By.cssSelector("frame[name='frame-top']")));

        page.switchToFrameByWebElement(webDriver.findElement(By.cssSelector("frame[name='frame-left']")));

        assertEquals("LEFT", webDriver.findElement(By.tagName("body")).getText());

        //
        // We need to switch back to the main content because we went 2 levels deep. Root > top frame > left frame
        // left frame > top frame > ROOT > bottom frame
        //
        page.returnToParentFrame().returnToParentFrame();
        page.switchToFrameByWebElement(webDriver.findElement(By.cssSelector("frame[name='frame-bottom']")));

        assertEquals("BOTTOM", webDriver.findElement(By.tagName("body")).getText());

    }

    @Test
    public void waits() {

        webDriver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        webDriver.findElement(By.cssSelector("#start>button")).click();

        // Fluent
        Wait<WebDriver> fwait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        fwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish>h4")));

        // Explicit
        webDriver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        webDriver.findElement(By.cssSelector("#start>button")).click();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        assertEquals(
                "Hello World!",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish>h4"))).getText()
        );
    }

    @Test
    public void executeJavaScript() throws InterruptedException{

        webDriver.get("https://the-internet.herokuapp.com/dropdown");

        Select select = new Select(webDriver.findElement(By.id("dropdown")));

        select.selectByValue("1");

        assertEquals(1, select.getAllSelectedOptions().size());

        String changeSelectToMultipleScript = "arguments[0].setAttribute('multiple','')";

        ((JavascriptExecutor) webDriver).executeScript(changeSelectToMultipleScript, select);

        select.selectByValue("2");
        select.selectByValue("1");

        assertEquals(2, select.getAllSelectedOptions().size());
        assertTrue(
                select.getAllSelectedOptions().stream().map(WebElement::getText).toList().containsAll(List.of("Option 1","Option 2"))
        );

        // elect.getAllSelectedOptions().stream().map(e -> e.getText()).collect(Collectors.toList())

        Thread.sleep(2000);
    }
}