---
title: Background Jobs
sidebar_position: 25
_i18n_hash: d419b53d933af4ef48890b8be2eab4dc
---
Wanneer gebruikers op een knop klikken om een rapport te genereren of gegevens te verwerken, verwachten ze dat de interface responsief blijft. Voortgangsbalken moeten animaties hebben, knoppen moeten reageren op hover, en de app mag niet bevriezen. Spring's `@Async` annotatie maakt dit mogelijk door langdurige processen naar achtergrondthreads te verplaatsen.

webforJ handhaaft threadveiligheid voor UI-componenten - alle updates moeten plaatsvinden op de UI-thread. Dit creëert een uitdaging: hoe kunnen achtergrondtaken voortgangsbalken bijwerken of resultaten weergeven? Het antwoord is `Environment.runLater()`, dat UI-updates veilig overbrengt van Spring's achtergrondthreads naar webforJ's UI-thread.

## Het inschakelen van asynchrone uitvoering {#enabling-asynchronous-execution}

De asynchrone methodenuitvoering van Spring vereist expliciete configuratie. Zonder deze configuratie worden methoden die zijn geannoteerd met `@Async` synchronisch uitgevoerd, wat hun doel tenietdoet.

Voeg `@EnableAsync` toe aan je Spring Boot-appklasse:

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

De `@EnableAsync` annotatie activeert de infrastructuur van Spring voor het detecteren van `@Async` methoden en het uitvoeren ervan op achtergrondthreads.

:::tip[Spring async guide]
Voor een snelle introductie tot Spring's `@Async` annotatie en basisgebruikspatronen, zie [Creating Asynchronous Methods](https://spring.io/guides/gs/async-method).
:::

## Het creëren van async services {#creating-async-services}

Services die zijn geannoteerd met `@Service` kunnen methoden hebben die zijn gemarkeerd met `@Async` om op achtergrondthreads te draaien. Deze methoden retourneren meestal `CompletableFuture` om correcte voltooiingsafhandeling en annulering mogelijk te maken:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Meld voortgang
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

Deze service accepteert een voortgangcallback (`Consumer<Integer>`) die wordt aangeroepen vanuit de achtergrondthread. Het callbackpatroon stelt de service in staat om voortgang te rapporteren zonder kennis te hebben van UI-componenten. 

De methode simuleert een taak van 5 seconden met 10 voortgangsupdates. In productie zou dit werkelijke taken zijn zoals databasequery's of bestandsverwerking. De exceptionafhandeling herstelt de interruptstatus om correcte taakannulering te ondersteunen wanneer `cancel(true)` wordt aangeroepen.

## Het gebruik van achtergrondtaken in weergaven {#using-background-tasks-in-views}

De weergave ontvangt de achtergrondservice via constructor-injectie:

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

Spring injecteert de `BackgroundService` in de constructor van de weergave, net als elke andere Spring-bean. De weergave gebruikt vervolgens deze service om achtergrondtaken te starten. Het belangrijkste concept: callbacks van de service worden uitgevoerd op achtergrondthreads, zodat alle UI-updates in die callbacks `Environment.runLater()` moeten gebruiken om de uitvoering naar de UI-thread over te brengen.

Voltooiingsafhandeling vereist hetzelfde zorgvuldige threadbeheer:

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

De `whenComplete` callback wordt ook uitgevoerd op een achtergrondthread. Elke UI-operatie - het inschakelen van de knop, het verbergen van de voortgangsbalk, het tonen van toasts - moet worden ingepakt in `Environment.runLater()`. Zonder deze inpakking gooit webforJ uitzonderingen omdat achtergrondthreads geen toegang hebben tot UI-componenten.

:::warning[Threadveiligheid]
Elke UI-update vanuit een achtergrondthread moet worden ingepakt in `Environment.runLater()`. Deze regel heeft geen uitzonderingen. Directe componenttoegang vanuit `@Async` methoden faalt altijd.
:::

:::tip[Leer meer over threadveiligheid]
Voor gedetailleerde informatie over het threadingmodel van webforJ, uitvoeringsgedrag en welke operaties `Environment.runLater()` vereisen, zie [Asynchronous Updates](../../advanced/asynchronous-updates).
:::

## Taakannulering en opruiming {#task-cancellation-and-cleanup}

Juist levenscyclusbeheer voorkomt geheugenlekken en ongewenste UI-updates. De weergave slaat de referentie naar `CompletableFuture` op:

```java
private CompletableFuture<String> currentTask;
```

Wanneer de weergave wordt vernietigd, annuleert deze elke actieve taak:

```java
@Override
protected void onDestroy() {
    // Annuleer de taak als de weergave wordt vernietigd
    if (currentTask != null && !currentTask.isDone()) {
        currentTask.cancel(true);
    }
}
```

De parameter `cancel(true)` is cruciaal. Het onderbreekt de achtergrondthread, waardoor blokkeringen zoals `Thread.sleep()` `InterruptedException` gooien. Dit maakt onmiddellijke taakbeëindiging mogelijk. Zonder de onderbrekingsvlag (`cancel(false)`) zou de taak doorgaan tot deze expliciet controleert op annulering.

Deze opruiming voorkomt verschillende problemen:
- Achtergrondthreads blijven middelen verbruiken nadat de weergave is verdwenen
- UI-updates proberen vernietigde componenten te wijzigen
- Geheugenlekken door callbacks die verwijzingen naar UI-componenten vasthouden
