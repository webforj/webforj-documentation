---
title: Browser History
sidebar_position: 20
_i18n_hash: 9b05a2e65e60a737d341a6bc37e9546f
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

`BrowserHistory`-luokka webforJ:ssä tarjoaa korkean tason API:n vuorovaikutukseen selaimen historian kanssa. Selainsivu history mahdollistaa verkkosovellusten seurata käyttäjän navigointia sovelluksen sisällä. Hyödyntämällä selaimen historiaa kehittäjät voivat mahdollistaa ominaisuuksia kuten taakse- ja eteenpäin navigointi, tilan säilyttäminen ja dynaaminen URL-hallinta ilman täydellisiä sivulatauksia.

## Navigointi historian läpi {#navigating-through-history}

Selaimen historian hallinta on keskeinen ominaisuus useimmissa verkkosovelluksissa. `BrowserHistory` API mahdollistaa kehittäjille hallita sitä, miten käyttäjät navigoivat sovellustensa sivujen ja tilojen välillä, jäljitellen tai muuttaen selaimen normaalia käyttäytymistä.

### Historian instanssin alustaminen tai hakeminen {#initializing-or-retrieving-a-history-instance}

Käyttääksesi `BrowserHistory` API:ta, sinulla on kaksi päävaihtoehtoa historian instanssin hankkimiseksi:

1) **Uuden historian objektin luominen**: Jos työskentelet itsenäisesti reitityskontekstista, voit luoda uuden instanssin `BrowserHistory`-luokasta suoraan.

```java
BrowserHistory history = new BrowserHistory();
```
Tämä lähestymistapa soveltuu tilanteisiin, joissa sinun on hallittava historiaa eksplisiittisesti reitityskehyksen ulkopuolella.

2) **Historian hakeminen `Router`-komponentista**: Jos sovelluksesi käyttää webforJ:n [reititysratkaisua](../routing/overview), `Router`-komponentti luo ja hallinnoi jaettua `BrowserHistory`-instanssia. Voit käyttää tätä instanssia suoraan reitittimestä, mikä varmistaa johdonmukaisen historian hallintatavan koko sovelluksessa.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Tätä menetelmää suositellaan, kun sovelluksesi perustuu reititykseen, sillä se ylläpitää johdonmukaisuutta historian hallinnassa kaikissa näkymissä ja navigointitoimissa.

### Historian hallinta {#managing-history}
Seuraavia menetelmiä voidaan käyttää historian navigointiin webforJ-sovelluksessa:

- `back()`: Siirtää selaimen historian taaksepäin yhdellä askeleella, mikä simuloi käyttäjän painavan selaimen takaisin-painiketta. Jos historiapino ei enää salli lisäyksiä, jäädään nykyiselle sivulle.

  ```java
  history.back();
  ```

- `forward()`: Siirtää selaimen historian eteenpäin yhdellä askeleella, mikä simuloi käyttäjän painavan selaimen eteenpäin-painiketta. Tämä toimii vain, jos historiapinossa on edessä merkintä.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigoi tiettyyn kohtaan historiapinossa indeksin perusteella. Positiivinen numero siirtää eteenpäin, negatiivinen numero taaksepäin ja nolla lataa nykyisen sivun uudelleen. Tämä menetelmä tarjoaa tarkemman hallinnan verrattuna `back()`- ja `forward()`-menetelmiin.

  ```java
  history.go(-2); // Siirtää taaksepäin kahdella merkinnällä historiapinossa
  ```

- `size()`: Hakee kokonaismäärän merkinnöistä istuntohistorian pinossa, mukaan lukien nykyisesti ladattu sivu. Tämä voi olla hyödyllistä ymmärtäessä käyttäjän navigointipolkua tai toteutettaessa mukautettuja navigointiohjaimia.

  ```java
  int historySize = history.size();
  System.out.println("Historiapituus: " + historySize);
  ```

