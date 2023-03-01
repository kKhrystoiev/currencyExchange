package lt.currencyExchange;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    private WebDriver driver;
    private Properties prop;

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/config.properties");
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }

        driver.manage().window().setSize(new Dimension(1366, 768));
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get(prop.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Properties getProp() {
        return prop;
    }

}
