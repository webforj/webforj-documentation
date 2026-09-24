---
sidebar_position: 40
title: Ansichtsübergänge
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: df97f8dc10601feff6a211aee0b4e9d7
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View-Übergänge bieten animierte Übergänge, wenn sich der [DOM](/docs/glossary#dom) ändert, wodurch visuelles Ruckeln reduziert und der räumliche Kontext während der Navigation oder Inhaltsaktualisierungen beibehalten wird. webforJ integriert sich mit der [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) des Browsers, um die Komplexität der Koordination von Animationen zwischen alten und neuen Zuständen zu bewältigen.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
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

Der Übergangsprozess erfasst einen Snapshot des aktuellen Zustands, wendet Ihre DOM-Änderungen im `onUpdate`-Callback an und animiert dann vom alten Snapshot zum neuen Inhalt. Sie müssen `done.run()` aufrufen, um anzuzeigen, wann Ihre Änderungen abgeschlossen sind.

:::warning Der `onUpdate`-Callback ist erforderlich
Ein Aufruf von `start()` ohne Setzen eines Update-Callbacks löst eine `IllegalStateException` aus.
:::

## Anwendung von Übergängen {#applying-transitions}

webforJ bietet vordefinierte Übergangstypen, die Sie auf Komponenten anwenden können, die in den DOM ein- oder austreten:

| Konstante | Effekt |
|-----------|--------|
| `ViewTransition.NONE` | Keine Animation |
| `ViewTransition.FADE` | Überblenden zwischen alten und neuen Inhalten |
| `ViewTransition.SLIDE_LEFT` | Inhalt fließt nach links (wie bei einer Vorwärtsnavigation) |
| `ViewTransition.SLIDE_RIGHT` | Inhalt fließt nach rechts (wie bei einer Rücknavigation) |
| `ViewTransition.SLIDE_UP` | Inhalt fließt nach oben |
| `ViewTransition.SLIDE_DOWN` | Inhalt fließt nach unten |
| `ViewTransition.ZOOM` | Alter Inhalt schrumpft, neuer Inhalt wächst |
| `ViewTransition.ZOOM_OUT` | Alter Inhalt wächst, neuer Inhalt schrumpft |

Verwenden Sie `enter()`, um eine Komponente hinzuzufügen, und `exit()`, um eine Komponente zu entfernen:

```java
// Animieren einer Komponente, die in den DOM eintritt
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animieren einer Komponente, die den DOM verlässt
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Gemeinsame Komponentenübergänge {#shared-component-transitions}

Gemeinsame Komponentenübergänge erzeugen einen Morphing-Effekt, bei dem eine Komponente so aussieht, als würde sie sich von ihrer Position im alten View in ihre Position im neuen View verwandeln. Dies wird erreicht, indem Komponenten denselben Übergangsname mit der Methode `setViewTransitionName()` zugewiesen wird, die auf jeder Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>-Interface implementiert.

```java
// Im Kartenansicht
image.setViewTransitionName("blog-image");

// Im Detailansicht - derselbe Name erzeugt den Morph
image.setViewTransitionName("blog-image");
```

Beim Übergang zwischen diesen Ansichten animiert der Browser die Komponente zwischen den Positionen und schafft ein verbundenes visuelles Erlebnis.

:::tip Verwenden Sie eindeutige Namen
Wenn Sie mit Listen oder wiederholten Komponenten arbeiten, fügen Sie eine eindeutige Identifikation in den Übergangsname ein. Jede Komponente benötigt ihren eigenen ununterscheidbaren Namen, um korrekt zu ihrem entsprechenden Element im neuen View zu morphen. Die Verwendung desselben Namens für mehrere sichtbare Komponenten führt zu undefiniertem Verhalten.
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/frontend/css/viewtransitions/morph.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='650px'
/>

### Listenreihenfolge ändern {#list-reordering}

Ein gängiger Anwendungsfall für gemeinsame Komponentenübergänge ist die Animation von Listenelementen, wenn sich ihre Reihenfolge ändert. Durch Zuordnung eines eindeutigen `view-transition-name` zu jedem Element animiert der Browser die Komponenten automatisch zu ihren neuen Positionen:

```java
// Jede Karte erhält einen eindeutigen Übergangsname basierend auf ihrer ID
card.setViewTransitionName("card-" + item.id());

// Beim Mischen einfach den DOM aktualisieren - der Browser übernimmt die Animation
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
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='550px'
/>

## Benutzerdefinierte CSS-Animationen {#custom-css-animations}

Für vollständige Kontrolle über Animationen können Sie benutzerdefinierte CSS-Keyframes definieren. webforJ fügt den Übergangsname `-enter` oder `-exit` Suffixe hinzu, die Sie verwenden, um die Pseudo-Elemente des View-Übergangs zu targetieren:

```css
/* Definieren Sie Keyframes für eingehende Komponenten */
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

/* Auf das Pseudo-Element des View-Übergangs anwenden */
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

// Verwenden Sie "blur-out" für den Exit - webforJ fügt automatisch das Suffix "-exit" hinzu
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
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='400px'
/>

## CSS-Anpassung {#css-customization}

Jeder vordefinierte Übergangsname stellt CSS-Custom-Properties für Feinabstimmungen bereit:

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
    <strong>Nach links gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-left-distance` | `30%` | Gleitdistanz |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Nach rechts gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-right-distance` | `30%` | Gleitdistanz |
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
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-up-distance` | `30%` | Gleitdistanz |
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
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-down-distance` | `30%` | Gleitdistanz |
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
      | `--vt-zoom-scale` | `0.8` | Skalierungsfaktor (alter zoomt hinaus, neuer zoomt herein) |
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
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-zoom-out-scale` | `1.2` | Skalierungsfaktor (alter zoomt herein, neuer zoomt hinaus) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Variablen überschreiben</strong>
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

      Für erweiterte Anpassungen targeten Sie die Pseudo-Elemente des View-Übergangs direkt:

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
