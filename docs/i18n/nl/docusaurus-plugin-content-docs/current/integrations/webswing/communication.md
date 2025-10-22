---
title: Communication
sidebar_position: 3
_i18n_hash: 4a12006d21bb2a0bd6e82f2f0ff8fa78
---
De `WebswingConnector` biedt bidirectionele communicatie tussen uw webforJ-app en de ingebedde Swing-app. Hierdoor kunt u opdrachten naar de Swing-app verzenden en meldingen ontvangen wanneer zich gebeurtenissen binnen deze app voordoen.

## Acties naar Swing verzenden

De `performAction()`-methode stelt uw webforJ-app in staat om functionaliteit in de Swing-app te activeren. Dit is nuttig voor het synchroniseren van de status, het activeren van updates of het controleren van het gedrag van de Swing-app vanuit de webinterface.

Bijvoorbeeld, als uw Swing-app een aangepaste actiehandler heeft voor het vernieuwen van gegevens:

```java
// Trigger een verversing in de Swing-applicatie vanuit webforJ
connector.performAction("refresh");
```

U kunt ook gegevens samen met de actie verzenden. De Swing-app ontvangt deze via de Webswing API-integratie:

```java
// Stuur een opdracht met gegevens vanuit webforJ
connector.performAction("selectRecord", "12345");

// Stuur binaire gegevens
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

De actienamen en verwachte gegevensformaten zijn gedefinieerd door de implementatie van uw Swing-app.

## Evenementen van Swing ontvangen

De connector genereert drie soorten evenementen die uw webforJ-app op de hoogte stellen van de status en acties van de Swing-app.

### Levenscyclus evenementen

Het **initialize evenement** wordt geactiveerd wanneer de Webswing-verbinding is tot stand gebracht en klaar is voor communicatie:

```java
connector.onInitialize(event -> {
  // Verbinding tot stand gebracht
  connector.getInstanceId().ifPresent(id ->
      console.log("Verbonden met Webswing-instantie: " + id)
  );
});
```

Het **start evenement** wordt geactiveerd wanneer de Swing-app volledig is geladen en draait:

```java
connector.onStart(event -> {
  // Swing-applicatie is nu zichtbaar en interactief
  console.log("Applicatie klaar voor gebruikersinteractie");
});
```

### Aangepaste actie-evenementen

Wanneer uw Swing-app aangepaste acties terug naar de webinterface verzendt met behulp van de [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api), worden deze ontvangen als actie-evenementen:

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Behandel de update-melding
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
