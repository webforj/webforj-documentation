---
title: Installable Apps
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 60a6bf616536e9c202af684e9a505af6
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

La anotación `@AppProfile` en webforJ te permite hacer que tu aplicación sea instalable en plataformas soportadas. Las aplicaciones web instalables se integran con el sistema operativo del dispositivo. Cuando están instaladas, aparecen en la pantalla de inicio o en el menú de aplicaciones, similar a las aplicaciones nativas. Para lograr esto, se debe proporcionar cierta metadata como el nombre, la descripción y los íconos. Estos detalles ayudan al sistema operativo a identificar y mostrar la aplicación.

:::info Requerimiento de origen seguro
Para que una aplicación sea instalable, debe ser servida desde un origen seguro, como `https`. Los navegadores rechazan los intentos de instalación en orígenes inseguros. Sin embargo, esta regla no se aplica cuando se sirve la aplicación localmente desde `localhost` durante el desarrollo.

<!-- vale off -->
Para más detalles sobre los contextos seguros y su importancia, consulta la [documentación de Contextos Seguros en MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Soporte de navegador {#browser-support}

El soporte para instalar una aplicación web varía según el navegador y la plataforma.

### Escritorio {#browser-support-desktop}

- **Navegadores Chromium** (Chrome, Edge, Opera, Brave y otros) instalan cualquier aplicación que tenga un archivo de manifiesto en todos los sistemas operativos de escritorio soportados.
- **Safari** soporta **Archivo → Agregar al Dock** en macOS Sonoma (Safari 17) y versiones posteriores. El flujo funciona para cualquier aplicación web, con o sin un archivo de manifiesto.
- **Firefox** no soporta la instalación de aplicaciones web desde un archivo de manifiesto en el escritorio.

### Móvil {#browser-support-mobile}

- En **Android**, Chrome, Edge, Firefox, Opera e Internet de Samsung soportan la instalación de aplicaciones web.
- En **iOS 16.3 y versiones anteriores**, las aplicaciones web solo pueden ser instaladas desde Safari (**Compartir → Agregar a la Pantalla de Inicio**).
- En **iOS 16.4 y versiones posteriores**, las aplicaciones web pueden ser instaladas desde el menú Compartir en Safari, Chrome, Edge, Firefox y Orion.

## Anotación `@AppProfile` {#appprofile-annotation}

La anotación `@AppProfile` se aplica a la clase principal de la aplicación y requiere una configuración mínima. Como mínimo, debes proporcionar:

- **name**: El nombre completo de la aplicación.
- **shortName**: Una versión concisa del nombre para usar en contextos de espacio limitado.

Propiedades adicionales opcionales permiten personalizar la apariencia y el comportamiento de la aplicación.

Cuando la anotación `@AppProfile` está presente, webforJ:

- Configura automáticamente las metaetiquetas necesarias.
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

## Propiedades `@AppProfile` {#appprofile-properties}

La siguiente tabla enumera todas las propiedades soportadas por la anotación `@AppProfile`:

| **Propiedad**      | **Tipo**                                           | **Descripción**                                                                                           | **Valor por defecto**     |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | El nombre completo de la aplicación, mostrado en los menús de aplicaciones y cuadros de diálogo de instalación.                                | **Obligatorio**         |
| `shortName`       | `String`                                           | Una versión corta del nombre, utilizada en contextos de espacio limitado. No debe exceder los 12 caracteres.         | **Obligatorio**         |
| `description`     | `String`                                           | Una breve descripción de la aplicación, mostrada durante la instalación y en la configuración de la aplicación.                        | `""`                  |
| `themeColor`      | `String`                                           | El color del tema para la aplicación, aplicado a la interfaz del navegador cuando la aplicación se lanza.                          | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | El color de fondo inicial para la aplicación durante la carga.                                                  | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | La URL que se abre cuando la aplicación se lanza.                                                                 | `"."`                 |
| `display`         | `Display` **_Enum_**                               | El modo de visualización de la aplicación (por ejemplo, `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | La orientación predeterminada de la aplicación (por ejemplo, `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Un array de íconos que representan la aplicación en diferentes resoluciones.                                          | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Especifica un ícono por defecto para la aplicación. Genera automáticamente rutas de íconos en múltiples tamaños si está configurado. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Un array de capturas de pantalla para la aplicación, utilizadas en cuadros de diálogo de instalación.                                        | `[]`                  |
| `categories`      | `String[]`                                         | Categorías para clasificar la aplicación (por ejemplo, `Finanzas`, `Compras`).                                             | `[]`                  |

### Propiedades `@AppProfile.Icon` {#appprofileicon-properties}

Los íconos definen la representación visual de tu aplicación en menús y pantallas de inicio. La anotación `@AppProfile.Icon` soporta las siguientes propiedades:

| **Propiedad**                                                                     | **Tipo** | **Descripción**                                                                                        | **Valor por defecto** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | La ruta al ícono. Esto puede ser una URL absoluta o una ruta `ws://`.                                     | **Obligatorio**     |
| `sizes`                                                                          | `String` | Una cadena que especifica uno o más tamaños de la imagen en el formato `AnchoxAlto` (por ejemplo, `512x512`). | **Obligatorio**     |
| `type`                                                                           | `String` | El tipo de medio del ícono (por ejemplo, `image/png`, `image/jpeg`). Si no se proporciona, se detectará  | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | El propósito del ícono (por ejemplo, `any`, `maskable`, `monochrome`).                                       | `""`              |

### Ejemplo {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Propiedades `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

La anotación `DefaultIcon` simplifica la configuración de los íconos de la aplicación generando múltiples variantes de tamaño a partir de un ícono base. Produce íconos en las resoluciones que los dispositivos suelen solicitar.

| **Propiedad** | **Tipo** | **Descripción**                                                                 | **Valor por defecto** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | La ruta al archivo de ícono base. Esto puede ser una URL absoluta o una ruta `ws://`. | **Obligatorio**     |
| `sizes`      | `int[]`  | Un array de tamaños a generar, especificados como enteros (por ejemplo, `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Requisitos del archivo de ícono
Esta configuración no genera automáticamente los archivos de ícono reales para la aplicación. En cambio, utiliza la anotación `@AppProfile.DefaultIcon` para generar entradas [`@AppProfile.Icon`](#appprofileicon-properties) correspondientes para cada tamaño especificado.

#### Si usas el [protocolo del servidor web](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Debes proporcionar un archivo de ícono base `icon.png` en la carpeta `static/icons`.
- Se espera que incluyas variaciones adicionales del ícono nombradas `icon-144x144.png`, `icon-192x192.png` e `icon-512x512.png`.
- Estos tamaños específicos cubren las resoluciones que los dispositivos suelen solicitar.

#### Si usas el [protocolo de íconos](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Se espera que proporciones un archivo de ícono base `icon.png` en la carpeta `/icons`.
- El endpoint `icons` proporciona dinámicamente diferentes tamaños de íconos a pedido cuando son solicitados.
:::

### Propiedades `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Las capturas de pantalla proporcionan una vista previa de la aplicación en diálogos de instalación o tiendas de aplicaciones. La anotación `@AppProfile.Screenshot` soporta las siguientes propiedades:

| **Propiedad**                                                                                  | **Tipo** | **Descripción**                                                                                             | **Valor por defecto** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | La ruta a la captura de pantalla. Esto puede ser una URL absoluta o una ruta `ws://`.                                 | **Obligatorio**     |
| `sizes`                                                                                       | `String` | Una cadena que especifica uno o más tamaños de la imagen en el formato `AnchoxAlto` (por ejemplo, `1080x1920`).    | **Obligatorio**     |
| `type`                                                                                        | `String` | El tipo de medio de la captura de pantalla (por ejemplo, `image/png`, `image/jpeg`). Si no se proporciona, se detectará | `""`              |
| `label`                                                                                       | `String` | Una etiqueta descriptiva para la captura de pantalla.                                                                     | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | El factor de forma de la captura de pantalla (por ejemplo, `narrow`, `wide`).                                                 | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | La plataforma para la que se destina la captura de pantalla (por ejemplo, `ios`, `android`).                                 | `""`              |

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
