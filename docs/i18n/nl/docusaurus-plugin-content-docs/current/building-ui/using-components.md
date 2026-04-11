---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: 3ffe2e3b31ea278e434f7319f8019637
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Componenten zijn de bouwstenen van webforJ-applicaties. Of je nu gebruikmaakt van ingebouwde componenten zoals `Button` en `TextField`, of werkt met aangepaste componenten die door je team zijn geleverd, de manier waarop je met hen interactie hebt volgt hetzelfde consistente model: je configureert eigenschappen, beheert status en stelt componenten samen in indelingen.

Deze gids richt zich op die dagelijkse operaties: niet op de interne werking van componenten, maar op hoe je ze in de praktijk kunt gebruiken.

## Component eigenschappen {#component-properties}

Elke component heeft eigenschappen die de content, uitstraling en gedrag ervan beheersen. De meeste hiervan hebben specifieke, getypte Java-methoden (`setText()`, `setTheme()`, `setExpanse()`, en meer), wat de primaire manier is waarop je componenten in webforJ configureert. De onderstaande secties behandelen de eigenschappen en methoden die breed toepasbaar zijn op componenttypes.

### Tekstinhoud {#text-content}

De `setText()`-methode stelt de zichtbare tekst van een component in, zoals de bijschrift op een `Button` of de inhoud van een `Label`. Voor invoercomponenten zoals `TextField`, gebruik je `setValue()` in plaats daarvan om de huidige waarde van het veld in te stellen.

```java
Button button = new Button();
button.setText("Klik op mij");

Label label = new Label();
label.setText("Status: klaar");

TextField field = new TextField();
field.setValue("Initiële waarde");
```

Sommige componenten ondersteunen ook `setHtml()` voor gevallen waarin je inline HTML-markup in de inhoud nodig hebt:

```java
Div container = new Div();
container.setHtml("<strong>Vetgedrukte tekst</strong> en <em>cursieve tekst</em>");
```

### HTML-attributen {#html-attributes}

De meeste configuratie in webforJ wordt gedaan via getypte Java-methoden in plaats van rauwe HTML-attributen. Echter, `setAttribute()` is nuttig voor het doorgeven van toegankelijkheidsattributen die geen specifieke API hebben:

```java
Button button = new Button("Indienen");
button.setAttribute("aria-label", "Indien het formulier");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Controleer componentondersteuning
Niet alle componenten ondersteunen willekeurige attributen. Dit hangt af van de onderliggende implementatie van de component.
:::

### Component-ID's {#component-ids}

Je kunt een ID toewijzen aan het HTML-element van een component met behulp van `setAttribute()`:

```java
Button submitButton = new Button("Indienen");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-mail");
emailField.setAttribute("id", "email-input");
```

DOM-ID's worden vaak gebruikt voor testselectoren en CSS-targeting in je stijlen.

:::tip Geef de voorkeur aan klassen voor meerdere componenttargeting
In tegenstelling tot CSS-klassen, moeten ID's uniek zijn binnen je app. Als je meerdere componenten wilt targeten, gebruik dan `addClassName()` in plaats daarvan.
:::

:::info Door het framework beheerde ID's
webforJ kent ook automatisch identificatoren toe aan componenten intern. De server-side ID (toegankelijk via `getComponentId()`) wordt gebruikt voor frameworktracking, terwijl de client-side ID (toegankelijk via `getClientComponentId()`) wordt gebruikt voor client-servercommunicatie. Deze verschillen van het DOM `id`-attribuut dat je instelt met `setAttribute()`.
:::

### Stijlen {#styling}

Drie methoden dekken de meeste stylingbehoeften: `setStyle()` voor individuele CSS-eigenschapwaarden, en `addClassName()` en `removeClassName()` om CSS-klassen toe te passen of te verwijderen die in je stijlen zijn gedefinieerd. Gebruik `setStyle()` voor kleine of eenmalige stijlaanpassingen, en gebruik CSS-klassen om grotere of herbruikbare stijlen toe te passen.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Wisselen");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

:::note Erfgoedbenadering
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) is een erfgoedbenadering en wordt over het algemeen niet aanbevolen voor nieuwe projecten. In de meeste gevallen, houd je stijlen in aparte CSS-bestanden.
:::

## Componentstatus {#component-state}

Naast inhoud en verschijning, hebben componenten statuseigenschappen die bepalen of ze zichtbaar zijn en of ze reageren op gebruikersinteractie. De twee meest gebruikte zijn `setVisible()` en `setEnabled()`.

`setVisible()` bepaalt of de component überhaupt in de UI wordt weergegeven. `setEnabled()` bepaalt of het invoer of interactie accepteert terwijl het zichtbaar blijft. In de meeste gevallen is uitschakelen de voorkeur boven verbergen: een uitgeschakelde knop communiceert nog steeds dat er een actie bestaat, maar die nog niet beschikbaar is, wat minder desoriënterend is dan wanneer deze verschijnt en weer verdwijnt.

```java
// Toon een extra veld wanneer een checkbox is aangevinkt
TextField advancedField = new TextField("Geavanceerde instelling");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Toon geavanceerde instellingen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Een knop inschakelen alleen wanneer het verplichte veld een waarde heeft
Button submitButton = new Button("Indienen");
submitButton.setEnabled(false);

