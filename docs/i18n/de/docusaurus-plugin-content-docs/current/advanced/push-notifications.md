---
sidebar_position: 39
title: Push-B Benachrichtigungen
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: be5e6f5947bf38824dde0289d6415971
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Push-Benachrichtigungen erreichen Nutzer auch dann, wenn eine App nicht geöffnet ist. Der Browser abonniert den Dienst einmal, die App speichert das Abonnement und der Server verwendet es, um Benachrichtigungen zu senden, sobald ein Ereignis eintritt. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> verwaltet das Abonnieren und Abbestellen im Browser. Auf dem Server sendet <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> eine <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> an ein gespeichertes Abonnement.

<!-- INTRO_END -->

<VideoPlayer video="push-notifications/push" width={1920} height={1072} title="Push notifications" />

## Einrichtung und Voraussetzungen {#setup-and-prerequisites}

Push-Benachrichtigungen werden von einem separaten Modul bereitgestellt. Fügen Sie es Ihrer App hinzu:

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

Für Push-Benachrichtigungen benötigen Sie:

- Ein Servlet-Deployment, zum Beispiel mit Jetty, Spring Boot oder als WAR-Datei.
- Ein unten generiertes Schlüsselpaar, mit dem das Deployment Benachrichtigungen signiert.
- Einen sicheren Ursprung. Browser lehnen Abonnements ab, die über etwas anderes als `https` bereitgestellt werden. Eine Ausnahme ist `localhost` während der Entwicklung.

