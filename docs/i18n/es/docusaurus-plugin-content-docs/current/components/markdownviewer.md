---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: dcbc11ba7581a82ae6857abfe11a62c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

El componente `MarkdownViewer` renderiza texto markdown como HTML. Soporta la sintaxis markdown estándar, incluyendo encabezados, listas, bloques de código, enlaces, imágenes y renderización de emojis. El componente también proporciona renderización progresiva, que muestra el contenido carácter por carácter para un efecto de máquina de escribir.

## Configuración del contenido {#setting-content}

Crea un `MarkdownViewer` con o sin contenido inicial, luego actualízalo usando `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hola Mundo");

// Reemplazar el contenido por completo
viewer.setContent("""
    ## Nuevo Contenido

    - Elemento 1
    - Elemento 2
    """);

// Obtener el contenido actual
String content = viewer.getContent();
```
:::tip
El componente implementa `HasText`, por lo que `setText()` y `getText()` funcionan como alias para los métodos de contenido.
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='650px'
/>

## Añadiendo contenido {#appending-content}

El método `append()` añade contenido de forma incremental sin reemplazar lo que ya hay:

```java
viewer.append("## Nueva Sección\n\n");
viewer.append("Más contenido aquí...");
```

Por defecto, el contenido añadido aparece inmediatamente. Cuando la [renderización progresiva](#progressive-rendering) está habilitada, el contenido añadido va a un búfer y se muestra carácter por carácter.

## Desplazamiento automático {#auto-scroll}

Habilita el desplazamiento automático para mantener la vista en la parte inferior a medida que crece el contenido. Esto funciona con cualquier método de añadir contenido, ya sea `setContent()`, `append()`, o renderización progresiva. Si un usuario se desplaza manualmente hacia arriba para revisar contenido anterior, el desplazamiento automático se pausa y se reanuda cuando se desplaza de nuevo hacia la parte inferior.

```java
viewer.setAutoScroll(true);
```

## Renderización progresiva {#progressive-rendering}

La renderización progresiva muestra el contenido carácter por carácter en lugar de todo de una vez, creando un efecto de máquina de escribir. Las interfaces de chat de IA comúnmente utilizan esto para mostrar respuestas que aparecen gradualmente:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Cuando está habilitado, el contenido añadido a través de `setContent()` o `append()` va a un búfer y se muestra de forma incremental. Cuando está deshabilitado, el contenido aparece inmediatamente.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Velocidad de renderización {#render-speed}

El método `setRenderSpeed()` controla cuántos caracteres se renderizan por fotograma de animación. Valores más altos significan renderización más rápida. A 60 fps, la velocidad predeterminada de 4 se traduce aproximadamente en 240 caracteres por segundo:

| Velocidad | Caracteres por segundo |
|-----------|------------------------|
| 4 (predeterminado) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Coincidiendo con tu tasa de datos
Si tu servidor envía contenido más rápido de lo que el visor renderiza, el búfer crece y el contenido mostrado se queda atrás. Aumenta `renderSpeed` para mantener el ritmo, o llama a `flush()` cuando todo el contenido ha sido recibido para mostrar el contenido restante de inmediato.
:::

### Estado de renderización {#render-state}

Cuando la renderización progresiva está habilitada, el método `isRendering()` devuelve `true` mientras el componente está mostrando activamente el contenido almacenado en búfer. Las interfaces de chat a menudo utilizan esto para mostrar u ocultar un botón de detener:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Este método siempre devuelve `false` cuando la renderización progresiva está deshabilitada.

### Controlando la renderización {#controlling-rendering}

Dos métodos controlan cómo se detiene la renderización progresiva:

- **`stop()`** detiene la renderización y descarta cualquier contenido en búfer que aún no se ha mostrado. Llama a esto cuando el usuario cancela.
- **`flush()`** detiene la renderización pero muestra inmediatamente todo el contenido en búfer restante. Llama a esto cuando todo el contenido ha sido recibido y quieres mostrarlo sin esperar.

```java
// El usuario hizo clic en "Detener generación"
viewer.stop();

// Todo el contenido recibido, mostrar todo ahora
viewer.flush();
```

Estos métodos no tienen efecto cuando la renderización progresiva está deshabilitada.

### Esperando a que finalice {#waiting-for-completion}

El método `whenRenderComplete()` devuelve un `PendingResult` que se completa cuando la renderización progresiva termina de mostrar todo el contenido en búfer:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Si la renderización progresiva no está habilitada o no se está renderizando ningún contenido, el `PendingResult` se completa de inmediato.

:::tip Coordinación de la interfaz de usuario
Al utilizar renderización progresiva, no vuelvas a habilitar los campos de entrada únicamente basándote en cuándo terminas de llamar a `append()`. El renderizador puede seguir mostrando contenido almacenado en búfer. Espera a `whenRenderComplete()` para que todo el contenido aparezca antes de que los usuarios puedan interactuar nuevamente.
:::

La siguiente demostración simula una interfaz de chat de IA utilizando `append()` con la renderización progresiva habilitada:

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='700px'
/>

## Limpiando contenido {#clearing-content}

Elimina todo el contenido con `clear()`:

```java
viewer.clear();
```

Si la renderización progresiva está activa, `clear()` también detiene la renderización y completa cualquier resultado pendiente de `whenRenderComplete()`.

## Resaltado de sintaxis {#syntax-highlighting}

El `MarkdownViewer` soporta resaltado de sintaxis para bloques de código cuando [Prism.js](https://prismjs.com/) está disponible. Agrega Prism.js a tu aplicación utilizando las anotaciones `@JavaScript` y `@StyleSheet`:

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/prismjs@1/themes/prism-tomorrow.min.css")
@JavaScript(
  value = "https://cdn.jsdelivr.net/combine/npm/prismjs@1/prism.min.js,npm/prismjs@1/plugins/autoloader/prism-autoloader.min.js",
  top = true
)
public class Application extends App {
  // ...
}
```

El plugin autoloader carga las definiciones de lenguaje según sea necesario, por lo que los bloques de código con indicaciones de lenguaje como ` ```java ` o ` ```python ` se resaltan automáticamente.

<TableBuilder name="MarkdownViewer" />
