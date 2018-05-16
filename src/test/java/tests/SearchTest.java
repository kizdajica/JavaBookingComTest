package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import basefolder.BaseTest;
import pageobjects.*;

public class SearchTest extends BaseTest {
	PageHeader pageHeader;
	HomePage homePage;
	SearchResultsPage searchResultsPage;

	// Path to csv file with search data and delimiter used
	private static final String CSV_PATH = "src/test/resources/Data/SearchTestData.csv";
	private static final String DELIMITER = ";";

	// Initialize all pages and their objects
	@BeforeMethod
	public void initializePageObjects() {
		pageHeader = PageFactory.initElements(driver, PageHeader.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		searchResultsPage = PageFactory.initElements(driver, SearchResultsPage.class);
	}

	@Test
	public void searchforProperty() throws InterruptedException {
		// Set the currency to 'Euro' and the language to 'English (US)' in page header
		pageHeader.setCurrencyAndLanguage();

		// Parse data from CSV file located in the project
		String[] row = parseCsv(CSV_PATH, DELIMITER);
		// Fill the search form with parsed data
		homePage.findDealsForAnySeason(row);

		// Assert we are on the correct search results page
		Assert.assertTrue(driver.getTitle().contains("Booking.com: Hotels in Malaga. Book your hotel now!"));

		// Find the needed Hotel and display its name
		searchResultsPage.findHotel();
	}
}