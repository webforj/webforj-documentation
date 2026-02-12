---
sidebar_position: 40
title: View Transitions
sidebar_class_name: new-content
_i18n_hash: eb57126d50375aa6da9197fa846291ff
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View transitions bieten animierte Übergänge, wenn sich der [DOM](/docs/glossary#dom) ändert, wodurch visuelle Störungen verringert und der räumliche Kontext während der Navigation oder Inhaltsaktualisierungen erhalten bleibt. webforJ integriert sich in die [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) des Browsers, um die Komplexität der Koordination von Animationen zwischen alten und neuen Zuständen zu bewältigen.

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

:::warning Experimentelle API
Diese API ist seit 25.11 als experimentell gekennzeichnet und kann sich in zukünftigen Versionen ändern. Die API-Signatur, das Verhalten und die Leistungseigenschaften können modifiziert werden.
:::

## Grundlegende Verwendung {#basic-usage}

Um einen View-Übergang zu erstellen, verwenden Sie `Page.getCurrent().startViewTransition()`, das einen Builder zur Konfiguration des Übergangs zurückgibt:

```java
Page.getCurrent().startViewTransition()
    .onUpdate(done -> {
        container.remove(oldView);
        container.add(newView);
        done.run();
    })
    .start();
```

Der Übergangsprozess erfasst einen Snapshot des aktuellen Zustands, wendet Ihre DOM-Änderungen im `onUpdate`-Callback an und animiert dann vom alten Snapshot zu den neuen Inhalten. Sie müssen `done.run()` aufrufen, um anzuzeigen, wann Ihre Änderungen abgeschlossen sind.

:::warning Der `onUpdate`-Callback ist erforderlich
Aufruf von `start()`, ohne einen Aktualisierungs-Callback festzulegen, löst eine `IllegalStateException` aus.
:::

## Anwenden von Übergängen {#applying-transitions}

webforJ bietet vordefinierte Übergangstypen, die Sie auf Komponenten anwenden können, die in den DOM eintreten oder ihn verlassen:

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

Verwenden Sie `enter()`, um eine Komponente, die hinzugefügt wird, zu animieren, und `exit()`, um eine Komponente, die entfernt wird, zu animieren:

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

Gemeinsame Komponentenübergänge erzeugen einen Morphing-Effekt, bei dem eine Komponente anscheinend von ihrer Position in der alten Ansicht zu ihrer Position in der neuen Ansicht transformiert wird. Dies wird erreicht, indem den Komponenten derselbe Übergangsname mit der Methode `setViewTransitionName()` zugewiesen wird, die für jede Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>-Interface implementiert.

```java
// In der Kartenansicht
image.setViewTransitionName("blog-image");

// In der Detailansicht - derselbe Name erzeugt das Morph
image.setViewTransitionName("blog-image");
```

Bei der Übergangs zwischen diesen Ansichten animiert der Browser die Komponente zwischen den Positionen und schafft ein verbundenes visuelles Erlebnis.

:::tip Verwenden Sie einzigartige Namen
Wenn Sie mit Listen oder wiederholten Komponenten arbeiten, fügen Sie eine eindeutige Kennung im Übergangsname hinzu. Jede Komponente benötigt ihren eigenen einzigartigen Namen, um korrekt zu ihrer entsprechenden Komponente in der neuen Ansicht zu morphieren. Die Verwendung desselben Namens für mehrere sichtbare Komponenten führt zu undefiniertem Verhalten.
:::

<ComponentDemo
  path='/webforj/viewtransitionmorph?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java'
  ]}
  cssURL='/css/viewtransitions/morph.css'
  height='650px'
/>

### Listenneuanordnung {#list-reordering}

Ein häufiger Anwendungsfall für gemeinsame Komponentenübergänge besteht darin, Listenelemente zu animieren, wenn sich deren Reihenfolge ändert. Durch die Zuordnung eines eindeutigen `view-transition-name`-Wertes zu jedem Element animiert der Browser die Komponenten automatisch in ihre neuen Positionen:

```java
// Jede Karte erhält einen einzigartigen Übergangsname basierend auf ihrer ID
card.setViewTransitionName("card-" + item.id());

// Beim Mischen, aktualisieren Sie einfach den DOM - der Browser übernimmt die Animation
Page.getCurrent().startViewTransition()
    .onUpdate(done -> {
        renderList();
        done.run();
    })
    .start();
```

<ComponentDemo
  path='/webforj/viewtransitionshuffle?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java'
  urls={[
    'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java'
  ]}
  cssURL='/css/viewtransitions/shuffle.css'
  height='550px'
/>

## Benutzerdefinierte CSS-Animationen {#custom-css-animations}

Um vollständige Kontrolle über Animationen zu haben, können Sie benutzerdefinierte CSS-Keyframes definieren. webforJ fügt den Übergangsname die Suffixe `-enter` oder `-exit` hinzu, die Sie verwenden, um die Pseudo-Elemente des View-Übergangs anzusprechen:

```css
/* Definieren von Keyframes für eintrittende Komponenten */
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

// Verwenden Sie "blur-out" für den Austritt - webforJ fügt automatisch das Suffix "-exit" hinzu
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
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionEnterExitView.java'
  cssURL='/css/viewtransitions/enterexit.css'
  height='400px'
/>

## CSS-Anpassung {#css-customization}

Jeder vordefinierte Übergangstyp stellt CSS-Benutzerdefinierte Eigenschaften zur Feinabstimmung zur Verfügung:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fade</strong>
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
    <strong>Links gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
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
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
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
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
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
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
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
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-zoom-scale` | `0.8` | Skalierungsfaktor (alter zoomt auf diesen Wert, neuer zoomt von diesem Wert ein) |
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
      | `--vt-zoom-out-scale` | `1.2` | Skalierungsfaktor (alter zoomt auf diesen Wert, neuer zoomt von diesem Wert aus) |
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

      Für umfassende Anpassungen adressieren Sie direkt die Pseudo-Elemente des Endübergangs:

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
