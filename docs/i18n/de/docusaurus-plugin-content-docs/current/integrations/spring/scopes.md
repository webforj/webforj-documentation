---
title: Scopes
sidebar_position: 16
description: >-
  Use WebforjSessionScope, EnvironmentScope, and RouteScope to control bean
  lifetimes across sessions, browser tabs, and route hierarchies.
_i18n_hash: ea33564c3dec0bc26426440f3b448c53
---
# Scopes <DocChip chip='since' label='25.03' />

Spring verwaltet den Lebenszyklus von Beans durch Scopes. Jeder Scope definiert, wann ein Bean erstellt wird, wie lange er lebt und wann er zerstört wird. Neben den standardmäßigen Spring-Scopes fügt webforJ drei benutzerdefinierte Scopes hinzu: `@WebforjSessionScope`, `@EnvironmentScope` und `@RouteScope`.

:::tip[Erfahren Sie mehr über Spring-Scopes]
Für eine umfassende Abdeckung des Scoping-Mechanismus von Spring und der Standard-Scope sehen Sie sich die [Dokumentation zu Spring-Bin-Scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html) an.
:::

## Übersicht {#overview}

webforJ bietet drei benutzerdefinierte Scopes, die für das Statusmanagement von Webanwendungen konzipiert sind:

- **`@WebforjSessionScope`**: Beans, die in allen Browsertabs/Fenstern für die gleiche Benutzersitzung geteilt werden. Perfekt für Authentifizierung, Benutzerpräferenzen und Warenkörbe.
- **`@EnvironmentScope`**: Beans, die auf ein einzelnes Browsertab/Fenster isoliert sind. Ideal für tab-spezifische Workflows, Formulardaten und unabhängige Dokumentenbearbeitung.
- **`@RouteScope`**: Beans, die innerhalb einer Routenhierarchie geteilt werden. Nützlich für Navigationsstatus und Daten, die zurückgesetzt werden sollten, wenn Benutzer zwischen den Anwendungsbereichen navigieren.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Session-Scoped {#session-scope}

Die Annotation `@WebforjSessionScope` erstellt Beans, die über die gesamte webforJ-Sitzung hinweg bestehen bleiben. Im Gegensatz zum [Environment-Scope](#environment-scope), der Beans pro Browsertab/Fenster isoliert, werden session-scoped Beans in allen Fenstern und Tabs desselben Browsers geteilt. Diese Beans leben so lange, wie die webforJ-Sitzung aktiv bleibt, typischerweise bis der Benutzer sich abmeldet oder die Sitzung abläuft.

Session-Scopes sind ideal für Authentifizierungsstatus, Benutzerpräferenzen, Warenkörbe und Daten, die zwischen mehreren Browsertabs bestehen bleiben, jedoch zwischen verschiedenen Benutzern isoliert sein sollten. Jede Benutzersitzung erhält ihre eigene Instanz von session-scoped Beans.

:::info Beans müssen serialisierbar sein
Session-scoped Beans müssen `Serializable` implementieren, da sie in HTTP-Sitzungsattributen gespeichert werden. Alle nicht-transienten Felder müssen ebenfalls serialisierbar sein (Primitiven, `String` oder Klassen, die `Serializable` implementieren). Markieren Sie Felder als `transient`, wenn sie nicht persistiert werden sollen.
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

### Sitzungsfreigabe über Tabs {#session-sharing-across-tabs}

Session-scoped Beans behalten den Status über alle Browserversionen und Tabs hinweg. Das Öffnen der Anwendung in mehreren Tabs teilt sich die gleiche Bean-Instanz:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Benutzer bereits in einem anderen Tab angemeldet
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Anmelden");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // Benutzer ist jetzt in allen Tabs angemeldet
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Dieselbe Instanz von AuthenticationService über alle Tabs hinweg
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Benutzer-Dashboard anzeigen
  }
}
```

Wenn sich ein Benutzer über einen Tab anmeldet, haben alle anderen Tabs sofort Zugriff auf den authentifizierten Zustand. Das Öffnen neuer Tabs oder Fenster erhält den angemeldeten Zustand. Das Abmelden von einem Tab wirkt sich auf alle Tabs aus, da sie die gleiche session-scoped Bean teilen.

## Umgebungs-Scope {#environment-scope}

Die Annotation `@EnvironmentScope` erstellt Beans, die für die Dauer eines Browsertabs oder -fensters bestehen bleiben. Wenn ein Benutzer die App in einem Browsertab oder -fenster öffnet, erstellt webforJ eine Umgebung. Jedes mit `@EnvironmentScope` gekennzeichnete Bean wird einmal pro Browsertab/Fenster erstellt und ist verfügbar, bis der Benutzer den Tab schließt oder die Sitzung abläuft.

Jede Umgebung stellt ein isoliertes Browsertab oder -fenster dar. Environment-scoped Beans können nicht zwischen verschiedenen Browsertabs oder -fenstern geteilt werden, da jedes Fenster/Tab seine eigene Instanz erhält.

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

Das `TabWorkspace`-Bean behält den Status während der Lebensdauer eines Browsertabs oder -fensters. Jedes Browsertab/-fenster erhält eine isolierte Instanz.

### Verwendung von umgebungs-scoped Beans {#using-environment-scoped-beans}

Routen erhalten umgebungs-scoped Beans durch Konstruktorinjektion:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Dokument für diesen Tab laden
    if (documentId == null) {
      // Neues Dokument erstellen
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Dieselbe Instanz von TabWorkspace wie im EditorView in diesem Tab
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Vorschau des bearbeiteten Dokuments in diesem Tab anzeigen
  }
}
```

