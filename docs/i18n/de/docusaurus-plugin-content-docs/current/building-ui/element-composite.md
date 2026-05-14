---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: c64ec386d273ab7facb974f5577afecf
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` dient als vielseitige Grundlage für die Verwaltung von Composite-Elementen in webforJ-Apps. Ihr Hauptzweck besteht darin, die Interaktion mit HTML-Elementen, die durch die Klasse `Element` dargestellt werden, zu erleichtern, indem sie einen strukturierten Ansatz zum Verwalten von Eigenschaften, Attributen und Ereignis-Listenern bietet. Sie ermöglicht die Implementierung und Wiederverwendung von Elementen in einer App. Verwenden Sie die Klasse `ElementComposite`, wenn Sie Webkomponenten für die Nutzung in webforJ-Apps implementieren.

Beim Einsatz der Klasse `ElementComposite` gibt Ihnen die Methode `getElement()` Zugriff auf die zugrunde liegende `Element`-Komponente. Ebenso liefert die Methode `getNodeName()` den Namen dieses Knotens im DOM.

:::tip
Es ist möglich, alles nur mit der Klasse `Element` selbst zu erledigen, ohne die Klasse `ElementComposite` zu verwenden. Die Methoden in `ElementComposite` bieten jedoch eine Möglichkeit, Ihre Arbeit wiederzuverwenden.
:::

Die Beispiele auf dieser Seite zeigen, wie die [Shoelace QR-Code-Webkomponente](https://shoelace.style/components/qr-code) unter Verwendung der Klasse `ElementComposite` implementiert wird.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute in Webkomponenten repräsentieren den Zustand der Komponente. Sie werden häufig verwendet, um Daten oder Konfiguration zu verwalten. Die Klasse `ElementComposite` bietet eine bequeme Möglichkeit, mit Eigenschaften und Attributen zu arbeiten.

Eigenschaften und Attribute können als `PropertyDescriptor`-Mitglieder der zu schreibenden `ElementComposite`-Klasse deklariert und initialisiert werden und dann im Code verwendet werden. Um Eigenschaften und Attribute zu definieren, verwenden Sie die Methode `set()`, um den Wert einer Eigenschaft festzulegen. Zum Beispiel setzt `set(PropertyDescriptor<V> property, V value)` eine Eigenschaft auf einen bestimmten Wert.

:::info
Eigenschaften werden intern innerhalb des Codes der Komponente zugegriffen und manipuliert und spiegeln sich nicht im DOM wider. Attribute hingegen sind Teil der externen Schnittstelle der Komponente und können verwendet werden, um Informationen von außen in eine Komponente zu übergeben, was es externen Elementen oder Skripten ermöglicht, die Komponente zu konfigurieren.
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

Verwenden Sie die Methode `get()` in der Klasse `ElementComposite`, um auf Eigenschaften zuzugreifen und sie zu lesen. Die Methode `get()` kann einen optionalen `boolean`-Wert akzeptieren, der standardmäßig false ist, um zu bestimmen, ob die Methode einen Zugriff auf den Client zur Abfrage des Wertes durchführen soll. Dies wirkt sich auf die Leistung aus, könnte aber notwendig sein, wenn die Eigenschaft rein im Client geändert werden kann.

Ein `Type` kann ebenfalls an die Methode übergeben werden, der bestimmt, in was das abgefragte Ergebnis umgewandelt werden soll.

:::tip
Dieser `Type` ist nicht unbedingt erforderlich und fügt eine zusätzliche Spezifizierungsebene hinzu, während die Daten abgerufen werden.
:::

```java
// Beispiel für eine Eigenschaft namens TITLE in einer ElementComposite-Klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Im untenstehenden Demo wurden Eigenschaften für den QR-Code basierend auf der Dokumentation für die Webkomponente hinzugefügt. Methoden wurden dann implementiert, die es den Benutzern ermöglichen, die verschiedenen implementierten Eigenschaften abzurufen und festzulegen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Ereignisregistrierung {#event-registration}

