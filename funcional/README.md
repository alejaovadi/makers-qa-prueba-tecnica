# Módulo funcional - MakersPay

## ¿Qué es MakersPay?
MakersPay es una billetera digital (producto ficticio) donde el usuario puede:
- Iniciar sesión.
- Ver su saldo.
- Enviar dinero a otro usuario usando su número de celular.

## ¿Qué voy a probar?
La funcionalidad de **enviar dinero**: un usuario que ya inició sesión envía dinero a otro usuario registrado usando su número de celular.

## Reglas de negocio
| ID | Regla |
|----|-------|
| RN01 | El monto mínimo por transacción es $5.000 COP. |
| RN02 | El monto máximo por transacción es $2.000.000 COP. |
| RN03 | El usuario no puede enviar más dinero del saldo que tiene disponible. |
| RN04 | No se permite enviar dinero a su mismo número de celular. |
| RN05 | Si el envío es exitoso: se descuenta el saldo del remitente, se suma al destinatario y queda registrado en el historial de ambos. |
| RN06 | Si el envío falla, se muestra un mensaje de error claro y el saldo no cambia. |

## Alcance
**Dentro del alcance:**
- Envío de dinero con montos válidos e inválidos.
- Validación de saldo disponible.
- Validación del número de celular del destinatario.
- Actualización de saldos e historial.
- Mensajes de error.

**Fuera del alcance:**
- Registro de usuarios y recuperación de contraseña.
- Recargas o retiros de saldo.
- Pruebas de rendimiento y seguridad avanzada.


## Técnicas de prueba que voy a usar

### 1. Partición de equivalencia
Divido los datos en grupos que el sistema debería tratar igual. Así no tengo que probar todos los valores posibles, sino uno de cada grupo.

| Dato | Grupo válido | Grupos inválidos |
|------|--------------|------------------|
| Monto | Entre $5.000 y $2.000.000 | Menor a $5.000 / Mayor a $2.000.000 / Cero o negativo / Letras o símbolos |
| Saldo | Saldo mayor o igual al monto | Saldo menor al monto |
| Celular destino | Número registrado de otro usuario | Mi propio número / Número no registrado / Formato incorrecto / Campo vacío |

### 2. Análisis de valores límite
Los errores suelen aparecer en los bordes de las reglas, por eso pruebo justo en el límite, un poco antes y un poco después.

| Regla | Valores a probar |
|-------|------------------|
| Monto mínimo (RN01) | $4.999  · $5.000  · $5.001  |
| Monto máximo (RN02) | $1.999.999  · $2.000.000  · $2.000.001  |
| Saldo disponible (RN03) | Enviar exactamente todo el saldo · Enviar $1 más que el saldo  |

### 3. Tabla de decisión
Combino las condiciones principales para ver qué debe pasar en cada caso.

| Condición | R1 | R2 | R3 | R4 |
|-----------|----|----|----|----|
| Monto dentro de los límites | Sí | No | Sí | Sí |
| Saldo suficiente | Sí | Sí | No | Sí |
| Destinatario válido (otro usuario registrado) | Sí | Sí | Sí | No |
| **Resultado** |  Envío exitoso |  Error de monto |  Error de saldo |  Error de destinatario |

### 4. Pruebas basadas en la experiencia
Pruebo situaciones que, por experiencia, suelen generar errores: montos con decimales, dar doble clic en "Enviar", dejar campos vacíos o pegar texto con espacios en el número de celular.

## Tipos de prueba
- **Funcionales:** verifican que el envío de dinero cumpla cada regla de negocio.
- **Positivas y negativas:** compruebo que funcione con datos correctos y que muestre errores claros con datos incorrectos.
- **Integración:** verifico que el saldo cambie en las dos cuentas y que el movimiento aparezca en el historial de ambos usuarios.
- **Smoke:** un recorrido rápido del flujo principal (iniciar sesión, ver saldo y enviar dinero) para saber si la versión está estable para seguir probando.
- **Regresión:** volver a ejecutar los casos después de corregir un bug, para asegurar que no se dañó nada más.
- **Usabilidad:** reviso que los mensajes de error sean claros y fáciles de entender.


## Escenarios de prueba

Los escenarios están escritos en Gherkin para que cualquier persona del equipo, técnica o no, entienda qué se va a probar.
Uso como ejemplo a dos usuarios: **Laura**, que envía el dinero, y **Andrés**, que lo recibe.

