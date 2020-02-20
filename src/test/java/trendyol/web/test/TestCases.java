package trendyol.web.test;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import pages.BoutiquePage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import config.TestConfig;

import java.util.Objects;

public class TestCases extends BaseWebTest {

    @Test(priority = 6)
    @Description("Search in Homepage")
    @Severity(SeverityLevel.MINOR)
    public void search()  {
        HomePage homePage = new HomePage(driver, wait);
        SearchPage searchPage = new SearchPage(driver, wait);
        ProductPage productPage = new ProductPage(driver, wait);
        homePage.goToTrendyol();
        homePage.fillSearchField(Objects.requireNonNull(TestConfig.getProperty("search")));
        searchPage.clicSecondProduct();
        productPage.addToBasket();
    }

    @Test(priority = 1)
    @Description("Successfully sign-in")
    @Severity(SeverityLevel.BLOCKER)
    public void successSignIn() throws InterruptedException {
        HomePage homePage = new HomePage(driver, wait);
        homePage.goToTrendyol();
        homePage.clickSignInButton();
        homePage.fillEmailField(Objects.requireNonNull(TestConfig.getProperty("email")));
        homePage.fillPasswordField(Objects.requireNonNull(TestConfig.getProperty("password")));
        homePage.clickSignInSubmit();
        homePage.closeSignInPopUp();
    }

    @Test(priority = 2)
    @Description("Check for boutique images")
    @Severity(SeverityLevel.BLOCKER)
    public void checkForBoutiqueImages() {
        HomePage homePage = new HomePage(driver, wait);
        homePage.goToTrendyol();
        homePage.checkForBoutiqueImages();
    }

    @Test(priority = 3)
    @Description("Check for products images")
    @Severity(SeverityLevel.BLOCKER)
    public void checkForProductImages() {
        HomePage homePage = new HomePage(driver, wait);
        BoutiquePage boutiquePage = new BoutiquePage(driver, wait);
        SearchPage searchPage = new SearchPage(driver, wait);
        homePage.goToTrendyol();
        homePage.clickTab(2);
        homePage.clickAnyBoutique();
        if (boutiquePage.isBoutiquePageOpened()) {
            boutiquePage.validateBoutiqueProductsImages();
        } else {
            searchPage.validateSearchProductsImages();
        }
    }

    @Test(priority = 4)
    @Description("Go to product page")
    @Severity(SeverityLevel.BLOCKER)
    public void goToProductPage() {
        //HomePage homePage = new HomePage(driver, wait);
        BoutiquePage boutiquePage = new BoutiquePage(driver, wait);
        SearchPage searchPage = new SearchPage(driver, wait);
        //homePage.goToTrendyol();
        //homePage.clickTab(5);
        //homePage.clickAnyBoutique();
        if (boutiquePage.isBoutiquePageOpened()) {
            boutiquePage.clickAnyProduct();
        } else {
            searchPage.clickAnyProduct();
        }
    }

    @Test(priority = 5)
    @Description("Add to cart")
    @Severity(SeverityLevel.BLOCKER)
    public void addToBasket() {
        HomePage homePage = new HomePage(driver, wait);
        BoutiquePage boutiquePage = new BoutiquePage(driver, wait);
        SearchPage searchPage = new SearchPage(driver, wait);
        ProductPage productPage = new ProductPage(driver, wait);
        homePage.goToTrendyol();
        homePage.clickTab(2);
        homePage.clickAnyBoutique();
        if (boutiquePage.isBoutiquePageOpened()) {
            boutiquePage.clickAnyProduct();
        } else {
            searchPage.clickAnyProduct();
        }
        productPage.chooseSize();
        productPage.addToBasket();
    }

}
