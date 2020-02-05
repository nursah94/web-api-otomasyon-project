package trendyol.web.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import config.TestConfig;

public class BaseWebTest {

    public WebDriver driver;
    public WebDriverWait wait;

    private RemoteWebDriver createDriverByBrowserParameter(String browser) {
        if (browser.equals("chrome")) {
            return new ChromeDriver();
        } else if (browser.equals("firefox")) {
            return new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser parameter can be 'chrome' or 'firefox'");
        }
    }

    @BeforeClass
    public void setup() {
        driver = createDriverByBrowserParameter(TestConfig.getProperty("browser"));
        wait = new WebDriverWait(driver, 45);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

}
