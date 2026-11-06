---
title: Hintergrundaufgaben
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 2760d6db6a7b7045e7612ca359d04a28
---
Wenn Benutzer auf eine Schaltfläche klicken, um einen Bericht zu erstellen oder Daten zu verarbeiten, erwarten sie, dass die Benutzeroberfläche weiterhin reagiert. Fortschrittsbalken sollten animiert werden, Schaltflächen auf Hover reagieren und die App nicht einfrieren. Mit Spring lässt sich dies durch die Annotation `@Async` erreichen, die lang laufende Vorgänge in Hintergrundthreads verschiebt.

webforJ gewährleistet die Thread-Sicherheit von UI-Komponenten – alle Aktualisierungen müssen im UI-Thread stattfinden. Dadurch stellt sich die Frage: Wie können Hintergrundaufgaben Fortschrittsbalken aktualisieren oder Ergebnisse anzeigen? Die Antwort ist `Environment.runLater()`, womit sich UI-Aktualisierungen sicher von den Spring-Hintergrundthreads in den UI-Thread des Benutzers übertragen lassen, der die Aufgabe gestartet hat.

## Asynchrone Ausführung aktivieren {#enabling-asynchronous-execution}

Die asynchrone Methodenausführung in Spring muss ausdrücklich konfiguriert werden. Andernfalls werden mit `@Async` annotierte Methoden synchron ausgeführt, was ihren Zweck zunichtemacht.

Füge `@EnableAsync` zu deiner Spring-Boot-App-Klasse hinzu:

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

Die Annotation `@EnableAsync` aktiviert die Spring-Infrastruktur, die `@Async`-Methoden erkennt und in Hintergrundthreads ausführt.

:::tip[Spring-Leitfaden zu Async]
Eine kurze Einführung in Springs Annotation `@Async` und grundlegende Verwendungsmuster findest du unter [Asynchrone Methoden erstellen](https://spring.io/guides/gs/async-method).
:::

## Asynchrone Services erstellen {#creating-async-services}

Mit `@Service` annotierte Services können Methoden enthalten, die mit `@Async` markiert sind und in Hintergrundthreads ausgeführt werden. Diese Methoden geben üblicherweise ein `CompletableFuture` zurück, um die korrekte Behandlung des Abschlusses und den Abbruch zu ermöglichen:

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
        "Aufgabe erfolgreich im Hintergrundservice abgeschlossen!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Dieser Service akzeptiert einen Fortschritts-Callback (`Consumer<Integer>`), der vom Hintergrundthread aufgerufen wird. Durch dieses Callback-Muster kann der Service den Fortschritt melden, ohne UI-Komponenten kennen zu müssen.

Die Methode simuliert eine fünf Sekunden dauernde Aufgabe mit zehn Fortschrittsmeldungen. In der Praxis würden hier tatsächliche Arbeiten wie Datenbankabfragen oder die Verarbeitung von Dateien ausgeführt. Die Ausnahmebehandlung setzt den Unterbrechungsstatus zurück, damit der ordnungsgemäße Abbruch der Aufgabe unterstützt wird, wenn `cancel(true)` aufgerufen wird.

## Hintergrundaufgaben in Views verwenden {#using-background-tasks-in-views}

Die View erhält den Hintergrundservice über die Konstruktorinjektion:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Hintergrundaufgabe starten");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Der Service wird von Spring injiziert
    asyncBtn.addClickListener(e -> {
      // Der Klick-Handler wird im UI-Thread dieses Benutzers ausgeführt
      Environment env = Environment.getCurrent();

      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring injiziert `BackgroundService` wie jede andere Spring-Bean in den Konstruktor der View. Anschließend verwendet die View diesen Service, um Hintergrundaufgaben zu starten. Entscheidend ist: Callbacks des Service werden in Springs Threadpool ausgeführt, der von allen Benutzern der App gemeinsam genutzt wird. Ein Thread aus diesem Pool kann daher nicht wissen, welche Benutzeroberfläche aktualisiert werden soll. Der Klick-Handler liest mit `Environment.getCurrent()` die `Environment` des Benutzers aus, solange er sich noch im UI-Thread befindet. Jede UI-Aktualisierung innerhalb der Callbacks übergibt sie dann an `Environment.runLater()`.

Auch beim Behandeln des Abschlusses ist eine sorgfältige Verwaltung der Threads erforderlich:

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(env, () -> {
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

Der Callback `whenComplete` wird ebenfalls in einem Hintergrundthread ausgeführt. Jeder UI-Vorgang – die Schaltfläche aktivieren, den Fortschrittsbalken ausblenden oder Toasts anzeigen – muss mit derselben `env`-Instanz in `Environment.runLater()` eingeschlossen werden. Ohne diese Umhüllung löst webforJ Ausnahmen aus, da Hintergrundthreads nicht auf UI-Komponenten zugreifen können.

:::warning[Thread-Sicherheit]
Jede UI-Aktualisierung aus einem Hintergrundthread muss in `Environment.runLater()` eingeschlossen werden. Die `Environment` muss ausgelesen werden, während sich die Ausführung noch im UI-Thread befindet, also bevor die Aufgabe gestartet wird. Diese Regel kennt keine Ausnahmen. Der direkte Zugriff auf Komponenten aus `@Async`-Methoden schlägt immer fehl, und `Environment.getCurrent()` gibt in einem Poolthread `null` zurück.
:::

:::tip[Mehr über Thread-Sicherheit erfahren]
Ausführliche Informationen zum Threading-Modell von webforJ, zum Ausführungsverhalten und dazu, bei welchen Vorgängen `Environment.runLater()` erforderlich ist, findest du unter [Asynchrone Aktualisierungen](../../advanced/asynchronous-updates).
:::

## Aufgaben abbrechen und aufräumen {#task-cancellation-and-cleanup}

Eine ordnungsgemäße Verwaltung des Lebenszyklus verhindert Speicherlecks und unerwünschte UI-Aktualisierungen. Die View speichert die Referenz auf das `CompletableFuture`:

```java
private CompletableFuture<String> currentTask;
```

Wird die View zerstört, bricht sie alle noch laufenden Aufgaben ab:

```java
@Override
protected void onDestroy() {
  // Aufgabe abbrechen, wenn die View zerstört wird
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

Der Parameter `cancel(true)` ist entscheidend. Er unterbricht den Hintergrundthread, wodurch blockierende Vorgänge wie `Thread.sleep()` eine `InterruptedException` auslösen. So kann die Aufgabe sofort beendet werden. Ohne das Unterbrechungs-Flag (`cancel(false)`) würde die Aufgabe weiterlaufen, bis sie ausdrücklich auf einen Abbruch prüft.

Durch diese Bereinigung werden mehrere Probleme verhindert:
- Hintergrundthreads verbrauchen weiterhin Ressourcen, obwohl die View nicht mehr vorhanden ist
- UI-Aktualisierungen versuchen, zerstörte Komponenten zu ändern
- Speicherlecks, weil Callbacks Referenzen auf UI-Komponenten behalten
