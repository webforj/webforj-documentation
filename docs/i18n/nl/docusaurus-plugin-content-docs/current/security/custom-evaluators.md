---
sidebar_position: 6
title: Custom Evaluators
_i18n_hash: 9b448cdd74811b257b78cc6c9f04e7c2
---
Custom evaluators breiden het beveiligingssysteem van webforJ uit met gespecialiseerde toegangscontrolelogica die verder gaat dan basisauthenticatie en rolcontroles. Gebruik ze wanneer je dynamische voorwaarden moet verifiëren die afhankelijk zijn van de context van het verzoek en niet alleen van gebruikersrechten.

:::info Spring Security gericht
Deze gids behandelt aangepaste evaluators voor Spring Security. Als je geen Spring Boot gebruikt, zie dan de [Evaluator Chain-gids](/docs/security/architecture/evaluator-chain) om te begrijpen hoe evaluators werken en [Complete Implementatie](/docs/security/architecture/custom-implementation) voor een werkend voorbeeld.
:::

## Wat zijn aangepaste evaluators {#what-are-custom-evaluators}

Een evaluator bepaalt of een gebruiker toegang kan krijgen tot een specifieke route op basis van aangepaste logica. Evaluators worden tijdens de navigatie gecontroleerd voordat een component wordt weergegeven, zodat je de toegang dynamisch kunt onderscheppen en controleren.

webforJ bevat ingebouwde evaluators voor standaard Jakarta-annotaties:

- `AnonymousAccessEvaluator` - Behandelt `@AnonymousAccess`
- `PermitAllEvaluator` - Behandelt `@PermitAll`
- `RolesAllowedEvaluator` - Behandelt `@RolesAllowed`
- `DenyAllEvaluator` - Behandelt `@DenyAll`

Aangepaste evaluators volgen hetzelfde patroon, zodat je je eigen annotaties en toegangscontrolelogica kunt maken.

:::tip[Leer meer over ingebouwde annotaties]
Voor details over `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` en `@DenyAll`, zie de [Beveiligingsannotaties-gids](/docs/security/annotations).
:::

## Gebruikscase: Eigenaarschap verificatie {#use-case-ownership-verification}

Een veelvoorkomend vereiste is om gebruikers alleen toegang te geven tot hun eigen bronnen. Gebruikers mogen bijvoorbeeld alleen hun eigen profiel bewerken, niet dat van iemand anders.

**Het probleem**: `@RolesAllowed("USER")` verleent toegang aan alle geverifieerde gebruikers, maar controleert niet of de gebruiker toegang heeft tot zijn eigen bron. Je moet de ID van de ingelogde gebruiker vergelijken met de bron-ID in de URL.

**Voorbeeldscenario:**
- Gebruiker ID `123` is ingelogd
- Ze navigeren naar `/users/456/edit`
- Moeten ze deze pagina mogen openen? **NEE** - ze kunnen alleen `/users/123/edit` bewerken

Je kunt dit niet oplossen met rollen omdat het afhankelijk is van de routeparameter `:userId`, die voor elke aanvraag verandert.

### Een aangepaste annotatie maken {#creating-a-custom-annotation}

Definieer een annotatie om routes te markeren die eigenaarschap verificatie vereisen:

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

Gebruik het op routes die eigenaarschapscontroles vereisen:

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

