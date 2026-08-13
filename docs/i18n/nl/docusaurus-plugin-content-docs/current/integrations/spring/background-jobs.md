---
title: Background Jobs
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 1b265d2e723c0f58c97fd2c4375f15a1
---
Wanneer gebruikers op een knop klikken om een rapport te genereren of gegevens te verwerken, verwachten ze dat de interface responsief blijft. Voortgangsbalken moeten animeren, knoppen moeten reageren op hover, en de app mag niet vastlopen. Spring's `@Async` annotatie maakt dit mogelijk door langdurige operaties naar achtergrondthreads te verplaatsen.

webforJ handhaaft threadveiligheid voor UI-componenten - alle updates moeten plaatsvinden op de UI-thread. Dit creëert een uitdaging: hoe kunnen achtergrondtaken voortgangsbalken bijwerken of resultaten tonen? Het antwoord is `Environment.runLater()`, dat UI-updates veilig van Spring's achtergrondthreads naar webforJ's UI-thread overdraagt.

## Asynchrone uitvoering inschakelen {#enabling-asynchronous-execution}

Spring's asynchrone methode-uitvoering vereist expliciete configuratie. Zonder deze configuratie worden methoden gemarkeerd met `@Async` synchrone uitgevoerd, waarmee hun doelstelling teniet wordt gedaan.

Voeg `@EnableAsync` toe aan uw Spring Boot-appklasse:

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

:::tip[Spring async gids]
Voor een snelle inleiding tot Spring's `@Async` annotatie en basisgebruikspatronen, zie [Asynchrone Methoden Aanmaken](https://spring.io/guides/gs/async-method).
:::

## Async-services aanmaken {#creating-async-services}

Services gemarkeerd met `@Service` kunnen methoden hebben die gemarkeerd zijn met `@Async` om op achtergrondthreads te draaien. Deze methoden retourneren typisch `CompletableFuture` om correcte voltooiing en annulering te ondersteunen:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Rapporteren van voortgang
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

Deze service accepteert een voortgangcallback (`Consumer<Integer>`) die vanuit de achtergrondthread wordt aangeroepen. Het callback-patroon stelt de service in staat om voortgang te rapporteren zonder kennis van UI-componenten.

De methode simuleert een taak van 5 seconden met 10 voortgangsupdates. In productie zou dit feitelijk werk zijn zoals databasequery's of bestandsverwerking. De uitzonderingafhandeling herstelt de onderbrekingsstatus om correcte annulering van de taak te ondersteunen wanneer `cancel(true)` wordt aangeroepen.

## Gebruik van achtergrondtaken in views {#using-background-tasks-in-views}

De view ontvangt de achtergrondservice via constructorinjectie:

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

Spring injecteert de `BackgroundService` in de constructor van de view, net als elke andere Spring-bean. De view gebruikt deze service vervolgens om achtergrondtaken te starten. Het sleutelconcept: callbacks van de service worden uitgevoerd op achtergrondthreads, dus elke UI-update binnen die callbacks moet `Environment.runLater()` gebruiken om de uitvoering naar de UI-thread over te brengen.

Voltooiingsafhandeling vereist dezelfde zorgvuldige threadbeheer:

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

De `whenComplete` callback wordt ook op een achtergrondthread uitgevoerd. Elke UI-operatie - het inschakelen van de knop, het verbergen van de voortgangsbalk, het tonen van toasts - moet worden verpakt in `Environment.runLater()`. Zonder deze verpakking gooit webforJ uitzonderingen omdat achtergrondthreads geen toegang hebben tot UI-componenten.

:::warning[Threadveiligheid]
Elke UI-update vanuit een achtergrondthread moet worden verpakt in `Environment.runLater()`. Deze regel heeft geen uitzonderingen. Directe toegang tot componenten vanuit `@Async` methoden faalt altijd.
:::

:::tip[Leer meer over threadveiligheid]
Voor gedetailleerde informatie over webforJ's threadingmodel, uitvoeringsgedrag en welke operaties `Environment.runLater()` vereisen, zie [Asynchrone Updates](../../advanced/asynchronous-updates).
:::

## Taakannulering en opruiming {#task-cancellation-and-cleanup}

Juiste levenscyclusbeheer voorkomt geheugenlekken en ongewenste UI-updates. De view slaat de referentie naar `CompletableFuture` op:

```java
private CompletableFuture<String> currentTask;
```

Wanneer de view wordt vernietigd, annuleert deze eventuele actieve taken:

```java
@Override
protected void onDestroy() {
  // Annuleer de taak als de view wordt vernietigd
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

De `cancel(true)` parameter is cruciaal. Het onderbreekt de achtergrondthread, waardoor blokkeringoperaties zoals `Thread.sleep()` een `InterruptedException` gooien. Dit stelt onmiddellijke beëindiging van de taak in staat. Zonder de onderbrekingsvlag (`cancel(false)`) zou de taak doorgaan met uitvoeren totdat deze expliciet op annulering controleert.

Deze opruiming voorkomt verschillende problemen:
- Achtergrondthreads blijven middelen verbruiken nadat de view weg is
- UI-updates proberen vernietigde componenten te wijzigen
- Geheugenlekken van callbacks die verwijzingen naar UI-componenten vasthouden
