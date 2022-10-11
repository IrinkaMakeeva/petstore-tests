import io.github.bonigarcia.wdm.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TextBoxTest {

  @Test
  public void firstScript () {
    // настройка браузера
    WebDriverManager.chromedriver().setup();
    ChromeDriver driver = new ChromeDriver();
    driver.get("https://demoqa.com/");

    // переход в меню Elements
    driver.findElement(By. xpath("//*[.='Elements']")).click();
    // выбор элемента item-0
    driver.findElement(By.id("item-0")).click();

    // заполнение формы
    WebElement fullName = driver.findElement(By.id("userName"));
    WebElement email = driver.findElement(By.id("userEmail"));
    WebElement currentAddress = driver.findElement(By.id("currentAddress"));
    WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));

    fullName.sendKeys("User");
    email.sendKeys("irinka@test.com");
    currentAddress.sendKeys("London");
    permanentAddress.sendKeys("Mst");

    // отправка формы
    driver.findElement(By.id("submit")).click();

    // проверка результата
    String result = driver.findElement(By.cssSelector("#output #name")).getText();
    assertEquals(result, "Name:User");

    result = driver.findElement(By.cssSelector("#output #email")).getText();
    assertEquals(result, "Email:irinka@test.com");

    result = driver.findElement(By.cssSelector("#output #currentAddress")).getText();
    assertEquals(result, "Current Address :London");

    result = driver.findElement(By.cssSelector("#output #permanentAddress")).getText();
    assertEquals(result, "Permananet Address :Mst");

    driver.quit();
  }
}
