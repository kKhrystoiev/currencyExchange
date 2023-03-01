package lt.currencyExchange.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class BasePage {

    protected WebDriver driver;
    private final WebDriverWait driverWait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        driverWait = new WebDriverWait(driver, 5);
    }

    protected void waitUntilElementIsVisible(By by) {
        driverWait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(by)
        );
    }

    protected void findInputByXpathAndFillItWithValue(By XPath, String value) {
        WebElement inputElement = driver.findElement(XPath);
        inputElement.clear();
        inputElement.sendKeys(value);
    }

    protected String getInputValueByXpath(By XPath) {
        WebElement inputElement = driver.findElement(XPath);
        return inputElement.getAttribute("value");
    }

    protected List<String> getListOfStringValues(List<WebElement> webElementsList) {
        return webElementsList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

}
