---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 996b617e97e439660bbe69f15d6355b9
---
Validatorit validoivat tietoja käyttöliittymäkomponenteissasi määriteltyjen rajoitteiden mukaisesti ennen tämän tiedon sitomista tietomalliin. Voit soveltaa validoijia varmistaaksesi, että tiedot täyttävät tietyt kriteerit, kuten olevan tietyn alueen sisällä, vastaavan mallia tai ettei se ole tyhjää.

Validoinnit on määritelty sitovia, jolloin tietyt säännöt koskevat kutakin tietopistettä erikseen. Jokainen tietopiste käy läpi validoinnin omien vaatimustensa mukaan.

## Adding validators {#adding-validators}

Lisää validoijia sitovaan käyttöön `useValidator`-metodia `BindingBuilder`:issa.

```java
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), "Nimi ei saa olla tyhjää")
  .useValidator(value -> value.length() >= 3, "Nimen on oltava vähintään 3 merkkiä pitkä")
  .add();
```

Yllä olevassa esimerkissä kaksi validoijaa varmistaa, että nimi ei ole tyhjää ja että se sisältää vähintään kolme merkkiä.

:::tip Validators processing
Validoijien määrä, jonka voit lisätä per sitova, ei ole rajattu. Sitova soveltaa validoijia lisäämisjärjestyksessä ja pysähtyy ensimmäiseen rikkomukseen.
:::

## Implementing validators {#implementing-validators}

Voit luoda mukautettuja uudelleenkäytettäviä validoijia toteuttamalla `Validator<T>`-rajapinnan, jossa `T` on se datatyyppi, jota haluat validoida. Tämä asetelma sisältää validate-metodin määrittelemisen, joka tarkistaa datan ja palauttaa `ValidationResult`:in.

Tässä on esimerkki uudelleenkäytettävästä validoijasta, joka tarkistaa, onko käyttäjän sähköpostiosoite voimassa.

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

### Using validators in bindings {#using-validators-in-bindings}

Kun olet määrittänyt validoijan, voit helposti soveltaa sitä kaikkiin asiaankuuluviin sitoviin sovelluksessasi. Tämä on erityisen hyödyllistä komponenteille, jotka vaativat yhteisiä validointisääntöjä eri osissa sovellustasi, kuten käyttäjän sähköpostiosoitteiden tai salasanojen vahvuuden tarkistamiseen.

Sovita `EmailValidator` sitovaan:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
  .useValidator(new EmailValidator())
  .add();
```

### Overriding validator messages {#overriding-validator-messages}

Voit mukauttaa validoijien virheilmoituksia sitomisen kohtaan tiettyyn käyttöliittymäkomponenttiin. Tämä mahdollistaa sen, että voit antaa tarkempaa tai kontekstin mukaisempaa tietoa käyttäjälle, jos validointi epäonnistuu. Mukautetut viestit ovat erityisen hyödyllisiä, kun sama validoija soveltuu useisiin komponentteihin, mutta vaatii erilaista ohjeistusta sen mukaan, missä kontekstissa sitä käytetään.

Näin voit ylittää oletusviestin uudelleenkäytettävässä validoijassa sitovassa:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
  .useValidator(
    Validator.from(new EmailValidator(), "Mukautettu viesti virheellisestä sähköpostiosoitteesta"))
  .add();
```

Yllä olevassa esimerkissä koodi soveltaa `EmailValidator`-validoijaa sähköpostikenttään mukautetulla virheviestillä, joka on erityisesti räätälöity tälle kentälle.

:::tip Understanding `Validator.from`
`Validator.from`-metodi käärii lähetetyn validoijan uudella, mahdollistamalla sen, että voit määrittää mukautetun virheviestin, jos validoija ei tue mukautettuja viestejä. Tämä tekniikka on erityisen hyödyllinen, kun tarvitset saman validointilogiken soveltamista useisiin komponentteihin, mutta erilaisten, kontekstiin liittyvien virheviestien kanssa jokaiselle instanssille.
:::

### Dynamic validation messages <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Oletusarvoisesti validointiviestit ovat staattisia merkkijonoja, jotka asetetaan kerran sitomisen ajankohtana. Sovelluksissa, jotka tukevat useita kieliä, nämä staattiset viestit eivät päivity, kun käyttäjä vaihtaa paikallisia asetuksia. Tämän ratkaisemiseksi sekä `useValidator`- että `Validator`-tehdasmetodit hyväksyvät `Supplier<String>`-rajapinnan, joka kutsutaan jokaisen validoinnin epäonnistuessa, jolloin viesti voidaan ratkaista dynaamisesti.

#### Inline validators with dynamic messages {#inline-validators-with-dynamic-messages}

Anna `Supplier<String>` tavallisen `String` sijasta `useValidator`-metodille:

```java {2,3}
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
  .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
  .add();
```

Joka kerta, kun validointi suoritetaan ja lauseke epäonnistuu, tarjoaja kutsuu `t()`, joka ratkaisee viestin nykyiselle paikalliselle asetukselle.

#### Factory methods with dynamic messages {#factory-methods-with-dynamic-messages}

`Validator.of` ja `Validator.from` tehdasmetodit hyväksyvät myös tarjoajia:

```java {4,10}
// Luo lausekkeeseen perustuva validoija dynaamisella viestillä
Validator<String> required = Validator.of(
  value -> !value.isEmpty(),
  () -> t("validation.required")
);

// Kääri olemassa oleva validoija dynaamisella ylikirjoitusviestillä
Validator<String> email = Validator.from(
  new EmailValidator(),
  () -> t("validation.email.invalid")
);
```

#### Locale-aware custom validators {#locale-aware-custom-validators}

Mikäli tarvitset uudelleenkäytettäviä validoijia, jotka tuottavat paikallismuotoisia viestejä sisäisesti, toteuta `LocaleAware`-rajapinta. Kun alue muutetaan `BindingContext.setLocale()`-menetelmällä, konteksti propagoidaan automaattisesti uusi alue kaikille validoijille, jotka toteuttavat tämän rajapinnan. Seuraava validointikierros tuottaa viestejä päivitettyinä alueina.
