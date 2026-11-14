---
title: Aplicaciones instalables
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 9aa99cc9da864a6808d72602b6e5c572
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

La anotación `@AppProfile` de webforJ permite que tu aplicación se pueda instalar en las plataformas compatibles.
Las aplicaciones web instalables se integran con el sistema operativo del dispositivo.
Una vez instaladas, aparecen en la pantalla de inicio o en el menú de aplicaciones, igual que las aplicaciones nativas.
Para ello, es necesario proporcionar ciertos metadatos, como el nombre, la descripción y los iconos.
Estos detalles ayudan al sistema operativo a identificar y mostrar la aplicación.

:::info Requisito de origen seguro
Para que una aplicación se pueda instalar, debe servirse desde un origen seguro, como `https`.
Los navegadores rechazan los intentos de instalación desde orígenes no seguros. Sin embargo, esta regla no se aplica cuando la aplicación se sirve localmente desde `localhost` durante el desarrollo.

<!-- vale off -->
Para obtener más información sobre los contextos seguros y su importancia, consulta la [documentación de MDN sobre contextos seguros](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<VideoPlayer video="installable-apps/install-chrome" width={1908} height={1052} title="Install chrome" />

## Compatibilidad con navegadores {#browser-support}

La compatibilidad con la instalación de aplicaciones web varía según el navegador y la plataforma.

### Escritorio {#browser-support-desktop}

- **Los navegadores Chromium** (Chrome, Edge, Opera, Brave y otros) instalan cualquier aplicación que incluya un archivo de manifiesto en todos los sistemas operativos de escritorio compatibles.
- **Safari** admite **Archivo → Añadir al Dock** en macOS Sonoma (Safari 17) y versiones posteriores. Este proceso funciona con cualquier aplicación web, con o sin archivo de manifiesto.
- **Firefox** no admite la instalación de aplicaciones web desde un archivo de manifiesto en equipos de escritorio.

### Móviles {#browser-support-mobile}

- En **Android**, Chrome, Edge, Firefox, Opera y Samsung Internet admiten la instalación de aplicaciones web.
- En **iOS 16.3 y versiones anteriores**, las aplicaciones web solo se pueden instalar desde Safari (**Compartir → Añadir a pantalla de inicio**).
- En **iOS 16.4 y versiones posteriores**, las aplicaciones web se pueden instalar desde el menú Compartir de Safari, Chrome, Edge, Firefox y Orion.

## Anotación `@AppProfile` {#appprofile-annotation}

La anotación `@AppProfile` se aplica a la clase principal de la aplicación y requiere una configuración mínima. Como mínimo, debes proporcionar:

- **name**: el nombre completo de la aplicación.
- **shortName**: una versión concisa del nombre para usar en contextos con espacio limitado.

Las propiedades opcionales adicionales permiten personalizar la apariencia y el comportamiento de la aplicación.

Cuando se incluye la anotación `@AppProfile`, webforJ:

- Configura automáticamente las metaetiquetas necesarias.
- Genera un [manifiesto de aplicación web](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Sirve recursos relacionados, como iconos y capturas de pantalla.

### Ejemplo: aplicar `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank is a simple banking application built with webforJ",
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

La siguiente tabla enumera todas las propiedades admitidas por la anotación `@AppProfile`:

| **Propiedad**      | **Tipo**                                           | **Descripción**                                                                                           | **Valor predeterminado** |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | El nombre completo de la aplicación, que se muestra en los menús de aplicaciones y los cuadros de diálogo de instalación. | **Obligatorio**         |
| `shortName`       | `String`                                           | Una versión abreviada del nombre, que se utiliza en contextos con espacio limitado. No debe superar los 12 caracteres. | **Obligatorio**         |
| `description`     | `String`                                           | Una breve descripción de la aplicación, que se muestra durante la instalación y en la configuración de la aplicación. | `""`                  |
| `themeColor`      | `String`                                           | El color del tema de la aplicación, que se aplica a la interfaz del navegador cuando se inicia la aplicación. | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | El color de fondo inicial de la aplicación durante la carga.                                                  | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | La URL que se abre cuando se inicia la aplicación.                                                                 | `"."`                 |
| `display`         | `Display` **_Enum_**                               | El modo de visualización de la aplicación (p. ej., `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | La orientación predeterminada de la aplicación (p. ej., `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Una matriz de iconos que representan la aplicación en distintas resoluciones.                                          | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Especifica un icono predeterminado para la aplicación. Si se configura, genera automáticamente rutas de iconos en varios tamaños. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Una matriz de capturas de pantalla de la aplicación, que se usan en los cuadros de diálogo de instalación.                                        | `[]`                  |
| `categories`      | `String[]`                                         | Categorías para clasificar la aplicación (p. ej., `Finance`, `Shopping`).                                             | `[]`                  |

### Propiedades de `@AppProfile.Icon` {#appprofileicon-properties}

Los iconos definen la representación visual de la aplicación en los menús y las pantallas de inicio. La anotación `@AppProfile.Icon` admite las siguientes propiedades:

| **Propiedad**                                                                     | **Tipo** | **Descripción**                                                                                        | **Valor predeterminado** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | La ruta al icono. Puede ser una URL absoluta o una ruta `ws://`.                                     | **Obligatorio**     |
| `sizes`                                                                          | `String` | Una cadena que especifica uno o más tamaños de la imagen en el formato `AnchoxAlto` (p. ej., `512x512`). | **Obligatorio**     |
| `type`                                                                           | `String` | El tipo de medio del icono (p. ej., `image/png`, `image/jpeg`). Si no se proporciona, se detectará.  | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | El propósito del icono (p. ej., `any`, `maskable`, `monochrome`).                                       | `""`              |

### Ejemplo {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Propiedades de `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

La anotación `DefaultIcon` simplifica la configuración de los iconos de la aplicación al generar variantes de varios tamaños a partir de un icono base.
Genera iconos con las resoluciones que suelen solicitar los dispositivos.

| **Propiedad** | **Tipo** | **Descripción**                                                                 | **Valor predeterminado** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | La ruta al archivo del icono base. Puede ser una URL absoluta o una ruta `ws://`. | **Obligatorio**     |
| `sizes`      | `int[]`  | Una matriz de tamaños que se van a generar, especificados como números enteros (p. ej., `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Requisitos de los archivos de icono
Esta configuración no genera automáticamente los archivos de icono de la aplicación. En su lugar, utiliza la anotación `@AppProfile.DefaultIcon` para generar entradas [`@AppProfile.Icon`](#appprofileicon-properties) correspondientes a cada tamaño especificado.

#### Si usas el [protocolo webserver](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Debes proporcionar un archivo base `icon.png` en la carpeta `static/icons`.
- Se espera que incluyas variaciones adicionales del icono llamadas `icon-144x144.png`, `icon-192x192.png` y `icon-512x512.png`.
- Estos tamaños específicos cubren las resoluciones que suelen solicitar los dispositivos.

#### Si usas el [protocolo icons](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Se espera que proporciones un archivo base `icon.png` en la carpeta `/icons`.
- El endpoint `icons` proporciona dinámicamente distintos tamaños de icono cuando se solicitan.
:::

### Propiedades de `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Las capturas de pantalla ofrecen una vista previa de la aplicación en los cuadros de diálogo de instalación o en las tiendas de aplicaciones. La anotación `@AppProfile.Screenshot` admite las siguientes propiedades:

| **Propiedad**                                                                                  | **Tipo** | **Descripción**                                                                                             | **Valor predeterminado** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | La ruta a la captura de pantalla. Puede ser una URL absoluta o una ruta `ws://`.                                 | **Obligatorio**     |
| `sizes`                                                                                       | `String` | Una cadena que especifica uno o más tamaños de la imagen en el formato `AnchoxAlto` (p. ej., `1080x1920`).    | **Obligatorio**     |
| `type`                                                                                        | `String` | El tipo de medio de la captura de pantalla (p. ej., `image/png`, `image/jpeg`). Si no se proporciona, se detectará. | `""`              |
| `label`                                                                                       | `String` | Una etiqueta descriptiva para la captura de pantalla.                                                                     | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | El factor de forma de la captura de pantalla (p. ej., `narrow`, `wide`).                                                 | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | La plataforma para la que está destinada la captura de pantalla (p. ej., `ios`, `android`).                                 | `""`              |

### Ejemplo {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<VideoPlayer video="installable-apps/install-android" width={374} height={824} title="Install android" />
