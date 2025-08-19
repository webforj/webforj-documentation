---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: 50cd3b00cb1fb7731b6328708d6d45ba
---
Tämä vaihe keskittyy reitittämisen toteuttamiseen, jotta sovelluksen rakennetta voidaan parantaa skaalautuvuuden ja organisoinnin osalta. Tämän saavuttamiseksi sovellusta päivitetään käsittelemään useita näkymiä, mikä mahdollistaa navigoinnin eri toiminnallisuuksien välillä, kuten asiakastietojen muokkaamisessa ja luomisessa. Siinä kerrotaan, kuinka luodaan näkymiä näille toiminnallisuuksille, käyttäen komponentteja kuten `Composite` rakennemoduulien ja uudelleenkäytettävien ulkoasujen luomiseksi.

Sovellukseen, joka luotiin [edellisessä vaiheessa](./working-with-data), lisätään reititysrakenne, joka tukee useita näkymiä, mahdollistaen käyttäjien hallita asiakasdataa tehokkaammin, samalla kun säilytetään siisti ja skaalautuva koodipohja. Sovelluksen suorittamiseksi:

- Siirry kansioon `3-scaling-with-routing-and-composites`
- Suorita komento `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Reititys {#routing}

[Reititys](../../routing/overview) on mekanismi, joka mahdollistaa sovelluksesi hallita navigointia eri näkymien tai sivujen välillä. Sen sijaan, että kaikki logiikka ja käyttäytyminen pidetään yhdessä paikassa, reititys antaa sinun jakaa sovelluksesi pienempiin, keskittyneisiin komponentteihin.

Perusasioissa reititys yhdistää tietyt URL-osoitteet niihin näkymiin tai komponentteihin, jotka käsittelevät näitä URL-osoitteita. Kun käyttäjä vuorovaikuttaa sovelluksesi kanssa – kuten napsauttamalla painiketta tai syöttämällä URL-osoitteen suoraan selaimeensa – reititin ratkaisee URL-osoitteen sopivaksi näkymäksi, alustaa sen ja näyttää sen näytöllä. Tämä lähestymistapa helpottaa navigoinnin hallintaa ja sovelluksen tilan ylläpitämistä.

Tässä vaiheessa keskitytään `App`-luokan muuttamiseen, näkymille tiedostojen luomiseen sekä reittien määrittämiseen, jotta saadaan sujuva navigointi sovelluksen eri osien välillä.

Sen sijaan, että kaikki logiikka sijoitettaisiin `App`-luokan `run()`-metodiin, näkymiä kuten `DemoView` ja `FormView` toteutetaan erillisinä luokkina. Tämä lähestymistapa on paremmin linjassa standardien Java-käytäntöjen kanssa.

- **DemoView**: Hakee taulukon ja navigoi `FormView`:hin.
- **FormView**: Hallitsee asiakasdatan lisäämistä ja muokkaamista.

### `App`-luokan muuttaminen {#changing-the-app-class}

Reitittämisen mahdollistamiseksi päivitä `App`-luokka `@Routify`-annotaatiolla. Tämä kertoo webforJ:lle, että reititys aktivoidaan ja määritetyt paketit skannataan reititykselle soveltuvia näkymiä varten.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Määrää, mitkä paketit skannataan reittejä määrittävien näkymien löytämiseksi.
- **`debug`**: Aktivoi virheenkorjaustilan helpottamaan kehitysvaiheen vianmääritystä.

### Näkymille tiedostojen luominen ja reittien määrittäminen {#creating-files-for-the-views-and-configuring-routes}

Kun reititys on otettu käyttöön, luodaan erilliset Java-tiedostot jokaiselle näkymälle, joita sovelluksessa on, tässä tapauksessa `DemoView.java` ja `FormView.java`. Nämä näkymät saavat uniikit reitit `@Route`-annotaation avulla. Tämä varmistaa, että kuhunkin näkymään pääsee käsiksi tietyllä URL-osoitteella.

Kun `@Route`-annotaatio on liitetty luokkaan, jolla on jokin näistä suffikseista ilman arvoa, webforJ määrittää automaattisesti luokan nimen ilman suffiksia reitiksi. Esimerkiksi, `DemoView` tanssii reittiin `/demo` oletuksena. Koska tässä tapauksessa `DemoView` on tarkoitus olla oletusreitittä, sille annetaan reitti.

Reitti `/` toimii oletuksena sovelluksesi sisäänkäyntipisteenä. Tämän reitin liittäminen näkymään varmistaa, että se on ensimmäinen sivu, jonka käyttäjät näkevät kirjauduttaessa sovellukseen. Usein nyt käytetään kojelautanäkymää tai yhteenvetonäkymää reitille `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView logiikka
}
```

:::info 
Lisätietoja eri reittityypeistä on saatavilla [täällä](../../routing/defining-routes).
:::

`FormView`:lle reitti `customer/:id?` käyttää valinnaista parametria `id` määrittämään `FormView`:n tilan.

- **Lisäystila**: Kun `id`:tä ei ole annettu, `FormView` alustaa tyhjällä lomakkeella uusien asiakasdatan lisäämistä varten.
- **Muokkaustila**: Kun `id` on annettu, `FormView` hakee vastaavan asiakkaan tiedot käyttäen `Service` ja esitäyttää lomakkeen, jolloin olemassa olevaa merkintää voidaan muokata.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Asiakaslomake")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView logiikka
}
```

