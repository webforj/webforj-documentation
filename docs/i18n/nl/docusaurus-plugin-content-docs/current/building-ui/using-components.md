---
sidebar_position: 3
title: Componenten gebruiken
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
_i18n_hash: df0f3d5a956eda1abd755f646899a7cc
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Componenten zijn de bouwstenen van webforJ-toepassingen. Of je nu gebruikmaakt van ingebouwde componenten zoals `Button` en `TextField`, of werkt met aangepaste componenten die door jouw team worden geleverd, de manier waarop je ermee interageert volgt hetzelfde consistente model: je configureert eigenschappen, beheert de status en stelt componenten samen in lay-outs.

Deze gids richt zich op die dagelijkse handelingen: niet de interne werking van componenten, maar hoe je ze in de praktijk kunt gebruiken.

## Component eigenschappen {#component-properties}

Elke component biedt eigenschappen die de inhoud, het uiterlijk en het gedrag bepalen. De meeste hiervan hebben speciale, getypeerde Java-methoden (`setText()`, `setTheme()`, `setExpanse()`, enzovoort), wat de primaire manier is waarop je componenten in webforJ configureert. De onderstaande secties behandelen de eigenschappen en methoden die breed toepasbaar zijn voor componenttypes.

### Tekstinhoud {#text-content}

De methode `setText()` stelt de zichtbare tekst van een component in als letterlijke karakters, zoals de bijschrift op een `Button` of de inhoud van een `Label`. Voor invoercomponenten zoals `TextField` gebruik je `setValue()` om de huidige waarde van het veld in te stellen.

```java
Button button = new Button();
button.setText("Klik op mij");

Label label = new Label();
label.setText("Status: gereed");

TextField field = new TextField();
field.setValue("Initiële waarde");
```

Markup die met `setText()` is geschreven verschijnt als die karakters en wordt nooit uitgevoerd, waardoor tekst die afkomstig is van gebruikersinvoer of externe gegevens niet als live markup wordt geïnterpreteerd.

```java
// Wordt weergegeven als de letterlijke karakters "<b>Status: gereed</b>"
component.setText("<b>Status: gereed</b>");
```

:::note Gebruik van de `<html>`-tag
Eerdere versies van webforJ beschouwden een waarde die was ingepakt in `<html>` en naar `setText()` was verzonden als HTML. Dit gedrag is verouderd en zal worden verwijderd in webforJ 27.00.

De eerste keer dat een `<html>`-ingepakte waarde `setText()` bereikt, wordt er een waarschuwing gelogd die de component en de aanroeplocatie benoemt, zodat de aanroep kan worden verplaatst naar `setHtml()`.

Om de standaard van webforJ 27.00 vooraf aan te nemen, stel `webforj.legacyHtmlInText` in op `false`. In een Spring-app wordt dezelfde waarde ingesteld via `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (standaard)
component.setText("<html><b>Status: gereed</b></html>"); // rendert vetgedrukt

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: gereed</b></html>"); // toont de karakters <b>Status: gereed</b>
```
:::

### HTML rendering {#rendering-html}

Sommige componenten ondersteunen ook `setHtml()` voor gevallen waarin je inline HTML-markup in de inhoud moet renderen:

```java
Div container = new Div();
container.setHtml("<strong>Vette tekst</strong> en <em>cursieve tekst</em>");
```

