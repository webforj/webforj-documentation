---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: a7482285684e797c3cfc30d025a95482
---
webforJ admite protocolos de activos personalizados que permiten un acceso a recursos estructurado y eficiente. Estos protocolos abstraen las complejidades de la recuperación de recursos estáticos y dinámicos, asegurando que los activos sean recuperados e integrados sin problemas dentro de la aplicación.

## El protocolo del servidor web {#the-webserver-protocol}

El protocolo **`ws://`** te permite recuperar activos alojados en la carpeta estática de una aplicación webforJ. Todos los archivos ubicados dentro del classpath de la aplicación `src/main/resources/static` son accesibles directamente desde el navegador. Por ejemplo, si tienes un archivo llamado **css/app.css** dentro de **resources/static**, se puede acceder a él en: **`/static/css/app.css`**  

El protocolo **ws://** elimina la necesidad de codificar a fuego el prefijo `static` en tus URLs de recursos, protegiendo contra cambios de prefijos dependiendo del contexto de despliegue. Si la aplicación web se despliega bajo un contexto diferente al raíz, como **/mycontext**, la URL para **css/app.css** sería: **`/mycontext/static/css/app.css`**  

:::tip Configuración del prefijo estático
Puedes controlar el prefijo `static` utilizando la opción de [configuración de webforj](../configuration/properties#configuration-options) `webforj.assetsDir`. Esta configuración especifica el nombre de la ruta utilizada para servir archivos estáticos, mientras que **la carpeta física sigue llamándose `static`**. Es particularmente útil si la ruta estática predeterminada entra en conflicto con una ruta en tu aplicación, ya que te permite cambiar el nombre de la ruta sin renombrar la carpeta.
:::

Puedes usar la clase utilitaria <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> para resolver una URL de servidor web dada. Esto puede ser útil si tienes un componente personalizado que necesita soportar ese protocolo.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## El protocolo de contexto {#the-context-protocol}

El protocolo de contexto se mapea a recursos dentro del classpath de la aplicación en `src/main/resources`. Algunos métodos de API de recursos y anotaciones soportan este protocolo para leer el contenido de un archivo ubicado en la carpeta de recursos y enviar su contenido al navegador. Por ejemplo, la anotación `InlineJavaScript` permite leer el contenido de un archivo JavaScript de la carpeta de recursos e incluirlo en el lado del cliente.

Por ejemplo:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## El protocolo de iconos {#the-icons-protocol}

El protocolo **`icons://`** proporciona un enfoque dinámico para la gestión de iconos, haciéndolo eficiente y flexible para generar y servir iconos en [aplicaciones instalables](../configuration/installable-apps). Este protocolo te permite generar iconos sobre la marcha basándote en el nombre de archivo solicitado y parámetros, eliminando la necesidad de iconos pre-generados en muchos casos.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Icono base {#base-icon}

Cuando se solicita un icono utilizando el protocolo `icons://`, una imagen base se deriva dinámicamente del nombre de archivo del icono solicitado. Esto asegura consistencia en el diseño de iconos y permite un retroceso automático a una imagen predeterminada si no se proporciona un icono base.

- **Ejemplo 1:** Solicitud: `/icons/icon-192x192.png` → Icono base: `resources/icons/icon.png`
- **Ejemplo 2:** Solicitud: `/icons/icon-different-192x192.png` → Icono base: `resources/icons/icon-different.png`

Si no existe una imagen base para el icono solicitado, se utiliza el logotipo predeterminado de webforJ como retroceso.

:::tip `webforj.iconsDir`
Por defecto, webforJ sirve iconos desde el directorio `resources/icons/`. Puedes cambiar el nombre del endpoint configurando la propiedad `webforj.iconsDir` en el archivo de configuración de webforJ. Esto solo cambia la URL del endpoint utilizada para acceder a los iconos, no el nombre real de la carpeta. El endpoint predeterminado es `icons/`. 
:::

### Sobrescribir Iconos {#overriding-icons}

Puedes sobrescribir tamaños específicos de iconos colocando imágenes pre-generadas en el directorio `resources/icons/`. Esto proporciona un mayor control sobre la apariencia de los iconos cuando ciertos tamaños o estilos necesitan ser personalizados.

- **Ejemplo:** Si `resources/icons/icon-192x192.png` existe, se servirá directamente en lugar de generarse dinámicamente.

### Personalizar la apariencia del icono {#customizing-icon-appearance}

El protocolo `icons://` admite parámetros adicionales que te permiten personalizar la apariencia de los iconos generados dinámicamente. Esto incluye opciones para el relleno y el color de fondo.

- **Relleno**: El parámetro `padding` se puede utilizar para controlar el relleno alrededor del icono. Los valores aceptados varían de `0`, lo que significa sin relleno, a `1`, lo que significa relleno máximo.
  - **Ejemplo:** `/icons/icon-192x192.png?padding=0.3`
  
- **Color de fondo**: El parámetro `background` te permite establecer el color de fondo del icono. Soporta los siguientes valores:
  - **`transparent`**: Sin color de fondo.
  <!-- vale off -->
  - **Códigos de Color Hexadecimal**: Colores de fondo personalizados (por ejemplo, `FFFFFF` para blanco).
  <!-- vale on -->
  - **`auto`**: Intenta detectar el color de fondo del icono automáticamente.

  Por ejemplo: 
  
  - **Ejemplo 1:** `/icons/icon-192x192.png?background=000000`
  - **Ejemplo 2:** `/icons/icon-192x192.png?background=auto`
