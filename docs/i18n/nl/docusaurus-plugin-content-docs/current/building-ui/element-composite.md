---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2f1ddb4b3375c89dc29d9dbc9cee7303
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse wikkelt een op maat gemaakt HTML-element of [webcomponent](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Het bindt je Java-klasse aan het onderliggende `Element` en stelt je in staat om met de eigenschappen, attributen en evenementen van dat element te werken via Java. Gebruik het bij het integreren van webcomponenten in een webforJ-app.

:::tip Wanneer `ElementComposite` te gebruiken
Gebruik `ElementComposite` bij het wikkelen van een webcomponent van derden die webforJ nog niet biedt. Als een ingebouwde webforJ-component de gebruiksbehoefte dekt (`TextField`, `ColorField`, `Button`, enzovoort), gebruik dan in plaats daarvan die. Voor eenmalig DOM-werk dat niet hergebruikt hoeft te worden, kan de `Element`-klasse rechtstreeks zonder een wrapper worden gebruikt.
:::

Deze gids demonstreert hoe je de [Web Awesome relatieve-tijd webcomponent](https://webawesome.com/docs/components/relative-time/) implementeert met behulp van de `ElementComposite` klasse.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klasse annotaties {#class-annotations}

Drie annotaties verschijnen vaak aan de bovenkant van een `ElementComposite` subclass: `@NodeName` declareert de HTML-tag die de component wikkelt, en `@JavaScript` en `@StyleSheet` laden alle client-side assets waar de onderliggende webcomponent van afhankelijk is. `@NodeName` is verplicht en specifiek voor `ElementComposite`. `@JavaScript` en `@StyleSheet` zijn algemene webforJ asset-annotaties en werken op elke klasse, inclusief views, componenten of de `App`-klasse.

### `@NodeName` {#nodename}

De `@NodeName` annotatie declareert de HTML-tag die de component wikkelt. WebforJ gebruikt deze naam bij het maken van het onderliggende element in de DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

De tagnaam moet overeenkomen met het aangepaste element dat op de client is geregistreerd. Zonder deze annotatie kan het framework niet bepalen welk element gemaakt moet worden.

Binnen een subclass leest `getNodeName()` de verklaarde tag terug, en `getElement()` retourneert het onderliggende `Element`, zodat je DOM-niveau methoden er direct op kunt aanroepen.

### `@JavaScript` {#javascript}

De `@JavaScript` annotatie laadt het script dat de onderliggende webcomponent definieert of registreert. Plaats het op de klasse zodat het script alleen wordt geladen wanneer de component wordt gebruikt.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Meerdere `@JavaScript` annotaties zijn toegestaan, en webforJ deduplicates automatische lading. Hetzelfde script wordt niet twee keer geladen als verschillende componenten ervan afhankelijk zijn.

Zie [Importing JavaScript files](../managing-resources/importing-assets#importing-javascript-files) voor de volledige set opties, inclusief `top`, `attributes`, en laadtiming.

### `@StyleSheet` {#stylesheet}

De `@StyleSheet` annotatie laadt een CSS-bestand waar de component van afhankelijk is. Het is handig voor componenten van derden die een afzonderlijk stylesheet leveren, of voor het bundelen van component-specifieke styling samen met de wrapper.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Voor lokaal gebundelde assets gebruik je het `ws://` prefix om bestanden in `resources/static` te verwijzen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Zie [Importing CSS files](../managing-resources/importing-assets#importing-css-files) voor de volledige set opties.

## Eigenschap- en attributendeskundigen {#property-and-attribute-descriptors}

Eigenschappen en attributen vertegenwoordigen de staat van een webcomponent, doorgaans houdend gegevens of configuratie. `ElementComposite` stelt beide bloot via `PropertyDescriptor`.

Twee fabrieksmethoden op `PropertyDescriptor` produceren de descriptor zelf, een per binddoel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindt aan een JavaScript-eigenschap op de DOM-knoop. `PropertyDescriptor.attribute()` bindt aan een HTML-attribuut. Het eerste argument is de naam die de webcomponent verwacht. Het tweede is een standaardwaarde, die ook het Java-type van de descriptor fixeert.

Declareer de descriptor als een privé veld op de component, en lees en schrijf erdoorheen met `set(PropertyDescriptor<V> property, V value)` en `get(PropertyDescriptor<V> property)`.

:::info
Eigenschappen zijn interne staat op de DOM-knoop en reflecteren zich niet in de markup. Attributen zijn HTML-markup, zichtbaar voor externe scripts en CSS.
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

De bovenstaande aanroepen gebruiken `set()` direct om de primitieve vorm te tonen. In de praktijk zijn `set()` en `get()` `protected` methoden op `ElementComposite`. Ze zijn de primitieve laag die Java-waarden synchroniseert met het onderliggende element, niet de publieke API die consumenten aanroepen. Het beoogde patroon is om de `PropertyDescriptor` privé te houden en publieke `setX()` en `getX()` methoden te schrijven die naar de primitieve methoden doorverwijzen.

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

Een enkele aanroep van `set(descriptor, value)` doet drie dingen tegelijk. Het duwt de waarde naar de client via `setProperty()` voor eigenschappen of `setAttribute()` voor attributen. Het slaat de waarde op in een lokale server-side cache, één map per componentinstantie. En het registreert het runtime-type naast de waarde, zodat latere `get()`-aanroepen weten hoe het moet worden gedeserialiseerd.

Die lokale cache is de reden waarom `get()` standaard goedkoop kan zijn. `get(descriptor)` retourneert de gecachete waarde uit de server-side opslag zonder netwerkaanroepen, omdat elke `set()` de cache in sync houdt met de client. Het optionele `boolean` tweede argument controleert of de cache moet worden omzeild en in plaats daarvan van de browser moet worden gelezen.

```java
String cached = get(heading);            // leest uit de server-side cache
String live = get(heading, true);        // dwingt een leesopdracht vanuit de browser af
```

Stel `fromClient` in op true wanneer de waarde op de client kan wijzigen zonder kennis van de server, zoals een getypte `<input>` waarde. Voor servergestuurde eigenschappen vermijdt de standaard een ronde reis.

Het optionele derde argument is een `java.lang.reflect.Type` en controleert hoe het resultaat wordt gedeserialiseerd. WebforJ bepaalt het type in deze volgorde: het expliciete `Type` argument als dat is doorgegeven, dan het runtime-type dat is geregistreerd door een eerdere `set()` op dezelfde descriptor, dan `Object.class`. In de praktijk is het type dat is geregistreerd bij een eerdere `set()` meestal voldoende, zodat het derde argument meestal kan worden weggelaten. Het is nodig wanneer de geregistreerde klasse informatie verliest waarop de deserializer vertrouwt, zoals een geparametriseerd type zoals `List<String>` waarvan de runtime-klasse gewoon `ArrayList` is.

De demo hieronder voegt eigenschappen voor relatieve tijd toe op basis van de documentatie van de webcomponent en stelt ze bloot via getters en setters. Elke rij in de activiteitsoverzicht gebruikt verschillende `format` en `numeric` waarden om te laten zien hoe dezelfde component varieert onder verschillende configuraties.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Eigenschappen versus attributen {#properties-versus-attributes}

Hoewel `PropertyDescriptor.property()` en `PropertyDescriptor.attribute()` verwisselbaar lijken, richten ze zich op verschillende delen van het onderliggende element. Het kiezen van de verkeerde resulteert in waarden die stilzwijgend niet worden toegepast.

Eigenschappen zijn JavaScript-objecteigenschappen op de DOM-knoop. Ze kunnen elk type vasthouden, inclusief strings, booleans, getallen, objecten en arrays, en ze vertegenwoordigen de huidige runtime-staat van het element. Het instellen van een eigenschap is een directe JavaScript-toewijzing.

Attributen zijn HTML-markup. Ze bevinden zich op de openingstag van het element, zijn altijd strings, en vertegenwoordigen de initiële configuratie van het element. Het instellen van een attribuut triggert een DOM-mutatie en een stringconversie.

In sommige gevallen blijven beide in sync. In andere wijken ze af. De `value` van een `<input>` is het klassieke voorbeeld: de `value` attribuut is de initiële waarde, terwijl de `value` eigenschap de huidige waarde is die de gebruiker heeft getypt. Het lezen van het attribuut nadat de gebruiker typt geeft de oorspronkelijke markup terug, maar het lezen van de eigenschap geeft de huidige inhoud van het veld terug.

Gebruik **eigenschappen** voor:

- **Frequent veranderende runtime-staat**: tellers, huidige selecties, getypte waarden
- **Niet-string types**: booleans, getallen, objecten, arrays
- **Prestatiegevoelige updates**: eigenschappen omzeilen de stringconversie die vereist is voor attributen

Gebruik **attributen** voor:

- **Initiële configuratie**: instellingen die de component één keer leest bij het verbinden
- **CSS-selectors**: waarden die je wilt targeten met selectors zoals `[disabled]` of `[variant="danger"]`
- **Toegankelijkheidshaken**: `aria-label`, `role`, en andere ARIA-attributen
- **String-achtige instellingen die zelden veranderen**

Bij het wikkelen van een webcomponent van derden, controleer de documentatie van de component om te bevestigen welke naam naar een eigenschap en welke naar een attribuut verwijst. Het gebruik van `PropertyDescriptor.attribute()` voor iets dat de component alleen als een eigenschap exposeert zal niet werken, en hetzelfde geldt omgekeerd. De component negeert de waarde stilzwijgend.

### Typen eigenschappen {#typing-properties}

Een descriptor is geparametriseerd door het Java-type van de waarde. De volledige declaratiesyntax is:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

De `<T>` generieke parameter verklaart het type van de waarde. Het runtime-type van de standaardwaarde fixeert ook `T`, zodat de generieke argument meestal zelden expliciet hoeft te worden opgegeven. WebforJ gebruikt `T` om waarden te serialiseren en deserialiseren bij communicatie met de client.

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

Serialisatie is automatisch voor primitieve types, hun boxed equivalenten, en `String`. Voor complexe types wordt de waarde als JSON geserialiseerd voordat deze aan de eigenschap op de client wordt toegewezen.

### Waarden valideren {#validating-values}

Valideer waarden in de setter voordat je `set()` aanroept. De setter is het natuurlijke handhaving punt omdat elke wijziging erdoorheen stroomt.

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

Voor nullable referenties, gebruik `Objects.requireNonNull()` zodat de fout aan de grens naar voren komt in plaats van later in de renderingspipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading kan niet null zijn");
  set(heading, value);
  return this;
}
```

Vermijd valideren in `get()`. Lezen moet goedkoop en consistent blijven.

### Enum-achtige eigenschappen {#enum-style-properties}

De meeste webcomponenten verwachten kleine of kebab-case stringwaarden voor enum-achtige eigenschappen (`theme="primary"`, `expanse="xs"`). WebforJ gebruikt Gson om enums te serialiseren, maar de standaardrepresentatie van Gson is de constante naam in hoofdletters. Annoteer elke constante met `@SerializedName` zodat de geconfigureerde waarde overeenkomt met wat de webcomponent verwacht.

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

`PropertyDescriptorTester` valideert dat elke `PropertyDescriptor` in een component correct is aangesloten. Het scant de klasse naar descriptorvelden, roept elke setter aan met de standaardwaarde, en vergelijkt het resultaat met wat de getter retourneert. De tester vangt integratiefouten op voordat ze een werkende app bereiken: een setter die naar de verkeerde descriptor schrijft, een getter die een andere eigenschap leest, een standaardwaarde die niet rondreist, of een ontbrekende toegang voor een verklaarde descriptor.

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

Sommige descriptors volgen niet de standaard getter- en setterconventies, of ze zijn afhankelijk van externe staat die de test niet kan vervullen. Annoteer ze met `@PropertyExclude` om ze over te slaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Aangepaste getter- en setter-namen {#custom-getter-and-setter-names}

Als een descriptor niet-standaard accessor-namen gebruikt, declareer ze met `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Het `target`-parameter accepteert een klasse wanneer de accessors zich ergens anders bevinden dan de component zelf.

Voor meer details over het testvlak, zie [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Concern interfaces {#concern-interfaces}

Concern interfaces geven een `ElementComposite` subclass component mogelijkheden zonder zelf de implementatie te schrijven. De interfaces versturen oproepen naar het onderliggende element. Implementeer degene die de component moet ondersteunen, geparameteriseerd met het subtype zodat chaining de component retourneert:

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

De drie interfaces hierboven dekken alles wat `MyBadge` nodig heeft zonder enige methode-inhoud in de klasse. `HasText` stelt `setText()` bloot en schrijft naar de tekstinhoud van het element. `HasClassName` stelt `addClassName()` bloot, wat de badge in staat stelt om van CSS te worden getarget. `HasStyle` stelt `setStyle()` bloot voor inline styling.

Voor de volledige set beschikbare interfaces en wat elke biedt, zie [Concern interfaces](./component-fundamentals#concern-interfaces) in het artikel Understanding Components. Als een standaard doorsturen niet overeenkomt met wat het gewikkelde element exposeert, overschrijf de methode in de subclass.

## Evenementen {#events}

### Evenementregistratie {#event-registration}

Webcomponenten dispatchen DOM-evenementen wanneer er iets in de browser gebeurt. Om vanaf Java te reageren, luister naar die evenementen met `addEventListener()`. De set van evenementen die een component dispatches varieert, dus controleer de eigen documentatie van de component voor de namen en payloads die beschikbaar zijn.

`ElementComposite` ondersteunt debouncing, throttling, filtering, en aangepaste evenementgegevens op geregistreerde listeners.

Registreer gebeurtenislijsten met de `addEventListener()` methode:

```java
// Voorbeeld: Een klikgebeurtenislijsten toevoegen
addEventListener(ElementClickEvent.class, event -> {
  // Behandel de klikgebeurtenis
});
```

:::info
`ElementComposite` accepteert alleen gebeurtenisklassen geannoteerd met `@EventName`, in tegenstelling tot `Element`, dat elke string gebeurtenisnaam accepteert.
:::

### Ingebouwde gebeurtenisklassen {#built-in-event-classes}

`ElementClickEvent` is de enige ingebouwde gebeurtenisklasse die `ElementComposite` levert. Het brengt muisklikgebeurtenissen op het onderliggende element naar voren met getypeerde accessors voor coördinaten (`getClientX()`, `getClientY()`), knoppeninformatie (`getButton()`), en modifier-toetsen (`isCtrlKey()`, `isShiftKey()`, enzovoort).

Om klikken af te handelen via de publieke API van een subclass, implementeer de `HasElementClickListener<T>` concern interface. Het biedt standaard `onClick()` en `addClickListener()` methoden die doorverwijzen naar de protected `addEventListener()` primitive.

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

Voor elke andere gebeurtenis die de onderliggende webcomponent verstuurt, definieer een aangepaste gebeurtenisklasse. Zie [Custom event classes](#custom-event-classes).

### Evenement payloads {#event-payloads}

Evenementen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor ruwe evenementgegevens of gebruik getypeerde methoden wanneer beschikbaar op ingebouwde gebeurtenisklassen. Zie de [Events guide](../building-ui/events) voor meer informatie over efficiënte payloadverwerking.

### Aangepaste gebeurtenisklassen {#custom-event-classes}

Definieer aangepaste gebeurtenisklassen met `@EventName` en `@EventOptions` om client-side gegevens in een getypeerd Java-evenement vast te leggen. Gebruik dit wanneer de Java-handler waarden uit de browser nodig heeft.

`@EventName` bindt de Java-klasse aan het evenement dat de component in de browser verstuurt, zodat een klas die is geannoteerd met `@EventName("change")` wordt geactiveerd telkens wanneer het onderliggende element `change` uitzendt. `@EventOptions` controleert wat er met dat evenement meereist. Elke `@EventData` binnenin paren een sleutel met een JavaScript-expressie die wordt geëvalueerd tegen het DOM-evenement. Het resultaat is beschikbaar in de Java-evenementklasse via `getData().get(key)`.

Het productbeoordelingsformulier hieronder gebruikt dit patroon met [`wa-rating`](https://webawesome.com/docs/components/rating/). De aangepaste `ChangeEvent` draagt de beoordelingswaarde als een getypeerde `double`, en de listener gebruikt deze om de verzendknop in te schakelen:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Evenementopties {#event-options}

`ElementEventOptions` configureert de evenementpayload, debounce- of throttle-timing, filterexpressies, en pre-executiecode. De onderstaande snippet toont de opties:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Voer JavaScript uit voordat het evenement wordt actiever
  .setCode("component.classList.add('processing');")

  // Vuur alleen af als aan de voorwaarden is voldaan
  .setFilter("component.value.length >= 2")

  // Vertraging van uitvoering totdat de gebruiker stopt met typen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Pas deze opties toe bij het registreren van een listener voor een aangepaste gebeurtenisklasse
// (zie de Custom event classes sectie hierboven voor hoe je er een definieert):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` exposeert alleen de op klasse gebaseerde vorm `addEventListener(Class, listener, options)`. Gebruik het met een gebeurtenisklasse geannoteerd met `@EventName`. Om direct tegen een string gebeurtenisnaam te registreren, roep je `getElement().addEventListener("input", listener, options)` aan.
:::

#### Prestatiecontrole {#performance-control}

**Debouncing** vertraging van uitvoering totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300ms na laatste gebeurtenis
```

Beschikbare debounce-fasen:

- `LEADING`: Vuur direct af, wacht daarna
- `TRAILING`: Wacht op stille periode, vuur dan af (standaard)
- `BOTH`: Vuur direct af en na stille periode

**Throttling** beperkt de uitvoering frequentie:

```java
options.setThrottle(100); // Vuur ten hoogste eenmaal per 100ms
```

## Interacties met slots {#interacting-with-slots}

Slots zijn houders binnen een webcomponent die gebruikers vullen met inhoud. De webcomponent verklaart zijn slots in zijn sjabloon met `<slot>` of `<slot name="...">`, en de wrapper exposeert methoden die Java-componenten in die slots plaatsen.

Om inhoud aan slots toe te voegen, breid `ElementCompositeContainer` uit in plaats van `ElementComposite`. De container draagt dezelfde eigenschap- en attribuutmachinerie plus de methoden die nodig zijn om kinderen toe te voegen. Kinderen die via `add()` zijn toegevoegd, gaan in de default slot. Kinderen die via `getElement().add(slotName, components)` zijn toegevoegd, gaan in de genoemde slot.

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

De demo hieronder laat twee prijskaartjes zien die zijn gebouwd met [`wa-card`](https://webawesome.com/docs/components/card/), en populeren de `header`, default, en `footer` slots vanuit Java:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inhoud van slots inspecteren {#inspecting-slot-contents}

Het onderliggende `Element` (toegankelijk via `getElement()`) biedt methoden om terug te lezen wat momenteel aan slots is toegewezen:

- **`findComponentSlot()`**: zoekt alle slots naar een specifieke component en retourneert de naam van de slot die deze bevat, of een lege string als de component in geen enkele slot zit.
- **`getComponentsInSlot()`**: retourneert de lijst van componenten die aan een gegeven slot zijn toegewezen. Optioneel kan het een klasse type nemen om de resultaten te filteren.
- **`getFirstComponentInSlot()`**: retourneert de eerste component die aan een slot is toegewezen. Optioneel kan het een klasse type nemen om te filteren.
