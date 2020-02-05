package pages;

import base.BaseProductsPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoutiquePage extends BaseProductsPage {

    private String boutiqueUrlPart = "butikdetay";

    private String productPlaceHolderImageUrl = "https://www.trendyol.com/Content/images/defaultThumb.jpg";

    private String classProductsContainer = "products";
    private String classProduct = "boutique-product";

    public BoutiquePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Validates products images")
    public void validateBoutiqueProductsImages() {
        validateProductImages(classProductsContainer,classProduct,productPlaceHolderImageUrl);
    }

    @Step("Clicks any product")
    public void clickAnyProduct() {
        click(By.className("boutique-product"));
    }

    public boolean isBoutiquePageOpened() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        return driver.getCurrentUrl().contains(boutiqueUrlPart);
    }
}
