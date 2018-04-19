package tests;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import basefolder.BaseTest;
import pageobjects.*;


public class SearchTest extends BaseTest{
	PageHeader pageHeader;
	HomePage homePage;
	SearchResultsPage searchResultsPage;
	
	private static double sScore;
	private static String sPriceString;
	private static int sPrice;
    private static final By sTotalPrice = By.className("totalPrice");
    private static final String sDataScore = "data-score";
    private static final By sHotelName = By.className("sr-hotel__name");
	
    // Path to csv file with search data and delimiter used
	private static final String sCsvPath = "src/test/resources/Data/SearchTestData.csv";
	private static final String sDelimiter = ";";
	
    // Initialize all pages and their objects
	@BeforeMethod
	public void InitializePageObjects() {
		pageHeader = PageFactory.initElements(driver, PageHeader.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		searchResultsPage = PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	@Test
	public void SearchforProperty() throws InterruptedException {
        // Set the currency to 'Euro' and the language to 'English (US)' in page header
		pageHeader.SetCurrencyAndLanguage();
		
        // Parse data from CSV file located in the project
		String[] row = ParseCsv(sCsvPath, sDelimiter);
        // Fill the search form with parsed data
		homePage.FindDealsForAnySeason(row);
		
        // Assert we are on the correct search results page
		Assert.assertTrue(driver.getTitle().contains("Booking.com: Hotels in Malaga. Book your hotel now!"));
		
        // Create a list of properties returned by search
		List<WebElement> listOfProperties = searchResultsPage.properties.findElements(By.className("sr_property_block"));
		
        // Loop through the list of properties and find the first property that matches the desired conditions
		for(WebElement property : listOfProperties) {
            // Find the first hotel with a review mark of higher than ‘8.0’ and	a price under ‘200’ EUR.
            // If any hotel is sold out, catch the NoSuchElementException and log the hotel's name and the message that it is sold out.
			try {
                // Find the price and the score of the hotel
				sPriceString = property.findElement(sTotalPrice).getText();
				sPrice = Integer.parseInt((sPriceString.substring(sPriceString.length() - 1)));
				sScore = Double.parseDouble(property.getAttribute(sDataScore));
			} catch (NoSuchElementException e) {
                // If the hotel is sold out log its name and message
				System.out.println(property.findElement(sHotelName).getText() + "'s last room sold out a few days ago.");
			}
			
            // 5. Assert that there is a property with both
            //  review mark of higher than ‘8.0’ and
            //  price under ‘200’ EUR
			if((sScore > 8.0) && (sPrice < 200)) {
				System.out.println("Matched hotel's name is: " + property.findElement(sHotelName).getText());
				break;
			}
		}		
	}
}