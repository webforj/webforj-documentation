---
title: Communication
sidebar_position: 3
_i18n_hash: 4a12006d21bb2a0bd6e82f2f0ff8fa78
---
Der `WebswingConnector` ermöglicht die bidirektionale Kommunikation zwischen Ihrer webforJ-App und der eingebetteten Swing-App. Dies ermöglicht es Ihnen, Befehle an die Swing-App zu senden und Benachrichtigungen zu erhalten, wenn Ereignisse innerhalb dieser auftreten.

## Aktionen an Swing senden

Die Methode `performAction()` erlaubt es Ihrer webforJ-App, Funktionen in der Swing-App auszulösen. Dies ist nützlich, um den Zustand zu synchronisieren, Aktualisierungen auszulösen oder das Verhalten der Swing-App von der Weboberfläche aus zu steuern.

Zum Beispiel, wenn Ihre Swing-App einen benutzerdefinierten Aktionshandler zum Aktualisieren von Daten hat:

```java
// Trigger a refresh in the Swing application from webforJ
connector.performAction("refresh");
```

Sie können auch Daten zusammen mit der Aktion senden. Die Swing-App empfängt dies über ihre Webswing API-Integration:

```java
// Send a command with data from webforJ
connector.performAction("selectRecord", "12345");

// Send binary data
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

Die Aktionsnamen und die erwarteten Datenformate werden durch die Implementierung Ihrer Swing-App definiert.

## Ereignisse von Swing empfangen

Der Connector löst drei Arten von Ereignissen aus, die Ihre webforJ-App über den Zustand und die Aktionen der Swing-App informieren.

### Lebenszyklusereignisse

Das **Initialisierungsereignis** wird ausgelöst, wenn die Webswing-Verbindung hergestellt und bereit für die Kommunikation ist:

```java
connector.onInitialize(event -> {
  // Verbindung hergestellt
  connector.getInstanceId().ifPresent(id ->
      console.log("Verbunden mit Webswing-Instanz: " + id)
  );
});
```

Das **Startereignis** wird ausgelöst, wenn die Swing-App vollständig geladen und aktiv ist:

```java
connector.onStart(event -> {
  // Die Swing-Anwendung ist jetzt sichtbar und interaktiv
  console.log("Anwendung bereit für Benutzereingaben");
});
```

### Benutzerdefinierte Aktionsereignisse

Wenn Ihre Swing-App benutzerdefinierte Aktionen zurück an die Weboberfläche sendet, indem sie die [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api) verwendet, werden diese als Aktionsereignisse empfangen:

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Handle the update notification
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // Binary data
            saveFile(fileData);
        });
        break;
  }
});
```
