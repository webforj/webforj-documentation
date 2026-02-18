---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 4af002debda2abb59282b5c6a1bf01d7
---
Validators validointia tietoja käyttöliittymäkomponenteissasi määriteltyjen rajoitusten mukaan ennen kuin nämä tiedot tallennetaan tietomalliin. Voit käyttää validointia varmistaaksesi, että tiedot täyttävät tietyt kriteerit, kuten ollessaan määritellyssä alueessa, vastaavan mallin mukaisia tai olematta tyhjät.

Validoinnit konfiguroidaan sidontakohtaisesti, jolloin erityiset säännöt voidaan soveltaa jokaiselle datapisteelle erikseen. Jokainen tietopala käy läpi validoinnin sen omien vaatimusten mukaisesti.

## Validointien lisääminen {#adding-validators}

Lisää validointeja sidontaan käyttämällä `useValidator`-metodia `BindingBuilder`:ssä.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Nimi ei voi olla tyhjää")
    .useValidator(value -> value.length() >= 3, "Nimen on oltava vähintään 3 merkkiä pitkä")
    .add();
```

Yllä olevassa esimerkissä kaksi validointia varmistaa, että nimi ei ole tyhjää ja että se sisältää vähintään kolme merkkiä.

:::tip Validointien käsittely
Sidontaan voi lisätä rajattoman määrän validointeja. Sidonta soveltaa validointeja lisäämisjärjestyksessä ja pysähtyy ensimmäiseen rikkomukseen.
:::

## Validointien toteuttaminen {#implementing-validators}

Voit luoda mukautettuja uudelleenkäytettäviä validointeja toteuttamalla `Validator<T>`-rajapinnan, jossa `T` on validoitavan datan tyyppi. Tämä asettelu sisältää validate-metodin määrittämisen, joka tarkistaa datan ja palauttaa `ValidationResult`:in.

Tässä on esimerkki uudelleenkäytettävästä validoinnista, joka tarkistaa, onko käyttäjän sähköpostiosoite voimassa.

```java
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;

public class EmailValidator implements Validator<String> {
  private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

  @Override
  public ValidationResult validate(String value) {
    if (value.matches(PATTERN)) {
        return ValidationResult.valid();
    } else {
        return ValidationResult.invalid("Virheellinen sähköpostiosoite");
    }
  }
}
```

### Validointien käyttäminen sidonnoissa {#using-validators-in-bindings}

Kun olet määrittänyt validoinnin, voit helposti soveltaa sitä kaikkiin relevantteihin sidontoihin sovelluksessasi. Tämä on erityisen hyödyllistä komponenteille, jotka vaativat yhteisiä validointisääntöjä eri puolilla sovellustasi, kuten käyttäjän sähköpostiosoitteet tai salasanan vahvuus.

Soveltaaksesi `EmailValidator`-validointia sidontaan:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

### Validointiviestien ylikirjoittaminen {#overriding-validator-messages}

Voit mukauttaa validointien virheviestejä sidonnan kohdalla tietylle käyttöliittymäkomponentille. Tämä mahdollistaa yksityiskohtaisempien tai kontekstuaalisesti merkityksellisten tietojen tarjoamisen käyttäjälle, jos validointi epäonnistuu. Mukautetut viestit ovat erityisen hyödyllisiä, kun sama validointi koskee useita komponentteja, mutta vaatii erilaista ohjausta käytön kontekstin mukaan.

Tässä on, miten ylikirjoitat uudelleenkäytettävän validoinnin oletusviestin sidonnassa:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mukautettu viesti virheelliselle sähköpostiosoitteelle"))
    .add();
```

Yllä olevassa esimerkissä koodi soveltaa `EmailValidator`-validointia sähköpostikenttään mukautetulla virheviestillä, joka on erityisesti suunniteltu tälle kentälle.

:::tip Ymmärtäminen `Validator.from`
`Validator.from`-metodi käärii annetun validoinnin uuden ympärille, jolloin voit määrittää mukautetun virheviestin, jos validointi ei tue mukautettuja viestejä. Tämä tekniikka on erityisen hyödyllinen, kun tarvitset samaa validointilogiikkaa useissa komponenteissa, mutta erilaisten, kontekstikohtaisten virheviestien kanssa jokaiselle instanssille.
:::

### Dynaamiset validointiviestit <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Oletusarvoisesti validointiviestit ovat staattisia merkkijonoja, jotka asetetaan kerran sidonta-aikana. Sovelluksissa, jotka tukevat useita kieliä, nämä staattiset viestit eivät päivity, kun käyttäjä vaihtaa kieliversioita. Tämän ratkaisemiseksi sekä `useValidator` että `Validator`-tehtävämenetelmät hyväksyvät `Supplier<String>`-tyypin, joka kutsutaan joka kerta, kun validointi epäonnistuu, jolloin viesti voidaan ratkaista dynaamisesti.

#### Inline-validoinnit dynaamisilla viesteillä {#inline-validators-with-dynamic-messages}

Anna `Supplier<String>` tavallisen `String`-arvon sijaan `useValidator`:lle:

```java {2,3}
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
    .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
    .add();
```

Joka kerta, kun validointi suoritetaan ja predikaatti epäonnistuu, toimittaja kutsuu `t()`, joka ratkaisee viestin nykyiselle paikalle.

#### Tehtävämenetelmät dynaamisilla viesteillä {#factory-methods-with-dynamic-messages}

`Validator.of` ja `Validator.from`-tehtävämenetelmät hyväksyvät myös toimittajia:

```java {4,10}
// Luo predikaattiin perustuva validointi dynaamisella viestillä
Validator<String> required = Validator.of(
    value -> !value.isEmpty(),
    () -> t("validation.required")
);

// Kääri olemassa oleva validointi dynaamiseen ylikirjoitusviestiin
Validator<String> email = Validator.from(
    new EmailValidator(),
    () -> t("validation.email.invalid")
);
```

#### Paikallisesti tietoiset mukautetut validoinnit {#locale-aware-custom-validators}

Uudelleenkäytettäville validoijille, jotka tarvitsevat tuottaa paikalliset viestit sisäisesti, toteuta `LocaleAware`-rajapinta. Kun kieli muuttuu `BindingContext.setLocale()`-kutsun kautta, konteksti propagoi automaattisesti uuden kielen kaikille validoijille, jotka toteuttavat tämän rajapinnan. Seuraava validointisuoritus tuottaa viestejä päivitettynä kielenä.
