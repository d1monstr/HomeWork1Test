package ru.appline.tests.sberbank;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class OrderTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 12, 1000);

        String baseUrl = "https://www.sberbank.ru/ru/person";
        driver.get(baseUrl);
    }

    @After
    public void after(){
        driver.quit();
    }

    @BeforeClass
    public static void beforeClass(){

    }

    @AfterClass
    public static void afterClass(){

    }

    @Test
    public void test(){

        // выбрать меню "Карты"
        String cardsMenuXPath = "//label[text() = 'Карты']";
        WebElement cardsMenuButton = driver.findElement(By.xpath(cardsMenuXPath));
        cardsMenuButton.click();

        // выбрать пункт подменю - "Дебетовые карты"
        String debetCardXPath = "//li/a[text() = 'Дебетовые карты']";
        WebElement debetCardButton = driver.findElement(By.xpath(debetCardXPath));
        debetCardButton.click();

        // проверка открытия страницы "Дебетовые карты"
        String pageTitleXPath = "//h2[contains(@class, 'kit-heading')]";
        WebElement pageTitle = driver.findElement(By.xpath(pageTitleXPath));
        waitUtilElementToBeVisible(pageTitle);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Дебетовые карты", pageTitle.getText());

        // нажать кнопку "Молодежная"
        String youthCardXPath = "//label[contains(text(), 'Молодежная')]";
        WebElement youthCardButton = driver.findElement(By.xpath(youthCardXPath));
        waitUtilElementToBeClickable(youthCardButton);
        youthCardButton.click();

        // нажать кнопку "Заказать онлайн" у карты "Молодежная"
        String orderXPath = "//h3[contains(text(), 'Молодежная карта')]/preceding::b[contains(text(), 'Заказать онлайн')]";
        WebElement orderButton = driver.findElement(By.xpath(orderXPath));
        waitUtilElementToBeClickable(orderButton);
        orderButton.click();

        // проверка открытия страницы "Молодежная карта"
        String pageTitle1XPath = "//h1[contains(@class, 'kitt-heading')]";
        WebElement pageTitle1 = driver.findElement(By.xpath(pageTitle1XPath));
        waitUtilElementToBeVisible(pageTitle1);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Молодёжная карта", pageTitle1.getText());

        driver.getPageSource();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // нажать кнопку "Оформить онлайн" под заголовком "Молодежная карта"
//        String orderOnlineXPath = "//h1[contains(text(), 'Молодёжная карта')]/..//span[contains(text(), 'Оформить онлайн')]/..";
//        WebElement orderOnlineButton = driver.findElement(By.xpath(orderOnlineXPath));
//        scrollToElementJs(orderOnlineButton);
////        try {
////            Thread.sleep(2000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//
//        waitUtilElementToBeClickable(orderOnlineButton);
//        waitUtilElementToBeVisible(orderOnlineButton);
//
//        JavascriptExecutor executor = (JavascriptExecutor)driver;
//        executor.executeScript("arguments[0].click();", orderOnlineButton);
////        orderOnlineButton.click();
        // закрываем куки
        WebElement closeCookies = driver.findElement(By.xpath("//button[text()='Закрыть']"));
        closeCookies.click();

        // заполнить поля данными
        scrollToElementJs(driver.findElement(By.xpath("//h2[text()='Основной документ']")));
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'lastName']")), "Иванов");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'firstName']")), "Иван");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'middleName']")), "Иванович");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'cardName']")), "IVAN IVANOV");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'birthDate']")), "15.06.1996");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'email']")), "qwerty@mail.ru");

        WebElement phoneField = driver.findElement(By.xpath("//input[@data-name = 'phone']"));

        waitUtilElementToBeClickable(phoneField);
        phoneField.click();
        phoneField.sendKeys("9999999999");
        Assert.assertEquals("Поле было заполнено некорректно",
                "+7 (999) 999-99-99", phoneField.getAttribute("value"));

        // нажать кнопку "Далее"
        scrollToElementJs(driver.findElement(By.xpath("//div[text()='Страница была вам полезна?']")));

        String furtherXPath = "//span[contains(text(), 'Далее')]/..";
        WebElement furtherButton = driver.findElement(By.xpath(furtherXPath));
        waitUtilElementToBeClickable(furtherButton);
//        furtherButton.click();
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", furtherButton);
        // проверка написей "Обязательное поле" у незаполненных полей
        checkErrorMessageAtField(driver.findElement(By.xpath("//input[@data-name = 'series']")), "Обязательное поле");
        checkErrorMessageAtField(driver.findElement(By.xpath("//input[@data-name = 'number']")), "Обязательное поле");
        checkErrorMessageAtField(driver.findElement(By.xpath("//input[@data-name = 'issueDate']")), "Обязательное поле");
        checkErrorMessageAtField(driver.findElement(By.xpath("//div[text() = 'Я соглашаюсь на']/button")), "Обязательное поле");






    }

    private void checkErrorMessageAtField(WebElement element, String errorMessage) {
        element = element.findElement(By.xpath("./..//div[@class = 'odcui-error__text']"));
        Assert.assertEquals("Проверка ошибки у поля не была пройдена",
                errorMessage, element.getText());
    }

    private void waitUtilElementToBeClickable(WebElement element) {
//        scrollToElementJs(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void fillInputField(WebElement element, String value) {
//        waitUtilElementToBeClickable(element);
        element.clear();
        element.sendKeys(value);
        Assert.assertEquals("Поле было заполнено некорректно",
                value, element.getAttribute("value"));

    }

    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

}
