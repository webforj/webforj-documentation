---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: 82037bcac961ffa8fefb90bf7579a3af
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Nimiavaruudet webforJ:ssä tarjoavat mekanismin yhteisten tietojen tallentamiseen ja hakemiseen eri alueilla web-sovelluksessa. Ne mahdollistavat komponenttien välistä ja sessioiden välistä tietoliikennettä ilman perinteisiä tallennusmenetelmiä, kuten sessioattribuutteja tai staattisia kenttiä. Tämä abstraktio antaa kehittäjille mahdollisuuden kapseloida ja käyttää tilaa hallitusti ja säikeet turvattuna. Nimivarat ovat täydellisiä monikäyttäjien yhteistyötyökalujen rakentamiseen tai yksinkertaisesti johdonmukaisten globaalien asetusten ylläpitämiseen, ja ne antavat sinun koordinoida tietoja turvallisesti ja tehokkaasti.

## Mikä on nimiavaruus? {#whats-a-namespace}

Nimiavaruus on nimetty säiliö, joka tallentaa avain-arvo-pareja. Näitä arvoja voidaan käyttää ja muokata eri osissa sovellustasi sen mukaan, mitä nimiavaruutta käytät. Ajattele sitä kuin säikeettömän, hajautetun kartan, jossa on sisäänrakennetut tapahtumankäsittely- ja lukitusmekanismit.

### Milloin käyttää nimiavaruuksia {#when-to-use-namespaces}

Käytä nimiavaruuksia, kun:

- Tarvitset jakaa arvoja käyttäjäsessioiden tai sovelluksen komponenttien kesken.
- Haluat reagoida arvomuutoksiin kuuntelijoiden kautta.
- Tarvitset tarkkoja lukituksia kriittisille alueille.
- Tarvitset säilyttää ja noutaa tilaa tehokkaasti sovelluksesi läpi.

### Nimiavaruuden tyypit {#types-of-namespaces}

webforJ tarjoaa kolme tyyppiä nimiavaruuksia:

| Tyyppi      | Laajuus                                                                                                               | Tyypillinen käyttö                         |
|-------------|----------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| **Yksityinen**  | Jaettu asiakkaiden kesken, jotka käyttävät samaa etuliitettä ja nimeä. Muisti vapautuu automaattisesti, kun viittauksia ei enää ole. | Jaettu tila liittyvien käyttäjäsessioiden välillä. |
| **Ryhmän**   | Jaettu kaikille säikeille, jotka on luotu samasta vanhempisäikeestä.                                                | Tilan koordinointi säiekasassa.               |
| **Globaali** | Saatavana kaikissa palvelinsäikeissä (JVM-laajuisesti). Muisti säilytetään, kunnes avaimet poistetaan erikseen.   | Sovelluksen laajuinen jaettu tila.            |

:::tip Oletusarvon valinta - Suosi `PrivateNamespace`
Kun olet epävarma, käytä `PrivateNamespace`:a. Se tarjoaa turvallista, rajattua jakamista liittyvien istuntojen kesken vaikuttamatta globaaliin tai palvelinlaajuiseen tilaan. Tämä tekee siitä luotettavan oletusarvon useimmissa sovelluksissa.
:::

## Nimiavaruuden luominen ja käyttö {#creating-and-using-a-namespace}

Nimiavaruudet luodaan instanssoimalla jokin saatavilla olevista tyypeistä. Jokainen tyyppi määrittelee, kuinka ja missä tieto jaetaan. Esimerkit alla havainnollistavat, kuinka luoda nimiavaruus ja olla vuorovaikutuksessa sen arvojen kanssa.

### `Yksityinen` nimiavaruus {#private-namespace}

Yksityisen nimiavaruuden nimi koostuu kahdesta osasta:

- **Etuliite**: Kehittäjän määrittelemä tunniste, joka pitäisi olla ainutlaatuisen sovelluksellesi tai moduulillesi, jotta vältetään ristiriidat.
- **Perusnimi**: Erityinen nimi jaetulle kontekstilta tai tiedolle, jota haluat hallita.

