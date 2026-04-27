---
title: Browser History
sidebar_position: 30
_i18n_hash: 918006c1e505baa4bbffbfb32eb3d9d7
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

`BrowserHistory`-luokka webforJ:ssä tarjoaa korkean tason API:n vuorovaikutukseen selainhistorian kanssa. Selainhistoria sallii verkkosovellusten seurata käyttäjän navigointia sovelluksessa. Käyttämällä selainhistoriaa kehittäjät voivat mahdollistaa ominaisuuksia, kuten taakse- ja eteen-navigointi, tilan säilyttäminen ja dynaaminen URL-hallinta ilman koko sivun latausta.

## Navigointi historian läpi {#navigating-through-history}

Selainhistorian hallinta on keskeinen ominaisuus useimmille verkkosovelluksille. `BrowserHistory`-API mahdollistaa kehittäjille hallita, kuinka käyttäjät navigoivat sovellustensa sivujen ja tilojen läpi, jäljittelemällä tai muokkaamalla standardin selainkäyttäytymistä.

### Historian instanssin alustaminen tai hakeminen {#initializing-or-retrieving-a-history-instance}

Käyttääksesi `BrowserHistory`-API:a, sinulla on kaksi päävaihtoehtoa historian instanssin hankkimiseen:

1) **Uuden historian objektin luominen**: Jos työskentelet reitityskontekstista riippumatta, voit luoda uuden instanssin `BrowserHistory`-luokasta suoraan.

```java
BrowserHistory history = new BrowserHistory();
```
Tämä lähestymistapa on sopiva tilanteisiin, joissa sinun on hallittava historiaa eksplisiittisesti reitityskehyksen ulkopuolella.

2) **Historian hakeminen `Router`ilta**: Jos sovelluksesi käyttää webforJ:n [reititysratkaisua](../routing/overview), `Router`-komponentti luo ja hallinnoi jaettua `BrowserHistory`-instanssia. Voit käyttää tätä instanssia suoraan reitittimestä saadaksesi johdonmukaisen historian hallintatavan sovelluksesi kaikille näkymille ja navigointitoiminnoille.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Tämä menetelmä on suositeltava, kun sovelluksesi luottaa reititykseen, sillä se ylläpitää johdonmukaisuutta historian hallinnassa kaikissa näkymissä ja navigointitoimissa.

### Historian hallinta {#managing-history}
Seuraavia menetelmiä voidaan käyttää historian navigointiin webforJ-sovelluksessa:

- `back()`: Siirtää selainhistorian taaksepäin yhdellä askeleella, simuloiden käyttäjän painavan selaimen takaisin-nappia. Jos historiakästackissa ei ole enemmän merkintöjä, se pysyy nykyisellä sivulla.

  ```java
  history.back();
  ```

- `forward()`: Siirtää selainhistorian eteenpäin yhdellä askeleella, simuloiden käyttäjän painavan selaimen eteenpäin-nappia. Tämä toimii vain, jos historiakästackissa on merkintä edessä.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigoi tiettyyn kohtaan historiakästackissa perustuen indeksiin. Positiivinen numero siirtää eteenpäin, negatiivinen numero taaksepäin, ja nolla lataa nykyisen sivun uudelleen. Tämä menetelmä tarjoaa tarkempaa hallintaa verrattuna `back()`- ja `forward()`-metodeihin.

  ```java
  history.go(-2); // Siirtyy taaksepäin kahdella merkinnällä historiakästackissa
  ```

- `size()`: Hakee istunnon historiakästackin merkintöjen kokonaismäärän, mukaan lukien nykyinen ladattu sivu. Tämä voi olla hyödyllistä käyttäjän navigointipolun ymmärtämisessä tai mukautettujen navigointikontrollien toteuttamisessa.

  ```java
  int historySize = history.size();
  System.out.println("Historia pituus: " + historySize);
  ```

