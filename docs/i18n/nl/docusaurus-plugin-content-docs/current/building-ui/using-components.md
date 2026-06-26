---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: fb67c93e2165a651245a703c772d3bcb
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Componenten zijn de bouwblokken van webforJ-toepassingen. Of je nu gebruikmaakt van ingebouwde componenten zoals `Button` en `TextField`, of werkt met aangepaste componenten die door jouw team zijn geleverd, de manier waarop je ermee omgaat volgt hetzelfde consistente model: je configureert eigenschappen, beheert de status en stelt componenten samen in indelingen.

Deze gids richt zich op die dagelijkse handelingen: niet op de interne werking van componenten, maar op hoe je in de praktijk dingen kunt doen met ze.

## Componenteigenschappen {#component-properties}

Elke component exposeert eigenschappen die de inhoud, het uiterlijk en het gedrag ervan controleren. De meeste hiervan hebben specifieke, getypeerde Java-methoden (`setText()`, `setTheme()`, `setExpanse()`, enzovoort), wat de primaire manier is waarop je componenten in webforJ configureert. De onderstaande secties behandelen de eigenschappen en methoden die breed van toepassing zijn op componenttypen.

### Tekstinhoud {#text-content}

De methode `setText()` stelt de zichtbare tekst van een component in als letterlijke karakters, zoals het bijschrift op een `Button` of de inhoud van een `Label`. Voor invoercomponenten zoals `TextField` gebruik je in plaats daarvan `setValue()` om de huidige waarde van het veld in te stellen.

```java
Button button = new Button();
button.setText("Klik op mij");

Label label = new Label();
label.setText("Status: gereed");

TextField field = new TextField();
field.setValue("Initiële waarde");
```

Markup geschreven met `setText()` verschijnt als die karakters en wordt nooit uitgevoerd, waardoor tekst die afkomstig is van gebruikersinvoer of externe gegevens niet als levende markup wordt geïnterpreteerd.

```java
// Weergegeven als de letterlijke karakters "<b>Status: gereed</b>"
component.setText("<b>Status: gereed</b>");
```

:::note Gebruik van de `<html>`-tag
Eerdere versies van webforJ behandelden een waarde die verpakt was in `<html>` en naar `setText()` werd gestuurd als HTML. Dit gedrag is verouderd en zal worden verwijderd in webforJ 27.00.

De eerste keer dat een `<html>` verpakte waarde `setText()` bereikt, wordt er een waarschuwing gelogd die de component en de aanroepplaats benoemt, zodat de aanroep kan worden verplaatst naar `setHtml()`.

Om de standaard van webforJ 27.00 vooraf te omarmen, stel je `webforj.legacyHtmlInText` in op `false`. In een Spring-app wordt dezelfde waarde ingesteld via `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (standaard)
component.setText("<html><b>Status: gereed</b></html>"); // toont vetgedrukt

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: gereed</b></html>"); // toont de karakters <b>Status: gereed</b>
```
:::

### HTML-rendering {#rendering-html}

Sommige componenten ondersteunen ook `setHtml()` voor gevallen waarin je inline HTML-markup in de inhoud moet weergeven:

```java
Div container = new Div();
container.setHtml("<strong>Vette tekst</strong> en <em>cursieve tekst</em>");
```

