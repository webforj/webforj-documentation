---
title: Accessing User
sidebar_position: 4
_i18n_hash: 5a29cabdc472de49bcc1db895b982485
---
Spring Security slaat geauthenticeerde gebruikersinformatie op in de `SecurityContextHolder`, die toegang biedt tot gebruikersnaam, rollen en autorisaties in uw applicatie. Deze sectie laat zien hoe u deze informatie kunt ophalen en gebruiken in webforJ-weergaven en -componenten.

## Huidige gebruikersinformatie ophalen {#get-current-user-information}

Spring Security slaat geauthenticeerde gebruikersinformatie op in de `SecurityContextHolder`, die thread-veilige toegang biedt overal in uw applicatie:

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

// Verkrijg huidige authenticatie
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// Verkrijg gebruikersnaam
String username = authentication.getName();

// Verkrijg autorisaties (rollen)
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// Controleer of de gebruiker een specifieke rol heeft
boolean isAdmin = authorities.stream()
  .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
```

De `SecurityContextHolder` is het centrale mechanisme van Spring Security voor het toegang verkrijgen tot de authenticatiegegevens van de huidige gebruiker. Het werkt door heel uw applicatie, inclusief in de constructeurs en methoden van webforJ-weergaven.

## Gebruikersinformatie weergeven in weergaven {#display-user-information-in-views}

Toegang tot gebruikersinformatie rechtstreeks in uw webforJ-weergaven om gepersonaliseerde inhoud weer te geven:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Weergave van gebruikersinformatie
    H1 welcome = new H1("Welkom, " + username + "!");
    
    self.add(welcome);
  }
}
```

## Voorwaardelijke weergave op basis van rollen {#conditional-rendering-based-on-roles}

Toon of verberg UI-elementen op basis van gebruikersrollen om te bepalen welke functies gebruikers zien:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Controleer op specifieke rol
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Voorwaardelijk een knop voor alleen beheerders toevoegen
    if (isAdmin) {
      Button adminPanel = new Button("Beheerderspaneel");
      adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
      self.add(adminPanel);
    }
  }
}
```
