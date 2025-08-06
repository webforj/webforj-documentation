---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: fdfd4b4255de20775bb12bcd863630f7
---
Tämä vaihe keskittyy reitityksen toteuttamiseen sovellusrakenteen skaalautuvuuden ja organisoinnin parantamiseksi. Tämän saavuttamiseksi sovellusta päivitetään käsittelemään useita näkymiä, mikä mahdollistaa navigoinnin eri toimintojen, kuten asiakastietojen muokkaamisen ja luomisen, välillä. Se kuvaa, miten nämä toiminnot voidaan toteuttaa näkymissä, käyttäen komponentteja kuten `Composite` rakentaakseen modulaarisia ja uudelleenkäytettäviä asetteluja.

Sovellus, joka luotiin [edellisessä vaiheessa](./working-with-data), saa reitityksen, joka tukee useita näkymiä, jolloin käyttäjät voivat hallita asiakastietoja tehokkaammin samalla kun säilytetään siisti ja skaalautuva koodipohja. Sovelluksen suorittamiseksi:

- Siirry hakemistoon `3-scaling-with-routing-and-composites`
- Suorita komento `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Reititys {#routing}

[Reititys](../../routing/overview) on mekanismi, joka mahdollistaa sovelluksesi hallita navigointia eri näkymien tai sivujen välillä. Sen sijaan, että kaikki logiikka ja käyttäytyminen pidetään yhdessä paikassa, reititys mahdollistaa sovelluksen jakamisen pienempiin, tarkoin määriteltyihin komponentteihin.

Perusperiaatteeltaan reititys yhdistää tietyt URL-osoitteet näkymiin tai komponentteihin, jotka käsittelevät näitä URL-osoitteita. Kun käyttäjä vuorovaikuttaa sovelluksesi kanssa—esimerkiksi napsauttamalla painiketta tai syöttämällä URL-osoitteen suoraan selaimeensa—reititin ratkaisee URL-osoitteen oikealle näkymälle, alustaa sen ja näyttää sen näytöllä. Tämä lähestymistapa tekee navigoinnin hallinnasta ja sovelluksen tilan ylläpidosta helppoa.

Tässä vaiheessa keskitytään `App`-luokan muuttamiseen, näkymä tiedostojen luomiseen ja reittien konfigurointiin sujuvan navigoinnin mahdollistamiseksi eri osien välillä sovellusta.

Sen sijaan, että kaikki logiikka sijoitetaan `App`:n `run()`-metodiin, näkymät kuten `DemoView` ja `FormView` toteutetaan erillisinä luokkina. Tämä lähestymistapa vastaa paremmin standardeja Java-käytäntöjä.

- **DemoView**: Vastaa taulukon näyttämisestä ja navigoinnista `FormView`:hen.
- **FormView**: Hallitsee asiakastietojen lisäämistä ja muokkaamista.

### `App`-luokan muuttaminen {#changing-the-app-class}

Ota reititys käyttöön sovelluksessasi päivittämällä `App`-luokkaa `@Routify`-annotaatiolla. Tämä kertoo webforJ:lle aktivoida reititys ja skannata määritellyt paketit reitti-ominaisuudella varustettujen näkymien löytämiseksi.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Määrittää, mitkä paketit skannataan reittejä määrittelevien näkymien löytämiseksi.
- **`debug`**: Ota käyttöön virheidenetsintätila, joka helpottaa kehittämisen aikana tapahtuvien ongelmien selvittämistä.

### Näkymien tiedostojen luominen ja reittien konfigurointi {#creating-files-for-the-views-and-configuring-routes}

Kun reititys on otettu käyttöön, luodaan erilliset Java-tiedostot jokaiselle näkymälle, joita sovellus sisältää, tässä tapauksessa `DemoView.java` ja `FormView.java`. Näille näkymille määritetään ainutlaatuiset reitit `@Route`-annotaation avulla. Tämä varmistaa, että jokainen näkymä on saavutettavissa tietyn URL-osoitteen kautta.

Kun tietyn luokan `@Route`-annotaatio, joka kuuluu joihinkin näistä liitettyistä suffikseista, ei sisällä arvoa, webforJ määrittää automaattisesti luokan nimen ilman suffiksia reitiksi. Esimerkiksi `DemoView` mapataan oletusarvoisesti reitille `/demo`. Koska tässä tapauksessa `DemoView` on tarkoitettu oletusreittinä, sille määritetään reitti.

Reitti `/` toimii oletus sisäänkäyntinä sovellukseesi. Määrittäminen tälle reitille näkymälle varmistaa, että se on ensimmäinen sivu, jonka käyttäjät näkevät päästessään sovellukseen. Useimmissa tapauksissa ohjauspaneeli tai yhteenvedonäkymä liitetään reittiin `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView logiikka
}
```

:::info 
Lisätietoa erilaisista reittymintyyppien saatavilla [tästä](../../routing/defining-routes).
:::

`FormView`:lle reitti `customer/:id?` käyttää valinnaista parametria `id`, joka määrittää `FormView`:n tilan.

- **Lisäysnäyttö**: Kun `id`:tä ei anneta, `FormView` alustaa tyhjällä lomakkeella uusien asiakastietojen lisäämiseksi.
- **Muokkaustila**: Kun `id` on annettu, `FormView` hakee vastaavan asiakkaan tiedot käyttäen `Service`: tä ja esitäyttää lomakkeen, mahdollistaen muokkaukset olemassa olevaan merkintään.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Asiakastiedot")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView logiikka
}
```