TextField nameField = new TextField("Naam");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Het volgende inlogformulier demonstreert `setEnabled()` in de praktijk. De aanmeldknop blijft uitgeschakeld totdat beide velden inhoud hebben, wat duidelijk maakt voor de gebruiker dat invoer vereist is voordat ze verder kunnen:

<ComponentDemo 
path='/webforj/conditionalstate?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/conditionalstate.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java'
height='400px'
/>

## Werken met containers {#working-with-containers}

In webforJ wordt de lay-out behandeld door containers, dat zijn componenten die andere componenten bevatten en bepalen hoe ze zijn gerangschikt. Je positioneert kindcomponenten niet handmatig; in plaats daarvan voeg je ze toe aan een container en configureer je de lay-out eigenschappen van die container.

### Componenten toevoegen {#adding-components}

Alle containers bieden een `add()`-methode. Je kunt componenten een voor een doorgeven of allemaal tegelijk:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klik op mij"));

TextField nameField = new TextField("Naam");
TextField emailField = new TextField("E-mail");
Button submitButton = new Button("Indienen");

container.add(nameField, emailField, submitButton);
```

### Lay-outopties {#layout-options}

`FlexLayout` is de primaire lay-outcontainer in webforJ en dekt de meeste gebruiksgevallen: rijen, kolommen, uitlijning, spacing, en wikkelen. Voor complexere arrangementen zoals CSS Grid of aangepaste positionering, kun je CSS rechtstreeks toepassen via `setStyle()` of `addClassName()` op elke containercomponent. Zie de [FlexLayout](/docs/components/flex-layout) documentatie voor het volledige scala aan lay-outopties.

### Secties tonen en verbergen {#showing-hiding-sections}

Een veel voorkomende toepassing van `setVisible()` in containers is het onthullen van extra UI alleen wanneer deze relevant is. Dit houdt de interface gefocust en vermindert visuele rommel. In plaats van naar een nieuw scherm te navigeren, kun je een sectie van de huidige lay-out tonen in directe reactie op de gebruikersinvoer.

Het volgende instellingenpaneel demonstreert dit: basis notificatievoorkeuren zijn altijd zichtbaar, en een sectie voor geavanceerde opties verschijnt alleen wanneer de gebruiker erom vraagt. De opslaan-knop wordt geactiveerd zodra een instelling is gewijzigd:

<ComponentDemo 
path='/webforj/progressivedisclosure?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/progressivedisclosure.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java'
height='450px'
/>

### Containerbeheer {#container-management}

Gebruik `remove()` en `removeAll()` om componenten uit een container te verwijderen tijdens runtime:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tijdelijk");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dit is nuttig wanneer je de inhoud volledig moet vervangen, zoals het verwisselen van een laadindicator voor de geladen data.

## Formuliervalidatie {#form-validation}

Het coördineren van meerdere componenten om een indienen actie te blokkeren is een van de meest voorkomende patronen in webforJ UI's. Het kernidee is simpel: elk invoerveld registreert een luisteraar, en telkens wanneer een waarde verandert, evalueert het formulier opnieuw of aan alle criteria is voldaan en werkt de indienen-knop dienovereenkomstig bij.

Dit is beter dan validatiefouten pas te laten zien nadat de gebruiker op indienen heeft geklikt, omdat het continue feedback geeft en onnodige indieningen voorkomt. De indienen-knop fungeert als de indicator: uitgeschakeld betekent dat het formulier niet klaar is, ingeschakeld betekent dat het dat wel is.

In dit contactformulier mag het naamveld niet leeg zijn, moet de e-mail een `@`-symbool bevatten, en moet het bericht minimaal 10 tekens lang zijn:

<ComponentDemo 
path='/webforj/formvalidation?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/formvalidation.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java'
height='500px'
/>

## Dynamische inhoud updates {#dynamic-content-updates}

Componenten hoeven niet in een vaste staat te blijven nadat ze zijn gemaakt. Je kunt tekst bijwerken, CSS-klassen verwisselen en de ingeschakelde status op elk moment toggle in reactie op evenementen in de app. Een veelvoorkomend voorbeeld is het geven van feedback tijdens een langdurige taak:

```java
Label statusLabel = new Label("Klaar");
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

