---
sidebar_position: 2
title: Routable Apps
_i18n_hash: edec1086b0723febd831816f8d1fa76a
---
Routing in webforJ ist ein optionales Werkzeug. Entwickler können zwischen der webforJ-Routing-Lösung oder einem traditionellen Modell mit `Frame`-Manipulation und ohne Deep Linking wählen. Um Routing zu aktivieren, muss die **`@Routify`**-Annotation auf der Ebene einer Klasse, die `App` implementiert, angewendet werden. Dies gibt webforJ die Autorität, den Browser-Verlauf zu verwalten, auf Navigationsereignisse zu reagieren und die Komponenten der Anwendung basierend auf der URL zu rendern.

:::info
Um mehr über den Aufbau von UIs mit Frames, integrierten und benutzerdefinierten Komponenten zu erfahren, besuchen Sie [Building UIs](../building-ui/overview).
:::

## Zweck der `@Routify`-Annotation {#purpose-of-the-routify-annotation}

Die **`@Routify`**-Annotation ermöglicht es dem Framework, Routen automatisch zu registrieren, die Sichtbarkeit von Frames zu verwalten und Routing-Verhalten wie Debugging und Frame-Initialisierung zu definieren, was dynamisches und flexibles Routing in der Anwendung ermöglicht.

## Verwendung von `@Routify` {#usage-of-routify}

Die **`@Routify`**-Annotation wird auf Klassenebene der Hauptanwendungsklasse angewendet. Sie gibt die Menge an Paketen an, die auf Routen gescannt werden sollen, und behandelt andere routingbezogene Einstellungen wie Frame-Initialisierung und Sichtbarkeitsverwaltung.

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
Die **`@Routify`**-Annotation kommt mit angemessenen Standardkonfigurationen. Sie geht davon aus, dass das aktuelle Paket, in dem die App definiert ist, zusammen mit all seinen Unterpaketen auf Routen gescannt werden sollte. Darüber hinaus geht sie davon aus, dass die App standardmäßig nur einen Frame verwaltet. Wenn Ihre App dieser Struktur folgt, ist es nicht notwendig, benutzerdefinierte Konfigurationen für die Annotation anzugeben.
:::

## Wesentliche Elemente von `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Das Element `packages` definiert, welche Pakete nach Routendefinitionen gescannt werden sollen. Es ermöglicht die automatische Entdeckung von Routen ohne manuelle Registrierung und vereinfacht den Prozess der Erweiterung des App-Routing-Systems.

```java
@Routify(packages = {"com.myapp.views"})
```

Wenn keine Pakete angegeben sind, wird das Standardpaket der App verwendet.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dieses Element gibt den Namen des Standardframes an, den die App initialisiert. Frames repräsentieren oberste UI-Container, und diese Einstellung steuert, wie der erste Frame benannt und verwaltet wird.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standardmäßig, wenn nicht ausdrücklich angegeben, wird der Wert auf `Routify.DEFAULT_FRAME_NAME` gesetzt.

### 3. **`initializeFrame`** {#3-initializeframe}

Das `initializeFrame`-Flag bestimmt, ob das Framework den ersten Frame automatisch initialisieren soll, wenn die App gestartet wird. Das Festlegen auf `true` vereinfacht die anfängliche Frame-Einrichtung.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dieses Element steuert, ob das Framework automatisch die Sichtbarkeit von Frames während der Navigation umschalten soll. Wenn aktiviert, zeigt die übereinstimmende Route automatisch den entsprechenden Frame an, während andere ausgeblendet werden, um eine klare und fokussierte UI zu gewährleisten. Diese Einstellung ist nur relevant, wenn Ihre App mehrere Frames verwaltet.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

Das `debug`-Flag aktiviert oder deaktiviert den Routing-Debug-Modus. Wenn aktiviert, werden Routing-Informationen und -Aktionen in der Konsole protokolliert, um das Debuggen während der Entwicklung zu erleichtern.

```java
@Routify(debug = true)
```

:::info Router-Debug-Modus und webforJ-Debug-Modus  
Wenn der Router-Debug-Modus auf `true` gesetzt ist, der webforJ-Debug-Modus jedoch auf `false`, werden keine Debugging-Informationen in der Konsole angezeigt.  
:::
