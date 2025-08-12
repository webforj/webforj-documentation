---
title: Namespaces
sidebar_position: 30
_i18n_hash: f3d79da01b17871bddf7543682a5e7e5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Nimit webforJ:ssä tarjoavat mekanismin jaetun datan tallentamiseen ja hakemiseen eri skopeissa web-sovelluksessa. Ne mahdollistavat komponenttien välisen ja istuntojen yli tapahtuvan dataviestinnän ilman perinteisten tallennusmenetelmien, kuten istuntoattribuuttien tai staattisten kenttien, käyttöä. Tämä abstraktio mahdollistaa kehittäjille tilan kapseloinnin ja käyttöoikeuden hallitussa, säikeistön turvallisessa ympäristössä. Nimit ovat ihanteellisia monikäyttäjäyhteistyötyökalujen rakentamiseen tai yksinkertaisesti johdonmukaisien globaalien asetusten ylläpitämiseen, ja ne mahdollistavat datan koordinoimisen turvallisesti ja tehokkaasti.

## Mikä on nimiavaruus? {#whats-a-namespace}

Nimiavaruus on nimetty säiliö, joka tallentaa avain-arvo-pareja. Näihin arvoihin voidaan käyttää pääsyä ja muuttaa niitä eri osissa sovellustasi riippuen siitä, minkä tyyppistä nimiavaruutta käytät. Voit ajatella sitä kuin säikeistön turvallista, hajautettua karttaa, jossa on sisäänrakennettu tapahtumankäsittely ja lukitussysteemit.

### Milloin käyttää nimiavaruuksia {#when-to-use-namespaces}

Käytä nimiavaruuksia, kun:

- Tarvitset jakaa arvoja käyttäjäistuntojen tai sovelluksen komponenttien välillä.
- Haluat reagoida arvojen muutoksiin kuuntelijoiden avulla.
- Tarvitset hienojakoista lukitusta kriittisille alueille.
- Sinun on säilytettävä ja haettava tilaa tehokkaasti sovelluksesi sisällä.

### Nimiavaruustyypit {#types-of-namespaces}

webforJ tarjoaa kolme tyyppiä nimiavaruuksia:

| Tyyppi      | Laajuus                                                                                                   | Tyypillinen käyttö                             |
|-------------|------------------------------------------------------------------------------------------------------------|------------------------------------------------|
| **Yksityinen** | Jaettu asiakkaiden kesken, jotka käyttävät samaa etuliitettä ja nimeä. Muisti vapautuu automaattisesti, kun viittauksia ei enää ole. | Jaettu tila liittyneiden käyttäjäistuntojen välillä. |
| **Ryhmä**   | Jaettu kaikilla säikeillä, jotka on luotu samasta vanhempi säikeestä.                                       | Tilan koordinointi säikeiden ryhmässä.          |
| **Globaali**| Saatavilla kaikilla palvelin säikeillä (JVM-laajuisesti). Muisti säilyy, kunnes avaimet poistetaan erikseen. | Sovelluksen laajuinen jaettu tila.              |

:::tip Oletusvalinnan valitseminen - Suosi `PrivateNamespace`
Kun epäilet, käytä `PrivateNamespace`:ia. Se tarjoaa turvallista, rajattua jakamista liittyneiden istuntojen kesken vaikuttamatta globaaliin tai palvelinlaajuiseen tilaan. Tämä tekee siitä luotettavan oletuksen useimmille sovelluksille. 
:::

## Nimiavaruuden luominen ja käyttäminen {#creating-and-using-a-namespace}

Nimiavaruudet luodaan instanssoimalla yksi saatavilla olevista tyypeistä. Jokainen tyyppi määrittää, miten ja missä dataa jaetaan. Alla olevat esimerkit osoittavat, kuinka luoda nimiavaruus ja olla vuorovaikutuksessa sen arvojen kanssa.

### `Private` nimiavaruus {#private-namespace}

