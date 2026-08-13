---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 046749107d0e78ccfaab4017d4e374d1
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Componenten zijn de bouwstenen van webforJ-toepassingen. Of je nu gebruikmaakt van ingebouwde componenten zoals `Button` en `TextField`, of werkt met aangepaste componenten die door jouw team worden geleverd, de manier waarop je met hen interactie hebt, volgt hetzelfde consistente model: je configureert eigenschappen, beheert de status en stelt componenten samen in lay-outs.

Deze gids richt zich op die dagelijkse activiteiten: niet op de interne werking van componenten, maar op hoe je in de praktijk dingen gedaan krijgt met ze.

## Component eigenschappen {#component-properties}

Elke component exposeert eigenschappen die de inhoud, uitstraling en het gedrag ervan beheersen. De meeste hiervan hebben specifieke, getypte Java-methoden (`setText()`, `setTheme()`, `setExpanse()`, enzovoort), wat de primaire manier is waarop je componenten in webforJ configureert. De onderstaande secties behandelen de eigenschappen en methoden die breed van toepassing zijn op componenttypen.

### Tekstinhoud {#text-content}

De `setText()`-methode stelt de zichtbare tekst van een component in als letterlijke tekens, zoals het bijschrift op een `Button` of de inhoud van een `Label`. Voor invoercomponenten zoals `TextField`, gebruik in plaats daarvan `setValue()` om de huidige waarde van het veld in te stellen.

```java
Button button = new Button();
button.setText("Klik Hier");

Label label = new Label();
label.setText("Status: gereed");

TextField field = new TextField();
field.setValue("Initiële waarde");
```

Markup geschreven met `setText()` verschijnt als die tekens en wordt nooit uitgevoerd, wat voorkomt dat tekst die afkomstig is van gebruikersinvoer of externe gegevens wordt geïnterpreteerd als live markup.

```java
// Wordt weergegeven als de letterlijke tekens "<b>Status: gereed</b>"
component.setText("<b>Status: gereed</b>");
```

:::note Gebruik van de `<html>`-tag
Eerdere versies van webforJ behandelden een waarde die in `<html>` is gewrapt en naar `setText()` wordt gestuurd als HTML. Dit gedrag is verouderd en zal worden verwijderd in webforJ 27.00.

De eerste keer dat een waarde die in `<html>` is gewrapt `setText()` bereikt, wordt er een waarschuwing gelogd die de component en de aanroepplaats benoemt, zodat de aanroep kan worden verplaatst naar `setHtml()`.

Om vooraf de standaard van webforJ 27.00 aan te nemen, stel `webforj.legacyHtmlInText` in op `false`. In een Spring-app wordt dezelfde waarde ingesteld via `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (standaard)
component.setText("<html><b>Status: gereed</b></html>"); // rendert vetgedrukt

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: gereed</b></html>"); // toont de tekens <b>Status: gereed</b>
```
:::

### HTML renderen {#rendering-html}

Sommige componenten ondersteunen ook `setHtml()` voor gevallen waarin je inline HTML-markup in de inhoud moet weergeven:

```java
Div container = new Div();
container.setHtml("<strong>Vetgedrukte tekst</strong> en <em>cursieve tekst</em>");
```

