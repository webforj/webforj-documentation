---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 88eca7b854822f9d78ac20731ac5a857
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` dient als vielseitige Grundlage zur Verwaltung von Composite-Elementen in webforJ-Anwendungen. Ihr Hauptzweck besteht darin, die Interaktion mit HTML-Elementen, die durch die Klasse `Element` repräsentiert werden, zu erleichtern, indem sie einen strukturierten Ansatz zur Handhabung von Eigenschaften, Attributen und Ereignis-Listenern bietet. Sie ermöglicht die Implementierung und Wiederverwendung von Elementen in einer Anwendung. Verwenden Sie die Klasse `ElementComposite`, wenn Sie Webkomponenten für die Verwendung in webforJ-Anwendungen implementieren.

Bei der Verwendung der Klasse `ElementComposite` ermöglicht die Verwendung der Methode `getElement()`, auf die zugrunde liegende `Element`-Komponente zuzugreifen. Ebenso liefert die Methode `getNodeName()` den Namen dieses Knotens im DOM.

:::tip
Es ist möglich, alles nur mit der Klasse `Element` selbst zu tun, ohne die Klasse `ElementComposite` zu verwenden. Die bereitgestellten Methoden in der `ElementComposite` geben Benutzern jedoch eine Möglichkeit, die geleistete Arbeit wiederzuverwenden.
:::

Im Verlauf dieses Leitfadens werden wir die [Shoelace QR-Code-Webkomponente](https://shoelace.style/components/qr-code) unter Verwendung der Klasse `ElementComposite` implementieren.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute in Webkomponenten repräsentieren den Zustand der Komponente. Sie werden oft verwendet, um Daten oder Konfigurationen zu verwalten. Die Klasse `ElementComposite` bietet eine bequeme Möglichkeit, mit Eigenschaften und Attributen zu arbeiten.

Eigenschaften und Attribute können als `PropertyDescriptor`-Mitglieder der zu schreibenden `ElementComposite`-Klasse deklariert und initialisiert werden und dann im Code verwendet werden. Um Eigenschaften und Attribute zu definieren, verwenden Sie die Methode `set()`, um den Wert einer Eigenschaft zu setzen. Zum Beispiel setzt `set(PropertyDescriptor<V> property, V value)` eine Eigenschaft auf einen angegebenen Wert.

:::info
Eigenschaften werden intern im Code der Komponente zugegriffen und manipuliert und spiegeln sich nicht im DOM wider. Attribute hingegen sind Teil der externen Schnittstelle der Komponente und können verwendet werden, um Informationen von außen in eine Komponente zu übergeben, was eine Möglichkeit bietet, externe Elemente oder Skripte zu konfigurieren.
:::

```java
// Beispiel für eine Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Beispiel für ein Attribut namens VALUE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Mein Titel");
set(VALUE, "Mein Wert");
```

Neben dem Setzen einer Eigenschaft können Sie die Methode `get()` in der Klasse `ElementComposite` verwenden, um auf Eigenschaften zuzugreifen und diese zu lesen. Die Methode `get()` kann mit einem optionalen `boolean`-Wert aufgerufen werden, der standardmäßig false ist, um anzugeben, ob die Methode zum Abrufen des Wertes eine Verbindung zum Client herstellen soll. Dies hat Auswirkungen auf die Leistung, könnte jedoch notwendig sein, wenn die Eigenschaft rein im Client verändert werden kann.

Ein `Type` kann auch an die Methode übergeben werden, um festzulegen, in welchen Typ das abgerufene Ergebnis umgewandelt werden soll.

:::tip
Dieser `Type` ist nicht unbedingt erforderlich und fügt eine zusätzliche Ebene der Spezifikation hinzu, während die Daten abgerufen werden.
:::

```java
// Beispiel für eine Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Im folgenden Demo wurden Eigenschaften für den QR-Code basierend auf der Dokumentation für die Webkomponente hinzugefügt. Methoden wurden dann implementiert, die es Benutzern ermöglichen, die verschiedenen implementierten Eigenschaften abzurufen und festzulegen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Ereignisregistrierung {#event-registration}

Ereignisse sind ein entscheidender Bestandteil von Webkomponenten, da sie die Kommunikation zwischen verschiedenen Teilen einer Anwendung ermöglichen. Die Klasse `ElementComposite` vereinfacht die Registrierung und Handhabung von Ereignissen. Um einen Ereignis-Listener zu registrieren, verwenden Sie die Methode `addEventListener()`, um Ereignis-Listener für spezifische Ereignisarten zu registrieren. Geben Sie die Ereignisklasse, den Listener und optionale Ereignisoptionen an.

```java
// Beispiel: Hinzufügen eines Klickereignis-Listeners
addEventListener(ClickEvent.class, event -> {
    // Bearbeiten des Klickereignisses
});
```

:::info
Die Ereignisse von `ElementComposite` unterscheiden sich von den Ereignissen von `Element`, da diese nicht jede Klasse, sondern nur bestimmte `Event`-Klassen zulassen.
:::

In der folgenden Demonstration wurde ein Klickereignis erstellt und dann zur QR-Code-Komponente hinzugefügt. Wenn dieses Ereignis ausgelöst wird, wird die "X"-Koordinate der Maus zum Zeitpunkt des Klicks auf die Komponente angezeigt, die als Daten an das Java-Ereignis übergeben wird. Eine Methode wurde dann implementiert, um dem Benutzer den Zugriff auf diese Daten zu ermöglichen, was die Art und Weise ist, wie sie in der Anwendung angezeigt werden.
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interaktion mit Slots {#interacting-with-slots}

Webkomponenten verwenden häufig Slots, um Entwicklern zu ermöglichen, die Struktur einer Komponente von außen zu definieren. Ein Slot ist ein Platzhalter innerhalb einer Webkomponente, der mit Inhalten gefüllt werden kann, wenn die Komponente verwendet wird. Im Kontext der Klasse `ElementComposite` bieten Slots eine Möglichkeit, den Inhalt innerhalb einer Komponente anzupassen. Die folgenden Methoden werden bereitgestellt, um Entwicklern zu ermöglichen, mit Slots zu interagieren und diese zu manipulieren:

1. **`findComponentSlot()`**: Diese Methode wird verwendet, um ein bestimmtes Komponentenelement in einem Komponentensystem zu suchen. Sie gibt den Namen des Slots zurück, in dem sich das Element befindet. Wenn das Element in keinem Slot gefunden wird, wird ein leerer String zurückgegeben.

2. **`getComponentsInSlot()`**: Diese Methode ruft die Liste der Komponenten ab, die einem bestimmten Slot in einem Komponentensystem zugeordnet sind. Optional können Sie einen spezifischen Klassentyp übergeben, um die Ergebnisse der Methode zu filtern.

3. **`getFirstComponentInSlot()`**: Diese Methode dient dazu, die erste Komponente abzurufen, die dem Slot zugeordnet ist. Optional können Sie einen spezifischen Klassentyp übergeben, um die Ergebnisse dieser Methode zu filtern.

Es ist auch möglich, die Methode `add()` mit einem `String`-Parameter zu verwenden, um den gewünschten Slot anzugeben, in den die übergebene Komponente hinzugefügt werden soll.

Diese Interaktionen ermöglichen es Entwicklern, die Vorteile von Webkomponenten zu nutzen, indem sie eine klare und unkomplizierte API zur Manipulation von Slots, Eigenschaften und zur Handhabung von Ereignissen innerhalb der Klasse `ElementComposite` bereitstellen.
