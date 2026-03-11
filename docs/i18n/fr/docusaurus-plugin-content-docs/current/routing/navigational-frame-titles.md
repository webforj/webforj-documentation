---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 9d594a84516af29dde3f66726bc22825
---
Dans webforJ, toutes les routes sont rendues au sein d'un Frame, qui sert de conteneur de haut niveau responsable de l'affichage du contenu de la route actuelle. À mesure que les utilisateurs naviguent entre les différentes routes, le titre du Frame est mis à jour dynamiquement pour refléter la vue active, aidant ainsi à fournir un contexte clair sur l'emplacement actuel de l'utilisateur dans l'application.

Le titre d'un frame peut être défini soit statiquement à l'aide d'annotations, soit dynamiquement par le biais du code à l'exécution. Cette approche flexible permet aux développeurs de définir des titres qui s'alignent sur l'objectif de chaque vue, tout en s'adaptant aux scénarios ou paramètres spécifiques si nécessaire.

## Titre du frame avec annotations {#frame-title-with-annotations}

La méthode la plus simple pour définir le titre d'un frame dans une vue est d'utiliser l'annotation `@FrameTitle`. Cette annotation vous permet de définir un titre statique pour tout composant de route, qui est ensuite appliqué au frame lors du rendu du composant.

### Utilisation de l'annotation `@FrameTitle` {#using-the-frametitle-annotation}

L'annotation `@FrameTitle` est appliquée au niveau de la classe et vous permet de spécifier une valeur de chaîne qui représente le titre de la page. Lorsque le routeur navigue vers un composant avec cette annotation, le titre spécifié sera automatiquement défini pour la fenêtre du navigateur.

Voici un exemple :

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // logique de vue
  }
}
```

Dans cet exemple :
- La classe `DashboardView` est annotée avec `@Route` pour définir la route.
- L'annotation `@FrameTitle("Dashboard")` définit le titre du frame sur "Dashboard".
- Lorsque l'utilisateur navigue vers `/dashboard`, le titre du frame sera automatiquement mis à jour avec la valeur spécifiée.

Cette méthode est utile pour les routes qui ont un titre statique et ne nécessitent pas de mises à jour fréquentes en fonction du contexte de la route.

:::tip `@AppTitle` et `@FrameTitle`  
Si le titre de l'application est défini, le titre du frame l'incorporera. Par exemple, si l'application définit le titre comme `@AppTitle("webforJ")` et le titre du frame est défini comme `@FrameTitle("Dashboard")`, le titre final de la page sera `Dashboard - webforJ`. Vous pouvez personnaliser le format du titre final dans l'annotation `@AppTitle` en utilisant l'attribut `format` si nécessaire.  
:::

## Titres de frame dynamiques {#dynamic-frame-titles}

Dans les cas où le titre du frame doit changer dynamiquement en fonction de l'état de l'application ou des paramètres de route, webforJ fournit une interface appelée `HasFrameTitle`. Cette interface permet aux composants de fournir un titre de frame basé sur le contexte de navigation actuel et les paramètres de route.

### Implémentation de l'interface `HasFrameTitle` {#implementing-the-hasframetitle-interface}

L'interface `HasFrameTitle` contient une méthode unique `getFrameTitle()`, qui est invoquée avant que le titre du frame ne soit mis à jour. Cette méthode offre la flexibilité de générer un titre dynamiquement en fonction du contexte de navigation ou d'autres facteurs dynamiques.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profile Page"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Définir dynamiquement le titre du frame à l'aide des paramètres de route
    String userId = parameters.get("id").orElse("Unknown");
    return "Profile - User " + userId;
  }
}
```

Dans cet exemple :
- Le composant `ProfileView` implémente l'interface `HasFrameTitle`.
- La méthode `getFrameTitle()` génère dynamiquement un titre en utilisant le paramètre `id` de l'URL.
- Si la route est `/profile/123`, le titre sera mis à jour en "Profile - User 123".

:::tip Combinaison d'annotations et de titres dynamiques
Vous pouvez combiner des méthodes statiques et dynamiques. Si un composant de route a à la fois une annotation `@FrameTitle` et implémente l'interface `HasFrameTitle`, le titre fourni dynamiquement par `getFrameTitle()` prévaudra sur la valeur statique de l'annotation.
:::
