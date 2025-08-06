---
sidebar_position: 2
title: Validators
_i18n_hash: 98f40d70b15464d8c7ee48710b07d8fc
---
Validators tarkistaa tietoja käyttöliittymän komponenteissasi määriteltyjen rajoitteiden perusteella ennen kuin tietoja tallennetaan tietomalliin. Voit soveltaa validaattoreita varmistaaksesi, että tiedot täyttävät tietyt kriteerit, kuten olevan määritellyn alueen sisällä, vastaavan kaavaa tai olemaan tyhjät.

Validoinnit on määritelty siten, että jokaiselle sidokselle voidaan soveltaa erityisiä sääntöjä, mikä mahdollistaa jokaisen tietopisteen individualisoidun vahvistamisen. Tämä asetus varmistaa, että jokainen tietotulo käy läpi validoinnin omien vaatimustensa mukaan.

## Adding validators {#adding-validators}

Lisää validaattoreita sidokseen käyttämällä `useValidator`-metodia `BindingBuilder`-luokassa.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Nimi ei voi olla tyhjö")
    .useValidator(value -> value.length() >= 3, "Nimen on oltava vähintään 3 merkkiä pitkä")
    .add();
```

Yllä olevassa esimerkissä kaksi validaattoria varmistaa, että nimi ei ole tyhjö ja että se sisältää vähintään kolme merkkiä.

:::tip Validators processing
Sidokselle voidaan lisätä rajattomasti validaattoreita. Sidonta soveltaa validaattoreita lisäämisjärjestyksessä ja pysähtyy ensimmäiseen rikkomukseen.
:::

## Implementing validators {#implementing-validators}

Voit luoda räätälöityjä uudelleenkäytettäviä validaattoreita toteuttamalla `Validator<T>`-rajapinnan, jossa `T` on se tietotyyppi, jota haluat validoida. Tämä asetus sisältää validate-metodin määrittämisen, joka tarkistaa tiedot ja palauttaa `ValidationResult`-tuloksen.

Tässä on esimerkki uudelleenkäytettävästä validaattorista, joka tarkistaa, onko käyttäjän sähköpostiosoite voimassa.

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

## Using validators in bindings {#using-validators-in-bindings}

Kun olet määrittänyt validaattorin, voit helposti soveltaa sitä mihin tahansa relevantteihin sidoksiin sovelluksessasi. Tämä on erityisen hyödyllistä komponenteille, jotka vaativat yleisiä validointisääntöjä eri osissa sovellustasi, kuten käyttäjän sähköpostiosoitteille tai salasanan vahvuudelle.

Sovita `EmailValidator`-validaattori sidokseen:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Overriding validator messages {#overriding-validator-messages}

Voit mukauttaa validaattorien virheviestejä sitoutumishetkellä tiettyyn käyttöliittymän komponenttiin. Tämä mahdollistaa käyttäjälle tarkempien tai kontekstin kannalta merkityksellisten tietojen tarjoamisen, jos validointi epäonnistuu. Mukautetut viestit ovat erityisen hyödyllisiä, kun sama validaattori soveltuu useisiin komponentteihin, mutta vaatii erilaisia ohjeita käyttäjältä käytön kontekstista riippuen.

Näin voit ohittaa uudelleenkäytettävän validaattorin oletusviestin sidoksessa:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mukautettu viesti virheelliselle sähköpostiosoitteelle"))
    .add();
```

Yllä olevassa esimerkissä koodi soveltaa `EmailValidator`-validaattoria sähköpostikenttään mukautetulla virheviestillä, joka on erityisesti räätälöity kyseiselle kentälle. Tämä mahdollistaa käyttäjäkokemuksen suuntaamista ja auttamista, jos validointi epäonnistuu.

:::tip Understanding `Validator.from`
`Validator.from`-metodi käärii annetun validaattorin uudella, mikä mahdollistaa mukautetun virheviestin määrittämisen, jos validaattori ei tue mukautettuja viestejä. Tämä tekniikka on erityisen hyödyllinen, kun sinun tarvitsee soveltaa samaa validointilogiiikkaa useisiin komponentteihin, mutta erilaisten, kontekstiin liittyvien virheviestien kanssa jokaiselle instanssille.
:::
