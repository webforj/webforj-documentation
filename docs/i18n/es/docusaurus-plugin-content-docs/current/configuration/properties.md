---
title: Property Configuration
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: 0f672146394b053aaa5d59a7e59841b2
---
# Configurando propiedades de webforJ

Para desplegar y ejecutar con éxito una aplicación webforJ, se requieren un par de archivos de configuración clave: `webforj.conf` y `web.xml`. Cada uno de estos archivos controla diferentes aspectos del comportamiento de la aplicación, desde puntos de entrada y configuraciones de depuración hasta mapeos de servlet.

## Configurando `webforj.conf` {#configuring-webforjconf}

El archivo `webforj.conf` es un archivo de configuración central en webforJ, que especifica configuraciones de la aplicación como puntos de entrada, modo de depuración e interacción cliente-servidor. El archivo está en [formato HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) y debe estar ubicado en el directorio `resources`.

:::tip
Si estás integrando con [Spring](../integrations/spring/overview.md), puedes establecer estas propiedades de `webforj.conf` en el archivo `application.properties`.
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

| Propiedad                             | Tipo    | Explicación                                                       | Por defecto                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cabecera Cache-Control para recursos estáticos.                        | `null` |
| **`webforj.assetsDir`**              | String  | El nombre de ruta utilizado para servir archivos estáticos, mientras que el nombre de la carpeta permanece como `static`. Esta configuración es útil si la ruta predeterminada `static` entra en conflicto con una ruta definida en tu aplicación, permitiéndote cambiar el nombre de la ruta sin renombrar la carpeta misma.       | `null`               |
| **`webforj.assetsExt`**              | String  | Extensión de archivo predeterminada para archivos estáticos. | `null` |
| **`webforj.assetsIndex`**            | String  | Archivo predeterminado servido para solicitudes de directorio (por ejemplo, index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | El intervalo en el que el cliente consulta al servidor para ver si sigue activo. Para desarrollo, establece esto en un intervalo más corto, por ejemplo `8s`, para detectar rápidamente la disponibilidad del servidor. Establecer en 50 segundos o más en producción para evitar solicitudes excesivas. | `50s`           |
| **`webforj.components`**             | String  | Cuando se especifica, la ruta base determina de dónde se cargan los componentes de DWC. Por defecto, los componentes se cargan desde el servidor que aloja la aplicación. Sin embargo, establecer una ruta base personalizada permite que los componentes se carguen desde un servidor alternativo o CDN. Por ejemplo, para cargar componentes de jsdelivr.com, establece la ruta base en: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es importante que los componentes cargados sean compatibles con la versión del marco webforJ en uso; de lo contrario, la aplicación puede no funcionar como se esperaba. Esta configuración se ignora cuando se usa una instalación estándar de BBj sin el motor. Para una instalación estándar de BBj, la configuración se puede gestionar con el `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Habilita el modo de depuración. En modo de depuración, webforJ imprimirá información adicional en la consola y mostrará todas las excepciones en el navegador. El modo de depuración está deshabilitado de forma predeterminada. | `null`          |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Solo entornos de desarrollo.** Activa [craftforJ](../craftforj/overview.md), el entorno de desarrollo que inspecciona la aplicación en ejecución, edita propiedades de componentes y escribe cambios de vuelta al código fuente de Java. Requiere que `webforj.debug` también esté habilitado. Ninguna de las propiedades es suficiente por sí sola. | `false` |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Lista | Direcciones de cliente permitidas para alcanzar craftforJ más allá de la máquina que ejecuta la aplicación. De forma predeterminada, solo un navegador en esa máquina puede acceder a él. Una entrada que termina en `*` coincide con un prefijo, y un solo `*` elimina la restricción. Consulta [seguridad de craftforJ](../craftforj/security.md). | solo loopback |
| **`webforj.devtools.craftforj.project-root`**&nbsp;<DocChip chip='since' label='26.02' /> | String | El directorio que craftforJ busca para tus fuentes, en casos donde no puede determinar eso a partir de cómo se inició la aplicación. | detectado |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Si craftforJ puede escribir cambios de propiedades y reglas de acceso de ruta en tu código fuente de Java. | `true` |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Si craftforJ puede guardar temas y estilos en la hoja de estilo de tu aplicación. | `true` |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Si el [asistente de IA craftforJ](../craftforj/ai.md) está disponible. | `true` |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Si el asistente puede escribir Java por su cuenta en lugar de solo cambiar propiedades. Cada edición aún debe compilarse y todavía necesita tu aprobación. | `true` |
| **`webforj.entry`**                  | String  | Define el punto de entrada de la aplicación especificando el nombre completamente calificado de la clase que extiende `webforj.App`. Si no se define un punto de entrada, webforJ escaneará automáticamente la classpath en busca de clases que extiendan `webforj.App`. Si se encuentran múltiples clases, ocurrirá un error. Cuando un paquete incluye más de un punto de entrada potencial, se requiere establecer esto explícitamente para evitar ambigüedad, o alternativamente, se puede usar la anotación `AppEntry` para especificar el punto de entrada en tiempo de ejecución. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lista | Lista de locales compatibles como etiquetas de idioma BCP 47 (por ejemplo, `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Cuando la detección automática está habilitada, los locales preferidos del navegador se comparan con esta lista. El primer locale de la lista se utiliza como el valor predeterminado de respaldo. Consulta [Traducción](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Cuando es `true`, el locale de la aplicación se establece automáticamente a partir del idioma preferido del navegador al inicio. El locale se resuelve comparando los locales preferidos del navegador con la lista de `supported-locales`. Cuando es `false` o cuando `supported-locales` está vacío, la aplicación usa `webforj.locale`. Consulta [Traducción](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Lista    | Los tipos de archivo permitidos para cargas de archivos. De forma predeterminada, se permiten todos los tipos de archivo. Los formatos admitidos incluyen tipos MIME como `image/*`, `application/pdf`, `text/plain`, o extensiones de archivo como `*.txt`. Cuando se utiliza una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | El tamaño máximo de archivo permitido para cargas de archivos, en bytes. De forma predeterminada, no hay límite. Cuando se utiliza una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | Punto final URL para el directorio de iconos (el predeterminado se sirve desde `resources/icons/`). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Cuando es `true`, un valor envuelto en `<html>` renderiza su contenido como HTML. Cuando es `false`, el mismo valor se muestra literalmente. | `true` |
| **`webforj.license.cfg`**            | String  | El directorio para la configuración de la licencia. De forma predeterminada, es el mismo que el directorio de configuración de webforJ, pero esto se puede personalizar si es necesario. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Tiempo de espera de inicio de la licencia en segundos. | `null` |
| **`webforj.locale`**                 | String  | El locale para la aplicación, determinando el idioma, configuraciones de región y formatos para fechas, horas y números. | `null` |
| **`webforj.quiet`**                  | Boolean | Desactiva la imagen de carga durante el inicio de la aplicación. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Solo entornos de desarrollo.** En un entorno de desarrollo, recarga automáticamente la página en errores relacionados con el redepliegue en caliente, pero no en otros tipos de error. Al usar el redepliegue en caliente, si el cliente envía una solicitud al servidor mientras se reinicia, puede ocurrir un error mientras se intercambia el archivo WAR. Debido a que el servidor probablemente volverá a estar en línea en breve, esta configuración permite que el cliente intente recargar automáticamente la página.  | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | La solicitud más grande que la aplicación aceptará, en bytes, como medida de seguridad contra solicitudes de gran tamaño destinadas a agotar la memoria del servidor. Establecer en `0` para desactivar el límite. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Cuántas nuevas sesiones de aplicación comenzará la aplicación cada minuto, como medida de seguridad contra la creación rápida de sesiones destinadas a agotar los recursos del servidor. Establecer en `0` para desactivar la limitación de tasa. | `0` |
| **`webforj.servlets[n].name`**       | String  | Nombre del servlet (utiliza el nombre de clase si no se especifica). | `null` |
| **`webforj.servlets[n].className`**  | String | Nombre completo de la clase del servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Parámetros de inicialización del servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duración de la sesión en segundos. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Un mapa de pares clave-valor que se utiliza para almacenar cadenas para su uso en la aplicación. Útil para almacenar mensajes o etiquetas de la aplicación. Más información sobre `StringTable` se puede encontrar [aquí](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mapeos personalizados de tipo MIME para extensiones de archivo al servir archivos estáticos. Te permite anular los tipos MIME predeterminados o definir tipos MIME para extensiones personalizadas. La clave del mapa es la extensión de archivo (sin el punto), y el valor es el tipo MIME. | `{}`            |

## Configurando `web.xml` {#configuring-webxml}

El archivo `web.xml` es un archivo de configuración esencial para aplicaciones web de Java, y en webforJ, define configuraciones importantes como la configuración del servlet, patrones de URL y páginas de bienvenida. Este archivo debe estar ubicado en el directorio `WEB-INF` de la estructura de despliegue de tu proyecto.

| Configuración                                 | Explicación                                                                                                                                                                                   | Valor predeterminado               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Establece el nombre de visualización para la aplicación web, derivado típicamente del nombre del proyecto. Este nombre aparece en las consolas de gestión de los servidores de aplicaciones.                                                        | `${project.name}`           |
| **`<servlet>` y `<servlet-mapping>`** | Define el `WebforjServlet`, el servlet central para manejar las solicitudes de webforJ. Este servlet está mapeado a todas las URL (`/*`), convirtiéndolo en el punto de entrada principal para las solicitudes web.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Especifica que `WebforjServlet` debe cargarse cuando la aplicación se inicia. Establecer esto en `1` hace que el servlet se cargue inmediatamente, lo que mejora el manejo de la solicitud inicial.                | `1`                         |