Yhdessä ne muodostavat täydellisen nimiavaruuden nimen seuraavassa muodossa:

```text
etuliite + "." + perusnimi
```

Esimerkiksi, `"myApp.sharedState"`.

Nimiavaruudet, jotka on luotu samasta etuliitteestä ja perusnimestä, viittaavat aina _samaan taustainstanssiin_. Tämä varmistaa johdonmukaisen jaetun pääsyn kaikkiin `PrivateNamespace`:in kutsuihin, joissa käytetään samoja tunnisteita.

```java
// Luo tai hae yksityinen nimiavaruus
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Voit tarkistaa olemassaolon ennen luomista:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Nimeämisohjeet
Kun nimeät `PrivateNamespace`:n, noudata näitä sääntöjä:

- Molempien osien on oltava ei-tyhjät.
- Jokaisen on aloitettava kirjaimella.
- Vain tulostettavat merkit ovat sallittuja.
- Välilyöntiä ei sallita.

Esimerkkejä:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (liian yleinen, todennäköisesti ristiriidassa)
:::

### `Ryhmän` ja `Globaalit` nimiavaruudet {#group-and-global-namespaces}

Yksityisen nimiavaruuden lisäksi webforJ tarjoaa kaksi muuta tyyppiä laajemmille jakamisalueille. Nämä ovat hyödyllisiä, kun tila tarvitsee pysyä yli yhdestä istunnosta tai säiekasasta.

- **Globaali Nimiavaruus**: Saatavana kaikissa palvelinsäikeissä (JVM-laajuisesti).
- **Ryhmän Nimiavaruus**: Jaettu säikeiden kesken, jotka ovat peräisin samasta vanhemmasta.

```java
// Globaali jaettu tila, saatavilla sovelluksen laajuudessa
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Ryhmälähtöinen tila, rajoitettu säikeisiin, jotka jakavat yhteisen vanhemman
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Työskentely arvojen kanssa {#working-with-values}

Nimiavaruudet tarjoavat johdonmukaisen käyttöliittymän jaetun tiedon hallitsemiseen avain-arvo-pareina. Tämä sisältää arvojen asettamisen, hakemisen, poistamisen, pääsyn synkronoinnin ja muutosten seuraamisen reaaliajassa.

### Arvojen asettaminen ja poistaminen {#setting-and-removing-values}

Käytä `put()`-metodia tallentaaksesi arvon tietyn avaimen alle. Jos avain on tällä hetkellä lukittu, metodi odottaa, kunnes lukitus vapautuu tai aikakatkaisu umpeutuu.

```java
// Odottaa enintään 20 ms (oletus) arvon asettamista
ns.put("username", "admin");

// Määritä mukautettu aikakatkaisu millisekunteina
ns.put("config", configObject, 100);
```

Poistaaksesi avaimen nimiavaruudesta:

```java
ns.remove("username");
```

Sekä `put()` että `remove()` ovat estäviä operaatioita, jos kohdeavain on lukittu. Jos aikakatkaisu umpeutuu ennen lukituksen vapautumista, heitetään `NamespaceLockedException`.

Turvallisia rinnakkaisia päivityksiä varten, joissa sinun tarvitsee vain korvata arvo, käytä `atomicPut()`. Se lukitsee avaimen, kirjoittaa arvon ja vapauttaa lukituksen yhdellä askelella:

```java
ns.atomicPut("counter", 42);
```

Tämä estää kilpailuasetelmia ja välttää tarpeen manuaaliseen lukitsemiseen yksinkertaisissa päivitysskenaarioissa.

### Arvojen hakeminen {#getting-values}

Arvon hakemiseen käytä `get()`:

```java
Object value = ns.get("username");
```

Jos avainta ei ole olemassa, tämä heittää `NoSuchElementException`. Poistaaksesi poikkeukset, käytä `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Tarkistaaksesi, onko avain määritelty:

```java
if (ns.contains("username")) {
  // avain on olemassa
}
```

Jos haluat laiskasti alustaa arvon vain, kun sitä ei ole, käytä `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Tämä on hyödyllistä jaettavissa arvoissa, jotka luodaan kerran ja käytetään uudelleen, kuten istuntotunnisteet, konfiguraatioblokit tai välimuistetut tiedot.

### Manuaalinen lukitseminen {#manual-locking}

Jos sinun täytyy suorittaa useita toimintoja samalla avaimella tai koordinoida useiden avainten välillä, käytä manuaalista lukitsemista.

```java
ns.setLock("flag", 500); // Odota enintään 500 ms lukitusta

// Kriittinen alue alkaa
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kriittinen alue päättyy

ns.removeLock("flag");
```

Käytä tätä kaavaa, kun useita toimintoja on suoritettava atomisesti lukemisten ja kirjoitusten välillä. Varmista aina, että lukitus vapautuu, jotta vältetään muiden säikeiden estäminen.

### Muutosten kuuntelu {#listening-for-changes}

Nimiavaruudet tukevat tapahtumakuuntelijoita, jotka antavat sinun reagoida arvojen käsittelyyn tai muokkaamiseen. Tämä on hyödyllistä seuraavissa skenaarioissa:

- Lokitus tai auditointi pääsyyn herkkiin avaimiin
- Päivitysten laukaiseminen, kun konfiguraatioarvo muuttuu
- Jaetun tilan muutosten seuranta monikäyttäjäisissä sovelluksissa

#### Saatavilla olevat kuuntelijametodit {#available-listener-methods}

| Metodi                     | Laukaisee                       | Laajuus            |
|---------------------------|----------------------------------|--------------------|
| `onAccess`                | Mikä tahansa avain luetaan      | Koko nimiavaruus   |
| `onChange`                | Mikä tahansa avain muutetaan    | Koko nimiavaruus   |
| `onKeyAccess("key")`      | Tietty avain luetaan            | Avainkohtaisesti    |
| `onKeyChange("key")`      | Tietty avain muutetaan          | Avainkohtaisesti    |

Jokainen kuuntelija saa tapahtumaolion, joka sisältää:
- Avaimen nimen
- Vanhan arvon
- Uuden arvon
- Viittauksen nimiavaruuteen

#### Esimerkki: Reagointi mihin tahansa avaimen muutokseen {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Avainta muutettiin: " + event.getVariableName());
  System.out.println("Vanha arvo: " + event.getOldValue());
  System.out.println("Uusi arvo: " + event.getNewValue());
});
```

#### Esimerkki: Seuraa tietyn avaimen käyttöä {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Tokenia päästiin käsiksi: " + event.getNewValue());
});
```

