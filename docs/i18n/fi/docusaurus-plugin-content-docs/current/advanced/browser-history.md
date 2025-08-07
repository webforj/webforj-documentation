---
title: Browser History
sidebar_position: 20
_i18n_hash: 877c6513ffd8f2b3ed8d4199bc2f5b39
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

`BrowserHistory`-luokka webforJ:ssä tarjoaa korkeantason API:n, jolla voidaan vuorovaikuttaa selaimen historiatietojen kanssa. Selaimen historia mahdollistaa verkkosovellusten seurata käyttäjän navigointia sovelluksessa. Hyödyntämällä selaimen historiaa kehittäjät voivat mahdollistaa ominaisuuksia kuten takaisin- ja eteenpäin navigointi, tilan säilyttäminen ja dynaaminen URL-hallinta ilman täysivälisiä sivulatauksia.

## Navigointi historian läpi {#navigating-through-history}

Selaimen historian hallinta on ydinominaisuus useimmille verkkosovelluksille. `BrowserHistory`-API mahdollistaa kehittäjille sen hallinnan, miten käyttäjät navigoivat sovellusten sivujen ja tilojen välillä samalla jäljitellen tai muuttaen standardin selainkäyttäytymistä.

### Historian instanssin alustaminen tai hakeminen {#initializing-or-retrieving-a-history-instance}

Käyttääksesi `BrowserHistory`-API:a, sinulla on kaksi päävaihtoehtoa historian instanssin saamiseksi:

1) **Uuden historian objektin luominen**: Jos työskentelet itsenäisesti ilman reititysympäristöä, voit luoda suoraan uuden instanssin `BrowserHistory`-luokasta.

```java
BrowserHistory history = new BrowserHistory();
```
Tämä lähestymistapa sopii tilanteisiin, joissa sinun on hallittava historiaa eksplisiittisesti reitityskehyksen ulkopuolella.

2) **Historian hakeminen `Router`-komponentilta**: Jos sovelluksesi käyttää webforJ:n [reititysr Ratkaisua](../routing/overview), `Router`-komponentti luo ja hallitsee yhteistä `BrowserHistory`-instanssia. Voit päästä tähän instanssiin suoraan routerilta, varmistaen johdonmukaisen historianhallintalähestymistavan koko sovelluksessasi.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Tämä menetelmä on suositeltava, kun sovelluksesi luottaa reititykseen, koska se ylläpitää johdonmukaisuutta historianhallinnassa kaikilla näkymillä ja navigointitoimilla.

### Historian hallinta {#managing-history}
Seuraavia menetelmiä voidaan käyttää historian navigointiin webforJ-sovelluksessa:

- `back()`: Siirtää selaimen historian taaksepäin yhdellä askeleella, simuloiden käyttäjän painavan selaimen takaisin-painiketta. Jos historian pinossa ei ole enemmän merkintöjä, pysytään nykyisellä sivulla.

  ```java
  history.back();
  ```

- `forward()`: Siirtää selaimen historian eteenpäin yhdellä askeleella, simuloiden käyttäjän painavan selaimen eteenpäin-painiketta. Tämä toimii vain, jos historian pinossa on merkintöjä eteenpäin.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigoi tiettyyn kohtaan historian pinossa indeksin perusteella. Positiivinen luku siirtää eteenpäin, negatiivinen luku taaksepäin, ja nolla lataa nykyisen sivun uudelleen. Tämä menetelmä tarjoaa tarkempaa hallintaa verrattuna `back()` ja `forward()`.

  ```java
  history.go(-2); // Siirtää taaksepäin kahdella merkinnällä historian pinossa
  ```

- `size()`: Hakee historian session pinoon tallennettujen merkintöjen kokonaismäärän mukaan lukien nykyinen ladattu sivu. Tämä voi olla hyödyllistä käyttäjän navigointipolun ymmärtämisessä tai mukautettujen navigointikontrollien toteuttamisessa.

  ```java
  int historySize = history.size();
  System.out.println("Historialle pituus: " + historySize);
  ```