Yksityisen nimiavaruuden nimi koostuu kahdesta osasta:

- **Etuliite**: Kehittäjän määrittelemä tunniste, joka pitäisi olla ainutlaatuinen sovelluksellesi tai moduulillesi konfliktien välttämiseksi.
- **Pohjanimi**: Spesifinen nimi jaetulle kontekstille tai datalle, jota haluat hallita.

Yhdessä ne muodostavat koko nimiavaruuden nimen käyttäen muotoa:

```text
etuliite + "." + pohjanimi
```

Esimerkiksi, `"myApp.sharedState"`.

Nimiavaruudet, jotka on luotu samalla etuliitteellä ja pohjanimellä, viittaavat aina _samaan taustaintanssiin_. Tämä varmistaa johdonmukaisen jaetun käytön kaikilla kutsuilla `PrivateNamespace`-instansseilla, joissa on samat tunnisteet.

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
Kun nimiä `PrivateNamespace`:ille, noudata seuraavia sääntöjä:

- Molempien osien on oltava tyhjät.
- Kummankin on aloitettava kirjaimella.
- Vain tulostettavat merkit ovat sallittuja.
- Välilyöntejä ei hyväksytä.

Esimerkkejä:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (liian yleinen, todennäköisesti aiheuttaa konflikteja)
:::

### `Ryhmä` ja `Globaali` nimiavaruudet {#group-and-global-namespaces}

Yksityisen nimiavaruuden lisäksi webforJ tarjoaa kaksi muuta tyyppiä laajemmille jakamiskonteksteille. Nämä ovat hyödyllisiä, kun tila tarvitsee säilyä yhden istunnon tai säikeen ryhmän yli.

- **Globaali Nimiavaruus**: Saatavilla kaikilla palvelin säikeillä (JVM-laajuisesti).
- **Ryhmä Nimiavaruus**: Jaettu säikeiden kesken, jotka alkavat samasta vanhemmasta.

```java
// Globaali jaettu tila, saatavilla sovelluksen laajuisesti
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Ryhmäkohtainen tila, rajoitettu säikeille, jotka jakavat yhteisen vanhemman
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Arvojen käsittely {#working-with-values}

Nimiavaruudet tarjoavat johdonmukaisen käyttöliittymän jaetun datan hallintaan avain-arvo-pareilla. Tämä sisältää arvojen asettamisen, hakemisen, poistamisen, pääsyn synkronoinnin ja muutosten tarkkailun reaaliajassa.

### Arvojen asettaminen ja poistaminen {#setting-and-removing-values}

Käytä `put()`-metodia tallentaaksesi arvo tietyllä avaimella. Jos avain on tällä hetkellä lukittu, metodi odottaa, kunnes lukitus vapautuu tai aika loppuu.

```java
// Odottaa enintään 20 ms (oletus) arvon asettamista
ns.put("username", "admin");

// Määritä mukautettu aikaraja millisekunteina
ns.put("config", configObject, 100);
```

Poistaaksesi avain nimiavaruudesta:

```java
ns.remove("username");
```

Sekä `put()` että `remove()` ovat estäviä toimintoja, jos kohdeavain on lukittu. Jos aikaraja loppuu ennen kuin lukitus vapautuu, heitetään `NamespaceLockedException`.

Turvallisten samanaikaisten päivitysten varalta, kun sinun tarvitsee vain ylittää arvo, käytä `atomicPut()`. Se lukitsee avaimen, kirjoittaa arvon ja vapauttaa lukituksen yhdellä askeleella:

```java
ns.atomicPut("counter", 42);
```

Tämä estää kilpailutilanteet ja välttää manuaalisen lukituksen tarpeen yksinkertaisissa päivitystapauksissa.

### Arvojen hakeminen {#getting-values}

Hakeaksesi arvon, käytä `get()`:

```java
Object value = ns.get("username");
```

Jos avainta ei ole olemassa, tämä heittää `NoSuchElementException`. Välttääksesi poikkeukset, käytä `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Tarkistaaksesi, onko avain määritelty:

