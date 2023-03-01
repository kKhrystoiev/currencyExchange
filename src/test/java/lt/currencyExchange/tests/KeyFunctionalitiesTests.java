package lt.currencyExchange.tests;

import lt.currencyExchange.TestBase;
import lt.currencyExchange.pages.CurrencyConvertorPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class KeyFunctionalitiesTests extends TestBase {

    @Test
    public void verifyThatSellAmountBoxIsBeingEmptiedWhenUserFillsBuyAmount() {
        CurrencyConvertorPage convertorPage = new CurrencyConvertorPage(getDriver());

        convertorPage.fillBuyInputWithValue("100");

        Assert.assertEquals(convertorPage.getSellInputValue(), "");
    }

    @Test
    public void verifyThatBuyAmountBoxIsBeingEmptiedWhenUserFillsSellAmount() {
        CurrencyConvertorPage convertorPage = new CurrencyConvertorPage(getDriver());

        convertorPage.fillBuyInputWithValue("100");
        convertorPage.fillSellInputWithValue("200");

        Assert.assertEquals(convertorPage.getBuyInputValue(), "");
    }

    @Test
    public void verifyThatCurrencyAndRatesAreUpdatedAfterChangingCountry() {
        CurrencyConvertorPage convertorPage = new CurrencyConvertorPage(getDriver());

        List<String> officialRates = convertorPage.getOfficialRates();
        List<String> seraRates = convertorPage.getSeraRates();

        convertorPage.selectOptionFromCountriesDropdown("United Kingdom");

        Assert.assertNotEquals(officialRates, convertorPage.getOfficialRates());
        Assert.assertNotEquals(seraRates, convertorPage.getSeraRates());
        Assert.assertEquals(convertorPage.getSellCurrencyDropdownValue(), "GBP");
    }

    @Test
    public void verifyThatLossAmountIsCalculatedCorrectlyForTheFirstItemInTheTable() {
        CurrencyConvertorPage convertorPage = new CurrencyConvertorPage(getDriver());

        List<Double> actualLossAmount = convertorPage.getActualLossAmountsForRow(1);
        List<Double> calculatedLossAmount = convertorPage.calculateLossAmountsForRow(1);

        Assert.assertEquals(actualLossAmount, calculatedLossAmount);
    }

}
