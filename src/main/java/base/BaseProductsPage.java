package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

abstract public class BaseProductsPage extends BasePage {

    public BaseProductsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

             //Container products  -  classProduct = boutique-product
    //Container prdct-cntnr-wrppr  -  classProduct = p-card-wrppr

    public void validateProductImages(String classProductsContainer, String classProduct, String productPlaceHolderImageUrl) {
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElements(driver
                    .findElement(By.className(classProductsContainer)).findElements(By.className(classProduct))));
        for (WebElement product : products) {
            String imageUrl = getImageUrl(product, productPlaceHolderImageUrl);
            validateImageUrl(imageUrl);
        }
    }

}