```java
if (ns.contains("username")) {
  // avain on olemassa
}
```

Jos haluat laiskasti alustaa arvon vain, kun se puuttuu, käytä `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Tämä on hyödyllistä jaetuissa arvoissa, jotka luodaan kerran ja käytetään uudelleen, kuten istuntotunnuksissa, kokoonpanolohkoissa tai välimuistitiedoissa.

### Manuaalinen lukitus {#manual-locking}

Jos sinun on suoritettava useita toimintoja samalla avaimella tai koordinoitava useiden avaimien välillä, käytä manuaalista lukitusta.

```java
ns.setLock("flag", 500); // Odottaa enintään 500 ms lukitusta

// Kriittinen osa alkaa
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kriittinen osa päättyy

ns.removeLock("flag");
```

Käytä tätä mallia, kun jono operaatioita on suoritettava atomisesti lukemisen ja kirjoittamisen välillä. Varmista aina, että lukitus vapautetaan, jotta voit estää muiden säikeiden blokkaamisen.

### Muutosten kuuntelu {#listening-for-changes}

Nimiavaruudet tukevat tapahtumakuuntelijoita, jotka mahdollistavat reagoimisen arvojen pääsyyn tai muokkaamiseen. Tämä on hyödyllistä esimerkiksi:

- Arkaluontoisten avaimien pääsyn lokituksessa tai tarkastuksessa
- Päivitysten laukaisemisessa, kun kokoonpanon arvo muuttuu
- Jaetun tilan muutosten valvonnassa monikäyttäjäisissä sovelluksissa

#### Saatavilla olevat kuuntelijametodit {#available-listener-methods}

| Metodi                  | Laukaus                          | Laajuus          |
|-------------------------|----------------------------------|-------------------|
| `onAccess`              | Mikä tahansa avain luetaan       | Koko nimiavaruus  |
| `onChange`              | Mikä tahansa avain muutetaan     | Koko nimiavaruus  |
| `onKeyAccess("key")`    | Tietty avain luetaan            | Per avain         |
| `onKeyChange("key")`    | Tietty avain muutetaan          | Per avain         |

Jokainen kuuntelija vastaanottaa tapahtumaobjektin, joka sisältää:
- Avaimen nimen
- Vanhan arvon
- Uuden arvon
- Viittauksen nimiavaruuteen

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

Kuuntelijat palauttavat `ListenerRegistration`-objektin, jota voit käyttää kuuntelijan poistamiseen myöhemmin:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logiikka
});
reg.remove();
```

## Esimerkki: Pelitilan jakaminen Risti-nolla-peli {#example-sharing-game-state-in-tic-tac-toe}

[webforJ Risti-nolla-demo](https://github.com/webforj/webforj-tictactoe) tarjoaa yksinkertaisen kaksinpeli, jossa vuorot jaetaan käyttäjien kesken. Projekti osoittaa, kuinka `Namespace` voi olla hyödyllinen tilan koordinoinnissa ilman ulkoisten työkalujen, kuten tietokantojen tai API:en, luottamista.

Tässä esimerkissä jaettu Java-peliobjekti tallennetaan `PrivateNamespace`:iin, mikä mahdollistaa useiden asiakkaiden vuorovaikutuksen saman pelilogikan kanssa. Nimiavaruus toimii keskitettynä säiliönä pelitilalle, varmistaen, että:

- Molemmat pelaajat näkevät johdonmukaisia pelilautapäivityksiä
- Vuorot synkronoidaan
- Pelilogiikka on jaettu istuntojen välillä

Ei ulkoisia palveluja (kuten REST tai WebSocket) tarvita. Kaikki koordinointi tapahtuu nimiavaruuksien kautta, mikä korostaa niiden kykyä hallita jaettua tilaa reaaliajassa vähäisellä infrastruktuurilla.

Tutustu koodiin: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
