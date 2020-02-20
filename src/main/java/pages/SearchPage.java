package pages;

import base.BasePage;
import base.BaseProductsPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPage extends BaseProductsPage {

    private String productPlaceHolderImageUrl = "https://www.trendyol.com/Content/images/defaultThumb.jpg";

    private String classProductsContainer = "prdct-cntnr-wrppr";
    private String classProduct = "p-card-wrppr";
    private String secondProduct = "//*[@id=\"search-app\"]/div/div[2]/div[2]/div[3]/div/div[2]/a/div[1]/img";

    public SearchPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Clicks any product")
    public void clickAnyProduct() {
        click(By.className(classProduct));
    }

    @Step("Clicks second product")
    public void clicSecondProduct() {
        click(By.className(secondProduct));
    }

    @Step("Validates products images")
    public void validateSearchProductsImages() {
        validateProductImages(classProductsContainer,classProduct,productPlaceHolderImageUrl);
    }

}
