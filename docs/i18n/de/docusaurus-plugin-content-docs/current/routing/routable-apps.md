---
sidebar_position: 2
title: Routable Apps
description: >-
  Enable webforJ routing with the @Routify annotation to scan packages, manage
  frames, and control browser history.
_i18n_hash: bea0848523a00ddfff8d79265ea699ac
---
Das Routing in webforJ ist ein optionales Tool. Entwickler können zwischen der webforJ-Routing-Lösung oder einem traditionellen Modell mit `Frame`-Manipulation und ohne Deep Linking wählen. Um das Routing zu aktivieren, muss die **`@Routify`**-Annotation auf der Ebene einer Klasse, die `App` implementiert, angewendet werden. Dadurch erhält webforJ die Befugnis, den Verlauf des Browsers zu verwalten, auf Navigationsereignisse zu reagieren und die Komponenten der App basierend auf der URL zu rendern.

:::info
Um mehr über den Aufbau von UIs mit Frames, eingebauten und benutzerdefinierten Komponenten zu erfahren, besuchen Sie [Building UIs](../building-ui/overview).
:::

## Zweck der `@Routify`-Annotation {#purpose-of-the-routify-annotation}

Die **`@Routify`**-Annotation ermöglicht es dem Framework, Routen automatisch zu registrieren, die Sichtbarkeit von Frames zu verwalten und Routing-Verhalten wie Debugging und Frame-Initialisierung zu definieren, was dynamisches, flexibles Routing in der App ermöglicht.

## Verwendung von `@Routify` {#usage-of-routify}

Die **`@Routify`**-Annotation wird auf der Klasse der Haupt-App-Klasse angewendet. Sie gibt die Menge der Pakete an, die nach Routen durchsucht werden sollen, und verwaltet andere routingbezogene Einstellungen wie die Initialisierung von Frames und die Sichtbarkeitsverwaltung.

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
    // Anwendungslogik geht hier hin
  }
}
```

:::tip Routify Standardkonfigurationen
Die **`@Routify`**-Annotation kommt mit angemessenen Standardkonfigurationen. Sie geht davon aus, dass das aktuelle Paket, in dem die App definiert ist, sowie alle seine Unterpakete nach Routen durchsucht werden sollen. Darüber hinaus geht sie davon aus, dass die App standardmäßig nur einen Frame verwaltet. Wenn Ihre App dieser Struktur folgt, ist es nicht erforderlich, der Annotation benutzerdefinierte Konfigurationen bereitzustellen.
:::

## Hauptbestandteile von `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Das Element `packages` definiert, welche Pakete nach Routendefinitionen durchsucht werden sollen. Es ermöglicht die automatische Entdeckung von Routen ohne manuelle Registrierung und vereinfacht den Prozess der Erweiterung des App-Routing-Systems.

```java
@Routify(packages = {"com.myapp.views"})
```

Wenn keine Pakete angegeben sind, wird das Standardpaket der App verwendet.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dieses Element gibt den Namen des Standardframes an, den die App initialisiert. Frames repräsentieren die obersten UI-Container, und diese Einstellung steuert, wie der erste Frame benannt und verwaltet wird.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standardmäßig, wenn nicht ausdrücklich angegeben, ist der Wert auf `Routify.DEFAULT_FRAME_NAME` gesetzt.

### 3. **`initializeFrame`** {#3-initializeframe}

Das Flag `initializeFrame` bestimmt, ob das Framework den ersten Frame automatisch initialisieren soll, wenn die App startet. Die Einstellung auf `true` vereinfacht die anfängliche Frame-Konfiguration.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dieses Element steuert, ob das Framework die Sichtbarkeit von Frames während der Navigation automatisch umschalten soll. Wenn aktiviert, zeigt die übereinstimmende Route automatisch den entsprechenden Frame an und blendet andere aus, um eine saubere und fokussierte UI zu gewährleisten. Diese Einstellung ist nur relevant, wenn Ihre App mehrere Frames verwaltet.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

Das `debug`-Flag aktiviert oder deaktiviert den Routing-Debug-Modus. Wenn aktiviert, werden Routing-Informationen und Aktionen in der Konsole protokolliert, um das Debugging während der Entwicklung zu erleichtern.

```java
@Routify(debug = true)
```

:::info Router-Debug-Modus und webforJ-Debug-Modus
Wenn der Router-Debug-Modus auf `true` gesetzt ist, der webforJ-Debug-Modus jedoch auf `false`, werden keine Debugging-Informationen in der Konsole angezeigt.
:::
