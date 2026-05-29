---
title: CheckBox
sidebar_position: 20
_i18n_hash: 7946f6753a03194ecdcf7e20a7053012
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Een `CheckBox` kan worden geselecteerd of gedeselecteerd en toont de huidige staat als een vinkje. Selectievakjes functioneren goed voor het in- of uitschakelen van individuele instellingen of om gebruikers meerdere opties uit een set te laten kiezen.

<!-- INTRO_END -->

## Toepassingen {#usages}

De `CheckBox` wordt het beste gebruikt in scenario's waarin gebruikers meerdere selecties moeten maken uit een lijst met opties. Hier zijn enkele voorbeelden van wanneer je de `CheckBox` moet gebruiken:

1. **Taak- of Kenmerkselectie**: Selectievakjes worden vaak gebruikt wanneer gebruikers meerdere taken of kenmerken moeten selecteren om bepaalde acties of configuraties uit te voeren.

2. **Voorkeurinstellingen**: Toepassingen die voorkeur- of instellingenpanelen omvatten, gebruiken vaak selectievakjes om gebruikers in staat te stellen meerdere opties uit een set keuzes te kiezen. Dit is het beste voor opties die niet wederzijds exclusief zijn. Bijvoorbeeld:

> - Het inschakelen of uitschakelen van meldingen
> - Kiezen van een donkere of lichte modus thema
> - Selecteren van e-mailmeldingsvoorkeuren

3. **Filteren of Sorteren**: Een `CheckBox` kan worden gebruikt in toepassingen die vereisen dat gebruikers meerdere filters of categorieën selecteren, zoals het filteren van zoekresultaten of het selecteren van meerdere items voor verdere actie.

4. **Formulierinvoeren**: Selectievakjes worden vaak gebruikt in formulieren om gebruikers in staat te stellen meerdere opties te selecteren of binaire keuzes te maken. Bijvoorbeeld:
   > - Abonneren op een nieuwsbrief
   > - Instemmen met de voorwaarden
   > - Selecteren van items voor aankoop of reservering

## Tekst en positionering {#text-and-positioning}

Selectievakjes kunnen de <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String tekst)</JavadocLink> methode gebruiken, die wordt gepositioneerd nabij het selectievakje volgens de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Selectievakjes hebben ingebouwde functionaliteit om tekst weer te geven, hetzij rechts of links van het vak. Standaard wordt de tekst rechts van de component weergegeven. Positionering van de tekst wordt ondersteund door gebruik te maken van de <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> enum. Hieronder staan de twee instellingen: <br/>

<ComponentDemo
path='/webforj/checkboxhorizontaltext'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java']}
height='200px'
/>

<br/>

## Indeterminisme {#indeterminism}

De `CheckBox` component ondersteunt indeterminisme, een UI-patroon dat vaak wordt gebruikt in formulieren en lijsten om aan te geven dat een groep selectievakjes een mengeling van aangevinkte en niet-aangevinkte toestanden heeft. Deze staat wordt weergegeven als een derde visuele staat, meestal weergegeven als een gevuld vierkant of een streepje binnen het selectievakje. Er zijn enkele veelvoorkomende gebruikstoepassingen verbonden aan indeterminisme:

- **Meerdere items selecteren**: Indeterminisme is nuttig wanneer gebruikers meerdere items moeten selecteren uit een lijst of een set opties. Het stelt gebruikers in staat aan te geven dat ze sommige, maar niet alle, beschikbare keuzes willen selecteren.

- **Hiërarchische gegevens**: Indeterminisme kan worden ingezet in scenario's waarin een hiërarchische relatie tussen selectievakjes bestaat. Bijvoorbeeld, bij het selecteren van categorieën en subcategorieën kan indeterminisme vertegenwoordigen dat sommige subcategorieën zijn geselecteerd terwijl andere dat niet zijn, en het bovenliggende component in de onbepaalde staat verkeert.

<ComponentDemo
path='/webforj/checkboxindeterminate'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java']}
height='150px'
/>

## Styling {#styling}

### Expanses {#expanses}

De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses waarden </JavadocLink> staan voor snelle styling zonder CSS te gebruiken. Expanses worden ondersteund door gebruik te maken van de `Expanse` enum klasse. Hieronder staan de expanses die worden ondersteund voor de selectievakjescomponent: <br/>

<ComponentDemo
path='/webforj/checkboxexpanse'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java']}
height='150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Checkbox` component, overweeg de volgende best practices:

1. **Opties Duidelijk Labelen**: Zorg voor duidelijke en beknopte labels voor elke `CheckBox` optie om de keuze nauwkeurig te beschrijven. Labels moeten gemakkelijk te begrijpen en van elkaar te onderscheiden zijn.

2. **Groeperen van Selectievakjes**: Groepeer gerelateerde selectievakjes bij elkaar om hun associatie aan te geven. Dit helpt gebruikers te begrijpen dat meerdere opties binnen een specifieke groep kunnen worden geselecteerd.

3. **Standaardselectie Bieden**: Indien van toepassing, overweeg dan om een standaardselectie voor selectievakjes te bieden om gebruikers te begeleiden wanneer ze de opties voor het eerst tegenkomen. De standaardselectie moet overeenkomen met de meest voorkomende of gewenste keuze.

4. **Indeterminisme**: Als een ouder `CheckBox` component meerdere componenten bevat waarvan sommige kunnen worden aangevinkt en andere kunnen worden uitgevinkt, gebruik dan de onbepaalde eigenschap om aan te geven dat niet alle `CheckBox` componenten zijn aangevinkt of uitgevinkt.
