---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 8f6b7b85dd246adc8d98c8a5bf994a39
---
In addition to [die Routen mit den `@Route`-Annotations zu registrieren](./defining-routes), ist es möglich, Routen dynamisch zur Laufzeit basierend auf der Anwendungslogik, den Benutzerrollen oder anderen Bedingungen zu registrieren, zu aktualisieren oder zu entfernen. Diese Flexibilität ermöglicht es Ihnen, die Navigation dynamischer zu verwalten, anstatt Routen zur Compile-Zeit statisch zu definieren.

## Dynamische Routenregistrierung {#registering-routes-dynamically}

Sie können eine Route dynamisch mit der Klasse `RouteRegistry` registrieren, die über den `Router` zugänglich ist. Dadurch können Sie während der Laufzeit neue Routen hinzufügen und flexible Navigation ermöglichen.

### Beispiel: Dynamische Route registrieren {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registriere die Einstellungen-Route dynamisch
registry.register("/settings", SettingsView.class);

// Navigiere zur Einstellungen-Ansicht
router.navigate(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch registriert, und die App navigiert zur neu registrierten Ansicht.

## Bedingte Routenregistrierung {#conditional-route-registration}

Oft müssen Routen basierend auf bestimmten Bedingungen, wie Benutzerrollen oder dem Zustand der App, hinzugefügt oder entfernt werden. Mit dynamischer Routenführung können Sie Routen bedingt zur Laufzeit registrieren oder abmelden.

### Beispiel: Bedingte Registrierung basierend auf der Benutzerrolle {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Überprüfe die Benutzerrolle und registriere die entsprechenden Routen
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Navigiere zum entsprechenden Dashboard
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

In diesem Beispiel:
- Die Route `/editor/dashboard` oder `/viewer/dashboard` wird dynamisch basierend auf der Benutzerrolle registriert.
- Die App navigiert zum entsprechenden Dashboard basierend auf den Zugangsrechten des Benutzers.

## Routen entfernen {#removing-routes}

Genauso wie Routen dynamisch hinzugefügt werden können, können sie auch zur Laufzeit entfernt werden, wenn sie nicht mehr benötigt werden oder sich der Kontext der App ändert.

### Beispiel: Entfernen einer registrierten Route {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Entferne die Route für die Einstellungen-Ansicht
registry.unregister("/settings");

// Optional nach Komponentenklasse entfernen
registry.unregister(SettingsView.class);
```

In diesem Beispiel wird die Route `/settings` dynamisch entfernt, wenn sie nicht mehr benötigt wird.

## Routen bei App-Start registrieren {#registering-routes-at-app-startup}

Sie können dynamische Routen während der Initialisierung der App registrieren, wodurch bestimmte Ansichten basierend auf der Umgebung oder Konfiguration beim Start verfügbar sind.

### Beispiel: Routen während des App-Starts registrieren {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Registriere eine Debugansicht nur im Entwicklungsmodus
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

In diesem Beispiel:
- Eine `DebugView` wird dynamisch beim Start der App registriert, jedoch nur, wenn die App im Entwicklungsmodus läuft.

## Dynamische Registrierung von mit `@Route` annotierten Komponenten {#registering-route-annotated-components-dynamically}

Neben der manuellen Definition von Routen ist es möglich, Komponenten zu registrieren, die bereits mit `@Route` annotiert sind. Dies ist nützlich, wenn Sie vorannotierte Klassen nutzen möchten, diese jedoch dynamisch basierend auf der Anwendungslogik registrieren möchten.

### Beispiel: Registrieren einer mit `@Route` annotierten Komponente {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Logik der Profilansicht
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Dynamische Registrierung der ProfileView mit ihrer @Route-Annotation
registry.register(ProfileView.class);

// Navigiere zur ProfileView
router.navigate(ProfileView.class);
```

In diesem Beispiel:
- Die Klasse `ProfileView` ist mit `@Route("profile")` annotiert.
- Die Route wird zur Laufzeit dynamisch mit `registry.register(ProfileView.class)` registriert.

## Routen aus einem gesamten Paket registrieren {#registering-routes-from-an-entire-package}

Wenn Ihre App eine große Anzahl von Routen hat, die in einem Paket organisiert sind, können Sie alle `@Route`-annotierten Komponenten aus dem Paket dynamisch registrieren.

### Beispiel: Registrieren aller Routen aus einem Paket {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registriere alle Routen im "com.myapp.admin" Paket
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In diesem Beispiel:
- Die Methode `ofPackage` durchsucht das Paket `com.myapp.admin` und registriert alle Klassen, die mit `@Route` annotiert sind.
- Dies ist besonders nützlich für große Anwendungen mit zahlreichen Routen, die nach Paketen organisiert sind.

## Registrierte Routen abrufen {#retrieving-registered-routes}

Um eine Liste aller dynamisch registrierten Routen abzurufen, verwenden Sie die Klasse `RouteRegistry`. Dies ist nützlich, wenn Sie Routen programmgesteuert verwalten oder anzeigen müssen.

### Beispiel: Abrufen und Anzeigen aller registrierten Routen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Pfad: " + route.getPath()));
```

In diesem Beispiel ruft die App alle derzeit registrierten Routen ab und druckt deren Pfade aus.

## Dynamisches Verwalten von Routen-Aliasen {#managing-route-aliases-dynamically}

webforJ ermöglicht es Ihnen, mehrere Aliase für eine einzelne Ansicht zu registrieren. Das bedeutet, dass Benutzer die gleiche Ansicht über unterschiedliche URL-Pfade aufrufen können.

### Beispiel: Dynamisches Registrieren von Routen-Aliassen {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Registriere eine primäre Route
registry.register("/contact", ContactView.class);

// Registriere Aliase für die Kontaktansicht
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

In diesem Beispiel ist die `ContactView` über drei verschiedene Pfade zugänglich: `/contact`, `/support` und `/help`.
