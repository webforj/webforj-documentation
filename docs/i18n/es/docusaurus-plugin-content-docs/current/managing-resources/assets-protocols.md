---
sidebar_position: 2
title: Assets Protocols
description: >-
  Reference app resources with the webforJ ws, context, and icons protocols to
  load static files, classpath content, and dynamic icons.
_i18n_hash: 3928ba255cb9887c80c20f904baf62b8
---
webforJ admite protocolos de activos personalizados que permiten un acceso estructurado y eficiente a los recursos. Estos protocolos abstraen las complejidades de la recuperación de recursos estáticos y dinámicos, asegurando que los activos se obtengan e integren sin problemas dentro de la aplicación.

## El protocolo del servidor web {#the-webserver-protocol}

El protocolo **`ws://`** te permite recuperar activos alojados en la carpeta estática de una aplicación webforJ. Todos los archivos ubicados dentro de la ruta de clase de la aplicación `src/main/resources/static` son accesibles directamente desde el navegador. Por ejemplo, si tienes un archivo llamado **css/app.css** dentro de **resources/static**, se puede acceder a él en: **`/static/css/app.css`**

El protocolo **ws://** elimina la necesidad de codificar a mano el prefijo `static` en tus URL de recursos, protegiendo contra cambios de prefijos dependiendo del contexto de despliegue. Si la aplicación web se despliega bajo un contexto diferente a la raíz, como **/mycontext**, la URL para **css/app.css** sería: **`/mycontext/static/css/app.css`**

:::tip Configuración del prefijo estático
Puedes controlar el prefijo `static` usando la opción de [configuración de webforj](../configuration/properties#configuration-options) `webforj.assetsDir`. Esta configuración especifica el nombre de la ruta utilizada para servir archivos estáticos, mientras que **la carpeta física sigue llamándose `static`**. Es especialmente útil si la ruta estática predeterminada entra en conflicto con una ruta en tu aplicación, ya que te permite cambiar el nombre de la ruta sin renombrar la carpeta.
:::

Puedes usar la clase de utilidades <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> para resolver una URL del servidor web dada. Esto puede ser útil si tienes un componente personalizado que necesita admitir ese protocolo.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## El protocolo de contexto {#the-context-protocol}

El protocolo de contexto se asigna a recursos dentro de la ruta de clase de la aplicación en `src/main/resources`. Algunos métodos y anotaciones de la API de recursos admiten este protocolo para leer el contenido de un archivo ubicado en la carpeta de recursos y enviar su contenido al navegador. Por ejemplo, la anotación `InlineJavaScript` permite leer el contenido de un archivo JavaScript de la carpeta de recursos e insertarlo en el lado del cliente.

Por ejemplo:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## El protocolo de íconos {#the-icons-protocol}

El protocolo **`icons://`** proporciona un enfoque dinámico para la gestión de íconos, haciéndolo eficiente y flexible para generar y servir íconos en [aplicaciones instalables](../configuration/installable-apps). Este protocolo te permite generar íconos al instante según el nombre de archivo y los parámetros solicitados, eliminando la necesidad de íconos pre-generados en muchos casos.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Ícono base {#base-icon}

Cuando se solicita un ícono utilizando el protocolo `icons://`, una imagen base se deriva dinámicamente del nombre de archivo del ícono solicitado. Esto asegura consistencia en el diseño del ícono y permite una retroalimentación automática a una imagen predeterminada si no se proporciona un ícono base.

- **Ejemplo 1:** Solicitud: `/icons/icon-192x192.png` → Ícono base: `resources/icons/icon.png`
- **Ejemplo 2:** Solicitud: `/icons/icon-different-192x192.png` → Ícono base: `resources/icons/icon-different.png`

Si no existe una imagen base para el ícono solicitado, se utiliza el logotipo predeterminado de webforJ como respaldo.

:::tip `webforj.iconsDir`
Por defecto, webforJ sirve íconos desde el directorio `resources/icons/`. Puedes cambiar el nombre del endpoint configurando la propiedad `webforj.iconsDir` en el archivo de configuración de webforJ. Esto solo cambia el endpoint de URL utilizado para acceder a los íconos, no el nombre real de la carpeta. El endpoint predeterminado es `icons/`.
:::

### Sobreescribiendo íconos {#overriding-icons}

Puedes sobreescribir tamaños específicos de íconos colocando imágenes pre-generadas en el directorio `resources/icons/`. Esto proporciona un mayor control sobre la apariencia de los íconos cuando ciertos tamaños o estilos necesitan personalización.

- **Ejemplo:** Si `resources/icons/icon-192x192.png` existe, se servirá directamente en lugar de generarse dinámicamente.

### Personalizando la apariencia del ícono {#customizing-icon-appearance}

El protocolo `icons://` admite parámetros adicionales que te permiten personalizar la apariencia de los íconos generados dinámicamente. Esto incluye opciones para el relleno y el color de fondo.

- **Relleno**: El parámetro `padding` se puede usar para controlar el relleno alrededor del ícono. Los valores aceptados van desde `0`, lo que significa sin relleno, hasta `1`, que significa relleno máximo.
  - **Ejemplo:** `/icons/icon-192x192.png?padding=0.3`

- **Color de fondo**: El parámetro `background` te permite establecer el color de fondo del ícono. Admite los siguientes valores:
  - **`transparent`**: Sin color de fondo.
  <!-- vale off -->
  - **Códigos de colores hexadecimales**: Colores de fondo personalizados (por ejemplo, `FFFFFF` para blanco).
  <!-- vale on -->
  - **`auto`**: Intenta detectar automáticamente el color de fondo del ícono.

  Por ejemplo:

  - **Ejemplo 1:** `/icons/icon-192x192.png?background=000000`
  - **Ejemplo 2:** `/icons/icon-192x192.png?background=auto`
