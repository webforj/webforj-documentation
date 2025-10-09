---
sidebar_position: 1
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 6e201040e3dfd4be12037094eb9e978e
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse dient als een veelzijdige basis voor het beheren van samengestelde elementen in webforJ-toepassingen. Het primaire doel is om de interactie met HTML-elementen, vertegenwoordigd door de `Element` klasse, te vergemakkelijken door een gestructureerde aanpak te bieden voor het omgaan met eigenschappen, attributen en gebeurtenisluisteraars. Het stelt gebruikers in staat om elementen in een app te implementeren en te hergebruiken. Gebruik de `ElementComposite` klasse bij het implementeren van Web Components voor gebruik in webforJ-toepassingen.

Bij het gebruik van de `ElementComposite` klasse hebt u met de `getElement()` methode toegang tot het onderliggende `Element` component. Evenzo geeft de `getNodeName()` methode de naam van die node in de DOM.

:::tip
Het is mogelijk om alles te doen met de `Element` klasse zelf, zonder de `ElementComposite` klasse te gebruiken. De aangeboden methoden in de `ElementComposite` geven gebruikers echter een manier om het werk dat wordt gedaan te hergebruiken.
:::

Deze gids demonstreert hoe u de [Shoelace QR-code webcomponent](https://shoelace.style/components/qr-code) kunt implementeren met behulp van de `ElementComposite` klasse.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschappen en attributen beschrijvingen {#property-and-attribute-descriptors}

Eigenschappen en attributen in webcomponenten vertegenwoordigen de staat van het component. Ze worden vaak gebruikt om gegevens of configuratie te beheren. De `ElementComposite` klasse biedt een handige manier om met eigenschappen en attributen te werken.

Eigenschappen en attributen kunnen worden verklaard en geïnitialiseerd als `PropertyDescriptor` leden van de `ElementComposite` klasse die wordt geschreven, en vervolgens in de code worden gebruikt. Om eigenschappen en attributen te definiëren, gebruikt u de `set()` methode om de waarde van een eigenschap in te stellen. Bijvoorbeeld, `set(PropertyDescriptor<V> property, V value)` stelt een eigenschap in op een opgegeven waarde.

:::info
Eigenschappen worden intern binnen de code van het component benaderd en gemanipuleerd en worden niet weerspiegeld in de DOM. Attributen zijn daarentegen onderdeel van de externe interface van het component en kunnen worden gebruikt om informatie van buitenaf naar een component door te geven, waardoor externe elementen of scripts de configuratie van het component kunnen aanpassen.
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

Naast het instellen van een eigenschap, gebruikt u de `get()` methode in de `ElementComposite` klasse om toegang te krijgen tot en eigenschappen te lezen. De `get()` methode kan een optionele `boolean` waarde krijgen, die standaard op false staat, om te bepalen of de methode een reis naar de client moet maken om de waarde op te halen. Dit heeft invloed op de prestaties, maar kan noodzakelijk zijn als de eigenschap puur aan de client kan worden gewijzigd.

Een `Type` kan ook aan de methode worden doorgegeven, wat bepaalt naar wat het opgehaalde resultaat moet worden omgezet.

:::tip
Dit `Type` is niet strikt noodzakelijk en voegt een extra laag specificatie toe wanneer de gegevens worden opgehaald.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In de demo hieronder zijn eigenschappen toegevoegd voor de QR-code op basis van de documentatie voor de webcomponent. Vervolgens zijn er methoden geïmplementeerd waarmee gebruikers de verschillende geïmplementeerde eigenschappen kunnen ophalen en instellen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Gebeurtenisregistratie {#event-registration}

Gebeurtenissen maken communicatie mogelijk tussen verschillende delen van uw webforJ-app. De `ElementComposite` klasse biedt gebeurtenisafhandeling met ondersteuning voor debouncing, throttling, filtering en verzameling van aangepaste gebeurtenisgegevens.

Registreer gebeurtenisluisteraars met behulp van de `addEventListener()` methode:

```java
// Voorbeeld: Een klikgebeurtenis luisteraar toevoegen
addEventListener(ElementClickEvent.class, event -> {
    // Verwerk de klikgebeurtenis
});
```

:::info
De `ElementComposite` gebeurtenissen zijn anders dan `Element` gebeurtenissen, omdat dit geen enkele klasse toestaat, maar alleen gespecificeerde `Event` klassen.
:::

### Vooraf ingebouwde gebeurtenisklassen {#built-in-event-classes}

webforJ biedt vooraf gebouwde gebeurtenisklassen met getypeerde gegevens toegang:

- **ElementClickEvent**: Muisklikgebeurtenissen met coördinaten (`getClientX()`, `getClientY()`), knop informatie (`getButton()`), en modificatietoetsen (`isCtrlKey()`, `isShiftKey()`, enz.)
- **ElementDefinedEvent**: Vuurd wanneer een aangepast element in de DOM is gedefinieerd en klaar is voor gebruik
- **ElementEvent**: Basisgebeurtenisklasse die toegang biedt tot ruwe gebeurtenisgegevens, gebeurtenistype (`getType()`) en gebeurtenis-ID (`getId()`)

### Gebeurtenispayloads {#event-payloads}

Gebeurtenissen dragen gegevens van de client naar uw Java-code. Toegang tot deze gegevens via `getData()` voor ruwe gebeurtenisgegevens of gebruik getypeerde methoden wanneer beschikbaar op ingebouwde gebeurtenisklassen. Voor meer details over hoe u gebeurtenispayloads efficiënt kunt gebruiken, zie de [Gids voor gebeurtenissen](../building-ui/events).

## Aangepaste gebeurtenisklassen {#custom-event-classes}

Voor gespecialiseerde gebeurtenisafhandeling, maak aangepaste gebeurtenisklassen met geconfigureerde payloads met behulp van de annotaties `@EventName` en `@EventOptions`.

In het onderstaande voorbeeld is een klikgebeurtenis gemaakt en vervolgens toegevoegd aan de QR-codecomponent. Deze gebeurtenis, wanneer deze wordt geactiveerd, toont de "X"-coördinaat van de muis op het moment van klikken op de component, die als gegevens naar de Java-gebeurtenis wordt gestuurd. Vervolgens is er een methode geïmplementeerd om de gebruiker toegang te geven tot deze gegevens, wat laat zien hoe deze in de app wordt weergegeven.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` stelt u in staat om het gedrag van gebeurtenissen aan te passen door te configureren welke gegevens moeten worden verzameld, wanneer gebeurtenissen worden geactiveerd en hoe ze worden verwerkt. Hier is een uitgebreide codevoorbeeld dat alle configuratie-opties laat zien:

```java
ElementEventOptions options = new ElementEventOptions()
    // Verzamel aangepaste gegevens van de client
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // Voer JavaScript uit voordat de gebeurtenis wordt geactiveerd
    .setCode("component.classList.add('processing');")
    
    // Alleen activeren als aan de voorwaarden wordt voldaan
    .setFilter("component.value.length >= 2")
    
    // Uitvoering vertragen totdat de gebruiker stopt met typen (300 ms)
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Prestatiecontrole {#performance-control}

Beheer wanneer en hoe vaak gebeurtenissen worden geactiveerd:

**Debouncing** vertraagt de uitvoering totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300 ms na de laatste gebeurtenis
```

**Throttling** beperkt de uitvoering frequentie:

```java
options.setThrottle(100); // Maximaal eens per 100 ms
```

Beschikbare debouncingsfases:

- `LEADING`: Activeren onmiddellijk, dan wachten
- `TRAILING`: Wachten op een stille periode, dan activeren (standaard)
- `BOTH`: Activeren onmiddellijk en na stille periode

## Opties samenvoegen {#options-merging}

Combineer gebeurtenisconfiguraties van verschillende bronnen met `mergeWith()`. Basisopties bieden gemeenschappelijke gegevens voor alle gebeurtenissen, terwijl specifieke opties gespecialiseerde configuratie toevoegen. Latere opties overschrijven conflicterende instellingen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interactie met slots {#interacting-with-slots}

Webcomponenten gebruiken vaak slots om ontwikkelaars in staat te stellen de structuur van een component van buitenaf te definiëren. Een slot is een tijdelijke aanduiding binnen een webcomponent die kan worden gevuld met inhoud wanneer de component wordt gebruikt. In de context van de `ElementComposite` klasse bieden slots een manier om de inhoud binnen een component aan te passen. De volgende methoden worden aangeboden om ontwikkelaars in staat te stellen met slots te interageren en deze te manipuleren:

1. **`findComponentSlot()`**: Deze methode wordt gebruikt om naar een specifiek component te zoeken in alle slots in een componentensysteem. Het retourneert de naam van de slot waar het component zich bevindt. Als het component niet in een slot wordt gevonden, wordt een lege string geretourneerd.

2. **`getComponentsInSlot()`**: Deze methode haalt de lijst op van componenten die aan een bepaalde slot in een componentensysteem zijn toegewezen. Optioneel kan een specifieke klasse worden doorgegeven om de resultaten van de methode te filteren.

3. **`getFirstComponentInSlot()`**: Deze methode is ontworpen om het eerste component op te halen dat aan de slot is toegewezen. Optioneel kan een specifieke klasse worden doorgegeven om de resultaten van deze methode te filteren.

Het is ook mogelijk om de `add()` methode te gebruiken met een `String` parameter om de gewenste slot aan te geven waarin het doorgegeven component moet worden toegevoegd.

Deze interacties stellen ontwikkelaars in staat om de kracht van webcomponenten te benutten door een schone en eenvoudige API voor het manipuleren van slots, eigenschappen en het afhandelen van gebeurtenissen binnen de `ElementComposite` klasse te bieden.
