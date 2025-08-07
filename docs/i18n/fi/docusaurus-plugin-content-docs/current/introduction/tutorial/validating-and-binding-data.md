---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
_i18n_hash: 3efedcc32a2111ba6ce08c1a3ee6b477
---
Tietosidonta on mekanismi, joka yhdistää sovelluksesi käyttöliittymäkomponentit suoraan taustalla olevaan datamalliin, mahdollistaen arvojen automaattisen synkronoinnin niiden välillä. Tämä poistaa tarpeen toistuville getter- ja setter-kutsuille, mikä vähentää kehitysaikaa ja parantaa koodin luotettavuutta.

Validointi tässä yhteydessä varmistaa, että lomakkeeseen syötetyt tiedot noudattavat ennalta määriteltyjä sääntöjä, kuten sen, ettei se ole tyhjää tai noudattaa tiettyä muotoa. Yhdistämällä tietosidonnan ja validoinnin voit virtaviivaistaa käyttäjäkokemusta samalla säilyttäen datan eheyden ilman laajamittaisia manuaalisia tarkistuksia.

Lisätietoja tietosidonnasta saat [tästä artikkelista](../../data-binding/overview). Sovelluksen suorittamiseksi:

- Siirry hakemistoon `4-validating-and-binding-data`
- Suorita komento `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Kenttien sitominen {#binding-the-fields}

Tietosidontakäytännön asetukset alkavat `BindingContext`-alkioiden alustamisella `Customer`-mallille. `BindingContext` linkittää mallin ominaisuudet lomakekenttien kanssa, mahdollistaen automaattisen datan synkronoinnin. Tämä on asetettu `FormView`-konstruktorissa.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` alustaa sidontakontekstin `Customer`-luokalle. Kolmas parametri, `true`, mahdollistaa [jakarta validoinnin](https://beanvalidation.org/).

:::info
Tämä toteutus käyttää automaattista sidontaa, kuten on kuvattu [Tietosidonta-artikkelissa](../../data-binding/automatic-binding). Tämä toimii, jos datamallin `Customer` kentät on nimetty samalla tavalla kuin vastaavat kentät `FormView`:ssä.

Mikäli kenttiä ei ole nimetty samalla tavalla, voit lisätä `UseProperty`-annotaation lomakkeeseen kentän ylle, jonka haluat sitoa, jotta ne tietävät mihin datakenttiin viitataan.
:::

### Tietosidonta `onDidEnter()`-menetelmällä {#data-binding-with-ondidenter}

`onDidEnter`-menetelmä hyödyntää tietosidontaa yksinkertaistaakseen lomakekenttien täyttämisprosessia. Sen sijaan, että arvot asetettaisiin manuaalisesti jokaiselle kentälle, tiedot synkronoidaan nyt automaattisesti `BindingContext`in kanssa.

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

`context.read`-metodi webforJ:n tietosidontajärjestelmässä synkronoi käyttöliittymäkomponentin kentät datamallin arvojen kanssa. Sitä käytetään tässä tapauksessa täyttämään lomakekentät olemassa olevista tiedoista, varmistaen, että käyttöliittymä heijastaa nykyistä datan tilaa.

## Datan validointi {#validating-data}

Validointi varmistaa, että lomakkeeseen syötetyt tiedot noudattavat määritettyjä sääntöjä, parantaen datan laatua ja estäen virheelliset lähetysten. Tietosidonnan avulla validoinnin ei tarvitse olla manuaalisesti toteutettu, vaan se voidaan yksinkertaisesti konfiguroida, jolloin käyttäjän syötteestä saadaan reaaliaikaista palautetta.

### Validointisääntöjen määrittäminen {#defining-validation-rules}

Käyttämällä [Jakartaa](https://beanvalidation.org) ja säännöllisiä lausekkeita voit pakottaa useita sääntöjä kentälle. Usein käytettyjä esimerkkejä ovat sen varmistaminen, ettei kenttä ole tyhjää tai null, tai noudattaa tiettyä kaavaa.
Annotations asiakasluokassa voit antaa jakarta validointiparametreja kentälle.

:::info
Lisätietoja validoinnin asetuksesta on saatavilla [täältä](../../data-binding/validation/jakarta-validation.md#installation).
:::

```java
  @NotEmpty(message = "Nimi ei voi olla tyhjää")
  @Pattern(regexp = "[a-zA-Z]*", message = "Virheelliset merkit")
  private String firstName = "";
```

`onValidate`-menetelmä lisätään sen jälkeen hallitsemaan `Submit`-napin tilaa lomakekenttien pätevyyden perusteella. Tämä varmistaa, että vain kelvolliset tiedot voidaan lähettää.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` palauttaa toden, jos kaikki kentät ovat kelvollisia, ja epätoden, jos eivät. Tämä tarkoittaa, että `Submit`-nappi on aktiivinen niin kauan kuin kaikki kentät ovat kelvollisia. Muussa tapauksessa se pysyy pois päältä, estäen lähettämisen ennen kuin korjaukset on tehty.

### Lisääminen ja muokkaaminen validoinnin kanssa {#adding-and-editing-entries-with-validation}

`submitCustomer()`-menetelmä validoi nyt tiedot käyttäen `BindingContext`-yhteyttä ennen lisäämistä tai muokkaamista. Tämä lähestymistapa eliminoi tarpeen manuaalisiin validointitarkastuksiin, hyödyntäen kontekstin sisäänrakennettuja mekanismeja varmistaakseen, että vain kelvolliset tiedot käsitellään.

- **Lisäystila**: Jos `id`:tä ei ole annettu, lomake on lisäystilassa. Vahvistetut tiedot kirjoitetaan `Customer`-malliin ja lisätään rekisteriin kutsumalla `Service.getCurrent().addCustomer(customer)`.
- **Muokkaustila**: Jos `id` on läsnä, menetelmä noutaa vastaavat asiakastiedot, päivittää ne vahvistetuilla syötteillä ja sitouttaa muutokset rekisteriin.

Kutsumalla `context.write(customer)` palautetaan `ValidationResult`-instanssi. Tämä luokka osoittaa, onko validointi onnistunut vai ei, ja tallentaa mahdolliset tämän tuloksen yhteydessä olevat viestit.

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

Suorittamalla tämän vaiheen sovellus tukee nyt tietosidontaa ja validointia, varmistaen että lomakkeiden syötteet synkronoidaan mallin kanssa ja noudattavat ennalta määriteltyjä sääntöjä.
