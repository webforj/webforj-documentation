---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2a742b2589b096aff73a1fcb67e041c1
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

Die Klasse `ElementComposite` umschließt ein benutzerdefiniertes HTML-Element oder [Webkomponente](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Sie bindet Ihre Java-Klasse an das zugrunde liegende `Element` und ermöglicht Ihnen, mit den Eigenschaften, Attributen und Ereignissen dieses Elements über Java zu arbeiten. Verwenden Sie sie, wenn Sie Webkomponenten in eine webforJ-Anwendung integrieren.

:::tip Wann man `ElementComposite` verwenden sollte
Verwenden Sie `ElementComposite`, wenn Sie eine Drittanbieter-Webkomponente umschließen, die webforJ nicht bereits bereitstellt. Wenn eine integrierte WebforJ-Komponente den Anwendungsfall abdeckt (z. B. `TextField`, `ColorField`, `Button` usw.), nutzen Sie diese stattdessen. Für einmalige DOM-Arbeiten, die nicht wiederverwendet werden müssen, kann die Klasse `Element` direkt ohne eine Wrapper verwendet werden.
:::

Dieser Leitfaden zeigt, wie man die [Web Awesome relative-time Webkomponente](https://webawesome.com/docs/components/relative-time/) unter Verwendung der Klasse `ElementComposite` implementiert.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klassendeklarationen {#class-annotations}

Drei Annotationen erscheinen häufig oben in einer Unterklasse von `ElementComposite`: `@NodeName` erklärt das HTML-Tag, das die Komponente umschließt, während `@JavaScript` und `@StyleSheet` alle clientseitigen Ressourcen laden, von denen die zugrunde liegende Webkomponente abhängt. `@NodeName` ist erforderlich und spezifisch für `ElementComposite`. `@JavaScript` und `@StyleSheet` sind allgemeine webforJ-Ressourcenannotationen und funktionieren bei jeder Klasse, einschließlich Views, Komponenten oder der `App`-Klasse.

### `@NodeName` {#nodename}

Die Annotation `@NodeName` deklariert das HTML-Tag, das die Komponente umschließt. WebforJ verwendet diesen Namen, wenn es das zugrunde liegende Element im DOM erstellt.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Der Tagname muss mit dem benutzerdefinierten Element übereinstimmen, das im Client registriert ist. Ohne diese Annotation kann das Framework nicht bestimmen, welches Element erstellt werden soll.

Innerhalb einer Unterklasse liest `getNodeName()` das deklarierte Tag zurück, und `getElement()` gibt das zugrunde liegende `Element` zurück, sodass Sie DOM-Methoden direkt darauf aufrufen können.

### `@JavaScript` {#javascript}

Die Annotation `@JavaScript` lädt das Skript, das die zugrunde liegende Webkomponente definiert oder registriert. Setzen Sie es auf die Klasse, damit das Skript nur geladen wird, wenn die Komponente verwendet wird.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Mehrere `@JavaScript`-Annotationen sind erlaubt, und webforJ dedupliziert die Ladungen automatisch. Dasselbe Skript wird nicht zweimal geladen, wenn mehrere Komponenten davon abhängen.

Siehe [Importieren von JavaScript-Dateien](../managing-resources/importing-assets#importing-javascript-files) für die vollständige Liste der Optionen, einschließlich `top`, `attributes` und Ladezeitpunkt.

### `@StyleSheet` {#stylesheet}

Die Annotation `@StyleSheet` lädt eine CSS-Datei, von der die Komponente abhängt. Sie ist nützlich für Drittanbieter-Komponenten, die ein separates Stylesheet mitliefern, oder für das Bündeln von komponentenspezifischem Styling zusammen mit dem Wrapper.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Für lokal gebündelte Ressourcen verwenden Sie das Präfix `ws://`, um auf Dateien in `resources/static` zu verweisen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Siehe [Importieren von CSS-Dateien](../managing-resources/importing-assets#importing-css-files) für die vollständige Liste der Optionen.

## Eigenschaften- und Attributbeschreibungen {#property-and-attribute-descriptors}

Eigenschaften und Attribute repräsentieren den Zustand einer Webkomponente und halten typischerweise Daten oder Konfiguration. `ElementComposite` macht beides über `PropertyDescriptor` zugänglich.

Zwei Fabrikmethoden auf `PropertyDescriptor` erstellen den Descriptor selbst, jeweils für das Bindungsziel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindet an eine JavaScript-Eigenschaft des DOM-Knotens. `PropertyDescriptor.attribute()` bindet an ein HTML-Attribut. Das erste Argument ist der Name, den die Webkomponente erwartet. Das zweite ist ein Standardwert, der auch den Java-Typ des Descriptors festlegt.

Deklarieren Sie den Descriptor als private Field in der Komponente, und lesen und schreiben Sie über ihn mit `set(PropertyDescriptor<V> property, V value)` und `get(PropertyDescriptor<V> property)`.

:::info
Eigenschaften sind der interne Zustand des DOM-Knotens und spiegeln sich nicht im Markup wider. Attribute sind HTML-Markup, das für externe Skripte und CSS sichtbar ist.
:::

```java
// Beispiel Eigenschaft namens "title" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Beispiel Attribut namens "value" in einer ElementComposite-Klasse
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mein Titel");
set(value, "Mein Wert");
```

Die obigen Aufrufe verwenden `set()` direkt, um die primitive Form zu zeigen. In der Praxis sind `set()` und `get()` `protected` Methoden in `ElementComposite`. Sie sind die primitive Ebene, die Java-Werte mit dem zugrunde liegenden Element synchronisiert, nicht die öffentliche API, die Verbraucher aufrufen. Das beabsichtigte Muster besteht darin, den `PropertyDescriptor` privat zu halten und öffentliche `setX()`- und `getX()`-Methoden zu schreiben, die an die Primitives delegieren.

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

Ein einzelner Aufruf von `set(descriptor, value)` führt drei Dinge gleichzeitig aus. Er überträgt den Wert an den Client über `setProperty()` für Eigenschaften oder `setAttribute()` für Attribute. Er speichert den Wert in einem lokalen serverseitigen Cache, einem Map pro Komponenteninstanz. Schließlich zeichnet er den Laufzeittyp neben dem Wert auf, sodass spätere `get()`-Aufrufe wissen, wie sie deserialisieren.

Dieser lokale Cache ist der Grund, warum `get()` standardmäßig kostengünstig sein kann. `get(descriptor)` gibt den zwischengespeicherten Wert aus dem serverseitigen Speicher ohne Netzwerkaufruf zurück, da jeder `set()` den Cache mit dem Client synchron hält. Das optionale `boolean` zweite Argument steuert, ob der Cache umgangen und vom Browser gelesen werden soll.

```java
String cached = get(heading);            // liest aus dem serverseitigen Cache
String live = get(heading, true);        // zwingt zu einem Lesen vom Browser
```

Setzen Sie `fromClient` auf true, wenn der Wert im Client ohne Wissen des Servers geändert werden kann, z. B. ein eingegebener `<input>`-Wert. Für servergesteuerte Eigenschaften vermeidet der Standard eine Rückreise.

Das optionale dritte Argument ist ein `java.lang.reflect.Type` und steuert, wie das Ergebnis deserialisiert wird. WebforJ löst den Typ in dieser Reihenfolge auf: das explizite `Type`-Argument, wenn angegeben, dann der zur Laufzeit aufgezeichnete Typ von einem vorherigen `set()` auf demselben Descriptor, und schließlich `Object.class`. In der Praxis ist der von einem vorherigen `set()` aufgezeichnete Typ ausreichend, sodass das dritte Argument meist weggelassen werden kann. Es wird benötigt, wenn die aufgezeichnete Klasse Informationen verliert, von denen der Deserializer abhängt, z. B. ein parametrisierten Typ wie `List<String>`, dessen Laufzeitklasse nur `ArrayList` ist.

Die Demo unten fügt Eigenschaften für die relative Zeit basierend auf den Dokumenten der Webkomponente hinzu und macht sie über Getter und Setter zugänglich. Jede Zeile im Aktivitäts-Feed verwendet unterschiedliche `format`- und `numeric`-Werte, um zu zeigen, wie dieselbe Komponente unter verschiedenen Konfigurationen gerendert wird.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Eigenschaften versus Attribute {#properties-versus-attributes}

Obwohl `PropertyDescriptor.property()` und `PropertyDescriptor.attribute()` austauschbar aussehen, zielen sie auf verschiedene Teile des zugrunde liegenden Elements ab. Die falsche Wahl führt dazu, dass Werte stillschweigend nicht angewendet werden.

Eigenschaften sind JavaScript-Objekt-Eigenschaften auf dem DOM-Knoten. Sie können jeden Typ halten, einschließlich Strings, Booleans, Zahlen, Objekten und Arrays, und sie repräsentieren den aktuellen Laufzeitstatus des Elements. Das Setzen einer Eigenschaft ist eine direkte JavaScript-Zuweisung.

Attribute sind HTML-Markup. Sie leben im öffnenden Tag des Elements, sind immer Strings und repräsentieren die anfängliche Konfiguration des Elements. Das Setzen eines Attributs löst eine DOM-Veränderung und eine String-Konvertierung aus.

In einigen Fällen bleiben die beiden synchron. In anderen weichen sie ab. Der `value` eines `<input>` ist das klassische Beispiel: Das Attribut `value` ist der ursprüngliche Wert, während die `value`-Eigenschaft der aktuelle Wert ist, den der Benutzer eingegeben hat. Das Lesen des Attributs nach der Benutzereingabe gibt das ursprüngliche Markup zurück, während das Lesen der Eigenschaft den aktuellen Inhalt des Feldes zurückgibt.

Verwenden Sie **Eigenschaften** für:

- **Häufig wechselnden Laufzeitstatus**: Zähler, aktuelle Auswahlen, eingegebene Werte
- **Nicht-String-Typen**: Booleans, Zahlen, Objekte, Arrays
- **Leistungsempfindliche Updates**: Eigenschaften überspringen die für Attribute erforderliche String-Konvertierung

Verwenden Sie **Attribute** für:

- **Anfängliche Konfiguration**: Einstellungen, die die Komponente einmal beim Verbinden liest
- **CSS-Selektoren**: Werte, die Sie mit Selektoren wie `[disabled]` oder `[variant="danger"]` anvisieren möchten
- **Zugänglichkeitshooks**: `aria-label`, `role` und andere ARIA-Attribute
- **String-ähnliche Einstellungen, die sich selten ändern**

Überprüfen Sie bei der Umschließung einer Drittanbieter-Webkomponente die Dokumentation der Komponente, um zu bestätigen, welcher Name auf eine Eigenschaft und welcher auf ein Attribut abgebildet ist. Die Verwendung von `PropertyDescriptor.attribute()` für etwas, das die Komponente nur als Eigenschaft exponiert, funktioniert nicht, und umgekehrt. Die Komponente ignoriert den Wert stillschweigend.

### Typisierung von Eigenschaften {#typing-properties}

Ein Descriptor ist parametrisiert durch den Java-Typ seines Wertes. Die vollständige Deklarationssyntax ist:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Der generische Parameter `<T>` erklärt den Typ des Wertes. Der Laufzeittyp des Standardwertes legt ebenfalls `T` fest, sodass das generische Argument selten explizit angegeben werden muss. WebforJ verwendet `T`, um Werte zu serialisieren und zu deserialisieren, wenn mit dem Client kommuniziert wird.

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

Die Serialisierung erfolgt automatisch für primitive Typen, deren Boxed-Äquivalente und `String`. Für komplexe Typen wird der Wert vor der Zuweisung an die Eigenschaft auf dem Client als JSON serialisiert.

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

Für nullable Referenzen verwenden Sie `Objects.requireNonNull()`, damit der Fehler an der Grenze sichtbar wird und nicht später in der Render-Pipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading darf nicht null sein");
  set(heading, value);
  return this;
}
```

Vermeiden Sie die Validierung in `get()`. Leseoperationen sollten kostengünstig und konsistent bleiben.

### Enumartige Eigenschaften {#enum-style-properties}

Die meisten Webkomponenten erwarten Kleinbuchstaben oder Kebab-Fall-Schreibweisen für enum-ähnliche Eigenschaften (`theme="primary"`, `expanse="xs"`). WebforJ verwendet Gson, um Enums zu serialisieren, aber die Standarddarstellung von Gson ist der Konstantenname in Großbuchstaben. Annotieren Sie jede Konstante mit `@SerializedName`, damit der serialisierte Wert mit dem übereinstimmt, was die Webkomponente erwartet.

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

Deklarieren Sie den Descriptor mit dem Enum-Typ und verwenden Sie das Enum direkt im Setter und Getter.

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

`PropertyDescriptorTester` validiert, dass jeder `PropertyDescriptor` in einer Komponente korrekt verbunden ist. Er scannt die Klasse nach Descriptorfeldern, ruft jeden Setter mit dem Standardwert auf und vergleicht das Ergebnis mit dem, was der Getter zurückgibt. Der Tester erkennt Integrationsfehler, bevor sie eine laufende Anwendung erreichen: ein Setter, der in den falschen Descriptor schreibt, ein Getter, der eine andere Eigenschaft liest, ein Standardwert, der nicht zurückgerundet wird, oder ein fehlender Accessor für einen deklarierten Descriptor.

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

#### Ausschluss von Eigenschaften {#excluding-properties}

Einige Deskriptoren folgen nicht den Standard-Getter- und Setter-Konventionen oder sind auf externe Zustände angewiesen, die der Test nicht befriedigen kann. Annotieren Sie sie mit `@PropertyExclude`, um sie zu überspringen.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Benutzerdefinierte Getter- und Setter-Namen {#custom-getter-and-setter-names}

Wenn ein Descriptor nicht-standardisierte Zugriffsnamen verwendet, deklarieren Sie diese mit `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Der Parameter `target` akzeptiert eine Klasse, wenn die Accessoren an einem anderen Ort als der Komponente selbst leben.

Für weitere Detailinformationen zur Testoberfläche siehe [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Anliegen-Interfaces {#concern-interfaces}

Anliegen-Interfaces bieten einer `ElementComposite`-Unterklasse Komponentenfähigkeiten, ohne die Implementierung selbst schreiben zu müssen. Die Interfaces leiten Aufrufe an das zugrunde liegende Element weiter. Implementieren Sie die, die die Komponente unterstützen soll, parametriert mit dem Unterklassentyp, damit das Chaining die Komponente zurückgibt:

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

Die drei oben genannten Interfaces decken alles ab, was `MyBadge` benötigt, ohne dass Methoden im Klassencode erforderlich sind. `HasText` exponiert `setText()` und schreibt in den Textinhalt des Elements. `HasClassName` exponiert `addClassName()`, wodurch das Abzeichen über CSS angesteuert werden kann. `HasStyle` exponiert `setStyle()` für Inline-Styling.

Für die vollständige Liste der verfügbaren Interfaces und was jedes bietet, siehe [Anliegen-Interfaces](./component-fundamentals#concern-interfaces) im Artikel "Understanding Components". Wenn ein Standard-Forwarding nicht dem entspricht, was das umschlossene Element exponiert, überschreiben Sie die Methode in der Unterklasse.

## Ereignisse {#events}

### Ereignisregistrierung {#event-registration}

Webkomponenten senden DOM-Ereignisse, wenn im Browser etwas passiert. Um von Java aus zu reagieren, hören Sie auf diese Ereignisse mit `addEventListener()`. Die Menge an Ereignissen, die eine Komponente auslöst, variiert, überprüfen Sie daher die eigenen Dokumente der Komponente auf die verfügbaren Namen und Nutzdaten.

`ElementComposite` unterstützt Debouncing, Throttling, Filtern und benutzerdefinierte Ereignisdaten für registrierte Listener.

Registrieren Sie Ereignis-Listener mit der Methode `addEventListener()`:

```java
// Beispiel: Hinzufügen eines Klickereignis-Listeners
addEventListener(ElementClickEvent.class, event -> {
  // Handle das Klickereignis
});
```

:::info
`ElementComposite` akzeptiert nur Ereignisklassen, die mit `@EventName` annotiert sind, im Gegensatz zu `Element`, das jeden String-Ereignisnamen akzeptiert.
:::

### Eingebaute Ereignisklassen {#built-in-event-classes}

`ElementClickEvent` ist die einzige eingebaute Ereignisklasse, die `ElementComposite` mitliefert. Sie gibt Mausklickereignisse auf dem zugrunde liegenden Element mit typisierten Zugriffsmethoden für Koordinaten (`getClientX()`, `getClientY()`), Button-Informationen (`getButton()`) und Modifikatortasten (`isCtrlKey()`, `isShiftKey()` usw.) weiter.

Um die Klickverarbeitung in der öffentlichen API einer Unterklasse verfügbar zu machen, implementieren Sie das Ansatz-Interface `HasElementClickListener<T>`. Es stellt die Standardmethoden `onClick()` und `addClickListener()` zur Verfügung, die an die geschützte primitive Methode `addEventListener()` delegieren.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() und addClickListener() sind jetzt für MyBadge verfügbar
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Für jedes andere Ereignis, das die zugrunde liegende Webkomponente auslöst, definieren Sie eine benutzerdefinierte Ereignisklasse. Siehe [Benutzerdefinierte Ereignisklassen](#custom-event-classes).

### Ereignisdaten {#event-payloads}

Ereignisse übertragen Daten vom Client an Ihren Java-Code. Greifen Sie über `getData()` auf die rohen Ereignisdaten zu oder verwenden Sie typisierte Methoden, wenn diese in den eingebauten Ereignisklassen verfügbar sind. Siehe den [Ereignisse-Leitfaden](../building-ui/events) für weitere Informationen zur effizienten Handhabung von Nutzdaten.

### Benutzerdefinierte Ereignisklassen {#custom-event-classes}

Definieren Sie benutzerdefinierte Ereignisklassen mit `@EventName` und `@EventOptions`, um clientseitige Daten in einem typisierten Java-Ereignis zu erfassen. Verwenden Sie dies, wenn der Java-Handler Werte vom Browser benötigt.

`@EventName` bindet die Java-Klasse an das Ereignis, das die Komponente im Browser auslöst. Eine Klasse mit der Annotation `@EventName("change")` feuert jedes Mal, wenn das zugrunde liegende Element `change` auslöst. `@EventOptions` steuert, was zusammen mit diesem Ereignis zurückkommt. Jede `@EventData` darin koppelt einen Schlüssel mit einem JavaScript-Ausdruck, der gegen das DOM-Ereignis ausgewertet wird. Das Ergebnis ist über `getData().get(key)` in der Java-Ereignisklasse verfügbar.

Das untenstehende Produktbewertungsformular verwendet dieses Muster mit [`wa-rating`](https://webawesome.com/docs/components/rating/). Das benutzerdefinierte `ChangeEvent` trägt den Bewertungswert als typisierten `double`, und der Listener verwendet ihn, um die Schaltfläche "Absenden" zu aktivieren:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Ereignisoptionen {#event-options}

`ElementEventOptions` konfiguriert die Ereignisnutzdaten, Debounce- oder Throttle-Zeitgeber, Filterausdrücke und Pre-Execution-Code. Der folgende Code zeigt die Optionen:

```java
ElementEventOptions options = new ElementEventOptions()
  // Erfassen Sie benutzerdefinierte Daten vom Client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Führen Sie JavaScript aus, bevor das Ereignis ausgelöst wird
  .setCode("component.classList.add('processing');")

  // Nur auslösen, wenn Bedingungen erfüllt sind
  .setFilter("component.value.length >= 2")

  // Verzögerung der Ausführung, bis der Benutzer das Tippen stoppt (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Wenden Sie diese Optionen beim Registrieren eines Listeners für eine benutzerdefinierte Ereignisklasse an
// (siehe den Abschnitt über benutzerdefinierte Ereignisklassen oben, um zu erfahren, wie man eine definiert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` bietet nur die klassenspezifische Form `addEventListener(Class, listener, options)` an. Verwenden Sie sie mit einer Ereignisklasse, die mit `@EventName` annotiert ist. Um direkt gegen einen String-Ereignisnamen zu registrieren, rufen Sie `getElement().addEventListener("input", listener, options)` auf.
:::

#### Leistungssteuerung {#performance-control}

**Debouncing** verzögert die Ausführung, bis die Aktivität stoppt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Warte 300ms nach dem letzten Ereignis
```

Verfügbare Debounce-Phasen:

- `LEADING`: Sofort auslösen, dann warten
- `TRAILING`: Warte auf eine stille Phase, dann auslösen (Standard)
- `BOTH`: Sowohl sofort als auch nach einer stillen Phase auslösen

**Throttling** begrenzt die Ausführungsfrequenz:

```java
options.setThrottle(100); // Höchstens einmal pro 100ms auslösen
```

## Interagieren mit Slots {#interacting-with-slots}

Slots sind Platzhalter innerhalb einer Webkomponente, die Benutzer mit Inhalten füllen. Die Webkomponente deklariert ihre Slots in ihrer Vorlage mit `<slot>` oder `<slot name="...">`, und der Wrapper stellt Methoden zur Verfügung, die Java-Komponenten in diese Slots einfügen.

Um Inhalte zu Slots hinzuzufügen, erweitern Sie `ElementCompositeContainer` anstelle von `ElementComposite`. Der Container enthält die gleiche Eigenschaften- und Attributmechanik sowie die Methoden, die benötigt werden, um Kinder hinzuzufügen. Kinder, die über `add()` hinzugefügt werden, gehen in den Standardslot. Kinder, die über `getElement().add(slotName, components)` hinzugefügt werden, gehen in den benannten Slot.

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

Die Demo unten zeigt zwei Preis-Karten, die mit [`wa-card`](https://webawesome.com/docs/components/card/) erstellt wurden und die `header`, den Standardslot und die `footer`-Slots aus Java befüllen:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Überprüfen des Inhalts von Slots {#inspecting-slot-contents}

Das zugrunde liegende `Element` (erreichbar über `getElement()`) stellt Methoden zum Lesen dessen bereit, was derzeit den Slots zugewiesen ist:

- **`findComponentSlot()`**: durchsucht alle Slots nach einer bestimmten Komponente und gibt den Namen des Slots zurück, der sie enthält, oder einen leeren String, wenn die Komponente sich in keinem Slot befindet.
- **`getComponentsInSlot()`**: gibt die Liste der Komponenten zurück, die einem bestimmten Slot zugewiesen sind. Optional kann ein Klassentyp zur Filterung der Ergebnisse übergeben werden.
- **`getFirstComponentInSlot()`**: gibt die erste Komponente zurück, die einem Slot zugewiesen ist. Optional kann ein Klassentyp zur Filterung übergeben werden.
