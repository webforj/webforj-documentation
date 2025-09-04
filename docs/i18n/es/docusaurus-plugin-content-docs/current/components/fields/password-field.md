---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: ca055ca343a756152533eb1ab3ec5c8c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

El componente `PasswordField` permite a los usuarios ingresar una contraseña de forma segura. Se muestra como un editor de texto de una sola línea donde el texto ingresado está oculto, típicamente reemplazado por símbolos como asteriscos (”*”) o puntos (”•”). El símbolo exacto puede variar según el navegador y el sistema operativo.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Valor del campo {#field-value}

El componente `PasswordField` almacena y recupera su valor como un `String` plano, similar a un `TextField`, pero con un renderizado visual oculto para esconder los caracteres de la vista.

Puedes recuperar el valor actual usando:

```java
passwordField.getValue();
```

:::warning datos sensibles
Aunque el campo enmascara visualmente el contenido, el valor devuelto por `getValue()` sigue siendo un string plano. Ten cuidado con esto al manejar datos sensibles y encripta o transforma la información antes de almacenarla.
:::

Para establecer o restablecer el valor programáticamente:

```java
passwordField.setValue("MySecret123!");
```

Si no se ha ingresado ningún valor por el usuario y no se establece un valor por defecto, el campo devolverá una cadena vacía (`""`).

Este comportamiento imita al del `<input type="password">` nativo de HTML, donde la propiedad `value` contiene la entrada actual.

## Usos {#usages}

El `PasswordField` se utiliza mejor en escenarios donde capturar o manejar información sensible, como contraseñas u otros datos confidenciales, es esencial para tu aplicación. Aquí hay algunos ejemplos de cuándo usar el `PasswordField`:

1. **Autenticación y Registro de Usuarios**: Los campos de contraseña son cruciales en aplicaciones que involucran procesos de autenticación o registro de usuarios, donde se requiere una entrada de contraseña segura.

2. **Entradas de Formulario Seguras**: Al diseñar formularios que requieren la entrada de información sensible, como detalles de tarjetas de crédito o números de identificación personal (PINs), usar un `PasswordField` asegura la entrada de tales datos.

3. **Gestión de Cuentas y Configuración de Perfiles**: Los campos de contraseña son valiosos en aplicaciones que involucran la gestión de cuentas o configuraciones de perfiles, permitiendo a los usuarios cambiar o actualizar sus contraseñas de forma segura.

## Visibilidad de la contraseña {#password-visibility}

Los usuarios pueden revelar el valor del `PasswordField` haciendo clic en el ícono de revelación. Esto permite a los usuarios verificar lo que han ingresado o copiar la información a su portapapeles. Sin embargo, para entornos de alta seguridad, puedes usar `setPasswordReveal()` para eliminar el ícono de revelación y evitar que los usuarios vean el valor. Puedes verificar si un usuario puede usar el ícono de revelación para mostrar el valor con el método `isPasswordReveal()`.

## Coincidencia de patrones {#pattern-matching}

Se recomienda encarecidamente aplicar un patrón de expresión regular al `PasswordField` usando el método `setPattern()`. Esto te permite hacer cumplir reglas de caracteres y requisitos estructurales, obligando a los usuarios a crear credenciales seguras y conformes. La coincidencia de patrones es especialmente útil al hacer cumplir reglas de contraseñas fuertes, como requerir una mezcla de letras mayúsculas y minúsculas, números y símbolos.

El patrón debe seguir la sintaxis de una [expresión regular de JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), tal como lo interpreta el navegador. La bandera `u` (Unicode) se utiliza internamente para garantizar la validación a través de todos los puntos de código Unicode. No **incluyas** barras diagonales (`/`) alrededor del patrón.

En el siguiente fragmento, el patrón requiere al menos una letra minúscula, una letra mayúscula, un número y una longitud mínima de 8 caracteres.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Si falta o es inválido el patrón, no se aplicará ninguna validación.

:::tip
Usa `setLabel()` para proporcionar una etiqueta clara que describa el propósito del campo de contraseña. Para ayudar a los usuarios a entender los requisitos de la contraseña, usa `setHelperText()` para mostrar orientación o reglas directamente debajo del campo.
:::

## Longitud mínima y máxima {#minimum-and-maximum-length}

Puedes controlar la longitud permitida de la entrada de contraseña utilizando `setMinLength()` y `setMaxLength()` en el `PasswordField`.

El método `setMinLength()` define el número mínimo de caracteres que un usuario debe ingresar en el campo para pasar la validación. Este valor debe ser un entero no negativo y no debe exceder la longitud máxima si se establece una.

```java
passwordField.setMinLength(8); // Mínimo 8 caracteres
```

Si el usuario ingresa menos caracteres que el mínimo, la entrada falla la validación de restricciones. Esta validación solo se aplica cuando el valor del campo es modificado por el usuario.

El método `setMaxLength()` establece el número máximo de caracteres permitidos en el campo. El valor debe ser 0 o mayor. Si no está definido o está establecido en un valor inválido, el campo no tiene límite superior de caracteres.

```java
passwordField.setMaxLength(20); // Máximo 20 caracteres
```

Si la entrada excede el límite máximo de caracteres, el campo falla la validación de restricciones. Al igual que el mínimo, esta regla solo se aplica cuando el usuario actualiza el valor del campo.

:::tip
Usa tanto `setMinLength()` como `setMaxLength()` juntos para crear límites de entrada efectivos. Consulta la [documentación de restricciones de longitud de HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) para más referencias.
:::

## Mejores prácticas {#best-practices}

Dado que el componente `PasswordField` a menudo está asociado con información sensible, considera las siguientes mejores prácticas al usar el `PasswordField`:

- **Proporcionar Retroalimentación sobre la Fortaleza de la Contraseña**: Incorpora indicadores de fortaleza de contraseña o mecanismos de retroalimentación para ayudar a los usuarios a crear contraseñas fuertes y seguras. Evalúa factores como longitud, complejidad y una mezcla de letras mayúsculas y minúsculas, números y caracteres especiales.

- **Forzar el Almacenamiento Seguro de Contraseñas**: Nunca almacenes contraseñas en texto plano. En su lugar, implementa medidas de seguridad adecuadas para manejar y almacenar contraseñas de forma segura en tu aplicación. Usa algoritmos de encriptación estándar de la industria para contraseñas y otros datos sensibles.

- **Confirmación de Contraseña**: Incluye un campo de confirmación adicional cuando un usuario cambie o cree una contraseña. Esta medida ayuda a minimizar la probabilidad de errores tipográficos y garantiza que los usuarios ingresen correctamente su contraseña deseada.

- **Permitir el Restablecimiento de Contraseña**: Si tu aplicación involucra cuentas de usuario, proporciona una opción para que los usuarios restablezcan su contraseña. Esto podría ser en forma de una función de "Olvidé mi Contraseña" que inicie un proceso de recuperación de contraseña.

- **Accesibilidad**: Configura el `PasswordField` con la accesibilidad en mente, para que cumpla con los estándares de accesibilidad, como proporcionar etiquetas adecuadas y compatibilidad con tecnologías de asistencia.
