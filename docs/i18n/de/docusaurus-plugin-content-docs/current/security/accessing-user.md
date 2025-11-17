---
title: Zugriff auf Benutzer
sidebar_position: 4
_i18n_hash: 5a29cabdc472de49bcc1db895b982485
---
Spring Security speichert Informationen über authentifizierte Benutzer im `SecurityContextHolder` und bietet Zugriff auf Benutzernamen, Rollen und Berechtigungen in Ihrer gesamten App. In diesem Abschnitt wird gezeigt, wie Sie diese Informationen in webforJ-Ansichten und -Komponenten abrufen und verwenden können.

## Aktuelle Benutzerinformationen abrufen {#get-current-user-information}

Spring Security speichert Informationen über authentifizierte Benutzer im `SecurityContextHolder`, der thread-sicheren Zugriff überall in Ihrer App bietet:

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

// Aktuelle Authentifizierung abrufen
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// Benutzernamen abrufen
String username = authentication.getName();

// Berechtigungen (Rollen) abrufen
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// Überprüfen, ob der Benutzer eine bestimmte Rolle hat
boolean isAdmin = authorities.stream()
  .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
```

Der `SecurityContextHolder` ist das zentrale Mechanismus von Spring Security zum Zugriff auf die Authentifizierungsdetails des aktuellen Benutzers. Er funktioniert in Ihrer gesamten App, auch in den Konstruktormethoden und Methoden von webforJ-Ansichten.

## Benutzerinformationen in Ansichten anzeigen {#display-user-information-in-views}

Greifen Sie direkt in Ihren webforJ-Ansichten auf Benutzerinformationen zu, um personalisierte Inhalte anzuzeigen:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Benutzerinformationen anzeigen
    H1 welcome = new H1("Willkommen, " + username + "!");

    self.add(welcome);
  }
}
```

## Bedingtes Rendering basierend auf Rollen {#conditional-rendering-based-on-roles}

Zeigen Sie UI-Elemente basierend auf Benutzerrollen an oder verbergen Sie sie, um zu steuern, welche Funktionen Benutzer sehen:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Überprüfen auf bestimmte Rolle
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Bedingt nur die Admin-Schaltfläche hinzufügen
    if (isAdmin) {
      Button adminPanel = new Button("Admin-Panel");
      adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
      self.add(adminPanel);
    }
  }
}
```
