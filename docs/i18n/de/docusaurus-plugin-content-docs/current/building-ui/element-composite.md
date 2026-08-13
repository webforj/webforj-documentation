---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: b8099816ab51d246d3a69c2ca8bd9108
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` umschließt ein benutzerdefiniertes HTML-Element oder einen [Webkomponent](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Sie bindet Ihre Java-Klasse an das zugrunde liegende `Element` und erlaubt es Ihnen, mit den Eigenschaften, Attributen und Ereignissen dieses Elements über Java zu arbeiten. Verwenden Sie es, wenn Sie Webkomponenten in eine webforJ-App integrieren.

:::tip Wann `ElementComposite` verwenden
Nutzen Sie `ElementComposite`, wenn Sie eine Drittanbieter-Webkomponente umschließen, die webforJ nicht bereits bereitstellt. Wenn ein integrierter webforJ-Komponente den Anwendungsfall abdeckt (z. B. `TextField`, `ColorField`, `Button` usw.), verwenden Sie stattdessen diese. Für einmalige DOM-Arbeiten, die nicht wiederverwendet werden müssen, kann die Klasse `Element` direkt ohne Wrapper verwendet werden.
:::

Dieser Leitfaden zeigt, wie Sie die [Shoelace-Webkomponente für relative Zeit](https://shoelace.style/components/relative-time) mithilfe der Klasse `ElementComposite` implementieren.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klassenannotationen {#class-annotations}

Drei Annotationen erscheinen häufig an der Spitze eines `ElementComposite`-Unterklassen: `@NodeName` erklärt das HTML-Tag, das die Komponente umschließt, und `@JavaScript` sowie `@StyleSheet` laden alle clientseitigen Assets, von denen die zugrunde liegende Webkomponente abhängt. `@NodeName` ist erforderlich und spezifisch für `ElementComposite`. `@JavaScript` und `@StyleSheet` sind allgemeine webforJ-Asset-Annotationen und funktionieren für jede Klasse, einschließlich Views, Komponenten oder der `App`-Klasse.

### `@NodeName` {#nodename}

Die Annotation `@NodeName` erklärt das HTML-Tag, das die Komponente umschließt. webforJ verwendet diesen Namen, wenn das zugrunde liegende Element im DOM erstellt wird.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Der Name des Tags muss mit dem benutzerdefinierten Element übereinstimmen, das im Client registriert ist. Ohne diese Annotation kann das Framework nicht bestimmen, welches Element erstellt werden soll.

Innerhalb einer Unterklasse liest `getNodeName()` den deklarierten Tag zurück, und `getElement()` gibt das zugrunde liegende `Element` zurück, sodass Sie DOM-Methoden direkt darauf aufrufen können.

### `@JavaScript` {#javascript}

Die Annotation `@JavaScript` lädt das Skript, das die zugrunde liegende Webkomponente definiert oder registriert. Platzieren Sie es an der Klasse, damit das Skript nur geladen wird, wenn die Komponente verwendet wird.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Mehrere `@JavaScript`-Annotationen sind erlaubt, und webforJ dedupliziert die Ladevorgänge automatisch. Das gleiche Skript wird nicht zweimal geladen, wenn mehrere Komponenten davon abhängen.

Siehe [Importieren von JavaScript-Dateien](../managing-resources/importing-assets#importing-javascript-files) für die vollständige Menge an Optionen, einschließlich `top`, `attributes` und Ladezeitpunkt.

### `@StyleSheet` {#stylesheet}

Die Annotation `@StyleSheet` lädt eine CSS-Datei, von der die Komponente abhängt. Sie ist nützlich für Drittanbieterkomponenten, die ein separates Stylesheet mitbringen, oder für die Bündelung komponentenspezifischer Stile zusammen mit dem Wrapper.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Für lokal gebündelte Assets verwenden Sie das Präfix `ws://`, um auf Dateien in `resources/static` zu verweisen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Siehe [Importieren von CSS-Dateien](../managing-resources/importing-assets#importing-css-files) für die vollständige Menge an Optionen.

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute repräsentieren den Zustand einer Webkomponente und halten typischerweise Daten oder Konfigurationen. `ElementComposite` legt beide über `PropertyDescriptor` frei.

Zwei Fabrikmethoden auf `PropertyDescriptor` erzeugen den Deskriptor selbst, eine für jedes Bindungsziel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindet an eine JavaScript-Eigenschaft im DOM-Knoten. `PropertyDescriptor.attribute()` bindet an ein HTML-Attribut. Das erste Argument ist der Name, den die Webkomponente erwartet. Das zweite ist ein Standardwert, der auch den Java-Typ des Deskriptor festlegt.

