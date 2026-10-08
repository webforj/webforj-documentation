---
title: Mit dem MCP-Client arbeiten
sidebar_position: 25
description: Connect a rendered webforJ view to its MCP client.
_i18n_hash: 082797b568bd8f308b625306c524d7ef
---
Eine MCP-App muss nicht jede Interaktion innerhalb ihrer eingebetteten Ansicht halten. Sie kann Informationen an das Gespräch senden, das Modell informieren, während der Benutzer die UI ändert, oder den Client bitten, etwas außerhalb des Rahmens zu bearbeiten.

Der gleiche Pfad kann auch in einem normalen Browser geöffnet werden. Beginne jede Client-Interaktion, indem du überprüfst, ob ein MCP-Host vorhanden ist.

## Führe das Gespräch aus der Ansicht fort {#send-a-message}

Betrachten wir eine Inventar-App, in der der Benutzer ein Lagerhaus auswählt und die KI dann bittet, den Bestand zu überprüfen. Der Button kann diese Anfrage als nächste Benutzer-Nachricht senden:

```java
Paragraph warehouse = new Paragraph("Lagerhaus: BER");
Button review = new Button("Bestand überprüfen");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Überprüfen Sie den aktuellen Bestand für " + warehouse.getText())));
```

`McpHost.ifPresent` führt das Callback nur aus, wenn die Ansicht mit einem MCP-Client verbunden ist. In einem normalen Browser hat der Button keine hostseitige Wirkung.

## Halte das Modell informiert {#update-model-context}

Nicht jede UI-Änderung sollte eine weitere Nachricht erzeugen. Wenn das ausgewählte Lagerhaus oder die Filter geändert werden, kann die App den Kontext ersetzen, den sie zum Modell beiträgt:

```java
McpHost host = McpHost.getCurrent();
if (host != null) {
  PendingResult<Void> result = host.updateModelContext(
      Map.of("warehouse", warehouse.getText(), "source", "inventory-app"));

  result.exceptionally(error -> {
    warehouse.setText("Teilen fehlgeschlagen: " + error.getMessage());
    return null;
  });
}
```

Der aktualisierte Zustand wird späteren Modellantworten zur Verfügung stehen, ohne eine sichtbare Nachricht zum Gespräch hinzuzufügen. Host-Aufrufe sind asynchron und geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, sodass der Abschluss oder das Scheitern behandelt werden kann, ohne den webforJ UI-Thread zu blockieren.

## Verlasse die eingebettete Ansicht {#leave-the-view}

Einige Arbeiten gehören außerhalb des App-Rahmens. Verwende `openLink`, wenn der Benutzer auf einer externen Seite fortfahren muss. Verwende `requestDisplayMode`, wenn der aktuelle Inhalt eine andere Darstellung benötigt, z. B. Vollbild für eine detaillierte Tabelle. Der Client entscheidet, ob er eine der beiden Anfragen erfüllen kann.

:::tip[Halte das Browser-Erlebnis vollständig]

Betrachte die Host-Integration als Verbesserung. Der Pfad sollte auch nützlich bleiben, wenn er in einem Browser ausgeführt wird oder wenn der verbundene Client eine angeforderte Fähigkeit nicht unterstützt.
:::

## Folge den Änderungen aus dem Gespräch {#host-events}

Der Client kann weiterhin mit der App arbeiten, nachdem sie gerendert wurde. Zum Beispiel kann die Ansicht einen Ladezustand aufheben, wenn ein Tool-Aufruf abgebrochen wird, und den erläuternden Text aktualisieren, wenn sich der Gesprächskontext ändert:

```java
McpHost.ifPresent(host -> {
  host.onToolCancelled(event ->
      warehouse.setText("Die Bestellanfrage wurde abgebrochen."));
  host.onHostContextChanged(event ->
      warehouse.setText("Der Gesprächskontext hat sich geändert."));
});
```

Registriere nur die Listener, die die Ansicht benötigt, und gehe nicht davon aus, dass jeder Client jedes Ereignis sendet. Siehe die `McpHost` Javadocs für die verfügbaren Anfragen, Ereignisse, Payloads und Methodensignaturen.
