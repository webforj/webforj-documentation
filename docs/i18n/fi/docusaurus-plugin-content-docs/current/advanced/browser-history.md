---
title: Browser History
sidebar_position: 30
_i18n_hash: e0426f58e099d38fa58fa2b722ec0605
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

`BrowserHistory`-luokka webforJ:ssä tarjoaa korkeatasoisen API:n, jolla voidaan vuorovaikuttaa selaimen historian kanssa. Selaimen historia mahdollistaa web-sovellusten seurata käyttäjän navigointia sovelluksessa. Käyttämällä selaimen historiaa kehittäjät voivat mahdollistaa ominaisuuksia, kuten takaisin- ja eteenpäin navigointi, tilan säilyttäminen ja dynaaminen URL-hallinta ilman, että koko sivua tarvitsee ladata uudelleen.

## Navigointi historian läpi {#navigating-through-history}

Selaimen historian hallinta on keskeinen ominaisuus useimmissa web-sovelluksissa. `BrowserHistory` API antaa kehittäjille mahdollisuuden hallita, kuinka käyttäjät navigoivat sovellustensa sivujen ja tilojen läpi, jäljittelemällä tai muuttaen vakiopohjaista selaimen käyttäytymistä.

### Historian instanssin alustaminen tai hakeminen {#initializing-or-retrieving-a-history-instance}

Käyttääksesi `BrowserHistory` API:a, sinulla on kaksi päävaihtoehtoa historian instanssin saamiseksi:

1) **Uuden historian objektin luominen**: Jos työskentelet itsenäisesti reititysasiayhteydestä, voit luoda suoraan uuden `BrowserHistory`-luokan instanssin.

```java
BrowserHistory history = new BrowserHistory();
```
Tämä lähestymistapa sopii tilanteisiin, joissa sinun täytyy hallita historiaa eksplisiittisesti reitityskehyksen ulkopuolella.

2) **Historian hakeminen `Router`-komponentilta**: Jos sovelluksesi käyttää webforJ:n [reititysratkaisua](../routing/overview), `Router`-komponentti luo ja hallitsee jaettua `BrowserHistory`-instanssia. Voit käyttää tätä instanssia suoraan reitittimestä, jotta historiaa voidaan hallita johdonmukaisesti koko sovelluksessa.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Tämä menetelmä on suositeltava, kun sovelluksesi nojaa reititykseen, sillä se ylläpitää johdonmukaisuutta historiatietojen hallinnassa kaikissa näkymissä ja navigointitoimissa.

### Historian hallinta {#managing-history}
Seuraavia menetelmiä voidaan käyttää historian navigointiin webforJ-sovelluksessa:

- `back()`: Siirtää selaimen historian takaisin yhdellä askeleella, simuloiden käyttäjän painavan selaimen takaisin-painiketta. Jos historiapinoon ei ole enää merkintöjä, se pysyy nykyisellä sivulla.

  ```java
  history.back();
  ```

- `forward()`: Siirtää selaimen historian eteenpäin yhdellä askeleella, simuloiden käyttäjän painavan selaimen eteenpäin-painiketta. Tämä toimii vain, jos historiapinossa on merkintä edessä.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigoi tiettyyn kohtaan historiapinossa indeksin mukaan. Positiivinen numero siirtää eteenpäin, negatiivinen numero taaksepäin, ja nolla lataa nykyisen sivun uudelleen. Tämä menetelmä tarjoaa hienojakoisempaa hallintaa verrattuna `back()` ja `forward()`.

  ```java
  history.go(-2); // Siirtää taaksepäin kahdella merkinnällä historiapinossa
  ```

- `size()`: Hakee kaikkien merkintöjen kokonaismäärän istunnon historia-pinoissa mukaan lukien tällä hetkellä ladattu sivu. Tämä voi olla hyödyllistä käyttäjän navigointipolun ymmärtämiseksi tai mukautettujen navigointikontrollien toteuttamiseksi.

  ```java
  int historySize = history.size();
  System.out.println("Historia Pituus: " + historySize);
  ```

