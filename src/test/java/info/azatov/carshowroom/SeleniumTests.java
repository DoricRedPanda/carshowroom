package info.azatov.carshowroom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    static FirefoxDriver driver;

    @BeforeAll
    static void setup() throws SQLException {
        System.setProperty("webdriver.gecko.driver", "/home/azatoth/Downloads/gecko/geckodriver");
        System.setProperty("webdriver.firefox.bin","/usr/bin/firefox-bin");
        driver = new FirefoxDriver();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }


    @Test
    void MainPage() {
        driver.get("http://127.0.0.1:8080/");
        assertEquals("Главная страница", driver.getTitle());
    }

    /* Test navigation bar
    */
    @Test
    void HeaderTest() {
        driver.get("http://127.0.0.1:8080/");

        WebElement clientsButton = driver.findElement(By.id("clientsListLink"));
        clientsButton.click();
        assertEquals("Клиенты", driver.getTitle());

        WebElement carsButton = driver.findElement(By.id("carsListLink"));
        carsButton.click();
        assertEquals("Автомобили", driver.getTitle());

        WebElement modelsButton = driver.findElement(By.id("modelsListLink"));
        modelsButton.click();
        assertEquals("Марки", driver.getTitle());

        WebElement contractsButton = driver.findElement(By.id("contractsListLink"));
        contractsButton.click();
        assertEquals("Заказы", driver.getTitle());
    }

}
