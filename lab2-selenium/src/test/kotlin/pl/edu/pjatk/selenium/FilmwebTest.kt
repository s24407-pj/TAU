package pl.edu.pjatk.selenium

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class FilmwebTest {
    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeEach
    fun setup() {
        driver = EdgeDriver()
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
    fun `filmweb e2e test`() {
        driver.get("https://www.filmweb.pl/")

        // Sprawdź tytuł strony
        val title = driver.title
        assertEquals("Filmweb - filmy takie jak Ty!", title)

        // Wyszukaj film "The Shawshank Redemption"
        val searchField = driver.findElement(By.cssSelector("input[name='q']"))
        searchField.sendKeys("Skazani na Shawshank", Keys.ENTER)

        // Kliknij w film
        val movie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Skazani+na+Shawshank']")))
        movie.click()


        // Sprawdź przekierowanie do strony filmu
        wait.until(ExpectedConditions.titleContains("Skazani na Shawshank"))
        val title2 = driver.title
        assertEquals("Skazani na Shawshank (1994) - Filmweb", title2)

        // Sprawdź czy jest trailer
        val trailer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Zwiastun']")))
        assertTrue(trailer.isDisplayed)

        // Sprawdz czy jest ocena
        val rating = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("filmRating__rateValue")))
        assertTrue(rating.isDisplayed)

        // Sprawdz czy jest rok produkcji
        val year = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("filmCoverSection__year")))
        assertTrue(year.text.contains("1994"))

        // Sprawdz czy jest czas trwania
        val duration = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("filmCoverSection__duration")))
        assertTrue(duration.text.contains("2h 22m"))

        // Sprawdz czy jest rezyser
        val director = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Frank+Darabont']")))
        assertTrue(director.isDisplayed)

        // Sprawdz czy jest gatunek
        val genre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[itemprop*='genre']")))
        assertTrue(genre.text.contains("Dramat"))

    }
}