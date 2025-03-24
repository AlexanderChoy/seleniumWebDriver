package samples;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.assertj.core.api.SoftAssertions;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DemoQA {

    WebDriver dr = null;
    String browser = System.getProperty("browser");

    public static WebElement waitForElement(WebDriver dr, WebDriverWait wait, By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    @Test
    public void openDemoQATest() throws InterruptedException {

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

        String url = "https://demoqa.com/automation-practice-form";

        dr.get(url);
        dr.manage().window().maximize();

        dr.findElement(By.id("firstName")).sendKeys("Alex");
        dr.findElement(By.id("lastName")).sendKeys("Choy");
        dr.findElement(By.id("userEmail")).sendKeys("alex@gmail.com");
        dr.findElement(By.xpath("//label[contains(text(), 'Male')]")).click();
        dr.findElement(By.id("userNumber")).sendKeys("9999999999");
        dr.findElement(By.id("dateOfBirthInput")).sendKeys("20 Dec 2000");

        dr.findElement(By.xpath("//label[contains(text(), 'Sports')]")).click();
        dr.findElement(By.xpath("//label[contains(text(), 'Music')]")).click();
        dr.findElement(By.id("currentAddress")).sendKeys("Moscow, Lomonosov street 5-25");

        WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(2));

        WebElement stateDropdown = waitForElement(dr, wait, By.id("state"));
        stateDropdown.click();

        WebElement stateOption = waitForElement(dr, wait, By.xpath("//div[text()='Uttar Pradesh']"));
        stateOption.click();

        // Используем метод для ожидания города
        WebElement cityDropdown = waitForElement(dr, wait, By.id("city"));
        cityDropdown.click();

        // Пример выбора города
        WebElement cityOption = waitForElement(dr, wait, By.xpath("//div[text()='Agra']"));
        cityOption.click();

        dr.findElement(By.id("submit")).click();

        waitForElement(dr, wait, By.className("modal-content"));

        String name = waitForElement(dr, wait, By.xpath("//td[text()='Student Name']/following-sibling::td")).getText();
        String email = waitForElement(dr, wait, By.xpath("//td[text()='Student Email']/following-sibling::td")).getText();
        String gender = waitForElement(dr, wait, By.xpath("//td[text()='Gender']/following-sibling::td")).getText();
        String phone = waitForElement(dr, wait, By.xpath("//td[text()='Mobile']/following-sibling::td")).getText();
        String address = waitForElement(dr, wait, By.xpath("//td[text()='Address']/following-sibling::td")).getText();
        String stateCity = waitForElement(dr, wait, By.xpath("//td[text()='State and City']/following-sibling::td")).getText();

        SoftAssertions softAssert = new SoftAssertions();

        softAssert.assertThat(name).isEqualTo("Alex Choy");
        softAssert.assertThat(email).isEqualTo("alex@gmail.com");
        softAssert.assertThat(gender).isEqualTo("Male");
        softAssert.assertThat(phone).isEqualTo("9999999999");
        softAssert.assertThat(address).isEqualTo("Moscow, Lomonosov street 5-25");
        softAssert.assertThat(stateCity).isEqualTo("Uttar Pradesh Agra");

        softAssert.assertAll();

        dr.quit();
    }

    @AfterEach
    public void tearDown() {
        if (dr != null) {
            dr.quit();
        }
    }

}

