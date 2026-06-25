---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: d626a230fe04d316c48e3cae7e292599
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` umschließt ein benutzerdefiniertes HTML-Element oder einen [Webkomponente](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Sie bindet Ihre Java-Klasse an das zugrunde liegende `Element` und ermöglicht es Ihnen, mit den Eigenschaften, Attributen und Ereignissen dieses Elements über Java zu arbeiten. Verwenden Sie es, wenn Sie Webkomponenten in einer webforJ-Anwendung integrieren.

:::tip Wann `ElementComposite` verwenden
Greifen Sie auf `ElementComposite` zu, wenn Sie eine Drittanbieter-Webkomponente umschließen, die webforJ nicht bereits bereitstellt. Wenn eine integrierte webforJ-Komponente den Anwendungsfall abdeckt (z. B. `TextField`, `ColorField`, `Button` usw.), verwenden Sie stattdessen diese. Für einmalige DOM-Arbeiten, die nicht wiederverwendet werden müssen, kann die Klasse `Element` direkt ohne Wrapper verwendet werden.
:::

Dieser Leitfaden zeigt, wie die [Shoelace-Webkomponente für relative Zeit](https://shoelace.style/components/relative-time) mithilfe der Klasse `ElementComposite` implementiert wird.

<ComponentDemo 
path='/webforj/relativetime' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klassendeklarationen {#class-annotations}

Drei Annotationen erscheinen häufig am Anfang einer `ElementComposite`-Unterklasse: `@NodeName` erklärt das HTML-Tag, das die Komponente umschließt, und `@JavaScript` sowie `@StyleSheet` laden alle clientseitigen Ressourcen, von denen die zugrunde liegende Webkomponente abhängt. `@NodeName` ist erforderlich und spezifisch für `ElementComposite`. `@JavaScript` und `@StyleSheet` sind allgemeine Annotations von webforJ für Ressourcen und funktionieren in jeder Klasse, einschließlich Ansichten, Komponenten oder der Klasse `App`.

### `@NodeName` {#nodename}

Die Annotation `@NodeName` erklärt das HTML-Tag, das die Komponente umschließt. webforJ verwendet diesen Namen beim Erstellen des zugrunde liegenden Elements im DOM.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Der Tagname muss mit dem benutzerdefinierten Element übereinstimmen, das im Client registriert ist. Ohne diese Annotation kann das Framework nicht bestimmen, welches Element erstellt werden soll.

Innerhalb einer Unterklasse liest `getNodeName()` das deklarierte Tag aus, und `getElement()` gibt das zugrunde liegende `Element` zurück, sodass Sie DOM-Methoden direkt darauf aufrufen können.

### `@JavaScript` {#javascript}

Die Annotation `@JavaScript` lädt das Skript, das die zugrunde liegende Webkomponente definiert oder registriert. Platzieren Sie es in der Klasse, damit das Skript nur geladen wird, wenn die Komponente verwendet wird.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Mehrere `@JavaScript`-Annotationen sind erlaubt, und webforJ entfernt automatisch Duplikate. Das gleiche Skript wird nicht zweimal geladen, wenn mehrere Komponenten davon abhängen.

Siehe [Importieren von JavaScript-Dateien](../managing-resources/importing-assets#importing-javascript-files) für die vollständige Liste der Optionen, einschließlich `top`, `attributes` und Ladezeitpunkt.

### `@StyleSheet` {#stylesheet}

Die Annotation `@StyleSheet` lädt eine CSS-Datei, von der die Komponente abhängt. Sie ist nützlich für Drittanbieterkomponenten, die ein separates Stylesheet mitliefern, oder um komponentenspezifische Stile zusammen mit dem Wrapper zu bündeln.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Für lokal gebündelte Ressourcen verwenden Sie das Präfix `ws://`, um auf Dateien im Verzeichnis `resources/static` zu verweisen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Siehe [Importieren von CSS-Dateien](../managing-resources/importing-assets#importing-css-files) für die vollständige Liste der Optionen.

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute repräsentieren den Zustand einer Webkomponente, die typischerweise Daten oder Konfigurationen enthält. `ElementComposite` stellt beide über `PropertyDescriptor` zur Verfügung.

