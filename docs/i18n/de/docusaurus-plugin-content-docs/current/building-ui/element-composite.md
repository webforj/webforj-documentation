---
sidebar_position: 1
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 6e201040e3dfd4be12037094eb9e978e
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` dient als vielseitige Basis für die Verwaltung von zusammengesetzten Elementen in webforJ-Anwendungen. Ihr Hauptzweck besteht darin, die Interaktion mit HTML-Elementen, die durch die Klasse `Element` repräsentiert werden, zu erleichtern, indem sie einen strukturierten Ansatz zur Handhabung von Eigenschaften, Attributen und Ereignis-Listenern bereitstellt. Sie ermöglicht die Implementierung und Wiederverwendung von Elementen in einer App. Verwenden Sie die Klasse `ElementComposite`, wenn Sie Webkomponenten für die Verwendung in webforJ-Anwendungen implementieren.

Bei der Verwendung der Klasse `ElementComposite` gibt Ihnen die Methode `getElement()` Zugriff auf die zugrunde liegende `Element`-Komponente. Ebenso gibt Ihnen die Methode `getNodeName()` den Namen dieses Knotens im DOM zurück.

:::tip
Es ist möglich, alles mit der `Element`-Klasse selbst zu tun, ohne die Klasse `ElementComposite` zu verwenden. Die bereitgestellten Methoden in der `ElementComposite` geben den Benutzern jedoch die Möglichkeit, die geleistete Arbeit wiederzuverwerten.
:::

Dieses Handbuch zeigt, wie die [Shoelace QR-Code-Webkomponente](https://shoelace.style/components/qr-code) unter Verwendung der Klasse `ElementComposite` implementiert wird.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschaften und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute in Webkomponenten repräsentieren den Zustand der Komponente. Sie werden häufig verwendet, um Daten oder Konfigurationen zu verwalten. Die Klasse `ElementComposite` bietet eine bequeme Möglichkeit, mit Eigenschaften und Attributen zu arbeiten.

Eigenschaften und Attribute können als `PropertyDescriptor`-Mitglieder der zu schreibenden Klasse `ElementComposite` deklariert und initialisiert werden und dann im Code verwendet werden. Um Eigenschaften und Attribute zu definieren, verwenden Sie die Methode `set()`, um den Wert einer Eigenschaft festzulegen. Zum Beispiel setzt `set(PropertyDescriptor<V> property, V value)` eine Eigenschaft auf einen angegebenen Wert.

:::info
Eigenschaften werden intern im Code der Komponente zugegriffen und manipuliert und spiegeln sich nicht im DOM wider. Attribute hingegen sind Teil der externen Schnittstelle der Komponente und können verwendet werden, um Informationen von außen in eine Komponente zu übergeben, wodurch eine Möglichkeit besteht, externe Elemente oder Skripte zu konfigurieren.
:::

```java
// Beispiel einer Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Beispiel eines Attributs namens VALUE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Mein Titel");
set(VALUE, "Mein Wert");
```

Zusätzlich zum Festlegen einer Eigenschaft verwenden Sie die Methode `get()` in der Klasse `ElementComposite`, um Eigenschaften zuzugreifen und zu lesen. Die Methode `get()` kann ein optionales `boolean`-Wert übergeben werden, der standardmäßig false ist, um zu bestimmen, ob die Methode einen Aufruf an den Client tätigen soll, um den Wert abzurufen. Dies wirkt sich auf die Leistung aus, könnte aber erforderlich sein, wenn die Eigenschaft rein im Client verändert werden kann.

Ein `Type` kann ebenfalls an die Methode übergeben werden, die angibt, in was das abgerufene Ergebnis umgewandelt werden soll.

:::tip
Dieser `Type` ist nicht zwingend erforderlich und fügt eine zusätzliche Ebene der Spezifikation hinzu, während die Daten abgerufen werden.
:::

```java
// Beispiel einer Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Im folgenden Demo wurden Eigenschaften für den QR-Code basierend auf der Dokumentation für die Webkomponente hinzugefügt. Es wurden dann Methoden implementiert, die es Benutzern ermöglichen, die verschiedenen implementierten Eigenschaften zu erhalten und festzulegen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Ereignisregistrierung {#event-registration}

