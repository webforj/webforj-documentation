---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 78629dd08e77cbd5f111aabb094f8db8
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` dient als vielseitige Grundlage für die Verwaltung von zusammengesetzten Elementen in webforJ-Anwendungen. Ihr Hauptzweck ist es, die Interaktion mit HTML-Elementen, die durch die Klasse `Element` dargestellt werden, zu erleichtern, indem sie einen strukturierten Ansatz zur Handhabung von Eigenschaften, Attributen und Ereignis-Listenern bietet. Sie ermöglicht die Implementierung und Wiederverwendung von Elementen in einer Anwendung. Verwenden Sie die Klasse `ElementComposite`, wenn Sie Webkomponenten für den Einsatz in webforJ-Anwendungen implementieren.

Beim Einsatz der Klasse `ElementComposite` gibt Ihnen die Methode `getElement()` Zugriff auf die zugrunde liegende `Element`-Komponente. Ebenso gibt Ihnen die Methode `getNodeName()` den Namen dieses Knotens im DOM.

:::tip
Es ist möglich, alles mit der Klasse `Element` selbst zu erledigen, ohne die Klasse `ElementComposite` zu verwenden. Die bereitgestellten Methoden in der `ElementComposite` bieten den Nutzern jedoch eine Möglichkeit, die geleistete Arbeit wiederzuverwenden.
:::

Im Verlauf dieses Leitfadens implementieren wir die [Shoelace QR-Code-Webkomponente](https://shoelace.style/components/qr-code) mithilfe der Klasse `ElementComposite`.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschaften und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute in Webkomponenten repräsentieren den Zustand der Komponente. Sie werden häufig verwendet, um Daten oder Konfigurationen zu verwalten. Die Klasse `ElementComposite` bietet eine bequeme Möglichkeit, mit Eigenschaften und Attributen zu arbeiten.

Eigenschaften und Attribute können als `PropertyDescriptor`-Mitglieder der zu schreibenden Klasse `ElementComposite` deklariert und initialisiert werden und dann im Code verwendet werden. Um Eigenschaften und Attribute zu definieren, verwenden Sie die Methode `set()`, um den Wert einer Eigenschaft festzulegen. Zum Beispiel setzt `set(PropertyDescriptor<V> property, V value)` eine Eigenschaft auf einen bestimmten Wert.

:::info
Eigenschaften werden intern im Code der Komponente zugegriffen und manipuliert und spiegeln sich nicht im DOM wider. Attribute hingegen sind Teil der externen Schnittstelle der Komponente und können verwendet werden, um Informationen von außen in eine Komponente zu übergeben, was eine Möglichkeit bietet, externe Elemente oder Skripte die Komponente zu konfigurieren.
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

Neben dem Setzen einer Eigenschaft verwenden Sie die Methode `get()` in der Klasse `ElementComposite`, um Eigenschaften abzurufen und zu lesen. Die Methode `get()` kann ein optionales `boolean`-Wert entgegennehmen, das standardmäßig false ist, um festzulegen, ob die Methode eine Verbindung zum Client herstellen soll, um den Wert abzurufen. Dies hat Auswirkungen auf die Leistung, könnte jedoch notwendig sein, wenn die Eigenschaft ausschließlich im Client geändert werden kann.

Ein `Type` kann ebenfalls an die Methode übergeben werden, was diktiert, in was das abgerufene Ergebnis konvertiert werden soll.

:::tip
Dieser `Type` ist nicht zwingend erforderlich und fügt eine zusätzliche Spezifikationsebene hinzu, während die Daten abgerufen werden.
:::

```java
// Beispiel für eine Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Im folgenden Demo wurden Eigenschaften für den QR-Code basierend auf der Dokumentation für die Webkomponente hinzugefügt. Methoden wurden implementiert, die es den Nutzern ermöglichen, die verschiedenen implementierten Eigenschaften abzurufen und festzulegen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Ereignisanmeldung {#event-registration}

Ereignisse sind ein entscheidender Bestandteil von Webkomponenten, da sie die Kommunikation zwischen verschiedenen Teilen einer Anwendung ermöglichen. Die Klasse `ElementComposite` vereinfacht die Anmeldung und Handhabung von Ereignissen. Um einen Ereignis-Listener zu registrieren, verwenden Sie die Methode `addEventListener()`, um Ereignis-Listener für spezifische Ereignisarten zu registrieren. Geben Sie die Ereignisklasse, den Listener und optionale Ereignisoptionen an.

```java
// Beispiel: Hinzufügen eines Klick-Ereignis-Listeners
addEventListener(ClickEvent.class, event -> {
    // Handhaben des Klickereignisses
});
```

:::info
Die Ereignisse der Klasse `ElementComposite` unterscheiden sich von den Ereignissen der Klasse `Element`, da dies nicht jede Klasse, sondern nur spezifizierte `Event`-Klassen zulässt.
:::

In der folgenden Demonstration wurde ein Klickereignis erstellt und dann zur QR-Code-Komponente hinzugefügt. Dieses Ereignis, wenn es ausgelöst wird, zeigt die "X"-Koordinate der Maus zum Zeitpunkt des Klicks auf die Komponente an, die als Daten an das Java-Ereignis übergeben werden. Eine Methode wird dann implementiert, um dem Benutzer den Zugriff auf diese Daten zu ermöglichen, die dann in der Anwendung angezeigt werden.
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interaktion mit Slots {#interacting-with-slots}

Webkomponenten verwenden häufig Slots, um Entwicklern zu ermöglichen, die Struktur einer Komponente von außen zu definieren. Ein Slot ist ein Platzhalter innerhalb einer Webkomponente, der beim Verwenden der Komponente mit Inhalten gefüllt werden kann. Im Kontext der Klasse `ElementComposite` bieten Slots eine Möglichkeit, den Inhalt innerhalb einer Komponente anzupassen. Die folgenden Methoden stehen zur Verfügung, um Entwicklern die Interaktion mit und Manipulation von Slots zu ermöglichen:

1. **`findComponentSlot()`**: Diese Methode wird verwendet, um nach einem bestimmten Komponenten in allen Slots eines Komponentensystems zu suchen. Sie gibt den Namen des Slots zurück, in dem sich die Komponente befindet. Wenn die Komponente in keinem Slot gefunden wird, wird ein leerer String zurückgegeben.

2. **`getComponentsInSlot()`**: Diese Methode ruft die Liste von Komponenten ab, die einem bestimmten Slot in einem Komponentensystem zugewiesen sind. Optional kann eine spezifische Klassentyp übergeben werden, um die Ergebnisse der Methode zu filtern.

3. **`getFirstComponentInSlot()`**: Diese Methode ist dafür ausgelegt, die erste Komponente abzurufen, die dem Slot zugewiesen ist. Optional kann eine spezifische Klassentyp übergeben werden, um die Ergebnisse dieser Methode zu filtern.

Es ist auch möglich, die Methode `add()` mit einem `String`-Parameter zu verwenden, um den gewünschten Slot anzugeben, in den die übergebene Komponente hinzugefügt werden soll.

Diese Interaktionen ermöglichen es Entwicklern, die Vorteile von Webkomponenten zu nutzen, indem sie eine klare und unkomplizierte API für die Manipulation von Slots, Eigenschaften und die Handhabung von Ereignissen innerhalb der Klasse `ElementComposite` bereitstellen.
