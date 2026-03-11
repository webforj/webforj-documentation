---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ae7a34d844eee10906ce2230f95a05cc
---
Der folgende Abschnitt behandelt verschiedene Leistungsqualitäten und bewährte Verfahren für webforJ sowie Implementierungsdetails für das Framework.

Beim Erstellen einer App in webforJ arbeiten der Client und der Server zusammen, um Daten zwischen Client und Server zu manipulieren, was in die breiten Kategorien unterteilt werden kann:

## 1. Server zu Client {#1-server-to-client}

webforJ-Methoden wie `setText()` fallen in diese Kategorie. Eine webforJ-App, die auf dem Server ausgeführt wird, sendet Daten an den Client, ohne auf eine Antwort zu warten. webforJ optimiert automatisch Gruppen von Operationen in dieser Kategorie, um die Leistung zu verbessern.

## 2. Client zu Server {#2-client-to-server}

Diese Kategorie umfasst Ereignisverkehr, wie z. B. eine `Button.onClick()`-Methode. In der Regel sendet der Client Ereignisse an den Server, ohne auf eine Antwort zu warten. Das Ereignisobjekt enthält typischerweise zusätzliche Parameter, die sich auf das Ereignis beziehen, wie z. B. den Hashcode. Da diese Informationen als Teil des Aktes der Übermittlung des Ereignisses an den Server geliefert werden, sind sie sofort verfügbar für das Programm, sobald das Ereignis empfangen wird.

## 3. Server zu Client zu Server (Hin- und Rückreise) {#3-server-to-client-to-server-round-trip}

Hin- und Rückreisen werden durchgeführt, wenn die App den Client nach dynamischen Informationen fragt, die nicht auf dem Server gecacht werden können. Methoden wie `Label.getText()` und `Checkbox.isChecked()` fallen in diese Kategorie. Wenn eine webforJ-App eine Zeile wie `String title = myLabel.getText()` ausführt, bleibt sie völlig stehen, während der Server diese Anfrage an den Client sendet und dann auf die Antwort des Clients wartet.

Wenn die App mehrere Nachrichten an den Client sendet, die keine Antwort erfordern (Kategorie 1), gefolgt von einer einzigen Nachricht, die eine Hin- und Rückreise erfordert (Kategorie 3), muss die App warten, bis der Client alle ausstehenden Nachrichten verarbeitet hat, bevor er auf die letzte Nachricht reagieren kann, die eine Antwort erfordert. In einigen Fällen kann dies zu einer Verzögerung führen. Wenn diese Hin- und Rückreise nicht eingeführt worden wäre, hätte der Client weiterhin an der Verarbeitung dieser Rückstaumeldungen arbeiten können, während die auf dem Server ausgeführte App mit neuer Arbeit fortgefahren wäre.

## Leistung verbessern {#improve-performance}

Es ist möglich, die Reaktionsgeschwindigkeit erheblich zu verbessern, indem die Hin- und Rückreisen der dritten Kategorie so weit wie möglich vermieden werden. Zum Beispiel könnte die Funktionalität `onSelect` des ComboBox von diesem:

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // Geht zum Client
    int selected = component.getSelectedIndex();
}
```

zu folgendem geändert werden:

```java
private void comboBoxSelect(ListSelectEvent ev){
    // Wert aus dem Ereignis erhalten
    int selected = ev.getSelectedIndex();
}
```

Im ersten Snippet zwingt das Ausführen von `ComboBox.getSelectedIndex()` auf der Komponente zu einer Hin- und Rückreise zum Client, was eine Verzögerung einführt. In der zweiten Version ruft die Verwendung der Methode `ListSelectEvent.getSelectedIndex()` des Ereignisses den Wert ab, der als Teil des ursprünglichen Ereignisses an den Server geliefert wurde.

## Caching {#caching}

webforJ optimiert die Leistung weiter durch die Nutzung von Caching. Im Allgemeinen existieren in diesem Kontext zwei Arten von Daten: Daten, die der Benutzer direkt ändern kann, und Daten, die der Benutzer nicht ändern kann. Im ersten Fall ist es notwendig, den Server nach Informationen zu fragen, mit denen Benutzer direkt interagieren.

Informationen, die vom Benutzer nicht geändert werden können, können jedoch zwischengespeichert werden, um zusätzliche Leistungsabfälle zu vermeiden. Dies gewährleistet, dass eine Hin- und Rückreise nicht unnötig durchgeführt werden muss, was zu einer effizienteren Benutzererfahrung führt. webforJ optimiert Apps auf diese Weise, um eine optimale Leistung sicherzustellen.

## Ladezeit {#loading-time}

Wenn der Benutzer eine webforJ-App startet, lädt sie nur einen kleinen Teil (nur etwa 2,5 kB gzip) von JavaScript, um die Sitzung zu starten. Danach lädt sie dynamisch einzelne Nachrichten oder Teile von JavaScript nach Bedarf, während die App die entsprechenden Funktionalitäten nutzt. Zum Beispiel sendet der Server dem Client nur einmal das notwendige JavaScript, um einen webforJ `Button` zu erstellen, wenn die App ihre erste `Button`-Komponente erstellt. Dies führt zu messbaren Verbesserungen der Anfangsladezeit, was zu einer besseren Benutzererfahrung führt.