:::info Sichere Ursprünge
<!-- vale off -->
Weitere Informationen zu sicheren Kontexten und ihrer Bedeutung finden Sie in der [MDN-Dokumentation zu sicheren Kontexten](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Schlüssel generieren {#generating-the-keys}

Push-Dienste akzeptieren nur Benachrichtigungen, die von dem Deployment signiert wurden, bei dem der Browser das Abonnement abgeschlossen hat. Führen Sie das [Build-Plugin](/docs/configuration/build-plugin) einmal pro Deployment aus, um das zugehörige Schlüsselpaar zu generieren:

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

Der Befehl gibt drei Konfigurationszeilen aus. Fügen Sie sie ohne Anführungszeichen in `application.properties` ein oder übernehmen Sie sie unverändert in `webforj.conf`. Ersetzen Sie den Betreff durch die Kontaktadresse des Deployments. Es muss eine `mailto:`- oder `https://`-Adresse sein, über die Push-Dienste den Betreiber kontaktieren können.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Eigenschaft | Erklärung |
|----------|-------------|
| `webforj.push.public-key` | Die öffentliche Hälfte des Schlüsselpaars, mit dem das Deployment Benachrichtigungen signiert |
| `webforj.push.private-key` | Die private Hälfte des Schlüsselpaars. Bewahren Sie sie wie jedes andere Geheimnis außerhalb der Versionsverwaltung auf |
| `webforj.push.subject` | Die Kontaktadresse des Deployments. Es muss eine `mailto:`- oder `https://`-Adresse sein, über die Push-Dienste den Betreiber erreichen können |

Die App liest diese Eigenschaften beim Start ein. Wenn die Konfiguration nur einige davon enthält, schlägt der Start fehl und es wird angegeben, welche Eigenschaften fehlen.

:::warning Schlüssel rotieren
Jeder Browser abonniert ein einzelnes Schlüsselpaar. Wenn sich die Schlüssel ändern, weist der Push-Dienst bestehende Abonnements zurück. Beim nächsten Aufruf von `subscribe()` ersetzt jeder Browser sein Abonnement.
:::

## Funktionsweise {#how-it-works}

Der Ablauf besteht aus drei Schritten:

1. **Abonnieren.** Eine Ansicht ruft `Push.getCurrent().subscribe()` auf, um die Zustimmung des Nutzers anzufordern. Der Aufruf gibt ein `PushSubscription` zurück, das die Adresse des Browsers enthält.
2. **Speichern.** Die App speichert das Abonnement zusammen mit seinen Daten und ordnet es dem entsprechenden Nutzer zu.
3. **Senden.** Später übergibt `PushSender.send(subscription, message)` die Nachricht von einem beliebigen Thread an den Push-Dienst des Browseranbieters. Der Dienst zeigt die Benachrichtigung an, unabhängig davon, ob die App geöffnet ist.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Order shipped").setUrl("/orders/42").build());
```

In den folgenden Abschnitten wird erläutert, was der Browser anzeigt und wie Fehler in den einzelnen Schritten behandelt werden.

## Instanz {#instance}

Rufen Sie die Push-Instanz für die aktuelle Umgebung ab:

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

## Browser abonnieren {#subscribing-the-browser}

Rufen Sie `subscribe()` als Reaktion auf eine Nutzeraktion auf, etwa beim Klicken auf eine Schaltfläche „Benachrichtigungen aktivieren“. Das zurückgegebene <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird mit dem <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> des Browsers abgeschlossen. Kann der Browser kein Abonnement erstellen, wird der Aufruf mit einer <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink> außergewöhnlich abgeschlossen.

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

Ist der Browser bereits abonniert, gibt ein erneuter Aufruf von `subscribe()` das vorhandene Abonnement zurück. Sie können den Aufruf daher bei jedem Besuch bedenkenlos ausführen.

:::info Browserberechtigung
Beim ersten Aufruf von `subscribe()` wird der Nutzer um seine Zustimmung gebeten. Diese Aufforderung wird vom Browser angezeigt und gehört nicht zur Benutzeroberfläche der App. Da Browser die Aufforderung nur als Reaktion auf eine Nutzeraktion anzeigen, sollten Sie `subscribe()` in einem Click-Listener und nicht im Konstruktor der Ansicht aufrufen.

Wenn der Nutzer die Aufforderung blockiert, kann die App für diesen Ursprung nicht erneut um Zustimmung bitten.
:::

### Abonnements speichern {#storing-subscriptions}

Ein Abonnement repräsentiert die Adresse eines Browsers und gehört auf den Server. Speichern Sie es zusammen mit den App-Daten und verwenden Sie dabei seinen Endpunkt als Schlüssel. Hinterlegen Sie alle Informationen, die die App später benötigt, um die passenden Browser auszuwählen, zum Beispiel den zugehörigen Nutzer. Jedes Abonnement enthält drei Textwerte:

| Wert | Bedeutung |
|-------|---------|
| `getEndpoint()` | Die vom Push-Dienst des Browseranbieters zugewiesene Zustell-URL |
| `getP256dh()` | Der öffentliche Schlüssel des Browsers |
| `getAuth()` | Das Authentifizierungsgeheimnis des Browsers |

Ein Nutzer, der sich über zwei Browser anmeldet, hat zwei Abonnements. Löschen Sie ein Abonnement, wenn der Browser es abbestellt oder ein Sendevorgang meldet, dass es abgelaufen ist. Siehe [Fehlerstatus](#failure-status).

### Abonnement wiederherstellen {#restoring-a-subscription}

`getSubscription()` gibt das aktuelle Abonnement des Browsers oder ein leeres Ergebnis zurück, wenn keines vorhanden ist. Verwenden Sie den Aufruf, um die Serverkopie zu synchronisieren, beispielsweise nachdem der App-Speicher zurückgesetzt wurde:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Über <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink> meldet `getPermission()`, ob der Nutzer der Benachrichtigungsaufforderung zugestimmt, sie abgelehnt oder noch nicht beantwortet hat. Verwenden Sie dieses Ergebnis, um die Schaltfläche „Benachrichtigungen aktivieren“ auszublenden, wenn ein Klick darauf keine Wirkung hätte.

### Abbestellen {#unsubscribing}

`unsubscribe()` kündigt das Browserabonnement. Der Aufruf wird mit dem entfernten Abonnement abgeschlossen, damit die App ihre gespeicherte Kopie löschen kann. Hatte der Browser kein Abonnement, wird ein leeres Ergebnis zurückgegeben.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Benachrichtigungen senden {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> sendet eine <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> an ein gespeichertes Abonnement. Er signiert die Nachricht mit den Schlüsseln des Deployments und übergibt sie an den Push-Dienst des Browseranbieters. Dieser Dienst weckt den Browser und zeigt die Benachrichtigung an. Da der Vorgang den aufrufenden Thread niemals blockiert, können Sie ihn in einem Click-Listener, einem geplanten Job oder einem Request-Handler aufrufen.

Sobald die Eigenschaften konfiguriert sind, steht der Sender als Bean zur Verfügung, die Sie in Ansichten, Services und geplante Jobs injizieren können. Um ihn zu ersetzen, definieren Sie eine eigene `PushSender`-Bean.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Ohne Spring liest `new PushSender()` die Schlüssel aus der App-Konfiguration. Erstellen Sie den Sender in einem App-Thread, entweder in einer Ansicht oder in `App.run()`, und verwenden Sie ihn anschließend in einem beliebigen Thread. Alle Sender nutzen denselben Verbindungspool zu den Push-Diensten. Daher entstehen keine zusätzlichen Kosten, wenn Sie bei Bedarf einen Sender erstellen.

Verwenden Sie für Benachrichtigungen, die später oder nach dem Verlassen des Nutzers gesendet werden müssen, einen Timer auf dem Server, etwa Spring's `TaskScheduler`. Verwenden Sie keinen Seitentimer wie `Interval`, da dieser beim Schließen des Tabs angehalten wird.

### Nachricht erstellen {#composing-a-message}

Erstellen Sie eine Nachricht mit ihrem Titel und konfigurieren Sie anschließend alle weiteren Optionen über den Builder:

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

`send()` gibt sofort zurück. Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird abgeschlossen, sobald der Push-Dienst die Nachricht akzeptiert, oder außergewöhnlich abgeschlossen, wenn der Dienst sie nicht akzeptiert. Wird `send()` in einem App-Thread aufgerufen, zum Beispiel in einem Listener, werden die Callbacks in diesem Thread ausgeführt und können Komponenten aktualisieren. Wird die Sitzung, die `send()` aufgerufen hat, beendet, bevor die Antwort eintrifft, werden die Callbacks nicht ausgeführt, die Benachrichtigung wird jedoch trotzdem zugestellt.

Ein Sendevorgang wartet bis zu 30 Sekunden auf den Push-Dienst und schlägt anschließend mit `UNREACHABLE` fehl. Verwenden Sie `setTimeout(Duration)`, um das Timeout für jeden Sender anzupassen.

| Option | Wirkung |
|--------|--------|
| `setBody` | Legt den Text fest, der unter dem Titel angezeigt wird |
| `setIcon` | Legt das Bild fest, das zusammen mit der Benachrichtigung angezeigt wird. Akzeptiert absolute URLs sowie die Protokolle `icons://` und `ws://`. Siehe [Ressourcen](/docs/managing-resources/assets-protocols). Das Protokoll `context://` wird nicht akzeptiert, da Push-Dienste die Nachricht auf 4 KB begrenzen |
| `setUrl` | Legt die Seite fest, die beim Klicken auf die Benachrichtigung geöffnet wird. Relative URLs werden relativ zum Stammverzeichnis der App aufgelöst. Ist keine URL festgelegt, wird das Stammverzeichnis der App geöffnet |
| `setActions` | Legt die Schaltflächen der Benachrichtigung fest, jeweils mit einer eigenen URL. Siehe [Browserunterstützung](#browser-support) |
| `setTag` | Legt ein Kennzeichnungstag fest. Hat eine angezeigte Benachrichtigung dasselbe Tag, wird sie durch die neue Benachrichtigung ersetzt |
| `setSilent` | Zeigt die Benachrichtigung ohne Ton oder Vibration an |
| `setTimeToLive` | Legt fest, wie lange der Push-Dienst die Nachricht für ein offline befindliches Gerät speichert (bis zu vier Wochen) |
| `setUrgency` | Verwendet <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink>, damit das Gerät Nachrichten mit niedriger Dringlichkeit verzögern und Akku sparen kann |
| `setTopic` | Ersetzt eine Nachricht, die beim Push-Dienst noch wartet, wenn beide Nachrichten dasselbe Thema haben. Themen dürfen höchstens 32 URL-sichere Zeichen enthalten |

Wird die Seite bereits in einem Tab angezeigt, wechselt ein Klick auf die Benachrichtigung zur App. Andernfalls wird die Seite in einem neuen Tab geöffnet. Ein Klick auf eine Schaltfläche der Benachrichtigung öffnet deren URL auf dieselbe Weise.

:::info Eine Benachrichtigung pro Nachricht
Jede Nachricht zeigt eine Benachrichtigung an. Da Browser eine Seite nicht für Nachrichten aktivieren, die nichts anzeigen, können Push-Benachrichtigungen nicht für stille Datenaktualisierungen verwendet werden.
:::

## Fehlerstatus {#failure-status}

Schlägt `subscribe()` oder `send()` fehl, meldet das zugehörige `PendingResult` eine `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> gibt den Grund an:

| Status | Wann | Was zu tun ist |
|--------|------|------------|
| `PERMISSION_DENIED` | Der Nutzer hat Benachrichtigungen für die App blockiert | Erklären Sie, wo der Nutzer Benachrichtigungen in den Browsereinstellungen zulassen kann |
| `UNSUPPORTED` | Push-Benachrichtigungen werden vom Browser nicht unterstützt, die Seite befindet sich nicht in einem sicheren Kontext oder die App ist nicht als Servlet deployed | Blenden Sie die Funktion aus |
| `NOT_CONFIGURED` | Mindestens eine `webforj.push.*`-Eigenschaft fehlt oder ist unvollständig | Generieren Sie die Schlüssel und konfigurieren Sie alle drei Eigenschaften |
| `SUBSCRIPTION_EXPIRED` | Der Push-Dienst erkennt das Abonnement nicht mehr, weil der Nutzer es gekündigt oder den Browser neu installiert hat | Entfernen Sie das gespeicherte Abonnement |
| `REJECTED` | Der Push-Dienst hat die Nachricht abgelehnt; `getStatusCode()` enthält seine Antwort | Überprüfen Sie die Schlüssel und die Nachrichtengröße |
| `UNREACHABLE` | Der Push-Dienst hat vor Ablauf des Timeouts nicht geantwortet | Versuchen Sie es später erneut |
| `UNKNOWN` | Der gespeicherte Endpunkt ist keine gültige URL oder das Abonnement bzw. die Nachricht konnte nicht codiert werden | Überprüfen Sie das gespeicherte Abonnement |

Entfernen Sie abgelaufene Abonnements bei jedem Sendevorgang:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Ablauf wird eine Nachricht verspätet gemeldet
Push-Dienste löschen Abonnements verzögert. Die erste Nachricht nach der Kündigung durch einen Nutzer wird noch angenommen, aber nicht zugestellt. Erst die nächste Nachricht meldet `SUBSCRIPTION_EXPIRED`. Ein erfolgreich angenommener Sendevorgang bedeutet, dass die Nachricht den Push-Dienst erreicht hat, nicht, dass der Nutzer sie gesehen hat.
:::

## Browserunterstützung {#browser-support}

Alle gängigen Desktop- und Mobilbrowser zeigen nach dem Abonnieren Push-Benachrichtigungen an. Beachten Sie die folgenden Einschränkungen:

- Auf iPhone und iPad funktioniert Push nur für Web-Apps, die unter iOS 16.4 oder neuer zum Home-Bildschirm hinzugefügt wurden. In einem Safari-Tab meldet `subscribe()` `UNSUPPORTED`. Informationen zum erforderlichen App-Manifest finden Sie unter [Installierbare Apps](/docs/configuration/installable-apps).
- Safari zeigt keine Schaltflächen für Benachrichtigungen an. Nachrichten mit Aktionen werden ohne Schaltflächen angezeigt, aber durch Klicken auf die Benachrichtigung wird weiterhin die Nachrichten-URL geöffnet.
- Android- und iOS-WebViews zeigen keine Benachrichtigungen an.

Details zu den einzelnen Browsern finden Sie in der MDN-[Kompatibilitätstabelle für showNotification](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Vollständiges Beispiel {#complete-example}

Die folgende Ansicht ermöglicht das Abonnieren und Abbestellen des Browsers, speichert Abonnements im Arbeitsspeicher und sendet eine Nachricht an jedes gespeicherte Abonnement. Die Nachricht kann sofort oder mithilfe von Spring's `TaskScheduler` mit einer Verzögerung von acht Sekunden gesendet werden. So kann der Tab geschlossen werden, bevor die Benachrichtigung eintrifft. Die App-Klasse verwendet `@EnableScheduling`, um den Scheduler verfügbar zu machen.

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