Zwei Fabrikmethoden von `PropertyDescriptor` erzeugen die Beschreibungen selbst, eine für jedes Bindungsziel:

```java
PropertyDescriptor<T> property = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindet an eine JavaScript-Eigenschaft des DOM-Knotens. `PropertyDescriptor.attribute()` bindet an ein HTML-Attribut. Das erste Argument ist der Name, den die Webkomponente erwartet. Das zweite ist ein Standardwert, der auch den Java-Typ der Beschreibung festlegt.

Deklarieren Sie die Beschreibung als private Feld in der Komponente, und lesen und schreiben Sie über `set(PropertyDescriptor<V> property, V value)` und `get(PropertyDescriptor<V> property)`.

:::info
Eigenschaften sind der interne Zustand des DOM-Knotens und spiegeln sich nicht im Markup wider. Attribute sind HTML-Markup, das für externe Skripte und CSS sichtbar ist.
:::

```java
// Beispiel für eine Eigenschaft namens "title" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Beispiel für ein Attribut namens "value" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mein Titel");
set(value, "Mein Wert");
```

Die obigen Aufrufe verwenden `set()` direkt, um die primitive Form zu zeigen. In der Praxis sind `set()` und `get()` geschützte Methoden in `ElementComposite`. Sie sind die primitive Schicht, die Java-Werte mit dem zugrunde liegenden Element synchronisiert, nicht die öffentliche API, die Verbraucher aufrufen. Das beabsichtigte Muster ist, die `PropertyDescriptor` privat zu halten und öffentliche `setX()`- und `getX()`-Methoden zu schreiben, die an die primitiven Methoden delegieren.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // geschützte primitive Methode
    return this;
  }

  public String getHeading() {
    return get(heading);     // geschützte primitive Methode
  }
}
```

Ein einzelner Aufruf von `set(descriptor, value)` erledigt drei Dinge auf einmal. Er schiebt den Wert über `setProperty()` für Eigenschaften oder `setAttribute()` für Attribute an den Client. Er speichert den Wert in einem lokalen serverseitigen Cache, eine Map pro Komponenteninstanz. Und er protokolliert den Laufzeittyp zusammen mit dem Wert, sodass spätere `get()`-Aufrufe wissen, wie sie deserialisieren sollen.

Dieser lokale Cache ist der Grund, warum `get()` in der Regel billig ist. `get(descriptor)` gibt den zwischengespeicherten Wert aus dem serverseitigen Speicher ohne Netzwerkaufruf zurück, da jeder `set()` den Cache mit dem Client synchron hält. Das optionale `boolean`-Zweitaruments steuert, ob der Cache umgangen werden soll und aus dem Browser gelesen wird.

```java
String cached = get(heading);            // liest aus dem serverseitigen Cache
String live = get(heading, true);        // zwingt eine Leseoperation aus dem Browser
```

Setzen Sie `fromClient` auf true, wenn der Wert im Client ohne Wissen des Servers geändert werden kann, wie z. B. ein getipptes `<input>`-Wert. Für servergesteuerte Eigenschaften vermeidet der Standard eine Hin- und Rückfahrt.

Das optionale dritte Argument ist ein `java.lang.reflect.Type` und steuert, wie das Ergebnis deserialisiert wird. webforJ löst den Typ in dieser Reihenfolge auf: das explizite `Type`-Argument, falls übergeben, dann der zur Laufzeit aufgezeichnete Typ von einem vorherigen `set()` auf derselben Beschreibung, dann `Object.class`. In der Praxis reicht der vorherige `set()`-Typ aus, sodass das dritte Argument in der Regel weggelassen werden kann. Es ist erforderlich, wenn die gespeicherte Klasse Informationen verliert, von denen der Deserialisierer abhängt, z. B. ein parametrisiertet Typ wie `List<String>`, dessen Laufzeitklasse nur `ArrayList` ist.

