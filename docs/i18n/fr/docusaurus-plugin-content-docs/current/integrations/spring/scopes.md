---
title: Scopes
sidebar_position: 16
description: >-
  Use WebforjSessionScope, EnvironmentScope, and RouteScope to control bean
  lifetimes across sessions, browser tabs, and route hierarchies.
_i18n_hash: ea33564c3dec0bc26426440f3b448c53
---
# Scopes <DocChip chip='since' label='25.03' />

Spring gère le cycle de vie des beans à travers des scopes. Chaque scope définit quand un bean est créé, combien de temps il vit et quand il est détruit. En plus des scopes standard de Spring, webforJ ajoute trois scopes personnalisés : `@WebforjSessionScope`, `@EnvironmentScope` et `@RouteScope`.

:::tip[En savoir plus sur les scopes de Spring]
Pour une couverture complète du mécanisme de scoping de Spring et des scopes standard, consultez la [documentation sur les scopes des beans de Spring](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Overview {#overview}

webforJ propose trois scopes personnalisés conçus pour la gestion de l'état des applications web :

- **`@WebforjSessionScope`** : Beans partagés entre tous les onglets/fenêtres du même utilisateur. Parfait pour l'authentification, les préférences utilisateur et les paniers d'achat.
- **`@EnvironmentScope`** : Beans isolés à un seul onglet/fenêtre du navigateur. Idéal pour les flux de travail spécifiques aux onglets, les données de formulaire et l'édition de documents indépendants.
- **`@RouteScope`** : Beans partagés au sein d'une hiérarchie de route. Utile pour l'état de navigation et les données qui doivent se réinitialiser lorsque les utilisateurs naviguent entre les sections de l'application.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Session scope {#session-scope}

L'annotation `@WebforjSessionScope` crée des beans qui persistent pendant toute la session webforJ. Contrairement au [scope d'environnement](#environment-scope) qui isole les beans par fenêtre/onglet de navigateur, les beans à portée de session sont partagés entre toutes les fenêtres et onglets du même navigateur. Ces beans vivent tant que la session webforJ reste active, généralement jusqu'à ce que l'utilisateur se déconnecte ou que la session expire.

Le scope de session est idéal pour l'état d'authentification, les préférences utilisateur, les paniers d'achat et les données qui doivent persister à travers plusieurs onglets de navigateur tout en restant isolées entre différents utilisateurs. Chaque session de navigateur de l'utilisateur reçoit sa propre instance de beans à portée de session.

:::info Les beans doivent être sérialisables
Les beans à portée de session doivent implémenter `Serializable` car ils sont stockés dans des attributs de session HTTP. Tous les champs non transitoires doivent également être sérialisables (primitives, `String` ou classes implémentant `Serializable`). Marquez les champs comme `transient` s'ils ne doivent pas être persistés.
:::

Ajoutez `@WebforjSessionScope` à tout composant Spring :

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Authentifier l'utilisateur
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Invalider la session
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Session sharing across tabs {#session-sharing-across-tabs}

Les beans à portée de session maintiennent l'état à travers toutes les fenêtres et onglets du navigateur. Ouvrir l'application dans plusieurs onglets partage la même instance de bean :

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // L'utilisateur est déjà connecté depuis un autre onglet
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Connexion");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // L'utilisateur est maintenant connecté à travers tous les onglets
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Même instance de AuthenticationService à travers tous les onglets
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Afficher le tableau de bord de l'utilisateur
  }
}
```

Lorsque l'utilisateur se connecte via un onglet, tous les autres onglets ont immédiatement accès à l'état authentifié. Ouvrir de nouveaux onglets ou fenêtres maintient l'état de connexion. Se déconnecter d'un onglet affecte tous les onglets, car ils partagent le même bean à portée de session.

## Environment scope {#environment-scope}

L'annotation `@EnvironmentScope` crée des beans qui vivent pendant la durée d'une session de fenêtre ou d'onglet du navigateur. Lorsqu'un utilisateur ouvre l'application dans une fenêtre ou un onglet de navigateur, webforJ crée un Environment. Tout bean marqué avec `@EnvironmentScope` est créé une seule fois par fenêtre/onglet de navigateur et reste disponible jusqu'à ce que l'utilisateur ferme l'onglet ou que la session expire.

Chaque Environment représente une fenêtre ou un onglet de navigateur isolé. Les beans à portée d'environnement ne peuvent pas être partagés entre différentes fenêtres ou onglets de navigateur, car chaque fenêtre/onglet reçoit sa propre instance.

Ajoutez `@EnvironmentScope` à tout composant Spring :

```java title="TabWorkspace.java" {2}
@Component
@EnvironmentScope
public class TabWorkspace {
  private String documentId;
  private Map<String, Object> workspaceData = new HashMap<>();

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setWorkspaceData(String key, Object value) {
    workspaceData.put(key, value);
  }

  public Object getWorkspaceData(String key) {
    return workspaceData.get(key);
  }
}
```

Le bean `TabWorkspace` maintient l'état tout au long de la durée de vie d'une fenêtre ou d'un onglet de navigateur. Chaque fenêtre/onglet de navigateur reçoit une instance isolée.

### Using environment-scoped beans {#using-environment-scoped-beans}

Les routes reçoivent des beans à portée d'environnement par injection de constructeur :

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Charger le document pour cet onglet
    if (documentId == null) {
      // Créer un nouveau document
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Même instance de TabWorkspace que dans EditorView pour cet onglet
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Prévisualiser le document en cours d'édition dans cet onglet
  }
}
```

Spring injecte la même instance de `TabWorkspace` dans les deux vues pour la même fenêtre/onglet de navigateur. La navigation entre l'éditeur et la prévisualisation préserve l'instance de l'espace de travail. Si l'utilisateur ouvre l'application dans une nouvelle fenêtre ou un nouvel onglet de navigateur, cette fenêtre reçoit sa propre instance distincte de `TabWorkspace`, permettant un édition indépendante de différents documents.

## Route scope {#route-scope}

L'annotation `@RouteScope` crée des beans partagés au sein d'une hiérarchie de route. La navigation vers `/admin/users` construit une hiérarchie de composants avec la vue d'administration comme parent et la vue des utilisateurs comme enfant. Les beans à portée de route sont instanciés une fois par hiérarchie et partagés entre les composants parents et enfants.

Le scope de route diffère du scope d'environnement en granularité. Alors que les beans à portée d'environnement existent pendant toute la session de la fenêtre/onglet de navigateur, les beans à portée de route existent seulement tant que l'utilisateur reste au sein d'une hiérarchie de route spécifique. Naviguer en dehors de la hiérarchie détruit les beans, et y revenir crée de nouvelles instances. Ce scope est idéal pour l'état qui doit se réinitialiser lorsque les utilisateurs naviguent entre différentes sections de votre application.

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

### Route hierarchies and sharing {#route-hierarchies-and-sharing}

Les routes forment des hiérarchies à travers le paramètre `outlet`. La route parent fournit une sortie où les routes enfants se rendent. Lorsque vous définissez une route avec une sortie, webforJ construit un arbre de composants où le composant d'outlet devient le parent et le composant de route devient l'enfant. Cette relation parent-enfant détermine quels composants partagent des beans à portée de route.

```java {11}
@Route
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

Les vues `AdminView` et `UsersView` partagent la même instance de `NavigationState`. La mise en page établit la structure de navigation tandis que la vue met à jour l'état actif. Navigation en dehors de la section `admin` (vers `/public` par exemple) détruit l'instance actuelle de `NavigationState` et en crée une nouvelle pour la hiérarchie suivante.

La frontière du scope suit la structure de l'arbre de routes. Tous les composants de la racine d'une hiérarchie jusqu'aux feuilles partagent les mêmes instances de beans à portée de route. La navigation vers des routes parentes au sein de la même hiérarchie préserve les beans, tandis que la navigation vers des hiérarchies non liées déclenche la destruction et la recréation des beans.

### Customizing scope boundaries with `@SharedFrom` {#customizing-scope-boundaries}

Les beans à portée de route sont partagés par défaut à partir du composant le plus élevé. L'annotation `@SharedFrom` spécifie un composant racine alternatif. Cette annotation change où dans la hiérarchie un bean devient disponible, vous permettant de restreindre l'accès à des sous-arbres spécifiques de votre structure de route :

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
    // Impossible d'injecter TeamContext - il est scoped à TeamSection
    // La tentative d'injection génère IllegalStateException
  }
}
```

L'annotation `@SharedFrom` impose des frontières architecturales. Les composants en dehors de la portée spécifiée ne peuvent pas accéder au bean. Lorsque Spring tente d'injecter un bean `@SharedFrom` dans un composant en dehors de sa hiérarchie désignée, l'injection échoue avec une `IllegalStateException`. Cette contrainte se produit à l'exécution lorsque la route est accessible, de sorte que les beans restent correctement scoped à leurs arbres de composants prévus.

L'annotation accepte un seul paramètre : la classe de composant qui doit servir de racine pour le partage. Seul ce composant et ses descendants dans la hiérarchie de routes peuvent accéder au bean. Les composants parents et les hiérarchies de pairs ne peuvent pas l'injecter.
