---
title: Property Configuration
sidebar_position: 30
_i18n_hash: dea9eb679150ca6124fb625c7d04d27c
---
# Configurando propiedades de webforJ

Para desplegar y ejecutar correctamente una aplicación webforJ, se requieren un par de archivos de configuración clave: `webforJ.conf` y `web.xml`. Cada uno de estos archivos controla diferentes aspectos del comportamiento de la aplicación, desde puntos de entrada y configuraciones de depuración hasta asignaciones de servlets.

## Configurando `webforj.conf` {#configuring-webforjconf}

El archivo `webforJ.conf` es un archivo de configuración fundamental en webforJ, especificando configuraciones de la aplicación como los puntos de entrada, el modo de depuración y la interacción cliente-servidor. El archivo está escrito en [formato HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) y debe estar ubicado en el directorio `resources`.

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

| Propiedad                       | Explicación                                                                                                                                                                            | Predeterminado   |
|--------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| **`webforj.entry`**            | Define el punto de entrada de la aplicación especificando el nombre completamente calificado de la clase que extiende `webforj.App`. Si no se define un punto de entrada, webforJ escaneará automáticamente el classpath en busca de clases que extiendan `webforj.App`. Si se encuentran múltiples clases, se producirá un error. Cuando un paquete incluye más de un punto de entrada potencial, se requiere configurarlo explícitamente para evitar ambigüedad; alternativamente, se puede usar la anotación `AppEntry` para especificar el punto de entrada en tiempo de ejecución. | `null`          |
| **`webforj.debug`**            | Habilita el modo de depuración. En modo de depuración, webforJ imprimirá información adicional en la consola y mostrará todas las excepciones en el navegador. El modo de depuración está deshabilitado por defecto. | `null`          |
| **`webforj.reloadOnServerError`** | Al usar la redeploy caliente, todo el archivo WAR será intercambiado. Si el cliente intenta enviar una solicitud al servidor mientras se reinicia, se producirá un error. Esta configuración permite que el cliente intente recargar la página si el servidor está temporalmente fuera de servicio, con la esperanza de que vuelva a estar en línea en breve. Esto solo se aplica a entornos de desarrollo y solo maneja errores específicos de la redeploy caliente, no otros tipos de errores. | `on`            |
| **`webforj.clientHeartbeatRate`** | Establece el intervalo al que el cliente envía pings al servidor para ver si sigue vivo. Esto ayuda a mantener la comunicación. Para desarrollo, configure esto a un intervalo más corto, por ejemplo `8s`, para detectar rápidamente la disponibilidad del servidor. No configure esto por debajo de 50 segundos en producción para evitar solicitudes excesivas. | `50s`           |
| **`webforj.components`**       | Cuando se especifica, la ruta base determina de dónde se cargan los componentes DWC. Por defecto, los componentes se cargan desde el servidor que aloja la aplicación. Sin embargo, establecer una ruta base personalizada permite que los componentes se carguen desde un servidor alternativo o CDN. Por ejemplo, para cargar componentes desde jsdelivr.com, establezca la ruta base en: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Es importante que los componentes cargados sean compatibles con la versión del marco webforJ en uso; de lo contrario, la aplicación puede no funcionar como se espera. Esta configuración se ignora al usar una instalación estándar de BBj sin el motor. Para una instalación estándar de BBj, la configuración se puede gestionar con el `!COMPONENTS` STBL. | `null`          |
| **`webforj.locale`**           | Define la configuración regional para la aplicación, determinando el idioma, las configuraciones regionales y los formatos para fechas, horas y números. | `null`          |
| **`webforj.assetsDir`**       | Especifica el nombre de la ruta utilizada para servir archivos estáticos, mientras que el nombre físico de la carpeta permanece como `static`. Esta configuración es útil si la ruta `static` predeterminada entra en conflicto con una ruta definida en su aplicación, lo que le permite cambiar el nombre de la ruta sin renombrar la carpeta en sí. | `static`        |
| **`webforj.stringTable`**      | Un mapa de pares clave-valor utilizado para almacenar cadenas para su uso en la aplicación. Útil para almacenar mensajes o etiquetas de la aplicación. Más información sobre `StringTable` se puede encontrar [aquí](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.fileUpload.accept`** | Especifica los tipos de archivos permitidos para cargas de archivos. Por defecto, se permiten todos los tipos de archivos. Los formatos compatibles incluyen tipos MIME como `image/*`, `application/pdf`, `text/plain`, o extensiones de archivo como `*.txt`. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`** | Define el tamaño máximo de archivo permitido para cargas de archivos, en bytes. Por defecto, no hay límite. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `null`          |
| **`license.cfg`**              | Configura el directorio para la configuración de la licencia. Por defecto, es el mismo que el directorio de configuración de webforJ, pero esto se puede personalizar si es necesario. | `"."`           |

## Configurando `web.xml` {#configuring-webxml}

El archivo web.xml es un archivo de configuración esencial para aplicaciones web Java, y en webforJ, define configuraciones importantes como la configuración de servlets, patrones de URL y páginas de bienvenida. Este archivo debe estar ubicado en el directorio `WEB-INF` de la estructura de despliegue de su proyecto.

| Configuración                          | Explicación                                                                                                                                                                                   | Valor Predeterminado          |
|---------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------|
| **`<display-name>`**                  | Establece el nombre para mostrar de la aplicación web, generalmente derivado del nombre del proyecto. Este nombre aparece en las consolas de gestión de los servidores de aplicaciones.       | `${project.name}`             |
| **`<servlet>` y `<servlet-mapping>`** | Define el `WebforjServlet`, el servlet central para manejar las solicitudes de webforJ. Este servlet está mapeado a todas las URL (`/*`), lo que lo convierte en el punto de entrada principal para las solicitudes web. | `WebforjServlet`              |
| **`<load-on-startup>`**               | Especifica que `WebforjServlet` debe ser cargado cuando la aplicación se inicia. Establecer esto en `1` asegura que el servlet se cargue inmediatamente, lo que mejora el manejo de solicitudes iniciales. | `1`                           |
