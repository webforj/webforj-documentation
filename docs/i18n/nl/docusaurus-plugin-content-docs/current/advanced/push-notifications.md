---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 47adf06762f8af67111f20937368723c
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Pushmeldingen kunnen gebruikers bereiken, zelfs als een app niet open is. De browser abonneert zich één keer, de app slaat de abonnement op, en de server gebruikt het om meldingen te verzenden wanneer er een gebeurtenis plaatsvindt. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> beheert het abonneren en afmelden in de browser. Op de server stuurt <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> een <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> naar een opgeslagen abonnement.

<!-- INTRO_END -->

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/push-notifications/push.mp4" type="video/mp4"/>
  </video>
</div>

## Setup en vereisten {#setup-and-prerequisites}

Pushmeldingen worden geleverd door een aparte module. Voeg deze toe aan je app:

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

Pushmeldingen vereisen:

- Een servlet-implementatie, zoals Jetty, Spring Boot, of een WAR-bestand.
- Een sleutelpair, hieronder gegenereerd, dat de implementatie gebruikt om meldingen te ondertekenen.
- Een veilige oorsprong. Browsers weigeren abonnementen die op iets anders dan `https` worden aangeboden, behalve van `localhost` tijdens ontwikkeling.

:::info Veilige oorsprongen
<!-- vale off -->
Voor meer informatie over veilige contexten en waarom ze belangrijk zijn, zie de [Secure Contexts MDN documentatie](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Sleutels genereren {#generating-the-keys}

Pushdiensten accepteren alleen meldingen die zijn ondertekend door de implementatie waarvoor de browser zich heeft geabonneerd. Voer de [build plugin](/docs/configuration/build-plugin) één keer uit voor elke implementatie om zijn sleutelpair te genereren:

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

De opdracht geeft drie configuratielijnen weer. Plak ze in `application.properties` zonder de aanhalingstekens, of kopieer ze zoals afgedrukt in `webforj.conf`. Vervang het onderwerp door het contactadres van de implementatie. Dit moet een `mailto:` of `https://` adres zijn dat pushdiensten kunnen gebruiken om contact op te nemen met de operator.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Eigenschap | Uitleg |
|------------|--------|
| `webforj.push.public-key` | De publieke helft van de sleutelpair die door de implementatie wordt gebruikt om meldingen te ondertekenen |
| `webforj.push.private-key` | De private helft van de sleutelpair. Net als elke andere geheim, houd deze uit de broncontrole |
| `webforj.push.subject` | Het contactadres van de implementatie. Dit moet een `mailto:` of `https://` adres zijn waarmee pushdiensten contact kunnen opnemen met de operator |

De app leest deze eigenschappen bij opstarten. Als de configuratie slechts enkele van deze bevat, mislukt de opstart en wordt gerapporteerd welke eigenschappen ontbreken.

:::warning Sleutels roteren
Elke browser abonneert zich op één sleutelpair. Als de sleutels veranderen, wijst de pushdienst bestaande abonnementen af. De volgende `subscribe()` aanroep in elke browser vervangt zijn abonnement.
:::

## Hoe het werkt {#how-it-works}

Het proces heeft drie stappen:

1. **Abonneren.** Vanuit een weergave vraagt `Push.getCurrent().subscribe()` toestemming van de gebruiker en retourneert een `PushSubscription` die het adres van de browser identificeert.
2. **Opslaan.** De app slaat het abonnement met zijn gegevens op en associeert het met de bijbehorende gebruiker.
3. **Verzenden.** Later, vanuit een willekeurige thread, geeft `PushSender.send(subscription, message)` het bericht door aan de pushdienst van de browserleverancier. De dienst toont de melding, of de app nu open is of niet.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Bestelling verzonden").setUrl("/orders/42").build());
```

De volgende secties leggen uit wat de browser weergeeft en hoe om te gaan met fouten in elke stap.

## Instanties {#instance}

Haal de push-instantie voor de huidige omgeving op:

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

Roep `subscribe()` aan als reactie op een gebruikersactie, zoals het klikken op een knop "Meldingen inschakelen". De geretourneerde <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> voltooit met de <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> van de browser. Als de browser zich niet kan abonneren, voltooit hij uitzonderlijk met een <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>.

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

Als de browser al is geabonneerd, retourneert een tweede aanroep van `subscribe()` het bestaande abonnement. Je kunt het daarom veilig bij elk bezoek aanroepen.

:::info Browser toestemming
De eerste aanroep van `subscribe()` vraagt de gebruiker om toestemming. De browser toont deze prompt, deze maakt geen deel uit van de app UI. Omdat browsers de prompt alleen tonen als reactie op een gebruikersactie, roep `subscribe()` aan vanuit een klikluisteraar in plaats van de weergaveconstructor.

Als de gebruiker de prompt blokkeert, kan de app niet opnieuw vragen om die oorsprong.
:::

### Abonnementen opslaan {#storing-subscriptions}

Een abonnement vertegenwoordigt het adres van één browser en behoort op de server. Sla het op met de gegevens van de app, met zijn eindpunt als de sleutel. Inclusief alle informatie die de app nodig heeft om later de juiste browsers te selecteren, zoals de bijbehorende gebruiker. Elk abonnement bevat drie tekstwaarden:

| Waarde | Betekenis |
|--------|-----------|
| `getEndpoint()` | De leverings-URL die door de pushdienst van de browserleverancier is toegewezen |
| `getP256dh()` | De publieke sleutel van de browser |
| `getAuth()` | Het authenticatiegeheim van de browser |

Een gebruiker die zich vanuit twee browsers abonneert, heeft twee abonnementen. Verwijder een abonnement wanneer de browser zich afmeldt of wanneer een verzending meldt dat het is verlopen. Zie [Foutstatus](#failure-status).

### Een abonnement herstellen {#restoring-a-subscription}

`getSubscription()` retourneert het huidige abonnement van de browser, of een leeg resultaat als er geen bestaat. Gebruik het om een kopie van de server te synchroniseren, bijvoorbeeld nadat de opslag van de app is gereset:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Via <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink> rapporteert `getPermission()` of de gebruiker toestemming heeft verleend, geweigerd of nog niet heeft geantwoord op de melding prompt. Gebruik dit resultaat om de knop "Meldingen inschakelen" te verbergen wanneer klikken daarop geen effect zou hebben.

### Afmelden {#unsubscribing}

`unsubscribe()` annuleert het abonnement van de browser. Het voltooit met het verwijderde abonnement, zodat de app zijn opgeslagen kopie kan verwijderen, of met een leeg resultaat als de browser geen abonnement had.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Meldingen verzenden {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> verzendt een <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> naar een opgeslagen abonnement. Het ondertekent het bericht met de sleutels van de implementatie en geeft het door aan de pushdienst van de browserleverancier. Deze dienst wekt de browser en toont de melding. Omdat de operatie de aanroepende thread nooit blokkeert, kun je deze aanroepen vanuit een klikluisteraar, geplande taak of aanvraaghandler.

Nadat de eigenschappen zijn geconfigureerd, is de verzender beschikbaar als een bean die je kunt injecteren in weergaven, services en geplande taken. Om deze te vervangen, definieer je je eigen `PushSender` bean.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Zonder Spring leest `new PushSender()` de sleutels uit de configuratie van de app. Maak de sender op een app-thread, hetzij in een weergave of in `App.run()`, en gebruik deze vervolgens vanuit elke thread. Alle zenders delen één verbindingspool met de pushdiensten, dus er zijn geen kosten aan het creëren ervan waar nodig.

Voor meldingen die later of nadat de gebruiker vertrekt, moeten worden verzonden, gebruik een timer op de server zoals Spring's `TaskScheduler`. Gebruik geen paginatimer zoals `Interval`, omdat deze stopt wanneer het tabblad wordt gesloten.

### Een bericht samenstellen {#composing-a-message}

Maak een bericht met de titel, configureer vervolgens elke andere optie op de bouwer:

```java
PushMessage message = PushMessage.create("Bestelling verzonden")
    .setBody("Bestelling #42 is onderweg")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Volgen", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Verzonden"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` retourneert onmiddellijk. De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> voltooit wanneer de pushdienst het bericht accepteert, of voltooit uitzonderlijk als de dienst het niet accepteert. Als `send()` wordt aangeroepen op een app-thread, zoals vanuit een luisteraar, worden zijn callbacks op die thread uitgevoerd en kunnen componenten bijwerken. Als de sessie die `send()` oproept eindigt voordat de reactie arriveert, worden de callbacks niet uitgevoerd, maar de melding wordt nog steeds afgeleverd.

Een zending wacht maximaal 30 seconden op de pushdienst voordat deze faalt met `UNREACHABLE`. Gebruik `setTimeout(Duration)` om de time-out voor elke zender te wijzigen.

| Optie | Effect |
|-------|--------|
| `setBody` | Stelt de tekst in die onder de titel wordt weergegeven |
| `setIcon` | Stelt de afbeelding in die met de melding wordt weergegeven. Het accepteert absolute URL's en de protocollen `icons://` en `ws://`. Zie [Assets](/docs/managing-resources/assets-protocols). Het accepteert de `context://` protocol niet omdat pushdiensten een bericht beperken tot 4 KB |
| `setUrl` | Stelt de pagina in die opent wanneer de gebruiker op de melding klikt. Relatieve URL's worden tegen de hoofdmap van de app opgelost. Als er geen URL is ingesteld, opent de hoofdmap van de app |
| `setActions` | Stelt de knoppen in die op de melding worden weergegeven, met een aparte URL voor elke knop. Zie [Browserondersteuning](#browser-support) |
| `setTag` | Stelt een identificerende tag in. Als een weergegeven melding dezelfde tag heeft, vervangt de nieuwe melding deze |
| `setSilent` | Toont de melding zonder geluid of trillingen |
| `setTimeToLive` | Stelt in hoe lang de pushdienst het bericht voor een offline apparaat behoudt, tot vier weken |
| `setUrgency` | Gebruikt <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> om het apparaat in staat te stellen meldingen van lage urgentie uit te stellen en batterij te besparen |
| `setTopic` | Vervangt een bericht dat nog wacht bij de pushdienst wanneer beide berichten hetzelfde onderwerp hebben. Onderwerpen kunnen maximaal 32 karakters bevatten die veilig zijn in een URL |

Wanneer een tabblad de pagina al weergeeft, focust het klikken op de melding de app. Anders opent de pagina in een nieuw tabblad. Klikken op een meldingsknop opent de URL op dezelfde manier.

:::info Eén melding per bericht
Elk bericht toont een melding. Omdat browsers een pagina niet wakker maken voor een bericht dat niets toont, kan push niet worden gebruikt voor stille gegevensupdates.
:::

## Foutstatus {#failure-status}

Wanneer `subscribe()` of `send()` faalt, rapporteert zijn `PendingResult` een `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> identificeert de reden:

| Status | Wanneer | Wat te doen |
|--------|---------|-------------|
| `PERMISSION_DENIED` | De gebruiker heeft meldingen voor de app geblokkeerd | Leg uit waar de gebruiker meldingen kan toestaan in de browserinstellingen |
| `UNSUPPORTED` | Push is niet ondersteund door de browser, de pagina bevindt zich niet in een veilige context, of de app is niet gedeployed als servlet | Verberg de functie |
| `NOT_CONFIGURED` | Ten minste één `webforj.push.*` eigenschap ontbreekt of is onvolledig | Genereer de sleutels en configureer alle drie de eigenschappen |
| `SUBSCRIPTION_EXPIRED` | De pushdienst herkent het abonnement niet meer omdat de gebruiker zich heeft afgemeld of de browser opnieuw heeft geïnstalleerd | Verwijder het opgeslagen abonnement |
| `REJECTED` | De pushdienst heeft het bericht afgewezen; `getStatusCode()` bevat de reactie | Verifieer de sleutels en de berichtgrootte |
| `UNREACHABLE` | De pushdienst reageerde niet voor de time-out | Probeer het later opnieuw |
| `UNKNOWN` | Het opgeslagen eindpunt is geen geldige URL, of het abonnement of bericht kon niet worden gecodeerd | Verifieer het opgeslagen abonnement |

Verwijder verlopen abonnementen tijdens elke verzending:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Verloop komt één bericht te laat aan
Pushdiensten deregistreren abonnementen lui. Ze accepteren nog steeds het eerste bericht nadat een gebruiker zich heeft afgemeld, maar het gaat nergens heen. Het volgende bericht meldt `SUBSCRIPTION_EXPIRED`. Een geaccepteerde verzending betekent dat het bericht de pushdienst heeft bereikt, niet dat de gebruiker het heeft gezien.
:::

## Browserondersteuning {#browser-support}

Alle belangrijke desktop- en mobiele browsers tonen pushmeldingen na abonnement. Houd deze beperkingen in gedachten:

- Op iPhone en iPad werkt push alleen voor webapps die zijn toegevoegd aan het startscherm op iOS 16.4 of later. In een Safari-tabblad meldt `subscribe()` `UNSUPPORTED`. Zie [Installable Apps](/docs/configuration/installable-apps) voor het vereiste app-manifest.
- Safari toont geen meldingsknoppen. Het toont berichten met acties zonder hun knoppen, maar klikken op de melding opent nog steeds de URL van het bericht.
- Android- en iOS-WebViews tonen geen meldingen.

Voor de details per browser, zie de MDN [showNotification compatibiliteitstabel](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Volledig voorbeeld {#complete-example}

De volgende weergave abonneert en meldt de browser af, slaat abonnementen in het geheugen op en verzendt een bericht naar elk opgeslagen abonnement. Het kan onmiddellijk verzenden of acht seconden wachten door gebruik te maken van Spring's `TaskScheduler`, zodat het tabblad kan sluiten voordat de melding arriveert. De app-klasse gebruikt `@EnableScheduling` om de planner beschikbaar te maken.

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
  private final Paragraph status = new Paragraph("Abonnement controleren…");
  private final TextField message = new TextField("Bericht", "Bestelling #42 is onderweg");
  private final Button subscribe =
      new Button("Meldingen inschakelen", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Meldingen uitschakelen");
  private final Button sendNow = new Button("Verzenden nu");
  private final Button sendLater = new Button("Verzenden in 8 seconden");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Geabonneerd");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Meldingen zijn geblokkeerd in deze browser"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Afgemeld" : "Er was geen abonnement");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Verzenden in 8 seconden, sluit nu het tabblad");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Geabonneerd" : "Niet geabonneerd");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Verzenden naar " + subscriptions.findAll().size() + " abonnementen");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Bestellingen")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Open home", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Afgeleverd"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("Een abonnement is verlopen en verwijderd");
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
