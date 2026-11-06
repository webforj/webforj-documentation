---
title: Achtergrondtaken
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 2760d6db6a7b7045e7612ca359d04a28
---
Wanneer gebruikers op een knop klikken om een rapport te genereren of gegevens te verwerken, verwachten ze dat de interface responsief blijft. Voortgangsbalken moeten animeren, knoppen moeten reageren op hoveren en de app mag niet vastlopen. Met Spring's annotatie `@Async` kun je dit mogelijk maken door langlopende bewerkingen naar achtergrondthreads te verplaatsen.

webforJ waarborgt de threadveiligheid van UI-componenten: alle updates moeten op de UI-thread plaatsvinden. Dat levert een uitdaging op: hoe kunnen achtergrondtaken voortgangsbalken bijwerken of resultaten weergeven? Het antwoord is `Environment.runLater()`, waarmee UI-updates veilig worden overgebracht van Spring-achtergrondthreads naar de UI-thread van de gebruiker die de taak heeft gestart.

## Asynchrone uitvoering inschakelen {#enabling-asynchronous-execution}

Voor de uitvoering van asynchrone methoden in Spring is expliciete configuratie nodig. Zonder die configuratie worden methoden met de annotatie `@Async` synchroon uitgevoerd, waardoor ze hun doel voorbijschieten.

Voeg `@EnableAsync` toe aan de Spring Boot-appklasse:

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

De annotatie `@EnableAsync` activeert Spring's infrastructuur voor het detecteren van methoden met `@Async` en het uitvoeren ervan op achtergrondthreads.

:::tip[Spring-handleiding voor async]
Bekijk [Asynchrone methoden maken](https://spring.io/guides/gs/async-method) voor een korte introductie tot Spring's annotatie `@Async` en basisgebruikspatronen.
:::

## Asynchrone services maken {#creating-async-services}

Services met de annotatie `@Service` kunnen methoden bevatten die met `@Async` zijn gemarkeerd, zodat ze op achtergrondthreads worden uitgevoerd. Deze methoden retourneren doorgaans `CompletableFuture`, zodat de voltooiing correct kan worden afgehandeld en taken kunnen worden geannuleerd:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Voortgang rapporteren
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Werk simuleren
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Taak is succesvol voltooid vanuit de achtergrondservice!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Deze service accepteert een voortgangscallback (`Consumer<Integer>`) die vanuit de achtergrondthread wordt aangeroepen. Dankzij dit callbackpatroon kan de service de voortgang rapporteren zonder iets over UI-componenten te hoeven weten.

De methode simuleert een taak van 5 seconden met 10 voortgangsupdates. In een productieomgeving zou dit echt werk zijn, zoals databasequery's of bestandsverwerking. De uitzonderingsafhandeling herstelt de interruptstatus, zodat taken correct kunnen worden geannuleerd wanneer `cancel(true)` wordt aangeroepen.

## Achtergrondtaken gebruiken in weergaven {#using-background-tasks-in-views}

De weergave ontvangt de achtergrondservice via constructorinjectie:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Achtergrondtaak starten");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // De service wordt door Spring geïnjecteerd
    asyncBtn.addClickListener(e -> {
      // De click-handler wordt uitgevoerd op de UI-thread van deze gebruiker
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

Spring injecteert `BackgroundService` in de constructor van de weergave, net als elke andere Spring-bean. De weergave gebruikt deze service vervolgens om achtergrondtaken te starten. Het belangrijkste om te begrijpen: callbacks vanuit de service worden uitgevoerd in de threadpool van Spring, die door alle gebruikers van de app wordt gedeeld. Een thread uit de pool kan dus niet weten welke gebruikersinterface moet worden bijgewerkt. De click-handler leest de `Environment` van de gebruiker met `Environment.getCurrent()` terwijl die nog op de UI-thread draait. Elke UI-update in de callbacks geeft deze vervolgens door aan `Environment.runLater()`.

Ook bij het afhandelen van de voltooiing moet de thread zorgvuldig worden beheerd:

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(env, () -> {
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

De callback `whenComplete` wordt ook op een achtergrondthread uitgevoerd. Elke UI-bewerking — de knop inschakelen, de voortgangsbalk verbergen en toasts weergeven — moet worden ingepakt in `Environment.runLater()` met dezelfde `env`. Zonder deze omhulling gooit webforJ uitzonderingen, omdat achtergrondthreads geen toegang hebben tot UI-componenten.

:::warning[Threadveiligheid]
Elke UI-update vanuit een achtergrondthread moet worden ingepakt in `Environment.runLater()`, met de `Environment` die op de UI-thread is uitgelezen voordat de taak werd gestart. Hierop zijn geen uitzonderingen. Rechtstreekse toegang tot componenten vanuit methoden met `@Async` mislukt altijd, en `Environment.getCurrent()` retourneert `null` op een thread uit de pool.
:::

:::tip[Meer informatie over threadveiligheid]
Zie [Asynchrone updates](../../advanced/asynchronous-updates) voor gedetailleerde informatie over het threadingmodel van webforJ, het uitvoeringsgedrag en de bewerkingen waarvoor `Environment.runLater()` nodig is.
:::

## Taak annuleren en opruimen {#task-cancellation-and-cleanup}

Goed lifecyclebeheer voorkomt geheugenlekken en ongewenste UI-updates. De weergave slaat een verwijzing naar de `CompletableFuture` op:

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

De parameter `cancel(true)` is cruciaal. Deze onderbreekt de achtergrondthread, waardoor blokkerende bewerkingen zoals `Thread.sleep()` een `InterruptedException` veroorzaken. Zo kan de taak direct worden beëindigd. Zonder de interruptvlag (`cancel(false)`) blijft de taak actief totdat deze expliciet op annulering controleert.

Deze opruiming voorkomt verschillende problemen:
- Achtergrondthreads blijven resources gebruiken nadat de weergave is verdwenen
- UI-updates proberen vernietigde componenten te wijzigen
- Geheugenlekken doordat callbacks verwijzingen naar UI-componenten vasthouden
