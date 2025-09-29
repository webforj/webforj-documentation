---
title: Communication
sidebar_position: 3
_i18n_hash: 4a12006d21bb2a0bd6e82f2f0ff8fa78
---
Le `WebswingConnector` fournit une communication bidirectionnelle entre votre application webforJ et l'application Swing intégrée. Cela vous permet d'envoyer des commandes à l'application Swing et de recevoir des notifications lorsque des événements se produisent à l'intérieur.

## Envoi d'actions à Swing

La méthode `performAction()` permet à votre application webforJ de déclencher des fonctionnalités dans l'application Swing. Ceci est utile pour synchroniser l'état, déclencher des mises à jour ou contrôler le comportement de l'application Swing depuis l'interface web.

Par exemple, si votre application Swing a un gestionnaire d'action personnalisé pour actualiser les données :

```java
// Déclencher une actualisation dans l'application Swing depuis webforJ
connector.performAction("refresh");
```

Vous pouvez également envoyer des données avec l'action. L'application Swing reçoit cela via son intégration avec l'API Webswing :

```java
// Envoyer une commande avec des données depuis webforJ
connector.performAction("selectRecord", "12345");

// Envoyer des données binaires
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

Les noms d'actions et les formats de données attendus sont définis par l'implémentation de votre application Swing.

## Réception d'événements de Swing

Le connecteur génère trois types d'événements qui notifient votre application webforJ de l'état et des actions de l'application Swing.

### Événements de cycle de vie

L'**événement d'initialisation** se déclenche lorsque la connexion Webswing est établie et prête pour la communication :

```java
connector.onInitialize(event -> {
  // Connexion établie
  connector.getInstanceId().ifPresent(id ->
      console.log("Connecté à l'instance Webswing : " + id)
  );
});
```

L'**événement de démarrage** se déclenche lorsque l'application Swing est complètement chargée et en cours d'exécution :

```java
connector.onStart(event -> {
  // L'application Swing est maintenant visible et interactive
  console.log("Application prête pour l'interaction utilisateur");
});
```

### Événements d'actions personnalisées

Lorsque votre application Swing envoie des actions personnalisées à l'interface web en utilisant l'[API Java Webswing](https://www.webswing.org/docs/25.1/integrate/api), celles-ci sont reçues sous forme d'événements d'action :

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
