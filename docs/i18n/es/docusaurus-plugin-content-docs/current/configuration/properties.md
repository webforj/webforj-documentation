---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 3e14c2d47a7963fe901feda071971419
---
# Configurando propiedades de webforJ

Para desplegar y ejecutar correctamente una aplicación webforJ, se requieren un par de archivos de configuración clave: `webforj.conf` y `web.xml`. Cada uno de estos archivos controla diferentes aspectos del comportamiento de la aplicación, desde puntos de entrada y configuraciones de depuración hasta mapeos de servlets.

## Configurando `webforj.conf` {#configuring-webforjconf}

El archivo `webforj.conf` es un archivo de configuración central en webforJ, que especifica configuraciones de la aplicación como puntos de entrada, modo de depuración e interacción cliente-servidor. El archivo está en [formato HOCON](https://github.com/lightbend/config/blob/master/HOCON.md), y debería estar ubicado en el directorio `resources`.

:::tip
Si estás integrando con el [Framework Spring](../integrations/spring/overview.md), puedes establecer estas propiedades de `webforj.conf` en el archivo `application.properties`.
:::



### Ejemplo del archivo `webforj.conf` {#example-webforjconf-file}

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
| **`webforj.assetsCacheControl`**     | String  | Cabecera Cache-Control para recursos estáticos.                   | `null` |
| **`webforj.assetsDir`**              | String  | El nombre de la ruta utilizado para servir archivos estáticos, mientras el nombre de la carpeta permanece como `static`. Esta configuración es útil si la ruta predeterminada `static` entra en conflicto con una ruta definida en tu aplicación, permitiéndote cambiar el nombre de la ruta sin renombrar la carpeta en sí. | `null`               |
| **`webforj.assetsExt`**              | String  | Extensión de archivo predeterminada para archivos estáticos.      | `null` |
| **`webforj.assetsIndex`**            | String  | Archivo predeterminado servido para solicitudes de directorio (ej., index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | El intervalo en el que el cliente verifica si el servidor está activo. Para desarrollo, establece esto en un intervalo más corto, por ejemplo `8s`, para detectar rápidamente la disponibilidad del servidor. Establece en 50 segundos o más en producción para evitar solicitudes excesivas. | `50s`           |
| **`webforj.components`**             | String  | Cuando se especifica, la ruta base determina de dónde se cargan los componentes DWC. Por defecto, los componentes se cargan desde el servidor que aloja la aplicación. Sin embargo, establecer una ruta base personalizada permite que los componentes se carguen desde un servidor alternativo o CDN. Por ejemplo, para cargar componentes desde jsdelivr.com, establece la ruta base en: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Es importante que los componentes cargados sean compatibles con la versión del marco webforJ en uso; de lo contrario, la aplicación puede no funcionar como se espera. Esta configuración se ignora al utilizar una instalación estándar de BBj sin el motor. Para una instalación estándar de BBj, la configuración se puede gestionar con el `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Activa el modo de depuración. En modo de depuración, webforJ imprimirá información adicional en la consola y mostrará todas las excepciones en el navegador. El modo de depuración está desactivado por defecto. | `null`          |
| **`webforj.entry`**                  | String  | Define el punto de entrada de la aplicación especificando el nombre completamente calificado de la clase que extiende `webforj.App`. Si no se define un punto de entrada, webforJ escaneará automáticamente el classpath en busca de clases que extiendan `webforj.App`. Si se encuentran múltiples clases, ocurrirá un error. Cuando un paquete incluye más de un posible punto de entrada, establecer esto explícitamente es necesario para prevenir ambigüedad, o alternativamente, se puede usar la anotación `AppEntry` para especificar el punto de entrada en tiempo de ejecución. | `null`          |
| **`webforj.fileUpload.accept`**      | List    | Los tipos de archivo permitidos para cargas de archivos. Por defecto, todos los tipos de archivo están permitidos. Formatos admitidos incluyen tipos MIME como `image/*`, `application/pdf`, `text/plain`, o extensiones de archivo como `*.txt`. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | El tamaño máximo de archivo permitido para cargas de archivos, en bytes. Por defecto, no hay límite. Al usar una instalación estándar de BBj, esta configuración se ignora y se gestiona a través de `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | Punto final de URL para el directorio de íconos (por defecto sirve desde `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | String  | El directorio para la configuración de la licencia. Por defecto, es el mismo que el directorio de configuración de webforJ, pero esto se puede personalizar si es necesario. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Tiempo de espera para el inicio de la licencia en segundos. | `null` |
| **`webforj.locale`**                 | String  | La localidad para la aplicación, determinando lenguaje, configuraciones regionales y formatos para fechas, horas y números. | `null` |
| **`webforj.quiet`**                  | Boolean | Desactiva la imagen de carga durante el inicio de la aplicación. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Solo entornos de desarrollo.** En un entorno de desarrollo, recarga automáticamente la página en errores relacionados con la redeploy caliente, pero no otros tipos de errores. Al usar la redeploy caliente, si el cliente envía una solicitud al servidor mientras se reinicia, puede ocurrir un error mientras se cambia el archivo WAR. Debido a que es probable que el servidor vuelva a estar en línea en breve, esta configuración permite que el cliente intente recargar automáticamente la página. | `false` |
| **`webforj.servlets[n].name`**       | String  | Nombre del servlet (usa el nombre de clase si no está especificado). | `null` |
| **`webforj.servlets[n].className`**  | String | Nombre de clase completamente calificado del servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Parámetros de inicialización del servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duración del tiempo de espera de la sesión en segundos. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Un mapa de pares clave-valor utilizados para almacenar cadenas para uso en la aplicación. Útil para almacenar mensajes o etiquetas de la aplicación. Más información sobre `StringTable` se puede encontrar [aquí](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

## Configurando `web.xml` {#configuring-webxml}

El archivo web.xml es un archivo de configuración esencial para aplicaciones web Java, y en webforJ, define configuraciones importantes como la configuración del servlet, patrones de URL y páginas de bienvenida. Este archivo debería estar ubicado en el directorio `WEB-INF` de la estructura de despliegue de tu proyecto.

| Configuración                                 | Explicación                                                                                                                                                                                   | Valor Predeterminado           |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Establece el nombre para mostrar de la aplicación web, que normalmente se deriva del nombre del proyecto. Este nombre aparece en las consolas de gestión de los servidores de aplicaciones.                                                        | `${project.name}`           |
| **`<servlet>` y `<servlet-mapping>`** | Define el `WebforjServlet`, el servlet central para manejar solicitudes de webforJ. Este servlet está mapeado a todas las URLs (`/*`), convirtiéndolo en el principal punto de entrada para solicitudes web.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Especifica que `WebforjServlet` debería cargarse cuando la aplicación se inicie. Establecer esto a `1` hace que el servlet se cargue inmediatamente, mejorando el manejo de solicitudes iniciales.                | `1`                         |
