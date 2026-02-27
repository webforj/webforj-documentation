---
sidebar_position: 40
title: View Transitions
sidebar_class_name: new-content
_i18n_hash: eb57126d50375aa6da9197fa846291ff
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View-transities bieden geanimeerde overgangen wanneer de [DOM](/docs/glossary#dom) verandert, waardoor visuele schokken worden verminderd en de ruimtelijke context tijdens navigatie of content-updates behouden blijft. webforJ integreert met de [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) van de browser om de complexiteit van het coördineren van animaties tussen oude en nieuwe toestanden aan te pakken.

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

:::warning Experimentele API
Deze API is gemarkeerd als experimenteel sinds 25.11 en kan in toekomstige versies veranderen. De API-handtekening, het gedrag en de prestatiekenmerken zijn onderhevig aan wijzigingen.
:::

## Basisgebruik {#basic-usage}

Om een view-transitie te creëren, gebruik `Page.getCurrent().startViewTransition()`, dat een builder retourneert voor het configureren van de transitie:

```java
Page.getCurrent().startViewTransition()
    .onUpdate(done -> {
        container.remove(oldView);
        container.add(newView);
        done.run();
    })
    .start();
```

Het transitieproces legt een momentopname van de huidige toestand vast, past je DOM-wijzigingen toe in de `onUpdate` callback en animeert vervolgens van de oude momentopname naar de nieuwe inhoud. Je moet `done.run()` aanroepen om aan te geven wanneer je wijzigingen zijn voltooid.

:::warning De `onUpdate` callback is vereist
Het aanroepen van `start()` zonder een update callback in te stellen, werpt een `IllegalStateException`.
:::

## Toepassen van overgangen {#applying-transitions}

webforJ biedt vooraf gedefinieerde overgangs typen die je kunt toepassen op componenten die de DOM binnenkomen of verlaten:

| Constant | Effect |
|----------|--------|
| `ViewTransition.NONE` | Geen animatie |
| `ViewTransition.FADE` | Crossfade tussen oude en nieuwe inhoud |
| `ViewTransition.SLIDE_LEFT` | Inhoud stroomt naar links (zoals vooruit navigatie) |
| `ViewTransition.SLIDE_RIGHT` | Inhoud stroomt naar rechts (zoals terug navigatie) |
| `ViewTransition.SLIDE_UP` | Inhoud stroomt omhoog |
| `ViewTransition.SLIDE_DOWN` | Inhoud stroomt omlaag |
| `ViewTransition.ZOOM` | Oude inhoud krimpt, nieuwe inhoud groeit aan |
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

Gedeelde componentovergangen creëren een morfings-effect waarbij een component lijkt te transformeren van zijn positie in de oude weergave naar zijn positie in de nieuwe weergave. Dit wordt bereikt door componenten dezelfde overgangsnaam te geven met behulp van de `setViewTransitionName()` methode, beschikbaar op elke component die de <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface implementeert.

```java
// In de kaartweergave
image.setViewTransitionName("blog-image");

// In de detailweergave - dezelfde naam creëert de morf
image.setViewTransitionName("blog-image");
```

Bij het overgaan tussen deze weergaven, animeert de browser de component tussen de posities, waardoor een verbonden visuele ervaring ontstaat.

:::tip Gebruik unieke namen
Wanneer je werkt met lijsten of herhaalde componenten, neem dan een unieke identificatie op in de overgangsnaam. Elke component heeft zijn eigen unieke naam nodig om correct te morfen naar de bijbehorende component in de nieuwe weergave. Het gebruik van dezelfde naam voor meerdere zichtbare componenten veroorzaakt ongedefinieerd gedrag.
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

### Lijstherordering {#list-reordering}

Een veelvoorkomend gebruik van gedeelde componentovergangen is het animeren van lijstitems wanneer hun volgorde verandert. Door een unieke `view-transition-name` aan elk item toe te wijzen, animeert de browser componenten automatisch naar hun nieuwe posities:

```java
// Elke kaart krijgt een unieke overgangsnaam op basis van zijn ID
card.setViewTransitionName("card-" + item.id());

// Bij het schudden, update gewoon de DOM - de browser verwerkt de animatie
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

## Aangepaste CSS-animaties {#custom-css-animations}

Voor volledige controle over animaties kun je aangepaste CSS-keyframes definiëren. webforJ voegt het achtervoegsel `-enter` of `-exit` toe aan je overgangsnamen, die je gebruikt om de pseudo-elementen van de view-overgang te targeten:

```css
/* Definieer keyframes voor inkomende componenten */
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

/* Toepassen op het pseudo-element van de view-overgang */
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

// Gebruik "blur-out" voor exit - webforJ voegt het "-exit" achtervoegsel toe
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

## CSS-aanpassing {#css-customization}

Elke vooraf gedefinieerde overgangstype exposeert CSS-aangepaste eigenschappen voor fine-tuning:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Vervagen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variabele | Standaard | Beschrijving |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Duur van de animatie |
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
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Duur van de animatie |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
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
      | `--vt-slide-right-duration` | `200ms` | Duur van de animatie |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
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
      | `--vt-slide-up-duration` | `200ms` | Duur van de animatie |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
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
      | `--vt-slide-down-duration` | `200ms` | Duur van de animatie |
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
      | `--vt-zoom-duration` | `200ms` | Duur van de animatie |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-zoom-scale` | `0.8` | Schaalfactor (oude zoomt uit naar dit, nieuwe zoomt in vanaf dit) |
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
      | `--vt-zoom-out-duration` | `200ms` | Duur van de animatie |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-functie |
      | `--vt-zoom-out-scale` | `1.2` | Schaalfactor (oude zoomt in naar dit, nieuwe zoomt uit vanaf dit) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Het overschrijven van variabelen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Om aan te passen, overschrijf deze variabelen in je CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Voor geavanceerde aanpassing, richt je rechtstreeks op de pseudo-elementen van de view-overgang:

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
