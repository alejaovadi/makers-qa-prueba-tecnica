package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Página de inicio de sesión de Sauce Demo.
 * Aquí se guarda dónde están los elementos y qué acciones se pueden hacer.
 * Las verificaciones (asserts) NO van aquí, van en los steps.
 */
public class LoginPage {

    private static final String URL = "https://www.saucedemo.com/";

    private final WebDriver driver;
    private final WebDriverWait espera;

    // Elementos de la página
    private final By campoUsuario = By.id("user-name");
    private final By campoContrasena = By.id("password");
    private final By botonLogin = By.id("login-button");
    private final By mensajeError = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Abre la página de login
    public void abrir() {
        driver.get(URL);
    }

    // Escribe el usuario y la contraseña, y da clic en Login
    public void iniciarSesion(String usuario, String contrasena) {
        escribir(campoUsuario, usuario);
        escribir(campoContrasena, contrasena);
        driver.findElement(botonLogin).click();
    }

    // Lee el mensaje de error que aparece cuando el login falla
    public String obtenerMensajeError() {
        return espera.until(ExpectedConditions.visibilityOfElementLocated(mensajeError)).getText();
    }

    // Espera a que el campo esté visible, lo limpia y escribe el texto
    private void escribir(By campo, String texto) {
        WebElement elemento = espera.until(ExpectedConditions.visibilityOfElementLocated(campo));
        elemento.clear();
        elemento.sendKeys(texto);
    }
}

