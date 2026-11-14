---
sidebar_position: 39
title: Pushmeldingen
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: be5e6f5947bf38824dde0289d6415971
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Pushmeldingen kunnen gebruikers bereiken, zelfs wanneer een app niet is geopend. De browser abonneert zich één keer, de app slaat het abonnement op en de server gebruikt het om meldingen te versturen wanneer er een gebeurtenis plaatsvindt. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> beheert het abonneren en afmelden in de browser. Op de server verstuurt <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> een <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> naar een opgeslagen abonnement.

<!-- INTRO_END -->

<VideoPlayer video="push-notifications/push" width={1920} height={1072} title="Push notifications" />

## Installatie en vereisten {#setup-and-prerequisites}

Pushmeldingen worden geleverd via een aparte module. Voeg deze toe aan je app:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-push</artifactId>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-push'
}
```

</TabItem>
</Tabs>

Voor pushmeldingen is het volgende vereist:

- Een servletimplementatie, zoals Jetty, Spring Boot of een WAR-bestand.
- Een sleutelpaar dat hieronder wordt gegenereerd en dat de implementatie gebruikt om meldingen te ondertekenen.
- Een veilige oorsprong. Browsers weigeren abonnementen die via iets anders dan `https` worden aangeboden, behalve via `localhost` tijdens de ontwikkeling.

:::info Veilige oorsprongen
<!-- vale off -->
Zie de [MDN-documentatie over Secure Contexts](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts) voor meer informatie over beveiligde contexten en waarom deze belangrijk zijn.
<!-- vale on -->
:::

### De sleutels genereren {#generating-the-keys}

Pushservices accepteren alleen meldingen die zijn ondertekend door de implementatie waarop de browser zich heeft geabonneerd. Voer de [buildplug-in](/docs/configuration/build-plugin) één keer uit voor elke implementatie om het sleutelpaar te genereren:

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn webforj:push-keys
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew webforjPushKeys
```

</TabItem>
</Tabs>

De opdracht geeft drie configuratieregels weer. Plak ze zonder de aanhalingstekens in `application.properties`, of kopieer ze zoals weergegeven naar `webforj.conf`. Vervang het onderwerp door het contactadres van de implementatie. Dit moet een `mailto:`- of `https://`-adres zijn waarmee pushservices contact kunnen opnemen met de beheerder.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Eigenschap | Uitleg |
|----------|-------------|
| `webforj.push.public-key` | De openbare helft van het sleutelpaar dat de implementatie gebruikt om meldingen te ondertekenen |
| `webforj.push.private-key` | De privésleutel van het sleutelpaar. Houd deze, net als elk ander geheim, buiten versiebeheer |
| `webforj.push.subject` | Het contactadres van de implementatie. Dit moet een `mailto:`- of `https://`-adres zijn waarmee pushservices de beheerder kunnen bereiken |

De app leest deze eigenschappen bij het opstarten. Als de configuratie slechts een deel ervan bevat, mislukt het opstarten en wordt gemeld welke eigenschappen ontbreken.

:::warning Sleutels roteren
Elke browser abonneert zich op één sleutelpaar. Als de sleutels veranderen, weigert de pushservice bestaande abonnementen. De volgende aanroep van `subscribe()` in elke browser vervangt het abonnement.
:::

## Werking {#how-it-works}

Het proces bestaat uit drie stappen:

1. **Abonneren.** Roep vanuit een view `Push.getCurrent().subscribe()` aan. Dit vraagt de gebruiker om toestemming en retourneert een `PushSubscription` die het adres van de browser identificeert.
2. **Opslaan.** De app slaat het abonnement op bij de gegevens en koppelt het aan de betreffende gebruiker.
3. **Versturen.** Later geeft `PushSender.send(subscription, message)` vanuit een willekeurige thread het bericht door aan de pushservice van de browserleverancier. De service toont de melding, ongeacht of de app is geopend.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Order shipped").setUrl("/orders/42").build());
```

In de volgende gedeelten wordt uitgelegd wat de browser weergeeft en hoe fouten bij elke stap kunnen worden afgehandeld.

## Instantie {#instance}

Haal de pushinstantie voor de huidige omgeving op:

```java
import com.webforj.push.Push;

Push push = Push.getCurrent();

