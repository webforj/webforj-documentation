---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: b37158687b15dc2b94a7543624eaa09f
---
webforJ soporta protocolos de activos personalizados que permiten un acceso estructurado y eficiente a los recursos. Estos protocolos abstraen las complejidades de la recuperación de recursos estáticos y dinámicos, asegurando que los activos se obtengan e integren sin problemas dentro de la aplicación.

## El protocolo del servidor web {#the-webserver-protocol}

El protocolo **`ws://`** te permite recuperar activos alojados en la carpeta estática de una aplicación webforJ. Todos los archivos ubicados dentro de la ruta de clase de la aplicación `src/main/resources/static` son accesibles directamente desde el navegador. Por ejemplo, si tienes un archivo llamado **css/app.css** dentro de **resources/static**, se puede acceder en: **`/static/css/app.css`**  

El protocolo **ws://** elimina la necesidad de codificar el prefijo `static` en tus URLs de recursos, protegiendo contra cambios de prefijos dependiendo del contexto de despliegue. Si la aplicación web se despliega bajo un contexto distinto al raíz, como **/mycontext**, la URL para **css/app.css** sería: **`/mycontext/static/css/app.css`**  

:::tip Configurando el prefijo estático
Puedes controlar el prefijo `static` utilizando la opción de configuración [webforj](../configuration/properties#configuration-options) `webforj.assetsDir`. Esta configuración especifica el nombre de la ruta utilizado para servir archivos estáticos, mientras que **la carpeta física sigue llamándose `static`**. Es particularmente útil si la ruta estática predeterminada entra en conflicto con una ruta en tu aplicación, ya que te permite cambiar el nombre de la ruta sin renombrar la carpeta.
:::

Puedes utilizar la clase utilitaria <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> para resolver una URL de servidor web dada. Esto puede ser útil si tienes un componente personalizado que necesita soportar ese protocolo.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## El protocolo de contexto {#the-context-protocol}

El protocolo de contexto mapea recursos dentro de la ruta de clase de la aplicación en `src/main/resources`. Algunos métodos y anotaciones de la API de recursos soportan este protocolo para leer el contenido de un archivo ubicado en la carpeta de recursos y enviar su contenido al navegador. Por ejemplo, la anotación `InlineJavaScript` permite leer el contenido de un archivo JavaScript desde la carpeta de recursos y hacerlo en línea en el lado del cliente.

Por ejemplo:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## El protocolo de íconos {#the-icons-protocol}

El protocolo **`icons://`** proporciona un enfoque dinámico para la gestión de íconos, haciéndolo eficiente y flexible para generar y servir íconos en [aplicaciones instalables](../configuration/installable-apps). Este protocolo permite generar íconos al vuelo basado en el nombre de archivo solicitado y los parámetros, eliminando la necesidad de íconos pre-generados en muchos casos.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Ícono base {#base-icon}

Cuando se solicita un ícono utilizando el protocolo `icons://`, una imagen base se deriva dinámicamente del nombre de archivo del ícono solicitado. Esto asegura consistencia en el diseño del ícono y permite un retroceso automático a una imagen predeterminada si no se proporciona un ícono base.

- **Ejemplo 1:** Solicitud: `/icons/icon-192x192.png` → Ícono base: `resources/icons/icon.png`
- **Ejemplo 2:** Solicitud: `/icons/icon-different-192x192.png` → Ícono base: `resources/icons/icon-different.png`

Si no existe una imagen base para el ícono solicitado, se utiliza el logotipo predeterminado de webforJ como alternativa.

:::tip `webforj.iconsDir`
Por defecto, webforJ sirve íconos desde el directorio `resources/icons/`. Puedes cambiar el nombre del endpoint configurando la propiedad `webforj.iconsDir` en el archivo de configuración de webforJ. Esto solo cambia el endpoint URL utilizado para acceder a los íconos, no el nombre real de la carpeta. El endpoint predeterminado es `icons/`.
:::

### Sobrescribiendo íconos {#overriding-icons}

Puedes sobrescribir tamaños específicos de íconos colocando imágenes pre-generadas en el directorio `resources/icons/`. Esto proporciona un mayor control sobre la apariencia de los íconos cuando ciertos tamaños o estilos necesitan ser personalizados.

- **Ejemplo:** Si `resources/icons/icon-192x192.png` existe, se servirá directamente en lugar de ser generado dinámicamente.

### Personalizando la apariencia de los íconos {#customizing-icon-appearance}

El protocolo `icons://` soporta parámetros adicionales que te permiten personalizar la apariencia de los íconos generados dinámicamente. Esto incluye opciones para el relleno y el color de fondo.

- **Relleno**: El parámetro `padding` puede ser utilizado para controlar el relleno alrededor del ícono. Los valores aceptados van desde `0`, lo que significa sin relleno, hasta `1`, lo que significa relleno máximo.
  - **Ejemplo:** `/icons/icon-192x192.png?padding=0.3`
  
- **Color de fondo**: El parámetro `background` permite establecer el color de fondo del ícono. Soporta los siguientes valores:
  - **`transparent`**: Sin color de fondo.
  <!-- vale off -->
  - **Códigos de color hexadecimal**: Colores de fondo personalizados (por ejemplo, `FFFFFF` para blanco).
  <!-- vale on -->
  - **`auto`**: Intenta detectar automáticamente el color de fondo del ícono.

  Por ejemplo: 
  
  - **Ejemplo 1:** `/icons/icon-192x192.png?background=000000`
  - **Ejemplo 2:** `/icons/icon-192x192.png?background=auto`
