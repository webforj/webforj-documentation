---
title: Background Jobs
sidebar_position: 25
_i18n_hash: 4f924436d02caee3bb07967d7055b0bc
---
Wanneer gebruikers op een knop klikken om een rapport te genereren of gegevens te verwerken, verwachten ze dat de interface responsief blijft. Voortgangsbalken zouden moeten animeren, knoppen moeten reageren op hover, en de app mag niet vastlopen. Spring's `@Async` annotatie maakt dit mogelijk door langdurige operaties naar achtergrondthreads te verplaatsen.

webforJ handhaaft threadveiligheid voor UI-componenten - alle updates moeten plaatsvinden op de UI-thread. Dit creëert een uitdaging: hoe kunnen achtergrondtaken voortgangsbalken bijwerken of resultaten weergeven? Het antwoord is `Environment.runLater()`, dat UI-updates veilig overbrengt van Spring's achtergrondthreads naar webforJ's UI-thread.

## Asynchrone uitvoering inschakelen {#enabling-asynchronous-execution}

Spring's asynchrone methode-uitvoering vereist expliciete configuratie. Zonder deze configuratie voeren methoden die zijn geannoteerd met `@Async` synchronisch uit, wat hun doel tenietdoet.

Voeg `@EnableAsync` toe aan de klasse van uw Spring Boot-app:

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

De `@EnableAsync` annotatie activeert Spring's infrastructuur voor het detecteren van `@Async` methoden en het uitvoeren ervan op achtergrondthreads.

:::tip[Spring async guide]
Voor een snelle introductie tot Spring's `@Async` annotatie en basisgebruikspatronen, zie [Creating Asynchronous Methods](https://spring.io/guides/gs/async-method).
:::

## Async-diensten maken {#creating-async-services}

Diensten die zijn geannoteerd met `@Service` kunnen methoden hebben die zijn gemarkeerd met `@Async` om op achtergrondthreads te draaien. Deze methoden geven doorgaans `CompletableFuture` terug om correcte afhandeling van voltooiing en annulering mogelijk te maken:

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

Deze service accepteert een voortgangs callback (`Consumer<Integer>`) die wordt aangeroepen vanuit de achtergrondthread. Het callback-patroon stelt de service in staat om voortgang te rapporteren zonder kennis te hebben van UI-componenten.

De methode simuleert een taak van 5 seconden met 10 voortgangsupdates. In productie zou dit daadwerkelijke werk kunnen zijn zoals databasequery's of bestandsverwerking. De foutafhandeling herstelt de onderbrekingsstatus om correcte taakannulering te ondersteunen wanneer `cancel(true)` wordt aangeroepen.

## Achtergrondtaken gebruiken in views {#using-background-tasks-in-views}

De view ontvangt de achtergrondservice via constructor-injectie:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Start achtergrondtaak");
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

Spring injecteert de `BackgroundService` in de constructor van de view, net als elke andere Spring-bean. De view gebruikt deze service vervolgens om achtergrondtaken te starten. Het belangrijkste concept: callbacks van de service worden uitgevoerd op achtergrondthreads, dus alle UI-updates binnen die callbacks moeten `Environment.runLater()` gebruiken om de uitvoering naar de UI-thread over te brengen.

Afhandeling van voltooiing vereist hetzelfde zorgvuldige threadbeheer:

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

De `whenComplete` callback wordt ook op een achtergrondthread uitgevoerd. Elke UI-operatie - het inschakelen van de knop, het verbergen van de voortgangsbalk, het tonen van toastmeldingen - moet worden omhulled met `Environment.runLater()`. Zonder deze omhulling werpt webforJ uitzonderingen omdat achtergrondthreads geen toegang hebben tot UI-componenten.

:::warning[Threadveiligheid]
Elke UI-update vanuit een achtergrondthread moet worden omhulled met `Environment.runLater()`. Deze regel kent geen uitzonderingen. Directe toegang tot componenten vanuit `@Async` methoden mislukt altijd.
:::

:::tip[Leer meer over threadveiligheid]
Voor gedetailleerde informatie over het threadmodel van webforJ, uitvoeringsgedrag en welke bewerkingen `Environment.runLater()` vereisen, zie [Asynchronous Updates](../../advanced/asynchronous-updates).
:::

## Taakannulering en opruiming {#task-cancellation-and-cleanup}

Juiste lifecyclebeheer voorkomt geheugenlekken en ongewenste UI-updates. De view slaat de referentie naar `CompletableFuture` op:

```java
private CompletableFuture<String> currentTask;
```

Wanneer de view wordt vernietigd, annuleert deze elke lopende taak:

```java
@Override
protected void onDestroy() {
  // Annuleer de taak als de view wordt vernietigd
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

De `cancel(true)` parameter is cruciaal. Het onderbreekt de achtergrondthread, waardoor blokkering operaties zoals `Thread.sleep()` een `InterruptedException` gooien. Dit maakt onmiddellijke beëindiging van de taak mogelijk. Zonder de onderbrekingsvlag (`cancel(false)`) zou de taak blijven draaien totdat deze expliciet op annulering controleert.

Deze opruiming voorkomt verschillende problemen:
- Achtergrondthreads blijven middelen verbruiken nadat de view is verdwenen
- UI-updates proberen vernietigde componenten te modificeren
- Geheugenlekken door callbacks die referenties naar UI-componenten vasthouden
