---
sidebar_position: 4
title: Transformation
_i18n_hash: fccb434a8897618a0197f9883cd94795
---
Tietomuunnokset ovat keskeinen ominaisuus, joka helpottaa saumattomia muutoksia UI-komponenttien ja tietomallisi käyttämien tietotyyppien välillä. Tämä ominaisuus varmistaa, että tietotyypit ovat yhteensopivia ja oikein muotoiltuja siirrettäessä tietoja sovelluksesi etu- ja taustapään välillä.

:::tip
Muunnossäätöä kannattaa käyttää silloin, kun pavun ominaisuuden tietotyyppi ei vastaa UI-komponenttien käsittelemää tietotyyppiä. Jos sinun tarvitsee vain muuntaa saman tyyppisiä tietoja, [sidosten getterien ja setterien](bindings#binding-getters-and-setters) määrittäminen on suositeltava lähestymistapa.
:::

## Muunnosten määrittäminen {#configuring-transformers}

Voit määrittää tietomuunnokset suoraan sidoskäskyissäsi, mikä mahdollistaa sen, että voit määritellä, miten tietoja tulisi muuntaa tietosidontaprosessin aikana.

Voit lisätä muunnoksia sidokseen käyttämällä `useTransformer`-menetelmää `BindingBuilder`:issa. Muunnosten on toteutettava `Transformer`-rajapinta, joka vaatii menetelmien määrittämistä molempiin suuntiin tietovirtaa: mallista UI:hin ja UI:sta malliin.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Esimerkissä yllä koodi määrittää `CurrencyTransformer`:in käsittelemään muutoksia mallin tietotyypin (esimerkiksi BigDecimal) ja UI-esityksen (esimerkiksi muotoiltu merkkijono) välillä.

:::info
Jokaisella sidoksella on oma muunnoksensa. Jos arvon muuntaminen vaatii useita vaiheita, on suositeltavaa toteuttaa oma muunnoksesi näitä vaiheita varten.
:::

## Muunnoksen toteuttaminen {#implementing-a-transformer}

Tässä on esimerkki yksinkertaisen muunnoksen toteuttamisesta, joka muuntaa `LocalDate`-mallin ja `String`-UI-esityksen välillä:

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

Tämä muunnos helpottaa päivämääräkenttien käsittelyä, varmistamalla, että päivämäärät on oikein muotoiltu, kun ne näytetään UI:ssa ja oikein jäsennelty takaisin malliin.

## Muunnosten käyttäminen sidoksissa {#using-transformers-in-bindings}

Kun olet määrittänyt muunnoksen, voit soveltaa sitä useisiin sidoksiin sovelluksessasi. Tämä lähestymistapa on erityisen hyödyllinen standardoiduille tietomuodoille, joita on käsiteltävä johdonmukaisesti eri osissa sovellustasi.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Pavun ominaisuuden tyypin määrittäminen

`bind`-menetelmässä pavun ominaisuuden tyypin määrittäminen kolmantena parametrina on tärkeää, kun UI-komponentin näyttämä tietotyyppi ja mallin käyttämä tietotyyppi poikkeavat toisistaan. Esimerkiksi, jos komponentti käsittelee `startDateField`:iä Java `LocalDate`-tyyppinä komponentissa mutta tallennettuna `String`-tyyppinä mallissa, tyypin määrittäminen selkeästi `String.class`:ksi varmistaa, että sidontamekanismi käsittelee ja muuntaa tiedot tarkasti erilaisten tyyppien välillä, joita komponentti ja pavun käyttävät annetun muunnoksen ja validoijien avulla.
:::

## Muunnosten yksinkertaistaminen `Transformer.of`-menetelmällä {#simplifying-transforms-with-transformerof}

Tällaisia muunnoksia voidaan yksinkertaistaa käyttämällä `Transformer.of`-menetelmää, joka on saatavilla `Transformer`:issa. Tämä menetelmä on syntaktista sokeria ja mahdollistaa sen, että voit kirjoittaa menetelmän, joka käsittelee muunnoksia rivissä, sen sijaan että siirtäisit luokan, joka toteuttaa `Transformer`-rajapinnan.

Seuraavassa esimerkissä koodi käsittelee valintaruututoimintoa matkailusovelluksessa, jossa käyttäjät voivat valita lisäpalveluja, kuten autonvuokrauksen. Valintaruudun tila `boolean` on muunnettava merkkijonoesitykseksi `"yes"` tai `"no"`, jota taustamalli käyttää.

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
      "Valintaruudun on oltava valittuna"
  )
  .add();
```
