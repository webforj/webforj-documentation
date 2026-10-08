---
title: Ajouter des outils pour une vue ouverte
sidebar_position: 20
description: Add tools that work with an MCP App already open in the current conversation.
_i18n_hash: 0ad6819ba9550e2ffd2372c09b91a746
---
Une application MCP peut publier des outils en plus de l'outil qui ouvre sa vue. Utilisez une action pour une opération distincte avec sa propre entrée. Implémentez l'observateur de mise à jour lorsque l'application a besoin d'un outil `inventory_update` avec la même entrée que son outil d'ouverture.

Ces outils n'ouvrent pas l'application. Un appel est dirigé vers la vue `inventory` rendue associée à la même session MCP. Si cette vue n'est pas ouverte, l'appel renvoie une erreur qui dirige le client à appeler d'abord `inventory`.

## Publier une action {#publish-an-action}

Ajoutez `@McpAppAction` à une méthode de vue. L'annotation publie un autre outil MCP ; la méthode contient l'opération qui s'exécute lorsque l'outil est appelé.

```java
@McpAppAction(description = "Actualise les niveaux de stock pour l'entrepôt ouvert.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - actualisé");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}
```

Pour une application nommée `inventory`, le nom de la méthode `refreshStock` produit le nom de l'outil `inventory_refresh_stock`. Définissez `name` sur `@McpAppAction` pour choisir explicitement la partie après `inventory_`. Chaque action doit avoir une description non vide.

Une méthode d'action peut n'avoir aucun paramètre d'entrée ou un paramètre d'entrée d'objet. Les propriétés de l'objet deviennent le schéma d'entrée de l'outil. Son résultat est renvoyé selon le type de retour de la méthode :

- `CallToolResult` est renvoyé directement.
- Toute autre valeur non-`void` devient un contenu structuré.
- Une méthode `void` renvoie un message de complétion.

:::info[La vue doit être ouverte]

L'action apparaît dans la liste des outils MCP même lorsque l'application n'est pas ouverte, mais son appel réussit uniquement pendant que l'application correspondante est rendue dans la même session MCP.
:::

Les actions peuvent également être déclarées dans une classe répertoriée par `@McpApp(actions = InventoryActions.class)`. Une action dans cette classe doit accepter la `InventoryView` rendue comme paramètre, en plus de son paramètre d'entrée d'objet optionnel.

## Publier l'outil de mise à jour {#publish-the-update-tool}

Implémentez `McpAppUpdateObserver` pour publier un outil de mise à jour pour l'application. Pour une application nommée `inventory`, webforJ publie `inventory_update`. Son schéma d'entrée est le même schéma utilisé par `inventory`.

```java
public class InventoryView extends Composite<FlexLayout>
    implements McpAppUpdateObserver {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @Override
  public CallToolResult onMcpAppUpdate(McpAppUpdateEvent event) {
    String warehouseCode = event.getArguments().path("warehouseCode").asString();
    warehouse.setText("Entrepôt : " + warehouseCode);
    return CallToolResult.builder()
        .addTextContent("Entrepôt d'inventaire mis à jour.")
        .build();
  }
}
```

Lorsque `inventory_update` est appelé, webforJ passe ses arguments à `onMcpAppUpdate` sur la `InventoryView` rendue. Le rappel décide comment utiliser ces arguments et renvoie le résultat de l'outil. webforJ n'applique pas les valeurs aux composants automatiquement.

L'outil de mise à jour n'a pas de métadonnées de ressource UI. L'appel ne génère pas la route ni ne rend une autre vue.

:::tip[Choisissez par l'entrée de l'outil]

Utilisez une action pour une opération distincte avec son propre schéma d'entrée. Utilisez l'observateur de mise à jour pour le seul outil `<app-name>_update` lorsque son entrée doit correspondre à l'outil d'ouverture. Une vue peut utiliser les deux.
:::

[L'interaction avec l'hôte](./host-interaction) couvre les requêtes que la vue rendue envoie à l'hôte MCP.
