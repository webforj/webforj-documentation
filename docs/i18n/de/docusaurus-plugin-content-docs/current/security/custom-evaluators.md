---
sidebar_position: 6
title: Custom Evaluators
description: >-
  Write custom RouteSecurityEvaluators for context-aware checks like ownership
  verification beyond role-based permissions.
_i18n_hash: d1edb7260efb2928e988a2cdf313f380
---
Custom-Evaluatoren erweitern das Sicherheitssystem von webforJ um spezialisierte Zugriffssteuerungslogik, die über die grundlegende Authentifizierung und Rollenprüfungen hinausgeht. Verwenden Sie sie, wenn Sie dynamische Bedingungen überprüfen müssen, die vom Kontext der Anfrage abhängen, nicht nur von den Berechtigungen des Benutzers.

:::info Fokussiert auf Spring Security
Dieser Leitfaden behandelt benutzerdefinierte Evaluatoren für Spring Security. Wenn Sie Spring Boot nicht verwenden, siehe den [Evaluator-Chain-Leitfaden](/docs/security/architecture/evaluator-chain), um zu verstehen, wie Evaluatoren funktionieren, und [Vollständige Implementierung](/docs/security/architecture/custom-implementation) für ein funktionierendes Beispiel.
:::

## Was sind benutzerdefinierte Evaluatoren {#what-are-custom-evaluators}

Ein Evaluator bestimmt, ob ein Benutzer auf eine spezifische Route basierend auf benutzerdefinierter Logik zugreifen kann. Evaluatoren werden während der Navigation überprüft, bevor eine Komponente gerendert wird, was es Ihnen ermöglicht, den Zugriff dynamisch abzufangen und zu steuern.

webforJ enthält integrierte Evaluatoren für standardmäßige Jakarta-Anmerkungen:

- `AnonymousAccessEvaluator` - Behandelt `@AnonymousAccess`
- `PermitAllEvaluator` - Behandelt `@PermitAll`
- `RolesAllowedEvaluator` - Behandelt `@RolesAllowed`
- `DenyAllEvaluator` - Behandelt `@DenyAll`

Benutzerdefinierte Evaluatoren folgen demselben Muster, sodass Sie Ihre eigenen Anmerkungen und Zugriffssteuerungslogik erstellen können.

:::tip[Erfahren Sie mehr über integrierte Anmerkungen]
Für Details zu `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` und `@DenyAll`, siehe den [Leitfaden zu Sicherheitsanmerkungen](/docs/security/annotations).
:::

## Anwendungsfall: Besitzverifizierung {#use-case-ownership-verification}

Ein häufiges Erfordernis ist es, Benutzern den Zugriff nur auf ihre eigenen Ressourcen zu erlauben. Beispielsweise sollten Benutzer nur ihr eigenes Profil bearbeiten können, nicht das Profil eines anderen.

**Das Problem**: `@RolesAllowed("USER")` gewährt den Zugriff auf alle authentifizierten Benutzer, überprüft jedoch nicht, ob der Benutzer auf seine eigene Ressource zugreift. Sie müssen die Benutzer-ID des angemeldeten Benutzers mit der Ressourcen-ID in der URL vergleichen.

**Beispielszenario:**
- Benutzer-ID `123` ist angemeldet
- Sie navigieren zu `/users/456/edit`
- Dürfen sie auf diese Seite zugreifen? **NEIN** - sie können nur `/users/123/edit` bearbeiten.

Sie können dies nicht mit Rollen lösen, da es von dem Routenparameter `:userId` abhängt, der sich für jede Anfrage ändert.

### Erstellen einer benutzerdefinierten Anmerkung {#creating-a-custom-annotation}

Definieren Sie eine Anmerkung, um Routen zu kennzeichnen, die eine Besitzverifizierung erfordern:

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * Der Name des Routenparameters, der die Benutzer-ID enthält.
   */
  String value() default "userId";
}
```

Verwenden Sie sie auf Routen, die Besitzprüfungen erfordern:

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("Profil bearbeiten");
  }
}
```

### Implementierung des Evaluators {#implementing-the-evaluator}

