\# Prueba técnica QA Full Stack - Makers



Este repositorio contiene mi solución a la prueba técnica. Está dividida en tres partes: el proceso de pruebas funcionales, pruebas a una API y automatización de pruebas web.



\##  funcional

Proceso completo de pruebas para \*\*MakersPay\*\*, una billetera digital ficticia, enfocado en la funcionalidad de enviar dinero a otro usuario.



Incluye:

\- Reglas de negocio y alcance.

\- Técnicas de prueba (partición de equivalencia, valores límite, tabla de decisión y pruebas basadas en la experiencia) y tipos de prueba.

\- Escenarios de prueba escritos en Gherkin.

\- 18 casos de prueba con datos, resultado esperado, prioridad, técnica y regla relacionada.

\- Ejemplos de reporte de bugs con severidad y prioridad.



\##  api

Pruebas funcionales a la API de usuarios de Reqres (https://reqres.in/api/), hechas con Postman.



Lo que valido:

\- Crear un usuario y comprobar que la API responda correctamente.

\- Consultar el usuario recién creado y verificar que sus datos sean los mismos.

\- Casos adicionales: usuario existente, usuario que no existe y creación sin datos.



Dentro de la carpeta encuentras los casos de prueba, cómo ejecutarlos y los hallazgos encontrados.



\##  automatizacion

Smoke test automatizado del inicio de sesión de la tienda Sauce Demo (https://www.saucedemo.com/), hecho con \*\*Selenium, Java y Cucumber\*\*, con los escenarios escritos en Gherkin en español.



Lo que valido:

\- Login exitoso con un usuario y contraseña válidos.

\- Login fallido cuando la contraseña es incorrecta.

\- Que el sistema avise cuando los campos obligatorios están vacíos.



Dentro de la carpeta encuentras cómo descargar y ejecutar el proyecto.



\## Herramientas usadas

\- Postman

\- Java 17, Maven, Selenium, Cucumber y JUnit 5

\- Git y GitHub

