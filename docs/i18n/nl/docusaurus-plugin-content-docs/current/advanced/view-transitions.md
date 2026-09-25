---
sidebar_class_name: experimental-content
sidebar_position: 40
title: View Transitions
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: 28ce066594fd539d6265eedfab52c2b0
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Weergaveovergangen bieden geanimeerde overgangen wanneer de [DOM](/docs/glossary#dom) verandert, waardoor visuele schok wordt verminderd en de ruimtelijke context tijdens navigatie of inhoudsupdates behouden blijft. webforJ integreert met de [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) van de browser om de complexiteit van het coördineren van animaties tussen oude en nieuwe toestanden te beheren.

<!-- INTRO_END -->

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

Gebruik `Page.getCurrent().startViewTransition()` om een overgang te creëren, wat een builder retourneert om de overgang te configureren:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Het overgangsproces legt een momentopname van de huidige staat vast, past uw DOM-wijzigingen toe in de `onUpdate` callback en animeert vervolgens van de oude momentopname naar de nieuwe inhoud. U moet `done.run()` aanroepen om te signaleren wanneer uw wijzigingen zijn voltooid.

:::warning De `onUpdate` callback is vereist
Aanroepen van `start()` zonder het instellen van een update callback leidt tot een `IllegalStateException`.
:::

## Toepassen van overgangen {#applying-transitions}

webforJ biedt vooraf gedefinieerde overgangstypen die u kunt toepassen op componenten die de DOM binnenkomen of verlaten:

| Constant | Effect |
|----------|--------|
| `ViewTransition.NONE` | Geen animatie |
| `ViewTransition.FADE` | Crossfade tussen oude en nieuwe inhoud |
| `ViewTransition.SLIDE_LEFT` | Inhoud stroomt naar links (zoals voorwaartse navigatie) |
| `ViewTransition.SLIDE_RIGHT` | Inhoud stroomt naar rechts (zoals terugwaartse navigatie) |
| `ViewTransition.SLIDE_UP` | Inhoud stroomt omhoog |
| `ViewTransition.SLIDE_DOWN` | Inhoud stroomt omlaag |
| `ViewTransition.ZOOM` | Oude inhoud krimpt weg, nieuwe inhoud groeit in |
| `ViewTransition.ZOOM_OUT` | Oude inhoud groeit weg, nieuwe inhoud krimpt in |

Gebruik `enter()` om een component die wordt toegevoegd te animeren en `exit()` om een component die wordt verwijderd te animeren:

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

## Gedeelde componentovergangen {#shared-component-transitions}

Gedeelde componentovergangen creëren een vervormingseffect waarbij een component lijkt te transformeren van zijn positie in de oude weergave naar zijn positie in de nieuwe weergave. Dit wordt bereikt door componenten dezelfde overgangsnaam te geven met behulp van de `setViewTransitionName()` methode, beschikbaar op elke component die de <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface implementeert.

```java
// In de kaartweergave
image.setViewTransitionName("blog-image");

// In de detailweergave - dezelfde naam creëert de vervorming
image.setViewTransitionName("blog-image");
```

Bij het overgaan tussen deze weergaven animeert de browser de component tussen posities, waardoor een verbonden visuele ervaring ontstaat.

:::tip Gebruik unieke namen
Bij het werken met lijsten of herhaalde componenten moet u een unieke identificator in de overgangsnaam opnemen. Elke component heeft een eigen unieke naam nodig om correct te vervormen naar de bijbehorende component in de nieuwe weergave. Het gebruik van dezelfde naam voor meerdere zichtbare componenten veroorzaakt onvoorspelbaar gedrag.
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

Een veelvoorkomend gebruiksvoorbeeld voor gedeelde componentovergangen is het animeren van lijstitems wanneer hun volgorde verandert. Door een unieke `view-transition-name` aan elk item toe te wijzen, animeert de browser automatisch componenten naar hun nieuwe posities:

```java
// Elke kaart krijgt een unieke overgangsnaam op basis van zijn ID
card.setViewTransitionName("card-" + item.id());

// Bij schudden, werk gewoon de DOM bij - de browser beheert de animatie
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

Voor volledige controle over animaties kunt u aangepaste CSS-keyframes definiëren. webforJ voegt `-enter` of `-exit` suffixen toe aan uw overgangsnaam, die u gebruikt om naar de view transition pseudo-elementen te verwijzen:

```css
/* Definieer keyframes voor binnenkomende componenten */
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

/* Toepassen op het view transition pseudo-element */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Verwijs naar uw aangepaste animatie door de naam (zonder de suffix) door te geven aan `enter()` of `exit()`:

```java
// Gebruik "flip-in" - webforJ voegt automatisch het "-enter" suffix toe
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Gebruik "blur-out" voor exit - webforJ voegt automatisch het "-exit" suffix toe
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

Elk vooraf gedefinieerd overgangstype exposeert CSS custom properties voor fijne afstemming:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Vervagen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animatieduur |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easingfunctie |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide naar links</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animatieduur |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easingfunctie |
      | `--vt-slide-left-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide naar rechts</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animatieduur |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easingfunctie |
      | `--vt-slide-right-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide omhoog</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animatieduur |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easingfunctie |
      | `--vt-slide-up-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide omlaag</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animatieduur |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easingfunctie |
      | `--vt-slide-down-distance` | `30%` | Schuifafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animatieduur |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easingfunctie |
      | `--vt-zoom-scale` | `0.8` | Schaalfactor (oude zoomt hiernaar uit, nieuwe zoomt hier van in) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom uit</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animatieduur |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easingfunctie |
      | `--vt-zoom-out-scale` | `1.2` | Schaalfactor (oude zoomt hiernaar in, nieuwe zoomt hieruit van) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Variabelen overschrijven</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Om aanpassingen te maken, overschrijft u deze variabelen in uw CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Voor geavanceerde aanpassing, richt u zich rechtstreeks op de view transition pseudo-elementen:

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
