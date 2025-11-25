---
title: Aplicaciones instalables
sidebar_position: 10
_i18n_hash: 76c2d63a5d5ea13f5ce55431108e6a3d
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

La anotación `@AppProfile` en webforJ te permite hacer que tu aplicación sea instalable en plataformas compatibles. 
Las aplicaciones web instalables se integran sin problemas con el sistema operativo del dispositivo. 
Cuando están instaladas, aparecen en la pantalla de inicio o en el menú de aplicaciones, similar a las aplicaciones nativas. 
Para lograr esto, se debe proporcionar cierta metadata como nombre, descripción e íconos. 
Estos detalles ayudan al sistema operativo a identificar y mostrar la aplicación.

:::info Requisito de Origen Seguro
Para que una aplicación sea instalable, debe ser servida desde un origen seguro, como `https`. 
Este requisito asegura que la aplicación cumpla con los estándares de seguridad necesarios para la instalación. Sin embargo, esta regla no se aplica al servir la aplicación localmente desde `localhost` durante el desarrollo.

<!-- vale off -->
Para más detalles sobre contextos seguros y su importancia, consulta la [documentación de Contextos Seguros de MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Anotación `@AppProfile` {#appprofile-annotation}

La anotación `@AppProfile` se aplica a la clase principal de la aplicación y requiere una configuración mínima. Como mínimo, necesitas proporcionar:

- **name**: El nombre completo de la aplicación.
- **shortName**: Una versión concisa del nombre para usar en contextos de espacio limitado.

Las propiedades adicionales opcionales permiten personalizar la apariencia y el comportamiento de la aplicación.

Cuando la anotación `@AppProfile` está presente, webforJ:

- Configura automáticamente las etiquetas meta necesarias.
- Genera un [Manifiesto de Aplicación Web](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Sirve recursos relacionados como íconos y capturas de pantalla.

### Ejemplo: Aplicando `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank es una aplicación bancaria simple construida con webforJ",
  screenshots = {
    @AppProfile.Screenshot(
      src = "ws://img/screenshots/s1.jpg",
      sizes = "1080x1920"
    )
  }
)
public class Application extends App {
}
```

## Propiedades de `@AppProfile` {#appprofile-properties}

La siguiente tabla enumera todas las propiedades soportadas por la anotación `@AppProfile`:

| **Propiedad**      | **Tipo**                                           | **Descripción**                                                                                           | **Valor Por Defecto**     |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | El nombre completo de la aplicación, mostrado en menús de aplicaciones y cuadros de diálogo de instalación.                                | **Obligatorio**         |
| `shortName`       | `String`                                           | Una versión corta del nombre, utilizada en contextos con espacio limitado. No debe exceder 12 caracteres.         | **Obligatorio**         |
| `description`     | `String`                                           | Una breve descripción de la aplicación, mostrada durante la instalación y en la configuración de la aplicación.                        | `""`                  |
| `themeColor`      | `String`                                           | El color del tema para la aplicación, aplicado a la interfaz del navegador cuando se lanza la aplicación.                          | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | El color de fondo inicial para la aplicación durante la carga.                                                  | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | La URL que se abrirá cuando se lance la aplicación.                                                                 | `"."`                 |
| `display`         | `Display` **_Enum_**                               | El modo de visualización de la aplicación (p. ej., `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | La orientación predeterminada de la aplicación (p. ej., `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Un array de íconos que representan la aplicación en diferentes resoluciones.                                          | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Especifica un ícono predeterminado para la aplicación. Genera automáticamente rutas de íconos en múltiples tamaños si está configurado. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Un array de capturas de pantalla para la aplicación, utilizadas en cuadros de diálogo de instalación.                                        | `[]`                  |
| `categories`      | `String[]`                                         | Categorías para clasificar la aplicación (p. ej., `Finanzas`, `Compras`).                                             | `[]`                  |

### Propiedades de `@AppProfile.Icon` {#appprofileicon-properties}

Los íconos definen la representación visual de tu aplicación en los menús y pantallas de inicio. La anotación `@AppProfile.Icon` soporta las siguientes propiedades:

| **Propiedad**                                                                     | **Tipo** | **Descripción**                                                                                        | **Valor Por Defecto** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | La ruta al ícono. Esto puede ser una URL absoluta, o una ruta `ws://`.                                     | **Obligatorio**     |
| `sizes`                                                                          | `String` | Una cadena que especifica uno o más tamaños de la imagen en el formato `AnchoxAlto` (p. ej., `512x512`). | **Obligatorio**     |
| `type`                                                                           | `String` | El tipo MIME del ícono (p. ej., `image/png`, `image/jpeg`). Si no se proporciona, se detectará.  | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | El propósito del ícono (p. ej., `any`, `maskable`, `monochrome`).                                       | `""`              |

### Ejemplo {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Propiedades de `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

La anotación `DefaultIcon` simplifica la configuración de los íconos de la aplicación generando múltiples variantes de tamaño a partir de un ícono base.
Esto es particularmente útil para asegurar la compatibilidad en dispositivos con distintas resoluciones.

| **Propiedad** | **Tipo** | **Descripción**                                                                 | **Valor Por Defecto** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | La ruta al archivo del ícono base. Esto puede ser una URL absoluta, o una ruta `ws://`. | **Obligatorio**     |
| `sizes`      | `int[]`  | Un array de tamaños a generar, especificados como enteros (p. ej., `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Requisitos del Archivo del Ícono
Esta configuración no genera automáticamente los archivos de ícono reales para la aplicación. En su lugar, utiliza la anotación `@AppProfile.DefaultIcon` para generar las entradas correspondientes de [`@AppProfile.Icon`](#appprofileicon-properties) para cada tamaño especificado.

#### Si usas el [protocolo de servidor web](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Debes proporcionar un archivo base `icon.png` en la carpeta `static/icons`.
- Se espera que incluya variaciones adicionales de íconos nombradas `icon-144x144.png`, `icon-192x192.png`, y `icon-512x512.png`.
- Estos tamaños específicos aseguran la compatibilidad con varios dispositivos y resoluciones.

#### Si usas el [protocolo de íconos](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Se espera que proporciones un archivo base `icon.png` en la carpeta `/icons`.
- El endpoint `icons` proporciona dinámicamente diferentes tamaños de íconos bajo demanda cuando se solicitan.
:::

### Propiedades de `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Las capturas de pantalla proporcionan una vista previa de la aplicación en cuadros de diálogo de instalación o tiendas de aplicaciones. La anotación `@AppProfile.Screenshot` soporta las siguientes propiedades:

| **Propiedad**                                                                                  | **Tipo** | **Descripción**                                                                                             | **Valor Por Defecto** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | La ruta a la captura de pantalla. Esto puede ser una URL absoluta, o una ruta `ws://`.                                 | **Obligatorio**     |
| `sizes`                                                                                       | `String` | Una cadena que especifica uno o más tamaños de la imagen en el formato `AnchoxAlto` (p. ej., `1080x1920`).    | **Obligatorio**     |
| `type`                                                                                        | `String` | El tipo MIME de la captura de pantalla (p. ej., `image/png`, `image/jpeg`). Si no se proporciona, se detectará | `""`              |
| `label`                                                                                       | `String` | Una etiqueta descriptiva para la captura de pantalla.                                                                     | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | El factor de forma de la captura de pantalla (p. ej., `narrow`, `wide`).                                                 | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | La plataforma para la cual se destina la captura de pantalla (p. ej., `ios`, `android`).                                 | `""`              |

### Ejemplo {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<div class="videos-container">
  <video controls>
    <source src="/video/install-android.mp4" type="video/mp4"/>
  </video>
</div>
