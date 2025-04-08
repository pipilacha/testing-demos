package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

import java.util.List;


public class Inventory extends BasePage {

    private By title = By.cssSelector("span[data-test='title']");
    private By inventory = By.cssSelector("div[data-test='inventory-list']");
    private By item = By.cssSelector("div[data-test='inventory-item']");

    public Inventory(WebDriver driver){
        super(driver);
        this.pagePath = "/inventory.html";
    }

    public String getTitle() {
        return driver.findElement(title).getText();
    }

    public List<WebElement> getInventoryItems(){
        return driver.findElement(inventory).findElements(item);
    }

    public Item getItemByName(String name){
        for (WebElement e: getInventoryItems()){
            Item product = new Item(driver, e);
            if (product.getLabel().equals(name)){
                return product;
            }
        }
        return null;
    }

    public Item getItemByIndex(int index){
        return new Item(driver, getInventoryItems().get(index));
    }

    public Inventory addItemToCart(String itemName){
        getItemByName(itemName).clickAddToCart();
        return this;
    }

    public class Item extends BasePage {

        private WebElement card;
        private String normalizedName;
        private By label = By.cssSelector("div[data-test='inventory-item-name']");
        private By addToCartBtn; // locator is assigned value when instantiating

        public Item(WebDriver driver, WebElement item) {
            super(driver);
            card = item;
            normalizedName =getLabel().toLowerCase().replace(" ", "-");
            addToCartBtn = By.cssSelector(String.format("button[data-test='add-to-cart-%s']", normalizedName));
        }

        public String getLabel() {
            return card.findElement(label).getText();
        }

        public Item clickAddToCart(){
            card.findElement(addToCartBtn).click();
            return this;
        }
    }
}
