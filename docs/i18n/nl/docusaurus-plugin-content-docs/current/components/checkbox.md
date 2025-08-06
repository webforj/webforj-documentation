---
title: CheckBox
sidebar_position: 20
_i18n_hash: 0c55e1e2b7f92aa8f1f65151bfa3e096
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

De `CheckBox` klasse maakt een component aan dat geselecteerd of niet geselecteerd kan worden, en dat zijn staat aan de gebruiker toont. Wanneer erop wordt geklikt, verschijnt er een vinkje in het vakje, om een bevestigende keuze (aan) aan te geven. Wanneer er opnieuw op wordt geklikt, verdwijnt het vinkje, wat een negatieve keuze (uit) aangeeft.

Door een duidelijke en directe visuele indicatie van de selectie-status te bieden, verbeteren checkboxen de interactie van gebruikers en hun besluitvorming, waardoor ze een essentieel element zijn in moderne gebruikersinterfaces.

## Usages {#usages}

De `CheckBox` wordt het beste gebruikt in scenario's waarin gebruikers meerdere selecties moeten maken uit een lijst met opties. Hier zijn enkele voorbeelden van wanneer je de `CheckBox` moet gebruiken:

1. **Taak- of Functiekeuze**: Checkboxen worden vaak gebruikt wanneer gebruikers meerdere taken of functies moeten selecteren om bepaalde acties of configuraties uit te voeren.

2. **Voorkeurinstellingen**: Applicaties die voorkeur- of instellingenpanelen bevatten, gebruiken vaak Checkboxen om gebruikers in staat te stellen meerdere opties uit een set keuzes te kiezen. Dit is het beste voor opties die niet onderling exclusief zijn. Bijvoorbeeld:

> - Meldingen in- of uitschakelen
> - Een donkere of lichte thema kiezen
> - Voorkeuren voor e-mailmeldingen selecteren

3. **Filteren of Sorteren**: Een `CheckBox` kan worden gebruikt in applicaties waarin gebruikers meerdere filters of categorieën moeten selecteren, zoals het filteren van zoekresultaten of het selecteren van meerdere items voor verdere acties.

4. **Formuliervelden**: Checkboxen worden vaak gebruikt in formulieren om gebruikers in staat te stellen meerdere opties te selecteren of binaire keuzes te maken. Bijvoorbeeld:
   > - Abonneren op een nieuwsbrief
   > - Akkoord gaan met de algemene voorwaarden
   > - Items selecteren voor aankoop of boeking

## Text and positioning {#text-and-positioning}

Checkboxen kunnen gebruikmaken van de <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> methode, die nabij het checkbox wordt gepositioneerd volgens de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Checkboxen hebben ingebouwde functionaliteit om tekst weer te geven, hetzij rechts of links van het vak. Standaard wordt de tekst rechts van het component weergegeven. De positionering van de tekst wordt ondersteund door het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> enum. Hieronder staan de twee instellingen: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminism {#indeterminism}

De `CheckBox` component ondersteunt indeterminisme, wat een UI-patroon is dat vaak wordt gebruikt in formulieren en lijsten om aan te geven dat een groep checkboxen een mengsel van aangevinkte en niet-aangevinkte staten heeft. Deze status wordt weergegeven door een derde visuele staat, die meestal wordt weergegeven als een ingevuld vierkant of een streepje in de checkbox. Er zijn een paar veelvoorkomende gebruiksscenario's verbonden aan indeterminisme:

- **Meerdere items selecteren**: Indeterminisme is nuttig wanneer gebruikers meerdere items moeten selecteren uit een lijst of een set opties. Het stelt gebruikers in staat aan te geven dat ze sommige, maar niet alle, beschikbare keuzes willen selecteren.

- **Hiërarchische gegevens**: Indeterminisme kan worden toegepast in scenario's waar er een hiërarchische relatie tussen Checkboxen is. Bijvoorbeeld, bij het selecteren van categorieën en subcategorieën kan indeterminisme aangeven dat sommige subcategorieën zijn geselecteerd terwijl andere dat niet zijn, en de bovenliggende component is in de onbepaalde staat.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Styling {#styling}

### Expanses {#expanses}

De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses waarden</JavadocLink> maken snelle styling mogelijk zonder gebruik te maken van CSS. Expanses worden ondersteund door het gebruik van de `Expanse` enum klasse. Hieronder staan de expanses die voor de checkboxcomponent worden ondersteund: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Checkbox` component, overweeg de volgende best practices:

1. **Duidelijke Labels**: Voorzie elke `CheckBox` optie van duidelijke en beknopte labels om de keuze nauwkeurig te beschrijven. Labels moeten gemakkelijk te begrijpen zijn en van elkaar te onderscheiden.

2. **Checkboxen Groeperen**: Groepeer verwante Checkboxen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat meerdere opties binnen een specifieke groep kunnen worden geselecteerd.

3. **Standaardselectie Bieden**: Indien van toepassing, overweeg een standaardselectie voor Checkboxen te bieden om gebruikers te begeleiden wanneer zij de opties voor het eerst tegenkomen. De standaardselectie moet overeenkomen met de meest gangbare of gewenste keuze.

4. **Indeterminisme**: Als een bovenliggende `CheckBox` component meerdere componenten heeft die op een manier kunnen worden aangevinkt of uitgevinkt, gebruik dan de onbepaalde eigenschap om aan te geven dat niet alle `CheckBox` componenten zijn aangevinkt of niet aangevinkt.
