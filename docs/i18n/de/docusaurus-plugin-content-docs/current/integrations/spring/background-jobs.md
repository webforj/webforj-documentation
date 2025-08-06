---
title: Background Jobs
sidebar_position: 25
_i18n_hash: 6770951556a0f793ce218daeb686b581
---
Wenn Benutzer auf eine Schaltfläche klicken, um einen Bericht zu erstellen oder Daten zu verarbeiten, erwarten sie, dass die Benutzeroberfläche reaktionsschnell bleibt. Fortschrittsbalken sollten animiert werden, Schaltflächen sollten auf Hover reagieren und die App darf nicht einfrieren. Springs `@Async`-Annotation macht dies möglich, indem lang laufende Vorgänge in Hintergrund-Threads verlagert werden.

webforJ gewährleistet die Thread-Sicherheit für UI-Komponenten - alle Updates müssen im UI-Thread erfolgen. Das stellt eine Herausforderung dar: Wie können Hintergrundaufgaben Fortschrittsbalken aktualisieren oder Ergebnisse anzeigen? Die Antwort lautet `Environment.runLater()`, das sicher UI-Updates von Springs Hintergrund-Threads in den UI-Thread von webforJ überträgt.

## Aktivieren der asynchronen Ausführung {#enabling-asynchronous-execution}

Die asynchrone Methoden-Ausführung von Spring erfordert eine explizite Konfiguration. Ohne diese werden mit `@Async` annotierte Methoden synchron ausgeführt, was ihren Zweck untergräbt.

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

Die `@EnableAsync`-Annotation aktiviert die Infrastruktur von Spring zur Erkennung von `@Async`-Methoden und deren Ausführung in Hintergrund-Threads.

:::tip[Spring-Async-Guide]
Für eine schnelle Einführung in Springs `@Async`-Annotation und grundlegende Nutzungsmuster siehe [Erstellen asynchroner Methoden](https://spring.io/guides/gs/async-method).
:::

## Erstellen von asynchronen Diensten {#creating-async-services}

Dienste, die mit `@Service` annotiert sind, können Methoden haben, die mit `@Async` markiert sind, um in Hintergrund-Threads ausgeführt zu werden. Diese Methoden geben in der Regel `CompletableFuture` zurück, um eine ordnungsgemäße Abschlussbehandlung und Abbruch zu ermöglichen:

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

Dieser Dienst akzeptiert einen Fortschritts-Callback (`Consumer<Integer>`), der aus dem Hintergrund-Thread aufgerufen wird. Das Callback-Muster ermöglicht es dem Dienst, Fortschritte zu melden, ohne etwas über UI-Komponenten zu wissen.

Die Methode simuliert eine 5-Sekunden-Aufgabe mit 10 Fortschrittsaktualisierungen. In der Produktion wäre dies tatsächliche Arbeit wie Datenbankabfragen oder die Verarbeitung von Dateien. Die Ausnahmebehandlung stellt den Interrupt-Status wieder her, um eine ordnungsgemäße Abbruchunterstützung zu ermöglichen, wenn `cancel(true)` aufgerufen wird.

## Verwendung von Hintergrundaufgaben in Ansichten {#using-background-tasks-in-views}

Die Ansicht erhält den Hintergrunddienst über Konstruktorinjektion:

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

Spring injiziert den `BackgroundService` in den Konstruktor der Ansicht, genau wie jede andere Spring-Bean. Die Ansicht verwendet dann diesen Dienst, um Hintergrundaufgaben zu starten. Das Schlüsselkonzept ist: Callbacks vom Dienst werden in Hintergrund-Threads ausgeführt, sodass alle UI-Aktualisierungen innerhalb dieser Callbacks `Environment.runLater()` verwenden müssen, um die Ausführung in den UI-Thread zu übertragen.

Die Abschlussbehandlung erfordert das gleiche sorgfältige Thread-Management:

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

Der `whenComplete`-Callback wird ebenfalls in einem Hintergrund-Thread ausgeführt. Jede UI-Operation - die Schaltfläche aktivieren, den Fortschrittsbalken ausblenden, Toasts anzeigen - muss in `Environment.runLater()` eingeschlossen werden. Ohne diese Einfassung wirft webforJ Ausnahmen, da Hintergrund-Threads keinen Zugriff auf UI-Komponenten haben.

:::warning[Thread-Sicherheit]
Jede UI-Aktualisierung von einem Hintergrund-Thread muss in `Environment.runLater()` eingeschlossen werden. Diese Regel hat keine Ausnahmen. Der direkte Zugriff auf Komponenten aus `@Async`-Methoden schlägt immer fehl.
:::

:::tip[Erfahren Sie mehr über Thread-Sicherheit]
Für detaillierte Informationen über das Thread-Modell von webforJ, das Ausführungsverhalten und welche Operationen `Environment.runLater()` erfordern, siehe [Asynchrone Updates](../../advanced/asynchronous-updates).
:::

## Aufgabenabbruch und Bereinigung {#task-cancellation-and-cleanup}

Eine ordnungsgemäße Lebenszyklusverwaltung verhindert Speicherlecks und unerwünschte UI-Aktualisierungen. Die Ansicht speichert die Referenz auf das `CompletableFuture`:

```java
private CompletableFuture<String> currentTask;
```

Wenn die Ansicht zerstört wird, bricht sie alle laufenden Aufgaben ab:

```java
@Override
protected void onDestroy() {
    // Abbrechen der Aufgabe, wenn die Ansicht zerstört wird
    if (currentTask != null && !currentTask.isDone()) {
        currentTask.cancel(true);
    }
}
```

Der Parameter `cancel(true)` ist entscheidend. Er unterbricht den Hintergrund-Thread, wodurch blockierende Operationen wie `Thread.sleep()` eine `InterruptedException` auslösen. Dies ermöglicht eine sofortige Aufgabentrennung. Ohne das Interrupt-Flag (`cancel(false)`) würde die Aufgabe weiterhin laufen, bis sie explizit auf Abbruch überprüft.

Diese Bereinigung verhindert mehrere Probleme:
- Hintergrund-Threads verbrauchen weiterhin Ressourcen, nachdem die Ansicht verschwunden ist
- UI-Aktualisierungen versuchen, zerstörte Komponenten zu ändern
- Speicherlecks durch Callbacks, die Verweise auf UI-Komponenten halten
