---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: fb5d7a0ef2a65790f0692612c07d9044
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ tarjoavat mekanismin jaettujen tietojen tallentamiseen ja hakemiseen eri alueilla verkkosovelluksessa. Ne mahdollistavat komponenttien välisen ja istuntojen välisen tietoliikenteen ilman, että luotetaan perinteisiin tallennustekniikoihin, kuten istuntotietoihin tai staattisiin kenttiin. Tämä abstraktio mahdollistaa kehittäjien kapseloida ja käyttää tilaa hallitusti ja säieystävällisesti. Namespacet ovat ihanteellisia monikäyttäjäyhteistyötyökalujen rakentamiseen tai yksinkertaisesti johdonmukaisen globaalin asetuksen ylläpitämiseen, ja ne antavat sinun koordinoida tietoja turvallisesti ja tehokkaasti.

## Mikä on namespace? {#whats-a-namespace}

Namespace on nimetty säilö, joka tallentaa avain-arvo-pareja. Nämä arvot voidaan käyttää ja muokata eri osissa sovellustasi sen mukaan, mitä namespace-tyyppiä käytät. Ajattele sitä säieystävällisenä, jaettuna karttana, jossa on sisäänrakennettu tapahtumankäsittely ja lukitusmekanismeja.

### Milloin käyttää namespacet {#when-to-use-namespaces}

Käytä namespacet, kun:

- Sinun tarvitsee jakaa arvoja käyttäjäistuntojen tai sovelluskomponenttien välillä.
- Halut toimia arvomuutoksille kuuntelijoiden kautta.
- Tarvitset hienojakoista lukitusta kriittisille osille.
- Sinun tarvitsee säilyttää ja hakea tilaa tehokkaasti koko sovelluksesi ajan.

### Namespacet tyypit {#types-of-namespaces}

webforJ tarjoaa kolme tyyppiä namespacet:

| Tyyppi      | Alue                                                                                                             | Tyypillinen käyttö                            |
|-------------|------------------------------------------------------------------------------------------------------------------|------------------------------------------------|
| **Yksityinen** | Jaettu niiden asiakkaiden kesken, jotka käyttävät samaa etuliitettä ja nimeä. Muisti vapautetaan automaattisesti, kun viittauksia ei enää ole. | Jaettu tila liittyvien käyttäjäistuntojen välillä. |
| **Ryhmä**    | Jaettu kaikilla säikeillä, jotka ovat syntyneet samasta parent-säikeestä.                                         | Tilan koordinointi säie-ryhmässä.             |
| **Globaali** | Saatavilla kaikille palvelin säikeille (JVM-laajuisesti). Muisti säilyy, kunnes avaimet poistetaan erikseen.  | Sovelluksen laajuinen jaettu tila.            |

:::tip Oletusarvon valinta - Suosi `PrivateNamespace`- käyttöä
Kun olet epävarma, käytä `PrivateNamespace`:ia. Se tarjoaa turvallista, rajattua jakamista liittyvien istuntojen välillä vaikuttamatta globaaliseen tai palvelinlaajuiseen tilaan. Tämä tekee siitä luotettavan oletuksen useimmissa sovelluksissa.
:::

## Namespace luominen ja käyttö {#creating-and-using-a-namespace}

Namespacet luodaan instanssoimalla yksi saatavilla olevista tyypeistä. Jokainen tyyppi määrittää, miten ja missä dataa jaetaan. Esimerkit alla osoittavat, kuinka luoda namespace ja vuorovaikuttaa sen arvojen kanssa.

### `Yksityinen` namespace {#private-namespace}

Yksityisen namespacen nimi koostuu kahdesta osasta:

- **Etuliite**: Kehittäjän määrittelemä tunniste, joka tulisi olla ainutlaatuinen sovelluksellesi tai moduulillesi konfliktien välttämiseksi.
- **Perusnimi**: Kananiveloitu nimi jaetulle kontekstille tai datalle, jota haluat hallita.

