---
title: Namespaces
sidebar_position: 30
_i18n_hash: 7e34cfb824d0e1e4637bd40f4f1133cc
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Nimetty tilat webforJ:ssä tarjoavat mekanismin jakamien tietojen tallentamiseen ja hakemiseen eri laajuuksien välillä verkkosovelluksessa. Ne mahdollistavat komponenttien välisen ja istuntojen välisen tietoliikenteen ilman perinteisten tallennusmenetelmien, kuten istuntotietojen tai staattisten kenttien, käyttöä. Tämä abstraktio antaa kehittäjille mahdollisuuden kapseloida ja käyttää tilaa hallitusti ja säikeiturvallisesti. Nimetyt tilat ovat ihanteellisia monikäyttäjäyhteistyötyökalujen rakentamiseen tai yksinkertaisesti globaalien asetusten ylläpitämiseen, ja niiden avulla voit koordinoida tietoa turvallisesti ja tehokkaasti.

## Mikä on nimetty tila? {#whats-a-namespace}

Nimettu tila on nimetty säiliö, joka tallentaa avain-arvo-pareja. Näitä arvoja voidaan käyttää ja muokata sovelluksesi eri osissa riippuen käytettävästä nimetytyypistä. Voit ajatella sitä säikeiturvallisena, hajautettuna karttana, jossa on sisäänrakennettu tapahtumakäsittely ja lukitusmekanismit.

### Milloin käyttää nimettyjä tiloja {#when-to-use-namespaces}

Käytä nimettyjä tiloja, kun:

- Sinun on jaettava arvoja käyttäjäistuntojen tai sovelluskomponenttien välillä.
- Haluat reagoida arvojen muutoksiin kuuntelijoiden kautta.
- Tarvitset hienojakoista lukitusta kriittisille osioille.
- Sinun on säilytettävä ja haettava tilaa tehokkaasti sovelluksesi sisällä.

### Nimityyppien {#types-of-namespaces}

webforJ tarjoaa kolmenlaista nimettyä tilaa:

| Tyyppi        | Laajuus                                                                                                                | Tyypillinen käyttö                                 |
| ------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------- |
| **Yksityinen** | Jaettu asiakkaiden kesken, jotka käyttävät samaa etuliitettä ja nimeä. Muisti vapautuu automaattisesti, kun ei ole viittauksia. | Jaettu tila liittyvien käyttäjäistuntojen välillä. |
| **Ryhmän**   | Kaikkien säikeiden kesken, jotka on luotu samasta vanhempisäikeestä.                                                  | Tilan koordinointi säie-ryhmässä.                  |
| **Globaali**  | Saavutettava kaikissa palvelinsäikeissä (JVM-laajuisesti). Muisti säilyy, kunnes avaimet poistetaan eksplisiittisesti.  | Sovelluslaajuinen jaettu tila.                     |

:::tip Oletuksen valinta - Suosi `PrivateNamespace`
Epävarmoissa tapauksissa käytä `PrivateNamespace`-tyyppiä. Se tarjoaa turvallista, rajattua jakamista liittyvien istuntojen välillä ilman, että se vaikuttaa globaaliin tai palvelinlaajuiseen tilaan. Tämä tekee siitä luotettavan oletuksen useimmille sovelluksille.
:::

## Nimetyn tilan luominen ja käyttö {#creating-and-using-a-namespace}

Nimettömiä tiloja luodaan instanssoimalla yksi saatavilla olevista tyypeistä. Jokaisella tyypillä on oma määritelmänsä sille, miten ja missä tiedot jaetaan. Alla olevat esimerkit osoittavat, kuinka luodaan nimetty tila ja vuorovaikutetaan sen arvojen kanssa.

### `Yksityinen` nimetty tila {#private-namespace}

Yksityisen nimetytilan nimi koostuu kahdesta osasta:

- **Etuliite**: Kehittäjän määrittelemä tunniste, jonka tulee olla ainutlaatuinen sovelluksellesi tai moduulillesi konfliktien välttämiseksi.
- **Perusnimi**: Erityinen nimi jaetulle kontekstille tai tiedolle, jonka haluat hallita.

