---
sidebar_position: 3
title: Security Annotations
_i18n_hash: af9997b8bee96bfa4005a65998fddaf5
---
Sicherheitsannotationen bieten eine deklarative Möglichkeit, den Zugriff auf Routen in Ihrer webforJ-App zu steuern. Durch das Hinzufügen von Annotationen zu Ihren Routenkomponenten definieren Sie, wer auf jede Ansicht zugreifen kann, ohne manuelle Berechtigungsprüfungen schreiben zu müssen. Das Sicherheitssystem erzwingt diese Regeln automatisch, bevor eine Komponente gerendert wird.

:::info Implementierungsnotiz
Dieser Leitfaden funktioniert mit jeder Sicherheitsimplementierung. Die gezeigten Beispiele funktionieren sowohl mit Spring Security als auch mit benutzerdefinierten Implementierungen. Wenn Sie Spring Boot nicht verwenden, siehe den [Leitfaden zur Sicherheitsarchitektur](/docs/security/architecture/overview), um die Grundlage zu verstehen und benutzerdefinierte Sicherheit zu implementieren.
:::

## `@AnonymousAccess` - öffentliche Routen {#anonymousaccess-public-routes}

Die `@AnonymousAccess`-Annotation kennzeichnet eine Route als öffentlich zugänglich. Benutzer müssen nicht authentifiziert sein, um auf diese Routen zuzugreifen. Diese Annotation wird typischerweise für Anmeldeseiten, öffentliche Landing-Pages oder andere Inhalte verwendet, die für jeden zugänglich sein sollten.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // Anmeldedialog
  }
}
```

In diesem Beispiel:
- Jeder Benutzer, authentifiziert oder nicht, kann auf die Route `/login` zugreifen.
- Wenn `@AnonymousAccess` vorhanden ist, dürfen nicht authentifizierte Benutzer auf diese Seite zugreifen, ohne umgeleitet zu werden.

:::tip Häufige Verwendungszwecke
Verwenden Sie `@AnonymousAccess` für Anmeldeseiten, Registrierungsseiten, öffentliche Startseiten, Nutzungsbedingungen, Datenschutzrichtlinien und andere Inhalte, die ohne Authentifizierung zugänglich sein sollten.
:::

## `@PermitAll` - authentifizierte Routen {#permitall-authenticated-routes}

Die `@PermitAll`-Annotation erfordert von Benutzern, dass sie authentifiziert sind, erzwingt jedoch keine spezifischen Rollenanforderungen. Jeder angemeldete Benutzer kann auf diese Routen zugreifen, unabhängig von seinen Rollen oder Berechtigungen.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Posteingang"));
  }
}
```

In diesem Beispiel:
- Benutzer müssen authentifiziert sein, um auf den Posteingang zuzugreifen.
- Jeder authentifizierte Benutzer kann diese Seite anzeigen, unabhängig von seiner Rolle.
- Nicht authentifizierte Benutzer werden zur Anmeldeseite umgeleitet.

:::info Sicher-standardmäßig-Modus
Wenn der Sicher-standardmäßig-Modus aktiviert ist, verhalten sich Routen ohne Sicherheitsannotation wie `@PermitAll` - sie erfordern Authentifizierung. Siehe den [abschnitt sicher-standardmäßig](#secure-by-default) für Details.
:::

## `@RolesAllowed` - rollenbasierte Routen {#rolesallowed-role-based-routes}

Die `@RolesAllowed`-Annotation schränkt den Zugang auf Benutzer mit spezifischen Rollen ein. Sie können eine oder mehrere Rollen festlegen, und Benutzer müssen mindestens eine der angegebenen Rollen haben, um auf die Route zuzugreifen.

### Einzelne Rollenanforderung {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {

  public TrashView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Papierkorb"));
  }
}
```

In diesem Beispiel:
- Nur Benutzer mit der Rolle `ADMIN` können auf die Papierkorbansicht zugreifen.
- Benutzer ohne die Rolle `ADMIN` werden zur Seite Zugriff verweigert umgeleitet.

### Mehrere Rollenanforderungen {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {

  public SettingsView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Explore("Einstellungen"));
  }
}
```

In diesem Beispiel:
- Benutzer mit entweder der Rolle `ADMIN` oder `MANAGER` können auf die Einstellungen zugreifen.
- Der Benutzer benötigt nur eine der aufgeführten Rollen, nicht alle.

