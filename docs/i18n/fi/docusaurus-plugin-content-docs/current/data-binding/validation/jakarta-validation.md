---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: eec00fac283efce49d856b1d40a48252
---
[Java Bean Validation](https://beanvalidation.org/) on laajalti tunnustettu standardiksi validointilogikan integroimiseksi Java-sovelluksiin. Se hyödyntää yhtenäistä lähestymistapaa validointiin sallimalla kehittäjien merkitä domain-mallin ominaisuuksia deklaratiivisilla validointirajoilla. Näitä rajoja noudatetaan ajonaikana, ja käytettävissä on sekä valmiita että käyttäjän määrittämiä sääntöjä.

webforJ integroituu saumattomasti Bean Validationiin `JakartaValidator`-sovittimen kautta, tarjoten vankkaa tukea suoraan käyttöön.

## Asennus {#installation}

On tarpeen lisätä yhteensopiva toteutus, kuten [Hibernate Validator](https://hibernate.org/validator/), luokkajuureen. Jos ympäristössäsi ei ole tätä toteutusta oletuksena, voit lisätä sen manuaalisesti käyttämällä seuraavia Maven-riippuvuuksia:

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

`JakartaValidator`-luokka toimii sovittimena yhdistäen webforJ-sidontakontekstin Jakarta Validationiin. Tämä integrointi mahdollistaa monimutkaisten validointisääntöjen käytön suoraan annotaatioiden avulla bean-luokassa.

### `JakartaValidator`-aktiivointi {#activating-jakartavalidator}

Voit aktivoida `JakartaValidator`:n koko kontekstissa käyttämällä tyypillisesti `useJakartaValidator`-parametria `BindingContext`:in rakentamisessa.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Rajoitusten määrittäminen bean-ominaisuuksille {#defining-constraints-for-bean-properties}

Annotaatioihin perustuvat rajoitukset sovelletaan suoraan bean-luokassa validointiehtojen määrittämiseksi, kuten seuraavassa esimerkissä:

```java
public class Hero {
  @NotEmpty(message = "Nimi ei voi olla tyhjää")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Määrittelemätön voima")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Virheellinen voima")
  private String power;

  // hakijat ja asettajat
}
```

Tällaiset rajoitukset ovat yhtä tehokkaita kuin ohjelmallisesti määritetyt rajoitukset sidontainitialisoinnin aikana, mikä varmistaa johdonmukaiset validointitulokset.

:::warning
Tällä hetkellä `JakartaValidator` tunnistaa vain suoraan ominaisuuksiin liitetyt rajoitukset ja sivuuttaa kaikki validoinnit, jotka eivät ole suoraan yhteydessä ominaisuuksiin.
:::
