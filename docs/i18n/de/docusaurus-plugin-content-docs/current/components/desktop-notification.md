---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 79d5ddb69e13f8536439346d8d4ee85d
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

Die `DesktopNotification`-Komponente zeigt native Desktop-Benachrichtigungen außerhalb des Browserfensters an. Sie kann verwendet werden, um Benutzer über Echtzeitereignisse wie neue Nachrichten, Systembenachrichtigungen oder Statusänderungen zu informieren, während sie Ihre App verwenden.

<!-- INTRO_END -->

## Einrichtung und Voraussetzungen {#setup-and-prerequisites}

:::warning experimentelles Feature
Die `DesktopNotification`-Komponente befindet sich noch in der Entwicklung, und ihre API kann sich im Laufe der Zeit ändern. Um dieses Feature zu nutzen, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml einfügen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

Stellen Sie vor der Integration der `DesktopNotification`-Komponente sicher, dass:

- Ihre App in einem **sicheren Kontext** (HTTPS) ausgeführt wird.
- Der Browser sich nicht im Inkognito- oder privaten Modus befindet.
- Der Benutzer mit der App interagiert hat (z. B. auf eine Schaltfläche geklickt oder eine Taste gedrückt), da Benachrichtigungen eine Benutzeraktion zum Anzeigen benötigen.
- Der Benutzer die Berechtigungen für Benachrichtigungen erteilt hat (dies wird automatisch angefordert, falls erforderlich).

## Grundlegende Verwendung {#basic-usage}

Es gibt mehrere Möglichkeiten, eine Benachrichtigung zu erstellen und anzuzeigen. In den meisten Szenarien besteht der einfachste Ansatz darin, eine der statischen `show`-Methoden aufzurufen, die den gesamten Lebenszyklus der Benachrichtigung kapseln.

### Beispiel: Anzeigen einer grundlegenden Benachrichtigung {#example-displaying-a-basic-notification}

```java
// Grundlegende Benachrichtigung mit Titel und Nachricht
DesktopNotification.show("Update Verfügbar", "Ihr Download ist abgeschlossen!");
```

Dieser Einzeiler erstellt eine Benachrichtigung mit einem Titel und einem Text und versucht dann, sie anzuzeigen.

## Anpassung der Benachrichtigung {#customizing-the-notification}

Es gibt verschiedene Optionen, um das Aussehen und Gefühl der angezeigten Benachrichtigung anzupassen, je nach den Bedürfnissen der App und dem Zweck der Benachrichtigung. 

### Einstellen eines benutzerdefinierten `Icons` {#setting-a-custom-icon}

Standardmäßig verwendet die Benachrichtigung Ihr definiertes App-Icon über das [Icons-Protokoll](../managing-resources/assets-protocols#the-icons-protocol). Sie können ein benutzerdefiniertes Icon mit der `setIcon`-Methode festlegen. Die Komponente unterstützt verschiedene URL-Schemata:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Wird als Kontext-URL auf den Ressourcenordner der App aufgelöst; das Bild wird base64-codiert.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Wird als URL eines Webservers aufgelöst, die eine vollständig qualifizierte URL bereitstellt.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Wird als Icons-URL aufgelöst.

**Beispiel:**

```java
// Erstellen einer Benachrichtigung mit einer benutzerdefinierten Icon-URL
DesktopNotification notification = new DesktopNotification(
  "Erinnerung", "Das Meeting beginnt in 10 Minuten."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Benachrichtigungsereignisse {#notification-events}

Die `DesktopNotification` unterstützt mehrere Lebenszyklusereignisse, und Listener können hinzugefügt werden, um Ereignisse zu behandeln, wie wenn eine Benachrichtigung angezeigt, geschlossen, angeklickt oder ein Fehler aufgetreten ist.

| Ereignis                  | Beschreibung                                           | Wann zu verwenden                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Öffnen** | Ausgelöst, wenn die Benachrichtigung angezeigt wird.       | Benachrichtigungsanzeige protokollieren, UI aktualisieren, Interaktionen verfolgen.    |
| **Schließen**| Ausgelöst, wenn die Benachrichtigung geschlossen wird.         | Ressourcen bereinigen, Abweisungen protokollieren, Folgeaktionen ausführen.|
| **Fehler**| Ausgelöst, wenn ein Fehler mit der Benachrichtigung auftritt oder der Benutzer keine Erlaubnis erteilt hat.| Fehler elegant behandeln, den Benutzer benachrichtigen, Alternativen anwenden.  |
| **Klick**| Ausgelöst, wenn der Benutzer auf die Benachrichtigung klickt. | Zu einem bestimmten Abschnitt navigieren, Interaktionen protokollieren, die App neu fokussieren. |


```java
DesktopNotification notification = new DesktopNotification("Alarm", "Sie haben eine neue Nachricht!")

// Fügen Sie einen Ereignis-Listener für das Öffnen-Ereignis hinzu
notification.onOpen(event -> {
  System.out.println("Benachrichtigung wurde vom Benutzer geöffnet.");
});

// Hören Sie ebenfalls auf das Klick-Ereignis
notification.onClick(event -> {
  System.out.println("Benachrichtigung angeklickt.");
});
```

:::warning Klickverhalten
Die Sicherheitsrichtlinien des Browsers verhindern, dass das Klickereignis der Benachrichtigung das Fenster oder den Tab Ihrer App automatisch in den Vordergrund bringt. Dieses Verhalten wird vom Browser durchgesetzt und kann nicht programmgesteuert überschrieben werden. Wenn Ihre App erfordert, dass das Fenster im Vordergrund ist, müssen Sie die Benutzer anweisen, nach der Interaktion mit der Benachrichtigung innerhalb der App zu klicken.
:::

## Sicherheits- und Kompatibilitätsüberlegungen {#security-and-compatibility-considerations}

Wenn Sie die **DesktopNotification**-Komponente verwenden, beachten Sie die folgenden Punkte:

- **Sicherheitskontext:** Ihre App muss über HTTPS bereitgestellt werden, um sicherzustellen, dass Benachrichtigungen von den meisten modernen Browsern erlaubt sind.
- **Erfordernis einer Benutzeraktion:** Benachrichtigungen werden nur nach einer benutzerinitiierten Aktion angezeigt. Allein das Laden einer Seite löst keine Benachrichtigung aus.
- **Browsereinschränkungen:** Nicht alle Browser handhaben benutzerdefinierte Icons oder das Fokusverhalten gleich. Beispielsweise funktionieren benutzerdefinierte Icons möglicherweise nicht in Safari, während sich das Ereignisverhalten in anderen Browsern unterscheiden kann.
- **Berechtigungen:** Überprüfen Sie immer, ob Ihre App Benachrichtigungsberechtigungen vom Benutzer elegant anfragt.

## Beste Praktiken zur Verwendung {#usage-best-practices}

Berücksichtigen Sie die folgenden besten Praktiken, während Sie die `DesktopNotification`-Komponente in Ihrer App verwenden:

- **Informieren Sie Ihre Benutzer:** Lassen Sie die Benutzer wissen, warum Benachrichtigungen benötigt werden und wie sie davon profitieren können.
- **Bieten Sie Fallbacks an:** Da einige Browser möglicherweise Benachrichtigungen einschränken, sollten Sie alternative Möglichkeiten in Betracht ziehen, um Benutzer zu benachrichtigen (z. B. In-App-Nachrichten).
- **Fehlerbehandlung:** Registrieren Sie immer einen Fehler-Listener, um Szenarien, in denen Benachrichtigungen nicht angezeigt werden können, elegant zu verwalten.
