---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: dcbc11ba7581a82ae6857abfe11a62c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

`MarkdownViewer` -komponentti renderoi markdown-tekstin HTML-muotoon. Se tukee standardia markdown-syntaksia, mukaan lukien otsikot, luettelot, koodilohkot, linkit, kuvat ja emojien renderöinti. Komponentti tarjoaa myös progressiivisen renderöinnin, joka näyttää sisällön merkki kerrallaan kirjoituskoneefektiä varten.

## Sisällön asettaminen {#setting-content}

Luo `MarkdownViewer` joko ilman tai aloitussisällöllä, ja päivitä sitten se `setContent()`-menetelmällä:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hei Maailma");

// Korvataan sisältö kokonaan
viewer.setContent("""
    ## Uusi sisältö

    - Kohde 1
    - Kohde 2
    """);

// Hanki nykyinen sisältö
String content = viewer.getContent();
```
:::tip
Komponentti toteuttaa `HasText`, joten `setText()` ja `getText()` toimivat synonyymeina sisällön menetelmille.
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='650px'
/>

## Sisällön lisääminen {#appending-content}

`append()`-metodi lisää sisältöä asteittain ilman, että se korvataan jo olemassa olevalla sisällöllä:

```java
viewer.append("## Uusi osio\n\n");
viewer.append("Lisää sisältöä tänne...");
```

Oletusarvoisesti lisätty sisältö näkyy heti. Kun [progressiivinen renderöinti](#progressive-rendering) on käytössä, lisätty sisältö menee puskuriin ja näkyy merkki kerrallaan sen sijaan.

## Autoskrollaus {#auto-scroll}

Ota autoscrollaus käyttöön, jotta näkymä pysyy alareunassa sisällön kasvaessa. Tämä toimii kaikilla sisällön lisäämismenetelmillä, olipa kyseessä `setContent()`, `append()` tai progressiivinen renderöinti. Jos käyttäjä vierittää manuaalisesti ylöspäin tarkistaakseen aikaisempaa sisältöä, autoscrollaus pysähtyy ja jatkuu, kun hän vierittää takaisin alas.

```java
viewer.setAutoScroll(true);
```

## Progressiivinen renderöinti {#progressive-rendering}

Progressiivinen renderöinti näyttää sisällön merkki kerrallaan eikä kaikkea kerralla, luoden kirjoituskoneefektiä. AI-keskusteluliittymät käyttävät tätä yleensä näyttääksesi vastauksia, jotka ilmestyvät asteittain:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Kun se on käytössä, `setContent()` tai `append()` -menetelmällä lisätty sisältö menee puskuriin ja näkyy asteittain. Kun se on pois päältä, sisältö näkyy heti.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Renderöintinopeus {#render-speed}

`setRenderSpeed()`-metodi ohjaa kuinka monta merkkiä renderöidään animaatioskeemalla. Korkeammat arvot tarkoittavat nopeampaa renderöintiä. 60 fps:llä oletusnopeus 4 tarkoittaa noin 240 merkkiä sekunnissa:

| Nopeus | Merkkejä/Sekunnissa |
|-------|-------------------|
| 4 (oletus) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Vastaa tietovirtaasi
Jos palvelimesi lähettää sisältöä nopeammin kuin katselija renderöi, puskuri kasvaa ja näkyvä sisältö jää jälkeen. Lisää `renderSpeed`-arvoa pysyäksesi tahdissa tai kutsu `flush()`, kun kaikki sisältö on vastaanotettu näyttääksesi jäljellä olevan sisällön heti.
:::

### Renderöintitila {#render-state}

Kun progressiivinen renderöinti on käytössä, `isRendering()`-metodi palauttaa `true`, kun komponentti näyttää aktiivisesti puskuroitua sisältöä. Keskusteluliittymät käyttävät usein tätä näyttääkseen tai piilottaakseen pysäytyspainikkeen:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Tämä metodi palauttaa aina `false`, kun progressiivinen renderöinti on pois päältä.

### Renderöinnin ohjaaminen {#controlling-rendering}

Kaksi metodia ohjaa, kuinka progressiivinen renderöinti pysähtyy:

- **`stop()`** keskeyttää renderöinnin ja hylkää kaikki puskuroitua sisältöä, jota ei ole vielä näytetty. Kutsu tätä, kun käyttäjä peruuttaa.
- **`flush()`** keskeyttää renderöinnin, mutta näyttää heti kaiken jäljellä olevan puskuroitua sisällön. Kutsu tätä, kun kaikki sisältö on vastaanotettu ja haluat näyttää sen ilman odottamista.

```java
// Käyttäjä napsautti "Pysäytä tuottaminen"
viewer.stop();

// Kaikki sisältö vastaanotettu, näytä kaikki nyt
viewer.flush();
```

Näillä metodeilla ei ole vaikutusta, kun progressiivinen renderöinti on pois päältä.

### Odottaminen valmistumiseen {#waiting-for-completion}

`whenRenderComplete()`-metodi palauttaa `PendingResult`-objektin, joka valmistuu, kun progressiivinen renderöinti lopettaa kaiken puskuroidun sisällön näyttämisen:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Jos progressiivinen renderöinti ei ole käytössä tai mitään sisältöä ei renderöidä, `PendingResult` valmistuu heti.

:::tip Käyttöliittymän koordinointi
Kun käytetään progressiivista renderöintiä, älä ota syöttökenttiä uudelleen käyttöön pelkästään sen perusteella, kun olet puhelut `append()`. Renderöijä saattaa vielä näyttää puskuroitua sisältöä. Odota `whenRenderComplete()` niin, että kaikki sisältö näkyy ennen kuin käyttäjät voivat olla vuorovaikutuksessa uudelleen.
:::

Seuraava demo simuloi AI-keskusteluliittymää käyttäen `append()`-menetelmää progressiivinen renderöinti käytössä:

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='700px'
/>

## Sisällön tyhjentäminen {#clearing-content}

Poista kaikki sisältö `clear()`-metodilla:

```java
viewer.clear();
```

Jos progressiivinen renderöinti on aktiivinen, `clear()` myös keskeyttää renderöinnin ja valmistaa kaikki odottavat `whenRenderComplete()`-tulokset.

## Syntaksin korostaminen {#syntax-highlighting}

`MarkdownViewer` tukee syntaksin korostamista koodilohkoille, kun [Prism.js](https://prismjs.com/) on käytettävissä. Lisää Prism.js sovellukseesi käyttämällä `@JavaScript` ja `@StyleSheet` -annotaatioita:

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/prismjs@1/themes/prism-tomorrow.min.css")
@JavaScript(
  value = "https://cdn.jsdelivr.net/combine/npm/prismjs@1/prism.min.js,npm/prismjs@1/plugins/autoloader/prism-autoloader.min.js",
  top = true
)
public class Application extends App {
  // ...
}
```

Autoloader-laajennus lataa kielimäärityksiä tarpeen mukaan, joten koodilohkot, joissa on kielivihjeitä kuten ` ```java ` tai ` ```python ` saavat korostuksen automaattisesti.

<TableBuilder name="MarkdownViewer" />
