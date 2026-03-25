---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 1fc82a7db864ec48118fb611a94a57fc
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse dient als een veelzijdige basis voor het beheren van samengestelde elementen in webforJ-toepassingen. Het primaire doel is om de interactie met HTML-elementen, vertegenwoordigd door de `Element` klasse, te vergemakkelijken door een gestructureerde aanpak te bieden voor het behandelen van eigenschappen, attributen en gebeurtenislijsten. Het staat implementatie en hergebruik van elementen in een app toe. Gebruik de `ElementComposite` klasse bij het implementeren van Web Components voor gebruik in webforJ-toepassingen.

Bij het gebruik van de `ElementComposite` klasse geeft de `getElement()` methode toegang tot de onderliggende `Element` component. Evenzo geeft de `getNodeName()` methode de naam van die knoop in de DOM.

:::tip
Het is mogelijk om alles met de `Element` klasse zelf te doen, zonder de `ElementComposite` klasse te gebruiken. De aangeboden methoden in de `ElementComposite` geven gebruikers echter een manier om het werk dat wordt gedaan te hergebruiken.
:::

Deze handleiding demonstreert hoe je de [Shoelace QR-code webcomponent](https://shoelace.style/components/qr-code) kunt implementeren met behulp van de `ElementComposite` klasse.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschap- en attributenbeschrijvingen {#property-and-attribute-descriptors}

Eigenschappen en attributen in webcomponenten vertegenwoordigen de staat van de component. Ze worden vaak gebruikt om gegevens of configuratie te beheren. De `ElementComposite` klasse biedt een handige manier om met eigenschappen en attributen te werken.

Eigenschappen en attributen kunnen worden verklaard en geïnitialiseerd als `PropertyDescriptor` leden van de `ElementComposite` klasse die wordt geschreven, en vervolgens in de code worden gebruikt. Om eigenschappen en attributen te definiëren, gebruik je de `set()` methode om de waarde van een eigenschap in te stellen. Bijvoorbeeld, `set(PropertyDescriptor<V> property, V value)` stelt een eigenschap in op een opgegeven waarde.

:::info
Eigenschappen worden intern binnen de code van de component benaderd en gemanipuleerd en worden niet weerspiegeld in de DOM. Attributen daarentegen maken deel uit van de externe interface van de component en kunnen worden gebruikt om informatie van buitenaf naar een component door te geven, waardoor externe elementen of scripts de component kunnen configureren.
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

Naast het instellen van een eigenschap, gebruik je de `get()` methode in de `ElementComposite` klasse om eigenschappen te benaderen en te lezen. De `get()` methode kan een optionele `boolean` waarde ontvangen, die standaard op false staat, om aan te geven of de methode een aanvraag naar de client moet doen om de waarde op te halen. Dit beïnvloedt de prestaties, maar kan noodzakelijk zijn als de eigenschap puur in de client kan worden gewijzigd.

Een `Type` kan ook aan de methode worden doorgegeven, die bepaalt naar wat de opgehaalde resultaten moeten worden cast.

:::tip
Dit `Type` is niet per se vereist en voegt een extra laag van specificatie toe naarmate de gegevens worden opgehaald.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In de onderstaande demo zijn eigenschappen toegevoegd voor de QR-code op basis van de documentatie voor de webcomponent. Methoden zijn vervolgens geïmplementeerd die gebruikers toestaan om de verschillende geïmplementeerde eigenschappen te verkrijgen en in te stellen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Gebeurtenisregistratie {#event-registration}

Gevenissen zorgen voor communicatie tussen verschillende delen van je webforJ-app. De `ElementComposite` klasse biedt gebeurtenisafhandeling met ondersteuning voor debouncing, throttling, filtering en verzameling van aangepaste gebeurtenisdata.

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

webforJ biedt vooraf gebouwde gebeurtenisklassen met typetoegang tot gegevens:

- **ElementClickEvent**: Muisklikgebeurtenissen met coördinaten (`getClientX()`, `getClientY()`), informatie over de knop (`getButton()`) en modifier-toetsen (`isCtrlKey()`, `isShiftKey()`, enz.)
- **ElementDefinedEvent**: Afgevuurd wanneer een aangepast element is gedefinieerd in de DOM en klaar voor gebruik
- **ElementEvent**: Basis gebeurtenisklasse die toegang biedt tot ruwe gebeurtenisgegevens, gebeurtenistype (`getType()`) en gebeurtenis-ID (`getId()`)

### Gebeurtenislasten {#event-payloads}

Gevenissen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor ruwe gebeurtenisgegevens of gebruik getypte methoden wanneer beschikbaar op vooraf gebouwde gebeurtenisklassen. Voor meer details over het efficiënt gebruiken van gebeurtenislasten, zie de [Evenementenhulpmiddelen](../building-ui/events).

## Aangepaste gebeurtenisklassen {#custom-event-classes}

Voor gespecificeerde gebeurtenisafhandeling, maak je aangepaste gebeurtenisklassen met geconfigureerde lasten met behulp van `@EventName` en `@EventOptions` annotaties.

In het onderstaande voorbeeld is een klikgebeurtenis gemaakt en vervolgens toegevoegd aan de QR-codecomponent. Deze gebeurtenis, wanneer afgevuurd, geeft de "X" coördinaat van de muis op het moment van klikken op de component weer, die als gegevens aan de Java-gebeurtenis wordt verstrekt. Een methode wordt vervolgens geïmplementeerd om gebruikers toegang te geven tot deze gegevens, wat is hoe het in de app wordt weergegeven.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` stelt je in staat om gebeurtenisgedrag aan te passen door te configureren welke gegevens verzameld moeten worden, wanneer gebeurtenissen plaatsvinden, en hoe ze worden verwerkt. Hier is een uitgebreide codefragment met alle configuratie-opties:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Voer JavaScript uit voordat de gebeurtenis wordt afgevuurd
  .setCode("component.classList.add('processing');")
  
  // Alleen afvuren als aan voorwaarden is voldaan
  .setFilter("component.value.length >= 2")
  
  // Vertraging van uitvoering totdat de gebruiker stopt met typen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Prestatiebeheer {#performance-control}

Beheer wanneer en hoe vaak gebeurtenissen worden afgevuurd:

**Debouncing** vertraagt de uitvoering totdat activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300ms na de laatste gebeurtenis
```

**Throttling** beperkt de uitvoeringsfrequentie:

```java
options.setThrottle(100); // Maximaal eenmaal per 100ms afvuren
```

Beschikbare debouncerfases:

- `LEADING`: Afvuren onmiddellijk, daarna wachten
- `TRAILING`: Wacht op een stille periode, daarna afvuren (standaard)
- `BOTH`: Afvuren onmiddellijk en na een stille periode

## Opties samenvoegen {#options-merging}

Combineer gebeurtenisconfiguraties vanuit verschillende bronnen met behulp van `mergeWith()`. Basisopties bieden gemeenschappelijke gegevens voor alle gebeurtenissen, terwijl specifieke opties gespecialiseerde configuratie toevoegen. Latere opties overschrijven conflicterende instellingen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interactie met slots {#interacting-with-slots}

Webcomponenten gebruiken vaak slots om ontwikkelaars in staat te stellen de structuur van een component van buitenaf te definiëren. Een slot is een plaatsvervanger binnen een webcomponent die kan worden gevuld met inhoud bij het gebruik van de component. In de context van de `ElementComposite` klasse bieden slots een manier om de inhoud binnen een component aan te passen. De volgende methoden worden aangeboden om ontwikkelaars in staat te stellen met slots te interageren en deze te manipuleren:

1. **`findComponentSlot()`**: Deze methode wordt gebruikt om een specifieke component in een componentensysteem te zoeken. Het retourneert de naam van het slot waar de component zich bevindt. Als de component in geen enkel slot wordt gevonden, wordt een lege string geretourneerd.

2. **`getComponentsInSlot()`**: Deze methode haalt de lijst van componenten op die aan een gegeven slot in een componentensysteem zijn toegewezen. Optioneel kan een specifieke klasse worden doorgegeven om de resultaten van de methode te filteren.

3. **`getFirstComponentInSlot()`**: Deze methode is ontworpen om de eerste component die aan het slot is toegewezen op te halen. Optioneel kan een specifieke klasse worden doorgegeven om de resultaten van deze methode te filteren.

Het is ook mogelijk om de `add()` methode met een `String` parameter te gebruiken om het gewenste slot op te geven waarin de doorgegeven component moet worden toegevoegd.

Deze interacties stellen ontwikkelaars in staat om de kracht van webcomponenten te benutten door een schone en eenvoudige API te bieden voor het manipuleren van slots, eigenschappen en het behandelen van gebeurtenissen binnen de `ElementComposite` klasse.
