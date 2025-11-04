---
title: Accessing User
sidebar_position: 4
_i18n_hash: 5a29cabdc472de49bcc1db895b982485
---
Spring Security tallentaa todennetun käyttäjätiedon `SecurityContextHolder`-objektiin, joka tarjoaa pääsyn käyttäjätunnukseen, rooleihin ja valtuuksiin koko sovelluksessa. Tässä osiossa näytetään, kuinka hakemaan ja käyttämään tätä tietoa webforJ-näkymissä ja -komponenteissa.

## Hanki nykyisen käyttäjän tiedot {#get-current-user-information}

Spring Security tallentaa todennetut käyttäjätiedot `SecurityContextHolder`-objektiin, joka tarjoaa säikeittäin turvallisen pääsyn mihin tahansa sovelluksessasi:

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

// Hanki nykyinen todennus
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// Hanki käyttäjätunnus
String username = authentication.getName();

// Hanki valtuudet (roolit)
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// Tarkista, onko käyttäjällä tietty rooli
boolean isAdmin = authorities.stream()
  .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
```

`SecurityContextHolder` on Spring Securityn keskeinen mekanismi nykyisen käyttäjän todennustietojen hakemiseen. Se toimii koko sovelluksessasi, mukaan lukien webforJ-näkymien konstruktorit ja metodit.

## Näytä käyttäjätiedot näkymissä {#display-user-information-in-views}

Pääset käyttäjätietoihin suoraan webforJ-näkymissä näyttämään henkilökohtaista sisältöä:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Näytä käyttäjätiedot
    H1 welcome = new H1("Tervetuloa, " + username + "!");

    self.add(welcome);
  }
}
```

## Ehdollinen renderöinti roolien mukaan {#conditional-rendering-based-on-roles}

Näytä tai piilota käyttöliittymän elementtejä käyttäjien roolien mukaan hallitaksesi, mitä ominaisuuksia käyttäjät näkevät:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Tarkista tietty rooli
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Ehdollisesti lisää vain adminille tarkoitettu painike
    if (isAdmin) {
      Button adminPanel = new Button("Admin-paneeli");
      adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
      self.add(adminPanel);
    }
  }
}
```
