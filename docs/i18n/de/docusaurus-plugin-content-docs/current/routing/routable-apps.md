---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 889bb5d90fac8315d6b7b1cf766fadea
---
Routing in webforJ ist ein optionales Werkzeug. Entwickler können zwischen der webforJ-Routing-Lösung oder einem traditionellen Modell mit `Frame`-Manipulation und ohne Deep Linking wählen. Um das Routing zu aktivieren, muss die **`@Routify`**-Annotation auf der Ebene einer Klasse angewendet werden, die `App` implementiert. Dies gewährt webforJ die Autorität, den Verlauf des Browsers zu verwalten, auf Navigationsereignisse zu reagieren und die Komponenten der App basierend auf der URL zu rendern.

:::info
Um mehr über den Aufbau von UIs mit Frames, integrierten und benutzerdefinierten Komponenten zu erfahren, besuchen Sie den Abschnitt [Building UIs](../building-ui/basics).
:::

## Zweck der `@Routify` Annotation {#purpose-of-the-routify-annotation}

**`@Routify`** ermöglicht es dem Framework, automatisch Routen zu registrieren, die Sichtbarkeit von Frames zu verwalten und Routing-Verhalten wie Debugging und Frame-Initialisierung zu definieren, was dynamisches, flexibles Routing in der App erlaubt.

## Verwendung von `@Routify` {#usage-of-routify}

Die **`@Routify`**-Annotation wird auf der Klassenebene der Haupt-App-Klasse angewendet. Sie legt das Set von Paketen fest, die auf Routen gescannt werden sollen, und verwaltet andere routingbezogene Einstellungen wie die Initialisierung von Frames und die Verwaltung der Sichtbarkeit.

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
    // Anwendungslogik kommt hier hin
  }
}
```

:::tip Routify Standardkonfigurationen
Die **`@Routify`**-Annotation verfügt über angemessene Standardkonfigurationen. Es wird angenommen, dass das aktuelle Paket, in dem die App definiert ist, zusammen mit allen Unterpaketen, auf Routen gescannt werden sollte. Darüber hinaus wird angenommen, dass die App standardmäßig nur einen Frame verwaltet. Wenn Ihre App dieser Struktur folgt, ist es nicht erforderlich, der Annotation benutzerdefinierte Konfigurationen bereitzustellen.
:::

## Schlüsselaspekte von `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Das Element `packages` definiert, welche Pakete auf Routendefinitionen gescannt werden sollen. Es ermöglicht die automatische Entdeckung von Routen ohne manuelle Registrierung und vereinfacht den Prozess der Erweiterung des App-Routing-Systems.

```java
@Routify(packages = {"com.myapp.views"})
```

Wenn keine Pakete angegeben sind, wird das Standardpaket der App verwendet.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dieses Element gibt den Namen des Standardframes an, den die App initialisiert. Frames repräsentieren oberste UI-Container, und diese Einstellung steuert, wie der erste Frame benannt und verwaltet wird.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standardmäßig wird, wenn nicht explizit angegeben, der Wert auf `Routify.DEFAULT_FRAME_NAME` gesetzt.

### 3. **`initializeFrame`** {#3-initializeframe}

Das Flag `initializeFrame` bestimmt, ob das Framework den ersten Frame automatisch initiiert, wenn die App gestartet wird. Diese Einstellung auf `true` zu setzen, vereinfacht die anfängliche Frame-Einrichtung.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dieses Element steuert, ob das Framework automatisch die Sichtbarkeit von Frames während der Navigation umschalten soll. Wenn aktiviert, zeigt die passende Route automatisch den entsprechenden Frame an, während andere verborgen werden, um eine klare und fokussierte Benutzeroberfläche zu gewährleisten. Diese Einstellung ist nur relevant, wenn Ihre App mehrere Frames verwaltet.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

Das `debug`-Flag aktiviert oder deaktiviert den Routing-Debug-Modus. Wenn aktiviert, werden Routing-Informationen und Aktionen in der Konsole protokolliert, um das Debuggen während der Entwicklung zu erleichtern.

```java
@Routify(debug = true)
```

:::info Router-Debug-Modus und webforJ-Debug-Modus  
Wenn der Router-Debug-Modus auf `true` gesetzt ist, aber der webforJ-Debug-Modus auf `false`, werden keine Debugging-Informationen in der Konsole angezeigt.  
:::
