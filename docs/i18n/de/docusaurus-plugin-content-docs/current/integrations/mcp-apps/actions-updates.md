---
title: Add tools for an open view
sidebar_position: 20
description: Add tools that work with an MCP App already open in the current conversation.
_i18n_hash: 0ad6819ba9550e2ffd2372c09b91a746
---
Eine MCP-App kann Werkzeuge zusätzlich zu dem Werkzeug veröffentlichen, das ihre Ansicht öffnet. Verwenden Sie eine Aktion für einen eigenständigen Vorgang mit eigener Eingabe. Implementieren Sie den Update-Beobachter, wenn die App ein `inventory_update` Werkzeug benötigt, das dieselbe Eingabe wie ihr öffnendes Werkzeug hat.

Diese Werkzeuge öffnen die App nicht. Ein Aufruf wird an die gerenderte `inventory` Ansicht weitergeleitet, die mit derselben MCP-Sitzung verbunden ist. Wenn diese Ansicht nicht geöffnet ist, wird ein Fehler zurückgegeben, der den Client anweist, zuerst `inventory` aufzurufen.

## Aktion veröffentlichen {#publish-an-action}

Fügen Sie `@McpAppAction` zu einer Methodenansicht hinzu. Die Annotation veröffentlicht ein weiteres MCP-Werkzeug; die Methode enthält den Betrieb, der ausgeführt wird, wenn das Werkzeug aufgerufen wird.

```java
@McpAppAction(description = "Aktualisiert die Bestandslevels für das geöffnete Lager.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - aktualisiert");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}
```

Für eine App namens `inventory` erzeugt der Methodenname `refreshStock` den Werkzeugnamen `inventory_refresh_stock`. Setzen Sie `name` auf `@McpAppAction`, um den Teil nach `inventory_` explizit auszuwählen. Jede Aktion muss eine nicht leere Beschreibung haben.

Eine Aktionsmethode kann kein Eingabeparameter oder einen Objekt-Eingabeparameter haben. Die Eigenschaften des Objekts werden zum Eingabeschema des Werkzeugs. Das Ergebnis wird entsprechend dem Rückgabetyp der Methode zurückgegeben:

- `CallToolResult` wird direkt zurückgegeben.
- Jeder andere nicht-`void` Wert wird zu strukturiertem Inhalt.
- Eine `void` Methode gibt eine Abschlussnachricht zurück.

:::info[Die Ansicht muss geöffnet sein]

Die Aktion erscheint in der MCP-Werkzeugliste, selbst wenn die App nicht geöffnet ist, aber ihr Aufruf gelingt nur, solange die übereinstimmende App in derselben MCP-Sitzung gerendert wird.
:::

Aktionen können auch in einer Klasse deklariert werden, die von `@McpApp(actions = InventoryActions.class)` aufgelistet wird. Eine Aktion in dieser Klasse muss die gerenderte `InventoryView` als Parameter akzeptieren, zusätzlich zu ihrem optionalen Objekt-Eingabeparameter.

## Aktualisierungswerkzeug veröffentlichen {#publish-the-update-tool}

Implementieren Sie `McpAppUpdateObserver`, um ein Aktualisierungswerkzeug für die App zu veröffentlichen. Für eine App namens `inventory` veröffentlicht webforJ `inventory_update`. Sein Eingabeschema ist dasselbe Schema, das von `inventory` verwendet wird.

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
    warehouse.setText("Lager: " + warehouseCode);
    return CallToolResult.builder()
        .addTextContent("Lagerbestand aktualisiert.")
        .build();
  }
}
```

Wenn `inventory_update` aufgerufen wird, übergibt webforJ seine Argumente an `onMcpAppUpdate` in der gerenderten `InventoryView`. Der Callback entscheidet, wie diese Argumente verwendet werden, und gibt das Werkzeugergebnis zurück. webforJ wendet die Werte nicht automatisch auf Komponenten an.

Das Aktualisierungswerkzeug hat keine UI-Ressourcenmetadaten. Ein Aufruf öffnet nicht die Route oder rendert eine andere Ansicht.

:::tip[Wählen Sie nach Werkzeug-Eingabe]

Verwenden Sie eine Aktion für einen separaten Vorgang mit seinem eigenen Eingabeschema. Verwenden Sie den Update-Beobachter für das einzelne `<app-name>_update` Werkzeug, wenn seine Eingabe dem öffnenden Werkzeug entsprechen muss. Eine Ansicht kann beide verwenden.
:::

[Host-Interaktion](./host-interaction) behandelt Anfragen, die die gerenderte Ansicht an den MCP-Host sendet.
