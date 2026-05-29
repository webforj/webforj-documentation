---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: 813ccefe385954366010291f50215611
---
[Java Bean Validation](https://beanvalidation.org/) on laajalti tunnettu standardina, joka integroi validoimislogiikan Java-sovelluksiin. Se käyttää yhtenäistä lähestymistapaa validointiin sallimalla kehittäjien merkitä domainmallin ominaisuudet deklaratiivisilla validointirajoitteilla. Nämä rajoitteet pakotetaan ajonaikaisesti, ja käytössä on sekä valmiita että käyttäjän määrittämiä sääntöjä.

webforJ integroituu Bean Validationiin `JakartaValidator`-sovittimen kautta, tarjoten täyden tuen suoraan käyttöön.

## Asennus {#installation}

On tarpeen sisällyttää yhteensopiva toteutus, kuten [Hibernate Validator](https://hibernate.org/validator/), luokkahakemistoon. Jos ympäristössäsi ei ole tätä toteutusta oletuksena, voit lisätä sen manuaalisesti seuraavien Maven-riippuvuuksien avulla:

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

`JakartaValidator`-luokka toimii sovittimena, yhdistäen webforJ:n sidontakontekstin Jakarta Validointiin. Tämä integraatio mahdollistaa monimutkaisten validointisääntöjen käytön suoraan anotaatioiden kautta bean-luokassa.

### `JakartaValidator`-aktivointi {#activating-jakartavalidator}

Aktivoidaksesi `JakartaValidator`in koko kontekstissa, käytät yleensä `useJakartaValidator`-parametria rakentaessasi `BindingContext`ia.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Rajoitteiden määrittäminen bean-ominaisuuksille {#defining-constraints-for-bean-properties}

Anotaatiopohjaiset rajoitteet sovelletaan suoraan bean-luokassa validoimisolojen määrittämiseksi, kuten alla olevassa esimerkissä on havainnollistettu:

```java
public class Hero {
  @NotEmpty(message = "Nimi ei voi olla tyhjää")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Määrittelemätön voima")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Virheellinen voima")
  private String power;

  // getters and setters
}
```

Tällaiset rajoitteet ovat yhtä tehokkaita kuin ohjelmallisesti sidontavaiheessa asetetut, ja tuottavat johdonmukaisia validointituloksia.

:::warning
Tällä hetkellä `JakartaValidator` tunnistaa vain rajoitteet, jotka on suoraan määritetty ominaisuuksille, ja se ohittaa kaikki validoinnit, jotka eivät ole suoraan liittyneet ominaisuuksiin.
:::

### Paikalliset validointiviestit <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation tukee lokalisoituja rajoitetiedotteita standardin mukaisen viestin interpoloinnin kautta. Kun vaihdat sovelluksen kieltä, `JakartaValidator`in on tiedettävä uusi kieli, jotta se voi ratkaista viestit oikealla kielellä.

`JakartaValidator` toteuttaa `LocaleAware`-rajapinnan, mikä tarkoittaa, että `BindingContext.setLocale()` välittää automaattisesti kieliasetuksen kaikille kontekstin Jakarta-validointijoukoille. Sinun ei tarvitse päivittää jokaista validaattoria manuaalisesti.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Kun kieli muuttuu, Jakarta-validointijoukot automaattisesti
// tuottavat viestejä uudessa kielessä
context.setLocale(Locale.GERMAN);
```

Komponentissa, joka toteuttaa `LocaleObserver`, kutsu `context.setLocale()` sisällä `onLocaleChange()`:ssa pitämään validointiviestit synkronoituina käyttöliittymän kielen kanssa:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Katso [dynaamiset validointiviestit](/docs/data-binding/validation/validators#dynamic-validation-messages) saadaksesi lisätietoja paikallista huomioivia validaattoreita.
