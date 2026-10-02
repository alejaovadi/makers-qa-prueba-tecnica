package steps;

import hooks.Hooks;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import pages.LoginPage;
import pages.ProductosPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Conecta cada paso escrito en Gherkin (login.feature) con las acciones en la página.
 * Aquí van las verificaciones (asserts).
 */
public class LoginSteps {

    private LoginPage loginPage;
    private ProductosPage productosPage;

    @Dado("que estoy en la página de inicio de sesión")
    public void queEstoyEnLaPaginaDeInicioDeSesion() {
        loginPage = new LoginPage(Hooks.getDriver());
        productosPage = new ProductosPage(Hooks.getDriver());
        loginPage.abrir();
    }

    @Cuando("ingreso el usuario {string} y la contraseña {string}")
    public void ingresoElUsuarioYLaContrasena(String usuario, String contrasena) {
        loginPage.iniciarSesion(usuario, contrasena);
    }

    @Entonces("debo ver la página de productos")
    public void deboVerLaPaginaDeProductos() {
        assertEquals("Products", productosPage.obtenerTitulo(),
                "Después del login debería ver el título 'Products'");
        assertTrue(productosPage.obtenerUrl().contains("inventory"),
                "Después del login la dirección debería ser la de productos (inventory)");
    }

    @Entonces("debo ver el mensaje de error {string}")
    public void deboVerElMensajeDeError(String mensajeEsperado) {
        assertEquals(mensajeEsperado, loginPage.obtenerMensajeError(),
                "El mensaje de error no es el esperado");
    }
}