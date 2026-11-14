---
title: Nimiavaruudet
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: f932a175d81df4147cae1335991db3d5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

webforJ:n nimiavaruudet tarjoavat mekanismin jaetun tiedon tallentamiseen ja noutamiseen verkkosovelluksen eri käyttöalueilla. Niiden avulla komponentit ja istunnot voivat viestiä keskenään ilman perinteisiä tallennustapoja, kuten istuntoattribuutteja tai staattisia kenttiä. Tämä abstraktio antaa kehittäjille mahdollisuuden kapseloida tilan ja käyttää sitä hallitusti ja säieturvallisesti. Nimiavaruudet sopivat erinomaisesti usean käyttäjän yhteistyötyökalujen rakentamiseen tai yhtenäisten yleisten asetusten ylläpitämiseen, ja niiden avulla tietoa voidaan koordinoida turvallisesti ja tehokkaasti.

## Mikä on nimiavaruus? {#whats-a-namespace}

Nimiavaruus on nimetty säilö, johon tallennetaan avain-arvo-pareja. Arvoja voidaan käyttää ja muokata sovelluksen eri osissa käytetyn nimiavaruustyypin mukaan. Ajattele sitä säieturvallisena, hajautettuna karttana, johon on sisäänrakennettu tapahtumien käsittely- ja lukitusmekanismit.

### Milloin nimiavaruuksia kannattaa käyttää {#when-to-use-namespaces}

Käytä nimiavaruuksia, kun:

- Sinun on jaettava arvoja käyttäjäistuntojen tai sovelluksen komponenttien kesken.
- Haluat reagoida arvojen muutoksiin kuuntelijoiden avulla.
- Tarvitset tarkkaa lukitusta kriittisille osioille.
- Sinun on tallennettava ja noudettava tilaa tehokkaasti koko sovelluksessa.

### Nimiavaruustyypit {#types-of-namespaces}

webforJ tarjoaa kolmenlaisia nimiavaruuksia:

| Tyyppi      | Käyttöalue                                                                                                           | Tyypillinen käyttötarkoitus                     |
| ----------- | -------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------- |
| **Private** | Jaettu samaa etuliitettä ja nimeä käyttävien asiakkaiden kesken. Muisti vapautetaan automaattisesti, kun viittauksia ei enää ole. | Jaettu tila toisiinsa liittyvien käyttäjäistuntojen kesken. |
| **Group**   | Jaettu kaikkien saman yläsäikeen luomien säikeiden kesken.                                                           | Säiekohtaisen tilan koordinointi.               |
| **Global**  | Käytettävissä kaikissa palvelinsäikeissä (koko JVM:ssä). Muisti säilyy, kunnes avaimet poistetaan erikseen.            | Sovelluksen laajuinen jaettu tila.              |

:::tip Oletusarvon valinta – suosi `PrivateNamespace`-luokkaa
Jos olet epävarma, käytä `PrivateNamespace`-luokkaa. Se tarjoaa turvallisen ja rajatun tavan jakaa tietoa toisiinsa liittyvien istuntojen kesken vaikuttamatta yleiseen tai koko palvelimen kattavaan tilaan. Siksi se on luotettava oletusvalinta useimmissa sovelluksissa.
:::

## Nimiavaruuden luominen ja käyttäminen {#creating-and-using-a-namespace}

Nimiavaruudet luodaan luomalla olio jostakin käytettävissä olevasta tyypistä. Kukin tyyppi määrittää, miten ja missä tietoa jaetaan. Seuraavissa esimerkeissä näytetään, miten nimiavaruus luodaan ja miten sen arvoja käsitellään.

### `Private`-nimiavaruus {#private-namespace}

Yksityisen nimiavaruuden nimi muodostuu kahdesta osasta:

- **Etuliite**: Kehittäjän määrittämä tunniste, jonka tulisi olla sovelluksellesi tai moduulillesi yksilöllinen ristiriitojen välttämiseksi.
- **Perusnimi**: Jaetulle kontekstille tai hallittavalle tiedolle annettu nimi.

