---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 412c446b42788eae1b7f7e16194afda9
---
webforJ tarjoaa useita ominaisuuksia, jotka helpottavat kehittäjien konfigurointi- ja automaattisen sidontaprosessia. Tämä osio osoittaa, kuinka käyttää näitä ominaisuuksia tehokkaasti.

## Käyttämällä `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of`-menetelmä sitoo automaattisesti käyttöliittymäkomponentit määritetyn bean-luokan ominaisuuksiin, mikä yksinkertaistaa sidontaprosessia ja vähentää manuaalista asetusta. Se kohdistaa sidottavat komponentit, jotka on määritelty kenttinä lomakkeessa tai sovelluksessa, bean-ominaisuuksiin niiden nimien perusteella.

```java
public class HeroRegistration extends App {
  // Sidottavat komponentit
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

  // Asetus- ja hakumenetelmät
}
```

### `UseProperty`-annotaatio {#useproperty-annotation}

Kun haluat sitoa bean-ominaisuuden käyttöliittymäkomponenttiin, jolla on erilainen nimi, käytä `UseProperty`-annotaatiota. Tämä annotaatio tarjoaa tarkempaa sidontaa bean-ominaisuuksien ja käyttöliittymäkomponenttien välillä, erityisesti kun käsittelet [sisäkkäisiä bean-ominaisuuksia](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Sitoutuu nimi-ominaisuuteen
  @UseProperty("name")
  TextField nameField = new TextField("Nimi");
  
  // Sitoutuu sisäkkäiseen address.street-ominaisuuteen
  @UseProperty("address.street")
  TextField streetField = new TextField("Katu");

  // Sitoutuu voima-ominaisuuteen
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

### `BindingExclude`-annotaatio {#bindingexclude-annotation}

Käytä `BindingExclude`-annotaatiota sulkeaksesi komponentti automaattisista sidontakonfiguraatioista, kun haluat sitoa sen manuaalisesti tai sulkea sen kokonaan pois.

```java
public class HeroRegistration extends App {
  // Sidottavat komponentit
  @UseProperty("name")
  TextField nameField = new TextField("Tekstikenttä");

  @BindingExclude
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

### `UseValidator`-annotaatio {#usevalidator-annotation}

Käytä `UseValidator`-annotaatiota ilmoittaaksesi validoijista, jotka toteuttavat lisävalidointisääntöjä sidonnan aikana. Validoijat pätevät siinä järjestyksessä, jossa määrittelet ne.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Sähköpostiosoite");
}
```

### `UseTransformer`-annotaatio {#usetransformer-annotation}

Käytä `UseTransformer`-annotaatiota ilmoittaaksesi muuntajaluokan suoraan käyttöliittymäkentällä. `BindingContext` soveltaa automaattisesti määritettyä muuntajaa.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Päivämääräkenttä");
}
```

### `BindingReadOnly`-annotaatio {#bindingreadonly-annotation}

Käytä `BindingReadOnly`-annotaatiota [merkitäksesi sitoumus vain luku -tilaksi](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Käyttäjä-ID");
}
```

### `BindingRequired`-annotaatio {#bindingrequired-annotation}

Käytä `BindingRequired`-annotaatiota merkitäksesi sidonta pakolliseksi. Katso myös [pakollisten sidontojen havaitsemiseksi](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Käyttäjän sähköposti");
}
```

## Datan kirjoittaminen automaattisesti {#writing-data-automatically}

Sovellusten reagointikyvyn ja dynamiikan parantamiseksi voit käyttää `observe`-menetelmää. Tämä menetelmä varmistaa, että käyttöliittymäkomponenttien muutokset heijastuvat välittömästi tietomalliin. Se on erityisen hyödyllinen, kun tarvitset jatkuvaa synkronointia tietomallin ja käyttöliittymän välillä.

`observe`-menetelmä rekisteröi `ValueChangeEvent`-kuuntelijan kaikille sidonnan yhteydessä, jotta se voi seurata käyttäjän tekemisiä muutoksia, ja kirjoittaa sitten heti nämä muutokset sidottuihin mallin ominaisuuksiin, jos ne ovat voimassa. Kun kutsut tätä menetelmää ensimmäisen kerran, se heijastaa bean-ominaisuuksia käyttöliittymäkomponenteissa.

Tässä on esimerkki siitä, kuinka käyttää `observe`:

```java
Hero bean = new Hero("Superman", "Lennä");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Toimenpiteet beanin kanssa.
  }
});
```

:::info Päivityssuunta
Tämä automaattinen sidonta on yksisuuntainen; päivitykset heijastuvat malliin, kun päivität käyttöliittymäkomponentteja, mutta muutokset mallissa heijastuvat käyttöliittymäkomponentteihin vain kerran, kun kutsut menetelmää ensimmäisen kerran.
:::

:::tip Huomioitavaa
Vaikka `observe` lisää sovellusten vuorovaikutteisuutta, on tärkeää käyttää sitä harkiten:

- **Suorituskykyvaikutus**: Tiheät päivitykset voivat vaikuttaa suorituskykyyn, erityisesti monimutkaisilla malleilla tai hitaita taustapalveluja käytettäessä.
- **Käyttäjäkokemus**: Automaattisten päivitysten ei tulisi häiritä käyttäjän kykyä syöttää tietoja mukavasti.
:::

## Pakollisten sidontojen havaitseminen {#required-binding-detections}

Kun merkitset sidonnan pakolliseksi, se merkitsee komponentin pakolliseksi, edellyttäen, että komponentti tukee tätä tilaa `RequiredAware`-rajapinnan kautta. Sidonta ei kuitenkaan pakota tätä tilaa itsessään, vaan asettaa sen komponentille, kun se on soveltuva.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Kun käytät [Jakarta-annotaatioita](./validation/jakarta-validation.md), sitominen voi automaattisesti havaita pakollisen tilan sen perusteella, että bean-ominaisuuksilla on jokin seuraavista annotaatioista:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
