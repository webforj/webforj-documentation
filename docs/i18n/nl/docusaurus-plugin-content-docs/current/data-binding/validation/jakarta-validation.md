---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: 68a57d576ce21a9f99b121e5db3cf85f
---
[Java Bean Validation](https://beanvalidation.org/) is breed erkend als de standaard voor het integreren van validatielogica in Java-toepassingen. Het maakt gebruik van een uniforme aanpak voor validatie door ontwikkelaars in staat te stellen eigenschappen van het domeinmodel te annoteren met declaratieve validatiebeperkingen. Deze beperkingen worden tijdens runtime afgedwongen, met opties voor zowel ingebouwde als door de gebruiker gedefinieerde regels.

webforJ integreert naadloos met Bean Validation via de `JakartaValidator`-adapter, die robuuste ondersteuning biedt direct uit de verpakking.

## Installatie {#installation}

Het is noodzakelijk om een compatibele implementatie, zoals [Hibernate Validator](https://hibernate.org/validator/), aan je classpath toe te voegen. Als je omgeving deze implementatie niet standaard bevat, kun je deze handmatig toevoegen door de volgende Maven-afhankelijkheden te gebruiken:

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

De `JakartaValidator`-klasse fungeert als een adapter, die de webforJ-bindingscontext verbindt met Jakarta Validation. Deze integratie stelt het gebruik van complexe validatieregels mogelijk via annotaties in de bean-klasse.

### Het activeren van `JakartaValidator` {#activating-jakartavalidator}

Om de `JakartaValidator` in de hele context te activeren, gebruik je doorgaans de parameter `useJakartaValidator` bij het construeren van de `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Beperkingen voor bean-eigenschappen definiëren {#defining-constraints-for-bean-properties}

Annotatie-gebaseerde beperkingen worden direct toegepast binnen de bean-klasse om validatievoorwaarden op te geven, zoals geïllustreerd in het onderstaande voorbeeld:

```java
public class Hero {
  @NotEmpty(message = "Naam mag niet leeg zijn")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Onbekende kracht")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Ongeldige kracht")
  private String power;

  // getters en setters
}
```

Dergelijke beperkingen zijn net zo effectief als die programatisch zijn ingesteld tijdens de bindinginitialisatie, wat zorgt voor consistente validatieresultaten.

:::warning
Momenteel herkent de `JakartaValidator` alleen beperkingen die direct aan eigenschappen zijn toegewezen en negeert deze eventuele validaties die niet direct zijn gekoppeld aan eigenschappen.
:::