Ereignisse ermöglichen die Kommunikation zwischen verschiedenen Teilen Ihrer webforJ-App. Die Klasse `ElementComposite` bietet die Ereignisbehandlung mit Unterstützung für Debouncing, Throttling, Filtering und benutzerdefinierte Ereignisdaten-Sammlungen.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klick-Ereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
    // Ereignis behandeln
});
```

:::info
Die Ereignisse von `ElementComposite` unterscheiden sich von den Ereignissen von `Element`, da letzteres nicht jede Klasse zulässt, sondern nur bestimmte `Event`-Klassen.
:::

### Integrierte Ereignisklassen {#built-in-event-classes}

webforJ bietet vorgefertigte Ereignisklassen mit typisierten Datenzugriff:

- **ElementClickEvent**: Mausklickereignisse mit Koordinaten (`getClientX()`, `getClientY()`), Button-Informationen (`getButton()`) und Modifikatortasten (`isCtrlKey()`, `isShiftKey()`, usw.)
- **ElementDefinedEvent**: Wird ausgelöst, wenn ein benutzerdefiniertes Element im DOM definiert und bereit zur Verwendung ist
- **ElementEvent**: Basis-Ereignisklasse, die Zugriff auf rohe Ereignisdaten, Ereignistyp (`getType()`) und Ereignis-ID (`getId()`) bietet

### Ereignis-Payloads {#event-payloads}

Ereignisse tragen Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf diese Daten für rohe Ereignisdaten zu oder verwenden Sie typisierte Methoden, wenn sie in integrierten Ereignisklassen verfügbar sind. Für weitere Details zur effizienten Nutzung von Ereignis-Payloads siehe das [Ereignis Handbuch](../building-ui/events).

## Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Für spezialisierte Ereignisbehandlungen erstellen Sie benutzerdefinierte Ereignisklassen mit konfigurierten Payloads unter Verwendung der Annotationen `@EventName` und `@EventOptions`.

Im folgenden Beispiel wurde ein Klickereignis erstellt und dann zur QR-Code-Komponente hinzugefügt. Dieses Ereignis zeigt, wenn es ausgelöst wird, die "X"-Koordinate der Maus zum Zeitpunkt des Klicks auf die Komponente an, die als Daten an das Java-Ereignis übergeben wird. Eine Methode wird dann implementiert, um dem Benutzer den Zugriff auf diese Daten zu ermöglichen, was zeigt, wie es in der App angezeigt wird.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` ermöglicht Ihnen, das Ereignisverhalten anzupassen, indem konfiguriert wird, welche Daten gesammelt werden, wann Ereignisse ausgelöst werden und wie sie verarbeitet werden. Hier ist ein umfassender Codeausschnitt, der alle Konfigurationsoptionen zeigt:

```java
ElementEventOptions options = new ElementEventOptions()
    // Benutzerdaten vom Client sammeln
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // JavaScript ausführen, bevor das Ereignis ausgelöst wird
    .setCode("component.classList.add(' processing');")
    
    // Nur auslösen, wenn Bedingungen erfüllt sind
    .setFilter("component.value.length >= 2")
    
    // Ausführung verzögern, bis der Benutzer mit dem Tippen aufhört (300ms)
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Leistungssteuerung {#performance-control}

Steuern Sie, wann und wie oft Ereignisse ausgelöst werden:

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Warte 300ms nach dem letzten Ereignis
```

**Throttling** begrenzt die Häufigkeit der Ausführung:

```java
options.setThrottle(100); // Höchstens einmal alle 100ms auslösen
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Warte auf die Ruhephase, dann auslösen (Standard)
- `BOTH`: Sofort auslösen und nach der Ruhephase

## Optionen zusammenführen {#options-merging}

Kombinieren Sie Ereigniskonfigurationen aus verschiedenen Quellen mit `mergeWith()`. Basisoptionen bieten allgemeine Daten für alle Ereignisse, während spezifische Optionen spezialisierte Konfigurationen hinzufügen. Spätere Optionen überschreiben Konflikteinstellungen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaktion mit Slots {#interacting-with-slots}

Webkomponenten verwenden häufig Slots, um Entwicklern zu ermöglichen, die Struktur einer Komponente von außen zu definieren. Ein Slot ist ein Platzhalter innerhalb einer Webkomponente, der mit Inhalt gefüllt werden kann, wenn die Komponente verwendet wird. Im Kontext der Klasse `ElementComposite` bieten Slots eine Möglichkeit, den Inhalt innerhalb einer Komponente anzupassen. Die folgenden Methoden werden bereitgestellt, um Entwicklern zu ermöglichen, mit Slots zu interagieren und sie zu manipulieren:

1. **`findComponentSlot()`**: Diese Methode wird verwendet, um eine bestimmte Komponente in allen Slots eines Komponenten-Systems zu suchen. Sie gibt den Namen des Slots zurück, in dem sich die Komponente befindet. Wenn die Komponente in keinem Slot gefunden wird, wird ein leerer String zurückgegeben.

2. **`getComponentsInSlot()`**: Diese Methode ruft die Liste der Komponenten ab, die einem bestimmten Slot in einem Komponenten-System zugewiesen sind. Optional kann eine spezifische Klassenart übergeben werden, um die Ergebnisse der Methode zu filtern.

3. **`getFirstComponentInSlot()`**: Diese Methode wurde entwickelt, um die erste Komponente abzurufen, die dem Slot zugewiesen ist. Optional kann eine spezifische Klassenart übergeben werden, um die Ergebnisse dieser Methode zu filtern.

Es ist auch möglich, die Methode `add()` mit einem `String`-Parameter zu verwenden, um den gewünschten Slot anzugeben, in den die übergebene Komponente hinzugefügt werden soll.

Diese Interaktionen ermöglichen es Entwicklern, die Leistung von Webkomponenten zu nutzen, indem sie eine saubere und unkomplizierte API für die Manipulation von Slots, Eigenschaften und die Ereignisbehandlung innerhalb der Klasse `ElementComposite` bereitstellen.
