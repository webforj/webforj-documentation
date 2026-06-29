---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
description: >-
  Apply Jakarta Bean Validation annotations to bean properties and activate
  JakartaValidator on a BindingContext with locale-aware messages.
_i18n_hash: e5b90cd31ee5ca5eab453a1c087967da
---
[Java Bean Validation](https://beanvalidation.org/) wordt algemeen erkend als de standaard voor het integreren van validatielogica in Java-toepassingen. Het gebruikt een uniforme benadering voor validatie door ontwikkelaars in staat te stellen domeinmodel-eigenschappen te annoteren met declaratieve validatieconstraints. Deze constraints worden tijdens runtime gehandhaafd, met opties voor zowel ingebouwde als op maat gedefinieerde regels.

webforJ integreert met Bean Validation via de `JakartaValidator`-adapter, die volledige ondersteuning biedt direct uit de doos.

## Installatie {#installation}

Het is noodzakelijk om een compatibele implementatie, zoals [Hibernate Validator](https://hibernate.org/validator/), op te nemen in je classpath. Als je omgeving deze implementatie niet standaard meelevert, kun je deze handmatig toevoegen met de volgende Maven-afhankelijkheden:

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

De `JakartaValidator`-klasse fungeert als een adapter die de webforJ-bindingscontext verbindt met Jakarta Validation. Deze integratie maakt het mogelijk om complexe validatieregels direct via annotaties in de bean-klasse te gebruiken.

### Het activeren van `JakartaValidator` {#activating-jakartavalidator}

Om de `JakartaValidator` in de hele context te activeren, gebruik je doorgaans de `useJakartaValidator`-parameter bij het construeren van de `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Constraints definiëren voor bean-eigenschappen {#defining-constraints-for-bean-properties}

Annotatie-gebaseerde constraints worden direct toegepast binnen de bean-klasse om validatievoorwaarden op te geven, zoals geïllustreerd in het onderstaande voorbeeld:

```java
public class Hero {
  @NotEmpty(message = "Naam mag niet leeg zijn")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Onbekende kracht")
  @Pattern(regexp = "Vliegen|Onzichtbaar|LaserVisie|Snelheid|Teleportatie", message = "Ongeldige kracht")
  private String power;

  // getters en setters
}
```

Dergelijke constraints zijn even effectief als diegene die programmatisch worden ingesteld tijdens de binding-initialisatie en zorgen voor consistente validatieresultaten.

:::warning
Momenteel herkent de `JakartaValidator` alleen constraints die direct aan eigenschappen zijn toegewezen en negeert eventuele validaties die niet direct aan eigenschappen zijn gekoppeld.
:::

### Geneste beans valideren <DocChip chip='since' label='26.01' /> {#validating-nested-beans}

Definieer constraints direct op de eigen velden van de geneste bean. Wanneer je een van die velden bindt via een [gestippeld eigenschaps pad](/docs/data-binding/bindings#nested-bean-properties), geldt de constraint op die eigenschap voor de binding op dezelfde manier als voor een top-level eigenschap.

```java
public class Address {
  @NotBlank(message = "Straat is verplicht")
  @Size(max = 80, message = "Straat is te lang")
  private String street;

  // getters en setters
}
```

```java {6-7}
public class Hero {
  @NotEmpty(message = "Naam mag niet leeg zijn")
  @Length(min = 3, max = 20)
  private String name;

  // Een geneste bean met de constraints voor address.street
  private Address address;

  // getters en setters
}
```

Binding `address.street` valideert tegen `@NotBlank` op `Address.street`. Elke binding valideert de eigenschap aan het einde van zijn pad.

Het [voorbeeld van geneste beans](https://github.com/webforj/built-with-webforj) bindt een `Employee` met geneste `Address` en `EmergencyContact` beans via een enkele `BindingContext` met dit patroon.

### Locale-bewuste validatienotities <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation ondersteunt gelokaliseerde constraint-notities via standaard berichtinterpolatie. Wanneer je de app-locale verandert, moet de `JakartaValidator` op de hoogte worden gesteld van de nieuwe locale, zodat het berichten in de juiste taal kan resolveren.

`JakartaValidator` implementeert de `LocaleAware` interface, wat betekent dat `BindingContext.setLocale()` automatisch de locale doorgeeft aan alle Jakarta-validators in de context. Je hoeft elke validator niet handmatig bij te werken.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Wanneer de locale verandert, produceren Jakarta validators automatisch
// berichten in de nieuwe locale
context.setLocale(Locale.GERMAN);
```

In een component die de `LocaleObserver` implementeert, roep je `context.setLocale()` aan binnen `onLocaleChange()` om de validatienotities in sync te houden met de UI-taal:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Zie [dynamische validatienotities](/docs/data-binding/validation/validators#dynamic-validation-messages) voor meer informatie over locale-bewuste validators.