Ereignisse ermöglichen die Kommunikation zwischen verschiedenen Teilen Ihrer webforJ-App. Die Klasse `ElementComposite` bietet die Ereignisbehandlung mit Unterstützung für Debouncing, Throttling, Filtering und benutzerdefinierte Ereignisdaten-Sammlung.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klick-Ereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Behandeln des Klickereignisses
});
```

:::info
Die `ElementComposite`-Ereignisse sind anders als die `Element`-Ereignisse, da sie keine beliebige Klasse, sondern nur bestimmte `Event`-Klassen zulassen.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

webforJ bietet vorkonfigurierte Ereignisklassen mit typisierten Datenzugriff:

- **ElementClickEvent**: Mausklickereignisse mit Koordinaten (`getClientX()`, `getClientY()`), Tasteninformationen (`getButton()`) und Modifier-Tasten (`isCtrlKey()`, `isShiftKey()`, usw.)
- **ElementDefinedEvent**: Wird ausgelöst, wenn ein benutzerdefiniertes Element im DOM definiert und bereit zur Verwendung ist
- **ElementEvent**: Basisklasse für Ereignisse, die Zugriff auf rohe Ereignisdaten, Ereignistyp (`getType()`) und Ereignis-ID (`getId()`) bietet

### Ereignisdaten {#event-payloads}

Ereignisse tragen Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf diese Daten zu, um rohe Ereignisdaten abzurufen, oder verwenden Sie typisierte Methoden, wenn sie auf eingebauten Ereignisklassen verfügbar sind. Für weitere Details zur effizienten Nutzung von Ereignisdaten siehe die [Ereignisguide](../building-ui/events).

## Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Für spezialisierte Ereignisbehandlungen erstellen Sie benutzerdefinierte Ereignisklassen mit konfigurierten Payloads unter Verwendung der Annotationen `@EventName` und `@EventOptions`.

Im folgenden Beispiel wurde ein Klickereignis erstellt und dann zur QR-Code-Komponente hinzugefügt. Dieses Ereignis, wenn es ausgelöst wird, zeigt die "X"-Koordinate der Maus zum Zeitpunkt des Klicks auf die Komponente an, die als Daten an das Java-Ereignis weitergegeben wird. Eine Methode wird dann implementiert, um dem Benutzer den Zugriff auf diese Daten zu ermöglichen, was in der App angezeigt wird.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` ermöglicht es Ihnen, das Ereignisverhalten durch die Konfiguration der zu sammelnden Daten, wann Ereignisse ausgelöst werden und wie sie verarbeitet werden, anzupassen. Hier ist ein umfassender Code-Ausschnitt, der alle Konfigurationsoptionen zeigt:

```java
ElementEventOptions options = new ElementEventOptions()
  // Benutzerspezifische Daten vom Client sammeln
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // JavaScript ausführen, bevor das Ereignis ausgelöst wird
  .setCode("component.classList.add('processing');")
  
  // Nur auslösen, wenn Bedingungen erfüllt sind
  .setFilter("component.value.length >= 2")
  
  // Ausführung verzögern, bis der Benutzer das Tippen stoppt (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Leistungssteuerung {#performance-control}

Steuern Sie, wann und wie oft Ereignisse ausgelöst werden:

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Warte 300ms nach dem letzten Ereignis
```

**Throttling** begrenzt die Ausführungsfrequenz:

```java
options.setThrottle(100); // Maximal einmal alle 100ms auslösen
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Warte auf eine Ruhezeit, dann auslösen (Standard)
- `BOTH`: Sofort auslösen und nach der Ruhezeit auslösen

## Optionen zusammenführen {#options-merging}

Kombinieren Sie Ereigniskonfigurationen aus verschiedenen Quellen, indem Sie `mergeWith()` verwenden. Basisoptionen bieten gemeinsame Daten für alle Ereignisse, während spezifische Optionen spezialisierte Konfigurationen hinzufügen. Spätere Optionen überschreiben sich widersprechende Einstellungen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaktion mit Slots {#interacting-with-slots}

Webkomponenten verwenden häufig Slots, um Entwicklern zu ermöglichen, die Struktur einer Komponente von außen zu definieren. Ein Slot ist ein Platzhalter innerhalb einer Webkomponente, der beim Verwenden der Komponente mit Inhalten gefüllt werden kann. Im Kontext der Klasse `ElementComposite` bieten Slots eine Möglichkeit, den Inhalt innerhalb einer Komponente anzupassen. Die folgenden Methoden stehen zur Verfügung, damit Entwickler mit Slots interagieren und diese manipulieren können:

1. **`findComponentSlot()`**: Diese Methode wird verwendet, um nach einem bestimmten Element in allen Slots eines Komponentensystems zu suchen. Sie gibt den Namen des Slots zurück, in dem das Element sich befindet. Wenn das Element in keinem Slot gefunden wird, wird ein leerer String zurückgegeben.

2. **`getComponentsInSlot()`**: Diese Methode ruft die Liste der Komponenten ab, die einem bestimmten Slot in einem Komponentensystem zugewiesen sind. Optional kann eine spezifische Klassentyp zur Filterung der Ergebnisse der Methode übergeben werden.

3. **`getFirstComponentInSlot()`**: Diese Methode ist dafür gedacht, die erste Komponente abzurufen, die dem Slot zugewiesen ist. Auch hier kann optional eine spezifische Klassentyp zur Filterung der Ergebnisse dieser Methode übergeben werden.

Es ist auch möglich, die Methode `add()` mit einem `String`-Parameter zu verwenden, um den gewünschten Slot anzugeben, in den die übergebene Komponente eingefügt werden soll.

Diese Interaktionen ermöglichen es Entwicklern, die Leistungsfähigkeit von Webkomponenten zu nutzen, indem sie eine saubere und einfache API zum Manipulieren von Slots, Eigenschaften und zum Handhaben von Ereignissen innerhalb der Klasse `ElementComposite` bieten.
