---
sidebar_position: 40
title: View Transitions
_i18n_hash: f906f47211e25b6b4bd659abdb1ad500
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Katseluen siirtymät tarjoavat animoituja siirtymiä, kun [DOM](/docs/glossary#dom) muuttuu, vähentäen visuaalista hämmennystä ja ylläpitäen tilallista kontekstia navigoinnin tai sisällön päivitysten aikana. webforJ integroituu selaimen [View Transition API:hin](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) käsittelemään animaatioiden koordinoimisen monimutkaisuutta vanhojen ja uusien tilojen välillä.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/resources/static/css/viewtransitions/chat.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Peruskäyttö {#basic-usage}

Luodaksesi katseluen siirtymisen, käytä `Page.getCurrent().startViewTransition()`, joka palauttaa builderin siirtymän konfiguroimiseen:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Siirtymäprosessi ottaa nykyisestä tilasta otteen, soveltaa DOM-muutoksesi `onUpdate`-kutsussa ja animoituu vanhasta otoksesta uuteen sisältöön. Sinun on kutsuttava `done.run()` ilmoittaaksesi, kun muutoksesi on valmis.

:::warning `onUpdate`-kutsu on pakollinen
Käynnistämällä `start()` ilman päivityskutsua heitetään `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ tarjoaa ennalta määriteltyjä siirtymätyyppejä, joita voit soveltaa komponenteille, jotka tulevat DOM:iin tai poistuvat sieltä:

| Vakiot | Vaikutus |
|----------|--------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristiin häivyttää vanhan ja uuden sisällön |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuten eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuten takaisin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö kutistuu pois, uusi sisältö kasvaa sisään |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois, uusi sisältö kutistuu sisään |

Käytä `enter()` animoidaksesi komponenttia, joka lisätään, ja `exit()` animoidaksesi komponenttia, joka poistuu:

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

## Yhteiset komponenttisiirtymät {#shared-component-transitions}

Yhteiset komponenttisiirtymät luovat muuntumistehokkuuden, jossa komponentti tuntuu muuttuvan sen paikasta vanhassa näkymässä sen paikkaan uudessa näkymässä. Tämä saavutetaan antamalla komponenteille sama siirtymän nimi käyttämällä `setViewTransitionName()`-menetelmää, joka on saatavilla kaikille komponenteille, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>-rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohdanäkymässä - sama nimi luo muunnoksen
image.setViewTransitionName("blog-image");
```

Siirtyminen näiden näkymien välillä tuo selaimen animaatiot komponentin välillä paikoiltaan, luoden yhteyden visuaaliseen kokemukseen.

:::tip Käytä ainutlaatuisia nimiä
Työskennellessäsi listojen tai toistuvien komponenttien kanssa, yhdistä siirtymän nimeen ainutlaatuinen tunniste. Jokaisella komponentilla on oltava oma erillinen nimi, jotta se voi muuntua oikein vastaavaksi komponentiksi uutessa näkymässä. Saman nimen käyttäminen usealle näkyvälle komponentille aiheuttaa määrittelemätöntä toimintaa.
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

### Listan uudelleenjärjestäminen {#list-reordering}

Yhteisten komponenttisiirtymien yleinen käyttötapa on listaelementtien animoiminen, kun niiden järjestys muuttuu. Antamalla jokaiselle itemille ainutlaatuinen `view-transition-name`, selain animoi komponentit automaattisesti uusiin paikkoihinsa:

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
  'src/main/resources/static/css/viewtransitions/shuffle.css',
]}
height='550px'
/>

## Mukautetut CSS-animaatiot {#custom-css-animations}

Saadaksesi täydellisen hallinnan animaatioista, voit määritellä mukautetut CSS-avainkehykset. webforJ lisää siirtymän nimiin päätteet `-enter` tai `-exit`, joita käytät kohdentamaan katseluen siirtymän pseudo-elementteihin:

```css
/* Määrittele avainkehykset komponenttien saapumiselle */
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

/* Sovelletaan katseluen siirtymän pseudo-elementtiin */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Viittaa mukautettuun animaatioosi siirtämällä sen nimi (ilman päätteitä) `enter()` tai `exit()`-metodille:

```java
// Käytä "flip-in" - webforJ lisää automaattisesti "-enter"-päätteen
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Käytä "blur-out" poistumiseen - webforJ lisää automaattisesti "-exit"-päätteen
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

## CSS-mukautus {#css-customization}

Jokainen ennalta määritelty siirtymätyyppi tarjoaa CSS-mukautusominaisuuksia hienosäätöä varten:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Häivytys</strong>
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
    <strong>Liukuvasemmalle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-left-distance` | `30%` | Liu'un etäisyys |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuoikealle</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-right-distance` | `30%` | Liu'un etäisyys |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukuylöspäin</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-up-distance` | `30%` | Liu'un etäisyys |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liukualas</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-slide-down-distance` | `30%` | Liu'un etäisyys |
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
      | `--vt-zoom-scale` | `0.8` | Skaalatekijä (vanha zoomaa tästä pois, uusi zoomaa tähän) |
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
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-zoom-out-scale` | `1.2` | Skaalatekijä (vanha zoomaa tähän, uusi zoomaa tästä pois) |
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

      Edistyksellisessä mukautuksessa kohdenna katseluen siirtymän pseudo-elementteihin suoraan:

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