:::info 
Lisätietoa siitä, kuinka toteuttaa näitä reittimallia, on saatavilla [täältä](../../routing/route-patterns).
:::

## `Composite`-komponenttien käyttö sivujen näyttämiseen {#using-composite-components-to-display-pages}

WebforJ:ssä Composite-komponentit, kuten `Composite<Div>`, mahdollistavat käyttöliittymälogiikan ja rakenteen kapseloinnin uudelleenkäytettävään säilöön. Laajentamalla `Composite`:a rajoitat metodeja ja tietoja, jotka ovat muiden sovelluksen osien saatavilla, mikä varmistaa siistimmän koodin ja paremman kapseloinnin.

Esimerkiksi `DemoView` laajentaa `Composite<Div>` suoraan sen sijaan, että laajentaisi `Div`:a:

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

Reitityksen konfiguroinnin ja näkymien asettamisen jälkeen, yhdistä näkymät ja tiedot käyttämällä tapahtumakuuntelijoita ja palvelimen metodeja. Ensimmäinen vaihe on lisätä yksi tai useampi käyttöliittymä-elementti navigoimaan yhdestä näkymästä toiseen.

### Painikennavigointi {#button-navigation}

`Button`-komponentti laukaisee navigaatiotapahtuman siirtymään yhdestä näkymästä toiseen `Router`-luokan avulla. Esimerkiksi:

