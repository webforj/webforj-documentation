---
title: Property Configuration
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: fe000276baa9ac8b0773e5c4372d8463
---
# Configurando propiedades de webforJ

Para implementar y ejecutar con éxito una aplicación webforJ, se requieren un par de archivos de configuración clave: `webforj.conf` y `web.xml`. Cada uno de estos archivos controla diferentes aspectos del comportamiento de la aplicación, desde puntos de entrada y configuraciones de depuración hasta mapeos de servlets.

## Configurando `webforj.conf` {#configuring-webforjconf}

El archivo `webforj.conf` es un archivo de configuración central en webforJ, que especifica configuraciones de la aplicación como puntos de entrada, modo de depuración e interacción cliente-servidor. El archivo está en [formato HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) y debe ubicarse en el directorio `resources`.

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

| Propiedad                             | Tipo    | Explicación                                                       | Predeterminado                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | Cadena  | Encabezado Cache-Control para recursos estáticos.                        | `null` |
| **`webforj.assetsDir`**              | Cadena  | El nombre de ruta utilizado para servir archivos estáticos, mientras que el nombre de carpeta real sigue siendo `static`. Esta configuración es útil si la ruta `static` predeterminada entra en conflicto con una ruta definida en tu aplicación, permitiéndote cambiar el nombre de la ruta sin renombrar la carpeta misma.       | `null`               |
| **`webforj.assetsExt`**              | Cadena  | Extensión de archivo predeterminada para archivos estáticos. | `null` |
| **`webforj.assetsIndex`**            | Cadena  | Archivo predeterminado servido para solicitudes de directorio (por ejemplo, index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | Cadena  | El intervalo al que el cliente verifica si el servidor sigue activo. Para desarrollo, establece esto en un intervalo más corto, por ejemplo, `8s`, para detectar rápidamente la disponibilidad del servidor. Establecer en 50 segundos o más en producción para evitar solicitudes excesivas. | `50s`           |
| **`webforj.components`**             | Cadena  | Cuando se especifica, la ruta base determina de dónde se cargan los componentes DWC. Por defecto, los componentes se cargan desde el servidor que aloja la aplicación. Sin embargo, establecer una ruta base personalizada permite que los componentes se carguen desde un servidor alternativo o CDN. Por ejemplo, para cargar componentes desde jsdelivr.com, establece la ruta base en: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Es importante que los componentes cargados sean compatibles con la versión del marco webforJ en uso; de lo contrario, la aplicación puede no funcionar como se espera. Esta configuración se ignora al usar una instalación estándar de BBj sin el motor. Para una instalación estándar de BBj, la configuración se puede gestionar con el `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Booleano | Activa el modo de depuración. En modo de depuración, webforJ imprimirá información adicional en la consola y mostrará todas las excepciones en el navegador. El modo de depuración está desactivado por defecto. | `null`          |
| **`webforj.entry`**                  | Cadena  | Define el punto de entrada de la aplicación especificando el nombre completamente calificado de la clase que extiende `webforj.App`. Si no se define un punto de entrada, webforJ buscará automáticamente en el classpath clases que extiendan `webforj.App`. Si se encuentran múltiples clases, se producirá un error. Cuando un paquete incluye más de un potencial punto de entrada, establecer esto explícitamente es necesario para prevenir ambigüedades, o alternativamente, se puede usar la anotación `AppEntry` para especificar el punto de entrada en tiempo de ejecución. | `null`          |
| **`webforj.fileUpload.accept`**      | Lista    | Los tipos de archivos permitidos para cargas de archivos. Por defecto, todos los tipos de archivos están permitidos. Los formatos admitidos incluyen tipos MIME como `image/*`, `application/pdf`, `text/plain`, o extensiones de archivo como `*.txt`. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Largo    | El tamaño máximo de archivo permitido para cargas de archivos, en bytes. Por defecto, no hay límite. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | Cadena  | Punto final URL para el directorio de iconos (por defecto sirve desde `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | Cadena  | El directorio para la configuración de la licencia. Por defecto, es el mismo que el directorio de configuración de webforJ, pero esto puede personalizarse si es necesario. | `"."`  |
| **`webforj.license.startupTimeout`** | Entero | Tiempo de espera de inicio de la licencia en segundos. | `null` |
| **`webforj.locale`**                 | Cadena  | La configuración regional para la aplicación, determinando el idioma, configuraciones de región y formatos para fechas, horas y números. | `null` |
| **`webforj.quiet`**                  | Booleano | Desactiva la imagen de carga durante el inicio de la aplicación. | `false` |
| **`webforj.reloadOnServerError`**    | Booleano | **Sólo entornos de desarrollo.** En un entorno de desarrollo, recarga automáticamente la página en errores relacionados con la redeploy caliente, pero no otros tipos de error. Al utilizar la redeploy caliente, si el cliente envía una solicitud al servidor mientras se reinicia, puede ocurrir un error mientras se intercambia el archivo WAR. Porque el servidor probablemente volverá a estar en línea en breve, esta configuración permite que el cliente intente recargar automáticamente la página.  | `false` |
| **`webforj.servlets[n].name`**       | Cadena  | Nombre del servlet (usa el nombre de clase si no se especifica). | `null` |
| **`webforj.servlets[n].className`**  | Cadena | Nombre de clase completamente calificado del servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Parámetros de inicialización del servlet. | `null` |
| **`webforj.sessionTimeout`**         | Entero | Duración del tiempo de espera de la sesión en segundos. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Un mapa de pares clave-valor utilizados para almacenar cadenas para su uso en la aplicación. Útil para almacenar mensajes o etiquetas de la aplicación. Más información sobre `StringTable` se puede encontrar [aquí](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mapeos de tipos MIME personalizados para extensiones de archivos al servir archivos estáticos. Te permite anular tipos MIME predeterminados o definir tipos MIME para extensiones personalizadas. La clave del mapa es la extensión del archivo (sin el punto), y el valor es el tipo MIME. | `{}`            |

## Configurando `web.xml` {#configuring-webxml}

El archivo `web.xml` es un archivo de configuración esencial para aplicaciones web de Java, y en webforJ, define configuraciones importantes como la configuración del servlet, patrones de URL y páginas de bienvenida. Este archivo debe ubicarse en el directorio `WEB-INF` de la estructura de implementación de tu proyecto.

| Configuración                                 | Explicación                                                                                                                                                                                   | Valor Predeterminado               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Establece el nombre de visualización para la aplicación web, generalmente derivado del nombre del proyecto. Este nombre aparece en las consolas de gestión de los servidores de aplicaciones.                                                        | `${project.name}`           |
| **`<servlet>` y `<servlet-mapping>`** | Define el `WebforjServlet`, el servlet principal para manejar solicitudes de webforJ. Este servlet está mapeado a todas las URL (`/*`), convirtiéndolo en el principal punto de entrada para solicitudes web.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Especifica que `WebforjServlet` debe cargarse cuando la aplicación comienza. Establecer esto en `1` hace que el servlet se cargue inmediatamente, lo que mejora el manejo inicial de solicitudes.                | `1`                         |
