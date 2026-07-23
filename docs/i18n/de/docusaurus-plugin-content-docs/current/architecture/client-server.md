---
sidebar_position: 5
title: Client/Server Interaction
description: >-
  Understand how webforJ batches server-to-client calls, avoids costly round
  trips, and uses caching and on-demand chunks for performance.
_i18n_hash: 893b34ce2601ff273d03ba4091b7bc51
---
Der folgende Abschnitt behandelt verschiedene Leistungsqualitäten und bewährte Verfahren für webforJ sowie Implementierungsdetails für das Framework.

Beim Erstellen einer App in webforJ arbeiten der Client und der Server zusammen, um Daten zwischen Client und Server zu manipulieren. Dies kann in die folgenden breiten Kategorien unterteilt werden:

## 1. Server zu Client {#1-server-to-client}

webforJ-Methoden wie `setText()` sind in dieser Kategorie enthalten. Eine webforJ-App, die auf dem Server läuft, sendet Daten an den Client, ohne auf eine Antwort zu warten. webforJ optimiert automatisch Batch-Vorgänge in dieser Kategorie, um die Leistung zu verbessern.

## 2. Client zu Server {#2-client-to-server}

Diese Kategorie umfasst Ereignisverkehr, wie die Methode `Button.onClick()`. In der Regel sendet der Client Ereignisse an den Server, ohne auf eine Antwort zu warten. Das Ereignisobjekt enthält normalerweise zusätzliche Parameter, die sich auf das Ereignis beziehen, wie den Hashcode. Da diese Informationen dem Server im Zuge der Übermittlung des Ereignisses zugestellt werden, stehen sie dem Programm sofort zur Verfügung, sobald das Ereignis empfangen wird.

## 3. Server zu Client zu Server (Hin- und Rückfahrt) {#3-server-to-client-to-server-round-trip}

Hin- und Rückfahrten werden durchgeführt, wenn die App den Client nach dynamischen Informationen fragt, die nicht auf dem Server zwischengespeichert werden können. Methoden wie `Label.getText()` und `Checkbox.isChecked()` fallen in diese Kategorie. Wenn eine webforJ-App eine Zeile wie `String title = myLabel.getText()` ausführt, kommt sie zum Stillstand, während der Server diese Anfrage an den Client sendet und dann auf die Rückmeldung des Clients wartet.

Wenn die App mehrere Nachrichten an den Client sendet, die keine Antwort erfordern (Kategorie 1), gefolgt von einer einzelnen Nachricht, die eine Hin- und Rückfahrt erfordert (Kategorie 3), muss die App warten, bis der Client alle ausstehenden Nachrichten verarbeitet hat, und dann auf die letzte Nachricht antworten, die eine Antwort erfordert. In einigen Fällen kann dies eine Verzögerung verursachen. Hätte diese Hin- und Rückfahrt nicht stattgefunden, hätte der Client weiterhin arbeiten können, während die auf dem Server ausgeführte App zu neuen Aufgaben übergegangen wäre.

## Leistung verbessern {#improve-performance}

Es ist möglich, die Reaktionsfähigkeit erheblich zu verbessern, indem man die Hin- und Rückfahrten der dritten Kategorie so weit wie möglich vermeidet. Zum Beispiel kann die Funktionalität `onSelect` des ComboBox von Folgendem geändert werden:

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Geht zum Client
  int selected = component.getSelectedIndex();
}
```

zu Folgendem:

```java
private void comboBoxSelect(ListSelectEvent ev){
  // Wert aus dem Ereignis abrufen
  int selected = ev.getSelectedIndex();
}
```

Im ersten Snippet zwingt `ComboBox.getSelectedIndex()`, das auf der Komponente durchgeführt wird, zu einer Hin- und Rückfahrt zurück zum Client, was eine Verzögerung einführt. In der zweiten Version wird mit der Methode `ListSelectEvent.getSelectedIndex()` des Ereignisses der Wert abgerufen, der im Rahmen des ursprünglichen Ereignisses an den Server übermittelt wurde.

## Caching {#caching}

webforJ optimiert die Leistung weiter durch die Nutzung von Caching. Im Allgemeinen gibt es in diesem Kontext zwei Arten von Daten: Daten, die der Benutzer direkt ändern kann, und Daten, die vom Benutzer nicht geändert werden können. Im ersten Fall ist es notwendig, den Server abzufragen, um die Informationen zu erhalten, mit denen die Benutzer direkt interagieren werden.

Allerdings können Informationen, die vom Benutzer nicht geändert werden können, zwischengespeichert werden, um zusätzliche Leistungseinbußen zu vermeiden. Dadurch wird sichergestellt, dass keine unnötige Hin- und Rückfahrt erforderlich ist, was ein effizienteres Benutzererlebnis bietet. webforJ optimiert Apps auf diese Weise, um eine optimale Leistung zu gewährleisten.

## Ladezeit {#loading-time}

Wenn der Benutzer eine webforJ-App startet, lädt sie
nur einen kleinen Teil (nur etwa 2,5 kB gzip) von JavaScript, um die Sitzung zu starten.
Danach lädt sie dynamisch einzelne Nachrichten oder Teile von
JavaScript nach Bedarf, während die App die entsprechende
Funktionalität nutzt. Zum Beispiel sendet der Server nur einmal, wenn die App ihre erste `Button`-Komponente erstellt, das für den Client erforderliche JavaScript, um einen webforJ `Button` zu erstellen. Dies führt zu messbaren Verbesserungen der initialen Ladezeit, was zu einer besseren Benutzererfahrung führt.
