---
sidebar_position: 11
title: Execute JavaScript
sidebar_class_name: new-content
description: >-
  Run client-side JavaScript from Java with executeJs, executeJsAsync, and
  executeJsVoidAsync at the app or element level.
slug: execute-javascript
_i18n_hash: c1d5b030c6f39ac6c83afc05ca4bb398
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

webforJ toimii palvelimella, mutta on hetkiä, jolloin sinun täytyy saavuttaa asiakas: vierittää ikkunaa, keskittyä kenttään, lukea selaimen arvoa tai kutsua metodia web-komponentissa. <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> -rajapinta tarjoaa tämän sillan. Sitä toteutetaan kahdella tasolla:

- [`Page`](#app-level-execution) suorittaa skriptiä koko sivun kontekstissa.
- [`Element`](#element-level-execution) suorittaa skriptiä rajattuna yhteen asiakas-elementtiin.

M Molemmat tarjoavat samat kolme metodia, joten kun tunnet alla olevat muodot, ne ovat samanlaisia käytettäväksi sekä `Page`:llä että `Element`:illä.

## Suoritusmetodit {#execution-methods}

Jokainen taso tarjoaa synkronisen metodin ja kaksi asynkronista. Ero on siinä, odottaako kutsuva säie ja palautetaanko tulos.

1. **`executeJs(String script)`**: suorittaa skriptin synkronisesti. **Suorittava säie on estetty** siihen asti, kun asiakas palauttaa, mikä maksaa yhden palvelin-asiakas kierroksen. Tulos palautuu `Object`-tyyppinä, jonka voit muuntaa ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: suorittaa skriptin asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:n, joka valmistuu, kun skripti valmistuu, joten voit reagoida tulokseen myöhemmin.

3. **`executeJsVoidAsync(String script)`**: suorittaa skriptin asynkronisesti ja ei palauta mitään palvelimelle. Käytä sitä tuloksettomissa töissä, joissa et tarvitse tulosta. Saatavilla versiosta `24.11`.

:::tip Metodin valinta
Käytä oletuksena `executeJsVoidAsync` kun aiheutat vain sivuvaikutusta asiakkaalla (vieritys, keskittyminen, metodin kutsuminen). Käytä `executeJsAsync` kun tarvitset arvon mutta haluat pysyä estämättömänä, ja säästä synkroninen `executeJs` harvoihin tapauksiin, joissa sinun on saatava tulos ennen seuraavaa Java-rivimääritystä, sillä se pidättää säiettä koko kierroksen ajan.
:::

### Tulosten lukeminen {#reading-results}

Kun skripti palauttaa arvon, webforJ muuntaa sen vastaavaksi Java-tyypiksi:

| JavaScript-arvo        | Java-tyyppi                            |
| ----------------------- | -------------------------------------- |
| number                  | `Integer`, `Long` tai `Double`       |
| string                  | `String`                               |
| boolean                 | `Boolean`                              |
| `null` tai `undefined`   | `null`                                 |
| mikä tahansa muu tyyppi | sen merkkijonoesitys                  |

Lue arvot `executeJsAsync` -metodilla, joka soveltaa muunnosta luotettavasti. Palautettu numero voi saapua `Integer`:ina, `Long`:ina tai `Double`:na, joten lue se `Number`-kenties kautta:

```java
Page.getCurrent()
    .executeJsAsync("return window.innerWidth;")
    .thenAccept(result -> {
      int width = ((Number) result).intValue();
      // käytä leveyttä
    });
```

:::warning Suosi asynkronista muotoa kun tarvitset arvon
Synkroninen `executeJs` palauttaa `null` kun suorituskonteksti ei ole valmis, esimerkiksi kun sitä kutsutaan ennen kuin komponentti on kiinnitetty. Käytä aina `executeJsAsync` -metodia, kun riippuu palautetusta arvosta, ja vältä synkronisen tuloksen muuntamista tiettyyn tyyppiin.
:::

## Sovellustason suoritus {#app-level-execution}

Kutsu metodeja <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>:lla, kun skripti koskee sivua kokonaisuudessaan eikä yhtä komponenttia. Hanki nykyinen sivu käyttämällä `Page.getCurrent()`.

Yksi yleinen tapaus on vierittää takaisin ylös reitin muutoksen jälkeen. Mikään ei tarvitse palata, joten `executeJsVoidAsync` sopii:

```java
Page.getCurrent().executeJsVoidAsync(
    "window.scrollTo({ top: 0, behavior: 'smooth' });");
```

Kun tarvitset asiakasarvoa palvelimella, lue se asynkronisesti ja toimi tuloksen saavuttua:

```java
Page.getCurrent()
    .executeJsAsync("return navigator.language;")
    .thenAccept(language -> {
      // kieli on selaimen alue, esimerkiksi "en-US"
      applyLocale(String.valueOf(language));
    });
```

:::info Sivu vs komponenttiscope
Käytä [elementtitasoista suorittamista](#element-level-execution) kun skripti tarvitsee toimia tietyn asiakas-elementin päällä sen sijaan, että koko sivun. 
:::

Alla olevassa demonstroinnissa "Kopioi linkki" valinta suorittaa skriptin `Page`:n kautta käyttämällä `executeJsVoidAsync` kirjoittaakseen kutsulinkin kävijän leikepöydälle. Kopiointi on sivuvaikutus, jolle ei ole mitään palautettavaa, joten tuloksettomat metodit sopii aivan oikein.

<ComponentDemo
path='/webforj/executejavascript'
files={[
  'src/main/java/com/webforj/samples/views/javascript/ExecuteJavaScriptView.java',
]}
height='260px'
/>

## Elementtitasoinen suoritus {#element-level-execution}

Kutsumalla samoja metodeja <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink>:lla rajaa skriptin vain tälle elementille sen sijaan, että koko sivulle. Palautusarvot sekä synkroninen että asynkroninen käyttäytyminen vastaavat edellisten sivutasojen metodeja.

Elementtiskriptit jonottavat niin kauan, että elementti on kiinnitetty DOM:iin, ennen kuin ne suoritetaan, joten voit kutsua niitä asennusvaiheessa ilman, että sinun tarvitsee odottaa kiinnittämistä itse.

### Funktion kutsuminen elementillä {#calling-a-function}

Kun haluat kutsua nimettyä asiakaspuolen funktiota sen sijaan, että suorittaisit skriptin merkkijonona, `Element` tarjoaa rinnakkaisen joukon menetelmiä. Skriptin sijaan siirrät funktion nimen ja sen argumentit, joita webforJ sarjaa ja siirtää niitä eteenpäin. Kaksi argumenttityyppiä käsitellään erityisesti: `this` korvataan asiakas-elementillä, ja mikä tahansa `Component`-argumentti korvataan sen asiakasinstanssilla kiinnityksen jälkeen.

Nämä vastaavat suoritusmetodeja, eroavat vain siinä, odottaako säie ja palautetaanko tulos:

1. **`callJsFunction(String name, Object... args)`**: kutsuu funktion synkronisesti ja palauttaa sen tuloksen `Object`-tyyppinä. Suorittava säie estyy yhden kierroksen aikana.

2. **`callJsFunctionAsync(String name, Object... args)`**: kutsuu funktion asynkronisesti ilman estämistä, ja palauttaa `PendingResult`n, joka valmistuu funktion tuloksella. Saatavilla versiossa `24.11`.

3. **`callJsFunctionVoidAsync(String name, Object... args)`**: kutsuu funktion asynkronisesti eikä palauta mitään palvelimelle. Käytä sitä tuloksettomissa kutsuissa, joissa et tarvitse palautusarvoa. Saatavilla versiossa `24.11`.

Koska kutsu odottaa, että jokainen `Component`-argumentti kiinnitetään ennen suoritusta, kutsu, joka ohittaa komponentin, joka ei koskaan kiinnity, ei koskaan valmistu.

```java
// Keskity web-komponentin syöttökenttään kutsumalla sen asiakaspuolen metodia
searchElement.callJsFunctionVoidAsync("focus");
```
