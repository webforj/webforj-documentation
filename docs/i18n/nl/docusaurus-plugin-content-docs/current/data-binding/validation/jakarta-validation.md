---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: eec00fac283efce49d856b1d40a48252
---
[Java Bean Validation](https://beanvalidation.org/) wordt algemeen erkend als de standaard voor het integreren van validatielogica in Java-toepassingen. Het hanteert een uniforme benadering van validatie door ontwikkelaars in staat te stellen domeinmodel eigenschappen te annoteren met declaratieve validatiebeperkingen. Deze beperkingen worden tijdens runtime gehandhaafd, met opties voor zowel ingebouwde als op maat gedefinieerde regels.

webforJ integreert naadloos met Bean Validation via de `JakartaValidator` adapter, die robuuste ondersteuning biedt vanaf het begin.

## Installatie {#installation}

Het is noodzakelijk om een compatibele implementatie, zoals [Hibernate Validator](https://hibernate.org/validator/), in je classpath op te nemen. Als je omgeving deze implementatie niet standaard bevat, kun je deze handmatig toevoegen met de volgende Maven-afhankelijkheden:

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

## De `JakartaValidator` {#the-jakartavalidator}

De `JakartaValidator` klasse fungeert als een adapter, die de webforJ binding context verbindt met Jakarta Validation. Deze integratie stelt het gebruik van complexe validatieregels mogelijk via annotaties in de beanklasse.

### `JakartaValidator` activeren {#activating-jakartavalidator}

Om de `JakartaValidator` in de hele context te activeren, gebruik je doorgaans de `useJakartaValidator` parameter bij het construeren van de `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Beperkingen definiëren voor bean-eigenschappen {#defining-constraints-for-bean-properties}

Annotatie-gebaseerde beperkingen worden direct toegepast binnen de beanklasse om validatievoorwaarden op te geven, zoals geïllustreerd in het onderstaande voorbeeld:

```java
public class Hero {
  @NotEmpty(message = "Naam mag niet leeg zijn")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Onspecificeerde kracht")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Ongeldige kracht")
  private String power;

  // getters en setters
}
```

Dergelijke beperkingen zijn net zo effectief als die programmatic tijdens de binding-initialisatie zijn ingesteld, wat zorgt voor consistente validatieresultaten.

:::warning
Momenteel herkent de `JakartaValidator` alleen beperkingen die rechtstreeks zijn toegewezen aan eigenschappen en negeert deze validaties die niet direct aan eigenschappen zijn gekoppeld.
:::
