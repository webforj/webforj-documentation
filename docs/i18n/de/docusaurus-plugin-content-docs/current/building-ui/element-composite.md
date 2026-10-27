---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 277c6e7e84197ab515cae210cd8207b0
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` umschließt ein benutzerdefiniertes HTML-Element oder [Webkomponente](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Sie bindet Ihre Java-Klasse an das zugrunde liegende `Element` und ermöglicht es Ihnen, mit den Eigenschaften, Attributen und Ereignissen dieses Elements über Java zu arbeiten. Verwenden Sie sie, wenn Sie Webkomponenten in eine WebforJ-App integrieren.

:::tip Wann `ElementComposite` verwenden
Greifen Sie auf `ElementComposite` zu, wenn Sie eine Drittanbieter-Webkomponente umschließen, die WebforJ nicht bereits bereitstellt. Wenn ein integriertes WebforJ-Element den Anwendungsfall abdeckt (z. B. `TextField`, `ColorField`, `Button` usw.), verwenden Sie stattdessen dieses. Für einmalige DOM-Arbeiten, die nicht wiederverwendet werden müssen, kann die Klasse `Element` direkt ohne eine Umhüllung verwendet werden.
:::

Diese Anleitung zeigt, wie Sie die [Web Awesome relative-time Webkomponente](https://webawesome.com/docs/components/relative-time/) mithilfe der Klasse `ElementComposite` implementieren.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klassennotation {#class-annotations}

Drei Anmerkungen erscheinen häufig am Anfang einer Unterklasse von `ElementComposite`: `@NodeName` erklärt das HTML-Tag, das die Komponente umschließt, und `@JavaScript` sowie `@StyleSheet` laden alle clientseitigen Komponenten, von denen die zugrunde liegende Webkomponente abhängt. `@NodeName` ist erforderlich und spezifisch für `ElementComposite`. `@JavaScript` und `@StyleSheet` sind allgemeine WebforJ-Asset-Anmerkungen und funktionieren für jede Klasse, einschließlich Views, Komponenten oder der `App`-Klasse.

### `@NodeName` {#nodename}

Die Annotation `@NodeName` erklärt das HTML-Tag, das die Komponente umschließt. WebforJ verwendet diesen Namen, wenn das zugrunde liegende Element im DOM erstellt wird.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Der Tagname muss mit dem benutzerdefinierten Element übereinstimmen, das auf der Clientseite registriert ist. Ohne diese Annotation kann das Framework nicht bestimmen, welches Element zu erstellen ist.

Innerhalb einer Unterklasse liest `getNodeName()` das deklarierte Tag zurück, und `getElement()` gibt das zugrunde liegende `Element` zurück, sodass Sie direkt DOM-Methoden darauf aufrufen können.

### `@JavaScript` {#javascript}

Die Annotation `@JavaScript` lädt das Skript, das die zugrunde liegende Webkomponente definiert oder registriert. Platzieren Sie sie auf der Klasse, damit das Skript nur geladen wird, wenn die Komponente verwendet wird.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Mehrere `@JavaScript`-Annotationen sind erlaubt, und WebforJ bereinigt doppelte Ladevorgänge automatisch. Dasselbe Skript wird nicht zweimal geladen, wenn mehrere Komponenten davon abhängen.

Siehe [JavaScript-Dateien importieren](../managing-resources/importing-assets#importing-javascript-files) für die vollständige Liste der Optionen, einschließlich `top`, `attributes` und Ladezeitpunkt.

### `@StyleSheet` {#stylesheet}

Die Annotation `@StyleSheet` lädt eine CSS-Datei, von der die Komponente abhängt. Sie ist nützlich für Drittanbieterkomponenten, die ein separates Stylesheet liefern, oder um komponentenspezifisches Styling zusammen mit der Umhüllung zu bündeln.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Für lokal gebündelte Assets verwenden Sie das `ws://`-Präfix, um auf Dateien in `resources/static` zu verweisen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Siehe [CSS-Dateien importieren](../managing-resources/importing-assets#importing-css-files) für die vollständige Liste der Optionen.

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute repräsentieren den Zustand einer Webkomponente und enthalten typischerweise Daten oder Konfigurationen. `ElementComposite` macht beide über `PropertyDescriptor` sichtbar.

