---
sidebar_position: 6
title: Custom Evaluators
_i18n_hash: 9b448cdd74811b257b78cc6c9f04e7c2
---
Custom-Evaluator erweitern das Sicherheitssystem von webforJ mit spezialisierten Zugriffssteuerungslogik, die über die grundlegende Authentifizierung und Rollenüberprüfung hinausgeht. Verwenden Sie sie, wenn Sie dynamische Bedingungen verifizieren müssen, die vom Kontext der Anfrage abhängen, und nicht nur von den Benutzerberechtigungen.

:::info Fokus auf Spring Security
Dieser Leitfaden behandelt benutzerdefinierte Evaluatoren für Spring Security. Wenn Sie Spring Boot nicht verwenden, sehen Sie sich den [Leitfaden zur Evaluator-Kette](/docs/security/architecture/evaluator-chain) an, um zu verstehen, wie Evaluatoren funktionieren, und die [Vollständige Implementierung](/docs/security/architecture/custom-implementation) für ein funktionierendes Beispiel.
:::

## Was sind benutzerdefinierte Evaluatoren {#what-are-custom-evaluators}

Ein Evaluator bestimmt, ob ein Benutzer auf eine bestimmte Route basierend auf benutzerdefinierter Logik zugreifen kann. Evaluatoren werden während der Navigation überprüft, bevor eine Komponente gerendert wird, sodass Sie den Zugriff dynamisch abfangen und steuern können.

webforJ enthält integrierte Evaluatoren für Standard-Jakarta-Annotationen:

- `AnonymousAccessEvaluator` - Behandelt `@AnonymousAccess`
- `PermitAllEvaluator` - Behandelt `@PermitAll`
- `RolesAllowedEvaluator` - Behandelt `@RolesAllowed`
- `DenyAllEvaluator` - Behandelt `@DenyAll`

Benutzerdefinierte Evaluatoren folgen demselben Muster, das es Ihnen ermöglicht, Ihre eigenen Annotationen und Zugriffssteuerungslogik zu erstellen.

:::tip[Erfahren Sie mehr über integrierte Annotationen]
Weitere Informationen zu `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` und `@DenyAll` finden Sie im [Leitfaden zu Sicherheitsanmerkungen](/docs/security/annotations).
:::

## Anwendungsfall: Besitzverifizierung {#use-case-ownership-verification}

Ein häufiges Anliegen ist es, Benutzern den Zugriff nur auf ihre eigenen Ressourcen zu ermöglichen. Zum Beispiel sollten Benutzer nur ihr eigenes Profil bearbeiten können, nicht das Profil eines anderen.

**Das Problem**: `@RolesAllowed("USER")` gewährt Zugriff auf alle authentifizierten Benutzer, überprüft jedoch nicht, ob der Benutzer auf seine eigene Ressource zugreift. Sie müssen die ID des eingeloggten Benutzers mit der Ressourcen-ID in der URL vergleichen.

**Beispielszenario:**
- Benutzer-ID `123` ist eingeloggt
- Sie navigieren zu `/users/456/edit`
- Sollten sie auf diese Seite zugreifen? **NEIN** - sie können nur `/users/123/edit` bearbeiten

Sie können dies nicht mit Rollen lösen, da es von dem Routenparameter `:userId` abhängt, der sich bei jeder Anfrage ändert.

### Erstellen einer benutzerdefinierten Annotation {#creating-a-custom-annotation}

Definieren Sie eine Annotation, um Routen zu markieren, die eine Besitzverifizierung erfordern:

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * Der Routenparametername, der die Benutzer-ID enthält.
   */
  String value() default "userId";
}
```

Verwenden Sie sie auf Routen, die Besitzüberprüfungen erfordern:

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("Profilbearbeitungsseite");
  }
}
```

### Implementierung des Evaluators {#implementing-the-evaluator}

