package samples;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FormOrder {
    WebDriver dr = null;
    String browser = System.getProperty("browser");

    @Test
    public void formOrderTest() throws InterruptedException {

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            dr = new ChromeDriver();
        } else if (browser.equals("safari")) {
            WebDriverManager.safaridriver().setup();
            dr = new SafariDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            dr = new FirefoxDriver();
        }

        String url = "https://formdesigner.ru/examples/order.html";
        try {
            dr.get(url);
            dr.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Принять все')]"))).click();
            //dr.findElement(By.id("c-p-bn")).click();
            Thread.sleep(10000);

            WebElement formElement = dr.findElement(By.id("form_1006"));
            ((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);", formElement);
            dr.findElement(By.xpath("//*[@name='submit']")).click();
            //dr.findElement(By.xpath("//button[@type='submit' and text()='Отправить']")).click();
            Thread.sleep(3000);
            //dr.quit();
        } finally {
            // Закрыть страницу
            dr.quit();
        }
    }
        @AfterEach
        public void tearDown () {
            if (dr != null) {
                dr.quit();
            }
        }
    }
