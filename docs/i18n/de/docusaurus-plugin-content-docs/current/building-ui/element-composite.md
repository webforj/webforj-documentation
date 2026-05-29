---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: de075e855ba84ee82ec08c2bef771ea8
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` dient als vielseitige Grundlage für die Verwaltung von zusammengesetzten Elementen in webforJ-Anwendungen. Ihr Hauptzweck besteht darin, die Interaktion mit HTML-Elementen, die durch die Klasse `Element` dargestellt werden, zu erleichtern, indem sie einen strukturierten Ansatz zur Handhabung von Eigenschaften, Attributen und Ereignis-Listenern bietet. Sie ermöglicht die Implementierung und Wiederverwendung von Elementen in einer Anwendung. Verwenden Sie die Klasse `ElementComposite`, wenn Sie Web Components für die Nutzung in webforJ-Anwendungen implementieren.

Bei der Verwendung der Klasse `ElementComposite` erhalten Sie über die Methode `getElement()` Zugriff auf das zugrunde liegende `Element`-Komponente. Ebenso gibt Ihnen die Methode `getNodeName()` den Namen dieses Knotens im DOM zurück.

:::tip
Es ist möglich, alles mit der Klasse `Element` selbst zu tun, ohne die Klasse `ElementComposite` zu verwenden. Die Methoden in `ElementComposite` bieten jedoch eine Möglichkeit, Ihre Arbeit wiederzuverwenden.
:::

Die Beispiele auf dieser Seite zeigen, wie man die [Shoelace QR-Code-Webkomponente](https://shoelace.style/components/qr-code) mit der Klasse `ElementComposite` implementiert.

<ComponentDemo
path='/webforj/qrdemo'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java']}
height='175px'
/>

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute in Webkomponenten repräsentieren den Status der Komponente. Sie werden oft verwendet, um Daten oder Konfigurationen zu verwalten. Die Klasse `ElementComposite` bietet eine bequeme Möglichkeit, mit Eigenschaften und Attributen zu arbeiten.

Eigenschaften und Attribute können als `PropertyDescriptor`-Mitglieder der zu schreibenden `ElementComposite`-Klasse deklariert und initialisiert werden und dann im Code verwendet werden. Um Eigenschaften und Attribute zu definieren, verwenden Sie die Methode `set()`, um den Wert einer Eigenschaft festzulegen. Zum Beispiel setzt `set(PropertyDescriptor<V> property, V value)` eine Eigenschaft auf einen bestimmten Wert.

:::info
Eigenschaften werden intern innerhalb des Codes der Komponente zugegriffen und manipuliert und spiegeln sich nicht im DOM wider. Attribute hingegen sind Teil der externen Schnittstelle der Komponente und können verwendet werden, um Informationen von außen in eine Komponente einzubringen, wodurch eine Möglichkeit für externe Elemente oder Skripte geschaffen wird, die Komponente zu konfigurieren.
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

Verwenden Sie die Methode `get()` in der Klasse `ElementComposite`, um Eigenschaften zuzugreifen und zu lesen. Die Methode `get()` kann einen optionalen `boolean`-Wert akzeptieren, der standardmäßig false ist, um zu bestimmen, ob die Methode einen Aufruf an den Client durchführen soll, um den Wert abzurufen. Dies hat Auswirkungen auf die Leistung, könnte aber notwendig sein, wenn die Eigenschaft ausschließlich im Client geändert werden kann.

Ein `Typ` kann auch an die Methode übergeben werden, der angibt, in was das abgerufene Ergebnis umgewandelt werden soll.

:::tip
Dieser `Typ` ist nicht unbedingt erforderlich und fügt eine zusätzliche Spezifikationsschicht hinzu, während die Daten abgerufen werden.
:::

```java
// Beispiel für eine Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Im untenstehenden Demo wurden Eigenschaften für den QR-Code basierend auf der Dokumentation für die Webkomponente hinzugefügt. Es wurden Methoden implementiert, die es Benutzern ermöglichen, die verschiedenen implementierten Eigenschaften zu lesen und zu setzen.

<ComponentDemo
path='/webforj/qrproperties'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java']}
height='250px'
/>

## Ereignisregistrierung {#event-registration}

