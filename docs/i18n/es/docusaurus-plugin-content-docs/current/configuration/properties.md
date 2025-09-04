---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e2cc183e859c85e0d1f4a24c196b8a55
---
# Configuración de propiedades de webforJ

Para implementar y ejecutar con éxito una aplicación webforJ, se requieren un par de archivos de configuración clave: `webforJ.conf` y `web.xml`. Cada uno de estos archivos controla diferentes aspectos del comportamiento de la aplicación, desde puntos de entrada y configuraciones de depuración hasta asignaciones de servlets.

## Configuración de `webforj.conf` {#configuring-webforjconf}

El archivo `webforJ.conf` es un archivo de configuración central en webforJ, que especifica configuraciones de la aplicación como puntos de entrada, modo de depuración e interacción cliente-servidor. El archivo está escrito en [formato HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) y debe estar ubicado en el directorio `resources`.

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

| Propiedad                        | Explicación                                                                                                                                                                            | Valor predeterminado |
|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| **`webforj.entry`**              | Define el punto de entrada de la aplicación especificando el nombre completamente cualificado de la clase que extiende `webforj.App`. Si no se define un punto de entrada, webforJ escaneará automáticamente el classpath en busca de clases que extiendan `webforj.App`. Si se encuentran múltiples clases, ocurrirá un error. Cuando un paquete incluye más de un punto de entrada potencial, es necesario establecer esto explícitamente para evitar ambigüedades, o alternativamente, se puede utilizar la anotación `AppEntry` para especificar el punto de entrada en tiempo de ejecución. | `null`               |
| **`webforj.debug`**              | Activa el modo de depuración. En modo de depuración, webforJ imprimirá información adicional en la consola y mostrará todas las excepciones en el navegador. El modo de depuración está deshabilitado por defecto. | `null`               |
| **`webforj.reloadOnServerError`** | Al utilizar la implementación de hot redeploy, se intercambiará todo el archivo WAR. Si el cliente intenta enviar una solicitud al servidor mientras se reinicia, ocurrirá un error. Esta configuración permite que el cliente intente recargar la página si el servidor está temporalmente no disponible, con la esperanza de que vuelva a estar en línea pronto. Esto solo se aplica a entornos de desarrollo y solo maneja errores específicos de la reimplementación en caliente, no otros tipos de errores. | `on`                 |
| **`webforj.clientHeartbeatRate`** | Establece el intervalo en el que el cliente envía un ping al servidor para ver si aún está vivo. Esto ayuda a mantener la comunicación. Para desarrollo, establece esto en un intervalo más corto, por ejemplo `8s`, para detectar rápidamente la disponibilidad del servidor. No establezcas esto en menos de 50 segundos en producción para evitar solicitudes excesivas. | `50s`                |
| **`webforj.components`**         | Cuando se especifica, la ruta base determina de dónde se cargan los componentes DWC. Por defecto, los componentes se cargan desde el servidor que aloja la aplicación. Sin embargo, establecer una ruta base personalizada permite que los componentes se carguen desde un servidor o CDN alternativo. Por ejemplo, para cargar componentes desde jsdelivr.com, establece la ruta base en: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Es importante que los componentes cargados sean compatibles con la versión del marco webforJ en uso; de lo contrario, la aplicación puede no funcionar como se esperaba. Esta configuración se ignora al usar una instalación estándar de BBj sin el motor. Para una instalación estándar de BBj, la configuración se puede gestionar con el `!COMPONENTS` STBL. | `null`               |
| **`webforj.locale`**             | Define la localización de la aplicación, determinando el idioma, configuraciones regionales y formatos para fechas, horas y números. | `null`               |
| **`webforj.assetsDir`**          | Especifica el nombre de ruta utilizado para servir archivos estáticos, mientras que el nombre de la carpeta física sigue siendo `static`. Esta configuración es útil si la ruta predeterminada `static` entra en conflicto con una ruta definida en tu aplicación, permitiéndote cambiar el nombre de la ruta sin renombrar la carpeta misma. | `static`            |
| **`webforj.stringTable`**        | Un mapa de pares clave-valor utilizados para almacenar cadenas para su uso en la aplicación. Útil para almacenar mensajes o etiquetas de la aplicación. Más información sobre `StringTable` se puede encontrar [aquí](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                 |
| **`webforj.fileUpload.accept`**  | Especifica los tipos de archivos permitidos para las cargas de archivos. Por defecto, se permiten todos los tipos de archivos. Los formatos admitidos incluyen tipos MIME como `image/*`, `application/pdf`, `text/plain`, o extensiones de archivo como `*.txt`. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `[]`                 |
| **`webforj.fileUpload.maxSize`** | Define el tamaño máximo de archivo permitido para las cargas de archivos, en bytes. Por defecto, no hay límite. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `null`               |
| **`license.cfg`**                | Configura el directorio para la configuración de la licencia. Por defecto, es el mismo que el directorio de configuración de webforJ, pero esto se puede personalizar si es necesario. | `"."`                |

## Configuración de `web.xml` {#configuring-webxml}

El archivo web.xml es un archivo de configuración esencial para aplicaciones web Java, y en webforJ, define configuraciones importantes como la configuración del servlet, patrones de URL y páginas de bienvenida. Este archivo debe estar ubicado en el directorio `WEB-INF` de la estructura de implementación de tu proyecto.

| Configuración                           | Explicación                                                                                                                                                                                   | Valor predeterminado            |
|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------|
| **`<display-name>`**                   | Establece el nombre de visualización de la aplicación web, derivado típicamente del nombre del proyecto. Este nombre aparece en las consolas de gestión de los servidores de aplicaciones.      | `${project.name}`                |
| **`<servlet>` y `<servlet-mapping>`** | Define el `WebforjServlet`, el servlet principal para manejar las solicitudes de webforJ. Este servlet está mapeado a todas las URLs (`/*`), convirtiéndose en el punto de entrada principal para las solicitudes web. | `WebforjServlet`                 |
| **`<load-on-startup>`**                | Especifica que `WebforjServlet` debe cargarse cuando la aplicación se inicia. Establecer esto en `1` asegura que el servlet se cargue inmediatamente, lo que mejora el manejo inicial de solicitudes. | `1`                              |
