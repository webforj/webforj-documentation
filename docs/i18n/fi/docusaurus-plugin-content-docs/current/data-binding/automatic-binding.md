---
sidebar_position: 5
title: Automaattinen sidonta
description: >-
  Sido käyttöliittymän kentät automaattisesti beanin ominaisuuksiin
  BindingContext.of-metodin avulla käyttäen UseProperty-, BindingExclude- ja
  UseValidator-annotaatioita.
_i18n_title_hash: 666e71f372d1b611262598ca5cd23499
_i18n_description_hash: 985dfb18ded910f005bf2a2e040a8f9d
_i18n_hash: 55ac69466d077caf09fa5b765b76074a
---
webforJ tarjoaa useita ominaisuuksia, jotka sujuvoittavat kehittäjien määritys- ja automaattista sidontaprosessia. Tässä osiossa näytetään, miten näitä ominaisuuksia käytetään tehokkaasti.

## `BindingContext.of`-metodin käyttäminen {#using-bindingcontextof}

`BindingContext.of`-metodi sitoo käyttöliittymäkomponentit automaattisesti määritetyn bean-luokan ominaisuuksiin, mikä yksinkertaistaa sidontaprosessia ja vähentää manuaalista määritystyötä. Se kohdistaa sidottavat komponentit, jotka on määritetty kenttinä lomakkeessa tai sovelluksessa, bean-ominaisuuksiin niiden nimien perusteella.

```java
public class HeroRegistration extends App {
  // Bindable components
  TextField name = new TextField("Text Field");
  ComboBox power = new ComboBox("Power");

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

### `UseProperty`-annotaatio {#useproperty-annotation}

Kun haluat sitoa bean-ominaisuuden käyttöliittymäkomponenttiin, jolla on eri nimi, käytä `UseProperty`-annotaatiota.
Tämä annotaatio tarkentaa bean-ominaisuuksien ja käyttöliittymäkomponenttien välisiä sidontoja etenkin silloin, kun käsittelet [sisäkkäisiä bean-ominaisuuksia](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Binds to the name property
  @UseProperty("name")
  TextField nameField = new TextField("Name");

  // Binds to the nested address.street property
  @UseProperty("address.street")
  TextField streetField = new TextField("Street");

  // Binds to the power property
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `BindingExclude`-annotaatio {#bindingexclude-annotation}

Käytä `BindingExclude`-annotaatiota, kun haluat jättää komponentin automaattisen sidonnan ulkopuolelle esimerkiksi siksi, että sidot sen mieluummin itse tai et halua sitoa sitä lainkaan.

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `UseValidator`-annotaatio {#usevalidator-annotation}

Käytä `UseValidator`-annotaatiota määrittääksesi validoijat, jotka valvovat sidonnan aikana lisävalidointisääntöjä. Validoijat suoritetaan määrittämässäsi järjestyksessä.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### `UseTransformer`-annotaatio {#usetransformer-annotation}

Käytä `UseTransformer`-annotaatiota määrittääksesi muunnosluokan suoraan käyttöliittymäkentässä. `BindingContext` ottaa määritetyn muuntimen automaattisesti käyttöön.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### `BindingReadOnly`-annotaatio {#bindingreadonly-annotation}

Käytä `BindingReadOnly`-annotaatiota [merkitäksesi sidonnan vain luku -tilaiseksi](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### `BindingRequired`-annotaatio {#bindingrequired-annotation}

Käytä `BindingReadOnly`-annotaatiota merkitäksesi sidonnan pakolliseksi. Katso myös [pakollisten sidontojen tunnistaminen](#required-binding-detections).


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Tietojen automaattinen kirjoittaminen {#writing-data-automatically}

Voit parantaa sovellusten reagointikykyä ja dynaamisuutta käyttämällä `observe`-metodia. Se varmistaa, että käyttöliittymäkomponenttien muutokset välittyvät välittömästi tietomalliin. Metodi on erityisen hyödyllinen, kun tietomalli ja käyttöliittymä on pidettävä jatkuvasti synkronoituina.

`observe`-metodi rekisteröi `ValueChangeEvent`-kuuntelijan kaikille kontekstin sidonnoille, jolloin se voi seurata käyttäjän tekemiä muutoksia ja kirjoittaa ne heti mallin sidottuihin ominaisuuksiin, jos muutokset ovat kelvollisia. Kun kutsut metodia ensimmäisen kerran, se heijastaa bean-ominaisuudet käyttöliittymäkomponentteihin.

Esimerkki `observe`-metodin käytöstä:

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
    // Take action with the bean.
  }
});
```

:::info Päivityssuunta
Tämä automaattinen sidonta on yksisuuntainen: käyttöliittymäkomponenttien päivitykset näkyvät mallissa, mutta mallin muutokset näkyvät käyttöliittymäkomponenteissa vain kerran, kun kutsut metodia ensimmäisen kerran.
:::

:::tip Huomioitavaa
Vaikka `observe` lisää sovellusten vuorovaikutteisuutta, sitä on tärkeää käyttää harkiten:

- **Suorituskykyvaikutus**: Tiheät päivitykset voivat heikentää suorituskykyä etenkin monimutkaisten mallien tai hitaiden taustapalveluiden yhteydessä.
- **Käyttökokemus**: Automaattiset päivitykset eivät saa haitata käyttäjän mahdollisuutta syöttää tietoja vaivattomasti.
:::


## Pakollisten sidontojen tunnistaminen {#required-binding-detections}

Kun merkitset sidonnan pakolliseksi, komponentti merkitään pakolliseksi, jos se tukee tätä tilaa `RequiredAware`-rajapinnan kautta. Sidonta ei itsessään pakota tätä tilaa, vaan asettaa sen komponentille silloin, kun se on mahdollista.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 Käytettäessä [Jakarta-annotaatioita](./validation/jakarta-validation.md) sidonta voi tunnistaa pakollisen tilan automaattisesti, jos bean-ominaisuudessa on jokin seuraavista annotaatioista:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`*

:::note `@Size`-annotaation käyttäminen pakollisessa sidonnassa <DocChip chip='since' label='26.10' />
`@Size` merkitsee sidotun komponentin pakolliseksi vain, jos sille on määritetty `min`-arvo.
Aiemmissa versioissa `@Size` merkitsi sidotun komponentin aina pakolliseksi.
:::
