---
title: Login
sidebar_position: 70
_i18n_hash: e0aded01aee7ef12465b2d7661cc0477
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

El componente `Login` simplifica la autenticación de usuarios al proporcionar un cuadro de diálogo de inicio de sesión listo para usar con campos de nombre de usuario y contraseña. Incluye funciones como validación de entrada, etiquetas y mensajes personalizables, controles de visibilidad de contraseña y soporte para campos personalizados adicionales.

<!-- INTRO_END -->

## Creando un cuadro de diálogo `Login` {#creating-a-login-dialog}

Crea un cuadro de diálogo `Login` instanciando el componente y llamando a `open()` para mostrarlo. El cuadro de diálogo incluye campos de nombre de usuario y contraseña, validación de entrada y un botón de inicio de sesión por defecto.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Envío de inicio de sesión {#login-submission}

Cuando los usuarios ingresan su nombre de usuario y contraseña, el componente `Login` valida estas entradas como campos requeridos. Una vez que la validación pasa, se dispara un evento de envío de formulario, entregando las credenciales ingresadas. Para evitar múltiples envíos, el botón [Sign in] se desactiva inmediatamente.

Lo siguiente ilustra un componente básico de `Login`. Si tanto el nombre de usuario como la contraseña están configurados en `"admin"`, el cuadro de diálogo de inicio de sesión se cierra y aparece un botón [Logout]. Si las credenciales no coinciden, se muestra el mensaje de error predeterminado.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Desactivando el botón [Sign in]
Por defecto, `Login` desactiva inmediatamente el botón [Sign in] una vez que el componente valida las entradas de inicio de sesión como correctas, para prevenir múltiples envíos. Puedes volver a activar el botón [Sign in] usando el método `setEnabled(true)`.
:::

:::tip Permitiendo contraseñas vacías
Puedes permitir que los usuarios inicien sesión solo con un nombre de usuario utilizando el método `setEmptyPassword(true)`.
:::

## Acción del formulario <DocChip chip='since' label='25.10' />{#form-action}

El componente `Login` puede enviar datos del formulario directamente a una URL especificada en lugar de manejar el envío a través del evento de envío. Cuando se establece una URL de acción, el formulario realiza una solicitud POST estándar con el nombre de usuario y la contraseña como parámetros del formulario.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Al usar `setAction()`, el envío del formulario omite el `LoginSubmitEvent` y realiza en su lugar una solicitud HTTP POST tradicional al endpoint especificado. El nombre de usuario y la contraseña se envían como parámetros del formulario llamados "username" y "password", respectivamente. Los campos personalizados con un atributo name también se incluyen en la solicitud POST.

:::tip 
Si no se establece una URL de acción, el envío del formulario se maneja a través del `LoginSubmitEvent`, permitiéndote procesar las credenciales programáticamente en el lado del servidor.
:::

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente `Login` son completamente personalizables utilizando la clase `LoginI18n`. Esta flexibilidad te permite adaptar la interfaz de inicio de sesión para cumplir con requisitos de localización específicos o preferencias de personalización.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Campos personalizados {#custom-fields}

El componente `Login` incluye varios espacios que te permiten agregar campos adicionales según sea necesario. Los campos personalizados se recogen automáticamente cuando se envía el formulario y se pueden acceder a través del mapa de datos del evento de envío.

El siguiente inicio de sesión tiene un campo personalizado agregado para un ID de cliente. Esto puede ayudarte a gestionar empresas o departamentos con contenido compartido entre múltiples usuarios.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Nombre requerido
Los campos personalizados deben tener un nombre establecido mediante `setName()` para ser incluidos en el envío del formulario. El nombre se utiliza como clave para recuperar el valor del campo de `event.getData()`.
:::

## Botón de cancelar {#cancel-button}

`Login` incluye un botón [Cancel] que está oculto por defecto. Esto es particularmente útil cuando un usuario intenta acceder a un área restringida de la aplicación y necesita una opción para regresar a su ubicación anterior sin completar el inicio de sesión.

Para hacer visible el botón de cancelar, proporciona una etiqueta para él. También puedes escuchar eventos de cancelación para manejar la cancelación de manera apropiada.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Ocultando elementos
Para ocultar un elemento, establece su etiqueta en una cadena vacía. Esto te permite alternar la visibilidad sin eliminar el componente de tu código.
:::

## Gestores de contraseñas {#password-managers}

Este componente funciona con gestores de contraseñas basados en el navegador para simplificar el proceso de inicio de sesión. En navegadores basados en Chromium, se integra con la API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential), que provee:

- **Autocompletar**: El navegador puede completar automáticamente los campos de nombre de usuario y contraseña si el usuario ha guardado credenciales para el sitio.
- **Gestión de Credenciales**: Después de iniciar sesión, el navegador puede solicitar al usuario que guarde nuevas credenciales, haciendo que futuros inicios de sesión sean más rápidos y fáciles.
- **Selección de Credenciales**: Si se han guardado múltiples credenciales, el navegador puede ofrecer al usuario una opción para seleccionar de uno de los conjuntos guardados.

## Estilizado {#styling}

<TableBuilder name="Login" />
