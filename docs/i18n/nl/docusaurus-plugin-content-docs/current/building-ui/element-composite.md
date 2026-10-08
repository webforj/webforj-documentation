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

De `ElementComposite` klasse wikkelt een aangepast HTML-element of [webcomponent](https://developer.mozilla.org/en-US/docs/Web/API/Web_components) in. Het bindt je Java-klasse aan het onderliggende `Element` en stelt je in staat om met de eigenschappen, attributen en gebeurtenissen van dat element te werken via Java. Gebruik het wanneer je webcomponenten in een webforJ-app integreert.

:::tip Wanneer `ElementComposite` te gebruiken
Gebruik `ElementComposite` wanneer je een webcomponent van een derde partij wikkelt die webforJ niet al aanbiedt. Als een ingebouwde webforJ-component het gebruiksgeval dekt (`TextField`, `ColorField`, `Button`, enzovoort), gebruik dan in plaats daarvan die. Voor eenmalig DOM-werk dat niet hergebruikt hoeft te worden, kan de `Element`-klasse rechtstreeks zonder een wrapper worden gebruikt.
:::

Deze gids demonstreert hoe je de [Web Awesome relative-time webcomponent](https://webawesome.com/docs/components/relative-time/) kunt implementeren met behulp van de `ElementComposite` klasse.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klasse annotaties {#class-annotations}

Drie annotaties verschijnen meestal aan de bovenkant van een `ElementComposite` subclass: `@NodeName` verklaart de HTML-tag die de component wikkelt, en `@JavaScript` en `@StyleSheet` laden alle client-side bronnen waarvan de onderliggende webcomponent afhankelijk is. `@NodeName` is verplicht en specifiek voor `ElementComposite`. `@JavaScript` en `@StyleSheet` zijn algemene webforJ bronnennotaties en werken op elke klasse, inclusief views, componenten of de `App` klasse.

### `@NodeName` {#nodename}

De `@NodeName` annotatie verklaart de HTML-tag die de component wikkelt. webforJ gebruikt deze naam bij het creëren van het onderliggende element in de DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

De tagnaam moet overeenkomen met het aangepaste element dat op de client is geregistreerd. Zonder deze annotatie kan het framework niet bepalen welk element moet worden aangemaakt.

Binnen een subclass leest `getNodeName()` de verklaarde tag, en `getElement()` retourneert het onderliggende `Element` zodat je DOM-methoden er rechtstreeks op kunt aanroepen.

### `@JavaScript` {#javascript}

De `@JavaScript` annotatie laadt het script dat de onderliggende webcomponent definieert of registreert. Plaats het op de klasse zodat het script alleen wordt geladen wanneer de component wordt gebruikt.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Meerdere `@JavaScript` annotaties zijn toegestaan, en webforJ verwijdert automatisch dubbele laadbeurten. Hetzelfde script wordt niet twee keer geladen als verschillende componenten ervan afhankelijk zijn.

Zie [JavaScript-bestanden importeren](../managing-resources/importing-assets#importing-javascript-files) voor de volledige set opties, inclusief `top`, `attributes`, en laadtiming.

### `@StyleSheet` {#stylesheet}

De `@StyleSheet` annotatie laadt een CSS-bestand waarop de component afhankelijk is. Het is nuttig voor componenten van derden die een aparte stylesheet meesturen, of voor het bundelen van component-specifieke styling samen met de wrapper.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Voor lokaal gebundelde middelen, gebruik de `ws://` prefix om bestanden in `resources/static` te verwijzen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Zie [CSS-bestanden importeren](../managing-resources/importing-assets#importing-css-files) voor de volledige set opties.

## Eigenschappen en attribuut descriptors {#property-and-attribute-descriptors}

Eigenschappen en attributen vertegenwoordigen de status van een webcomponent, houden doorgaans gegevens of configuratie vast. `ElementComposite` maakt beiden toegankelijk via `PropertyDescriptor`.

Twee fabrieksmethoden op `PropertyDescriptor` produceren de descriptor zelf, één per binden doel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindt aan een JavaScript-eigenschap op de DOM-knoop. `PropertyDescriptor.attribute()` bindt aan een HTML-attribuut. Het eerste argument is de naam die de webcomponent verwacht. Het tweede is een standaardwaarde, die ook het Java-type van de descriptor vastlegt.

Declareer de descriptor als een private veld op de component, lees en schrijf er vervolgens doorheen met `set(PropertyDescriptor<V> property, V value)` en `get(PropertyDescriptor<V> property)`.

:::info
Eigenschappen zijn interne status op de DOM-knop en zijn niet zichtbaar in de markup. Attributen zijn HTML-markup, zichtbaar voor externe scripts en CSS.
:::

```java
// Voorbeeld eigenschap genaamd "title" in een ElementComposite klasse
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Voorbeeld attribuut genaamd "value" in een ElementComposite klasse
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mijn Titel");
set(value, "Mijn Waarde");
```

De bovenstaande oproepen gebruiken `set()` direct om de primitieve vorm te tonen. In de praktijk zijn `set()` en `get()` `protected` methoden op `ElementComposite`. Het zijn de primitieve laag die Java-waarden synchroniseert met het onderliggende element, niet de publieke API waar consumenten naar verwijzen. Het bedoelde patroon is om de `PropertyDescriptor` privé te houden en openbare `setX()` en `getX()` methoden te schrijven die naar de primitieve methoden verwijzen.

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

Een enkele oproep aan `set(descriptor, value)` doet drie dingen tegelijk. Het duwt de waarde naar de client via `setProperty()` voor eigenschappen, of `setAttribute()` voor attributen. Het slaat de waarde op in een lokale server-side cache, één map per componentinstantie. En het legt het runtime-type vast naast de waarde, zodat latere `get()`-oproepen weten hoe ze moeten deserialiseren.

Die lokale cache is de reden dat `get()` standaard goedkoop kan zijn. `get(descriptor)` retourneert de gecachte waarde uit de server-side opslag zonder netwerkoproep, omdat elke `set()` de cache synchroniseert met de client. Het optionele tweede argument van type `boolean` controleert of de cache omzeild moet worden en in plaats daarvan van de browser gelezen moet worden.

```java
String cached = get(heading);            // leest uit de server-side cache
String live = get(heading, true);        // dwingt een lezing uit de browser
```

Stel `fromClient` in op true wanneer de waarde aan de client kan veranderen zonder kennis van de server, zoals een getypte `<input>` waarde. Voor server-gestuurde eigenschappen vermijdt de standaard een round trip.

Het optionele derde argument is een `java.lang.reflect.Type` en controleert hoe het resultaat wordt deserialiseerd. webforJ bepaalt het type in deze volgorde: het expliciete `Type` argument als het is doorgegeven, dan het runtime-type vastgelegd door een eerdere `set()` op de dezelfde descriptor, dan `Object.class`. In de praktijk is het type dat door een eerdere `set()` is vastgelegd meestal voldoende, zodat het derde argument meestal kan worden weggelaten. Het is nodig wanneer de vastgelegde klasse informatie verliest waar de deserializer afhankelijk van is, zoals een geparameteriseerd type als `List<String>` waarvan de runtime-klasse gewoon `ArrayList` is.

De demo hieronder voegt eigenschappen voor relative-time toe op basis van de documentatie van de webcomponent en stelt ze bloot via getters en setters. Elke rij in de activiteitenfeed gebruikt verschillende `format` en `numeric` waarden om te laten zien hoe dezelfde component onder verschillende configuraties wordt weergegeven.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Eigenschappen versus attributen {#properties-versus-attributes}

Hoewel `PropertyDescriptor.property()` en `PropertyDescriptor.attribute()` er uitzien als uitwisselbaar, richten ze zich op verschillende delen van het onderliggende element. Het kiezen van de verkeerde resulteert in waarden die stilletjes niet worden toegepast.

Eigenschappen zijn JavaScript-objecteigenschappen op de DOM-knoop. Ze kunnen elk type bevatten, inclusief strings, booleans, nummers, objecten en arrays, en ze vertegenwoordigen de huidige runtime-status van de element. Het instellen van een eigenschap is een directe JavaScript-toewijzing.

Attributen zijn HTML-markup. Ze leven op de openings-tag van het element, zijn altijd strings en vertegenwoordigen de initiële configuratie van het element. Het instellen van een attribuut triggert een DOM-mutatie en een stringconversie.

Voor sommige gevallen blijven de twee in sync. Voor anderen divergeren ze. De `value` van een `<input>` is het klassieke voorbeeld: het `value` attribuut is de initiële waarde, terwijl de `value` eigenschap de huidige waarde is die de gebruiker heeft getypt. Het lezen van het attribuut nadat de gebruiker typt geeft de oorspronkelijke markup terug, maar het lezen van de eigenschap geeft de huidige inhoud van het veld terug.

Gebruik **eigenschappen** voor:

- **Frequent veranderende runtime-status**: tellers, huidige selecties, getypte waarden
- **Niet-string types**: booleans, nummers, objecten, arrays
- **Prestaties gevoelige updates**: eigenschappen omzeilen de stringconversie die vereist is voor attributen

Gebruik **attributen** voor:

- **Initiële configuratie**: instellingen die de component één keer leest bij de verbinding
- **CSS-selectors**: waarden die je wilt targeten met selectors zoals `[disabled]` of `[variant="danger"]`
- **Toegankelijkheidshooks**: `aria-label`, `role`, en andere ARIA-attributen
- **String-achtige instellingen die zelden veranderen**

Wanneer je een webcomponent van een derde partij wikkelt, controleer de documentatie van de component om te bevestigen welke naam overeenkomt met een eigenschap en welke met een attribuut. Het gebruik van `PropertyDescriptor.attribute()` voor iets dat de component alleen als een eigenschap exposeert, zal niet werken, en hetzelfde geldt omgekeerd. De component zal de waarde stilletjes negeren.

### Typen eigenschappen {#typing-properties}

Een descriptor is geparametriseerd door het Java-type van de waarde. De volledige declaratie-syntaxis is:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

De `<T>` generieke parameter verklaart het type van de waarde. Het runtime-type van de standaardwaarde legt ook `T` vast, zodat de generieke argument zelden expliciet hoeft te worden gespecificeerd. webforJ gebruikt `T` om waarden te serialiseren en deserialiseren bij communicatie met de client.

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

Serialisatie is automatisch voor primitieve types, hun verpakte equivalenten, en `String`. Voor complexe types wordt de waarde als JSON geserialiseerd voordat het aan de eigenschap op de client wordt toegewezen.

### Waarden valideren {#validating-values}

Valideer waarden in de setter voordat je `set()` aanroept. De setter is het natuurlijke handhavingspunt omdat elke mutatie erdoorheen stroomt.

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

Voor nullable referenties, gebruik `Objects.requireNonNull()` zodat de fout aan de grens naar voren komt in plaats van later in de renderpijplijn.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading kan niet null zijn");
  set(heading, value);
  return this;
}
```

Vermijd validatie in `get()`. Lezingen moeten goedkoop en consistent blijven.

### Enum-achtige eigenschappen {#enum-style-properties}

De meeste webcomponenten verwachten lowercase of kebab-case stringwaarden voor enum-achtige eigenschappen (`theme="primary"`, `expanse="xs"`). webforJ gebruikt Gson om enums te serialiseren, maar Gson's standaardrepresentatie is de constante naam in hoofdletters. Annotateer elke constante met `@SerializedName` zodat de geserialiseerde waarde overeenkomt met wat de webcomponent verwacht.

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

Dit is hetzelfde patroon dat de ingebouwde componenten van webforJ gebruiken voor `Theme`, `Expanse`, en vergelijkbare enums. De publieke Java API blijft type-veilig, en de waarde die de webcomponent ontvangt is de string vanuit `@SerializedName`.

### Eigenschappen testen {#testing-properties}

`PropertyDescriptorTester` valideert dat elke `PropertyDescriptor` in een component correct is aangesloten. Het scant de klasse op descriptor-velden, roept elke setter aan met de standaardwaarde, en vergelijkt het resultaat met wat de getter retourneert. De tester vangt integratiefouten op voordat ze een draaiende app bereiken: een setter die naar de verkeerde descriptor schrijft, een getter die een andere eigenschap leest, een standaardwaarde die niet rondreist, of een ontbrekende accessor voor een verklaarde descriptor.

Een basis test voor een component ziet er als volgt uit:

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

#### Eigenschappen uitsluiten {#excluding-properties}

Sommige descriptors volgen geen standaard getter- en setterconventies, of ze zijn afhankelijk van externe status die de test niet kan waarmaken. Annotateer ze met `@PropertyExclude` om ze over te slaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Aangepaste getter- en setter-namen {#custom-getter-and-setter-names}

Als een descriptor niet-standaard accessor-namen gebruikt, verklaar ze dan met `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

De parameter `target` accepteert een klasse wanneer de accessoren zich ergens anders bevinden dan de component zelf.

Voor meer details over de testoppervlakte, zie [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Concern interfaces {#concern-interfaces}

Concern interfaces geven een `ElementComposite` subclass component mogelijkheden zonder het zelf te implementeren. De interfaces doorsturen oproepen naar het onderliggende element. Implementeer degene die de component moet ondersteunen, geparameteriseerd met het subtype zodat chaining de component retourneert:

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

De drie interfaces hierboven dekken alles wat `MyBadge` nodig heeft zonder enige methode-lichamen in de klasse. `HasText` maakt `setText()` beschikbaar en schrijft naar de tekstinhoud van het element. `HasClassName` maakt `addClassName()` beschikbaar, wat het mogelijk maakt dat de badge vanuit CSS wordt getarget. `HasStyle` maakt `setStyle()` beschikbaar voor inline styling.

Voor de volledige set beschikbare interfaces en wat elke biedt, zie [Concern interfaces](./component-fundamentals#concern-interfaces) in het artikel Begrijpen van Componenten. Als een standaard doorsturen niet overeenkomt met wat het gewikkelde element exposeert, overschrijf dan de methode in de subclass.

## Gebeurtenissen {#events}

### Evenementregistratie {#event-registration}

Webcomponenten versturen DOM-gebeurtenissen wanneer er iets gebeurt in de browser. Om hierop te reageren vanuit Java, luister naar die evenementen met `addEventListener()`. De set van gebeurtenissen die een component verstuurt varieert, dus controleer de eigen documentatie van de component voor de namen en payloads die beschikbaar zijn.

`ElementComposite` ondersteunt debouncing, throttling, filtering en aangepaste evenementgegevens op geregistreerde luisteraars.

Registreer gebeurtenislisteners met de methode `addEventListener()`:

```java
// Voorbeeld: Een click-eventlistener toevoegen
addEventListener(ElementClickEvent.class, event -> {
  // Verwerk het klikgebeurtenis
});
```

:::info
`ElementComposite` accepteert alleen gebeurtenisklassen die zijn geannoteerd met `@EventName`, in tegenstelling tot `Element`, dat elke string gebeurtenisnaam accepteert.
:::

### Ingebouwde gebeurtenisklassen {#built-in-event-classes}

`ElementClickEvent` is de enige ingebouwde gebeurtenisklasse die met `ElementComposite` wordt geleverd. Het maakt muisklikgebeurtenissen op het onderliggende element zichtbaar met getypeerde accessoren voor de coördinaten (`getClientX()`, `getClientY()`), knopinformatie (`getButton()`), en modifiersleutels (`isCtrlKey()`, `isShiftKey()`, enzovoort).

Om klikverwerking bloot te stellen op de publieke API van een subclass, implementeer je de `HasElementClickListener<T>` concern interface. Deze biedt standaard `onClick()` en `addClickListener()` methoden die doorverwijzen naar de protected `addEventListener()` primitief.

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

Voor elke andere gebeurtenis die de onderliggende webcomponent verstuurt, definieer je een aangepaste gebeurtenisklasse. Zie [Aangepaste gebeurtenisklassen](#custom-event-classes).

### Gebeurtenispayloads {#event-payloads}

Gevoelige gebeurtenissen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor rauwe gebeurtenisgegevens of gebruik getypeerde methoden wanneer beschikbaar op ingebouwde gebeurtenisklassen. Zie de [Gebeurtissen-gids](../building-ui/events) voor meer informatie over efficiënte payloadafhandeling.

### Aangepaste gebeurtenisklassen {#custom-event-classes}

Definieer aangepaste gebeurtenisklassen met `@EventName` en `@EventOptions` om client-side gegevens vast te leggen in een getypeerde Java-gebeurtenis. Gebruik dit wanneer de Java-handler waarden van de browser nodig heeft.

`@EventName` bindt de Java-klasse aan het evenement dat de component in de browser verstuurt, zodat een klasse die is geannoteerd met `@EventName("change")` wordt geactiveerd telkens wanneer het onderliggende element `change` uitgeeft. `@EventOptions` beheert wat met dat evenement meereist. Elke `@EventData` binnenin koppelt een sleutel aan een JavaScript-expressie die wordt geëvalueerd op de DOM-gebeurtenis. Het resultaat is beschikbaar in de Java-gebeurtenisklasse via `getData().get(key)`.

Het productbeoordelingsformulier hieronder gebruikt dit patroon met [`wa-rating`](https://webawesome.com/docs/components/rating/). De aangepaste `ChangeEvent` bevat de beoordelingswaarde als een getypeerde `double`, en de listener gebruikt deze om de verzendknop in te schakelen:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Gebeurtenisopties {#event-options}

`ElementEventOptions` configureert de gebeurtenispayload, debounce of throttle-timing, filterexpressies en pre-executiecode. De onderstaande snippet toont de opties:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Voer JavaScript uit voordat het evenement plaatsvindt
  .setCode("component.classList.add('processing');")

  // Vuur alleen als aan de voorwaarden is voldaan
  .setFilter("component.value.length >= 2")

  // Vertraging van uitvoering totdat de gebruiker stopt met typen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Pas deze opties toe bij het registreren van een listener voor een aangepaste gebeurtenisklasse
// (zie de sectie Aangepaste gebeurtenisklassen hierboven voor hoe je er een definieert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` biedt alleen de klasse-gebaseerde vorm `addEventListener(Class, listener, options)`. Gebruik het met een gebeurtenisklasse die is geannoteerd met `@EventName`. Om direct tegen een string gebeurtenisnaam te registreren, bel je `getElement().addEventListener("input", listener, options)`.
:::

#### Prestatiecontrole {#performance-control}

**Debouncing** vertraagt de uitvoering totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300 ms na het laatste evenement
```

Beschikbare debounce-fasen:

- `LEADING`: Vuur onmiddellijk, wacht dan
- `TRAILING`: Wacht op een stille periode, vuur dan (standaard)
- `BOTH`: Vuur onmiddellijk en na stille periode

**Throttling** beperkt de frequentie van uitvoering:

```java
options.setThrottle(100); // Vuur maximaal één keer per 100 ms
```

## Interageren met slots {#interacting-with-slots}

Slots zijn placeholders binnen een webcomponent die gebruikers vullen met inhoud. De webcomponent verklaart zijn slots in zijn template met `<slot>` of `<slot name="...">`, en de wrapper biedt methoden die Java-componenten in die slots plaatsen.

Om inhoud aan slots toe te voegen, breid je `ElementCompositeContainer` uit in plaats van `ElementComposite`. De container bevat dezelfde eigenschap en attribuut mechanica plus de methoden die nodig zijn om kinderen toe te voegen. Kinderen die via `add()` worden toegevoegd, gaan in de standaard slot. Kinderen die via `getElement().add(slotName, components)` worden toegevoegd, gaan in de benoemde slot.

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

De demo hieronder toont twee prijsinformatiekaarten gebouwd met [`wa-card`](https://webawesome.com/docs/components/card/), die de `header`, standaard, en `footer` slots vanuit Java populeren:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Inhoud van slots inspecteren {#inspecting-slot-contents}

Het onderliggende `Element` (toegankelijk via `getElement()`) biedt methoden om terug te lezen wat momenteel aan slots is toegewezen:

- **`findComponentSlot()`**: zoekt in alle slots naar een specifieke component en retourneert de naam van de slot die deze bevat, of een lege string als de component zich in geen enkele slot bevindt.
- **`getComponentsInSlot()`**: retourneert de lijst van componenten die aan een gegeven slot zijn toegewezen. Optioneel kan een klassetype worden opgegeven om de resultaten te filteren.
- **`getFirstComponentInSlot()`**: retourneert de eerste component die aan een slot is toegewezen. Optioneel kan een klassetype worden opgegeven om te filteren.
