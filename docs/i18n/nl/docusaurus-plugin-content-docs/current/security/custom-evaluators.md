---
sidebar_position: 6
title: Custom Evaluators
description: >-
  Write custom RouteSecurityEvaluators for context-aware checks like ownership
  verification beyond role-based permissions.
_i18n_hash: d1edb7260efb2928e988a2cdf313f380
---
Custom evaluators breiden het beveiligingssysteem van webforJ uit met gespecialiseerde toegangscodes die verder gaan dan de basisverificatie en rolchecks. Gebruik ze wanneer je dynamische voorwaarden moet verifiëren die afhankelijk zijn van de context van het verzoek, niet alleen van de gebruikersmachtigingen.

:::info Gericht op Spring Security
Deze gids behandelt aangepaste evaluators voor Spring Security. Als je Spring Boot niet gebruikt, zie dan de [Evaluators Chain-gids](/docs/security/architecture/evaluator-chain) om te begrijpen hoe evaluators werken en [Compleet Implementatie](/docs/security/architecture/custom-implementation) voor een werkend voorbeeld.
:::

## Wat zijn aangepaste evaluators {#what-are-custom-evaluators}

Een evaluator bepaalt of een gebruiker toegang kan krijgen tot een specifieke route op basis van aangepaste logica. Evaluators worden tijdens de navigatie gecontroleerd voordat een component wordt gerenderd, waardoor je toegang dynamisch kunt onderscheppen en regelen.

webforJ bevat ingebouwde evaluators voor standaard Jakarta-annotaties:

- `AnonymousAccessEvaluator` - Behandelt `@AnonymousAccess`
- `PermitAllEvaluator` - Behandelt `@PermitAll`
- `RolesAllowedEvaluator` - Behandelt `@RolesAllowed`
- `DenyAllEvaluator` - Behandelt `@DenyAll`

Aangepaste evaluators volgen hetzelfde patroon, waardoor je je eigen annotaties en toegangscodes kunt maken.

:::tip[Meer leren over ingebouwde annotaties]
Voor meer details over `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` en `@DenyAll`, zie de [Beveiligingsannotaties-gids](/docs/security/annotations).
:::

## Gebruikscase: Eigendom verificatie {#use-case-ownership-verification}

Een veelvoorkomende vereiste is dat gebruikers alleen toegang hebben tot hun eigen middelen. Bijvoorbeeld, gebruikers mogen alleen hun eigen profiel bewerken, niet dat van iemand anders.

**Het probleem**: `@RolesAllowed("USER")` verleent toegang aan alle geauthenticeerde gebruikers, maar verifieert niet of de gebruiker hun eigen middel gebruikt. Je moet de ingelogde gebruikers-ID vergelijken met de middelen-ID in de URL.

**Voorbeeldscenario:**
- Gebruikers-ID `123` is ingelogd
- Ze navigeren naar `/users/456/edit`
- Mogen ze deze pagina openen? **NEE** - ze kunnen alleen `/users/123/edit` bewerken

Je kunt dit niet oplossen met rollen, omdat het afhangt van de routeparameter `:userId`, die voor elk verzoek verandert.

### Een aangepaste annotatie maken {#creating-a-custom-annotation}

Definieer een annotatie om routes te markeren die eigendom verificatie vereisen:

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * De naam van de routeparameter die de gebruikers-ID bevat.
   */
  String value() default "userId";
}
```

Gebruik het op routes die eigendom checks vereisen:

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("Bewerk Profiel Pagina");
  }
}
```

### De evaluator implementeren {#implementing-the-evaluator}

Maak een door Spring beheerde evaluator die de ingelogde gebruikers-ID vergelijkt met de routeparameter:

```java title="OwnershipEvaluator.java"
@RegisteredEvaluator(priority = 10)
public class OwnershipEvaluator implements RouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(RequireOwnership.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {

    // Controleer eerst de authenticatie
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // Haal de annotatie op
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // Haal de ingelogde gebruikers-ID op uit de beveiligingscontext
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // Haal :userId op uit de routeparameters
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Controleer of ze overeenkomen
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Eigendom geverifieerd - ga door met de keten om andere evaluators toe te staan
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Je kunt alleen toegang krijgen tot je eigen middelen");
  }
}
```

