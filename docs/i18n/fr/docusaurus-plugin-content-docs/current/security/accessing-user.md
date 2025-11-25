---
title: Accéder à l'utilisateur
sidebar_position: 4
_i18n_hash: 5a29cabdc472de49bcc1db895b982485
---
Spring Security stocke les informations de l'utilisateur authentifié dans le `SecurityContextHolder`, fournissant l'accès au nom d'utilisateur, aux rôles et aux autorisations dans toute votre application. Cette section montre comment récupérer et utiliser ces informations dans les vues et composants de webforJ.

## Obtenir les informations de l'utilisateur actuel {#get-current-user-information}

Spring Security stocke les informations de l'utilisateur authentifié dans le `SecurityContextHolder`, qui fournit un accès sécurisé par thread à n'importe quel endroit de votre application :

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

// Obtenir l'authentification actuelle
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// Obtenir le nom d'utilisateur
String username = authentication.getName();

// Obtenir les autorités (rôles)
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// Vérifier si l'utilisateur a un rôle spécifique
boolean isAdmin = authorities.stream()
  .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
```

Le `SecurityContextHolder` est le mécanisme central de Spring Security pour accéder aux détails d'authentification de l'utilisateur actuel. Il fonctionne dans toute votre application, y compris dans les constructeurs et méthodes de vue de webforJ.

## Afficher les informations de l'utilisateur dans les vues {#display-user-information-in-views}

Accédez directement aux informations de l'utilisateur dans vos vues webforJ pour afficher du contenu personnalisé :

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Afficher les informations de l'utilisateur
    H1 welcome = new H1("Bienvenue, " + username + "!");

    self.add(welcome);
  }
}
```

## Rendu conditionnel basé sur les rôles {#conditional-rendering-based-on-roles}

Montrez ou cachez des éléments UI en fonction des rôles des utilisateurs pour contrôler quelles fonctionnalités les utilisateurs voient :

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Vérifier un rôle spécifique
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Ajouter conditionnellement un bouton uniquement pour les administrateurs
    if (isAdmin) {
      Button adminPanel = new Button("Panneau Admin");
      adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
      self.add(adminPanel);
    }
  }
}
```