Yhdessä ne muodostavat nimiavaruuden koko nimen seuraavassa muodossa:

```text
prefix + "." + baseName
```

Esimerkiksi `"myApp.sharedState"`.

Samalla etuliitteellä ja perusnimellä luodut nimiavaruudet viittaavat aina _samaan taustalla olevaan instanssiin_. Näin varmistetaan, että kaikki samalla tunnisteella `PrivateNamespace`-luokkaa käyttävät kutsut pääsevät johdonmukaisesti käsiksi jaettuun tietoon.

```java
// Create or retrieve a private namespace
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Voit tarkistaa, onko nimiavaruus jo olemassa, ennen sen luomista:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Nimeämisohjeet
Noudata seuraavia sääntöjä, kun annat nimen `PrivateNamespace`-luokalle:

- Kumpikaan osa ei saa olla tyhjä.
- Kummankin osan on alettava kirjaimella.
- Vain tulostettavat merkit ovat sallittuja.
- Tyhjämerkkejä ei sallita.

Esimerkkejä:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (liian yleinen ja todennäköisesti ristiriidassa muiden nimien kanssa)
:::

### `Group`- ja `Global`-nimiavaruudet {#group-and-global-namespaces}

`PrivateNamespace`-luokan lisäksi webforJ tarjoaa kaksi muuta tyyppiä laajempaan jakamiseen. Niistä on hyötyä, kun tilan on säilyttävä yksittäisen istunnon tai säieryhmän ulkopuolella.

- **Global-nimiavaruus**: Käytettävissä kaikissa palvelinsäikeissä (koko JVM:ssä).
- **Group-nimiavaruus**: Jaettu saman yläsäikeen luomien säikeiden kesken.

```java
// Global shared state, accessible application-wide
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Group-specific state, limited to threads sharing a common parent
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Arvojen käsittely {#working-with-values}

Nimiavaruudet tarjoavat yhtenäisen tavan hallita avain-arvo-pareihin tallennettua jaettua tietoa. Niiden avulla voi asettaa, noutaa ja poistaa arvoja, synkronoida niiden käyttöä sekä seurata muutoksia reaaliajassa.

### Arvojen asettaminen ja poistaminen {#setting-and-removing-values}

Tallenna arvo tietyn avaimen alle käyttämällä `put()`-metodia. Jos avain on lukittu, metodi odottaa lukituksen vapautumista tai aikakatkaisun umpeutumista.

```java
// Waits up to 20ms (default) to set the value
ns.put("username", "admin");

// Specify custom timeout in milliseconds
ns.put("config", configObject, 100);
```

Voit poistaa avaimen nimiavaruudesta seuraavasti:

```java
ns.remove("username");
```

Sekä `put()` että `remove()` ovat estäviä toimintoja, jos kohdeavain on lukittu. Jos aikakatkaisu umpeutuu ennen lukituksen vapautumista, heitetään `NamespaceLockedException`.

Kun haluat päivittää arvon samanaikaisesti turvallisesti ja tarvitset vain arvon korvaamisen, käytä `atomicPut()`-metodia. Se lukitsee avaimen, kirjoittaa arvon ja vapauttaa lukituksen yhdellä kertaa:

```java
ns.atomicPut("counter", 42);
```

Näin estetään kilpailutilanteet eikä yksinkertaisissa päivityksissä tarvita manuaalista lukitusta.

### Arvojen noutaminen {#getting-values}

Voit noutaa arvon käyttämällä `get()`-metodia:

```java
Object value = ns.get("username");
```

Jos avainta ei ole olemassa, metodi heittää `NoSuchElementException`-poikkeuksen. Voit välttää poikkeukset käyttämällä `getOrDefault()`-metodia:

```java
Object value = ns.getOrDefault("username", "guest");
```

Voit tarkistaa, onko avain määritetty:

```java
if (ns.contains("username")) {
  // key exists
}
```

Jos haluat alustaa arvon laiskasti vain silloin, kun sitä ei ole, käytä `computeIfAbsent()`-metodia:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Tästä on hyötyä jaetuille arvoille, jotka luodaan kerran ja joita käytetään uudelleen, kuten istuntotunnisteille, kokoonpanoille tai välimuistiin tallennetuille tiedoille.

### Manuaalinen lukitus {#manual-locking}

Jos haluat suorittaa samalle avaimelle useita toimintoja tai koordinoida useita avaimia, käytä manuaalista lukitusta.

```java
ns.setLock("flag", 500); // Wait up to 500ms for the lock

// Critical section starts
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Critical section ends

ns.removeLock("flag");
```

Käytä tätä tapaa, kun toimintosarja on suoritettava atomisesti luku- ja kirjoitustoimintojen yli. Varmista aina, että lukitus vapautetaan, jotta muiden säikeiden toiminta ei esty.

### Muutosten kuunteleminen {#listening-for-changes}

Nimiavaruudet tukevat tapahtumakuuntelijoita, joiden avulla voit reagoida arvojen käyttöön tai muokkaamiseen. Tästä on hyötyä esimerkiksi seuraavissa tilanteissa:

- Arkaluonteisten avainten käytön kirjaaminen tai auditointi
- Päivitysten käynnistäminen, kun kokoonpanoarvo muuttuu
- Jaetun tilan muutosten seuraaminen usean käyttäjän sovelluksissa

#### Käytettävissä olevat kuuntelijametodit {#available-listener-methods}

| Metodi               | Käynnistyy, kun                 | Käyttöalue          |
|----------------------|---------------------------------|---------------------|
| `onAccess`           | Mitä tahansa avainta luetaan    | Koko nimiavaruus    |
| `onChange`           | Mitä tahansa avainta muokataan  | Koko nimiavaruus    |
| `onKeyAccess("key")` | Tiettyä avainta luetaan         | Avaimen mukaan      |
| `onKeyChange("key")` | Tiettyä avainta muokataan       | Avaimen mukaan      |

Kukin kuuntelija saa tapahtumaolion, joka sisältää:
- Avaimen nimen
- Vanhan arvon
- Uuden arvon
- Viittauksen nimiavaruuteen

#### Esimerkki: reagointi minkä tahansa avaimen muutokseen {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Key changed: " + event.getVariableName());
  System.out.println("Old value: " + event.getOldValue());
  System.out.println("New value: " + event.getNewValue());
});
```

#### Esimerkki: tietyn avaimen käytön seuraaminen {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token was accessed: " + event.getNewValue());
});
```

