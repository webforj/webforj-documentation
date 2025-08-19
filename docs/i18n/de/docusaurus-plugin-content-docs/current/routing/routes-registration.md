---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 5075588c497508fc77d7f76b1b412cf4
---
Zusätzlich zur [Registrierung von Routen mithilfe der `@Route`-Annotationen](./defining-routes) ist es möglich, Routen zur Laufzeit dynamisch zu registrieren, zu aktualisieren oder zu entfernen, basierend auf der Logik der App, Benutzerrollen oder anderen Bedingungen. Diese Flexibilität ermöglicht es Ihnen, die Navigation dynamischer zu gestalten, anstatt Routen statisch zur Kompilierzeit zu definieren.

## Dynamische Routenregistrierung {#registering-routes-dynamically}

Sie können eine Route dynamisch mithilfe der Klasse `RouteRegistry` registrieren, die über den `Router` zugänglich ist. Dies ermöglicht es Ihnen, während der Laufzeit neue Routen hinzuzufügen und eine flexible Navigation zu ermöglichen.

### Beispiel: Registrierung einer dynamischen Route {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrieren Sie die Einstellungsroute dynamisch
registry.register("/settings", SettingsView.class);

// Navigieren Sie zur Einstellungsansicht
router.navigate(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch registriert, und die App navigiert zu der neu registrierten Ansicht.

## Bedingte Routenregistrierung {#conditional-route-registration}

Oft müssen Routen basierend auf spezifischen Bedingungen wie Benutzerrollen oder dem Zustand der App hinzugefügt oder entfernt werden. Mit dynamischem Routing können Sie Routen bedingt zur Laufzeit registrieren oder deregistrieren.

### Beispiel: Bedingte Registrierung basierend auf der Benutzerrolle {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Überprüfen Sie die Benutzerrolle und registrieren Sie die entsprechenden Routen
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Navigieren Sie zum entsprechenden Dashboard
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

In diesem Beispiel:
- Die Route `/editor/dashboard` oder `/viewer/dashboard` wird dynamisch basierend auf der Benutzerrolle registriert.
- Die App navigiert zum entsprechenden Dashboard, basierend auf den Zugriffsrechten des Benutzers.

## Entfernen von Routen {#removing-routes}

So wie Routen dynamisch hinzugefügt werden können, können sie auch zur Laufzeit entfernt werden, wenn sie nicht mehr benötigt werden oder wenn sich der Kontext der App ändert.

### Beispiel: Entfernen einer registrierten Route {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Entfernen Sie die Route für die Einstellungsansicht
registry.unregister("/settings");

// Optional, entfernen Sie sie nach Komponentenklasse
registry.unregister(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch entfernt, wenn sie nicht mehr benötigt wird.

## Registrierung von Routen beim App-Start {#registering-routes-at-app-startup}

Sie können dynamische Routen während der App-Initialisierung registrieren, sodass bestimmte Ansichten basierend auf der Umgebung oder Konfiguration beim Start verfügbar sind.

### Beispiel: Registrierung von Routen während des App-Starts {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registrieren Sie eine Debug-Ansicht nur im Entwicklungsmodus
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

In diesem Beispiel:
- Eine `DebugView` wird während des App-Starts dynamisch registriert, aber nur, wenn die App im Entwicklungsmodus läuft.

## Registrierung von `@Route`-annotierten Komponenten dynamisch {#registering-route-annotated-components-dynamically}

Zusätzlich zur manuellen Definition von Routen ist es möglich, Komponenten, die bereits mit `@Route` annotiert sind, dynamisch zu registrieren. Dies ist nützlich, wenn Sie vorab annotierte Klassen nutzen möchten, diese jedoch dynamisch basierend auf der Logik der App registrieren wollen.

### Beispiel: Registrierung einer `@Route`-annotierten Komponente {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Logik für die Profilansicht
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Dynamisch registrieren der ProfileView mit ihrer @Route-Annotation
registry.register(ProfileView.class);

// Navigieren Sie zur ProfileView
router.navigate(ProfileView.class);
```

In diesem Beispiel:
- Die Klasse `ProfileView` ist mit `@Route("profile")` annotiert.
- Die Route wird zur Laufzeit dynamisch mithilfe von `registry.register(ProfileView.class)` registriert.

## Registrierung von Routen aus einem gesamten Paket {#registering-routes-from-an-entire-package}

Wenn Ihre App eine große Anzahl von Routen hat, die innerhalb eines Pakets organisiert sind, können Sie alle `@Route`-annotierten Komponenten aus dem Paket dynamisch registrieren.

### Beispiel: Registrierung aller Routen aus einem Paket {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrieren Sie alle Routen im Paket "com.myapp.admin"
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In diesem Beispiel:
- Die Methode `ofPackage` durchsucht das Paket `com.myapp.admin` und registriert alle Klassen, die mit `@Route` annotiert sind.
- Dies ist besonders nützlich für große Anwendungen mit zahlreichen Routen, die paketweise organisiert sind.

## Abrufen registrierter Routen {#retrieving-registered-routes}

Um eine Liste aller dynamisch registrierten Routen abzurufen, verwenden Sie die Klasse `RouteRegistry`. Dies ist nützlich, wenn Sie verfügbare Routen programmgesteuert verwalten oder anzeigen müssen.

### Beispiel: Abrufen und Anzeigen aller registrierten Routen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Pfad: " + route.getPath()));
```

In diesem Beispiel ruft die App alle derzeit registrierten Routen ab und gibt deren Pfade aus.

## Dynamisches Verwalten von Routen-Aliassen {#managing-route-aliases-dynamically}

webforJ ermöglicht es Ihnen, mehrere Aliasse für eine einzige Ansicht zu registrieren. Das bedeutet, dass Benutzer auf dieselbe Ansicht über verschiedene URL-Pfade zugreifen können.

### Beispiel: Dynamisches Registrieren von Routen-Aliassen {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registrieren Sie eine Primärroute
registry.register("/contact", ContactView.class);

// Registrieren Sie Aliasse für die Kontaktansicht
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

In diesem Beispiel ist die `ContactView` über drei verschiedene Pfade zugänglich: `/contact`, `/support` und `/help`.
