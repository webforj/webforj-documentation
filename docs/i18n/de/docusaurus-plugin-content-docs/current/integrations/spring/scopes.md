---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 79be5bc5e8982111f185b0f474a1f746
---
# Scopes <DocChip chip='since' label='25.03' />

Spring verwaltet den Lebenszyklus von Beans durch Scopes. Jeder Scope definiert, wann ein Bean erstellt wird, wie lange er lebt und wann er zerstört wird. Zusätzlich zu den standardmäßigen Spring-Scope fügt webforJ drei benutzerdefinierte Scopes hinzu: `@WebforjSessionScope`, `@EnvironmentScope` und `@RouteScope`.

:::tip[Erfahren Sie mehr über Spring Scopes]
Für umfassende Informationen zum Scoping-Mechanismus von Spring und den standardmäßigen Scopes siehe [Dokumentation zu Spring Beans Scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Übersicht

webforJ bietet drei benutzerdefinierte Scopes, die für das Management des Zustands von Webanwendungen entwickelt wurden:

- **`@WebforjSessionScope`**: Beans, die über alle Browser-Tabs/Fenster für die gleiche Benutzersitzung gemeinsam genutzt werden. Perfekt für Authentifizierung, Benutzerpräferenzen und Warenkörbe.
- **`@EnvironmentScope`**: Beans, die auf ein einziges Browser-Tab/Fenster isoliert sind. Ideal für tab-spezifische Arbeitsabläufe, Formulardaten und unabhängiges Dokumentenbearbeiten.
- **`@RouteScope`**: Beans, die innerhalb einer Routen-Hierarchie geteilt werden. Nützlich für Navigationsstatus und Daten, die zurückgesetzt werden sollten, wenn Benutzer zwischen den App-Sektionen navigieren.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Sitzungsscope {#session-scope}

Die Annotation `@WebforjSessionScope` erstellt Beans, die während der gesamten webforJ-Sitzung bestehen bleiben. Im Gegensatz zum [Umgebungsscope](#environment-scope), bei dem Beans pro Browserfenster/-tab isoliert sind, werden session-scoped Beans über alle Fenster und Tabs im gleichen Browser geteilt. Diese Beans leben so lange, wie die webforJ-Sitzung aktiv bleibt, typischerweise bis der Benutzer sich abmeldet oder die Sitzung abläuft.

Der Sitzungsscope ist ideal für den Authentifizierungsstatus, Benutzerpräferenzen und Warenkörbe. Daten, die über mehrere Browser-Tabs hinweg bestehen bleiben sollen, aber zwischen verschiedenen Benutzern isoliert bleiben sollen. Jede Benutzersitzung im Browser erhält ihre eigene Instanz von session-scoped Beans.

:::info Beans müssen serialisierbar sein
Session-scoped Beans müssen `Serializable` implementieren, da sie in HTTP-Sitzungsattributen gespeichert werden. Alle nicht-transienten Felder müssen ebenfalls serialisierbar sein (Primitives, `String` oder Klassen, die `Serializable` implementieren). Markieren Sie Felder als `transient`, wenn sie nicht persistent sein sollen.
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

### Sitzungsfreigabe über Tabs hinweg {#session-sharing-across-tabs}

Session-scoped Beans halten den Zustand über alle Browser-Fenster und -Tabs hinweg. Das Öffnen der App in mehreren Tabs teilt die gleiche Bean-Instanz:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Benutzer ist bereits in einem anderen Tab angemeldet
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Login");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // Benutzer ist jetzt über alle Tabs hinweg angemeldet
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Dieselbe AuthenticationService-Instanz über alle Tabs hinweg
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Benutzer-Dashboard anzeigen
  }
}
```

Wenn ein Benutzer sich über einen Tab anmeldet, haben alle anderen Tabs sofort Zugang zum authentifizierten Status. Das Öffnen neuer Tabs oder Fenster erhält den angemeldeten Status. Das Abmelden von einem Tab wirkt sich auf alle Tabs aus, da sie die gleiche session-scoped Bean teilen.

## Umgebungsscope {#environment-scope}

Die Annotation `@EnvironmentScope` erstellt Beans, die für die Dauer einer Broweserwinde oder Tab-Sitzung leben. Wenn ein Benutzer die App in einem Browserfenster oder Tab öffnet, erstellt webforJ eine Umgebung. Jedes Bean, das mit `@EnvironmentScope` gekennzeichnet ist, wird einmal pro Browserfenster/-tab erstellt und bleibt verfügbar, bis der Benutzer den Tab schließt oder die Sitzung abläuft.

Jede Umgebung stellt ein isoliertes Browserfenster oder Tab dar. Environment-scoped Beans können nicht zwischen verschiedenen Browserfenstern oder -tabs geteilt werden, da jedes Fenster/Tab seine eigene Instanz erhält.

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

Der `TabWorkspace`-Bean hält den Zustand während der Lebensdauer eines Browserfensters oder -tabs aufrecht. Jedes Browserfenster/-tab erhält eine isolierte Instanz.

### Verwendung von umgebungsscopierten Beans {#using-environment-scoped-beans}

Routen erhalten umgebungsscopierte Beans durch Konstruktorinjektion:

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
    // Gleiche TabWorkspace-Instanz wie in EditorView in diesem Tab
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Vorschau des bearbeiteten Dokuments in diesem Tab
  }
}
```

Spring injiziert dieselbe `TabWorkspace`-Instanz in beide Ansichten für das gleiche Browserfenster/-tab. Die Navigation zwischen Bearbeitungs- und Vorschauansicht bewahrt die Arbeitsbereichsinstanz. Wenn der Benutzer die App in einem neuen Browserfenster oder -tab öffnet, erhält dieses Fenster seine eigene distinct `TabWorkspace`-Instanz, die ein unabhängiges Bearbeiten verschiedener Dokumente ermöglicht.

## Routen-Scope {#route-scope}

Die Annotation `@RouteScope` erstellt Beans, die innerhalb einer Routen-Hierarchie geteilt werden. Die Navigation zu `/admin/users` baut eine Komponenten-Hierarchie mit der Admin-Ansicht als Eltern- und der Benutzeransicht als Kind auf. Route-scoped Beans werden einmal pro Hierarchie instanziiert und zwischen Eltern- und Kindkomponenten geteilt.

Der Routen-Scope unterscheidet sich im Granularität vom Umgebungsscope. Während umgebungsscopierte Beans für die gesamte Dauer der Browsersitzung existieren, existieren route-scoped Beans nur, solange der Benutzer sich innerhalb einer bestimmten Routen-Hierarchie aufhält. Das Navigieren außerhalb der Hierarchie zerstört die Beans und das Zurückkehren erstellt frische Instanzen. Dieser Scope ist ideal für Statusinformationen, die zurückgesetzt werden sollten, wenn Benutzer zwischen verschiedenen Abschnitten Ihrer App navigieren.

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

### Routen-Hierarchien und Teilen {#route-hierarchies-and-sharing}

Routen bilden Hierarchien durch den `outlet`-Parameter. Die Elternroute stellt einen Outlet bereit, in dem die Kindrouten gerendert werden. Wenn Sie eine Route mit einem Outlet definieren, konstruiert webforJ einen Komponentebaum, in dem die Outlet-Komponente die Eltern- und die Routen-Komponente das Kind wird. Diese Eltern-Kind-Beziehung bestimmt, welche Komponenten route-scoped Beans teilen.

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
    // Dieselbe NavigationState-Instanz wie in AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Users");
  }
}
```

Die `AdminView` und `UsersView` teilen sich dieselbe `NavigationState`-Instanz. Das Layout definiert die Navigationsstruktur, während die Ansicht den aktiven Zustand aktualisiert. Die Navigation außerhalb des `admin`-Bereichs (z. B. zu `/public`) zerstört die aktuelle `NavigationState`-Instanz und erstellt eine neue für die nachfolgende Hierarchie.

Die Scope-Grenze folgt der Struktur des Routennbaums. Alle Komponenten von der Wurzel einer Hierarchie bis zu den Blättern teilen sich dieselben route-scoped Bean-Instanzen. Die Navigation zu Geschwisterrouten innerhalb derselben Hierarchie bewahrt die Beans, während die Navigation zu nicht verwandten Hierarchien die Zerstörung und Neuerstellung von Beans auslöst.

### Anpassen von Scope-Grenzen mit `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped Beans werden standardmäßig von der obersten Komponente geteilt. Die Annotation `@SharedFrom` gibt eine alternative Wurzelkomponente an. Diese Annotation ändert, wo in der Hierarchie ein Bean verfügbar wird, und ermöglicht es Ihnen, den Zugriff auf spezifische Teilbäume Ihrer Routenstruktur einzuschränken:

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

Der Bean ist ausschließlich innerhalb von `TeamSection` und seinen Kindkomponenten zugänglich:

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
    // Der Versuch der Injektion wirft IllegalStateException
  }
}
```

Die Annotation `@SharedFrom` erzwingt architektonische Grenzen. Komponenten außerhalb des angegebenen Scopes können nicht auf den Bean zugreifen. Wenn Spring versucht, einen `@SharedFrom`-Bean in eine Komponente außerhalb ihrer vorgesehenen Hierarchie zu injizieren, schlägt die Injektion mit einer `IllegalStateException` fehl. Diese Durchsetzung erfolgt zur Laufzeit, wenn die Route aufgerufen wird, sodass Beans korrekt auf ihre beabsichtigten Komponentenbäume beschränkt bleiben.

Die Annotation akzeptiert einen einzelnen Parameter - die Komponentenklasse, die als Wurzel für das Teilen dienen soll. Nur diese Komponente und ihre Nachkommen in der Routenhierarchie können auf den Bean zugreifen. Elternkomponenten und Geschwisterhierarchien können ihn nicht injizieren.
