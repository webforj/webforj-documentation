---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 8c977fdef41f6125ac21239e7e397f4d
---
<!-- vale off -->

# Scopes <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring beheert de levenscyclus van beans via scopes. Elke scope definieert wanneer een bean wordt aangemaakt, hoe lang deze leeft en wanneer deze wordt vernietigd. Naast de standaard Spring-scopes, voegt webforJ drie aangepaste scopes toe: `@WebforjSessionScope`, `@EnvironmentScope` en `@RouteScope`.

:::tip[Leer meer over Spring-scopes]
Voor uitgebreide informatie over de scopingmechanisme van Spring en standaardscopes, zie [Spring's bean scopes documentation](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Overzicht

webforJ biedt drie aangepaste scopes die zijn ontworpen voor het beheren van de status van web-apps:

- **`@WebforjSessionScope`**: Beans die worden gedeeld over alle browsertabbladen/vensters voor dezelfde gebruikerssessie. Perfect voor authenticatie, gebruikersvoorkeuren en winkelwagentjes.
- **`@EnvironmentScope`**: Beans die geïsoleerd zijn voor een enkel browsertabblad/venster. Ideaal voor tab-specifieke workflows, formuliergegevens en onafhankelijke documentbewerking.
- **`@RouteScope`**: Beans die worden gedeeld binnen een routehiërarchie. Handig voor navigatiestatus en gegevens die opnieuw moeten worden ingesteld wanneer gebruikers navigeren tussen secties van de app.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Sessie-scope {#session-scope}

De `@WebforjSessionScope` annotatie creëert beans die persistent zijn gedurende de hele webforJ-sessie. In tegenstelling tot [environment scope](#environment-scope) die beans isoleert per browsertabblad/venster, worden sessie-scoped beans gedeeld tussen alle vensters en tabbladen van dezelfde browser. Deze beans leven zolang de webforJ-sessie actief blijft, meestal totdat de gebruiker uitlogt of de sessie verloopt.

Sessie-scope is ideaal voor authenticatiestatus, gebruikersvoorkeuren, winkelwagentjes en gegevens die moeten blijven bestaan over meerdere browsertabbladen maar geïsoleerd moeten blijven tussen verschillende gebruikers. Elke gebruikersbrowser-sessie ontvangt zijn eigen instantie van sessie-scoped beans.

:::info Beans moeten serialiseerbaar zijn
Sessie-scoped beans moeten `Serializable` implementeren, aangezien ze worden opgeslagen in HTTP-sessie-attributen. Alle niet-transiënte velden moeten ook serialiseerbaar zijn (primitieven, `String`, of klassen die `Serializable` implementeren). Markeer velden als `transient` als ze niet moeten worden opgeslagen.
:::

Voeg `@WebforjSessionScope` toe aan elke Spring-component:

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

### Sessie delen tussen tabbladen {#session-sharing-across-tabs}

Sessie-scoped beans behouden staat tussen alle browservervensters en tabbladen. Het openen van de app in meerdere tabbladen deelt dezelfde bean-instantie:

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

    // Toon gebruikers dashboard
  }
}
```

Wanneer een gebruiker inlogt via een tabblad, hebben alle andere tabbladen onmiddellijk toegang tot de geauthenticeerde status. Het openen van nieuwe tabbladen of vensters behoudt de ingelogde status. Afmelden vanuit een tabblad heeft invloed op alle tabbladen, aangezien ze dezelfde sessie-scoped bean delen.

## Omgevingsscope {#environment-scope}

De `@EnvironmentScope` annotatie creëert beans die leven gedurende de duur van een browservervenster of tabblad-sessie. Wanneer een gebruiker de app opent in een browservervenster of tabblad, creëert webforJ een omgeving. Elke bean gemarkeerd met `@EnvironmentScope` wordt eenmaal per browservervenster/tabblad aangemaakt en blijft beschikbaar totdat de gebruiker het tabblad sluit of de sessie verloopt.

Elke omgeving vertegenwoordigt een geïsoleerd browservervenster of tabblad. Environment-scoped beans kunnen niet worden gedeeld tussen verschillende browservervensters of tabbladen omdat elk venster/tabblad zijn eigen instantie ontvangt.

Voeg `@EnvironmentScope` toe aan elke Spring-component:

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

De `TabWorkspace` bean behoudt staat gedurende de levensduur van een browservervenster of tabblad. Elke browservervenster/tabblad ontvangt een geïsoleerde instantie.

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

Spring injecteert dezelfde `TabWorkspace` instantie in beide views voor hetzelfde browservervenster/tabblad. Navigeren tussen editor en voorbeeld behoudt de workspace-instantie. Als de gebruiker de app in een nieuw browservervenster of tabblad opent, ontvangt dat venster zijn eigen onderscheidende `TabWorkspace` instantie, waardoor onafhankelijke bewerking van verschillende documenten mogelijk is.

## Route-scope {#route-scope}

De `@RouteScope` annotatie creëert beans die worden gedeeld binnen een routehiërarchie. Navigeren naar `/admin/users` bouwt een componenthiërarchie waarbij de admin-weergave als ouder en de gebruikersweergave als kind fungeert. Route-scoped beans worden eenmaal per hiërarchie geïnstantieerd en gedeeld tussen ouder- en kindercomponenten.

Route-scope verschilt van environment-scope in granulariteit. Terwijl environment-scoped beans bestaan gedurende de hele browservervenster/tabblad-sessie, bestaan route-scoped beans alleen terwijl de gebruiker zich binnen een specifieke routehiërarchie bevindt. Navigeren weg van de hiërarchie vernietigt de beans en terugkeren creëert nieuwe instanties. Deze scope is ideaal voor staat die opnieuw moet worden ingesteld wanneer gebruikers navigeren tussen verschillende secties van je app.

Voeg `@RouteScope` toe aan elke Spring-component:

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

Routes vormen hiërarchieën via de `outlet` parameter. De ouderroute biedt een outlet waar kindroutes zich renderen. Wanneer je een route definieert met een outlet, construeert webforJ een componentenboom waarbij het outletcomponent de ouder wordt en het routecomponent het kind. Deze ouder-kind relatie bepaalt welke componenten route-scoped beans delen.

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

De `AdminView` en `UsersView` delen dezelfde `NavigationState` instantie. De lay-out stelt de navigatiestructuur vast, terwijl de weergave de actieve status bijwerkt. Navigeren buiten de `admin` sectie (naar `/public` bijvoorbeeld) vernietigt de huidige `NavigationState` instantie en creëert een nieuwe voor de daaropvolgende hiërarchie.

De scope-grens volgt de structuur van de routeboom. Alle componenten van de wortel van een hiërarchie tot de bladeren delen dezelfde route-scoped bean-instanties. Navigeren naar broederoutes binnen dezelfde hiërarchie behoudt de beans, terwijl navigeren naar niet-gerelateerde hiërarchieën leidt tot het vernietigen en opnieuw creëren van beans.

### Het aanpassen van scope-grenzen met `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped beans worden standaard gedeeld vanaf de bovenste component. De annotatie `@SharedFrom` specificeert een alternatieve hoofcomponent. Deze annotatie verandert waar in de hiërarchie een bean beschikbaar wordt, waardoor je toegang tot specifieke subbomen van je routestructuur kunt beperken:

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
    // Bean wordt hier gecreëerd
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Kan TeamContext niet injecteren - deze is scoped naar TeamSection
    // Poging tot injectie zal IllegalStateException veroorzaken
  }
}
```

De annotatie `@SharedFrom` handhaaft architecturale grenzen. Componenten buiten de opgegeven scope kunnen de bean niet benaderen. Wanneer Spring probeert een `@SharedFrom` bean te injecteren in een component buiten de aangewezen hiërarchie, faalt de injectie met een `IllegalStateException`. Deze handhaving vindt plaats tijdens de uitvoering wanneer de route wordt benaderd, zodat beans correct worden gescopeerd naar hun beoogde componentbomen.

De annotatie accepteert een enkele parameter: de componentklasse die als de wortel voor delen moet dienen. Alleen deze component en zijn afstammelingen in de routestructuur kunnen de bean benaderen. Oudercomponenten en broederhiërarchieën kunnen deze niet injecteren.
