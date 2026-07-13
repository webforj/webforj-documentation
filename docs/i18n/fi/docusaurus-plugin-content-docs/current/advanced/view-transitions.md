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

Näkymäsiirtymät tarjoavat animoituja siirtymiä, kun [DOM](/docs/glossary#dom) muuttuu, vähentäen visuaalista kuormitusta ja säilyttäen tilallisesta kontekstista navigoinnin tai sisällön päivitysten aikana. webforJ integroituu selaimen [View Transition API:iin](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) hallitakseen animaatioiden koordinoinnin vanhojen ja uusien tilojen välillä.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Peruskäyttö {#basic-usage}

Näkymäsiirtymän luomiseksi käytä `Page.getCurrent().startViewTransition()`, joka palauttaa rakennusohjelman siirtymän konfiguroimiseen:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Siirtymäprosessi tallentaa nykyaikaisen tilan näytön, soveltaa DOM-muutokset `onUpdate` -takaisinkutsussa ja animoi vanhasta hetkestä uuteen sisältöön. Sinun on kutsuttava `done.run()` merkitäksesi, kun muutoksesi ovat valmiita.

:::warning `onUpdate` -takaisinkutsu on pakollinen
Kutsumalla `start()` ilman päivitystakaisinkutsua heitetään `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ tarjoaa määriteltyjä siirtymätyyppejä, joita voit soveltaa DOM:iin tuleville tai lähteville komponenteille:

| Vakiot | Vaikutus |
|----------|--------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Yli- ja ala-vaihto vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö liikkuu vasemmalle (kuin eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö liikkuu oikealle (kuin taaksepäin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö liikkuu ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö liikkuu alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö pienenee, uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa, uusi sisältö pienenee |

Käytä `enter()` animoidessasi komponenttia, joka lisätään, ja `exit()` animoidessasi komponenttia, joka poistetaan:

```java
// Animoidaan komponentti, joka tulee DOM:iin
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animoidaan komponentti, joka poistuu DOM:ista
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Jaetut komponenttisiirtymät {#shared-component-transitions}

Jaetut komponenttisiirtymät luovat muunnosvaikutelman, jossa komponentti näyttää muuttuvan sen sijainnista vanhassa näkymässä uuteen. Tämä saavutetaan antamalla komponenteille sama siirtymän nimi käyttäen `setViewTransitionName()` -menetelmää, joka on käytettävissä kaikilla komponenteilla, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohtanäkymässä - sama nimi luo muunnoksen
image.setViewTransitionName("blog-image");
```

Kun siirretään näiden näkymien välillä, selain animoi komponentin sijainneilta, luoden yhteyden visuaalisen kokemuksen.

:::tip Käytä ainutlaatuisia nimiä
Työskennellessäsi listojen tai toistuvien komponenttien kanssa, sisällytä ainutlaatuinen tunniste siirtymän nimeen. Jokaisella komponentilla tarvitsee olla oma erillinen nimensä muuntua oikein vastaavaksi komponentiksi uudessa näkymässä. Samojen nimien käyttäminen useille näkyville komponenteille aiheuttaa määrittelemätöntä käyttäytymistä.
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

### Listan järjestäminen {#list-reordering}

Yksi jaettujen komponenttisiirtymien yleisistä käyttötapauksista on listaelementtien animointi, kun niiden järjestys muuttuu. Antamalla jokaiselle kohteelle ainutlaatuinen `view-transition-name`, selain animoi komponentit automaattisesti uusiin sijainteihinsa:

```java
// Jokaiselle kortille annetaan ainutlaatuinen siirtymän nimi sen ID:n perusteella
card.setViewTransitionName("card-" + item.id());

// Kun sekoitetaan, päivitä vain DOM - selain hoitaa animaation
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

## Mukautetut CSS-animaatiot {#custom-css-animations}

Täydellisen hallinnan saamiseksi animaatioista, voit määrittää mukautetut CSS-avainkehykset. webforJ lisää `-enter` tai `-exit` päätteet siirtymän nimiin, joita käytät kohdistamaan näkymäsiirtymän pseudo-elementteihin:

```css
/* Määritä avainkehykset komponenttien saapumiselle */
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

/* Käytä näkymäsiirtymän pseudo-elementissä */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Viitaten mukautettuun animaatioosi, siirrä sen nimi (ilman päätettä) `enter()` tai `exit()`:

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
]}
height='400px'
/>

## CSS-mukautus {#css-customization}

Jokainen ennalta määritelty siirtymätyyppi tarjoaa CSS-mukautusominaisuuksia hienosäätöä varten:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Haalistuminen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animaation kesto |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuu vasemmalle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-left-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuu oikealle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-right-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuu ylöspäin</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-up-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuu alaspäin</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
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
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-zoom-scale` | `0.8` | Skaalakerroin (vanha zoomaa tästä, uusi zoomaa tähän) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoomaa ulos</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-zoom-out-scale` | `1.2` | Skaalakerroin (vanha zoomaa tähän, uusi zoomaa tähän) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Muuttujien ylittämiseen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Mukauttaaksesi, ohita nämä muuttujat CSS:ssäsi:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Edistynyt mukauttaminen, kohdistaa näkymäsiirtymän pseudo-elementit suoraan:

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
