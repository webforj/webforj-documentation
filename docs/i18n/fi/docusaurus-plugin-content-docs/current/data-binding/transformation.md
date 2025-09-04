---
sidebar_position: 4
title: Transformation
_i18n_hash: fe3acbd17750ab0092cbc3609b967969
---
Data transformations ovat keskeinen ominaisuus, joka mahdollistaa sujuvan muuntamisen käyttöliittymäkomponenttien ja datamallisi käyttämien tietotyyppien välillä. Tämä ominaisuus varmistaa, että tietotyypit ovat yhteensopivia ja kunnolla muotoiltuja, kun siirrät tietoja sovellustesi etu- ja takapään välillä.

:::tip
Muunnossäätö on parasta käyttää, kun bean-ominaisuuden tietotyyppi ei vastaa käyttöliittymäkomponenttien käsittelemiä tietotyyppejä. Jos tarvitset vain muuntaa saman tyyppisiä tietoja, on suositeltavaa määrittää [sidontojen getterit ja setterit](bindings#binding-getters-and-setters).
:::

## Muunnosten määrittäminen {#configuring-transformers}

Voit määrittää tietomuunnoksia suoraan sidonnoissasi, jolloin voit määrittää, miten tietoja tulisi muuntaa tietosidontaprosessin aikana.

Voit lisätä muunnoksia sidontaan käyttämällä `useTransformer`-metodia `BindingBuilder`:issa. Muunnosten on toteutettava `Transformer`-rajapinta, joka vaatii määrittämään menetelmät kummallekin datan suuntalle: mallista käyttöliittymään ja käyttöliittymästä malliin.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Edellisessä esimerkissä koodi määrittää `CurrencyTransformer`:in käsittelemään muunnoksia mallin tietotyypin (esimerkiksi BigDecimal) ja käyttöliittymän esitysmuodon (esimerkiksi muotoiltu merkkijono) välillä.

:::info
Jokaisella sidonnalla on yksi ainoa muunnos. Jos arvon muuntaminen vaatii useita vaiheita, on suositeltavaa toteuttaa oma muunnos näitä vaiheita varten.
:::

## Muunnoksen toteuttaminen {#implementing-a-transformer}

Tässä on esimerkki yksinkertaisesta muunnoksesta, joka muuntaa `LocalDate`-mallin ja `String`-käyttöliittymäesityksen välillä:

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

Tämä muunnos helpottaa päivämääräkenttien käsittelyä varmistaen, että päivämäärät on oikein muotoiltu, kun ne näytetään käyttöliittymässä ja ne on oikein analysoitu takaisin malliin.

## Muunnosten käyttö sidonnoissa {#using-transformers-in-bindings}

Kun olet määrittänyt muunnoksen, voit soveltaa sitä useisiin sidontoihin sovelluksessasi. Tämä lähestymistapa on erityisen hyödyllinen vakioiduille tietomuodoille, joiden käsittely tarvitaan johdonmukaisesti sovelluksesi eri osissa.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Bean-ominaisuuden tyypin määrittäminen

`bind`-metodissa bean-ominaisuuden tyypin määrittäminen kolmannena parametrina on välttämätöntä, kun käyttöliittymäkomponentin näyttämä tietotyyppi ja mallissa käytetty tietotyyppi eivät vastaa toisiaan. Esimerkiksi, jos komponentti käsittelee `startDateField` Java `LocalDate`:na, mutta se tallennetaan mallissa `String`-tyypiksi, tyypin määrittäminen eksplisiittisesti `String.class`:ksi varmistaa, että sidontamekanismi käsittelee ja muuntaa tiedot tarkasti kahden eri tietotyypin välillä, joita komponentti ja bean käyttävät, käyttäen annettua muunnosta ja validoijia.
:::

## Muunnosten yksinkertaistaminen `Transformer.of` {#simplifying-transforms-with-transformerof}

On mahdollista yksinkertaistaa tällaisen muunnoksen toteutusta käyttämällä `Transformer.of`-metodia, jota `Transformer` tarjoaa. Tämä metodi on synnin sisältöä, ja se mahdollistaa kirjoittaa menetelmän, joka käsittelee muunnoksia suoraan, sen sijaan että siirtää luokan, joka toteuttaa `Transformer`-rajapinnan.

Seuraavassa esimerkissä koodi käsittelee valintaruudun vuorovaikutusta matkailusovelluksessa, jossa käyttäjät voivat valita lisäpalveluita, kuten autonvuokrausta. Valintaruudun tila `boolean` on muunnettava merkkiesitykseksi `"yes"` tai `"no"`, jota taustamalli käyttää.

```java
CheckBox carRental = new CheckBox("Autonvuokraus");
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
