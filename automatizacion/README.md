# Smoke Test de Login - Sauce Demo

Pruebas automatizadas del inicio de sesión de la tienda [Sauce Demo](https://www.saucedemo.com/).
Las pruebas están escritas en español con Gherkin, para que cualquier persona pueda leerlas y entender qué se valida.

## ¿Qué se valida?

| Escenario | ¿Qué hace? | Resultado esperado |
|-----------|------------|--------------------|
| Login exitoso | Inicio sesión con `standard_user` / `secret_sauce` | Entra a la página de productos |
| Login fallido | Inicio sesión con una contraseña incorrecta | Muestra el mensaje de error de credenciales |
| Campos obligatorios | Intento iniciar sesión dejando campos vacíos (3 combinaciones) | Muestra el mensaje que indica qué campo falta |

**Resultado:** ✅ los 5 escenarios pasan.

## Herramientas
- Java 17
- Maven
- Selenium 4.27
- Cucumber 7 (Gherkin en español)
- JUnit 5 (asserts)
- Google Chrome

## ¿Qué necesito tener instalado?
- **Java 17** o superior
- **Maven** 3.9 o superior
- **Google Chrome** (no hace falta descargar ChromeDriver; Selenium lo gestiona automáticamente)
- **Git**

Puedes verificarlo en la consola con:

```
java -version
mvn -version
```

## ¿Cómo descargar y ejecutar el proyecto?

1. Clonar el repositorio:

```
git clone https://github.com/alejaovadi/makers-qa-prueba-tecnica.git
```

2. Entrar a la carpeta de automatización:

```
cd makers-qa-prueba-tecnica/automatizacion
```

3. Ejecutar las pruebas:

```
mvn test
```

Chrome se abrirá automáticamente y ejecutará cada escenario.

También se puede ejecutar desde IntelliJ: clic derecho sobre `RunCucumberTest` → **Run**.

## ¿Dónde veo los resultados?
Al terminar, se genera un reporte en:

```
automatizacion/target/reporte-cucumber.html
```

Ábrelo con el navegador para ver cada escenario y sus pasos.
Si algún escenario falla, el reporte incluye una **captura de pantalla** del momento del error.

## ¿Cómo está organizado?

```
automatizacion/
├── pom.xml                             → dependencias del proyecto
└── src/test/
    ├── java/
    │   ├── pages/                      → Page Objects (dónde están los elementos y qué acciones se pueden hacer)
    │   │   ├── LoginPage.java
    │   │   └── ProductosPage.java
    │   ├── steps/LoginSteps.java       → conecta el Gherkin con el código y hace las verificaciones (asserts)
    │   ├── hooks/Hooks.java            → abre y cierra Chrome; toma captura si un escenario falla
    │   └── runner/RunCucumberTest.java → ejecuta todos los escenarios
    └── resources/features/
        └── login.feature               → los escenarios escritos en Gherkin
```

## Buenas prácticas aplicadas
- **Page Object Model:** cada página tiene su propia clase, así si cambia la página solo se ajusta en un lugar.
- **Localizadores estables:** se usan `id` y `data-test`, evitando XPath frágiles.
- **Esperas explícitas:** se espera a que los elementos estén visibles, sin pausas fijas (`Thread.sleep`).
- **Asserts con mensajes claros:** si una prueba falla, se entiende por qué.
- **Esquema del escenario:** se prueban varias combinaciones de campos vacíos sin repetir pasos.
- **Evidencia automática:** captura de pantalla cuando un escenario falla.

## Nota
Al ejecutar puede aparecer una advertencia de Selenium sobre "CDP". No es un error: indica que la versión de Chrome es más nueva que unas herramientas opcionales que este proyecto no usa.