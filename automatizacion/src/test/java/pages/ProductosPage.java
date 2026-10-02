package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Página de productos de Sauce Demo.
 * Es la página a la que se llega después de un login exitoso.
 */
public class ProductosPage {

    private final WebDriver driver;
    private final WebDriverWait espera;

    // Elementos de la página
    private final By titulo = By.className("title");

    public ProductosPage(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Lee el título de la página (debe decir "Products")
    public String obtenerTitulo() {
        return espera.until(ExpectedConditions.visibilityOfElementLocated(titulo)).getText();
    }

    // Devuelve la dirección actual del navegador
    public String obtenerUrl() {
        return driver.getCurrentUrl();
    }
}
