---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 8d01fe0878cf3002fe34ef2e566c2837
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` dient als vielseitige Grundlage für die Verwaltung von komposite Elementen in webforJ-Anwendungen. Ihr Hauptzweck ist es, die Interaktion mit HTML-Elementen, die durch die Klasse `Element` repräsentiert werden, zu erleichtern, indem sie einen strukturierten Ansatz zum Umgang mit Eigenschaften, Attributen und Ereignis-Listenern bietet. Sie ermöglicht die Implementierung und Wiederverwendung von Elementen in einer Anwendung. Verwenden Sie die Klasse `ElementComposite`, wenn Sie Webkomponenten für die Verwendung in webforJ-Anwendungen implementieren.

Beim Einsatz der Klasse `ElementComposite` gibt Ihnen die Methode `getElement()` Zugriff auf das zugrunde liegende `Element`-Komponente. Ebenso gibt Ihnen die Methode `getNodeName()` den Namen dieses Knotens im DOM.

:::tip
Es ist möglich, alles mit der `Element`-Klasse selbst zu tun, ohne die Klasse `ElementComposite` zu verwenden. Die bereitgestellten Methoden in der `ElementComposite` geben den Benutzern jedoch eine Möglichkeit, die geleistete Arbeit wiederzuverwenden.
:::

Diese Anleitung demonstriert, wie man die [Shoelace QR-Code-Webkomponente](https://shoelace.style/components/qr-code) mithilfe der Klasse `ElementComposite` implementiert.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschafts- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute in Webkomponenten repräsentieren den Zustand der Komponente. Sie werden häufig verwendet, um Daten oder Konfiguration zu verwalten. Die Klasse `ElementComposite` bietet eine bequeme Möglichkeit, mit Eigenschaften und Attributen zu arbeiten.

Eigenschaften und Attribute können als `PropertyDescriptor`-Mitglieder der zu schreibenden `ElementComposite`-Klasse deklariert und initialisiert werden, und dann im Code verwendet werden. Um Eigenschaften und Attribute zu definieren, verwenden Sie die Methode `set()`, um den Wert einer Eigenschaft festzulegen. Zum Beispiel, `set(PropertyDescriptor<V> property, V value)` setzt eine Eigenschaft auf einen bestimmten Wert.

:::info
Eigenschaften werden intern innerhalb des Codes der Komponente zugegriffen und manipuliert und spiegeln sich nicht im DOM wider. Attribute hingegen sind Teil der externen Schnittstelle der Komponente und können verwendet werden, um Informationen von außen in eine Komponente zu übergeben, was eine Möglichkeit bietet, externe Elemente oder Skripte zu verwenden, um die Komponente zu konfigurieren.
:::

```java
// Beispiel für die Eigenschaft TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Beispielattribut VALUE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Mein Titel");
set(VALUE, "Mein Wert");
```

Zusätzlich zur Festlegung einer Eigenschaft verwenden Sie die Methode `get()` in der Klasse `ElementComposite`, um auf Eigenschaften zuzugreifen und sie zu lesen. Die `get()`-Methode kann ein optionales `boolean`-Argument entgegennehmen, das standardmäßig false ist, um zu bestimmen, ob die Methode eine Anfrage an den Client senden soll, um den Wert abzurufen. Dies hat Auswirkungen auf die Leistung, könnte jedoch notwendig sein, wenn die Eigenschaft rein im Client geändert werden kann.

Ein `Type` kann ebenfalls an die Methode übergeben werden, das diktieren kann, in was der abgerufene Wert umgewandelt werden soll.

:::tip
Dieser `Type` ist nicht unbedingt erforderlich und fügt eine zusätzliche Spezifikationsebene hinzu, während die Daten abgerufen werden.
:::

```java
// Beispiel für die Eigenschaft TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Im folgenden Demo wurden Eigenschaften für den QR-Code basierend auf der Dokumentation für die Webkomponente hinzugefügt. Methoden wurden dann implementiert, die es Benutzern ermöglichen, die verschiedenen implementierten Eigenschaften zu erhalten und festzulegen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Ereignisregistrierung {#event-registration}