Deklarieren Sie den Deskriptor als privates Feld in der Komponente, und lesen und schreiben Sie über ihn mit `set(PropertyDescriptor<V> property, V value)` und `get(PropertyDescriptor<V> property)`.

:::info
Eigenschaften sind interne Zustände des DOM-Knotens und spiegeln sich nicht im Markup wider. Attribute sind HTML-Markup, sichtbar für externe Skripte und CSS.
:::

```java
// Beispiel einer Eigenschaft namens "title" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Beispiel eines Attributs namens "value" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mein Titel");
set(value, "Mein Wert");
```

Die obigen Aufrufe verwenden `set()` direkt, um die primitive Form zu zeigen. In der Praxis sind `set()` und `get()` geschützte Methoden in `ElementComposite`. Sie sind die primitive Schicht, die Java-Werte mit dem zugrunde liegenden Element synchronisiert, nicht die öffentliche API, auf die Verbraucher zugreifen. Das beabsichtigte Muster besteht darin, den `PropertyDescriptor` privat zu halten und öffentliche `setX()`- und `getX()`-Methoden zu schreiben, die an die primitiven Methoden delegieren.

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

Ein einziger Aufruf von `set(descriptor, value)` erledigt gleichzeitig drei Dinge. Er sendet den Wert an den Client über `setProperty()` für Eigenschaften oder `setAttribute()` für Attribute. Er speichert den Wert in einem lokalen serverseitigen Cache, eine Map pro Komponenteninstanz. Und er speichert den zur Laufzeit Typ zusammen mit dem Wert, sodass spätere `get()`-Aufrufe wissen, wie sie deserialisieren sollen.

Dieser lokale Cache ist der Grund, warum `get()` standardmäßig günstig sein kann. `get(descriptor)` gibt den zwischengespeicherten Wert aus dem serverseitigen Speicher ohne Netzwerkaufruf zurück, da jeder `set()` den Cache mit dem Client synchron hält. Das optionale `boolean`-Zweite Argument steuert, ob der Cache umgangen und stattdessen vom Browser gelesen werden soll.

```java
String cached = get(heading);            // liest aus dem serverseitigen Cache
String live = get(heading, true);        // zwingt ein Lesen vom Browser
```

Setzen Sie `fromClient` auf true, wenn sich der Wert auf dem Client ändern kann, ohne dass der Server darüber informiert wird, z. B. bei einem eingegebenen `<input>`-Wert. Für servergesteuerte Eigenschaften vermeidet das Standardverhalten eine Rundreise.

Das optionale dritte Argument ist ein `java.lang.reflect.Type` und steuert, wie das Ergebnis deserialisiert wird. webforJ löst den Typ in dieser Reihenfolge: das explizite `Type`-Argument, falls übergeben, dann der zur Laufzeit aufgezeichnete Typ von einem vorherigen `set()` auf demselben Deskriptor, dann `Object.class`. In der Praxis ist der bei einem vorherigen `set()` aufgezeichnete Typ ausreichend, sodass das dritte Argument in der Regel weggelassen werden kann. Es ist erforderlich, wenn die aufgezeichnete Klasse Informationen verliert, von denen der Deserializer abhängt, z. B. bei einem parametrisierten Typ wie `List<String>`, dessen Laufzeitklasse nur `ArrayList` ist.

