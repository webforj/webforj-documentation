---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e7a922cb3f035dd19fdc282d245bdf2c
---
# Configuración de propiedades de webforJ

Para desplegar y ejecutar con éxito una aplicación webforJ, se requieren un par de archivos de configuración clave: `webforj.conf` y `web.xml`. Cada uno de estos archivos controla diferentes aspectos del comportamiento de la aplicación, desde puntos de entrada y configuraciones de depuración hasta asignaciones de servlets.

## Configuración de `webforj.conf` {#configuring-webforjconf}

El archivo `webforj.conf` es un archivo de configuración central en webforJ, que especifica configuraciones de la aplicación como puntos de entrada, modo de depuración e interacción cliente-servidor. El archivo está en [formato HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) y debería estar ubicado en el directorio `resources`.

:::tip
Si está integrando con [Spring](../integrations/spring/overview.md), puede establecer estas propiedades de `webforj.conf` en el archivo `application.properties`.
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
| **`webforj.assetsDir`**              | String  | El nombre de ruta utilizado para servir archivos estáticos, mientras que el nombre de carpeta real permanece como `static`. Esta configuración es útil si la ruta `static` predeterminada entra en conflicto con una ruta definida en su aplicación, permitiéndole cambiar el nombre de la ruta sin renombrar la carpeta misma.       | `null`               |
| **`webforj.assetsExt`**              | String  | Extensión de archivo predeterminada para archivos estáticos. | `null` |
| **`webforj.assetsIndex`**            | String  | Archivo predeterminado servido para solicitudes de directorio (p. ej., index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | El intervalo en el que el cliente envía un ping al servidor para ver si sigue activo. Para desarrollo, establezca esto en un intervalo más corto, por ejemplo `8s`, para detectar rápidamente la disponibilidad del servidor. Establezca en 50 segundos o más en producción para evitar solicitudes excesivas. | `50s`           |
| **`webforj.components`**             | String  | Cuando se especifica, la ruta base determina desde dónde se cargan los componentes de DWC. Por defecto, los componentes se cargan desde el servidor que aloja la aplicación. Sin embargo, establecer una ruta base personalizada permite cargar componentes desde un servidor alternativo o CDN. Por ejemplo, para cargar componentes desde jsdelivr.com, establezca la ruta base en: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es importante que los componentes cargados sean compatibles con la versión del marco webforJ en uso; de lo contrario, la aplicación puede no funcionar como se espera. Esta configuración se ignora al usar una instalación estándar de BBj sin el motor. Para una instalación estándar de BBj, la configuración se puede gestionar con el `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Habilita el modo de depuración. En modo de depuración, webforJ imprimirá información adicional en la consola y mostrará todas las excepciones en el navegador. El modo de depuración está deshabilitado de forma predeterminada. | `null`          |
| **`webforj.entry`**                  | String  | Define el punto de entrada de la aplicación al especificar el nombre completamente calificado de la clase que extiende `webforj.App`. Si no se define un punto de entrada, webforJ escaneará automáticamente el classpath en busca de clases que extienden `webforj.App`. Si se encuentran múltiples clases, ocurrirá un error. Cuando un paquete incluye más de un posible punto de entrada, establecer esto explícitamente es necesario para prevenir ambigüedad, o alternativamente, se puede usar la anotación `AppEntry` para especificar el punto de entrada en tiempo de ejecución. | `null`          |
| **`webforj.fileUpload.accept`**      | List    | Los tipos de archivo permitidos para cargas de archivos. De forma predeterminada, se permiten todos los tipos de archivo. Los formatos soportados incluyen tipos MIME como `image/*`, `application/pdf`, `text/plain`, o extensiones de archivo como `*.txt`. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | El tamaño máximo de archivo permitido para cargas de archivos, en bytes. De forma predeterminada, no hay límite. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | Punto de conexión URL para el directorio de íconos (de forma predeterminada se sirve desde `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | String  | El directorio para la configuración de la licencia. De forma predeterminada, es el mismo que el directorio de configuración de webforJ, pero esto se puede personalizar si es necesario. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Tiempo de espera de inicio de licencia en segundos. | `null` |
| **`webforj.locale`**                 | String  | La configuración regional para la aplicación, que determina el idioma, configuraciones regionales y formatos para fechas, horas y números. | `null` |
| **`webforj.quiet`**                  | Boolean | Desactiva la imagen de carga durante el inicio de la aplicación. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Solo entornos de desarrollo.** En un entorno de desarrollo, recargar automáticamente la página en errores relacionados con la nueva implementación, pero no en otros tipos de error. Al usar la nueva implementación en caliente, si el cliente envía una solicitud al servidor mientras se está reiniciando, puede ocurrir un error mientras se intercambia el archivo WAR. Debido a que el servidor probablemente volverá a estar en línea en breve, esta configuración permite que el cliente intente recargar la página automáticamente.  | `false` |
| **`webforj.servlets[n].name`**       | String  | Nombre del servlet (usa el nombre de la clase si no se especifica). | `null` |
| **`webforj.servlets[n].className`**  | String | Nombre de clase completamente calificado del servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Parámetros de inicialización del servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duración del tiempo de espera de la sesión en segundos. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Un mapa de pares clave-valor utilizados para almacenar cadenas para su uso en la aplicación. Útil para almacenar mensajes o etiquetas de la aplicación. Más información sobre `StringTable` se puede encontrar [aquí](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

## Configuración de `web.xml` {#configuring-webxml}

El archivo `web.xml` es un archivo de configuración esencial para aplicaciones web Java, y en webforJ, define configuraciones importantes como la configuración del servlet, patrones de URL y páginas de bienvenida. Este archivo debe estar ubicado en el directorio `WEB-INF` de la estructura de despliegue de su proyecto.

| Configuración                                 | Explicación                                                                                                                                                                                   | Valor Predeterminado               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Establece el nombre que se mostrará para la aplicación web, normalmente derivado del nombre del proyecto. Este nombre aparece en las consolas de gestión de los servidores de aplicaciones.                                                        | `${project.name}`           |
| **`<servlet>` y `<servlet-mapping>`** | Define el `WebforjServlet`, el servlet principal para manejar las solicitudes de webforJ. Este servlet está mapeado a todas las URLs (`/*`), convirtiéndose en el punto de entrada principal para las solicitudes web.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Especifica que `WebforjServlet` debe cargarse cuando la aplicación se inicia. Establecer esto en `1` hace que el servlet se cargue inmediatamente, lo que mejora el manejo de la primera solicitud.                | `1`                         |
