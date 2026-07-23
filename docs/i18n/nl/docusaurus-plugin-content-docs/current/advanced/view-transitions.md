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

Bekijkovergangen bieden geanimeerde overgangen wanneer de [DOM](/docs/glossary#dom) verandert, waardoor visuele schokken worden verminderd en de ruimtelijke context behouden blijft tijdens navigatie of inhoudsupdates. webforJ is geïntegreerd met de [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) van de browser om de complexiteit van het coördineren van animaties tussen oude en nieuwe toestanden te behandelen.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Basisgebruik {#basic-usage}

Om een kijkovergang te creëren, gebruik `Page.getCurrent().startViewTransition()`, dat een builder retourneert voor het configureren van de overgang:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Het overgangsproces legt een momentopname van de huidige staat vast, past uw DOM-wijzigingen toe in de `onUpdate` callback en animeert vervolgens van de oude momentopname naar de nieuwe inhoud. U moet `done.run()` aanroepen om aan te geven wanneer uw wijzigingen zijn voltooid.

:::warning De `onUpdate` callback is vereist
Aanroepen van `start()` zonder een update-callback in te stellen, werpt een `IllegalStateException`.
:::

## Overgangen toepassen {#applying-transitions}

webforJ biedt vooraf gedefinieerde overgangstypes die u kunt toepassen op componenten die de DOM binnenkomen of verlaten:

| Constant | Effect |
|----------|--------|
| `ViewTransition.NONE` | Geen animatie |
| `ViewTransition.FADE` | Crossfade tussen oude en nieuwe inhoud |
| `ViewTransition.SLIDE_LEFT` | Inhoud beweegt naar links (zoals voorwaartse navigatie) |
| `ViewTransition.SLIDE_RIGHT` | Inhoud beweegt naar rechts (zoals terug navigatie) |
| `ViewTransition.SLIDE_UP` | Inhoud beweegt omhoog |
| `ViewTransition.SLIDE_DOWN` | Inhoud beweegt naar beneden |
| `ViewTransition.ZOOM` | Oude inhoud krimpt weg, nieuwe inhoud groeit in |
| `ViewTransition.ZOOM_OUT` | Oude inhoud groeit weg, nieuwe inhoud krimpt in |

Gebruik `enter()` om een component die wordt toegevoegd te animeren en `exit()` om een component die wordt verwijderd te animeren:

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

Gedeelde componentovergangen creëren een morphing-effect waarbij een component lijkt te transformeren van zijn positie in de oude weergave naar zijn positie in de nieuwe weergave. Dit wordt bereikt door componenten dezelfde overgangsnaam te geven met behulp van de `setViewTransitionName()`-methode, beschikbaar op elk component dat de <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface implementeert.

```java
// In de kaartweergave
image.setViewTransitionName("blog-image");

// In de detailweergave - dezelfde naam creëert de morph
image.setViewTransitionName("blog-image");
```

Wanneer er tussen deze weergaven wordt overgaan, animeert de browser de component tussen posities, waardoor een verbonden visuele ervaring ontstaat.

:::tip Gebruik unieke namen
Bij het werken met lijsten of herhaalde componenten, voeg een unieke identificator toe in de overgangsnaam. Elke component vereist zijn eigen unieke naam om correct naar de bijbehorende component in de nieuwe weergave te morphen. Het gebruik van dezelfde naam voor meerdere zichtbare componenten veroorzaakt ongedefinieerd gedrag.
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

### Lijst herordenen {#list-reordering}

Een veelvoorkomende use case voor gedeelde componentovergangen is het animeren van lijstitems wanneer hun volgorde verandert. Door een unieke `view-transition-name` aan elk item toe te wijzen, animeert de browser componenten automatisch naar hun nieuwe posities:

```java
// Elke kaart krijgt een unieke overgangsnaam op basis van zijn ID
card.setViewTransitionName("card-" + item.id());

// Bij het schudden, update gewoon de DOM - de browser behandelt animatie
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

## Aangepaste CSS-animaties {#custom-css-animations}

Voor volledige controle over animaties kunt u aangepaste CSS-keyframes definiëren. webforJ voegt `-enter` of `-exit` achtervoegsels toe aan uw overgangsnamen, die u gebruikt om de pseudo-elementen van de kijkovergang te targeten:

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

/* Toepassen op het pseudo-element van de kijkovergang */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Verwijs naar uw aangepaste animatie door de naam (zonder het achtervoegsel) door te geven aan `enter()` of `exit()`:

```java
// Gebruik "flip-in" - webforJ voegt automatisch "-enter" achtervoegsel toe
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Gebruik "blur-out" voor exit - webforJ voegt automatisch "-exit" achtervoegsel toe
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

## CSS-aanpassing {#css-customization}

Elk vooraf gedefinieerd overgangstype exposeert CSS-variabelen voor verfijning:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Vervagen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animatieduur |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Versnellingsfunctie |
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
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Versnellingsfunctie |
      | `--vt-slide-left-distance` | `30%` | Glijdafstand |
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
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Versnellingsfunctie |
      | `--vt-slide-right-distance` | `30%` | Glijdafstand |
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
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Versnellingsfunctie |
      | `--vt-slide-up-distance` | `30%` | Glijdafstand |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Slide naar beneden</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animatieduur |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Versnellingsfunctie |
      | `--vt-slide-down-distance` | `30%` | Glijdafstand |
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
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Versnellingsfunctie |
      | `--vt-zoom-scale` | `0.8` | Schaalfactor (oud zoomt naar deze, nieuw zoomt in vanaf deze) |
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
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Versnellingsfunctie |
      | `--vt-zoom-out-scale` | `1.2` | Schaalfactor (oud zoomt in naar deze, nieuw zoomt uit vanaf deze) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Variabelen overschrijven</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Om aan te passen, overschrijf deze variabelen in uw CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Voor geavanceerde aanpassing, target de pseudo-elementen van de kijkovergang direct:

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