- `getLocation()`: Palauttaa nykyisen URL-polun suhteessa sovelluksen alkuperään. Tämä menetelmä auttaa kehittäjiä hakemaan nykyisen polun, mikä on hyödyllistä URL-pohjaisessa reitityksessä yhden sivun sovelluksissa.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Nykyinen Polku: " + loc.getFullURI()));
  ```

Ymmärtäminen, kuinka navigoida tehokkaasti, on dynaamisten sovellusten rakentamisen kulmakivi. Kun sinulla on navigoinnin perusasiat hallussa, on tärkeää tietää, miten pääsee käsiksi ja päivittää URL-osoitteita, jotka liittyvät näihin navigointitapahtumiin.

## URL-osoitteen käsittely ja päivittäminen {#accessing-and-updating-url}

Keskeinen osa navigoinnissa ja selaimen historian hallinnassa on kyky käsitellä ja päivittää nykyistä URL-polkuasi tehokkaasti. Tämä on välttämätöntä moderneissa web-sovelluksissa, joissa URL-muutokset vastaavat erilaisia näkymiä tai tiloja sovelluksessa. `BrowserHistory` API tarjoaa yksinkertaisen tavan noutaa ja manipuloida nykyistä polkua suhteessa sovelluksen juureen.

:::tip webforJ `Router`
Katso [`Router` artikkeli](../routing/overview) oppiaksesi lisää kattavasta URL- ja reitinhallinnasta
:::

`getLocation()` noutaa nykyisen URL-polun suhteessa sovelluksen alkuperään. `getLocation()`-menetelmä palauttaa `Optional<Location>`, jonka avulla voit hankkia URL-osoitteen polkuosan ilman verkkotunnusta.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Nykyinen Polku: " + loc.getFullURI()));
```

## Tilanhallinta {#managing-state}

`BrowserHistory` antaa sinun tallentaa ja hallita mukautettua tilatietoa `pushState()` ja `replaceState()` -menetelmien avulla. Käyttämällä tilanhallintamenetelmiä voit hallita, mitä tietoja tallennetaan osana historiamerkintää, mikä auttaa ylläpitämään johdonmukaista käyttäjäkokemusta, kun navigoidaan edestakaisin sovelluksessa. Seuraavia menetelmiä voidaan käyttää tilan hallintaan webforJ-sovelluksessa.

- `pushState(Object state, Location location)`: Lisää uuden merkinnän historiaan. Hyväksyy tilan objektin ja `Location`-objektin, joka edustaa uutta URL-osoitetta.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Korvataan nykyinen historiamerkintä. Tämä ei luo uutta merkintää pinoon kuten yllä mainittu menetelmä.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Noutaa tilan objektin, joka liittyy nykyiseen historiamerkintään. Tämä menetelmä palauttaa Optional-tyypin, joka sisältää tilaobjektin, joka deserialisoidaan määritettyyn luokkaan.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Nykyinen Sivu: " + state.getViewName()));
```

### Tilan muutosten kuuntelu {#listening-for-state-changes}
`BrowserHistory`-luokka tarjoaa mahdollisuuden rekisteröidä tapahtumakuuntelijoita, jotka reagoivat historian tilan muutoksiin.

`addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` rekisteröi kuuntelijan, joka aktivoituu, kun tila muuttuu, esimerkiksi kun käyttäjä napsauttaa selaimen takaisin- tai eteenpäin-painikkeita. Tämä menetelmä asettaa kuuntelijan selaimen `popstate`-tapahtumalle, mikä sallii sovelluksesi reagoida käyttäjän toimiin tai ohjelmallisesti laukaistuihin tilamuutoksiin.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Historian tila muuttui: " + event.getLocation().getFullURI());
});
```

Tehokas tilan hallinta mahdollistaa sovellusten luomisen, jotka reagoivat dynaamisesti käyttäjän toimiin. Käyttäjät voivat navigoida sovelluksessasi menettämättä konteksteja, mikä tekee kokemuksesta sujuvamman ja intuitiivisemman. Lisäksi tilan tallentaminen mahdollistaa kehittyneiden ominaisuuksien, kuten näkymien palauttamisen, suodatus- tai lajitteluasetusten ylläpitämisen ja syvälinkkien tukemisen - kaikki nämä avulla voi luoda sitouttavamman ja luotettavamman sovelluksen.
