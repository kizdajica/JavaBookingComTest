package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * This class models the Search Results Page and its objects
 * @author Zoran Dragovic
 *
 */
public class SearchResultsPage {
	@FindBy(how=How.ID, using = "hotellist_inner")
	public WebElement properties;
}