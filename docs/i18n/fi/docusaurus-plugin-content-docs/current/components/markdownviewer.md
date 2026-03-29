---
title: MarkdownViewer
sidebar_position: 74
sidebar_class_name: new-content
_i18n_hash: 4583c753ac5c37b5f1c44106347f5732
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

`MarkdownViewer` -komponentti renderöi markdown-tekstiä HTML:ksi. Se tukee standardia markdown-syntaksia, mukaan lukien otsikot, luettelot, koodilohkot, linkit, kuvat ja emojien renderöinti. Komponentti tarjoaa myös progressiivisen renderöinnin, joka näyttää sisällön merkki kerrallaan typografiovaikutuksen saavuttamiseksi.

## Sisällön asettaminen {#setting-content}

Luo `MarkdownViewer` ilman tai kanssa alkuperäisen sisällön, ja päivitä se käyttämällä `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hei maailma");

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
Komponentti implementoi `HasText`, joten `setText()` ja `getText()` toimivat sisällön menetelmien aliasina.
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='600px'
/>

## Sisällön lisääminen {#appending-content}

`append()`-metodi lisää sisältöä asteittain ilman, että se korvataan jo olemassa olevalla sisällöllä:

```java
viewer.append("## Uusi osio\n\n");
viewer.append("Lisää sisältöä täällä...");
```

Oletusarvoisesti liitetty sisältö näkyy heti. Kun [progressiivinen renderointi](#progressive-rendering) on käytössä, liitetty sisältö menee puskuriin ja näkyy merkki kerrallaan.

## Automaattinen vieritys {#auto-scroll}

Ota automaattinen vieritys käyttöön pitämään näkymä alalaidassa sisällön kasvaessa. Tämä toimii minkä tahansa sisällön lisääämisen menetelmän kanssa, olipa se sitten `setContent()`, `append()` tai progressiivinen renderointi. Jos käyttäjä vierittää manuaalisesti ylös tarkistaakseen aikaisempaa sisältöä, automaattinen vieritys keskeytyy ja jatkuu, kun he vierittävät takaisin alas.

```java
viewer.setAutoScroll(true);
```

## Progressiivinen renderointi {#progressive-rendering}

Progressiivinen renderointi näyttää sisällön merkki kerrallaan sen sijaan, että se näkyisi kaikki kerralla, luoden typografiovaikutuksen. AI-chat-käyttöliittymät käyttävät tätä usein näyttämään vastauksia, jotka näkyvät vähitellen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Kun se on käytössä, `setContent()` tai `append()` kautta lisättävä sisältö menee puskuriin ja näkyy asteittain. Kun se on pois käytöstä, sisältö näkyy heti.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Renderöintinopeus {#render-speed}

`setRenderSpeed()`-metodi hallitsee, kuinka monta merkkiä renderöidään animaatiossa. Korkeammat arvot tarkoittavat nopeampaa renderointia. 60 fps:llä oletusnopeus 4 tarkoittaa noin 240 merkkiä sekunnissa:

| Nopeus | Merkkejä/Sekunti |
|-------|-------------------|
| 4 (oletus) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Vastaa tietovirtaasi
Jos palvelimesi lähettää sisältöä nopeammin kuin katselija renderöi, puskuri kasvaa ja näkyvä sisältö jää jälkeen. Lisää `renderSpeed` pitämään vauhtia, tai kutsu `flush()`, kun kaikki sisältö on vastaanotettu, näyttääksesi jäljellä olevan sisällön heti.
:::

### Renderöintitila {#render-state}

Kun progressiivinen renderointi on käytössä, `isRendering()`-metodi palauttaa `true`, kun komponentti näyttää aktiivisesti puskuroitua sisältöä. Chat-käyttöliittymät käyttävät tätä usein näyttämään tai piilottamaan pysäytysnappia:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Tämä metodi palauttaa aina `false`, kun progressiivinen renderointi on pois käytöstä.

### Renderoinnin hallinta {#controlling-rendering}

Kaksi metodia hallitsevat, kuinka progressiivinen renderointi pysähtyy:

- **`stop()`** keskeyttää renderoinnin ja hylkää kaikki vielä näyttämättömät puskurit. Kutsu tätä, kun käyttäjä peruuttaa.
- **`flush()`** keskeyttää renderoinnin, mutta näyttää heti kaiken jäljellä olevan puskurin sisällön. Kutsu tätä, kun kaikki sisältö on vastaanotettu ja haluat näyttää sen odottamatta.

```java
// Käyttäjä napsautti "Pysäytä generoimasta"
viewer.stop();

// Kaikki sisältö vastaanotettu, näytä kaikki nyt
viewer.flush();
```

Näillä metodeilla ei ole vaikutusta, kun progressiivinen renderointi on pois käytöstä.

### Valmistumisen odottaminen {#waiting-for-completion}

`whenRenderComplete()`-metodi palauttaa `PendingResult`, joka valmistuu, kun progressiivinen renderointi on valmis näyttämästä kaiken puskurissa olevan sisällön:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Jos progressiivinen renderointi ei ole käytössä tai mitään sisältöä ei renderöidä, `PendingResult` valmistuu heti.

:::tip UI-koordinaatio
Kun käytät progressiivista renderointia, älä ota syöttökenttiä uudelleen käyttöön pelkästään sen perusteella, kun olet kutsunut `append()`. Renderöijä saattaa edelleen näyttää puskurissa olevaa sisältöä. Odota `whenRenderComplete()` varten, jotta kaikki sisältö näkyy ennen kuin käyttäjät voivat jälleen vuorovaikuttaa.
:::

Seuraava demo simuloidaan AI-chat-käyttöliittymää käyttämällä `append()`-toimintoa progressiivisen renderoinnin ollessa käytössä:

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

Jos progressiivinen renderointi on aktiivinen, `clear()` pysäyttää myös renderoinnin ja viimeistelee kaikki odottavat `whenRenderComplete()`-tulokset.

## Syntaksin korostaminen {#syntax-highlighting}

`MarkdownViewer` tukee syntaksin korostamista koodilohkoissa, kun [Prism.js](https://prismjs.com/) on saatavilla. Lisää Prism.js sovellukseesi käyttämällä `@JavaScript` ja `@StyleSheet` -annotaatioita:

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

Autoloader-plugin lataa kielten määritelmät tarpeen mukaan, joten koodilohkot, joissa on kielivihjeitä, kuten ` ```java ` tai ` ```python `, korostuvat automaattisesti.

<TableBuilder name="MarkdownViewer" />
