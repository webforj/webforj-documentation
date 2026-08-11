---
title: MarkdownViewer
sidebar_position: 74
description: >-
  Render markdown as HTML with the MarkdownViewer component, supporting append,
  auto-scroll, and progressive typewriter rendering.
_i18n_hash: fbd31d2317bf5de95c282a1319f35cf6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

El componente `MarkdownViewer` renderiza texto markdown como HTML. Soporta la sintaxis markdown estándar incluyendo encabezados, listas, bloques de código, enlaces, imágenes y renderización de emoji. El componente también proporciona renderización progresiva, que muestra el contenido carácter por carácter para crear un efecto de máquina de escribir.

## Configuración del contenido {#setting-content}

Crea un `MarkdownViewer` con o sin contenido inicial, luego actualízalo usando `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hello World");

// Reemplazar contenido completamente
viewer.setContent("""
    ## Nuevo Contenido

    - Elemento 1
    - Elemento 2
    """);

// Obtener contenido actual
String content = viewer.getContent();
```
:::tip
El componente implementa `HasText`, por lo que `setText()` y `getText()` funcionan como alias para los métodos de contenido.
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Agregar contenido {#appending-content}

El método `append()` agrega contenido de forma incremental sin reemplazar lo que ya está allí:

```java
viewer.append("## Nueva Sección\n\n");
viewer.append("Más contenido aquí...");
```

Por defecto, el contenido agregado aparece inmediatamente. Cuando se habilita la [renderización progresiva](#progressive-rendering), el contenido agregado va a un búfer y se muestra carácter por carácter.

## Desplazamiento automático {#auto-scroll}

Habilita el desplazamiento automático para mantener la vista en la parte inferior a medida que el contenido crece. Esto funciona con cualquier método de adición de contenido, ya sea `setContent()`, `append()`, o renderización progresiva. Si un usuario se desplaza manualmente hacia arriba para revisar contenido anterior, el desplazamiento automático se pausa y se reanuda cuando vuelven a desplazarse hacia abajo.

```java
viewer.setAutoScroll(true);
```

## Renderización progresiva {#progressive-rendering}

La renderización progresiva muestra contenido carácter por carácter en lugar de todo de una vez, creando un efecto de máquina de escribir. Las interfaces de chat de IA comúnmente usan esto para mostrar respuestas que aparecen gradualmente:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Cuando está habilitado, el contenido agregado a través de `setContent()` o `append()` va a un búfer y se muestra de forma incremental. Cuando está deshabilitado, el contenido aparece inmediatamente.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Velocidad de renderizado {#render-speed}

El método `setRenderSpeed()` controla cuántos caracteres se renderizan por cuadro de animación. Valores más altos significan una renderización más rápida. A 60fps, la velocidad predeterminada de 4 se traduce aproximadamente en 240 caracteres por segundo:

| Velocidad | Caracteres/Secundo |
|-----------|---------------------|
| 4 (predeterminado) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Sincronizando tu tasa de datos
Si tu servidor envía contenido más rápido de lo que el visor renderiza, el búfer crece y el contenido mostrado se queda atrás. Aumenta `renderSpeed` para mantener el ritmo, o llama a `flush()` cuando todo el contenido haya sido recibido para mostrar el contenido restante inmediatamente.
:::

### Estado de renderizado {#render-state}

Cuando la renderización progresiva está habilitada, el método `isRendering()` devuelve `true` mientras el componente está mostrando activamente contenido almacenado en búfer. Las interfaces de chat a menudo usan esto para mostrar u ocultar un botón de detener:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Este método siempre devuelve `false` cuando la renderización progresiva está deshabilitada.

### Controlando la renderización {#controlling-rendering}

Dos métodos controlan cómo se detiene la renderización progresiva:

- **`stop()`** detiene la renderización y descarta cualquier contenido en búfer que aún no se haya mostrado. Llame a esto cuando el usuario cancele.
- **`flush()`** detiene la renderización pero muestra inmediatamente todo el contenido restante en búfer. Llame a esto cuando todo el contenido haya sido recibido y desee mostrarlo sin esperar.

```java
// El usuario hizo clic en "Detener generación"
viewer.stop();

// Todo el contenido recibido, mostrar todo ahora
viewer.flush();
```

Estos métodos no tienen efecto cuando la renderización progresiva está deshabilitada.

### Esperando a la finalización {#waiting-for-completion}

El método `whenRenderComplete()` devuelve un `PendingResult` que se completa cuando la renderización progresiva termina de mostrar todo el contenido en búfer:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Si la renderización progresiva no está habilitada o no se está renderizando contenido, el `PendingResult` se completa inmediatamente.

:::tip Coordinación de la UI
Cuando uses renderización progresiva, no re-habilites los campos de entrada basándote solo en cuando termines de llamar a `append()`. El renderizador puede seguir mostrando contenido almacenado en búfer. Espera a `whenRenderComplete()` para que todo el contenido aparezca antes de que los usuarios puedan interactuar nuevamente.
:::

La siguiente demo simula una interfaz de chat de IA utilizando `append()` con renderización progresiva habilitada:

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Limpiando el contenido {#clearing-content}

Elimina todo el contenido con `clear()`:

```java
viewer.clear();
```

Si la renderización progresiva está activa, `clear()` también detiene la renderización y completa cualquier resultado pendiente de `whenRenderComplete()`.

## Resaltado de sintaxis {#syntax-highlighting}

El `MarkdownViewer` soporta resaltado de sintaxis para bloques de código cuando [Prism.js](https://prismjs.com/) está disponible. Lleva Prism a tu aplicación con el [agregador de frontend](/docs/managing-resources/bundler/overview): declara el paquete en tu clase `App` y autoriza una entrada que importe Prism, el complemento de autoload y un tema.

```java title="Application.java"
@BundlePackage(value = "prismjs", version = "^1.29.0")
@BundleEntry("prism/entry.ts")
public class Application extends App {
  // ...
}
```

```ts title="src/main/frontend/prism/entry.ts"
import "prismjs";
import "prismjs/plugins/autoloader/prism-autoloader";
import "prismjs/themes/prism-tomorrow.min.css";
```

El complemento de autoload carga definiciones de lenguajes según sea necesario, por lo que los bloques de código con sugerencias de lenguaje como ` ```java ` o ` ```python ` se resaltan automáticamente.

<TableBuilder name="MarkdownViewer" />