Die folgende Demo fügt Eigenschaften für relative Zeit basierend auf den Dokumenten der Webkomponente hinzu und stellt sie über Getter und Setter zur Verfügung. Jede Zeile im Aktivitätsfeed verwendet unterschiedliche `format`- und `numeric`-Werte, um zu zeigen, wie dieselbe Komponente unter verschiedenen Konfigurationen gerendert wird.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Eigenschaften versus Attribute {#properties-versus-attributes}

Obwohl `PropertyDescriptor.property()` und `PropertyDescriptor.attribute()` austauschbar erscheinen, zielen sie auf unterschiedliche Teile des zugrunde liegenden Elements ab. Die falsche Wahl führt zu Werten, die im Stillen nicht angewendet werden.

Eigenschaften sind JavaScript-Objekteigenschaften des DOM-Knotens. Sie können beliebigen Typen halten, einschließlich Strings, Booleans, Zahlen, Objekten und Arrays und repräsentieren den aktuellen Laufzeitstatus des Elements. Das Setzen einer Eigenschaft ist eine direkte JavaScript-Zuweisung.

Attribute sind HTML-Markup. Sie befinden sich im öffnenden Tag des Elements, sind immer Strings und repräsentieren die ursprüngliche Konfiguration des Elements. Das Setzen eines Attributs löst eine DOM-Veränderung und eine String-Konvertierung aus.

In einigen Fällen bleiben die beiden synchron. In anderen divergieren sie. Der `value` eines `<input>` ist das klassische Beispiel: das `value`-Attribut ist der ursprüngliche Wert, während die `value`-Eigenschaft der aktuelle Wert ist, den der Benutzer eingegeben hat. Das Lesen des Attributs, nachdem der Benutzer getippt hat, gibt das ursprüngliche Markup zurück, während das Lesen der Eigenschaft die aktuellen Inhalte des Feldes zurückgibt.

Verwenden Sie **Eigenschaften** für:

- **Häufig wechselnde Laufzeitzustände**: Zähler, aktuelle Auswahlen, eingegebene Werte
- **Nicht-String-Typen**: Booleans, Zahlen, Objekte, Arrays
- **Leistungsempfindliche Updates**: Eigenschaften umgehen die für Attribute erforderliche String-Konvertierung

Verwenden Sie **Attribute** für:

- **Ursprüngliche Konfiguration**: Einstellungen, die die Komponente einmal beim Anschluss liest
- **CSS-Selektoren**: Werte, die Sie mit Selektoren wie `[disabled]` oder `[variant="danger"]` ansprechen möchten
- **Zugänglichkeits-Hooks**: `aria-label`, `role` und andere ARIA-Attribute
- **String-ähnliche Einstellungen, die sich selten ändern**

Beim Wrapping einer Drittanbieter-Webkomponente sollten Sie die Dokumentation der Komponente überprüfen, um zu bestätigen, welcher Name zu einer Eigenschaft und welcher zu einem Attribut führt. Die Verwendung von `PropertyDescriptor.attribute()` für etwas, das die Komponente nur als Eigenschaft exponiert, funktioniert nicht, und das Gleiche gilt umgekehrt. Die Komponente ignoriert den Wert stillschweigend.

### Typisierung von Eigenschaften {#typing-properties}

Ein Deskriptor wird durch den Java-Typ seines Wertes parametrisiert. Die vollständige Deklarationssyntax lautet:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Der generische Parameter `<T>` erklärt den Typ des Wertes. Der Laufzeittyp des Standardwerts legt auch `T` fest, sodass das generische Argument in der Regel nicht explizit angegeben werden muss. webforJ verwendet `T`, um Werte bei der Kommunikation mit dem Client zu serialisieren und deserialisieren.

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

Die Serialisierung erfolgt automatisch für primitive Typen, deren Boxed-Äquivalente und `String`. Für komplexe Typen wird der Wert als JSON serialisiert, bevor er der Eigenschaft auf dem Client zugewiesen wird.

### Validierung von Werten {#validating-values}

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

Für nullable Referenzen verwenden Sie `Objects.requireNonNull()`, sodass der Fehler an der Grenze auftritt, anstatt später in der Rendering-Pipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading darf nicht null sein");
  set(heading, value);
  return this;
}
```

Vermeiden Sie die Validierung in `get()`. Lesevorgänge sollten preiswert und konsistent bleiben.

### Enum-ähnliche Eigenschaften {#enum-style-properties}

Die meisten Webkomponenten erwarten Kleinbuchstaben- oder Kebab-Case-Stringwerte für enum-ähnliche Eigenschaften (`theme="primary"`, `expanse="xs"`). webforJ verwendet Gson zur Serialisierung von Enums, aber Gson's standardmäßige Darstellung ist der Konstantenname in Großbuchstaben. Annotieren Sie jede Konstante mit `@SerializedName`, damit der serialisierte Wert dem entspricht, was die Webkomponente erwartet.

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

Deklarieren Sie den Deskriptor mit dem enum-Typ und verwenden Sie das enum direkt im Setter und Getter.

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

Dies ist dasselbe Muster, das die integrierten Komponenten von webforJ für `Theme`, `Expanse` und ähnliche Enums verwenden. Die öffentliche Java-API bleibt typensicher, und der Wert, den die Webkomponente erhält, ist der String aus `@SerializedName`.

### Testen von Eigenschaften {#testing-properties}

`PropertyDescriptorTester` validiert, dass jeder `PropertyDescriptor` in einer Komponente korrekt verkabelt ist. Es durchsucht die Klasse nach Deskriptorfeldern, ruft jeden Setter mit dem Standardwert auf und vergleicht das Ergebnis mit dem, was der Getter zurückgibt. Der Tester fängt Integrationsfehler ab, bevor sie eine laufende Anwendung erreichen: einen Setter, der in den falschen Deskriptor schreibt, einen Getter, der eine andere Eigenschaft liest, einen Standardwert, der nicht round-trip-tauglich ist, oder einen fehlenden Zugriff für einen deklarierten Deskriptor.

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

#### Ausschließen von Eigenschaften {#excluding-properties}

Einige Deskriptoren folgen nicht den standardmäßigen Getter- und Setter-Konventionen oder sind auf einen externen Zustand angewiesen, den der Test nicht erfüllen kann. Annotieren Sie sie mit `@PropertyExclude`, um sie zu überspringen.

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

Der Parameter `target` akzeptiert eine Klasse, wenn sich die Zugriffs-Methoden nicht in der Komponente selbst befinden.

Für detailliertere Informationen zur Testumgebung siehe [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Anliegen-Interfaces {#concern-interfaces}

Anliegen-Interfaces geben einer `ElementComposite`-Unterklasse Komponentenfunktionen, ohne die Implementierung selbst zu schreiben. Die Interfaces leiten Aufrufe an das zugrunde liegende Element weiter. Implementieren Sie die, die die Komponente unterstützen soll, parametrisiert mit dem Typ der Unterklasse, sodass die Verkettung die Komponente zurückgibt:

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

Die drei obigen Interfaces decken alles ab, was `MyBadge` benötigt, ohne dass im Klassendefinition Methoden vorhanden sind. `HasText` gibt `setText()` frei und schreibt in den Textinhalt des Elements. `HasClassName` gibt `addClassName()` frei, wodurch die Auszeichnung von CSS aus angesprochen werden kann. `HasStyle` gibt `setStyle()` für Inline-Styles frei.

Für die vollständige Liste der verfügbaren Interfaces und was jedes bietet, siehe [Anliegen-Interfaces](./component-fundamentals#concern-interfaces) im Artikel "Verstehen von Komponenten". Wenn eine Standardweiterleitung nicht mit dem übereinstimmt, was das umschlossene Element bereitstellt, überschreiben Sie die Methode in der Unterklasse.

## Ereignisse {#events}

### Ereignisregistrierung {#event-registration}

Webkomponenten senden DOM-Ereignisse, wenn im Browser etwas passiert. Um von Java aus zu reagieren, hören Sie auf diese Ereignisse mit `addEventListener()`. Das Set an Ereignissen, das eine Komponente sendet, variiert, überprüfen Sie daher die eigenen Dokumente der Komponente auf die verfügbaren Namen und Payloads.

`ElementComposite` unterstützt Debouncing, Throttling, Filtering und benutzerdefinierte Ereignisdaten bei registrierten Listenern.

Registrieren Sie Ereignis-Listener mithilfe der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klick-Ereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Verarbeiten Sie das Klickereignis
});
```

