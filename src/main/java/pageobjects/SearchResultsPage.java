package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * This class models the Search Results Page and its objects
 * 
 * @author Zoran Dragovic
 *
 */
public class SearchResultsPage {
	private static double sScore;
	private static int sPrice;
	private static final By TOTAL_PRICE = By.className("totalPrice");
	private static final String DATA_SCORE = "data-score";
	private static final By HOTEL_NAME = By.className("sr-hotel__name");

	@FindBy(how = How.ID, using = "hotellist_inner")
	public WebElement properties;

	/**
	 * This method searches for the hotel with desired room available and outputs
	 * its name
	 */
	public void findHotel() {
		// Create a list of properties returned by search
		List<WebElement> listOfProperties = properties.findElements(By.className("sr_property_block"));

		// Loop through the list of properties and find the first property that matches
		// the desired conditions
		for (WebElement property : listOfProperties) {
			// Find the first hotel with a review mark of higher than ‘8.0’ and a price
			// under ‘200’ EUR.
			// If any hotel is sold out, catch the NoSuchElementException and log the
			// hotel's name and the message that it is sold out.
			try {
				// Find the price and the score of the hotel
				String sPriceString = property.findElement(TOTAL_PRICE).getText();
				sPrice = Integer.parseInt((sPriceString.substring(sPriceString.length() - 1)));
				sScore = Double.parseDouble(property.getAttribute(DATA_SCORE));
			} catch (NoSuchElementException e) {
				// If the hotel is sold out log its name and message
				System.out
						.println(property.findElement(HOTEL_NAME).getText() + "'s last room sold out a few days ago.");
			}

			// 5. Assert that there is a property with both
			// review mark of higher than ‘8.0’ and
			// price under ‘200’ EUR
			if ((sScore > 8.0) && (sPrice < 200)) {
				System.out.println("Matched hotel's name is: " + property.findElement(HOTEL_NAME).getText());
				break;
			}
		}
	}
}