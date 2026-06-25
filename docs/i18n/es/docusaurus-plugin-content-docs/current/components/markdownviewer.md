---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: e50beb488f343e35da80b6d4f9ceddf5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

El componente `MarkdownViewer` renderiza texto markdown como HTML. Soporta sintaxis markdown estándar que incluye encabezados, listas, bloques de código, enlaces, imágenes y renderizado de emojis. El componente también proporciona renderizado progresivo, que muestra contenido carácter por carácter para un efecto de máquina de escribir.

## Configuración de contenido {#setting-content}

Crea un `MarkdownViewer` con o sin contenido inicial, luego actualízalo usando `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hola Mundo");

// Reemplazar el contenido por completo
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

## Agregando contenido {#appending-content}

El método `append()` agrega contenido de manera incremental sin reemplazar lo que ya está:

```java
viewer.append("## Nueva Sección\n\n");
viewer.append("Más contenido aquí...");
```

Por defecto, el contenido agregado aparece inmediatamente. Cuando se habilita [el renderizado progresivo](#progressive-rendering), el contenido agregado se guarda en un búfer y se muestra carácter por carácter.

## Desplazamiento automático {#auto-scroll}

Habilita el desplazamiento automático para mantener la vista en la parte inferior a medida que crece el contenido. Esto funciona con cualquier método de adición de contenido, ya sea `setContent()`, `append()`, o renderizado progresivo. Si un usuario se desplaza manualmente hacia arriba para revisar contenido anterior, el desplazamiento automático se pausa y se reanuda cuando se desplaza de nuevo hacia abajo.

```java
viewer.setAutoScroll(true);
```

## Renderizado progresivo {#progressive-rendering}

El renderizado progresivo muestra contenido carácter por carácter en lugar de todo a la vez, creando un efecto de máquina de escribir. Las interfaces de chat de IA comúnmente utilizan esto para mostrar respuestas que aparecen gradualmente:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Cuando está habilitado, el contenido agregado a través de `setContent()` o `append()` se almacena en un búfer y se muestra de forma incremental. Cuando está deshabilitado, el contenido aparece inmediatamente.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Velocidad de renderizado {#render-speed}

El método `setRenderSpeed()` controla cuántos caracteres se renderizan por cuadro de animación. Valores más altos significan un renderizado más rápido. A 60 fps, la velocidad predeterminada de 4 se traduce en aproximadamente 240 caracteres por segundo:

| Velocidad | Caracteres/Secundo |
|-----------|---------------------|
| 4 (predeterminado) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Sincronizando tu tasa de datos
Si tu servidor envía contenido más rápido de lo que el visor renderiza, el búfer crece y el contenido mostrado se queda atrás. Aumenta `renderSpeed` para mantener el ritmo, o llama a `flush()` cuando todo el contenido haya sido recibido para mostrar el contenido restante de inmediato.
:::

### Estado de renderizado {#render-state}

Cuando el renderizado progresivo está habilitado, el método `isRendering()` devuelve `true` mientras el componente está activamente mostrando contenido almacenado en el búfer. Las interfaces de chat utilizan a menudo esto para mostrar u ocultar un botón de detención:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Este método siempre devuelve `false` cuando el renderizado progresivo está deshabilitado.

### Controlando el renderizado {#controlling-rendering}

Dos métodos controlan cómo se detiene el renderizado progresivo:

- **`stop()`** detiene el renderizado y descarta cualquier contenido almacenado que aún no se haya mostrado. Llama a esto cuando el usuario cancela.
- **`flush()`** detiene el renderizado pero muestra inmediatamente todo el contenido almacenado restante. Llama a esto cuando todo el contenido ha sido recibido y quieres mostrarlo sin esperar.

```java
// El usuario hizo clic en "Detener generación"
viewer.stop();

// Todo el contenido recibido, mostrar todo ahora
viewer.flush();
```

Estos métodos no tienen efecto cuando el renderizado progresivo está deshabilitado.

### Esperando a que se complete {#waiting-for-completion}

El método `whenRenderComplete()` devuelve un `PendingResult` que se completa cuando el renderizado progresivo termina de mostrar todo el contenido almacenado:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Si el renderizado progresivo no está habilitado o no se está renderizando contenido, el `PendingResult` se completa inmediatamente.

:::tip Coordinación de UI
Al utilizar renderizado progresivo, no vuelvas a habilitar campos de entrada únicamente basándote en cuándo terminas de llamar a `append()`. El renderizador aún puede estar mostrando contenido almacenado. Espera a `whenRenderComplete()` para que todo el contenido aparezca antes de que los usuarios puedan interactuar de nuevo.
:::

La siguiente demostración simula una interfaz de chat de IA usando `append()` con renderizado progresivo habilitado:

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Limpiando contenido {#clearing-content}

Elimina todo el contenido con `clear()`:

```java
viewer.clear();
```

Si el renderizado progresivo está activo, `clear()` también detiene el renderizado y completa cualquier resultado pendiente de `whenRenderComplete()`.

## Resaltado de sintaxis {#syntax-highlighting}

El `MarkdownViewer` soporta resaltado de sintaxis para bloques de código cuando [Prism.js](https://prismjs.com/) está disponible. Agrega Prism.js a tu aplicación usando las anotaciones `@JavaScript` y `@StyleSheet`:

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

El plugin autoloader carga definiciones de lenguaje según sea necesario, por lo que los bloques de código con pistas de lenguaje como ` ```java ` o ` ```python ` se resaltan automáticamente.

<TableBuilder name="MarkdownViewer" />
