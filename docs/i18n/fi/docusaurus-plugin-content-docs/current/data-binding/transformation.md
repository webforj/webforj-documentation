---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: 3b1655fdbfa9c303ae1445beee9ee327
---
Data transformations convert between the data types used in UI components and those in your data model. This keeps data types compatible and appropriately formatted when moving data between the frontend and backend of your applications.

:::tip
Transformer-asetusta käytetään parhaiten, kun bean-ominaisuuden tietotyyppi ei vastaa UI-komponenttien käsittelemää tietotyyppiä. Jos tarvitset vain muuntaa samantyyppistä dataa, on suosittua määrittää [sidosten gettereitä ja settereitä](bindings#binding-getters-and-setters).
:::

## Transformerien konfigurointi {#configuring-transformers}

Voit konfiguroida datamuunnoksia suoraan sidoksissasi, mikä mahdollistaa sen, että voit määrittää, miten dataa tulisi muuntaa data-sidontaprosessin aikana.

Voit lisätä transformereita sidokseen käyttämällä `useTransformer`-menetelmää `BindingBuilder`:ssä. Transformereiden on toteutettava `Transformer`-rajapinta, joka vaatii metodien määrittämistä molempiin suuntiin datan virtausta: mallista UI:hin ja UI:sta malliin.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Yllä olevassa esimerkissä koodi konfiguroi `CurrencyTransformer`:n käsittelemään muunnoksia mallin tietotyypin (esimerkiksi BigDecimal) ja UI-edustuksen (esimerkiksi muotoiltu merkkijono) välillä.

:::info
Jokaisella sidoksella on yksi transformer. Jos arvon muuntaminen vaatii useita vaiheita, on suositeltavaa toteuttaa oma transformer näille vaiheille.
:::

## Transformer:in toteuttaminen {#implementing-a-transformer}

Tässä on esimerkki yksinkertaisesta transformerista, joka muuntaa `LocalDate`-mallin ja `String`-UI-edustuksen välillä:

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.webforj.data.transformation.TransformationException;
import com.webforj.data.transformation.transformer.Transformer;

public class DateTransformer implements Transformer<LocalDate, String> {
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public LocalDate transformToComponent(String modelValue) {
    try {
      return LocalDate.parse(modelValue, formatter);
    } catch (Exception e) {
      throw new TransformationException("Virheellinen päivämäärämuoto");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Virheellinen päivämäärämuoto");
    }
  }
}
```

Tämä transformer käsittelee päivämääräkenttiä, muotoilemalla päivämäärät esitettäväksi UI:ssa ja jäsentämällä ne takaisin malliin.

### Transformerien käyttäminen sidoksissa {#using-transformers-in-bindings}

Kun olet määrittänyt transformerin, voit soveltaa sitä useisiin sidoksiin sovelluksessa. Tämä lähestymistapa on erityisen hyödyllinen standardoituja datamuotoja varten, joita on käsiteltävä johdonmukaisesti sovelluksen eri osissa.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Bean-ominaisuuden tyypin määrittäminen

`bind`-menetelmässä bean-ominaisuuden tyypin määrittäminen kolmantena parametrina on olennaista, kun UI-komponentin näyttämä tietotyyppi ja mallissa käytetty tietotyyppi eivät vastaa toisiaan. Esimerkiksi, jos komponentti käsittelee `startDateField` Java `LocalDate`-tietotyypinä, mutta se on tallennettu `String`-tietotyppinä mallissa, tyypin määrittäminen selvästi `String.class`:iksi ohjeistaa sidontamekanismin käsittelemään ja muuntamaan dataa oikein komponentin ja beanin välillä käyttäen annettua transformeria ja validoijia.
:::

### Muunnosten yksinkertaistaminen `Transformer.of`:lla {#simplifying-transforms-with-transformerof}

On mahdollista yksinkertaistaa tällaisten muunnosten toteutusta käyttämällä `Transformer.of`-menetelmää, jonka tarjoaa `Transformer`. Tämä menetelmä on syntaktista sokeria ja mahdollistaa sen, että voit kirjoittaa metodin, joka käsittelee muunnoksia sisäisesti, sen sijaan, että siirtäisit luokan, joka toteuttaa `Transformer`-rajapinnan.

Seuraavassa esimerkissä koodi käsittelee valintaruudun vuorovaikutusta matkailusovelluksessa, jossa käyttäjät voivat valita lisäpalveluja, kuten auton vuokrauksen. Valintaruudun tila `boolean` on muunnettava merkkijonoedustukseen `"yes"` tai `"no"`, joka on taustamallissa käytössä.

```java
CheckBox carRental = new CheckBox("Auton vuokraus");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // muunna komponentin arvo mallin arvoksi
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // muunna mallin arvo komponentin arvoksi
        str -> str.equals("yes")
      ),

      // jos muunnos epäonnistuu, näytä seuraava
      // viesti
      "Valintaruutu on oltava valittuna"
  )
  .add();
```

### Dynaamiset transformer-virheviestit <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Oletuksena virheviesti, joka näytetään, kun muunnos epäonnistuu, on staattinen merkkijono. Sovelluksissa, jotka tukevat useita kieliä, voit siirtää `Supplier<String>`-rajapinnan sijasta niin, että viesti ratkaistaan joka kerta, kun muunnos epäonnistuu:

```java {7}
context.bind(quantityField, "quantity", Integer.class)
    .useTransformer(
        Transformer.of(
            str -> Integer.parseInt(str),
            val -> String.valueOf(val)
        ),
        () -> t("validation.quantity.invalid")
    )
    .add();
```

Supplier kutsutaan vain silloin, kun muunnos heittää `TransformationException`:n. Tämä tarkoittaa, että viesti heijastaa aina nykyistä paikallista asetusta epäonnistumisen hetkellä.

#### Paikalliset tietoanalyysit {#locale-aware-transformers}

Toistuville transformereille, jotka tarvitsevat pääsyn nykyiseen paikalliseen asetukseen sisäisesti (esimerkiksi numeroiden tai päivämäärien muotoiluun alueellisten käytäntöjen mukaan), toteuta `LocaleAware`-rajapinta. Kun paikallisuus muuttuu `BindingContext.setLocale()`-menetelmällä, konteksti siirtää automaattisesti uuden paikallisuuden muuntelijalle, joka toteuttaa tämän rajapinnan.
