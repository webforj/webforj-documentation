---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: d626a230fe04d316c48e3cae7e292599
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse wikkelt een aangepast HTML-element of [webcomponent](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Het bindt jouw Java-klasse aan het onderliggende `Element` en laat je werken met de eigenschappen, attributen en gebeurtenissen van dat element via Java. Gebruik het bij de integratie van webcomponenten in een webforJ-app.

:::tip Wanneer `ElementComposite` gebruiken
Gebruik `ElementComposite` wanneer je een derde-partij webcomponent wikkelt die webforJ niet al biedt. Als een ingebouwde webforJ-component de use case dekt (`TextField`, `ColorField`, `Button`, enzovoort), gebruik die dan in plaats daarvan. Voor eenmalig DOM-werk dat niet hergebruikt hoeft te worden, kan de `Element` klasse rechtstreeks worden gebruikt zonder een wrapper.
:::

Deze gids demonstreert hoe je de [Shoelace relative-time webcomponent](https://shoelace.style/components/relative-time) implementeert met de `ElementComposite` klasse.

<ComponentDemo 
path='/webforj/relativetime' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klassenannotaties {#class-annotations}

Drie annotaties verschijnen vaak bovenaan een `ElementComposite` subclass: `@NodeName` declareert de HTML-tag die de component wikkelt, en `@JavaScript` en `@StyleSheet` laden eventuele client-side middelen waar de onderliggende webcomponent van afhankelijk is. `@NodeName` is vereist en specifiek voor `ElementComposite`. `@JavaScript` en `@StyleSheet` zijn algemene webforJ-middelenannotaties en werken op elke klasse, inclusief views, componenten of de `App`-klasse.

### `@NodeName` {#nodename}

De `@NodeName` annotatie verklaart de HTML-tag die de component wikkelt. webforJ gebruikt deze naam bij het aanmaken van het onderliggende element in de DOM.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

De tagnaam moet overeenkomen met het aangepaste element dat aan de client is geregistreerd. Zonder deze annotatie kan het framework niet bepalen welk element moet worden aangemaakt.

Binnen een subclass leest `getNodeName()` de gedeclareerde tag terug, en `getElement()` retourneert het onderliggende `Element`, zodat je DOM-methoden direct op het element kunt aanroepen.

### `@JavaScript` {#javascript}

De `@JavaScript` annotatie laadt het script dat de onderliggende webcomponent definieert of registreert. Plaats het op de klasse zodat het script alleen wordt geladen wanneer de component wordt gebruikt.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Er zijn meerdere `@JavaScript` annotaties toegestaan, en webforJ verwijdert dubbele aanroepen automatisch. Hetzelfde script zal niet twee keer worden geladen als verschillende componenten ervan afhankelijk zijn.

Zie [JavaScript-bestanden importeren](../managing-resources/importing-assets#importing-javascript-files) voor de volledige set opties, inclusief `top`, `attributes`, en laadtiming.

### `@StyleSheet` {#stylesheet}

De `@StyleSheet` annotatie laadt een CSS-bestand waar de component afhankelijk van is. Het is nuttig voor derde-partij componenten die een aparte stijlblad meegeven, of voor het bundelen van component-specifieke styling samen met de wrapper.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Voor lokaal gebundelde middelen, gebruik de `ws://` prefix om naar bestanden in `resources/static` te verwijzen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Zie [CSS-bestanden importeren](../managing-resources/importing-assets#importing-css-files) voor de volledige set opties.

## Eigenschappen en attribuut descriptors {#property-and-attribute-descriptors}

Eigenschappen en attributen vertegenwoordigen de toestand van een webcomponent, doorgaans houdend data of configuratie. `ElementComposite` maakt beide toegankelijk via `PropertyDescriptor`.

Twee fabrieksmethoden op `PropertyDescriptor` produceren de descriptor zelf, één per binddoel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindt aan een JavaScript-eigenschap op de DOM-knoop. `PropertyDescriptor.attribute()` bindt aan een HTML-attribuut. Het eerste argument is de naam die de webcomponent verwacht. Het tweede is een standaardwaarde, die ook het Java-type van de descriptor vastlegt.

Declareer de descriptor als een privé veld op de component, en lees en schrijf er doorheen met `set(PropertyDescriptor<V> property, V value)` en `get(PropertyDescriptor<V> property)`.

:::info
Eigenschappen zijn interne staat op de DOM-knoop en weerspiegelen zich niet in de markup. Attributen zijn HTML-markup, zichtbaar voor externe scripts en CSS.
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

De bovenstaande aanroepen gebruiken `set()` direct om de primitieve vorm te tonen. In de praktijk zijn `set()` en `get()` `protected` methoden op `ElementComposite`. Ze zijn de primitieve laag die Java-waarden synchroniseert met het onderliggende element, niet de openbare API die consumenten aanroepen. Het beoogde patroon is om de `PropertyDescriptor` privé te houden en openbare `setX()` en `getX()` methoden te schrijven die naar de primitieve methoden verwijzen.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // protected primitive
    return this;
  }

  public String getHeading() {
    return get(heading);     // protected primitive
  }
}
```

Een enkele aanroep naar `set(descriptor, value)` doet drie dingen tegelijk. Het duwt de waarde naar de client via `setProperty()` voor eigenschappen, of `setAttribute()` voor attributen. Het slaat de waarde op in een lokale server-side cache, één map per component instantie. En het registreert het runtime-type samen met de waarde, zodat latere `get()` aanroepen weten hoe ze moeten deserialiseren.

Die lokale cache is de reden waarom `get()` standaard goedkoop kan zijn. `get(descriptor)` retourneert de cached waarde van de server-side opslag zonder netwerkoproep, omdat elke `set()` de cache in sync houdt met de client. Het optionele `boolean` tweede argument controleert of de cache moet worden omzeild en in plaats daarvan van de browser moet worden gelezen.

```java
String cached = get(heading);            // leest van de server-side cache
String live = get(heading, true);        // dwingt een lezing van de browser af
```

Stel `fromClient` in op true wanneer de waarde op de client kan veranderen zonder kennis van de server, zoals een getypte `<input>`-waarde. Voor server-gestuurde eigenschappen vermijdt de standaard een rondreis.

Het optionele derde argument is een `java.lang.reflect.Type` en bepaalt hoe het resultaat wordt gedeserialiseerd. webforJ bepaalt het type in deze volgorde: het expliciete `Type` argument als het is opgegeven, dan het runtime-type dat eerder is geregistreerd door een eerdere `set()` op dezelfde descriptor, dan `Object.class`. In de praktijk is het type dat geregistreerd is door een eerdere `set()` vaak voldoende, zodat het derde argument meestal kan worden weggelaten. Het is nodig wanneer de geregistreerde klasse informatie verliest waar de deserializer van afhankelijk is, zoals een geparametriseerd type zoals `List<String>` waarvan de runtime-klasse alleen `ArrayList` is.

De demo hieronder voegt eigenschappen voor relatieve tijd toe op basis van de documentatie van de webcomponent en maakt ze toegankelijk via getters en setters. Elke rij in het activiteitsfeed gebruikt verschillende `format` en `numeric` waarden om te laten zien hoe dezelfde component onder verschillende configuraties wordt weergegeven.

<ComponentDemo 
path='/webforj/relativetimeproperties' 
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/resources/static/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Eigenschappen versus attributen {#properties-versus-attributes}

Hoewel `PropertyDescriptor.property()` en `PropertyDescriptor.attribute()` verwisselbaar lijken, richten ze zich op verschillende delen van het onderliggende element. Het kiezen van de verkeerde resulteert in waarden die stilletjes niet worden toegepast.

Eigenschappen zijn JavaScript objecteigenschappen op de DOM-knoop. Ze kunnen elk type bevatten, inclusief strings, booleans, nummers, objecten en arrays, en ze vertegenwoordigen de huidige runtime-toestand van het element. Het instellen van een eigenschap is een directe JavaScript-toewijzing.

Attributen zijn HTML-markup. Ze bevinden zich op de openings-tag van het element, zijn altijd strings, en vertegenwoordigen de initieel configuratie van het element. Het instellen van een attribuut veroorzaakt een DOM-mutatie en een stringconversie.

In sommige gevallen blijven de twee synchroon. Voor anderen divergeren ze. De `value` van een `<input>` is het klassieke voorbeeld: de `value` attribuut is de initiële waarde, terwijl de `value` eigenschap de huidige waarde is die de gebruiker heeft getypt. Het lezen van het attribuut nadat de gebruiker typt geeft de oorspronkelijke markup terug, maar het lezen van de eigenschap geeft de huidige inhoud van het veld terug.

Gebruik **eigenschappen** voor:

- **Vaak veranderende runtime-toestand**: tellers, huidige selecties, getypte waarden
- **Niet-string types**: booleans, nummers, objecten, arrays
- **Prestatiesgevoelige updates**: eigenschappen omzeilen de stringconversie die vereist is voor attributen

Gebruik **attributen** voor:

- **Initiële configuratie**: instellingen die de component één keer leest bij het verbinden
- **CSS-selectors**: waarden die je wilt targeten met selectors zoals `[disabled]` of `[variant="danger"]`
- **Toegankelijkheidskoppelingen**: `aria-label`, `role`, en andere ARIA-attributen
- **Stringachtige instellingen die zelden veranderen**

Bij het wikkelen van een derde-partij webcomponent, controleer de documentatie van de component om te bevestigen welke naam overeenkomt met een eigenschap en welke met een attribuut. Het gebruik van `PropertyDescriptor.attribute()` voor iets dat de component alleen als een eigenschap blootlegt werkt niet, en hetzelfde geldt omgekeerd. De component negeert stilletjes de waarde.

### Typen eigenschappen {#typing-properties}

Een descriptor is geparameteriseerd door het Java-type van zijn waarde. De volledige declaratiesyntaxis is:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

De `<T>` generieke parameter verklaart het type van de waarde. Het runtime-type van de standaardwaarde legt ook `T` vast, zodat het generieke argument zelden expliciet hoeft te worden opgegeven. webforJ gebruikt `T` om waarden te serialiseren en deserialiseren bij de communicatie met de client.

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

Serialisatie is automatisch voor primitieve waarden, hun boxed equivalenten, en `String`. Voor complexe types wordt de waarde als JSON geserialiseerd voordat deze aan de eigenschap op de client wordt toegewezen.

### Validatie van waarden {#validating-values}

Valideer waarden in de setter voordat je `set()` aanroept. De setter is het natuurlijke handhavingspunt omdat elke mutatie er doorheen vloeit.

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

De meeste webcomponenten verwachten kleine of kebab-case stringwaarden voor enum-achtige eigenschappen (`theme="primary"`, `expanse="xs"`). webforJ gebruikt Gson om enums te serialiseren, maar de standaardweergave van Gson is de constante naam in hoofdletters. Annotateer elke constante met `@SerializedName` zodat de geseialiseerde waarde overeenkomt met wat de webcomponent verwacht.

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

Dit is hetzelfde patroon dat de ingebouwde componenten van webforJ gebruiken voor `Theme`, `Expanse`, en soortgelijke enums. De publieke Java API blijft type-safe, en de waarde die de webcomponent ontvangt is de string van `@SerializedName`.

### Testen van eigenschappen {#testing-properties}

`PropertyDescriptorTester` valideert dat elke `PropertyDescriptor` in een component correct is verbonden. Het scant de klasse naar descriptorvelden, roept elke setter aan met de standaardwaarde, en vergelijkt het resultaat met wat de getter retourneert. De tester vangt integratiefouten op voordat ze een draaiende app bereiken: een setter die naar de verkeerde descriptor schrijft, een getter die een andere eigenschap leest, een standaardwaarde die geen round-trip maakt, of een ontbrekende accessor voor een gedeclareerde descriptor.

Een baseline test voor een component ziet er als volgt uit:

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

Sommige descriptors volgen geen standaard getter en setter conventies, of ze vertrouwen op externe toestanden die de test niet kan vervullen. Annotateer ze met `@PropertyExclude` om ze over te slaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Aangepaste getter- en setter-namen {#custom-getter-and-setter-names}

Als een descriptor niet-standaard accessor namen gebruikt, declareer ze met `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

De `target` parameter accepteert een klasse wanneer de accessors zich ergens anders bevinden dan de component zelf.

Voor meer details over het testoppervlak, zie [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Zorginterfaces {#concern-interfaces}

Zorginterfaces geven een `ElementComposite` subclass componentcapaciteiten zonder de implementatie zelf te schrijven. De interfaces sturen de aanroepen door naar het onderliggende element. Implementeer degene die de component moet ondersteunen, geparameteriseerd met het subtype zodat de chaining de component retourneert:

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

De drie bovenstaande interfaces dekken alles wat `MyBadge` nodig heeft zonder enige methode-lichamen in de klasse. `HasText` maakt `setText()` toegankelijk en schrijft naar de tekstinhoud van het element. `HasClassName` maakt `addClassName()` toegankelijk, wat het mogelijk maakt om de badge vanuit CSS te targeten. `HasStyle` maakt `setStyle()` toegankelijk voor inline styling.

Voor de volledige set beschikbare interfaces en wat elk biedt, zie [Zorginterfaces](./component-fundamentals#concern-interfaces) in het artikel Begrijpen van Componenten. Als een standaard doorsturing niet overeenkomt met wat het gewikkelde element biedt, overschrijf dan de methode in de subclass.

## Evenementen {#events}

### Evenementregistratie {#event-registration}

Webcomponenten dispatchen DOM-evenementen wanneer er iets gebeurt in de browser. Om te reageren vanuit Java, luister naar die evenementen met `addEventListener()`. De set van gebeurtenissen die een component dispatches varieert, dus controleer de documentatie van de component zelf voor de namen en payloads die beschikbaar zijn.

`ElementComposite` ondersteunt debouncing, throttling, filtering, en aangepaste gebeurtenisdata op geregistreerde listeners.

Registreer evenementlisteners met de methode `addEventListener()`:

```java
// Voorbeeld: Een klikgebeurtenislistener toevoegen
addEventListener(ElementClickEvent.class, event -> {
  // Behandel de klikgebeurtenis
});
```

:::info
`ElementComposite` accepteert alleen evenementklassen die zijn geannoteerd met `@EventName`, in tegenstelling tot `Element`, dat elke string evenementnaam accepteert.
:::

### Ingebouwde evenementklassen {#built-in-event-classes}

`ElementClickEvent` is de enige ingebouwde evenementklasse die `ElementComposite` meegeleverd wordt. Het legt muisklikgebeurtenissen op het onderliggende element bloot met getypte accessors voor coördinaten (`getClientX()`, `getClientY()`), knopinformatie (`getButton()`), en modifier-toetsen (`isCtrlKey()`, `isShiftKey()`, enzovoort).

Om klikverwerking op de openbare API van een subclass bloot te leggen, implementeer de `HasElementClickListener<T>` zorginterface. Dit biedt standaard `onClick()` en `addClickListener()` methoden die delegeren naar de protected `addEventListener()` primitive.

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

Voor elke andere gebeurtenis die de onderliggende webcomponent dispatches, definieer een aangepaste gebeurtenisklasse. Zie [Aangepaste gebeurtenisklassen](#custom-event-classes).

### Evenementpayloads {#event-payloads}

Evenementen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor ruwe gebeurtenisdata of gebruik getypte methoden wanneer beschikbaar op ingebouwde evenementklassen. Zie de [Evenementengids](../building-ui/events) voor meer informatie over efficiënte payloadverwerking.

### Aangepaste gebeurtenisklassen {#custom-event-classes}

Definieer aangepaste gebeurtenisklassen met `@EventName` en `@EventOptions` om client-side gegevens vast te leggen in een getypte Java-gebeurtenis. Gebruik dit wanneer de Java-handler waarden uit de browser nodig heeft.

`@EventName` bindt de Java-klasse aan de gebeurtenis die de component in de browser dispatches, zodat een klasse die is geannoteerd met `@EventName("sl-change")` wordt afgevuurd telkens wanneer het onderliggende element `sl-change` uitzendt. `@EventOptions` controleert wat er samen met die gebeurtenis terugkomt. Elke `@EventData` binnenin koppelt een sleutel met een JavaScript-expressie die wordt geëvalueerd op basis van het DOM-evenement. Het resultaat is beschikbaar in de Java-gebeurtenisklasse via `getData().get(key)`.

Het productbeoordelingsformulier hieronder gebruikt dit patroon met [`sl-rating`](https://shoelace.style/components/rating). De aangepaste `ChangeEvent` draagt de beoordelingswaarde als een getypte `double`, en de listener gebruikt het om de verzendknop in te schakelen:

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Evenementopties {#event-options}

`ElementEventOptions` configureert de gebeurtenispayload, debounce of throttle timing, filterexpressies, en pre-executiecode. De onderstaande snippet laat de opties zien:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Voer JavaScript uit voordat het evenement afgevuurd wordt
  .setCode("component.classList.add('processing');")
  
  // Vuur alleen af als aan voorwaarden wordt voldaan
  .setFilter("component.value.length >= 2")
  
  // Vertraging van uitvoering totdat de gebruiker stopt met typen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Pas deze opties toe bij het registreren van een listener voor een aangepaste gebeurtenisklasse
// (zie de sectie Aangepaste gebeurtenisklassen hierboven voor hoe je er een definieert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` blootlegt alleen de klasse-gebaseerde vorm `addEventListener(Class, listener, options)`. Gebruik het met een gebeurtenisklasse die is geannoteerd met `@EventName`. Om direct tegen een string gebeurtenisnaam te registreren, bel `getElement().addEventListener("input", listener, options)`.
:::

#### Prestatiecontrole {#performance-control}

**Debouncing** vertraagt de uitvoering totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300ms na het laatste evenement
```

Beschikbare debounce-fases:

- `LEADING`: Vuur onmiddellijk, wacht dan
- `TRAILING`: Wacht op een stille periode, vuur dan af (standaard)
- `BOTH`: Vuur onmiddellijk en na stille periode

**Throttling** beperkt de frequentie van uitvoering:

```java
options.setThrottle(100); // Vuur hooguit één keer per 100ms
```

## Interactie met slots {#interacting-with-slots}

Slots zijn plaatsaanduiders binnen een webcomponent die gebruikers vullen met inhoud. De webcomponent verklaart zijn slots in zijn sjabloon met `<slot>` of `<slot name="...">`, en de wrapper maakt methoden toegankelijk die Java-componenten in die slots plaatsen.

Om inhoud aan slots toe te voegen, breid `ElementCompositeContainer` uit in plaats van `ElementComposite`. De container heeft dezelfde eigenschap- en attribuutmechanismen plus de methoden die nodig zijn om kinderen toe te voegen. Kinderen die via `add()` worden toegevoegd, komen in de standaardslot. Kinderen die via `getElement().add(slotName, components)` worden toegevoegd, komen in de genoemde slot.

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

De demo hieronder toont twee prijskaarten gebouwd met [`sl-card`](https://shoelace.style/components/card), die de `header`, standaard en `footer` slots vanuit Java vullen:

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspectie van slotinhoud {#inspecting-slot-contents}

Het onderliggende `Element` (toegankelijk via `getElement()`) biedt methoden voor het lezen van wat op dit moment aan slots is toegewezen:

- **`findComponentSlot()`**: doorzoekt alle slots naar een specifieke component en retourneert de naam van de slot die deze bevat, of een lege string als de component zich in geen enkele slot bevindt.
- **`getComponentsInSlot()`**: retourneert de lijst van componenten die aan een bepaalde slot zijn toegewezen. Optioneel neemt het een klassetype om de resultaten te filteren.
- **`getFirstComponentInSlot()`**: retourneert de eerste component die aan een slot is toegewezen. Optioneel neemt het een klassetype om te filteren.