Erstellen Sie einen von Spring verwalteten Evaluator, der die angemeldete Benutzer-ID mit dem Routenparameter vergleicht:

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

    // Zuerst die Authentifizierung überprüfen
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // Annotation abrufen
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // Die angemeldete Benutzer-ID aus dem Sicherheitskontext abrufen
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // :userId von den Routenparametern abrufen
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Überprüfen, ob sie übereinstimmen
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Besitz verifiziert - Kette fortsetzen, um anderen Evaluatoren zu erlauben, zu laufen
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Sie können nur auf Ihre eigenen Ressourcen zugreifen");
  }
}
```

Spring entdeckt und registriert automatisch Evaluatoren, die mit `@RegisteredEvaluator` annotiert sind.

### Wie es funktioniert {#how-it-works}

Die Implementierung des Evaluators hat zwei Schlüsselmethoden:

#### `supports(Class<?> routeClass)` {#supports-method}

- Gibt `true` zurück, wenn dieser Evaluator die Route handhaben soll
- Nur Evaluatoren, die `true` zurückgeben, werden für die Route aufgerufen
- Filtert Routen, indem es nach der `@RequireOwnership`-Anmerkung sucht

#### `evaluate(...)` {#evaluate-method}

- Überprüft zuerst, ob der Benutzer authentifiziert ist
- Holt die angemeldete Benutzer-ID aus `securityContext.getPrincipal()`
- Holt den Wert des Routenparameters aus `context.getRouteParameters().get(paramName)`
- Vergleicht die beiden IDs
- Wenn sie übereinstimmen, delegiert sie an `chain.evaluate()`, um anderen Evaluatoren zu erlauben, zu laufen
- Wenn sie nicht übereinstimmen, gibt sie `deny()` mit einem Grund zurück

### Flussbeispiel {#flow-example}

**Wenn die Besitzprüfung fehlschlägt:**

1. Benutzer `123` meldet sich an und navigiert zu `/users/456/edit`
2. `OwnershipEvaluator.supports()` gibt `true` zurück (Route hat `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` läuft:
   - `currentUserId = "123"` (aus dem Sicherheitskontext)
   - `requestedUserId = "456"` (vom Routenparameter `:userId`)
   - `"123".equals("456")` → `false`
   - Gibt `RouteAccessDecision.deny("Sie können nur auf Ihre eigenen Ressourcen zugreifen")` zurück
4. Benutzer wird zur Zugriffsverweigerungsseite umgeleitet

**Wenn die Besitzprüfung besteht:**

1. Benutzer `123` meldet sich an und navigiert zu `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` läuft:
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - IDs stimmen überein → ruft `chain.evaluate()` auf, um fortzufahren
3. Wenn keine anderen Evaluatoren den Zugriff verweigern, wird dem Benutzer der Zugriff gewährt

## Verständnis der Evaluator-Kette {#understanding-the-evaluator-chain}

Das Sicherheitssystem verwendet ein **Chain of Responsibility-Muster**, bei dem Evaluatoren in prioritärer Reihenfolge verarbeitet werden. Evaluatoren können entweder terminale Entscheidungen treffen oder an die Kette delegieren, um mehrere Prüfungen zu kombinieren.

### Wie die Kette funktioniert {#how-chain-works}

1. Evaluatoren werden nach Priorität sortiert (niedrigere Zahlen zuerst)
2. Für jeden Evaluator wird `supports(routeClass)` aufgerufen, um zu überprüfen, ob er zutrifft
3. Wenn `supports()` `true` zurückgibt, wird die `evaluate()`-Methode des Evaluators aufgerufen
4. Der Evaluator kann entweder:
   - **Eine terminale Entscheidung zurückgeben** (`grant()` oder `deny()`) - **stoppt die Kette**
   - **An die Kette delegieren**, indem er `chain.evaluate()` aufruft - **ermöglicht es anderen Evaluatoren, zu laufen**
5. Wenn die Kette ohne Entscheidung abgeschlossen wird und secure-by-default aktiviert ist, werden nicht authentifizierte Benutzer abgelehnt

### Terminale Entscheidungen {#terminal-decisions}

Stoppt die Kette sofort:

#### `RouteAccessDecision.grant()` {#routeaccessdecisiongrant}

- Gewährt Zugang und stoppt die weitere Evaluierung
- Wird von `@AnonymousAccess` und `@PermitAll` verwendet - dies sind vollständige Genehmigungen, die nicht mit anderen Prüfungen kombiniert werden

#### `RouteAccessDecision.deny(reason)` {#routeaccessdecisiondenyauthentication}

- Verweigert den Zugang und stoppt die weitere Evaluierung
- Wird von `@DenyAll` und wenn benutzerdefinierte Prüfungen fehlschlagen, verwendet
- Beispiel: `RouteAccessDecision.deny("Sie können nur auf Ihre eigenen Ressourcen zugreifen")`

#### `RouteAccessDecision.denyAuthentication()` {#routeaccessdecisiondenyauthentication}

- Leitet zur Anmeldeseite weiter
- Wird verwendet, wenn Authentifizierung erforderlich, aber nicht vorhanden ist

### Kettendelegation {#chain-delegation}

Ermöglicht das Kombinieren von Prüfungen:

#### `chain.evaluate(routeClass, context, securityContext)` {#chainevaluaterouteclass-context-securitycontext}

- Überträgt die Kontrolle an den nächsten Evaluator in der Kette
- Ermöglicht das Kombinieren mehrerer Autorisierungsprüfungen
- Wird von `@RolesAllowed` und `@RouteAccess` verwendet, nachdem ihre Prüfungen bestanden haben
- Benutzerdefinierte Evaluatoren sollten dieses Muster verwenden, wenn Prüfungen bestehen, um eine Zusammensetzung zu ermöglichen

## Evaluator-Priorität {#evaluator-priority}

Evaluatoren werden in prioritärer Reihenfolge überprüft (niedrigere Zahlen zuerst). Framework-Evaluator verwenden die Priorität 1-9, benutzerdefinierte Evaluatoren sollten 10 oder höher verwenden.

Integrierte Evaluatoren werden in dieser Reihenfolge registriert:

```java
// Priorität 1: @DenyAll - blockiert alles
// Priorität 2: @AnonymousAccess - erlaubt nicht authentifizierten Zugriff
// Priorität 3: AuthenticationRequiredEvaluator - stellt sicher, dass Auth für @PermitAll/@RolesAllowed erforderlich ist
// Priorität 4: @PermitAll - erfordert nur Authentifizierung
// Priorität 5: @RolesAllowed - erfordert spezifische Rollen
// Priorität 6: @RouteAccess - SpEL-Ausdrücke (nur Spring Security)
// Priorität 10+: Benutzerdefinierte Evaluatoren (wie @RequireOwnership)
```

### Wie Priorität die Evaluierung beeinflusst {#priority-affects-evaluation}

- Evaluatoren mit niedrigerer Priorität laufen zuerst und können die Kette "kurzschließen"
- `@DenyAll` (Priorität 1) läuft zuerst - wenn vorhanden, wird der Zugriff immer verweigert
- `@AnonymousAccess` (Priorität 2) läuft als nächstes - wenn vorhanden, wird der Zugriff immer gewährt (auch ohne Auth)
- `AuthenticationRequiredEvaluator` (Priorität 3) überprüft, ob die Route Authentifizierung benötigt und der Benutzer authentifiziert ist
- Wenn kein Evaluator die Route behandelt, gelten secure-by-default-Regeln

### Priorität festlegen {#setting-priority}

Setzen Sie die Priorität mit der Annotation `@RegisteredEvaluator`:

```java
@RegisteredEvaluator(priority = 10)  // Läuft nach integrierten Evaluatoren
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Prioritätsbereich
Benutzerdefinierte Evaluatoren sollten Priorität 10 oder höher verwenden. Prioritäten 1-9 sind für Framework-Evaluatoren reserviert. Wenn Sie eine Priorität im reservierten Bereich verwenden, erhalten Sie eine Warnung in den Protokollen.
:::

