---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 8d01fe0878cf3002fe34ef2e566c2837
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse dient als een veelzijdige basis voor het beheren van samenstellende elementen in webforJ-toepassingen. Het primaire doel is om de interactie met HTML-elementen, gerepresenteerd door de `Element` klasse, te vergemakkelijken door een gestructureerde aanpak te bieden voor het omgaan met eigenschappen, attributen en gebeurtenisluisteraars. Het stelt de implementatie en hergebruik van elementen in een app mogelijk. Gebruik de `ElementComposite` klasse bij het implementeren van Web Components voor gebruik in webforJ-toepassingen.

Bij het gebruik van de `ElementComposite` klasse geeft het gebruik van de `getElement()` methode toegang tot de onderliggende `Element` component. Evenzo geeft de `getNodeName()` methode de naam van die knoop in de DOM.

:::tip
Het is mogelijk om alles met de `Element` klasse zelf te doen, zonder de `ElementComposite` klasse te gebruiken. De geboden methoden in de `ElementComposite` bieden echter gebruikers de mogelijkheid om het werk dat wordt verricht te hergebruiken.
:::

Deze gids demonstreert hoe je de [Shoelace QR code webcomponent](https://shoelace.style/components/qr-code) implementeert met behulp van de `ElementComposite` klasse.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschap- en attributenbeschrijvingen {#property-and-attribute-descriptors}

Eigenschappen en attributen in webcomponenten vertegenwoordigen de staat van de component. Ze worden vaak gebruikt om gegevens of configuraties te beheren. De `ElementComposite` klasse biedt een handige manier om met eigenschappen en attributen te werken.

Eigenschappen en attributen kunnen worden gedeclareerd en geïnitialiseerd als `PropertyDescriptor` leden van de te schrijven `ElementComposite` klasse, en vervolgens in de code worden gebruikt. Gebruik de `set()` methode om de waarde van een eigenschap in te stellen. Bijvoorbeeld, `set(PropertyDescriptor<V> property, V value)` stelt een eigenschap in op een gespecificeerde waarde.

:::info
Eigenschappen worden intern binnen de code van de component benaderd en gemanipuleerd en worden niet weerspiegeld in de DOM. Attributen daarentegen maken deel uit van de externe interface van de component en kunnen worden gebruikt om informatie van buitenaf in een component door te geven, waardoor een manier ontstaat voor externe elementen of scripts om de component te configureren.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Voorbeeld attribuut genaamd VALUE in een ElementComposite klasse
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Mijn Titel");
set(VALUE, "Mijn Waarde");
```

Naast het instellen van een eigenschap, gebruik de `get()` methode in de `ElementComposite` klasse om eigenschappen te benaderen en te lezen. De `get()` methode kan een optionele `boolean` waarde ontvangen, die standaard false is, om te bepalen of de methode naar de client moet reizen om de waarde op te halen. Dit heeft invloed op de prestaties, maar kan nodig zijn als de eigenschap puur in de client kan worden gewijzigd.

Een `Type` kan ook aan de methode worden doorgegeven, wat bepaalt waaraan de opgehaalde resultaat moet worden cast.

:::tip
Dit `Type` is niet per se nodig en voegt een extra laag van specificatie toe terwijl de gegevens worden opgehaald.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In de demo hieronder zijn eigenschappen toegevoegd voor de QR-code op basis van de documentatie voor de webcomponent. Vervolgens zijn er methoden geïmplementeerd die gebruikers in staat stellen om de verschillende geïmplementeerde eigenschappen te verkrijgen en in te stellen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Gebeurtenisregistratie {#event-registration}

Gebeurtenissen maken communicatie tussen verschillende delen van je webforJ-app mogelijk. De `ElementComposite` klasse biedt gebeurtenisafhandeling met ondersteuning voor debouncing, throttling, filtering en aangepaste gegevensverzameling voor gebeurtenissen.

Registreer gebeurtenisluisteraars met de `addEventListener()` methode:

```java
// Voorbeeld: Een klikgebeurtenisluisteraar toevoegen
addEventListener(ElementClickEvent.class, event -> {
    // Verwerk de klikgebeurtenis
});
```

:::info
De `ElementComposite` gebeurtenissen zijn anders dan `Element` gebeurtenissen, omdat dit geen enkele klasse toestaat, maar alleen gespecificeerde `Event` klassen.
:::

### Vooraf gebouwde gebeurtenisklassen {#built-in-event-classes}

webforJ biedt vooraf gebouwde gebeurtenisklassen met getypte gegevensaccess:

- **ElementClickEvent**: Muisklikgebeurtenissen met coördinaten (`getClientX()`, `getClientY()`), knopgegevens (`getButton()`) en modifiertoetsen (`isCtrlKey()`, `isShiftKey()`, enz.)
- **ElementDefinedEvent**: Vuurd wanneer een aangepast element in de DOM is gedefinieerd en klaar is voor gebruik
- **ElementEvent**: Basisgebeurtenisklasse die toegang biedt tot ruwe gebeurtenisgegevens, gebeurtenistype (`getType()`) en gebeurtenis-ID (`getId()`)

### Gegevenspayloads {#event-payloads}

Gebeurtenissen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor ruwe gebeurtenisgegevens of gebruik getypte methoden wanneer beschikbaar op vooraf gebouwde gebeurtenisklassen. Voor meer details over het efficiënt gebruiken van gegevenspayloads, zie de [Events guide](../building-ui/events).

## Aangepaste gebeurtenisklassen {#custom-event-classes}

Voor gespecialiseerde gebeurtenisafhandeling, creëer aangepaste gebeurtenisklassen met geconfigureerde payloads met behulp van de annotaties `@EventName` en `@EventOptions`.

In het onderstaande voorbeeld is een klikgebeurtenis gecreëerd en vervolgens toegevoegd aan de QR-codecomponent. Deze gebeurtenis, wanneer geactiveerd, toont de "X" coördinaat van de muis op het moment van klikken op de component, welke aan het Java-gebeurtenis als gegevens wordt doorgegeven. Een methode is vervolgens geïmplementeerd om de gebruiker toegang te geven tot deze gegevens, wat is hoe het in de app wordt weergegeven.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` stelt je in staat om het gedrag van gebeurtenissen aan te passen door te configureren welke gegevens verzameld moeten worden, wanneer gebeurtenissen worden geactiveerd, en hoe ze worden verwerkt. Hier is een uitgebreid codefragment dat alle configuratieopties toont:

```java
ElementEventOptions options = new ElementEventOptions()
    // Verzamel aangepaste gegevens van de client
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // Voer JavaScript uit voordat de gebeurtenis wordt geactiveerd
    .setCode("component.classList.add('processing');")
    
    // Vuur alleen als aan voorwaarden is voldaan
    .setFilter("component.value.length >= 2")
    
    // Vertraging van uitvoering totdat de gebruiker stopt met typen (300ms)
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Prestaties controle {#performance-control}

Controleer wanneer en hoe vaak gebeurtenissen worden geactiveerd:

**Debouncing** vertraagt de uitvoering totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300ms na laatste gebeurtenis
```

**Throttling** beperkt de uitvoeringsfrequentie:

```java
options.setThrottle(100); // Maximaal één keer per 100ms
```

Beschikbare debouncingfases:

- `LEADING`: Vuur onmiddellijk, wacht dan
- `TRAILING`: Wacht op een stille periode, vuur dan (standaard)
- `BOTH`: Vuur onmiddellijk en na een stille periode

## Opties samenvoegen {#options-merging}

Combineer gebeurtenisconfiguraties van verschillende bronnen met behulp van `mergeWith()`. Basisopties bieden gemeenschappelijke gegevens voor alle gebeurtenissen, terwijl specifieke opties gespecialiseerde configuratie toevoegen. Latere opties overschrijven conflicterende instellingen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interageren met slots {#interacting-with-slots}

Webcomponenten gebruiken vaak slots om ontwikkelaars in staat te stellen de structuur van een component van buitenaf te definiëren. Een slot is een placeholder binnen een webcomponent die kan worden gevuld met inhoud bij het gebruik van de component. In de context van de `ElementComposite` klasse bieden slots een manier om de inhoud binnen een component aan te passen. De volgende methoden worden aangeboden om ontwikkelaars in staat te stellen te interageren met en slots te manipuleren:

1. **`findComponentSlot()`**: Deze methode wordt gebruikt om een specifiek component te zoeken in alle slots in een componentensysteem. Het retourneert de naam van de slot waar het component zich bevindt. Als het component in geen enkele slot wordt gevonden, wordt een lege string geretourneerd.

2. **`getComponentsInSlot()`**: Deze methode haalt de lijst van componenten op die aan een bepaalde slot in een componentensysteem zijn toegewezen. Optioneel kan een specifieke klastype worden doorgegeven om de resultaten van de methode te filteren.

3. **`getFirstComponentInSlot()`**: Deze methode is ontworpen om de eerste component op te halen die aan de slot is toegewezen. Optioneel kan een specifieke klastype worden doorgegeven om de resultaten van deze methode te filteren.

Het is ook mogelijk om de `add()` methode te gebruiken met een `String` parameter om de gewenste slot aan te geven waarin het doorgegeven component moet worden toegevoegd.

Deze interacties stellen ontwikkelaars in staat om de kracht van webcomponenten te benutten door een schone en eenvoudige API te bieden voor het manipuleren van slots, eigenschappen en het afhandelen van gebeurtenissen binnen de `ElementComposite` klasse.
