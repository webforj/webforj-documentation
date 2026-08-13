---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 60ea231c7622e56330eef34d26d615cc
---
webforJ tarjoaa useita ominaisuuksia, jotka yksinkertaistavat kehittäjien konfigurointia ja automaattista sitomista. Tämä osio havainnollistaa, kuinka näitä ominaisuuksia voidaan käyttää tehokkaasti.

## Using `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of` -metodi sitoo automaattisesti käyttöliittymäkomponentit määritellyn bean-luokan ominaisuuksiin, yksinkertaistaen sitomista ja vähentäen manuaalista asetusta. Se yhdistää sidottavat komponentit, jotka on määritelty kenttinä lomakkeessa tai sovelluksessa, bean-ominaisuuksiin niiden nimien perusteella.

```java
public class HeroRegistration extends App {
  // Sitoutuvat komponentit
  TextField name = new TextField("Tekstikenttä");
  ComboBox power = new ComboBox("Voima");

  // ...

  @Override
  public void run() throws WebforjException {
    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    // ...
  }
}
```

```java
public class Hero {
  private String name;
  private String power;

  // Asetus- ja hakufunktiot
}
```

### `UseProperty` annotation {#useproperty-annotation}

Kun haluat sitoa bean-ominaisuuden käyttöliittymäkomponenttiin, jolla on eri nimi, käytä `UseProperty` -annotaatiota. Tämä annotaatio antaa tarkkuutta bean-ominaisuuksien sitomisessa käyttöliittymäkomponentteihin, erityisesti kun käsittelet [sisäkkäisiä bean-ominaisuuksia](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Sitoo name-ominaisuuteen
  @UseProperty("name")
  TextField nameField = new TextField("Nimi");

  // Sitoo sisäkkäiseen address.street-ominaisuuteen
  @UseProperty("address.street")
  TextField streetField = new TextField("Katu");

  // Sitoo power-ominaisuuteen
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

### `BindingExclude` annotation {#bindingexclude-annotation}

Käytä `BindingExclude` -annotaatiota, jotta voit jättää komponentin automaattisten sitomis- ja konfigurointitusten ulkopuolelle, kun haluat sitoa sen manuaalisesti tai jättää sen kokonaan pois.

```java
public class HeroRegistration extends App {
  // Sitoutuvat komponentit
  @UseProperty("name")
  TextField nameField = new TextField("Tekstikenttä");

  @BindingExclude
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

### `UseValidator` annotation {#usevalidator-annotation}

Käytä `UseValidator` -annotaatiota sääntelemään validoijia, jotka pakottavat lisävalidaatiosäännöt sitomisen aikana. Validoijat soveltuvat siinä järjestyksessä, kuin määrittelet ne.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Sähköpostiosoite");
}
```

### `UseTransformer` annotation {#usetransformer-annotation}

Käytä `UseTransformer` -annotaatiota ilmoittaaksesi muuntajaluokan suoraan käyttöliittymäkentälle. `BindingContext` soveltaa automaattisesti määritettyä muuntajaa.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Päivämääräkenttä");
}
```

### `BindingReadOnly` annotation {#bindingreadonly-annotation}

Käytä `BindingReadOnly` -annotaatiota [merkitäksesi sitomisen vain luku -tilaksi](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Käyttäjän ID");
}
```

### `BindingRequired` annotation {#bindingrequired-annotation}

Käytä `BindingRequired` -annotaatiota merkitäksesi sitomisen pakolliseksi. Katso myös [pakollisten sitomisten tunnistukset](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Käyttäjän sähköposti");
}
```

## Writing data automatically {#writing-data-automatically}

Sovellusten reaktiivisuuden ja dynaamisuuden parantamiseksi voit käyttää `observe` -metodia. Tämä metodi varmistaa, että muutokset käyttöliittymäkomponenteissa leviävät heti tietomalliin. Se on erityisen hyödyllinen, kun tarvitset jatkuvaa synkronointia tietomallin ja käyttöliittymän välillä.

`Observe`-metodi rekisteröi `ValueChangeEvent`-kuuntelijan kaikille sitomisille kontekstissa, jotta se voi seurata käyttäjän tekemiä muutoksia ja kirjoittaa välittömästi nämä muutokset mallin sidottuihin ominaisuuksiin, jos ne ovat voimassa. Kun käytät tätä metodia ensimmäisen kerran, se heijastaa bean-ominaisuuksia käyttöliittymäkomponenteissa.

Tässä on esimerkki siitä, kuinka käyttää `observe`:

```java
Hero bean = new Hero("Superman", "Fly");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Toimi beanin kanssa.
  }
});
```

:::info Päivityssuunta
Tämä automaattinen sitominen on yksisuuntainen; päivitykset heijastuvat malliin, kun päivität käyttöliittymäkomponentteja, mutta muutokset mallissa heijastuvat käyttöliittymäkomponentteihin vain kerran, kun käytät metodia ensimmäisen kerran.
:::

:::tip Huomioita
Vaikka `observe` lisää sovellusten vuorovaikutteisuutta, on tärkeää käyttää sitä harkiten:

- **Suorituskykyvaikutus**: Usein tapahtuvat päivitykset voivat vaikuttaa suorituskykyyn, erityisesti monimutkaisilla malleilla tai hitaita taustapalveluita käyttäessä.
- **Käyttäjäkokemus**: Automaattisten päivitysten ei tulisi häiritä käyttäjän mahdollisuutta syöttää tietoja mukavasti.
:::

## Required binding detections {#required-binding-detections}

Kun merkitset sitomisen pakolliseksi, se merkitsee komponentin pakolliseksi, edellyttäen että komponentti tukee tätä tilaa `RequiredAware` -rajapinnan kautta. Sitoja ei pakota tätä tilaa itse, vaan asettaa sen komponentille, kun se on sovellettavissa.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add();
```

Kun käytetään [Jakarta-annotaatioita](./validation/jakarta-validation.md), sitominen voi automaattisesti tunnistaa pakollisen tilan sen perusteella, että bean-ominaisuuksissa on jokin seuraavista annotaatioista:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
