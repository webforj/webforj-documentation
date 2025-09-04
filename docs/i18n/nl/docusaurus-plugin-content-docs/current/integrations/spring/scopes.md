---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 79be5bc5e8982111f185b0f474a1f746
---
<!-- vale off -->

# Scopes <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring beheert de levenscyclus van beans via scopes. Elke scope definieert wanneer een bean wordt aangemaakt, hoe lang deze leeft en wanneer deze wordt vernietigd. Naast standaard Spring scopes voegt webforJ drie aangepaste scopes toe: `@WebforjSessionScope`, `@EnvironmentScope` en `@RouteScope`.

:::tip[Leer meer over Spring scopes]
Voor een uitgebreide dekking van de scoping-mechanismen van Spring en standaard scopes, zie [de documentatie over Spring's bean scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Overzicht

webforJ biedt drie aangepaste scopes die ontworpen zijn voor het beheer van de status van webapps:

- **`@WebforjSessionScope`**: Beans die gedeeld worden over alle browsertabs/windows voor dezelfde gebruikerssessie. Perfect voor authenticatie, gebruikersvoorkeuren en winkelwagentjes.
- **`@EnvironmentScope`**: Beans die geïsoleerd zijn voor een enkele browsertab/window. Ideaal voor tab-specifieke workflows, formuliergegevens en onafhankelijke documentbewerking.
- **`@RouteScope`**: Beans die gedeeld worden binnen een routehiërarchie. Handig voor navigatiestatus en gegevens die moeten worden gereset wanneer gebruikers tussen verschillende secties van de app navigeren.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Sessie scope {#session-scope}

De annotatie `@WebforjSessionScope` creëert beans die bestaan tijdens de gehele webforJ-sessie. In tegenstelling tot [environment scope](#environment-scope), die beans isoleert per browsertab/window, worden sessie-scoped beans gedeeld over alle vensters en tabs van dezelfde browser. Deze beans leven zo lang als de webforJ-sessie actief blijft, doorgaans totdat de gebruiker uitlogt of de sessie verloopt.

Sessie scope is ideaal voor authenticatiestatus, gebruikersvoorkeuren en winkelwagentjes. Gegevens die moeten blijven bestaan over meerdere browsertabs maar geïsoleerd moeten zijn tussen verschillende gebruikers. Elke gebruikerssessie in de browser ontvangt zijn eigen instantie van sessie-scoped beans.

:::info Beans moeten serialiseerbaar zijn
Sessie-scoped beans moeten `Serializable` implementeren, omdat ze worden opgeslagen in HTTP-sessie-attributen. Alle niet-transiënte velden moeten ook serialiseerbaar zijn (primitieven, `String` of klassen die `Serializable` implementeren). Markeer velden als `transient` als ze niet moeten worden behouden.
:::

Voeg `@WebforjSessionScope` toe aan een Spring-component:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Authenticate user
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Invalidate session
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Sessie delen over tabs {#session-sharing-across-tabs}

Sessie-scoped beans behouden de status over alle browservenners en -tabs. Het openen van de app in meerdere tabs deelt dezelfde bean-instantie:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Gebruiker is al ingelogd vanuit een andere tab
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Login");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // Gebruiker is nu ingelogd over alle tabs
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Zelfde AuthenticationService-instantie over alle tabs
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Toon gebruikersdashboard
  }
}
```

Wanneer een gebruiker inlogt via één tab, hebben alle andere tabs onmiddellijk toegang tot de geverifieerde status. Het openen van nieuwe tabs of vensters behoudt de ingelogde status. Uitloggen vanuit eender welke tab beïnvloedt alle tabs, aangezien ze dezelfde sessie-scoped bean delen.

## Environment scope {#environment-scope}

De annotatie `@EnvironmentScope` creëert beans die leven voor de duur van een browservenster of tab sessie. Wanneer een gebruiker de app opent in een browservenster of -tab, creëert webforJ een omgeving. Elke bean gemarkeerd met `@EnvironmentScope` wordt één keer per browservenster/tab aangemaakt en blijft beschikbaar totdat de gebruiker de tab sluit of de sessie verloopt.

Elke omgeving vertegenwoordigt een geïsoleerd browserverw/n met tab. Environment-scoped beans kunnen niet worden gedeeld tussen verschillende browserverw/n of tabs omdat elk venster/tab zijn eigen instantie ontvangt.

Voeg `@EnvironmentScope` toe aan een Spring-component:

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

De bean `TabWorkspace` behoudt de status gedurende de levensduur van een browservenster of -tab. Elke browserverw/tab ontvangt een geïsoleerde instantie.

### Gebruik van environment-scoped beans {#using-environment-scoped-beans}

Routes ontvangen environment-scoped beans via constructor-injectie:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Laad document voor deze tab
    if (documentId == null) {
      // Maak nieuw document
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Zelfde TabWorkspace-instantie als EditorView in deze tab
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Voorvertoning van het document dat in deze tab wordt bewerkt
  }
}
```

Spring injecteert dezelfde `TabWorkspace`-instantie in beide views voor hetzelfde browservenster/tab. Navigeren tussen bewerker en voorvertoning behoudt de werkruimte-instantie. Als de gebruiker de app in een nieuw browservenster of -tab opent, ontvangt dat venster zijn eigen distincte `TabWorkspace`-instantie, wat onafhankelijke bewerking van verschillende documenten mogelijk maakt.

## Route scope {#route-scope}

De annotatie `@RouteScope` creëert beans die worden gedeeld binnen een routehiërarchie. Navigeren naar `/admin/users` bouwt een componenthiërarchie met de admin-view als ouder en de users-view als kind. Route-scoped beans worden één keer per hiërarchie geïnstantieerd en gedeeld tussen ouder- en kindcomponenten.

Route scope verschilt van environment scope in granulariteit. Terwijl environment-scoped beans bestaan voor de gehele browservenster/tab-sessie, bestaan route-scoped beans alleen zolang de gebruiker binnen een specifieke routehiërarchie blijft. Navigeren buiten de hiërarchie vernietigt de beans, en terugkeren creëert nieuwe instanties. Deze scope is ideaal voor status die moet worden gereset wanneer gebruikers tussen verschillende secties van uw app navigeren.

Voeg `@RouteScope` toe aan een Spring-component:

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

### Routehiërarchieën en delen {#route-hierarchies-and-sharing}

Routes vormen hiërarchieën via de parameter `outlet`. De ouderroute biedt een outlet waar kindroutes worden gerenderd. Wanneer u een route met een outlet definieert, bouwt webforJ een componentenboom waarin de outletcomponent de ouder wordt en de routecomponent het kind wordt. Deze ouder-kindrelatie bepaalt welke componenten route-scoped beans delen.

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

De `AdminView` en `UsersView` delen dezelfde `NavigationState`-instantie. De lay-out stelt de navigatiestructuur vast terwijl de weergave de actieve status bijwerkt. Navigeren buiten de `admin` sectie (bijvoorbeeld naar `/public`) vernietigt de huidige `NavigationState`-instantie en creëert een nieuwe voor de volgende hiërarchie.

De scopegrens volgt de structuur van de routeboom. Alle componenten van de wortel van een hiërarchie tot de bladeren delen dezelfde route-scoped bean-instanties. Navigeren naar gelijke routes binnen dezelfde hiërarchie behoudt de beans, terwijl navigeren naar niet-verwante hiërarchieën leidt tot vernietiging en recreatie van de beans.

### Scopegrenzen aanpassen met `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped beans worden standaard gedeeld vanaf de bovenste component. De annotatie `@SharedFrom` specificeert een alternatieve rootcomponent. Deze annotatie verandert waar in de hiërarchie een bean beschikbaar wordt, zodat u de toegang tot specifieke subtrees van uw routestructuur kunt beperken:

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

De bean is uitsluitend toegankelijk binnen `TeamSection` en zijn kindcomponenten:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // Bean wordt hier aangemaakt
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Kan TeamContext niet injecteren - het is scoped naar TeamSection
    // Poging tot injectie leidt tot IllegalStateException
  }
}
```

De annotatie `@SharedFrom` handhaaft architecturale grenzen. Componenten buiten de opgegeven scope kunnen geen toegang verkrijgen tot de bean. Wanneer Spring probeert een `@SharedFrom` bean in een component buiten zijn aangewezen hiërarchie te injecteren, mislukt de injectie met een `IllegalStateException`. Deze handhaving vindt plaats tijdens runtime wanneer de route wordt benaderd, zodat beans goed binnen hun bedoelde componentbomen blijven. 

De annotatie accepteert één parameter - de componentklasse die als de root voor delen moet fungeren. Alleen deze component en zijn nakomelingen in de routestructuur kunnen toegang verkrijgen tot de bean. Bovenliggende componenten en broederhiërarchieën kunnen het niet injecteren.
