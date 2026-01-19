---
title: Communication
sidebar_position: 3
_i18n_hash: 06bf57e08ee82a4970539b73215c1540
---
De `WebswingConnector` biedt bidirectionele communicatie tussen uw webforJ-app en de ingesloten Swing-app. Dit stelt u in staat om commando's naar de Swing-app te verzenden en meldingen te ontvangen wanneer er gebeurtenissen binnen de app plaatsvinden.

## Acties naar Swing verzenden {#sending-actions-to-swing}

De `performAction()`-methode stelt uw webforJ-app in staat om functionaliteit in de Swing-app te activeren. Dit is nuttig voor het synchroniseren van de status, het activeren van updates of het controleren van het gedrag van de Swing-app vanuit de webinterface.

Bijvoorbeeld, als uw Swing-app een aangepaste actiehandler heeft voor het verversen van gegevens:

```java
// Trigger een verversing in de Swing-toepassing vanuit webforJ
connector.performAction("refresh");
```

U kunt ook gegevens samen met de actie verzenden. De Swing-app ontvangt dit via de Webswing API-integratie:

```java
// Stuur een commando met gegevens vanuit webforJ
connector.performAction("selectRecord", "12345");

// Stuur binaire gegevens
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

De actienamen en verwachte gegevensformaten zijn gedefinieerd door de implementatie van uw Swing-app.

## Evenementen van Swing ontvangen {#receiving-events-from-swing}

De connector genereert drie soorten evenementen die uw webforJ-app informeren over de status en acties van de Swing-app.

### Levenscyclus evenementen {#lifecycle-events}

Het **initialiseer evenement** wordt geactiveerd wanneer de Webswing-verbinding is tot stand gebracht en klaar is voor communicatie:

```java
connector.onInitialize(event -> {
  // Verbinding tot stand gebracht
  connector.getInstanceId().ifPresent(id ->
      console.log("Verbonden met Webswing-instantie: " + id)
  );
});
```

Het **start evenement** wordt geactiveerd wanneer de Swing-app volledig is geladen en actief is:

```java
connector.onStart(event -> {
  // Swing-toepassing is nu zichtbaar en interactief
  console.log("Toepassing klaar voor gebruikersinteractie");
});
```

### Aangepaste actie evenementen {#custom-action-events}

Wanneer uw Swing-app aangepaste acties terugstuurt naar de webinterface met behulp van de [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api), worden deze ontvangen als actie-evenementen:

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Verwerk de update-melding
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // Binaire gegevens
            saveFile(fileData);
        });
        break;
  }
});
```