## Kombinieren von Evaluatoren {#combining-evaluators}

Evaluatoren, die an die Kette delegieren, können kombiniert werden, um komplexe Autorisierungslogik zu erstellen. Routen können mehrere Sicherheitsanmerkungen haben:

### Kombinieren von Rollenprüfungen mit benutzerdefinierter Logik {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Muss die Rolle USER haben UND auf ihre eigenen Einstellungen zugreifen
}
```

**Wie es funktioniert:**
1. `RolesAllowedEvaluator` (Priorität 5) überprüft, ob der Benutzer die Rolle "USER" hat
2. Wenn ja, ruft er `chain.evaluate()` auf, um fortzufahren
3. `OwnershipEvaluator` (Priorität 10) überprüft, ob `userId` mit dem angemeldeten Benutzer übereinstimmt
4. Wenn ja, ruft er `chain.evaluate()` auf, um fortzufahren
5. Die Kette endet → Zugriff gewährt

### Kombinieren von SpEL-Ausdrücken mit benutzerdefinierter Logik {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Muss Admin sein UND auf ihr eigenes Konto zugreifen
}
```

### Was kann nicht kombiniert werden {#cant-combine}

`@AnonymousAccess` und `@PermitAll` treffen **terminal Entscheidungen** - sie gewähren sofort Zugriff, ohne die Kette aufzurufen. Sie können sie nicht mit benutzerdefinierten Evaluatoren kombinieren:

```java
// @PermitAll gewährt sofort Zugang, @RequireOwnership wird niemals ausgeführt
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Für Ressourcen, auf die alle authentifizierten Benutzer zugreifen können, verwenden Sie `@RolesAllowed` mit einer allgemeinen Rolle stattdessen:

```java
// @RolesAllowed delegiert an die Kette
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Muss ein authentifizierter Benutzer sein UND auf sein eigenes Profil zugreifen
}
```
