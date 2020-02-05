package pages;

import base.BasePage;
import config.TestConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class HomePage extends BasePage {

    private String boutiquePlaceHolderImageUrl = "https://static.dsmcdn.com/frontend/web/production/large_boutique_placeholder.png";

    private String xPathPopUpCloseButton = "//a[@class='fancybox-item fancybox-close']";
    private String xPathSignInMenu = "//li[@id='accountBtn']";
    private String xPathSignInButton = "//div[@class='account-button login']";
    private String classSignInModalCloseButton = "modal-close";
    private String xPathEmailField = "//input[@id='email']";
    private String xPathPasswordField = "//input[@id='password']";
    private String xPathSignInSubmitButton = "//a[@id='loginSubmit']";
    private String xPathMainTab = "//ul[@class='main-nav']";
    private String classTab = "category-header";
    private String xPathBoutiqueImagesContainer = "//div[@class='component-list component-big-list']";
    private String classBoutiqueImageArticle = "component-item";
    private String xPathRandomBoutique = "//article[1]//a[1]//span[1]//img[1]";

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Opens trendyol.com")
    public void goToTrendyol() {
        driver.get(Objects.requireNonNull(TestConfig.getProperty("web_url")));
        closePopup();
    }

    // If the pop-up is opened on the home page, close
    private void closePopup() {
        try {
            click(By.xpath(xPathPopUpCloseButton));
        } catch (Exception ignored) {
        }
    }

    @Step("Clicks sign-in button on homepage")
    public void clickSignInButton() throws InterruptedException {
        Actions actions = new Actions(driver);
        WebElement webElement = driver.findElement(By.xpath(xPathSignInMenu));
        actions.moveToElement(webElement).build().perform();
        click(By.xpath(xPathSignInButton));
    }

    @Step("Fills email field")
    public void fillEmailField(String email) {
        fillInput(By.xpath(xPathEmailField), email);
    }

    @Step("Fills password field ")
    public void fillPasswordField(String password) {
        fillInput(By.xpath(xPathPasswordField), password);
    }

    @Step("Clicks sign-in button")
    public void clickSignInSubmit() {
        click(By.xpath(xPathSignInSubmitButton));
    }

    @Step("Closes sign-in popUp")
    public void closeSignInPopUp() {
        try {
            click(By.className(classSignInModalCloseButton));
        } catch (Exception ignored) {
        }
    }

    @Step("Checks for boutique images")
    public void checkForBoutiqueImages() {
        WebElement mainTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathMainTab)));  //sayfa yenilendigi icin bekliyor.
        int tabCount = mainTab.findElements(By.className(classTab)).size();  //9
        for (int i = 0; i < tabCount; i++) {
            clickTab(i);
            validateBoutiqueImage();
        }
    }

    private void validateBoutiqueImage() {
        List<WebElement> imageArticleList = wait.until(ExpectedConditions.visibilityOfAllElements(driver
                .findElement(By.xpath(xPathBoutiqueImagesContainer)).findElements(By.className(classBoutiqueImageArticle))));
        for (WebElement imageArticle : imageArticleList) {
            String imageUrl = getImageUrl(imageArticle, boutiquePlaceHolderImageUrl);
            validateImageUrl(imageUrl);
        }
    }

    @Step("User click any boutique")
    public void clickAnyBoutique() {
        click(By.xpath(xPathRandomBoutique));
    }

    @Step("Clicks tab")
    public void clickTab(int tabIndex) {
        scrollToTop();
        wait.until(ExpectedConditions.elementToBeClickable(
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathMainTab)))
                        .findElements(By.className(classTab)).get(tabIndex))
        ).click();
    }
}
