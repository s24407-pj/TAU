package pl.edu.pjatk.selenium

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class BMICalcTest {
    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeEach
    fun setup() {
        driver = FirefoxDriver()
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
    fun `bmi calculator test`() {
        // Otwórz stronę kalkulatora BMI
        driver.get("https://www.calculator.net/bmi-calculator.html")

        // Sprawdź tytuł strony
        val title = driver.title
        assertEquals("BMI Calculator", title)

        // Wprowadź wzrost w centymetrach
        val heightField = driver.findElement(By.id("cheightmeter"))
        heightField.clear()
        heightField.sendKeys("170")

        // Wprowadź wagę w kilogramach
        val weightField = driver.findElement(By.id("ckg"))
        weightField.clear()
        weightField.sendKeys("70")

        // Kliknij przycisk obliczający
        val calculateButton = driver.findElement(By.cssSelector("input[type='submit'][value='Calculate']"))
        calculateButton.click()

        // Sprawdź wynik BMI
        val result = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='bigtext']"))
        ).text
        assertTrue(result.contains("24.2"))

        // Sprawdź interpretację wyniku
        val interpretation = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("font[color='green'] b"))
        ).text
        assertEquals("Normal", interpretation)


        // Sprawdz czy przy przeładowaniu strony dane zostaly zachowane
        driver.navigate().refresh()
        val heightField2 = driver.findElement(By.id("cheightmeter"))
        val weightField2 = driver.findElement(By.id("ckg"))
        val result3 = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='bigtext']"))
        ).text
        assertTrue(result3.contains("24.2"))
        assertTrue(heightField2.getAttribute("value").contains("170"))
        assertTrue(weightField2.getAttribute("value").contains("70"))

        // Sprawdz czy jest logo
        val logo =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt=\"Calculator.net\"]")))
        assertTrue(logo.isDisplayed)

        // Sprawdz czy jest link do strony glownej
        val homeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/']")))
        assertTrue(homeLink.isDisplayed)
    }
}