:::info
`ElementComposite` akzeptiert nur Ereignisklassen, die mit `@EventName` annotiert sind, im Gegensatz zu `Element`, das jeden String-Ereignisnamen akzeptiert.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

`ElementClickEvent` ist die ein einzige eingebaute Ereignisklasse, die `ElementComposite` mitliefert. Es gibt Mausklickereignisse auf dem zugrunde liegenden Element mit typisierten Zugriffsmethoden für Koordinaten (`getClientX()`, `getClientY()`), Tastendaten (`getButton()`) und Modifier-Tasten (`isCtrlKey()`, `isShiftKey()` usw.) zurück.

Um das Klicken auf der öffentlichen API einer Unterklasse bereitzustellen, implementieren Sie das Anliegen-Interface `HasElementClickListener<T>`. Es bietet die Standardmethoden `onClick()` und `addClickListener()`, die an die geschützte primitive Methode `addEventListener()` delegieren.

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

Für jedes andere Ereignis, das die zugrunde liegende Webkomponente sendet, definieren Sie eine benutzerdefinierte Ereignisklasse. Siehe [Benutzerdefinierte Ereignisklassen](#custom-event-classes).

### Ereignis-Payloads {#event-payloads}

Ereignisse transportieren Daten vom Client zu Ihrem Java-Code. Greifen Sie über `getData()` auf diese Daten zu, um rohe Ereignisdaten abzurufen, oder verwenden Sie typisierte Methoden, wenn sie in den eingebauten Ereignisklassen verfügbar sind. Weitere Informationen zur effizienten Verarbeitung von Payloads finden Sie im [Ereignisse-Leitfaden](../building-ui/events).

### Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Definieren Sie benutzerdefinierte Ereignisklassen mit `@EventName` und `@EventOptions`, um clientseitige Daten in einem typisierten Java-Ereignis zu erfassen. Verwenden Sie dies, wenn der Java-Handler Werte vom Browser benötigt.

`@EventName` bindet die Java-Klasse an das Ereignis, das die Komponente im Browser senden, sodass eine mit `@EventName("sl-change")` annotierte Klasse immer dann ausgelöst wird, wenn das zugrunde liegende Element `sl-change` auslöst. `@EventOptions` steuert, was mit diesem Ereignis zurückkommt. Jedes `@EventData` darin paart einen Schlüssel mit einem JavaScript-Ausdruck, der gegen das DOM-Ereignis ausgewertet wird. Das Ergebnis ist in der Java-Ereignisklasse über `getData().get(key)` verfügbar.

Das Produktbewertungsformular unten nutzt dieses Muster mit [`sl-rating`](https://shoelace.style/components/rating). Das benutzerdefinierte `ChangeEvent` überträgt den Bewertungswert als typisierten `double`, und der Listener verwendet ihn, um die Schaltfläche "Absenden" zu aktivieren:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Ereignisoptionen {#event-options}

`ElementEventOptions` konfiguriert die Ereignispayload, Debounce- oder Throttle-Timing, Filterausdrücke und vorlaufenden Code. Der folgende Snippet zeigt die Optionen:

```java
ElementEventOptions options = new ElementEventOptions()
  // Sammeln von benutzerdefinierten Daten vom Client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Führen Sie JavaScript aus, bevor das Ereignis ausgelöst wird
  .setCode("component.classList.add('processing');")

  // Nur auslösen, wenn Bedingungen erfüllt sind
  .setFilter("component.value.length >= 2")

  // Verzögern Sie die Ausführung, bis der Benutzer das Tippen stoppt (300 ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Wenden Sie diese Optionen beim Registrieren eines Listener für eine benutzerdefinierte Ereignisklasse an
// (siehe den Abschnitt Benutzerdefinierte Ereignisklassen oben, um zu erfahren, wie man eine definiert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` bietet nur die klassenbasierte Form `addEventListener(Class, listener, options)` an. Verwenden Sie es mit einer Ereignisklasse, die mit `@EventName` annotiert ist. Um direkt gegen einen String-Ereignisnamen zu registrieren, rufen Sie `getElement().addEventListener("input", listener, options)` auf.
:::

#### Leistungssteuerung {#performance-control}

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 300 ms nach dem letzten Ereignis warten
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Auf eine ruhige Phase warten, dann auslösen (Standard)
- `BOTH`: Sofort und nach einer ruhigen Phase auslösen

**Throttling** begrenzt die Häufigkeit der Ausführung:

```java
options.setThrottle(100); // Höchstens einmal alle 100 ms auslösen
```

## Interaktion mit Slots {#interacting-with-slots}

Slots sind Platzhalter innerhalb einer Webkomponente, die von Benutzern mit Inhalten gefüllt werden. Die Webkomponente erklärt ihre Slots in ihrer Vorlage mit `<slot>` oder `<slot name="...">`, und der Wrapper stellt Methoden bereit, die Java-Komponenten in diese Slots einfügen.

Um Inhalte in Slots hinzuzufügen, erweitern Sie `ElementCompositeContainer` anstelle von `ElementComposite`. Der Container trägt die gleiche Eigenschaften- und Attributmaschinerie sowie die Methoden, die zum Hinzufügen von Kindkomponenten erforderlich sind. Kinder, die über `add()` hinzugefügt werden, gehen in den Standard-Slot. Kinder, die über `getElement().add(slotName, components)` hinzugefügt werden, gehen in den benannten Slot.

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

Die Demo unten zeigt zwei Preis Karten, die mit [`sl-card`](https://shoelace.style/components/card) erstellt wurden, und die die Slots `header`, Standard und `footer` aus Java befüllen:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspizieren von Slot-Inhalten {#inspecting-slot-contents}

Das zugrunde liegende `Element` (erreichbar über `getElement()`) stellt Methoden zum Lesen dessen bereit, was derzeit den Slots zugewiesen ist:

- **`findComponentSlot()`**: sucht alle Slots nach einer bestimmten Komponente und gibt den Namen des Slots zurück, die sie enthält, oder eine leere Zeichenfolge, wenn sich die Komponente nicht in einem Slot befindet.
- **`getComponentsInSlot()`**: gibt die Liste der Komponenten zurück, die einem bestimmten Slot zugewiesen sind. Optional kann ein Klassentyp zur Filterung der Ergebnisse übergeben werden.
- **`getFirstComponentInSlot()`**: gibt die erste Komponente zurück, die einem Slot zugewiesen ist. Optional kann ein Klassentyp zur Filterung übergeben werden.