Spring injiziert dieselbe `TabWorkspace`-Instanz in beide Ansichten für dasselbe Browsertab/-fenster. Die Navigation zwischen Editor und Vorschau bewahrt die Instanz des Arbeitsbereichs. Wenn der Benutzer die App in einem neuen Browsertab oder -fenster öffnet, erhält dieses Fenster seine eigene `TabWorkspace`-Instanz, die eine unabhängige Bearbeitung verschiedener Dokumente ermöglicht.

## Routen-Scope {#route-scope}

Die Annotation `@RouteScope` erstellt Beans, die innerhalb einer Routenhierarchie geteilt werden. Die Navigation zu `/admin/users` erstellt eine Komponentenhierarchie mit der Admin-Ansicht als Eltern und der Benutzeransicht als Kind. Route-scoped Beans werden einmal pro Hierarchie instanziiert und zwischen Eltern- und Kindkomponenten geteilt.

Route-Scope unterscheidet sich im Granularity von Environment-Scope. Während environment-scoped Beans während der gesamten Browsertab/Fenster-Sitzung existieren, existieren route-scoped Beans nur, solange der Benutzer sich innerhalb einer bestimmten Routenhierarchie aufhält. Das Navigieren außerhalb der Hierarchie zerstört die Beans, und das Zurückkehren erstellt neue Instanzen. Dieser Scope ist ideal für Status, der zurückgesetzt werden sollte, wenn Benutzer zwischen verschiedenen Abschnitten Ihrer App navigieren.

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

### Routenhierarchien und -freigabe {#route-hierarchies-and-sharing}

Routen bilden Hierarchien durch den `outlet`-Parameter. Die Elternroute stellt einen Outlet bereit, in dem die Kindrouten gerendert werden. Wenn Sie eine Route mit einem Outlet definieren, erstellt webforJ einen Komponentbaum, in dem die Outlet-Komponente die Eltern- und die Routkomponente die Kindkomponente ist. Diese Eltern-Kind-Beziehung bestimmt, welche Komponenten route-scoped Beans teilen.

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

Die `AdminView` und `UsersView` teilen sich dieselbe `NavigationState`-Instanz. Das Layout legt die Navigationsstruktur fest, während die Ansicht den aktiven Status aktualisiert. Die Navigation außerhalb des `admin`-Abschnitts (zum Beispiel zu `/public`) zerstört die aktuelle `NavigationState`-Instanz und erstellt eine neue für die nachfolgende Hierarchie.

Die Scope-Grenze folgt der Struktur des Routenbaums. Alle Komponenten vom Wurzelpunkt einer Hierarchie bis zu den Blättern teilen sich die gleichen route-scoped Bean-Instanzen. Die Navigation zu Geschwisterrouten innerhalb derselben Hierarchie bewahrt die Beans, während die Navigation zu nicht verwandten Hierarchien zur Zerstörung und Neuerstellung von Beans führt.

### Anpassen der Scope-Grenzen mit `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped Beans werden standardmäßig vom obersten Komponent geteilt. Die Annotation `@SharedFrom` bestimmt eine alternative Wurzelkomponente. Diese Annotation ändert, wo in der Hierarchie ein Bean verfügbar wird, und ermöglicht es Ihnen, den Zugriff auf bestimmte Teilbäume Ihrer Routenstruktur einzuschränken:

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

Das Bean ist ausschließlich innerhalb von `TeamSection` und deren Kindkomponenten zugänglich:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // Bean wird hier erstellt
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Kann TeamContext nicht injizieren - es ist auf TeamSection beschränkt
    // Versuch der Injektion löst IllegalStateException aus
  }
}
```

Die Annotation `@SharedFrom` zwingt architektonische Grenzen. Komponenten außerhalb des angegebenen Scopes können nicht auf das Bean zugreifen. Wenn Spring versucht, ein `@SharedFrom`-Bean in eine Komponente außerhalb ihrer vorgesehenen Hierarchie zu injizieren, schlägt die Injektion mit einer `IllegalStateException` fehl. Diese Durchsetzung erfolgt zur Laufzeit, wenn die Route aufgerufen wird, sodass die Beans korrekt auf ihre beabsichtigten Baumsysteme beschränkt bleiben.

Die Annotation akzeptiert einen einzelnen Parameter: die Komponentenklasse, die als Wurzel für das Teilen dienen soll. Nur diese Komponente und ihre Nachkommen in der Routenhierarchie können auf das Bean zugreifen. Elternkomponenten und Geschwisterhierarchien können es nicht injizieren.
