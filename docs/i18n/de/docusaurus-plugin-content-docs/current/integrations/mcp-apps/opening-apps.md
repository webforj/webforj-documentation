---
title: Open a view with input
sidebar_position: 15
description: >-
  Accept structured opening input in a routed MCP App and choose its requested
  display mode.
_i18n_hash: 158831b08974dd001c1322c38213e331
---
Das Öffnen von Eingaben ermöglicht es der KI, den Anfangszustand einer Ansicht auszuwählen. Zum Beispiel kann eine Inventaranwendung einen Lagercode akzeptieren, wenn der Kunde sie öffnet, und diesen Wert anwenden, nachdem die Route gerendert wurde.

## Eingabe beschreiben {#describe-the-input}

Verwenden Sie einen Objekttyp für die Tool-Argumente. Jackson-Anmerkungen fügen die Details hinzu, die der Client verwendet, um den Aufruf zu erstellen und zu validieren.

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Lagercode zur Anzeige")
    String warehouseCode) {
}
```

Das generierte Schema markiert `warehouseCode` als erforderlich und enthält seine Beschreibung. Klare Eigenschaftsbeschreibungen helfen der KI, die beabsichtigten Werte zu liefern.

## Eingabe nach dem Öffnen der Ansicht anwenden {#apply-opening-input}

Fügen Sie eine `@McpAppInput`-Methode zur gerouteten Ansicht hinzu. Sie muss einen Objektparameter akzeptieren.

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
    description = "Zeigt das aktuelle Inventar für ein Lager an.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("Lager: " + input.warehouseCode());
  }
}
```

Der Client erhält das generierte Schema für `inventory`. Wenn er das Tool aufruft, rendert webforJ `/inventory` und ruft anschließend `applyOpeningInput` auf dieser Ansichtinstanz auf.

:::tip[Halten Sie Toolnamen stabil]

Jedes `@McpApp` benötigt eine nicht leere Beschreibung. Wenn `name` weggelassen wird, leitet webforJ den Toolnamen von der Route ab: `/inventory` wird zu `inventory`, `/sales/inventory` wird zu `sales_inventory`, und die Stammroute wird zu `app`. Setzen Sie `name`, wenn Integrationen einen stabilen Namen benötigen, der sich nicht mit der Route ändert.
:::

:::tip[Wählen Sie eine Eingabedeklaration]

`@McpAppInput` ist nicht die einzige Schemaquelle. Eine Ansicht kann stattdessen `input = InventoryInput.class` festlegen oder ein JSON-Schema-Dokument mit `inputSchema` auf `@McpApp` bereitstellen. Wählen Sie genau eine Form. Die Kombination wird während der Anwendungsentdeckung abgelehnt. Verwenden Sie `@McpAppInput`, wenn die Ansicht die Werte nach dem Rendern empfangen und anwenden muss.
:::

Die Eingabemethode kann auch in einer Klasse leben, die von `@McpApp(actions = InventoryActions.class)` aufgeführt ist. In diesem Fall muss sie die laufende `InventoryView` zusammen mit dem einen Eingabeobjekt akzeptieren. Deklarieren Sie nur eine `@McpAppInput`-Methode in der Ansicht und ihren aufgelisteten Klassen.

## Halten Sie die Eröffnungsroute navigierbar {#route-parameters}

Das generierte Eröffnungstool navigiert ohne Routenparameter. Eine Route mit erforderlichen Parametern, wie `/inventory/:warehouse`, kann nicht direkt exponiert werden. Verwenden Sie eine parameterfreie Route und Eröffnungseingaben oder erstellen Sie ein separates benutzerdefiniertes MCP-Tool, das die erforderlichen Routenparameter bereitstellt. Optionale Parameter, Wildcards und Layoutsegmente sind erlaubt, wenn der Router eine URL ohne Werte generieren kann.

## Anfrage nach einem Anzeigeformat {#display-mode}

`displayMode` fragt den Client, wie die Ansicht dargestellt werden soll. `INLINE` hält das Inventar neben dem Gespräch, `PIP` fordert Bild-in-Bild an und `FULLSCREEN` fordert die größte Präsentation an. `FULLSCREEN` ist das Standardformat von webforJ. Der Client kann einen anderen Modus wählen, basierend darauf, was er unterstützt.

[Aktionen und Aktualisierungen](./actions-updates) können dieselbe Ansicht nach dem Öffnen ändern.
