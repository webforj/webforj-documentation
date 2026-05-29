---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: 813ccefe385954366010291f50215611
---
[Java Bean Validation](https://beanvalidation.org/) wordt algemeen erkend als de standaard voor het integreren van validatielogica in Java-toepassingen. Het gebruikt een uniforme aanpak voor validatie door ontwikkelaars in staat te stellen eigenschapen van domeinmodellen te annoteren met declaratieve validatiebeperkingen. Deze beperkingen worden tijdens runtime afgedwongen, met opties voor zowel ingebouwde als zelf gedefinieerde regels.

webforJ integreert met Bean Validation via de `JakartaValidator` adapter, die volledige ondersteuning biedt vanaf de eerste configuratie.

## Installatie {#installation}

Het is noodzakelijk om een compatibele implementatie, zoals [Hibernate Validator](https://hibernate.org/validator/), in je classpath op te nemen. Als je omgeving deze implementatie niet standaard bevat, kun je deze handmatig toevoegen met behulp van de volgende Maven-afhankelijkheden:

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

De `JakartaValidator` klasse dient als een adapter, die de webforJ binding context verbindt met Jakarta Validation. Deze integratie maakt het mogelijk om complexe validatieregels direct via annotaties in de bean-klasse te gebruiken.

### Activeren van `JakartaValidator` {#activating-jakartavalidator}

Om de `JakartaValidator` in de gehele context te activeren, gebruik je doorgaans de `useJakartaValidator` parameter bij het construeren van de `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Beperkingen definiëren voor bean-eigenschappen {#defining-constraints-for-bean-properties}

Annotatie-gebaseerde beperkingen worden direct toegepast binnen de bean-klasse om validatievoorwaarden op te geven, zoals geïllustreerd in het onderstaande voorbeeld:

```java
public class Hero {
  @NotEmpty(message = "Naam mag niet leeg zijn")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Onopgegeven kracht")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Ongeldige kracht")
  private String power;

  // getters en setters
}
```

Dergelijke beperkingen zijn even effectief als die welke programmatisch tijdens de binding-initialisatie zijn ingesteld en produceren consistente validatie-uitkomsten.

:::warning
Momenteel herkent de `JakartaValidator` alleen beperkingen die direct aan eigenschappen zijn toegewezen en negeert alle validaties die niet direct zijn gekoppeld aan eigenschappen.
:::

### Lokale-validatieberichten <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation ondersteunt gelokaliseerde beperkingberichten via standaard berichtinterpolatie. Wanneer je de app-locale wijzigt, moet de `JakartaValidator` weten wat de nieuwe locale is, zodat hij berichten in de juiste taal kan oplossen.

`JakartaValidator` implementeert de `LocaleAware` interface, wat betekent dat `BindingContext.setLocale()` automatisch de locale naar alle Jakarta-validators in de context doorgeeft. Je hoeft elke validator niet handmatig bij te werken.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Wanneer de locale verandert, produceren Jakarta validators automatisch
// berichten in de nieuwe locale
context.setLocale(Locale.GERMAN);
```

In een component die `LocaleObserver` implementeert, roep je `context.setLocale()` aan binnen `onLocaleChange()` om validatieberichten in sync te houden met de UI-taal:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Zie [dynamische validatieberichten](/docs/data-binding/validation/validators#dynamic-validation-messages) voor meer informatie over locale-bewuste validators.
