---
title: Working With Data
sidebar_position: 3
_i18n_hash: 3afbf6e4eb4921183cc11d87c8457150
---
Tässä vaiheessa keskitytään datanhallinnan ja näyttömahdollisuuksien lisäämiseen demosovellukseen. Tätä varten luodaan keinotekoista tietoa eri `Customer`-objekteista, ja sovellusta päivitetään käsittelemään näitä tietoja ja näyttämään niitä [`Table`](../../components/table/overview), joka lisätään aiempaan sovellukseen.

Tässä vaiheessa luodaan `Customer`-malliluokka ja integroidaan se `Service`-luokan kanssa, jotta päästään käsiksi ja hallitaan tarvittavia tietoja käyttöliittymän kautta. Sen jälkeen käydään läpi, miten saatuja tietoja käytetään `Table`-komponentin toteuttamiseen sovelluksessa, jolloin asiakkaan tietoja näytetään interaktiivisessa ja rakenteellisessa muodossa.

Tämän vaiheen lopussa sovellus, joka on luotu [edellisessä vaiheessa](./creating-a-basic-app), näyttää taulukon luodusta datasta, jota voidaan kehittää seuraavissa vaiheissa. Sovelluksen suorittamiseksi:

- Siirry `2-working-with-data`-hakemistoon
- Suorita `mvn jetty:run`

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

## Datan mallin luominen {#creating-a-data-model}

Jotta voit luoda `Table`-komponentin, joka näyttää tietoja päätösovelluksessa, tarvitaan Java-bean-luokka, jota voidaan käyttää `Table`:n kanssa tietojen näyttämiseen.

Tässä ohjelmassa `Customer`-luokka `src/main/java/com/webforj/demos/data/Customer.java` tekee tämän. Tämä luokka toimii sovelluksen keskeisenä datamallina, kapseloinnin asiakasta koskevat attribuutit, kuten `firstName`, `lastName`, `company` ja `country`. Tämä malli sisältää myös ainutlaatuisen ID:n.

```java title="Customer.java"
public class Customer implements HasEntityKey {
  private String firstName = "";
  private String lastName = "";
  private String company = "";
  private Country country = Country.UNKNOWN;
  private UUID uuid = UUID.randomUUID();

  public enum Country {

    @SerializedName("Germany")
    GERMANY,

    // Jäljellä olevat maat
  }

    // Getterit ja setterit

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info `HasEntityKey`:n käyttäminen ainutkertaisille tunnisteille

`HasEntityKey`-rajapinnan toteuttaminen on ratkaisevan tärkeää, jotta mallit, joita käytetään `Table`:n kanssa, voivat hallita ainutlaatuisia tunnisteita. Se varmistaa, että jokaisella mallin instanssilla on ainutkertainen avain, jolloin `Table` voi tehokkaasti tunnistaa ja hallita rivejä.

Tässä demossa `getEntityKey()`-metodi palauttaa UUID:n jokaiselle asiakkaalle, mikä varmistaa ainutkertaisen tunnistamisen. Vaikka UUID:ita käytetään täällä yksinkertaisuuden vuoksi, todellisissa sovelluksissa tietokannan ensisijainen avain on usein parempi vaihtoehto ainutkaisten avainten luomiseksi.

Jos `HasEntityKey`:ta ei toteuteta, `Table` käyttää oletusarvoisesti Java:n hash-koodia avaimena. Koska hash-koodit eivät takaa ainutlaatuisuutta, tämä voi aiheuttaa konflikteja `Table`:n rivien hallinnassa.
:::

Kun `Customer`-datamalli on paikallaan, seuraava vaihe on hallita ja järjestää näitä malleja sovelluksessa.

## `Service`-luokan luominen {#creating-a-service-class}

Keskitettyä tietojen hallintaa varten `Service`-luokka ei vain lataa `Customer`-tietoja, vaan myös tarjoaa tehokkaan käyttöliittymän pääsyyn ja vuorovaikutukseen niiden kanssa.

`Service.java`-luokka luodaan `src/main/java/com/webforj/demos/data`-hakemistoon. Sen sijaan, että tietoja siirrettäisiin manuaalisesti komponenttien tai luokkien välillä, `Service` toimii jaettuna resurssina, jolloin kiinnostuneet osapuolet voivat helposti noutaa ja käsitellä tietoja.

Tässä demossa `Service`-luokka lukee asiakastietoja JSON-tiedostosta, joka sijaitsee `src/main/resources/data/customers.json`. Tiedot kartoitetaan `Customer`-objekteiksi ja tallennetaan `ArrayList`-tietorakenteeseen, joka muodostaa perustan taulukon `Repository`:lle.

webforJ:ssä `Repository`-luokka tarjoaa rakenteellisen tavan hallita ja noutaa kokoelmia entiteeteistä. Se toimii rajapintana sovelluksen ja sen tietojen välillä ja tarjoaa menetelmiä tietojen kyselyyn, laskentaan ja päivittämiseen säilyttäen samalla puhtaan ja johdonmukaisen rakenteen. Sitä käyttää `Table`-luokka tietojen näyttämiseen, jotka on tallennettu sisälle.

Vaikka `Repository`-luokassa ei ole menetelmiä entiteettien päivittämiseen tai poistamiseen, se toimii rakenteellisena kääreenä objektikokoelman ympärillä. Tämä tekee siitä ihanteellisen tarjoamaan organisoitua ja tehokasta tietojen käyttöä.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  //Jäljellä oleva toteutus
}
```

