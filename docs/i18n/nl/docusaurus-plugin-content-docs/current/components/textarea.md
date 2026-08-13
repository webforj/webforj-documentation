---
title: TextArea
sidebar_position: 130
description: >-
  Capture multi-line input with the TextArea component, including paragraph
  management, character limits, wrapping, and validation.
_i18n_hash: f9863352a124e1af3575a849204b97ed
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

De `TextArea` component biedt een meerregelige tekstinvoerveld waar gebruikers langere tekstblokken kunnen typen en bewerken. Het ondersteunt maximale tekenlimieten, paragraafstructuur, regelomsluiting en validatieregels om te controleren hoe invoer wordt behandeld.

<!-- INTRO_END -->

## Een `TextArea` maken {#creating-a-textarea}

Maak een `TextArea` door een label naar de constructor door te geven. Eigenschappen zoals placeholder-tekst, tekenlimieten en omgedraaid gedrag kunnen worden geconfigureerd via settermethoden.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Beheren van paragrafen {#managing-paragraphs}

De `TextArea` component biedt functies voor het omgaan met tekstparagrafen, waardoor het ideaal is voor toepassingen die documentbewerking of gestructureerde tekstinvoer vereisen.

Hier is een snel voorbeeld van hoe je paragrafeninformatie kunt opbouwen en manipuleren:

```java
TextArea textArea = new TextArea();

// Voeg een paragraaf toe aan het begin
textArea.addParagraph(0, "Dit is de eerste paragraaf.");

// Voeg een andere paragraaf toe aan het einde
textArea.addParagraph("Hier is een tweede paragraaf.");

// Voeg extra inhoud toe aan de eerste paragraaf
textArea.appendToParagraph(0, " Deze zin gaat verder met de eerste.");

// Verwijder de tweede paragraaf
textArea.removeParagraph(1);

// Haal alle huidige paragrafen op en print ze
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Paragraaf " + i + ": " + paragraphs.get(i));
}
```

## Validatie {#validation}

De `TextArea` component ondersteunt twee complementaire soorten validatie: structurele beperkingen en inhoudsbeperkingen.

**Structurele beperkingen** richten zich op hoe de tekst is georganiseerd en visueel is ingedeeld. Bijvoorbeeld:
- `setLineCountLimit(int maxLines)` beperkt het aantal regels dat is toegestaan in de tekstgebied.
- `setParagraphLengthLimit(int maxCharsPerLine)` beperkt het aantal tekens per paragraaf (of regel), wat helpt om leesbaarheid of opmaakstandaarden af te dwingen.

**Inhoudsbeperkingen**, aan de andere kant, hebben betrekking op de totale hoeveelheid tekst die is ingevoerd, ongeacht hoe deze is verdeeld:
- `setMaxLength(int maxChars)` beperkt het totale aantal tekens dat is toegestaan over alle paragrafen.
- `setMinLength(int minChars)` handhaaft een minimumlengte, waarmee wordt verzekerd dat er voldoende inhoud wordt geboden.

De volgende demo maakt het mogelijk voor gebruikers om validatielimieten aan te passen - zoals het maximale aantal tekens, de paragraaflengte en het aantal regels - in realtime en te zien hoe de `TextArea` reageert.

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Woordomslag en regelomsluiting {#word-wrap-and-line-wrapping}

Je kunt bepalen of tekst omgeslagen of horizontaal gescrold wordt met `setLineWrap()`. Wanneer omsluiting is uitgeschakeld, gaan regels horizontaal verder dan het zichtbare gebied, wat scrollen vereist. Wanneer ingeschakeld, omslaat tekst automatisch naar de volgende regel wanneer het de rand van de component bereikt.

Om verder te verfijnen hoe omsluiting functioneert, laat `setWrapStyle()` je kiezen tussen twee stijlen:
- `WORD_BOUNDARIES` omslaat tekst op hele woorden, wat de natuurlijke leesvolgorde behoudt.
- `CHARACTER_BOUNDARIES` omslaat op individuele karakters, wat strakkere controle over de lay-out mogelijk maakt, vooral in smalle of vaste containers.

Deze omslagsopties werken hand in hand met structurele beperkingen zoals het aantal regels en de lengtelimieten van paragrafen. Terwijl omsluiting bepaalt *hoe* tekst stroomt binnen de beschikbare ruimte, definiëren de structurele limieten *hoeveel* ruimte tekst mag innemen. Samen helpen ze zowel visuele structuur als gebruikersinvoeren grenzen te handhaven.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Voorspelde tekst {#predicted-text}

De `TextArea` component ondersteunt slimme tekstsuggesties om gebruikers te helpen sneller en met minder fouten te typen. Terwijl gebruikers tekst invoeren, verschijnen voorspellende suggesties op basis van de huidige invoer, waardoor ze veelvoorkomende of verwachte zinnen kunnen aanvullen.

Voorspellingen kunnen worden geaccepteerd door de `Tab` of `ArrowRight` toets in te drukken, waardoor de voorgestelde tekst naadloos in de invoer wordt ingevoegd. Als er op een bepaald moment geen geschikte voorspelling beschikbaar is, blijft de invoer onveranderd en kan de gebruiker zonder onderbreking doorgaan met typen, waardoor wordt verzekerd dat de functie nooit in de weg staat.

Dit voorspellende gedrag verbetert zowel de snelheid als de nauwkeurigheid, vooral in scenario's met herhalende invoer of toepassingen waar consistentie van formulering belangrijk is.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/frontend/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Deze demo maakt gebruik van de [Datamuse API](https://datamuse.com/) om woordsuggesties te bieden op basis van de invoer van de gebruiker. De kwaliteit en relevantie van de voorspellingen zijn volledig afhankelijk van de dataset en het scoringsmechanisme van de API. Het gebruikt geen AI-modellen of grote taalmodellen (LLM's); de suggesties worden gegenereerd door een lichte, op regels gebaseerde motor die gericht is op lexicale similariteit.
:::

## Alleen-lezen en Uitgeschakelde status {#read-only-and-disabled-state}

De `TextArea` component kan worden ingesteld op alleen-lezen of uitgeschakeld om de interactie van de gebruiker te controleren.

Een **alleen-lezen** tekstgebied stelt gebruikers in staat om de inhoud te bekijken en te selecteren, maar niet te bewerken. Dit is nuttig voor het weergeven van dynamische of vooraf ingevulde informatie die ongewijzigd moet blijven.

Een **uitgeschakeld** tekstgebied blokkeert daarentegen alle interactie - inclusief focus en tekstselectie - en wordt doorgaans gestyled als inactief of grijs.

Gebruik de alleen-lezen modus wanneer de inhoud relevant maar onveranderlijk is, en de uitgeschakelde modus wanneer de invoer momenteel niet van toepassing is of tijdelijk inactief moet zijn.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Stylen {#styling}

<TableBuilder name="TextArea" />
