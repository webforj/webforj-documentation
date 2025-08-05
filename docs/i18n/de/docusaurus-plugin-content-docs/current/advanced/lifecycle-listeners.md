---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 475cc2842226c605bbe7f2ee931955dd
---
<!-- vale off -->
# Lebenszyklus-Listener <DocChip chip='since' label='25.02' />
<!-- vale on -->

Das `AppLifecycleListener`-Interface ermöglicht es externem Code, App-Lebensereignisse zu beobachten und darauf zu reagieren. Durch die Implementierung dieses Interfaces können Sie Code zu spezifischen Zeitpunkten während des App-Starts und -Herunterfahrens ausführen, ohne die `App`-Klasse selbst zu ändern.

Lebenszyklus-Listener werden zur Laufzeit automatisch über Konfigurationsdateien des Dienstanbieters entdeckt und geladen. Jede App-Instanz erhält ihre eigenen Satz von Listener-Instanzen, wobei die Isolation zwischen verschiedenen Apps, die in derselben Umgebung ausgeführt werden, gewahrt bleibt.

## Wann man Lebenszyklus-Listener verwenden sollte {#when-to-use-lifecycle-listeners}

Verwenden Sie Lebenszyklus-Listener, wenn Sie:
- Ressourcen oder Dienste initialisieren möchten, bevor eine App ausgeführt wird
- Ressourcen beim Beenden einer App bereinigen möchten
- Querschnittsbelange hinzufügen möchten, ohne die `App`-Klasse zu ändern
- Plugin-Architekturen erstellen möchten

## Das `AppLifecycleListener`-Interface {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info App-Isolation
Jede App-Instanz erhält ihren eigenen Satz von Listener-Instanzen:
- Listener sind zwischen verschiedenen Apps isoliert
- Statische Felder in Listenern werden nicht zwischen Apps geteilt
- Listener-Instanzen werden erstellt, wenn die App startet, und gelöscht, wenn sie beendet wird

Wenn Sie Daten zwischen Apps teilen müssen, verwenden Sie externe Speichermechanismen wie Datenbanken oder gemeinsame Dienste.
:::

### Lebenszyklusereignisse {#lifecycle-events}

| Ereignis | Wann aufgerufen | Häufige Verwendungen |
|----------|----------------|----------------------|
| `onWillRun` | Bevor `app.run()` ausgeführt wird | Ressourcen initialisieren, Dienste konfigurieren |
| `onDidRun` | Nachdem `app.run()` erfolgreich abgeschlossen wurde | Hintergrundaufgaben starten, erfolgreichen Start protokollieren |
| `onWillTerminate` | Vor dem Beenden der App | Zustand speichern, auf Herunterfahren vorbereiten |
| `onDidTerminate` | Nach dem Beenden der App | Ressourcen bereinigen, abschließendes Protokollieren |

## Erstellen eines Lebenszyklus-Listeners {#creating-a-lifecycle-listener}

### Grundlegende Implementierung {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
    @Override
    public void onWillRun(App app) {
        System.out.println("App startet: " + app.getId());
    }
    
    @Override
    public void onDidRun(App app) {
        System.out.println("App gestartet: " + app.getId());
    }
}
```

### Registrieren des Listeners {#registering-the-listener}

Erstellen Sie eine Datei zur Konfiguration des Dienstanbieters:

**Datei**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Verwendung von AutoService
Es ist leicht zu vergessen, Dienstbeschreibungen zu aktualisieren. Verwenden Sie Googles [AutoService](https://github.com/google/auto/blob/main/service/README.md), um die Dienstdatei automatisch zu generieren:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implementierung
}
```
:::

## Steuerung der Ausführungsreihenfolge {#controlling-execution-order}

Wenn mehrere Listener registriert sind, können Sie deren Ausführungsreihenfolge mit der `@AppListenerPriority`-Annotation steuern. Dies ist besonders wichtig, wenn Listener Abhängigkeiten zueinander haben oder wenn bestimmte Initialisierungen vor anderen erfolgen müssen.

Prioritätswerte wirken in aufsteigender Reihenfolge - **niedrigere Zahlen werden zuerst ausgeführt**. Die Standardpriorität beträgt 10, sodass Listener ohne explizite Prioritätsannotationen nach denen mit niedrigeren Prioritätswerten ausgeführt werden.

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

Über die Kontrolle der Reihenfolge zwischen mehreren Listenern hinaus ist es wichtig zu verstehen, wie Listener mit den Lifecycle-Hooks der `App`-Klasse interagieren. Für jedes Lebenszyklusereignis folgt das Framework einer spezifischen Ausführungsreihenfolge, die bestimmt, wann Ihre Listener im Verhältnis zu den integrierten Hooks der App ausgeführt werden.

Das folgende Diagramm zeigt diesen Ausführungsfluss und die präzise Zeitplanung, wann die `AppLifecycleListener`-Methoden im Verhältnis zu den entsprechenden `App`-Hooks aufgerufen werden:

<div align="center">

![AppLifecycleListener listener VS `App` hooks](/img/lifecycle-listeners.svg)

</div>


## Fehlerbehandlung {#error-handling}

Ausnahmen, die von Listenern ausgelöst werden, werden protokolliert, verhindern jedoch nicht, dass andere Listener ausgeführt werden oder die App weiterhin läuft. Stellen Sie immer sicher, dass Ausnahmen innerhalb Ihrer Listener behandelt werden:

```java title="Fehlerbehandlungsbeispiel"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Initialisierung fehlgeschlagen", e);
    }
}
```