:::danger Cross-site Scripting (XSS)
Als voorzorgsmaatregel tegen [cross-site scripting (XSS)-aanvallen](/docs/security/application-security/common-threats#cross-site-scripting-xss), gebruik `setHtml()` alleen met inhoud die je direct beheert.
:::

### HTML-attributen {#html-attributes}

De meeste configuratie in webforJ gebeurt via getypeerde Java-methoden in plaats van ruwe HTML-attributen. `setAttribute()` is echter nuttig voor het doorgeven van toegankelijkheidsattributen die geen specifieke API hebben:

```java
Button button = new Button("Verzenden");
button.setAttribute("aria-label", "Verzend het formulier");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Controleer de ondersteuning van componenten
Niet alle componenten ondersteunen willekeurige attributen. Dit hangt af van de onderliggende componentimplementatie.
:::

### Component-ID's {#component-ids}

Je kunt een ID toewijzen aan het HTML-element van een component met behulp van `setAttribute()`:

```java
Button submitButton = new Button("Verzenden");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-mail");
emailField.setAttribute("id", "email-input");
```

DOM-ID's worden vaak gebruikt voor testselectoren en CSS-targeting in je stijlen.

:::tip Geef de voorkeur aan klassen voor meervoudige component-targeting
In tegenstelling tot CSS-klassen moeten ID's uniek zijn binnen je app. Als je meerdere componenten wilt targeten, gebruik dan in plaats daarvan `addClassName()`.
:::

:::info Door het kader beheerde ID's
webforJ wijst ook automatische identificatoren toe aan componenten intern. De serverzijde-ID (toegankelijk via `getComponentId()`) wordt gebruikt voor frame-werktracking, terwijl de clientzijde-ID (toegankelijk via `getClientComponentId()`) wordt gebruikt voor client-servercommunicatie. Deze zijn gescheiden van het DOM `id`-attribuut dat je instelt met `setAttribute()`.
:::

### Stijlen {#styling}

Drie methoden dekken de meeste stijlingbehoeften: `setStyle()` voor individuele CSS-eigenschap waarden en `addClassName()` en `removeClassName()` voor het toepassen of verwijderen van CSS-klassen die in je stijlen zijn gedefinieerd. Gebruik `setStyle()` voor kleinere of eenmalige stijlaanpassingen, en gebruik CSS-klassen om grotere of herbruikbare stijlen toe te passen.

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

Naast inhoud en uiterlijk hebben componenten status-eigenschappen die bepalen of ze zichtbaar zijn en of ze reageren op gebruikersinteractie. De twee meest gebruikte zijn `setVisible()` en `setEnabled()`.

`setVisible()` bepaalt of de component in de UI wordt weergegeven. `setEnabled()` bepaalt of deze invoer of interactie accepteert terwijl hij zichtbaar blijft. In de meeste gevallen is het voordeliger om te verbergen in plaats van uit te schakelen: een uitgeschakelde knop communiceert nog steeds dat een actie bestaat, maar momenteel niet beschikbaar is, wat minder verwarrend is dan dat deze verschijnt en verdwijnt.

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
`setVisible(false)` en `setEnabled(false)` beïnvloeden alleen de UI. Ze stoppen niet dat een vastberaden gebruiker de onderliggende actie via de browser of een zorgvuldig verzoek oproept, dus vertrouw nooit op hen om gevoelige operaties te beschermen. Zorg altijd voor toegangscontrole aan de serverzijde. Zie [Uitgeschakeld en verborgen zijn geen beveiliging](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) voor meer details.
:::

De onderstaande inlogformulierdemonstratie toont `setEnabled()` in de praktijk. De aanmeldknop blijft uitgeschakeld totdat beide velden inhoud hebben, wat duidelijk maakt voor de gebruiker dat invoer vereist is voordat hij verdergaat:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Werken met containers {#working-with-containers}

In webforJ wordt indeling behandeld door containers, dit zijn componenten die andere componenten bevatten en regelen hoe zij zijn gerangschikt. Je positioneert kindcomponenten niet handmatig; in plaats daarvan voeg je ze toe aan een container en configureer je de lay-out eigenschappen van die container.

### Componenten toevoegen {#adding-components}

Alle containers bieden een `add()`-methode. Je kunt componenten één voor één of allemaal tegelijk doorgeven:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klik op mij"));

TextField nameField = new TextField("Naam");
TextField emailField = new TextField("E-mail");
Button submitButton = new Button("Verzenden");

container.add(nameField, emailField, submitButton);
```

### Indelingsopties {#layout-options}

`FlexLayout` is de primaire lay-outcontainer in webforJ en dekt de meeste gebruiksscenario's: rijen, kolommen, uitlijning, spatiëring en omwikkeling. Voor complexere indelingen zoals CSS Grid of aangepaste positionering kun je CSS direct toepassen via `setStyle()` of `addClassName()` op elke containercomponent. Zie de [FlexLayout](/docs/components/flex-layout) documentatie voor het volledige scala aan indelingsopties.

### Secties weergeven en verbergen {#showing-hiding-sections}

Een veelvoorkomende toepassing van `setVisible()` in containers is het onthullen van extra UI alleen wanneer dit relevant is. Dit houdt de interface gericht en vermindert visuele rommel. In plaats van naar een nieuw venster te navigeren, kun je een sectie van de huidige lay-out direct als reactie op gebruikersinvoer weergeven.

Paneel instellingen toont dit: basisnotificatievoorkeuren zijn altijd zichtbaar, en een sectie met geavanceerde opties verschijnt alleen wanneer de gebruiker daarom vraagt. De opslaan-knop wordt geactiveerd zodra een instelling is gewijzigd:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
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

Dit is nuttig wanneer je inhoud volledig moet vervangen, zoals het verwisselen van een laadindicator voor de geladen gegevens.

## Formuliervalidatie {#form-validation}

Het coördineren van meerdere componenten om een verzendactie te onderbreken is een veelvoorkomend patroon in webforJ UI's. Het basisidee is dat elk invoerveld een listener registreert, en wanneer een waarde verandert, evalueert het formulier opnieuw of aan alle criteria is voldaan en werkt de verzendknop dienovereenkomstig bij.

Het onderstaande voorbeeld koppelt dit handmatig zodat je kunt zien hoe de componentstatus en gebeurtenislislisteners samenwerken. Dit is niet de aanbevolen aanpak voor echte formulieren: handmatige listenerlogica wordt moeilijk te onderhouden naarmate formulieren groeien, en het verbind je componenten niet met een onderliggend datamodel.

:::tip Gebruik databinding voor formuliervalidatie
Gebruik voor productievormen [databinding](/docs/data-binding/overview). Het dekt validatie, bidirectionele synchronisatie tussen componenten en je model, en waarde-transformatie via `BindingContext`. Het handmatige patroon dat hier wordt getoond, is alleen ter illustratie.
:::

In dit contactformulier mag het naamveld niet leeg zijn, moet de e-mail een `@`-symbool bevatten, en moet het bericht minimaal 10 tekens lang zijn:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamische inhoudupdates {#dynamic-content-updates}

Componenten hoeven na hun creatie niet in een vaste staat te blijven. Je kunt op elk moment tekst bijwerken, CSS-klassen verwisselen en de ingeschakelde status togglen als reactie op gebeurtenissen in de app. Een veelvoorkomend voorbeeld is het geven van feedback tijdens een langdurige taak:

```java
Label statusLabel = new Label("Klaar");
Button startButton = new Button("Start Proces");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Bezig met verwerken...");
    statusLabel.addClassName("verwerking");
    
    performTask(() -> {
        statusLabel.setText("Voltooid");
        statusLabel.removeClassName("verwerking");
        statusLabel.addClassName("succes");
        startButton.setEnabled(true);
    });
});
```

Het uitschakelen van de knop terwijl de taak draait voorkomt dubbele indieningen, en het bijwerken van het label houdt de gebruiker op de hoogte van wat er gebeurt.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

De interface `ComponentLifecycleObserver` stelt je in staat om componentlevenscyclus evenementen van buiten de component zelf te observeren. Dit is nuttig wanneer je moet reageren op een component die is gemaakt of verwijderd zonder de implementatie ervan te wijzigen. Je zou het bijvoorbeeld kunnen gebruiken om een register van actieve componenten bij te houden of externe middelen vrij te geven wanneer een component wordt verwijderd.

### Basisgebruik {#basic-usage}

Roep `addLifecycleObserver()` aan op een component om een callback te registreren. De callback ontvangt de component en het levenscycusevenement:

```java
Button button = new Button("Kijk naar mij");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Knop is gemaakt");
            break;
        case DESTROY:
            System.out.println("Knop is verwijderd");
            break;
    }
});
```

### Patroon: Middelenregister {#pattern-resource-registry}

Het DESTROY-evenement is bijzonder nuttig om een register automatisch synchroon te houden. In plaats van componenten handmatig te verwijderen wanneer ze niet meer nodig zijn, laat je de component zelf het register op de hoogte stellen:

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

Een coördinatorklasse die een set gerelateerde componenten beheert kan dezelfde aanpak gebruiken om zijn interne lijst nauwkeurig te houden:

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
- Het bouwen van component registers
- Het implementeren van logging of monitoring
- Coördineren van meerdere componenten
- Opruimen van externe middelen

Voor het uitvoeren van code nadat een component aan de DOM is toegevoegd, zie `whenAttached()` in de [Composing Components](/docs/building-ui/composing-components) gids.

## Gebruikersgegevens {#user-data}

Componenten kunnen willekeurige serverzijde gegevens bevatten via `setUserData()` en `getUserData()`. Beide methoden nemen een sleutel om de gegevens te identificeren. Dit is nuttig wanneer je domeinobjecten of context aan een component wilt associëren zonder een aparte opzoekstructuur te beheren.

```java
Button button = new Button("Verwerken");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Aangezien gebruikersgegevens nooit naar de client worden verzonden, kun je veilig gevoelige informatie of grote objecten opslaan zonder de netwerklast te beïnvloeden.
