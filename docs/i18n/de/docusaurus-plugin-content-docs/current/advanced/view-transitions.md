---
sidebar_class_name: experimental-content
sidebar_position: 40
title: View Transitions
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: 3440413f572744ea709085a2975c0023
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Ansichtsübergänge bieten animierte Übergänge, wenn sich das [DOM](/docs/glossary#dom) ändert, was visuelle Störungen verringert und den räumlichen Kontext während der Navigation oder Inhaltsaktualisierungen aufrechterhält. webforJ integriert sich mit der [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) des Browsers, um die Komplexität der Koordination von Animationen zwischen alten und neuen Zuständen zu handhaben.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
  'src/main/java/com/webforj/samples.views.viewtransitions.components.ChatCard.java',
  'src/main/frontend/css/viewtransitions/components/chat-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='450px'
/>

<ExperimentalWarning />

Verwenden Sie `Page.getCurrent().startViewTransition()`, um einen Übergang zu erstellen, der einen Builder zurückgibt, um den Übergang zu konfigurieren:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Der Übergangsprozess erfasst einen Snapshot des aktuellen Zustands, wendet Ihre DOM-Änderungen im `onUpdate`-Rückruf an und animiert dann vom alten Snapshot zum neuen Inhalt. Sie müssen `done.run()` aufrufen, um zu signalisieren, wann Ihre Änderungen abgeschlossen sind.

:::warning Der `onUpdate`-Rückruf ist erforderlich
Ein Aufruf von `start()` ohne Festlegung eines Aktualisierungsrückrufs löst eine `IllegalStateException` aus.
:::

## Anwendung von Übergängen {#applying-transitions}

webforJ bietet vordefinierte Übergangsarten, die Sie auf Komponenten anwenden können, die in das DOM eintreten oder es verlassen:

| Konstante | Effekt |
|-----------|--------|
| `ViewTransition.NONE` | Keine Animation |
| `ViewTransition.FADE` | Überblenden zwischen alten und neuen Inhalten |
| `ViewTransition.SLIDE_LEFT` | Inhalt fließt nach links (wie bei der Vorwärtsnavigation) |
| `ViewTransition.SLIDE_RIGHT` | Inhalt fließt nach rechts (wie bei der Rücknavigation) |
| `ViewTransition.SLIDE_UP` | Inhalt fließt nach oben |
| `ViewTransition.SLIDE_DOWN` | Inhalt fließt nach unten |
| `ViewTransition.ZOOM` | Alter Inhalt schrumpft weg, neuer Inhalt wächst hinein |
| `ViewTransition.ZOOM_OUT` | Alter Inhalte wächst weg, neuer Inhalt schrumpft hinein |

Verwenden Sie `enter()`, um eine Komponente zu animieren, die hinzugefügt wird, und `exit()`, um eine Komponente zu animieren, die entfernt wird:

```java
// Animieren einer Komponente, die in das DOM eintritt
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animieren einer Komponente, die das DOM verlässt
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Geteilte Komponentenübergänge {#shared-component-transitions}

Geteilte Komponentenübergänge erzeugen einen Morphing-Effekt, bei dem eine Komponente so aussieht, als ob sie von ihrer Position in der alten Ansicht zu ihrer Position in der neuen Ansicht transformiert wird. Dies wird erreicht, indem den Komponenten derselbe Übergangsnamen über die Methode `setViewTransitionName()` gegeben wird, die auf jeder Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>-Interface implementiert.

```java
// In der Kartenansicht
image.setViewTransitionName("blog-image");

// In der Detailansicht - derselbe Name erzeugt den Morph
image.setViewTransitionName("blog-image");
```

Beim Übergang zwischen diesen Ansichten animiert der Browser die Komponente zwischen den Positionen und erzeugt ein verbundenes visuelles Erlebnis.

:::tip Verwenden Sie eindeutige Namen
Wenn Sie mit Listen oder wiederholten Komponenten arbeiten, fügen Sie einen eindeutigen Bezeichner im Übergangsname hinzu. Jede Komponente benötigt ihren eigenen eindeutigen Namen, um korrekt zum entsprechenden Element in der neuen Ansicht zu morphieren. Die Verwendung desselben Namens für mehrere sichtbare Komponenten führt zu undefiniertem Verhalten.
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/frontend/css/viewtransitions/morph.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/frontend/css/viewtransitions/components/blog-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='650px'
/>

### Neuordnung von Listen {#list-reordering}

Ein häufiger Anwendungsfall für geteilte Komponentenübergänge ist das Animieren von Listenelementen, wenn sich ihre Reihenfolge ändert. Durch das Zuweisen eines eindeutigen `view-transition-name` zu jedem Element animiert der Browser die Komponenten automatisch zu ihren neuen Positionen:

```java
// Jede Karte erhält einen eindeutigen Übergangsname basierend auf ihrer ID
card.setViewTransitionName("card-" + item.id());

// Beim Mischen einfach das DOM aktualisieren - der Browser übernimmt die Animation
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
  'src/main/frontend/css/viewtransitions/shuffle.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java',
  'src/main/frontend/css/viewtransitions/components/shuffle-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='550px'
/>

## Benutzerdefinierte CSS-Animationen {#custom-css-animations}

Für die vollständige Kontrolle über Animationen können Sie benutzerdefinierte CSS-Keyframes definieren. webforJ fügt den Übergangsname die Suffixe `-enter` oder `-exit` hinzu, die Sie verwenden, um die Pseudo-Elemente für den Ansichtsübergang anzusprechen:

```css
/* Keyframes für eintretende Komponenten definieren */
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

/* Auf das Pseudo-Element des Ansichtsübergangs anwenden */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Referenzieren Sie Ihre benutzerdefinierte Animation, indem Sie ihren Namen (ohne das Suffix) an `enter()` oder `exit()` übergeben:

```java
// Verwenden Sie "flip-in" - webforJ fügt automatisch das Suffix "-enter" hinzu
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Verwenden Sie "blur-out" für den Ausgang - webforJ fügt automatisch das Suffix "-exit" hinzu
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
  'src/main/java/com/webforj/samples/views/viewtransitions/components/NotificationCard.java',
  'src/main/frontend/css/viewtransitions/components/notification-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='400px'
/>

## CSS-Anpassung {#css-customization}

Jede vordefinierte Übergangsart bietet CSS-Benutzereigenschaften zur Feinabstimmung:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fade</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animationsdauer |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Bezier-Funktion |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Links gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Bezier-Funktion |
      | `--vt-slide-left-distance` | `30%` | Gleiten-Distanz |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Rechts gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Bezier-Funktion |
      | `--vt-slide-right-distance` | `30%` | Gleiten-Distanz |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Nach oben gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Bezier-Funktion |
      | `--vt-slide-up-distance` | `30%` | Gleiten-Distanz |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Nach unten gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Bezier-Funktion |
      | `--vt-slide-down-distance` | `30%` | Gleiten-Distanz |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animationsdauer |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Bezier-Funktion |
      | `--vt-zoom-scale` | `0.8` | Skalierungsfaktor (alter Inhalt zoomt auf diesen Wert, neuer zoomt von diesem Wert) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom aus</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animationsdauer |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Bezier-Funktion |
      | `--vt-zoom-out-scale` | `1.2` | Skalierungsfaktor (alter Inhalt zoomt auf diesen Wert, neuer zoomt von diesem Wert) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Überschreiben von Variablen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Um anzupassen, überschreiben Sie diese Variablen in Ihrem CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Für erweiterte Anpassungen, sprechen Sie die Pseudo-Elemente des Ansichtsübergangs direkt an:

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
