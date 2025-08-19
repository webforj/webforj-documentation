---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
_i18n_hash: 11d03e09c4c37172713713649c920e9e
---
Data binding on mekanismi, joka yhdistää sovelluksesi käyttöliittymän komponentit suoraan taustalla olevaan datamalliin, mikä mahdollistaa arvojen automaattisen synkronoinnin niiden välillä. Tämä poistanee tarpeen toistuville getter- ja setter-kutsuille, vähentäen kehitysaikaa ja parantaen koodin luotettavuutta.

Validointi tässä kontekstissa varmistaa, että lomakkeeseen syötetty data noudattaa ennalta määrättyjä sääntöjä, kuten olemista tyhjättynä tai tietyn muodon mukaista. Yhdistämällä data bindingin validointiin voit virtaviivaistaa käyttäjäkokemusta samalla kun ylläpidät datan eheyttä ilman laajojen manuaalisten tarkistusten kirjoittamista.

Lisätietoja data bindingista löytyy [tästä artikkelista.](../../data-binding/overview) Suorittaaksesi sovelluksen:

- Siirry hakemistoon `4-validating-and-binding-data`
- Suorita komento `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Kenttien liittäminen {#binding-the-fields}

Data binding -asetukset alkavat `BindingContext`-alkuperäisen mallin `Customer` alustamisesta. `BindingContext` yhdistää mallin ominaisuudet lomakekenttiin, mahdollistaen automaattisen datan synkronoinnin. Tämä asetus tehdään `FormView`-konstruktorissa.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` alustaa liittämiskontekstin `Customer`-luokalle. Kolmas parametri, `true`, mahdollistaa [jakarta validoinnin](https://beanvalidation.org/).

:::info
Tämä toteutus käyttää automaattista liittämistä, kuten on kuvattu [Data Binding Artikkelissa](../../data-binding/automatic-binding). Tämä toimii, jos datamallin `Customer` kentät on nimetty samoin kuin vastaavat kentät `FormView`:ssä.

Jos kentät eivät ole nimetty samoin, voit lisätä `UseProperty`-annotaation lomakkeeseen kentän päälle, jonka haluat liittää, jotta tiedetään, mihin datakenttiin viitataan.
:::

### Data binding `onDidEnter()`-menetelmällä {#data-binding-with-ondidenter}

`onDidEnter`-metodi hyödyntää data binding -asetuksia helpottaakseen lomakekenttien täyttämisprosessia. Sen sijaan, että arvoja asetettaisiin manuaalisesti jokaiselle kentälle, data synkronoi nyt automaattisesti `BindingContextin` kanssa.

```java {7}
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
```

`context.read`-metodi webforJ:n data binding -järjestelmässä synkronoi käyttöliittymäkomponentin kentät datamallin arvojen kanssa. Sitä käytetään tässä tapauksessa täyttämään lomakekenttiä olemassa olevasta mallista, varmistaen, että käyttöliittymä heijastaa datan nykyistä tilaa.

## Datan validointi {#validating-data}

Validointi varmistaa, että lomakkeeseen syötetty data noudattaa määriteltyjä sääntöjä, parantaen datan laatua ja estäen virheelliset lähetykset. Data bindingin myötä validointia ei enää tarvitse toteuttaa manuaalisesti, vaan se voidaan yksinkertaisesti konfiguroida, jolloin käyttäjän syötteistä saadaan reaaliaikaista palautetta.

### Validointisääntöjen määrittäminen {#defining-validation-rules}

Käyttämällä [Jakartaa](https://beanvalidation.org) ja säännöllisiä lausekkeita voit pakottaa lukuisia sääntöjä kentälle. Usein käytettyjä esimerkkejä ovat sen varmistaminen, että kenttä ei ole tyhjänä tai null, tai noudattaa tiettyä kaavaa.
Annotaatioiden avulla asiakaskin luokassa voit antaa jakarta validointiparametreja kentälle.

:::info
Lisätietoja validoinnin asetuksesta on saatavilla [täältä](../../data-binding/validation/jakarta-validation.md#installation).
:::

```java
  @NotEmpty(message = "Nimi ei voi olla tyhjö")
  @Pattern(regexp = "[a-zA-Z]*", message = "Virheelliset merkit")
  private String firstName = "";
```

`onValidate`-metodi lisätään ohjaamaan `Submit`-painikkeen tilaa lomakekenttien voimassaolon perusteella. Tämä varmistaa, että vain voimassa oleva data voidaan lähettää.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` palauttaa true, jos kaikki kentät ovat voimassa, ja false, jos eivät. Tämä tarkoittaa, että `Submit`-painike on käytössä niin kauan kuin kaikki kentät ovat voimassa. Muussa tapauksessa se pysyy pois päältä, estäen lähetyksen ennen korjausten tekemistä.

### Kannan lisääminen ja muokkaaminen validoinnin kanssa {#adding-and-editing-entries-with-validation}

`submitCustomer()`-metodi validoi nyt dataa käyttämällä `BindingContext`-kontekstia ennen lisäys- tai muokkaustoimintoja. Tämä lähestymistapa poistaa tarpeen manuaalisille validointitarkistuksille, hyödyntäen kontekstin sisäänrakennettuja mekanismeja varmistaakseen, että vain voimassa oleva data käsitellään.

- **Lisäystila**: Jos `id`:tä ei ole annettu, lomake on lisäämistilassa. Vahvistettu data kirjoitetaan `Customer`-malliin ja lisätään varastoon `Service.getCurrent().addCustomer(customer)`-kutsun kautta.
- **Muokkaustila**: Jos `id` on läsnä, metodi noutaa vastaavat asiakastiedot, päivittää ne vahvistetuilla syötteillä ja tallentaa muutokset varastoon.

Kutsumalla `context.write(customer)` palautuu `ValidationResult`:in instanssi. Tämä luokka ilmoittaa, oliko validointi onnistunutta vai ei, ja tallentaa kaikki tähän tulokseen liittyvät viestit.

Tämä koodi varmistaa, että kaikki muutokset validoidaan ja sovelletaan automaattisesti malliin ennen uuden lisäämistä tai olemassa olevan `Customer`-muokkaamista.

```java title="FormView.java"
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerId.isEmpty()) {
      Service.getCurrent().addCustomer(customer);
    }
    Router.getCurrent().navigate(DemoView.class);
  }
}
```

Suorittamalla tämän vaiheen sovellus tukee nyt data bindingia ja validointia, varmistaen, että lomake syötteet synkronoidaan mallin kanssa ja noudattavat ennalta määrättyjä sääntöjä.