:::tip Rollennamenskonventionen
Verwenden Sie Großbuchstaben für Rollennamen (wie `ADMIN`, `USER`, `MANAGER`), um Konsistenz zu gewährleisten. Dies entspricht gängigen Konventionen in Sicherheitsframeworks und macht Ihren Code lesbarer.
:::

## `@DenyAll` - gesperrte Routen {#denyall-blocked-routes}

Die `@DenyAll`-Annotation sperrt den Zugriff auf eine Route für alle Benutzer, unabhängig vom Authentifizierungsstatus oder den Rollen. Dies ist nützlich, um Routen vorübergehend während Wartungsarbeiten zu deaktivieren oder für Routen, die sich in der Entwicklung befinden.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {

  public MaintenanceView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Paragraph("Diese Seite befindet sich in Wartung."));
  }
}
```

In diesem Beispiel:
- Kein Benutzer kann auf diese Route zugreifen, selbst Administratoren nicht.
- Alle Zugriffsversuche führen zur Umleitung zur Seite Zugriff verweigert.

:::warning Vorübergehende Verwendung
`@DenyAll` wird typischerweise vorübergehend während der Entwicklung oder Wartungsarbeiten verwendet. Für Produktionsanwendungen sollten Sie die Route vollständig entfernen oder stattdessen geeignete Rolleneinschränkungen verwenden.
:::

## Was passiert, wenn der Zugriff verweigert wird {#what-happens-when-access-is-denied}

Wenn ein Benutzer versucht, auf eine Route zuzugreifen, auf die er nicht autorisiert ist, wird die Ablehnung automatisch vom Sicherheitssystem behandelt:

1. **Nicht authentifizierte Benutzer**: Werden zur Anmeldeseite umgeleitet, die in Ihrer Sicherheitskonfiguration festgelegt ist.
2. **Authentifizierte Benutzer ohne erforderliche Rollen**: Werden zur Seite Zugriff verweigert umgeleitet.
3. **Alle Benutzer bei `@DenyAll`-Routen**: Werden zur Seite Zugriff verweigert umgeleitet.

Sie können diese Umleitungsstandorte anpassen, um der Navigationsstruktur Ihrer App zu entsprechen, sodass Zugriffsverweigerungen und Authentifizierungsanfragen zu den gewünschten Seiten führen. Siehe [Konfigurieren von Spring Security](/docs/security/getting-started#configure-spring-security) für Konfigurationsdetails.

## Sicher-standardmäßig {#secure-by-default}

Sicher-standardmäßig ist eine Konfigurationsoption, die bestimmt, wie Routen ohne Sicherheitsannotation behandelt werden. Wenn aktiviert, erfordern alle Routen standardmäßig Authentifizierung, es sei denn, sie sind ausdrücklich mit `@AnonymousAccess` gekennzeichnet.

### Aktiviert (empfohlen für die Produktion) {#enabled-recommended-for-production}

Fügen Sie dies zu Ihrer `application.properties` hinzu:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Mit aktivierter sicher-standardmäßig:
- Routen ohne Annotationen erfordern Authentifizierung (gleich wie `@PermitAll`).
- Nur `@AnonymousAccess` Routen sind öffentlich zugänglich.
- Sie müssen öffentliche Routen ausdrücklich kennzeichnen, wodurch das Risiko verringert wird, versehentlich geschützte Inhalte offenzulegen.

```java
// Erfordert Authentifizierung (keine Annotation)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Öffentlicher Zugang (ausdrücklich gekennzeichnet)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Deaktiviert (erlaube-standardmäßig) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Mit deaktiviertem sicher-standardmäßig:
- Routen ohne Annotationen sind öffentlich zugänglich.
- Sie müssen `@PermitAll` oder `@RolesAllowed` ausdrücklich hinzufügen, um Routen zu schützen.
- Einfacher für die Entwicklung, aber riskanter für die Produktion.

```java
// Öffentlicher Zugang (keine Annotation)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Erfordert Authentifizierung (ausdrücklich gekennzeichnet)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Best Practice
Aktivieren Sie `secure-by-default` für Produktionsanwendungen. Mit dieser Einstellung sind geschützte Routen nicht zugänglich, es sei denn, sie werden ausdrücklich als öffentlich gekennzeichnet, was das Risiko der versehentlichen Offenlegung aufgrund fehlender Annotationen verringert. Deaktivieren Sie es nur während der anfänglichen Entwicklung, wenn Sie die zusätzlichen Annotationen als umständlich empfinden.
:::
