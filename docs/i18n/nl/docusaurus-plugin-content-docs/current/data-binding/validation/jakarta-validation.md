---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: fa09682ac85db8e2c53ff9eea2d0633e
---
[Java Bean Validation](https://beanvalidation.org/) wordt breed erkend als de standaard voor het integreren van validatielogica in Java-toepassingen. Het gebruikt een uniforme benadering voor validatie door ontwikkelaars in staat te stellen om domeinmodel-eigenschappen te annoteren met declaratieve validatiebeperkingen. Deze beperkingen worden tijdens runtime afgedwongen, met opties voor zowel ingebouwde als door de gebruiker gedefinieerde regels.

webforJ integreert met Bean Validation via de `JakartaValidator`-adapter, die volledige ondersteuning biedt vanuit de doos.

## Installatie {#installation}

Het is noodzakelijk om een compatibele implementatie, zoals [Hibernate Validator](https://hibernate.org/validator/), in je classpath op te nemen. Als je omgeving deze implementatie niet standaard bevat, kun je deze handmatig toevoegen door de volgende Maven-afhankelijkheden te gebruiken:

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

De `JakartaValidator`-klasse fungeert als een adapter die de webforJ-bindingscontext verbindt met Jakarta Validation. Deze integratie maakt het mogelijk om complexe validatieregels direct via annotaties in de beanklasse te gebruiken.

### `JakartaValidator` activeren {#activating-jakartavalidator}

Om de `JakartaValidator` in de hele context te activeren, gebruik je meestal de parameter `useJakartaValidator` bij het construeren van de `BindingContext`.

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

  @NotEmpty(message = "Ongekwalificeerde kracht")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Ongeldige kracht")
  private String power;

  // getters en setters
}
```

Dergelijke beperkingen zijn even effectief als die progammatisch zijn ingesteld tijdens de binding-initialisatie en produceren consistente validatieresultaten.

:::warning
Momenteel herkent de `JakartaValidator` alleen beperkingen die direct aan eigenschappen zijn toegewezen en negeert elk validatie die niet direct aan eigenschappen is gekoppeld.
:::

### Locatiebewuste validatieberichten <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation ondersteunt gelokaliseerde beperkingberichten via standaard berichtinterpolatie. Wanneer je de app-locatie wijzigt, moet de `JakartaValidator` op de hoogte worden gesteld van de nieuwe locatie, zodat deze berichten in de juiste taal kan oplossen.

`JakartaValidator` implementeert de `LocaleAware`-interface, wat betekent dat `BindingContext.setLocale()` automatisch de locatie doorgeeft aan alle Jakarta-validators in de context. Je hoeft niet elke validator handmatig bij te werken.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Wanneer de locatie verandert, produceren Jakarta-validators automatisch
// berichten in de nieuwe locatie
context.setLocale(Locale.GERMAN);
```

In een component die `LocaleObserver` implementeert, roep je `context.setLocale()` aan binnen `onLocaleChange()` om validatieberichten in sync te houden met de UI-taal:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Zie [dynamische validatieberichten](/docs/data-binding/validation/validators#dynamic-validation-messages) voor meer informatie over locatiebewuste validators.
