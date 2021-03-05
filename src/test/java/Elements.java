import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Elements {

    private static final String BASE_URL = "https://www.biletix.com/anasayfa/TURKIYE/tr";

    WebDriver driver = new ChromeDriver();

    public void setup() {
        driver.get(BASE_URL);
    }

    public String currentUrl() {
       return driver.getCurrentUrl();
    }

    public void clickElement(By by) {
        driver.findElement(by).click();
    }
    public void oneWaitElement(int second) {
        driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS);
    }

    public void twoWaitElement(int second) {
        WebDriverWait wait = new WebDriverWait(driver, second);

    }

    public void selectElement(By by, int index) {
        new Select(driver.findElement(by)).selectByIndex(index);
    }

    public void findElements(By by) {
        driver.findElements(by);
    }

    public List<WebElement> findElementList(By by) {
        return driver.findElements(by);
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public void closeDriver() {
        driver.quit();
    }

}
