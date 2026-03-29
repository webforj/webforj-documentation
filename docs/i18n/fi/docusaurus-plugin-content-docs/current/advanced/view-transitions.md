---
sidebar_position: 40
title: View Transitions
_i18n_hash: 4c19f96d864f10e742350b16ffd54981
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Näkymäsiirtymät tarjoavat animoituja siirtymiä, kun [DOM](/docs/glossary#dom) muuttuu, vähentäen visuaalista häiriöitä ja säilyttäen tilallisen kontekstin navigoinnin tai sisällön päivityksen aikana. webforJ integroituu selaimen [View Transition API:in](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) käsitelläkseen animaatioiden koordinoinnin vanhojen ja uusien tilojen välillä.

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

:::warning Kokeellinen API
Tämä API on merkitty kokeelliseksi 25.11 alkaen ja voi muuttua tulevissa julkaisuissa. API:n allekirjoitus, käyttäytyminen ja suorituskykyominaisuudet voivat muuttua.
:::

## Peruskäyttö {#basic-usage}

Näkymäsiirtymän luomiseksi käytä `Page.getCurrent().startViewTransition()`, joka palauttaa rakennuspalikan siirtymän konfiguroimiseksi:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Siirtymäprosessi tallentaa nykyisen tilan, soveltaa DOM-muutoksesi `onUpdate` palautteessa ja sitten animoituu vanhasta kuvasta uuteen sisältöön. Sinun on kutsuttava `done.run()` merkitäksesi, että muutoksesi ovat valmiit.

:::warning `onUpdate` palautteen asettaminen on pakollista
Kutsuminen `start()` ilman päivityspalautteen asettamista heittää `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ tarjoaa ennalta määriteltyjä siirtymätyyppejä, joita voit soveltaa komponenteille, jotka tulevat tai poistuvat DOMista:

| Vakiomuoto | Vaikutus |
|----------|--------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristiinnäytös vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuten eteenpäin navigoinnissa) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuten takaisin navigoinnissa) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö kutistuu, uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa, uusi sisältö kutistuu |

Käytä `enter()` animoidaksesi komponenttia, joka lisätään ja `exit()` animoidaksesi komponenttia, joka poistuu:

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

## Jaetut komponenttisiirtymät {#shared-component-transitions}

Jaetut komponenttisiirtymät luovat muuntumisen vaikutuksen, jossa komponentti näyttäisi muuttuvan vanhasta näkymästä uuteen. Tämä saavutetaan antamalla komponenteille sama siirtymän nimi käyttämällä `setViewTransitionName()` menetelmää, joka on saatavilla kaikilla komponenteilla, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohtanäkymässä - sama nimi luo muodon
image.setViewTransitionName("blog-image");
```

Siirtyessään näiden näkymien välillä selain animoi komponenttia sijaintien välillä, luoden yhteyden visuaalisen kokemuksen.

:::tip Käytä ainutlaatuisia nimiä
Työskennellessäsi luetteloiden tai toistettavien komponenttien parissa, sisällytä siirtymän nimeen ainutlaatuinen tunnistin. Jokaisella komponentilla on oltava oma erillinen nimi muuttaakseen oikein vastavikastaan uudessa näkymässä. Samojen nimien käyttäminen useille näkyville komponenteille aiheuttaa määrittelemätöntä käyttäytymistä.
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

### Luettelon uudelleenjärjestäminen {#list-reordering}

Yksi yleisimmistä jaetuista komponenttisiirtymistä on luettelokohteiden animoiminen, kun niiden järjestys muuttuu. Antamalla jokaiselle kohteelle ainutlaatuisen `view-transition-name`, selain animoi komponentteja automaattisesti uusiin sijainteihinsa:

```java
// Jokaiselle kortille annetaan ainutlaatuinen siirtymän nimi sen ID:n perusteella
card.setViewTransitionName("card-" + item.id());

// Sekoitettaessa päivitetään vain DOM - selain käsittelee animaation
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

Täyden hallinnan saavuttamiseksi animaatioista voit määrittää mukautettuja CSS-avainkehyksiä. webforJ lisää siirtymän nimiin `-enter` tai `-exit` -pääkappaleet, joilla voit kohdistaa näkymäsiirtymän pseudo-elementteihin:

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

/* Sovella näkymäsiirtymän pseudo-elementtiin */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Viittaa mukautettuun animaatioosi antamalla sen nimi (ilman pääkappaletta) `enter()` tai `exit()`-kutsussa:

```java
// Käytä "flip-in" - webforJ lisää "-enter" pääkappaleen automaattisesti
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Käytä "blur-out" poistumiseen - webforJ lisää "-exit" pääkappaleen
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

## CSS-mukautukset {#css-customization}

Jokainen ennalta määritelty siirtymätyyppi altistaa CSS-mukautusominaisuuksia hienosäätöä varten:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Häivitys</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Animaation kesto |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktion |
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
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktion |
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
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktion |
      | `--vt-slide-right-distance` | `30%` | Liukumatka |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Liuku ylös</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktion |
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
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktion |
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
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktion |
      | `--vt-zoom-scale` | `0.8` | Skaala (vanhat zoomit ulos tähän, uudet zoomit sisään tältä) |
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
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktion |
      | `--vt-zoom-out-scale` | `1.2` | Skaala (vanha zoomaa sisään tähän, uusi zoomaa ulos tästä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Muuttujien ohittaminen</strong>
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

      Edistyneelle mukauttamiselle kohdistu näkymäsiirtymän pseudo-elementteihin suoraan:

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
