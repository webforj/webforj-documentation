---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: 170c308c3b93a933f5fb85c0f0ec4f15
---
webforJ tarjoaa useita ominaisuuksia, jotka tehostavat kehittäjien konfigurointi- ja automaattisen sitomisprosessia. Tämä osa osoittaa, kuinka näitä ominaisuuksia voidaan käyttää tehokkaasti.

## Using `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of` -metodi sitoo automaattisesti käyttöliittymäkomponentit tietyn bean-luokan ominaisuuksiin, mikä yksinkertaistaa sitomisprosessia ja vähentää manuaalista asetusta. Se yhdistää sitoutuvat komponentit, jotka on ilmoitettu kenttinä lomakkeessa tai sovelluksessa, bean-ominaisuuksien nimiin perustuen.

```java
public class HeroRegistration extends App {
  // Bindable components
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

  // Setters and getters
}
```

### `UseProperty` annotation {#useproperty-annotation}

Käytä `UseProperty`-annotaatiota määrittääksesi bean-ominaisuuden nimen, kun käyttöliittymän kentän nimi ei vastaa bean-ominaisuuden nimeä.

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Tekstikenttä");
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

Yllä olevassa esimerkissä käyttöliittymän kentän nimi on `nameField`, mutta bean-ominaisuus on `name`. Voit merkitä käyttöliittymän kentän bean-ominaisuuden nimellä varmistaaksesi oikean sitomisen.

### `BindingExclude` annotation {#bindingexclude-annotation}

Käytä `BindingExclude` -annotaatiota poistaaksesi komponentti automaattisesta sitomisesta, kun haluat sitoa sen manuaalisesti tai jättää sen kokonaan pois.

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Tekstikenttä");

  @BindingExclude
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

### `UseValidator` annotation {#usevalidator-annotation}

Käytä `UseValidator` -annotaatiota ilmoittaaksesi validoijista, jotka toteuttavat lisävälineitä sitomisen aikana. Validoijat sovelletaan siinä järjestyksessä, jossa ne määrität.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Sähköpostiosoite");
}
```

### `UseTransformer` annotation {#usetransformer-annotation}

Käytä `UseTransformer` -annotaatiota ilmoittaaksesi transformaattoriluokan suoraan käyttöliittymään. `BindingContext` soveltaa automaattisesti määritettyä transformaattoria.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Päivämääräkenttä");
}
```

### `BindingReadOnly` annotation {#bindingreadonly-annotation}

Käytä `BindingReadOnly` merkitäksesi sitominen vain lukukelpoiseksi. 

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Käyttäjätunnus");
}
```

### `BindingRequired` annotation {#bindingrequired-annotation}

Käytä `BindingRequired` merkitäksesi sitominen pakolliseksi. Katso myös [pakollisten sitomisten tunnistamiset](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Käyttäjän sähköposti");
}
```

## Writing data automatically {#writing-data-automatically}

Sovellusten reaktiivisuuden ja dynaamisuuden lisäämiseksi voit käyttää `observe`-metodia. Tämä metodi varmistaa, että käyttöliittymäkomponenttien muutokset välittyvät heti tietomalliin. Se on erityisen hyödyllinen, kun tarvitset jatkuvaa synkronointia tietomallin ja käyttöliittymän välillä.

`Observe`-metodi rekisteröi `ValueChangeEvent`-kuuntelijan kaikille sitomille konteksteissa, jotta se voi seurata käyttäjän tekemiä muutoksia ja kirjoittaa nämä muutokset mallitettuihin ominaisuuksiin heti, jos ne ovat voimassa. Kun kutsut tätä metodia ensimmäisen kerran, se heijastaa bean-ominaisuudet käyttöliittymäkomponenteissa.

Tässä on esimerkki `observe`-käytöstä:

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

:::info Update Direction
Tämä automaattinen sitominen on yksisuuntainen; päivitykset näkyvät mallissa, kun päivität käyttöliittymäkomponentteja, mutta muutokset mallissa näkyvät käyttöliittymäkomponenteissa vain kerran, kun kutsut metodia ensimmäisen kerran.
:::

:::tip Considerations
Vaikka `observe` lisää sovellusten vuorovaikutteisuutta, on tärkeää käyttää sitä harkiten:

- **Suorituskykyvaikutus**: Useat päivitykset voivat vaikuttaa suorituskykyyn, erityisesti monimutkaisissa malleissa tai hitaita taustapalveluja käytettäessä.
- **Käyttäjäkokemus**: Automaattiset päivitykset eivät saa häiritä käyttäjän kykyä syöttää tietoja mukavasti.
:::

## Required binding detections {#required-binding-detections}

Kun merkitset sitomisen pakolliseksi, se merkitsee komponentin pakolliseksi, edellyttäen että komponentti tukee tätä tilaa `RequiredAware`-rajapinnan kautta. Sitominen ei kuitenkaan pakota tätä tilaa, vaan asettaa sen komponentille tarvittaessa.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Kun käytät [Jakarta-annotaatioita](./validation/jakarta-validation.md), sitominen voi automaattisesti havaita pakollisen tilan, jos bean-ominaisuuksissa on jokin seuraavista annotaatioista:

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