:::danger Cross-site Scripting (XSS)
Als voorzorgsmaatregel tegen [cross-site scripting (XSS)-aanvallen](/docs/security/application-security/common-threats#cross-site-scripting-xss), gebruik alleen `setHtml()` met inhoud die je direct beheert.
:::

### HTML-attributen {#html-attributes}

De meeste configuratie in webforJ gebeurt via getypte Java-methoden in plaats van ruwe HTML-attributen. `setAttribute()` is echter handig voor het doorgeven van toegankelijkheidsattributen die geen specifieke API hebben:

```java
Button button = new Button("Verzenden");
button.setAttribute("aria-label", "Verzend het formulier");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Controleer de ondersteuning van componenten
Niet alle componenten ondersteunen willekeurige attributen. Dit hangt af van de onderliggende implementatie van de component.
:::

### Component-ID's {#component-ids}

Je kunt een ID toewijzen aan het HTML-element van een component met `setAttribute()`:

```java
Button submitButton = new Button("Verzenden");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-mail");
emailField.setAttribute("id", "email-input");
```

DOM-ID's worden vaak gebruikt voor testselectoren en CSS-doelstellingen in je stijlen.

:::tip Geef de voorkeur aan klassen voor het targeten van meerdere componenten
In tegenstelling tot CSS-klassen moeten ID's uniek zijn binnen je app. Als je meerdere componenten wilt targeten, gebruik dan `addClassName()` in plaats daarvan.
:::

:::info Door het framework beheerde ID's
webforJ kent ook automatisch identificerende nummers toe aan componenten intern. De serverzijde ID (toegankelijk via `getComponentId()`) wordt gebruikt voor framework tracking, terwijl de clientzijde ID (toegankelijk via `getClientComponentId()`) wordt gebruikt voor communicatie tussen client en server. Deze zijn apart van het DOM `id` attribuut dat je instelt met `setAttribute()`.
:::

### Styling {#styling}

Drie methoden dekken de meeste stylingbehoeften: `setStyle()` voor individuele CSS-eigenschap waarden, en `addClassName()` en `removeClassName()` om CSS-klassen toe te passen of te verwijderen die in je stijlen zijn gedefinieerd. Gebruik `setStyle()` voor kleine of eenmalige stylingaanpassingen, en gebruik CSS-klassen om grotere of herbruikbare styling toe te passen.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Toggle");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

## Componentstatus {#component-state}

Naast inhoud en uiterlijk hebben componenten status eigenschappen die bepalen of ze zichtbaar zijn en of ze reageren op gebruikersinteractie. De twee meest gebruikte zijn `setVisible()` en `setEnabled()`.

`setVisible()` beheert of de component al dan niet in de UI wordt weergegeven. `setEnabled()` beheert of het input of interactie accepteert terwijl het zichtbaar blijft. In de meeste gevallen is uitschakelen te verkiezen boven verbergen: een uitgeschakelde knop communiceert nog steeds dat een actie bestaat, maar nog niet beschikbaar is, wat minder verwarrend is dan wanneer deze verschijnt en verdwijnt.

```java
// Toon een extra veld wanneer een selectievakje is aangevinkt
TextField advancedField = new TextField("Geavanceerde instelling");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Toon geavanceerde instellingen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Schakel een knop alleen in wanneer het vereiste veld een waarde heeft
Button submitButton = new Button("Verzenden");
submitButton.setEnabled(false);

TextField nameField = new TextField("Naam");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Uitgeschakeld en verborgen zijn geen beveiliging
`setVisible(false)` en `setEnabled(false)` beïnvloeden alleen de UI. Ze voorkomen niet dat een vastberaden gebruiker de onderliggende actie via de browser of een gemaakte aanvraag aanroept, dus reken nooit op hen om gevoelige bewerkingen te beschermen. Handhaaf altijd toegangscontrole op de server. Zie [Uitgeschakeld en verborgen zijn geen beveiliging](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) voor meer details.
:::

Het volgende aanmeldformulier demonstreert `setEnabled()` in de praktijk. De inlogknop blijft uitgeschakeld totdat beide velden inhoud hebben, waardoor het duidelijk is voor de gebruiker dat invoer vereist is voordat hij verder kan:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/frontend/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Werken met containers {#working-with-containers}

In webforJ wordt de lay-out beheerd door containers, die componenten bevatten en bepalen hoe ze zijn gerangschikt. Je plaatst kindercomponenten niet handmatig; in plaats daarvan voeg je ze toe aan een container en configureer je de lay-out eigenschappen van die container.

### Componenten toevoegen {#adding-components}

Alle containers bieden een `add()`-methode. Je kunt componenten één voor één of allemaal tegelijk doorgeven:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klik Hier"));

TextField nameField = new TextField("Naam");
TextField emailField = new TextField("E-mail");
Button submitButton = new Button("Verzenden");

container.add(nameField, emailField, submitButton);
```

### Lay-out opties {#layout-options}

`FlexLayout` is de primaire lay-outcontainer in webforJ en dekt de meeste gebruiksscenario's: rijen, kolommen, uitlijning, ruimte, en wikkeling. Voor complexere arrangementen zoals CSS Grid of aangepaste positionering kun je CSS rechtstreeks toepassen via `setStyle()` of `addClassName()` op elke containercomponent. Zie de [FlexLayout](/docs/components/flex-layout) documentatie voor het volledige scala aan lay-out opties.

### Secties tonen en verbergen {#showing-hiding-sections}

Een veel voorkomende toepassing van `setVisible()` in containers is het onthullen van extra UI alleen wanneer het relevant is. Dit houdt de interface gefocust en vermindert visuele rommel. In plaats van naar een nieuwe weergave te navigeren, kun je een sectie van de huidige lay-out tonen als directe reactie op gebruikersinvoer.

Het volgende instellingenpaneel demonstreert dit: basis notificatievoorkeuren zijn altijd zichtbaar, en een sectie met geavanceerde opties verschijnt alleen wanneer de gebruiker hierom vraagt. De knop opslaan wordt geactiveerd zodra een instelling wordt gewijzigd:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/frontend/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Containerbeheer {#container-management}

Gebruik `remove()` en `removeAll()` om componenten uit een container te halen tijdens runtime:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tijdelijk");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dit is handig wanneer je de inhoud volledig moet vervangen, zoals het wisselen van een laadindicator voor de geladen gegevens.

## Formuliervalidatie {#form-validation}

Het coördineren van meerdere componenten om een verzendactie te blokkeren is een veelvoorkomend pattern in webforJ UI's. Het basisidee is dat elk invoerveld een luisteraar registreert, en telkens wanneer een waarde verandert, evalueert het formulier opnieuw of aan alle criteria is voldaan en werkt de verzendknop dienovereenkomstig bij.

Het onderstaande voorbeeld laat dit handmatig inrichten, zodat je kunt zien hoe componentstatus en evenementluisteraars samenwerken. Het is niet de aanbevolen aanpak voor echte formulieren: handmatige luisterlogica wordt moeilijk te onderhouden naarmate formulieren groeien, en het verbindt je componenten niet met een onderliggend datamodel.

:::tip Gebruik databinding voor formuliervalidatie
Voor productieformulieren gebruik je [databinding](/docs/data-binding/overview). Dit dekt validatie, tweezijdige synchronisatie tussen componenten en je model, en waarde transformatie via `BindingContext`. Het handmatige patroon dat hier wordt getoond, is alleen ter illustratie.
:::

In dit contactformulier mag het naamveld niet leeg zijn, moet de e-mail een `@`-symbool bevatten en moet het bericht minstens 10 tekens lang zijn:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/frontend/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamische inhoudsupdates {#dynamic-content-updates}

Componenten hoeven niet in een vaste staat te blijven nadat ze zijn gemaakt. Je kunt tekst bijwerken, CSS-klassen verwisselen en de ingeschakelde status op elk moment in reactie op app-gebeurtenissen wijzigen. Een veelvoorkomend voorbeeld is het geven van feedback tijdens een langdurige taak:

```java
Label statusLabel = new Label("Gereed");
Button startButton = new Button("Start Proces");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Verwerken...");
    statusLabel.addClassName("processing");

    performTask(() -> {
        statusLabel.setText("Voltooid");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Het uitschakelen van de knop terwijl de taak wordt uitgevoerd voorkomt dubbele indieningen, en het bijwerken van het label houdt de gebruiker op de hoogte van wat er gebeurt.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

De `ComponentLifecycleObserver`-interface stelt je in staat om componentlevenscyclus gebeurtenissen van buiten de component zelf te observeren. Dit is nuttig wanneer je moet reageren op een component die wordt gemaakt of vernietigd zonder de implementatie ervan te wijzigen. Je kunt het bijvoorbeeld gebruiken om een register van actieve componenten bij te houden of externe middelen vrij te geven wanneer een component wordt verwijderd.

### Basisgebruik {#basic-usage}

Roep `addLifecycleObserver()` aan op een component om een callback te registreren. De callback ontvangt de component en de levenscyclus gebeurtenis:

```java
Button button = new Button("Kijk Naar Mij");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Button is gemaakt");
            break;
        case DESTROY:
            System.out.println("Button is vernietigd");
            break;
    }
});
```

### Patroon: Hulpbronregister {#pattern-resource-registry}

De DESTROY-gebeurtenis is bijzonder nuttig voor het automatisch synchroniseren van een register. In plaats van componenten handmatig te verwijderen wanneer ze niet langer nodig zijn, laat je de component zelf het register op de hoogte stellen:

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

### Patroon: Coördinatie van componenten {#pattern-component-coordination}

Een coördinatorklasse die een set gerelateerde componenten beheert, kan dezelfde aanpak gebruiken om zijn interne lijst nauwkeurig te houden:

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
- Het implementeren van logging of monitoring
- Het coördineren van meerdere componenten
- Het opruimen van externe middelen

Voor het uitvoeren van code nadat een component aan de DOM is toegevoegd, zie `whenAttached()` in de [Composing Components](/docs/building-ui/composing-components) gids.

## Gebruikersgegevens {#user-data}

Componenten kunnen willekeurige serverzijde gegevens dragen via `setUserData()` en `getUserData()`. Beide methoden nemen een sleutel om de gegevens te identificeren. Dit is nuttig wanneer je domeinobjecten of context met een component wilt associëren zonder een aparte lookup-structuur te beheren.

```java
Button button = new Button("Verwerken");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Aangezien gebruikersgegevens nooit naar de client worden verzonden, kun je gevoelige informatie of grote objecten veilig opslaan zonder de netwerkverkeer te beïnvloeden.
