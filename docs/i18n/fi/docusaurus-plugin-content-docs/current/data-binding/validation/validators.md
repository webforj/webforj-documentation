---
sidebar_position: 2
title: Validators
_i18n_hash: 3d41925977054029c22c2110455dd419
---
Validators tarkistavat tietoja käyttöliittymän komponenteissa määriteltyjä rajoja vastaan ennen kuin nämä tiedot tallennetaan tietomalliin. Voit käyttää validoijia varmistaaksesi, että tiedot täyttävät tietyt kriteerit, kuten olevan tietyllä alueella, vastaavan kaavaa tai eivät ole tyhjät.

Validoinnit on määritetty jokaiselle sidonnalle, mikä mahdollistaa erityisten sääntöjen soveltamisen jokaiselle tietopisteelle erikseen. Tämä asetus varmistaa, että kukin tieto käy läpi validoinnin sen omien vaatimusten mukaan.

## Validatorsin lisääminen {#adding-validators}

Lisää validoijat sidontaan käyttämällä `useValidator`-metodia `BindingBuilder`:issa.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Nimi ei voi olla tyhjö")
    .useValidator(value -> value.length() >= 3, "Nimen on oltava vähintään 3 merkkiä pitkä")
    .add();
```

Yllä olevassa esimerkissä kaksi validoijaa varmistaa, että nimi ei ole tyhjö ja että se sisältää vähintään kolme merkkiä.

:::tip Validatorsin käsittely
Sidonnalle ei ole ylärajaa sille, kuinka monta validoijaa voit lisätä. Sidonta soveltaa validoijia lisäämisen järjestyksessä ja pysähtyy ensimmäiseen rikkomukseen.
:::

## Validatorsien toteuttaminen {#implementing-validators}

Voit luoda mukautettuja uudelleenkäytettäviä validoijia toteuttamalla `Validator<T>`-rajapinnan, jossa `T` on se tietotyyppi, jonka haluat validoida. Tämä asetus edellyttää validointimenetelmän määrittämistä, joka tarkistaa tiedot ja palauttaa `ValidationResult`.

Tässä on esimerkki uudelleenkäytettävästä validoijasta, joka tarkistaa onko käyttäjän sähköposti voimassa.

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

## Validatorsien käyttäminen sidonnoissa {#using-validators-in-bindings}

Kun olet määrittänyt validoijan, voit helposti soveltaa sitä mihin tahansa relevanttiin sidontaan sovelluksessasi. Tämä on erityisen hyödyllistä komponenteille, jotka vaativat yhteisiä validointisääntöjä eri osissa sovellustasi, kuten käyttäjän sähköpostiosoitteille tai salasanan vahvuudelle.

Sovelletaan `EmailValidator`-validoijaa sidontaan:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Validoijaviestien ohittaminen {#overriding-validator-messages}

Voit mukauttaa validoijien virheviestejä sidontapisteessä tietylle käyttöliittymäkomponentille. Tämä antaa sinulle mahdollisuuden tarjota yksityiskohtaisempaa tai kontekstiin liittyvää tietoa käyttäjälle, jos validointi epäonnistuu. Mukautetut viestit ovat erityisen hyödyllisiä, kun sama validoija soveltuu useisiin komponentteihin, mutta vaatii erilaista käyttäjän opastusta sen mukaan, missä kontekstissa sitä käytetään.

Näin voit ohittaa käyttöliittymäkomponentin sidonnassa uudelleenkäytettävän validoijan oletusviestin:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mukautettu viesti virheelliselle sähköpostiosoitteelle"))
    .add();
```

Yllä olevassa esimerkissä koodi soveltaa `EmailValidator`-validoijaa sähköpostikentälle mukautetulla virheviestillä, joka on erityisesti räätälöity kyseiselle kentälle. Tämä mahdollistaa suuntautuneemman ja avustavamman käyttäjäkokemuksen, jos validointi epäonnistuu.

:::tip Ymmärtäminen `Validator.from`
`Validator.from`-metodi käärii annetun validoijan uudella, jonka avulla voit määrittää mukautetun virheviestin, jos validoija ei tue mukautettuja viestejä. Tämä tekniikka on erityisen hyödyllinen, kun haluat soveltaa samaa validointilogiikkaa useisiin komponentteihin, mutta eri, kontekstiin liittyvien virheviestien kanssa jokaiselle instanssille.
:::
