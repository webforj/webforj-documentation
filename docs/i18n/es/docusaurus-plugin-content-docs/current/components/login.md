---
title: Login
sidebar_position: 70
_i18n_hash: b95b5a072de318071d9d7ecae890a883
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

El componente Login está diseñado para proporcionar una interfaz intuitiva y amigable para la autenticación, permitiendo a los usuarios iniciar sesión utilizando un nombre de usuario y una contraseña. Soporta diversas personalizaciones para mejorar la experiencia del usuario en diferentes dispositivos y localidades.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usos {#usages}

El componente Login proporciona una interfaz de formulario de inicio de sesión amigable dentro de un cuadro de diálogo para ingresar las credenciales de autenticación. Mejora la experiencia del usuario al ofrecer:
   >- Campos de entrada claros para nombre de usuario y contraseña.
   >- Alternar visibilidad de la contraseña para verificar la entrada.
   >- Retroalimentación de validación de entrada para solicitar el formato correcto antes de la presentación.

## Envío de inicio de sesión {#login-submission}

Cuando los usuarios ingresan su nombre de usuario y contraseña, el componente de inicio de sesión valida estas entradas como campos requeridos. Una vez que la validación pasa, se activa un evento de envío de formulario, entregando las credenciales ingresadas. Para evitar múltiples envíos, el botón `Signin` se desactiva inmediatamente.

La demostración a continuación ilustra un proceso básico de envío de formulario. Si tanto el nombre de usuario como la contraseña están configurados en `"admin"`, el cuadro de diálogo de inicio de sesión se cierra y aparece un botón de cierre de sesión. Si las credenciales no coinciden, se muestra el mensaje de error predeterminado del formulario de inicio de sesión.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Desactivando el botón de inicio de sesión
Por defecto, el formulario de inicio de sesión desactiva inmediatamente el botón `Signin` una vez que el componente valida que las entradas de inicio de sesión son correctas, para evitar múltiples envíos. Puedes volver a habilitar el botón `Signin` usando el método `setEnabled(true)`.
:::

:::tip Permitiendo contraseñas vacías
En ciertos escenarios, se pueden permitir contraseñas vacías, permitiendo a los usuarios iniciar sesión solo con un nombre de usuario. El cuadro de diálogo de inicio de sesión se puede configurar para aceptar contraseñas vacías estableciendo `setEmptyPassword(true)`.
:::

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente de inicio de sesión son completamente personalizables utilizando la clase `LoginI18n`. Esta flexibilidad te permite adaptar la interfaz de inicio de sesión para satisfacer requisitos de localización específicos o preferencias de personalización.

La demostración a continuación ilustra cómo proporcionar una traducción al alemán para el cuadro de diálogo de inicio de sesión, asegurando que todos los elementos de la interfaz se adapten al idioma alemán para mejorar la experiencia del usuario para los hablantes de alemán.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Campos personalizados {#custom-fields}

El componente de inicio de sesión incluye varios espacios, que permiten agregar campos extra si es necesario. Esta característica proporciona más control sobre la información requerida para una autenticación exitosa.

En el ejemplo a continuación, se añade un campo de ID de cliente al formulario de inicio de sesión. Los usuarios deben proporcionar un ID válido para completar la autenticación, mejorando la seguridad y asegurando que el acceso se conceda solo después de verificar todas las credenciales requeridas.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Carga de envío
Ten en cuenta que el componente de inicio de sesión no reconoce ni incluye automáticamente los campos adicionales añadidos al formulario en su carga de envío. Esto significa que los desarrolladores deben recuperar explícitamente el valor de cualquier campo adicional desde el lado del cliente y manejarlo de acuerdo con los requisitos de la aplicación para completar el proceso de autenticación.
:::

## Botón de cancelar {#cancel-button}

En ciertos escenarios, puede ser deseable agregar un botón de cancelar junto al botón `Signin`. Esta característica es útil particularmente cuando un usuario intenta acceder a un área restringida de la aplicación y necesita una opción para cancelar la acción y regresar a su ubicación anterior. El formulario de inicio de sesión incluye un botón de cancelar por defecto, pero está oculto de la vista.

Para hacer visible el botón de cancelar, debes proporcionar una etiqueta para él; una vez etiquetado, aparecerá en la pantalla. También puedes escuchar eventos de cancelación para responder apropiadamente a las acciones del usuario, asegurando una experiencia de navegación fluida y amigable en la aplicación.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Ocultando elementos
Para ocultar un elemento de la pantalla de inicio de sesión, simplemente establece su etiqueta en una cadena vacía. Este enfoque es particularmente útil para eliminar temporalmente componentes de la interfaz sin alterar permanentemente la base de código.
:::

## Administradores de contraseñas {#password-managers}

El componente de inicio de sesión está diseñado para ser compatible con administradores de contraseñas basados en el navegador, mejorando la experiencia del usuario al simplificar el proceso de inicio de sesión. Para los usuarios de navegadores basados en Chromium, el componente se integra perfectamente con la API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential). Esta integración permite varias características convenientes:

- **Autocompletar**: El navegador puede completar automáticamente los campos de nombre de usuario y contraseña si el usuario ha guardado credenciales para el sitio.
- **Gestión de credenciales**: Después de iniciar sesión, el navegador puede solicitar al usuario que guarde nuevas credenciales, haciendo que futuros inicios de sesión sean más rápidos y fáciles.
- **Selección de credenciales**: Si se han guardado múltiples credenciales, el navegador puede ofrecer al usuario la opción de seleccionar de uno de los conjuntos guardados.

## Estilizando {#styling}

<TableBuilder name="Login" />
