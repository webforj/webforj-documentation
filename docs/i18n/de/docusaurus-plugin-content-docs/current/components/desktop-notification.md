---
title: DesktopNotification
sidebar_position: 29
description: >-
  Send native OS notifications outside the browser window with the
  DesktopNotification component for real-time messages, alerts, and status
  changes.
_i18n_hash: 529ae2fce596f744b423574be0a95dc0
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

Die `DesktopNotification`-Komponente zeigt native Desktop-Benachrichtigungen außerhalb des Browserfensters an. Sie kann verwendet werden, um Benutzer über Echtzeitevents wie neue Nachrichten, Systembenachrichtigungen oder Statusänderungen zu informieren, während sie Ihre App nutzen.

<!-- INTRO_END -->

## Einrichtung und Voraussetzungen {#setup-and-prerequisites}

<ExperimentalWarning />

Um diese Funktion zu nutzen, fügen Sie die folgende Abhängigkeit in Ihre pom.xml ein:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Stellen Sie vor der Integration der `DesktopNotification`-Komponente sicher, dass:

- Ihre App in einem **sicheren Kontext** (HTTPS) läuft.
- Der Browser nicht im Inkognito- oder privaten Modus ist.
- Der Benutzer mit der App interagiert hat (z. B. auf einen Button geklickt oder eine Taste gedrückt), da Benachrichtigungen eine Benutzerinteraktion erfordern, um angezeigt zu werden.
- Der Benutzer die Berechtigung für Benachrichtigungen erteilt hat (dies wird automatisch angefordert, wenn erforderlich).

## Grundlegende Verwendung {#basic-usage}

Es gibt mehrere Möglichkeiten, eine Benachrichtigung zu erstellen und anzuzeigen. In den meisten Fällen ist der einfachste Ansatz, eine der statischen `show`-Methoden aufzurufen, die den vollständigen Lebenszyklus der Benachrichtigung kapseln.

### Beispiel: Anzeigen einer einfachen Benachrichtigung {#example-displaying-a-basic-notification}

```java
// Einfache Benachrichtigung mit Titel und Nachricht
DesktopNotification.show("Update Verfügbar", "Ihr Download ist abgeschlossen!");
```

Diese eine Zeile erstellt eine Benachrichtigung mit einem Titel und einem Textkörper und versucht dann, sie anzuzeigen.

## Anpassung der Benachrichtigung {#customizing-the-notification}

Es gibt verschiedene Optionen zur Anpassung des Erscheinungsbilds der angezeigten Benachrichtigung, je nach den Anforderungen der App und dem Zweck der Benachrichtigung.

### Festlegen eines benutzerdefinierten `Icons` {#setting-a-custom-icon}

Standardmäßig verwendet die Benachrichtigung Ihr definiertes App-Icon über das [Icons-Protokoll](../managing-resources/assets-protocols#the-icons-protocol). Sie können ein benutzerdefiniertes Icon mit der Methode `setIcon` festlegen. Die Komponente unterstützt verschiedene URL-Schemata:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Wird als Kontext-URL auf den Ressourcenordner der App aufgelöst; das Bild ist base64-kodiert.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Wird als Webserver-URL aufgelöst, was eine vollständig qualifizierte URL ergibt.
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

Die `DesktopNotification` unterstützt mehrere Lebenszyklusereignisse, und Listener können angehängt werden, um Ereignisse zu behandeln, wie z. B. wenn eine Benachrichtigung angezeigt, geschlossen, angeklickt wird oder ein Fehler auftritt.

| Ereignis                  | Beschreibung                                           | Wann zu verwenden                                               |
|-----------------------------|-------------------------------------------------------|---------------------------------------------------------------|
| **Öffnen** | Wird ausgelöst, wenn die Benachrichtigung angezeigt wird.       | Protokollieren der Anzeige von Benachrichtigungen, UI aktualisieren, Engagement verfolgen.    |
| **Schließen**| Wird ausgelöst, wenn die Benachrichtigung geschlossen wird.         | Ressourcen bereinigen, Ablehnungen protokollieren, Folgeaktionen ausführen.|
| **Fehler**| Wird ausgelöst, wenn ein Fehler mit der Benachrichtigung auftritt oder der Benutzer keine Erlaubnis erteilt hat.| Fehler elegant behandeln, Benutzer benachrichtigen, Fallbacks anwenden.  |
| **Klick**| Wird ausgelöst, wenn der Benutzer auf die Benachrichtigung klickt. | Zu einem bestimmten Abschnitt navigieren, Interaktionen protokollieren, App erneut fokussieren. |

```java
DesktopNotification notification = new DesktopNotification("Alarm", "Sie haben eine neue Nachricht!")

// Einen Ereignislistener für das Öffnen-Ereignis anhängen
notification.onOpen(event -> {
  System.out.println("Benachrichtigung wurde vom Benutzer geöffnet.");
});

// Ebenso, für das Klick-Ereignis hören
notification.onClick(event -> {
  System.out.println("Benachrichtigung angeklickt.");
});
```

:::warning Klickverhalten
Die Sicherheitsrichtlinien des Browsers verhindern, dass das Klickereignis der Benachrichtigung automatisch Ihr App-Fenster oder Tab in den Fokus bringt. Dieses Verhalten wird vom Browser durchgesetzt und kann nicht programmatisch überschrieben werden. Wenn Ihre App erfordert, dass das Fenster fokussiert wird, müssen Sie die Benutzer anweisen, nach der Interaktion mit der Benachrichtigung innerhalb der App zu klicken.
:::

## Sicherheits- und Kompatibilitätsüberlegungen {#security-and-compatibility-considerations}

Beim Gebrauch der **DesktopNotification**-Komponente sollten Sie folgende Punkte beachten:

- **Sicherheitskontext:** Ihre App muss über HTTPS bereitgestellt werden, damit die meisten modernen Browser Benachrichtigungen zulassen.
- **Benutzerinteraktionsanforderung:** Benachrichtigungen werden nur nach einer benutzergetriggerten Aktion angezeigt. Das bloße Laden einer Seite führt nicht dazu, dass eine Benachrichtigung ausgelöst wird.
- **Browser-Beschränkungen:** Nicht alle Browser behandeln benutzerdefinierte Icons oder Fokussierungsverhalten gleich. Zum Beispiel funktionieren benutzerdefinierte Icons möglicherweise nicht in Safari, während sich das Ereignisverhalten in anderen Browsern unterscheiden kann.
- **Berechtigungen:** Stellen Sie immer sicher, dass Ihre App Benachrichtigungsberechtigungen vom Benutzer elegant überprüft und anfordert.

## Beste Praktiken zur Nutzung {#usage-best-practices}

Behalten Sie die folgenden besten Praktiken im Auge, während Sie die `DesktopNotification`-Komponente in Ihrer App verwenden:

- **Informieren Sie Ihre Benutzer:** Lassen Sie die Benutzer wissen, warum Benachrichtigungen benötigt werden und wie sie davon profitieren können.
- **Fallbacks bereitstellen:** Da einige Browser möglicherweise Benachrichtigungen einschränken, sollten Sie alternative Möglichkeiten zur Benachrichtigung der Benutzer in Betracht ziehen (z. B. In-App-Nachrichten).
- **Fehlerbehandlung:** Registrieren Sie immer einen Fehlerlistener, um Szenarien elegant zu verwalten, in denen Benachrichtigungen nicht angezeigt werden können.