Maak een Spring-beheerde evaluator die de ingelogde gebruikers-ID vergelijkt met de routeparameter:

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
      // Eigenaarschap geverifieerd - ga door met de keten om andere evaluators toe te staan
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Je kunt alleen toegang krijgen tot je eigen bronnen");
  }
}
```

Spring ontdekt en registreert automatisch evaluators die zijn geannoteerd met `@RegisteredEvaluator`.

### Hoe het werkt {#how-it-works}

De implementatie van de evaluator heeft twee belangrijke methoden:

#### `supports(Class<?> routeClass)` {#supports-method}

- Retourneert `true` als deze evaluator de route moet behandelen
- Alleen evaluators die `true` retourneren, worden voor de route aangeroepen
- Filtert routes door te controleren op de annotatie `@RequireOwnership`

#### `evaluate(...)` {#evaluate-method}

- Controleert eerst of de gebruiker geverifieerd is
- Haalt de ingelogde gebruikers-ID op uit `securityContext.getPrincipal()`
- Haalt de waarde van de routeparameter op uit `context.getRouteParameters().get(paramName)`
- Vergelijkt de twee IDs
- Als ze overeenkomen, geeft hij de controle door aan `chain.evaluate()` om andere evaluators uit te laten voeren
- Als ze niet overeenkomen, retourneert hij `deny()` met een reden

### Voorbeeld flow {#flow-example}

**Wanneer de eigenaarschapscontrole faalt:**

1. Gebruiker `123` logt in en navigeert naar `/users/456/edit`
2. `OwnershipEvaluator.supports()` retourneert `true` (route heeft `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` wordt uitgevoerd:
   - `currentUserId = "123"` (uit de beveiligingscontext)
   - `requestedUserId = "456"` (uit de routeparameter `:userId`)
   - `"123".equals("456")` → `false`
   - Retourneert `RouteAccessDecision.deny("Je kunt alleen toegang krijgen tot je eigen bronnen")`
4. Gebruiker wordt doorgestuurd naar de pagina met toegang geweigerd

**Wanneer de eigenaarschapscontrole slaagt:**

1. Gebruiker `123` logt in en navigeert naar `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` wordt uitgevoerd:
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - IDs komen overeen → roept `chain.evaluate()` aan om door te gaan
3. Als geen andere evaluators de toegang weigeren, krijgt de gebruiker toegang

## De evaluatorketen begrijpen {#understanding-the-evaluator-chain}

Het beveiligingssysteem maakt gebruik van een **keten van verantwoordelijkheidspatroon** waarbij evaluators in volgorde van prioriteit worden verwerkt. Evaluators kunnen terminale beslissingen nemen of de keten delegeren voor het combineren van meerdere controles.

### Hoe de keten werkt {#how-chain-works}

1. Evaluators worden gesorteerd op prioriteit (lagere nummers eerst)
2. Voor elke evaluator wordt `supports(routeClass)` aangeroepen om te controleren of deze van toepassing is
3. Als `supports()` `true` retourneert, wordt de methode `evaluate()` van de evaluator aangeroepen
4. De evaluator kan ofwel:
   - **Een terminale beslissing retourneren** (`grant()` of `deny()`) - **stop de keten**
   - **Delegeren aan de keten** door `chain.evaluate()` aan te roepen - **maakt het mogelijk dat andere evaluators draaien**
5. Als de keten voltooid is zonder een beslissing en secure-by-default is ingeschakeld, worden niet-geverifieerde gebruikers geweigerd

### Terminale beslissingen {#terminal-decisions}

Stop de keten onmiddellijk:

#### `RouteAccessDecision.grant()`

- Verleent toegang en stopt verdere evaluatie
- Gebruikt door `@AnonymousAccess` en `@PermitAll` - dit zijn complete autorisaties die niet combineren met andere controles

#### `RouteAccessDecision.deny(reason)`

- Weigert toegang en stopt verdere evaluatie
- Gebruikt door `@DenyAll` en wanneer aangepaste controles falen
- Voorbeeld: `RouteAccessDecision.deny("Je kunt alleen toegang krijgen tot je eigen bronnen")`

#### `RouteAccessDecision.denyAuthentication()`

- Stuur door naar de aanmeldpagina
- Gebruikt wanneer authenticatie vereist is maar ontbreekt

### Ketende delegatie {#chain-delegation}

Maakt het combineren van controles mogelijk:

#### `chain.evaluate(routeClass, context, securityContext)`

- Geeft de controle door aan de volgende evaluator in de keten
- Maakt het mogelijk om meerdere autorisatiecontroles te combineren
- Gebruikt door `@RolesAllowed` en `@RouteAccess` nadat hun controles zijn doorgegeven
- Aangepaste evaluators moeten dit patroon gebruiken wanneer controles slagen om samenstelling mogelijk te maken

## Evaluator prioriteit {#evaluator-priority}

Evaluators worden in volgorde van prioriteit gecontroleerd (lagere nummers eerst). Framework evaluators gebruiken prioriteit 1-9, aangepaste evaluators moeten 10 of hoger gebruiken.

Ingebouwde evaluators worden in deze volgorde geregistreerd:

```java
// Prioriteit 1: @DenyAll - blokkeert alles
// Prioriteit 2: @AnonymousAccess - staat niet-geverifieerde toegang toe
// Prioriteit 3: AuthenticationRequiredEvaluator - zorgt voor auth voor @PermitAll/@RolesAllowed
// Prioriteit 4: @PermitAll - vereist alleen authenticatie
// Prioriteit 5: @RolesAllowed - vereist specifieke rollen
// Prioriteit 6: @RouteAccess - SpEL-expressies (alleen Spring Security)
// Prioriteit 10+: Aangepaste evaluators (zoals @RequireOwnership)
```

### Hoe prioriteit evaluatie beïnvloedt {#priority-affects-evaluation}

- Evaluators met een lagere prioriteit draaien eerst en kunnen de keten "opschorten"
- `@DenyAll` (prioriteit 1) draait als eerste - als deze aanwezig is, wordt toegang altijd geweigerd
- `@AnonymousAccess` (prioriteit 2) draait daarna - als deze aanwezig is, wordt toegang altijd verleend (zelfs zonder auth)
- `AuthenticationRequiredEvaluator` (prioriteit 3) controleert of de route auth nodig heeft en of de gebruiker geverifieerd is
- Als geen enkele evaluator de route behandelt, wordt de secure-by-default-logica toegepast

### Prioriteit instellen {#setting-priority}

Stel prioriteit in met de annotatie `@RegisteredEvaluator`:

```java
@RegisteredEvaluator(priority = 10)  // Draait na ingebouwde evaluators
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Prioriteitsbereik
Aangepaste evaluators moeten prioriteit 10 of hoger gebruiken. Prioriteiten 1-9 zijn gereserveerd voor framework evaluators. Als je een prioriteit in het gereserveerde bereik gebruikt, ontvang je een waarschuwing in de logs.
:::