:::info 
Lisätietoja siitä, miten toteuttaa näitä reittimalleja on saatavilla [täällä](../../routing/route-patterns).
:::

## Käyttäen `Composite`-komponentteja sivujen näyttämiseen {#using-composite-components-to-display-pages}

Composite-komponentit webforJ:ssä, kuten `Composite<Div>`, mahdollistavat UI-logiikan ja rakenteen kapseloinnin uudelleenkäytettävään säiliöön. Laajentamalla `Composite`:a rajoitat metodeja ja dataa, joita muu sovellus voi käyttää, mikä varmistaa siistimmän koodin ja paremman kapseloinnin.

Esimerkiksi, `DemoView` laajentaa `Composite<Div>`:a sen sijaan, että se laajentaisi suoraan `Div`:a:

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Lisää asiakas", ButtonTheme.PRIMARY);  

  public DemoView() {
    setupLayout();
  }

  private void setupLayout() {
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    getBoundComponent().add(layout);
  }
}
```

## Reittien yhdistäminen {#connecting-the-routes}

Konfiguroitujen reittien ja näkemien asetusten jälkeen yhdistä näkymät ja tiedot tapahtumakuuntelijoiden ja palvelumetodien avulla. Ensimmäinen askel on lisätä yksi tai useampi
UI-elementti, joka navigoi yhdestä näkymästä toiseen.

### Painikennavigointi {#button-navigation}

`Button`-komponentti laukaisee navigointitapahtuman siirtyäkseen yhdestä näkymästä toiseen käyttäen `Router`-luokkaa. Esimerkiksi:

```java title="DemoView.java"
private Button add = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
Router-luokka käyttää annettua luokkaa reitin ratkaisemiseksi ja URL:n rakentamiseksi navigoimiseksi. Kaikki selaimen navigointi käsitellään näin ollen historiahallinta
ja näkymän alustaminen eivät ole huolenaiheita.
Lisätietoja navigoinnista, katso [Reitityksen navigointi -artikkeli](../../routing/route-navigation).
:::

### Taulukon muokkaaminen {#table-editing}

Lisäksi navigoinnin ohella painiketta napsauttamalla monet sovellukset sallivat myös navigoinnin sovelluksen muihin osiin, kun `Table`-komponenttia tuplaklikataan. Seuraavat muutokset tehdään, jotta käyttäjät voivat tuplaklikata taulukossa olevaa kohdetta siirtyäkseen lomakkeeseen, joka on esitäytetty kohteen tiedoilla.

Kun tiedot on muokattu oikealla näytöllä, muutokset tallennetaan ja `Table` päivitetään näyttämään valitun kohteen muuttuneet tiedot.

