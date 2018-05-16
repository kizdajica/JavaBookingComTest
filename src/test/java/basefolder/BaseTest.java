package basefolder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * This is the Base Test class that initializes the driver and holds common
 * methods
 * 
 * @author Zoran Dragovic
 *
 */
public class BaseTest {
	protected WebDriver driver;

	// 1. Open https://www.booking.com/
	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/Data/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://booking.com");
	}

	/**
	 * This method closes the driver
	 */
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	/**
	 * This method performs the data parsing from provided CSV file
	 * 
	 * @param file
	 * @param delimiter
	 * @return
	 */
	public String[] parseCsv(String file, String delimiter) {
		String line = "";
		String[] fields = null;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
			while ((line = br.readLine()) != null) {

				fields = line.split(delimiter);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fields;
	}
}