```gherkin
# language: es
Característica: Enviar dinero a otro usuario con su número de celular
  Como usuaria de MakersPay
  Quiero enviarle dinero a otra persona usando solo su número de celular
  Para pagarle o compartir gastos de forma rápida y segura

  Antecedentes:
    Dado que Laura inició sesión en MakersPay y tiene $3.000.000 disponibles
    Y Andrés está registrado con el celular 310 987 6543 y tiene $100.000

  Escenario: Laura le envía dinero a Andrés y todo sale bien
    Cuando Laura le envía $50.000 a Andrés
    Entonces Laura ve un mensaje confirmando que el envío fue exitoso
    Y su saldo baja a $2.950.000
    Y el saldo de Andrés sube a $150.000
    Y el envío aparece en el historial de los dos

  Esquema del escenario: Laura envía montos justo en los límites permitidos
    Cuando Laura le envía <monto> a Andrés
    Entonces el envío se realiza sin problemas
    Y a Laura se le descuentan <monto> de su saldo

    Ejemplos:
      | monto      |
      | $5.000     |
      | $5.001     |
      | $1.999.999 |
      | $2.000.000 |

  Esquema del escenario: Laura intenta enviar un monto fuera de los límites
    Cuando Laura intenta enviarle <monto> a Andrés
    Entonces ve un mensaje que le explica que <explicacion>
    Y su saldo sigue igual
    Y Andrés no recibe nada

    Ejemplos:
      | monto      | explicacion                                    |
      | $4.999     | el monto mínimo para enviar es $5.000          |
      | $2.000.001 | el monto máximo por envío es $2.000.000        |

  Escenario: Laura intenta enviar más dinero del que tiene
    Dado que a Laura solo le quedan $40.000 disponibles
    Cuando intenta enviarle $50.000 a Andrés
    Entonces ve un mensaje que le explica que no tiene saldo suficiente
    Y su saldo sigue en $40.000
    Y Andrés no recibe nada

  Escenario: Laura envía exactamente todo lo que tiene
    Dado que a Laura solo le quedan $40.000 disponibles
    Cuando le envía $40.000 a Andrés
    Entonces el envío se realiza sin problemas
    Y el saldo de Laura queda en $0

  Escenario: Laura intenta enviarse dinero a sí misma
    Cuando Laura intenta enviar $20.000 a su propio número de celular
    Entonces ve un mensaje que le explica que no puede enviarse dinero a sí misma
    Y su saldo sigue igual

  Escenario: Laura intenta enviar dinero a un número que no está en MakersPay
    Cuando Laura intenta enviar $20.000 al celular 300 000 0000, que no está registrado
    Entonces ve un mensaje que le explica que ese número no tiene cuenta en MakersPay
    Y su saldo sigue igual

  Esquema del escenario: Laura escribe datos que no son válidos
    Cuando Laura intenta enviar "<monto>" al celular "<celular>"
    Entonces ve un mensaje claro que le indica qué debe corregir
    Y no se realiza ningún envío

    Ejemplos:
      | monto     | celular      | por qué no es válido           |
      |           | 310 987 6543 | dejó el monto vacío            |
      | 0         | 310 987 6543 | el monto es cero               |
      | -10.000   | 310 987 6543 | el monto es negativo           |
      | diez mil  | 310 987 6543 | escribió el monto en letras    |
      | 20.000    |              | dejó el celular vacío          |
      | 20.000    | 310 ABC 6543 | el celular tiene letras        |

  Escenario: Alguien intenta enviar dinero sin haber iniciado sesión
    Dado que Laura cerró su sesión
    Cuando intenta entrar directamente a la opción de enviar dinero
    Entonces la app le pide que inicie sesión primero

  Escenario: Laura da doble clic en "Enviar" sin querer
    Cuando Laura le envía $50.000 a Andrés y presiona "Enviar" dos veces seguidas
    Entonces el dinero se envía una sola vez
    Y en el historial aparece un solo movimiento


## Casos de prueba

**Datos de prueba que uso en todos los casos (salvo que el caso diga otra cosa):**
- **Laura** (quien envía): sesión iniciada, celular 300 123 4567, saldo $3.000.000.
- **Andrés** (quien recibe): registrado con el celular 310 987 6543, saldo $100.000.

**Pasos base para enviar dinero:**
1. Entrar a la opción "Enviar dinero".
2. Escribir el número de celular del destinatario.
3. Escribir el monto.
4. Presionar "Enviar".

| ID | Caso de prueba | Datos | Resultado esperado | Prioridad | Técnica | Regla |
|----|----------------|-------|--------------------|-----------|---------|-------|
| CP01 | Enviar dinero con datos válidos | Celular: 310 987 6543<br>Monto: $50.000 | Mensaje de envío exitoso.<br>Saldo de Laura: $2.950.000.<br>Saldo de Andrés: $150.000. | Alta | Partición de equivalencia | RN05 |
| CP02 | Enviar exactamente el monto mínimo | Monto: $5.000 | El envío se realiza y a Laura se le descuentan $5.000. | Alta | Valores límite | RN01 |
| CP03 | Enviar un peso más del mínimo | Monto: $5.001 | El envío se realiza sin problemas. | Media | Valores límite | RN01 |
| CP04 | Enviar un peso menos del mínimo | Monto: $4.999 | Mensaje claro indicando que el mínimo es $5.000.<br>Los saldos no cambian. | Alta | Valores límite | RN01, RN06 |
| CP05 | Enviar exactamente el monto máximo | Monto: $2.000.000 | El envío se realiza y a Laura se le descuentan $2.000.000. | Alta | Valores límite | RN02 |
| CP06 | Enviar un peso menos del máximo | Monto: $1.999.999 | El envío se realiza sin problemas. | Media | Valores límite | RN02 |
| CP07 | Enviar un peso más del máximo | Monto: $2.000.001 | Mensaje claro indicando que el máximo es $2.000.000.<br>Los saldos no cambian. | Alta | Valores límite | RN02, RN06 |
| CP08 | Enviar más dinero del saldo disponible | Saldo de Laura: $40.000<br>Monto: $50.000 | Mensaje claro de saldo insuficiente.<br>Saldo de Laura sigue en $40.000.<br>Andrés no recibe nada. | Alta | Partición de equivalencia | RN03, RN06 |
| CP09 | Enviar exactamente todo el saldo | Saldo de Laura: $40.000<br>Monto: $40.000 | El envío se realiza.<br>Saldo de Laura queda en $0. | Media | Valores límite | RN03 |
| CP10 | Enviarse dinero a sí misma | Celular: 300 123 4567 (el de Laura)<br>Monto: $20.000 | Mensaje claro indicando que no puede enviarse dinero a sí misma.<br>El saldo no cambia. | Alta | Partición de equivalencia | RN04, RN06 |
| CP11 | Enviar a un número que no está registrado | Celular: 300 000 0000<br>Monto: $20.000 | Mensaje claro indicando que el número no tiene cuenta en MakersPay.<br>El saldo no cambia. | Alta | Partición de equivalencia | RN06 |
| CP12 | Dejar el monto vacío | Monto: (vacío) | El sistema no permite enviar y pide escribir un monto. | Media | Partición de equivalencia | RN06 |
| CP13 | Escribir un monto en cero o negativo | Monto: 0 y luego -10.000 | El sistema no permite enviar y muestra un mensaje claro en ambos casos. | Media | Partición de equivalencia | RN06 |
| CP14 | Escribir el monto con letras | Monto: "diez mil" | El campo no acepta letras o muestra un mensaje claro. | Baja | Basada en la experiencia | RN06 |
| CP15 | Dejar el celular vacío o escribirlo con letras | Celular: (vacío) y luego "310 ABC 6543" | El sistema no permite enviar y pide un número válido. | Media | Partición de equivalencia | RN06 |
| CP16 | Revisar el historial después de un envío exitoso | Ejecutar primero el CP01 | En el historial de Laura aparece el envío de $50.000 a Andrés.<br>En el historial de Andrés aparece el dinero recibido de Laura.<br>Ambos con la misma fecha, hora y monto. | Alta | Integración | RN05 |
| CP17 | Intentar enviar dinero sin sesión iniciada | Laura cierra sesión e intenta entrar a "Enviar dinero" | La app no deja entrar y le pide iniciar sesión. | Alta | Basada en la experiencia | Requerimiento: usuario autenticado |
| CP18 | Presionar "Enviar" dos veces seguidas | Monto: $50.000<br>Doble clic en "Enviar" | El dinero se envía una sola vez.<br>Saldo de Laura: $2.950.000 (no $2.900.000).<br>Un solo movimiento en el historial. | Alta | Basada en la experiencia | RN05 |
```