if (Push.isPresent()) {
  // ...
}

Push.ifPresent(p -> {
  // ...
});
```

## De browser abonneren {#subscribing-the-browser}

Roep `subscribe()` aan als reactie op een gebruikersactie, zoals het klikken op een knop ‘Meldingen inschakelen’. De geretourneerde <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt voltooid met de <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> van de browser. Als de browser zich niet kan abonneren, wordt de bewerking uitzonderlijk voltooid met een <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>.

```java
PendingResult<PushSubscription> request = Push.getCurrent().subscribe();
request.thenAccept(subscription -> {
  subscriptions.save(subscription);
});
request.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable.getCause();
  PushStatus status = error.getStatus();
  String message = error.getMessage();

  return null;
});
```

Als de browser al geabonneerd is, retourneert een nieuwe aanroep van `subscribe()` het bestaande abonnement. Je kunt de methode dus veilig bij elk bezoek aanroepen.

:::info Browsertoestemming
De eerste aanroep van `subscribe()` vraagt de gebruiker om toestemming. De browser toont deze prompt; deze maakt geen deel uit van de gebruikersinterface van de app. Omdat browsers de prompt alleen tonen als reactie op een gebruikersactie, roep je `subscribe()` aan vanuit een kliklistener en niet vanuit de constructor van de view.

Als de gebruiker de prompt blokkeert, kan de app voor die oorsprong niet opnieuw om toestemming vragen.
:::

### Abonnementen opslaan {#storing-subscriptions}

Een abonnement vertegenwoordigt het adres van één browser en hoort op de server thuis. Sla het op bij de gegevens van de app en gebruik het endpoint als sleutel. Voeg alle informatie toe die de app later nodig heeft om de juiste browsers te selecteren, zoals de bijbehorende gebruiker. Elk abonnement bevat drie tekstwaarden:

| Waarde | Betekenis |
|-------|---------|
| `getEndpoint()` | De bezorg-URL die door de pushservice van de browserleverancier is toegewezen |
| `getP256dh()` | De openbare sleutel van de browser |
| `getAuth()` | Het authenticatiegeheim van de browser |

Een gebruiker die zich vanuit twee browsers abonneert, heeft twee abonnementen. Verwijder een abonnement wanneer de browser zich afmeldt of wanneer een verzending meldt dat het is verlopen. Zie [Foutstatus](#failure-status).

### Een abonnement herstellen {#restoring-a-subscription}

`getSubscription()` retourneert het huidige abonnement van de browser, of een leeg resultaat als er geen abonnement is. Gebruik dit om de kopie op de server te synchroniseren, bijvoorbeeld nadat de opslag van de app is gereset:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Via <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink> geeft `getPermission()` aan of de gebruiker toestemming heeft gegeven, geweigerd of nog niet heeft gereageerd op de meldingsprompt. Gebruik dit resultaat om de knop ‘Meldingen inschakelen’ te verbergen wanneer klikken erop geen effect zou hebben.

### Afmelden {#unsubscribing}

`unsubscribe()` annuleert het abonnement van de browser. De bewerking wordt voltooid met het verwijderde abonnement, zodat de app de opgeslagen kopie kan verwijderen, of met een leeg resultaat als de browser geen abonnement had.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Meldingen versturen {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> verstuurt een <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> naar een opgeslagen abonnement. De sender ondertekent het bericht met de sleutels van de implementatie en geeft het door aan de pushservice van de browserleverancier. Die service maakt de browser actief en toont de melding. Omdat de bewerking de aanroepende thread nooit blokkeert, kun je deze aanroepen vanuit een kliklistener, geplande taak of request handler.

Nadat de eigenschappen zijn geconfigureerd, is de sender beschikbaar als bean die je kunt injecteren in views, services en geplande taken. Definieer je eigen `PushSender`-bean om deze te vervangen.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Zonder Spring leest `new PushSender()` de sleutels uit de configuratie van de app. Maak de sender aan op een app-thread, bijvoorbeeld in een view of in `App.run()`, en gebruik hem vervolgens vanuit elke thread. Alle senders delen één verbindingspool met de pushservices, dus het maakt niet uit waar je er een aanmaakt.

Gebruik voor meldingen die later moeten worden verstuurd of nadat de gebruiker is vertrokken een timer op de server, zoals Spring's `TaskScheduler`. Gebruik geen paginatimer zoals `Interval`, want die stopt wanneer het tabblad wordt gesloten.

### Een bericht samenstellen {#composing-a-message}

Maak een bericht aan met de titel en configureer vervolgens alle andere opties met de builder:

```java
PushMessage message = PushMessage.create("Order shipped")
    .setBody("Order #42 is on its way")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Track", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Sent"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` retourneert onmiddellijk. De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wordt voltooid wanneer de pushservice het bericht accepteert, of uitzonderlijk wanneer de service het niet accepteert. Als `send()` wordt aangeroepen op een app-thread, bijvoorbeeld vanuit een listener, worden de callbacks op die thread uitgevoerd en kunnen ze componenten bijwerken. Als de sessie die `send()` heeft aangeroepen eindigt voordat de reactie binnenkomt, worden de callbacks niet uitgevoerd, maar wordt de melding nog steeds bezorgd.

Een verzending wacht maximaal 30 seconden op de pushservice voordat deze mislukt met `UNREACHABLE`. Gebruik `setTimeout(Duration)` om de time-out voor elke sender te wijzigen.

| Optie | Effect |
|--------|--------|
| `setBody` | Stelt de tekst in die onder de titel wordt weergegeven |
| `setIcon` | Stelt de afbeelding in die bij de melding wordt weergegeven. Accepteert absolute URL's en de protocollen `icons://` en `ws://`. Zie [Assets](/docs/managing-resources/assets-protocols). Het protocol `context://` wordt niet geaccepteerd, omdat pushservices een bericht beperken tot 4 KB |
| `setUrl` | Stelt de pagina in die wordt geopend wanneer de gebruiker op de melding klikt. Relatieve URL's worden opgelost ten opzichte van de hoofdmap van de app. Als er geen URL is ingesteld, wordt de hoofdmap van de app geopend |
| `setActions` | Stelt de knoppen in die op de melding worden weergegeven, met een aparte URL voor elke knop. Zie [Browserondersteuning](#browser-support) |
| `setTag` | Stelt een identificerende tag in. Als een weergegeven melding dezelfde tag heeft, vervangt de nieuwe melding deze |
| `setSilent` | Toont de melding zonder geluid of trilling |
| `setTimeToLive` | Stelt in hoe lang de pushservice het bericht bewaart voor een offline apparaat, tot maximaal vier weken |
| `setUrgency` | Gebruikt <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> om het apparaat toe te staan berichten met lage urgentie uit te stellen en batterij te besparen |
| `setTopic` | Vervangt een bericht dat nog bij de pushservice in de wachtrij staat wanneer beide berichten hetzelfde onderwerp hebben. Onderwerpen mogen maximaal 32 tekens bevatten die veilig zijn in een URL |

Als een tabblad de pagina al weergeeft, brengt klikken op de melding de app naar de voorgrond. Anders wordt de pagina in een nieuw tabblad geopend. Klikken op een meldingsknop opent de bijbehorende URL op dezelfde manier.

:::info Eén melding per bericht
Elk bericht toont een melding. Omdat browsers een pagina niet activeren voor een bericht dat niets weergeeft, kun je push niet gebruiken voor stille gegevensupdates.
:::

## Foutstatus {#failure-status}

Wanneer `subscribe()` of `send()` mislukt, rapporteert de bijbehorende `PendingResult` een `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> geeft de reden aan:

| Status | Wanneer | Wat te doen |
|--------|------|------------|
| `PERMISSION_DENIED` | De gebruiker heeft meldingen voor de app geblokkeerd | Leg uit waar de gebruiker meldingen kan toestaan in de browserinstellingen |
| `UNSUPPORTED` | Push wordt niet ondersteund door de browser, de pagina bevindt zich niet in een beveiligde context of de app is niet als servlet geïmplementeerd | Verberg de functie |
| `NOT_CONFIGURED` | Minstens één `webforj.push.*`-eigenschap ontbreekt of is onvolledig | Genereer de sleutels en configureer alle drie de eigenschappen |
| `SUBSCRIPTION_EXPIRED` | De pushservice herkent het abonnement niet meer omdat de gebruiker zich heeft afgemeld of de browser opnieuw heeft geïnstalleerd | Verwijder het opgeslagen abonnement |
| `REJECTED` | De pushservice heeft het bericht geweigerd; `getStatusCode()` bevat de reactie | Controleer de sleutels en de berichtgrootte |
| `UNREACHABLE` | De pushservice heeft niet vóór de time-out gereageerd | Probeer het later opnieuw |
| `UNKNOWN` | Het opgeslagen endpoint is geen geldige URL, of het abonnement of bericht kon niet worden gecodeerd | Controleer het opgeslagen abonnement |

Verwijder verlopen abonnementen bij elke verzending:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Verloop wordt één bericht later gemeld
Pushservices melden abonnementen pas na verloop van tijd af. Ze accepteren het eerste bericht nadat een gebruiker zich heeft afgemeld nog steeds, maar het wordt nergens bezorgd. Het volgende bericht meldt `SUBSCRIPTION_EXPIRED`. Een geaccepteerde verzending betekent dat het bericht de pushservice heeft bereikt, niet dat de gebruiker het heeft gezien.
:::

## Browserondersteuning {#browser-support}

Alle belangrijke desktop- en mobiele browsers tonen pushmeldingen nadat de gebruiker zich heeft geabonneerd. Houd rekening met de volgende beperkingen:

- Op iPhone en iPad werkt push alleen voor webapps die vanaf iOS 16.4 of later aan het beginscherm zijn toegevoegd. In een Safari-tabblad meldt `subscribe()` `UNSUPPORTED`. Zie [Installeerbare apps](/docs/configuration/installable-apps) voor het vereiste app-manifest.
- Safari toont geen meldingsknoppen. Het toont berichten met acties zonder de knoppen, maar klikken op de melding opent nog steeds de URL van het bericht.
- Android- en iOS-WebViews tonen geen meldingen.

Zie voor de details per browser de MDN-[compatibiliteitstabel voor showNotification](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Compleet voorbeeld {#complete-example}

De volgende view abonneert en meldt de browser af, slaat abonnementen in het geheugen op en verstuurt een bericht naar elk opgeslagen abonnement. Het bericht kan direct worden verstuurd of na acht seconden met Spring's `TaskScheduler`, zodat het tabblad kan worden gesloten voordat de melding binnenkomt. De app-klasse gebruikt `@EnableScheduling` om de scheduler beschikbaar te maken.

```java title="PushSubscriptions.java"
package com.example;

import com.webforj.push.PushSubscription;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PushSubscriptions {

  private final Map<String, PushSubscription> byEndpoint = new ConcurrentHashMap<>();

  public void save(PushSubscription subscription) {
    byEndpoint.put(subscription.getEndpoint(), subscription);
  }

  public void delete(PushSubscription subscription) {
    byEndpoint.remove(subscription.getEndpoint());
  }

  public Collection<PushSubscription> findAll() {
    return byEndpoint.values();
  }
}
```

<!-- vale off -->

<ExpandableCode title="PushView.java" language="java" startLine={40} endLine={73}>

```java
package com.example;

import com.webforj.PendingResult;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.push.Push;
import com.webforj.push.PushAction;
import com.webforj.push.PushMessage;
import com.webforj.push.PushSender;
import com.webforj.push.PushStatus;
import com.webforj.push.PushSubscription;
import com.webforj.push.exception.WebforjPushException;
import com.webforj.router.annotation.Route;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.scheduling.TaskScheduler;

@Route("/push")
public class PushView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph status = new Paragraph("Checking subscription…");
  private final TextField message = new TextField("Message", "Order #42 is on its way");
  private final Button subscribe =
      new Button("Enable notifications", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Disable notifications");
  private final Button sendNow = new Button("Send now");
  private final Button sendLater = new Button("Send in 8 seconds");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Subscribed");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Notifications are blocked in this browser"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Unsubscribed" : "There was no subscription");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Sending in 8 seconds, close the tab now");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Subscribed" : "Not subscribed");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Sending to " + subscriptions.findAll().size() + " subscriptions");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Orders")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Open home", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Delivered"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("A subscription expired and was removed");
        } else {
          report.accept(error.getMessage());
        }

        return null;
      });
    }
  }
}
```

</ExpandableCode>

<!-- vale on -->
