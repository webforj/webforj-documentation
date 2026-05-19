---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: cf47b1c83e67cb4c4998c149a7696701
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Componenten zijn de bouwstenen van webforJ-toepassingen. Of je nu gebruikmaakt van ingebouwde componenten zoals `Button` en `TextField`, of werkt met aangepaste componenten die door jouw team zijn geleverd, de manier waarop je ermee omgaat volgt hetzelfde consistente model: je configureert eigenschappen, beheert staat en stelt componenten samen in lay-outs.

Deze gids richt zich op die dagelijkse taken: niet de interne werking van hoe componenten functioneren, maar hoe je dingen praktisch voor elkaar krijgt met hen.

## Componenteigenschappen {#component-properties}

Elke component exposeert eigenschappen die de inhoud, uitstraling en het gedrag ervan beheersen. De meeste hiervan hebben specifieke, getypte Java-methoden (`setText()`, `setTheme()`, `setExpanse()`, enzovoort), wat de primaire manier is waarop je componenten in webforJ configureert. De onderstaande secties behandelen de eigenschappen en methoden die in het algemeen van toepassing zijn op componenttypes.

### Tekstinhoud {#text-content}

De methode `setText()` stelt de zichtbare tekst van een component in, zoals het bijschrift op een `Button` of de inhoud van een `Label`. Voor invoercomponenten zoals `TextField`, gebruik je `setValue()` om de huidige waarde van het veld in te stellen.

```java
Button button = new Button();
button.setText("Klik op Mij");

Label label = new Label();
label.setText("Status: gereed");

TextField field = new TextField();
field.setValue("Begin waarde");
```

Sommige componenten ondersteunen ook `setHtml()` voor gevallen waar je inline HTML-markup in de inhoud nodig hebt:

```java
Div container = new Div();
container.setHtml("<strong>Vette tekst</strong> en <em>cursieve tekst</em>");
```

### HTML-attributen {#html-attributes}

De meeste configuratie in webforJ gebeurt via getypte Java-methoden en niet via rauwe HTML-attributen. Echter, `setAttribute()` is nuttig voor het doorgeven van toegankelijkheidsattributen waarvoor geen specifieke API bestaat:

```java
Button button = new Button("Verzenden");
button.setAttribute("aria-label", "Verzend het formulier");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Controleer componentondersteuning
Niet alle componenten ondersteunen willekeurige attributen. Dit hangt af van de onderliggende componentimplementatie.
:::

### Component-ID's {#component-ids}

Je kunt een ID toekennen aan een HTML-element van een component met `setAttribute()`:

```java
Button submitButton = new Button("Verzenden");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "email-input");
```

DOM-ID's worden vaak gebruikt voor testselectors en CSS-targeting in je stylesheets.

:::tip Geef de voorkeur aan klassen voor targeting van meerdere componenten
In tegenstelling tot CSS-klassen, moeten ID's uniek zijn binnen je app. Als je meerdere componenten moet targeten, gebruik dan `addClassName()` in plaats daarvan.
:::

:::info Framework-beheerde ID's
webforJ wijst ook automatisch identificatoren toe aan componenten intern. De serverzijde ID (toegankelijk via `getComponentId()`) wordt gebruikt voor frameworktracking, terwijl de clientzijde ID (toegankelijk via `getClientComponentId()`) wordt gebruikt voor client-servercommunicatie. Deze zijn gescheiden van het DOM `id` attribuut dat je instelt met `setAttribute()`.
:::

### Stijling {#styling}

Drie methoden dekken de meeste stijlingbehoeften: `setStyle()` voor afzonderlijke CSS-eigenschappen, en `addClassName()` en `removeClassName()` om CSS-classes toe te passen of te verwijderen die in je stylesheets zijn gedefinieerd. 
Gebruik `setStyle()` voor kleine of eenmalige stijlaanpassingen, en gebruik CSS-klassen om grotere of herbruikbare stijlen toe te passen.

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

:::note Verouderde aanpak
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) is een verouderde aanpak en wordt over het algemeen niet aanbevolen voor nieuwe projecten. In de meeste gevallen is het beter om je stijlen in aparte CSS-bestanden te houden.
:::

## Componentstaat {#component-state}

Naast inhoud en uiterlijk hebben componenten state-eigenschappen die bepalen of ze zichtbaar zijn en of ze reageren op gebruikersinteractie. De twee meest gebruikte zijn `setVisible()` en `setEnabled()`.

`setVisible()` beheert of de component überhaupt in de UI wordt weergegeven. `setEnabled()` beheert of deze invoer of interactie accepteert terwijl deze zichtbaar blijft. In de meeste gevallen is het beter om een component uit te schakelen dan om deze te verbergen: een uitgeschakelde knop communiceert nog steeds dat er een actie bestaat maar nog niet beschikbaar is, wat minder verwarrend is dan dat deze verschijnt en verdwijnt.

```java
// Toon een extra veld wanneer een checkbox is aangevinkt
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

