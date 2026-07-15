---
title: Scopes
sidebar_position: 16
description: >-
  Use WebforjSessionScope, EnvironmentScope, and RouteScope to control bean
  lifetimes across sessions, browser tabs, and route hierarchies.
_i18n_hash: ea33564c3dec0bc26426440f3b448c53
---
<!-- vale off -->

# Scope <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring beheert de levenscyclus van beans via scopes. Elke scope definieert wanneer een bean wordt aangemaakt, hoe lang deze leeft en wanneer deze wordt vernietigd. Naast de standaard Spring-scopes voegt webforJ drie aangepaste scopes toe: `@WebforjSessionScope`, `@EnvironmentScope` en `@RouteScope`.

:::tip[Leer meer over Spring-scopes]
Voor uitgebreide informatie over het scoping-mechanisme van Spring en de standaard scopes, zie [de documentatie over Spring's bean scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Overzicht {#overview}

webforJ biedt drie aangepaste scopes die zijn ontworpen voor het beheer van de status van webapplicaties:

- **`@WebforjSessionScope`**: Beans die worden gedeeld over alle browser-tabbladen/vensters voor dezelfde gebruikerssessie. Perfect voor authenticatie, gebruikersvoorkeuren en winkelwagentjes.
- **`@EnvironmentScope`**: Beans die geïsoleerd zijn tot een enkel browser-tabblad/venster. Ideaal voor tab-specifieke werkstromen, formulierdata en onafhankelijke documentbewerking.
- **`@RouteScope`**: Beans die worden gedeeld binnen een route-hiërarchie. Handig voor navigatiestatus en gegevens die moeten worden gereset wanneer gebruikers tussen secties van de app navigeren.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Sessie scope {#session-scope}

De annotatie `@WebforjSessionScope` creëert beans die persistent zijn gedurende de gehele webforJ-sessie. In tegenstelling tot de [environment scope](#environment-scope) die beans isoleert per browservenster/tab, worden sessie-scoped beans gedeeld over alle vensters en tabbladen van dezelfde browser. Deze beans leven zolang de webforJ-sessie actief blijft, meestal totdat de gebruiker uitlogt of de sessie vervalt.

Sessie scope is ideaal voor authenticatiestatus, gebruikersvoorkeuren, winkelwagentjes en gegevens die persistent moeten blijven over meerdere browser-tabbladen maar geïsoleerd moeten blijven tussen verschillende gebruikers. Elke gebruikerssessie in de browser ontvangt zijn eigen instantie van sessie-scoped beans.

:::info Beans moeten Serializable zijn
Sessie-scoped beans moeten `Serializable` implementeren, aangezien ze worden opgeslagen in HTTP-sessie-attributen. Alle niet-transiënte velden moeten ook serialiseerbaar zijn (primitieven, `String` of klassen die `Serializable` implementeren). Markeer velden als `transient` als ze niet moeten worden gepersistent.
:::

Voeg `@WebforjSessionScope` toe aan elk Spring-component:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Authenticeer gebruiker
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Ongeldig maken van sessie
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Delen van de sessie over tabbladen {#session-sharing-across-tabs}

Sessie-scoped beans behouden de status over alle browservensters en tabbladen. Het openen van de app in meerdere tabbladen deelt dezelfde bean-instantie:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Gebruiker is al ingelogd vanuit een ander tabblad
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Inloggen");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // Gebruiker is nu ingelogd over alle tabbladen
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Zelfde AuthenticationService-instantie over alle tabbladen
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Toon gebruikersdashboard
  }
}
```

Wanneer een gebruiker inlogt via een tabblad, hebben alle andere tabbladen onmiddellijk toegang tot de geauthenticeerde status. Het openen van nieuwe tabbladen of vensters behoudt de ingelogde status. Uitloggen vanuit eender welk tabblad heeft invloed op alle tabbladen, aangezien ze dezelfde sessie-scoped bean delen.

## Environment scope {#environment-scope}

De annotatie `@EnvironmentScope` creëert beans die zolang leven als een browservenster- of tabblad-sessie. Wanneer een gebruiker de app opent in een browservenster of -tabblad, creëert webforJ een Environment. Elke bean die is gemarkeerd met `@EnvironmentScope` wordt één keer per browservenster/tab gemaakt en blijft beschikbaar totdat de gebruiker het tabblad sluit of de sessie vervalt.

Elke Environment vertegenwoordigt een geïsoleerd browservenster of tabblad. Environment-scoped beans kunnen niet worden gedeeld tussen verschillende browservensters of tabbladen omdat elk venster/tabblad zijn eigen instantie ontvangt.

Voeg `@EnvironmentScope` toe aan elk Spring-component:

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

De bean `TabWorkspace` behoudt de status gedurende de levensduur van een browservenster of tabblad. Elk browservenster/tabblad ontvangt een geïsoleerde instantie.

### Gebruik van environment-scoped beans {#using-environment-scoped-beans}

Routes ontvangen environment-scoped beans via constructor-injectie:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Laad document voor dit tabblad
    if (documentId == null) {
      // Maak nieuw document
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Zelfde TabWorkspace-instantie als EditorView in dit tabblad
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Voorbeeld van het document dat in dit tabblad wordt bewerkt
  }
}
```

Spring injecteert dezelfde `TabWorkspace`-instantie in beide views voor hetzelfde browservenster/tabblad. Navigatie tussen editor en preview behoudt de workspace-instantie. Als de gebruiker de app opent in een nieuw browservenster of -tabblad, ontvangt dat venster zijn eigen unieke `TabWorkspace`-instantie, waardoor onafhankelijke bewerking van verschillende documenten mogelijk is.

## Route scope {#route-scope}

De annotatie `@RouteScope` creëert beans die worden gedeeld binnen een route-hiërarchie. Navigeren naar `/admin/users` bouwt een component-hiërarchie met de beheerdersweergave als ouder en de gebruikersweergave als kind. Route-scoped beans worden eenmaal per hiërarchie geïnstantieerd en gedeeld tussen ouder- en kindcomponenten.

Route scope verschilt van environment scope in granulariteit. Terwijl environment-scoped beans bestaan voor de gehele browservenster/tab-sessie, bestaan route-scoped beans alleen terwijl de gebruiker binnen een specifieke route-hiërarchie blijft. Navigeren weg van de hiërarchie vernietigt de beans en terugkeren creëert nieuwe instanties. Deze scope is ideaal voor status die moet worden gereset wanneer gebruikers tussen verschillende secties van je app navigeren.

Voeg `@RouteScope` toe aan elk Spring-component:

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

### Route-hiërarchieën en delen {#route-hierarchies-and-sharing}

Routes vormen hiërarchieën via de parameter `outlet`. De ouderroute biedt een outlet waar de kindroutes worden weergegeven. Wanneer je een route definieert met een outlet, construeert webforJ een componentboom waarin de outlet-component de ouder wordt en de route-component het kind wordt. Deze ouder-kind-relatie bepaalt welke componenten route-scoped beans delen.

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Home");
    navState.addBreadcrumb("Admin");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {

  public UsersView(NavigationState navState) {
    // Zelfde NavigationState-instantie als AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Users");
  }
}
```

De `AdminView` en `UsersView` delen dezelfde `NavigationState`-instantie. De lay-out stelt de navigatiestructuur vast terwijl de view de actieve status bijwerkt. Navigatie buiten de `admin` sectie (naar `/public` bijvoorbeeld) vernietigt de huidige `NavigationState`-instantie en creëert een nieuwe voor de volgende hiërarchie.

De scope-grens volgt de structuur van de routeboom. Alle componenten van de wortel van een hiërarchie tot aan de bladeren delen dezelfde route-scoped bean-instanties. Navigeren naar zusterroutes binnen dezelfde hiërarchie behoudt de beans, terwijl navigatie naar niet-verwante hiërarchieën leidt tot het vernietigen en recreëren van beans.

### Scope-grenzen aanpassen met `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped beans worden standaard gedeeld vanaf de bovenste component. De annotatie `@SharedFrom` specificeert een alternatieve wortelcomponent. Deze annotatie verandert waar in de hiërarchie een bean beschikbaar wordt, zodat je de toegang tot specifieke subbomen van je routestructuur kunt beperken:

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

De bean is exclusief toegankelijk binnen `TeamSection` en zijn kindcomponenten:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // Bean wordt hier gemaakt
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Kan TeamContext niet injecteren - het is scoped naar TeamSection
    // Poging tot injectie resulteert in IllegalStateException
  }
}
```

De annotatie `@SharedFrom` handhaaft architectonische grenzen. Componenten buiten de gespecificeerde scope kunnen de bean niet bereiken. Wanneer Spring probeert een `@SharedFrom` bean in een component buiten zijn aangewezen hiërarchie te injecteren, mislukt de injectie met een `IllegalStateException`. Deze handhaving vindt plaats tijdens runtime wanneer de route wordt geopend, zodat beans correct binnen hun beoogde componentbomen blijven gescopeerd.

De annotatie accepteert één parameter: de componentklasse die als de wortel voor delen moet dienen. Alleen deze component en zijn afstammelingen in de routestructuur kunnen de bean bereiken. Bovenliggende componenten en zusterhiërarchieën kunnen deze niet injecteren.