Helpottaakseen tätä navigointia, kohteen napsautukset taulukossa käsitellään `TableItemClickEvent<Customer>`-kuuntelijalla. Tapahtuma sisältää napsautetun asiakkaan `id`:n, jonka se välittää `FormView`:lle hyödyntämällä `navigate()`-metodia `ParametersBag`:in avulla:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Alustamisen käsittely `onDidEnter`-metodilla {#handling-initialization-with-ondidenter}

`onDidEnter`-metodi webforJ:ssä on osa reitityksen elinkaarta ja se laukaisee, kun näkymä aktivoituu.

Kun `Router` navigoi näkymään, `onDidEnter` laukaisee osana elinkaarta seuraavat toiminnot:
- **Tietojen lataaminen**: Alustaa tai hakee näkymässä tarvittavat tiedot reittiparametrien perusteella.
- **Näkymän asetusten määrittäminen**: Päivittää UI-elementtejä dynaamisesti kontekstin mukaan.
- **Reagointi tilamuutoksiin**: Suorittaa toimenpiteitä, jotka riippuvat näkymän aktiivisuudesta, kuten lomakkeiden nollaaminen tai komponenttien korostaminen.

`onDidEnter`-metodi `FormView` muuttujassa tarkistaa, onko reitissä `id`-parametri ja säätää lomakkeen toimintaa sen mukaan:

- **Muokkaustila**: Jos `id` on annettu, metodi hakee vastaavan asiakkaan tiedot käyttäen `Service` ja esitäyttää lomakkeen kentät. `Lähetä`-painike on määritetty päivittämään olemassa olevaa merkintää.
- **Lisäystila**: Jos `id`:tä ei ole, lomake pysyy tyhjänä ja `Lähetä`-painike on määritetty luomaan uusi asiakas.

```java
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }
```


### Tietojen lähettäminen {#submitting-data}

Kun tiedot on muokattu, on tarpeen lähettää ne palvelimelle, joka käsittelee tietovarastoa. Siksi `Service`-luokkaa, joka on jo asetettu aiemmassa vaiheessa, on nyt parannettava lisämetodeilla, jotka mahdollistavat käyttäjien lisätä ja muokata asiakkaita.

Alla oleva koodinpätkä näyttää, kuinka tämä toteutetaan:

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### `commit()`-metodin käyttäminen {#using-commit}

`commit()`-metodi `Repository`-luokassa pitää sovelluksen tiedot ja käyttöliittymän synkronoituna. Se tarjoaa mekanismin päivittää tietoja, jotka on tallennettu `Repository`:hin, varmistaen, että viimeisin tila heijastuu sovelluksessa.

Tätä metodia voidaan käyttää kahdella tavalla:

1) **Kaikkien tietojen päivittäminen**:
  `commit()`-kutsuminen ilman argumentteja lataa kaikki entiteetit varaston taustadata lähteestä, kuten tietokannasta tai palveluluokasta.

2) **Yksittäisen entiteetin päivittäminen**:
  `commit(T entity)`-kutsuminen lataa tietyn entiteetin, varmistaen, että sen tila vastaa viimeisimpiä tietolähteen muutoksia.

Kutsu `commit()`, kun tiedot `Repository`-luokassa muuttuvat, kuten lisäämisen tai muokkaamisen jälkeen.

```java
// Päivitä kaikki asiakastiedot varastossa
customerRepository.commit();

// Päivitä yksittäinen asiakasentiteetti
Customer updatedCustomer = ...; // Päivitetty ulkoisesta lähteestä
customerRepository.commit(updatedCustomer);

```

Näiden muutosten myötä on saavutettu seuraavat tavoitteet:

1. Reititys on toteutettu ja konfiguroitu niin, että tulevat näkymät voidaan integroida vaivattomasti.
2. Käyttöliittymäimplementointeja on poistettu `App`:sta ja siirretty erillisiin näkymiin.
3. On lisätty uusi näkymä asiakastietojen manipulointiin, joka näkyy asiakastaulukossa.

Asiakastietojen muokkaaminen ja reititys on nyt toteutettu, ja seuraava vaihe keskittyy tietosidonnan toteuttamiseen ja sen käyttämiseen validoinnin helpottamiseksi.