Kuuntelijat palauttavat `ListenerRegistration` -olion, jota voit käyttää kuuntelijan poistamiseen myöhemmin:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logiikka
});
reg.remove();
```

## Esimerkki: Pelitilan jakaminen Tik-Tak-Toessa {#example-sharing-game-state-in-tic-tac-toe}

[webforJ Tic-Tac-Toe -esittely](https://github.com/webforj/webforj-tictactoe) tarjoaa yksinkertaisen kaksinpelaajapelin, jossa vuorot jaetaan käyttäjien kesken. Projekti osoittaa, kuinka `Namespace`-moduulia voidaan käyttää tilan koordinoimiseen ilman ulkoisten työkalujen, kuten tietokantojen tai API: iden, luomista.

Tässä esimerkissä jaettu Java-peliodotus tallennetaan `PrivateNamespace`:een, mikä mahdollistaa useiden asiakaspuolten vuorovaikuttaa saman pelilogiikan kanssa. Nimiavaruus toimii pelin tilan keskitettynä säiliönä varmistaen, että:

- Molemmat pelaajat näkevät johdonmukaisia lautapäivityksiä
- Vuorot synkronoidaan
- Pelilogiikka jakautuu sessioiden kesken

Ulkoisia palveluja (kuten REST tai WebSocketit) ei tarvita. Kaikki koordinointi tapahtuu nimiavaruuksien kautta, mikä korostaa niiden kykyä hallita jaettua tilaa reaaliajassa vähäisellä infrastruktuurilla.

Tutustu koodiin: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