- `getLocation()`: Palauttaa nykyisen URL-polun suhteessa sovelluksen alkuperään. Tämä menetelmä auttaa kehittäjiä hakemaan nykyisen polun, mikä on hyödyllistä URL-pohjaisessa reitityksessä yksisivuisissa sovelluksissa.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Nykyinen polku: " + loc.getFullURI()));
  ```

Tehokas navigoinnin ymmärtäminen on dynaamisten sovellusten rakentamisen peruskivi. Kun olet hallinnut navigoinnin perusteet, on tärkeää tietää, miten pääset käsiksi ja päivität URL-osoitteita, jotka liittyvät näihin navigointitapahtumiin.

## URL-osoitteen hakeminen ja päivittäminen {#accessing-and-updating-url}

Keskeinen osa navigointia ja selaimen historian hallintaa on kyky tehokkaasti hakea ja päivittää nykyistä URL-polku. Tämä on olennaista nykyaikaisissa verkkosovelluksissa, joissa URL-muutokset vastaavat erilaisia näkymiä tai tiloja sovelluksessa. `BrowserHistory` API tarjoaa yksinkertaisen tavan hakea ja manipuloida nykyistä polkua suhteessa sovelluksen juuriin.

:::tip webforJ `Router`
Katso [`Router` artikkeli](../routing/overview) saadaksesi lisätietoja kattavasta URL- ja reitinhallinnasta
:::

`getLocation()` hakee nykyisen URL-polun suhteessa sovelluksen alkuperään. `getLocation()`-menetelmä palauttaa `Optional<Location>`-objektin, mikä mahdollistaa URL-osoitteen polkipartion hankkimisen ilman verkkotunnusta.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Nykyinen polku: " + loc.getFullURI()));
```

## Tilanhallinta {#managing-state}

`BrowserHistory` antaa sinun tallentaa ja hallita mukautettuja tietotiloja käyttämällä `pushState()` ja `replaceState()` -menetelmiä. Käyttämällä tilanhallintamenetelmiä voit hallita, mitä tietoja säilytetään osana historiamerkintää, mikä auttaa ylläpitämään johdonmukaista käyttäjäkokemusta navigoitaessa taakse- ja eteenpäin sovelluksessa. Seuraavia menetelmiä voidaan käyttää tilan hallintaan webforJ-sovelluksessa.

- `pushState(Object state, Location location)`: Lisää uuden merkinnän historiapinoon. Hyväksyy tilakohteen ja `Location`-objektin, joka edustaa uutta URL:ia.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Korvataan nykyinen historian merkintä. Tämä ei luo uutta merkintää pinoon kuten yllä oleva menetelmä.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Hakee tilakohteen, joka liittyy nykyiseen historian merkintään. Tämä menetelmä palauttaa Optiona, joka sisältää tilakohteen, joka deserialisoidaan määritettyyn luokkaan.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Nykyinen sivu: " + state.getViewName()));
```

### Tilamuutosten kuuntelu {#listening-for-state-changes}
`BrowserHistory`-luokka tarjoaa mahdollisuuden rekisteröidä tapahtumakuuntelijoita, jotka reagoivat historian tilan muutoksiin.

`addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` rekisteröi kuuntelijan, joka laukeaa, kun tila muuttuu, kuten kun käyttäjä painaa selaimen takaisin- tai eteenpäin-painiketta. Tämä menetelmä asettaa kuuntelijan selaimen `popstate`-tapahtumalle, mikä mahdollistaa sovelluksesi reagoida käyttäjän toimintoihin tai ohjelmallisesti laukaistuihin tilamuutoksiin.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Historian tila muuttui: " + event.getLocation().getFullURI());
});
```

Tehokas tilanhallinta mahdollistaa sinun luoda sovelluksia, jotka reagoivat dynaamisesti käyttäjän toimintoihin. Käyttäjät voivat navigoida sovelluksessasi menettämättä kontekstiansa, mikä tekee kokemuksesta sujuvamman ja intuitiivisemman. Lisäksi tilan tallentaminen mahdollistaa edistykselliset ominaisuudet, kuten näkymäpaikkojen palauttamisen, suodattamis- tai lajitteluasetusten ylläpitämisen ja syvien linkkien tukemisen—kaikki nämä edesauttavat kiehtovampaa ja luotettavampaa sovellusta.
