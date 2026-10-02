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