Die Demo unten fügt relativ zeitbasierte Eigenschaften entsprechend der Dokumentation der Webkomponente hinzu und stellt sie über Getter und Setter zur Verfügung. Jede Zeile im Aktivitäts-Feed verwendet unterschiedliche `format`- und `numeric`-Werte, um zu zeigen, wie die gleiche Komponente unter verschiedenen Konfigurationen gerendert wird.

<ComponentDemo 
path='/webforj/relativetimeproperties' 
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/resources/static/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Eigenschaften versus Attribute {#properties-versus-attributes}

Obwohl `PropertyDescriptor.property()` und `PropertyDescriptor.attribute()` austauschbar erscheinen, zielen sie auf unterschiedliche Teile des zugrunde liegenden Elements ab. Die falsche Wahl führt zu Werten, die lautlos nicht angewendet werden.

Eigenschaften sind JavaScript-Objekteigenschaften des DOM-Knotens. Sie können jeden Typ halten, einschließlich Strings, Booleans, Zahlen, Objekten und Arrays, und sie repräsentieren den aktuellen Laufzeitstatus des Elements. Das Setzen einer Eigenschaft ist eine direkte JavaScript-Zuweisung.

Attribute sind HTML-Markup. Sie leben am öffnenden Tag des Elements, sind immer Strings und repräsentieren die ursprüngliche Konfiguration des Elements. Das Setzen eines Attributs löst eine DOM-Veränderung und eine String-Konvertierung aus.

In einigen Fällen bleiben die beiden synchron. In anderen weichen sie voneinander ab. Der `value` eines `<input>` ist das klassische Beispiel: Das `value`-Attribut ist der ursprüngliche Wert, während die `value`-Eigenschaft der aktuelle Wert ist, den der Benutzer eingegeben hat. Das Lesen des Attributs nach der Eingabe des Benutzers gibt das ursprüngliche Markup zurück, aber das Lesen der Eigenschaft gibt den aktuellen Inhalt des Feldes zurück.

Verwenden Sie **Eigenschaften** für:

- **Häufig wechselnden Laufzeitzustand**: Zähler, aktuelle Auswahlen, eingegebene Werte
- **Nicht-String-Typen**: Booleans, Zahlen, Objekte, Arrays
- **Leistungsoptimierte Updates**: Eigenschaften umgehen die notwendige String-Konvertierung für Attribute

Verwenden Sie **Attribute** für:

- **Ursprüngliche Konfiguration**: Einstellungen, die die Komponente einmal liest, wenn sie verbunden wird
- **CSS-Selektoren**: Werte, die Sie mit Selektoren wie `[disabled]` oder `[variant="danger"]` anvisieren möchten
- **Zugänglichkeits-Hooks**: `aria-label`, `role` und andere ARIA-Attribute
- **String-ähnliche Einstellungen, die sich selten ändern**

Wenn Sie eine Drittanbieter-Webkomponente umschließen, überprüfen Sie die Dokumentation der Komponente, um zu bestätigen, welcher Name einer Eigenschaft und welcher einem Attribut zugeordnet ist. Die Verwendung von `PropertyDescriptor.attribute()` für etwas, das die Komponente nur als Eigenschaft bereitstellt, funktioniert nicht, und dasselbe gilt umgekehrt. Die Komponente ignoriert den Wert stillschweigend.

### Typisierung von Eigenschaften {#typing-properties}

Eine Beschreibung wird durch den Java-Typ ihres Wertes parametrisiert. Die vollständige Deklarationssyntax lautet:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Der `<T>`-Generikparameter deklarierte den Typ des Wertes. Der Laufzeittyp des Standardwerts fixiert auch `T`, sodass das generische Argument selten explizit angegeben werden muss. webforJ verwendet `T`, um Werte zu serialisieren und zu deserialisieren, wenn es mit dem Client kommuniziert.

```java
private final PropertyDescriptor<String> label =
    PropertyDescriptor.property("label", "");

private final PropertyDescriptor<Boolean> disabled =
    PropertyDescriptor.property("disabled", false);

private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

private final PropertyDescriptor<Double> step =
    PropertyDescriptor.property("step", 1.0);
```

Die Serialisierung erfolgt automatisch für primitive Typen, deren verpackte Äquivalente und `String`. Für komplexe Typen wird der Wert als JSON serialisiert, bevor er der Eigenschaft auf dem Client zugewiesen wird.

### Validierung von Werten {#validating-values}

Validieren Sie Werte im Setter, bevor Sie `set()` aufrufen. Der Setter ist der natürliche Durchsetzungspunkt, da jede Mutation durch ihn fließt.

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max muss nicht negativ sein");
  }
  set(max, value);
  return this;
}
```

Für nullable Referenzen verwenden Sie `Objects.requireNonNull()`, damit der Fehler an der Grenze auftritt, anstatt später in der Render-Pipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading darf nicht null sein");
  set(heading, value);
  return this;
}
```

