---
title: TextArea
sidebar_position: 130
_i18n_hash: f109f006fcd252bf81b6cccb83d38a50
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

De `TextArea`-component in webforJ biedt een oplossing voor invoer van meerregels tekst. Eindgebruikers kunnen vrij typen en tekst bewerken, terwijl ontwikkelaars redelijke grenzen kunnen stellen met functies zoals maximaal aantal tekens, paragraafstructuur en validatieregels.

Hier is een voorbeeld van een `TextArea` voor het invoeren van meerregels tekst:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Beheren van paragrafen {#managing-paragraphs}

De `TextArea`-component biedt functies voor het omgaan met tekstparagrafen, waardoor het ideaal is voor toepassingen die documentbewerkingen of gestructureerde tekstinvoer vereisen.

Hier is een snel voorbeeld van hoe u paragraafinhoud kunt opbouwen en manipuleren:

```java
TextArea textArea = new TextArea();

// Voeg een paragraaf aan het begin toe
textArea.addParagraph(0, "Dit is de eerste paragraaf.");

// Voeg een andere paragraaf aan het einde toe
textArea.addParagraph("Hier is een tweede paragraaf.");

// Voeg extra inhoud toe aan de eerste paragraaf
textArea.appendToParagraph(0, " Deze zin vervolgt de eerste.");

// Verwijder de tweede paragraaf
textArea.removeParagraph(1);

// Haal alle huidige paragrafen op en print ze
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("Paragraaf " + i + ": " + paragraphs.get(i));
}
```

## Validatie {#validation}

De `TextArea`-component ondersteunt twee aanvullende soorten validatie: structurele beperkingen en inhoudelijke beperkingen.

**Structurele beperkingen** richten zich op hoe de tekst is georganiseerd en visueel is ingedeeld. Bijvoorbeeld:
- `setLineCountLimit(int maxLines)` beperkt het aantal regels dat is toegestaan in het tekstgebied.
- `setParagraphLengthLimit(int maxCharsPerLine)` beperkt het aantal tekens per paragraaf (of regel), wat helpt om leesbaarheid of opmaakstandaarden te handhaven.

**Inhoudelijke beperkingen** hebben daarentegen betrekking op de totale hoeveelheid tekst die is ingevoerd, ongeacht hoe deze is verdeeld:
- `setMaxLength(int maxChars)` stelt een maximum aantal tekens vast dat is toegestaan over alle paragrafen.
- `setMinLength(int minChars)` handhaaft een minimumlengte, zodat er voldoende inhoud wordt geleverd.

De volgende demo stelt gebruikers in staat om validatiegrenzen aan te passen—zoals maximaal aantal tekens, paragraaflengte en regel aantal—in realtime en te zien hoe de `TextArea` reageert.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Woordomsluiting en Regeloмsluiting {#word-wrap-and-line-wrapping}

U kunt controleren of tekst omgevouwen of horizontaal scrolt met behulp van `setLineWrap()`. Wanneer omvouwen is uitgeschakeld, blijven regels horizontaal doorgaan buiten het zichtbare gebied, wat scrollen vereist. Wanneer het is ingeschakeld, wordt tekst automatisch omgevouwen naar de volgende regel wanneer deze de rand van de component bereikt.

Om verder te verfijnen hoe omvouwen werkt, laat `setWrapStyle()` u kiezen tussen twee stijlen:
- `WORD_BOUNDARIES` vouwt tekst bij hele woorden om, waardoor de natuurlijke leesflow wordt behouden.
- `CHARACTER_BOUNDARIES` vouwt bij individuele tekens om, wat een strakkere controle over het ontwerp mogelijk maakt, vooral in smalle of vaste breedte containers.

Deze omvouwopties werken hand in hand met structurele beperkingen zoals lijn- en paragraaflengte-limieten. Terwijl omvouwen bepaalt *hoe* tekst binnen de beschikbare ruimte stroomt, definiëren de structurele limieten *hoeveel* ruimte tekst mag innemen. Samen helpen ze zowel visuele structuur als grenzen van gebruikersinvoer te behouden.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Voorspelde tekst {#predicted-text}

De `TextArea`-component ondersteunt slimme tekstsuggesties om gebruikers te helpen sneller en met minder fouten te typen. Terwijl gebruikers tekst invoeren, verschijnen voorspellende suggesties op basis van de huidige invoer, waardoor ze veelvoorkomende of verwachte zinnen kunnen voltooien.

Voorspellingen kunnen worden geaccepteerd door op de `Tab`- of `ArrowRight`-toets te drukken, waardoor de voorgestelde tekst naadloos in de invoer wordt geplaatst. Als er op dat moment geen geschikte voorspelling beschikbaar is, blijft de invoer onveranderd en kan de gebruiker doorgaan met typen zonder onderbreking—wat ervoor zorgt dat de functie nooit in de weg staat.

Dit voorspellende gedrag verbetert zowel de snelheid als de nauwkeurigheid, vooral in repetitieve invoerscenario's of toepassingen waar consistentie van formulering belangrijk is.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Deze demo maakt gebruik van de [Datamuse API](https://datamuse.com/) om woord suggesties te bieden op basis van de invoer van de gebruiker. De kwaliteit en relevantie van de voorspellingen zijn volledig afhankelijk van de dataset en scoringmechanisme van de API. Het maakt geen gebruik van AI-modellen of grote taalmodellen (LLM); de suggesties worden gegenereerd vanuit een lichtgewicht, op regels gebaseerde engine gericht op lexicale gelijkenis.
:::

## Alleen-lezen en gedeactiveerde status {#read-only-and-disabled-state}

De `TextArea`-component kan zo worden ingesteld dat deze alleen-lezen of gedeactiveerd is om de gebruikersinteractie te beheersen.

Een **alleen-lezen** tekstgebied stelt gebruikers in staat om de inhoud te bekijken en te selecteren, maar niet te bewerken. Dit is nuttig voor het weergeven van dynamische of vooraf ingevulde informatie die onveranderd moet blijven.

Een **gedeactiveerd** tekstgebied blokkeert daarentegen alle interactie—waaronder focus en tekstselectie—en is doorgaans gestyled als inactief of vervaagd.

Gebruik de alleen-lezen modus wanneer de inhoud relevant maar onveranderlijk is, en de gedeactiveerde modus wanneer de invoer momenteel niet van toepassing is of tijdelijk inactief moet zijn.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
