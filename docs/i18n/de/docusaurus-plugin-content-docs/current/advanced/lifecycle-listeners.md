---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 8134c6a2d602b0d69733de9770b44afe
---
<!-- vale off -->
# Lebenszyklus-Listener <DocChip chip='since' label='25.02' />
<!-- vale on -->

Das `AppLifecycleListener`-Interface ermöglicht externem Code, App-Lebenszyklusereignisse zu beobachten und darauf zu reagieren. Durch die Implementierung dieses Interfaces können Sie Code zu bestimmten Zeitpunkten während des App-Starts und -Herunterfahrens ausführen, ohne die `App`-Klasse selbst zu ändern.

Lebenszyklus-Listener werden zur Laufzeit automatisch entdeckt und geladen durch Konfigurationsdateien von Dienstanbietern. Jede App-Instanz erhält ihr eigenes Set von Listener-Instanzen, wodurch eine Isolation zwischen verschiedenen Apps, die in derselben Umgebung ausgeführt werden, aufrechterhalten wird.

## Wann man Lebenszyklus-Listener verwenden sollte {#when-to-use-lifecycle-listeners}

Verwenden Sie Lebenszyklus-Listener, wenn Sie:

- Ressourcen oder Dienste initialisieren müssen, bevor eine App ausgeführt wird
- Ressourcen bereinigen möchten, wenn eine App beendet wird
- Querschnittsbelange hinzufügen möchten, ohne die `App`-Klasse zu ändern
- Plugin-Architekturen erstellen möchten

## Das `AppLifecycleListener`-Interface {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillCreate(Environment env) {}     // Seit 25.03
    default void onDidCreate(App app) {}              // Seit 25.03
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info App-Isolation
Jede App-Instanz erhält ihr eigenes Set von Listener-Instanzen:

- Listener sind zwischen verschiedenen Apps isoliert
- Statische Felder in Listenern werden nicht zwischen Apps geteilt
- Listener-Instanzen werden erstellt, wenn die App startet, und zerstört, wenn sie beendet wird

Wenn Sie Daten zwischen Apps teilen müssen, verwenden Sie externe Speichermechanismen wie Datenbanken oder gemeinsame Dienste.
:::

### Lebenszyklusereignisse {#lifecycle-events}

| Ereignis          | Wann aufgerufen                                      | Häufige Verwendung                                   |
| ----------------- | ---------------------------------------------------- | --------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Nach der Initialisierung der Umgebung, vor der Erstellung der App | Konfiguration ändern, externe Konfigurationsquellen zusammenführen |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Nach der Instanziierung der App, vor der Initialisierung | Frühe App-Level-Einrichtung, Dienste registrieren    |
| `onWillRun`       | Vor der Ausführung von `app.run()`                  | Ressourcen initialisieren, Dienste konfigurieren     |
| `onDidRun`        | Nach erfolgreichem Abschluss von `app.run()`        | Hintergrundaufgaben starten, erfolgreichen Start protokollieren |
| `onWillTerminate` | Vor der Beendigung der App                           | Zustand speichern, auf Herunterfahren vorbereiten    |
| `onDidTerminate`  | Nach der Beendigung der App                          | Ressourcen bereinigen, abschließende Protokollierung |

## Erstellen eines Lebenszyklus-Listeners {#creating-a-lifecycle-listener}

### Grundlegende Implementierung {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

    @Override
    public void onWillCreate(Environment env) {
        // Konfiguration vor der Erstellung der App ändern
        Config additionalConfig = ConfigFactory.parseString(
            "myapp.feature.enabled = true"
        );
        env.setConfig(additionalConfig);
    }

    @Override
    public void onDidCreate(App app) {
        System.out.println("App erstellt: " + app.getId());
    }

    @Override
    public void onWillRun(App app) {
        System.out.println("App wird gestartet: " + app.getId());
    }

    @Override
    public void onDidRun(App app) {
        System.out.println("App gestartet: " + app.getId());
    }
}
```

### Registrieren des Listeners {#registering-the-listener}

Erstellen Sie eine Konfigurationsdatei für den Dienstanbieter:

**Datei**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Verwendung von AutoService
Es ist leicht zu vergessen, Dienstbeschreiber zu aktualisieren. Verwenden Sie Google’s [AutoService](https://github.com/google/auto/blob/main/service/README.md), um die Dienstdatei automatisch zu generieren:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implementierung
}
```
:::

## Steuerung der Ausführungsreihenfolge {#controlling-execution-order}

Wenn mehrere Listener registriert sind, können Sie ihre Ausführungsreihenfolge mit der Annotation `@AppListenerPriority` steuern. Dies ist besonders wichtig, wenn Listener Abhängigkeiten voneinander haben oder wenn bestimmte Initialisierungen vor anderen erfolgen müssen.

Prioritätswerte arbeiten in aufsteigender Reihenfolge - **niedrigere Zahlen werden zuerst ausgeführt**. Die Standardpriorität beträgt 10, sodass Listener ohne explizite Prioritätsannotationen nach denen mit niedrigeren Prioritätswerten ausgeführt werden.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Wird zuerst ausgeführt - kritische Sicherheitseinrichtung
public class SecurityListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeSecurity();
    }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Standardpriorität - allgemeines Protokollieren
public class LoggingListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeLogging();
    }
}
```

### Ausführungsfluss mit App-Hooks {#execution-flow-with-app-hooks}

Neben der Kontrolle der Reihenfolge zwischen mehreren Listenern ist es wichtig zu verstehen, wie Listener mit den eigenen Lebenszyklus-Hooks der `App`-Klasse interagieren. Für jedes Lebenszyklusereignis folgt das Framework einer bestimmten Ausführungssequenz, die bestimmt, wann Ihre Listener in Bezug auf die integrierten Hooks der App ausgeführt werden.

Das Diagramm unten veranschaulicht diesen Ausführungsfluss und zeigt den genauen Zeitpunkt, zu dem die Methoden `AppLifecycleListener` im Zusammenhang mit den entsprechenden `App`-Hooks aufgerufen werden: 

<div align="center">

![AppLifecycleListener-Listener VS `App`-Hooks ](/img/lifecycle-listeners.svg)

</div>


## Fehlerbehandlung {#error-handling}

Ausnahmen, die von Listenern ausgelöst werden, werden protokolliert, verhindern jedoch nicht, dass andere Listener ausgeführt werden oder die App läuft. Behandeln Sie immer Ausnahmen innerhalb Ihrer Listener:

```java title="Fehlerbeispiel"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Initialisierung fehlgeschlagen", e);
    }
}
```