Yhdessä ne muodostavat täydellisen namespacen nimen muodossa:

```text
etuliite + "." + perusnimi
```

Esimerkiksi, `"myApp.sharedState"`.

Namespacet, jotka on luotu samalla etuliitteellä ja perusnimellä, viittaavat aina _samaan perustavanlaatuiseen instanssiin_. Tämä varmistaa johdonmukaisen jaetun pääsyn kaikkiin `PrivateNamespace` -kutsuihin, joissa käytetään samoja tunnisteita.

```java
// Luo tai hae yksityinen namespace
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Voit tarkistaa olemassaolon ennen luomista:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Nimivihjeet
Kun nimität `PrivateNamespace`:ia, noudata seuraavia sääntöjä:

- Molempien osien on oltava ei-tyhjää.
- Kummankin on aloitettava kirjaimella.
- Vain tulostettavat merkit ovat sallittuja.
- Tyhjiä merkkejä ei sallita.

Esimerkkejä:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (liian geneerinen, todennäköisesti aiheuttaa konflikteja)
:::

### `Ryhmä` ja `Globaali` namespacet {#group-and-global-namespaces}

Yksityisen namespacen lisäksi webforJ tarjoaa kaksi muuta tyyppiä laajemmille jakamiskonteksteille. Nämä ovat hyödyllisiä, kun tila tarvitsee säilyä yli yhden istunnon tai säie-ryhmän.

- **Globaali Namespace**: Saatavilla kaikille palvelin säikeille (JVM-laajuisesti).
- **Ryhmä Namespace**: Jaettu säikeiden kesken, jotka syntyvät samasta parentista.

```java
// Globaali jaettu tila, saatavilla sovelluslaajuisesti
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Ryhmäkohtainen tila, rajoitettu säikeisiin, jotka jakavat yhteisen parentin
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Työskentely arvojen kanssa {#working-with-values}

Namespacet tarjoavat johdonmukaisen rajapinnan jaetun datan hallitsemiseen avain-arvo-pareilla. Tämä sisältää arvojen asettamisen, hakemisen, poistamisen, pääsyn synkronoinnin ja muutosten tarkkailemisen reaaliaikaisesti.

### Arvojen asettaminen ja poistaminen {#setting-and-removing-values}

Käytä `put()`-metodia tallentaaksesi arvon tietyn avaimen alle. Jos avain on tällä hetkellä lukittu, metodi odottaa, kunnes lukitus vapautuu tai aikaraja päättyy.

```java
// Odottaa jopa 20ms (oletus) arvon asettamista
ns.put("username", "admin");

// Määritä mukautettu aikaraja millisekunneissa
ns.put("config", configObject, 100);
```

Poistaaksesi avaimen namespacesta:

```java
ns.remove("username");
```

Sekä `put()` että `remove()` ovat estäviä toimintoja, jos kohdeavain on lukittu. Jos aikaraja päättyy ennen lukituksen vapauttamista, heitetään `NamespaceLockedException`.

Turvallisten rinnakkaisten päivitysten yhteydessä, joissa sinun tarvitsee vain korvata arvo, käytä `atomicPut()`. Se lukitsee avaimen, kirjoittaa arvon ja vapauttaa lukituksen yhdessä vaiheessa:

```java
ns.atomicPut("counter", 42);
```

Tämä estää kilpajuoksutilanteet ja välttää manuaalisen lukituksen tarpeen yksinkertaisissa päivitysskenaarioissa.

### Arvojen hakeminen {#getting-values}

Arvon hakemiseen käytä `get()`:

```java
Object value = ns.get("username");
```

Jos avainta ei ole, tämä heittää `NoSuchElementException`. Poistaaksesi poikkeuksia, käytä `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Tarkistaaksesi, onko avain määritelty:

```java
if (ns.contains("username")) {
  // avain on olemassa
}
```

Jos haluat alustaa arvon laiskasti vain, kun se puuttuu, käytä `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Tämä on hyödyllistä jaetuissa arvoissa, jotka luodaan kerran ja käytetään uudelleen, kuten istuntotunnukset, konfiguraatiolohkot tai välimuistitiedot.

### Manuaalinen lukitus {#manual-locking}

Jos sinun tarvitsee suorittaa useita toimintoja samalla avaimella tai koordinoida useiden avainten välillä, käytä manuaalista lukitusta.

```java
ns.setLock("flag", 500); // Odota jopa 500ms lukitusta

// Kriittinen osio alkaa
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kriittinen osio päättyy

ns.removeLock("flag");
```

Käytä tätä kaavakuviota, kun toimintoja on suoritettava atomisesti lukemisten ja kirjoitusten kesken. Varmista aina, että lukitus vapautetaan estämään muiden säikeiden estäminen.

### Muutoksista kuunteleminen {#listening-for-changes}

Namespacet tukevat tapahtumakuuntelijoita, jotka sallivat sinun reagoida arvojen hakemiseen tai muokkaamiseen. Tämä on hyödyllistä skenaarioissa, kuten:

- Rajoituksen tai käytön kirjaaminen arkaluontoisille avaimille
- Päivitysten laukaisu, kun konfiguraatioarvo muuttuu
- Jaetun tilan muutosten seuranta monikäyttäjä sovelluksissa

#### Saatavilla olevat kuuntelumetodit {#available-listener-methods}

| Metodi                     | Laukaise                      | Laajuus            |
|----------------------------|-------------------------------|---------------------|
| `onAccess`                 | Mikä tahansa avain on luettu  | Koko namespace      |
| `onChange`                 | Mikä tahansa avain on muutettu| Koko namespace      |
| `onKeyAccess("key")`       | Tietty avain on luettu        | Per avain          |
| `onKeyChange("key")`       | Tietty avain on muutettu      | Per avain          |

Jokainen kuuntelija saa tapahtumaobjektin, joka sisältää:
- Avain nimen
- Vanha arvo
- Uusi arvo
- Viittauksen namespaceen

#### Esimerkki: Reagoi mihin tahansa avaimen muutokseen {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Avain muuttui: " + event.getVariableName());
  System.out.println("Vanha arvo: " + event.getOldValue());
  System.out.println("Uusi arvo: " + event.getNewValue());
});
```

#### Esimerkki: Seuraa pääsyä tiettyyn avaimen {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Tokenia käytettiin: " + event.getNewValue());
});
```