Zwei Fabrikmethoden auf `PropertyDescriptor` erzeugen den Deskriptor selbst, eine für jedes Bindungsziel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindet an eine JavaScript-Eigenschaft am DOM-Knoten. `PropertyDescriptor.attribute()` bindet an ein HTML-Attribut. Das erste Argument ist der Name, den die Webkomponente erwartet. Das zweite ist ein Standardwert, der auch den Java-Typ des Deskriptors festlegt.

Deklarieren Sie den Deskriptor als privates Feld in der Komponente und lesen und schreiben Sie durch ihn mit `set(PropertyDescriptor<V> property, V value)` und `get(PropertyDescriptor<V> property)`.

:::info
Eigenschaften sind interne Zustände des DOM-Knotens und spiegeln sich nicht im Markup wider. Attribute sind HTML-Markup, das für externe Skripte und CSS sichtbar ist.
:::

```java
// Beispieldaten in einer ElementComposite-Klasse
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Beispieldaten in einer ElementComposite-Klasse
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mein Titel");
set(value, "Mein Wert");
```

Die obigen Aufrufe verwenden `set()` direkt, um die primitive Form zu zeigen. In der Praxis sind `set()` und `get()` `protected`-Methoden auf `ElementComposite`. Sie sind die primitive Schicht, die Java-Werte mit dem zugrunde liegenden Element synchronisiert, und nicht die öffentliche API, die Verbraucher aufrufen. Das beabsichtigte Muster besteht darin, den `PropertyDescriptor` privat zu halten und öffentliche `setX()`- und `getX()`-Methoden zu schreiben, die auf die primitiven Methoden delegieren.

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

Ein einzelner Aufruf von `set(descriptor, value)` erledigt drei Dinge gleichzeitig. Er schiebt den Wert über `setProperty()` für Eigenschaften oder `setAttribute()` für Attribute an den Client. Er speichert den Wert in einem lokalen serverseitigen Cache, einer Map pro Komponenteninstanz. Und er zeichnet den Laufzeittyp zusammen mit dem Wert auf, damit spätere `get()`-Aufrufe wissen, wie sie deserialisieren müssen.

Dieser lokale Cache ist der Grund, warum `get()` standardmäßig günstig sein kann. `get(descriptor)` gibt den zwischengespeicherten Wert aus dem serverseitigen Store ohne Netzwerkaufruf zurück, da jedes `set()` den Cache mit dem Client synchron hält. Das optionale `boolean`-zweite Argument steuert, ob der Cache umgangen und stattdessen vom Browser gelesen werden soll.

```java
String cached = get(heading);            // liest aus dem serverseitigen Cache
String live = get(heading, true);        // zwingt eine Lesung vom Browser
```

Setzen Sie `fromClient` auf true, wenn sich der Wert auf dem Client ändern kann, ohne dass der Server informiert wird, z. B. bei einem eingegebenen `<input>`-Wert. Für servergesteuerte Eigenschaften vermeidet die Standardoption eine Runde.

Das optionale dritte Argument ist ein `java.lang.reflect.Type` und steuert, wie das Ergebnis deserialisiert wird. WebforJ bestimmt den Typ in dieser Reihenfolge: das explizite `Type`-Argument, falls übergeben, dann der zur Laufzeit gespeicherte Typ, der von einem vorherigen `set()` für denselben Deskriptor aufgezeichnet wurde, dann `Object.class`. In der Praxis reicht der von einem vorherigen `set()` aufgezeichnete Typ, sodass das dritte Argument in der Regel weggelassen werden kann. Es ist erforderlich, wenn die aufgezeichnete Klasse Informationen verliert, die der Deserialisierer benötigt, z. B. einen parametrisierten Typ wie `List<String>`, dessen Laufzeitklasse nur `ArrayList` ist.

