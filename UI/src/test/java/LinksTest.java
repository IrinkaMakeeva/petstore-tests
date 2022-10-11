import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LinksTest {

    @Test
    public void firstScript () {
      // настройка браузера
      WebDriverManager.chromedriver().setup();
      ChromeDriver driver = new ChromeDriver();
      driver.get("https://demoqa.com/");

      // переход в меню Elements
      driver.findElement(By. xpath("//*[.='Elements']")).click();
      // выбор элемента item-5
      driver.findElement(By.id("item-5")).click();

      // для ожидания элементов
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

      // нажатие на ссылку Created
      driver.findElement(By.id("created")).click();

      // проверка результата
      String result = driver.findElement(By.id("linkResponse")).getText();
      assertEquals("Link has responded with staus 201 and status text Created", result);

      // нажатие на ссылку No Content
      driver.findElement(By.id("no-content")).click();

      // ждём появления текста
      String text = "Link has responded with staus 204 and status text No Content";
      Boolean textFound = wait.until(ExpectedConditions.textToBe(By.id("linkResponse"), text));
      assertTrue(textFound, String.format("Текст '%s' не найден", text));

      // нажатие на ссылку Moved
      driver.findElement(By.id("moved")).click();

      // ждём появления текста
      text = "Link has responded with staus 301 and status text Moved Permanently";
      textFound = wait.until(ExpectedConditions.textToBe(By.id("linkResponse"), text));
      assertTrue(textFound, String.format("Текст '%s' не найден", text));

      driver.quit();
   }
}
