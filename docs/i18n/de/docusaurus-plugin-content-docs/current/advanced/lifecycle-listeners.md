---
sidebar_position: 10
title: Lifecycle Listeners
_i18n_hash: ffb3121402861d501b322c7efca6f669
---
<!-- vale off -->
# Lebenszyklus-Listener <DocChip chip='since' label='25.02' />
<!-- vale on -->

Das `AppLifecycleListener`-Interface ermöglicht externem Code, die App-Lebensereignisse zu beobachten und darauf zu reagieren. Durch die Implementierung dieses Interfaces können Sie Code zu spezifischen Zeitpunkten während des Starts und Herunterfahrens der App ausführen, ohne die `App`-Klasse selbst zu ändern.

Lebenszyklus-Listener werden zur Laufzeit automatisch über Dienstanbieter-Konfigurationsdateien entdeckt und geladen. Jede App-Instanz erhält ihre eigenen Satz von Listener-Instanzen, wodurch die Isolation zwischen verschiedenen Apps, die in derselben Umgebung ausgeführt werden, gewahrt bleibt.

## Wann Lebenszyklus-Listener verwenden {#when-to-use-lifecycle-listeners}

Verwenden Sie Lebenszyklus-Listener, wenn Sie:

- Ressourcen oder Dienste initialisieren müssen, bevor eine App läuft
- Ressourcen bereinigen müssen, wenn eine App beendet wird
- Querbeschneidungsanliegen hinzufügen möchten, ohne die `App`-Klasse zu ändern
- Plugin-Architekturen aufbauen möchten

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
Jede App-Instanz erhält ihre eigenen Satz von Listener-Instanzen:

- Listener sind zwischen verschiedenen Apps isoliert
- Statische Felder in Listenern werden nicht zwischen Apps geteilt
- Listener-Instanzen werden erstellt, wenn die App startet, und werden zerstört, wenn sie beendet wird

Wenn Sie Daten zwischen Apps teilen müssen, verwenden Sie externe Speichermechanismen wie Datenbanken oder gemeinsame Dienste.
:::

### Lebenszyklusereignisse {#lifecycle-events}

| Ereignis             | Wann aufgerufen                                           | Häufige Verwendungen                                 |
| ----------------- | ----------------------------------------------------- | --------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Nach der Initialisierung der Umgebung, vor der App-Erstellung  | Konfiguration ändern, externe Konfigurationsquellen zusammenführen |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Nach der Instanziierung der App, vor der Initialisierung        | Frühe App-Einrichtungsmaßnahmen, Dienste registrieren            |
| `onWillRun`       | Bevor `app.run()` ausgeführt wird                           | Ressourcen initialisieren, Dienste konfigurieren            |
| `onDidRun`        | Nachdem `app.run()` erfolgreich abgeschlossen ist              | Hintergrundaufgaben starten, erfolgreichen Start protokollieren      |
| `onWillTerminate` | Vor der Beendigung der App                                | Zustand speichern, auf Herunterfahren vorbereiten                    |
| `onDidTerminate`  | Nach der Beendigung der App                                 | Ressourcen bereinigen, finales Protokollieren                   |

## Erstellen eines Lebenszyklus-Listeners {#creating-a-lifecycle-listener}

### Grundimplementierung {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

  @Override
  public void onWillCreate(Environment env) {
    // Konfiguration vor der App-Erstellung ändern
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
    System.out.println("App startet: " + app.getId());
  }

  @Override
  public void onDidRun(App app) {
    System.out.println("App gestartet: " + app.getId());
  }
}
```

### Registrieren des Listeners {#registering-the-listener}

Erstellen Sie eine Dienstanbieter-Konfigurationsdatei:

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

Wenn mehrere Listener registriert sind, können Sie deren Ausführungsreihenfolge mithilfe der Annotation `@AppListenerPriority` steuern. Dies ist besonders wichtig, wenn Listener voneinander abhängig sind oder wenn bestimmte Initialisierungen vor anderen erfolgen müssen.

Prioritätswerte funktionieren in aufsteigender Reihenfolge - **niedrigere Zahlen werden zuerst ausgeführt**. Die Standardpriorität ist 10, sodass Listener ohne explizite Prioritätsannotation nach denen mit niedrigeren Prioritätswerten ausgeführt werden.

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

Neben der Steuerung der Reihenfolge zwischen mehreren Listenern ist es wichtig, zu verstehen, wie Listener mit den eigenen Lebenszyklus-Hooks der `App`-Klasse interagieren. Für jedes Lebenszyklusereignis folgt das Framework einer spezifischen Ausführungsreihenfolge, die bestimmt, wann Ihre Listener im Verhältnis zu den integrierten Hooks der App ausgeführt werden.

Das folgende Diagramm veranschaulicht diesen Ausführungsfluss und zeigt den genauen Zeitpunkt, wann die Methoden von `AppLifecycleListener` im Verhältnis zu den entsprechenden `App`-Hooks aufgerufen werden:

<div align="center">

![AppLifecycleListener listeners VS `App` hooks  ](/img/lifecycle-listeners.svg)

</div>

## Fehlerbehandlung {#error-handling}

Ausnahmen, die von Listenern ausgelöst werden, werden protokolliert, verhindern jedoch nicht die Ausführung anderer Listener oder das Ausführen der App. Behandeln Sie immer Ausnahmen innerhalb Ihrer Listener:

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
