---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: a69f444ce2e5a9dea37304d466f4e6ac
---
Der folgende Abschnitt behandelt verschiedene Leistungsqualitäten und Best Practices für webforJ sowie Implementierungsdetails für das Framework.

Beim Erstellen einer App in webforJ arbeiten der Client und der Server zusammen, um Daten zu manipulieren. Dies kann in die breiten Kategorien unterteilt werden:

## 1. Server zu Client {#1-server-to-client}

webforJ-Methoden wie `setText()` fallen in diese Kategorie. Eine webforJ-App, die auf dem Server läuft, sendet Daten an den Client, ohne auf eine Antwort zu warten. webforJ optimiert automatisch Batches von Operationen in dieser Kategorie, um die Leistung zu verbessern.

## 2. Client zu Server {#2-client-to-server}

Diese Kategorie umfasst Ereignistrafik, wie die `Button.onClick()`-Methode. In der Regel sendet der Client Ereignisse an den Server, ohne auf eine Antwort zu warten. Das Ereignisobjekt enthält typischerweise zusätzliche Parameter, die sich auf das Ereignis beziehen, wie den Hashcode. Da diese Informationen als Teil der Übermittlung des Ereignisses an den Server geliefert werden, sind sie sofort verfügbar, sobald das Ereignis empfangen wird.

## 3. Server zu Client zu Server (Hin- und Rückweg) {#3-server-to-client-to-server-round-trip}

Hin- und Rückreisen werden durchgeführt, wenn die App den Client nach bestimmten dynamischen Informationen fragt, die nicht auf dem Server zwischengespeichert werden können. Methoden wie `Label.getText()` und `Checkbox.isChecked()` fallen in diese Kategorie. Wenn eine webforJ-App eine Zeile wie `String title = myLabel.getText()` ausführt, kommt sie vollständig zum Stillstand, während der Server diese Anfrage an den Client sendet und dann auf die Antwort des Clients wartet.

Wenn die App mehrere Nachrichten an den Client sendet, die keine Antwort erfordern (Kategorie 1), gefolgt von einer einzigen Nachricht, die eine Hin- und Rückreise erfordert (Kategorie 3), muss die App warten, bis der Client alle ausstehenden Nachrichten bearbeitet hat, um dann auf die letzte Nachricht zu antworten, die eine Antwort erfordert. In einigen Fällen kann dies eine Verzögerung hinzufügen. Wenn diese Hin- und Rückreise nicht eingeführt worden wäre, hätte der Client weiterhin an der Verarbeitung dieser Rückstands Nachrichten arbeiten können, während die auf dem Server ausgeführte App neue Aufgaben hätte übernehmen können.

## Leistung verbessern {#improve-performance}

Es ist möglich, die Reaktionsfähigkeit erheblich zu verbessern, indem man Hin- und Rückreisen aus Kategorie drei so weit wie möglich vermeidet. Zum Beispiel, indem man die onSelect-Funktionalität des ComboBox von diesem:

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Geht zum Client
  int selected = component.getSelectedIndex();
}
```

zu folgendem ändert:

```java
private void comboBoxSelect(ListSelectEvent ev){
  // Wert aus dem Ereignis abrufen
  int selected = ev.getSelectedIndex();
}
```

Im ersten Snippet verursacht das Ausführen von `ComboBox.getSelectedIndex()` auf dem Component eine Hin- und Rückreise zurück zum Client, was eine Verzögerung einführt. In der zweiten Version wird mit der Methode `ListSelectEvent.getSelectedIndex()` des Ereignisses der Wert abgerufen, der Teil des ursprünglichen Ereignisses ist, das an den Server übermittelt wurde.

## Caching {#caching}

webforJ optimiert die Leistung weiter durch die Nutzung von Caching. Im Allgemeinen gibt es in diesem Kontext zwei Arten von Daten: Daten, die der Benutzer direkt ändern kann, und Daten, die vom Benutzer nicht geändert werden können. Im ersten Fall, wenn Informationen abgerufen werden, mit denen Benutzer direkt interagieren, ist es notwendig, den Server nach diesen Informationen abzufragen.

Informationen, die vom Benutzer jedoch nicht geändert werden können, können zwischengespeichert werden, um zusätzliche Leistungseinbußen zu vermeiden. Dies stellt sicher, dass eine Hin- und Rückreise nicht unnötig gemacht werden muss, was eine effizientere Benutzererfahrung bietet. webforJ optimiert Apps auf diese Weise, um eine optimale Leistung sicherzustellen.

## Ladezeit {#loading-time}

Wenn der Benutzer eine webforJ-App startet, lädt sie nur einen winzigen Teil (nur etwa 2,5 kB gzip) an JavaScript, um die Sitzung zu starten. Danach werden dynamisch einzelne Nachrichten oder Teile von JavaScript nach Bedarf heruntergeladen, während die App die entsprechenden Funktionen verwendet. Beispielsweise sendet der Server dem Client das notwendige JavaScript, um einmal einen webforJ `Button` zu erstellen, wenn die App ihre erste `Button`-Komponente erstellt. Dies führt zu messbaren Verbesserungen der Anfangsladezeit, was zu einer besseren Benutzererfahrung führt.
