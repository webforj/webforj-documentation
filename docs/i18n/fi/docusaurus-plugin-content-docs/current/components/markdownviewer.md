---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: e50beb488f343e35da80b6d4f9ceddf5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

`MarkdownViewer`-komponentti renderöi markdown-tekstiä HTML:ksi. Se tukee vakiomerkintäsynntaksia, mukaan lukien otsikot, luettelot, koodilohkot, linkit, kuvat ja emojit. Komponentti tarjoaa myös progressiivisen renderöinnin, joka näyttää sisällön merkki kerrallaan kirjoituskoneefektin aikaansaamiseksi.

## Sisällön asettaminen {#setting-content}

Luo `MarkdownViewer` alkuperäisellä sisällöllä tai ilman, ja päivitä se käyttämällä `setContent()`-metodia:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hei maailma");

// Korvataan sisältö täysin
viewer.setContent("""
    ## Uusi sisältö

    - Kohde 1
    - Kohde 2
    """);

// Hae nykyinen sisältö
String content = viewer.getContent();
```
:::tip
Komponentti toteuttaa `HasText`, joten `setText()` ja `getText()` toimivat synonyymeinä sisällön metodeille.
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Sisältöön lisääminen {#appending-content}

`append()`-metodi lisää sisältöä vähitellen ilman, että se korvataan jo olemassaolevalla sisällöllä:

```java
viewer.append("## Uusi osio\n\n");
viewer.append("Lisää sisältöä tähän...");
```

Oletuksena lisätty sisältö näkyy heti. Kun [progressiivinen renderöinti](#progressive-rendering) on käytössä, lisätty sisältö siirtyy välimuistiin ja näkyy merkki kerrallaan sen sijaan.

## Auto-scroll {#auto-scroll}

Ota automaattinen vieritys käyttöön, jotta näkymä pysyy alareunassa, kun sisältö kasvaa. Tämä toimii kaikilla sisällön lisäämiskeinoilla, olipa se sitten `setContent()`, `append()` tai progressiivinen renderöinti. Jos käyttäjä vierittää manuaalisesti ylös tarkastellakseen aikaisempaa sisältöä, automaattinen vieritys keskeytyy ja jatkuu, kun hän vierittää takaisin alas.

```java
viewer.setAutoScroll(true);
```

## Progressiivinen renderöinti {#progressive-rendering}

Progressiivinen renderöinti näyttää sisältöä merkki kerrallaan sen sijaan, että koko sisältö näkyisi kerralla, luoden kirjoituskoneefektin. AI-keskusteluliitännät käyttävät usein tätä vastausten näyttämiseen vähitellen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Kun se on käytössä, `setContent()` tai `append()` -metodilla lisätty sisältö siirtyy välimuistiin ja näkyy vähitellen. Kun se ei ole käytössä, sisältö näkyy heti.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Renderöintinopeus {#render-speed}

`setRenderSpeed()`-metodi kontrolloi, kuinka monta merkkiä renderöidään per animaatiokehys. Korkeammat arvot tarkoittavat nopeampaa renderöintiä. 60fps:llä oletusnopeus 4 tarkoittaa noin 240 merkkiä sekunnissa:

| Nopeus | Merkit/Sekunnissa |
|-------|-------------------|
| 4 (oletus) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Tietovirrasta vastaaminen
Jos palvelimesi lähettää sisältöä nopeammin kuin katselija renderöi, välimuisti kasvaa ja näkyvä sisältö myöhästyy. Lisää `renderSpeed` pitämään vauhtia, tai kutsu `flush()`, kun kaikki sisältö on vastaanotettu, jotta jäljelle jäävä sisältö näytetään heti.
:::

### Renderöintitila {#render-state}

Kun progressiivinen renderöinti on käytössä, `isRendering()`-metodi palauttaa `true`, kun komponentti näyttää aktiivisesti välimuistissa olevaa sisältöä. Keskustelu liitännät käyttävät usein tätä näyttämään tai piilottamaan pysäytyspainiketta:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Tämä metodi palauttaa aina `false`, kun progressiivinen renderöinti on pois käytöstä.

### Renderöinnin hallinta {#controlling-rendering}

Kaksi metodia hallitsevat, miten progressiivinen renderöinti pysähtyy:

- **`stop()`** pysäyttää renderöinnin ja hylkää kaikki välimuistissa olevat näyttämättömät sisällöt. Kutsu tätä, kun käyttäjä peruu.
- **`flush()`** pysäyttää renderöinnin mutta näyttää heti kaikki jäljellä olevat välimuistissa olevat sisällöt. Kutsu tätä, kun kaikki sisältö on vastaanotettu ja haluat näyttää sen ilman odottamista.

```java
// Käyttäjä napsautti "Pysäytä generointi"
viewer.stop();

// Kaikki sisältö vastaanotettu, näytä kaikki nyt
viewer.flush();
```

Nämä menetelmät eivät vaikuta, kun progressiivinen renderöinti on pois käytöstä.

### Valmis odottaminen {#waiting-for-completion}

`whenRenderComplete()`-metodi palauttaa `PendingResult`-objektin, joka valmistuu, kun progressiivinen renderöinti on lopettanut kaiken välimuistissa olevan sisällön näyttämisen:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Jos progressiivista renderöintiä ei ole otettu käyttöön tai mitään sisältöä ei renderöidä, `PendingResult` valmistuu heti.

:::tip UI-yhteensopivuus
Kun käytät progressiivista renderöintiä, älä ota syöttökenttiä takaisin käyttöön pelkästään sen perusteella, kun olet lopettanut `append()`-kutsumisen. Renderöijä saattaa silti näyttää välimuistissa olevaa sisältöä. Odota `whenRenderComplete()` -metodin valmistumista, jotta kaikki sisältö näkyy ennen kuin käyttäjät voivat jälleen olla vuorovaikutuksessa.
:::

Seuraava demo simulaoi AI-keskusteluliittymää käyttäen `append()`-metodia progressiivisen renderöinnin ollessa käytössä:

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Sisällön tyhjentäminen {#clearing-content}

Poista kaikki sisältö `clear()`-metodilla:

```java
viewer.clear();
```

Jos progressiivinen renderöinti on aktiivinen, `clear()` pysäyttää myös renderöinnin ja viimeistelee kaikki odottavat `whenRenderComplete()`-tulokset.

## Koodin korostus {#syntax-highlighting}

`MarkdownViewer` tukee syntaksin korostamista koodilohkoissa, kun [Prism.js](https://prismjs.com/) on käytettävissä. Lisää Prism.js sovellukseesi käyttämällä `@JavaScript` ja `@StyleSheet` -annotaatioita:

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

Autoloader-laajennus lataa kielimääritykset tarpeen mukaan, joten koodilohkot, joissa on kielivihjeitä, kuten ` ```java ` tai ` ```python `, korostuvat automaattisesti.

<TableBuilder name="MarkdownViewer" />
