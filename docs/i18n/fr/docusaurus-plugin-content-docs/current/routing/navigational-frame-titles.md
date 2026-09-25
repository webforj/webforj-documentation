---
sidebar_position: 10
title: Titres de cadre de navigation
description: >-
  Set browser frame titles per route with the @FrameTitle annotation or generate
  them dynamically using HasFrameTitle.
_i18n_hash: 7b190f89d8eeb58df6d8a25ce863cc5e
---
Dans webforJ, toutes les routes sont rendues dans un Frame, qui sert de conteneur de niveau supérieur responsable de l'affichage du contenu de la route actuelle. À mesure que les utilisateurs naviguent entre les différentes routes, le titre du Frame est mis à jour dynamiquement pour refléter la vue active, aidant à fournir un contexte clair sur la localisation actuelle de l'utilisateur dans l'application.

Le titre d'un frame peut être défini soit statiquement à l'aide d'annotations, soit dynamiquement par le code à l'exécution. Cette approche flexible permet aux développeurs de définir des titres qui s'alignent avec l'objectif de chaque vue, tout en s'adaptant à des scénarios ou paramètres spécifiques selon les besoins.

## Titre du Frame avec annotations {#frame-title-with-annotations}

La façon la plus simple de définir le titre d'un frame dans une vue est d'utiliser l'annotation `@FrameTitle`. Cette annotation permet de définir un titre statique pour tout composant de route, qui est ensuite appliqué au frame lorsque le composant est rendu.

### Utilisation de l'annotation `@FrameTitle` {#using-the-frametitle-annotation}

L'annotation `@FrameTitle` est appliquée au niveau de la classe et vous permet de spécifier une valeur de chaîne qui représente le titre de la page. Lorsque le routeur navigue vers un composant avec cette annotation, le titre spécifié sera automatiquement défini pour la fenêtre du navigateur.

Voici un exemple :

```java
@Route
@FrameTitle("Tableau de bord")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // logique de la vue
  }
}
```

Dans cet exemple :
- La classe `DashboardView` est annotée avec `@Route` pour définir la route.
- L'annotation `@FrameTitle("Tableau de bord")` définit le titre du frame sur "Tableau de bord".
- Lorsque l'utilisateur navigue vers `/dashboard`, le titre du frame sera automatiquement mis à jour avec la valeur spécifiée.

Cette méthode est utile pour les routes qui ont un titre statique et ne nécessitent pas de mises à jour fréquentes en fonction du contexte de la route.

:::tip `@AppTitle` et `@FrameTitle`
Si le titre de l'application est défini, le titre du frame l'incorporera. Par exemple, si l'application définit le titre avec `@AppTitle("webforJ")` et que le titre du frame est défini avec `@FrameTitle("Tableau de bord")`, le titre final de la page sera `Tableau de bord - webforJ`. Vous pouvez personnaliser le format du titre final dans l'annotation `@AppTitle` en utilisant l'attribut `format` si nécessaire.
:::

## Titres de frame dynamiques {#dynamic-frame-titles}

Dans les cas où le titre du frame doit changer dynamiquement en fonction de l'état de l'application ou des paramètres de route, webforJ fournit une interface appelée `HasFrameTitle`. Cette interface permet aux composants de fournir un titre de frame en fonction du contexte de navigation actuel et des paramètres de route.

### Mise en œuvre de l'interface `HasFrameTitle` {#implementing-the-hasframetitle-interface}

L'interface `HasFrameTitle` contient une méthode unique `getFrameTitle()`, qui est invoquée avant que le titre du frame ne soit mis à jour. Cette méthode offre la flexibilité de générer un titre dynamiquement en fonction du contexte de navigation ou d'autres facteurs dynamiques.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Page de Profil"));
  }

  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Définir dynamiquement le titre du frame en utilisant les paramètres de route
    String userId = parameters.get("id").orElse("Inconnu");
    return "Profil - Utilisateur " + userId;
  }
}
```

Dans cet exemple :
- Le composant `ProfileView` implémente l'interface `HasFrameTitle`.
- La méthode `getFrameTitle()` génère dynamiquement un titre en utilisant le paramètre `id` de l'URL.
- Si la route est `/profile/123`, le titre sera mis à jour sur "Profil - Utilisateur 123".

:::tip Combinaison d'annotations et de titres dynamiques
Vous pouvez combiner des méthodes statiques et dynamiques. Si un composant de route possède à la fois une annotation `@FrameTitle` et implémente l'interface `HasFrameTitle`, le titre fourni dynamiquement par `getFrameTitle()` aura la priorité sur la valeur statique de l'annotation.
:::
