---
title: TextArea
sidebar_position: 130
_i18n_hash: 5e61ae2b47786f23e6f1f6eba317ed54
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

De `TextArea` component biedt een meerregelige tekstinvoerveld waar gebruikers langere tekstblokken kunnen typen en bewerken. Het ondersteunt maximale tekentalen, paragraafstructuur, regelomsluiting en validatieregels om te bepalen hoe invoer wordt behandeld.

<!-- INTRO_END -->

## Aanmaken van een `TextArea` {#creating-a-textarea}

Maak een `TextArea` door een label naar zijn constructor te sturen. Eigenschappen zoals opmaak tekst, tekenlimieten en omhulgedrag kunnen worden geconfigureerd via setter-methoden.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Het beheren van paragrafen {#managing-paragraphs}

De `TextArea` component biedt functies voor het omgaan met tekstparagrafen, waardoor het ideaal is voor toepassingen die documentbewerking of gestructureerde tekstinvoer vereisen.

Hier is een snel voorbeeld van hoe je paragraafinhoud kunt opbouwen en manipuleren:

```java
TextArea textArea = new TextArea();

// Voeg een paragraaf toe aan het begin
textArea.addParagraph(0, "Dit is de eerste paragraaf.");

// Voeg een andere paragraaf toe aan het einde
textArea.addParagraph("Dit is een tweede paragraaf.");

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

**Structurele beperkingen** richten zich op hoe de tekst is georganiseerd en visueel is weergegeven. Bijvoorbeeld:
- `setLineCountLimit(int maxLines)` beperkt het aantal regels dat is toegestaan in het tekstgebied.
- `setParagraphLengthLimit(int maxCharsPerLine)` beperkt het aantal tekens per paragraaf (of regel), wat helpt bij het handhaven van leesbaarheid of opmaakstandaarden.

**Inhoudsbeperkingen** daarentegen hebben betrekking op de totale hoeveelheid tekst die is ingevoerd, ongeacht hoe deze is verdeeld:
- `setMaxLength(int maxChars)` beperkt het totale aantal toegestane tekens over alle paragrafen.
- `setMinLength(int minChars)` stelt een minimale lengte in, zodat er voldoende inhoud wordt geleverd.

De volgende demo stelt gebruikers in staat om validatielimieten aan te passen—zoals maximaal aantal tekens, paragraaflengte en regelcount—en te zien hoe de `TextArea` hierop reageert.

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Woordomsluiting en Regelomsluiting {#word-wrap-and-line-wrapping}

Je kunt controleren of tekst omsluit of horizontaal scrollt met `setLineWrap()`. Wanneer omsluiting is uitgeschakeld, blijven regels horizontaal doorgaan buiten het zichtbare gebied, met scrollen als gevolg. Wanneer ingeschakeld, wikkelt tekst automatisch naar de volgende regel wanneer deze de rand van de component bereikt.

Om verder te verfijnen hoe omsluiting gedraagt, laat `setWrapStyle()` je kiezen tussen twee stijlen:
- `WORD_BOUNDARIES` wikkelt tekst bij hele woorden, wat de natuurlijke leesstroom behoudt.
- `CHARACTER_BOUNDARIES` wikkelt bij individuele tekens, waardoor strakkere controle over de lay-out mogelijk is, vooral in smalle of vastgeconfigureerde containers.

Deze omsluitingsopties werken hand in hand met structurele beperkingen zoals regelcount en paragrafenlengte limieten. Terwijl omsluiting bepaalt *hoe* tekst stroomt binnen de beschikbare ruimte, definiëren de structurele limieten *hoeveel* ruimte tekst mag innemen. Samen helpen ze zowel visuele structuur als gebruikersinvoerlijnen te behouden.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Voorspelde tekst {#predicted-text}

De `TextArea` component ondersteunt slimme tekstsuggesties om gebruikers te helpen sneller en met minder fouten te typen. Terwijl gebruikers tekst invoeren, verschijnen voorspellende suggesties op basis van de huidige invoer, waardoor ze gebruik kunnen maken van veelvoorkomende of verwachte zinnen.

Voorspellingen kunnen worden geaccepteerd door op de `Tab` of `ArrowRight` toets te drukken, waardoor de voorgestelde tekst naadloos in de invoer wordt ingevoegd. Als er op dat moment geen passende voorspelling beschikbaar is, blijft de invoer onveranderd en kan de gebruiker blijven typen zonder onderbreking—zorgend dat de functie nooit in de weg staat.

Dit voorspellende gedrag verhoogt zowel de snelheid als de nauwkeurigheid, vooral in situaties met herhaalde invoer of toepassingen waar consistentie van formulering belangrijk is.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/resources/static/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Deze demo gebruikt de [Datamuse API](https://datamuse.com/) om woordsuggesties te bieden op basis van de invoer van de gebruiker. De kwaliteit en relevantie van de voorspellingen zijn volledig afhankelijk van de dataset en het beoordelingsmechanisme van de API. Het gebruikt geen AI-modellen of grote taalmodellen (LLM's); de suggesties worden gegenereerd vanuit een lichte, op regels gebaseerde motor die zich richt op lexicale gelijkenis.
:::

## Alleen-lezen en Deactiveren staat {#read-only-and-disabled-state}

De `TextArea` component kan worden ingesteld op alleen-lezen of gedeactiveerd om gebruikersinteractie te beheersen.

Een **alleen-lezen** tekstgebied stelt gebruikers in staat om de inhoud te bekijken en te selecteren, maar niet te bewerken. Dit is nuttig voor het weergeven van dynamische of vooraf ingevulde informatie die onveranderd moet blijven.

Een **gedeactiveerd** tekstgebied daarentegen blokkeert alle interactie—including focus en tekstselectie—en is typisch opgemaakt als inactief of grijs weergegeven.

Gebruik de alleen-lezen modus wanneer de inhoud relevant maar onveranderlijk is, en de gedeactiveerde modus wanneer de invoer momenteel niet van toepassing is of tijdelijk inactief moet zijn.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
