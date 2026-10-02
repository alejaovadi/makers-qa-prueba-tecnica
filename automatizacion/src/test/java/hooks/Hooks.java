package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Acciones que se ejecutan antes y después de cada escenario.
 */
public class Hooks {

    private static WebDriver driver;

    // Antes de cada escenario: abre Chrome
    @Before
    public void abrirNavegador() {
        ChromeOptions opciones = new ChromeOptions();
        opciones.addArguments("--start-maximized");
        opciones.addArguments("--incognito"); // evita ventanas emergentes de contraseñas
        driver = new ChromeDriver(opciones);
    }

    // Después de cada escenario: si falló, toma una captura; luego cierra Chrome
    @After
    public void cerrarNavegador(Scenario escenario) {
        if (escenario.isFailed()) {
            byte[] captura = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            escenario.attach(captura, "image/png", "Captura del error");
        }
        driver.quit();
    }

    // Permite que los steps usen el mismo navegador
    public static WebDriver getDriver() {
        return driver;
    }
}