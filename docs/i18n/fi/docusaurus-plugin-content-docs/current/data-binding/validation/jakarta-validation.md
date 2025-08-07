---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: 68a57d576ce21a9f99b121e5db3cf85f
---
[Java Bean Validation](https://beanvalidation.org/) on laajalti tunnustettu standardiksi vahvistuslogiikan integroimiseksi Java-sovelluksiin. Se hyödyntää yhtenäistä lähestymistapaa validoimalla, sallimalla kehittäjien liittää domain-mallin ominaisuuksiin deklaratiivisia vahvistusrajoituksia. Nämä rajoitukset pannaan täytäntöön ajonaikaisesti, ja käytettävissä on sekä sisäänrakennettuja että käyttäjän määrittämiä sääntöjä.

webforJ integroituu saumattomasti Bean Validationiin `JakartaValidator`-sovittimen avulla tarjoten vankkaa tukea suoraan käyttöön.

## Asennus {#installation}

On tarpeen sisällyttää yhteens Sopiva toteutus, kuten [Hibernate Validator](https://hibernate.org/validator/), luokkakehykseesi. Jos ympäristösi ei tule tämän toteutuksen kanssa oletuksena, voit lisätä sen manuaalisesti seuraavien Maven-riippuvuuksien avulla:

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

`JakartaValidator`-luokka toimii sovittimena, joka yhdistää webforJ-sitojan kontekstin Jakarta Validointiin. Tämä integraatio mahdollistaa monimutkaisten vahvistussääntöjen käytön suoraan annotaatioiden kautta bean-luokassa.

### `JakartaValidator` aktivointi {#activating-jakartavalidator}

Aktivoidaksesi `JakartaValidator`-sovittimen koko kontekstissa, käytät tyypillisesti `useJakartaValidator`-parametria `BindingContext`-rakentaessa.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Raajausten määrittäminen bean-ominaisuuksille {#defining-constraints-for-bean-properties}

Annotaatio-pohjaiset rajoitukset sovelletaan suoraan bean-luokassa vahvistusolosuhteiden määrittämiseksi, kuten esimerkissä alla:

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

Tällaiset rajoitukset ovat yhtä tehokkaita kuin ohjelmallisesti asetetut rajoitukset sidonnan aloituksessa, varmistaen johdonmukaiset vahvistustulokset.

:::warning
Tällä hetkellä `JakartaValidator` tunnistaa vain ominaisuuksille suoraan asetetut rajoitukset ja ohittaa kaikki validoinnit, jotka eivät ole suoraan yhteydessä ominaisuuksiin.
:::
