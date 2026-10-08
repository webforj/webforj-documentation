---
sidebar_class_name: experimental-content
sidebar_position: 40
title: View Transitions
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: 3440413f572744ea709085a2975c0023
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Näkymäsiirtymät tarjoavat animaatioita, kun [DOM](/docs/glossary#dom) muuttuu, vähentäen visuaalista häiritsevät hetkiä ja ylläpitäen tilallista kontekstia navigoinnin tai sisällön päivitysten aikana. webforJ integroituu selaimen [View Transition API:in](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) käsitelläkseen animaatioiden koordinoimisen vanhojen ja uusien tilojen välillä.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
  'src/main/java/com/webforj/samples.views.viewtransitions.components.ChatCard.java',
  'src/main/frontend/css/viewtransitions/components/chat-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='450px'
/>

<ExperimentalWarning />

Käytä `Page.getCurrent().startViewTransition()` luodaksesi siirtymän, joka palauttaa rakenteen siirtymän konfiguroimiseksi:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Siirtymäprosessi tallentaa nykytilanteen, soveltaa DOM-muutoksesi `onUpdate` palautteessa ja sitten animoidaan vanhasta näkymästä uuteen sisältöön. Sinun on kutsuttava `done.run()` signaloidaksesi, kun muutoksesi ovat valmiit.

:::warning `onUpdate` palautteen asettaminen on pakollista
Kutsuminen `start()` ilman päivityspalautetta heittää `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ tarjoaa ennalta määriteltyjä siirtymätyyppejä, joita voit soveltaa komponentteihin, jotka tulevat tai poistuvat DOM:sta:

| Vakio | Vaikutus |
|-------|----------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristiinliuku vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuten eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuten taaksepäin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö pienenee, uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois, uusi sisältö pienenee |

Käytä `enter()` animaatiossa komponentin lisäämiseen ja `exit()` animaatiossa komponentin poistamiseen:

```java
// Animoidaan komponenttia, joka tulee DOM:iin
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animoidaan komponenttia, joka poistuu DOM:ista
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Jaetut komponenttisiirtymät {#shared-component-transitions}

Jaetut komponenttisiirtymät luovat muuntamistehosteen, jossa komponentti tuntuu siirtyvän paikastaan vanhassa näkymässä uuteen näkymään. Tämä saavutetaan antamalla komponentille sama siirtymän nimi käyttämällä `setViewTransitionName()`-menetelmää, joka on saatavilla kaikilla komponenteilla, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohtanäkymässä - sama nimi luo muunnoksen
image.setViewTransitionName("blog-image");
```

Kun siirrytään näiden näkymien välillä, selain animoi komponenttia paikkojen välillä, luoden yhteyden visuaalisen kokemuksen.

:::tip Käytä ainutlaatuisia nimiä
Työskennellessäsi luetteloiden tai toistuvien komponenttien kanssa, sisällytä ainutlaatuinen tunniste siirtymän nimeen. Jokainen komponentti tarvitsee oman erillisen nimen muuntuaakseen oikein vastaavaan komponenttiin uudessa näkymässä. Saman nimen käyttäminen useille näkyville komponenteille aiheuttaa määrittelemätöntä käyttäytymistä.
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/frontend/css/viewtransitions/morph.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/frontend/css/viewtransitions/components/blog-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='650px'
/>

### Luetteloiden uudelleenjärjestely {#list-reordering}

Yksi yleinen käyttötapa jaetuissa komponenttisiirtymissä on luetteloelementtien animointi, kun niiden järjestys muuttuu. Antamalla jokaiselle tuotteelle ainutlaatuinen `view-transition-name`, selain animoi automaattisesti komponentit uusiin sijainteihinsa:

```java
// Jokaiselle kortille annetaan ainutlaatuinen siirtymän nimi sen ID:n perusteella
card.setViewTransitionName("card-" + item.id());

// Sekoituksessa päivitä vain DOM - selain käsittelee animaation
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
  'src/main/frontend/css/viewtransitions/shuffle.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java',
  'src/main/frontend/css/viewtransitions/components/shuffle-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='550px'
/>

## Mukautetut CSS-animaatiot {#custom-css-animations}

Täydelliseen ohjaukseen animaatioissa voit määrittää mukautettuja CSS-käyräkohtia. webforJ lisää `-enter` tai `-exit` -liitteet siirtymän nimiin, joita käytät kohdistamaan näkymäsiirtymän pseudo-elementit:

```css
/* Määrittele avainkehykset tuleville komponenteille */
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

/* Soveltaa näkymäsiirtymän pseudo-elementtiin */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Viittaa mukautettuun animaatioosi antamalla sen nimi (ilman liitettä) `enter()` tai `exit()`-menetelmille:

```java
// Käytä "flip-in" - webforJ lisää "-enter" liitteen automaattisesti
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Käytä "blur-out" poistettaessa - webforJ lisää "-exit" liitteen
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
  'src/main/java/com/webforj/samples/views/viewtransitions/components/NotificationCard.java',
  'src/main/frontend/css/viewtransitions/components/notification-card.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='400px'
/>

## CSS-kustomointi {#css-customization}

Jokainen ennalta määritelty siirtymätyyppi tarjoaa CSS-mukautusominaisuuksia hienosäätöön:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fade</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|--------|
      | `--vt-fade-duration` | `200ms` | Animaation kesto |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Tasoitusfunktio |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuminen vasemmalle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|--------|
      | `--vt-slide-left-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Tasoitusfunktio |
      | `--vt-slide-left-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuminen oikealle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|--------|
      | `--vt-slide-right-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Tasoitusfunktio |
      | `--vt-slide-right-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuminen ylöspäin</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|--------|
      | `--vt-slide-up-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Tasoitusfunktio |
      | `--vt-slide-up-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuminen alaspäin</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|--------|
      | `--vt-slide-down-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Tasoitusfunktio |
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
      |----------|--------|--------|
      | `--vt-zoom-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Tasoitusfunktio |
      | `--vt-zoom-scale` | `0.8` | Skaalakerroin (vanha zoomaa tähän, uusi zoomaa tähän) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom out</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|--------|
      | `--vt-zoom-out-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Tasoitusfunktio |
      | `--vt-zoom-out-scale` | `1.2` | Skaalakerroin (vanha zoomaa tänne, uusi zoomaa täältä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Muuttujien ylikirjoittaminen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Mukautusta varten voit ylikirjoittaa nämä muuttujat CSS:ssäsi:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Vaativampaan mukautukseen kohdistaa suoraan näkymäsiirtymän pseudo-elementit:

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
