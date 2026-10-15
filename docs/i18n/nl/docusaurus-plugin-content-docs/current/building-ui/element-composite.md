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

De `ElementComposite` klasse wikkelt een aangepast HTML-element of [webcomponent](https://developer.mozilla.org/en-US/docs/Web/API/Web_components) in. Het bindt je Java-klasse aan het onderliggende `Element` en stelt je in staat om met de eigenschappen, attributen en evenementen van dat element te werken via Java. Gebruik het bij het integreren van webcomponenten in een webforJ-app.

:::tip Wanneer `ElementComposite` te gebruiken
Gebruik `ElementComposite` wanneer je een derde partij webcomponent wikkelt die webforJ nog niet biedt. Als een ingebouwde webforJ component de use case dekt (`TextField`, `ColorField`, `Button`, enzovoort), gebruik die dan in plaats daarvan. Voor eenmalig DOM-werk dat niet opnieuw gebruikt hoeft te worden, kan de `Element` klasse rechtstreeks zonder een wrapper worden gebruikt.
:::

Deze gids demonstreert hoe je de [Web Awesome relative-time webcomponent](https://webawesome.com/docs/components/relative-time/) implementeert met de `ElementComposite` klasse.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Klasseannotaties {#class-annotations}

Drie annotaties komen vaak voor aan de top van een `ElementComposite` subclass: `@NodeName` verklaart de HTML-tag die het component wikkelt, en `@JavaScript` en `@StyleSheet` laden eventuele cliënt-side assets waarvan het onderliggende webcomponent afhankelijk is. `@NodeName` is vereist en specifiek voor `ElementComposite`. `@JavaScript` en `@StyleSheet` zijn algemene webforJ asset-annotaties en werken op elke klasse, inclusief views, componenten of de `App` klasse.

### `@NodeName` {#nodename}

De `@NodeName` annotatie verklaart de HTML-tag die het component wikkelt. webforJ gebruikt deze naam bij het maken van het onderliggende element in de DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

De tagnaam moet overeenkomen met het aangepaste element dat op de client is geregistreerd. Zonder deze annotatie kan het framework niet bepalen welk element moet worden gemaakt.

Binnen een subclass leest `getNodeName()` de gedeclareerde tag, en `getElement()` retourneert het onderliggende `Element`, zodat je DOM-niveau methoden er direct op kunt aanroepen.

### `@JavaScript` {#javascript}

De `@JavaScript` annotatie laadt het script dat het onderliggende webcomponent definieert of registreert. Plaats het op de klasse zodat het script alleen wordt geladen wanneer het component wordt gebruikt.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Meerdere `@JavaScript` annotaties zijn toegestaan, en webforJ dupliceert laadt automatisch. Hetzelfde script wordt niet twee keer geladen als verschillende componenten ervan afhankelijk zijn.

Zie [Het importeren van JavaScript-bestanden](../managing-resources/importing-assets#importing-javascript-files) voor de volledige set opties, waaronder `top`, `attributes` en laadtiming.

### `@StyleSheet` {#stylesheet}

De `@StyleSheet` annotatie laadt een CSS-bestand waarvan het component afhankelijk is. Het is handig voor derde partij componenten die een aparte stylesheet meeleveren, of voor het bundelen van component-specifieke styling naast de wrapper.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Voor lokaal gebundelde middelen gebruik je het `ws://` prefix om bestanden in `resources/static` te verwijzen:

```java
@StyleSheet("ws://components/relative-time.css")
```

Zie [Het importeren van CSS-bestanden](../managing-resources/importing-assets#importing-css-files) voor de volledige set opties.

## Eigenschappen en attribuut descriptors {#property-and-attribute-descriptors}

Eigenschappen en attributen vertegenwoordigen de staat van een webcomponent, die typisch gegevens of configuratie bevatten. `ElementComposite` exposeert beide via `PropertyDescriptor`.

Twee fabrieksmethoden op `PropertyDescriptor` produceren de descriptor zelf, één per binddoel:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` bindt aan een JavaScript-eigenschap op de DOM-knoop. `PropertyDescriptor.attribute()` bindt aan een HTML-attribuut. Het eerste argument is de naam die de webcomponent verwacht. Het tweede is een standaardwaarde, die ook het Java-type van de descriptor fixeert.

Declareer de descriptor als een privéveld op het component, en lees en schrijf er doorheen met `set(PropertyDescriptor<V> property, V value)` en `get(PropertyDescriptor<V> property)`.

:::info
Eigenschappen zijn interne staat op de DOM-knooppunt en worden niet weergegeven in de markup. Attributen zijn HTML-markup, zichtbaar voor externe scripts en CSS.
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

De bovenstaande aanroepen gebruiken `set()` direct om de primitieve vorm te tonen. In de praktijk zijn `set()` en `get()` `protected` methoden op `ElementComposite`. Ze zijn de primitieve laag die Java-waarden synchroniseert met het onderliggende element, niet de publieke API die consumenten aanroepen. Het bedoelde patroon is om de `PropertyDescriptor` privé te houden en publieke `setX()` en `getX()` methoden te schrijven die naar de primitieve methoden verwijzen.

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

Een enkele aanroep naar `set(descriptor, value)` doet drie dingen tegelijk. Het duwt de waarde naar de client via `setProperty()` voor eigenschappen, of `setAttribute()` voor attributen. Het slaat de waarde op in een lokale server-side cache, één map per componentinstantie. En het legt het runtime-type vast naast de waarde, zodat latere `get()` aanroepen weten hoe ze moeten deserialiseren.

Die lokale cache is de reden waarom `get()` standaard goedkoop kan zijn. `get(descriptor)` retourneert de gecachete waarde vanuit de server-side opslag zonder netwerkverzoek, omdat elke `set()` de cache gesynchroniseerd houdt met de client. Het optionele `boolean` tweede argument controleert of het de cache moet omzeilen en vanaf de browser moet lezen.

```java
String cached = get(heading);            // leest van de server-side cache
String live = get(heading, true);        // dwingt een lezing vanaf de browser af
```

Stel `fromClient` in op true als de waarde op de client kan veranderen zonder kennis van de server, zoals een getypte `<input>` waarde. Voor server-gestuurde eigenschappen vermijdt de standaard een round trip.

Het optionele derde argument is een `java.lang.reflect.Type` en bepaalt hoe het resultaat wordt deserialiseerd. webforJ lost het type op in deze volgorde: het expliciete `Type` argument als het is opgegeven, dan het runtime-type dat is vastgelegd door een eerdere `set()` op dezelfde descriptor, en dan `Object.class`. In de praktijk is het type dat is vastgelegd door een eerdere `set()` meestal voldoende, zodat het derde argument meestal weggelaten kan worden. Het is nodig wanneer de vastgelegde klasse informatie verliest waarop de deserializer afhankelijk is, zoals een geparameteriseerd type zoals `List<String>` waarvan de runtime-klasse gewoon `ArrayList` is.

De demo hieronder voegt eigenschappen voor relative-time toe op basis van de documentatie van de webcomponent en exposeert ze via getters en setters. Elke rij in de activiteitenfeed gebruikt verschillende `format` en `numeric` waarden om te laten zien hoe dezelfde component onder verschillende configuraties renderen.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Eigenschappen versus attributen {#properties-versus-attributes}

Hoewel `PropertyDescriptor.property()` en `PropertyDescriptor.attribute()` er uitzien alsof ze verwisselbaar zijn, hebben ze verschillende doelen binnen het onderliggende element. De verkeerde keuze leidt tot waarden die stilletjes falen om toe te passen.

Eigenschappen zijn JavaScript objecteigenschappen op de DOM-knoop. Ze kunnen elke soort bevatten, inclusief strings, booleans, cijfers, objecten en arrays, en ze vertegenwoordigen de huidige runtime-staat van het element. Het instellen van een eigenschap is een directe JavaScript-toewijzing.

Attributen zijn HTML-markup. Ze bevinden zich op de openings-tag van het element, zijn altijd strings, en vertegenwoordigen de initiële configuratie van het element. Het instellen van een attribuut triggert een DOM-mutatie en een stringconversie.

In sommige gevallen blijven de twee gesynchroniseerd. In andere divergeren ze. De `value` van een `<input>` is het klassieke voorbeeld: het `value` attribuut is de initiële waarde, terwijl de `value` eigenschap de huidige waarde is die de gebruiker heeft getypt. Het lezen van het attribuut nadat de gebruiker typt geeft de oorspronkelijke markup terug, maar het lezen van de eigenschap geeft de huidige inhoud van het veld terug.

Gebruik **eigenschappen** voor:

- **Frequent veranderende runtime-staat**: tellers, huidige selecties, getypte waarden
- **Niet-string types**: booleans, cijfers, objecten, arrays
- **Prestaties gevoelige updates**: eigenschappen omzeilen de stringconversie die vereist is voor attributen

Gebruik **attributen** voor:

- **Initiële configuratie**: instellingen die het component eenmaal leest wanneer het verbinding maakt
- **CSS-selectors**: waarden die je wilt targeten met selectors zoals `[disabled]` of `[variant="danger"]`
- **Toegankelijkheidshaken**: `aria-label`, `role`, en andere ARIA-attributen
- **String-achtige instellingen die zelden veranderen**

Wanneer je een derde partij webcomponent wrapt, controleer dan de documentatie van de component om te bevestigen welke naam naar een eigenschap en welke naar een attribuut verwijst. `PropertyDescriptor.attribute()` gebruiken voor iets dat de component alleen als een eigenschap exposeert, zal niet werken, en hetzelfde geldt andersom. De component zal de waarde stilletjes negeren.

### Typen eigenschappen {#typing-properties}

Een descriptor is geparameteriseerd door het Java-type van de waarde. De volledige verklaringsyntax is:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

De `<T>` generieke parameter verklaart het type van de waarde. Het runtime-type van de standaardwaarde fixeert ook `T`, zodat het generieke argument zelden expliciet hoeft te worden opgegeven. webforJ gebruikt `T` om waarden te serialiseren en deserialiseren wanneer het met de client communiceert.

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

Serialisatie is automatisch voor primitieve types, hun geboxte tegenhangers, en `String`. Voor complexe types wordt de waarde als JSON geconfigureerd voordat deze aan de eigenschap op de client wordt toegewezen.

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

Voor nullable verwijzingen, gebruik `Objects.requireNonNull()` zodat de fout naar voren komt bij de grens in plaats van later in de rendering pipeline.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading kan niet null zijn");
  set(heading, value);
  return this;
}
```

Vermijd validatie in `get()`. Lezingen moeten goedkoop en consistent blijven.

### Enum-stijl eigenschappen {#enum-style-properties}

De meeste webcomponenten verwachten kleine letters of kebab-case stringwaarden voor enum-achtige eigenschappen (`theme="primary"`, `expanse="xs"`). webforJ gebruikt Gson om enums te serialiseren, maar de standaardrepresentatie van Gson is de constante naam in hoofdletters. Annotateer elke constante met `@SerializedName` zodat de geserialiseerde waarde overeenkomt met wat de webcomponent verwacht.

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

Declareer de descriptor met het enum-type en gebruik de enum rechtstreeks in de setter en getter.

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

Dit is hetzelfde patroon dat de ingebouwde componenten van webforJ gebruiken voor `Theme`, `Expanse`, en soortgelijke enums. De publieke Java API blijft typeveilig, en de waarde die de webcomponent ontvangt is de string van `@SerializedName`.

### Eigenschappen testen {#testing-properties}

`PropertyDescriptorTester` valideert dat elke `PropertyDescriptor` in een component correct is aangesloten. Het scant de klasse op descriptor-velden, roept elke setter aan met de standaardwaarde en vergelijkt het resultaat met wat de getter retourneert. De tester vangt integratiefouten voordat ze een draaiende app bereiken: een setter die naar de verkeerde descriptor schrijft, een getter die een andere eigenschap leest, een standaardwaarde die niet round-trip, of een ontbrekende accessor voor een gedeclareerde descriptor.

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

Sommige descriptors volgen geen standaard getter en setter conventies, of ze zijn afhankelijk van externe staat die de test niet kan vervullen. Annotateer ze met `@PropertyExclude` om ze over te slaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Aangepaste getter- en setter-namen {#custom-getter-and-setter-names}

Als een descriptor non-standaard accessor-namen gebruikt, declareer ze met `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

De `target` parameter accepteert een klasse wanneer de accessors zich ergens anders bevinden dan het component zelf.

Voor meer details over het testoppervlak, zie [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Zorginterfaces {#concern-interfaces}

Zorginterfaces geven een `ElementComposite` subclass componentmogelijkheden zonder de implementatie zelf te schrijven. De interfaces sturen aanroepen door naar het onderliggende element. Implementeer de interfaces die het component moet ondersteunen, geparameteriseerd met het subtype zodat chaining het component retourneert:

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

De drie bovenstaande interfaces dekken alles wat `MyBadge` nodig heeft zonder enige methode-lichamen in de klasse. `HasText` exposeert `setText()` en schrijft naar de tekstinhoud van het element. `HasClassName` exposeert `addClassName()`, waarmee de badge vanuit CSS kan worden getarget. `HasStyle` exposeert `setStyle()` voor inline styling.

Voor de volledige set beschikbare interfaces en wat elk biedt, zie [Zorginterfaces](./component-fundamentals#concern-interfaces) in het artikel Begrijpen van Componenten. Als een standaard doorverwijzing niet overeenkomt met wat het verpakte element exposeert, overschrijf de methode in de subclass.

## Evenementen {#events}

### Evenementregistratie {#event-registration}

Webcomponenten versturen DOM-evenementen wanneer er iets gebeurt in de browser. Om vanuit Java te reageren, luister naar die evenementen met `addEventListener()`. De set van evenementen die een component verstuurt varieert, dus controleer de eigen documentatie van de component voor de beschikbare namen en payloads.

`ElementComposite` ondersteunt debouncing, throttling, filtering en aangepaste evenementgegevens op geregistreerde luisteraars.

Registreer gebeurtenisluisteraars met de `addEventListener()` methode:

```java
// Voorbeeld: een klikgebeurtenisluisteraar toevoegen
addEventListener(ElementClickEvent.class, event -> {
  // Verwerk de klikgebeurtenis
});
```

:::info
`ElementComposite` accepteert alleen evenementklassen die zijn gemarkeerd met `@EventName`, in tegenstelling tot `Element`, dat elke string evenementnaam accepteert.
:::

### Ingebouwde evenementenklassen {#built-in-event-classes}

`ElementClickEvent` is de enige ingebouwde evenementklasse die `ElementComposite` meegeleverd. Het maakt muisklikgebeurtenissen op het onderliggende element zichtbaar met getypeerde accessors voor coördinaten (`getClientX()`, `getClientY()`), knopinformatie (`getButton()`) en modifier-toetsen (`isCtrlKey()`, `isShiftKey()`, enzovoort).

Om klikverwerking op de publieke API van een subclass beschikbaar te stellen, implementeer de `HasElementClickListener<T>` zorginterface. Het biedt standaard `onClick()` en `addClickListener()` methoden die doorverwijzen naar de protected `addEventListener()` primitive.

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

Voor elk ander evenement dat het onderliggende webcomponent verstuurt, definieer een aangepaste evenementklasse. Zie [Aangepaste evenementklassen](#custom-event-classes).

### Evenementpayloads {#event-payloads}

Evenementen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens is mogelijk via `getData()` voor rauwe evenementgegevens of gebruik getypeerde methoden wanneer deze beschikbaar zijn op ingebouwde evenementklassen. Zie de [Evenementengids](../building-ui/events) voor meer informatie over efficiënte payloadverwerking.

### Aangepaste evenementklassen {#custom-event-classes}

Definieer aangepaste evenementklassen met `@EventName` en `@EventOptions` om client-side gegevens in een getypte Java-gebeurtenis vast te leggen. Gebruik dit wanneer de Java-handler waarden uit de browser nodig heeft.

`@EventName` bindt de Java-klasse aan het evenement dat de component in de browser verstuurt, zodat een klasse die is gemarkeerd met `@EventName("change")` wordt geactiveerd telkens wanneer het onderliggende element `change` uitzendt. `@EventOptions` bepaalt wat mee terugkomt met dat evenement. Elke `@EventData` daarin koppelt een sleutel aan een JavaScript-expressie die wordt geëvalueerd tegen het DOM-evenement. Het resultaat is beschikbaar in de Java-gebeurtenisklasse via `getData().get(key)`.

Het productbeoordelingsformulier hieronder gebruikt dit patroon met [`wa-rating`](https://webawesome.com/docs/components/rating/). De aangepaste `ChangeEvent` draagt de beoordelingswaarde als een getypte `double`, en de luisteraar gebruikt deze om de verzendknop in te schakelen:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Evenementopties {#event-options}

`ElementEventOptions` configureert de evenementpayload, debounce of throttle timing, filteruitdrukkingen, en pre-executiecode. De onderstaande snippet toont de opties:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Voer JavaScript uit voordat het evenement wordt geactiveerd
  .setCode("component.classList.add('processing');")

  // Vuur alleen af als aan de voorwaarden is voldaan
  .setFilter("component.value.length >= 2")

  // Vertraging van uitvoering totdat de gebruiker stopt met typen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Pas deze opties toe bij het registreren van een luisteraar voor een aangepaste evenementklasse
// (zie de sectie Aangepaste evenementklassen hierboven voor hoe deze te definiëren):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` exposeert alleen de klasse-gebaseerde vorm `addEventListener(Class, listener, options)`. Gebruik dit met een evenementklasse die is gemarkeerd met `@EventName`. Om rechtstreeks tegen een string evenementnaam te registreren, roep je `getElement().addEventListener("input", listener, options)` aan.
:::

#### Prestatiecontrole {#performance-control}

**Debouncing** vertraagt de uitvoering totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300 ms na het laatste evenement
```

Beschikbare debounce-fases:

- `LEADING`: Vuur onmiddellijk, wacht dan
- `TRAILING`: Wacht op een stille periode, vuurt dan af (standaard)
- `BOTH`: Vuur onmiddellijk en na een stille periode

**Throttling** beperkt de uitvoeringsfrequentie:

```java
options.setThrottle(100); // Vuur maximaal één keer per 100 ms
```

## Interactie met slots {#interacting-with-slots}

Slots zijn plaatstalen binnen een webcomponent die gebruikers vullen met inhoud. De webcomponent verklaart zijn slots in zijn sjabloon met `<slot>` of `<slot name="...">`, en de wrapper exposeert methoden die Java-componenten in die slots plaatsen.

Om inhoud aan slots toe te voegen, breid je `ElementCompositeContainer` uit in plaats van `ElementComposite`. De container draagt dezelfde eigenschap- en attributenmechanismen plus de methoden die nodig zijn om kinderen toe te voegen. Kinderen die via `add()` worden toegevoegd, gaan naar de standaardslot. Kinderen die via `getElement().add(slotName, components)` worden toegevoegd, gaan naar de benoemde slot.

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

De demo hieronder toont twee prijskaarten opgebouwd met [`wa-card`](https://webawesome.com/docs/components/card/), die de `header`, standaard en `footer` slots vanuit Java populeren:

<ComponentDemo
path='/webforj/webawesomecard'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Inhoud van slots inspecteren {#inspecting-slot-contents}

Het onderliggende `Element` (toegankelijk via `getElement()`) biedt methoden om terug te lezen wat momenteel aan slots is toegewezen:

- **`findComponentSlot()`**: zoekt alle slots naar een specifieke component en retourneert de naam van de slot die deze bevat, of een lege string als de component in geen enkele slot zit.
- **`getComponentsInSlot()`**: retourneert de lijst van componenten die aan een bepaalde slot zijn toegewezen. Optioneel kan een klastype worden opgegeven om de resultaten te filteren.
- **`getFirstComponentInSlot()`**: retourneert de eerste component die aan een slot is toegewezen. Optioneel kan een klastype worden opgegeven om te filteren.
