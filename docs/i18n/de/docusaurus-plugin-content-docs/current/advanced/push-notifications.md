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

Push-Benachrichtigungen können Benutzer erreichen, auch wenn eine App nicht geöffnet ist. Der Browser abonniert einmal, die App speichert das Abonnement und der Server verwendet es, um Benachrichtigungen zu senden, wenn ein Ereignis eintritt. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> verwaltet das Abonnieren und Abbestellen im Browser. Auf dem Server sendet <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> eine <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> an ein gespeichertes Abonnement.

<!-- INTRO_END -->

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/push-notifications/push.mp4" type="video/mp4"/>
  </video>
</div>

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

Push-Benachrichtigungen erfordern:

- Eine Servlet-Bereitstellung, wie Jetty, Spring Boot oder eine WAR-Datei.
- Ein Schlüsselpaar, das im Folgenden generiert wird, das die Bereitstellung verwendet, um Benachrichtigungen zu signieren.
- Eine sichere Herkunft. Browser lehnen Abonnements ab, die über etwas anderes als `https` bereitgestellt werden, außer von `localhost` während der Entwicklung.

:::info Sichere Herkunft
<!-- vale off -->
Für weitere Informationen zu sicheren Kontexten und warum sie wichtig sind, siehe die [MDN-Dokumentation zu sicheren Kontexten](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Generieren der Schlüssel {#generating-the-keys}

Push-Dienste akzeptieren nur Benachrichtigungen, die von der Bereitstellung signiert sind, bei der der Browser abonniert hat. Führen Sie das [Build-Plugin](/docs/configuration/build-plugin) einmal für jede Bereitstellung aus, um das Schlüsselpaar zu generieren:

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

Der Befehl gibt drei Konfigurationszeilen aus. Fügen Sie sie in `application.properties` ohne Anführungszeichen ein oder kopieren Sie sie wie gedruckt in `webforj.conf`. Ersetzen Sie das Subjekt durch die Kontaktadresse der Bereitstellung. Es muss eine `mailto:` oder `https://` Adresse sein, die Push-Dienste verwenden können, um den Betreiber zu kontaktieren.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Eigenschaft | Erklärung |
|-------------|-----------|
| `webforj.push.public-key` | Die öffentliche Hälfte des Schlüsselpaares, das von der Bereitstellung verwendet wird, um Benachrichtigungen zu signieren |
| `webforj.push.private-key` | Die private Hälfte des Schlüsselpaares. Wie bei jedem anderen Geheimnis, halten Sie es aus der Quellkontrolle |
| `webforj.push.subject` | Die Kontaktadresse der Bereitstellung. Es muss eine `mailto:` oder `https://` Adresse sein, über die Push-Dienste den Betreiber erreichen können |

Die App liest diese Eigenschaften beim Start. Wenn die Konfiguration nur einige von ihnen enthält, schlägt der Start fehl und meldet, welche Eigenschaften fehlen.

:::warning Schlüsselrotation
Jeder Browser abonniert ein Schlüsselpaar. Wenn sich die Schlüssel ändern, lehnt der Push-Dienst bestehende Abonnements ab. Der nächste `subscribe()`-Aufruf in jedem Browser ersetzt sein Abonnement.
:::

## Funktionsweise {#how-it-works}

Der Prozess hat drei Schritte:

1. **Abonnieren.** Aus einer Ansicht fordert `Push.getCurrent().subscribe()` die Erlaubnis des Benutzers an und gibt ein `PushSubscription` zurück, das die Adresse des Browsers identifiziert.
2. **Speichern.** Die App speichert das Abonnement mit seinen Daten und verknüpft es mit dem entsprechenden Benutzer.
3. **Senden.** Später, aus jedem Thread, übergibt `PushSender.send(subscription, message)` die Nachricht an den Push-Dienst des Browseranbieters. Der Dienst zeigt die Benachrichtigung an, unabhängig davon, ob die App geöffnet ist oder nicht.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Bestellung versandt").setUrl("/orders/42").build());
```

Die folgenden Abschnitte erläutern, was der Browser anzeigt und wie man Fehler bei jedem Schritt behandelt.

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

## Abonnieren des Browsers {#subscribing-the-browser}

Rufen Sie `subscribe()` als Reaktion auf eine Benutzeraktion auf, z. B. durch Klicken auf eine Schaltfläche "Benachrichtigungen aktivieren". Das zurückgegebene <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird mit dem <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> des Browsers abgeschlossen. Wenn der Browser sich nicht abonnieren kann, wird es außergewöhnlich mit einer <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink> abgeschlossen.

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

Wenn der Browser bereits abonniert ist, gibt der erneute Aufruf von `subscribe()` das bestehende Abonnement zurück. Sie können es daher bei jedem Besuch sicher aufrufen.

:::info Berechtigung des Browsers
Der erste Aufruf von `subscribe()` fordert den Benutzer zur Erlaubnis auf. Der Browser zeigt diese Eingabeaufforderung an, sie gehört nicht zur UI der App. Da Browser die Eingabeaufforderung nur als Reaktion auf eine Benutzeraktion anzeigen, rufen Sie `subscribe()` aus einem Klick-Listener statt aus dem Konstruktor der Ansicht auf.

Wenn der Benutzer die Eingabeaufforderung blockiert, kann die App nicht erneut nach dieser Herkunft fragen.
:::

### Speichern von Abonnements {#storing-subscriptions}

Ein Abonnement stellt die Adresse eines Browsers dar und gehört auf den Server. Speichern Sie es mit den Daten der App, wobei Sie seinen Endpunkt als Schlüssel verwenden. Fügen Sie alle Informationen hinzu, die die App benötigt, um später die entsprechenden Browser auszuwählen, wie den zugehörigen Benutzer. Jedes Abonnement enthält drei Textwerte:

| Wert | Bedeutung |
|------|-----------|
| `getEndpoint()` | Die Liefer-URL, die vom Push-Dienst des Browseranbieters zugewiesen wurde |
| `getP256dh()` | Der öffentliche Schlüssel des Browsers |
| `getAuth()` | Das Authentifizierungsgeheimnis des Browsers |

Ein Benutzer, der sich von zwei Browsern abonniert, hat zwei Abonnements. Löschen Sie ein Abonnement, wenn der Browser sich abmeldet oder wenn ein Senden meldet, dass es abgelaufen ist. Siehe [Fehlerstatus](#failure-status).

### Wiederherstellen eines Abonnements {#restoring-a-subscription}

`getSubscription()` gibt das aktuelle Abonnement des Browsers zurück oder ein leeres Ergebnis, wenn keines vorhanden ist. Verwenden Sie es, um die Kopie des Servers zu synchronisieren, z. B. nachdem der Speicher der App zurückgesetzt wurde:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Über <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink> berichtet `getPermission()`, ob der Benutzer die Benachrichtigungen genehmigt, abgelehnt oder noch nicht auf die Eingabeaufforderung geantwortet hat. Verwenden Sie dieses Ergebnis, um die Schaltfläche "Benachrichtigungen aktivieren" auszublenden, wenn ein Klick darauf keine Wirkung hätte.

### Abbestellen {#unsubscribing}

`unsubscribe()` annuliert das Abonnement des Browsers. Es wird mit dem entfernten Abonnement abgeschlossen, sodass die App ihre gespeicherte Kopie löschen kann, oder mit einem leeren Ergebnis, wenn der Browser kein Abonnement hatte.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Senden von Benachrichtigungen {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> sendet eine <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> an ein gespeichertes Abonnement. Es signiert die Nachricht mit den Schlüsseln der Bereitstellung und übergibt sie an den Push-Dienst des Browseranbieters. Dieser Dienst weckt den Browser und zeigt die Benachrichtigung an. Da der Vorgang den aufrufenden Thread niemals blockiert, können Sie ihn aus einem Klick-Listener, einem geplanten Job oder einem Anforderungs-Handler aufrufen.

Nachdem die Eigenschaften konfiguriert sind, ist der Sender als Bean verfügbar, die Sie in Ansichten, Diensten und geplanten Jobs injizieren können. Um ihn zu ersetzen, definieren Sie Ihre eigene `PushSender`-Bean.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Ohne Spring liest `new PushSender()` die Schlüssel aus der Konfiguration der App. Erstellen Sie den Sender in einem App-Thread, entweder in einer Ansicht oder in `App.run()`, und verwenden Sie ihn dann aus jedem Thread. Alle Sender teilen sich einen Verbindungs-Pool zu den Push-Diensten, sodass es keine Kosten verursacht, einen überall dort zu erstellen, wo es nötig ist.

Für Benachrichtigungen, die später oder nachdem der Benutzer gegangen ist, gesendet werden müssen, verwenden Sie einen Timer auf dem Server, z. B. Springs `TaskScheduler`. Verwenden Sie keinen Seiten-Timer wie `Interval`, da dieser stoppt, wenn der Tab geschlossen wird.

### Erstellen einer Nachricht {#composing-a-message}

Erstellen Sie eine Nachricht mit ihrem Titel, und konfigurieren Sie alle anderen Optionen im Builder:

```java
PushMessage message = PushMessage.create("Bestellung versandt")
    .setBody("Bestellung #42 ist auf dem Weg")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Verfolgen", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Gesendet"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` gibt sofort zurück. Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird abgeschlossen, wenn der Push-Dienst die Nachricht akzeptiert, oder wird außergewöhnlich abgeschlossen, wenn der Dienst sie nicht akzeptiert. Wenn `send()` in einem App-Thread aufgerufen wird, z. B. von einem Listener, werden die Rückrufe in diesem Thread ausgeführt und können Komponenten aktualisieren. Wenn die Sitzung, die `send()` aufgerufen hat, endet, bevor die Antwort eintrifft, werden die Rückrufe nicht ausgeführt, aber die Benachrichtigung wird trotzdem zugestellt.

Ein Versand wartet bis zu 30 Sekunden auf den Push-Dienst, bevor er mit `UNREACHABLE` fehlschlägt. Verwenden Sie `setTimeout(Duration)`, um die Zeitüberschreitung für jeden Sender zu ändern.

| Option | Effekt |
|--------|--------|
| `setBody` | Legt den Text fest, der unter dem Titel angezeigt wird |
| `setIcon` | Legt das Bild fest, das mit der Benachrichtigung angezeigt wird. Es akzeptiert absolute URLs sowie die Protokolle `icons://` und `ws://`. Siehe [Assets](/docs/managing-resources/assets-protocols). Es akzeptiert nicht das Protokoll `context://`, da Push-Dienste eine Nachricht auf 4 KB beschränken |
| `setUrl` | Legt die Seite fest, die geöffnet wird, wenn der Benutzer auf die Benachrichtigung klickt. Relative URLs werden gegen die App-Wurzel aufgelöst. Wenn keine URL festgelegt ist, öffnet sich die App-Wurzel |
| `setActions` | Legt die Schaltflächen fest, die in der Benachrichtigung angezeigt werden, mit einer separaten URL für jede Schaltfläche. Siehe [Browserunterstützung](#browser-support) |
| `setTag` | Legt ein identifizierendes Tag fest. Wenn eine angezeigte Benachrichtigung dasselbe Tag hat, wird die neue Benachrichtigung ersetzt |
| `setSilent` | Zeigt die Benachrichtigung ohne Ton oder Vibration an |
| `setTimeToLive` | Legt fest, wie lange der Push-Dienst die Nachricht für ein offline Gerät behält, bis zu vier Wochen |
| `setUrgency` | Verwendet <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink>, um dem Gerät zu ermöglichen, Nachrichten mit niedriger Dringlichkeit zu verzögern und die Batterie zu sparen |
| `setTopic` | Ersetzt eine Nachricht, die noch beim Push-Dienst wartet, wenn beide Nachrichten dasselbe Thema haben. Themen können maximal 32 Zeichen enthalten, die in einer URL sicher sind |

Wenn ein Tab bereits die Seite anzeigt, wird bei einem Klick auf die Benachrichtigung die App fokussiert. Andernfalls wird die Seite in einem neuen Tab geöffnet. Ein Klick auf eine Schaltfläche der Benachrichtigung öffnet ihre URL auf dieselbe Weise.

:::info Eine Benachrichtigung pro Nachricht
Jede Nachricht zeigt eine Benachrichtigung an. Da Browser eine Seite nicht für eine Nachricht wecken, die nichts anzeigen, kann Push nicht für stille Datenaktualisierungen verwendet werden.
:::

## Fehlerstatus {#failure-status}

Wenn `subscribe()` oder `send()` fehlschlägt, meldet sein `PendingResult` eine `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> identifiziert den Grund:

| Status | Wann | Was zu tun ist |
|--------|------|----------------|
| `PERMISSION_DENIED` | Der Benutzer hat Benachrichtigungen für die App blockiert | Erklären Sie, wo der Benutzer in den Browsereinstellungen Benachrichtigungen erlauben kann |
| `UNSUPPORTED` | Push wird vom Browser nicht unterstützt, die Seite ist nicht in einem sicheren Kontext, oder die App ist nicht als Servlet bereitgestellt | Funktion ausblenden |
| `NOT_CONFIGURED` | Mindestens eine `webforj.push.*`-Eigenschaft fehlt oder ist unvollständig | Generieren Sie die Schlüssel und konfigurieren Sie alle drei Eigenschaften |
| `SUBSCRIPTION_EXPIRED` | Der Push-Dienst erkennt das Abonnement nicht mehr, weil der Benutzer sich abgemeldet oder den Browser neu installiert hat | Entfernen Sie das gespeicherte Abonnement |
| `REJECTED` | Der Push-Dienst hat die Nachricht abgelehnt; `getStatusCode()` enthält die Antwort | Überprüfen Sie die Schlüssel und die Nachrichtengröße |
| `UNREACHABLE` | Der Push-Dienst hat vor der Zeitüberschreitung nicht geantwortet | Versuchen Sie es später erneut |
| `UNKNOWN` | Der gespeicherte Endpunkt ist keine gültige URL oder das Abonnement oder die Nachricht konnte nicht kodiert werden | Überprüfen Sie das gespeicherte Abonnement |

Entfernen Sie abgelaufene Abonnements während jedes Sendens:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Ablauf kommt eine Nachricht zu spät
Push-Dienste deregistrieren Abonnements träge. Sie akzeptieren immer noch die erste Nachricht, nachdem sich ein Benutzer abgemeldet hat, aber sie kommt nirgendwohin. Die nächste Nachricht meldet `SUBSCRIPTION_EXPIRED`. Ein akzeptiertes Senden bedeutet, dass die Nachricht den Push-Dienst erreicht hat, nicht dass der Benutzer sie gesehen hat.
:::

## Unterstützung der Browser {#browser-support}

Alle wichtigen Desktop- und Mobilbrowser zeigen Push-Benachrichtigungen nach dem Abonnieren an. Behalten Sie diese Einschränkungen im Hinterkopf:

- Auf iPhone und iPad funktioniert Push nur für Web-Apps, die auf dem Home-Bildschirm in iOS 16.4 oder später hinzugefügt wurden. In einem Safari-Tab meldet `subscribe()` `UNSUPPORTED`. Siehe [Installierbare Apps](/docs/configuration/installable-apps) für das erforderliche App-Manifest.
- Safari zeigt keine Benachrichtigungsschaltflächen an. Es zeigt Nachrichten mit Aktionen ohne ihre Schaltflächen an, aber ein Klick auf die Benachrichtigung öffnet trotzdem die URL der Nachricht.
- Android- und iOS-WebViews zeigen keine Benachrichtigungen an.

Für die Details pro Browser siehe die MDN [Kompatibilitätstabelle für showNotification](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Vollständiges Beispiel {#complete-example}

Die folgende Ansicht abonniert und meldet den Browser ab, speichert Abonnements im Speicher und sendet eine Nachricht an jedes gespeicherte Abonnement. Es kann sofort oder mit einer Verzögerung von acht Sekunden senden, indem es Springs `TaskScheduler` verwendet, und ermöglicht es, den Tab zu schließen, bevor die Benachrichtigung eintrifft. Die App-Klasse verwendet `@EnableScheduling`, um den Scheduler verfügbar zu machen.

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
  private final Paragraph status = new Paragraph("Überprüfung des Abonnements...");
  private final TextField message = new TextField("Nachricht", "Bestellung #42 ist auf dem Weg");
  private final Button subscribe =
      new Button("Benachrichtigungen aktivieren", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Benachrichtigungen deaktivieren");
  private final Button sendNow = new Button("Jetzt senden");
  private final Button sendLater = new Button("In 8 Sekunden senden");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Abonniert");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Benachrichtigungen sind in diesem Browser blockiert"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Abgemeldet" : "Es gab kein Abonnement");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Sende in 8 Sekunden, schließen Sie jetzt den Tab");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Abonniert" : "Nicht abonniert");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Sende an " + subscriptions.findAll().size() + " Abonnements");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Bestellungen")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Öffne Start", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Zugestellt"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("Ein Abonnement ist abgelaufen und wurde entfernt");
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
