---
title: TextArea
sidebar_position: 130
_i18n_hash: e8956f1a5bf39eab9a42244ff8d5ff21
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

De `TextArea` component biedt een meerregel-inputveld waar gebruikers langere tekstblokken kunnen typen en bewerken. Het ondersteunt maximum aantal karakters, paragraafstructuur, regelomsluiting en validatieregels om te controleren hoe input wordt behandeld.

<!-- INTRO_END -->

## Het creëren van een `TextArea` {#creating-a-textarea}

Maak een `TextArea` aan door een label door te geven aan de constructor. Eigenschappen zoals placeholder-tekst, karakterlimieten en omsluitgedrag kunnen worden geconfigureerd via setter-methoden.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Beheren van paragrafen {#managing-paragraphs}

De `TextArea` component biedt functies voor het verwerken van tekstparagrafen, waardoor het ideaal is voor toepassingen die documentbewerking of gestructureerde tekstinvoer vereisen.

Hier is een snel voorbeeld van hoe je paragraafinhoud kunt opbouwen en manipuleren:

```java
TextArea textArea = new TextArea();

// Voeg een alinea toe aan het begin
textArea.addParagraph(0, "Dit is de eerste alinea.");

// Voeg een andere alinea toe aan het einde
textArea.addParagraph("Hier is een tweede alinea.");

// Voeg extra inhoud toe aan de eerste alinea
textArea.appendToParagraph(0, " Deze zin vervolgt de eerste.");

// Verwijder de tweede alinea
textArea.removeParagraph(1);

// Haal alle huidige alinea's op en print ze
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Alinea " + i + ": " + paragraphs.get(i));
}
```

## Validatie {#validation}

De `TextArea` component ondersteunt twee complementaire types van validatie: structurele beperkingen en inhoudsbeperkingen.

**Structurele beperkingen** richten zich op hoe de tekst is georganiseerd en visueel is gerangschikt. Bijvoorbeeld:
- `setLineCountLimit(int maxLines)` beperkt het aantal lijnen dat is toegestaan in het tekstgebied.
- `setParagraphLengthLimit(int maxCharsPerLine)` beperkt het aantal karakters per alinea (of lijn), om de leesbaarheid of opmaakstandaarden te handhaven.

**Inhoudsbeperkingen** zijn daarentegen gericht op de totale hoeveelheid tekst die wordt ingevoerd, ongeacht hoe deze is verdeeld:
- `setMaxLength(int maxChars)` beperkt het totale aantal toegestane karakters over alle alinea's.
- `setMinLength(int minChars)` handhaaft een minimale lengte, zodat er voldoende inhoud wordt gegeven.

De volgende demo stelt gebruikers in staat om validatielimieten aan te passen—zoals de maximale tekenlimiet, alenlengte en aantal lijnen—in real-time en te zien hoe de `TextArea` hierop reageert.
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Woordomslag en regelomsluiting {#word-wrap-and-line-wrapping}

Je kunt regelen of tekst omwringt of horizontaal scrolt met `setLineWrap()`. Wanneer omsluiting is uitgeschakeld, verlengen lijnen horizontaal voorbij het zichtbare gebied, wat scrollen vereist. Wanneer ingeschakeld, wordt tekst automatisch omgevouwen naar de volgende regel wanneer het de rand van de component bereikt.

Om verder te verfijnen hoe omsluiting werkt, laat `setWrapStyle()` je kiezen tussen twee stijlen:
- `WORD_BOUNDARIES` vouwt tekst bij hele woorden om, waardoor de natuurlijke leesstroom behouden blijft.
- `CHARACTER_BOUNDARIES` vouwt om bij individuele karakters, wat een strakker controle over de lay-out mogelijk maakt, vooral in smalle of vaste breedte containers.

Deze omsluitopties werken hand in hand met structurele beperkingen zoals het aantal lijnen en limieten voor alenlengte. Terwijl omsluiting bepaalt *hoe* tekst stroomt binnen de beschikbare ruimte, definiëren de structurele limieten *hoeveel* ruimte tekst mag innemen. Samen helpen ze zowel de visuele structuur als de grenzen van gebruikersinvoer te handhaven.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Voorspelde tekst {#predicted-text}

De `TextArea` component ondersteunt slimme tekstsuggesties om gebruikers te helpen sneller te typen met minder fouten. Terwijl gebruikers tekst invoeren, verschijnen voorspellende suggesties op basis van de huidige invoer, zodat ze veelvoorkomende of verwachte zinnen kunnen voltooien.

Voorspellingen kunnen worden geaccepteerd door de `Tab` of `PijlRechts` toets in te drukken, waardoor de voorgestelde tekst naadloos in de invoer wordt ingevoegd. Als er op dat moment geen geschikte voorspelling beschikbaar is, blijft de invoer onveranderd, en kan de gebruiker zonder onderbreking blijven typen—wat ervoor zorgt dat de functie nooit in de weg staat.

Dit voorspellende gedrag verbetert zowel de snelheid als de nauwkeurigheid, vooral in repetitieve invoerscenario's of toepassingen waar consistentie van zinsopbouw belangrijk is.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
cssURL='/css/textarea/text-area-predicted-text-view.css'
height = '400px'
/>

:::info
Deze demo maakt gebruik van de [Datamuse API](https://datamuse.com/) om woordensuggesties te geven op basis van de invoer van de gebruiker. De kwaliteit en relevantie van de voorspellingen zijn volledig afhankelijk van de dataset en het beoordelingsmechanisme van de API. Het maakt geen gebruik van AI-modellen of grote taalmodellen (LLM's); de suggesties worden gegenereerd vanuit een lichtgewicht, op regels gebaseerd systeem dat zich richt op lexicale overeenkomst.
:::

## Alleen-lezen en uitgeschakelde staat {#read-only-and-disabled-state}

De `TextArea` component kan worden ingesteld op alleen-lezen of uitgeschakeld om de interactie van de gebruiker te regelen.

Een **alleen-lezen** tekstgebied stelt gebruikers in staat om de inhoud te bekijken en te selecteren, maar niet te bewerken. Dit is nuttig om dynamische of vooraf ingevulde informatie weer te geven die ongewijzigd moet blijven.

Een **uitgeschakeld** tekstgebied blokkeert daarentegen alle interactie—waaronder focus en tekstselectie—en wordt meestal gestyled als inactief of grijs.

Gebruik de alleen-lezen modus wanneer de inhoud relevant maar onveranderlijk is, en de uitgeschakelde modus wanneer de invoer momenteel niet van toepassing is of tijdelijk inactief moet zijn.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Stijling {#styling}

<TableBuilder name="TextArea" />
