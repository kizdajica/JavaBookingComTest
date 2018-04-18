package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * This class models the Page Header and its objects
 * @author Zoran Dragovic
 *
 */
public class PageHeader {
	@FindBy(how=How.CLASS_NAME, using = "long_currency_text")
	public WebElement currency;
	
	@FindBy(how=How.CLASS_NAME, using = "currency_EUR")
	public WebElement currencyType;
	
	@FindBy(how=How.CLASS_NAME, using = "uc_language")
	public WebElement language;
	
	@FindBy(how=How.CLASS_NAME, using = "lang_en-us")
	public WebElement languageType;
	
    // 2. Choose:
    //  Currency: ‘Euro’
    //  Language: ‘English(US)’
	public void SetCurrencyAndLanguage() throws InterruptedException {
		currency.click();
		Thread.sleep(500);
		currencyType.click();
		language.click();
		languageType.click();
	}
}