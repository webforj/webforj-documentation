---
title: Login
sidebar_position: 70
_i18n_hash: f2f1f96cfde1dbbede5bfdaafd3f0a92
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

El componente de Login está diseñado para proporcionar una interfaz amigable para la autenticación, permitiendo a los usuarios iniciar sesión usando un nombre de usuario y una contraseña. Soporta varias personalizaciones para mejorar la experiencia del usuario en diferentes dispositivos y locales.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usos {#usages}

El componente de Login proporciona una interfaz de formulario de inicio de sesión amigable dentro de un diálogo para ingresar las credenciales de autenticación. Mejora la experiencia del usuario al ofrecer:
   >- Campos de entrada claros para el nombre de usuario y la contraseña.
   >- Alternar visibilidad de la contraseña para verificar la entrada.
   >- Retroalimentación de validación de entrada para solicitar el formato correcto antes de la presentación.

## Envío de inicio de sesión {#login-submission}

Cuando los usuarios ingresan su nombre de usuario y contraseña, el componente de inicio de sesión valida estas entradas como campos requeridos. Una vez que la validación pasa, se genera un evento de envío de formulario, entregando las credenciales ingresadas. Para prevenir múltiples envíos, el botón `Signin` se deshabilita inmediatamente.

La demostración a continuación ilustra un proceso básico de envío de formulario. Si el nombre de usuario y la contraseña se establecen en `"admin"` respectivamente, el diálogo de inicio de sesión se cierra y aparece un botón de cierre de sesión. Si las credenciales no coinciden, se muestra el mensaje de error predeterminado del formulario de inicio de sesión.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Deshabilitando el botón de inicio de sesión
Por defecto, el formulario de inicio de sesión deshabilita inmediatamente el botón `Signin` una vez que el componente valida las entradas de inicio de sesión como correctas, para prevenir múltiples envíos. Puedes volver a habilitar el botón `Signin` usando el método `setEnabled(true)`.
:::

:::tip Permitiendo contraseñas vacías
En ciertos escenarios, las contraseñas vacías pueden ser permisibles, permitiendo a los usuarios iniciar sesión solo con un nombre de usuario. El diálogo de inicio de sesión puede configurarse para aceptar contraseñas vacías estableciendo `setEmptyPassword(true)`.
:::

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente de inicio de sesión son totalmente personalizables usando la clase `LoginI18n`. Esta flexibilidad te permite adaptar la interfaz de inicio de sesión para cumplir con requisitos de localización específicos o preferencias de personalización.

La demostración a continuación ilustra cómo proporcionar una traducción al alemán para el diálogo de inicio de sesión, asegurando que todos los elementos de la interfaz se adapten al idioma alemán para mejorar la experiencia del usuario para los usuarios de habla alemana.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Campos personalizados {#custom-fields}

El componente de inicio de sesión incluye varios espacios, que te permiten agregar campos adicionales si es necesario. Esta característica proporciona más control sobre la información requerida para una autenticación exitosa.

En el ejemplo a continuación, se añade un campo de ID de Cliente al formulario de inicio de sesión. Los usuarios deben proporcionar un ID válido para completar la autenticación, lo que mejora la seguridad y asegura que el acceso se conceda solo después de verificar todas las credenciales requeridas.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Payload de envío
Ten en cuenta que el componente de inicio de sesión no reconoce ni incluye automáticamente los campos adicionales agregados al formulario en su payload de envío. Esto significa que los desarrolladores deben recuperar explícitamente el valor de cualquier campo adicional del lado del cliente y manejarlo de acuerdo con los requisitos de la aplicación para completar el proceso de autenticación.
:::

## Botón de cancelar {#cancel-button}

En ciertos escenarios, puede ser deseable agregar un botón de cancelar junto al botón `Signin`. Esta característica es útil particularmente cuando un usuario intenta acceder a un área restringida de la aplicación y necesita una opción para cancelar la acción y volver a su ubicación anterior. El formulario de inicio de sesión incluye un botón de cancelar por defecto, pero está oculto de la vista.

Para hacer visible el botón de cancelar, debes proporcionar una etiqueta para él - una vez etiquetado, aparecerá en la pantalla. También puedes escuchar eventos de cancelación para responder apropiadamente a las acciones del usuario, asegurando una experiencia fluida y amigable al navegar por la aplicación.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Ocultando elementos
Para ocultar un elemento de la pantalla de inicio de sesión, simplemente establece su etiqueta en una cadena vacía. Este enfoque es particularmente útil para eliminar temporalmente componentes de la interfaz sin alterar permanentemente la base de código.
:::

## Administradores de contraseñas {#password-managers}

El componente de inicio de sesión está diseñado para ser compatible con administradores de contraseñas basados en navegadores, mejorando la experiencia del usuario al simplificar el proceso de inicio de sesión. Para los usuarios de navegadores basados en Chromium, el componente se integra sin problemas con la API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential). Esta integración permite varias características convenientes:

- **Relleno automático**: El navegador puede rellenar automáticamente los campos de nombre de usuario y contraseña si el usuario ha guardado credenciales para el sitio.
- **Gestión de credenciales**: Después de iniciar sesión, el navegador puede solicitar al usuario que guarde nuevas credenciales, haciendo que futuros inicios de sesión sean más rápidos y fáciles.
- **Selección de credenciales**: Si se han guardado múltiples credenciales, el navegador puede ofrecer al usuario la opción de seleccionar uno de los conjuntos guardados.

## Estilo {#styling}

<TableBuilder name="Login" />
