---
title: Communication
sidebar_position: 3
_i18n_hash: 06bf57e08ee82a4970539b73215c1540
---
Le `WebswingConnector` fournit une communication bidirectionnelle entre votre application webforJ et l'application Swing intégrée. Cela vous permet d'envoyer des commandes à l'application Swing et de recevoir des notifications lorsque des événements se produisent à l'intérieur.

## Envoi d'actions à Swing {#sending-actions-to-swing}

La méthode `performAction()` permet à votre application webforJ de déclencher des fonctionnalités dans l'application Swing. Cela est utile pour synchroniser l'état, déclencher des mises à jour ou contrôler le comportement de l'application Swing depuis l'interface web.

Par exemple, si votre application Swing a un gestionnaire d'action personnalisé pour rafraîchir les données :

```java
// Déclencher un rafraîchissement dans l'application Swing depuis webforJ
connector.performAction("refresh");
```

Vous pouvez également envoyer des données avec l'action. L'application Swing reçoit cela via son intégration API Webswing :

```java
// Envoyer une commande avec des données depuis webforJ
connector.performAction("selectRecord", "12345");

// Envoyer des données binaires
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

Les noms d'action et les formats de données attendus sont définis par l'implémentation de votre application Swing.

## Réception d'événements de Swing {#receiving-events-from-swing}

Le connecteur déclenche trois types d'événements qui notifient votre application webforJ sur l'état et les actions de l'application Swing.

### Événements de cycle de vie {#lifecycle-events}

L'**événement d'initialisation** se déclenche lorsque la connexion Webswing est établie et prête pour la communication :

```java
connector.onInitialize(event -> {
  // Connexion établie
  connector.getInstanceId().ifPresent(id ->
      console.log("Connecté à l'instance Webswing : " + id)
  );
});
```

L'**événement de démarrage** se déclenche lorsque l'application Swing a complètement chargé et est en cours d'exécution :

```java
connector.onStart(event -> {
  // L'application Swing est maintenant visible et interactive
  console.log("Application prête pour l'interaction utilisateur");
});
```

### Événements d'action personnalisés {#custom-action-events}

Lorsque votre application Swing envoie des actions personnalisées vers l'interface web en utilisant l'[API Java Webswing](https://www.webswing.org/docs/25.1/integrate/api), celles-ci sont reçues en tant qu'événements d'action :

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Gérer la notification de mise à jour
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // Données binaires
            saveFile(fileData);
        });
        break;
  }
});
```
