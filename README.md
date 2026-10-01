# Pruebas a la API de usuarios de Reqres

En este proyecto pruebo que la API de Reqres (https://reqres.in/api/) funcione bien a la hora de crear y consultar usuarios.

## ¿Qué voy a validar?
- Que se pueda crear un usuario nuevo.
- Que después de crearlo, se pueda consultar y sus datos sean los mismos que envié.
- Otros casos adicionales que vaya encontrando en el camino.

## ¿Con qué lo hice?
- Postman, para armar y ejecutar las pruebas.
- Git, para ir guardando el avance paso a paso.

## ¿Cómo ejecutar las pruebas?
1. Abrir Postman y dar clic en **Import**.
2. Importar los dos archivos de la carpeta `postman`.
3. Crear una cuenta gratis en https://app.reqres.in para obtener una API key propia.
4. En el environment **regres**, reemplazar el valor de `api_key` por esa llave.
5. Seleccionar el environment **regres** (arriba a la derecha).
6. Ejecutar la colección **Usuarios Regres**.

## Casos de prueba

| Caso | ¿Qué hace? | ¿Qué espero? | Resultado |
|------|------------|--------------|-----------|
| TC01 - Crear usuario | Envío un usuario nuevo con nombre "Test User" y trabajo "Automation Engineer" | Que responda 201, con los mismos datos que envié, un id y la fecha de creación |  Pasó |
| TC02 - Consultar usuario creado | Consulto el usuario usando el id que me dio el TC01 | Que responda 200 y que el nombre y el trabajo sean los mismos que envié |  Falló (404) |
| TC03 - Consultar un usuario que sí existe | Consulto el usuario 2, que ya viene en Reqres | Que responda 200 y traiga sus datos |  Pasó |
| TC04 - Consultar un usuario que no existe | Consulto el usuario 9999 | Que responda 404 y venga vacío |  Pasó |
| TC05 - Crear un usuario sin datos | Envío la creación sin nombre ni trabajo | Que la API lo rechace con 400 |  Falló (201) |
## Hallazgos
- La API tiene un límite de peticiones por día. Sin una API key propia responde **429 Too Many Requests**. Por eso se recomienda crear una cuenta gratis.
- Al consultar el usuario recién creado, la API responde **404 Not Found**. Esto pasa porque Reqres es una API de práctica que no guarda los usuarios: responde como si los creara, pero no quedan almacenados. La misma API lo indica en su respuesta ("read-only demo endpoint"). La prueba está bien construida; el fallo se debe al comportamiento de la API.
- El TC03 confirma que la consulta (GET) sí funciona con los usuarios que ya trae Reqres. Esto refuerza que el 404 del TC02 se debe a que la API no guarda los usuarios creados.
- La API permite crear un usuario sin nombre ni trabajo (responde 201). No valida los campos obligatorios; lo esperado sería un error 400.