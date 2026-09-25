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

Näyttösiirtymät tarjoavat animoituja siirtymiä, kun [DOM](/docs/glossary#dom) muuttuu, vähentäen visuaalista hämmennystä ja ylläpitäen tilallisesta kontekstia navigoinnin tai sisällön päivityksien aikana. webforJ integroituu selaimen [View Transition API:iin](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) käsittelemään vanhojen ja uusien tilojen välillä tapahtuvan animoimisen monimutkaisuutta.

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

Käytä `Page.getCurrent().startViewTransition()` luodaksesi siirtymän, joka palauttaa rakennuspalikan siirtymän konfiguroimiseksi:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Siirtymisprosessi ottaa valokuvan nykyisestä tilasta, toteuttaa DOM-muutokset `onUpdate` takaisinkutsussa ja sitten animoidaan vanhasta valokuvasta uuteen sisältöön. Sinun on kutsuttava `done.run()` ilmoittaaksesi, kun muutoksesi ovat valmiit.

:::warning `onUpdate` takaisinkutsua vaaditaan
Kutsuminen `start()` ilman päivitystakaisinkutsua heittää `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ tarjoaa ennalta määritettyjä siirtymätyyppejä, joita voit soveltaa komponentteihin, kun ne tulevat tai poistuvat DOMista:

| Vakio | Vaikutus |
|----------|--------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristiinnaiminta vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuten eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuten taaksepäin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö kutistuu, uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois, uusi sisältö kutistuu |

Käytä `enter()` animoidaksesi komponenttia, joka lisätään, ja `exit()` animoidaksesi komponenttia, joka poistetaan:

```java
// Animoidaan komponenttia, joka tulee DOMiin
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animoidaan komponenttia, joka poistuu DOMista
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Jaetut komponenttisiirtymät {#shared-component-transitions}

Jaetut komponenttisiirtymät luovat muotoiluefektiä, jossa komponentti näyttää muuntuvan sen paikasta vanhassa näkymässä uuteen paikkaansa uudessa näkymässä. Tämä saavutetaan antamalla komponentille sama siirtymän nimi käyttämällä `setViewTransitionName()` -metodia, joka on saatavilla kaikille komponenteille, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohtanäkymässä - sama nimi luo muodon
image.setViewTransitionName("blog-image");
```

Siirtyessäsi näiden näkymien välillä selain animoi komponenttia paikkojen välillä, luoden yhdistetyn visuaalisen kokemuksen.

:::tip Käytä ainutlaatuisia nimiä
Työskennellessäsi listojen tai toistettavien komponenttien kanssa, sisällytä ainutlaatuinen tunniste siirtymän nimeen. Jokaisella komponentilla on oltava oma ainutlaatuinen nimi, jotta se voi muuntua oikein vastaavaksi komponentiksi uudessa näkymässä. Samojen nimien käyttäminen useille näkyville komponenteille aiheuttaa määrittelemätöntä käyttäytymistä.
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

### Listan uudelleenjärjestely {#list-reordering}

Yksi yleinen tapa käyttää jaettuja komponenttisiirtymiä on animointi listan kohteista, kun niiden järjestys muuttuu. Antamalla jokaiselle kohteelle ainutlaatuinen `view-transition-name`, selain animoi komponentit automaattisesti uusiin paikkoihinsa:

```java
// Jokaiselle kortille määritetään ainutlaatuinen siirtymän nimi sen ID:n mukaan
card.setViewTransitionName("card-" + item.id());

// Kun sekoitamme, päivitä vain DOM - selain hoitaa animaation
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

## Mukautetut CSS-animaatiot {#custom-css-animations}

Täydelliseen hallintaan animaatioista voit määrittää mukautettuja CSS-avainkehyksiä. webforJ lisää siirtymän nappuihin `-enter` tai `-exit` päätteet, joita käytät kohdistamaan näkymä siirtymän pseudo-elementteihin:

```css
/* Määritä avainkehyksiä komponenttien sisään tulemiselle */
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

/* Sovelletaan näkymä siirtymän pseudo-elementtiin */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Viittaa mukautettuun animaatioosi antamalla sen nimi (ilman päätteitä) `enter()` tai `exit()`:

```java
// Käytä "flip-in" - webforJ lisää "-enter" päätteet automaattisesti
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Käytä "blur-out" poistumiseen - webforJ lisää "-exit" päätteet
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

## CSS-räätälöinti {#css-customization}

Jokainen ennalta määritelty siirtymätyyppi paljastaa CSS-mukautusominaisuuksia hienosäätöä varten:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Häivy</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletusarvo | Kuvaus |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animaation kesto |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Pehmennysfunktio |
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
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Pehmennysfunktio |
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
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Pehmennysfunktio |
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
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Pehmennysfunktio |
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
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Pehmennysfunktio |
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
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Pehmennysfunktio |
      | `--vt-zoom-scale` | `0.8` | Skaalauskerroin (vanha zoomaa pois tästä, uusi zoomaa tähän) |
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
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Pehmennysfunktio |
      | `--vt-zoom-out-scale` | `1.2` | Skaalauskerroin (vanha zoomaa tähän, uusi zoomaa pois tästä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Muuttujien ylikirjoitus</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Räätälöidäksesi ylikirjoita nämä muuttujat CSS:ssäsi:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Edistykselliseen räätälöintiin, kohdistaa näkymä siirtymän pseudo-elementteihin suoraan:

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
