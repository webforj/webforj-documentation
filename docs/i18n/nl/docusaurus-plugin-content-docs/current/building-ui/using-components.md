---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 97722c8e3bf6c3129c078d8ae23cf2a4
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Componenten zijn de bouwstenen van webforJ-toepassingen. Of je nu gebruikmaakt van ingebouwde componenten zoals `Button` en `TextField`, of werkt met aangepaste componenten die door jouw team zijn geleverd, de manier waarop je ermee omgaat volgt hetzelfde consistente model: je configureert eigenschappen, beheert de status en stelt componenten samen in lay-outs.

Deze gids richt zich op die dagelijkse handelingen: niet op de internals van hoe componenten werken, maar op hoe je in de praktijk dingen kunt doen met ze.

## Componenteigenschappen {#component-properties}

Elke component biedt eigenschappen die de inhoud, uitstraling en gedrag regelen. De meeste hiervan hebben specifieke, getypte Java-methoden (`setText()`, `setTheme()`, `setExpanse()`, en ga zo maar door), wat de primaire manier is waarop je componenten in webforJ configureert. De onderstaande secties behandelen de eigenschappen en methoden die op brede schaal van toepassing zijn op componenttypen.

### Tekstinhoud {#text-content}

De methode `setText()` stelt de zichtbare tekst van een component in als letterlijke tekens, zoals het bijschrift op een `Button` of de inhoud van een `Label`. Voor invoercomponenten zoals `TextField` gebruik je in plaats daarvan `setValue()` om de huidige waarde van het veld in te stellen.

```java
Button button = new Button();
button.setText("Klik op mij");

Label label = new Label();
label.setText("Status: gereed");

TextField field = new TextField();
field.setValue("Initiële waarde");
```

Markup geschreven met `setText()` verschijnt als die tekens en wordt nooit uitgevoerd, waardoor tekst afkomstig van gebruikersinvoer of externe gegevens niet wordt geïnterpreteerd als live markup.

```java
// Wordt getoond als de letterlijke tekens "<b>Status: gereed</b>"
component.setText("<b>Status: gereed</b>");
```

:::note Gebruik van de `<html>`-tag
Eerdere versies van webforJ beschouwden een waarde omgeven door `<html>` en doorgegeven aan `setText()` als HTML. Dit gedrag is verouderd en zal worden verwijderd in webforJ 27.00.

De eerste keer dat een `<html>`-omgeven waarde `setText()` bereikt, wordt er een waarschuwing gelogd waarin de component en de aanroeplocatie worden genoemd, zodat de aanroep naar `setHtml()` kan worden verplaatst.

Om de standaard van webforJ 27.00 vooraf in te voeren, stel je `webforj.legacyHtmlInText` in op `false`. In een Spring-app wordt dezelfde waarde ingesteld via `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (standaard)
component.setText("<html><b>Status: gereed</b></html>"); // rendert vet

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: gereed</b></html>"); // toont de tekens <b>Status: gereed</b>
```
:::

### HTML renderen {#rendering-html}

Sommige componenten ondersteunen ook `setHtml()` voor gevallen waarin je inline HTML-markup in de inhoud moet renderen:

```java
Div container = new Div();
container.setHtml("<strong>Vetgedrukte tekst</strong> en <em>cursieve tekst</em>");
```

