---
sidebar_position: 3
title: Defining Routes
_i18n_hash: 6d7133c5636f63b82b13dd0a07a97620
---
Routes definiëren is essentieel voor het koppelen van URL's aan specifieke componenten. Dit stelt je in staat om te controleren hoe verschillende delen van je gebruikersinterface worden weergegeven op basis van de URL-structuur. Het framework gebruikt de `@Route` annotatie om dit proces declaratief en eenvoudig te maken, waardoor de behoefte aan handmatige configuratie wordt verminderd.

:::info Routes Registratie
Routes kunnen statisch worden geregistreerd met behulp van de `@Route` annotatie of dynamisch via de `RouteRegistry` API. Voor meer informatie, raadpleeg de [Routes Registratie documentatie](./routes-registration).
:::

## Een route definiëren met `@Route` {#defining-a-route-with-route}

De `@Route` annotatie wordt gebruikt om een URL-pad aan een specifieke component te binden. Dit stelt de component in staat om te worden weergegeven telkens wanneer de app naar die URL navigeert. Hier is een eenvoudig voorbeeld:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Component logica hier
}
```

In dit voorbeeld:
- De `DashboardView` component is gebonden aan de `/dashboard` URL.
- Wanneer een gebruiker navigeert naar `/dashboard`, zal de `DashboardView` dynamisch worden weergegeven door het framework.

### De `value` parameter {#the-value-parameter}

De `value` parameter in de `@Route` annotatie definieert het URL-pad. Dit kan een statisch pad zijn zoals `"dashboard"` of dynamischer, waardoor flexibele routing mogelijk is.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Component logica hier
}
```

In dit geval zal navigeren naar `/user/123` de `UserView` weergeven.

:::tip Route Patronen
De `user/:id` staat bekend als een routepatroon. De router kan zowel eenvoudige patronen verwerken, die een enkele statische segment matchen, als complexe patronen, die meerdere statische, vereiste en optionele segmenten kunnen matchen. Voor meer informatie over het configureren van patronen, raadpleeg de [diepe duik in route patronen](./route-patterns).
:::

## Route-aliases definiëren {#defining-route-aliases}

In sommige gevallen wil je misschien meerdere URL's toestaan die naar dezelfde component wijzen. Bijvoorbeeld, je zou willen dat gebruikers toegang hebben tot hun profiel via zowel `/profile` als `/user/me`. webforJ maakt dit mogelijk via de **`@RouteAlias`** annotatie, waarmee je meerdere aliassen voor een enkele route kunt definiëren.

Hier is een voorbeeld waarin de component toegankelijk is via zowel `/profile` als `/user/me`:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Component logica hier
}
```

Route-aliases definiëren vergroot de flexibiliteit in je navigatieontwerp, waardoor gebruikers dezelfde inhoud via verschillende URL's kunnen bereiken.