Jotta `Repository` saadaan täytettyä tiedoilla, `Service`-luokka toimii keskeisenä hallitsijana, joka huolehtii resurssien lataamisesta ja järjestämisestä sovelluksessa. Asiakastiedot luetaan JSON-tiedostosta ja kartoitellaan `Customer`-objekteihin `Repository`:ssä.

webforJ:ssä `Assets`-työkalu helpottaa tämän datan dynaamista lataamista kontekstin URL-osoitteita käyttäen. Esimerkiksi asiakastiedot voidaan ladata JSON-tiedostosta seuraavasti:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Käyttäen `ObjectTable`:ta
`Service`-luokka käyttää `ObjectTable`:ta hallitsemaan instansseja dynaamisesti sen sijaan, että luottaisi staattisiin kenttiin. Tämä lähestymistapa ratkaisee tärkeän rajoituksen, kun käytetään servlettejä: staattiset kentät ovat sidottuja palvelimen elinkaareen ja voivat aiheuttaa ongelmia ympäristöissä, joissa on useita pyyntöjä tai samanaikaisia istuntoja. `ObjectTable` on sidottu käyttäjäistuntoon, ja sen käyttäminen varmistaa yksinäisen kaltaisen käyttäytymisen ilman näitä rajoituksia, mikä mahdollistaa johdonmukaisen ja skaalautuvan tietojen hallinnan.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Yksityinen konstruktori hallitun instansoinnin varmistamiseksi
  private Service() {
    // toteutus
  }

  // Noutaa nykyisen instanssin Service:sta tai luo uuden, jos sitä ei ole
  public static Service getCurrent() {
    // toteutus
  }

  // Lataa asiakastiedot JSON-tiedostosta ja kartoittaa ne Customer-objekteiksi
  private List<Customer> buildDemoList() {
    // toteutus
  }

  // Getter...
}
```

## `Table`-komponentin luominen ja käyttäminen {#creating-and-using-a-table}

Nyt kun tarvittavat tiedot on oikeaoppisesti luotu `Customer`-luokan kautta ja ne voidaan palauttaa `Repository`:n kautta `Service`-luokasta, tämän vaiheen viimeinen tehtävä on integroida `Table`-komponentti sovellukseen asiakastietojen näyttämiseksi.

:::tip Lisätietoja `Table`:sta
Yksityiskohtaisemman yleiskuvan `Table`:n erilaisista ominaisuuksista ja käyttäytymisistä löydät [tästä artikkelista](../../components/table/overview).
:::

`Table` tarjoaa dynaamisen ja joustavan tavan näyttää jäsentynyttä dataa sovelluksessa. Se on suunniteltu toimimaan `Repository`-luokan kanssa, mahdollistaen ominaisuuksia kuten tietojen kyselyn, sivutuksen ja tehokkaat päivitykset. `Table` on erittäin konfiguroitavissa, jolloin voit määrittää sarakkeet, hallita sen ulkoasua ja sitoa sen tietovarastoihin vaivattomasti.

### `Table`:n toteuttaminen sovelluksessa {#implementing-the-table-in-the-app}

Koska `Table`:n tiedot käsitellään täysin `Service`-luokan kautta, päätehtävä `DemoApplication.java`:ssa on konfiguroida `Table` ja liittää se `Repository`:n, jonka `Service` tarjoaa.

`Table`:n konfiguroimiseksi:

- Aseta sen leveys ja korkeus asettelua varten `setHeight()` ja `setWidth()`-menetelmien avulla.
- Määritä sarakkeet, tarkentamalla niiden nimet ja menetelmät, joilla kukin tieto noudetaan.
- Määritä `Repository`, josta tiedot saadaan dynaamisesti.

Kun tämä on tehty, koodi näyttää seuraavilta:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Muut komponentit ensimmäisestä vaiheesta

  // Table-komponentti asiakastietojen näyttämistä varten
  Table<Customer> table = new Table<>(); 

  @Override
  public void run() throws WebforjException {
    // Aikaisempi toteutus ensimmäisestä vaiheesta
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Aseta taulukon korkeus 300 pikseliin
    table.setHeight("300px");
    // Aseta taulukon leveys 1000 pikseliin
    table.setWidth(1000);

    // Lisää erilaiset sarakeotsikot ja määritä asianmukaiset getterit
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // Sitouta Table Repositoryyn, joka sisältää asiakastiedot
    // Repository noudetaan Service-luokan kautta
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Kun sovelluksen muutokset on toteutettu, seuraavat vaiheet tapahtuvat sovelluksen käynnistyessä:

1. `Service`-luokka noutaa asiakastiedot JSON-tiedostosta ja tallentaa ne `Repository`:hin.
2. `Table` integroidaan `Repository`:hin datan näyttämiseksi ja täyttää rivinsä dynaamisesti.

Nyt kun `Table` näyttää asiakastietoja, seuraava askel keskittyy uuden näkymän luomiseen asiakastietojen muokkaamiseksi ja reitityksen integroimiseksi sovellukseen.

Tämä mahdollistaa sovelluksen logiikan organisoimisen tehokkaammin siirtämällä sen pois pääasiallisesta `App`-luokasta ja osiin, joihin pääsee käsiksi reittien kautta.
