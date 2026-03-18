---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 1fc82a7db864ec48118fb611a94a57fc
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die `ElementComposite`-Klasse dient als vielseitige Grundlage für die Verwaltung von zusammengesetzten Elementen in webforJ-Anwendungen. Ihr Hauptzweck besteht darin, die Interaktion mit HTML-Elementen, die durch die `Element`-Klasse repräsentiert werden, zu erleichtern, indem ein strukturierter Ansatz zur Handhabung von Eigenschaften, Attributen und Ereignis-Listenern bereitgestellt wird. Sie ermöglicht die Implementierung und Wiederverwendung von Elementen in einer Anwendung. Verwenden Sie die `ElementComposite`-Klasse, wenn Sie Webkomponenten für die Verwendung in webforJ-Anwendungen implementieren.

Bei der Verwendung der `ElementComposite`-Klasse liefert die Methode `getElement()` Zugriff auf das zugrunde liegende `Element`-Komponente. Ebenso gibt die Methode `getNodeName()` den Namen dieses Knotens im DOM zurück.

:::tip
Es ist möglich, alles mit der `Element`-Klasse selbst zu tun, ohne die `ElementComposite`-Klasse zu verwenden. Die bereitgestellten Methoden in der `ElementComposite`-Klasse bieten jedoch den Benutzern eine Möglichkeit, die geleistete Arbeit wiederzuverwenden.
:::

