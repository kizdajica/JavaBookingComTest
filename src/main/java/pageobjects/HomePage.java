package pageobjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

/**
 * This class models the Home Page and its objects
 * @author Zoran Dragovic
 *
 */
public class HomePage {
	@FindBy(how=How.ID, using = "ss")
	public WebElement destination;
	
	@FindBy(how=How.CSS, using = "[name = 'checkin_month']")
	public WebElement checkIn;
	
	@FindBy(how=How.CSS, using = "[name = 'checkout_month']")
	public WebElement checkOut;
	
	@FindBy(how=How.ID, using = "group_adults")
	public WebElement adults;
	
	@FindBy(how=How.ID, using = "group_children")
	public WebElement children;
	
	@FindBy(how=How.ID, using = "no_rooms")
	public WebElement rooms;
	
	@FindBy(how=How.NAME, using = "age")
	public WebElement age;
	
	@FindBy(how=How.NAME, using = "sb_travel_purpose")
	public WebElement work;
	
	@FindBy(how=How.CLASS_NAME, using = "sb-searchbox__button  ")
	public WebElement search;
	
	public void FindDealsForAnySeason(String[] row) {
        // 3. Complete property search details as follows:
        //  Destination: Málaga, Andalucía, Spain
        //  Check -in: last day of current month
        //  Check -out: first day of next month
        //  1 adult
        //  1 child 5 years old
        //  2 rooms
        //  I'm traveling for work: check
		destination.sendKeys(row[0]);
		String lastDay = GetLastDayOfCurrentMonth();
		checkIn.sendKeys(lastDay);
		String firstDay = GetFirstDayOfNextMonth();
		checkOut.sendKeys(firstDay);
		Select numberOfRooms = new Select(rooms);
		numberOfRooms.selectByValue(row[1]);
		Select numberOfAdults = new Select(adults);
		numberOfAdults.selectByValue(row[2]);
		Select numberOfChildren = new Select(children);
		numberOfChildren.selectByValue(row[2]);
		Select ageNumber = new Select(age);
		ageNumber.selectByValue(row[3]);
		
        // "I'm traveling for work" chekcbox disabled due to the fact
        // that the alternative Booking.com page style doesn't have this checkbox
		//work.click();
		
        // 4.Click on ‘Search’ button
		search.click();
	}
	
    // Return the last day of the current month in needed form
	public String GetLastDayOfCurrentMonth() {
		LocalDate today = LocalDate.now();
		LocalDate firstOfNextMonth = LocalDate.of(today.getYear(), today.getMonth(), 1).plusMonths(1);
		LocalDate lastDay = firstOfNextMonth.minusDays(1);
		return lastDay.format(DateTimeFormatter.ofPattern("MMddyyyy"));
	}
	
    // Return the first day of the next month in needed form
	public String GetFirstDayOfNextMonth() {
		LocalDate today = LocalDate.now();
		LocalDate firstOfNextMonth = LocalDate.of(today.getYear(), today.getMonth(), 1).plusMonths(1);
		return firstOfNextMonth.format(DateTimeFormatter.ofPattern("MMddyyyy"));
	}
}