Het volgende inlogformulier demonstreert `setEnabled()` in de praktijk. De aanmeldknop blijft uitgeschakeld totdat beide velden inhoud hebben, wat duidelijk maakt voor de gebruiker dat invoer vereist is voordat hij verder kan:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Werken met containers {#working-with-containers}

In webforJ wordt de lay-out behandeld door containers, die componenten zijn die andere componenten vasthouden en het arrangement ervan beheersen. Je positioneert kindercomponenten niet handmatig; in plaats daarvan voeg je ze toe aan een container en configureert je de lay-out-eigenschappen van die container.

### Componenten toevoegen {#adding-components}

Alle containers bieden een `add()` methode. Je kunt componenten één voor één of allemaal tegelijk doorgeven:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klik op Mij"));

TextField nameField = new TextField("Naam");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Verzenden");

container.add(nameField, emailField, submitButton);
```

### Lay-outopties {#layout-options}

`FlexLayout` is de primaire lay-outcontainer in webforJ en dekt de meeste gebruikssituaties: rijen, kolommen, uitlijning, spatiëring en wikkeling. Voor complexere arrangementen zoals CSS Grid of aangepaste positionering, kun je CSS direct toepassen via `setStyle()` of `addClassName()` op elke containercomponent. Zie de [FlexLayout](/docs/components/flex-layout) documentatie voor het volledige aanbod van lay-outopties.

### Secties tonen en verbergen {#showing-hiding-sections}

Een veelgebruikte toepassing van `setVisible()` in containers is het onthullen van extra UI alleen wanneer deze relevant is. Dit houdt de interface gefocust en vermindert visuele rommel. In plaats van naar een nieuw zicht te navigeren, kun je een sectie van de huidige lay-out tonen als directe reactie op gebruikersinvoer.

Het volgende instellingenpaneel demonstreert dit: basis meldingsvoorkeuren zijn altijd zichtbaar, en een sectie met geavanceerde opties verschijnt alleen wanneer de gebruiker erom vraagt. De opslaan-knop wordt geactiveerd zodra er een instelling is gewijzigd:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Containerbeheer {#container-management}

Gebruik `remove()` en `removeAll()` om componenten tijdens runtime uit een container te verwijderen:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tijdelijk");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dit is nuttig wanneer je de inhoud volledig moet vervangen, zoals het vervangen van een laadindicator door de geladen gegevens.

## Formuliervalidatie {#form-validation}

Het coördineren van meerdere componenten om een verzendactie te blokkeren, is een van de meest voorkomende patronen in webforJ UI's. Het kernidee is simpel: elk invoerveld registreert een luisteraar, en wanneer een waarde verandert, evalueert het formulier opnieuw of aan alle criteria is voldaan en werkt het de verzendknop dienovereenkomstig bij.

Dit is verkieslijk boven het tonen van validatiefouten alleen nadat de gebruiker op verzenden klikt, omdat het continue feedback geeft en onnodige indieningen voorkomt. De verzendknop dient als indicator: uitgeschakeld betekent dat het formulier niet gereed is, ingeschakeld betekent dat het dat wel is.

In dit contactformulier mag het naamveld niet leeg zijn, moet de e-mail een `@`-symbool bevatten en moet het bericht minstens 10 tekens lang zijn:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamische inhoudsupdates {#dynamic-content-updates}

Componenten hoeven niet in een vaste staat te blijven nadat ze zijn aangemaakt. Je kunt tekst bijwerken, CSS-klassen wisselen en de ingeschakelde staat op elk moment toggelen in reactie op app-gebeurtenissen. Een algemeen voorbeeld is het geven van feedback tijdens een langdurige taak:

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

Het uitschakelen van de knop terwijl de taak wordt uitgevoerd voorkomt dubbele indieningen, en het bijwerken van het label houdt de gebruiker geïnformeerd over wat er gebeurt.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

De interface `ComponentLifecycleObserver` laat je componentlevenscyclusgebeurtenissen observeren van buiten de component zelf. Dit is nuttig wanneer je moet reageren op een component die wordt gemaakt of vernietigd zonder de implementatie ervan te wijzigen. Bijvoorbeeld, je zou het kunnen gebruiken om een register van actieve componenten bij te houden of externe bronnen vrij te geven wanneer een component wordt verwijderd.

### Basisgebruik {#basic-usage}

Roep `addLifecycleObserver()` aan op elke component om een callback te registreren. De callback ontvangt de component en de levenscyclegebeurtenis:

```java
Button button = new Button("Bekijk Mij");

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

### Patroon: Resource register {#pattern-resource-registry}

De DESTROY-gebeurtenis is bijzonder nuttig voor het automatisch synchroniseren van een register. In plaats van componenten handmatig te verwijderen wanneer ze niet langer nodig zijn, laat je de component het register zelf informeren:

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

Een coördinator klasse die een set gerelateerde componenten beheert, kan dezelfde aanpak gebruiken om zijn interne lijst nauwkeurig te houden:

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
- Het bouwen van componentregistraties
- Het implementeren van logging of monitoring
- Het coördineren van meerdere componenten
- Het opruimen van externe middelen

Voor het uitvoeren van code nadat een component aan de DOM is bevestigd, zie [`whenAttached()`](/docs/building-ui/composite-components) in de gids voor samenstellende componenten.

## Gebruikersgegevens {#user-data}

Componenten kunnen willekeurige serverzijde gegevens meedragen via `setUserData()` en `getUserData()`. Beide methoden nemen een sleutel om de gegevens te identificeren. Dit is nuttig wanneer je domeinobjecten of context aan een component wilt associëren zonder een aparte opzoekstructuur te beheren.

```java
Button button = new Button("Verwerken");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Aangezien gebruikersgegevens nooit naar de client worden verzonden, kun je gevoelige informatie of grote objecten veilig opslaan zonder de netwerklasten te beïnvloeden.
