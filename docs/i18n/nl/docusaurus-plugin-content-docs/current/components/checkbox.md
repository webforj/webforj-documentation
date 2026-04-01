---
title: CheckBox
sidebar_position: 20
_i18n_hash: e5ace9c598a0892cfa456f376035c87a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Een `CheckBox` kan geselecteerd of niet geselecteerd worden en geeft zijn huidige toestand weer als een vinkje. Checkboxen werken goed voor het wisselen van individuele instellingen of het laten kiezen van meerdere opties uit een set.

<!-- INTRO_END -->

## Usages {#usages}

De `CheckBox` wordt het beste gebruikt in scenario's waarbij gebruikers meerdere selecties moeten maken uit een lijst met opties. Hier zijn enkele voorbeelden wanneer je de `CheckBox` moet gebruiken:

1. **Selectie van taken of functies**: Checkboxen worden vaak gebruikt wanneer gebruikers meerdere taken of functies moeten selecteren om bepaalde acties of configuraties uit te voeren.

2. **Voorkeursinstellingen**: Toepassingen die betrekking hebben op voorkeur- of instellingenpanelen gebruiken vaak checkboxen om gebruikers in staat te stellen meerdere opties uit een set keuzes te kiezen. Dit is het beste voor opties die niet onderling uitsluitend zijn. Bijvoorbeeld:

> - Meldingen in- of uitschakelen
> - Kiezen voor een donkere of lichte modus thema
> - Selecteren van voorkeuren voor e-mailmeldingen

3. **Filteren of sorteren**: Een `CheckBox` kan worden gebruikt in toepassingen waarbij gebruikers meerdere filters of categorieën moeten selecteren, zoals het filteren van zoekresultaten of het selecteren van meerdere items voor verdere acties.

4. **Formuliervelden**: Checkboxen worden vaak gebruikt in formulieren om gebruikers in staat te stellen meerdere opties te selecteren of binaire keuzes te maken. Bijvoorbeeld:
   > - Abonneren op een nieuwsbrief
   > - Accepteren van algemene voorwaarden
   > - Items selecteren voor aankoop of boeking

## Text and positioning {#text-and-positioning}

Checkboxen kunnen de <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> methode gebruiken, die dichtbij de checkbox wordt gepositioneerd volgens de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Checkboxen hebben ingebouwde functionaliteit om tekst weer te geven die ofwel rechts of links van het vak wordt weergegeven. Standaard wordt de tekst rechts van de component weergegeven. Positionering van de tekst wordt ondersteund door het gebruik van de <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> enum. Hieronder staan de twee instellingen: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminism {#indeterminism}

De `CheckBox` component ondersteunt onbepaaldheid, wat een UI-patroon is dat vaak wordt gebruikt in formulieren en lijsten om aan te geven dat een groep checkboxen een mix van geselecteerde en niet-geselecteerde toestanden heeft. Deze toestand wordt weergegeven door een derde visuele staat, meestal weergegeven als een gevulde vierkant of een streep binnen de checkbox. Er zijn een paar veelvoorkomende gebruiksgevallen geassocieerd met onbepaaldheid:

- **Meerdere items selecteren**: Onbepaaldheid is nuttig wanneer gebruikers meerdere items uit een lijst of een set opties moeten selecteren. Het stelt gebruikers in staat om aan te geven dat ze sommige, maar niet alle, beschikbare keuzes willen selecteren.

- **Hiërarchische gegevens**: Onbepaaldheid kan worden toegepast in scenario's waar er een hiërarchische relatie tussen checkboxen is. Bijvoorbeeld, wanneer categorieën en subcategorieën worden geselecteerd, kan onbepaaldheid vertegenwoordigen dat sommige subcategorieën zijn geselecteerd terwijl andere dat niet zijn, en de bovenliggende component in de onbepaalde staat is.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Styling {#styling}

### Expanses {#expanses}

De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses waarden</JavadocLink> maken snelle styling mogelijk zonder CSS te gebruiken.
Expanses worden ondersteund door het gebruik van de `Expanse` enum klasse. Hieronder staan de expanses die worden ondersteund voor de checkboxcomponent: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring bij het gebruik van de `Checkbox` component te waarborgen, overweeg dan de volgende best practices:

1. **Duidelijk labelen van opties**: Bied duidelijke en beknopte labels voor elke `CheckBox` optie om de keuze nauwkeurig te beschrijven. Labels moeten gemakkelijk te begrijpen zijn en van elkaar te onderscheiden.

2. **Checkboxen groeperen**: Groepeer gerelateerde checkboxen samen om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat meerdere opties binnen een specifieke groep kunnen worden geselecteerd.

3. **Standaardselectie bieden**: Indien van toepassing, overweeg dan om een standaardselectie voor checkboxen te bieden om gebruikers te begeleiden als ze de opties voor het eerst tegenkomen. De standaardselectie moet aansluiten bij de meest voorkomende of gewenste keuze.

4. **Onbepaaldheid**: Als een bovenliggende `CheckBox` component meerdere componenten heeft die eraan behoren op een manier waarop sommige kunnen worden aangevinkt en andere kunnen worden uitgevinkt, gebruik dan de onbepaalde eigenschap om aan te geven dat niet alle `CheckBox` componenten zijn aangevinkt of niet aangevinkt.