:::danger Cross-site Scripting (XSS)
Als voorzorgsmaatregel tegen [cross-site scripting (XSS)-aanvallen](/docs/security/application-security/common-threats#cross-site-scripting-xss), gebruik `setHtml()` alleen met inhoud die je direct beheert.
:::

### HTML-attributen {#html-attributes}

De meeste configuratie in webforJ gebeurt via getypte Java-methoden in plaats van ruwe HTML-attributen. `setAttribute()` is echter nuttig voor het doorgeven van toegankelijkheidsattributen die geen specifieke API hebben:

```java
Button button = new Button("Verzend");
button.setAttribute("aria-label", "Verzend het formulier");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Controleer de ondersteuning van componenten
Niet alle componenten ondersteunen willekeurige attributen. Dit hangt af van de implementatie van de onderliggende component.
:::

### Component-ID's {#component-ids}

Je kunt een ID toewijzen aan het HTML-element van een component met `setAttribute()`:

```java
Button submitButton = new Button("Verzend");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-mail");
emailField.setAttribute("id", "email-input");
```

DOM-ID's worden vaak gebruikt voor testselectoren en CSS-targeting in je stijlen.

:::tip Geef de voorkeur aan klassen voor targeting van meerdere componenten
In tegenstelling tot CSS-klassen moeten ID's uniek zijn binnen je app. Als je meerdere componenten wilt targeten, gebruik dan `addClassName()` in plaats daarvan.
:::

:::info Door het Framework beheerde ID's
webforJ kent ook automatisch identificaties toe aan componenten inwendig. De serverzijde ID (toegankelijk via `getComponentId()`) wordt gebruikt voor frameworktracking, terwijl de clientzijde ID (toegankelijk via `getClientComponentId()`) wordt gebruikt voor communicatie tussen client en server. Deze zijn gescheiden van het DOM `id`-attribuut dat je instelt met `setAttribute()`.
:::

### Styling {#styling}

Drie methoden dekken de meeste stylingbehoeften: `setStyle()` voor individuele CSS-eigenschapswaarden, en `addClassName()` en `removeClassName()` om CSS-klassen toe te passen of te verwijderen die in je stijlen zijn gedefinieerd. 
Gebruik `setStyle()` voor kleine of eenmalige stijl aanpassingen, en gebruik CSS-klassen om grotere of herbruikbare styling toe te passen.

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

:::note Verouderde aanpak
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) is een verouderde aanpak en wordt over het algemeen niet aanbevolen voor nieuwe projecten. In de meeste gevallen is het beter om je stijlen in gescheiden CSS-bestanden te houden.
:::

## Componentstatus {#component-state}

Naast inhoud en uitstraling hebben componenten status-eigenschappen die bepalen of ze zichtbaar zijn en of ze reageren op gebruikersinteractie. De twee meest gebruikte zijn `setVisible()` en `setEnabled()`.

`setVisible()` regelt of de component überhaupt in de UI wordt weergegeven. `setEnabled()` regelt of het invoer of interactie accepteert terwijl het zichtbaar blijft. In de meeste gevallen is het beter om te deactiveren dan om te verbergen: een uitgeschakelde knop communiceert nog steeds dat er een actie bestaat, maar nog niet beschikbaar is, wat minder ontregelend is dan wanneer deze verschijnt en verdwijnt.

```java
// Toon een extra veld wanneer een checkbox is aangevinkt
TextField advancedField = new TextField("Geavanceerde instelling");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Toon geavanceerde instellingen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Schakel een knop alleen in wanneer het vereiste veld een waarde heeft
Button submitButton = new Button("Verzend");
submitButton.setEnabled(false);

TextField nameField = new TextField("Naam");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Het volgende inlogformulier demonstreert `setEnabled()` in de praktijk. De aanmeldknop blijft uitgeschakeld totdat beide velden een inhoud hebben, waardoor het voor de gebruiker duidelijk is dat invoer vereist is voordat hij verdergaat:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Werken met containers {#working-with-containers}

In webforJ wordt de lay-out afgehandeld door containers, dat zijn componenten die andere componenten vasthouden en regelen hoe ze zijn gerangschikt. Je positioneert kindcomponenten niet handmatig; in plaats daarvan voeg je ze toe aan een container en configureer je de lay-out eigenschappen van die container.

### Componenten toevoegen {#adding-components}

Alle containers bieden een `add()`-methode. Je kunt componenten één voor één of allemaal tegelijk doorgeven:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klik op mij"));

TextField nameField = new TextField("Naam");
TextField emailField = new TextField("E-mail");
Button submitButton = new Button("Verzend");

container.add(nameField, emailField, submitButton);
```

### Lay-outopties {#layout-options}

`FlexLayout` is de primaire lay-outcontainer in webforJ en dekt de meeste gebruiksgevallen: rijen, kolommen, uitlijning, spatiëring en wikkeling. Voor complexere indelingen zoals CSS Grid of aangepaste positionering kun je CSS rechtstreeks toepassen via `setStyle()` of `addClassName()` op elke containercomponent. Zie de [FlexLayout](/docs/components/flex-layout) documentatie voor de volledige reeks lay-outopties.

### Secties tonen en verbergen {#showing-hiding-sections}

Een veelvoorkomende toepassing van `setVisible()` in containers is het onthullen van aanvullende UI alleen wanneer dit relevant is. Dit houdt de interface gefocust en vermindert visuele rommel. In plaats van naar een nieuw overzicht te navigeren, kun je een sectie van de huidige lay-out tonen als directe reactie op gebruikersinvoer.

Het volgende instellingenpaneel demonstreert dit: basis notificatievoorkeuren zijn altijd zichtbaar, en een sectie van geavanceerde opties verschijnt alleen wanneer de gebruiker erom vraagt. De opslaan-knop wordt geactiveerd zodra er enige instelling is gewijzigd:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Containerbeheer {#container-management}

Gebruik `remove()` en `removeAll()` om componenten op runtime uit een container te verwijderen:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tijdelijk");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dit is nuttig wanneer je de inhoud volledig wilt vervangen, zoals het verwisselen van een laadindicator voor de geladen gegevens.

## Formvalidatie {#form-validation}

Het coördineren van meerdere componenten om een verzendactie te bemoeilijken is een van de meest voorkomende patronen in webforJ UI's. Het kernidee is eenvoudig: elk invoerveld registreert een listener, en telkens wanneer een waarde verandert, evalueert het formulier opnieuw of aan alle criteria is voldaan en werkt de verzendknop dienovereenkomstig bij.

Dit is beter dan het tonen van validatiefouten alleen zodra de gebruiker op verzenden klikt, omdat het voortdurende feedback geeft en onnodige indieningen voorkomt. De verzendknop dient als indicator: uitgeschakeld betekent dat het formulier niet klaar is, ingeschakeld betekent dat het dat wel is.

In dit contactformulier mag het naamveld niet leeg zijn, de e-mail moet een `@`-symbool bevatten en het bericht moet minstens 10 tekens lang zijn:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamische inhoudsupdates {#dynamic-content-updates}

Componenten hoeven niet in een vaste staat te blijven nadat ze zijn aangemaakt. Je kunt tekst bijwerken, CSS-classen verwisselen en de ingeschakelde status op elk moment omzetten als reactie op gebeurtenissen in de app. Een veelvoorkomend voorbeeld is het geven van feedback tijdens een langdurige taak:

```java
Label statusLabel = new Label("Klaar");
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

Het uitschakelen van de knop tijdens het uitvoeren van de taak voorkomt dubbele indieningen, en het bijwerken van het label houdt de gebruiker geïnformeerd over wat er gebeurt.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

De interface `ComponentLifecycleObserver` stelt je in staat om gebeurtenissen in de levenscyclus van componenten van buiten de component zelf te observeren. Dit is nuttig wanneer je moet reageren op een component die wordt aangemaakt of verwijderd zonder de implementatie ervan te wijzigen. Je zou dit bijvoorbeeld kunnen gebruiken om een register van actieve componenten bij te houden of externe bronnen vrij te geven wanneer een component wordt verwijderd.

### Basisgebruik {#basic-usage}

Roep `addLifecycleObserver()` aan op elke component om een callback te registreren. De callback ontvangt de component en het levenscycusevenement:

```java
Button button = new Button("Kijk naar mij");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("De knop is aangemaakt");
            break;
        case DESTROY:
            System.out.println("De knop is verwijderd");
            break;
    }
});
```

### Patroon: Hulpbronnenregister {#pattern-resource-registry}

Het DESTROY-evenement is bijzonder nuttig om een register automatisch in sync te houden. In plaats van handmatig componenten te verwijderen wanneer ze niet meer nodig zijn, laat je de component zelf de registratie notificeren:

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

Een coördinatorclass die een reeks gerelateerde componenten beheert, kan dezelfde aanpak gebruiken om zijn interne lijst nauwkeurig te houden:

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
- Logging of monitoring implementeren
- Coördineren van meerdere componenten
- Schoonmaken van externe bronnen

Voor het uitvoeren van code nadat een component aan de DOM is gekoppeld, zie [`whenAttached()`](/docs/building-ui/composing-components) in de gids voor samengestelde componenten.

## Gebruikersdata {#user-data}

Componenten kunnen willekeurige serverzijde gegevens bevatten via `setUserData()` en `getUserData()`. Beide methoden nemen een sleutel om de gegevens te identificeren. Dit is nuttig wanneer je domeinobjecten of context met een component wilt associëren zonder een aparte opzoektstructuur te beheren.

```java
Button button = new Button("Verwerk");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Aangezien gebruikersgegevens nooit naar de client worden verzonden, kun je veilig gevoelige informatie of grote objecten opslaan zonder de netwerkverkeer te beïnvloeden.
