package cmc.webprak;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SeleniumWebTests {
    private final String rootTitle = "Главная страница";
    private final String routesTitle = "Маршруты";
    private final String companiesTitle = "Компании";
    private final String clientsTitle = "Клиенты";
    private final String errorTitle = "Ошибка!";

    private ChromeDriver ChromeDriverRightVersion() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("start-maximized"); // open Browser in maximized mode
        //options.addArguments("disable-infobars"); // disabling infobars
        //options.addArguments("--disable-extensions"); // disabling extensions
        //options.addArguments("--disable-gpu"); // applicable to windows os only
        //options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        //options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    @Test
    void MainPage() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://localhost:8080/");
        assertEquals(rootTitle, driver.getTitle());
        driver.quit();
    }

    @Test
    void HeaderTest() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1024,768));
        driver.get("http://localhost:8080/");

        WebElement routesButton = driver.findElement(By.id("routesLink"));
        routesButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(routesTitle, driver.getTitle());

        WebElement companiesBtn = driver.findElement(By.id("companiesLink"));
        companiesBtn.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(companiesTitle, driver.getTitle());

        WebElement clientsBtn = driver.findElement(By.id("clientsLink"));
        clientsBtn.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clientsTitle, driver.getTitle());

        WebElement rootButton = driver.findElement(By.id("rootLink"));
        rootButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(rootTitle, driver.getTitle());

        driver.quit();
    }

    @Test
    void routesTest() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://localhost:8080/routes");
        assertEquals(routesTitle, driver.getTitle());

        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> cells = table.findElements(By.tagName("tr"));
        assertEquals(4, cells.size());

        WebElement testRoute = null;
        boolean isFirst = true;
        for (WebElement elem : cells) {
            if (isFirst) {
                isFirst = false;
                continue;
            }

            List<WebElement> info = elem.findElements(By.tagName("td"));
            assertEquals(7, info.size());

            if (Objects.equals(info.get(info.size() - 1).findElement(By.tagName("span")).getText(), "11")) {
                testRoute = elem;
                break;
            }
        }
        assertNotNull(testRoute);

        var link = testRoute.findElement(By.id("routeLink")).getAttribute("href");
        assertEquals("http://localhost:8080/route?id=1", link);

        driver.get(link);
        assertEquals("Информация о маршруте", driver.getTitle());

        assertEquals("Маршрут номер 1", driver.findElement(By.tagName("h4")).getText());

        cells = driver.findElements(By.tagName("p"));
        assertEquals("Место отправления: Москва", cells.get(0).getText());
        assertEquals("Время отправления: 2023-09-21 10:06:00.0", cells.get(1).getText());
        assertEquals("Место прибытия: Санкт-Петербург", cells.get(2).getText());
        assertEquals("Время прибытия: 2023-09-21 18:50:00.0", cells.get(3).getText());
        assertEquals("Компания перевозчик: EUROBUS", cells.get(4).getText());
        assertEquals("Автобус: ГАЗ 322173", cells.get(5).getText());

        cells = driver.findElements(By.tagName("tr"));
        assertEquals(12, cells.size());

        for (int i = 1; i < cells.size(); i++) {
            WebElement elem = cells.get(i);
            String price = elem.findElements(By.tagName("span")).get(1).getText();
            assertEquals("999.99", price);
        }

        driver.quit();
    }

    @Test
    void companiesTest() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://localhost:8080/companies");
        assertEquals(companiesTitle, driver.getTitle());

        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> cells = table.findElements(By.tagName("tr"));
        assertEquals(4, cells.size());

        WebElement testRoute = null;
        for (int i = 1; i < cells.size(); i++) {
            WebElement elem = cells.get(i);
            List<WebElement> info = elem.findElements(By.tagName("td"));
            assertEquals(1, info.size());

            if (Objects.equals(info.get(info.size() - 1).findElement(By.tagName("span")).getText(), "EUROBUS")) {
                testRoute = elem;
                break;
            }
        }
        assertNotNull(testRoute);

        var link = testRoute.findElement(By.id("companyLink")).getAttribute("href");
        assertEquals("http://localhost:8080/company?id=1", link);

        driver.get(link);
        assertEquals("Информация о компании", driver.getTitle());

        assertEquals("Компания EUROBUS", driver.findElement(By.tagName("h4")).getText());

        cells = driver.findElements(By.tagName("p"));
        assertEquals(4, cells.size());
        assertEquals("Дата добавления в систему: 2022-09-28 11:06:00.0", cells.get(0).getText());

        cells = driver.findElements(By.tagName("tr"));
        assertEquals(2, cells.size());

        for (int i = 1; i < cells.size(); i++) {
            WebElement elem = cells.get(i);
            var tmp = elem.findElements(By.tagName("span"));
            assertEquals(3, tmp.size());
            assertEquals("ГАЗ 322173", tmp.get(0).getText());
            assertEquals("2020", tmp.get(1).getText());
            assertEquals("13", tmp.get(2).getText());
        }

        driver.quit();
    }

    @Test
    void clientsTest() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://localhost:8080/clients");
        assertEquals(clientsTitle, driver.getTitle());

        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> cells = table.findElements(By.tagName("tr"));
        assertEquals(6, cells.size());

        WebElement testRoute = null;
        for (int i = 1; i < cells.size(); i++) {
            WebElement elem = cells.get(i);
            List<WebElement> info = elem.findElements(By.tagName("td"));
            assertEquals(3, info.size());

            if (Objects.equals(info.get(info.size() - 1).findElement(By.tagName("span")).getText(), "+79632501491")) {
                testRoute = elem;
                break;
            }
        }
        assertNotNull(testRoute);

        var link = testRoute.findElement(By.id("clientLink")).getAttribute("href");
        assertEquals("http://localhost:8080/client?id=1", link);

        driver.get(link);
        assertEquals("Информация о клиенте", driver.getTitle());

        assertEquals("Некрасов Александр Борисович", driver.findElement(By.tagName("h4")).getText());

        cells = driver.findElements(By.tagName("p"));
        assertEquals(5, cells.size());
        assertEquals("Телефон: +79632501491", cells.get(0).getText());
        assertEquals("Электронная почта: nekrasov@gmail.com", cells.get(1).getText());

        cells = driver.findElements(By.tagName("tr"));
        assertEquals(2, cells.size());

        for (int i = 1; i < cells.size(); i++) {
            WebElement elem = cells.get(i);
            var tmp = elem.findElements(By.tagName("span"));
            assertEquals(3, tmp.size());
            assertEquals("Москва - Санкт-Петербург", tmp.get(0).getText());
            assertEquals("1", tmp.get(1).getText());
            assertEquals("999.99", tmp.get(2).getText());
        }

        driver.quit();
    }

    @Test
    void buyTicketTest() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://127.0.0.1:8080/client?id=5");

        List<WebElement> h4Elements = driver.findElements(By.tagName("h4"));
        assertEquals(1, h4Elements.size());
        assertEquals("Баранов Роман Максимович", h4Elements.get(0).getText());

        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> cells = table.findElements(By.tagName("tr"));
        assertEquals(1, cells.size());

        driver.get("http://127.0.0.1:8080/route?id=1");
        table = driver.findElement(By.tagName("table"));
        cells = table.findElements(By.tagName("tr"));
        WebElement last_seat = cells.get(cells.size() - 1).findElement(By.tagName("span"));
        assertEquals("13", last_seat.getText());

        String buyLink = cells.get(cells.size() - 1).findElement(By.id("buyLink")).getAttribute("href");
        assertEquals("http://127.0.0.1:8080/buy?id=13", buyLink);

        driver.get(buyLink);

        var confirmBtn = driver.findElements(By.id("confirmButton"));
        assertEquals(1, confirmBtn.size());

        driver.findElement(By.id("clientFullname")).sendKeys("Баранов Роман Максимович");
        driver.findElement(By.id("clientPhone")).sendKeys("+79632727641");
        driver.findElement(By.id("clientEmail")).sendKeys("baranovrm@gmail.com");

        confirmBtn.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.get("http://127.0.0.1:8080/client?id=5");
        table = driver.findElement(By.tagName("table"));
        cells = table.findElements(By.tagName("tr"));
        assertEquals(2, cells.size());

        for (int i = 1; i < cells.size(); i++) {
            WebElement elem = cells.get(i);
            var tmp = elem.findElements(By.tagName("span"));
            assertEquals(3, tmp.size());
            assertEquals("Москва - Санкт-Петербург", tmp.get(0).getText());
            assertEquals("13", tmp.get(1).getText());
            assertEquals("999.99", tmp.get(2).getText());
        }

        driver.quit();
    }

    @Test
    void editAndDeleteRoute() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://localhost:8080/route?id=1");

        List<WebElement> editBtn = driver.findElements(By.id("editButton"));
        assertEquals(1, editBtn.size());
        editBtn.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        List<WebElement> departure = driver.findElements(By.id("departure"));
        assertEquals(1, departure.size());
        departure.get(0).clear();
        departure.get(0).sendKeys("Тверь");

        List<WebElement> submit = driver.findElements(By.id("submitButton"));
        assertEquals(1, submit.size());
        submit.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.get("http://localhost:8080/route?id=1");
        List<WebElement> pElements = driver.findElements(By.tagName("p"));
        assertEquals(9, pElements.size());
        assertEquals("Место отправления: Тверь", pElements.get(0).getText());

        List<WebElement> deleteBtn = driver.findElements(By.id("deleteButton"));
        assertEquals(1, deleteBtn.size());
        deleteBtn.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.get("http://localhost:8080/route?id=1");
        assertEquals(errorTitle, driver.getTitle());

        driver.quit();
    }

    @Test
    void editAndDeleteCompany() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://localhost:8080/company?id=1");

        List<WebElement> editBtn = driver.findElements(By.id("editButton"));
        assertEquals(1, editBtn.size());
        editBtn.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        List<WebElement> departure = driver.findElements(By.id("companyName"));
        assertEquals(1, departure.size());
        departure.get(0).clear();
        departure.get(0).sendKeys("EUROBUSNEW");

        List<WebElement> submit = driver.findElements(By.id("submitButton"));
        assertEquals(1, submit.size());
        submit.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.get("http://localhost:8080/company?id=1");
        List<WebElement> h4Elements = driver.findElements(By.tagName("h4"));
        assertEquals(1, h4Elements.size());
        assertEquals("Компания EUROBUSNEW", h4Elements.get(0).getText());

        List<WebElement> deleteBtn = driver.findElements(By.id("deleteButton"));
        assertEquals(1, deleteBtn.size());
        deleteBtn.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.get("http://localhost:8080/company?id=1");
        assertEquals(errorTitle, driver.getTitle());

        driver.quit();
    }

    @Test
    void editAndDeleteClient() {
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get("http://localhost:8080/client?id=1");

        List<WebElement> editBtn = driver.findElements(By.id("editButton"));
        assertEquals(1, editBtn.size());
        editBtn.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        List<WebElement> departure = driver.findElements(By.id("clientFullname"));
        assertEquals(1, departure.size());
        departure.get(0).clear();
        departure.get(0).sendKeys("Некрасов Алексей Борисович");

        List<WebElement> submit = driver.findElements(By.id("submitButton"));
        assertEquals(1, submit.size());
        submit.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.get("http://localhost:8080/client?id=1");
        List<WebElement> h4Elements = driver.findElements(By.tagName("h4"));
        assertEquals(1, h4Elements.size());
        assertEquals("Некрасов Алексей Борисович", h4Elements.get(0).getText());

        List<WebElement> deleteBtn = driver.findElements(By.id("deleteButton"));
        assertEquals(1, deleteBtn.size());
        deleteBtn.get(0).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.get("http://localhost:8080/client?id=1");
        assertEquals(errorTitle, driver.getTitle());

        driver.quit();
    }

}
