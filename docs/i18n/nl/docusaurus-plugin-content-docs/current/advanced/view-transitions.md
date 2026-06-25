---
sidebar_position: 40
title: View Transitions
_i18n_hash: f906f47211e25b6b4bd659abdb1ad500
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View-transities bieden geanimeerde overgangen wanneer de [DOM](/docs/glossary#dom) verandert, waardoor visuele verstoringen worden verminderd en de ruimtelijke context tijdens navigatie of contentupdates behouden blijft. webforJ integreert met de [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) van de browser om de complexiteit van het coördineren van animaties tussen oude en nieuwe toestanden te beheren.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/resources/static/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Basisgebruik {#basic-usage}

Om een view-transitie te creëren, gebruik je `Page.getCurrent().startViewTransition()`, wat een builder retourneert voor het configureren van de transitie:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Het transitieproces legt een momentopname van de huidige staat vast, past je DOM-wijzigingen toe in de `onUpdate` callback en animeert vervolgens van de oude momentopname naar de nieuwe inhoud. Je moet `done.run()` aanroepen om aan te geven wanneer je wijzigingen voltooid zijn.

:::warning De `onUpdate` callback is vereist
Aanroepen van `start()` zonder het instellen van een update callback werpt een `IllegalStateException`.
:::

## Toepassen van transities {#applying-transitions}

webforJ biedt gedefinieerde transietypes die je kunt toepassen op componenten die de DOM binnenkomen of verlaten:

| Constante | Effect |
|-----------|--------|
| `ViewTransition.NONE` | Geen animatie |
| `ViewTransition.FADE` | Crossfade tussen oude en nieuwe inhoud |
| `ViewTransition.SLIDE_LEFT` | Inhoud stroomt naar links (zoals voorwaartse navigatie) |
| `ViewTransition.SLIDE_RIGHT` | Inhoud stroomt naar rechts (zoals terug navigatie) |
| `ViewTransition.SLIDE_UP` | Inhoud stroomt omhoog |
| `ViewTransition.SLIDE_DOWN` | Inhoud stroomt omlaag |
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

## Gedeelde componenttransities {#shared-component-transitions}

Gedeelde componenttransities creëren een morfingseffect waarbij een component lijkt te transformeren van zijn positie in de oude weergave naar zijn positie in de nieuwe weergave. Dit wordt bereikt door componenten dezelfde transitie naam te geven met behulp van de `setViewTransitionName()` methode, die beschikbaar is op elk component dat de <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface implementeert.

```java
// In de kaartweergave
image.setViewTransitionName("blog-image");

// In de detailweergave - dezelfde naam creëert de morf
image.setViewTransitionName("blog-image");
```

Bij het overgaan tussen deze weergaven animeert de browser de component tussen posities, waardoor een verbonden visuele ervaring ontstaat.

:::tip Gebruik unieke namen
Bij het werken met lijsten of herhaalde componenten, neem een unieke identificatie op in de transitie naam. Elke component heeft zijn eigen unieke naam nodig om correct te morfen naar de overeenkomstige component in de nieuwe weergave. Het gebruik van dezelfde naam voor meerdere zichtbare componenten veroorzaakt ongedefinieerd gedrag.
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

### Lijst herschikken {#list-reordering}

Een veelvoorkomend gebruik van gedeelde componenttransities is het animeren van lijstitems wanneer hun volgorde verandert. Door elke item een unieke `view-transition-name` toe te wijzen, animeert de browser de componenten automatisch naar hun nieuwe posities:

```java
// Elke kaart krijgt een unieke transitie naam op basis van zijn ID
card.setViewTransitionName("card-" + item.id());

// Bij het schudden, update gewoon de DOM - de browser verzorgt de animatie
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

## Aangepaste CSS-animaties {#custom-css-animations}

Voor volledige controle over animaties kun je aangepaste CSS-keyframes definiëren. webforJ voegt `-enter` of `-exit` achtervoegsels toe aan je transitienamen, die je gebruikt om de pseudo-elementen van de view-transitie te targeten:

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

/* Toepassen op het pseudo-element van de view-transitie */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Verwijs naar je aangepaste animatie door de naam (zonder het achtervoegsel) door te geven aan `enter()` of `exit()`:

```java
// Gebruik "flip-in" - webforJ voegt automatisch het "-enter" achtervoegsel toe
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Gebruik "blur-out" voor exit - webforJ voegt "-exit" achtervoegsel toe
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

## CSS-aanpassing {#css-customization}

Elke gedefinieerde transitie type exposeert CSS aangepaste eigenschappen voor verdere verfijning:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fade</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |-----------|-----------|-------------|
      | `--vt-fade-duration` | `200ms` | Animatieduur |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
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
      |-----------|-----------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animatieduur |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-left-distance` | `30%` | Slide-afstand |
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
      |-----------|-----------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animatieduur |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-right-distance` | `30%` | Slide-afstand |
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
      |-----------|-----------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animatieduur |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-up-distance` | `30%` | Slide-afstand |
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
      |-----------|-----------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animatieduur |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-slide-down-distance` | `30%` | Slide-afstand |
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
      |-----------|-----------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animatieduur |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-zoom-scale` | `0.8` | Schaalfactor (oude zoomt naar deze, nieuwe zoomt in vanuit deze) |
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
      |-----------|-----------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animatieduur |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-zoom-out-scale` | `1.2` | Schaalfactor (oude zoomt in naar deze, nieuwe zoomt uit van deze) |
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

      Voor geavanceerde aanpassing, richt je je direct op de pseudo-elementen van de view-transitie:

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