Erstellen Sie einen von Spring verwalteten Evaluator, der die ID des eingeloggten Benutzers mit dem Routenparameter vergleicht:

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

    // Eingeloggte Benutzer-ID aus dem Sicherheitskontext abrufen
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // :userId aus den Routenparametern abrufen
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Überprüfen, ob sie übereinstimmen
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Besitz verifiziert - Kette fortsetzen, um anderen Evaluatoren zu erlauben
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Sie können nur auf Ihre eigenen Ressourcen zugreifen");
  }
}
```

Spring erkennt und registriert automatisch Evaluatoren, die mit `@RegisteredEvaluator` annotiert sind.

### So funktioniert es {#how-it-works}

Die Implementierung des Evaluators hat zwei wichtige Methoden:

#### `supports(Class<?> routeClass)` {#supports-method}

- Gibt `true` zurück, wenn dieser Evaluator die Route behandeln soll
- Nur Evaluatoren, die `true` zurückgeben, werden für die Route aufgerufen
- Filtert Routen, indem geprüft wird, ob die Annotation `@RequireOwnership` vorhanden ist

#### `evaluate(...)` {#evaluate-method}

- Überprüft zuerst, ob der Benutzer authentifiziert ist
- Ruft die ID des eingeloggten Benutzers aus `securityContext.getPrincipal()` ab
- Holt den Routenparameterwert aus `context.getRouteParameters().get(paramName)`
- Vergleicht die beiden IDs
- Wenn sie übereinstimmen, delegiert er an `chain.evaluate()`, um anderen Evaluatoren zu erlauben, ausgeführt zu werden
- Wenn sie nicht übereinstimmen, gibt er `deny()` mit einem Grund zurück

### Flussbeispiel {#flow-example}

**Wenn die Besitzüberprüfung fehlschlägt:**

1. Benutzer `123` meldet sich an und navigiert zu `/users/456/edit`
2. `OwnershipEvaluator.supports()` gibt `true` zurück (Route hat `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` wird ausgeführt:
   - `currentUserId = "123"` (aus dem Sicherheitskontext)
   - `requestedUserId = "456"` (aus dem Routenparameter `:userId`)
   - `"123".equals("456")` → `false`
   - Gibt `RouteAccessDecision.deny("Sie können nur auf Ihre eigenen Ressourcen zugreifen")` zurück
4. Benutzer wird zur Seite "Zugriff verweigert" umgeleitet

**Wenn die Besitzüberprüfung besteht:**

1. Benutzer `123` meldet sich an und navigiert zu `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` wird ausgeführt:
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - IDs stimmen überein → ruft `chain.evaluate()` auf, um fortzufahren
3. Wenn keine anderen Evaluatoren den Zugriff verweigern, wird dem Benutzer der Zugriff gewährt

## Verstehen der Evaluator-Kette {#understanding-the-evaluator-chain}

Das Sicherheitssystem verwendet ein **Kettenmuster** der Verantwortung, bei dem Evaluatoren in Prioritätsreihenfolge verarbeitet werden. Evaluatoren können entweder terminale Entscheidungen treffen oder an die Kette delegieren, um mehrere Überprüfungen zu kombinieren.

### So funktioniert die Kette {#how-chain-works}

1. Evaluatoren werden nach Priorität sortiert (niedrigere Nummern zuerst)
2. Für jeden Evaluator wird `supports(routeClass)` aufgerufen, um zu prüfen, ob er anwendbar ist
3. Wenn `supports()` `true` zurückgibt, wird die Methode `evaluate()` des Evaluators aufgerufen
4. Der Evaluator kann entweder:
   - **Eine terminale Entscheidung zurückgeben** (`grant()` oder `deny()`) - **stoppt die Kette**
   - **An die Kette delegieren**, indem er `chain.evaluate()` aufruft - **ermöglicht anderen Evaluatoren, ausgeführt zu werden**
5. Wenn die Kette ohne Entscheidung abgeschlossen wird und der "sicherheitsstandard" aktiviert ist, werden nicht authentifizierte Benutzer abgelehnt

### Terminale Entscheidungen {#terminal-decisions}

Stoppen Sie die Kette sofort:

#### `RouteAccessDecision.grant()`

- Gewährt Zugriff und stoppt weitere Bewertungen
- Wird von `@AnonymousAccess` und `@PermitAll` verwendet - dies sind vollständige Berechtigungen, die sich nicht mit anderen Überprüfungen kombinieren

#### `RouteAccessDecision.deny(reason)`

- Verweigert den Zugriff und stoppt weitere Bewertungen
- Wird von `@DenyAll` und wenn benutzerdefinierte Überprüfungen fehlschlagen verwendet
- Beispiel: `RouteAccessDecision.deny("Sie können nur auf Ihre eigenen Ressourcen zugreifen")`

#### `RouteAccessDecision.denyAuthentication()`

- Leitet zur Anmeldeseite weiter
- Wird verwendet, wenn Authentifizierung erforderlich, aber nicht verfügbar ist

### Kettendelegation {#chain-delegation}

Ermöglicht das Kombinieren von Überprüfungen:

#### `chain.evaluate(routeClass, context, securityContext)`

- Überträgt die Kontrolle an den nächsten Evaluator in der Kette
- Ermöglicht das Kombinieren mehrerer Autorisierungsüberprüfungen
- Wird von `@RolesAllowed` und `@RouteAccess` nach bestandenem Test verwendet
- Benutzerdefinierte Evaluatoren sollten dieses Muster verwenden, wenn Überprüfungen erfolgreich sind, um Komposition zu ermöglichen

## Evaluator-Priorität {#evaluator-priority}

Evaluatoren werden in Prioritätsreihenfolge überprüft (niedrigere Nummern zuerst). Framework-Evaluatoren verwenden Priorität 1-9, benutzerdefinierte Evaluatoren sollten 10 oder höher verwenden.

Integrierte Evaluatoren werden in dieser Reihenfolge registriert:

```java
// Priorität 1: @DenyAll - blockiert alles
// Priorität 2: @AnonymousAccess - ermöglicht nicht authentifizierten Zugriff
// Priorität 3: AuthenticationRequiredEvaluator - stellt Authentifizierung für @PermitAll/@RolesAllowed sicher
// Priorität 4: @PermitAll - erfordert nur Authentifizierung
// Priorität 5: @RolesAllowed - erfordert spezifische Rollen
// Priorität 6: @RouteAccess - SpEL-Ausdrücke (nur Spring Security)
// Priorität 10+: Benutzerdefinierte Evaluatoren (wie @RequireOwnership)
```

### Wie sich die Priorität auf die Bewertung auswirkt {#priority-affects-evaluation}

- Evaluatoren mit niedrigerer Priorität laufen zuerst und können die Kette "kurzschließen"
- `@DenyAll` (Priorität 1) läuft zuerst - wenn vorhanden, wird der Zugriff immer verweigert
- `@AnonymousAccess` (Priorität 2) läuft als nächstes - wenn vorhanden, wird der Zugriff immer gewährt (auch ohne Authentifizierung)
- `AuthenticationRequiredEvaluator` (Priorität 3) überprüft, ob die Route Authentifizierung benötigt und ob der Benutzer authentifiziert ist
- Wenn kein Evaluator die Route behandelt, wird die Sicherheitslogik angewendet

### Priorität festlegen {#setting-priority}

Legen Sie die Priorität mit der Annotation `@RegisteredEvaluator` fest:

```java
@RegisteredEvaluator(priority = 10)  // Läuft nach den integrierten Evaluatoren
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Prioritätsbereich
Benutzerdefinierte Evaluatoren sollten Priorität 10 oder höher verwenden. Prioritäten 1-9 sind für Framework-Evaluatoren reserviert. Wenn Sie eine Priorität im reservierten Bereich verwenden, erhalten Sie eine Warnung in den Logs.
:::