Vermeiden Sie eine Validierung in `get()`. Lesevorgänge sollten günstig und konsistent bleiben.

### Enum-ähnliche Eigenschaften {#enum-style-properties}

Die meisten Webkomponenten erwarten Kleinbuchstaben oder kebab-case-Strings für enum-ähnliche Eigenschaften (`theme="primary"`, `expanse="xs"`). webforJ verwendet Gson zur Serialisierung von Enums, aber die Standarddarstellung von Gson ist der Konstantenname in Großbuchstaben. Annotieren Sie jede Konstante mit `@SerializedName`, damit der serialisierte Wert den Erwartungen der Webkomponente entspricht.

```java
import com.google.gson.annotations.SerializedName;

public enum Variant {
  @SerializedName("primary")
  PRIMARY,

  @SerializedName("secondary")
  SECONDARY,

  @SerializedName("danger")
  DANGER
}
```

Deklarieren Sie die Beschreibung mit dem Enum-Typ und verwenden Sie das Enum direkt im Setter und Getter.

```java
private final PropertyDescriptor<Variant> variant =
    PropertyDescriptor.property("variant", Variant.PRIMARY);

public MyButton setVariant(Variant value) {
  set(variant, value);
  return this;
}

public Variant getVariant() {
  return get(variant);
}
```

Dies ist dasselbe Muster, das die integrierten Komponenten von webforJ für `Theme`, `Expanse` und ähnliche Enums verwenden. Die öffentliche Java-API bleibt typsicher, und der Wert, den die Webkomponente erhält, ist der String von `@SerializedName`.

### Testen von Eigenschaften {#testing-properties}

`PropertyDescriptorTester` validiert, dass jeder `PropertyDescriptor` in einer Komponente korrekt verkabelt ist. Er durchsucht die Klasse nach Beschreibungsfeldern, ruft jeden Setter mit dem Standardwert auf und vergleicht das Ergebnis mit dem, was der Getter zurückgibt. Der Tester erkennt Integrationsfehler, bevor sie eine laufende App erreichen: ein Setter, der in die falsche Beschreibung schreibt, ein Getter, der eine andere Eigenschaft liest, ein Standardwert, der nicht in beide Richtungen funktioniert, oder einen fehlenden Accessor für eine deklarierte Beschreibung.

