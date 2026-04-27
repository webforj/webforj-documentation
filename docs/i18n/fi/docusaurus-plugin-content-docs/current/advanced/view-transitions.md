---
sidebar_position: 40
title: View Transitions
_i18n_hash: 95d65a391ac0b11d6976acfc43691754
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View transitions provide animated transitions when the [DOM](/docs/glossary#dom) changes, reducing visual jarring and maintaining spatial context during navigation or content updates. webforJ integrates with the browser's [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) to handle the complexity of coordinating animations between old and new states.

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

<ExperimentalWarning />

## Perus käyttö {#basic-usage}

To create a view transition, use `Page.getCurrent().startViewTransition()`, which returns a builder for configuring the transition:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

The transition process captures a snapshot of the current state, applies your DOM changes in the `onUpdate` callback, then animates from the old snapshot to the new content. You must call `done.run()` to signal when your changes are complete.

:::warning `onUpdate`-kokoukset ovat pakollisia
Calling `start()` without setting an update callback throws an `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ provides predefined transition types that you can apply to components entering or exiting the DOM:

| Vakio | Vaikutus |
|----------|--------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristiin haalistuminen vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö liikkuu vasemmalle (kuten eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö liikkuu oikealle (kuten taaksepäin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö liikkuu ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö liikkuu alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö kutistuu ja uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois ja uusi sisältö kutistuu |

Use `enter()` to animate a component being added and `exit()` to animate a component being removed:

```java
// Animaatio komponentin lisäämiselle DOM:iin
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animaatio komponentin poistamiselle DOM:ista
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Jaetun komponentin siirtymät {#shared-component-transitions}

Shared component transitions create a morphing effect where a component appears to transform from its position in the old view to its position in the new view. Tämä saavutetaan antamalla komponenteille sama siirtymien nimi käyttäen `setViewTransitionName()`-metodia, joka on saatavilla kaikilla komponenteilla, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohdanäkymässä - sama nimi luo morphin
image.setViewTransitionName("blog-image");
```

When transitioning between these views, the browser animates the component between positions, creating a connected visual experience.

:::tip Käytä ainutlaatuisia nimiä
When working with lists or repeated components, include a unique identifier in the transition name. Each component requires its own distinct name to morph correctly to its corresponding component in the new view. Using the same name for multiple visible components causes undefined behavior.
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

### Luettelon uudelleenjärjestäminen {#list-reordering}

A common use case for shared component transitions is animating list items when their order changes. By assigning a unique `view-transition-name` to each item, the browser automatically animates components to their new positions:

```java
// Jokaiselle kortille annetaan ainutlaatuinen siirtymisen nimi perustuen sen ID:hen
card.setViewTransitionName("card-" + item.id());

// Kun sekoitetaan, päivitä vain DOM - selain käsittelee animaation
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

## Mukautetut CSS-animaatiot {#custom-css-animations}

For full control over animations, you can define custom CSS keyframes. webforJ appends `-enter` or `-exit` suffixes to your transition names, which you use to target the view transition pseudo-elements:

```css
/* Määritellään avainkehykset komponenttien sisäänkäynnille */
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

/* Käytä siirtymän pseudo-elementtiin */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Reference your custom animation by passing its name (without the suffix) to `enter()` or `exit()`:

```java
// Käytä "flip-in" - webforJ lisää "-enter" -liitteen automaattisesti
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Käytä "blur-out" poistuttaessa - webforJ lisää "-exit" -liitteen
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

## CSS-mukautus {#css-customization}

Each predefined transition type exposes CSS custom properties for fine-tuning:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Haalea</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animaation kesto |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liuku vasemmalle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-left-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liuku oikealle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-right-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liuku ylöspäin</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-up-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liuku alaspäin</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-down-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-zoom-scale` | `0.8` | Skaalakerroin (vanha zoomaa ulos tästä, uusi zoomaa sisään tästä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom ulos</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-zoom-out-scale` | `1.2` | Skaalakerroin (vanha zoomaa sisään tähän, uusi zoomaa ulos tästä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Muuttujien ylikirjoittaminen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      To customize, override these variables in your CSS:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      For advanced customization, target the view transition pseudo-elements directly:

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
