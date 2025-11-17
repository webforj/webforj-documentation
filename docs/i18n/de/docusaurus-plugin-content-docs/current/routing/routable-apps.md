---
sidebar_position: 2
title: Routbare Apps
_i18n_hash: 6d09e8327e3391cedd4e8059d9390d09
---
Routing in webforJ ist ein optionales Werkzeug. Entwickler können zwischen der webforJ-Routing-Lösung oder einem traditionellen Modell mit `Frame`-Manipulation und ohne tiefes Linking wählen. Um Routing zu aktivieren, muss die **`@Routify`**-Annotation auf der Ebene einer Klasse angewendet werden, die `App` implementiert. Dies gewährt webforJ die Befugnis, den Browserverlauf zu verwalten, auf Navigationsevents zu reagieren und die Komponenten der App basierend auf der URL zu rendern.

:::info
Um mehr über den Aufbau von UIs mit Frames, integrierten und benutzerdefinierten Komponenten zu erfahren, besuchen Sie den Abschnitt [Building UIs](../building-ui/basics).
:::

## Zweck der `@Routify`-Annotation {#purpose-of-the-routify-annotation}

**`@Routify`** ermöglicht dem Framework, Routen automatisch zu registrieren, die Sichtbarkeit von Frames zu verwalten und Routing-Verhalten wie Debugging und Frame-Initialisierung zu definieren, wodurch dynamisches, flexibles Routing in der App ermöglicht wird.

## Verwendung von `@Routify` {#usage-of-routify}

Die **`@Routify`**-Annotation wird auf der Klassenebene der Haupt-App-Klasse angewendet. Sie gibt die Menge von Paketen an, die auf Routen gescannt werden sollen, und verwaltet andere routingbezogene Einstellungen wie die Frame-Initialisierung und das Sichtbarkeitsmanagement.

Hier ist ein einfaches Beispiel:

```java
@Routify(
  packages = {"com.myapp.views"},
  defaultFrameName = "MainFrame",
  initializeFrame = true,
  manageFramesVisibility = false,
  debug = true
)
public class MyApp extends App {

  @Override
  public void run() {
    // Anwendungslogik hier
  }
}
```

:::tip Routify Standardkonfigurationen
Die **`@Routify`**-Annotation kommt mit angemessenen Standardkonfigurationen. Es wird davon ausgegangen, dass das aktuelle Paket, in dem die App definiert ist, zusammen mit allen seinen Unterpaketen, auf Routen gescannt werden soll. Außerdem wird davon ausgegangen, dass die App standardmäßig nur einen Frame verwaltet. Wenn Ihre App dieser Struktur folgt, ist es nicht erforderlich, der Annotation benutzerdefinierte Konfigurationen bereitzustellen.
:::

## Schlüsselfunktionen von `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Das Element `packages` definiert, welche Pakete auf Routendefinitionen gescannt werden sollen. Es ermöglicht die automatische Entdeckung von Routen ohne manuelle Registrierung und optimiert den Prozess zur Erweiterung des App-Routing-Systems.

```java
@Routify(packages = {"com.myapp.views"})
```

Wenn keine Pakete angegeben sind, wird das Standardpaket der App verwendet.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dieses Element gibt den Namen des Standardframes an, den die App initialisiert. Frames stellen Top-Level UI-Container dar, und diese Einstellung steuert, wie der erste Frame benannt und verwaltet wird.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standardmäßig, wenn nicht ausdrücklich angegeben, wird der Wert auf `Routify.DEFAULT_FRAME_NAME` gesetzt.

### 3. **`initializeFrame`** {#3-initializeframe}

Das Flag `initializeFrame` bestimmt, ob das Framework den ersten Frame automatisch initialisieren soll, wenn die App startet. Das Setzen dieser Option auf `true` vereinfacht die initiale Frame-Einrichtung.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dieses Element steuert, ob das Framework die Sichtbarkeit der Frames während der Navigation automatisch umschalten soll. Wenn aktiviert, zeigt die übereinstimmende Route automatisch den entsprechenden Frame an, während andere ausgeblendet werden, um eine klare und fokussierte UI zu gewährleisten. Diese Einstellung ist nur relevant, wenn Ihre App mehrere Frames verwaltet.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

Das Flag `debug` aktiviert oder deaktiviert den Routing-Debug-Modus. Wenn aktiviert, werden Routing-Informationen und Aktionen in der Konsole zur einfacheren Fehlersuche während der Entwicklung protokolliert.

```java
@Routify(debug = true)
```

:::info Router-Debug-Modus und webforJ-Debug-Modus  
Wenn der Router-Debug-Modus auf `true` gesetzt ist, der webforJ-Debug-Modus jedoch auf `false` gesetzt ist, werden keine Debugging-Informationen in der Konsole angezeigt.  
:::
