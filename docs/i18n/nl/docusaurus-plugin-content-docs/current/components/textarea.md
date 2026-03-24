---
title: TextArea
sidebar_position: 130
_i18n_hash: c25007720c315e5b0b26197e1fdfff61
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

De `TextArea` component biedt een multi-line tekstinvoerveld waarin gebruikers langere tekstblokken kunnen typen en bewerken. Het ondersteunt maximale tekenlimieten, paragraafstructuren, regelomsluiting en validatieregels om te bepalen hoe invoer wordt afgehandeld.

<!-- INTRO_END -->

## Het creëren van een `TextArea` {#creating-a-textarea}

Maak een `TextArea` door een label aan de constructor door te geven. Eigenschappen zoals placeholder tekst, tekenlimieten en omgedrag kunnen worden geconfigureerd via setter-methoden.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Paragrafen beheren {#managing-paragraphs}

De `TextArea` component biedt functies voor het omgaan met tekstparagrafen, waardoor het ideaal is voor applicaties die documentbewerking of gestructureerde tekstinvoer vereisen.

Hier is een snel voorbeeld van hoe je paragrafen kunt opbouwen en manipuleren:

```java
TextArea textArea = new TextArea();

// Voeg een paragraaf aan het begin toe
textArea.addParagraph(0, "Dit is de eerste paragraaf.");

// Voeg een andere paragraaf aan het einde toe
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

**Structurele beperkingen** richten zich op hoe de tekst is georganiseerd en visueel is opgesteld. Bijvoorbeeld:
- `setLineCountLimit(int maxLines)` beperkt het aantal toegestane regels in het tekstgebied.
- `setParagraphLengthLimit(int maxCharsPerLine)` beperkt het aantal tekens per paragraaf (of regel), wat helpt bij het handhaven van leesbaarheid of opmaakstandaarden.

**Inhoudsbeperkingen** daarentegen hebben betrekking op de totale hoeveelheid tekst die wordt ingevoerd, ongeacht hoe deze is verdeeld:
- `setMaxLength(int maxChars)` stelt een limiet op het totaal aantal toegestane tekens over alle paragrafen.
- `setMinLength(int minChars)` handhaaft een minimale lengte, zodat er voldoende inhoud wordt geleverd.

De volgende demo stelt gebruikers in staat om validatielimieten aan te passen - zoals het maximale aantal tekens, paragraaflengte en regelcount - in real time en te zien hoe de `TextArea` hierop reageert.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Woordomsluiting en regelomsluiting {#word-wrap-and-line-wrapping}

Je kunt controleren of tekst omloopt of horizontaal scrollt met behulp van `setLineWrap()`. Wanneer omloop is uitgeschakeld, gaan regels horizontaal verder dan het zichtbare gebied, wat scrollen vereist. Wanneer deze is ingeschakeld, omloopt de tekst automatisch naar de volgende regel wanneer deze de rand van de component bereikt.

Om verder te verfijnen hoe omloop zich gedraagt, laat `setWrapStyle()` je kiezen tussen twee stijlen:
- `WORD_BOUNDARIES` omlijst de tekst bij hele woorden, wat de natuurlijke leesstroom behoudt.
- `CHARACTER_BOUNDARIES` omlijst bij individuele tekens, waardoor strakkere controle over de lay-out mogelijk is, vooral in smalle of vaste containers.

Deze omloopopties werken hand in hand met structurele beperkingen zoals regelcount en paragraaflengtebeperkingen. Terwijl omloop bepaalt *hoe* tekst stroomt binnen de beschikbare ruimte, definiëren de structurele limieten *hoeveel* ruimte de tekst mag innemen. Samen helpen ze zowel de visuele structuur als de grenzen van gebruikersinvoer te behouden.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Voorspelde tekst {#predicted-text}

De `TextArea` component ondersteunt slimme tekstsuggesties om gebruikers te helpen sneller en met minder fouten te typen. Terwijl gebruikers tekst invoeren, verschijnen voorspellende suggesties op basis van de huidige invoer, waardoor ze veelvoorkomende of verwachte zinnen kunnen aanvullen.

Voorspellingen kunnen worden geaccepteerd door op de `Tab` of `ArrowRight` toets te drukken, waardoor de voorgestelde tekst naadloos in de invoer wordt ingevoegd. Als er op dat moment geen geschikte voorspelling beschikbaar is, blijft de invoer ongewijzigd en kan de gebruiker zonder onderbreking doorgaan met typen - waarmee wordt gegarandeerd dat de functie nooit in de weg zit.

Dit voorspellende gedrag verbetert zowel snelheid als nauwkeurigheid, vooral in scenario's met herhaalde invoer of applicaties waar consistentie van zinsvorming belangrijk is.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Deze demo maakt gebruik van de [Datamuse API](https://datamuse.com/) om woordsuggesties te bieden op basis van de invoer van de gebruiker. De kwaliteit en relevantie van de voorspellingen zijn volledig afhankelijk van de dataset en het scoringsmechanisme van de API. Het maakt geen gebruik van AI-modellen of grote taalmodellen (LLMs); de suggesties worden gegenereerd door een lichtgewicht, regelsysteem dat zich richt op lexicale similariteit.
:::

## Alleen-lezen en uitgeschakelde status {#read-only-and-disabled-state}

De `TextArea` component kan worden ingesteld als alleen-lezen of uitgeschakeld om de interactie van de gebruiker te controleren.

Een **alleen-lezen** tekstgebied staat gebruikers toe de inhoud te bekijken en te selecteren, maar niet te bewerken. Dit is nuttig voor het weergeven van dynamische of vooraf ingevulde informatie die onveranderd moet blijven.

Een **uitgeschakeld** tekstgebied blokkeert daarentegen alle interactie - inclusief focus en tekstselectie - en is doorgaans gestyled als inactief of grijs.

Gebruik de alleen-lezen modus wanneer de inhoud relevant maar onveranderlijk is, en de uitgeschakelde modus wanneer de invoer op dit moment niet van toepassing is of tijdelijk inactief moet zijn.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Stijl {#styling}

<TableBuilder name="TextArea" />
