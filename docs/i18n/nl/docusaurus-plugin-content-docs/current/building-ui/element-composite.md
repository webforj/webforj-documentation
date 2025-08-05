---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 88eca7b854822f9d78ac20731ac5a857
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse fungeert als een veelzijdige basis voor het beheren van samengestelde elementen in webforJ-toepassingen. Het primaire doel is om de interactie met HTML-elementen, weergegeven door de `Element` klasse, te vergemakkelijken door een gestructureerde aanpak te bieden voor het omgaan met eigenschappen, attributen en gebeurtenislijsten. Het maakt implementatie en hergebruik van elementen in een toepassing mogelijk. Gebruik de `ElementComposite` klasse bij het implementeren van Web Components voor gebruik in webforJ-toepassingen.

Bij het gebruik van de `ElementComposite` klasse biedt de `getElement()` methode toegang tot het onderliggende `Element` component. Evenzo geeft de `getNodeName()` methode de naam van die knoop in de DOM.

:::tip
Het is mogelijk om alles te doen met de `Element` klasse zelf, zonder de `ElementComposite` klasse te gebruiken. De geboden methoden in de `ElementComposite` geven gebruikers echter een manier om het werk dat wordt gedaan te hergebruiken.
:::

Gedurende deze gids implementeren we de [Shoelace QR-code webcomponent](https://shoelace.style/components/qr-code) met behulp van de `ElementComposite` klasse.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschappen en attributen beschrijvingen {#property-and-attribute-descriptors}

Eigenschappen en attributen in webcomponenten vertegenwoordigen de status van de component. Ze worden vaak gebruikt om gegevens of configuratie te beheren. De `ElementComposite` klasse biedt een handige manier om met eigenschappen en attributen te werken.

Eigenschappen en attributen kunnen worden gedeclareerd en geïnitialiseerd als `PropertyDescriptor` leden van de `ElementComposite` klasse die wordt geschreven, en vervolgens in de code worden gebruikt. Om eigenschappen en attributen te definiëren, gebruik je de `set()` methode om de waarde van een eigenschap in te stellen. Bijvoorbeeld, `set(PropertyDescriptor<V> property, V value)` stelt een eigenschap in op een opgegeven waarde.

:::info
Eigenschappen worden intern binnen de code van de component benaderd en gemanipuleerd en weerspiegelen zich niet in de DOM. Attributen daarentegen maken deel uit van de externe interface van de component en kunnen worden gebruikt om informatie van buitenaf naar een component door te geven, waardoor een manier wordt geboden voor externe elementen of scripts om de component te configureren.
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

Naast het instellen van een eigenschap, gebruik je de `get()` methode in de `ElementComposite` klasse om eigenschappen te benaderen en te lezen. De `get()` methode kan een optionele `boolean` waarde worden doorgegeven, die standaard false is, om te bepalen of de methode een reis naar de client moet maken om de waarde op te halen. Dit heeft invloed op de prestaties, maar kan nodig zijn als de eigenschap uitsluitend aan de client kan worden gewijzigd.

Een `Type` kan ook aan de methode worden doorgegeven, wat aangeeft naar wat de opgehaalde resultaat moet worden geconverteerd.

:::tip
Dit `Type` is niet noodzakelijk, en voegt een extra laag specificatie toe terwijl de gegevens worden opgehaald.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In de onderstaande demo zijn eigenschappen toegevoegd voor de QR-code op basis van de documentatie voor de webcomponent. Methoden zijn vervolgens geïmplementeerd waarmee gebruikers de verschillende geïmplementeerde eigenschappen kunnen verkrijgen en instellen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Gebeurtenisregistratie {#event-registration}

Gebeurtenissen zijn een cruciaal onderdeel van webcomponenten, waardoor communicatie tussen verschillende delen van een toepassing mogelijk is. De `ElementComposite` klasse vereenvoudigt de registratie en verwerking van gebeurtenissen. Om een gebeurtenislijstenaar te registreren, gebruik je de `addEventListener()` methode om gebeurtenislijstenaren voor specifieke gebeurtenistypen te registreren. Geef de gebeurtenisklasse, de luisteraar en optionele gebeurtenisopties op.

```java
// Voorbeeld: Een klikgebeurtenis listener toevoegen
addEventListener(ClickEvent.class, event -> {
    // Behandel de klikgebeurtenis
});
```

:::info
De `ElementComposite` gebeurtenissen zijn anders dan `Element` gebeurtenissen, aangezien dit geen enkele klasse toestaat, maar alleen opgegeven `Event` klassen.
:::

In de demonstratie hieronder is een klikgebeurtenis gemaakt en vervolgens toegevoegd aan de QR-code component. Deze gebeurtenis, wanneer geactiveerd, zal de "X"-coördinaten van de muis weergeven op het moment van klikken op de component, die aan de Java-gebeurtenis als gegevens wordt verstrekt. Een methode is dan geïmplementeerd om de gebruiker toegang te geven tot deze gegevens, wat is hoe het in de toepassing wordt weergegeven. 
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interactie met Slots {#interacting-with-slots}

Webcomponenten maken vaak gebruik van slots om ontwikkelaars in staat te stellen de structuur van een component van buitenaf te definiëren. Een slot is een tijdelijke aanduiding binnen een webcomponent die kan worden gevuld met inhoud bij het gebruik van de component. In de context van de `ElementComposite` klasse bieden slots een manier om de inhoud binnen een component aan te passen. De volgende methoden worden aangeboden om ontwikkelaars in staat te stellen om met slots te interageren en deze te manipuleren:

1. **`findComponentSlot()`**: Deze methode wordt gebruikt om een specifieke component te zoeken in alle slots in een componentensysteem. Het retourneert de naam van de slot waar de component zich bevindt. Als de component in geen enkele slot wordt gevonden, wordt een lege string geretourneerd.

2. **`getComponentsInSlot()`**: Deze methode haalt de lijst van componenten op die aan een bepaalde slot in een componentensysteem zijn toegewezen. Optioneel kan een specifieke klasse worden doorgegeven om de resultaten van de methode te filteren.

3. **`getFirstComponentInSlot()`**: Deze methode is ontworpen om de eerste component op te halen die aan de slot is toegewezen. Optioneel kan een specifieke klasse worden doorgegeven om de resultaten van deze methode te filteren.

Het is ook mogelijk om de `add()` methode met een `String` parameter te gebruiken om de gewenste slot op te geven waarin de doorgegeven component moet worden toegevoegd.

Deze interacties stellen ontwikkelaars in staat om de kracht van webcomponenten te benutten door een schone en eenvoudige API te bieden voor het manipuleren van slots, eigenschappen en het afhandelen van gebeurtenissen binnen de `ElementComposite` klasse.
