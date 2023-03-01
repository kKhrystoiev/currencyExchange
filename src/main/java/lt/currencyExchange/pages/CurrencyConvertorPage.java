package lt.currencyExchange.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static lt.currencyExchange.utils.Utils.convertStringToDoubleFromElement;
import static lt.currencyExchange.utils.Utils.df;

public class CurrencyConvertorPage extends BasePage {

    private final By sellInputXpath = By.xpath("//*[.='Sell']/following-sibling::input");
    private final By sellCurrencyDropdownXpath = By.xpath("//*[.='Sell']/following-sibling::*[contains(@class, 'dropdown')]");
    private final By buyInputXpath = By.xpath("//*[.='Buy']/following-sibling::input");
    private final By buyCurrencyDropdownXpath = By.xpath("//*[.='Buy']/following-sibling::*[contains(@class, 'dropdown')]");
    private final By buyCurrencyOptionsXpath = By.xpath("//*[.='Buy']/following-sibling::*[contains(@class, 'dropdown')]//*[@role='option']");
    private final By filterButtonXpath = By.xpath("//button[contains(text(), 'Filter')]");
    private final By tableBodyXpath = By.xpath("//*[@id='currency-exchange-app']//tbody");
    private final String columnByIndexXpath = "//*[@id='currency-exchange-app']//tbody//td[%d]";
    private final String seraRateOfRowByIndexXpath = "//*[@id='currency-exchange-app']//tbody//tr[%d]/td[contains(@data-title, 'sera rate')]/preceding-sibling::*[1]";
    private final String seraAmountOfRowByIndexXpath = "//*[@id='currency-exchange-app']//tbody//tr[%d]/td[contains(@data-title, 'sera rate')]";
    private final String rowByIndexAmountXpath = "//*[@id='currency-exchange-app']//tbody//tr[%d]/td[contains(@data-title, 'amount')]//*[contains(@class, 'other-bank-loss')]/preceding-sibling::*";
    private final String rowByIndexWithLossAmountXpath = "//*[@id='currency-exchange-app']//tbody//tr[%d]/td[contains(@data-title, 'amount')]//*[contains(@class, 'other-bank-loss')]";
    private final By localizationButtonXpath = By.xpath("//footer//*[@role='button']");
    private final By countryDropdownId = By.id("countries-dropdown");
    private final By countriesOptionsXpath = By.xpath("//*[@id='countries-dropdown']/following-sibling::*//li");

    public CurrencyConvertorPage(WebDriver driver) {
        super(driver);
        waitUntilTableBodyLoads();
    }

    private void waitUntilTableBodyLoads() {
        waitUntilElementIsVisible(tableBodyXpath);
    }

    private List<String> getListOfValuesFromTableByColumnIndex(int index) {
        return getListOfStringValues(driver
                .findElements(By.xpath(String.format(columnByIndexXpath, index))));
    }

    private void selectOptionFromDropdown(By by, String option) {
        driver.findElements(by)
                .stream()
                .filter(e -> e.getText().toLowerCase().contains(option.toLowerCase()))
                .findFirst()
                .orElseThrow()
                .click();
    }

    public void fillSellInputWithValue(String value) {
        findInputByXpathAndFillItWithValue(sellInputXpath, value);
    }

    public void fillBuyInputWithValue(String value) {
        findInputByXpathAndFillItWithValue(buyInputXpath, value);
    }

    public String getSellInputValue() {
        return getInputValueByXpath(sellInputXpath);
    }

    public String getBuyInputValue() {
        return getInputValueByXpath(buyInputXpath);
    }

    public List<String> getCurrencies() {
        return getListOfValuesFromTableByColumnIndex(1);
    }

    public List<String> getOfficialRates() {
        return getListOfValuesFromTableByColumnIndex(2);
    }

    public List<String> getSeraRates() {
        return getListOfValuesFromTableByColumnIndex(3);
    }

    public void selectOptionFromCountriesDropdown(String country) {
        driver.findElement(localizationButtonXpath).click();
        driver.findElement(countryDropdownId).click();
        selectOptionFromDropdown(countriesOptionsXpath, country);

        waitUntilTableBodyLoads();
    }

    public String getSellCurrencyDropdownValue() {
        return driver.findElement(sellCurrencyDropdownXpath).getText();
    }

    public List<Double> getActualLossAmountsForRow(int index) {
        List<WebElement> lossAmountElements = driver.findElements(
                By.xpath(String.format(rowByIndexWithLossAmountXpath, index)));

        return lossAmountElements
                .stream()
                .mapToDouble(e ->
                        convertStringToDoubleFromElement(e.getText()))
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Double> calculateLossAmountsForRow(int index) {
        double seraAmount = getSeraAmountForRow(1);
        List<WebElement> amountElements = driver.findElements(
                By.xpath(String.format(rowByIndexAmountXpath, index)));

        return amountElements
                .stream()
                .mapToDouble(e ->
                        Double.parseDouble(
                                df.format(
                                        convertStringToDoubleFromElement(e.getText()) - seraAmount)))
                .boxed()
                .collect(Collectors.toList());
    }

    public double getSeraAmountForRow(int index) {
        return Double.parseDouble(driver.findElement(
                        By.xpath(String.format(seraAmountOfRowByIndexXpath, index)))
                .getText());
    }

    public double calculateSeraAmountForRow(int index) {
        double sellValue = Double.parseDouble(getSellInputValue());
        double seraRate = Double.parseDouble(driver.findElement(
                        By.xpath(String.format(seraRateOfRowByIndexXpath, index)))
                .getText());

        return Double.parseDouble(df.format(sellValue * seraRate));
    }

    public void clickOnFilterButton() {
        driver.findElement(filterButtonXpath).click();
        waitUntilTableBodyLoads();
    }

    public void selectOptionFromBuyCurrencyDropdown(String currency) {
        driver.findElement(buyCurrencyDropdownXpath).click();
        selectOptionFromDropdown(buyCurrencyOptionsXpath, currency);
    }

}
