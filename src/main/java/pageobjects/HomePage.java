package pageobjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

/**
 * This class models the Home Page and its objects
 * 
 * @author Zoran Dragovic
 *
 */
public class HomePage {
	@FindBy(how = How.ID, using = "ss")
	public WebElement destination;

	@FindBy(how = How.CSS, using = "[name = 'checkin_month']")
	public WebElement checkIn;

	@FindBy(how = How.CSS, using = "[name = 'checkout_month']")
	public WebElement checkOut;

	@FindBy(how = How.ID, using = "group_adults")
	public WebElement adults;

	@FindBy(how = How.ID, using = "group_children")
	public WebElement children;

	@FindBy(how = How.ID, using = "no_rooms")
	public WebElement rooms;

	@FindBy(how = How.NAME, using = "age")
	public WebElement age;

	@FindBy(how = How.NAME, using = "sb_travel_purpose")
	public WebElement oldWork;

	@FindBy(how = How.CLASS_NAME, using = "bui-checkbox__label")
	public WebElement newWork;

	@FindBy(how = How.CLASS_NAME, using = "sb-searchbox__button  ")
	public WebElement search;

	@FindBy(how = How.CSS, using = "[data-visible = 'accommodation']")
	public WebElement adultsChildren;

	/**
	 * This method performs hotels search based on selected search criteria
	 * 
	 * @param row
	 * @throws InterruptedException 
	 */
	public void findDealsForAnySeason(String[] row) throws InterruptedException {
		// 3. Complete property search details as follows:
		// Destination: Málaga, Andalucía, Spain
		// Check -in: last day of current month
		// Check -out: first day of next month
		// 1 adult
		// 1 child 5 years old
		// 2 rooms
		// I'm traveling for work: check
		destination.sendKeys(row[0]);
		String lastDay = getLastDayOfCurrentMonth();
		Thread.sleep(500);
		checkIn.sendKeys(lastDay);
		String firstDay = getFirstDayOfNextMonth();
		checkOut.sendKeys(firstDay);

		// Since "Number of rooms" element is missing on the new booking.com home page
		// layout,
		// possible exception is caught and message logged
		try {
			Select numberOfRooms = new Select(rooms);
			numberOfRooms.selectByValue(row[1]);
		} catch (NoSuchElementException e) {
			// If the booking.com home page is displayed using the new layout, log the
			// message
			System.out.println("New Home Page layout displayed without the rooms element.");
		}

		// If the booking.com home page is displayed using the new layout,
		// first click on the adults-child element to open dropdown
		try {
			adultsChildren.click();
		} catch (NoSuchElementException e) {
			// If the booking.com home page is displayed using the old layout, log the
			// message
			System.out.println("Old Home Page layout displayed.");
		}

		Select numberOfAdults = new Select(adults);
		numberOfAdults.selectByValue(row[2]);
		Select numberOfChildren = new Select(children);
		numberOfChildren.selectByValue(row[2]);
		Select ageNumber = new Select(age);
		ageNumber.selectByValue(row[3]);

		// Since it is currently not possible to find work element by the same locator
		// on different booking.com home page layouts,
		// first try the old home page locator and then the new home page locator
		try {
			oldWork.click();
		} catch (ElementNotVisibleException e) {
			newWork.click();
		}

		// 4.Click on ‘Search’ button
		search.click();
	}

	// Return the last day of the current month in needed form
	public String getLastDayOfCurrentMonth() {
		LocalDate today = LocalDate.now();
		LocalDate firstOfNextMonth = LocalDate.of(today.getYear(), today.getMonth(), 1).plusMonths(1);
		LocalDate lastDay = firstOfNextMonth.minusDays(1);
		return lastDay.format(DateTimeFormatter.ofPattern("MMddyyyy"));
	}

	// Return the first day of the next month in needed form
	public String getFirstDayOfNextMonth() {
		LocalDate today = LocalDate.now();
		LocalDate firstOfNextMonth = LocalDate.of(today.getYear(), today.getMonth(), 1).plusMonths(1);
		return firstOfNextMonth.format(DateTimeFormatter.ofPattern("MMddyyyy"));
	}
}