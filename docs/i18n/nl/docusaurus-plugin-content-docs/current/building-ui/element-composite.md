---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: b8099816ab51d246d3a69c2ca8bd9108
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse wikkelt een aangepast HTML-element of [webcomponent](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Het bindt je Java-klasse aan het onderliggende `Element` en laat je werken met de eigenschappen, attributen en gebeurtenissen van dat element via Java. Gebruik het wanneer je webcomponenten in een webforJ-app integreert.

:::tip Wanneer `ElementComposite` te gebruiken
Kies voor `ElementComposite` wanneer je een derdepartij webcomponent wikkelt die webforJ nog niet biedt. Als een ingebouwde webforJ-component de gebruikscasus dekt (`TextField`, `ColorField`, `Button`, enzovoort), gebruik die dan in plaats daarvan. Voor eenmalig DOM-werk dat niet hergebruikt hoeft te worden, kan de `Element` klasse direct zonder een wrapper gebruikt worden.
:::

Deze gids demonstreert hoe de [Shoelace relative-time webcomponent](https://shoelace.style/components/relative-time) kan worden geïmplementeerd met behulp van de `ElementComposite` klasse.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klasse annotaties {#class-annotations}

Drie annotaties komen vaak voor aan de bovenkant van een `ElementComposite` subklasse: `@NodeName` verklaart de HTML-tag die de component wikkelt, en `@JavaScript` en `@StyleSheet` laden eventuele client-side middelen die de onderliggende webcomponent nodig heeft. `@NodeName` is vereist en specifiek voor `ElementComposite`. `@JavaScript` en `@StyleSheet` zijn algemene webforJ-middelen annotaties en werken op elke klasse, inclusief views, componenten of de `App` klasse.

### `@NodeName` {#nodename}

De `@NodeName` annotatie verklaart de HTML-tag die de component wikkelt. webforJ gebruikt deze naam bij het aanmaken van het onderliggende element in de DOM.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

De tagnaam moet overeenkomen met het aangepaste element dat op de client is geregistreerd. Zonder deze annotatie kan het framework niet bepalen welk element moet worden aangemaakt.

Binnen een subklasse leest `getNodeName()` de verklaarde tag terug, en `getElement()` retourneert het onderliggende `Element` zodat je DOM-methoden er direct op kunt aanroepen.

### `@JavaScript` {#javascript}

De `@JavaScript` annotatie laadt het script dat de onderliggende webcomponent definieert of registreert. Plaats het op de klasse zodat het script alleen wordt geladen wanneer de component wordt gebruikt.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Meerdere `@JavaScript` annotaties zijn toegestaan, en webforJ voorkomt automatisch dubbele laadverzoeken. Hetzelfde script wordt niet twee keer geladen als verschillende componenten eraf afhangen.

Zie [Importeren van JavaScript-bestanden](../managing-resources/importing-assets#importing-javascript-files) voor de volledige set opties, inclusief `top`, `attributes`, en laadtijd.

### `@StyleSheet` {#stylesheet}

De `@StyleSheet` annotatie laadt een CSS-bestand waar de component afhankelijk van is. Het is nuttig voor derdepartij componenten die een aparte stijldocument leveren, of voor het bundelen van component-specifieke styling naast de wrapper.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Voor lokaal gebundelde middelen gebruik je het `ws://` prefix om bestanden in `resources/static` te verwijzen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Zie [Importeren van CSS-bestanden](../managing-resources/importing-assets#importing-css-files) voor de volledige set opties.

## Eigenschappen en attribuut descriptors {#property-and-attribute-descriptors}

Eigenschappen en attributen vertegenwoordigen de staat van een webcomponent, die meestal gegevens of configuratie vasthouden. `ElementComposite` maakt beide toegankelijk via `PropertyDescriptor`.

Twee fabrieksmethoden op `PropertyDescriptor` produceren de descriptor zelf, één per binddoel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindt aan een JavaScript-eigenschap op de DOM-knoop. `PropertyDescriptor.attribute()` bindt aan een HTML-attribuut. Het eerste argument is de naam die de webcomponent verwacht. Het tweede is een standaardwaarde, die ook het Java-type van de descriptor vastlegt.

Declareer de descriptor als een privé veld op de component, en lees en schrijf erdoor met `set(PropertyDescriptor<V> property, V value)` en `get(PropertyDescriptor<V> property)`.

:::info
Eigenschappen zijn interne status op de DOM-knoop en worden niet weergegeven in de markup. Attributen zijn HTML-markup, zichtbaar voor externe scripts en CSS.
:::

```java
// Voorbeeld van een eigenschap genaamd "title" in een ElementComposite klasse
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Voorbeeld van een attribuut genaamd "value" in een ElementComposite klasse
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mijn Titel");
set(value, "Mijn Waarde");
```

Bovenstaande aanroepen gebruiken `set()` direct om de primitieve vorm te tonen. In de praktijk zijn `set()` en `get()` `protected` methoden op `ElementComposite`. Het zijn de primitieve lagen die Java-waarden synchroniseren met het onderliggende element, niet de publieke API die consumenten aanroepen. Het beoogde patroon is om de `PropertyDescriptor` privé te houden en publieke `setX()` en `getX()` methoden te schrijven die naar de primitieve methoden doorverwijzen.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // protected primitief
    return this;
  }

  public String getHeading() {
    return get(heading);     // protected primitief
  }
}
```

Een enkele aanroep naar `set(descriptor, value)` doet drie dingen in één keer. Het duwt de waarde naar de client via `setProperty()` voor eigenschappen, of `setAttribute()` voor attributen. Het slaat de waarde op in een lokale server-side cache, één kaart per componentinstantie. En het registreert het runtime-type naast de waarde, zodat latere `get()` aanroepen weten hoe ze moeten deserialiseren.

Die lokale cache is de reden waarom `get()` standaard goedkoop kan zijn. `get(descriptor)` retourneert de gecachte waarde uit de server-side opslag zonder netwerkoproep, omdat elke `set()` de cache synchroniseert met de client. Het optionele `boolean` tweede argument bestuurt of de cache moet worden omzeild en van de browser moet worden gelezen.

```java
String cached = get(heading);            // leest uit de server-side cache
String live = get(heading, true);        // dwingt een lezing uit de browser
```

Stel `fromClient` in op true wanneer de waarde op de client kan veranderen zonder dat de server het weet, zoals een getypte `<input>` waarde. Voor server-gedreven eigenschappen vermijdt de standaard een round trip.

Het optionele derde argument is een `java.lang.reflect.Type` en bestiert hoe het resultaat wordt deserialiseerd. webforJ lost het type in deze volgorde op: het expliciete `Type` argument indien doorgegeven, vervolgens het runtime-type dat door een vorige `set()` op dezelfde descriptor is geregistreerd, en ten slotte `Object.class`. In de praktijk is het type dat door een eerdere `set()` is geregistreerd vaak voldoende, zodat het derde argument meestal kan worden weggelaten. Het is nodig wanneer de geregistreerde klasse informatie verliest waarop de deserializer vertrouwt, zoals een parameterized type zoals `List<String>` waarvan de runtime-klasse gewoon `ArrayList` is.

De demo hieronder voegt eigenschappen toe voor relative-time op basis van de documentatie van de webcomponent en maakt deze toegankelijk via getters en setters. Elke rij in de activiteit feed gebruikt verschillende `format` en `numeric` waarden om te tonen hoe dezelfde component onder verschillende configuraties rendert.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Eigenschappen versus attributen {#properties-versus-attributes}

Hoewel `PropertyDescriptor.property()` en `PropertyDescriptor.attribute()` uitwisselbaar lijken, richten ze zich op verschillende delen van het onderliggende element. Het kiezen van de verkeerde leidt tot waarden die stilletjes niet worden toegepast.

Eigenschappen zijn JavaScript objecteigenschappen op de DOM-knoop. Ze kunnen elk type vasthouden, inclusief strings, booleans, getallen, objecten en arrays, en ze vertegenwoordigen de huidige runtime-staat van het element. Het instellen van een eigenschap is een directe JavaScript-toewijzing.

Attributen zijn HTML-markup. Ze bevinden zich op de openingstag van het element, zijn altijd strings, en vertegenwoordigen de initiële configuratie van het element. Het instellen van een attribuut maakt een DOM-mutatie en een stringconversie in gang.

Voor sommige gevallen blijven de twee in sync. Voor anderen divergeren ze. De `value` van een `<input>` is het klassieke voorbeeld: het `value` attribuut is de initiële waarde, terwijl de `value` eigenschap de huidige waarde is die de gebruiker heeft getypt. Het lezen van het attribuut nadat de gebruiker typt geeft terug de oorspronkelijke markup, maar het lezen van de eigenschap geeft de huidige inhoud van het veld terug.

Gebruik **eigenschappen** voor:

- **Frequent wisselende runtime status**: tellers, huidige selecties, getypte waarden
- **Niet-string types**: booleans, getallen, objecten, arrays
- **Prestatiegevoelige updates**: eigenschappen omzeilen de stringconversie die voor attributen nodig is

Gebruik **attributen** voor:

- **Initiële configuratie**: instellingen die de component eenmaal leest wanneer deze wordt verbonden
- **CSS-selectors**: waarden die je wilt targetten met selectors zoals `[disabled]` of `[variant="danger"]`
- **Toegankelijkheidskoppelingen**: `aria-label`, `role`, en andere ARIA-attributen
- **String-achtige instellingen die zelden veranderen**

Wanneer je een derdepartij webcomponent wikkelt, controleer dan de documentatie van de component om te bevestigen welke naam bij een eigenschap hoort en welke bij een attribuut. Het gebruik van `PropertyDescriptor.attribute()` voor iets dat de component alleen als eigenschap blootlegt, werkt niet, en hetzelfde geldt in omgekeerde richting. De component zal de waarde stilletjes negeren.

### Typen van eigenschappen {#typing-properties}

Een descriptor is geparameteriseerd door het Java-type van zijn waarde. De volledige declaratiesyntax is:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

De `<T>` generieke parameter verklaart het type van de waarde. Het runtime-type van de standaardwaarde legt ook `T` vast, zodat de generieke argumenten zelden expliciet hoeven te worden opgegeven. webforJ gebruikt `T` om waarden te serialiseren en deserialiseren bij communicatie met de client.

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

Serialisatie is automatisch voor primitieve types, hun verpakte equivalente, en `String`. Voor complexe types wordt de waarde als JSON gechocoletteerd voordat deze aan de eigenschap op de client wordt toegewezen.

### Waarden valideren {#validating-values}

Valideer waarden in de setter voordat je `set()` aanroept. De setter is het natuurlijke handhavingpunt omdat elke mutatie er doorheen gaat.

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max moet niet-negatief zijn");
  }
  set(max, value);
  return this;
}
```

Voor nullbare referenties gebruik `Objects.requireNonNull()` zodat de fout aan de rand zichtbaar is in plaats van later in de rendering-pijplijn.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading kan niet null zijn");
  set(heading, value);
  return this;
}
```

Vermijd valideren in `get()`. Lezingen moeten goedkoop en consistent blijven.

### Enum-stijl eigenschappen {#enum-style-properties}

De meeste webcomponenten verwachten kleine letter of kebab-case stringwaarden voor enum-achtige eigenschappen (`theme="primary"`, `expanse="xs"`). webforJ gebruikt Gson om enums te serialiseren, maar de standaardweergave van Gson is de constante naam in hoofdletters. Annotateer elke constante met `@SerializedName` zodat de serialisatie waarde overeenkomt met wat de webcomponent verwacht.

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

Declareer de descriptor met het enum-type en gebruik de enum direct in de setter en getter.

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

Dit is hetzelfde patroon dat de ingebouwde componenten van webforJ gebruiken voor `Theme`, `Expanse`, en soortgelijke enums. De publieke Java API blijft type-veilig, en de waarde die de webcomponent ontvangt is de string van `@SerializedName`.

### Eigenschappen testen {#testing-properties}

`PropertyDescriptorTester` valideert dat elke `PropertyDescriptor` in een component correct is aangesloten. Het scant de klasse naar descriptorvelden, roept elke setter aan met de standaardwaarde, en vergelijkt het resultaat met wat de getter retourneert. De tester vangt integratiefouten voordat ze een draaiende app bereiken: een setter die naar de verkeerde descriptor schrijft, een getter die een andere eigenschap leest, een standaardwaarde die niet rondreist, of een ontbrekende accessor voor een verklaarde descriptor.

Een basis-test voor een component ziet er als volgt uit:

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

#### Uitsluiten van eigenschappen {#excluding-properties}

Sommige descriptors volgen niet de standaard getter en setter conventies, of ze zijn afhankelijk van externe status die de test niet kan voldoen. Annotateer ze met `@PropertyExclude` om ze over te slaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Aangepaste getter en setter namen {#custom-getter-and-setter-names}

Als een descriptor niet-standaard accessor-namen gebruikt, declareer ze met `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

De `target` parameter accepteert een klasse wanneer de accessoren zich ergens anders bevinden dan de component zelf.

Voor meer details over de testoppervlakte zie [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Zorginterfaces {#concern-interfaces}

Zorginterfaces geven een `ElementComposite` subklasse componentmogelijkheden zonder zelf de implementatie te schrijven. De interfaces sturen oproepen door naar het onderliggende element. Implementeer de interfaces die de component zou moeten ondersteunen, geparameteriseerd met het subklasstype zodat chaining de component retourneert:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Geen implementatie nodig.
}

MyBadge badge = new MyBadge()
    .setText("Nieuw")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

De drie hierboven genoemde interfaces dekken alles wat `MyBadge` nodig heeft zonder enige method bodies in de klasse. `HasText` exposeert `setText()` en schrijft naar de tekstinhoud van het element. `HasClassName` exposeert `addClassName()`, waarmee de badge vanuit CSS kan worden getarget. `HasStyle` exposeert `setStyle()` voor inline styling.

Voor de volledige set beschikbare interfaces en wat elk ervan biedt, zie [Zorginterfaces](./component-fundamentals#concern-interfaces) in het artikel Over Componenten. Als een standaarddoorverwijzing niet overeenkomt met wat het gewikkelde element aanbiedt, overschrijf dan de methode in de subklasse.

## Gebeurtenissen {#events}

### Evenementregistratie {#event-registration}

Webcomponenten versturen DOM-gebeurtenissen wanneer er iets gebeurt in de browser. Om te reageren vanuit Java, luister naar die gebeurtenissen met `addEventListener()`. De set gebeurtenissen die een component verzendt varieert, dus controleer de documentatie van de component zelf voor de namen en payloads die beschikbaar zijn.

`ElementComposite` ondersteunt debouncing, throttling, filtering en aangepaste evenementgegevens op geregistreerde listeners.

Registreer gebeurtenisluiters met de `addEventListener()` methode:

```java
// Voorbeeld: Een click-gebeurtenislistener toevoegen
addEventListener(ElementClickEvent.class, event -> {
  // Verwerk de klikgebeurtenis
});
```

:::info
`ElementComposite` accepteert alleen gebeurtenisklassen die zijn geannoteerd met `@EventName`, in tegenstelling tot `Element`, dat elke string gebeurtenisnaam accepteert.
:::

### Ingebouwde gebeurtenisklassen {#built-in-event-classes}

`ElementClickEvent` is de enige ingebouwde gebeurtenisklasse die `ElementComposite` meegeeft. Het brengt muisklikgebeurtenissen op het onderliggende element naar voren met getypte accessoren voor coördinaten (`getClientX()`, `getClientY()`), knopinformatie (`getButton()`) en modifier-toetsen (`isCtrlKey()`, `isShiftKey()`, enzovoorts).

Om het klikken op de publieke API van een subklasse beschikbaar te maken, implementeer de `HasElementClickListener<T>` zorginterface. Het biedt standaard `onClick()` en `addClickListener()` methoden die doorverwijzen naar de beschermde `addEventListener()` primitief.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() en addClickListener() zijn nu beschikbaar op MyBadge
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Voor elke andere gebeurtenis die de onderliggende webcomponent verzendt, definieer een aangepaste gebeurtenisklasse. Zie [Aangepaste gebeurtenisklassen](#custom-event-classes).

### Gebeurtenispayloads {#event-payloads}

Gebeurtenissen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor ruwe gebeurtenisgegevens of gebruik getypte methoden wanneer beschikbaar op ingebouwde gebeurtenisklassen. Zie de [Gids voor gebeurtenissen](../building-ui/events) voor meer informatie over efficiënte payloadverwerking.

### Aangepaste gebeurtenisklassen {#custom-event-classes}

Definieer aangepaste gebeurtenisklassen met `@EventName` en `@EventOptions` om client-side gegevens te vangen in een getypte Java-gebeurtenis. Gebruik dit wanneer de Java-handler waarden uit de browser nodig heeft.

`@EventName` bindt de Java-klasse aan de gebeurtenis die de component in de browser verzendt, zodat een klasse die is geannoteerd met `@EventName("sl-change")` wordt geactiveerd telkens wanneer het onderliggende element `sl-change` uitgeeft. `@EventOptions` bestiert wat er met die gebeurtenis meereist. Elke `@EventData` erin koppelt een sleutel aan een JavaScript-expressie die wordt geëvalueerd tegen de DOM-gebeurtenis. Het resultaat is beschikbaar in de Java-gebeurtenisklasse via `getData().get(key)`.

Het productbeoordelingsformulier hieronder gebruikt dit patroon met [`sl-rating`](https://shoelace.style/components/rating). De aangepaste `ChangeEvent` draagt de beoordelingswaarde als een getypte `double`, en de listener gebruikt deze om de verzendknop te activeren:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Gebeurtenisopties {#event-options}

`ElementEventOptions` configureert de gebeurtenispayload, debounce- of throttletiming, filterexpressies, en pre-uitvoer code. De onderstaande snippet laat de opties zien:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Voer JavaScript uit voordat het evenement afgevuurd wordt
  .setCode("component.classList.add('processing');")

  // Vuur alleen af als de voorwaarden zijn vervuld
  .setFilter("component.value.length >= 2")

  // Vertraging van uitvoering totdat de gebruiker stopt met typen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Pas deze opties toe bij het registreren van een listener voor een aangepaste gebeurtenisklasse
// (zie de sectie Aangepaste gebeurtenisklassen hierboven voor hoe je er een definieert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` exposed alleen de klasse-gebaseerde vorm `addEventListener(Class, listener, options)`. Gebruik het met een gebeurtenisklasse die is geannoteerd met `@EventName`. Om direct tegen een string gebeurtenisnaam te registreren, roep je `getElement().addEventListener("input", listener, options)` aan.
:::

#### Prestatiecontrole {#performance-control}

**Debouncing** vertraagt de uitvoering totdat activiteit stoppen:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300ms na laatste evenement
```

Beschikbare debouce-fasen:

- `LEADING`: Vlug meteen afvuren, dan wachten
- `TRAILING`: Wacht op een stille periode, dan afvuren (standaard)
- `BOTH`: Vuur onmiddellijk af en na stille periode

**Throttling** beperkt de frequentie van uitvoering:

```java
options.setThrottle(100); // Vuur maximaal één keer per 100ms af
```

## Interageren met slots {#interacting-with-slots}

Slots zijn tijdelijke plaatsen binnen een webcomponent die gebruikers vullen met inhoud. De webcomponent verklaart zijn slots in zijn sjabloon met `<slot>` of `<slot name="...">`, en de wrapper blootlegt methoden die Java-componenten in die slots plaatsen.

Om inhoud aan slots toe te voegen, breid je `ElementCompositeContainer` uit in plaats van `ElementComposite`. De container draagt dezelfde eigenschappen- en attribuutmachinerie plus de methoden die nodig zijn om kinderen toe te voegen. Kinderen die via `add()` worden toegevoegd gaan in de standaardslot. Kinderen die via `getElement().add(slotName, components)` worden toegevoegd gaan in de genoemde slot.

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

De demo hieronder toont twee prijskaarten gemaakt met [`sl-card`](https://shoelace.style/components/card), die de `header`, standaard- en `footer` slots vanuit Java populeren:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspecteren van slotinhoud {#inspecting-slot-contents}

Het onderliggende `Element` (toegankelijk via `getElement()`) biedt methoden om terug te lezen wat momenteel aan slots is toegewezen:

- **`findComponentSlot()`**: zoekt alle slots naar een specifieke component en retourneert de naam van de slot die deze bevat, of een lege string als de component zich in geen enkele slot bevindt.
- **`getComponentsInSlot()`**: retourneert de lijst van componenten die aan een bepaalde slot zijn toegewezen. Neemt optioneel een klassetype om de resultaten te filteren.
- **`getFirstComponentInSlot()`**: retourneert de eerste component die aan een slot is toegewezen. Neemt optioneel een klassetype om te filteren.
