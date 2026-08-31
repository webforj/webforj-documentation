---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 3e487693f1f11322be81f1c5a93c1ad0
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Push-Benachrichtigungen können Benutzer sogar erreichen, wenn eine App nicht geöffnet ist. Der Browser abonniert einmal, die App speichert das Abonnement und der Server nutzt es, um Benachrichtigungen zu liefern, wenn ein Ereignis eintritt. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> verwaltet das Abonnieren und Abbestellen im Browser. Auf dem Server sendet <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> eine <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> an ein gespeichertes Abonnement.

<!-- INTRO_END -->

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
- Ein Schlüsselpaar, das weiter unten generiert wird und von der Bereitstellung verwendet wird, um Benachrichtigungen zu signieren.
- Eine sichere Herkunft. Browser lehnen Abonnements ab, die über andere Protokolle als `https` bereitgestellt werden, außer von `localhost` während der Entwicklung.

:::info Sichere Ursprünge
<!-- vale off -->
Weitere Informationen zu sicheren Kontexten und warum sie wichtig sind, finden Sie in der [MDN-Dokumentation zu sicheren Kontexten](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Schlüssel generieren {#generating-the-keys}

Push-Dienste akzeptieren nur Benachrichtigungen, die von der Bereitstellung signiert sind, bei der der Browser abonniert wurde. Führen Sie das [Build-Plugin](/docs/configuration/build-plugin) einmal für jede Bereitstellung aus, um sein Schlüsselpaar zu generieren:

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

Der Befehl gibt drei Konfigurationszeilen aus. Fügen Sie diese ohne die Anführungszeichen in die `application.properties` ein oder kopieren Sie sie wie gedruckt in die `webforj.conf`. Ersetzen Sie das Subjekt durch die Kontaktadresse der Bereitstellung. Es muss sich um eine `mailto:` oder `https://` Adresse handeln, über die Push-Dienste den Betreiber kontaktieren können.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Eigenschaft | Erklärung |
|-------------|-----------|
| `webforj.push.public-key` | Die öffentliche Hälfte des Schlüsselpaares, die von der Bereitstellung verwendet wird, um Benachrichtigungen zu signieren |
| `webforj.push.private-key` | Die private Hälfte des Schlüsselpaares. Halten Sie sie wie jedes andere Geheimnis aus der Quellkontrolle fern |
| `webforj.push.subject` | Die Kontaktadresse der Bereitstellung. Es muss sich um eine `mailto:` oder `https://` Adresse handeln, über die Push-Dienste den Betreiber erreichen können |

Die App liest diese Eigenschaften beim Start. Wenn die Konfiguration nur einige von ihnen enthält, schlägt der Start fehl und meldet, welche Eigenschaften fehlen.

:::warning Schlüsselrotation
Jeder Browser abonniert ein Schlüsselpaar. Wenn sich die Schlüssel ändern, lehnt der Push-Dienst bestehende Abonnements ab. Der nächste `subscribe()`-Aufruf in jedem Browser ersetzt sein Abonnement.
:::

## Wie es funktioniert {#how-it-works}

Der Prozess hat drei Schritte:

1. **Abonnieren.** Aus einer Ansicht fordert `Push.getCurrent().subscribe()` die Erlaubnis des Benutzers an und gibt ein `PushSubscription` zurück, das die Adresse des Browsers identifiziert.
2. **Speichern.** Die App speichert das Abonnement mit seinen Daten und verknüpft es mit dem entsprechenden Benutzer.
3. **Senden.** Später, von jedem Thread aus, übergibt `PushSender.send(subscription, message)` die Nachricht an den Push-Dienst des Browseranbieters. Der Dienst zeigt die Benachrichtigung an, unabhängig davon, ob die App geöffnet ist oder nicht.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Bestellung versendet").setUrl("/orders/42").build());
```

Die folgenden Abschnitte erklären, was der Browser anzeigt und wie man Fehler bei jedem Schritt behandelt.

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

## Browsers abonnieren {#subscribing-the-browser}

Rufen Sie `subscribe()` als Reaktion auf eine Benutzeraktion auf, zum Beispiel beim Klicken auf eine Schaltfläche "Benachrichtigungen aktivieren". Das zurückgegebene <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird mit dem <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> des Browsers abgeschlossen. Wenn der Browser nicht abonnieren kann, wird es mit einer <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink> außergewöhnlich abgeschlossen.

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

Wenn der Browser bereits abonniert ist, gibt der erneute Aufruf von `subscribe()` das bestehende Abonnement zurück. Sie können es daher sicher bei jedem Besuch aufrufen.

:::info Berechtigung des Browsers
Der erste Aufruf von `subscribe()` fordert den Benutzer um Erlaubnis an. Der Browser zeigt dieses Eingabefeld an, es gehört nicht zur UI der App. Da Browser das Eingabefeld nur als Reaktion auf eine Benutzeraktion anzeigen, rufen Sie `subscribe()` aus einem Klick-Listener und nicht aus dem Konstruktor der Ansicht auf.

Wenn der Benutzer das Eingabefeld blockiert, kann die App nicht erneut um Erlaubnis für diesen Ursprung bitten.
:::

### Abonnements speichern {#storing-subscriptions}

Ein Abonnement stellt die Adresse eines Browsers dar und gehört auf den Server. Speichern Sie es mit den Daten der App, wobei Sie seinen Endpunkt als Schlüssel verwenden. Fügen Sie alle Informationen hinzu, die die App benötigt, um später die entsprechenden Browser auszuwählen, z. B. den zugehörigen Benutzer. Jedes Abonnement enthält drei Textwerte:

| Wert | Bedeutung |
|------|-----------|
| `getEndpoint()` | Die Liefer-URL, die vom Push-Dienst des Browseranbieters zugewiesen wurde |
| `getP256dh()` | Der öffentliche Schlüssel des Browsers |
| `getAuth()` | Das Authentifizierungsgeheimnis des Browsers |

Ein Benutzer, der sich von zwei Browsern anmeldet, hat zwei Abonnements. Löschen Sie ein Abonnement, wenn sein Browser sich abmeldet oder wenn ein Senden meldet, dass es abgelaufen ist. Siehe [Fehlerstatus](#failure-status).

### Ein Abonnement wiederherstellen {#restoring-a-subscription}

`getSubscription()` gibt das aktuelle Abonnement des Browsers zurück oder ein leeres Ergebnis, wenn keines vorhanden ist. Verwenden Sie es, um die Kopie des Servers zu synchronisieren, zum Beispiel nachdem der Speicher der App zurückgesetzt wurde:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Über <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink> berichtet `getPermission()`, ob der Benutzer die Benachrichtigung genehmigt, abgelehnt hat oder noch nicht auf die Eingabeaufforderung geantwortet hat. Verwenden Sie dieses Ergebnis, um die Schaltfläche "Benachrichtigungen aktivieren" auszublenden, wenn ein Klick darauf keine Wirkung hätte.

### Abmelden {#unsubscribing}

`unsubscribe()` kündigt das Abonnement des Browsers. Es wird mit dem entfernten Abonnement abgeschlossen, sodass die App ihre gespeicherte Kopie löschen kann, oder mit einem leeren Ergebnis, wenn der Browser kein Abonnement hatte.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Benachrichtigungen senden {#sending-notifications}

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

Ohne Spring, `new PushSender()` liest die Schlüssel aus der Konfiguration der App. Erstellen Sie den Sender in einem App-Thread, entweder in einer Ansicht oder in `App.run()`, und verwenden Sie ihn dann von jedem Thread aus. Alle Sender teilen sich einen Verbindungs-Pool zu den Push-Diensten, sodass es keine Kosten verursacht, einen überall dort zu erstellen, wo er benötigt wird.

Für Benachrichtigungen, die später oder nachdem der Benutzer gegangen ist, gesendet werden müssen, verwenden Sie einen Timer auf dem Server wie Springs `TaskScheduler`. Verwenden Sie keinen Seiten-Timer wie `Interval`, da er stoppt, wenn der Tab geschlossen wird.

### Eine Nachricht erstellen {#composing-a-message}

Erstellen Sie eine Nachricht mit ihrem Titel, und konfigurieren Sie dann jede andere Option im Builder:

```java
PushMessage message = PushMessage.create("Bestellung versendet")
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

`send()` gibt sofort zurück. Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird abgeschlossen, wenn der Push-Dienst die Nachricht annimmt, oder es wird außergewöhnlich abgeschlossen, wenn der Dienst sie nicht annimmt. Wenn `send()` in einem App-Thread aufgerufen wird, z. B. aus einem Listener, werden dessen Rückrufe in diesem Thread ausgeführt und können Komponenten aktualisieren. Wenn die Sitzung, die `send()` aufgerufen hat, endet, bevor die Antwort eintrifft, werden die Rückrufe nicht ausgeführt, aber die Benachrichtigung wird trotzdem geliefert.

Ein Versand wartet bis zu 30 Sekunden auf den Push-Dienst, bevor er mit `UNREACHABLE` fehlschlägt. Verwenden Sie `setTimeout(Duration)`, um das Timeout für jeden Sender zu ändern.

| Option | Effekt |
|--------|--------|
| `setBody` | Setzt den Text, der unter dem Titel angezeigt wird |
| `setIcon` | Legt das Bild fest, das mit der Benachrichtigung angezeigt wird. Es akzeptiert absolute URLs und die Protokolle `icons://` und `ws://`. Siehe [Assets](/docs/managing-resources/assets-protocols). Es akzeptiert nicht das Protokoll `context://`, da Push-Dienste eine Nachricht auf 4 KB beschränken |
| `setUrl` | Legt die Seite fest, die öffnet wird, wenn der Benutzer auf die Benachrichtigung klickt. Relative URLs werden gegen die App-Wurzel aufgelöst. Wenn keine URL festgelegt ist, wird die App-Wurzel geöffnet |
| `setActions` | Legt die Schaltflächen fest, die auf der Benachrichtigung angezeigt werden, mit einer separaten URL für jede Schaltfläche. Siehe [Browserunterstützung](#browser-support) |
| `setTag` | Legt ein identifizierendes Tag fest. Wenn eine angezeigte Benachrichtigung dasselbe Tag hat, ersetzt die neue Benachrichtigung sie |
| `setSilent` | Zeigt die Benachrichtigung ohne Ton oder Vibration an |
| `setTimeToLive` | Legt fest, wie lange der Push-Dienst die Nachricht für ein offline Gerät aufbewahrt, bis zu vier Wochen |
| `setUrgency` | Verwendet <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink>, um dem Gerät zu ermöglichen, Nachrichten mit niedriger Dringlichkeit zu verzögern und Batterielebensdauer zu sparen |
| `setTopic` | Ersetzt eine Nachricht, die noch beim Push-Dienst wartet, wenn beide Nachrichten dasselbe Thema haben. Themen können maximal 32 Zeichen enthalten, die in einer URL sicher sind |

Wenn ein Tab die Seite bereits anzeigt, fokussiert das Klicken auf die Benachrichtigung die App. Ansonsten wird die Seite in einem neuen Tab geöffnet. Das Klicken auf eine Benachrichtigungs-Schaltfläche öffnet ihre URL auf die gleiche Weise.

:::info Eine Benachrichtigung pro Nachricht
Jede Nachricht zeigt eine Benachrichtigung an. Da Browser eine Seite nicht für eine Nachricht wecken, die nichts anzeigt, kann Push nicht für stille Datenupdates verwendet werden.
:::

## Fehlerstatus {#failure-status}

Wenn `subscribe()` oder `send()` fehlschlägt, berichtet sein `PendingResult` eine `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> identifiziert den Grund:

| Status | Wann | Was zu tun ist |
|--------|------|----------------|
| `PERMISSION_DENIED` | Der Benutzer hat Benachrichtigungen für die App blockiert | Erklären Sie, wo der Benutzer Benachrichtigungen in den Browsereinstellungen zulassen kann |
| `UNSUPPORTED` | Push wird vom Browser nicht unterstützt, die Seite befindet sich nicht in einem sicheren Kontext oder die App ist nicht als Servlet bereitgestellt | Verstecken Sie die Funktion |
| `NOT_CONFIGURED` | Mindestens eine `webforj.push.*`-Eigenschaft fehlt oder ist unvollständig | Generieren Sie die Schlüssel und konfigurieren Sie alle drei Eigenschaften |
| `SUBSCRIPTION_EXPIRED` | Der Push-Dienst erkennt das Abonnement nicht mehr, da der Benutzer sich abgemeldet oder den Browser neu installiert hat | Entfernen Sie das gespeicherte Abonnement |
| `REJECTED` | Der Push-Dienst hat die Nachricht abgelehnt; `getStatusCode()` enthält die Antwort | Überprüfen Sie die Schlüssel und die Nachrichtengröße |
| `UNREACHABLE` | Der Push-Dienst hat vor dem Timeout nicht reagiert | Versuchen Sie es später erneut |
| `UNKNOWN` | Der gespeicherte Endpunkt ist keine gültige URL, oder das Abonnement oder die Nachricht konnten nicht kodiert werden | Überprüfen Sie das gespeicherte Abonnement |

Entfernen Sie abgelaufene Abonnements bei jedem Versand:

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
Push-Dienste deregistrieren Abonnements träge. Sie akzeptieren immer noch die erste Nachricht, nachdem sich ein Benutzer abgemeldet hat, aber sie geht nirgends hin. Die nächste Nachricht meldet `SUBSCRIPTION_EXPIRED`. Eine akzeptierte Sendung bedeutet, dass die Nachricht den Push-Dienst erreicht hat, nicht, dass der Benutzer sie gesehen hat.
:::

## Browserunterstützung {#browser-support}

Alle wichtigen Desktop- und Mobilbrowser zeigen Push-Benachrichtigungen nach dem Abonnieren an. Beachten Sie diese Einschränkungen:

- Auf iPhone und iPad funktioniert Push nur für Web-Apps, die ab iOS 16.4 zum Startbildschirm hinzugefügt wurden. In einem Safari-Tab meldet `subscribe()` `UNSUPPORTED`. Siehe [Installierbare Apps](/docs/configuration/installable-apps) für das erforderliche App-Manifest.
- Safari zeigt keine Benachrichtigungs-Schaltflächen an. Es zeigt Nachrichten mit Aktionen ohne ihre Schaltflächen an, aber das Klicken auf die Benachrichtigung öffnet immer noch die Nachrichten-URL.
- Android- und iOS-Webansichten zeigen keine Benachrichtigungen an.

Für die Details pro Browser siehe die MDN [Kompatibilitätstabelle für showNotification](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Vollständiges Beispiel {#complete-example}

Die folgende Ansicht abonniert und meldet den Browser ab, speichert Abonnements im Speicher und sendet eine Nachricht an jedes gespeicherte Abonnement. Es kann sofort oder nach acht Sekunden senden, indem es Springs `TaskScheduler` verwendet, sodass der Tab geschlossen werden kann, bevor die Benachrichtigung ankommt. Die App-Klasse verwendet `@EnableScheduling`, um den Scheduler verfügbar zu machen.

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
  private final Paragraph status = new Paragraph("Überprüfen des Abonnements…");
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
      status.setText("Senden in 8 Sekunden, schließen Sie jetzt den Tab");
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
    report.accept("Senden an " + subscriptions.findAll().size() + " Abonnements");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Bestellungen")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Startseite öffnen", "/")))
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
