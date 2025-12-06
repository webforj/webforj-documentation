---
title: Communication
sidebar_position: 3
_i18n_hash: 06bf57e08ee82a4970539b73215c1540
---
Der `WebswingConnector` ermöglicht eine bidirektionale Kommunikation zwischen Ihrer webforJ-Anwendung und der eingebetteten Swing-Anwendung. Dies ermöglicht es Ihnen, Befehle an die Swing-Anwendung zu senden und Benachrichtigungen zu erhalten, wenn innerhalb dieser Ereignisse auftreten.

## Aktionen an Swing senden {#sending-actions-to-swing}

Die Methode `performAction()` ermöglicht es Ihrer webforJ-Anwendung, Funktionalitäten in der Swing-Anwendung auszulösen. Dies ist nützlich, um den Zustand zu synchronisieren, Aktualisierungen auszulösen oder das Verhalten der Swing-Anwendung von der Weboberfläche aus zu steuern.

Wenn Ihre Swing-Anwendung beispielsweise einen benutzerdefinierten Aktionshandler für das Aktualisieren von Daten hat:

```java
// Auslösen eines Refresh in der Swing-Anwendung von webforJ
connector.performAction("refresh");
```

Sie können auch Daten zusammen mit der Aktion senden. Die Swing-Anwendung empfängt dies über ihre Webswing API-Integration:

```java
// Senden eines Befehls mit Daten von webforJ
connector.performAction("selectRecord", "12345");

// Senden binärer Daten
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

Die Aktionsnamen und erwarteten Datenformate sind durch die Implementierung Ihrer Swing-Anwendung definiert.

## Ereignisse von Swing empfangen {#receiving-events-from-swing}

Der Connector löst drei Arten von Ereignissen aus, die Ihre webforJ-Anwendung über den Zustand und die Aktionen der Swing-Anwendung informieren.

### Lebenszyklusereignisse {#lifecycle-events}

Das **initialisieren Ereignis** wird ausgelöst, wenn die Webswing-Verbindung hergestellt und bereit für die Kommunikation ist:

```java
connector.onInitialize(event -> {
  // Verbindung hergestellt
  connector.getInstanceId().ifPresent(id ->
      console.log("Verbunden mit Webswing-Instanz: " + id)
  );
});
```

Das **start Ereignis** wird ausgelöst, wenn die Swing-Anwendung vollständig geladen und aktiv ist:

```java
connector.onStart(event -> {
  // Swing-Anwendung ist nun sichtbar und interaktiv
  console.log("Anwendung bereit für Benutzerinteraktion");
});
```

### Benutzerdefinierte Aktionsereignisse {#custom-action-events}

Wenn Ihre Swing-Anwendung benutzerdefinierte Aktionen an die Weboberfläche mit der [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api) sendet, werden diese als Aktionsereignisse empfangen:

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Benachrichtigung über die Aktualisierung bearbeiten
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // Binäre Daten
            saveFile(fileData);
        });
        break;
  }
});
```
