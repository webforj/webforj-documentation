---
title: Add tools for an open view
sidebar_position: 20
description: Add tools that work with an MCP App already open in the current conversation.
_i18n_hash: 0ad6819ba9550e2ffd2372c09b91a746
---
Een MCP-app kan tools publiceren naast het tool dat zijn weergave opent. Gebruik een actie voor een aparte operatie met zijn eigen invoer. Implementeer de update-observer wanneer de app één `inventory_update` tool nodig heeft met dezelfde invoer als zijn openingsgereedschap.

Deze tools openen de app niet. Een oproep wordt doorgestuurd naar de weergegeven `inventory` weergave die is gekoppeld aan dezelfde MCP-sessie. Als die weergave niet open is, retourneert de oproep een foutmelding die de client aanraadt eerst `inventory` aan te roepen.

## Publiceer een actie {#publish-an-action}

Voeg `@McpAppAction` toe aan een weergavemethode. De annotatie publiceert een andere MCP-tool; de methode bevat de operatie die uitgevoerd wordt wanneer het gereedschap wordt aangeroepen.

```java
@McpAppAction(description = "Vernieuwt de voorraadniveaus voor het open magazijn.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - vernieuwd");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}
```

Voor een app genaamd `inventory` produceert de methodenaam `refreshStock` de toolnaam `inventory_refresh_stock`. Stel `name` in op `@McpAppAction` om expliciet het deel na `inventory_` te kiezen. Elke actie moet een niet-lege beschrijving hebben.

Een actiemethode kan geen invoerparameter of één objectinvoerveld hebben. De eigenschappen van het object worden het invoerschema van de tool. Het resultaat wordt geretourneerd volgens het retourtype van de methode:

- `CallToolResult` wordt direct geretourneerd.
- Elke andere niet-`void` waarde wordt gestructureerde inhoud.
- Een `void` methode retourneert een voltooiingsbericht.

:::info[De weergave moet open zijn]

De actie verschijnt in de lijst met MCP-tools, zelfs wanneer de app niet open is, maar de oproep slaagt alleen wanneer de bijbehorende app wordt weergegeven in dezelfde MCP-sessie.
:::

Acties kunnen ook worden gedeclareerd in een klasse die wordt vermeld door `@McpApp(actions = InventoryActions.class)`. Een actie in die klasse moet de weergegeven `InventoryView` als parameter accepteren, naast de optionele objectinvoer.

## Publiceer de update-tool {#publish-the-update-tool}

Implementeer `McpAppUpdateObserver` om één update-tool voor de app te publiceren. Voor een app genaamd `inventory` publiceert webforJ `inventory_update`. Het invoerschema is hetzelfde schema dat door `inventory` wordt gebruikt.

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
    warehouse.setText("Magazijn: " + warehouseCode);
    return CallToolResult.builder()
        .addTextContent("Voorraadmagazijn bijgewerkt.")
        .build();
  }
}
```

Wanneer `inventory_update` wordt aangeroepen, geeft webforJ zijn argumenten door aan `onMcpAppUpdate` op de weergegeven `InventoryView`. De callback beslist hoe deze argumenten te gebruiken en retourneert het toolresultaat. webforJ past de waarden niet automatisch op componenten toe.

De update-tool heeft geen UI-hulpmiddelmetadata. Het aanroepen ervan opent de route niet of rendert geen andere weergave.

:::tip[Kies op basis van toolinvoer]

Gebruik een actie voor een aparte operatie met zijn eigen invoerschema. Gebruik de update-observer voor de enkele `<app-name>_update` tool wanneer de invoer moet overeenkomen met het openingsgereedschap. Een weergave kan beide gebruiken.
:::

[Host-interactie](./host-interaction) behandelt verzoeken die de weergegeven weergave naar de MCP-host verzendt.
