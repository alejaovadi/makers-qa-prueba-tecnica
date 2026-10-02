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