Ereignisse ermöglichen die Kommunikation zwischen verschiedenen Teilen Ihrer webforJ-Anwendung. Die Klasse `ElementComposite` bietet Ereignisbehandlung mit Unterstützung für Debouncing, Throttling, Filtern und benutzerdefinierte Ereignisdaten-Sammlungen.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klickereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
    // Verarbeiten des Klickereignisses
});
```

:::info
Die `ElementComposite`-Ereignisse unterscheiden sich von `Element`-Ereignissen, da hier keine beliebige Klasse erlaubt ist, sondern nur spezifizierte `Event`-Klassen.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

webforJ bietet vordefinierte Ereignisklassen mit typisiertem Datenzugriff:

- **ElementClickEvent**: Mausklickereignisse mit Koordinaten (`getClientX()`, `getClientY()`), Schaltflächeninformationen (`getButton()`) und Modifikatortasten (`isCtrlKey()`, `isShiftKey()`, usw.)
- **ElementDefinedEvent**: Wird ausgelöst, wenn ein benutzerdefiniertes Element im DOM definiert und bereit zur Verwendung ist
- **ElementEvent**: Basis-Ereignisklasse, die Zugriff auf rohe Ereignisdaten, Ereignistyp (`getType()`) und Ereignis-ID (`getId()`) bietet

### Ereignislasten {#event-payloads}

Ereignisse transportieren Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf diese Daten für rohe Ereignisdaten zu oder verwenden Sie typisierte Methoden, wenn sie in den eingebauten Ereignisklassen verfügbar sind. Für weitere Informationen zur effizienten Nutzung von Ereignislasten siehe die [Ereignisleitfaden](../building-ui/events).

## Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Für spezialisierte Ereignisbehandlungen erstellen Sie benutzerdefinierte Ereignisklassen mit konfigurierten Lasten unter Verwendung der Annotations `@EventName` und `@EventOptions`.

Im folgenden Beispiel wurde ein Klickereignis erstellt und dann der QR-Code-Komponente hinzugefügt. Dieses Ereignis wird beim Auslösen die "X"-Koordinate der Maus zum Zeitpunkt des Klicks auf die Komponente anzeigen, die den Java-Ereignisdaten als Informationen zur Verfügung gestellt werden. Eine Methode wird dann implementiert, um es dem Benutzer zu ermöglichen, auf diese Daten zuzugreifen, was so in der Anwendung angezeigt wird.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` lässt Sie das Verhalten von Ereignissen anpassen, indem Sie konfigurieren, welche Daten gesammelt werden, wann Ereignisse ausgelöst werden und wie sie verarbeitet werden. Hier ist ein umfassendes Codebeispiel, das alle Konfigurationsoptionen zeigt:

```java
ElementEventOptions options = new ElementEventOptions()
    // Sammeln Sie benutzerdefinierte Daten vom Client
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // Führen Sie JavaScript aus, bevor das Ereignis ausgelöst wird
    .setCode("component.classList.add('processing');")
    
    // Nur auslösen, wenn die Bedingungen erfüllt sind
    .setFilter("component.value.length >= 2")
    
    // Ausführung verzögern, bis der Benutzer mit dem Tippen aufhört (300 ms)
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Leistungssteuerung {#performance-control}

Steuern Sie, wann und wie oft Ereignisse ausgelöst werden:

**Debouncing** verzögert die Ausführung, bis die Aktivität aufhört:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Warte 300 ms nach dem letzten Ereignis
```

**Throttling** begrenzt die Ausführungsfrequenz:

```java
options.setThrottle(100); // Maximal einmal alle 100 ms auslösen
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Auf ruhige Phase warten, dann auslösen (Standard)
- `BOTH`: Sofort auslösen und nach ruhiger Phase auslösen

## Optionszusammenführung {#options-merging}

Kombinieren Sie Ereignis-Konfigurationen aus verschiedenen Quellen mithilfe von `mergeWith()`. Basisoptionen bieten gemeinschaftliche Daten für alle Ereignisse, während spezifische Optionen spezialisierte Konfigurationen hinzufügen. Spätere Optionen überschreiben widersprüchliche Einstellungen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaktion mit Slots {#interacting-with-slots}

Webkomponenten verwenden häufig Slots, um Entwicklern zu ermöglichen, die Struktur einer Komponente von außen zu definieren. Ein Slot ist ein Platzhalter innerhalb einer Webkomponente, der mit Inhalten gefüllt werden kann, wenn die Komponente verwendet wird. Im Kontext der Klasse `ElementComposite` bieten Slots eine Möglichkeit, den Inhalt innerhalb einer Komponente anzupassen. Die folgenden Methoden werden bereitgestellt, um Entwicklern die Interaktion mit und Manipulation von Slots zu ermöglichen:

1. **`findComponentSlot()`**: Diese Methode wird verwendet, um nach einem bestimmten Komponenten in allen Slots eines Komponentensystems zu suchen. Sie gibt den Namen des Slots zurück, in dem sich die Komponente befindet. Wenn die Komponente in keinem Slot gefunden wird, wird ein leerer String zurückgegeben.

2. **`getComponentsInSlot()`**: Diese Methode ruft die Liste der Komponenten ab, die einem bestimmten Slot in einem Komponentensystem zugewiesen sind. Optional kann ein bestimmter Klassentyp übergeben werden, um die Ergebnisse der Methode zu filtern.

3. **`getFirstComponentInSlot()`**: Diese Methode ist dazu gedacht, die erste Komponente abzurufen, die dem Slot zugewiesen ist. Optional kann ein bestimmter Klassentyp übergeben werden, um die Ergebnisse dieser Methode zu filtern.

Es ist auch möglich, die Methode `add()` mit einem `String`-Parameter zu verwenden, um den gewünschten Slot anzugeben, in den die übergebene Komponente hinzugefügt werden soll.

Diese Interaktionen ermöglichen es Entwicklern, die Vorteile von Webkomponenten zu nutzen, indem sie eine saubere und unkomplizierte API zur Manipulation von Slots, Eigenschaften und zur Ereignisbehandlung innerhalb der Klasse `ElementComposite` bieten.
