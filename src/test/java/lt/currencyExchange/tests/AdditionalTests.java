package lt.currencyExchange.tests;

import lt.currencyExchange.TestBase;
import lt.currencyExchange.pages.CurrencyConvertorPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdditionalTests extends TestBase {

    @Test
    public void verifyThatFilteringByNOKWorks() {
        CurrencyConvertorPage convertorPage = new CurrencyConvertorPage(getDriver());

        convertorPage.selectOptionFromBuyCurrencyDropdown("NOK");
        convertorPage.clickOnFilterButton();

        Assert.assertEquals(convertorPage.getCurrencies().size(), 1);
        Assert.assertTrue(convertorPage.getCurrencies().get(0).contains("NOK"));
    }

    @Test
    public void verifyThatSeraAmountCalculatesCorrectlyForSellForTheFirstItemInTheTable() {
        CurrencyConvertorPage convertorPage = new CurrencyConvertorPage(getDriver());

        convertorPage.fillSellInputWithValue("200");
        convertorPage.clickOnFilterButton();

        Assert.assertEquals(convertorPage.getSeraAmountForRow(1),
                convertorPage.calculateSeraAmountForRow(1));
    }

}
