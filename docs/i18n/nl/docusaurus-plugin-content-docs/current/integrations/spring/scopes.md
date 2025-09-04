---
title: Scopes
sidebar_position: 16
_i18n_hash: 96d36a28887e551ba3f9acab0d7059bc
---
<!-- vale off -->
# Scopes <DocChip chip='since' label='25.03' />
<!-- vale on -->

Spring beheert de levenscyclus van beans via scopes. Elke scope definieert wanneer een bean wordt aangemaakt, hoe lang deze leeft en wanneer deze wordt vernietigd. webforJ voegt twee aangepaste scopes toe - `@EnvironmentScope` en `@RouteScope` - die overeenkomen met hoe webforJ-toepassingen omgaat met browsersessies en navigatie.

:::tip[Leer meer over Spring scopes]
Voor een uitgebreide dekking van de scopingmechanismen van Spring en standaard scopes, zie [de documentatie van Spring's bean scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Environment scope {#environment-scope}

De annotatie `@EnvironmentScope` creëert beans die leven voor de duur van een browsersessie in een venster of tabblad. Wanneer een gebruiker de app opent in een browservenster of tabblad, creëert webforJ een Environment. Elke bean die gemarkeerd is met `@EnvironmentScope` wordt eenmaal per browservenster/tabblad aangemaakt en blijft beschikbaar totdat de gebruiker het tabblad sluit of de sessie vervalt.

Elke Environment vertegenwoordigt een geïsoleerd browservenster of tabblad. Beans met een Environment scope kunnen niet worden gedeeld tussen verschillende browservensters of tabbladen - elk venster/tabblad ontvangt zijn eigen instantie.

Voeg `@EnvironmentScope` toe aan elke Spring-component:

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

De `UserSession` bean behoudt de status gedurende de levensduur van een browservenster of tabblad. Elke browservenster/tabblad ontvangt een geïsoleerde instantie.

### Gebruik van beans met environment scope {#using-environment-scoped-beans}

Routes ontvangen beans met environment scope via constructor-injectie:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  public DashboardView(UserSession session) {
    String userId = session.getUserId();
    // Gebruik de sessiegegevens
    if (userId == null) {
      // Doorverwijzen naar inloggen
    }
  }
}

@Route("/profile")
public class ProfileView extends Composite<Div> {
  
  public ProfileView(UserSession session) {
    // Zelfde UserSession-instantie als DashboardView
    session.setAttribute("lastView", "profile");
  }
}
```

Spring injecteert dezelfde `UserSession`-instantie in beide views voor hetzelfde browservenster/tabblad. Navigatie tussen dashboard en profiel behoudt de sessie-instantie. Als de gebruiker de app in een nieuw browservenster of tabblad opent, ontvangt dat venster zijn eigen distincte `UserSession`-instantie.

## Route scope {#route-scope}

De annotatie `@RouteScope` creëert beans die worden gedeeld binnen een routehiërarchie. Navigatie naar `/admin/users` bouwt een componenthiërarchie op met de admin-view als ouder en de gebruikers-view als kind. Beans met route scope worden eenmaal per hiërarchie geïnstantieerd en gedeeld tussen ouder- en kindcomponenten.

Route scope verschilt van environment scope in granulariteit. Terwijl beans met environment scope bestaan voor de gehele browsersessie in een venster/tabblad, bestaan beans met route scope alleen zolang de gebruiker zich binnen een specifieke routehiërarchie bevindt. Weg navigeren van de hiërarchie vernietigt de beans, en terugkeren creëert nieuwe instanties. Deze scope is ideaal voor status die moet worden gereset wanneer gebruikers tussen verschillende secties van je app navigeren.

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

Routes vormen hiërarchieën via de parameter `outlet`. De ouderroute biedt een outlet waar kindroutes worden gerenderd. Wanneer je een route met een outlet definieert, construeert webforJ een componentenboom waarbij de outletcomponent de ouder wordt en de routecomponent het kind. Deze ouder-kindrelatie bepaalt welke componenten beans met route scope delen.

```java
@Route("/admin")
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

De `AdminView` en `UsersView` delen dezelfde `NavigationState`-instantie. De lay-out stelt de navigatiestructuur vast terwijl de view de actieve status bijwerkt. Navigeren buiten de `admin` sectie (bijvoorbeeld naar `/public`) vernietigt de huidige `NavigationState`-instantie en creëert een nieuwe voor de volgende hiërarchie.

De scope-grens volgt de structuur van de routeboom. Alle componenten van de wortel van een hiërarchie tot aan de bladeren delen dezelfde instances van beans met route scope. Navigatie naar broederoutes binnen dezelfde hiërarchie behoudt de beans, terwijl navigatie naar niet-gerelateerde hiërarchieën de vernietiging en recreatie van beans triggert.

### Scope-grenzen aanpassen met `@SharedFrom` {#customizing-scope-boundaries}

Beans met route scope worden standaard gedeeld vanuit de bovenste component. De annotatie `@SharedFrom` specificeert een alternatieve wortelcomponent. Deze annotatie verandert waar in de hiërarchie een bean beschikbaar wordt, waardoor je toegang tot specifieke subtrees van je routestructuur kunt beperken:

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
    // Bean hier aangemaakt
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Kan TeamContext niet injecteren - het is beperkt tot TeamSection
    // Poging tot injectie genereert IllegalStateException
  }
}
```

De annotatie `@SharedFrom` handhaaft architectonische grenzen. Componenten buiten de gespecificeerde scope kunnen de bean niet toegankelijk maken. Wanneer Spring probeert een `@SharedFrom`-bean in een component buiten zijn aangewezen hiërarchie te injecteren, mislukt de injectie met een `IllegalStateException`. Deze handhaving gebeurt tijdens runtime wanneer de route wordt geopend, zodat beans correct zijn beperkt tot hun bedoelde componentbomen.

De annotatie accepteert één parameter - de componentklasse die als wortel voor delen moet dienen. Alleen deze component en zijn afstammelingen in de routehiërarchie kunnen de bean toegankelijk maken. Bovenliggende componenten en broederhiërarchieën kunnen deze niet injecteren.
