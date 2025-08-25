---
title: Scopes
sidebar_position: 16
_i18n_hash: 96d36a28887e551ba3f9acab0d7059bc
---
<!-- vale off -->
# Scopes <DocChip chip='since' label='25.03' />
<!-- vale on -->

Spring verwaltet den Lebenszyklus von Beans über Scopes. Jedes Scope definiert, wann ein Bean erstellt wird, wie lange es lebt und wann es zerstört wird. webforJ fügt zwei benutzerdefinierte Scopes hinzu - `@EnvironmentScope` und `@RouteScope` - die definieren, wie webforJ-Anwendungen Browser-Sitzungen und Navigationen handhaben.

:::tip[Erfahren Sie mehr über Spring-Scopes]
Für umfassende Informationen über das Scoping-Mechanismus von Spring und die Standard-Scopes, siehe [Dokumentation zu Spring-Bean-Scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Environment Scope {#environment-scope}

Die Annotation `@EnvironmentScope` erstellt Beans, die für die Dauer einer Browserfenster- oder Tab-Sitzung leben. Wenn ein Benutzer die App in einem Browserfenster oder -tab öffnet, erstellt webforJ eine Umgebung. Jeder Bean, der mit `@EnvironmentScope` gekennzeichnet ist, wird einmal pro Browserfenster/-tab erstellt und bleibt verfügbar, bis der Benutzer das Tab schließt oder die Sitzung abläuft.

Jede Umgebung repräsentiert ein isoliertes Browserfenster oder Tab. Environment-scoped Beans können nicht zwischen verschiedenen Browserfenstern oder -tabs geteilt werden - jedes Fenster/tab erhält seine eigene Instanz.

Fügen Sie `@EnvironmentScope` zu einer beliebigen Spring-Komponente hinzu:

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

Der `UserSession` Bean hält den Zustand während der Lebensdauer eines Browserfensters oder Tabs. Jedes Browserfenster/tab erhält eine isolierte Instanz.

### Verwendung von Environment-scoped Beans {#using-environment-scoped-beans}

Routen erhalten Environment-scoped Beans durch Konstruktorinjektion:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  public DashboardView(UserSession session) {
    String userId = session.getUserId();
    // Verwenden Sie die Sitzungsdaten
    if (userId == null) {
      // Zur Anmeldung umleiten
    }
  }
}

@Route("/profile")
public class ProfileView extends Composite<Div> {
  
  public ProfileView(UserSession session) {
    // Dieselbe UserSession-Instanz wie DashboardView
    session.setAttribute("lastView", "profile");
  }
}
```

Spring injiziert dieselbe `UserSession`-Instanz in beide Views für dasselbe Browserfenster/tab. Die Navigation zwischen Dashboard und Profil bewahrt die Sitzungsinstanz. Wenn der Benutzer die App in einem neuen Browserfenster oder -tab öffnet, erhält dieses Fenster seine eigene distinct `UserSession`-Instanz.

## Route Scope {#route-scope}

Die Annotation `@RouteScope` erstellt Beans, die innerhalb einer Routenhierarchie geteilt werden. Die Navigation zu `/admin/users` erstellt eine Komponenten-Hierarchie mit der Admin-Ansicht als Eltern und der Benutzeransicht als Kind. Route-scoped Beans werden einmal pro Hierarchie instanziiert und zwischen Eltern- und Kindkomponenten geteilt.

Route scope unterscheidet sich von Environment scope in der Granularität. Während Environment-scoped Beans für die gesamte Browsersitzung des Fensters/Tabs existieren, existieren Route-scoped Beans nur, solange der Benutzer sich innerhalb einer bestimmten Routenhierarchie aufhält. Das Navigieren von der Hierarchie weg zerstört die Beans, und bei einer Rückkehr werden frische Instanzen erstellt. Dieses Scope ist ideal für Zustände, die zurückgesetzt werden sollten, wenn Benutzer zwischen verschiedenen Bereichen Ihrer App navigieren.

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

Routen bilden Hierarchien durch den `outlet`-Parameter. Die Elternroute stellt einen Outlet bereit, in dem Kindrouten gerendert werden. Wenn Sie eine Route mit einem Outlet definieren, konstruiert webforJ einen Komponentenbaum, in dem die Outlet-Komponente das Elternteil und die Routenkomponente das Kind wird. Diese Eltern-Kind-Beziehung bestimmt, welche Komponenten Route-scoped Beans teilen.

```java
@Route("/admin")
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

Die `AdminView` und `UsersView` teilen sich dieselbe `NavigationState`-Instanz. Das Layout etabliert die Navigationsstruktur, während die View den aktiven Zustand aktualisiert. Die Navigation außerhalb des `admin`-Bereichs (zum Beispiel zu `/public`) zerstört die aktuelle `NavigationState`-Instanz und erstellt eine neue für die nachfolgende Hierarchie.

Die Scope-Grenze folgt der Struktur des Routenbaums. Alle Komponenten von der Wurzel einer Hierarchie bis zu den Blättern teilen sich dieselben Route-scoped Bean-Instanzen. Das Navigieren zu Geschwisterrouten innerhalb derselben Hierarchie bewahrt die Beans, während das Navigieren zu nicht verwandten Hierarchien die Zerstörung und Neuerstellung der Beans auslöst.

### Anpassen von Scope-Grenzen mit `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped Beans werden standardmäßig von der obersten Komponente geteilt. Die Annotation `@SharedFrom` gibt eine alternative Wurzelkomponente an. Diese Annotation ändert, wo in der Hierarchie ein Bean verfügbar wird, sodass Sie den Zugriff auf spezifische Teilbäume Ihrer Routenstruktur einschränken können:

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

Der Bean ist ausschließlich innerhalb von `TeamSection` und ihren Kindkomponenten zugänglich:

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
    // Der Versuch, die Injektion durchzuführen, löst IllegalStateException aus
  }
}
```

Die Annotation `@SharedFrom` erzwingt architektonische Grenzen. Komponenten außerhalb des angegebenen Scopes können nicht auf den Bean zugreifen. Wenn Spring versucht, einen `@SharedFrom`-Bean in eine Komponente außerhalb ihrer festgelegten Hierarchie zu injizieren, schlägt die Injektion mit einer `IllegalStateException` fehl. Diese Durchsetzung geschieht zur Laufzeit, wenn die Route aufgerufen wird, sodass Beans ordnungsgemäß auf ihre vorgesehenen Komponentenbäume beschränkt bleiben.

Die Annotation akzeptiert einen einzigen Parameter - die Komponentenklasse, die als Wurzel für das Teilen dienen soll. Nur diese Komponente und ihre Nachfahren in der Routenhierarchie können auf den Bean zugreifen. Übergeordnete Komponenten und Geschwisterhierarchien können ihn nicht injizieren.
