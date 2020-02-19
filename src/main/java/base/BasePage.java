package base;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    public WebDriver driver;
    public WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void fillInput(By locator, String value) {
        WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
        webElement.clear();
        webElement.sendKeys(value);

    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    }

    protected String getImageUrl(WebElement parent, String placeHolderImageUrl) {
        String imageUrl = wait.until(ExpectedConditions.visibilityOf(parent.findElement(By.tagName("img")))).getAttribute("src");

        if (imageUrl.equals(placeHolderImageUrl)) {
            pressSpace();
            imageUrl = getImageUrl(parent, placeHolderImageUrl);  //recursive
        }
        return imageUrl;
    }

    private void pressSpace() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.SPACE).build().perform();
    }

    public void pressEnter() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).build().perform();
    }

    protected void validateImageUrl(String imageUrl ) {
        System.out.print("Validating image url: " + imageUrl);
        try {
            HttpResponse response = HttpClientBuilder.create().build().execute(new HttpGet(imageUrl));
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.print(" => INVALID!" );
            }
        } catch (Exception e) {
            System.out.print(" => INVALID!");
            e.printStackTrace();
        } finally {
            System.out.println();  //retrofit.
        }
    }
}