---
sidebar_position: 3
title: Security Annotations
_i18n_hash: 564a7d991d26edb972bc2c7c99366f37
---
Sicherheitsannotationen bieten eine deklarative Möglichkeit, den Zugriff auf Routen in Ihrer webforJ-App zu steuern. Durch das Hinzufügen von Annotationen zu Ihren Routenkomponenten definieren Sie, wer auf jede Ansicht zugreifen kann, ohne manuelle Berechtigungsprüfungen schreiben zu müssen. Das Sicherheitssystem setzt diese Regeln automatisch durch, bevor eine Komponente gerendert wird.

:::info Implementierungsnotiz
Dieser Leitfaden funktioniert mit jeder Sicherheitsimplementierung. Die gezeigten Beispiele funktionieren sowohl mit Spring Security als auch mit benutzerdefinierten Implementierungen. Wenn Sie Spring Boot nicht verwenden, lesen Sie den [Leitfaden zur Sicherheitsarchitektur](/docs/security/architecture/overview), um das Fundament zu verstehen und eine benutzerdefinierte Sicherheit zu implementieren.
:::

## `@AnonymousAccess` - öffentliche Routen {#anonymousaccess-public-routes}

Die Annotation `@AnonymousAccess` kennzeichnet eine Route als öffentlich zugänglich. Benutzer müssen sich nicht authentifizieren, um auf diese Routen zuzugreifen. Diese Annotation wird typischerweise für Anmeldeseiten, öffentliche Landing-Pages oder andere Inhalte verwendet, die für alle verfügbar sein sollten.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // Anmeldesicht
  }
}
```

In diesem Beispiel:
- Jeder Benutzer, sowohl authentifiziert als auch nicht, kann auf die Route `/login` zugreifen.
- Wenn `@AnonymousAccess` vorhanden ist, dürfen nicht authentifizierte Benutzer auf diese Seite zugreifen, ohne umgeleitet zu werden.

:::tip Häufige Anwendungsfälle
Verwenden Sie `@AnonymousAccess` für Anmeldeseiten, Registrierungsseiten, öffentliche Homepages, Dienstleistungsbedingungen, Datenschutzrichtlinien und andere Inhalte, die ohne Authentifizierung zugänglich sein sollten.
:::

## `@PermitAll` - authentifizierte Routen {#permitall-authenticated-routes}

Die Annotation `@PermitAll` erfordert, dass Benutzer authentifiziert sind, setzt jedoch keine spezifischen Rollenanforderungen durch. Jeder angemeldete Benutzer kann auf diese Routen zugreifen, unabhängig von seinen Rollen oder Berechtigungen.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.add(new Explore("Posteingang"));
  }
}
```

In diesem Beispiel:
- Benutzer müssen sich authentifizieren, um auf den Posteingang zuzugreifen.
- Jeder authentifizierte Benutzer kann diese Seite anzeigen, unabhängig von seiner Rolle.
- Nicht authentifizierte Benutzer werden zur Anmeldeseite umgeleitet.

:::info Sicher von Anfang an
Wenn der sichere Standardmodus aktiviert ist, verhalten sich Routen ohne Sicherheitsannotation wie `@PermitAll`— sie erfordern eine Authentifizierung. Siehe den Abschnitt [sicher von Anfang an](#secure-by-default) für Details.
:::

## `@RolesAllowed` - rollenbasierte Routen {#rolesallowed-role-based-routes}

Die Annotation `@RolesAllowed` schränkt den Zugang auf Benutzer mit bestimmten Rollen ein. Sie können eine oder mehrere Rollen angeben, und Benutzer müssen mindestens eine der aufgeführten Rollen haben, um auf die Route zuzugreifen.

### Einfache Rollenanforderung {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TrashView() {
    self.setHeight("100%");
    self.add(new Explore("Papierkorb"));
  }
}
```

In diesem Beispiel:
- Nur Benutzer mit der Rolle `ADMIN` können auf die Papieransicht zugreifen.
- Benutzer ohne die Rolle `ADMIN` werden zur Seite mit verweigertem Zugriff umgeleitet.

### Mehrfache Rollenanforderungen {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public SettingsView() {
    self.add(new Explore("Einstellungen"));
  }
}
```

In diesem Beispiel:
- Benutzer mit entweder der Rolle `ADMIN` oder `MANAGER` können auf die Einstellungen zugreifen.
- Der Benutzer benötigt nur eine der aufgeführten Rollen, nicht alle.

