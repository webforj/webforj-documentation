---
sidebar_position: 40
title: View Transitions
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: df97f8dc10601feff6a211aee0b4e9d7
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Viewtransities bieden geanimeerde overgangen wanneer de [DOM](/docs/glossary#dom) verandert, waardoor visuele schokken worden verminderd en de ruimtelijke context tijdens navigatie of content-updates behouden blijft. webforJ integreert met de [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) van de browser om de complexiteit van het coördineren van animaties tussen oude en nieuwe toestanden te beheren.

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

## Basisgebruik {#basic-usage}

Om een weergaveovergang te creëren, gebruik `Page.getCurrent().startViewTransition()`, dat een builder retourneert voor het configureren van de overgang:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Het overgangsproces legt een momentopname van de huidige staat vast, past je DOM-wijzigingen toe in de `onUpdate` callback, en animeert vervolgens van de oude momentopname naar de nieuwe inhoud. Je moet `done.run()` aanroepen om aan te geven wanneer je wijzigingen zijn voltooid.

:::warning De `onUpdate` callback is vereiste
Als je `start()` aanroept zonder een update callback in te stellen, resulteert dit in een `IllegalStateException`.
:::

## Toepassen van overgangen {#applying-transitions}

webforJ biedt vooraf gedefinieerde overgangstypen die je kunt toepassen op componenten die de DOM binnenkomen of verlaten:

| Constante | Effect |
|----------|--------|
| `ViewTransition.NONE` | Geen animatie |
| `ViewTransition.FADE` | Crossfade tussen oude en nieuwe inhoud |
| `ViewTransition.SLIDE_LEFT` | Inhoud stroomt naar links (zoals voortgangsnavigatie) |
| `ViewTransition.SLIDE_RIGHT` | Inhoud stroomt naar rechts (zoals terug navigatie) |
| `ViewTransition.SLIDE_UP` | Inhoud stroomt omhoog |
| `ViewTransition.SLIDE_DOWN` | Inhoud stroomt omlaag |
| `ViewTransition.ZOOM` | Oude inhoud krimpt, nieuwe inhoud groeit in |
| `ViewTransition.ZOOM_OUT` | Oude inhoud groeit weg, nieuwe inhoud krimpt in |

Gebruik `enter()` om een component dat wordt toegevoegd te animeren en `exit()` om een component dat wordt verwijderd te animeren:

```java
// Animeer een component die de DOM binnenkomt
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animeer een component die de DOM verlaat
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Gedeelde componentovergangen {#shared-component-transitions}

Gedeelde componentovergangen creëren een vervormingseffect waarbij een component lijkt te transformeren van zijn positie in de oude weergave naar zijn positie in de nieuwe weergave. Dit wordt bereikt door componenten dezelfde overgangsnaam te geven met behulp van de `setViewTransitionName()` methode, die beschikbaar is op elk component dat de <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface implementeert.

```java
// In de kaartweergave
image.setViewTransitionName("blog-image");

// In de detailweergave - dezelfde naam creëert de morph
image.setViewTransitionName("blog-image");
```

Tijdens de overgang tussen deze weergaven animeert de browser de component tussen posities, wat een verbonden visuele ervaring creëert.

:::tip Gebruik unieke namen
Wanneer je met lijsten of herhaalde componenten werkt, neem een unieke identifier op in de overgangsnaam. Elk component heeft zijn eigen unieke naam nodig om correct naar het bijbehorende component in de nieuwe weergave te vervormen. Het gebruik van dezelfde naam voor meerdere zichtbare componenten veroorzaakt ongedefinieerd gedrag.
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

### Lijstreordering {#list-reordering}

Een veelvoorkomende use case voor gedeelde componentovergangen is het animeren van lijstitems wanneer hun volgorde verandert. Door elke item een unieke `view-transition-name` toe te wijzen, animeert de browser automatisch componenten naar hun nieuwe posities:

```java
// Elke kaart krijgt een unieke overgangsnaam op basis van zijn ID
card.setViewTransitionName("card-" + item.id());

// Bij het schudden, update gewoon de DOM - de browser regelt de animatie
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

## Aangepaste CSS-animaties {#custom-css-animations}

Voor volledige controle over animaties kun je aangepaste CSS-keyframes definiëren. webforJ voegt `-enter` of `-exit` suffixes toe aan je overgangsnamen, die je gebruikt om de pseudo-elementen van de weergaveovergang te targeten:

```css
/* Definieer keyframes voor het binnenkomen van componenten */
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

/* Toepassen op het pseudo-element van de weergaveovergang */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Verwijs naar je aangepaste animatie door de naam ervan (zonder het suffix) door te geven aan `enter()` of `exit()`:

```java
// Gebruik "flip-in" - webforJ voegt automatisch "-enter" suffix toe
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Gebruik "blur-out" voor exit - webforJ voegt automatisch "-exit" suffix toe
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

## CSS-aanpassing {#css-customization}

Elk vooraf gedefinieerd overgangstype blootlegt CSS-aangepaste eigenschappen voor fijnafstelling:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Vervagen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animatieduur |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Links schuiven</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animatieduur |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-left-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Rechts schuiven</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animatieduur |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-right-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Omhoog schuiven</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animatieduur |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-up-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Omlaag schuiven</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animatieduur |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-down-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Inzoomen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animatieduur |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-zoom-scale` | `0.8` | Schaalfactor (oude zoomt naar buiten, nieuwe zoomt naar binnen) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Uitzoomen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animatieduur |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-zoom-out-scale` | `1.2` | Schaalfactor (oude zoomt naar binnen, nieuwe zoomt naar buiten) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Variabelen overschrijven</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Om aan te passen, overschrijf je deze variabelen in je CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Voor geavanceerde aanpassing, target de pseudo-elementen van de weergaveovergang direct:

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
