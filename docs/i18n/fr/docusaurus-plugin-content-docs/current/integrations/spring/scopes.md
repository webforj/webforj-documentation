---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 8c977fdef41f6125ac21239e7e397f4d
---
<!-- vale off -->

# Domaines <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring gère le cycle de vie des beans à travers des domaines. Chaque domaine définit quand un bean est créé, combien de temps il vit et quand il est détruit. En plus des domaines Spring standard, webforJ ajoute trois domaines personnalisés : `@WebforjSessionScope`, `@EnvironmentScope` et `@RouteScope`.

:::tip[En savoir plus sur les domaines Spring]
Pour une couverture complète du mécanisme de découpage de Spring et des domaines standard, consultez [la documentation sur les domaines de beans de Spring](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Vue d'ensemble

webforJ fournit trois domaines personnalisés conçus pour la gestion de l'état des applications web :

- **`@WebforjSessionScope`** : Beans partagés entre tous les onglets/fenêtres du même utilisateur pour la même session. Parfait pour l'authentification, les préférences utilisateur et les paniers d'achat.
- **`@EnvironmentScope`** : Beans isolés dans un seul onglet/fenêtre de navigateur. Idéal pour des flux de travail spécifiques à un onglet, des données de formulaires et l'édition de documents indépendants.
- **`@RouteScope`** : Beans partagés au sein d'une hiérarchie de routes. Utile pour l'état de navigation et les données qui doivent être réinitialisées lorsque les utilisateurs naviguent entre les sections de l'application.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Domaine de session {#session-scope}

L'annotation `@WebforjSessionScope` crée des beans qui persistent tout au long de la session webforJ. Contrairement au [domaine environnemental](#environment-scope) qui isole les beans par fenêtre/onglet de navigateur, les beans avec domaine de session sont partagés entre toutes les fenêtres et onglets du même navigateur. Ces beans vivent tant que la session webforJ reste active, généralement jusqu'à ce que l'utilisateur se déconnecte ou que la session expire.

Le domaine de session est idéal pour l'état d'authentification, les préférences utilisateur, les paniers d'achat et les données qui doivent persister entre plusieurs onglets de navigateur mais rester isolées entre différents utilisateurs. Chaque session de navigateur d'un utilisateur reçoit sa propre instance de beans de session.

:::info Les beans doivent être sérialisables
Les beans de domaine de session doivent implémenter `Serializable` car ils sont stockés dans les attributs de session HTTP. Tous les champs non transitoires doivent également être sérialisables (primitifs, `String` ou classes implémentant `Serializable`). Marquez les champs comme `transient` s'ils ne doivent pas être persistés.
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

### Partage de session entre les onglets {#session-sharing-across-tabs}

Les beans de domaine de session maintiennent l'état à travers toutes les fenêtres et onglets du navigateur. Ouvrir l'application dans plusieurs onglets partage la même instance de bean :

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
      // L'utilisateur est maintenant connecté sur tous les onglets
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Même instance d'AuthenticationService sur tous les onglets
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Afficher le tableau de bord utilisateur
  }
}
```

Lorsqu'un utilisateur se connecte par un onglet, tous les autres onglets ont immédiatement accès à l'état d'authentification. L'ouverture de nouveaux onglets ou fenêtres maintient l'état de connexion. Se déconnecter depuis n'importe quel onglet affecte tous les onglets, car ils partagent le même bean de domaine de session.

## Domaine environnemental {#environment-scope}

L'annotation `@EnvironmentScope` crée des beans qui vivent durant une session de fenêtre ou d'onglet de navigateur. Lorsqu'un utilisateur ouvre l'application dans une fenêtre ou un onglet de navigateur, webforJ crée un Environnement. Tout bean marqué avec `@EnvironmentScope` est créé une fois par fenêtre/onglet de navigateur et reste disponible jusqu'à ce que l'utilisateur ferme l'onglet ou que la session expire.

Chaque Environnement représente une fenêtre ou un onglet de navigateur isolé. Les beans de domaine environnemental ne peuvent pas être partagés entre différentes fenêtres ou onglets de navigateur car chaque fenêtre/onglet reçoit sa propre instance.

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

### Utilisation des beans de domaine environnemental {#using-environment-scoped-beans}

Les routes reçoivent des beans de domaine environnemental par injection par le constructeur :

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
    // Même instance de TabWorkspace que dans EditorView dans cet onglet
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Prévisualiser le document en cours d'édition dans cet onglet
  }
}
```