Die Demo unten fügt Eigenschaften für relative Zeiten basierend auf den Dokumenten der Webkomponente hinzu und macht diese durch Getter und Setter zugänglich. Jede Zeile im Aktivitäts-Feed verwendet unterschiedliche `format`- und `numeric`-Werte, um zu zeigen, wie die gleiche Komponente unter variierenden Konfigurationen gerendert wird.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Eigenschaften vs. Attribute {#properties-versus-attributes}

Obwohl `PropertyDescriptor.property()` und `PropertyDescriptor.attribute()` austauschbar erscheinen, zielen sie auf verschiedene Teile des zugrunde liegenden Elements ab. Die falsche Wahl führt zu Werten, die stillschweigend fehlschlagen.

Eigenschaften sind JavaScript-Objekteigenschaften am DOM-Knoten. Sie können jeden Typ halten, einschließlich Zeichenfolgen, Booleans, Zahlen, Objekte und Arrays, und sie repräsentieren den aktuellen Laufzeitzustand des Elements. Das Setzen einer Eigenschaft ist eine direkte JavaScript-Zuweisung.

Attribute sind HTML-Markup. Sie befinden sich am öffnenden Tag des Elements, sind immer Zeichenfolgen und stellen die ursprüngliche Konfiguration des Elements dar. Das Setzen eines Attributs löst eine DOM-Veränderung und eine Zeichenfolgenkonvertierung aus.

In einigen Fällen bleiben die beiden synchron. In anderen divergieren sie. Der `value` eines `<input>` ist das klassische Beispiel: Das `value`-Attribut ist der ursprüngliche Wert, während die `value`-Eigenschaft der aktuelle Wert ist, den der Benutzer eingegeben hat. Das Lesen des Attributs nach der Eingabe des Benutzers gibt das ursprüngliche Markup zurück, aber das Lesen der Eigenschaft gibt den aktuellen Inhalt des Feldes zurück.

Verwenden Sie **Eigenschaften** für:

- **Häufig wechselnde Laufzeitzustände**: Zähler, aktuelle Auswahlen, eingegebene Werte
- **Nicht-String-Typen**: Booleans, Zahlen, Objekte, Arrays
- **Leistungsbedarf**: Eigenschaften überspringen die Zeichenfolgenkonvertierung, die für Attribute erforderlich ist

Verwenden Sie **Attribute** für:

- **Ursprüngliche Konfiguration**: Einstellungen, die die Komponente einmal beim Anschluss liest
- **CSS-Selektoren**: Werte, die Sie mit Selektoren wie `[disabled]` oder `[variant="danger"]` ansprechen möchten
- **Zugänglichkeits-Hooks**: `aria-label`, `role` und andere ARIA-Attribute
- **String-ähnliche Einstellungen, die sich selten ändern**

Beim Umschließen einer Drittanbieter-Webkomponente überprüfen Sie die Dokumentation der Komponente, um zu bestätigen, welches Name auf eine Eigenschaft und welches auf ein Attribut abgebildet wird. `PropertyDescriptor.attribute()` für etwas zu verwenden, das die Komponente nur als Eigenschaft exposes, funktioniert nicht, und umgekehrt gilt das gleiche. Die Komponente ignoriert den Wert stillschweigend.

### Typisierung von Eigenschaften {#typing-properties}

Ein Deskriptor ist parametrisiert durch den Java-Typ seines Wertes. Die vollständige Deklarationssyntax lautet:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Der `<T>`-generische Parameter gibt den Typ des Wertes an. Auch der Laufzeittyp des Standardwerts legt `T` fest, sodass das generische Argument selten explizit angegeben werden muss. WebforJ verwendet `T`, um Werte zu serialisieren und zu deserialisieren, wenn mit dem Client kommuniziert wird.

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

Die Serialisierung erfolgt automatisch für primitive Typen, ihre Box-Äquivalente und `String`. Für komplexe Typen wird der Wert als JSON serialisiert, bevor er der Eigenschaft auf der Clientseite zugewiesen wird.

### Werte validieren {#validating-values}

Validieren Sie Werte im Setter, bevor Sie `set()` aufrufen. Der Setter ist der natürliche Durchsetzungsort, da jede Mutation durch ihn fließt.

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

Für nullable Referenzen verwenden Sie `Objects.requireNonNull()`, damit der Fehler an der Grenze auftritt und nicht später in der Rendering-Pipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading darf nicht null sein");
  set(heading, value);
  return this;
}
```

Vermeiden Sie die Validierung in `get()`. Lesevorgänge sollten günstig und konsistent bleiben.

### Enum-ähnliche Eigenschaften {#enum-style-properties}

Die meisten Webkomponenten erwarten Kleinbuchstaben- oder Kebab-Case-Zeichenfolgenwerte für enum-ähnliche Eigenschaften (`theme="primary"`, `expanse="xs"`). WebforJ verwendet Gson zur Serialisierung von Enums, aber die Standarddarstellung von Gson ist der Konstantenname in Großbuchstaben. Annotieren Sie jede Konstante mit `@SerializedName`, damit der serialisierte Wert dem entspricht, was die Webkomponente erwartet.

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

Erklären Sie den Deskriptor mit dem Enum-Typ und verwenden Sie das Enum direkt im Setter und Getter.

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

Dies ist das gleiche Muster, das die integrierten Komponenten von WebforJ für `Theme`, `Expanse` und ähnliche Enums verwenden. Die öffentliche Java-API bleibt typsicher, und der Wert, den die Webkomponente erhält, ist die Zeichenfolge aus `@SerializedName`.

### Eigenschaften testen {#testing-properties}

`PropertyDescriptorTester` validiert, dass jeder `PropertyDescriptor` in einer Komponente korrekt verdrahtet ist. Es scannt die Klasse nach Descriptorfeldern, ruft jeden Setter mit dem Standardwert auf und vergleicht das Ergebnis mit dem, was der Getter zurückgibt. Der Tester fängt Integrationsfehler ab, bevor sie eine laufende App erreichen: ein Setter, der in den falschen Deskriptor schreibt, ein Getter, der eine andere Eigenschaft liest, ein Standardwert, der nicht zurückgegeben wird, oder ein fehlender Zugriff für einen erklärten Deskriptor.

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

Einige Deskriptoren folgen nicht den standardmäßigen Getter- und Setter-Konventionen oder sind auf externe Zustände angewiesen, die der Test nicht erfüllen kann. Annotieren Sie sie mit `@PropertyExclude`, um sie zu überspringen.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Benutzerdefinierte Getter- und Setter-Namen {#custom-getter-and-setter-names}

Wenn ein Deskriptor nicht standardmäßige Zugriffsnamen verwendet, erklären Sie sie mit `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Der Parameter `target` akzeptiert eine Klasse, wenn die Zugriffsmethoden sich an einem anderen Ort als der Komponente selbst befinden.

