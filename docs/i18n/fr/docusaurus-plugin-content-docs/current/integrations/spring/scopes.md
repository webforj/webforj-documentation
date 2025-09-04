---
title: Scopes
sidebar_position: 16
_i18n_hash: 96d36a28887e551ba3f9acab0d7059bc
---
<!-- vale off -->
# Scopes <DocChip chip='since' label='25.03' />
<!-- vale on -->

Spring gère le cycle de vie des beans à travers des portées. Chaque portée définit quand un bean est créé, combien de temps il vit et quand il est détruit. webforJ ajoute deux portées personnalisées - `@EnvironmentScope` et `@RouteScope` - qui correspondent à la façon dont les applications webforJ gèrent les sessions de navigateur et la navigation.

:::tip[En savoir plus sur les portées Spring]
Pour une couverture complète du mécanisme de portée de Spring et des portées standard, consultez la [documentation des portées de beans de Spring](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Portée d'environnement {#environment-scope}

L'annotation `@EnvironmentScope` crée des beans qui vivent pendant la durée d'une session de fenêtre ou d'onglet de navigateur. Quand un utilisateur ouvre l'application dans une fenêtre ou un onglet de navigateur, webforJ crée un Environnement. Tout bean marqué avec `@EnvironmentScope` est créé une fois par fenêtre/onglet de navigateur et reste disponible jusqu'à ce que l'utilisateur ferme l'onglet ou que la session expire.

Chaque Environnement représente une fenêtre ou un onglet de navigateur isolé. Les beans de portée environnementale ne peuvent pas être partagés entre différentes fenêtres ou onglets de navigateur - chaque fenêtre/onglet reçoit sa propre instance.

Ajoutez `@EnvironmentScope` à tout composant Spring :

```java title="UserSession.java" {2}
@Component
@EnvironmentScope
public class UserSession {
  private String userId;
  private Map<String, Object> attributes = new HashMap<>();
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }
  
  public Object getAttribute(String key) {
    return attributes.get(key);
  }
}
```

Le bean `UserSession` maintient l'état tout au long de la durée de vie d'une fenêtre ou d'un onglet de navigateur. Chaque fenêtre/onglet de navigateur reçoit une instance isolée.

### Utilisation des beans à portée environnementale {#using-environment-scoped-beans}

Les routes reçoivent des beans à portée environnementale par injection de constructeur :

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  public DashboardView(UserSession session) {
    String userId = session.getUserId();
    // Utilisez les données de session
    if (userId == null) {
      // Rediriger vers la connexion
    }
  }
}

@Route("/profile")
public class ProfileView extends Composite<Div> {
  
  public ProfileView(UserSession session) {
    // Même instance de UserSession que DashboardView
    session.setAttribute("lastView", "profile");
  }
}
```

Spring injecte la même instance de `UserSession` dans les deux vues pour la même fenêtre/onglet de navigateur. La navigation entre le tableau de bord et le profil préserve l'instance de session. Si l'utilisateur ouvre l'application dans une nouvelle fenêtre ou un nouvel onglet de navigateur, cette fenêtre reçoit sa propre instance distincte de `UserSession`.

## Portée de route {#route-scope}

L'annotation `@RouteScope` crée des beans partagés au sein d'une hiérarchie de routes. La navigation vers `/admin/users` construit une hiérarchie de composants avec la vue administrateur comme parent et la vue utilisateurs comme enfant. Les beans de portée de route sont instanciés une fois par hiérarchie et partagés entre les composants parents et enfants.

La portée de route diffère de la portée environnementale en granularité. Alors que les beans à portée environnementale existent pendant toute la durée de la session de fenêtre/onglet de navigateur, les beans à portée de route existent uniquement tant que l'utilisateur reste dans une hiérarchie de route spécifique. Naviguer en dehors de la hiérarchie détruit les beans, et revenir crée de nouvelles instances. Cette portée est idéale pour les états qui doivent être réinitialisés lorsque les utilisateurs naviguent entre différentes sections de votre application.

Ajoutez `@RouteScope` à tout composant Spring :

```java title="NavigationState" {2}
@Component
@RouteScope
public class NavigationState {
  private String activeTab;
  private List<String> breadcrumbs = new ArrayList<>();
  
  public void setActiveTab(String tab) {
    this.activeTab = tab;
  }
  
  public void addBreadcrumb(String crumb) {
    breadcrumbs.add(crumb);
  }
  
  public List<String> getBreadcrumbs() {
    return Collections.unmodifiableList(breadcrumbs);
  }
}
```

### Hiérarchies de route et partage {#route-hierarchies-and-sharing}

Les routes forment des hiérarchies grâce au paramètre `outlet`. La route parent fournit un outlet où les routes enfants se rendent. Lorsque vous définissez une route avec un outlet, webforJ construit un arbre de composants où le composant outlet devient le parent et le composant de route devient l'enfant. Cette relation parent-enfant détermine quels composants partagent des beans à portée de route.

```java
@Route("/admin")
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Accueil");
    navState.addBreadcrumb("Admin");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {
  
  public UsersView(NavigationState navState) {
    // Même instance de NavigationState que AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Utilisateurs");
  }
}
```

Les `AdminView` et `UsersView` partagent la même instance de `NavigationState`. La mise en page établit la structure de navigation tandis que la vue met à jour l'état actif. La navigation en dehors de la section `admin` (vers `/public` par exemple) détruit l'instance actuelle de `NavigationState` et crée une nouvelle pour la hiérarchie suivante.

La frontière de portée suit la structure de l'arbre de route. Tous les composants de la racine d'une hiérarchie jusqu'aux feuilles partagent les mêmes instances de beans à portée de route. La navigation vers des routes sœurs au sein de la même hiérarchie préserve les beans, tandis que la navigation vers des hiérarchies non liées déclenche la destruction et la recréation des beans.

### Personnaliser les limites de portée avec `@SharedFrom` {#customizing-scope-boundaries}

Les beans à portée de route sont partagés par le composant le plus haut par défaut. L'annotation `@SharedFrom` spécifie un composant racine alternatif. Cette annotation change l'endroit dans la hiérarchie où un bean devient disponible, vous permettant de restreindre l'accès à des sous-arbres spécifiques de votre structure de routes :

```java title="TeamContext" {2,3}
@Component
@RouteScope
@SharedFrom(TeamSection.class)
public class TeamContext {
  private String teamId;
  private List<String> permissions = new ArrayList<>();
  
  public void setTeamId(String id) {
    this.teamId = id;
  }
  
  public String getTeamId() {
    return teamId;
  }
}
```

Le bean est accessible exclusivement au sein de `TeamSection` et de ses composants enfants :

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {
  
  public TeamSection(TeamContext context) {
    // Bean créé ici
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Impossible d'injecter TeamContext - il est limité à TeamSection
    // Tenter une injection déclenche IllegalStateException
  }
}
```

L'annotation `@SharedFrom` impose des limites architecturales. Les composants en dehors de la portée spécifiée ne peuvent pas accéder au bean. Lorsque Spring tente d'injecter un bean `@SharedFrom` dans un composant en dehors de sa hiérarchie désignée, l'injection échoue avec un `IllegalStateException`. Cette application se produit à l'exécution lorsque la route est accédée, de sorte que les beans restent correctement limités à leurs arbres de composants prévus.

L'annotation accepte un seul paramètre - la classe de composant qui doit servir de racine pour le partage. Seul ce composant et ses descendants dans la hiérarchie de route peuvent accéder au bean. Les composants parents et les hiérarchies sœurs ne peuvent pas l'injecter.
