---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
_i18n_hash: 2eb59302da44bcdd27d6366419bd78ad
---
# Configuración de propiedades de webforJ

Para implementar y ejecutar con éxito una aplicación webforJ, se requieren un par de archivos de configuración clave: `webforj.conf` y `web.xml`. Cada uno de estos archivos controla diferentes aspectos del comportamiento de la aplicación, desde los puntos de entrada y las configuraciones de depuración hasta el mapeo de servlets.

## Configuración de `webforj.conf` {#configuring-webforjconf}

El archivo `webforj.conf` es un archivo de configuración central en webforJ, que especifica configuraciones de la aplicación como puntos de entrada, modo de depuración e interacción cliente-servidor. El archivo está en [formato HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) y debe estar ubicado en el directorio `resources`.

:::tip
Si estás integrándote con [Spring](../integrations/spring/overview.md), puedes establecer estas propiedades de `webforj.conf` en el archivo `application.properties`.
:::

### Ejemplo de archivo `webforj.conf` {#example-webforjconf-file}

```Ini
# Este archivo de configuración está en formato HOCON:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Opciones de configuración {#configuration-options}

| Propiedad                             | Tipo    | Explicación                                                       | Predeterminado                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Encabezado Cache-Control para recursos estáticos.                        | `null` |
| **`webforj.assetsDir`**              | String  | El nombre de la ruta utilizada para servir archivos estáticos, mientras que el nombre real de la carpeta permanece como `static`. Esta configuración es útil si la ruta `static` por defecto entra en conflicto con una ruta definida en tu aplicación, permitiéndote cambiar el nombre de la ruta sin renombrar la carpeta misma.       | `null`               |
| **`webforj.assetsExt`**              | String  | Extensión de archivo predeterminada para archivos estáticos. | `null` |
| **`webforj.assetsIndex`**            | String  | Archivo predeterminado servido para solicitudes de directorio (por ejemplo, index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | El intervalo en el que el cliente consulta al servidor para ver si sigue vivo. Para desarrollo, establece esto en un intervalo más corto, por ejemplo `8s`, para detectar rápidamente la disponibilidad del servidor. Establecer en 50 segundos o más en producción para evitar solicitudes excesivas. | `50s`           |
| **`webforj.components`**             | String  | Cuando se especifica, la ruta base determina de dónde se cargan los componentes de DWC. Por defecto, los componentes se cargan desde el servidor que aloja la aplicación. Sin embargo, establecer una ruta base personalizada permite que los componentes se carguen desde un servidor alternativo o CDN. Por ejemplo, para cargar componentes desde jsdelivr.com, establece la ruta base en: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Es importante que los componentes cargados sean compatibles con la versión del marco webforJ que se esté utilizando; de lo contrario, la aplicación puede no funcionar como se espera. Esta configuración se ignora al utilizar una instalación estándar de BBj sin el motor. Para una instalación estándar de BBj, la configuración se puede gestionar con el `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Activa el modo de depuración. En modo de depuración, webforJ imprimirá información adicional en la consola y mostrará todas las excepciones en el navegador. El modo de depuración está deshabilitado por defecto. | `null`          |
| **`webforj.entry`**                  | String  | Define el punto de entrada de la aplicación especificando el nombre completamente calificado de la clase que extiende `webforj.App`. Si no se define un punto de entrada, webforJ escaneará automáticamente el classpath en busca de clases que extiendan `webforj.App`. Si se encuentran varias clases, ocurrirá un error. Cuando un paquete incluye más de un posible punto de entrada, es necesario establecer esto explícitamente para evitar ambigüedad, o alternativamente, se puede utilizar la anotación `AppEntry` para especificar el punto de entrada en tiempo de ejecución. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lista | Lista de locales soportados como etiquetas de idioma BCP 47 (por ejemplo, `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Cuando la detección automática está habilitada, los locales preferidos del navegador se comparan con esta lista. El primer locale de la lista se usa como la moneda por defecto. Ver [Traducción](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Cuando `true`, el locale de la aplicación se configura automáticamente desde el idioma preferido del navegador al iniciar. El locale se resuelve comparando los locales preferidos del navegador con la lista de `supported-locales`. Cuando `false` o cuando `supported-locales` está vacío, la aplicación utiliza `webforj.locale`. Ver [Traducción](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Lista    | Los tipos de archivo permitidos para cargas de archivos. Por defecto, se permiten todos los tipos de archivos. Los formatos soportados incluyen tipos MIME como `image/*`, `application/pdf`, `text/plain`, o extensiones de archivo como `*.txt`. Al utilizar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | El tamaño máximo de archivo permitido para cargas de archivos, en bytes. Por defecto, no hay límite. Al utilizar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | URL de punto final para el directorio de iconos (por defecto sirve desde `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | String  | El directorio para la configuración de la licencia. Por defecto, es el mismo que el directorio de configuración de webforJ, pero se puede personalizar si es necesario. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Tiempo de espera para el inicio de la licencia en segundos. | `null` |
| **`webforj.locale`**                 | String  | El locale para la aplicación, que determina el idioma, la configuración regional y los formatos para fechas, horas y números. | `null` |
| **`webforj.quiet`**                  | Boolean | Desactiva la imagen de carga durante el inicio de la aplicación. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Solo para entornos de desarrollo.** En un entorno de desarrollo, recarga automáticamente la página ante errores relacionados con la redeployment en caliente, pero no otros tipos de errores. Al usar redeployment en caliente, si el cliente envía una solicitud al servidor mientras se reinicia, puede ocurrir un error mientras se intercambia el archivo WAR. Como el servidor probablemente estará de vuelta en línea pronto, esta configuración permite que el cliente intente recargar automáticamente la página.  | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Solicitud más grande que la aplicación aceptará, en bytes, como una salvaguarda contra solicitudes de gran tamaño destinadas a agotar la memoria del servidor. Establecer en `0` para desactivar el límite. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Cuántas nuevas sesiones de aplicación iniciará la aplicación cada minuto, como una salvaguarda contra la creación rápida de sesiones destinada a agotar los recursos del servidor. Establecer en `0` para desactivar la limitación de tasas. | `0` |
| **`webforj.servlets[n].name`**       | String  | Nombre del servlet (usa el nombre de clase si no se especifica). | `null` |
| **`webforj.servlets[n].className`**  | String | Nombre de clase completamente calificado del servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Parámetros de inicialización del servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duración de la sesión en segundos. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Un mapa de pares clave-valor utilizado para almacenar cadenas para su uso en la aplicación. Útil para almacenar mensajes o etiquetas de la aplicación. Más información sobre `StringTable` se puede encontrar [aquí](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mapeo de tipo MIME personalizado para extensiones de archivos al servir archivos estáticos. Te permite anular tipos MIME predeterminados o definir tipos MIME para extensiones personalizadas. La clave del mapa es la extensión del archivo (sin el punto), y el valor es el tipo MIME. | `{}`            |

## Configuración de `web.xml` {#configuring-webxml}

El archivo `web.xml` es un archivo de configuración esencial para aplicaciones web de Java, y en webforJ, define configuraciones importantes como la configuración del servlet, patrones de URL y páginas de bienvenida. Este archivo debe estar ubicado en el directorio `WEB-INF` de la estructura de despliegue de tu proyecto.

| Configuración                                 | Explicación                                                                                                                                                                                   | Valor Predeterminado               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Establece el nombre de visualización para la aplicación web, normalmente derivado del nombre del proyecto. Este nombre aparece en las consolas de gestión de los servidores de aplicaciones.                                                        | `${project.name}`           |
| **`<servlet>` y `<servlet-mapping>`** | Define el `WebforjServlet`, el servlet central para manejar las solicitudes de webforJ. Este servlet está mapeado a todas las URLs (`/*`), convirtiéndose en el principal punto de entrada para las solicitudes web.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Especifica que `WebforjServlet` debe ser cargado cuando la aplicación se inicia. Establecer esto en `1` hace que el servlet se cargue inmediatamente, lo que mejora el manejo de las solicitudes iniciales.                | `1`                         |
