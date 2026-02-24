---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: fa09682ac85db8e2c53ff9eea2d0633e
---
[Java Bean Validation](https://beanvalidation.org/) on laajalti tunnustettu standardi validoimisen logiikan integroimiseksi Java-sovelluksiin. Se käyttää yhtenäistä lähestymistapaa validoimiseen sallimalla kehittäjien merkitä kenttämallin ominaisuudet deklaratiivisilla validoimisen rajoitteilla. Nämä rajoitteet pannaan täytäntöön ajonaikana, ja vaihtoehtoja on sekä valmiiksi määriteltyihin että käyttäjän määrittelemille säännöille.

webforJ integroituu Bean Validationiin `JakartaValidator`-adapterin kautta, mikä mahdollistaa täyden tuen suoraan käyttöönotettavaksi.

## Asennus {#installation}

On tarpeen sisällyttää yhteensopiva toteutus, kuten [Hibernate Validator](https://hibernate.org/validator/), luokkateesiisi. Jos ympäristösi ei sisällä tätä toteutusta oletusarvoisesti, voit lisätä sen manuaalisesti käyttämällä seuraavia Maven-riippuvuuksia:

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

`JakartaValidator`-luokka toimii adapterina, joka yhdistää webforJ-sidontakontekstin Jakarta Validationiin. Tämä integraatio mahdollistaa monimutkaisten validoimissääntöjen käytön suoraan annotaatioiden kautta bean-luokassa.

### `JakartaValidator`-aktivointi {#activating-jakartavalidator}

Aktivoidaksesi `JakartaValidator`-koko kontekstissa käytät tyypillisesti `useJakartaValidator`-parametria `BindingContext`-rakenteessa.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Rajoitteiden määrittäminen bean-ominaisuuksille {#defining-constraints-for-bean-properties}

Annotaatioihin perustuvat rajoitteet sovelletaan suoraan bean-luokassa validoimisen olosuhteiden määrittämiseksi, kuten alla olevassa esimerkissä:

```java
public class Hero {
  @NotEmpty(message = "Nimi ei voi olla tyhjää")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Määrittelemätön voima")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Virheellinen voima")
  private String power;

  // getterit ja setterit
}
```

Tällaiset rajoitteet ovat yhtä tehokkaita kuin ohjelmallisesti asetetut sidornan aloituksessa ja tuottavat johdonmukaisia validoimistuloksia.

:::warning
Tällä hetkellä `JakartaValidator` tunnistaa vain rajoitteet, jotka on suoraan määritetty ominaisuuksille, eikä huomioi voimassaoloja, jotka eivät ole suoraan yhteydessä ominaisuuksiin.
:::

### Lokalisoinnin mukaiset validoimisviestit <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation tukee lokalisoituja rajoitusviestejä standardin mukaisella viestien interpoloinnilla. Kun vaihdat sovelluksen lokalisointia, `JakartaValidator`-luokan on tiedettävä uusi lokalisointi, jotta se voi ratkaista viestit oikein kielellä.

`JakartaValidator` toteuttaa `LocaleAware`-rajapinnan, mikä tarkoittaa, että `BindingContext.setLocale()` levittää automaattisesti lokalisoinnin kaikkiin Jakarta validoijiin kontekstissa. Sinun ei tarvitse päivittää jokaista validoijaa manuaalisesti.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Kun lokalisointi muuttuu, Jakarta validoijat tuottavat automaattisesti
// viestejä uudessa lokalisoinnissa
context.setLocale(Locale.GERMAN);
```

Komponentissa, joka toteuttaa `LocaleObserver`, kutsu `context.setLocale()`-metodia `onLocaleChange()`-sisällä pitääksesi validoimisviestit synkronoituna käyttöliittymän kielen kanssa:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Katso [dynaamiset validoimisviestit](/docs/data-binding/validation/validators#dynamic-validation-messages) lisää tietoa lokalisointiin liittyvistä validoijista.
