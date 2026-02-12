---
sidebar_position: 40
title: View Transitions
sidebar_class_name: new-content
_i18n_hash: eb57126d50375aa6da9197fa846291ff
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

View transitions tarjoavat animaatioita DOM:n muuttuessa, mikä vähentää visuaalista häiriötä ja ylläpitää tilallista kontekstia navigoinnin tai sisällön päivitysten aikana. webforJ integroituu selaimen [View Transition API:hin](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) käsitellen monimutkaisuudet, jotka liittyvät animaatioiden koordinointiin vanhojen ja uusien tilojen välillä.

<ComponentDemo
  path='/webforj/viewtransitionchat?'
  javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java'
  cssURL='/css/viewtransitions/chat.css'
  height='450px'
/>

:::warning Kokeellinen API
Tämä API on merkitty kokeelliseksi alkaen 25.11 ja se saattaa muuttua tulevissa versioissa. API:n allekirjoitus, käyttäytyminen ja suorituskyky voivat muuttua.
:::

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

Siirtymäprosessi ottaa kuvakaappauksen nykyisestä tilasta, soveltaa DOM-muutoksia `onUpdate` palautteessa ja animoidut vanhasta kuvasta uuteen sisältöön. Sinun on kutsuttava `done.run()`, jotta voit ilmoittaa kun muutoksesi ovat valmiit.

:::warning `onUpdate` palautteen asettaminen on pakollista
`start()` kutsuminen ilman päivityspalautteen asettamista heittää `IllegalStateException`.
:::

## Siirtymien soveltaminen {#applying-transitions}

webforJ tarjoaa määriteltyjä siirtymätyyppejä, joita voit soveltaa DOM:iin tuleville tai sieltä poistuville komponenteille:

| Vakiot | Vaikutus |
|--------|----------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristikkäissuljetaan vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö liikkuu vasemmalle (kuten eteenpäin navigaatiossa) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö liikkuu oikealle (kuten taaksepäin navigaatiossa) |
| `ViewTransition.SLIDE_UP` | Sisältö liikkuu ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö liikkuu alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö kutistuu pois, uusi sisältö kasvaa sisään |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois, uusi sisältö kutistuu sisään |

Käytä `enter()` animoidaksesi komponentin lisäämistä ja `exit()` animoidaksesi komponentin poistamista:

```java
// Animoidaan komponentin lisääminen DOM:iin
Page.getCurrent().startViewTransition()
    .enter(chatPanel, ViewTransition.ZOOM)
    .onUpdate(done -> {
        container.add(chatPanel);
        done.run();
    })
    .start();

// Animoidaan komponentin poistaminen DOM:ista
Page.getCurrent().startViewTransition()
    .exit(chatPanel, ViewTransition.FADE)
    .onUpdate(done -> {
        container.remove(chatPanel);
        done.run();
    })
    .start();
```

## Jaetut komponenttisiirtymät {#shared-component-transitions}

Jaetut komponenttisiirtymät luovat muuntumisen vaikutuksen, jossa komponentti näyttää siirtyvän vanhasta näkymästä uuteen näkymään. Tämä saavutetaan antamalla komponenteille sama siirtymän nimi käyttämällä `setViewTransitionName()`-metodia, joka on saatavilla kaikilla komponenteilla, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>-rajapinnan.

```java
// Korttinäkymässä
image.setViewTransitionName("blog-image");

// Yksityiskohtienäkymässä - sama nimi luo muunnoksen
image.setViewTransitionName("blog-image");
```

Kun siirrytään näiden näkymien välillä, selain animoi komponentin paikkojen välillä, luoden yhteyden kokenan visuaalisen kokemuksen.

:::tip Käytä ainutlaatuisia nimiä
Työskentelemällä luetteloiden tai toistettavien komponenttien kanssa, sisällytä ainutlaatuinen tunniste siirtymän nimeen. Jokaisella komponentilla on oltava oma erillinen nimi muuntaakseen oikein vastaavaan komponenttiin uudessa näkymässä. Samojen nimien käyttäminen useille näkyville komponenteille aiheuttaa määrittelemätöntä käyttäytymistä.
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

### Luettelon järjestyksen muuttaminen {#list-reordering}

Yksi yleinen käyttötapa jaetuissa komponenttisiirtymissä on luetteloiden animaatio, kun niiden järjestys muuttuu. Antamalla jokaiselle kohdalle ainutlaatuinen `view-transition-name`, selain animaatio automaattisesti siirtää komponentit uusiin paikkoihinsa:

```java
// Jokaiselle kortille annetaan ainutlaatuinen siirtymän nimi sen ID:n perusteella
card.setViewTransitionName("card-" + item.id());

// Seuraavassa sekoituksessa vain päivitä DOM - selain käsittelee animaation
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

Täydellisen hallinnan animaatioista voit määrittää mukautetut CSS-avainkehykset. webforJ liittää `-enter` tai `-exit` liitteet siirtymän nimiin, joita käytät kohdaten näkymäsiirtymän pseudo-elementit:

```css
/* Määritä avainkehykset komponenttien lisäämiseksi */
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

Viittaa mukautettuun animaatioosi käyttämällä sen nimeä (ilman liitettä) `enter()` tai `exit()`:

```java
// Käytä "flip-in" - webforJ lisää automaattisesti "-enter" liitteen
Page.getCurrent().startViewTransition()
    .enter(notification, "flip-in")
    .onUpdate(done -> {
        stage.add(notification);
        done.run();
    })
    .start();

// Käytä "blur-out" poistumiseen - webforJ lisää "-exit" liitteen
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

Jokainen ennalta määritelty siirtymätyyppi esittelee CSS-mukautusominaisuuksia hienosäätöä varten:

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fade</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|---------|
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
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|---------|
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
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|---------|
      | `--vt-slide-right-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
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
      |----------|--------|---------|
      | `--vt-slide-up-duration` | `200ms` | Animaation kesto |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
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
      |----------|--------|---------|
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
      |----------|--------|---------|
      | `--vt-zoom-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-zoom-scale` | `0.8` | Mittakaavatekijä (vanha zoomaa tästä, uusi zoomaa sisään tästä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom-out</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Muuttuja | Oletus | Kuvaus |
      |----------|--------|---------|
      | `--vt-zoom-out-duration` | `200ms` | Animaation kesto |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Easing-funktio |
      | `--vt-zoom-out-scale` | `1.2` | Mittakaavatekijä (vanha zoomaa sisään tähän, uusi zoomaa ulos tästä) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Muuttujien korvaaminen</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Mukautuksena, voit ylittää nämä muuttujat CSS:ssäsi:

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Edistykselliseen mukautukseen, kohdenna näkymäsiirtymän pseudo-elementit suoraan:

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