:::danger Cross-site Scripting (XSS)
Als voorzorgsmaatregel tegen [cross-site scripting (XSS) aanvallen](/docs/security/application-security/common-threats#cross-site-scripting-xss), gebruik `setHtml()` alleen met inhoud die je direct beheert.
:::

### HTML-attributen {#html-attributes}

De meeste configuratie in webforJ wordt gedaan via getypeerde Java-methoden in plaats van ruwe HTML-attributen. `setAttribute()` is echter nuttig voor het doorgeven van toegankelijkheidsattributen die geen speciale API hebben:

```java
Button button = new Button("Indienen");
button.setAttribute("aria-label", "Dien het formulier in");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Controleer de ondersteuning van componenten
Niet alle componenten ondersteunen willekeurige attributen. Dit hangt af van de onderliggende componentimplementatie.
:::

### Component-ID's {#component-ids}

Je kunt een ID toewijzen aan het HTML-element van een component met `setAttribute()`:

```java
Button submitButton = new Button("Indienen");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-mail");
emailField.setAttribute("id", "email-input");
```

DOM-ID's worden vaak gebruikt voor testselectoren en CSS-targeting in je stijlen.

:::tip Geef de voorkeur aan klassen voor targeting van meerdere componenten
In tegenstelling tot CSS-klassen moeten ID's uniek zijn binnen je applicatie. Als je meerdere componenten wilt targeten, gebruik dan `addClassName()` in plaats daarvan.
:::

:::info Door het framework beheerde ID's
webforJ wijst ook automatische identificatie aan componenten intern toe. De serverzijde ID (toegankelijk via `getComponentId()`) wordt gebruikt voor frameworktracking, terwijl de clientzijde ID (toegankelijk via `getClientComponentId()`) wordt gebruikt voor client-servercommunicatie. Deze zijn gescheiden van het DOM `id` attribuut dat je met `setAttribute()` instelt.
:::

### Stijling {#styling}

Drie methoden dekken de meeste stijlingbehoeften: `setStyle()` voor individuele CSS-eigenschapwaarden, en `addClassName()` en `removeClassName()` om CSS-klassen die in je stijlen zijn gedefinieerd toe te passen of te verwijderen. Gebruik `setStyle()` voor kleine of unieke stijlaanpassingen, en gebruik CSS-klassen om grotere of herbruikbare stijlen toe te passen.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Schakel");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

## Componentstatus {#component-state}

Naast inhoud en uiterlijk hebben componenten statuseigenschappen die bepalen of ze zichtbaar zijn en of ze reageren op gebruikersinteractie. De twee meest gebruikte zijn `setVisible()` en `setEnabled()`.

`setVisible()` controleert of de component überhaupt in de gebruikersinterface wordt weergegeven. `setEnabled()` controleert of deze invoer of interactie accepteert terwijl deze zichtbaar blijft. In de meeste gevallen is uitschakelen verkieslijk boven verbergen: een uitgeschakelde knop communiceert nog steeds dat er een actie bestaat, maar deze is nog niet beschikbaar, wat minder verwarrend is dan dat deze verschijnt en weer verdwijnt.

```java
// Toon een extra veld wanneer een checkbox is aangevinkt
TextField advancedField = new TextField("Geavanceerde instelling");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Toon geavanceerde instellingen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Schakel een knop alleen in wanneer het vereiste veld een waarde heeft
Button submitButton = new Button("Indienen");
submitButton.setEnabled(false);

TextField nameField = new TextField("Naam");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Uitgeschakeld en verborgen zijn geen beveiliging
`setVisible(false)` en `setEnabled(false)` beïnvloeden de UI alleen. Ze voorkomen niet dat een vastberaden gebruiker de onderliggende actie via de browser of een vervaardigd verzoek uitvoert, dus vertrouw nooit op hen om gevoelige operaties te beschermen. Handhaaf altijd toegangsniveaus op de server. Zie [Uitgeschakeld en verborgen zijn geen beveiliging](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) voor meer details.
:::

Het onderstaande inlogformulier demonstreert `setEnabled()` in de praktijk. De aanmeldknop blijft uitgeschakeld totdat beide velden inhoud hebben, waardoor het voor de gebruiker duidelijk is dat invoer vereist is voordat hij verdergaat:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
]}
height='450px'
/>

## Werken met containers {#working-with-containers}

In webforJ wordt de lay-out afgehandeld door containers, die componenten zijn die andere componenten bevatten en bepalen hoe ze zijn gerangschikt. Je positioneert kindcomponenten niet handmatig; in plaats daarvan voeg je ze aan een container toe en configureer je de lay-out eigenschappen van die container.

### Componenten toevoegen {#adding-components}

Alle containers bieden een `add()`-methode. Je kunt componenten een voor een of allemaal tegelijk doorgeven:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klik op mij"));

TextField nameField = new TextField("Naam");
TextField emailField = new TextField("E-mail");
Button submitButton = new Button("Indienen");

