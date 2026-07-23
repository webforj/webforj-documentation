---
sidebar_position: 40
title: View Transitions
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: fb54ad2ee8205e9dbdc27165635fda55
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Las transiciones de vista proporcionan transiciones animadas cuando el [DOM](/docs/glossary#dom) cambia, reduciendo la incomodidad visual y manteniendo el contexto espacial durante la navegación o las actualizaciones de contenido. webforJ se integra con la [API de transición de vista](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) del navegador para manejar la complejidad de coordinar animaciones entre estados antiguos y nuevos.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Uso básico {#basic-usage}

Para crear una transición de vista, utiliza `Page.getCurrent().startViewTransition()`, que devuelve un constructor para configurar la transición:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

El proceso de transición captura una instantánea del estado actual, aplica tus cambios de DOM en el callback `onUpdate`, y luego anima desde la instantánea antigua hasta el nuevo contenido. Debes llamar a `done.run()` para señalizar cuando tus cambios están completos.

:::warning El callback `onUpdate` es obligatorio
Llamar a `start()` sin establecer un callback de actualización lanza un `IllegalStateException`.
:::

## Aplicando transiciones {#applying-transitions}

webforJ proporciona tipos de transición predefinidos que puedes aplicar a componentes que entran o salen del DOM:

| Constante | Efecto |
|----------|--------|
| `ViewTransition.NONE` | Sin animación |
| `ViewTransition.FADE` | Desvanecimiento cruzado entre contenido antiguo y nuevo |
| `ViewTransition.SLIDE_LEFT` | El contenido fluye hacia la izquierda (como navegación hacia adelante) |
| `ViewTransition.SLIDE_RIGHT` | El contenido fluye hacia la derecha (como navegación hacia atrás) |
| `ViewTransition.SLIDE_UP` | El contenido fluye hacia arriba |
| `ViewTransition.SLIDE_DOWN` | El contenido fluye hacia abajo |
| `ViewTransition.ZOOM` | El contenido antiguo se encoge, el contenido nuevo crece |
| `ViewTransition.ZOOM_OUT` | El contenido antiguo crece, el contenido nuevo se encoge |

Utiliza `enter()` para animar un componente que se está agregando y `exit()` para animar un componente que se está eliminando:

```java
// Animar un componente entrando en el DOM
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animar un componente saliendo del DOM
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Transiciones de componentes compartidos {#shared-component-transitions}

Las transiciones de componentes compartidos crean un efecto de transformación donde un componente parece transformarse desde su posición en la vista antigua a su posición en la nueva vista. Esto se logra al dar a los componentes el mismo nombre de transición utilizando el método `setViewTransitionName()`, que está disponible en cualquier componente que implemente la interfaz <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>.

```java
// En la vista de tarjeta
image.setViewTransitionName("blog-image");

// En la vista de detalle - el mismo nombre crea la transformación
image.setViewTransitionName("blog-image");
```

Al transitar entre estas vistas, el navegador anima el componente entre posiciones, creando una experiencia visual conectada.

:::tip Utiliza nombres únicos
Al trabajar con listas o componentes repetidos, incluye un identificador único en el nombre de la transición. Cada componente requiere su propio nombre distinto para transformarse correctamente en su componente correspondiente en la nueva vista. Usar el mismo nombre para múltiples componentes visibles causa un comportamiento indefinido.
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/frontend/css/viewtransitions/morph.css',
]}
height='650px'
/>

### Reordenamiento de listas {#list-reordering}

Un caso de uso común para las transiciones de componentes compartidos es animar elementos de lista cuando su orden cambia. Al asignar un `view-transition-name` único a cada elemento, el navegador anima automáticamente los componentes a sus nuevas posiciones:

```java
// Cada tarjeta recibe un nombre de transición único basado en su ID
card.setViewTransitionName("card-" + item.id());

// Al mezclar, solo actualiza el DOM - el navegador maneja la animación
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    renderList();
    done.run();
  })
  .start();
```

<ComponentDemo
path='/webforj/viewtransitionshuffle'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java',
  'src/main/frontend/css/viewtransitions/shuffle.css',
]}
height='550px'
/>

## Animaciones CSS personalizadas {#custom-css-animations}

Para tener un control total sobre las animaciones, puedes definir keyframes CSS personalizados. webforJ añade sufijos `-enter` o `-exit` a tus nombres de transición, los cuales usas para dirigirte a los pseudo-elementos de transición de vista:

```css
/* Definir keyframes para componentes que entran */
@keyframes flip-enter {
  from {
    opacity: 0;
    transform: perspective(1000px) rotateX(-90deg);
  }
  to {
    opacity: 1;
    transform: perspective(1000px) rotateX(0deg);
  }
}

/* Aplicar al pseudo-elemento de transición de vista */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Referencia tu animación personalizada pasando su nombre (sin el sufijo) a `enter()` o `exit()`:

```java
// Usar "flip-in" - webforJ añade automáticamente el sufijo "-enter"
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Usar "blur-out" para salir - webforJ añade automáticamente el sufijo "-exit"
Page.getCurrent().startViewTransition()
  .exit(notification, "blur-out")
  .onUpdate(done -> {
    stage.remove(notification);
    done.run();
  })
  .start();
```

<ComponentDemo
path='/webforj/viewtransitionenterexit'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionEnterExitView.java',
  'src/main/frontend/css/viewtransitions/enterexit.css',
]}
height='400px'
/>

## Personalización CSS {#css-customization}

Cada tipo de transición predefinido expone propiedades CSS personalizables para ajustes finos:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Desvanecimiento</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Predeterminado | Descripción |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Duración de la animación |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Función de desaceleración |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Deslizar a la izquierda</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Predeterminado | Descripción |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Duración de la animación |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Función de desaceleración |
      | `--vt-slide-left-distance` | `30%` | Distancia de deslizamiento |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Deslizar a la derecha</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Predeterminado | Descripción |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Duración de la animación |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Función de desaceleración |
      | `--vt-slide-right-distance` | `30%` | Distancia de deslizamiento |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Deslizar hacia arriba</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Predeterminado | Descripción |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Duración de la animación |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Función de desaceleración |
      | `--vt-slide-up-distance` | `30%` | Distancia de deslizamiento |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Deslizar hacia abajo</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Predeterminado | Descripción |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Duración de la animación |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Función de desaceleración |
      | `--vt-slide-down-distance` | `30%` | Distancia de deslizamiento |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Acercar</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Predeterminado | Descripción |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Duración de la animación |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Función de desaceleración |
      | `--vt-zoom-scale` | `0.8` | Factor de escala (el viejo se aleja a este valor, el nuevo se acerca desde este valor) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Alejar</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Predeterminado | Descripción |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Duración de la animación |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Función de desaceleración |
      | `--vt-zoom-out-scale` | `1.2` | Factor de escala (el viejo se acerca a este valor, el nuevo se aleja desde este valor) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Sobrescribiendo variables</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Para personalizar, sobrescribe estas variables en tu CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Para una personalización avanzada, dirígete a los pseudo-elementos de transición de vista directamente:

      ```css
      ::view-transition-old(vt-slide-left-exit) {
        animation-duration: 400ms;
      }

      ::view-transition-new(vt-slide-left-enter) {
        animation-timing-function: ease-out;
      }
      ```
    </div>
  </AccordionDetails>
</Accordion>
<br />