Ein Basistest für eine Komponente sieht wie folgt aus:

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void validateProperties() {
    Card component = new Card();
    PropertyDescriptorTester.run(Card.class, component);
  }
}
```

#### Ausschließen von Eigenschaften {#excluding-properties}

Einige Beschreibungen folgen nicht den Standardkonventionen für Getter und Setter oder hängen von externen Zuständen ab, die der Test nicht erfüllen kann. Annotieren Sie sie mit `@PropertyExclude`, um sie zu überspringen.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Benutzerdefinierte Getter- und Setter-Namen {#custom-getter-and-setter-names}

Wenn eine Beschreibung nicht-standardisierte Zugriffsname verwendet, deklarieren Sie sie mit `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "standard");
```

Der `target`-Parameter akzeptiert eine Klasse, wenn die Accessoren an einem anderen Ort als der Komponente selbst leben.

Für weitere Informationen zur Testoberfläche siehe [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interessen-Schnittstellen {#concern-interfaces}

Interesse-Schnittstellen verleihen einer `ElementComposite`-Unterklasse Funktionen, ohne die Implementierung selbst schreiben zu müssen. Die Schnittstellen leiten die Aufrufe an das zugrunde liegende Element weiter. Implementieren Sie die, die die Komponente unterstützen soll, parametrisiert mit dem Unterklassatyp, sodass die Verkettung die Komponente zurückgibt:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Keine Implementierung erforderlich.
}

MyBadge badge = new MyBadge()
    .setText("Neu")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Die drei oben genannten Schnittstellen decken alles ab, was `MyBadge` benötigt, ohne Methodenimplementierungen in der Klasse. `HasText` stellt `setText()` zur Verfügung und schreibt in den Textinhalt des Elements. `HasClassName` stellt `addClassName()` bereit, mit dem das Badge von CSS angesprochen werden kann. `HasStyle` stellt `setStyle()` für Inline-Styling bereit.

Für die vollständige Liste der verfügbaren Schnittstellen und was jede bietet, siehe [Interesse-Schnittstellen](./component-fundamentals#concern-interfaces) im Artikel "Komponenten verstehen". Wenn eine Standardweiterleitung nicht dem entspricht, was das umschlossene Element bereitstellt, überschreiben Sie die Methode in der Unterklasse.

## Ereignisse {#events}

### Ereignisregistrierung {#event-registration}

Webkomponenten senden DOM-Ereignisse aus, wenn im Browser etwas passiert. Um von Java aus zu reagieren, hören Sie auf diese Ereignisse mit `addEventListener()`. Die Menge an Ereignissen, die eine Komponente abfeuert, variiert, daher überprüfen Sie die Dokumentation der Komponente selbst auf die verfügbaren Namen und Payloads.

`ElementComposite` unterstützt Debouncing, Throttling, Filtering und benutzerdefinierte Ereignisdaten in registrierten Listenern.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klickereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Behandeln Sie das Klickereignis
});
```

:::info
`ElementComposite` akzeptiert nur Ereignisklassen, die mit `@EventName` annotiert sind, im Gegensatz zu `Element`, das jeden String-Ereignisnamen akzeptiert.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

`ElementClickEvent` ist die einzige eingebaute Ereignisklasse, die `ElementComposite` mitliefert. Sie gibt Mausklickereignisse auf dem zugrunde liegenden Element mit typisierten Zugriffsmethoden für Koordinaten (`getClientX()`, `getClientY()`), Button-Informationen (`getButton()`) und Modifikatortasten (`isCtrlKey()`, `isShiftKey()` usw.) aus.