Weitere Informationen zur Testoberfläche finden Sie unter [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Concern-Schnittstellen {#concern-interfaces}

Concern-Schnittstellen geben einer `ElementComposite`-Unterklassenkomponente Fähigkeiten, ohne die Implementierung selbst schreiben zu müssen. Die Schnittstellen leiten Aufrufe an das zugrunde liegende Element weiter. Implementieren Sie diejenigen, die die Komponente unterstützen soll, parametrisiert mit dem Unterklassentyp, damit das Verketten die Komponente zurückgibt:

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

Die drei oben genannten Schnittstellen decken alles ab, was `MyBadge` benötigt, ohne dass im Klassencode method body erforderlich ist. `HasText` exponiert `setText()` und schreibt in den Textinhalt des Elements. `HasClassName` exponiert `addClassName()`, damit die Auszeichnung von CSS angesprochen werden kann. `HasStyle` exponiert `setStyle()` für Inline-Styling.

Für die vollständige Liste der verfügbaren Schnittstellen und was jede einzelne bereitstellt, siehe [Concern-Schnittstellen](./component-fundamentals#concern-interfaces) im Artikel über das Verständnis von Komponenten. Wenn ein Standardweiterleitung nicht dem entspricht, was das umschlossene Element bereitstellt, überschreiben Sie die Methode in der Unterklasse.

## Ereignisse {#events}

### Ereignisregistrierung {#event-registration}

Webkomponenten versenden DOM-Ereignisse, wenn im Browser etwas passiert. Um von Java aus zu reagieren, hören Sie auf diese Ereignisse mit `addEventListener()`. Die Menge der Ereignisse, die eine Komponente sendet, variiert, überprüfen Sie daher die eigene Dokumentation der Komponente für die verfügbaren Namen und Payloads.

`ElementComposite` unterstützt Debouncing, Throttling, Filterung und benutzerdefinierte Ereignisdaten bei registrierten Listenern.

Registrieren Sie Ereignislistener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Click-Ereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Behandeln Sie das Click-Ereignis
});
```

:::info
`ElementComposite` akzeptiert nur Ereignisklassen, die mit `@EventName` annotiert sind, im Gegensatz zu `Element`, das jedes String-Ereignisnamen akzeptiert.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

`ElementClickEvent` ist die einzige eingebaute Ereignisklasse, die `ElementComposite` mitbringt. Sie gibt Mausklickereignisse auf dem zugrunde liegenden Element mit typisierten Zugriffsmethoden für die Koordinaten (`getClientX()`, `getClientY()`), die Schaltflächeninformation (`getButton()`) und die Modifikatortasten (`isCtrlKey()`, `isShiftKey()` usw.) zurück.

Um die Click-Behandlung in der öffentlichen API einer Unterklasse verfügbar zu machen, implementieren Sie die Concern-Schnittstelle `HasElementClickListener<T>`. Sie bietet standardmäßig `onClick()` und `addClickListener()`-Methoden, die sich auf die geschützte `addEventListener()`-primitive beziehen.

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

Ereignisse tragen Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf diese Daten für rohe Ereignisdaten zu oder verwenden Sie typisierte Methoden, wenn sie in den eingebauten Ereignisklassen verfügbar sind. Weitere Informationen zur effizienten Handhabung von Payloads finden Sie im [Ereignisse-Guide](../building-ui/events).

### Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Definieren Sie benutzerdefinierte Ereignisklassen mit `@EventName` und `@EventOptions`, um clientseitige Daten in einem typisierten Java-Ereignis zu erfassen. Verwenden Sie dies, wenn der Java-Handler Werte aus dem Browser benötigt.

`@EventName` bindet die Java-Klasse an das Ereignis, das die Komponente im Browser auslöst, sodass eine Klasse mit `@EventName("change")` immer dann ausgelöst wird, wenn das zugrunde liegende Element `change` ausgibt. `@EventOptions` steuert, was mit diesem Ereignis übertragen wird. Jede `@EventData`-Annotation darin paaren einen Schlüssel mit einem JavaScript-Ausdruck, der gegen das DOM-Ereignis ausgewertet wird. Das Ergebnis ist in der Java-Ereignisklasse über `getData().get(key)` verfügbar.

Das Produktbewertungsformular unten verwendet dieses Muster mit [`wa-rating`](https://webawesome.com/docs/components/rating/). Das benutzerdefinierte `ChangeEvent` trägt den Bewertungswert als typisiertes `double`, und der Listener verwendet ihn, um die Schaltfläche "Einreichen" zu aktivieren:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Ereignisoptionen {#event-options}

`ElementEventOptions` konfiguriert die Event-Payload, Debounce- oder Throttle-Zeitpunkte, Filterausdrücke und vorab ausgeführte Codes. Der folgende Ausschnitt zeigt die Optionen:

```java
ElementEventOptions options = new ElementEventOptions()
  // Relevante Daten vom Client sammeln
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Führen Sie JavaScript aus, bevor das Ereignis ausgelöst wird
  .setCode("component.classList.add('processing');")

  // Nur auslösen, wenn Bedingungen erfüllt sind
  .setFilter("component.value.length >= 2")

  // Verzögere die Ausführung, bis der Benutzer aufhört zu tippen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Wenden Sie diese Optionen beim Registrieren eines Listeners für eine benutzerdefinierte Ereignisklasse an
// (siehe den Abschnitt Benutzerdefinierte Ereignisklassen oben, um zu erfahren, wie man eine definiert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` bietet nur die klassenbasierte Form `addEventListener(Class, listener, options)` an. Verwenden Sie dies mit einer Ereignisklasse, die mit `@EventName` annotiert ist. Um direkt gegen einen String-Ereignisnamen zu registrieren, rufen Sie `getElement().addEventListener("input", listener, options)` auf.
:::

#### Leistungssteuerung {#performance-control}

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Warte 300 ms nach dem letzten Ereignis
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Warte auf eine ruhige Periode, dann auslösen (Standard)
- `BOTH`: Sofort und nach einer ruhigen Periode auslösen

**Throttling** begrenzt die Ausführungsfrequenz:

```java
options.setThrottle(100); // Maximal einmal alle 100 ms auslösen
```

## Interaktion mit Slots {#interacting-with-slots}

Slots sind Platzhalter innerhalb einer Webkomponente, die Benutzer mit Inhalten füllen. Die Webkomponente deklariert ihre Slots in ihrer Vorlage mit `<slot>` oder `<slot name="...">`, und die Wrapper-Klasse bietet Methoden an, um Java-Komponenten in diese Slots einzufügen.

Um Inhalte in Slots hinzuzufügen, erweitern Sie `ElementCompositeContainer` anstelle von `ElementComposite`. Der Container trägt dasselbe Eigenschaften- und Attribut-System, plus die Methoden, die erforderlich sind, um Kinder hinzuzufügen. Kinder, die über `add()` hinzugefügt werden, gehen in den Standard-Slot. Kinder, die über `getElement().add(slotName, components)` hinzugefügt werden, gehen in den benannten Slot.

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

Die Demo unten zeigt zwei Preis-Karten, die mit [`wa-card`](https://webawesome.com/docs/components/card/) erstellt wurden, wobei die `header`, der Standard- und der `footer`-Slot von Java aus gefüllt werden:

<ComponentDemo
path='/webforj/webawesomecard'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Slot-Inhalte inspizieren {#inspecting-slot-contents}

Das zugrunde liegende `Element` (erreichbar über `getElement()`) bietet Methoden, um zurückzulesen, was momentan an Slots zugewiesen ist:

- **`findComponentSlot()`**: durchsucht alle Slots nach einer bestimmten Komponente und gibt den Namen des Slots zurück, der sie enthält, oder eine leere Zeichenfolge, wenn die Komponente nicht in einem Slot vorhanden ist.
- **`getComponentsInSlot()`**: gibt die Liste der Komponenten zurück, die einem gegebenen Slot zugewiesen sind. Optional kann ein Klassentyp übergeben werden, um die Ergebnisse zu filtern.
- **`getFirstComponentInSlot()`**: gibt die erste Komponente zurück, die einem Slot zugewiesen ist. Optional kann ein Klassentyp übergeben werden, um zu filtern.
