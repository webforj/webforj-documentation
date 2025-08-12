---
title: CheckBox
sidebar_position: 20
_i18n_hash: c2be55222401962b275faf28ff6ddba3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

De `CheckBox` klasse creëert een component die geselecteerd of gedeselecteerd kan worden, en die zijn status aan de gebruiker toont. Wanneer erop geklikt wordt, verschijnt er een vinkje binnen de doos om een bevestigende keuze (aan) aan te geven. Bij een volgende klik verdwijnt het vinkje, wat een negatieve keuze (uit) aangeeft.

Door een duidelijke en eenvoudige visuele indicatie van de selectie-status te bieden, verbeteren checkboxen de gebruikersinteractie en besluitvorming, waardoor ze een essentieel element in moderne gebruikersinterfaces vormen.

## Usages {#usages}

De `CheckBox` is het beste te gebruiken in scenario's waar gebruikers meerdere selecties moeten maken uit een lijst met opties. Hier zijn enkele voorbeelden wanneer je de `CheckBox` moet gebruiken:

1. **Taak- of Kenmerkselectie**: Checkboxen worden vaak gebruikt wanneer gebruikers meerdere taken of kenmerken moeten selecteren om bepaalde acties of configuraties uit te voeren.

2. **Voorkeurinstellingen**: Applicaties die voorkeur- of instellingenpanelen omvatten, gebruiken vaak checkboxen om gebruikers in staat te stellen meerdere opties uit een set keuzes te kiezen. Dit is het beste voor opties die niet wederzijds exclusief zijn. Bijvoorbeeld:

> - Meldingen in- of uitschakelen
> - Kiezen voor een donkere of lichte modus thema
> - Selecteren van voorkeuren voor e-mailmeldingen

3. **Filteren of Sorteren**: Een `CheckBox` kan worden gebruikt in applicaties die vereisen dat gebruikers meerdere filters of categorieën selecteren, zoals het filteren van zoekresultaten of het selecteren van meerdere items voor verdere acties.

4. **Formuliervelden**: Checkboxen worden vaak gebruikt in formulieren om gebruikers in staat te stellen meerdere opties te selecteren of binaire keuzes te maken. Bijvoorbeeld:
   > - Abonneren op een nieuwsbrief
   > - Akkoord gaan met de voorwaarden
   > - Selecteren van items voor aankoop of boeking

## Text and positioning {#text-and-positioning}

Checkboxen kunnen gebruikmaken van de <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String tekst)</JavadocLink> methode, die dicht bij de checkbox wordt gepositioneerd volgens de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Checkboxen hebben ingebouwde functionaliteit om tekst weer te geven die aan de rechter- of linkerkant van de doos wordt getoond. Standaard wordt de tekst aan de rechterzijde van de component weergegeven. Het positioneren van de tekst wordt ondersteund door het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> enum. Hieronder staan de twee instellingen: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminism {#indeterminism}

De `CheckBox` component ondersteunt indeterminisme, wat een UI-patroon is dat vaak wordt gebruikt in formulieren en lijsten om aan te geven dat een groep checkboxen een mix van aangevinkte en niet-aangevinkte statussen heeft. Deze status wordt weergegeven door een derde visuele staat, die typisch wordt weergegeven als een gevuld vierkant of een streepje binnen de checkbox. Er zijn een paar veelvoorkomende gebruikssituaties geassocieerd met indeterminisme:

- **Meerdere items selecteren**: Indeterminisme is nuttig wanneer gebruikers meerdere items uit een lijst of een set opties willen selecteren. Het stelt gebruikers in staat om aan te geven dat ze sommige, maar niet alle, beschikbare keuzes willen selecteren.

- **Hiërarchische gegevens**: Indeterminisme kan worden toegepast in scenario's waarbij er een hiërarchische relatie tussen checkboxen bestaat. Bijvoorbeeld, bij het selecteren van categorieën en subcategorieën kan indeterminisme aangeven dat sommige subcategorieën zijn geselecteerd terwijl andere dat niet zijn, en de bovenliggende component zich in de onbepaalde staat bevindt.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Styling {#styling}

### Expanses {#expanses}

De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses waarden </JavadocLink> maken snelle styling mogelijk zonder CSS te gebruiken. Expanses worden ondersteund door het gebruik van de `Expanse` enum klasse. Hieronder staan de expanses die worden ondersteund voor de checkbox component: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Checkbox` component, overweeg de volgende best practices:

1. **Duidelijke Labels voor Opties**: Voorzie duidelijke en beknopte labels voor elke `CheckBox` optie om de keuze nauwkeurig te beschrijven. Labels moeten gemakkelijk te begrijpen en van elkaar te onderscheiden zijn.

2. **Groep Checkboxen**: Groepeer gerelateerde checkboxen zodat hun associatie duidelijk is. Dit helpt gebruikers te begrijpen dat meerdere opties binnen een specifieke groep geselecteerd kunnen worden.

3. **Bied Standaardselectie Aan**: Indien van toepassing, overweeg om een standaardselectie voor checkboxen te bieden om gebruikers te begeleiden wanneer ze voor het eerst met de opties in aanraking komen. De standaardselectie moet overeenkomen met de meest voorkomende of gewenste keuze.

4. **Indeterminisme**: Als een bovenliggende `CheckBox` component meerdere componenten omvat waarvan sommige kunnen worden aangevinkt en andere uitgevinkt, gebruik dan de onbepaalde eigenschap om aan te geven dat niet alle `CheckBox` componenten zijn aangevinkt of niet zijn aangevinkt.
