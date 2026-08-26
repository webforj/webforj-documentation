---
title: Ouvrir une vue avec une entrée
sidebar_position: 15
description: >-
  Accept structured opening input in a routed MCP App and choose its requested
  display mode.
_i18n_hash: 158831b08974dd001c1322c38213e331
---
L'entrée d'ouverture permet à l'IA de choisir l'état initial d'une vue. Par exemple, une application d'inventaire peut accepter un code d'entrepôt lorsque le client l'ouvre et appliquer cette valeur après que la route soit rendue.

## Décrire l'entrée {#describe-the-input}

Utilisez un type d'objet pour les arguments de l'outil. Les annotations Jackson ajoutent les détails que le client utilise pour construire et valider l'appel.

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Code d'entrepôt à afficher")
    String warehouseCode) {
}
```

Le schéma généré marque `warehouseCode` comme requis et inclut sa description. Des descriptions de propriétés claires aident l'IA à fournir les valeurs prévues.

## Appliquer l'entrée après l'ouverture de la vue {#apply-opening-input}

Ajoutez une méthode `@McpAppInput` à la vue routée. Elle doit accepter un paramètre d'objet.

```java
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.mcp.McpAppDisplayMode;
import com.webforj.mcp.annotation.McpApp;
import com.webforj.mcp.annotation.McpAppInput;
import com.webforj.router.annotation.Route;

@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Montre l'inventaire actuel pour un entrepôt.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("Entrepôt : " + input.warehouseCode());
  }
}
```

Le client reçoit le schéma généré sur `inventory`. Lorsqu'il appelle l'outil, webforJ rend `/inventory` puis invoque `applyOpeningInput` sur cette instance de vue.

:::tip[Gardez les noms des outils stables]

Chaque `@McpApp` a besoin d'une description non vide. Si `name` est omis, webforJ dérive le nom de l'outil de la route : `/inventory` devient `inventory`, `/sales/inventory` devient `sales_inventory`, et la route racine devient `app`. Définissez `name` lorsque les intégrations ont besoin d'un nom stable qui ne changera pas avec la route.
:::

:::tip[Choisissez une déclaration d'entrée]

`@McpAppInput` n'est pas la seule source de schéma. Une vue peut au lieu de cela définir `input = InventoryInput.class` ou fournir un document JSON Schema avec `inputSchema` sur `@McpApp`. Choisissez exactement une forme. Les combiner est rejeté lors de la découverte de l'application. Utilisez `@McpAppInput` lorsque la vue doit recevoir et appliquer les valeurs après le rendu.
:::

La méthode d'entrée peut également vivre dans une classe listée par `@McpApp(actions = InventoryActions.class)`. Dans ce cas, elle doit accepter la `InventoryView` en cours ainsi que l'objet d'entrée. Déclarez uniquement une méthode `@McpAppInput` à travers la vue et ses classes listées.

## Gardez la route d'ouverture navigable {#route-parameters}

L'outil d'ouverture généré navigue sans paramètres de route. Une route avec des paramètres requis, tels que `/inventory/:warehouse`, ne peut pas être exposée directement. Utilisez une route sans paramètre et une entrée d'ouverture, ou créez un outil MCP personnalisé séparé qui fournit les paramètres de route requis. Les paramètres optionnels, les jokers, et les segments de mise en page sont autorisés lorsque le routeur peut générer une URL sans valeurs.

## Demander un mode d'affichage {#display-mode}

`displayMode` demande au client comment présenter la vue. `INLINE` garde l'inventaire à côté de la conversation, `PIP` demande une image dans l'image, et `FULLSCREEN` demande la plus grande présentation. `FULLSCREEN` est le défaut de webforJ. Le client peut choisir un mode différent en fonction de ce qu'il supporte.

[Actions et mises à jour](./actions-updates) peuvent changer la même vue après son ouverture.
