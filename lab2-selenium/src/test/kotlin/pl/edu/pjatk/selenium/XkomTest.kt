package pl.edu.pjatk.selenium

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class XkomTest {
    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeEach
    fun setup() {
        driver = ChromeDriver()
        wait = WebDriverWait(driver, Duration.ofSeconds(5))
        driver.manage().apply {
            timeouts().implicitlyWait(Duration.ofSeconds(3))
            timeouts().pageLoadTimeout(Duration.ofSeconds(5))
            window().maximize()
        }
    }

    @AfterEach
    fun teardown() {
        driver.quit()
    }

    @Test
    fun `xkom e2e test`() {
        driver.get("https://www.x-kom.pl/")

        // Sprawdź tytuł strony
        val title = driver.title
        assertEquals("x-kom.pl - Sklep komputerowy", title)

        // Sprawdź obecność dymka czatu
        val isDymekCzatu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Czat']")))
        assertTrue(isDymekCzatu.isDisplayed)

        // Wyszukiwanie produktu "smartfon"
        val searchField = driver.findElement(By.cssSelector("input[placeholder='Czego szukasz?']"))
        searchField.sendKeys("smartfon", Keys.ENTER)

        // Sprawdź przekierowanie do "Smartfony i telefony"
        wait.until(ExpectedConditions.titleContains("Smartfony i telefony"))
        val title2 = driver.title
        assertEquals("Smartfony i telefony - Sklep komputerowy - x-kom.pl", title2)

        // Dodaj produkt do koszyka
        val phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-name='productCard']")))
        val price = phone.findElement(By.cssSelector("span[data-name='productPrice']")).text
        val cartButton = phone.findElement(By.cssSelector("button[title='Dodaj do koszyka']"))
        Actions(driver).moveToElement(phone).moveToElement(cartButton).click().perform()

        // Sprawdź, czy produkt jest w koszyku
        val isOneProduct = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("sc-gWHAAX"), "1"))
        assertTrue(isOneProduct)

        // Sprawdź, czy modal "Dodano do koszyka" jest widoczny
        val modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-name='addedToBasketModalLayout']")))
        assertTrue(modal.isDisplayed)

        // Zamknij modal
        driver.findElement(By.cssSelector("button[title='Zamknij']")).click()

        // Przejdź do koszyka
        driver.findElement(By.cssSelector("a[href='/koszyk']")).click()
        val isCart = wait.until(ExpectedConditions.urlContains("koszyk"))
        assertTrue(isCart)

        // Sprawdź zgodność ceny
        val totalPrice = driver.findElement(By.className("parts__TotalValue-sc-64847818-3")).text
        assertEquals(price, totalPrice)

        // Usuń produkt z koszyka
        driver.findElement(By.cssSelector("button[title='Usuń z koszyka']")).click()
        val isCartEmpty = wait.until(ExpectedConditions.textToBe(By.tagName("h2"), "Koszyk jest pusty"))
        assertTrue(isCartEmpty)
    }
}