Spring ontdekt en registreert automatisch evaluators die zijn gemarkeerd met `@RegisteredEvaluator`.

### Hoe het werkt {#how-it-works}

De implementatie van de evaluator heeft twee belangrijke methoden:

#### `supports(Class<?> routeClass)` {#supports-method}

- Retourneert `true` als deze evaluator de route moet afhandelen
- Alleen evaluators die `true` retourneren zullen voor de route worden aangeroepen
- Filtert routes door te controleren op de annotatie `@RequireOwnership`

#### `evaluate(...)` {#evaluate-method}

- Controleert eerst of de gebruiker geauthenticeerd is
- Haalt de ingelogde gebruikers-ID op uit `securityContext.getPrincipal()`
- Haalt de waarde van de routeparameter op uit `context.getRouteParameters().get(paramName)`
- Vergelijkt de twee ID's
- Als ze overeenkomen, geeft het de controle door aan `chain.evaluate()` om andere evaluators uit te voeren
- Als ze niet overeenkomen, retourneert het `deny()` met een reden

### Stroomschema voorbeeld {#flow-example}

**Wanneer de eigendomcheck faalt:**

1. Gebruiker `123` logt in en navigeert naar `/users/456/edit`
2. `OwnershipEvaluator.supports()` retourneert `true` (route heeft `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` wordt uitgevoerd:
   - `currentUserId = "123"` (uit beveiligingscontext)
   - `requestedUserId = "456"` (uit routeparameter `:userId`)
   - `"123".equals("456")` → `false`
   - Retourneert `RouteAccessDecision.deny("Je kunt alleen toegang krijgen tot je eigen middelen")`
4. Gebruiker wordt doorgestuurd naar de toegang geweigerd pagina

**Wanneer de eigendomcheck slaagt:**

1. Gebruiker `123` logt in en navigeert naar `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` wordt uitgevoerd:
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - ID's komen overeen → roept `chain.evaluate()` aan om door te gaan
3. Als geen andere evaluators toegang weigeren, krijgt de gebruiker toegang

## Begrijpen van de evaluatorketen {#understanding-the-evaluator-chain}

Het beveiligingssysteem gebruikt een **keten van verantwoordelijkheid patroon** waarin evaluators in prioriteitsvolgorde worden verwerkt. Evaluators kunnen ofwel terminale beslissingen nemen of delegeren aan de keten om meerdere controle uit te voeren.

### Hoe de keten werkt {#how-chain-works}

1. Evaluators worden gesorteerd op prioriteit (lagere nummers eerst)
2. Voor elke evaluator wordt `supports(routeClass)` aangeroepen om te controleren of deze van toepassing is
3. Als `supports()` `true` retourneert, wordt de `evaluate()`-methode van de evaluator aangeroepen
4. De evaluator kan:
   - **Een terminale beslissing retourneren** (`grant()` of `deny()`) - **stop de keten**
   - **Delegeer aan de keten** door `chain.evaluate()` aan te roepen - **laat andere evaluators uitvoeren**
5. Als de keten eindigt zonder een beslissing en secure-by-default is ingeschakeld, worden niet-geauthenticeerde gebruikers geweigerd

### Terminale besluiten {#terminal-decisions}

Stop de keten onmiddellijk:

#### `RouteAccessDecision.grant()` {#routeaccessdecisiongrant}

- Verleent toegang en stopt verdere evaluatie
- Gebruikt door `@AnonymousAccess` en `@PermitAll` - dit zijn complete autorisaties die niet combineren met andere controles

#### `RouteAccessDecision.deny(reason)` {#routeaccessdecisiondenyreason}

- Weigert toegang en stopt verdere evaluatie
- Gebruikt door `@DenyAll` en wanneer aangepaste controles falen
- Voorbeeld: `RouteAccessDecision.deny("Je kunt alleen toegang krijgen tot je eigen middelen")`

#### `RouteAccessDecision.denyAuthentication()` {#routeaccessdecisiondenyauthentication}

- Stuur door naar inlogpagina
- Gebruikt wanneer authenticatie vereist is maar ontbreekt

### Ketendelegatie {#chain-delegation}

Maakt het combineren van controles mogelijk:

#### `chain.evaluate(routeClass, context, securityContext)` {#chainevaluaterouteclass-context-securitycontext}

- Geeft de controle door aan de volgende evaluator in de keten
- Maakt het combineren van meerdere autorisatiecontroles mogelijk
- Gebruikt door `@RolesAllowed` en `@RouteAccess` nadat hun controles zijn geslaagd
- Aangepaste evaluators moeten dit patroon gebruiken wanneer controles slagen om samenstelling mogelijk te maken

## Evaluator prioriteit {#evaluator-priority}

Evaluators worden gecontroleerd in prioriteitsvolgorde (lagere nummers eerst). Framework-evaluators gebruiken prioriteiten 1-9, aangepaste evaluators moeten 10 of hoger gebruiken.

Ingebouwde evaluators zijn in deze volgorde geregistreerd:

```java
// Prioriteit 1: @DenyAll - blokkeert alles
// Prioriteit 2: @AnonymousAccess - staat niet-geauthenticeerde toegang toe
// Prioriteit 3: AuthenticationRequiredEvaluator - zorgt voor auth voor @PermitAll/@RolesAllowed
// Prioriteit 4: @PermitAll - vereist alleen authenticatie
// Prioriteit 5: @RolesAllowed - vereist specifieke rollen
// Prioriteit 6: @RouteAccess - SpEL-expressies (alleen Spring Security)
// Prioriteit 10+: Aangepaste evaluators (zoals @RequireOwnership)
```

### Hoe prioriteit de evaluatie beïnvloedt {#priority-affects-evaluation}

- Evaluators met een lagere prioriteit worden eerst uitgevoerd en kunnen de keten "kortsluiten"
- `@DenyAll` (prioriteit 1) wordt als eerste uitgevoerd - als deze aanwezig is, wordt de toegang altijd geweigerd
- `@AnonymousAccess` (prioriteit 2) wordt vervolgens uitgevoerd - als deze aanwezig is, wordt de toegang altijd verleend (zelfs zonder auth)
- `AuthenticationRequiredEvaluator` (prioriteit 3) controleert of de route auth nodig heeft en of de gebruiker geauthenticeerd is
- Als geen enkele evaluator de route afhandelt, geldt de secure-by-default-logica

### Prioriteit instellen {#setting-priority}

Stel de prioriteit in met de annotatie `@RegisteredEvaluator`:

```java
@RegisteredEvaluator(priority = 10)  // Draaft na ingebouwde evaluators
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Prioriteitsbereik
Aangepaste evaluators moeten prioriteit 10 of hoger gebruiken. Prioriteiten 1-9 zijn gereserveerd voor framework-evaluators. Als je een prioriteit in het gereserveerde bereik gebruikt, ontvang je een waarschuwing in de logs.
:::

## Evaluators combineren {#combining-evaluators}

Evaluators die delegeerden aan de keten kunnen worden gecombineerd om complexe autorisatielogica te creëren. Routes kunnen meerdere beveiligingsannotaties hebben:

### Combineren van rolchecks met aangepaste logica {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Moet de USER-rol hebben EN hun eigen instellingen raadplegen
}
```

**Hoe het werkt:**
1. `RolesAllowedEvaluator` (prioriteit 5) controleert of de gebruiker de rol "USER" heeft
2. Als dit het geval is, roept het `chain.evaluate()` aan om door te gaan
3. `OwnershipEvaluator` (prioriteit 10) controleert of `userId` overeenkomt met de ingelogde gebruiker
4. Als dit het geval is, roept het `chain.evaluate()` aan om door te gaan
5. Ketting eindigt → toegang verleend

### Combineren van SpEL-expressies met aangepaste logica {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Moet admin zijn EN hun eigen account raadplegen
}
```

### Wat kan niet worden gecombineerd {#cant-combine}

`@AnonymousAccess` en `@PermitAll` maken **terminal beslissingen** - ze verlenen onmiddellijk toegang zonder de keten aan te roepen. Je kunt ze niet combineren met aangepaste evaluators:

```java
// @PermitAll verleent onmiddellijk toegang, @RequireOwnership wordt nooit uitgevoerd
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Voor middelen waartoe alle geauthenticeerde gebruikers toegang hebben, gebruik je `@RolesAllowed` met een gemeenschappelijke rol in plaats daarvan:

```java
// @RolesAllowed delegeert naar keten
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Moet een geauthenticeerde gebruiker zijn EN hun eigen profiel raadplegen
}
```
