---
title: TextArea
sidebar_position: 130
_i18n_hash: 423b70520e8f64a463d2c7b1d0e35ddc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

De `TextArea` component biedt een multi-regel tekstinvoerveld waar gebruikers langere tekstblokken kunnen typen en bewerken. Het ondersteunt maximale tekenlimieten, paragraafstructuur, regelomsluiting en validatieregels om te controleren hoe invoer wordt behandeld.

<!-- INTRO_END -->

## Het creëren van een `TextArea` {#creating-a-textarea}

Maak een `TextArea` door een label aan de constructor door te geven. Eigenschappen zoals tijdelijke tekst, tekenlimieten en gedrag van omsluiting kunnen worden geconfigureerd via settermethoden.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Beheren van paragrafen {#managing-paragraphs}

De `TextArea` component biedt mogelijkheden voor het omgaan met tekstparagrafen, waardoor het ideaal is voor applicaties die documentbewerking of gestructureerde tekstinvoer vereisen.

Hier is een snel voorbeeld van hoe je inhoud van paragrafen kunt opbouwen en manipuleren:

```java
TextArea textArea = new TextArea();

// Voeg een paragraaf toe aan het begin
textArea.addParagraph(0, "Dit is de eerste paragraaf.");

// Voeg een andere paragraaf toe aan het einde
textArea.addParagraph("Hier is een tweede paragraaf.");

// Voeg aanvullende inhoud toe aan de eerste paragraaf
textArea.appendToParagraph(0, " Deze zin vervolgt de eerste.");

// Verwijder de tweede paragraaf
textArea.removeParagraph(1);

// Haal alle huidige paragrafen op en druk ze af
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("Paragraaf " + i + ": " + paragraphs.get(i));
}
```

## Validatie {#validation}

De `TextArea` component ondersteunt twee complementaire typen validatie: structurele beperkingen en inhoudsbeperkingen.

**Structurele beperkingen** richten zich op hoe de tekst is georganiseerd en visueel is gerangschikt. Bijvoorbeeld:
- `setLineCountLimit(int maxLines)` beperkt het aantal regels dat is toegestaan in het tekstgebied.
- `setParagraphLengthLimit(int maxCharsPerLine)` beperkt het aantal tekens per paragraaf (of regel), wat helpt om leesbaarheid of opmaakstandaarden af te dwingen.

**Inhoudsbeperkingen** daarentegen, houden zich bezig met de totale hoeveelheid tekst die wordt ingevoerd, ongeacht hoe deze is verdeeld:
- `setMaxLength(int maxChars)` legt een maximum aantal toegestane tekens over alle paragrafen op.
- `setMinLength(int minChars)` dwingt een minimale lengte af, zodat er voldoende inhoud wordt aangeboden.

De volgende demo stelt gebruikers in staat om validatielimieten aan te passen - zoals het maximum aantal tekens, paragraaflengte en regelnummer - in realtime en te zien hoe de `TextArea` reageert.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Woordomsluiting en regelomsluiting {#word-wrap-and-line-wrapping}

Je kunt controleren of tekst omgevouwen of horizontaal gescrold wordt met `setLineWrap()`. Wanneer omsluiting is uitgeschakeld, gaan regels horizontaal verder dan het zichtbare gebied, wat scrollen vereist. Wanneer deze is ingeschakeld, wordt tekst automatisch omgevouwen naar de volgende regel wanneer deze de rand van de component bereikt.

Om verder te verfijnen hoe omsluiting zich gedraagt, laat `setWrapStyle()` je kiezen tussen twee stijlen:
- `WORD_BOUNDARIES` vouwt tekst op bij hele woorden, waardoor de natuurlijke leesstroom behouden blijft.
- `CHARACTER_BOUNDARIES` vouwt bij individuele tekens, waardoor strakkere controle over de lay-out mogelijk is, vooral in smalle of vaste breedtecontainers.

Deze omsluitingsopties werken hand in hand met structurele beperkingen zoals limieten voor het aantal regels en paragraaflengte. Terwijl omsluiting bepaalt *hoe* tekst stroomt binnen de beschikbare ruimte, definiëren de structurele limieten *hoeveel* ruimte tekst mag innemen. Samen helpen ze zowel de visuele structuur als de grenzen van gebruikersinvoer te behouden.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Voorspelde tekst {#predicted-text}

De `TextArea` component ondersteunt slimme tekstsuggesties om gebruikers te helpen sneller te typen en minder fouten te maken. Terwijl gebruikers tekst invoeren, verschijnen er voorspellende suggesties op basis van de huidige invoer, waarmee ze veelvoorkomende of verwachte zinnen kunnen aanvullen.

Voorspellingen kunnen worden geaccepteerd door op de `Tab` of `ArrowRight` toets te drukken, waarbij de voorgestelde tekst naadloos in de invoer wordt ingevoegd. Als op een bepaald moment geen geschikte voorspelling beschikbaar is, blijft de invoer ongewijzigd en kan de gebruiker zonder onderbreking doorgaan met typen - waardoor wordt gegarandeerd dat de functie de gebruiker niet in de weg zit.

Dit voorspellend gedrag verbetert zowel de snelheid als de nauwkeurigheid, vooral in scenario's met herhaalde invoer of applicaties waarin consistentie van formulering belangrijk is.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Deze demo maakt gebruik van de [Datamuse API](https://datamuse.com/) om woordsuggesties te bieden op basis van de invoer van de gebruiker. De kwaliteit en relevantie van de voorspellingen hangen volledig af van de dataset en het scoringsmechanisme van de API. Het maakt geen gebruik van AI-modellen of grote taalmodellen (LLM's); de suggesties worden gegenereerd vanuit een lichte, op regels gebaseerde motor die zich richt op lexicale gelijkheid.
:::

## Alleen-lezen en gedeactiveerde toestand {#read-only-and-disabled-state}

De `TextArea` component kan worden ingesteld op alleen-lezen of gedeactiveerd om gebruikersinteractie te beheersen.

Een **alleen-lezen** tekstgebied staat gebruikers toe de inhoud te bekijken en te selecteren, maar niet te bewerken. Dit is handig voor het weergeven van dynamische of vooraf ingevulde informatie die onveranderd moet blijven.

Een **gedeactiveerd** tekstgebied daarentegen blokkeert alle interactie - inclusief focus en tekstselectie - en wordt meestal gestileerd als inactief of grijs.

Gebruik de alleen-lezen modus wanneer de inhoud relevant maar onveranderlijk is, en de gedeactiveerde modus wanneer de invoer momenteel niet van toepassing is of tijdelijk inactief moet zijn.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
