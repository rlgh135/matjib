package crawling;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class KakaoMap {
	ArrayList<String[]> res_info;
	public KakaoMap(String station_name) throws Exception{
//		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		String url = "https://map.kakao.com/";
		WebDriver driver = new ChromeDriver();
		driver.get(url);

		Thread.sleep(3000);
		WebElement btn = driver.findElement(By.id("search.keyword.query"));

//		Thread.sleep(1000);
	
		btn.sendKeys(station_name);
		Thread.sleep(1000);
		btn.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		List<WebElement> title = driver.findElements(By.className("link_name"));
		Thread.sleep(1000);

		List<WebElement> category = driver.findElements(By.className("subcategory"));
		Thread.sleep(1000);

		List<WebElement> addr = driver.findElements(By.className("addr"));
		Thread.sleep(1000);
		List<WebElement> phone = driver.findElements(By.className("phone"));
		Thread.sleep(1000);

		res_info = new ArrayList<String[]>();
		for (int i = 0; i < title.size(); i++) {
			String[] info = new String[4];
			info[0] = title.get(i).getText();
			info[1] = category.get(i).getText();
			info[2] = addr.get(i).getText();
			info[3] = phone.get(i).getText();

			res_info.add(info);
		}

	}

	public KakaoMap(String rest_name, int x) throws Exception {
//		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		String url = "https://map.kakao.com/";
		WebDriver driver = new ChromeDriver();
		driver.get(url);

		Thread.sleep(3000);
		WebElement btn = driver.findElement(By.id("search.keyword.query"));

//		Thread.sleep(1000);
		btn.sendKeys(rest_name);
		Thread.sleep(1000);
		btn.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		List<WebElement> title = driver.findElements(By.className("link_name"));
		Thread.sleep(1000);

		List<WebElement> category = driver.findElements(By.className("subcategory"));
		Thread.sleep(1000);

		List<WebElement> addr = driver.findElements(By.className("addr"));
		Thread.sleep(1000);
		List<WebElement> phone = driver.findElements(By.className("phone"));
		Thread.sleep(1000);

		res_info = new ArrayList<String[]>();
		for (int i = 0; i < title.size(); i++) {
			String[] info = new String[4];
			info[0] = title.get(i).getText();
			info[1] = category.get(i).getText();
			info[2] = addr.get(i).getText();
			info[3] = phone.get(i).getText();

			res_info.add(info);
		}
	}
	
	public ArrayList<String[]> getList(){
		//res_info에 데이터가 있으면 res_info 리턴
		if(res_info !=null) return res_info;
		return null;
	}
}