Yhdessä ne muodostavat koko nimetty tila käyttäen seuraavaa muotoa:

```text
etuliite + "." + perusnimi
```

Esimerkiksi, `"myApp.sharedState"`.

Saman etuliitteen ja perusnimen avulla luodut nimetyt tilat viittaavat aina _samaan taustainstanttiin_. Tämä varmistaa, että jaettu pääsy pysyy johdonmukaisena kaikissa kutsuissa `PrivateNamespace` käyttäen samoja tunnisteita.

```java
// Luo tai nouda yksityinen nimetty tila
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Voit tarkistaa olemassaolon ennen luomista:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Nimeämisohjeet
Kun nimeät `PrivateNamespace`, noudata seuraavia sääntöjä:

- Molempien osien on oltava tyhjät.
- Jokaisen on alettava kirjaimella.
- Vain tulostettavat merkit ovat sallittuja.
- Välilyöntejä ei sallita.

Esimerkkejä:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (liian geneerinen, todennäköisesti konfliktia)
:::

### `Ryhmän` ja `Globaali` nimetytilat {#group-and-global-namespaces}

Yksityisen nimettävän tilan lisäksi webforJ tarjoaa kaksi muuta tyyppiä laajemmille jakamiskonteksteille. Nämä ovat hyödyllisiä, kun tila on säilytettävä yli yhden istunnon tai säie-ryhmän.

- **Globaali nimetty tila**: Saavutettavissa kaikissa palvelinsäikeissä (JVM-laajuisesti).
- **Ryhmän nimetty tila**: Jaettu säikeiden kesken, jotka алttavat samasta vanhempisäikeestä.

```java
// Globaali jaettu tila, saavutettavissa sovelluslaajuisesti
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Rykmenttiin liittyvät tila, vain vanhempisäikeeseen liittyville säikeille
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Arvojen käsittely {#working-with-values}

Nimettävät tilat tarjoavat johdonmukaisen käyttöliittymän jaetun tiedon hallintaan avain-arvo-pareina. Tämä sisältää arvojen asettamisen, hakemisen, poistamisen, pääsyn synkronoinnin ja muutosten seuraamisen reaaliaikaisesti.

### Arvojen asettaminen ja poistaminen {#setting-and-removing-values}

Käytä `put()`-menetelmää tallentaaksesi arvo tietyn avaimen alle. Jos avain on tällä hetkellä lukittu, menetelmä odottaa, kunnes lukitus vapautuu tai aikaraja umpeutuu.

```java
// Odottaa enintään 20ms (oletus) arvon asettamiseksi
ns.put("username", "admin");

// Määritä mukautettu aikaraja millisekunteina
ns.put("config", configObject, 100);
```

Poistaaksesi avaimen nimetyltä tilalta, käytä:

```java
ns.remove("username");
```

Sekä `put()` että `remove()` ovat estäviä toimenpiteitä, jos kohdeavain on lukittu. Jos aikaraja umpeutuu ennen kuin lukitus vapautuu, heitetään `NamespaceLockedException`.

Turvallisten rinnakkaisten päivitysten yhteydessä, joissa sinun on vain ylikirjoitettava arvo, käytä `atomicPut()`. Se lukitsee avaimen, kirjoittaa arvon ja vapauttaa lukituksen yhdellä askeleella:

```java
ns.atomicPut("counter", 42);
```

Tämä estää kisausongelmia ja välttää manuaalisen lukituksen tarvetta yksinkertaisissa päivitysskenaarioissa.

### Arvojen hakeminen {#getting-values}

Hakeaksesi arvon, käytä `get()`:

```java
Object value = ns.get("username");
```

Jos avainta ei ole olemassa, tämä heittää `NoSuchElementException`. Välttääksesi poikkeuksia, käytä `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Tarkistaaksesi, onko avain määritelty:

```java
if (ns.contains("username")) {
  // avain olemassa
}
```

Jos haluat laiskasti inicialisoida arvon vain, kun se puuttuu, käytä `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Tämä on hyödyllistä jaetuissa arvoissa, jotka luodaan kerran ja käytetään uudelleen, kuten istuntotunnuksissa, konfiguraatioblokeissa tai välimuistissa olevissa tiedoissa.

### Manuaalinen lukitus {#manual-locking}

Jos sinun on suoritettava useita operaatioita samalla avaimella tai koordinoitava useiden avainten yli, käytä manuaalista lukitusta.

```java
ns.setLock("flag", 500); // Odota enintään 500ms lukitusta

// Kriittinen osa alkaa
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kriittinen osa päättyy

ns.removeLock("flag");
```

Käytä tätä mallia, kun useita toimintoja on suoritettava atomisesti lukemisen ja kirjoittamisen yli. Varmista aina, että lukitus vapautetaan estämään muiden säikeiden estäminen.

### Muutosten kuuntelu {#listening-for-changes}

Nimettävät tilat tukevat tapahtumakuuntelijoita, jotka mahdollistavat reagoimisen arvojen käyttöön tai muokkaamiseen. Tämä on hyödyllistä skenaarioissa, kuten:

- Arkaluonteisten avainten käytön lokituskirjaus tai auditointi
- Päivityksien käynnistäminen, kun konfiguraatioarvo muuttuu
- Jaetun tilan muutosten seuranta monikäyttäjäisissä sovelluksissa

#### Saatavilla olevat kuuntelijametodit {#available-listener-methods}

| Metodi                   | Käynnistys                       | Laajuus             |
|--------------------------|----------------------------------|---------------------|
| `onAccess`               | Mikä tahansa avain on luettu     | Koko nimetty tila   |
| `onChange`               | Mikä tahansa avain on muutettu   | Koko nimetty tila   |
| `onKeyAccess("key")`     | Tietty avain on luettu           | Per avain           |
| `onKeyChange("key")`     | Tietty avain on muutettu         | Per avain           |

Jokainen kuuntelija saa tapahtumaobjektin, joka sisältää:
- Avain nimi
- Vanha arvo
- Uusi arvo
- Viittaus nimettyyn tilaan

#### Esimerkki: Reagoida kaikkiin avaimen muutoksiin {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Avainta muutettu: " + event.getVariableName());
  System.out.println("Vanha arvo: " + event.getOldValue());
  System.out.println("Uusi arvo: " + event.getNewValue());
});
```

#### Esimerkki: Seuraa tietyn avaimen käyttöä {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Tokenia on käytetty: " + event.getNewValue());
});
```

