---
title: Login
sidebar_position: 70
_i18n_hash: 59a9ab8cb7ba550b955ab83de0c6d878
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

El componente `Login` simplifica la autenticación de usuario al proporcionar un cuadro de diálogo de inicio de sesión listo para usar con campos de nombre de usuario y contraseña. Incluye características como validación de entrada, etiquetas y mensajes personalizables, controles de visibilidad de la contraseña y soporte para campos personalizados adicionales.

<!-- INTRO_END -->

## Creando un cuadro de diálogo `Login` {#creating-a-login-dialog}

Crea un cuadro de diálogo `Login` instanciando el componente y llamando a `open()` para mostrarlo. El cuadro de diálogo incluye campos de nombre de usuario y contraseña, validación de entrada y un botón de inicio de sesión por defecto.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Envío de inicio de sesión {#login-submission}

Cuando los usuarios ingresan su nombre de usuario y contraseña, el componente `Login` valida estas entradas como campos requeridos. Una vez que la validación se completa, se activa un evento de envío de formulario, entregando las credenciales ingresadas. Para evitar múltiples envíos, el botón de [Iniciar sesión] se deshabilita de inmediato.

Lo siguiente ilustra un componente `Login` básico. Si el nombre de usuario y la contraseña se establecen ambos en `"admin"` respectivamente, el cuadro de diálogo de inicio de sesión se cierra y aparece un botón de [Cerrar sesión]. Si las credenciales no coinciden, se muestra el mensaje de error predeterminado.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Deshabilitando el botón [Iniciar sesión]
Por defecto, `Login` deshabilita inmediatamente el botón [Iniciar sesión] una vez que el componente valida las entradas de inicio de sesión como correctas, para evitar múltiples envíos. Puedes volver a habilitar el botón [Iniciar sesión] usando el método `setEnabled(true)`.
:::

:::tip Permitindo contraseñas vacías
Puedes permitir que los usuarios inicien sesión con solo un nombre de usuario utilizando el método `setEmptyPassword(true)`.
:::

## Acción de formulario <DocChip chip='since' label='25.10' />{#form-action}

El componente `Login` puede enviar datos de formulario directamente a una URL especificada en lugar de manejar el envío a través del evento de envío. Cuando se establece una URL de acción, el formulario realiza una solicitud POST estándar con el nombre de usuario y la contraseña como parámetros del formulario.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Al usar `setAction()`, el envío del formulario omite el `LoginSubmitEvent` y en su lugar realiza una solicitud HTTP POST tradicional al punto final especificado. El nombre de usuario y la contraseña se envían como parámetros del formulario llamados `"username"` y `"password"`, respectivamente. Los campos personalizados con un atributo de nombre también se incluyen en la solicitud POST.

:::tip 
Si no se establece una URL de acción, el envío del formulario se maneja a través del `LoginSubmitEvent`, lo que te permite procesar credenciales programáticamente en el servidor.
:::

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente `Login` son completamente personalizables utilizando la clase `LoginI18n`. Esta flexibilidad te permite adaptar la interfaz de inicio de sesión para cumplir con requisitos de localización específicos o preferencias de personalización.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '600px'
/>

## Campos personalizados {#custom-fields}

El componente `Login` incluye varios slots que te permiten agregar campos adicionales según sea necesario. Los campos personalizados se recopilan automáticamente cuando se envía el formulario y se pueden acceder a través del mapa de datos del evento de envío.

El siguiente inicio de sesión tiene un campo personalizado añadido para un ID de cliente. Esto puede ayudarte a gestionar empresas o departamentos con contenido compartido entre múltiples usuarios.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Nombre requerido
Los campos personalizados deben tener un nombre establecido utilizando `setName()` para ser incluidos en el envío del formulario. El nombre se utiliza como clave para recuperar el valor del campo de `event.getData()`.
:::

## Botón de cancelar {#cancel-button}

`Login` incluye un botón de [Cancelar] que está oculto por defecto. Esto es particularmente útil cuando un usuario intenta acceder a un área restringida de la aplicación y necesita una opción para volver a su ubicación anterior sin completar el inicio de sesión.

Para hacer visible el botón de cancelar, proporciona una etiqueta para él. También puedes escuchar eventos de cancelación para manejar la cancelación de manera adecuada.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Ocultando elementos
Para ocultar un elemento, establece su etiqueta en una cadena vacía. Esto te permite alternar la visibilidad sin eliminar el componente de tu código.
:::

## Gestores de contraseñas {#password-managers}

Este componente funciona con gestores de contraseñas basados en navegadores para simplificar el proceso de inicio de sesión. En navegadores basados en Chromium, se integra con la API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential), que proporciona:

- **Autocompletar**: El navegador puede completar automáticamente los campos de nombre de usuario y contraseña si el usuario ha guardado credenciales para el sitio.
- **Gestión de credenciales**: Después de iniciar sesión, el navegador puede solicitar al usuario que guarde nuevas credenciales, haciendo que los futuros inicios de sesión sean más rápidos y fáciles.
- **Selección de credenciales**: Si se han guardado múltiples credenciales, el navegador puede ofrecer al usuario la opción de seleccionar uno de los conjuntos guardados.

## Estilo {#styling}

<TableBuilder name="Login" />
