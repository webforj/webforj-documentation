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

View transitions bieten animierte Übergänge, wenn sich der [DOM](/docs/glossary#dom) ändert, wodurch visuelle Störungen reduziert und der räumliche Kontext während der Navigation oder Inhaltsaktualisierungen beibehalten wird. webforJ integriert sich mit der [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) des Browsers, um die Komplexität der Koordination von Animationen zwischen alten und neuen Zuständen zu bewältigen.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Grundlegende Verwendung {#basic-usage}

Um einen View-Übergang zu erstellen, verwenden Sie `Page.getCurrent().startViewTransition()`, das einen Builder zurückgibt, um den Übergang zu konfigurieren:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Der Übergangsprozess erfasst einen Snapshot des aktuellen Zustands, wendet Ihre DOM-Änderungen im `onUpdate` Callback an und animiert dann vom alten Snapshot zum neuen Inhalt. Sie müssen `done.run()` aufrufen, um zu signalisieren, wann Ihre Änderungen abgeschlossen sind.

:::warning Der `onUpdate` Callback ist erforderlich
Ein Aufruf von `start()` ohne das Setzen eines Update-Callbacks löst eine `IllegalStateException` aus.
:::

## Anwendung von Übergängen {#applying-transitions}

webforJ bietet vordefinierte Übergangstypen, die Sie auf Komponenten anwenden können, die in den DOM eintreten oder ihn verlassen:

| Konstante | Effekt |
|-----------|--------|
| `ViewTransition.NONE` | Keine Animation |
| `ViewTransition.FADE` | Überblenden zwischen alten und neuen Inhalten |
| `ViewTransition.SLIDE_LEFT` | Inhalt fließt nach links (wie eine Vorwärtsnavigation) |
| `ViewTransition.SLIDE_RIGHT` | Inhalt fließt nach rechts (wie eine Rücknavigation) |
| `ViewTransition.SLIDE_UP` | Inhalt fließt nach oben |
| `ViewTransition.SLIDE_DOWN` | Inhalt fließt nach unten |
| `ViewTransition.ZOOM` | Alter Inhalt schrumpft, neuer Inhalt wächst |
| `ViewTransition.ZOOM_OUT` | Alter Inhalt wächst, neuer Inhalt schrumpft |

Verwenden Sie `enter()`, um eine Komponente hinzuzufügen, und `exit()`, um eine Komponente zu entfernen:

```java
// Animieren Sie eine Komponente, die in den DOM eintritt
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animieren Sie eine Komponente, die den DOM verlässt
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Gemeinsame Komponentenübergänge {#shared-component-transitions}

Gemeinsame Komponentenübergänge schaffen einen Morphing-Effekt, bei dem eine Komponente so erscheint, als würde sie sich von ihrer Position im alten View zu ihrer Position im neuen View transformieren. Dies wird erreicht, indem Komponenten denselben Übergangsname mit der Methode `setViewTransitionName()` zugewiesen wird, die auf jeder Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> Interface implementiert.

```java
// Im Kartenansicht
image.setViewTransitionName("blog-image");

// In der Detailansicht - derselbe Name erzeugt das Morph
image.setViewTransitionName("blog-image");
```

Beim Übergang zwischen diesen Ansichten animiert der Browser die Komponente zwischen den Positionen und schafft so ein verbundenes visuelles Erlebnis.

:::tip Verwenden Sie eindeutige Namen
Wenn Sie mit Listen oder wiederholten Komponenten arbeiten, fügen Sie eine eindeutige Kennung im Übergangsname hinzu. Jede Komponente benötigt ihren eigenen eindeutigen Namen, um richtig mit ihrer entsprechenden Komponente im neuen View zu morphen. Die Verwendung desselben Namens für mehrere sichtbare Komponenten führt zu undefiniertem Verhalten.
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

### Listenreihenfolge {#list-reordering}

Ein häufiger Anwendungsfall für gemeinsame Komponentenübergänge ist die Animation von Listenelementen, wenn sich deren Reihenfolge ändert. Durch die Zuweisung eines eindeutigen `view-transition-name` zu jedem Element animiert der Browser die Komponenten automatisch in ihre neuen Positionen:

```java
// Jede Karte erhält einen einzigartigen Übergangsname basierend auf ihrer ID
card.setViewTransitionName("card-" + item.id());

// Beim Mischen aktualisieren Sie einfach den DOM - der Browser kümmert sich um die Animation
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

## Benutzerdefinierte CSS-Animationen {#custom-css-animations}

Für die vollständige Kontrolle über Animationen können Sie benutzerdefinierte CSS-Keyframes definieren. webforJ fügt die Suffixe `-enter` oder `-exit` zu Ihren Übergangsname hinzu, die Sie verwenden, um die Pseudo-Elemente des View-Übergangs anzusprechen:

```css
/* Definieren Sie Keyframes für eintretende Komponenten */
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

/* Anwenden auf das Pseudo-Element des View-Übergangs */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Verweisen Sie auf Ihre benutzerdefinierte Animation, indem Sie ihren Namen (ohne das Suffix) an `enter()` oder `exit()` übergeben:

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
]}
height='400px'
/>

## CSS-Anpassung {#css-customization}

Jeder vordefinierte Übergangstyp bietet CSS-Anpassungseigenschaften für Feinabstimmungen:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Überblenden</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animationsdauer |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Links rutschen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-left-distance` | `30%` | Rutschentfernung |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Rechts rutschen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-right-distance` | `30%` | Rutschentfernung |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Nach oben rutschen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-up-distance` | `30%` | Rutschentfernung |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Nach unten rutschen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-down-distance` | `30%` | Rutschentfernung |
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
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-zoom-scale` | `0.8` | Skalierungsfaktor (alter zoomt auf diesen, neuer zoomt von diesem) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom out</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animationsdauer |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-zoom-out-scale` | `1.2` | Skalierungsfaktor (alter zoomt zu diesem, neuer zoomt von diesem) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Variablen überschreiben</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Um diese Variablen anzupassen, überschreiben Sie sie in Ihrem CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Für erweiterte Anpassungen richten Sie direkt auf die Pseudo-Elemente des View-Übergangs aus:

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