```java title="DemoView.java"
private Button add = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
Router-luokka käyttää annettua luokkaa reitin ratkaisemiseen ja URL-osoitteen rakentamiseen, johon navigoidaan. Kaikki selaimen navigointi hoidetaan siten, että historiaseuranta ja näkymän alustaminen eivät ole huolenaihe.
Lisätietoja navigoinnista, katso [Route Navigation Artikkeli](../../routing/route-navigation).
:::

### Taulukon muokkaaminen {#table-editing}

Lisäksi navigoinnin lisäksi painikkeella monet sovellukset mahdollistavat navigoinnin muihin osiin sovellusta, kun `Table`:a kaksoisnapsautetaan. Seuraavat muutokset tehdään, jotta käyttäjät voivat kaksoisnapsauttaa taulukon kohdetta navigoidakseen lomakkeeseen, joka on esitäytetty kohteen tiedoilla.

Kun tiedot on muokattu oikeassa näkymässä, muutokset tallennetaan, ja `Table`:a päivitetään näyttämään valitun kohteen muuttuneet tiedot.

Helpottaakseen tätä navigointia, taulukon kohteiden napsautuksia käsitellään `TableItemClickEvent<Customer>`-kuuntelijalla. Tapahtuma sisältää napsautetun asiakkaan `id`:n, jonka se siirtää `FormView`:lle `navigate()`-metodin avulla `ParametersBag`-rakennetta käyttäen:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Alustamisen käsittely `onDidEnter`-metodilla {#handling-initialization-with-ondidenter}

`onDidEnter`-metodi webforJ:ssä on osa reitityksen elinkaarta ja se laukaistaan, kun näkymä tulee aktiiviseksi.

Kun `Router` navigoi näkymään, `onDidEnter` laukaistaan osana elinkaaren tapahtumaa:
- **Datan lataaminen**: Alustaa tai hakee näkymän tarvitsemat tiedot reitti-parametrin perusteella.
- **Näkymän asettaminen**: Päivittää käyttöliittymäelementtejä dynaamisesti kontekstin mukaan.
- **Reagoiminen tilamuutoksiin**: Suorittaa toimia, jotka riippuvat näkymän aktiivisuudesta, kuten lomakkeiden nollaaminen tai komponenttien korostaminen.

`onDidEnter`-metodi `FormView`:ssä tarkistaa, onko reitissä `id`-parametria saatavilla ja säätää lomakkeen käyttäytymistä sen mukaan:

- **Muokkaustila**: Jos `id` on annettu, metodi hakee vastaavan asiakkaan tiedot käyttäen `Service`: tä ja esitäyttää lomakkeen kentät. `Lähetä`-painike konfiguroidaan päivittämään olemassa oleva merkintä.
- **Lisäystila**: Jos `id`:tä ei ole olemassa, lomake pysyy tyhjänä, ja `Lähetä`-painike konfiguroidaan luomaan uusi asiakas.

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

### Datan lähettäminen {#submitting-data}

Kun tiedot on muokattu, on tarpeen lähettää ne palvelimelle, joka käsittelee tietovarastoa. Siksi `Service`-luokkaa, joka on jo määritetty aiemmassa tämän oppaan vaiheessa, on nyt parannettava lisämetodeilla, jotka mahdollistavat asiakkaiden lisäämisen ja muokkaamisen.

Alla oleva pätkä näyttää, kuinka tämä saavutetaan:

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### `commit()`-käyttö {#using-commit}

`commit()`-metodi `Repository`-luokassa pitää sovelluksen tiedot ja käyttöliittymän synkronoituna. Se tarjoaa mekanismin päivittää tiedot, jotka on tallennettu `Repository`:ssä, varmistaen, että sovelluksessa näkyy viimeisin tila.

Tätä metodia voidaan käyttää kahdella tavalla:

1. **Kaikkien tietojen päivittäminen:**
  `commit()`-kutsuminen ilman argumentteja lataa kaikki entiteetit repositoryn taustatietolähteestä, kuten tietokannasta tai palvelinluokasta.

2. **Yksittäisen entiteetin päivittäminen:**
  `commit(T entity)`-kutsuminen lataa tietyn entiteetin, varmistaen, että sen tila vastaa viimeisimpiä tietolähteen muutoksia.

Kutsu `commit()`-metodia, kun `Repository`:n tiedot muuttuvat, kuten entiteettien lisäämisen tai muokkaamisen jälkeen tietolähteessä.

```java
// Päivitä kaikki asiakastiedot repositoryssä
customerRepository.commit();

// Päivitä yksittäinen asiakasentiteetti
Customer updatedCustomer = ...; // Päivitetty ulkoisesta lähteestä
customerRepository.commit(updatedCustomer);
```

Näiden muutosten myötä on saavutettu seuraavat tavoitteet:

1. Toteutettu reititys ja asetettu se siten, että tulevat näkymät voidaan integroida helposti.
2. Poistettu käyttöliittymän toteutukset `App`:sta ja siirretty ne erilliseen näkymään.
3. Lisätty ylimääräinen näkymä hallitsemaan tietoa, joka näytetään asiakastaulukossa.

Asiakastietojen muokkaamisen ja reitityksen toteutuksen myötä seuraava vaihe keskittyy tietojen sitomiseen ja sen avulla validoinnin helpottamiseen.
