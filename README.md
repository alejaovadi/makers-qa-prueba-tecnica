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
| TC01 - Crear usuario | Envío un usuario nuevo con nombre "Test User" y trabajo "Automation Engineer" | Que responda 201, con los mismos datos que envié, un id y la fecha de creación | ✅ Pasó |

## Hallazgos
- La API tiene un límite de peticiones por día. Sin una API key propia responde **429 Too Many Requests**. Por eso se recomienda crear una cuenta gratis.