:::tip Namenskonventionen für Rollen
Verwenden Sie Großbuchstaben für Rollennamen (wie `ADMIN`, `USER`, `MANAGER`) für Konsistenz. Dies entspricht den gängigen Konventionen von Sicherheitsframeworks und verbessert die Lesbarkeit Ihres Codes.
:::

## `@DenyAll` - gesperrte Routen {#denyall-blocked-routes}

Die Annotation `@DenyAll` blockiert den Zugriff auf eine Route für alle Benutzer, unabhängig vom Authentifizierungsstatus oder von Rollen. Dies ist nützlich, um Routen während der Wartung vorübergehend zu deaktivieren oder für Routen, die sich in der Entwicklung befinden.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public MaintenanceView() {
    self.add(new Paragraph("Diese Seite wird gewartet."));
  }
}
```

In diesem Beispiel:
- Kein Benutzer kann auf diese Route zugreifen, auch nicht Administratoren.
- Alle Zugriffsversuche führen zur Umleitung auf die Seite mit verweigertem Zugriff.

:::warning Vorübergehende Verwendung
`@DenyAll` wird typischerweise vorübergehend während der Entwicklung oder Wartung verwendet. Für Produktionsanwendungen sollten Sie die Route ganz entfernen oder stattdessen ordnungsgemäße Rollenbeschränkungen verwenden.
:::

## Was passiert, wenn der Zugriff verweigert wird {#what-happens-when-access-is-denied}

Wenn ein Benutzer versucht, auf eine Route zuzugreifen, auf die er nicht autorisiert ist, behandelt das Sicherheitssystem die Ablehnung automatisch:

1. **Nicht authentifizierte Benutzer**: Werden zur Anmeldeseite umgeleitet, die in Ihrer Sicherheitskonfiguration festgelegt ist.
2. **Authentifizierte Benutzer ohne erforderliche Rollen**: Werden zur Seite mit verweigertem Zugriff umgeleitet.
3. **Alle Benutzer auf `@DenyAll`-Routen**: Werden zur Seite mit verweigertem Zugriff umgeleitet.

Sie können diese Umleitungsstandorte anpassen, um Ihrer Navigationsstruktur zu entsprechen, sodass Zugangsverweigerungen und Authentifizierungsanfragen zu den vorgesehenen Seiten führen. Siehe [Spring Security konfigurieren](/docs/security/getting-started#configure-spring-security) für Konfigurationsdetails.

## Sicher von Anfang an {#secure-by-default}

Der sichere Standardmodus ist eine Konfigurationsoption, die festlegt, wie Routen ohne Sicherheitsannotation behandelt werden. Wenn aktiviert, erfordern alle Routen standardmäßig eine Authentifizierung, es sei denn, sie sind ausdrücklich mit `@AnonymousAccess` gekennzeichnet.

### Aktiviert (empfohlen für die Produktion) {#enabled-recommended-for-production}

Fügen Sie dies Ihrer `application.properties` hinzu:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Mit aktiviertem sicheren Standardmodus:
- Routen ohne Annotationen erfordern eine Authentifizierung (gleichbedeutend mit `@PermitAll`).
- Nur `@AnonymousAccess`-Routen sind öffentlich zugänglich.
- Sie müssen öffentliche Routen ausdrücklich kennzeichnen, um das Risiko zu verringern, geschützte Inhalte versehentlich offenzulegen.

```java
// Erfordert Authentifizierung (keine Annotation)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Öffentlicher Zugriff (ausdrücklich gekennzeichnet)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Deaktiviert (Standardzugriff erlauben) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Mit deaktiviertem sicheren Standardmodus:
- Routen ohne Annotationen sind öffentlich zugänglich.
- Sie müssen ausdrücklich `@PermitAll` oder `@RolesAllowed` hinzufügen, um Routen zu schützen.
- Einfacher für die Entwicklung, aber riskanter für die Produktion.

```java
// Öffentlicher Zugriff (keine Annotation)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Erfordert Authentifizierung (ausdrücklich gekennzeichnet)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Beste Praxis
Aktivieren Sie `secure-by-default` für Produktionsanwendungen. Mit dieser Einstellung werden geschützte Routen nicht offengelegt, es sei denn, sie sind ausdrücklich als öffentlich gekennzeichnet, wodurch das Risiko einer versehentlichen Offenlegung aufgrund fehlender Annotationen verringert wird. Schalten Sie es nur während der anfänglichen Entwicklung aus, wenn Sie die zusätzlichen Annotationen als umständlich empfinden.
:::
