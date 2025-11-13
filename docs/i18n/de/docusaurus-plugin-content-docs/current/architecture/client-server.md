---
sidebar_position: 5
title: Client/Server-Interaktion
_i18n_hash: e5eafeb3f76c9a412d5a124f2eed2da8
---
Der folgende Abschnitt behandelt verschiedene Leistungsqualitäten und bewährte Praktiken für webforJ sowie Implementierungsdetails für das Framework.

Beim Erstellen einer Anwendung in webforJ arbeiten der Client und der Server zusammen, um Daten zwischen Client und Server zu verarbeiten, die in die folgenden breiten Kategorien unterteilt werden können:

## 1. Server zu Client {#1-server-to-client}

webforJ-Methoden wie `setText()` fallen in diese Kategorie. Eine webforJ-Anwendung, die auf dem Server ausgeführt wird, sendet Daten an den Client, ohne auf eine Antwort zu warten. webforJ optimiert automatisch Batch-Operationen in dieser Kategorie, um die Leistung zu verbessern.

## 2. Client zu Server {#2-client-to-server}

Diese Kategorie umfasst Ereignisverkehr, wie eine `Button.onClick()`-Methode. In den meisten Fällen sendet der Client Ereignisse an den Server, ohne auf eine Antwort zu warten. Das Ereignisobjekt enthält typischerweise zusätzliche Parameter, die sich auf das Ereignis beziehen, wie den Hashcode. Da diese Informationen als Teil des Vorgangs zur Übermittlung des Ereignisses an den Server übermittelt werden, sind sie sofort verfügbar, sobald das Ereignis empfangen wird.

## 3. Server zu Client zu Server (Hin- und Rückreise) {#3-server-to-client-to-server-round-trip}

Hin- und Rückreisen werden durchgeführt, wenn die Anwendung den Client nach dynamischen Informationen abfragt, die nicht auf dem Server zwischengespeichert werden können. Methoden wie `Label.getText()` und `Checkbox.isChecked()` fallen in diese Kategorie. Wenn eine webforJ-Anwendung eine Zeile wie `String title = myLabel.getText()` ausführt, kommt sie vollständig zum Stillstand, während der Server diese Anfrage an den Client sendet und dann auf die Antwort des Clients wartet.

Wenn die Anwendung mehrere Nachrichten an den Client sendet, die keine Antwort erfordern (Kategorie 1), gefolgt von einer einzigen Nachricht, die eine Hin- und Rückreise erfordert (Kategorie 3), muss die Anwendung warten, bis der Client alle ausstehenden Nachrichten verarbeitet hat, und dann auf die letzte Nachricht, die eine Antwort erfordert, reagieren. In einigen Fällen kann dies eine Verzögerung verursachen. Hätte diese Hin- und Rückreise nicht stattgefunden, hätte der Client weiterhin an der Verarbeitung dieser Rückstände arbeiten können, während die auf dem Server ausgeführte Anwendung neue Arbeiten fortsetzte.

## Leistung verbessern {#improve-performance}

Es ist möglich, die Reaktionsfähigkeit der Anwendung erheblich zu verbessern, indem man Hin- und Rückreisen der dritten Kategorie so weit wie möglich vermeidet. Zum Beispiel kann die Funktionalität `onSelect` des ComboBox von diesem:

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // Geht zum Client
    int selected = component.getSelectedIndex();
}
```

auf Folgendes geändert werden:

```java
private void comboBoxSelect(ListSelectEvent ev){
    //Werte aus dem Ereignis erhalten
    int selected = ev.getSelectedIndex();
}
```

Im ersten Snippet erzwingt die Ausführung von `ComboBox.getSelectedIndex()` auf der Komponente eine Hin- und Rückreise zurück zum Client, was eine Verzögerung verursacht. In der zweiten Version wird durch die Verwendung der Methode `ListSelectEvent.getSelectedIndex()` des Ereignisses der Wert abgerufen, der als Teil des ursprünglichen Ereignisses an den Server übermittelt wurde.

## Caching {#caching}

webforJ optimiert die Leistung weiter durch die Nutzung von Caching. Im Allgemeinen gibt es in diesem Kontext zwei Arten von Daten: Daten, die der Benutzer direkt ändern kann, und Daten, die vom Benutzer nicht geändert werden können. Im ersten Fall, wenn Informationen abgerufen werden, mit denen Benutzer direkt interagieren, ist es notwendig, den Server nach diesen Informationen abzufragen.

Informationen, die vom Benutzer nicht geändert werden können, können jedoch zwischengespeichert werden, um zusätzliche Leistungseinbußen zu vermeiden. Dies stellt sicher, dass keine unnötige Hin- und Rückreise erforderlich ist, was eine effizientere Benutzererfahrung bietet. webforJ optimiert Anwendungen auf diese Weise, um eine optimale Leistung sicherzustellen.

## Ladezeit {#loading-time}

Wenn der Benutzer eine webforJ-App startet, lädt sie nur ein kleines Stück (nur etwa 2,5 kB gzip) JavaScript, um die Sitzung zu bootstrappen. Danach werden dynamisch einzelne Nachrichten oder Teile von JavaScript bedarfsgerecht heruntergeladen, während die Anwendung die entsprechende Funktionalität nutzt. Beispielsweise sendet der Server dem Client das JavaScript, das zur Erstellung eines webforJ `Button` erforderlich ist, nur einmal — wenn die Anwendung ihre erste `Button`-Komponente erstellt. Dies führt zu einer messbaren Verbesserung der initialen Ladezeit, was zu einer besseren Benutzererfahrung führt.
