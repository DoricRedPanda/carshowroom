package info.azatov.carshowroom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    static FirefoxDriver driver;
    static Actions act;
    static Wait<FirefoxDriver> wait;

    @BeforeAll
    static void setup() throws SQLException {
        System.setProperty("webdriver.gecko.driver", "/home/azatoth/Downloads/gecko/geckodriver");
        System.setProperty("webdriver.firefox.bin","/usr/bin/firefox-bin");
        driver = new FirefoxDriver();
       // driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1280, 1024));
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        act = new Actions(driver);
        wait = new FluentWait<FirefoxDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
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

    @Test
    void addAndDeleteClient() {
        driver.get("http://127.0.0.1:8080/clients");

        assertEquals("Клиенты", driver.getTitle());
        //wait.until(ExpectedConditions.elementToBeClickable(By.id("addClientButton")));
        int clientCount = driver.findElements(By.className("deleteClientButton")).size();
        act.moveToElement(driver.findElement(By.partialLinkText("Добавить"))).doubleClick().perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));

        assertEquals("Добавить клиента", driver.getTitle());
        driver.findElement(By.id("client_name")).sendKeys("Walter Melon");
        driver.findElement(By.id("client_address")).sendKeys("UK");
        driver.findElement(By.id("client_phone")).sendKeys("+7 123 123 2323");
        driver.findElement(By.id("client_email")).sendKeys("walter@melon.com");

        driver.findElement(By.id("submitButton")).click();
        wait.until(ExpectedConditions.titleIs("Клиенты"));

        assertEquals("Клиенты", driver.getTitle());
        List<WebElement> deleteButtons = driver.findElements(By.className("deleteClientButton"));
        assertEquals(clientCount + 1, deleteButtons.size());
        deleteButtons.get(clientCount).click();
        assertEquals(clientCount, driver.findElements(By.className("deleteClientButton")).size());
    }

    @Test
    void addAndDeleteCar() {
        driver.get("http://127.0.0.1:8080/cars");
        assertEquals("Автомобили", driver.getTitle());
        int carsCount = driver.findElements(By.className("deleteCarButton")).size();
        act.moveToElement(driver.findElement(By.partialLinkText("Добавить"))).doubleClick().perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));

        assertEquals("Добавить автомобиль", driver.getTitle());
        driver.findElement(By.id("model_name")).sendKeys("Rio");
        driver.findElement(By.id("registration_plate")).sendKeys("c111pk");
        driver.findElement(By.id("price")).sendKeys("1000000");
        driver.findElement(By.id("kilometers")).sendKeys("111");
        WebElement date = driver.findElement(By.id("service_date"));
        date.click();
        date.sendKeys("2019-01-01");
        driver.findElement(By.id("displacement")).sendKeys("33");
        driver.findElement(By.id("power")).sendKeys("33");
        driver.findElement(By.id("top_speed")).sendKeys("33");
        driver.findElement(By.id("seat_count")).sendKeys("3");
        driver.findElement(By.id("transmission_type")).sendKeys("AT");
        driver.findElement(By.id("devices")).sendKeys("GPS");
        driver.findElement(By.id("color")).sendKeys("red");
        driver.findElement(By.id("saloon")).sendKeys("middle");
        driver.findElement(By.id("submitButton")).click();

        wait.until(ExpectedConditions.titleIs("Автомобили"));

        assertEquals("Автомобили", driver.getTitle());
        List<WebElement> deleteButtons = driver.findElements(By.className("deleteCarButton"));
        assertEquals(carsCount + 1, deleteButtons.size());
        deleteButtons.get(carsCount).click();
        assertEquals(carsCount, driver.findElements(By.className("deleteCarButton")).size());
    }

    @Test
    void addAndDeleteContract() {
        driver.get("http://127.0.0.1:8080/contracts");
        assertEquals("Заказы", driver.getTitle());
        int contractsCount = driver.findElements(By.className("deleteContractButton")).size();
        act.moveToElement(driver.findElement(By.partialLinkText("Оформить"))).doubleClick().perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));

        assertEquals("Оформить заказ", driver.getTitle());
        driver.findElement(By.id("client_id")).sendKeys("1");
        driver.findElement(By.id("vin")).sendKeys("1");
        WebElement date = driver.findElement(By.id("date"));
        date.click();
        date.sendKeys("2019-01-01");
        driver.findElement(By.id("status")).sendKeys("DONE");
        driver.findElement(By.id("submitButton")).click();

        wait.until(ExpectedConditions.titleIs("Заказы"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteContractButton")));

        assertEquals("Заказы", driver.getTitle());
        List<WebElement> deleteButtons = driver.findElements(By.className("deleteContractButton"));
        assertEquals(contractsCount + 1, deleteButtons.size());
        deleteButtons.get(contractsCount).click();
        assertEquals(contractsCount, driver.findElements(By.className("deleteContractButton")).size());
    }
}
