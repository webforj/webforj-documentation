---
sidebar_position: 40
title: View Transitions
_i18n_hash: 95d65a391ac0b11d6976acfc43691754
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View-Transitions ermöglichen animierte Übergänge, wenn sich der [DOM](/docs/glossary#dom) ändert, wodurch visuelles Ruckeln reduziert und der räumliche Kontext während der Navigation oder Inhaltsaktualisierungen beibehalten wird. webforJ integriert sich mit der [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) des Browsers, um die Komplexität der Koordination von Animationen zwischen alten und neuen Zuständen zu handhaben.

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

<ExperimentalWarning />

## Grundlegende Verwendung {#basic-usage}

Um einen View-Transition zu erstellen, verwenden Sie `Page.getCurrent().startViewTransition()`, das einen Builder zurückgibt, um die Transition zu konfigurieren:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Der Übergangsprozess erfasst einen Snapshot des aktuellen Zustands, wendet Ihre DOM-Änderungen im `onUpdate` Callback an und animiert dann vom alten Snapshot zum neuen Inhalt. Sie müssen `done.run()` aufrufen, um anzuzeigen, wann Ihre Änderungen abgeschlossen sind.

:::warning Der `onUpdate` Callback ist erforderlich
Ein Aufruf von `start()` ohne Setzen eines Update-Callbacks wirft eine `IllegalStateException`.
:::

## Anwendung von Übergängen {#applying-transitions}

webforJ bietet vordefinierte Übergangstypen, die Sie auf Komponenten anwenden können, die den DOM betreten oder verlassen:

| Konstante | Effekt |
|-----------|--------|
| `ViewTransition.NONE` | Keine Animation |
| `ViewTransition.FADE` | Überblenden zwischen altem und neuem Inhalt |
| `ViewTransition.SLIDE_LEFT` | Inhalt fließt nach links (wie bei einer Vorwärtsnavigation) |
| `ViewTransition.SLIDE_RIGHT` | Inhalt fließt nach rechts (wie bei einer Rücknavigation) |
| `ViewTransition.SLIDE_UP` | Inhalt fließt nach oben |
| `ViewTransition.SLIDE_DOWN` | Inhalt fließt nach unten |
| `ViewTransition.ZOOM` | Alter Inhalt schrumpft, neuer Inhalt wächst |
| `ViewTransition.ZOOM_OUT` | Alter Inhalt wächst, neuer Inhalt schrumpft |

Verwenden Sie `enter()`, um eine Komponente zu animieren, die hinzugefügt wird, und `exit()`, um eine Komponente zu animieren, die entfernt wird:

```java
// Animate a component entering the DOM
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animate a component exiting the DOM
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Geteilte Komponentenübergänge {#shared-component-transitions}

Geteilte Komponentenübergänge schaffen einen Morphing-Effekt, bei dem eine Komponente so aussieht, als würde sie sich von ihrer Position in der alten Ansicht zu ihrer Position in der neuen Ansicht verwandeln. Dies wird erreicht, indem den Komponenten denselben Übergangsname mit der Methode `setViewTransitionName()` gegeben wird, die auf jeder Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>-Interface implementiert.

```java
// In der Kartenansicht
image.setViewTransitionName("blog-image");

// In der Detailansicht - derselbe Name erzeugt das Morphing
image.setViewTransitionName("blog-image");
```

Beim Übergang zwischen diesen Ansichten animiert der Browser die Komponente zwischen den Positionen und schafft ein verbundenes visuelles Erlebnis.

:::tip Verwenden Sie eindeutige Namen
Wenn Sie mit Listen oder wiederholten Komponenten arbeiten, fügen Sie einen eindeutigen Bezeichner im Übergangsname hinzu. Jede Komponente benötigt ihren eigenen eindeutigen Namen, um korrekt zu ihrer entsprechenden Komponente in der neuen Ansicht zu morphieren. Die Verwendung desselben Namens für mehrere sichtbare Komponenten führt zu undefiniertem Verhalten.
:::

<ComponentDemo
  path='/webforj/viewtransitionmorph?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
    'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java'
  ]}
  cssURL='/css/viewtransitions/morph.css'
  height='650px'
/>

### Listenreihenfolge {#list-reordering}

Ein häufiger Anwendungsfall für geteilte Komponentenübergänge ist die Animation von Listenelementen, wenn sich deren Reihenfolge ändert. Durch die Zuweisung eines einzigartigen `view-transition-name` zu jedem Element animiert der Browser automatisch die Komponenten zu ihren neuen Positionen:

```java
// Jede Karte erhält einen einzigartigen Übergangsname basierend auf ihrer ID
card.setViewTransitionName("card-" + item.id());

// Beim Mischen einfach den DOM aktualisieren - der Browser kümmert sich um die Animation
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    renderList();
    done.run();
  })
  .start();
```

<ComponentDemo
  path='/webforj/viewtransitionshuffle?'
  javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java'
  ]}
  cssURL='/css/viewtransitions/shuffle.css'
  height='550px'
/>

## Benutzerdefinierte CSS-Animationen {#custom-css-animations}

Für volle Kontrolle über Animationen können Sie benutzerdefinierte CSS-Keyframes definieren. webforJ hängt Suffixe `-enter` oder `-exit` an Ihre Übergangsnamen an, die Sie verwenden, um die Pseudo-Elemente des View-Transitions anzusprechen:

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

/* Auf das Pseudo-Element der View-Transition anwenden */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Referenzieren Sie Ihre benutzerdefinierte Animation, indem Sie ihren Namen (ohne Suffix) an `enter()` oder `exit()` übergeben:

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
  path='/webforj/viewtransitionenterexit?'
  javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionEnterExitView.java'
  cssURL='/css/viewtransitions/enterexit.css'
  height='400px'
/>

## CSS-Anpassung {#css-customization}

Jeder vordefinierte Übergangstyp bietet CSS-Benutzerdefinierte Eigenschaften für Feinabstimmung:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fade</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|----------|-------------|
      | `--vt-fade-duration` | `200ms` | Animationsdauer |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
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
      |----------|----------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-left-distance` | `30%` | Gleitabstand |
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
      |----------|----------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-right-distance` | `30%` | Gleitabstand |
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
      |----------|----------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-up-distance` | `30%` | Gleitabstand |
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
      |----------|----------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-down-distance` | `30%` | Gleitabstand |
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
      |----------|----------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animationsdauer |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-zoom-scale` | `0.8` | Skalierungsfaktor (alter zoomt auf dieses, neuer zoomt von diesem) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom hinaus</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|----------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animationsdauer |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-zoom-out-scale` | `1.2` | Skalierungsfaktor (alter zoomt auf dieses, neuer zoomt von diesem) |
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

      Für erweiterte Anpassungen zielen Sie direkt auf die Pseudo-Elemente der View-Transition ab:

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
