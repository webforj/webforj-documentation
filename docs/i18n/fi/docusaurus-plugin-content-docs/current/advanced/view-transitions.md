---
sidebar_position: 40
title: Näkymäsiirtymät
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: df97f8dc10601feff6a211aee0b4e9d7
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Näytön siirtymät tarjoavat animoituja siirtymiä, kun [DOM](/docs/glossary#dom) muuttuu, vähentäen visuaalista häiritsevyyttä ja ylläpitäen tilallista kontekstia navigoinnin tai sisällön päivitysten aikana. webforJ integroituu selaimen [View Transition API:iin](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) käsitelläkseen animaatioiden koordinoimisen monimutkaisuutta vanhojen ja uusien tilojen välillä.

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

## Peruskäyttö {#basic-usage}

Luodaksesi näkymäsiirtymän, käytä `Page.getCurrent().startViewTransition()`, joka palauttaa rakentajan siirtymän määrittämiseksi:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Siirtymäprosessi tallentaa nykyisen tilan kuvakaappauksen, soveltaa DOM-muutoksiasi `onUpdate`-kutsussa, ja sitten animoidaan vanhasta kuvasta uuteen sisältöön. Sinun on kutsuttava `done.run()` merkitäksesi, kun muutoksesi ovat valmiit.

:::warning `onUpdate`-kutsu on pakollinen
Kutsuminen `start()` ilman päivityskutsun määrittämistä heittää `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ tarjoaa ennaltamääriteltyjä siirtymätyyppejä, joita voit soveltaa komponentteihin, jotka tulevat tai poistuvat DOMista:

| Vakio | Vaikutus |
|----------|--------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristiinhaalistus vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuten eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuten takaisin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö pienenee, uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois, uusi sisältö pienenee |

Käytä `enter()`-toimintoa animoidaksesi komponentin lisäämisen ja `exit()`-toimintoa animoidaksesi komponentin poistamisen:

```java
// Animoidaan komponentti, joka tulee DOMiin
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animoidaan komponentti, joka poistuu DOMista
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Jako komponenttisiiirtymät {#shared-component-transitions}

Jaetut komponenttisiiirtymät luovat muuntumistehosteen, jossa komponentti näyttää siirtyvän vanhasta näkymästä uuteen näkymään. Tämä saavutetaan antamalla komponentille sama siirtymän nimi käyttämällä `setViewTransitionName()`-metodia, joka on saatavilla kaikilla komponenteilla, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>-rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohtanäkymässä - sama nimi luo muuntumisen
image.setViewTransitionName("blog-image");
```

Kun siirrytään näiden näkymien välillä, selain animoi komponentin paikkojen välillä, luoden yhteyden visuaalisen kokemuksen.

:::tip Käytä ainutlaatuisia nimiä
Työskennellessäsi listojen tai toistuvien komponenttien kanssa, lisää ainutlaatuinen tunniste siirtymän nimeen. Jokaisella komponentilla on oltava oma erillinen nimensä, jotta se voi muuntua oikein vastaavaan komponenttiin uudessa näkymässä. Samojen nimien käyttäminen useille näkyville komponentille aiheuttaa määrittelemätöntä käyttäytymistä.
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

### Listan uudelleenjärjestäminen {#list-reordering}

Yksi yleisimmistä käyttötapauksista jaetuille komponenttisiiirtymille on listan kohteiden animointi, kun niiden järjestys muuttuu. Antamalla jokaiselle kohteelle ainutlaatuinen `view-transition-name`, selain animoi komponentit automaattisesti uusiin paikkoihinsa:

```java
// Jokaiselle kortille annetaan ainutlaatuinen siirtymän nimi sen ID:n perusteella
card.setViewTransitionName("card-" + item.id());

// Kun sekoitetaan, päivitetään vain DOM - selain käsittelee animaation
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

## Räätälöidyt CSS-animaatiot {#custom-css-animations}

Täydelliseen hallintaan animaatioista voit määrittää räätälöityjä CSS-avainkehyksiä. webforJ lisää siirtymän nimiin `-enter` tai `-exit` päätteet, joita käytät osoittamaan näkymäsiirtymän pseudo-elementtejä:

```css
/* Määritä avainkehyksiä komponenttien sisäänmenolle */
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

/* Sovelletaan näkymäsiirtymän pseudo-elementtiin */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Viittaat räätälöityyn animaatioosi antamalla sen nimen (ilman päätettä) `enter()`- tai `exit()`-kutsussa:

```java
// Käytä "flip-in" - webforJ lisää "-enter" päätteet automaattisesti
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Käytä "blur-out" poistolle - webforJ lisää "-exit" päätteet
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

## CSS-mukautus {#css-customization}

Jokainen ennaltamääritelty siirtymätyyppi altistaa CSS-mukautusominaisuudet hienosäätöön:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Haalistus</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animaation kesto |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Helpotustoiminto |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liuku vasemmalle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Helpotustoiminto |
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
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Helpotustoiminto |
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
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Helpotustoiminto |
      | `--vt-slide-up-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liuku alas</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Helpotustoiminto |
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
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Helpotustoiminto |
      | `--vt-zoom-scale` | `0.8` | Skaalakerroin (vanha zoomaa tästä, uusi zoomaa tähän) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom ulos</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Helpotustoiminto |
      | `--vt-zoom-out-scale` | `1.2` | Skaalakerroin (vanha zoomaa tähän, uusi zoomaa tästä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Muuttujien ylikirjoittaminen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Mukauttaaksesi, ylikirjoita nämä muuttujat CSS:ssäsi:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Edistyksellistä mukautusta varten, kohdenna näkymäsiirtymän pseudo-elementit suoraan:

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
