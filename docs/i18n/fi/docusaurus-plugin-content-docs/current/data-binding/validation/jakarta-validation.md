---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
description: >-
  Apply Jakarta Bean Validation annotations to bean properties and activate
  JakartaValidator on a BindingContext with locale-aware messages.
_i18n_hash: e5b90cd31ee5ca5eab453a1c087967da
---
[Java Bean Validation](https://beanvalidation.org/) on laajalti tunnustettu standardi validointilogiikan integroimiseksi Java-sovelluksiin. Se käyttää yhtenäistä lähestymistapaa validointiin sallimalla kehittäjien merkitä domain-mallin ominaisuudet deklaratiivisilla validointirajoitteilla. Näitä rajoitteita valvotaan ajonaikaisesti, ja vaihtoehtoja on sekä sisäänrakennettuihin että käyttäjän määrittämiin sääntöihin.

webforJ integroituu Bean Validationin kanssa `JakartaValidator`-adapterin kautta tarjoten täydellisen tuen suoraan käyttöön.

## Asennus {#installation}

On tarpeen sisällyttää yhteensopiva toteutus, kuten [Hibernate Validator](https://hibernate.org/validator/), luokkasi polkuun. Jos ympäristösi ei tule tämän toteutuksen kanssa oletuksena, voit lisätä sen manuaalisesti käyttämällä seuraavia Maven-riippuvuuksia:

```xml
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>8.0.1.Final</version>
</dependency>
<dependency>
  <groupId>org.glassfish.expressly</groupId>
  <artifactId>expressly</artifactId>
  <version>5.0.0</version>
</dependency>
```

## `JakartaValidator` {#the-jakartavalidator}

`JakartaValidator`-luokka toimii adapterina, joka yhdistää webforJ-sidontayhteyden Jakarta Validointiin. Tämä integraatio mahdollistaa monimutkaisten validointisääntöjen käytön suoraan annotaatioiden kautta bean-luokassa.

### `JakartaValidator`-aktiivointi {#activating-jakartavalidator}

Aktivoidaksesi `JakartaValidator` koko kontekstissa, käytät yleensä `useJakartaValidator`-parametria rakentaessasi `BindingContext`-objektia.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Rajoitteiden määrittäminen bean-ominaisuuksille {#defining-constraints-for-bean-properties}

Annotaatiorajoitteet sovelletaan suoraan bean-luokassa määrittämään validointiehdot, kuten on kuvattu alla olevassa esimerkissä:

```java
public class Hero {
  @NotEmpty(message = "Nimi ei voi olla tyhjää")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Määräämätön voima")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Virheellinen voima")
  private String power;

  // getters and setters
}
```

Tällaiset rajoitteet ovat yhtä tehokkaita kuin ohjelmallisesti asetetut sidonnan aloituksessa ja tuottavat johdonmukaisia validointituloksia.

:::warning
Tällä hetkellä `JakartaValidator` tunnistaa vain rajoitteet, jotka on suoraan määritelty ominaisuuksille, ja se ohittaa kaikki validoinnit, jotka eivät ole suoraan liitettyjä ominaisuuksiin.
:::

### Sisäisten beanien validointi <DocChip chip='since' label='26.01' /> {#validating-nested-beans}

Määritä rajoitteet suoraan sisäisen beanin omille kentille. Kun sitoudut yhteen näistä kentistä [pisteomaisuuden polun](/docs/data-binding/bindings#nested-bean-properties) kautta, kyseisen ominaisuuden rajoite soveltuu sitoumukseen samalla tavalla kuin päättötason ominaisuuden rajoite.

```java
public class Address {
  @NotBlank(message = "Katuosoite on pakollinen")
  @Size(max = 80, message = "Katuosoite on liian pitkä")
  private String street;

  // getters and setters
}
```

```java {6-7}
public class Hero {
  @NotEmpty(message = "Nimi ei voi olla tyhjää")
  @Length(min = 3, max = 20)
  private String name;

  // Sisäinen bean, jossa on rajoitteet address.streetille
  private Address address;

  // getters and setters
}
```

Sitominen `address.street` validoi `@NotBlank`-rajoitetta `Address.street`:lle. Jokainen sitominen validoi ominaisuuden sen polun lopussa.

[Nested beans -esimerkki](https://github.com/webforj/built-with-webforj) sitoo `Employee`-beanin sisäisten `Address`- ja `EmergencyContact`-beanien kautta yhden `BindingContext`-objektin käyttäen tätä kaavaa.

### Paikalliselle huomiota herättävät validointiviestit <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation tukee lokalisoituja rajoiteviestejä standardin viestin interpoloinnin kautta. Kun vaihdat sovelluksen lokalisointia, `JakartaValidator` tarvitsee tietää uusi lokalisointi, jotta se voi ratkaista viestit oikealla kielellä.

`JakartaValidator` toteuttaa `LocaleAware`-rajapinnan, mikä tarkoittaa, että `BindingContext.setLocale()` siirtää automaattisesti lokalisoinnin kaikkiin Jakarta-validointimalleihin kontekstissa. Sinun ei tarvitse päivittää jokaista validointimallia manuaalisesti.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Kun lokalisointi muuttuu, Jakarta-validoinnit automaattisesti
// tuottavat viestejä uudessa lokalisoinnissa
context.setLocale(Locale.GERMAN);
```

Komponentissa, joka toteuttaa `LocaleObserver`, kutsu `context.setLocale()` sisällä `onLocaleChange()`-metodissa pitämään validointiviestit synkronoituina käyttöliittymän kielen kanssa:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Katso [dynaamiset validointiviestit](/docs/data-binding/validation/validators#dynamic-validation-messages) lisätietoja lokalisoiduista validoijista.