container.add(nameField, emailField, submitButton);
```

### Lay-outopties {#layout-options}

`FlexLayout` is de primaire lay-outcontainer in webforJ en dekt de meeste gebruiksgevallen: rijen, kolommen, uitlijning, ruimte en verpakken. Voor meer complexe arrangements zoals CSS Grid of aangepaste positionering kun je CSS direct toepassen via `setStyle()` of `addClassName()` op elke containercomponent. Zie de [FlexLayout](/docs/components/flex-layout) documentatie voor het volledige bereik van lay-outopties.

### Secties weergeven en verbergen {#showing-hiding-sections}

Een veel voorkomende gebruik van `setVisible()` in containers is het onthullen van extra UI alleen wanneer dit relevant is. Dit houdt de interface gefocust en vermindert visuele rommel. In plaats van naar een nieuw uitzicht te navigeren, kun je een sectie van de huidige lay-out tonen als directe reactie op gebruikersinvoer.

Het volgende instellingenpaneel demonstreert dit: basismelding voorkeuren zijn altijd zichtbaar, en een sectie van geavanceerde opties verschijnt alleen wanneer de gebruiker hierom vraagt. De opslaanknop wordt geactiveerd zodra een instelling is gewijzigd:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
]}
height='450px'
/>

### Beheer van containers {#container-management}

Gebruik `remove()` en `removeAll()` om componenten tijdens runtime uit een container te verwijderen:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tijdelijk");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dit is nuttig wanneer je de inhoud volledig wilt vervangen, zoals het omwisselen van een laadindicator voor de geladen gegevens.

## Formuliervalidatie {#form-validation}

Het coördineren van meerdere componenten om een indieningsactie te regelen is een veelvoorkomend patroon in webforJ UI's. Het basisidee is dat elk invoerveld een luisteraar registreert, en telkens wanneer een waarde verandert, evaluëert het formulier opnieuw of aan alle criteria is voldaan en werkt de indien-knop dienovereenkomstig bij.

Het onderstaande voorbeeld sluit dit handmatig aan, zodat je kunt zien hoe componentstatus en evenementluisteraars samenwerken. Dit is niet de aanbevolen aanpak voor echte formulieren: handmatige luisterlogica wordt moeilijk te onderhouden naarmate formulieren groeien, en het verbindt je componenten niet met een onderliggend gegevensmodel.

:::tip Gebruik gegevensbinding voor formuliervalidatie
Voor productieformulieren, gebruik [gegevensbinding](/docs/data-binding/overview). Het dekt validatie, tweeweg-synchronisatie tussen componenten en je model, en waarde-transformatie via `BindingContext`. Het handmatige patroon dat hier wordt weergegeven is alleen ter illustratie.
:::

In dit contactformulier mag het naamveld niet leeg zijn, moet de e-mail een `@`-symbool bevatten, en moet het bericht minimaal 10 tekens lang zijn:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
]}
height='500px'
/>

## Dynamische inhoudsupdates {#dynamic-content-updates}

Componenten hoeven niet in een vaste staat te blijven nadat ze zijn gemaakt. Je kunt tekst bijwerken, CSS-klassen verwisselen en de ingeschakelde status op elk moment wisselen als reactie op app-gebeurtenissen. Een veelvoorkomend voorbeeld is het geven van feedback tijdens een langdurige taak:

```java
Label statusLabel = new Label("Gereed");
Button startButton = new Button("Start Proces");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Verwerken...");
    statusLabel.addClassName("processing");

    performTask(() -> {
        statusLabel.setText("Compleet");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Het uitschakelen van de knop terwijl de taak wordt uitgevoerd voorkomt dubbele indieningen, en het bijwerken van het label houdt de gebruiker geïnformeerd over wat er gebeurt.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

De interface `ComponentLifecycleObserver` stelt je in staat om componentlevenscyclus gebeurtenissen van buiten de component zelf te observeren. Dit is nuttig wanneer je moet reageren op een component die is gemaakt of vernietigd zonder de implementatie te wijzigen. Je kunt het bijvoorbeeld gebruiken om een register van actieve componenten bij te houden of externe middelen vrij te geven wanneer een component wordt verwijderd.

### Basisgebruik {#basic-usage}

Roep `addLifecycleObserver()` aan op een component om een callback te registreren. De callback ontvangt de component en de levenscyclus gebeurtenis:

```java
Button button = new Button("Bekijk mij");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Knop is gemaakt");
            break;
        case DESTROY:
            System.out.println("Knop is vernietigd");
            break;
    }
});
```

### Patroon: Resource-register {#pattern-resource-registry}

De DESTROY-gebeurtenis is bijzonder nuttig om een register automatisch in sync te houden. In plaats van handmatig componenten te verwijderen wanneer ze niet meer nodig zijn, laat je de component het register zelf op de hoogte brengen:

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();

    public void track(Component component, String name) {
        activeComponents.put(name, component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### Patroon: Componentcoördinatie {#pattern-component-coordination}

Een coördinator klasse die een set van gerelateerde componenten beheert kan dezelfde aanpak gebruiken om zijn interne lijst nauwkeurig te houden:

```java
public class FormCoordinator {
    private final List<DwcComponent<?>> managedComponents = new ArrayList<>();

    public void manage(DwcComponent<?> component) {
        managedComponents.add(component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }

    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### Wanneer te gebruiken {#when-to-use}

Gebruik `ComponentLifecycleObserver` voor:
- Het bouwen van componentregisters
- Implementatie van logging of monitoring
- Coördinatie van meerdere componenten
- Opruimen van externe middelen

Voor het uitvoeren van code nadat een component aan de DOM is bevestigd, zie `whenAttached()` in de [Composeren van componenten](/docs/building-ui/composing-components) gids.

## Gebruikersgegevens {#user-data}

Componenten kunnen willekeurige serverzijde gegevens bevatten via `setUserData()` en `getUserData()`. Beide methoden nemen een sleutel om de gegevens te identificeren. Dit is nuttig wanneer je domeinobjecten of context met een component wilt associëren zonder een aparte lookup-structuur te beheren.

```java
Button button = new Button("Verwerken");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Aangezien gebruikersgegevens nooit naar de cliënt worden verzonden, kun je gevoelige informatie of grote objecten veilig opslaan zonder het netwerkverkeer te beïnvloeden.