Diese Anleitung zeigt, wie die [Shoelace QR-Code-Webkomponente](https://shoelace.style/components/qr-code) mithilfe der `ElementComposite`-Klasse implementiert wird.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute in Webkomponenten repräsentieren den Zustand der Komponente. Sie werden häufig verwendet, um Daten oder Konfigurationen zu verwalten. Die `ElementComposite`-Klasse bietet eine bequeme Möglichkeit, mit Eigenschaften und Attributen zu arbeiten.

Eigenschaften und Attribute können als `PropertyDescriptor`-Mitglieder der zu schreibenden `ElementComposite`-Klasse deklariert und initialisiert werden und dann im Code verwendet werden. Um Eigenschaften und Attribute zu definieren, verwenden Sie die Methode `set()`, um den Wert einer Eigenschaft festzulegen. Zum Beispiel setzt `set(PropertyDescriptor<V> property, V value)` eine Eigenschaft auf einen bestimmten Wert.

:::info
Eigenschaften werden intern im Code der Komponente zugegriffen und manipuliert und spiegeln sich nicht im DOM wider. Attribute hingegen sind Teil der externen Schnittstelle der Komponente und können verwendet werden, um Informationen von außen in eine Komponente zu übergeben, wobei eine Möglichkeit bereitgestellt wird, externe Elemente oder Skripte zur Konfiguration der Komponente zu verwenden.
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

Neben der Festlegung einer Eigenschaft können Sie die Methode `get()` in der `ElementComposite`-Klasse verwenden, um auf Eigenschaften zuzugreifen und sie zu lesen. Die Methode `get()` kann ein optionales `boolean`-Wert übergeben werden, der standardmäßig false ist, um zu bestimmen, ob die Methode einen Aufruf an den Client zur Abruf des Wertes durchführen soll. Dies beeinflusst die Leistung, könnte jedoch notwendig sein, wenn die Eigenschaft rein im Client modifiziert werden kann.

Ein `Type` kann ebenfalls an die Methode übergeben werden, was bestimmt, in welche Art die abgerufene Ergebnis umgewandelt werden soll.

:::tip
Dieser `Type` ist nicht unbedingt erforderlich und fügt eine zusätzliche Spezifikationsebene hinzu, wenn die Daten abgerufen werden.
:::

```java
// Beispiel für eine Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Im folgenden Demo wurden Eigenschaften für den QR-Code basierend auf der Dokumentation für die Webkomponente hinzugefügt. Methoden wurden dann implementiert, die es Benutzern ermöglichen, die verschiedenen implementierten Eigenschaften zu lesen und zu setzen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Ereignisanmeldung {#event-registration}

Ereignisse ermöglichen die Kommunikation zwischen verschiedenen Teilen Ihrer webforJ-Anwendung. Die `ElementComposite`-Klasse bietet das Ereignishandling mit Unterstützung für Debouncing, Throttling, Filterung und benutzerdefinierte Ereignisdaten.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klickereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Behandeln Sie das Klickereignis
});
```

:::info
Die `ElementComposite`-Ereignisse unterscheiden sich von `Element`-Ereignissen, in dem, dass dies nicht jede Klasse, sondern nur bestimmte `Event`-Klassen zulässt.
:::

### Eingebaute Ereignis-Klassen {#built-in-event-classes}

webforJ bietet vorkonfigurierte Ereignis-Klassen mit typisierten Datenzugriff:

- **ElementClickEvent**: Mausklickereignisse mit Koordinaten (`getClientX()`, `getClientY()`), Button-Informationen (`getButton()`) und Modifier-Tasten (`isCtrlKey()`, `isShiftKey()`, usw.)
- **ElementDefinedEvent**: Wird ausgelöst, wenn ein benutzerdefiniertes Element im DOM definiert und bereit zur Verwendung ist
- **ElementEvent**: Basisklasse für Ereignisse, die Zugriff auf Rohereignisdaten, Ereignistyp (`getType()`) und Ereignis-ID (`getId()`) bietet

### Ereignis-Nutzdaten {#event-payloads}

Ereignisse transportieren Daten vom Client zu Ihrem Java-Code. Greifen Sie auf diese Daten über `getData()` für rohe Ereignisdaten zu oder verwenden Sie typisierte Methoden, wenn sie auf eingebauten Ereignisklassen verfügbar sind. Weitere Informationen zur effizienten Nutzung von Ereignis-Nutzdaten finden Sie im [Ereignis-Guide](../building-ui/events).

## Benutzerdefinierte Ereignis-Klassen {#custom-event-classes}

Für spezielle Ereignisbehandlungen erstellen Sie benutzerdefinierte Ereignis-Klassen mit konfigurierten Nutzdaten mithilfe der Annotations `@EventName` und `@EventOptions`.

Im folgenden Beispiel wurde ein Klickereignis erstellt und dann zur QR-Code-Komponente hinzugefügt. Dieses Ereignis zeigt beim Auslösen die "X"-Koordinaten der Maus zum Zeitpunkt des Klickens auf die Komponente an, die als Daten an das Java-Ereignis übergeben werden. Eine Methode wird dann implementiert, um dem Benutzer den Zugriff auf diese Daten zu ermöglichen, die in der Anwendung angezeigt werden.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` ermöglicht es Ihnen, das Verhalten von Ereignissen anzupassen, indem Sie konfigurieren, welche Daten gesammelt werden sollen, wann Ereignisse ausgelöst werden und wie sie verarbeitet werden. Hier ist ein umfassendes Codebeispiel, das alle Konfigurationsoptionen zeigt:

```java
ElementEventOptions options = new ElementEventOptions()
  // Sammeln Sie benutzerdefinierte Daten vom Client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Führen Sie JavaScript aus, bevor das Ereignis ausgelöst wird
  .setCode("component.classList.add('processing');")
  
  // Nur auslösen, wenn Bedingungen erfüllt sind
  .setFilter("component.value.length >= 2")
  
  // Verzögern Sie die Ausführung, bis der Benutzer das Tippen beendet hat (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Leistungssteuerung {#performance-control}

Steuern Sie, wann und wie oft Ereignisse ausgelöst werden:

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 300ms nach dem letzten Ereignis warten
```

**Throttling** beschränkt die Ausführungsfrequenz:

```java
options.setThrottle(100); // Höchstens einmal alle 100ms auslösen
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Nach der ruhigen Phase auslösen (Standard)
- `BOTH`: Sofort auslösen und nach der ruhigen Phase auslösen

## Optionen zusammenführen {#options-merging}

Kombinieren Sie Ereigniskonfigurationen aus verschiedenen Quellen mithilfe von `mergeWith()`. Basisoptionen bieten gemeinsame Daten für alle Ereignisse, während spezifische Optionen spezialisierte Konfigurationen hinzufügen. Spätere Optionen überschreiben konfliktierende Einstellungen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaktion mit Slots {#interacting-with-slots}

Webkomponenten verwenden häufig Slots, um Entwicklern zu ermöglichen, die Struktur einer Komponente von außen zu definieren. Ein Slot ist ein Platzhalter innerhalb einer Webkomponente, der beim Verwenden der Komponente mit Inhalten gefüllt werden kann. Im Kontext der `ElementComposite`-Klasse bieten Slots eine Möglichkeit, den Inhalt innerhalb einer Komponente anzupassen. Die folgenden Methoden werden bereitgestellt, um Entwicklern die Interaktion mit und die Manipulation von Slots zu ermöglichen:

1. **`findComponentSlot()`**: Diese Methode wird verwendet, um ein bestimmtes Komponenten über alle Slots in einem Komponentensystem zu suchen. Sie gibt den Namen des Slots zurück, in dem sich die Komponente befindet. Wenn die Komponente in keinem Slot gefunden wird, wird eine leere Zeichenkette zurückgegeben.

2. **`getComponentsInSlot()`**: Diese Methode ruft die Liste der Komponenten ab, die einem bestimmten Slot in einem Komponentensystem zugewiesen sind. Optional kann eine bestimmte Klassentyp übergeben werden, um die Ergebnisse der Methode zu filtern.

3. **`getFirstComponentInSlot()`**: Diese Methode ist dazu gedacht, die erste Komponente abzurufen, die dem Slot zugewiesen ist. Optional kann eine bestimmte Klassentyp übergeben werden, um die Ergebnisse dieser Methode zu filtern.

Es ist auch möglich, die Methode `add()` mit einem `String`-Parameter zu verwenden, um den gewünschten Slot anzugeben, in den die übergebene Komponente hinzugefügt werden soll.

Diese Interaktionen ermöglichen es Entwicklern, die Leistungsfähigkeit von Webkomponenten zu nutzen, indem sie eine saubere und unkomplizierte API für die Manipulation von Slots, Eigenschaften und die Handhabung von Ereignissen innerhalb der `ElementComposite`-Klasse bereitstellen.