Ereignisse ermöglichen die Kommunikation zwischen verschiedenen Teilen Ihrer webforJ-Anwendung. Die Klasse `ElementComposite` bietet eine Ereignisbehandlung mit Unterstützung für Debouncing, Throttling, Filtering und benutzerdefinierte Datenaufnahme.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klick-Ereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Verarbeiten Sie das Klick-Ereignis
});
```

:::info
Die `ElementComposite`-Ereignisse unterscheiden sich von den `Element`-Ereignissen, da sie keine beliebige Klasse zulassen, sondern nur festgelegte `Event`-Klassen.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

webforJ bietet vorgefertigte Ereignisklassen mit typisierten Datenzugriff:

- **ElementClickEvent**: Maus-Klick-Ereignisse mit Koordinaten (`getClientX()`, `getClientY()`), Informationen zu Tasten (`getButton()`) und Modifikatortasten (`isCtrlKey()`, `isShiftKey()`, usw.)
- **ElementDefinedEvent**: Wird ausgelöst, wenn ein benutzerdefiniertes Element im DOM definiert und bereit zur Verwendung ist.
- **ElementEvent**: Basisklasse für Ereignisse, die Zugriff auf Rohereignisdaten, Ereignistyp (`getType()`) und Ereignis-ID (`getId()`) bietet.

### Ereignispayloads {#event-payloads}

Ereignisse transportieren Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf Rohereignisdaten zu oder verwenden Sie typisierte Methoden, wenn diese in den eingebauten Ereignisklassen verfügbar sind. Für weitere Einzelheiten zur effizienten Nutzung von Ereignispayloads siehe den [Ereignisleitfaden](../building-ui/events).

## Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Für spezialisierte Ereignisverarbeitung erstellen Sie benutzerdefinierte Ereignisklassen mit konfigurierten Payloads unter Verwendung der Annotationen `@EventName` und `@EventOptions`.

Im folgenden Beispiel wurde ein Klickereignis erstellt und dann zur QR-Code-Komponente hinzugefügt. Dieses Ereignis zeigt beim Auslösen die "X"-Koordinate der Maus zum Zeitpunkt des Klickens auf die Komponente an, die als Daten an das Java-Ereignis übergeben wird. Eine Methode wird dann implementiert, um dem Benutzer den Zugriff auf diese Daten zu ermöglichen, die so in der Anwendung angezeigt werden.

<ComponentDemo
path='/webforj/qrevent'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java']}
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` ermöglicht es Ihnen, das Ereignisverhalten anzupassen, indem Sie festlegen, welche Daten gesammelt werden sollen, wann Ereignisse ausgelöst werden und wie sie verarbeitet werden. Hier ist ein umfassendes Codebeispiel, das alle Konfigurationsoptionen zeigt:

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
  
  // Verzögerung der Ausführung, bis der Benutzer das Tippen stoppt (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Leistungssteuerung {#performance-control}

Steuern Sie, wann und wie oft Ereignisse ausgelöst werden:

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Warten Sie 300ms nach dem letzten Ereignis
```

**Throttling** begrenzt die Häufigkeit der Ausführung:

```java
options.setThrottle(100); // Höchstens einmal alle 100ms auslösen
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Warten Sie auf die Ruhephase, dann auslösen (Standard)
- `BOTH`: Sofort auslösen und nach der Ruhephase auslösen

## Optionszusammenführung {#options-merging}

Kombinieren Sie Ereigniskonfigurationen aus verschiedenen Quellen mithilfe von `mergeWith()`. Basisoptionen bieten gemeinsame Daten für alle Ereignisse, während spezifische Optionen spezialisierte Konfigurationen hinzufügen. Spätere Optionen überschreiben widersprüchliche Einstellungen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaktion mit Slots {#interacting-with-slots}

Webkomponenten verwenden oft Slots, um Entwicklern zu ermöglichen, die Struktur einer Komponente von außen zu definieren. Ein Slot ist ein Platzhalter innerhalb einer Webkomponente, der mit Inhalt gefüllt werden kann, wenn die Komponente verwendet wird. Im Kontext der Klasse `ElementComposite` bieten Slots eine Möglichkeit, den Inhalt innerhalb einer Komponente anzupassen. Die folgenden Methoden werden bereitgestellt, um Entwicklern Interaktionen mit und Manipulationen von Slots zu ermöglichen:

1. **`findComponentSlot()`**: Diese Methode wird verwendet, um ein bestimmtes Komponentenelement in einem Komponentensystem zu suchen. Sie gibt den Namen des Slots zurück, in dem sich die Komponente befindet. Wenn die Komponente in keinem Slot gefunden wird, wird ein leerer String zurückgegeben.

2. **`getComponentsInSlot()`**: Diese Methode ruft die Liste der Komponenten ab, die einem bestimmten Slot in einem Komponentensystem zugewiesen sind. Optional kann ein bestimmter Klassentyp übergeben werden, um die Ergebnisse der Methode zu filtern.

3. **`getFirstComponentInSlot()`**: Diese Methode ist dazu gedacht, die erste Komponente abzurufen, die dem Slot zugewiesen ist. Optional kann ein bestimmter Klassentyp übergeben werden, um die Ergebnisse dieser Methode zu filtern.

Es ist auch möglich, die Methode `add()` mit einem `String`-Parameter zu verwenden, um den gewünschten Slot anzugeben, in den die übergebene Komponente hinzugefügt werden soll.

Diese Interaktionen ermöglichen es Entwicklern, die Leistungsfähigkeit von Webkomponenten zu nutzen, indem sie eine saubere und unkomplizierte API zur Manipulation von Slots, Eigenschaften und zur Handhabung von Ereignissen innerhalb der Klasse `ElementComposite` bereitstellen.