- `getLocation()`: Palauttaa nykyisen URL-polun suhteessa sovelluksen alkuun. Tämä metodi auttaa kehittäjiä hakemaan nykyisen polun, mikä on hyödyllistä URL-perusteisen reitityksen hallinnassa yksisivusovelluksissa.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Nykyinen polku: " + loc.getFullURI()));
  ```

Ymmärtäminen, kuinka navigoida tehokkaasti, on dynaamisten sovellusten rakentamisen kulmakivi. Kun hallitset navigoinnin perusasiat, on olennaista tietää, kuinka pääset käsiksi ja päivität URL-osoitteita, jotka liittyvät näihin navigointitapahtumiin.

## URL-osoitteen käsittely ja päivittäminen {#accessing-and-updating-url}

Keskeinen osa selaimen historian navigointia ja hallintaa on kyky käsitellä ja päivittää nykyinen URL-polku tehokkaasti. Tämä on olennaista nykyaikaisissa verkkosovelluksissa, joissa URL-muutokset vastaavat erilaisia näkymiä tai tiloja sovelluksessa. `BrowserHistory`-API tarjoaa yksinkertaisen tavan hakea ja manipuloida nykyistä polkua suhteessa sovelluksen juureen.

:::tip webforJ `Router`
Katso [`Router` artikkeli](../routing/overview) oppiaksesi lisää kattavasta URL- ja reittihallinnasta
:::

`getLocation()` hakee nykyisen URL-polun suhteessa sovelluksen alkuun. `getLocation()` metodi palauttaa `Optional<Location>`, jolloin voit saada URL:n polkuosion ilman verkkotunnusta.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Nykyinen polku: " + loc.getFullURI()));
```

## Tilanhallinta {#managing-state}

`BrowserHistory` antaa sinun tallentaa ja hallita mukautettua tilatietoa käyttämällä `pushState()` ja `replaceState()` metodeja. Käyttämällä tilanhallintamenetelmiä voit hallita, mitä tietoa tallennetaan osana historian merkintää, mikä auttaa ylläpitämään johdonmukaista käyttäjäkokemusta navigoidessasi takaisin ja eteenpäin sovelluksessasi. Seuraavia metodeja voidaan käyttää tilan hallintaan webforJ-sovelluksessa.

- `pushState(Object state, Location location)`: Lisää uuden merkinnän historian stackiin. Hyväksyy tilan objektin ja `Location`-objektin, joka edustaa uutta URL:ia.

```java
Location location = new Location("/uusi-sivu");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Korvaa nykyisen historian merkinnän. Tämä ei luo uutta merkintää stackiin kuten yllä oleva metodi.

```java
Location location = new Location("/päivitetty-sivu");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Hakee tilan objektin, joka liittyy nykyiseen historian merkintään. Tämä metodi palauttaa Optionalin, joka sisältää tilaobjektin, joka deserialisoidaan määriteltyyn luokkaan.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Nykyinen sivu: " + state.getViewName()));
```

### Tilan muutosten kuunteleminen {#listening-for-state-changes}
`BrowserHistory`-luokka tarjoaa mahdollisuuden rekisteröidä tapahtumakuuntelijoita, jotka reagoivat historian tilan muutoksiin.

`addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` rekisteröi kuuntelijan, joka aktivoituu, kun tila muuttuu, esimerkiksi kun käyttäjä painaa selaimen takaisin- tai eteenpäin-nappeja. Tämä metodi asettaa kuuntelijan selaimen `popstate`-tapahtumalle, jolloin sovelluksesi voi reagoida käyttäjätoimintoihin tai ohjelmallisesti laukaistuihin tilan muutoksiin.

```java
history.addHistoryStateChangeListener(event -> {
  System.out.println("Historia tila muuttui: " + event.getLocation().getFullURI());
});
```

Tilojen tehokas hallinta antaa sinun luoda sovelluksia, jotka reagoivat dynaamisesti käyttäjien toimiin. Käyttäjät voivat navigoida sovelluksessasi ilman kontekstin häviämistä, mikä tekee kokemuksesta sujuvampaa ja intuitiivisempaa. Lisäksi tilan tallentaminen mahdollistaa edistyneet ominaisuudet, kuten näkymäasemien palauttamisen, suodatus- tai lajitteluasetusten ylläpitämisen ja syvään linkittämisen tukemisen - kaikki nämä asioita edistävät sitouttavampaa ja luotettavampaa sovellusta.