Kuuntelijat palauttavat `ListenerRegistration` -objektin, jota voit käyttää rekisteröidyn kuuntelijan poistamiseen myöhemmin:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logiikka
});
reg.remove();
```

## Esimerkki: Pelitilan jakaminen Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

[webforJ Tic-Tac-Toe demo](https://github.com/webforj/webforj-tictactoe) tarjoaa yksinkertaisen kaksinpelin, jossa vuorot jakautuvat käyttäjien kesken. Projekti havainnollistaa, kuinka `Namespace` voi koordinoida tilaa ilman ulkoisten työkalujen, kuten tietokantojen tai API:iden, turvautumista.

Tässä esimerkissä jaettu Java-peliesine tallennetaan `PrivateNamespace`:iin, jolloin useat asiakkaat voivat vuorovaikuttaa saman pelilogiikan kanssa. Namespace toimii pelitilan keskitettynä säilönä varmistaen, että:

- Molemmat pelaajat näkevät johdonmukaiset lautapäivitykset
- Vuorot ovat synkronoituja
- Pelilogiikka jaetaan istuntojen välillä

Ulkoisia palveluja (kuten REST tai WebSockets) ei tarvita. Kaikki koordinointi tehdään namespacen kautta, mikä korostaa niiden kykyä hallita jaettua tilaa reaaliajassa vähäisellä infrastruktuurilla.

Tutustu koodiin: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
