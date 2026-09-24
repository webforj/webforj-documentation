---
sidebar_position: 6
title: Element-Komposition
sidebar_class_name: new-content
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2f1ddb4b3375c89dc29d9dbc9cee7303
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die `ElementComposite`-Klasse umhüllt ein benutzerdefiniertes HTML-Element oder [Webkomponente](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Sie bindet Ihre Java-Klasse an das zugrunde liegende `Element` und ermöglicht Ihnen, mit den Eigenschaften, Attributen und Ereignissen dieses Elements über Java zu arbeiten. Verwenden Sie sie, wenn Sie Webkomponenten in eine WebforJ-Anwendung integrieren.

:::tip Wann man `ElementComposite` verwenden sollte
Greifen Sie zu `ElementComposite`, wenn Sie eine Drittanbieter-Webkomponente umhüllen, die WebforJ nicht bereits bereitstellt. Wenn eine integrierte WebforJ-Komponente den Anwendungsfall abdeckt (`TextField`, `ColorField`, `Button` usw.), verwenden Sie diese stattdessen. Für einmalige DOM-Arbeiten, die nicht wiederverwendet werden müssen, kann die `Element`-Klasse direkt ohne eine Hülle verwendet werden.
:::

Dieser Leitfaden zeigt, wie Sie die [Web Awesome Webkomponente für relative Zeit](https://webawesome.com/docs/components/relative-time/) mit der `ElementComposite`-Klasse implementieren.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klassennotationen {#class-annotations}

Drei Notationen erscheinen häufig am Anfang einer `ElementComposite`-Unterklasse: `@NodeName` erklärt das HTML-Tag, das die Komponente umhüllt, und `@JavaScript` sowie `@StyleSheet` laden alle clientseitigen Ressourcen, von denen die zugrunde liegende Webkomponente abhängt. `@NodeName` ist erforderlich und spezifisch für `ElementComposite`. `@JavaScript` und `@StyleSheet` sind allgemeine WebforJ-Ressourcennotationen und funktionieren in jeder Klasse, einschließlich Ansichten, Komponenten oder der `App`-Klasse.

### `@NodeName` {#nodename}

Die `@NodeName`-Annotation erklärt das HTML-Tag, das die Komponente umhüllt. WebforJ verwendet diesen Namen, wenn es das zugrunde liegende Element im DOM erstellt.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Der Tagname muss mit dem benutzerdefinierten Element übereinstimmen, das auf der Clientseite registriert ist. Ohne diese Annotation kann das Framework nicht bestimmen, welches Element erstellt werden soll.

Innerhalb einer Unterklasse liest `getNodeName()` das deklarierte Tag zurück, und `getElement()` gibt das zugrunde liegende `Element` zurück, sodass Sie direkt DOM-Methoden darauf aufrufen können.

### `@JavaScript` {#javascript}

Die `@JavaScript`-Annotation lädt das Skript, das die zugrunde liegende Webkomponente definiert oder registriert. Platzieren Sie es auf der Klasse, damit das Skript nur geladen wird, wenn die Komponente verwendet wird.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Es sind mehrere `@JavaScript`-Annotationen erlaubt, und WebforJ entfernt doppelte Ladungen automatisch. Das gleiche Skript wird nicht zweimal geladen, wenn mehrere Komponenten davon abhängen.

Siehe [JavaScript-Dateien importieren](../managing-resources/importing-assets#importing-javascript-files) für die vollständige Liste der Optionen, einschließlich `top`, `attributes` und Ladezeitpunkt.

### `@StyleSheet` {#stylesheet}

Die `@StyleSheet`-Annotation lädt eine CSS-Datei, von der die Komponente abhängt. Sie ist nützlich für Drittanbieterkomponenten, die ein separates Stylesheet mitliefern, oder für das Bündeln von komponentenspezifischem Styling zusammen mit der Hülle.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Für lokal gebündelte Ressourcen verwenden Sie das Präfix `ws://`, um auf Dateien in `resources/static` zuzugreifen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Siehe [CSS-Dateien importieren](../managing-resources/importing-assets#importing-css-files) für die vollständige Liste der Optionen.

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute repräsentieren den Zustand einer Webkomponente und halten typischerweise Daten oder Konfigurationen. `ElementComposite` legt beide über `PropertyDescriptor` frei.

Zwei Fabrikmethoden auf `PropertyDescriptor` erzeugen den Descriptor selbst, eine für jedes Bindungsziel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindet an eine JavaScript-Eigenschaft des DOM-Knotens. `PropertyDescriptor.attribute()` bindet an ein HTML-Attribut. Das erste Argument ist der Name, den die Webkomponente erwartet. Das zweite ist ein Standardwert, der auch den Java-Typ des Descriptors festlegt.

Deklarieren Sie den Descriptor als private Feld in der Komponente und lesen und schreiben Sie über `set(PropertyDescriptor<V> property, V value)` und `get(PropertyDescriptor<V> property)`.

:::info
Eigenschaften sind interner Zustand am DOM-Knoten und spiegeln sich nicht im Markup wider. Attribute sind HTML-Markup, das für externe Skripte und CSS sichtbar ist.
:::

```java
// Beispielhafte Eigenschaft "title" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Beispielhaftes Attribut "value" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mein Titel");
set(value, "Mein Wert");
```

Die obigen Aufrufe verwenden `set()` direkt, um die primitive Form zu zeigen. In der Praxis sind `set()` und `get()` `protected`-Methoden auf `ElementComposite`. Sie sind die primitive Schicht, die die Java-Werte mit dem zugrunde liegenden Element synchronisiert, nicht die öffentliche API, die Verbraucher aufrufen. Das beabsichtigte Muster ist, den `PropertyDescriptor` privat zu halten und öffentliche `setX()`- und `getX()`-Methoden zu schreiben, die an die Primitiven delegieren.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // geschützte primitive
    return this;
  }

  public String getHeading() {
    return get(heading);     // geschützte primitive
  }
}
```

Ein einzelner Aufruf von `set(descriptor, value)` erledigt drei Dinge auf einmal. Es schickt den Wert an den Client über `setProperty()` für Eigenschaften oder `setAttribute()` für Attribute. Es speichert den Wert in einem lokalen Cache auf der Serverseite, eine Map pro Komponenteninstanz. Und es zeichnet den Laufzeittyp zusammen mit dem Wert auf, sodass spätere `get()`-Aufrufe wissen, wie sie deserialisieren müssen.

Dieser lokale Cache ist der Grund, warum `get()` standardmäßig kostengünstig sein kann. `get(descriptor)` gibt den zwischengespeicherten Wert aus dem Cache auf der Serverseite zurück, ohne einen Netzwerkaufruf, da jedes `set()` den Cache mit dem Client synchron hält. Das optionale `boolean`-Zweite Argument steuert, ob der Cache umgangen und stattdessen vom Browser gelesen werden soll.

```java
String cached = get(heading);            // liest aus dem Server-Cache
String live = get(heading, true);        // zwingt eine Lesung vom Browser
```

Setzen Sie `fromClient` auf true, wenn der Wert auf dem Client ohne Wissen des Servers geändert werden kann, wie zum Beispiel bei einem eingegebenen `<input>`-Wert. Für servergesteuerte Eigenschaften vermeidet die Standardoption eine Rundreise.

Das optionale dritte Argument ist ein `java.lang.reflect.Type` und steuert, wie das Ergebnis deserialisiert wird. WebforJ löst den Typ in dieser Reihenfolge auf: das explizite `Type`-Argument, wenn es übergeben wird, dann der zur Laufzeit aufgezeichnete Typ, der durch ein vorhergehendes `set()` des gleichen Descriptors festgelegt wurde, dann `Object.class`. In der Praxis ist der Typ, der bei einem vorherigen `set()` aufgezeichnet wurde, ausreichend, sodass das dritte Argument normalerweise weggelassen werden kann. Es ist erforderlich, wenn die aufgezeichnete Klasse Informationen verliert, auf die der Deserializer angewiesen ist, wie z. B. einen parametrisierten Typ wie `List<String>`, dessen Laufzeittyp einfach `ArrayList` ist.

Die folgende Demo fügt Eigenschaften für die relative Zeit basierend auf den Dokumenten der Webkomponente hinzu und stellt sie über Getter und Setter zur Verfügung. Jede Zeile im Aktivitätsfeed verwendet unterschiedliche `format`- und `numeric`-Werte, um zu zeigen, wie dieselbe Komponente unter verschiedenen Konfigurationen gerendert wird.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Eigenschaften im Vergleich zu Attributen {#properties-versus-attributes}

Obwohl `PropertyDescriptor.property()` und `PropertyDescriptor.attribute()` austauschbar erscheinen, zielen sie auf unterschiedliche Teile des zugrunde liegenden Elements ab. Die Wahl des falschen führt zu Werten, die stillschweigend nicht angewendet werden.

Eigenschaften sind JavaScript-Objekteigenschaften am DOM-Knoten. Sie können jeden Typ halten, einschließlich Zeichenfolgen, Booleans, Zahlen, Objekten und Arrays, und sie repräsentieren den aktuellen Laufzeitstatus des Elements. Das Setzen einer Eigenschaft ist eine direkte JavaScript-Zuweisung.

Attribute sind HTML-Markup. Sie leben am öffnenden Tag des Elements, sind immer Zeichenfolgen und repräsentieren die ursprüngliche Konfiguration des Elements. Das Setzen eines Attributs löst eine DOM-Änderung und eine Zeichenfolgenumwandlung aus.

In einigen Fällen bleiben die beiden synchron. In anderen divergenzieren sie. Der `value` eines `<input>` ist das klassische Beispiel: Das `value`-Attribut ist der ursprüngliche Wert, während die `value`-Eigenschaft den aktuellen Wert darstellt, den der Benutzer eingegeben hat. Das Lesen des Attributs, nachdem der Benutzer getippt hat, gibt das ursprüngliche Markup zurück, aber das Lesen der Eigenschaft gibt den aktuellen Inhalt des Feldes zurück.

Verwenden Sie **Eigenschaften** für:

- **Häufig ändernder Laufzeitstatus**: Zähler, aktuelle Auswahlen, eingegebene Werte
- **Nicht-Zeichenfolgen-Typen**: Booleans, Zahlen, Objekte, Arrays
- **Leistungsempfindliche Updates**: Eigenschaften umgehen die für Attribute erforderliche Zeichenfolgenumwandlung

Verwenden Sie **Attribute** für:

- **Ursprüngliche Konfiguration**: Einstellungen, die die Komponente einmal beim Verbinden liest
- **CSS-Selektoren**: Werte, die Sie mit Selektoren wie `[disabled]` oder `[variant="danger"]` ansprechen möchten
- **Zugänglichkeits-Hooks**: `aria-label`, `role` und andere ARIA-Attribute
- **Zeichenfolgenähnliche Einstellungen, die sich selten ändern**

Überprüfen Sie beim Umhüllen einer Drittanbieter-Webkomponente die Dokumentation der Komponente, um zu bestätigen, welcher Name einer Eigenschaft und welcher einem Attribut zugeordnet wird. Die Verwendung von `PropertyDescriptor.attribute()` für etwas, das die Komponente nur als Eigenschaft offengelegt hat, funktioniert nicht, und umgekehrt. Die Komponente wird den Wert stillschweigend ignorieren.

### Typisierung von Eigenschaften {#typing-properties}

Ein Descriptor wird durch den Java-Typ seines Wertes parametrisiert. Die vollständige Deklarationssyntax lautet:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Der `<T>`-Generikparameter erklärt den Typ des Wertes. Der Laufzeittyp des Standardwerts festlegt ebenfalls `T`, sodass das generische Argument selten explizit angegeben werden muss. WebforJ verwendet `T`, um Werte zu serialisieren und zu deserialisieren, wenn mit dem Client kommuniziert wird.

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

Die Serialisierung erfolgt automatisch für Primitive, ihre verpackten Äquivalente und `String`. Für komplexe Typen wird der Wert als JSON serialisiert, bevor er der Eigenschaft auf dem Client zugewiesen wird.

### Werte validieren {#validating-values}

Validieren Sie Werte im Setter, bevor Sie `set()` aufrufen. Der Setter ist der natürliche Durchsetzungspunkt, da jede Mutation über ihn fließt.

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

Für nullable Referenzen verwenden Sie `Objects.requireNonNull()`, sodass der Fehler an der Grenze auftaucht und nicht später in der Rendering-Pipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading kann nicht null sein");
  set(heading, value);
  return this;
}
```

Vermeiden Sie Validierungen in `get()`. Leseoperationen sollten kostengünstig und konsistent bleiben.

### Enum-ähnliche Eigenschaften {#enum-style-properties}

Die meisten Webkomponenten erwarten Kleinbuchstaben oder Kebab-Case-Zeichenfolgenwerte für enum-artige Eigenschaften (`theme="primary"`, `expanse="xs"`). WebforJ verwendet Gson zur Serialisierung von Enums, aber die Standarddarstellung von Gson ist der Konstantenname in Großbuchstaben. Annotieren Sie jede Konstante mit `@SerializedName`, damit der serialisierte Wert dem entspricht, was die Webkomponente erwartet.

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

Erklären Sie den Descriptor mit dem Enum-Typ und verwenden Sie das Enum direkt im Setter und Getter.

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

Dies ist dasselbe Muster, das WebforJs integrierte Komponenten für `Theme`, `Expanse` und ähnliche Enums verwenden. Die öffentliche Java-API bleibt typensicher, und der Wert, den die Webkomponente erhält, ist die Zeichenfolge aus `@SerializedName`.

### Eigenschaften testen {#testing-properties}

`PropertyDescriptorTester` validiert, dass jeder `PropertyDescriptor` in einer Komponente korrekt verkabelt ist. Es durchsucht die Klasse nach Descriptorfeldern, ruft jeden Setter mit dem Standardwert auf und vergleicht das Ergebnis mit dem, was der Getter zurückgibt. Der Tester fängt Integrationsfehler ein, bevor sie eine laufende App erreichen: ein Setter, der in den falschen Descriptor schreibt, ein Getter, der eine andere Eigenschaft liest, ein Standardwert, der nicht rund läuft, oder ein fehlender Zugriff für einen deklarierten Descriptor.

Ein Basistest für eine Komponente sieht so aus:

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

#### Eigenschaften ausschließen {#excluding-properties}

Einige Deskriptoren folgen nicht den Standard-Konventions für Getter und Setter oder sind von externen Zuständen abhängig, die der Test nicht erfüllen kann. Annotieren Sie sie mit `@PropertyExclude`, um sie zu überspringen.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Benutzerdefinierte Getter- und Setter-Namen {#custom-getter-and-setter-names}

Wenn ein Descriptor nicht-standardmäßige Zugriffsnamen verwendet, erklären Sie sie mit `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Der `target`-Parameter akzeptiert eine Klasse, wenn die Zugriffsmethoden nicht in der Komponente selbst leben.

Für weitere Details zur Testoberfläche siehe [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Anwendungsfälle von Concern-Interfaces {#concern-interfaces}

Concern-Interfaces geben einer `ElementComposite`-Unterklasse Funktionen, ohne die Implementierung selbst schreiben zu müssen. Die Interfaces leiten Aufrufe an das zugrunde liegende Element weiter. Implementieren Sie die, die die Komponente unterstützen soll, parametrisiert mit dem Subtyp, damit das Chaining die Komponente zurückgibt:

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

Die drei obigen Interfaces decken alles ab, was `MyBadge` benötigt, ohne dass im Klassentext Methoden implementiert werden müssen. `HasText` stellt `setText()` zur Verfügung und schreibt in den Textinhalt des Elements. `HasClassName` stellt `addClassName()` zur Verfügung, mit dem die Auszeichnung von CSS angesteuert werden kann. `HasStyle` stellt `setStyle()` für Inline-Styles zur Verfügung.

Für die vollständige Liste der verfügbaren Interfaces und was jedes bietet, siehe [Concern-Interfaces](./component-fundamentals#concern-interfaces) im Artikel zu Verstehen von Komponenten. Wenn ein Standard-Forwarding nicht dem entspricht, was das umhüllte Element bereitstellt, überschreiben Sie die Methode in der Unterklasse.

## Ereignisse {#events}

### Ereignisregistrierung {#event-registration}

Webkomponenten senden DOM-Events, wenn im Browser etwas passiert. Um in Java darauf zu reagieren, hören Sie auf diese Ereignisse mit `addEventListener()`. Die Menge der Ereignisse, die eine Komponente ausgibt, variiert; überprüfen Sie daher die eigenen Dokumente der Komponente auf die verwendeten Namen und Payloads.

`ElementComposite` unterstützt Debouncing, Throttling, Filtering und benutzerdefinierte Ereignisdaten bei registrierten Listenern.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klickereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Klickereignis verarbeiten
});
```

:::info
`ElementComposite` akzeptiert nur Ereignisklassen, die mit `@EventName` annotiert sind, im Gegensatz zu `Element`, das jeden String-Ereignisnamen akzeptiert.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

`ElementClickEvent` ist die einzige eingebaute Ereignisklasse, die `ElementComposite` mitliefert. Sie gibt Mausklickereignisse auf dem zugrunde liegenden Element mit typisierten Zugriffsmethoden für Koordinaten (`getClientX()`, `getClientY()`), Informationen zu Schaltflächen (`getButton()`) und Modifikatortasten (`isCtrlKey()`, `isShiftKey()` usw.) zurück.

Um die Klickverarbeitung in der öffentlichen API einer Unterklasse freizulegen, implementieren Sie das `HasElementClickListener<T>`-Concern-Interface. Es stellt Standardmethoden `onClick()` und `addClickListener()` bereit, die an das geschützte `addEventListener()` primitive delegieren.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() und addClickListener() stehen jetzt auf MyBadge zur Verfügung
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Für jedes andere Ereignis, das die zugrunde liegende Webkomponente ausgibt, definieren Sie eine benutzerdefinierte Ereignisklasse. Siehe [Benutzerdefinierte Ereignisklassen](#custom-event-classes).

### Ereignis-Payloads {#event-payloads}

Ereignisse tragen Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf diese Daten zu oder verwenden Sie die typisierten Methoden, wenn sie in den eingebauten Ereignisklassen verfügbar sind. Siehe den [Ereignisse-Leitfaden](../building-ui/events) für mehr zum effizienten Umgang mit Payloads.

### Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Definieren Sie benutzerdefinierte Ereignisklassen mit `@EventName` und `@EventOptions`, um clientseitige Daten in einem typisierten Java-Ereignis zu erfassen. Verwenden Sie dies, wenn der Java-Handler Werte aus dem Browser benötigt.

`@EventName` bindet die Java-Klasse an das Ereignis, das die Komponente im Browser ausgibt, sodass eine Klasse, die mit `@EventName("change")` annotiert ist, jedes Mal ausgelöst wird, wenn das zugrunde liegende Element `change` ausgibt. `@EventOptions` steuert, was mit diesem Ereignis zurückgegeben wird. Jedes `@EventData` darin paart einen Schlüssel mit einem JavaScript-Ausdruck, der gegen das DOM-Ereignis ausgewertet wird. Das Ergebnis ist in der Java-Ereignisklasse über `getData().get(key)` verfügbar.

Das Produktbewertungsformular unten verwendet dieses Muster mit [`wa-rating`](https://webawesome.com/docs/components/rating/). Das benutzerdefinierte `ChangeEvent` trägt den Bewertungswert als typisiertes `double`, und der Listener verwendet ihn, um die Schaltfläche zum Absenden zu aktivieren:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Ereignisoptionen {#event-options}

`ElementEventOptions` konfiguriert die Ereignispayload, Debounce- oder Throttle-Timings, Filterausdrücke und Pre-Execution-Code. Der folgende Snippet zeigt die Optionen:

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

  // Verzögern Sie die Ausführung, bis der Benutzer mit dem Tippen aufhört (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Wenden Sie diese Optionen beim Registrieren eines Listeners für eine benutzerdefinierte Ereignisklasse an
// (siehe den Abschnitt Benutzerdefinierte Ereignisklassen oben, um zu erfahren, wie man eine definiert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` bietet nur die klassenspezifische Form `addEventListener(Class, listener, options)`. Verwenden Sie es mit einer Ereignisklasse, die mit `@EventName` annotiert ist. Um direkt gegen einen String-Ereignisnamen zu registrieren, rufen Sie `getElement().addEventListener("input", listener, options)` auf.
:::

#### Leistungssteuerung {#performance-control}

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 300ms warten nach dem letzten Ereignis
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Auf ruhige Zeit warten, dann auslösen (Standard)
- `BOTH`: Sofort und nach ruhiger Zeit auslösen

**Throttling** beschränkt die Ausführungsfrequenz:

```java
options.setThrottle(100); // Höchstens einmal pro 100ms auslösen
```

## Interagieren mit Slots {#interacting-with-slots}

Slots sind Platzhalter in einer Webkomponente, die Benutzer mit Inhalten füllen können. Die Webkomponente erklärt ihre Slots in ihrem Template mit `<slot>` oder `<slot name="...">`, und die Hülle stellt Methoden zur Verfügung, um Java-Komponenten in diese Slots zu setzen.

Um Inhalte zu Slots hinzuzufügen, erweitern Sie `ElementCompositeContainer` anstelle von `ElementComposite`. Der Container trägt die gleiche Eigenschaften- und Attributtechnik plus die Methoden, die benötigt werden, um Kinder hinzuzufügen. Über `add()` hinzugefügte Kinder gelangen in den Standardslot. Über `getElement().add(slotName, components)` hinzugefügte Kinder gelangen in den benannten Slot.

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

Die Demo unten zeigt zwei Preiskarten, die mit [`wa-card`](https://webawesome.com/docs/components/card/) erstellt wurden, wobei die `header`, der Standard- und der `footer`-Slot aus Java befüllt werden:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Slot-Inhalte inspizieren {#inspecting-slot-contents}

Das zugrunde liegende `Element` (erreichbar über `getElement()`) bietet Methoden zum Zurücklesen dessen, was derzeit den Slots zugeordnet ist:

- **`findComponentSlot()`**: durchsucht alle Slots nach einer bestimmten Komponente und gibt den Namen des Slots zurück, der sie enthält, oder eine leere Zeichenfolge, wenn die Komponente nicht in einem Slot ist.
- **`getComponentsInSlot()`**: gibt die Liste der Komponenten zurück, die einem bestimmten Slot zugeordnet sind. Optional kann eine Klassentyp angegeben werden, um die Ergebnisse zu filtern.
- **`getFirstComponentInSlot()`**: gibt die erste Komponente zurück, die einem Slot zugeordnet ist. Optional kann eine Klassentyp angegeben werden, um zu filtern.
