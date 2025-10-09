---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 8c977fdef41f6125ac21239e7e397f4d
---
<!-- vale off -->

# Scopes <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring verwaltet den Lebenszyklus von Beans durch Scopes. Jedes Scope definiert, wann ein Bean erstellt wird, wie lange es lebt und wann es zerstört wird. Neben den standardmäßigen Spring-Scopes fügt webforJ drei benutzerdefinierte Scopes hinzu: `@WebforjSessionScope`, `@EnvironmentScope` und `@RouteScope`.

:::tip[Erfahren Sie mehr über Spring-Scopes]
Für umfassende Informationen über das Scoping-Mechanismus von Spring und die standardmäßigen Scopes siehe [Spring's bean scopes documentation](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Übersicht

webforJ bietet drei benutzerdefinierte Scopes, die für das Zustandmanagement in Webanwendungen entwickelt wurden:

- **`@WebforjSessionScope`**: Beans, die in allen Browser-Tabs/Fenstern für dieselbe Benutzersitzung geteilt werden. Perfekt für Authentifizierung, Benutzerpräferenzen und Einkaufswagen.
- **`@EnvironmentScope`**: Beans, die auf ein einzelnes Browser-Tab/Fenster isoliert sind. Ideal für tab-spezifische Workflows, Formulardaten und unabhängiges Dokumentenbearbeiten.
- **`@RouteScope`**: Beans, die innerhalb einer Routen-Hierarchie geteilt werden. Nützlich für Navigationszustände und Daten, die zurückgesetzt werden sollten, wenn Benutzer zwischen den Abschnitten der Anwendung navigieren.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Sitzungsscope {#session-scope}

Die Annotation `@WebforjSessionScope` erstellt Beans, die über die gesamte webforJ-Sitzung hinweg bestehen bleiben. Im Gegensatz zum [Umgebungsscope](#environment-scope), der Beans pro Browserfenster/-tab isoliert, werden session-scoped Beans in allen Fenstern und Tabs des gleichen Browsers geteilt. Diese Beans leben so lange wie die webforJ-Sitzung aktiv bleibt, typischerweise bis der Benutzer sich abmeldet oder die Sitzung abläuft.

Der Sitzungsscope ist ideal für Authentifizierungsstatus, Benutzerpräferenzen, Einkaufswagen und Daten, die über mehrere Browser-Tabs hinweg bestehen bleiben sollten, jedoch zwischen verschiedenen Benutzern isoliert bleiben. Jede Benutzer-Browsersitzung erhält ihre eigene Instanz der session-scoped Beans.

:::info Beans müssen serialisierbar sein
Session-scoped Beans müssen `Serializable` implementieren, da sie in HTTP-Sitzungsattributen gespeichert werden. Alle nicht-transienten Felder müssen ebenfalls serialisierbar sein (Primitives, `String` oder Klassen, die `Serializable` implementieren). Markieren Sie Felder als `transient`, wenn sie nicht persistiert werden sollen.
:::

Fügen Sie `@WebforjSessionScope` zu einer beliebigen Spring-Komponente hinzu:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Benutzer authentifizieren
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Sitzung ungültig machen
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Sitzungsteilung über Tabs {#session-sharing-across-tabs}

Session-scoped Beans erhalten ihren Zustand über alle Browserfenster und -tabs hinweg. Wenn die Anwendung in mehreren Tabs geöffnet wird, wird dieselbe Bean-Instanz geteilt:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Benutzer bereits in einem anderen Tab eingeloggt
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Login");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // Benutzer ist nun in allen Tabs eingeloggt
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Dieselbe AuthenticationService-Instanz in allen Tabs
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Benutzer-Dashboard anzeigen
  }
}
```

Wenn sich ein Benutzer über einen Tab einloggt, haben alle anderen Tabs sofort Zugriff auf den authentifizierten Zustand. Das Öffnen neuer Tabs oder Fenster erhält den eingeloggten Zustand. Das Abmelden aus einem Tab wirkt sich auf alle Tabs aus, da sie dieselbe session-scoped Bean teilen.

## Umgebungsscope {#environment-scope}

Die Annotation `@EnvironmentScope` erstellt Beans, die nur für die Dauer einer Browsersitzung oder eines Tabs leben. Wenn ein Benutzer die Anwendung in einem Browserfenster oder Tab öffnet, erstellt webforJ eine Umgebung. Jede Bean, die mit `@EnvironmentScope` markiert ist, wird einmal pro Browserfenster/-tab erstellt und bleibt verfügbar, bis der Benutzer das Tab schließt oder die Sitzung abläuft.

Jede Umgebung stellt ein isoliertes Browserfenster oder -tab dar. Environment-scoped Beans können nicht zwischen verschiedenen Browserfenstern oder -tabs geteilt werden, da jedes Fenster/tab seine eigene Instanz erhält.

Fügen Sie `@EnvironmentScope` zu einer beliebigen Spring-Komponente hinzu:

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

Die Bean `TabWorkspace` erhält ihren Zustand über die Lebensdauer eines Browserfensters oder -tabs hinweg. Jedes Browserfenster/-tab erhält eine isolierte Instanz.

### Verwendung von environment-scoped Beans {#using-environment-scoped-beans}

Routen erhalten environment-scoped Beans durch Konstruktorinjektion:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Dokument für dieses Tab laden
    if (documentId == null) {
      // Neues Dokument erstellen
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Dieselbe TabWorkspace-Instanz wie in EditorView in diesem Tab
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Dokument, das in diesem Tab bearbeitet wird, anzeigen
  }
}
```

Spring injiziert dieselbe `TabWorkspace`-Instanz in beide Ansichten für dasselbe Browserfenster/-tab. Die Navigation zwischen Editor und Vorschau bewahrt die Arbeitsbereich-Instanz. Wenn der Benutzer die Anwendung in einem neuen Browserfenster oder -tab öffnet, erhält dieses Fenster seine eigene distinct `TabWorkspace`-Instanz, die eine unabhängige Bearbeitung verschiedener Dokumente ermöglicht.

## Routen-Scope {#route-scope}

Die Annotation `@RouteScope` erstellt Beans, die innerhalb einer Routen-Hierarchie geteilt werden. Die Navigation zu `/admin/users` erstellt eine Komponentenhierarchie mit der Admin-Ansicht als Eltern- und der Benutzer-Ansicht als Kind. Route-scoped Beans werden einmal pro Hierarchie instanziiert und zwischen Eltern- und Kindkomponenten geteilt.

Der Routen-Scope unterscheidet sich vom Umgebungsscope in der Granularität. Während environment-scoped Beans während der gesamten Browsersitzung bestehen, existieren route-scoped Beans nur solange, wie der Benutzer innerhalb einer bestimmten Routenhierarchie bleibt. Das Navigieren weg von der Hierarchie zerstört die Beans, und das Zurückkehren erstellt frische Instanzen. Dieser Scope ist ideal für Zustände, die zurückgesetzt werden sollten, wenn Benutzer zwischen verschiedenen Abschnitten Ihrer Anwendung navigieren.

Fügen Sie `@RouteScope` zu einer beliebigen Spring-Komponente hinzu:

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

### Routenhierarchien und Teilen {#route-hierarchies-and-sharing}

Routen bilden Hierarchien durch den `outlet`-Parameter. Die übergeordnete Route stellt einen Outlet bereit, in dem die untergeordneten Routen gerendert werden. Wenn Sie eine Route mit einem Outlet definieren, erstellt webforJ einen Komponentbaum, in dem die Outlet-Komponente zur Elternkomponente und die Routenkomponente zur Kindkomponente wird. Diese Eltern-Kind-Beziehung bestimmt, welche Komponenten route-scoped Beans teilen.

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Startseite");
    navState.addBreadcrumb("Admin");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {

  public UsersView(NavigationState navState) {
    // Dieselbe NavigationState-Instanz wie AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Benutzer");
  }
}
```

Die `AdminView` und `UsersView` teilen sich dieselbe `NavigationState`-Instanz. Das Layout legt die Navigationsstruktur fest, während die Ansicht den aktiven Zustand aktualisiert. Die Navigation außerhalb des `admin`-Bereichs (zu `/public` zum Beispiel) zerstört die aktuelle `NavigationState`-Instanz und erstellt eine neue für die nachfolgende Hierarchie.

Die Scope-Grenze folgt der Struktur des Routennbaums. Alle Komponenten vom Wurzelknoten einer Hierarchie bis zu den Blättern teilen sich dieselben instances von route-scoped Beans. Die Navigation zu Geschwisterrouten innerhalb derselben Hierarchie bewahrt die Beans, während die Navigation zu nicht verwandten Hierarchien die Zerstörung und Neuerstellung von Beans auslöst.

### Anpassen der Scope-Grenzen mit `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped Beans werden standardmäßig vom obersten Element geteilt. Die Annotation `@SharedFrom` gibt eine alternative Wurzelkomponente an. Diese Annotation ändert, wo in der Hierarchie eine Bean verfügbar wird, sodass Sie den Zugriff auf bestimmte Teilbäume Ihrer Routenstruktur einschränken können:

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

Die Bean ist ausschließlich innerhalb von `TeamSection` und deren untergeordneten Komponenten zugänglich:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // Bean hier erstellt
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Kann TeamContext nicht injizieren - es ist auf TeamSection beschränkt
    // Der Versuch, die Injektion durchzuführen, wirft IllegalStateException
  }
}
```

Die Annotation `@SharedFrom` erzwingt architektonische Grenzen. Komponenten außerhalb des angegebenen Scopes können nicht auf die Bean zugreifen. Wenn Spring versucht, eine `@SharedFrom`-Bean in eine Komponente außerhalb ihrer vorgesehenen Hierarchie zu injizieren, schlägt die Injektion mit einer `IllegalStateException` fehl. Diese Durchsetzung erfolgt zur Laufzeit, wenn die Route aufgerufen wird, sodass die Beans ordnungsgemäß auf die beabsichtigten Komponentenbäume beschränkt bleiben.

Die Annotation akzeptiert einen einzelnen Parameter: die Komponentenklasse, die als Wurzel für das Teilen dienen soll. Nur diese Komponente und ihre Nachfahren in der Routenhierarchie können auf die Bean zugreifen. Übergeordnete Komponenten und Geschwisterhierarchien können sie nicht injizieren.
