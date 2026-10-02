# language: es
Característica: Inicio de sesión en Sauce Demo
  Como cliente de la tienda
  Quiero iniciar sesión con mi usuario
  Para poder ver y comprar productos

  Antecedentes:
    Dado que estoy en la página de inicio de sesión

  Escenario: Login exitoso con credenciales válidas
    Cuando ingreso el usuario "standard_user" y la contraseña "secret_sauce"
    Entonces debo ver la página de productos

  Escenario: Login fallido con contraseña incorrecta
    Cuando ingreso el usuario "standard_user" y la contraseña "clave_incorrecta"
    Entonces debo ver el mensaje de error "Epic sadface: Username and password do not match any user in this service"

  Esquema del escenario: Validación de campos obligatorios
    Cuando ingreso el usuario "<usuario>" y la contraseña "<contrasena>"
    Entonces debo ver el mensaje de error "<mensaje>"

    Ejemplos:
      | usuario       | contrasena   | mensaje                            |
      |               |              | Epic sadface: Username is required |
      |               | secret_sauce | Epic sadface: Username is required |
      | standard_user |              | Epic sadface: Password is required |