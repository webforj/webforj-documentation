---
title: Work with the MCP client
sidebar_position: 25
description: Connect a rendered webforJ view to its MCP client.
_i18n_hash: 082797b568bd8f308b625306c524d7ef
---
Une application MCP n'a pas besoin de garder chaque interaction à l'intérieur de sa vue intégrée. Elle peut envoyer des informations à la conversation, tenir le modèle informé lorsque l'utilisateur modifie l'interface, ou demander au client de gérer quelque chose en dehors du cadre.

La même route peut également s'ouvrir dans un navigateur normal. Commencez chaque interaction client en vérifiant si un hôte MCP est présent.

## Continuer la conversation depuis la vue {#send-a-message}

Considérez une application d'inventaire où l'utilisateur sélectionne un entrepôt et demande ensuite à l'IA de revoir son stock. Le bouton peut envoyer cette demande comme le prochain message de l'utilisateur :

```java
Paragraph warehouse = new Paragraph("Entrepôt : BER");
Button review = new Button("Revoir le stock");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Revoir le stock actuel pour " + warehouse.getText())));
```

`McpHost.ifPresent` exécute le callback uniquement lorsque la vue est connectée à un client MCP. Dans un navigateur normal, le bouton n'a pas d'effet côté hôte.

## Tenir le modèle informé {#update-model-context}

Chaque changement d'interface ne doit pas créer un nouveau message. Lorsque l'entrepôt sélectionné ou les filtres changent, l'application peut remplacer le contexte qu'elle contribue au modèle :

```java
McpHost host = McpHost.getCurrent();
if (host != null) {
  PendingResult<Void> result = host.updateModelContext(
      Map.of("warehouse", warehouse.getText(), "source", "inventory-app"));

  result.exceptionally(error -> {
    warehouse.setText("Échec du partage : " + error.getMessage());
    return null;
  });
}
```

L'état mis à jour devient disponible pour les réponses ultérieures du modèle sans ajouter un message visible à la conversation. Les appels hôtes sont asynchrones et renvoient un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, donc gérez l'achèvement ou l'échec sans bloquer le fil d'UI de webforJ.

## Quitter la vue intégrée {#leave-the-view}

Certaines tâches appartiennent à l'extérieur du cadre de l'application. Utilisez `openLink` lorsque l'utilisateur a besoin de continuer sur une page externe. Utilisez `requestDisplayMode` lorsque le contenu actuel nécessite une présentation différente, comme le mode plein écran pour un tableau détaillé. Le client décide s'il peut satisfaire l'une ou l'autre demande.

:::tip[Maintenir l'expérience de navigation complète]

Considérez l'intégration de l'hôte comme une amélioration. La route doit rester utile lorsqu'elle s'exécute dans un navigateur ou lorsque le client connecté ne prend pas en charge une capacité demandée.
:::

## Suivre les changements de la conversation {#host-events}

Le client peut continuer à travailler avec l'application après qu'elle a été rendue. Par exemple, la vue peut effacer un état de chargement lorsque l'appel d'outil est annulé et actualiser le texte explicatif lorsque le contexte de la conversation change :

```java
McpHost.ifPresent(host -> {
  host.onToolCancelled(event ->
      warehouse.setText("La demande d'inventaire a été annulée."));
  host.onHostContextChanged(event ->
      warehouse.setText("Le contexte de la conversation a changé."));
});
```

Enregistrez uniquement les écouteurs dont la vue a besoin et ne supposez pas que chaque client envoie chaque événement. Consultez les Javadocs de `McpHost` pour les demandes, événements, charges utiles et signatures de méthodes disponibles.
