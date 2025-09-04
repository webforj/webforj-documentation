---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: e914be874b2c22c5e32f7fce4b5f1885
---
webforJ tarjoaa useita ominaisuuksia, jotka tehostavat konfigurointia ja automaattista sitoutumisprosessia kehittäjille. Tämä osa osoittaa, miten näitä ominaisuuksia voidaan käyttää tehokkaasti.

## Käyttäen `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of`-menetelmä sitoo automaattisesti käyttöliittymäkomponentit määritellyn bean-luokan ominaisuuksiin, yksinkertaistaen sitoutumisprosessia ja vähentäen manuaalista asetusta. Se kohdistaa sitoutettavat komponentit, jotka on ilmoitettu kenttinä lomakkeessa tai sovelluksessa, beanin ominaisuuksiin niiden nimien perusteella.

```java
public class HeroRegistration extends App {
  // Sitoutettavat komponentit
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

  // Asetus ja haku
}
```

### `UseProperty`-annotaatio {#useproperty-annotation}

Käytä `UseProperty`-annotaatiota määrittääksesi bean-ominaisuuden nimen, kun käyttöliittymän kentän nimi ei vastaa bean-ominaisuuden nimeä.

```java
public class HeroRegistration extends App {
  // Sitoutettavat komponentit
  @UseProperty("name")
  TextField nameField = new TextField("Tekstikenttä");
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

Yllä olevassa esimerkissä käyttöliittymän kentän nimi on `nameField`, mutta bean-ominaisuus on `name`. Voit merkitä käyttöliittymän kentän bean-ominaisuuden nimellä varmistaaksesi oikean sitoutumisen.

### `BindingExclude`-annotaatio {#bindingexclude-annotation}

Käytä `BindingExclude`-annotaatiota sulkeaksesi komponentin automaattisista sitoutumisvaatimuksista, kun haluat sitoa sen manuaalisesti tai sulkea sen kokonaan pois.

```java
public class HeroRegistration extends App {
  // Sitoutettavat komponentit
  @UseProperty("name")
  TextField nameField = new TextField("Tekstikenttä");

  @BindingExclude
  ComboBox power = new ComboBox("Voima");

  // ...
}
```

### `UseValidator`-annotaatio {#usevalidator-annotation}

Käytä `UseValidator`-annotaatiota määrittääksesi validaattorit, jotka pakottavat lisävaatimuksia sitoutumisen aikana. Validaattorit sovelletaan siinä järjestyksessä, jossa ne ilmoitat.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Sähköpostiosoite");
}
```

### `UseTransformer`-annotaatio {#usetransformer-annotation}

Käytä `UseTransformer`-annotaatiota määrittääksesi muunnosluokka suoraan käyttöliittymäkentälle. `BindingContext` soveltaa automaattisesti määritettyä muunnosta.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Päivämääräkenttä");
}
```

### `BindingReadOnly`-annotaatio {#bindingreadonly-annotation}

Käytä `BindingReadOnly`-annotaatiota [merkitäksesi sitoutumisen vain luku -tilaksi](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("Käyttäjätunnus");
}
```

### `BindingRequired`-annotaatio {#bindingrequired-annotation}

Käytä `BindingRequired`-annotaatiota merkitäksesi sitoutuminen pakolliseksi. Katso myös [pakollisten sitoutumisten tunnistaminen](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Käyttäjän sähköposti");
}
```

## Datan automaattinen kirjoittaminen {#writing-data-automatically}

Parantaaksesi sovellusten reagointikykyä ja dynaamisuutta voit käyttää `observe`-menetelmää. Tämä menetelmä varmistaa, että käyttöliittymäkomponenteissa tapahtuvat muutokset siirtyvät välittömästi datamalliin. Se on erityisen hyödyllinen, kun tarvitset jatkuvaa synkronointia datamallin ja käyttöliittymän välillä.

`observe`-menetelmä rekisteröi `ValueChangeEvent`-kuuntelijan kaikille sitoutumisille kontekstissa seuraamaan käyttäjän tekemät muutoksia ja kirjoittaa nämä muutokset välittömästi sidottuihin ominaisuuksiin, jos ne ovat voimassa. Kun kutsut tätä menetelmää ensimmäisen kerran, se heijastaa bean-ominaisuudet käyttöliittymäkomponenteissa.

Tässä esimerkki kuinka käyttää `observe`:

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

:::info Päivityssuunnitelma
Tämä automaattinen sitoutuminen on suuntautunutta; päivitykset heijastuvat malliin, kun päivität käyttöliittymäkomponentteja, mutta muutokset mallissa heijastuvat käyttöliittymäkomponentteihin vain kerran, kun kutsut menetelmää ensimmäisen kerran.
:::

:::tip Huomioitavaa
Vaikka `observe` lisää sovellusten interaktiivisuutta, on tärkeää käyttää sitä harkiten:

- **Suorituskykyvaikutus**: Tiheät päivitykset voivat vaikuttaa suorituskykyyn, erityisesti monimutkaisilla malleilla tai hitailla taustapalveluilla.
- **Käyttäjäkokemus**: Automaattiset päivitykset eivät saisi häiritä käyttäjän kykyä syöttää tietoja mukavasti.
:::


## Pakollisten sitoutumisten tunnistaminen {#required-binding-detections}

Kun merkitset sitoutumisen pakolliseksi, se merkitsee komponentin pakolliseksi, edellyttäen että komponentti tukee tätä tilaa `RequiredAware`-rajapinnan kautta. Sitoutuminen ei itse valvo tätä tilaa, vaan asettaa sen komponentille, kun se on sovellettavissa.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

Käyttäessäsi [Jakarta-annotaatioita](./validation/jakarta-validation.md) sitoutuminen voi automaattisesti tunnistaa pakollisen tilan perustuen siihen, onko bean-ominaisuuksilla jokin seuraavista annotaatioista:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