## Evaluators combineren {#combining-evaluators}

Evaluators die delegeren aan de keten kunnen worden gecombineerd om complexe autorisatielogica te creëren. Routes kunnen meerdere beveiligingsannotaties hebben:

### Rollencontroles combineren met aangepaste logica {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Moet de rol USER hebben EN toegang hebben tot hun eigen instellingen
}
```

**Hoe het werkt:**
1. `RolesAllowedEvaluator` (prioriteit 5) controleert of de gebruiker de rol "USER" heeft
2. Als dat zo is, roept hij `chain.evaluate()` aan om door te gaan
3. `OwnershipEvaluator` (prioriteit 10) controleert of `userId` overeenkomt met de ingelogde gebruiker
4. Als dat zo is, roept hij `chain.evaluate()` aan om door te gaan
5. Ketting eindigt → toegang verleend

### SpEL-expressies combineren met aangepaste logica {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Moet admin zijn EN toegang hebben tot hun eigen account
}
```

### Wat kan niet worden gecombineerd {#cant-combine}

`@AnonymousAccess` en `@PermitAll` nemen **terminal beslissingen** - ze verlenen onmiddellijk toegang zonder de keten aan te roepen. Je kunt ze niet combineren met aangepaste evaluators:

```java
// @PermitAll verleent onmiddellijk toegang, @RequireOwnership draait nooit
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Voor bronnen waartoe alle geverifieerde gebruikers toegang hebben, gebruik `@RolesAllowed` met een algemene rol in plaats daarvan:

```java
// @RolesAllowed delegeert naar de keten
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Moet een geverifieerde gebruiker zijn EN toegang hebben tot hun eigen profiel
}
```
