---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 95e3a7e349b0cf54679daf76d2bf209c
---
<!-- vale off -->
# Lebenszyklus-Listener <DocChip chip='since' label='25.02' />
<!-- vale on -->

Die Schnittstelle `AppLifecycleListener` ermöglicht externem Code, die Ereignisse des Anwendungslebenszyklus zu beobachten und darauf zu reagieren. Durch die Implementierung dieser Schnittstelle können Sie Code zu bestimmten Zeitpunkten während des Starts und der Beendigung der Anwendung ausführen, ohne die `App`-Klasse selbst zu ändern.

Lebenszyklus-Listener werden automatisch zur Laufzeit über Konfigurationsdateien für Dienstanbieter entdeckt und geladen. Jede Instanz der Anwendung erhält ihren eigenen Satz von Listener-Instanzen, wodurch die Isolation zwischen verschiedenen Anwendungen, die in derselben Umgebung ausgeführt werden, gewahrt bleibt.

## Wann Sie Lebenszyklus-Listener verwenden sollten {#when-to-use-lifecycle-listeners}

Verwenden Sie Lebenszyklus-Listener, wenn Sie:
- Ressourcen oder Dienste initialisieren müssen, bevor eine Anwendung ausgeführt wird
- Ressourcen bereinigen müssen, wenn eine Anwendung beendet wird  
- Querschnittsbelange hinzufügen möchten, ohne die `App`-Klasse zu ändern
- Plugin-Architekturen aufbauen möchten

## Die Schnittstelle `AppLifecycleListener` {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info Anwendung Isolierung
Jede Instanz der Anwendung erhält ihren eigenen Satz von Listener-Instanzen:
- Listener sind zwischen verschiedenen Anwendungen isoliert
- Statische Felder in Listenern werden nicht zwischen Anwendungen geteilt
- Listener-Instanzen werden erstellt, wenn die Anwendung startet, und zerstört, wenn sie beendet wird

Wenn Sie Daten zwischen Anwendungen teilen müssen, verwenden Sie externe Speichermechanismen wie Datenbanken oder gemeinsame Dienste.
:::

### Lebenszyklusereignisse {#lifecycle-events}

| Ereignis | Wann aufgerufen | Übliche Verwendungen |
|----------|----------------|---------------------|
| `onWillRun` | Vor der Ausführung von `app.run()` | Ressourcen initialisieren, Dienste konfigurieren |
| `onDidRun` | Nach erfolgreichem Abschluss von `app.run()` | Hintergrundaufgaben starten, erfolgreichen Start protokollieren |
| `onWillTerminate` | Vor der Beendigung der Anwendung | Status speichern, auf Shutdown vorbereiten |
| `onDidTerminate` | Nach der Beendigung der Anwendung | Ressourcen bereinigen, abschließendes Protokollieren |

## Erstellung eines Lebenszyklus-Listeners {#creating-a-lifecycle-listener}

### Basisimplementierung {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
    @Override
    public void onWillRun(App app) {
        System.out.println("Anwendung startet: " + app.getId());
    }
    
    @Override
    public void onDidRun(App app) {
        System.out.println("Anwendung gestartet: " + app.getId());
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
Es ist leicht, das Aktualisieren von Dienstbeschreibungen zu vergessen. Verwenden Sie Googles [AutoService](https://github.com/google/auto/blob/main/service/README.md), um die Datei automatisch zu generieren:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implementierung
}
```
:::

## Steuerung der Ausführungsreihenfolge {#controlling-execution-order}

Wenn mehrere Listener registriert sind, können Sie ihre Ausführungsreihenfolge mit der Annotation `@AppListenerPriority` steuern. Dies ist besonders wichtig, wenn Listener Abhängigkeiten untereinander haben oder wenn bestimmte Initialisierungen vor anderen erfolgen müssen.

Prioritätswerte funktionieren in aufsteigender Reihenfolge - **niedrigere Zahlen werden zuerst ausgeführt**. Die Standardpriorität beträgt 10, sodass Listener ohne explizite Prioritätsannotation nach denen mit niedrigeren Prioritätswerten ausgeführt werden.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Wird zuerst ausgeführt - kritische Sicherheitskonfiguration
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

Neben der Steuerung der Reihenfolge zwischen mehreren Listenern ist es wichtig zu verstehen, wie Listener mit den eigenen Lebenszyklus-Hooks der `App`-Klasse interagieren. Für jedes Lebenszyklusereignis folgt das Framework einer spezifischen Ausführungssequenz, die bestimmt, wann Ihre Listener im Verhältnis zu den integrierten Hooks der Anwendung ausgeführt werden.

Das folgende Diagramm veranschaulicht diesen Ausführungsfluss und zeigt den genauen Zeitpunkt, zu dem die Methoden `AppLifecycleListener` im Verhältnis zu den entsprechenden `App`-Hooks aufgerufen werden: 

<div align="center">

![AppLifecycleListener listeners VS `App` hooks  ](/img/lifecycle-listeners.svg)

</div>

## Fehlerbehandlung {#error-handling}

Ausnahmen, die von Listeners ausgelöst werden, werden protokolliert, verhindern jedoch nicht die Ausführung anderer Listener oder das Ausführen der Anwendung. Behandeln Sie immer Ausnahmen innerhalb Ihrer Listener:

```java title="Beispiel für Fehlerbehandlung"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Initialisierung fehlgeschlagen", e);
    }
}
```
