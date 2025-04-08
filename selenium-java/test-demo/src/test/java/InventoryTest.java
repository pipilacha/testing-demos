import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pages.Inventory;
import pages.Login;

import utils.BaseTest;
import utils.CookieSetter;
import utils.CustomizeDriver;

public class InventoryTest extends BaseTest {

    @Override
    @BeforeEach
    public void launchBrowser(){
        webDriver = CustomizeDriver.startBrowser();
        new Login(webDriver);
        CookieSetter.setStandardUser(webDriver);
    }

    @Test
    @DisplayName("Verify user can add and remove items to cart")
    public void addItemsToCart() {

        Inventory inventory = new Inventory(webDriver);
        inventory.visit();

        assertEquals(inventory.getPagePath(), inventory.getUrlPath());
        assertEquals("Products", inventory.getTitle());

        inventory.addItemToCart("Sauce Labs Backpack");
        inventory.addItemToCart("Sauce Labs Fleece Jacket");
        inventory.addItemToCart("Sauce Labs Onesie");


    }
}
