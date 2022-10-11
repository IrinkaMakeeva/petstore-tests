import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ButtonsTest {

    @Test
    public void firstScript () {
      // настройка браузера
      WebDriverManager.chromedriver().setup();
      ChromeDriver driver = new ChromeDriver();
      driver.get("https://demoqa.com/");

      // для цепочки действий
      Actions actions = new Actions(driver);

      // переход в меню Elements
      driver.findElement(By. xpath("//*[.='Elements']")).click();
      // выбор элемента item-4
      driver.findElement(By.id("item-4")).click();

      // проверка, что сообщение не отображается
      assertEquals(driver.findElements(By.id("dynamicClickMessage")).size(), 0);

      // двойной клик на кнопку Double Click Me
      WebElement doubleClickBtn = driver.findElement(By.id("doubleClickBtn"));
      actions.doubleClick(doubleClickBtn).perform();

      // проверка сообщения
      String result = driver.findElement(By.id("doubleClickMessage")).getText();
      assertEquals(result, "You have done a double click");

      // нажимаем на кнопку Right Click Me
      WebElement rightClickBtn = driver.findElement(By.id("rightClickBtn"));
      actions.contextClick(rightClickBtn).perform();

      // проверка сообщения
      result = driver.findElement(By.id("rightClickMessage")).getText();
      assertEquals(result, "You have done a right click");

      // нажимаем на кнопку Click Me
      driver.findElement(By.xpath("//button[(text() = 'Click Me')]")).click();

      // проверка сообщения
      result = driver.findElement(By.id("dynamicClickMessage")).getText();
      assertEquals(result, "You have done a dynamic click");

      driver.quit();
   }
}
