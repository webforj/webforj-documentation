---
sidebar_position: 40
title: View Transitions
_i18n_hash: f906f47211e25b6b4bd659abdb1ad500
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Übergänge bieten animierte Übergänge, wenn sich der [DOM](/docs/glossary#dom) ändert, wodurch visuelles Stören verringert und der räumliche Kontext während der Navigation oder Inhaltsaktualisierungen aufrechterhalten wird. webforJ integriert sich mit der [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) des Browsers, um die Komplexität der Koordinierung von Animationen zwischen alten und neuen Zuständen zu handhaben.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/resources/static/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Grundlegende Nutzung {#basic-usage}

Um einen Übergang zu erstellen, verwenden Sie `Page.getCurrent().startViewTransition()`, das einen Builder zur Konfiguration des Übergangs zurückgibt:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Der Übergangsprozess erfasst eine Momentaufnahme des aktuellen Zustands, wendet Ihre DOM-Änderungen im `onUpdate` Callback an und animiert dann von der alten Momentaufnahme zum neuen Inhalt. Sie müssen `done.run()` aufrufen, um anzuzeigen, wann Ihre Änderungen abgeschlossen sind.

:::warning Der `onUpdate` Callback ist erforderlich
Das Aufrufen von `start()` ohne Setzen eines Update-Callbacks wirft eine `IllegalStateException`.
:::

## Anwenden von Übergängen {#applying-transitions}

webforJ bietet vordefinierte Übergangstypen, die Sie auf Komponenten anwenden können, die in den DOM ein- oder austreten:

| Konstante | Wirkung |
|-----------|---------|
| `ViewTransition.NONE` | Keine Animation |
| `ViewTransition.FADE` | Übereinanderblenden zwischen altem und neuem Inhalt |
| `ViewTransition.SLIDE_LEFT` | Inhalt fließt nach links (wie bei Vorwärtsnavigation) |
| `ViewTransition.SLIDE_RIGHT` | Inhalt fließt nach rechts (wie bei Zurücknavigation) |
| `ViewTransition.SLIDE_UP` | Inhalt fließt nach oben |
| `ViewTransition.SLIDE_DOWN` | Inhalt fließt nach unten |
| `ViewTransition.ZOOM` | Alter Inhalt schrumpft, neuer Inhalt wächst |
| `ViewTransition.ZOOM_OUT` | Alter Inhalt wächst, neuer Inhalt schrumpft |

Verwenden Sie `enter()`, um eine hinzukommende Komponente zu animieren, und `exit()`, um eine entfernte Komponente zu animieren:

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

## Übergänge für gemeinsame Komponenten {#shared-component-transitions}

Übergänge für gemeinsame Komponenten erzeugen einen Morphing-Effekt, bei dem eine Komponente scheinbar von ihrer Position im alten Ansicht in ihre Position in der neuen Ansicht übergeht. Dies wird erreicht, indem Komponenten denselben Übergangsnamen mithilfe der Methode `setViewTransitionName()` gegeben wird, die für jede Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> Interface implementiert.

```java
// Im Kartenansicht
image.setViewTransitionName("blog-image");

// In der Detailansicht - derselbe Name erzeugt das Morph
image.setViewTransitionName("blog-image");
```

Beim Übergang zwischen diesen Ansichten animiert der Browser die Komponente zwischen den Positionen und schafft ein verbundenes visuelles Erlebnis.

:::tip Verwenden Sie eindeutige Namen
Wenn Sie mit Listen oder wiederholten Komponenten arbeiten, fügen Sie eine eindeutige Kennung in den Übergangsnamen ein. Jede Komponente benötigt ihren eigenen verschiedenen Namen, um korrekt zur entsprechenden Komponente in der neuen Ansicht zu morphieren. Die Verwendung des gleichen Namens für mehrere sichtbare Komponenten führt zu undefiniertem Verhalten.
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/resources/static/css/viewtransitions/morph.css',
]}
height='650px'
/>

### Listen Neuordnung {#list-reordering}

Ein häufiges Anwendungsfall für Übergänge von gemeinsamen Komponenten ist das Animieren von Listenelementen, wenn sich ihre Reihenfolge ändert. Indem Sie jedem Element einen einzigartigen `view-transition-name` zuweisen, animiert der Browser automatisch die Komponenten zu ihren neuen Positionen:

```java
// Jede Karte erhält einen einzigartigen Übergangsnamen basierend auf ihrer ID
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
path='/webforj/viewtransitionshuffle'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java',
  'src/main/resources/static/css/viewtransitions/shuffle.css',
]}
height='550px'
/>

## Benutzerdefinierte CSS-Animationen {#custom-css-animations}

Für vollständige Kontrolle über Animationen können Sie benutzerdefinierte CSS-Keyframes definieren. webforJ fügt den Übergangsnamen die Suffixe `-enter` oder `-exit` hinzu, die Sie verwenden, um die Pseudo-Elemente des Übergangs zu steuern:

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

/* Auf das Pseudo-Element des Übergangs anwenden */
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

// Verwenden Sie "blur-out" für den Ausstieg - webforJ fügt automatisch das Suffix "-exit" hinzu
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
  'src/main/resources/static/css/viewtransitions/enterexit.css',
]}
height='400px'
/>

## CSS-Anpassung {#css-customization}

Jeder vordefinierte Übergangstyp bietet CSS-Custom-Properties zur Feinabstimmung:

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
    <strong>Nach links gleiten</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animationsdauer |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-slide-left-distance` | `30%` | Gleitschance |
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
      | `--vt-slide-right-distance` | `30%` | Gleitschance |
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
      | `--vt-slide-up-distance` | `30%` | Gleitschance |
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
      | `--vt-slide-down-distance` | `30%` | Gleitschance |
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
      | `--vt-zoom-scale` | `0.8` | Skalierungsfaktor (alter zoomt auf dies, neuer zoomt davon ein) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom raus</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Standard | Beschreibung |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animationsdauer |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-Funktion |
      | `--vt-zoom-out-scale` | `1.2` | Skalierungsfaktor (alter zoomt auf, neuer zoomt davon aus) |
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

      Für erweiterte Anpassungen zielen Sie direkt auf die Pseudo-Elemente des Übergangs ab:

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
