---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 6bc148e8bcb06161115d0509601b516b
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

In webforj 25.00 und höher bietet die **DesktopNotification**-Komponente eine einfache Schnittstelle zum Erstellen, Anzeigen und Verwalten von Desktopbenachrichtigungen. Mit dem Fokus auf minimaler Konfiguration und integrierter Ereignisbehandlung kann die Komponente verwendet werden, wenn Benutzer über Echtzeitereignisse (wie neue Nachrichten, Warnungen oder Systemereignisse) benachrichtigt werden sollen, während sie Ihre App nutzen.

:::warning experimentelle Funktion
Die `DesktopNotification`-Komponente entwickelt sich noch weiter, und ihre API kann sich ändern, während sie reift. Um diese Funktion zu nutzen, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml hinzufügen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Voraussetzungen
Bevor Sie die `DesktopNotification`-Komponente integrieren, stellen Sie sicher, dass:

- Ihre App in einem **sicheren Kontext** (HTTPS) ausgeführt wird.
- Der Browser sich nicht im Inkognito- oder privaten Browsing-Modus befindet.
- Der Benutzer mit der App interagiert hat (z. B. eine Schaltfläche geklickt oder eine Taste gedrückt), da Benachrichtigungen eine Benutzerinteraktion benötigen, um angezeigt zu werden.
- Der Benutzer die Berechtigungen für Benachrichtigungen erteilt hat (dies wird automatisch angefordert, wenn nötig).
::::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Grundlegende Verwendung {#basic-usage}

Es gibt mehrere Möglichkeiten, eine Benachrichtigung zu erstellen und anzuzeigen. In den meisten Szenarien ist der einfachste Ansatz, eine der statischen `show`-Methoden aufzurufen, die den gesamten Lebenszyklus der Benachrichtigung kapseln.

### Beispiel: Eine grundlegende Benachrichtigung anzeigen {#example-displaying-a-basic-notification}

```java
// Grundlegende Benachrichtigung mit Titel und Nachricht
DesktopNotification.show("Update verfügbar", "Ihr Download ist abgeschlossen!");
```

Dieser Einzeiler erstellt eine Benachrichtigung mit einem Titel und einem Text und versucht dann, sie anzuzeigen.

## Anpassung der Benachrichtigung {#customizing-the-notification}

Es gibt verschiedene Optionen zur Anpassung des Aussehens und der Funktionalität der angezeigten Benachrichtigung, je nach den Bedürfnissen der App und dem Zweck der Benachrichtigung.

### Festlegen eines benutzerdefinierten `Icons` {#setting-a-custom-icon}

Standardmäßig verwendet die Benachrichtigung Ihr definiertes App-Symbol über das [icons-Protokoll](../managing-resources/assets-protocols#the-icons-protocol). Sie können ein benutzerdefiniertes Symbol mit der Methode `setIcon` festlegen. Die Komponente unterstützt verschiedene URL-Schemata:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Wird als Kontext-URL auf den Ressourcenordner der App aufgelöst; Bild ist base64-kodiert.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Wird als Webserver-URL aufgelöst, die eine voll qualifizierte URL angibt.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Wird als Icons-URL aufgelöst.

**Beispiel:**

```java
// Erstellen einer Benachrichtigung mit einer benutzerdefinierten Symbol-URL
DesktopNotification notification = new DesktopNotification(
  "Erinnerung", "Das Meeting beginnt in 10 Minuten."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Benachrichtigungsereignisse {#notification-events}

Die `DesktopNotification` unterstützt mehrere Lebenszyklusereignisse, und Listener können angehängt werden, um Ereignisse zu behandeln, wie wenn eine Benachrichtigung angezeigt, geschlossen, angeklickt oder ein Fehler aufgetreten ist.

| Ereignis                  | Beschreibung                                           | Wann zu verwenden                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Öffnen** | Wird ausgelöst, wenn die Benachrichtigung angezeigt wird.       | Protokollieren Sie die Anzeige der Benachrichtigung, aktualisieren Sie die Benutzeroberfläche, verfolgen Sie das Engagement.    |
| **Schließen**| Wird ausgelöst, wenn die Benachrichtigung geschlossen wird.         | Ressourcen bereinigen, Abweisungen protokollieren, Folgeaktionen ausführen.|
| **Fehler**| Wird ausgelöst, wenn ein Fehler mit der Benachrichtigung auftritt oder der Benutzer keine Berechtigung erteilt hat.| Behandeln Sie Fehler elegant, benachrichtigen Sie den Benutzer, wenden Sie Fallbacks an.  |
| **Klicken**| Wird ausgelöst, wenn der Benutzer auf die Benachrichtigung klickt. | Zu einem bestimmten Abschnitt navigieren, Interaktionen protokollieren, die App fokussieren. |

```java
DesktopNotification notification = new DesktopNotification("Alarm", "Sie haben eine neue Nachricht!")

// Hängen Sie einen Ereignis-Listener für das Öffnen-Ereignis an
notification.onOpen(event -> {
  System.out.println("Benachrichtigung wurde vom Benutzer geöffnet.");
});

// Ebenso, hören Sie auf das Klicken-Ereignis
notification.onClick(event -> {
  System.out.println("Benachrichtigung geklickt.");
});
```

:::warning Klickverhalten
Die Sicherheitsrichtlinien des Browsers verhindern, dass das Klicken auf die Benachrichtigung automatisch das Fenster oder den Tab Ihrer App in den Fokus bringt. Dieses Verhalten wird vom Browser durchgesetzt und kann nicht programmgesteuert überschrieben werden. Wenn Ihre App erfordert, dass das Fenster fokussiert wird, müssen Sie die Benutzer anweisen, innerhalb der App zu klicken, nachdem sie mit der Benachrichtigung interagiert haben.
:::

## Sicherheits- und Kompatibilitätsüberlegungen {#security-and-compatibility-considerations}

Bei der Verwendung der **DesktopNotification**-Komponente sollten Sie die folgenden Punkte beachten:

- **Sicherheitskontext:** Ihre App muss über HTTPS bereitgestellt werden, damit die meisten modernen Browser Benachrichtigungen zulassen.
- **Benutzerinteraktion erforderlich:** Benachrichtigungen werden nur nach einer benutzerinitiierte Aktion angezeigt. Das bloße Laden einer Seite löst keine Benachrichtigung aus.
- **Browserbeschränkungen:** Nicht alle Browser behandeln benutzerdefinierte Symbole oder Fokusverhalten auf die gleiche Weise. Beispielsweise funktionieren benutzerdefinierte Symbole möglicherweise nicht in Safari, während sich das Ereignisverhalten in anderen Browsern unterscheiden kann.
- **Berechtigungen:** Überprüfen Sie stets, ob Ihre App die Berechtigungen für Benachrichtigungen beim Benutzer elegant anfordert und überprüft.

## Best Practices für die Nutzung {#usage-best-practices}

Behalten Sie die folgenden Best Practices im Hinterkopf, während Sie die `DesktopNotification`-Komponente in Ihrer App verwenden:

- **Informieren Sie Ihre Benutzer:** Lassen Sie die Benutzer wissen, warum Benachrichtigungen benötigt werden und wie sie davon profitieren können.
- **Fallen Sie zurück:** Da einige Browser möglicherweise Benachrichtigungen einschränken, ziehen Sie alternative Möglichkeiten in Betracht, um Benutzer zu benachrichtigen (z. B. In-App-Nachrichten).
- **Fehlerbehandlung:** Registrieren Sie immer einen Fehlerlistener, um Szenarien, in denen Benachrichtigungen nicht angezeigt werden können, elegant zu verwalten.
