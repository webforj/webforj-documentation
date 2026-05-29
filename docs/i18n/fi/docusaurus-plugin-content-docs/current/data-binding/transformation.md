---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: e03ca3208470e53be7128ffb972c2670
---
Tietomuunnokset muuntavat käyttöliittymäkomponenttien käyttämät tietotyypit ja tietomallisi käyttämät tietotyypit. Tämä pitää tietotyypit yhteensopivina ja asianmukaisesti muotoiltuina, kun siirretään tietoja sovelluksesi etu- ja taustapään välillä.

:::tip
Muunninasetusta on parasta käyttää, kun pavun ominaisuuden tietotyyppi ei vastaa käyttöliittymäkomponenttien käsittelemää tietotyyppiä. Jos tarvitset vain muuntaa samaa tietotyyppiä, on suositeltavaa konfiguroida [sidosten getterit ja setterit](bindings#binding-getters-and-setters).
:::

## Muuntimien konfigurointi {#configuring-transformers}

Voit konfiguroida tietomuunnoksia suoraan sidoksissasi, jolloin voit määrittää, miten tietoja tulisi muuntaa tietosidontaprosessin aikana.

Voit lisätä muuntimia sidokseen `useTransformer`-menetelmällä `BindingBuilder`:issa. Muuntimien on toteutettava `Transformer`-rajapinta, mikä edellyttää menetelmien määrittämistä tietovirran molempiin suuntiin: modelista käyttöliittymään ja käyttöliittymästä malliin.

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

Edellä olevassa esimerkissä koodi konfiguroi `CurrencyTransformer`:in käsittelemään muunnoksia mallin tietotyypin (esimerkiksi BigDecimal) ja käyttöliittymän esityksen (esimerkiksi muotoiltu merkkijono) välillä.

:::info
Jokainen sidonta on liitetty yhteen muuntimeen. Jos arvon muuntaminen vaatii useita vaiheita, on suositeltavaa toteuttaa oma muunnin näitä vaiheita varten.
:::

## Muuntimen toteuttaminen {#implementing-a-transformer}

Tässä on esimerkki yksinkertaisesta muuntimesta, joka muuntaa `LocalDate`-mallin ja `String`-käyttöliittymäesityksen välillä:

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

Tämä muunnin käsittelee päivämääräkenttiä, muotoillen päivämääriä käyttöliittymässä ja jäsentäen ne takaisin malliin.

### Muuntimien käyttäminen sidoksissa {#using-transformers-in-bindings}

Kun olet määrittänyt muuntimen, voit soveltaa sitä useisiin sidoksiin sovelluksessasi. Tämä lähestymistapa on erityisen hyödyllinen standardoiduille tietomuodoille, jotka tarvitsevat yhdenmukaista käsittelyä eri osissa sovellustasi.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info Pavun ominaisuustyypin määrittäminen

`bind`-menetelmässä on tärkeää määrittää pavun ominaisuuden tyyppi kolmantena parametrina, kun käyttöliittymäkomponentin näyttämä tietotyyppi ja mallissa käytetty tietotyyppi poikkeavat. Esimerkiksi, jos komponentti käsittelee `startDateField`-kenttää Java `LocalDate`-tyyppinä, mutta se on tallennettu `String`-tyyppinä mallissa, tyyppinä määrittäminen `String.class`-muodossa kertoo sidontamekanismille, että se käsittelee ja muuntaa tiedot tarkasti kahden eri tyypin välillä, joita komponentti ja pavun muunnin käyttävät.
:::

### Muunnosten yksinkertaistaminen `Transformer.of`-menetelmällä {#simplifying-transforms-with-transformerof}

On mahdollista yksinkertaistaa tällaisten muunnosten toteutusta `Transformer.of`-menetelmällä, jonka `Transformer` tarjoaa. Tämä menetelmä on syntaktista sokeria, ja sen avulla voit kirjoittaa menetelmän, joka käsittelee muunnoksia inline, sen sijaan että välität `Transformer`-rajapinnan toteuttavan luokan. 

Seuraavassa esimerkissä koodi käsittelee valintaruudun vuorovaikutusta matkailusovelluksessa, jossa käyttäjät voivat valita lisäpalveluja, kuten autonvuokrausta. Valintaruudun tila `boolean` on muutettava merkkijonoesitykseksi `"yes"` tai `"no"`, jota taustamalli käyttää.

```java
CheckBox carRental = new CheckBox("Autonvuokraus");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // muunnos komponentin arvosta mallin arvoon
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // muunnos mallin arvosta komponentin arvoon
        str -> str.equals("yes")
      ),

      // jos muunnos epäonnistuu, näytä seuraava
      // viesti
      "Valintaruutu on tarkistettava"
  )
  .add();
```

### Dynaamiset muunninvirheviestit <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Oletuksena virheviesti, joka näytetään muunnoksen epäonnistuessa, on staattinen merkkijono. Monikielisiä sovelluksia tukevaan sovellukseen voit sen sijaan välittää `Supplier<String>`-tyyppisen, jolloin viesti ratkaistaan aina, kun muunnos epäonnistuu:

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

Supplieria kutsutaan vain, kun muunnos heittää `TransformationException`:in. Tämä tarkoittaa, että viesti heijastaa aina nykyistä paikallista kieltä epäonnistumisen hetkellä.

#### Paikallinen tieto huomioivat muuntimet {#locale-aware-transformers}

Toistuville muuntimille, jotka tarvitsevat pääsyn nykyiseen kieliversioon sisäisesti (esimerkiksi numeroiden tai päivämäärien muotoilua alueellisten käytäntöjen mukaan), toteuta `LocaleAware`-rajapinta. Kun kieliversio muuttuu `BindingContext.setLocale()`-menetelmällä, konteksti välittää automaattisesti uuden kieliversion muuntimiin, jotka toteuttavat tämän rajapinnan.