Kuuntelijat palauttavat `ListenerRegistration`-olion, jonka avulla kuuntelijan voi poistaa käytöstä myöhemmin:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logic
});
reg.remove();
```

## Esimerkki: pelitilan jakaminen ristinollassa {#example-sharing-game-state-in-tic-tac-toe}

[webforJ:n ristinollademo](https://github.com/webforj/webforj-tictactoe) on yksinkertainen kahden pelaajan peli, jossa vuorot jaetaan pelaajien kesken. Projekti havainnollistaa, miten `Namespace`-luokkaa voidaan käyttää tilan koordinointiin ilman ulkoisia työkaluja, kuten tietokantoja tai API-rajapintoja.

Tässä esimerkissä jaettu Java-peliolio tallennetaan `PrivateNamespace`-nimiavaruuteen, jolloin useat asiakkaat voivat käyttää samaa pelilogiikkaa. Nimiavaruus toimii pelitilan keskitettynä säilönä ja varmistaa, että:

- Molemmat pelaajat näkevät pelilaudan päivitykset yhdenmukaisesti
- Vuorot synkronoidaan
- Pelilogiikka jaetaan istuntojen kesken

Ulkoisia palveluja, kuten REST- tai WebSocket-palveluja, ei tarvita. Kaikki koordinointi tapahtuu nimiavaruuksien avulla, mikä osoittaa, miten ne voivat hallita jaettua tilaa reaaliajassa vähäisellä infrastruktuurilla.

Tutustu koodiin: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<VideoPlayer video="namespaces/tic-tac-toe" width={1660} height={902} title="Tic tac toe" />
