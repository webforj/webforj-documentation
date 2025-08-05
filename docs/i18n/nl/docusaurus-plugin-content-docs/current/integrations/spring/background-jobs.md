---
title: Background Jobs
sidebar_position: 25
_i18n_hash: 6770951556a0f793ce218daeb686b581
---
Wanneer gebruikers op een knop klikken om een rapport te genereren of gegevens te verwerken, verwachten ze dat de interface responsief blijft. Voortgangsbalken moeten animeren, knoppen moeten reageren op hover, en de app mag niet vastlopen. Spring's `@Async` annotatie maakt dit mogelijk door langdurige bewerkingen naar achtergrondthreads te verplaatsen.

webforJ waarborgt threadveiligheid voor UI-componenten - alle updates moeten plaatsvinden op de UI-thread. Dit creëert een uitdaging: hoe kunnen achtergrondtaken voortgangsbalken bijwerken of resultaten weergeven? Het antwoord is `Environment.runLater()`, dat veilig UI-updates van Spring's achtergrondthreads naar webforJ's UI-thread overbrengt.

## Asynchrone uitvoering inschakelen {#enabling-asynchronous-execution}

Spring's asynchrone methoden uitvoering vereist expliciete configuratie. Zonder deze configuratie worden methoden met de annotatie `@Async` synchronisch uitgevoerd, wat hun doel tenietdoet.

Voeg `@EnableAsync` toe aan je Spring Boot-app-klasse:

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

De annotatie `@EnableAsync` activeert de infrastructuur van Spring voor het detecteren van `@Async` methoden en het uitvoeren ervan op achtergrondthreads.

:::tip[Spring async gids]
Voor een snelle introductie in Spring's `@Async` annotatie en basis gebruikspatronen, zie [Asynchrone Methoden Creëren](https://spring.io/guides/gs/async-method).
:::

## Async-services creëren {#creating-async-services}

Diensten gemarkeerd met `@Service` kunnen methoden hebben die gemarkeerd zijn met `@Async` om op achtergrondthreads te draaien. Deze methoden geven doorgaans `CompletableFuture` terug om een goede verwerking en annulering mogelijk te maken:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Rapport progressie
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simuleer werk
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Taak succesvol voltooid vanuit de achtergrondservice!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Deze service accepteert een voortgangs callback (`Consumer<Integer>`) die vanuit de achtergrondthread wordt aangeroepen. Het callback patroon stelt de service in staat om voortgang te rapporteren zonder kennis van UI-componenten.

De methode simuleert een taak van 5 seconden met 10 voortgangsupdates. In productie zou dit werkelijke taken zijn zoals databasequery's of bestandverwerking. De uitzondering behandeling herstelt de interruptstatus om een goede taakannulering te ondersteunen wanneer `cancel(true)` wordt aangeroepen.

## Achtergrondtaken gebruiken in weergaven {#using-background-tasks-in-views}

De weergave ontvangt de achtergrondservice via constructorinjectie:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Start Achtergrond Taak");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Service wordt geïnjecteerd door Spring
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

Spring injecteert de `BackgroundService` in de constructor van de weergave, net als elke andere Spring bean. De weergave gebruikt deze service vervolgens om achtergrondtaken te starten. Het sleutelconcept: callbacks van de service worden op achtergrondthreads uitgevoerd, zodat alle UI-updates binnen die callbacks `Environment.runLater()` moeten gebruiken om de uitvoering naar de UI-thread te verplaatsen.

Voltooiingsverwerking vereist hetzelfde zorgvuldige threadbeheer:

```java
currentTask.whenComplete((result, error) -> {
    Environment.runLater(() -> {
        asyncBtn.setEnabled(true);
        progressBar.setVisible(false);
        if (error != null) {
            Toast.show("Taak mislukt: " + error.getMessage(), Theme.DANGER);
        } else {
            Toast.show(result, Theme.SUCCESS);
        }
    });
});
```

De `whenComplete` callback wordt ook op een achtergrondthread uitgevoerd. Elke UI-operatie - het inschakelen van de knop, het verbergen van de voortgangsbalk, het tonen van toastberichten - moet worden ingepakt in `Environment.runLater()`. Zonder deze verpakking gooit webforJ uitzonderingen omdat achtergrondthreads geen toegang hebben tot UI-componenten.

:::warning[Threadveiligheid]
Elke UI-update vanuit een achtergrondthread moet worden ingepakt in `Environment.runLater()`. Deze regel kent geen uitzonderingen. Directe toegang tot componenten vanuit `@Async` methoden faalt altijd.
:::

:::tip[Leer meer over threadveiligheid]
Voor gedetailleerde informatie over het threadingmodel van webforJ, uitvoeringsgedrag en welke bewerkingen `Environment.runLater()` vereisen, zie [Asynchrone Updates](../../advanced/asynchronous-updates).
:::

## Taakannulering en opruiming {#task-cancellation-and-cleanup}

Correcte levenscyclusbeheer voorkomt geheugenlekken en ongewenste UI-updates. De weergave slaat de referentie naar de `CompletableFuture` op:

```java
private CompletableFuture<String> currentTask;
```

Wanneer de weergave wordt vernietigd, annuleert deze eventuele lopende taken:

```java
@Override
protected void onDestroy() {
    // Annuleer de taak als de weergave wordt vernietigd
    if (currentTask != null && !currentTask.isDone()) {
        currentTask.cancel(true);
    }
}
```

De parameter `cancel(true)` is cruciaal. Het onderbreekt de achtergrondthread, waardoor blokkering operaties zoals `Thread.sleep()` een `InterruptedException` gooien. Dit maakt onmiddellijke taakterminatie mogelijk. Zonder de interrupt-vlag (`cancel(false)`) zou de taak doorgaan totdat deze expliciet controleert op annulering.

Deze opruiming voorkomt verschillende problemen:
- Achtergrondthreads blijven middelen verbruiken nadat de weergave is verdwenen
- UI-updates proberen vernietigde componenten te wijzigen
- Geheugenlekken van callbacks die referenties naar UI-componenten vasthouden
