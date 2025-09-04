---
title: Background Jobs
sidebar_position: 25
_i18n_hash: d419b53d933af4ef48890b8be2eab4dc
---
Wenn Benutzer auf eine Schaltfläche klicken, um einen Bericht zu generieren oder Daten zu verarbeiten, erwarten sie, dass die Benutzeroberfläche reaktionsschnell bleibt. Fortschrittsbalken sollten animiert werden, Schaltflächen sollten auf Hover reagieren, und die App sollte nicht einfrieren. Springs `@Async`-Annotation macht dies möglich, indem sie lang laufende Operationen in Hintergrundthreads verlagert.

webforJ sorgt für Thread-Sicherheit der UI-Komponenten - alle Updates müssen im UI-Thread erfolgen. Dies schafft eine Herausforderung: Wie können Hintergrundaufgaben Fortschrittsbalken aktualisieren oder Ergebnisse anzeigen? Die Antwort ist `Environment.runLater()`, das sicher UI-Updates von Springs Hintergrundthreads zum UI-Thread von webforJ überträgt.

## Aktivieren der asynchronen Ausführung {#enabling-asynchronous-execution}

Die asynchrone Methodenausführung von Spring erfordert eine explizite Konfiguration. Ohne diese werden Methoden, die mit `@Async` annotiert sind, synchron ausgeführt, was ihren Zweck untergräbt.

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

Die Annotation `@EnableAsync` aktiviert die Infrastruktur von Spring zur Erkennung von `@Async`-Methoden und deren Ausführung in Hintergrundthreads.

:::tip[Spring-Async-Guide]
Für eine schnelle Einführung in Springs `@Async`-Annotation und grundlegende Nutzungsmuster siehe [Asynchrone Methoden erstellen](https://spring.io/guides/gs/async-method).
:::

## Erstellen von asynchronen Diensten {#creating-async-services}

Dienste, die mit `@Service` annotiert sind, können Methoden haben, die mit `@Async` markiert sind, um in Hintergrundthreads ausgeführt zu werden. Diese Methoden geben typischerweise `CompletableFuture` zurück, um eine ordnungsgemäße Abschlussbehandlung und Stornierung zu ermöglichen:

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

Dieser Dienst akzeptiert ein Fortschritts-Callback (`Consumer<Integer>`), das aus dem Hintergrundthread aufgerufen wird. Das Callback-Muster ermöglicht es dem Dienst, den Fortschritt zu melden, ohne über UI-Komponenten Bescheid zu wissen.

Die Methode simuliert eine 5-Sekunden-Aufgabe mit 10 Fortschrittsaktualisierungen. In der Produktion wäre dies echte Arbeit wie Datenbankabfragen oder Datei-Verarbeitung. Die Ausnahmebehandlung stellt den Interrupt-Status wieder her, um eine ordnungsgemäße Stornierung der Aufgabe zu unterstützen, wenn `cancel(true)` aufgerufen wird.

## Verwendung von Hintergrundaufgaben in Ansichten {#using-background-tasks-in-views}

Die Ansicht erhält den Hintergrunddienst über die Konstruktorinjektion:

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

Spring injiziert den `BackgroundService` in den Konstruktor der Ansicht, genau wie jede andere Spring-Bean. Die Ansicht verwendet diesen Dienst, um Hintergrundaufgaben zu starten. Das Schlüsselkonzept: Callbacks aus dem Dienst werden in Hintergrundthreads ausgeführt, sodass alle UI-Aktualisierungen innerhalb dieser Callbacks `Environment.runLater()` verwenden müssen, um die Ausführung in den UI-Thread zu übertragen.

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

Das `whenComplete`-Callback wird ebenfalls in einem Hintergrundthread ausgeführt. Jede UI-Operation - Aktivierung der Schaltfläche, Ausblenden des Fortschrittsbalkens, Anzeigen von Toasts - muss in `Environment.runLater()` gewickelt werden. Ohne dieses Wrapping wirft webforJ Ausnahmen, da Hintergrundthreads nicht auf UI-Komponenten zugreifen können.

:::warning[Thread-Sicherheit]
Jede UI-Aktualisierung von einem Hintergrundthread muss in `Environment.runLater()` gewickelt sein. Diese Regel hat keine Ausnahmen. Der direkte Zugriff auf Komponenten aus `@Async`-Methoden schlägt immer fehl.
:::

:::tip[Erfahren Sie mehr über Thread-Sicherheit]
Für detaillierte Informationen über das Thread-Modell von webforJ, das Ausführungsverhalten und welche Operationen `Environment.runLater()` erfordern, siehe [Asynchrone Updates](../../advanced/asynchronous-updates).
:::

## Aufgabestornierung und Bereinigung {#task-cancellation-and-cleanup}

Eine ordnungsgemäße Lebenszyklusverwaltung verhindert Speicherlecks und unerwünschte UI-Aktualisierungen. Die Ansicht speichert die Referenz auf das `CompletableFuture`:

```java
private CompletableFuture<String> currentTask;
```

Wenn die Ansicht zerstört wird, storniert sie jede laufende Aufgabe:

```java
@Override
protected void onDestroy() {
    // Stornieren Sie die Aufgabe, wenn die Ansicht zerstört wird
    if (currentTask != null && !currentTask.isDone()) {
        currentTask.cancel(true);
    }
}
```

Der `cancel(true)`-Parameter ist entscheidend. Er unterbricht den Hintergrundthread, was dazu führt, dass blockierende Operationen wie `Thread.sleep()` eine `InterruptedException` auslösen. Dies ermöglicht die sofortige Beendigung der Aufgabe. Ohne das Interrupt-Flag (`cancel(false)`) würde die Aufgabe weiterlaufen, bis sie explizit auf eine Stornierung prüft.

Diese Bereinigung verhindert mehrere Probleme:
- Hintergrundthreads verbrauchen weiterhin Ressourcen, nachdem die Ansicht verschwunden ist
- UI-Aktualisierungen versuchen, zerstörte Komponenten zu ändern
- Speicherlecks durch Callbacks, die Referenzen auf UI-Komponenten halten