- `getLocation()`: Palauttaa nykyisen URL-polun suhteessa sovelluksen alkuperään. Tämä menetelmä auttaa kehittäjiä hakemaan nykyistä polkua, mikä on hyödyllistä URL-perusteisen reitityksen hallinnassa yksisivuisissa sovelluksissa.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Nykyinen polku: " + loc.getFullURI()));
  ```

Ymmärtäminen siitä, miten navigoida tehokkaasti, on dynaamisten sovellusten rakentamisen kulmakivi. Kun olet saanut perusasiat navigoinnista haltuun, on tärkeää tietää, miten päästä käsiksi ja päivittää URL-osoitteita, jotka liittyvät näihin navigointitapahtumiin.

## URL-osoitteen hakeminen ja päivittäminen {#accessing-and-updating-url}

Keskeinen näkökohta navigoinnissa ja selaimen historian hallinnassa on pystyä hakemaan ja päivittämään nykyinen URL-polku tehokkaasti. Tämä on olennaista modernissa verkkosovelluksessa, jossa URL-muutokset vastaavat erilaisia näkymiä tai tiloja sovelluksessa. `BrowserHistory`-API tarjoaa yksinkertaisen tavan noutaa ja manipuloida nykyistä polkua suhteessa sovelluksen juureen.

:::tip webforJ `Router`
Katso [`Router` artikkeli](../routing/overview) saadaksesi lisää tietoa kattavasta URL- ja reitinhallinnasta
:::

`getLocation()` noutaa nykyisen URL-polun suhteessa sovelluksen alkuperään. `getLocation()`-menetelmä palauttaa `Optional<Location>`-tyypin, jolloin voit saada URL-osoitteen polkuosuuden ilman verkkotunnusta.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Nykyinen polku: " + loc.getFullURI()));
```

## Tilan hallinta {#managing-state}

`BrowserHistory` mahdollistaa mukautetun tilatiedon tallentamisen ja hallinnan `pushState()` ja `replaceState()`-menetelmien avulla. käyttämällä tilan hallintamenetelmiä, voit hallita, mitä tietoa tallennetaan osaksi historiaa, mikä auttaa ylläpitämään johdonmukaista käyttäjäkokemusta siirryttäessä taakse- ja eteenpäin sovelluksessa. Seuraavia menetelmiä voidaan käyttää tilan hallintaan webforJ-sovelluksessasi.

- `pushState(Object state, Location location)`: Lisää uuden merkinnän historian pinoon. Hyväksyy tilan objektin ja `Location`-objektin, joka edustaa uutta URL-osoitetta.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Korvataan nykyinen merkitty historia. Tämä ei luo uutta merkintää pinossa kuten edellinen menetelmä.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Hakee tilan objektin, joka liittyy nykyiseen historiaan. Tämä menetelmä palauttaa Optional-tyypin, joka sisältää tilan objektin, joka deserialisoidaan määritettyyn luokkaan.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Nykyinen sivu: " + state.getViewName()));
```

### Tilan muutosten kuuntelu {#listening-for-state-changes}
`BrowserHistory`-luokka tarjoaa mahdollisuuden rekisteröidä tapahtumakuuntelijoita, jotka reagoivat historian tilan muutoksiin.

`addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` rekisteröi kuuntelijan, joka laukeaa, kun tila muuttuu, kuten silloin, kun käyttäjä napsauttaa selaimen takaisin- tai eteenpäin-painiketta. Tämä menetelmä määrittää kuuntelijan selaimen `popstate`-tapahtumalle, jolloin sovelluksesi voi reagoida käyttäjän toimintoihin tai ohjelmallisesti laukaistuihin tilamuutoksiin.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Historialla tila muuttui: " + event.getLocation().getFullURI());
});
```

Tilan tehokas hallinta antaa sinun luoda sovelluksia, jotka reagoivat dynaamisesti käyttäjän toimintaan. Käyttäjät voivat navigoida sovelluksesi läpi ilman kontekstin menettämistä, tehden kokemuksesta sujuvampaa ja intuitiivisempaa. Lisäksi tilan tallentaminen mahdollistaa edistyneet ominaisuudet, kuten näkymien palauttamisen, suodatin- tai lajitteluasetusten säilyttämisen sekä syvälinkityksen tuen, jotka kaikki myötävaikuttavat kiinnostavampaan ja luotettavampaan sovellukseen.
