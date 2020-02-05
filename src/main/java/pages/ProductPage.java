package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    private String xPathAddToBasketButton = "//div[@class='add-to-bs-tx']";

    public ProductPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Adds product to basket")
    public void addToBasket() {
        click(By.xpath(xPathAddToBasketButton));
    }

    @Step("If there is size option, choose size")
    public void chooseSize() {
        try {
            driver.findElement(By.xpath("//div[@class='pr-in-sz-pk']")).click();
            driver.findElement(By.xpath("//div[@class='pr-in-at']//li[1]")).click();
        } catch (Exception ignored) {
        }
    }
}
