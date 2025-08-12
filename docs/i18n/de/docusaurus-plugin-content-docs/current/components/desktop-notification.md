---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 16e95136d6067cafa258ef513f9e757f
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

In webforj 25.00 und höher bietet die **DesktopNotification**-Komponente eine einfache Schnittstelle zum Erstellen, Anzeigen und Verwalten von Desktopbenachrichtigungen. Mit dem Fokus auf minimale Konfiguration und integrierte Ereignisbehandlung kann die Komponente verwendet werden, um Benutzer über Echtzeite Ereignisse (wie neue Nachrichten, Warnungen oder Systemereignisse) zu benachrichtigen, während sie Ihre App durchsuchen.

:::warning experimentelles Feature
Die `DesktopNotification`-Komponente befindet sich noch in der Entwicklung, und ihre API kann sich im Laufe der Zeit ändern. Um dieses Feature zu nutzen, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml einfügen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Voraussetzungen
Stellen Sie sicher, dass Folgendes gegeben ist, bevor Sie die `DesktopNotification`-Komponente integrieren:

- Ihre App läuft in einem **sicheren Kontext** (HTTPS).
- Der Browser befindet sich nicht im Inkognito- oder privaten Modus.
- Der Benutzer hat mit der App interagiert (z. B. einen Button geklickt oder eine Taste gedrückt), da Benachrichtigungen eine Benutzerinteraktion benötigen, um angezeigt zu werden.
- Der Benutzer hat die Berechtigung für Benachrichtigungen erteilt (dies wird bei Bedarf automatisch angefordert).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Grundlegende Verwendung {#basic-usage}

Es gibt mehrere Möglichkeiten, eine Benachrichtigung zu erstellen und anzuzeigen. In den meisten Szenarien ist der einfachste Ansatz, eine der statischen `show`-Methoden aufzurufen, die den gesamten Lebenszyklus der Benachrichtigung kapseln.

### Beispiel: Anzeig einer grundlegenden Benachrichtigung {#example-displaying-a-basic-notification}

```java
// Grundlegende Benachrichtigung mit Titel und Nachricht
DesktopNotification.show("Update Verfügbar", "Ihr Download ist abgeschlossen!");
```

Diese Einzeilige erstellt eine Benachrichtigung mit einem Titel und einem Text und versucht dann, sie anzuzeigen.

## Anpassung der Benachrichtigung {#customizing-the-notification}

Es gibt verschiedene Optionen zur Anpassung des Aussehens und des Inhalts der angezeigten Benachrichtigung, abhängig von den Bedürfnissen der App und dem Zweck der Benachrichtigung. 

### Festlegen eines benutzerdefinierten `Icons` {#setting-a-custom-icon}

Standardmäßig verwendet die Benachrichtigung Ihr definiertes App-Symbol über das [icons-Protokoll](../managing-resources/assets-protocols#the-icons-protocol). Sie können ein benutzerdefiniertes Symbol mit der Methode `setIcon` festlegen. Die Komponente unterstützt verschiedene URL-Schemata:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Wird als Kontext-URL interpretiert, die auf den Ressourcenordner der App verweist; das Bild ist base64-enkodiert.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Wird als URL eines Webservers interpretiert und gibt eine vollständig qualifizierte URL zurück.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Wird als Icons-URL interpretiert.

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

Die `DesktopNotification` unterstützt mehrere Lebenszyklusereignisse, und es können Listener angehängt werden, um Ereignisse zu behandeln, wie z. B. wenn eine Benachrichtigung angezeigt, geschlossen, angeklickt oder ein Fehler aufgetreten ist.

| Ereignis                  | Beschreibung                                           | Wann zu verwenden                                               |
|-----------------------------|-------------------------------------------------------|---------------------------------------------------------------|
| **Öffnen** | Wird ausgelöst, wenn die Benachrichtigung angezeigt wird.       | Protokollieren der Anzeige von Benachrichtigungen, Aktualisieren der UI, Nachverfolgen der Interaktionen.    |
| **Schließen**| Wird ausgelöst, wenn die Benachrichtigung geschlossen wird.         | Bereinigen von Ressourcen, Protokollieren von Ablehnungen, Ausführen von Folgeaktionen.|
| **Fehler**| Wird ausgelöst, wenn ein Fehler bei der Benachrichtigung auftritt oder der Benutzer keine Berechtigung erteilt hat.| Fehler sanft behandeln, den Benutzer benachrichtigen, Rückfalle anwenden.  |
| **Klick**| Wird ausgelöst, wenn der Benutzer auf die Benachrichtigung klickt. | Zu einem bestimmten Abschnitt navigieren, Interaktionen protokollieren, die App erneut fokussieren. |


```java
DesktopNotification notification = new DesktopNotification("Alarm", "Sie haben eine neue Nachricht!")

// Anfügen eines Ereignis-Listeners für das Öffnen-Ereignis
notification.onOpen(event -> {
  System.out.println("Benachrichtigung wurde vom Benutzer geöffnet.");
});

// Ähnlich, hören Sie auf das Klick-Ereignis
notification.onClick(event -> {
  System.out.println("Benachrichtigung angeklickt.");
});
```

:::warning Klickverhalten
Die Sicherheitsrichtlinien des Browsers verhindern, dass das Klicken auf die Benachrichtigung automatisch Ihr App-Fenster oder Ihren Tab in den Vordergrund bringt. Dieses Verhalten wird vom Browser durchgesetzt und kann nicht programmgesteuert überschrieben werden. Wenn Ihre App erfordert, dass das Fenster fokussiert wird, müssen Sie die Benutzer anweisen, nach der Interaktion mit der Benachrichtigung innerhalb der App zu klicken.
:::

## Sicherheits- und Kompatibilitätsüberlegungen {#security-and-compatibility-considerations}

Bei der Verwendung der **DesktopNotification**-Komponente sollten Sie die folgenden Punkte beachten:

- **Sicherheitskontext:** Ihre App muss über HTTPS bereitgestellt werden, um sicherzustellen, dass Benachrichtigungen von den meisten modernen Browsern akzeptiert werden.
- **Benutzerinteraktionsanforderung:** Benachrichtigungen werden nur nach einer benutzerinitiierten Aktion angezeigt. Einfaches Laden einer Seite löst keine Benachrichtigung aus.
- **Browserbeschränkungen:** Nicht alle Browser behandeln benutzerdefinierte Symbole oder das Fokussierverhalten gleich. Zum Beispiel funktionieren benutzerdefinierte Symbole möglicherweise nicht in Safari, während sich das Ereignisverhalten in anderen Browsern unterscheiden kann.
- **Berechtigungen:** Stellen Sie immer sicher, dass Ihre App die Berechtigungen für Benachrichtigungen reserviert und anfordert.

## Best Practices für die Nutzung {#usage-best-practices}

Beachten Sie die folgenden Best Practices, während Sie die `DesktopNotification`-Komponente in Ihrer App verwenden:

- **Informieren Sie Ihre Benutzer:** Lassen Sie die Benutzer wissen, warum Benachrichtigungen benötigt werden und wie sie davon profitieren können.
- **Fallbacks bereitstellen:** Da einige Browser Benachrichtigungen einschränken können, ziehen Sie alternative Möglichkeiten in Betracht, um Benutzer zu alarmieren (z. B. In-App-Nachrichten).
- **Fehlerbehandlung:** Registrieren Sie immer einen Fehlerlistener, um Szenarien sanft zu verwalten, in denen Benachrichtigungen nicht angezeigt werden können.
