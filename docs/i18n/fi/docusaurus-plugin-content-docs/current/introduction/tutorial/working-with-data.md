---
title: Working With Data
sidebar_position: 3
_i18n_hash: 42dff7cecf07f976ccbe007e04e78a22
---
Tämä vaihe keskittyy tiedonhallinta- ja näyttökäskyjen lisäämiseen demon sovellukseen. Tätä varten luodaan testidataa eri `Customer`-objekteista, ja sovellusta päivitetään käsittelemään näitä tietoja ja esittämään niitä aiemmin lisättyyn [`Table`](../../components/table/overview) komponenttiin.

Tässä vaiheessa käsitellään `Customer`-malli-luokan luomista ja sen integroimista `Service`-luokkaan, jolla pääsee käsiksi ja hallitsemaan tarvittavaa dataa käyttäen varaston toteutusta. Lisäksi tarkennetaan, miten haettua dataa käytetään `Table`-komponentin toteuttamiseen sovelluksessa, esittäen asiakastietoja interaktiivisessa ja jäsennellyssä muodossa.

Tämän vaiheen lopussa aiemmin luotu sovellus [edellisessä vaiheessa](./creating-a-basic-app) esittää taulukon luodusta datasta, jota voidaan laajentaa seuraavissa vaiheissa. Sovelluksen käynnistämiseksi:

- Siirry `2-working-with-data` -hakemistoon
- Suorita `mvn jetty:run`

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

## Datan mallin luominen {#creating-a-data-model}

Jotta voidaan luoda `Table`, joka esittää dataa pääsovelluksessa, on luotava Java bean -luokka, jota voidaan käyttää `Table`-komponentin kanssa datan esittämiseen.

Tässä ohjelmassa `Customer`-luokka tiedostossa `src/main/java/com/webforj/demos/data/Customer.java` tekee tämän. Tämä luokka toimii sovelluksen ydintietomallina, kapseloiden asiakastietoihin liittyvät attribuutit, kuten `firstName`, `lastName`, `company` ja `country`. Tämä malli sisältää myös ainutlaatuisen tunnisteen.

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

    // Jäännösmaat
  }

    // Getterit ja setterit

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info `HasEntityKey` ainutlaatuisten tunnisteiden hallintaan

`HasEntityKey`-rajapinnan toteuttaminen on keskeistä mallien ainutlaatuisten tunnisteiden hallinnassa, joita käytetään `Table`-komponentin kanssa. Se varmistaa, että jokaisella mallin instanssilla on ainutlaatuinen avain, mikä mahdollistaa `Table`-komponentin tunnistavan ja hallitsevan rivejä tehokkaasti.

Tässä demon esimerkissä `getEntityKey()`-metodi palauttaa UUID:n jokaiselle asiakkaalle, varmistaen ainutlaatuisen tunnistamisen. Vaikka UUID:itä käytetäänkin tässä yksinkertaisuuden vuoksi, oikeissa sovelluksissa tietokannan pääavain on usein parempi vaihtoehto ainutlaatuisten avainten luomiseen.

Jos `HasEntityKey` ei ole toteutettu, `Table` käyttää oletuksena Java-hash-koodia avaimena. Koska hash-koodit eivät takaa ainutlaatuisuutta, tämä voi aiheuttaa ristiriitoja hallittaessa rivejä `Table`-komponentissa.
:::

Kun `Customer`-datamalli on paikoillaan, seuraava vaihe on hallita ja järjestää näitä malleja sovelluksessa.

## `Service`-luokan luominen {#creating-a-service-class}

Keskitettynä datanhallintana `Service`-luokka ei vain lataa `Customer`-dataa, vaan myös tarjoaa tehokkaan käyttöliittymän sen saamiseksi ja käsittelemiseksi.

`Service.java`-luokka luodaan tiedostoon `src/main/java/com/webforj/demos/data`. Sen sijaan, että dataa kuljetettaisiin manuaalisesti komponenttien tai luokkien välillä, `Service` toimii jaettuna resurssina, mikä mahdollistaa asianomaisten tahojen hankkia ja käsitellä dataa helposti.

Tässä demon esimerkissä `Service`-luokka lukee asiakastietoja JSON-tiedostosta, joka sijaitsee kohdassa `src/main/resources/data/customers.json`. Data kartutetaan `Customer`-objekteille ja tallennetaan `ArrayList`-tietorakenteeseen, joka muodostaa perustan taulukon `Repository`-komponentille.

webforJ:ssa `Repository`-luokka tarjoaa jäsennellyn tavan hallita ja noutaa kokoelmia entiteeteistä. Se toimii rajapintana sovelluksesi ja sen datan välillä, tarjoten menetelmiä kyselyjen suorittamiseen, laskemiseen ja datan päivittämiseen samalla kun se ylläpitää siistiä ja johdonmukaista rakennetta. Sitä käyttää `Table`-luokka esittääkseen siellä tallennettua dataa.

