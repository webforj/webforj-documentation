---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: b7e4651594dee824d6bcdf1ac32e1998
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

Die `DesktopNotification`-Komponente zeigt native Desktop-Benachrichtigungen außerhalb des Browserfensters an. Sie kann verwendet werden, um Benutzer über Echtzeitereignisse wie neue Nachrichten, Systemwarnungen oder Statusänderungen zu informieren, während sie Ihre App nutzen.

<!-- INTRO_END -->

## Einrichtung und Voraussetzungen {#setup-and-prerequisites}

<ExperimentalWarning />

Um diese Funktion zu nutzen, fügen Sie die folgende Abhängigkeit in Ihrer pom.xml hinzu:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Stellen Sie vor der Integration der `DesktopNotification`-Komponente sicher, dass:

- Ihre App in einem **sicheren Kontext** (HTTPS) ausgeführt wird.
- Der Browser sich nicht im Inkognito- oder privaten Modus befindet.
- Der Benutzer mit der App interagiert hat (z. B. eine Schaltfläche angeklickt oder eine Taste gedrückt), da Benachrichtigungen eine Benutzerinteraktion erfordern, um angezeigt zu werden.
- Der Benutzer die Berechtigungen für Benachrichtigungen erteilt hat (dies wird automatisch angefordert, wenn es notwendig ist).

## Grundlegende Verwendung {#basic-usage}

Es gibt mehrere Möglichkeiten, eine Benachrichtigung zu erstellen und anzuzeigen. In den meisten Szenarien ist der einfachste Ansatz, eine der statischen `show`-Methoden aufzurufen, die den vollständigen Lebenszyklus der Benachrichtigung kapseln.

### Beispiel: Eine einfache Benachrichtigung anzeigen {#example-displaying-a-basic-notification}

```java
// Einfache Benachrichtigung mit Titel und Nachricht
DesktopNotification.show("Update verfügbar", "Ihr Download ist abgeschlossen!");
```

Dieser Einzeiler erstellt eine Benachrichtigung mit einem Titel und Inhalt und versucht dann, sie anzuzeigen.

## Anpassung der Benachrichtigung {#customizing-the-notification}

Es gibt verschiedene Optionen zur Anpassung des Aussehens und des Verhaltens der angezeigten Benachrichtigung, abhängig von den Bedürfnissen der App und dem Zweck der Benachrichtigung. 

### Festlegen eines benutzerdefinierten `Icons` {#setting-a-custom-icon}

Standardmäßig verwendet die Benachrichtigung das definierte App-Icon über das [icons-Protokoll](../managing-resources/assets-protocols#the-icons-protocol). Sie können ein benutzerdefiniertes Icon mit der Methode `setIcon` festlegen. Die Komponente unterstützt verschiedene URL-Schemata:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Wird als Kontext-URL auf den Ressourcenordner der App aufgelöst; Bild ist base64-kodiert.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Wird als URL eines Webservers aufgelöst und gibt eine vollqualifizierte URL zurück.
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

Die `DesktopNotification` unterstützt mehrere Lebenszyklusereignisse, und Listener können angehängt werden, um Ereignisse zu behandeln, wie wenn eine Benachrichtigung angezeigt, geschlossen, angeklickt oder ein Fehler aufgetreten ist.

| Ereignis             | Beschreibung                                           | Wann zu verwenden                                        |
|----------------------|-------------------------------------------------------|---------------------------------------------------------|
| **Öffnen**           | Wird ausgelöst, wenn die Benachrichtigung angezeigt wird.       | Protokollierung der Benachrichtigungsanzeige, UI-Updates, Engagement-Tracking. |
| **Schließen**        | Wird ausgelöst, wenn die Benachrichtigung geschlossen wird.     | Ressourcen bereinigen, Abmeldungen protokollieren, Folgeaktionen ausführen. |
| **Fehler**           | Wird ausgelöst, wenn ein Fehler mit der Benachrichtigung auftritt oder der Benutzer keine Berechtigung erteilt hat. | Fehler elegant behandeln, Benutzer benachrichtigen, Fallbacks anwenden. |
| **Klicken**          | Wird ausgelöst, wenn der Benutzer auf die Benachrichtigung klickt. | Zu einem bestimmten Abschnitt navigieren, Interaktionen protokollieren, die App erneut fokussieren. |

```java
DesktopNotification notification = new DesktopNotification("Alarm", "Sie haben eine neue Nachricht!")

// Anfügen eines Ereignis-Listeners für das Öffnen-Ereignis
notification.onOpen(event -> {
  System.out.println("Benachrichtigung wurde vom Benutzer geöffnet.");
});

// Ähnlich, das Klicken-Ereignis abhören
notification.onClick(event -> {
  System.out.println("Benachrichtigung angeklickt.");
});
```

:::warning Klickverhalten
Sicherheitsrichtlinien des Browsers verhindern, dass das Klicken auf die Benachrichtigung automatisch das Fenster oder den Tab Ihrer App in den Fokus bringt. Dieses Verhalten wird vom Browser erzwungen und kann nicht programmgesteuert überschrieben werden. Wenn Ihre App erfordert, dass das Fenster fokussiert wird, müssen Sie die Benutzer anweisen, nach der Interaktion mit der Benachrichtigung innerhalb der App zu klicken.
:::

## Sicherheits- und Kompatibilitätsüberlegungen {#security-and-compatibility-considerations}

Bei der Verwendung der **DesktopNotification**-Komponente sind die folgenden Punkte zu beachten:

- **Sicherheitskontext:** Ihre App muss über HTTPS bereitgestellt werden, um sicherzustellen, dass Benachrichtigungen von den meisten modernen Browsern zugelassen werden.
- **Anforderung der Benutzerinteraktion:** Benachrichtigungen werden nur nach einer benutzergetriggerten Aktion angezeigt. Das bloße Laden einer Seite löst keine Benachrichtigung aus.
- **Browser-Einschränkungen:** Nicht alle Browser behandeln benutzerdefinierte Icons oder das Fokusverhalten gleich. Zum Beispiel funktionieren benutzerdefinierte Icons möglicherweise nicht in Safari, während sich das Ereignisverhalten in anderen Browsern unterscheiden kann.
- **Berechtigungen:** Stellen Sie immer sicher, dass Ihre App berechtigt ist und die Benachrichtigungserlaubnisse vom Benutzer elegant anfordert.

## Beste Praktiken für die Verwendung {#usage-best-practices}

Behalten Sie die folgenden besten Praktiken im Hinterkopf, während Sie die `DesktopNotification`-Komponente in Ihrer App verwenden:

- **Informieren Sie Ihre Benutzer:** Lassen Sie die Benutzer wissen, warum Benachrichtigungen benötigt werden und wie sie davon profitieren können.
- **Bereitstellung von Fallbacks:** Da einige Browser Benachrichtigungen einschränken können, ziehen Sie alternative Möglichkeiten in Betracht, um die Benutzer zu benachrichtigen (z. B. In-App-Nachrichten).
- **Fehlerbehandlung:** Registrieren Sie immer einen Fehler-Listener, um Szenarien elegant zu verwalten, in denen Benachrichtigungen nicht angezeigt werden können.
