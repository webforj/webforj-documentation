---
title: Background Jobs
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 1b265d2e723c0f58c97fd2c4375f15a1
---
Wenn Benutzer auf eine Schaltfläche klicken, um einen Bericht zu generieren oder Daten zu verarbeiten, erwarten sie, dass die Benutzeroberfläche reaktionsschnell bleibt. Fortschrittsbalken sollten animiert werden, Schaltflächen sollten auf Hover reagieren, und die App sollte nicht einfrieren. Sprungs `@Async`-Annotation macht dies möglich, indem langlaufende Operationen in Hintergrund-Threads verschoben werden.

webforJ gewährleistet die Thread-Sicherheit für UI-Komponenten - alle Aktualisierungen müssen im UI-Thread erfolgen. Dies schafft eine Herausforderung: Wie aktualisieren Hintergrundaufgaben Fortschrittsbalken oder zeigen Ergebnisse an? Die Antwort ist `Environment.runLater()`, das UI-Aktualisierungen sicher von Springs Hintergrund-Threads zum UI-Thread von webforJ überträgt.

## Aktivieren der asynchronen Ausführung {#enabling-asynchronous-execution}

Die asynchrone Methoden-Ausführung von Spring erfordert eine explizite Konfiguration. Ohne diese werden Methoden, die mit `@Async` annotiert sind, synchron ausgeführt, was ihren Zweck zunichte macht.

Fügen Sie `@EnableAsync` zu Ihrer Spring Boot-Anwendungsklasse hinzu:

```java {2}
@SpringBootApplication
@EnableAsync
@Routify(packages = { "com.example.views" })
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

Die Annotation `@EnableAsync` aktiviert die Infrastruktur von Spring zur Erkennung von `@Async`-Methoden und deren Ausführung in Hintergrund-Threads.

:::tip[Spring-Async-Leitfaden]
Für eine schnelle Einführung in die `@Async`-Annotation von Spring und grundlegende Nutzungsmuster siehe [Erstellen asynchroner Methoden](https://spring.io/guides/gs/async-method).
:::

## Erstellen asynchroner Dienste {#creating-async-services}

Dienste, die mit `@Service` annotiert sind, können Methoden haben, die mit `@Async` markiert sind, um in Hintergrund-Threads ausgeführt zu werden. Diese Methoden geben typischerweise `CompletableFuture` zurück, um eine ordnungsgemäße Abschlussbehandlung und Stornierung zu ermöglichen:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Fortschritt melden
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Arbeit simulieren
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Aufgabe erfolgreich aus dem Hintergrunddienst abgeschlossen!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Dieser Dienst akzeptiert einen Fortschritts-Callback (`Consumer<Integer>`), der aus dem Hintergrund-Thread aufgerufen wird. Das Callback-Muster ermöglicht es dem Dienst, Fortschritte zu melden, ohne über UI-Komponenten Bescheid zu wissen.

Die Methode simuliert eine 5-sekündige Aufgabe mit 10 Fortschrittsaktualisierungen. In der Produktion würde dies tatsächliche Arbeiten wie Datenbankabfragen oder Datei­verarbeitung sein. Die Ausnahmebehandlung stellt den Interrupt-Status wieder her, um eine ordnungsgemäße Aufgabe-Stornierung zu unterstützen, wenn `cancel(true)` aufgerufen wird.

## Verwendung von Hintergrundaufgaben in Ansichten {#using-background-tasks-in-views}

Die Ansicht erhält den Hintergrunddienst über die Konstruktor-Injektion:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Hintergrundaufgabe starten");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Dienst wird von Spring injiziert
    asyncBtn.addClickListener(e -> {
      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring injiziert den `BackgroundService` in den Konstruktor der Ansicht, genau wie bei jedem anderen Spring-Bean. Die Ansicht verwendet diesen Dienst, um Hintergrundaufgaben zu starten. Das Schlüsselkonzept: Rückrufe aus dem Dienst werden in Hintergrund-Threads ausgeführt, sodass alle UI-Aktualisierungen innerhalb dieser Rückrufe `Environment.runLater()` verwenden müssen, um die Ausführung in den UI-Thread zu übertragen.

Die Abschlussbehandlung erfordert dasselbe sorgfältige Thread-Management:

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(() -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("Aufgabe fehlgeschlagen: " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

Der Rückruf `whenComplete` wird ebenfalls in einem Hintergrund-Thread ausgeführt. Jede UI-Operation - die Schaltfläche aktivieren, den Fortschrittsbalken ausblenden, Toasts anzeigen - muss in `Environment.runLater()` gewrappt werden. Ohne dieses Wrapping gibt webforJ Ausnahmen aus, da Hintergrund-Threads nicht auf UI-Komponenten zugreifen können.

:::warning[Thread-Sicherheit]
Jede UI-Aktualisierung von einem Hintergrund-Thread muss in `Environment.runLater()` gewrappt werden. Diese Regel hat keine Ausnahme. Direkter Komponenten-Zugriff von `@Async`-Methoden schlägt immer fehl.
:::

:::tip[Erfahren Sie mehr über Thread-Sicherheit]
Für detaillierte Informationen über das Thread-Modell von webforJ, das Ausführungsverhalten und welche Operationen `Environment.runLater()` erfordern, siehe [Asynchrone Aktualisierungen](../../advanced/asynchronous-updates).
:::

## Aufgabenstornierung und Bereinigung {#task-cancellation-and-cleanup}

Ein ordnungsgemäßes Lebenszyklusmanagement verhindert Gedächtnislecks und unerwünschte UI-Aktualisierungen. Die Ansicht speichert die Referenz auf `CompletableFuture`:

```java
private CompletableFuture<String> currentTask;
```

Wenn die Ansicht zerstört wird, storniert sie alle laufenden Aufgaben:

```java
@Override
protected void onDestroy() {
  // Stornieren Sie die Aufgabe, wenn die Ansicht zerstört wird
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

Der Parameter `cancel(true)` ist entscheidend. Er unterbricht den Hintergrund-Thread, wodurch blockierende Operationen wie `Thread.sleep()` `InterruptedException` auslösen. Dies ermöglicht eine sofortige Aufgabenbeendigung. Ohne das Interrupt-Flag (`cancel(false)`) würde die Aufgabe weiterhin ausgeführt, bis sie explizit auf die Stornierung prüft.

Diese Bereinigung verhindert mehrere Probleme:
- Hintergrund-Threads verbrauchen weiterhin Ressourcen, nachdem die Ansicht verschwunden ist
- UI-Aktualisierungen versuchen, zerstörte Komponenten zu modifizieren
- Gedächtnislecks von Rückrufen, die Referenzen auf UI-Komponenten halten
