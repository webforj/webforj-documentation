---
title: TextArea
sidebar_position: 130
_i18n_hash: 0ca8e9c1163e55bb86adf44931de139a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

De `TextArea` component in webforJ biedt een oplossing voor meerregelige tekstinvoer. Eindgebruikers kunnen vrijelijk tekst typen en bewerken, terwijl ontwikkelaars redelijke grenzen kunnen instellen met functies zoals maximale tekenlimieten, alinea-indeling en validatieregels.

Hier is een voorbeeld van een `TextArea` voor het invoeren van meerregelige tekst:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Beheren van alinea's {#managing-paragraphs}

De `TextArea` component biedt functies voor het omgaan met tekstalinea's, waardoor het ideaal is voor toepassingen die documentbewerking of gestructureerde tekstinvoer vereisen.

Hier is een snel voorbeeld van hoe je alinea-inhoud kunt opbouwen en manipuleren:

```java
TextArea textArea = new TextArea();

// Voeg een alinea toe aan het begin
textArea.addParagraph(0, "Dit is de eerste alinea.");

// Voeg een andere alinea toe aan het einde
textArea.addParagraph("Hier is een tweede alinea.");

// Voeg extra inhoud toe aan de eerste alinea
textArea.appendToParagraph(0, " Deze zin gaat verder met de eerste.");

// Verwijder de tweede alinea
textArea.removeParagraph(1);

// Haal alle huidige alinea's op en print ze
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("Alinea " + i + ": " + paragraphs.get(i));
}
```

## Validatie {#validation}

De `TextArea` component ondersteunt twee complementaire types van validatie: structurele beperkingen en inhoudelijke beperkingen.

**Structurele beperkingen** richten zich op hoe de tekst is georganiseerd en visueel is weergegeven. Bijvoorbeeld:
- `setLineCountLimit(int maxLines)` beperkt het aantal regels dat is toegestaan in het tekstvak.
- `setParagraphLengthLimit(int maxCharsPerLine)` beperkt het aantal tekens per alinea (of regel), wat helpt bij het handhaven van leesbaarheid of opmaakstandaarden.

**Inhoudelijke beperkingen** daarentegen, hebben betrekking op de totale hoeveelheid tekst die is ingevoerd, ongeacht hoe deze is verdeeld:
- `setMaxLength(int maxChars)` plaatst een limiet op het totale aantal tekens dat is toegestaan in alle alinea's.
- `setMinLength(int minChars)` handhaaft een minimale lengte, waardoor ervoor wordt gezorgd dat voldoende inhoud wordt geleverd.

De onderstaande demo laat gebruikers toe om validatielimieten aan te passen—zoals maximale tekenlimiet, alinea lengte en aantal regels—in real-time en te zien hoe de `TextArea` hierop reageert.
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Woordwrapping en regelafbreking {#word-wrap-and-line-wrapping}

Je kunt bepalen of tekst omgebroken of horizontaal gescrold wordt met `setLineWrap()`. Wanneer ombreking is uitgeschakeld, gaan regels horizontaal verder dan het zichtbare gebied, wat scrollen vereist. Wanneer het is ingeschakeld, wordt tekst automatisch omgebroken naar de volgende regel wanneer het de rand van de component bereikt.

Om verder te verfijnen hoe ombreking werkt, laat `setWrapStyle()` je kiezen tussen twee stijlen:
- `WORD_BOUNDARIES` breekt tekst bij hele woorden, waardoor de natuurlijke leesstroom behouden blijft.
- `CHARACTER_BOUNDARIES` breekt bij afzonderlijke tekens, wat meer controle over de lay-out mogelijk maakt, vooral in smalle of vaste containers.

Deze ombrekingsopties werken hand in hand met structurele beperkingen zoals het aantal regels en alinea limieten. Terwijl ombreking bepaalt *hoe* tekst stroomt binnen de beschikbare ruimte, definiëren de structurele limieten *hoeveel* ruimte tekst mag innemen. Samen helpen ze zowel visuele structuur als gebruikersinvoeren grenzen te behouden.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Voorspelde tekst {#predicted-text}

De `TextArea` component ondersteunt slimme tekstsuggesties om gebruikers te helpen sneller te typen en met minder fouten. Terwijl gebruikers tekst invoeren, verschijnen voorspellende suggesties op basis van de huidige invoer, waardoor ze veelvoorkomende of verwachte zinnen kunnen aanvullen.

Voorspellingen kunnen worden geaccepteerd door de `Tab` of `ArrowRight` toets in te drukken, waarbij de voorgestelde tekst naadloos in de invoer wordt ingevoegd. Als er op een bepaald moment geen geschikte voorspelling beschikbaar is, blijft de invoer onveranderd en kan de gebruiker doorgaan met typen zonder onderbreking—wat ervoor zorgt dat de functie nooit in de weg zit.

Dit voorspellende gedrag verbetert zowel de snelheid als de nauwkeurigheid, vooral in herhalende invoerscenario's of toepassingen waar consistentie in zinsbouw belangrijk is.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Deze demo maakt gebruik van de [Datamuse API](https://datamuse.com/) om woordsuggesties te bieden op basis van de invoer van de gebruiker. De kwaliteit en relevantie van de voorspellingen zijn geheel afhankelijk van de dataset en het beoordelingsmechanisme van de API. Er worden geen AI-modellen of grote taalmodellen (LLM's) gebruikt; de suggesties worden gegenereerd vanuit een lichtgewicht, regelgebaseerde engine die zich richt op lexicale similariteit.
:::

## Alleen-lezen en uitgeschakelde staat {#read-only-and-disabled-state}

De `TextArea` component kan worden ingesteld op alleen-lezen of uitgeschakeld om gebruikersinteracties te controleren.

Een **alleen-lezen** tekstvak stelt gebruikers in staat de inhoud te bekijken en te selecteren, maar niet te bewerken. Dit is nuttig voor het weergeven van dynamische of vooraf ingevulde informatie die ongewijzigd moet blijven.

Een **uitgeschakeld** tekstvak daarentegen, blokkeert alle interactie—including focus en tekstselectie—en is meestal gestileerd als inactief of grijs gemaakt.

Gebruik de alleen-lezen modus wanneer de inhoud relevant maar onveranderlijk is, en de uitgeschakelde modus wanneer de invoer momenteel niet van toepassing is of tijdelijk inactief moet zijn.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