Spring injecte la même instance de `TabWorkspace` dans les deux vues pour la même fenêtre/onglet de navigateur. La navigation entre l'éditeur et la prévisualisation préserve l'instance de l'espace de travail. Si l'utilisateur ouvre l'application dans une nouvelle fenêtre ou un nouvel onglet de navigateur, cette fenêtre reçoit sa propre instance distincte de `TabWorkspace`, permettant l'édition indépendante de différents documents.

## Domaine de route {#route-scope}

L'annotation `@RouteScope` crée des beans partagés au sein d'une hiérarchie de routes. La navigation vers `/admin/users` construit une hiérarchie de composants avec la vue admin comme parent et la vue utilisateurs comme enfant. Les beans de domaine de route sont instanciés une fois par hiérarchie et partagés entre les composants parents et enfants.

Le domaine de route diffère du domaine environnemental en granularité. Alors que les beans de domaine environnemental existent pendant toute la session de fenêtre/onglet de navigateur, les beans de domaine de route existent uniquement tant que l'utilisateur reste dans une hiérarchie de route spécifique. Naviguer en dehors de la hiérarchie détruit les beans, et revenir crée de nouvelles instances. Ce domaine est idéal pour les états qui doivent être réinitialisés lorsque les utilisateurs naviguent entre différentes sections de votre application.

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

### Hiérarchies de routes et partage {#route-hierarchies-and-sharing}

Les routes forment des hiérarchies à travers le paramètre `outlet`. La route parent fournit une sortie où les routes enfants se rendent. Lorsque vous définissez une route avec une sortie, webforJ construit un arbre de composants où le composant de sortie devient le parent et le composant de route devient l'enfant. Cette relation parent-enfant détermine quels composants partagent des beans de domaine de route.

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

Les `AdminView` et `UsersView` partagent la même instance de `NavigationState`. La mise en page établit la structure de navigation tandis que la vue met à jour l'état actif. La navigation en dehors de la section `admin` (vers `/public` par exemple) détruit l'instance actuelle de `NavigationState` et en crée une nouvelle pour la hiérarchie suivante.

La frontière de domaine suit la structure de l'arbre de routes. Tous les composants de la racine d'une hiérarchie jusqu'aux feuilles partagent les mêmes instances de beans de domaine de route. La navigation vers des routes sœurs au sein de la même hiérarchie préserve les beans, tandis que naviguer vers des hiérarchies non liées déclenche la destruction et la recréation des beans.

### Personnaliser les frontières de domaine avec `@SharedFrom` {#customizing-scope-boundaries}

Les beans de domaine de route sont partagés par défaut depuis le composant le plus haut. L'annotation `@SharedFrom` spécifie un composant racine alternatif. Cette annotation change le point de la hiérarchie où un bean devient disponible, vous permettant de restreindre l'accès à des sous-arbres spécifiques de votre structure de route :

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
    // La tentative d'injection génère une IllegalStateException
  }
}
```

L'annotation `@SharedFrom` impose des frontières architecturales. Les composants en dehors de la portée spécifiée ne peuvent pas accéder au bean. Lorsque Spring tente d'injecter un bean `@SharedFrom` dans un composant en dehors de sa hiérarchie désignée, l'injection échoue avec une `IllegalStateException`. Cette enforcement se produit à l'exécution lorsque la route est accédée, de sorte que les beans restent correctement limités à leurs arbres de composants prévus.

L'annotation accepte un seul paramètre : la classe de composant qui doit servir de racine pour le partage. Seul ce composant et ses descendants dans la hiérarchie de route peuvent accéder au bean. Les composants parents et les hiérarchies sœurs ne peuvent pas l'injecter.