Um die Klickbehandlung in der öffentlichen API einer Unterklasse bereitzustellen, implementieren Sie die Schnittstelle `HasElementClickListener<T>`. Sie bietet standardmäßige `onClick()` und `addClickListener()`-Methoden, die an die geschützte `addEventListener()`-primitive Methode delegieren.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() und addClickListener() sind jetzt auf MyBadge verfügbar
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Für jedes andere Ereignis, das die zugrunde liegende Webkomponente auslöst, definieren Sie eine benutzerdefinierte Ereignisklasse. Siehe [Benutzerdefinierte Ereignisklassen](#custom-event-classes).

### Ereignis-Payloads {#event-payloads}

Ereignisse tragen Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf diese Daten für rohe Ereignisdaten zu oder verwenden Sie typisierte Methoden, wenn sie in den eingebauten Ereignisklassen verfügbar sind. Siehe den [Ereignisleitfaden](../building-ui/events) für mehr Informationen zur effizienten Verarbeitung von Payloads.

### Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Definieren Sie benutzerdefinierte Ereignisklassen mit `@EventName` und `@EventOptions`, um clientseitige Daten in ein typisiertes Java-Ereignis zu erfassen. Verwenden Sie dies, wenn der Java-Handler Werte aus dem Browser benötigt.

`@EventName` bindet die Java-Klasse an das Ereignis, das die Komponente im Browser auslöst. Eine Klasse, die mit `@EventName("sl-change")` annotiert ist, wird jedes Mal ausgeführt, wenn das zugrunde liegende Element `sl-change` auslöst. `@EventOptions` steuert, was mit diesem Ereignis zurückreist. Jedes `@EventData` darin paart einen Schlüssel mit einem JavaScript-Ausdruck, der gegen das DOM-Ereignis ausgewertet wird. Das Ergebnis ist über `getData().get(key)` in der Java-Ereignisklasse verfügbar.

Das Produktbewertungsformular unten verwendet dieses Muster mit [`sl-rating`](https://shoelace.style/components/rating). Das benutzerdefinierte `ChangeEvent` trägt den Bewertungswert als typisierte `double`, und der Listener verwendet dies, um die Schaltfläche "Absenden" zu aktivieren:

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Ereignisoptionen {#event-options}

`ElementEventOptions` konfiguriert die Ereignispayload, Debounce- oder Throttle-Zeit, Filterausdrücke und Pre-Execution-Code. Der folgende Snippet zeigt die Optionen:

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
  
  // Verzögerte Ausführung, bis der Benutzer mit dem Tippen aufhört (300 ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Wenden Sie diese Optionen beim Registrieren eines Listeners für eine benutzerdefinierte Ereignisklasse an
// (siehe den Abschnitt "Benutzerdefinierte Ereignisklassen" oben, um zu erfahren, wie man eine definiert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` stellt nur die klassenbasierte Form `addEventListener(Class, listener, options)` zur Verfügung. Verwenden Sie sie mit einer Ereignisklasse, die mit `@EventName` annotiert ist. Um direkt gegen einen String-Ereignisnamen zu registrieren, rufen Sie `getElement().addEventListener("input", listener, options)` auf.
:::

#### Leistungssteuerung {#performance-control}

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Warte 300 ms nach dem letzten Ereignis
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen und dann warten
- `TRAILING`: Auf ruhige Periode warten und dann feuern (Standard)
- `BOTH`: Sofort feuern und nach der ruhigen Periode feuern

**Throttling** begrenzt die Häufigkeit der Ausführung:

```java
options.setThrottle(100); // Höchstens einmal alle 100 ms auslösen
```

## Interagieren mit Slots {#interacting-with-slots}

Slots sind Platzhalter innerhalb einer Webkomponente, die Benutzer mit Inhalten füllen. Die Webkomponente erklärt ihre Slots in ihrem Template mit `<slot>` oder `<slot name="...">`, und der Wrapper stellt Methoden zur Verfügung, die Java-Komponenten in diese Slots einfügen.

Um Inhalte zu Slots hinzuzufügen, erweitern Sie `ElementCompositeContainer` anstelle von `ElementComposite`. Der Container trägt die gleiche Eigenschaften- und Attributmaschinerie sowie die Methoden, die benötigt werden, um Kinder hinzuzufügen. Über `add()` hinzugefügte Kinder gehen in den Standardslot. Über `getElement().add(slotName, components)` hinzugefügte Kinder gehen in den benannten Slot.

```java
@NodeName("my-dialog")
public class Dialog extends ElementCompositeContainer {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Dialog setHeading(String value) {
    set(heading, value);
    return this;
  }

  public Dialog addToFooter(Component... components) {
    getElement().add("footer", components);
    return this;
  }
}
```

Die Demo unten zeigt zwei Preiskarten, die mit [`sl-card`](https://shoelace.style/components/card) erstellt wurden, und füllt die `header`, standart und `footer` Slots aus Java:

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Überprüfen des Inhalts in Slots {#inspecting-slot-contents}

Das zugrunde liegende `Element` (erreichbar über `getElement()`) stellt Methoden bereit, um zurückzugeben, was derzeit den Slots zugewiesen ist:

- **`findComponentSlot()`**: durchsucht alle Slots nach einer bestimmten Komponente und gibt den Namen des Slots zurück, der sie enthält, oder einen leeren String, wenn die Komponente sich in keinem Slot befindet.
- **`getComponentsInSlot()`**: gibt die Liste der Komponenten zurück, die einem bestimmten Slot zugewiesen sind. Optional kann ein Klassentyp angegeben werden, um die Ergebnisse zu filtern.
- **`getFirstComponentInSlot()`**: gibt die erste Komponente zurück, die einem Slot zugewiesen ist. Optional kann ein Klassentyp angegeben werden, um zu filtern.