## Kombinieren von Evaluatoren {#combining-evaluators}

Evaluatoren, die an die Kette delegieren, können kombiniert werden, um komplexe Autorisierungslogik zu erstellen. Routen können mehrere Sicherheitsannotationen haben:

### Kombinieren von Rollenüberprüfungen mit benutzerdefinierter Logik {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Muss die Rolle USER haben UND auf ihre eigenen Einstellungen zugreifen
}
```

**So funktioniert es:**
1. `RolesAllowedEvaluator` (Priorität 5) überprüft, ob der Benutzer die Rolle "USER" hat
2. Wenn ja, wird `chain.evaluate()` aufgerufen, um fortzufahren
3. `OwnershipEvaluator` (Priorität 10) überprüft, ob `userId` mit dem eingeloggten Benutzer übereinstimmt
4. Wenn ja, wird `chain.evaluate()` aufgerufen, um fortzufahren
5. Kette endet → Zugriff gewährt

### Kombinieren von SpEL-Ausdrücken mit benutzerdefinierter Logik {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Muss Admin sein UND auf sein eigenes Konto zugreifen
}
```

### Was nicht kombiniert werden kann {#cant-combine}

`@AnonymousAccess` und `@PermitAll` treffen **terminal Entscheidungen** - sie gewähren sofort Zugriff, ohne die Kette aufzurufen. Sie können sie nicht mit benutzerdefinierten Evaluatoren kombinieren:

```java
// @PermitAll gewährt sofort Zugriff, @RequireOwnership wird niemals ausgeführt
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Für Ressourcen, auf die alle authentifizierten Benutzer zugreifen können, verwenden Sie `@RolesAllowed` mit einer gemeinsamen Rolle:

```java
// @RolesAllowed delegiert an die Kette
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Muss ein authentifizierter Benutzer sein UND auf sein eigenes Profil zugreifen
}
```