Kuuntelijat palauttavat `ListenerRegistration`-objektin, jota voit käyttää kuuntelijan poistamiseen myöhemmin:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logiikka
});
reg.remove();
```

## Esimerkki: Pelitilan jakaminen Tic-Tac-Toessa {#example-sharing-game-state-in-tic-tac-toe}

[webforJ Tic-Tac-Toe demo](https://github.com/webforj/webforj-tictactoe) tarjoaa yksinkertaisen kaksipelaajan pelin, jossa vuorot jaetaan käyttäjien kesken. Projekti osoittaa, kuinka `Namespace` voidaan käyttää tilan koordinoimiseen ilman ulkoisten työkalujen, kuten tietokantojen tai rajapintojen, luomista.

Tässä esimerkissä jaettu Java-peliobjekti tallennetaan `PrivateNamespace`-tyyppiseen nimettävään tilaan, jolloin useat asiakkaat voivat vuorovaikuttaa saman pelilogiikan kanssa. Nimetytila toimii pelitilan keskitettynä säilönä, varmistaen että:

- Molemmat pelaajat näkevät johdonmukaiset lautapäivitykset
- Vuorot ovat synkronoituja
- Pelilogiikka on jaettu istuntojen kesken

Ei ulkoisia palveluja (kuten REST tai WebSocket) tarvita. Kaikki koordinaatio tapahtuu nimettyjen tilojen kautta, mikä korostaa niiden kykyä hallita jaettua tilaa reaaliaikaisesti vähäisellä infrastruktuurilla.

Tutustu koodiin: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