Het uitschakelen van de knop terwijl de taak loopt voorkomt dubbele indieningen, en het bijwerken van het label houdt de gebruiker geïnformeerd over wat er gebeurt.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

De `ComponentLifecycleObserver` interface stelt je in staat om lifecycle-events van componenten van buiten de component zelf te observeren. Dit is nuttig wanneer je moet reageren op het creëren of vernietigen van een component zonder de implementatie ervan te wijzigen. Bijvoorbeeld, je zou het kunnen gebruiken om een register van actieve componenten bij te houden of externe bronnen vrij te geven wanneer een component wordt verwijderd.

### Basisgebruik {#basic-usage}

Roep `addLifecycleObserver()` aan op elke component om een callback te registreren. De callback ontvangt de component en het lifecycle-event:

```java
Button button = new Button("Bekijk mij");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("De knop is gemaakt");
            break;
        case DESTROY:
            System.out.println("De knop is vernietigd");
            break;
    }
});
```

### Patroon: Resource registry {#pattern-resource-registry}

Het DESTROY-event is bijzonder nuttig voor het automatisch synchroniseren van een register. In plaats van handmatig componenten te verwijderen wanneer ze niet meer nodig zijn, laat je de component het register zelf informeren:

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

Een coördinatorclass die een set gerelateerde componenten beheert, kan dezelfde aanpak gebruiken om zijn interne lijst accuraat te houden:

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
- Opruimen van externe bronnen

Voor het uitvoeren van code nadat een component aan de DOM is bevestigd, zie [`whenAttached()`](/docs/building-ui/composite-components) in de gids voor samenstellende componenten.

## Gebruikersdata {#user-data}

Componenten kunnen willekeurige server-side data met zich meedragen via `setUserData()` en `getUserData()`. Beide methoden nemen een sleutel om de data te identificeren. Dit is nuttig wanneer je domeinobjecten of context aan een component wilt koppelen zonder een aparte opzoekstructuur te beheren.

```java
Button button = new Button("Verwerken");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Aangezien gebruikersdata nooit naar de client wordt verzonden, kun je gevoelige informatie of grote objecten veilig opslaan zonder invloed op het netwerkverkeer.
