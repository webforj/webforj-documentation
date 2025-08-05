---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ed7cdbde8cee6b173108326dfa7fce2a
---
Der folgende Abschnitt behandelt verschiedene Leistungsqualitäten und Best Practices für webforJ sowie Implementierungsdetails für das Framework.

Beim Erstellen einer Anwendung in webforJ arbeiten der Client und der Server zusammen, um Daten zwischen Client und Server zu manipulieren. Diese Interaktionen können in grobe Kategorien unterteilt werden:

## 1. Server zu Client {#1-server-to-client}

webforJ-Methoden wie `setText()` fallen in diese Kategorie. Eine webforJ-Anwendung, die auf dem Server läuft, sendet Daten an den Client, ohne auf eine Antwort zu warten. webforJ optimiert automatisch Batches von Operationen in dieser Kategorie, um die Leistung zu verbessern.

## 2. Client zu Server {#2-client-to-server}

Diese Kategorie umfasst Ereignisverkehr, wie z.B. eine `Button.onClick()`-Methode. In den meisten Fällen sendet der Client Ereignisse an den Server, ohne auf eine Antwort zu warten. Das Ereignisobjekt enthält typischerweise zusätzliche Parameter, die sich auf das Ereignis beziehen, wie den Hashcode. Da diese Informationen als Teil des Übermittlungsakts des Ereignisses an den Server gesendet werden, stehen sie dem Programm sofort zur Verfügung, sobald das Ereignis empfangen wird.

## 3. Server zu Client zu Server (Rundreise) {#3-server-to-client-to-server-round-trip}

Rundreisen werden durchgeführt, wenn die Anwendung den Client nach dynamischen Informationen fragt, die nicht auf dem Server zwischengespeichert werden können. Methoden wie `Label.getText()` und `Checkbox.isChecked()` fallen in diese Kategorie. Wenn eine webforJ-Anwendung eine Zeile wie `String title = myLabel.getText()` ausführt, kommt sie vollständig zum Stillstand, während der Server diese Anfrage an den Client sendet und dann auf die Antwort des Clients wartet.

Wenn die Anwendung mehrere Nachrichten an den Client sendet, die keine Antwort erfordern (Kategorie 1), gefolgt von einer einzelnen Nachricht, die eine Rundreise erfordert (Kategorie 3), muss die Anwendung warten, bis der Client alle ausstehenden Nachrichten verarbeitet hat, bevor er auf die letzte Nachricht antwortet, die eine Antwort erfordert. In einigen Fällen kann dies eine Verzögerung verursachen. Wäre diese Rundreise nicht eingeführt worden, hätte der Client weiterhin an der Verarbeitung dieser zurückgestauten Nachrichten arbeiten können, während die auf dem Server laufende Anwendung mit neuen Arbeiten fortgegangen wäre.

## Leistung verbessern {#improve-performance}

Es ist möglich, die Reaktionsfähigkeit der Anwendung erheblich zu verbessern, indem die Rundreisen der dritten Kategorie so weit wie möglich vermieden werden. Beispielsweise kann die Funktionalität onSelect des ComboBox von Folgendem geändert werden:

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // Geht an den Client
    int selected = component.getSelectedIndex();
}
```

zu Folgendem:

```java
private void comboBoxSelect(ListSelectEvent ev){
    //Werte aus dem Ereignis abrufen
    int selected = ev.getSelectedIndex();
}
```

Im ersten Codeabschnitt zwängt `ComboBox.getSelectedIndex()`, das auf dem Component ausgeführt wird, eine Rundreise zurück zum Client, was eine Verzögerung einführt. In der zweiten Version ruft die Verwendung der Methode `ListSelectEvent.getSelectedIndex()` des Ereignisses den Wert ab, der im Rahmen des ursprünglichen Ereignisses an den Server übermittelt wurde.

## Caching {#caching}

webforJ optimiert die Leistung weiter durch die Nutzung von Caching. Im Allgemeinen existieren in diesem Kontext zwei Arten von Daten: Daten, die der Benutzer direkt ändern kann, und Daten, die nicht vom Benutzer geändert werden können. Im ersten Fall ist es notwendig, den Server nach diesen Informationen zu fragen, wenn man die Informationen abruft, mit denen die Benutzer direkt interagieren werden.

Jedoch können Informationen, die vom Benutzer nicht geändert werden können, zwischengespeichert werden, um zusätzliche Leistungseinbußen zu vermeiden. Dies stellt sicher, dass eine Rundreise nicht unnötig durchgeführt werden muss, was eine effizientere Benutzererfahrung bietet. webforJ optimiert Anwendungen auf diese Weise, um eine optimale Leistung sicherzustellen.

## Ladezeit {#loading-time}

Wenn der Benutzer eine webforJ-Anwendung startet, lädt sie nur einen kleinen Teil (etwa 2,5 kB gzip) von JavaScript, um die Sitzung zu starten. Danach lädt sie dynamisch einzelne Nachrichten oder Stücke von JavaScript bedarfsorientiert herunter, während die Anwendung die entsprechende Funktionalität nutzt. Zum Beispiel sendet der Server dem Client nur das JavaScript, das zur Erstellung eines webforJ `Button` erforderlich ist, einmal — wenn die Anwendung ihre erste `Button`-Komponente erstellt. Dies führt zu messbaren Verbesserungen der anfänglichen Ladezeit, was zu einer besseren Benutzererfahrung führt.
