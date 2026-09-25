---
title: Een view met invoer openen
sidebar_position: 15
description: >-
  Accept structured opening input in a routed MCP App and choose its requested
  display mode.
_i18n_hash: 158831b08974dd001c1322c38213e331
---
Opening input stelt de AI in staat om de initiële staat van een weergave te kiezen. Bijvoorbeeld, een inventaris-app kan een magazijncode accepteren wanneer de client deze opent en die waarde toepassen nadat de route is weergegeven.

## Beschrijf de invoer {#describe-the-input}

Gebruik één objecttype voor de argumenten van de tool. Jackson-annotaties voegen de details toe die de client gebruikt om de aanroep te bouwen en te valideren.

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Magazijncode om weer te geven")
    String warehouseCode) {
}
```

Het gegenereerde schema markeert `warehouseCode` als verplicht en bevat de beschrijving ervan. Duidelijke eigenschappenselecties helpen de AI om de bedoelde waarden te leveren.

## Pas invoer toe na het openen van de weergave {#apply-opening-input}

Voeg één `@McpAppInput` methode toe aan de gerouteerde weergave. Deze moet één objectparameter accepteren.

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
    description = "Toont de huidige inventaris voor een magazijn.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("Magazijn: " + input.warehouseCode());
  }
}
```

De client ontvangt het gegenereerde schema op `inventory`. Wanneer het de tool aanroept, rendert webforJ `/inventory` en roept vervolgens `applyOpeningInput` aan op die weergave-instantie.

:::tip[Houd toolnamen stabiel]

Elke `@McpApp` heeft een niet-lege beschrijving nodig. Als `name` wordt weggelaten, derivet webforJ de toolnaam van de route: `/inventory` wordt `inventory`, `/sales/inventory` wordt `sales_inventory`, en de rootroute wordt `app`. Stel `name` in wanneer integraties een stabiele naam nodig hebben die niet verandert met de route.
:::

:::tip[Kies één invoerdeclaratie]

`@McpAppInput` is niet de enige schema-bron. Een weergave kan in plaats daarvan `input = InventoryInput.class` instellen of een JSON Schema-document met `inputSchema` op `@McpApp` verstrekken. Kies exact één vorm. Combinaties worden afgewezen tijdens de app-ontdekking. Gebruik `@McpAppInput` wanneer de weergave de waarden moet ontvangen en toepassen na het weergeven.
:::

De invoermethode kan ook leven in een klasse die wordt vermeld met `@McpApp(actions = InventoryActions.class)`. In dat geval moet het de actieve `InventoryView` accepteren samen met het invoerobject. Verklaar slechts één `@McpAppInput` methode over de weergave en zijn vermelde klassen.

## Houd de openingsroute navigeerbaar {#route-parameters}

De gegenereerde openingstool navigeert zonder routeparameters. Een route met vereiste parameters, zoals `/inventory/:warehouse`, kan niet direct worden blootgesteld. Gebruik een parameterloze route en openingsinvoer, of creëer een aparte aangepaste MCP-tool die de vereiste routeparameters levert. Optionele parameters, wildcards en lay-outsegmenten zijn toegestaan wanneer de router een URL kan genereren zonder waarden.

## Vraag een weergavemodus aan {#display-mode}

`displayMode` vraagt de client hoe de weergave moet worden gepresenteerd. `INLINE` houdt de inventaris naast het gesprek, `PIP` vraagt om picture-in-picture, en `FULLSCREEN` vraagt om de grootste presentatie. `FULLSCREEN` is de standaard van webforJ. De client kan een andere modus kiezen afhankelijk van wat het ondersteunt.

[Acties en updates](./actions-updates) kunnen dezelfde weergave wijzigen nadat deze is geopend.