Vaikka `Repository` ei sisällä menetelmiä entiteettien päivittämiseen tai poistamiseen, se toimii jäsenneltynä kääreenä kokoelmaobjekteista. Tämä tekee siitä ihanteellisen tarjoamaan järjestettyä ja tehokasta datan saatavuutta.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // Jäljelle jäävä toteutus
}
```

`Repository`-komponentin täyttämiseksi dataa `Service`-luokka toimii keskitettynä hallintona, joka huolehtii varastojen lataamisesta ja järjestämisestä sovelluksessa. Asiakastiedot luetaan JSON-tiedostosta ja kartoitetaan `Customer`-objekteiksi `Repository`:ssä.

`Assets`-apuväline webforJ:ssa helpottaa tämän datan dynaamista lataamista käyttämällä kontekstiosoitteita. Datan ja resurssien lataamiseksi webforJ:ssa `Service`-luokka käyttää kontekstiosoitteita yhdessä `Assets`-apuvälineen kanssa. Esimerkiksi asiakastiedot voidaan ladata JSON-tiedostosta seuraavasti:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip `ObjectTable`-komponentin käyttäminen
`Service`-luokka käyttää `ObjectTable`-komponenttia hallitsemaan instansseja dynaamisesti sen sijaan, että luottaisi staattisiin kenttiin. Tämä lähestymistapa ratkaisee keskeisen rajoituksen servlet-toteutuksissa: staattiset kentät liittyvät palvelimen elinkaareen ja voivat aiheuttaa ongelmia ympäristöissä, joissa on useita pyyntöjä tai samanaikaisia istuntoja. `ObjectTable` on rajattu käyttäjän istuntoon, ja sen käyttäminen varmistaa singleton-tyyppisen toiminnan ilman näitä rajoituksia, mahdollistaen johdonmukaisen ja skaalautuvan datan hallinnan.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Yksityinen konstruktori hallitun instansoinnin varmistamiseksi
  private Service() {
    // toteutus
  }

  // Palauttaa nykyisen Service-instanssin tai luo uuden, jos sitä ei ole olemassa
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

## `Table`-komponentin luominen ja käyttö {#creating-and-using-a-table}

Nyt kun tarvittava data on luotu kunnolla `Customer`-luokan avulla ja se voidaan palauttaa `Repository`-tietorakenteena `Service`-luokasta, viimeinen tehtävä tässä vaiheessa on integroida `Table`-komponentti sovellukseen asiakastietojen näyttämiseksi.

:::tip Lisätietoa `Table`-komponentista
Yksityiskohtaisemman yleiskatsauksen `Table`-komponentin eri ominaisuuksista ja käytöksistä katso [tämä artikkeli](../../components/table/overview).
:::

`Table` tarjoaa dynaamisen ja joustavan tavan näyttä datan sovelluksessa. Se on suunniteltu integroitavaksi `Repository`-luokan kanssa, mahdollistaen ominaisuudet kuten datan kysely, sivuuttaminen ja tehokkaat päivitykset. `Table` on erittäin konfiguroitava, mikä mahdollistaa sarakkeiden määrittämisen, sen ulkoasun hallinnan ja datavarastojen sitomisen vaivattomasti.

### `Table`-komponentin toteuttaminen sovelluksessa {#implementing-the-table-in-the-app}

Koska `Table`-komponentin dataa käsitellään täysin `Service`-luokan kautta, päätehtävä `DemoApplication.java`-tiedostossa on konfiguroida `Table` ja liittää se `Repository`:n kanssa, jonka `Service` tarjoaa.

`Table`-komponentin konfiguroimiseksi:

- Aseta sen leveys ja korkeus asettelutarkoituksiin käyttäen `setHeight()` ja `setWidth()` menetelmiä.
- Määritä sarakkeet, jolloin kerrotaan niiden nimet ja metodit, joista kukin data haetaan.
- Määritä `Repository`, joka tarjoaa dynaamista dataa.

Tämän jälkeen koodi näyttää seuraavanlaiselta:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Muita komponentteja ensimmäisestä vaiheesta

  // Taulukkoasiakastietojen esittämiseksi
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // Edellisen vaiheen toteutus
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Aseta taulukon korkeus 300 pikseliksi
    table.setHeight("300px");
    // Aseta taulukon leveys 1000 pikseliksi
    table.setWidth(1000);

    // Lisää erilaisia sarakeotsikoita ja määritä vastaavat getterit
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // Sidotaan Table `Repository`:n avulla, joka sisältää asiakastiedot
    // Repository haetaan Service-luokan kautta
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Kun sovellukseen on tehty tarvittavat muutokset, seuraavat vaiheet tapahtuvat, kun sovellus suoritetaan:

1. `Service`-luokka noutaa `Customer`-datan JSON-tiedostosta ja tallentaa sen `Repository`:hen.
2. `Table` integroi `Repository`:n datan ja täyttää rivinsä dynaamisesti.

Nyt kun `Table` esittää `Customer`-dataa, seuraava vaihe tulee keskittymään uuden näkymän luomiseen asiakastietojen muokkaamista varten ja reitityksen integroimiseen sovellukseen.

Tämä mahdollistaa sovelluksen logiikan järjestämisen tehokkaammin siirtämällä sen pää `App`-luokasta ja reitittämään käsittelee sitä eri näytöissä.
