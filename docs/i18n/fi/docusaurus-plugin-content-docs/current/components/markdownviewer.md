---
title: MarkdownViewer
sidebar_position: 74
description: >-
  Render markdown as HTML with the MarkdownViewer component, supporting append,
  auto-scroll, and progressive typewriter rendering.
_i18n_hash: fbd31d2317bf5de95c282a1319f35cf6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

`MarkdownViewer` -komponentti renderoi markdown-tekstin HTML:ksi. Se tukee standardin markdown-syntaksia, mukaan lukien otsikot, luettelot, koodilohkot, linkit, kuvat ja emojien renderointi. Komponentti tarjoaa myös progressiivisen renderoinnin, joka näyttää sisällön merkki kerrallaan kirjoituskoneefektin luomiseksi.

## Aseta sisältö {#setting-content}

Luo `MarkdownViewer` aloitussisällöllä tai ilman, ja päivitä se käyttämällä `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hei maailma");

// Korvata koko sisältö
viewer.setContent("""
    ## Uusi sisältö

    - Kohde 1
    - Kohde 2
    """);

// Hanki nykyinen sisältö
String content = viewer.getContent();
```
:::tip
Komponentti toteuttaa `HasText`, joten `setText()` ja `getText()` toimivat aliasina sisällön metodeille.
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Sisällön lisääminen {#appending-content}

`append()`-metodi lisää sisältöä asteittain ilman, että korvataan jo olemassa olevaa:

```java
viewer.append("## Uusi osio\n\n");
viewer.append("Lisää sisältöä täällä...");
```

Oletusarvoisesti lisätty sisältö näkyy heti. Kun [progressiivinen renderointi](#progressive-rendering) on käytössä, lisätty sisältö siirtyy puskuriin ja näkyy merkki kerrallaan.

## Automaattinen vieritys {#auto-scroll}

Ota automaattinen vieritys käyttöön, jotta näkymä pysyy alareunassa sisällön kasvaessa. Tämä toimii kaikilla tavoilla, joilla sisältöä lisätään, oli se sitten `setContent()`, `append()` tai progressiivinen renderointi. Jos käyttäjä vierittää manuaalisesti ylös tarkistaakseen aikaisempia sisältöjä, automaattinen vieritys pysähtyy ja jatkuu, kun he vierittävät takaisin alas.

```java
viewer.setAutoScroll(true);
```

## Progressiivinen renderointi {#progressive-rendering}

Progressiivinen renderointi näyttää sisältöä merkki kerrallaan sen sijaan, että kaikki näkyisi kerralla, luoden kirjoituskoneefektin. AI-chat-käyttöliittymät käyttävät tätä yleisesti näyttääkseen vastauksia, jotka ilmestyvät vähitellen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Kun se on käytössä, `setContent()`- tai `append()`-metodilla lisätty sisältö menee puskuriin ja näkyy asteittain. Kun se on pois käytöstä, sisältö näkyy heti.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Renderointinopeus {#render-speed}

`setRenderSpeed()`-metodi hallitsee kuinka monta merkkiä renderoidaan per animaatioruutu. Korkeammat arvot tarkoittavat nopeampaa renderointia. 60fps:ssä oletusnopeus 4 tarkoittaa noin 240 merkkiä sekunnissa:

| Nopeus | Merkkejä / Sekunti |
|--------|--------------------|
| 4 (oletus) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Tietovirran yhteensovittaminen
Jos palvelimesi lähettää sisältöä nopeammin kuin katseluohjelma renderoi, puskuri kasvaa ja näytettävä sisältö jää jälkeen. Lisää `renderSpeed` pitääksesi tahdin, tai kutsu `flush()`, kun kaikki sisältö on vastaanotettu, jotta jäljellä oleva sisältö näkyy heti.
:::

### Renderointitila {#render-state}

Kun progressiivinen renderointi on käytössä, `isRendering()`-metodi palauttaa `true`, kun komponentti näyttää aktiivisesti puskuroitua sisältöä. Chat-käyttöliittymät käyttävät tätä usein näyttämään tai piilottamaan pysäytysnappia:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Tämä metodi palauttaa aina `false`, kun progressiivinen renderointi on pois käytöstä.

### Renderoinnin hallinta {#controlling-rendering}

Kaksi metodia hallitsevat sitä, kuinka progressiivinen renderointi lopetetaan:

- **`stop()`** pysäyttää renderoinnin ja hylkää kaikki vielä näyttämättömät puskuroitua sisältöä. Kutsu tätä, kun käyttäjä peruuttaa.
- **`flush()`** pysäyttää renderoinnin, mutta näyttää heti kaikki jäljellä oleva puskuroitu sisältö. Kutsu tätä, kun kaikki sisältö on vastaanotettu ja haluat näyttää sen ilman odottamista.

```java
// Käyttäjä napsautti "Pysäytä generointi"
viewer.stop();

// Kaikki sisältö vastaanotettu, näytä kaikki nyt
viewer.flush();
```

Näillä metodeilla ei ole vaikutusta, kun progressiivinen renderointi on pois käytöstä.

### Valmistumisen odottaminen {#waiting-for-completion}

`whenRenderComplete()`-metodi palauttaa `PendingResult`-objektin, joka valmistuu, kun progressiivinen renderointi on lopettanut kaikkien puskuroitujen sisältöjen näyttämisen:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Jos progressiivinen renderointi ei ole käytössä tai mitään sisältöä ei renderöidä, `PendingResult` valmistuu heti.

:::tip UI-yhteensovitus
Kun käytät progressiivista renderointia, älä ota syöttökenttiä uudelleen käyttöön pelkästään sen perusteella, kun olet lopettanut `append()`-kutsun. Renderöijä voi edelleen näyttää puskuroitua sisältöä. Odota `whenRenderComplete()`, jotta kaikki sisältö ilmestyy ennen kuin käyttäjät voivat vuorovaikuttaa taas.
:::

Seuraava demo simuloi AI-chat-käyttöliittymää käyttäen `append()`-metodia, kun progressiivinen renderointi on käytössä:

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Sisällön tyhjentäminen {#clearing-content}

Poista kaikki sisältö käyttämällä `clear()`:

```java
viewer.clear();
```

Jos progressiivinen renderointi on aktiivinen, `clear()` pysäyttää myös renderoinnin ja lopettaa kaikki odottavat `whenRenderComplete()` tulokset.

## Syntaksin korostaminen {#syntax-highlighting}

`MarkdownViewer` tukee syntaksin korostamista koodilohkoissa, kun [Prism.js](https://prismjs.com/) on saatavilla. Tuo Prism sovellukseesi [frontend bundlerin](/docs/managing-resources/bundler/overview) avulla: julista paketti `App`-luokassasi ja kirjoita entry, joka tuo Prismin, automaattisen latauslaajennuksen ja teeman.

```java title="Application.java"
@BundlePackage(value = "prismjs", version = "^1.29.0")
@BundleEntry("prism/entry.ts")
public class Application extends App {
  // ...
}
```

```ts title="src/main/frontend/prism/entry.ts"
import "prismjs";
import "prismjs/plugins/autoloader/prism-autoloader";
import "prismjs/themes/prism-tomorrow.min.css";
```

Automaattinen latauslaajennus lataa kielimäärittelyt tarpeen mukaan, joten koodilohkot, joissa on kielivinkkejä kuten ` ```java ` tai ` ```python ` korostuvat automaattisesti.

<TableBuilder name="MarkdownViewer" />
