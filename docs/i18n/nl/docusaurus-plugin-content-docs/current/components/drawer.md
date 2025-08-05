---
title: Drawer
sidebar_position: 35
_i18n_hash: e3b531e5fb7f1554e035f4d05aad8512
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De lade is een container die in het viewport schuift om extra opties en informatie bloot te stellen. Meerdere lades kunnen in een applicatie worden gemaakt, en ze zullen boven elkaar worden gestapeld.

De Drawer-component kan in veel verschillende situaties worden gebruikt, zoals door een navigatiemenu te bieden dat kan worden in- en uitgeschakeld, een paneel dat aanvullende of contextuele informatie weergeeft, of om het gebruik op een mobiel apparaat te optimaliseren. Het volgende voorbeeld toont een mobiele applicatie die de webforJ AppLayout-component gebruikt en een "Welkom Popup" lade onderaan weergeeft wanneer deze voor het eerst is geladen. Bovendien kan een navigerende Drawer-component in de applicatie worden omgeschakeld door op het hamburgermenu te klikken.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Usages {#usages}

1. **Navigatiemenu**: Een veelvoorkomende toepassing van een ladecomponent is als navigatiemenu. Het biedt een ruimte-efficiënte manier om links naar verschillende secties of pagina's van uw applicatie weer te geven, vooral in mobiele of responsieve lay-outs. Gebruikers kunnen de lade openen en sluiten om toegang te krijgen tot navigatieopties zonder het hoofdinhoudsgebied rommelig te maken.

2. **Filter en zijbalk**: Een lade kan worden gebruikt als filter of zijbalk in applicaties die een lijst van items weergeven. Gebruikers kunnen de lade uitbreiden om filteropties, sorteermogelijkheden of aanvullende informatie gerelateerd aan de lijstitems te onthullen. Dit houdt de hoofdinhoud gericht op de lijst terwijl het geavanceerde functies op een toegankelijke manier biedt.

3. **Gebruikersprofiel of instellingen**: U kunt een lade gebruiken om gebruikersprofielinformatie of applicatie-instellingen weer te geven. Dit houdt dergelijke informatie gemakkelijk toegankelijk maar verborgen wanneer dat niet nodig is, waardoor een schone en overzichtelijke interface behouden blijft. Gebruikers kunnen de lade openen om hun profielen bij te werken of instellingen aan te passen.

4. **Meldingen**: Voor applicaties met meldingen of waarschuwingen kan een lade naar binnen schuiven om nieuwe berichten of updates weer te geven. Gebruikers kunnen snel meldingen controleren en verwijderen zonder hun huidige weergave te verlaten.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Customization {#customization}

Er zijn verschillende eigenschappen die het mogelijk maken om verschillende attributen van de Drawer-component aan te passen. Deze sectie geeft een overzicht van die eigenschappen met voorbeelden voor hun wijziging.

## Autofocus {#autofocus}

De Auto-Focus-eigenschap is ontworpen om de toegangbaarheid en gebruiksvriendelijkheid te verbeteren door automatisch de focus te leggen op het eerste item binnen een lade wanneer deze wordt geopend. Deze functie elimineert de noodzaak voor gebruikers om handmatig naar het gewenste item te navigeren, wat tijd en moeite bespaart.

Wanneer de lade wordt geactiveerd om te openen, hetzij via een gebeurtenis, standaard of een andere interactie, wordt de focus van de gebruiker naar het eerste item binnen de lade geleid. Dit eerste item kan een knop, een link, een menu-optie of een ander focusbaar element zijn.

:::tip
Door automatisch de focus op het eerste item te leggen, zorgt de ontwikkelaar ervoor dat gebruikers onmiddellijk kunnen omgaan met de meest relevante of vaak gebruikte optie zonder door de hele lade te hoeven navigeren. Dit gedrag stroomlijnt de gebruikerservaring en bevordert efficiënte navigatie binnen de UI.
:::

Deze eigenschap kan ook bijzonder gunstig zijn voor personen die afhankelijk zijn van toetsenbordinavigatie of ondersteunende technologieën zoals schermlezers. Het biedt een duidelijk startpunt binnen de lade en stelt gebruikers in staat om de gewenste functionaliteit te bereiken zonder onnodige handmatige invoer.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Voorbeeld -->

## Label {#label}

De Drawer Label-eigenschap is een functie die is ontworpen om de toegankelijkheid te verbeteren en beschrijvende context te bieden voor een lade binnen een gebruikersinterface. Deze eigenschap stelt ontwikkelaars in staat om een label aan een lade toe te wijzen, voornamelijk voor toegankelijkheidsdoeleinden, zodat schermlezers en andere ondersteunende technologieën de functie en de inhoud van de lade nauwkeurig kunnen overbrengen aan gebruikers.

Wanneer de Drawer Label-eigenschap wordt gebruikt, wordt het toegewezen label een integraal onderdeel van de toegankelijkheidsstructuur van de lade. Het stelt gebruikers die afhankelijk zijn van ondersteunende technologieën in staat om de functie van de lade te begrijpen en effectiever door de interface te navigeren.

Door een label voor de lade te bieden, zorgen ontwikkelaars ervoor dat schermlezers de functie van de lade aankondigen aan visueel gehandicapte gebruikers. Deze informatie stelt individuen in staat om weloverwogen beslissingen te nemen over de interactie met de lade, omdat ze de inhoud en relevantie binnen de bredere gebruikersinterface kunnen begrijpen.

De Label-eigenschap kan worden aangepast om aan de specifieke context en ontwerpvereisten van de applicatie te voldoen. Ontwikkelaars hebben de flexibiliteit om beknopte en beschrijvende labels te bieden die de inhoud of functionaliteit van de lade nauwkeurig weergeven.

<!-- Voorbeeld -->

## Plaatsing {#placement}

De plaatsingseigenschap van de Drawer UI-component stelt ontwikkelaars in staat om de positie en uitlijning van de lade binnen het viewport te specificeren. Deze eigenschap biedt een reeks enumwaarden die flexibiliteit bieden in het bepalen waar de lade verschijnt in verhouding tot de hoofdinhoud.

De beschikbare enumwaarden voor de plaatsingseigenschap zijn als volgt:

- **BOVEN**: Deze waarde plaatst de lade bovenaan het viewport, waardoor het het bovenste gebied kan innemen.

- **BOVEN_CENTRAAL**: Met deze waarde wordt de lade in het midden van het bovenste deel van het viewport gepositioneerd. Het is horizontaal in het midden uitgelijnd, waardoor een gebalanceerde lay-out ontstaat.

- **BENEDEN**: Bij gebruik van deze waarde bevindt de lade zich onderaan het viewport, onder de hoofdinhoud.

- **BENEDEN_CENTRAAL**: Deze waarde centreert de lade horizontaal onderaan het viewport. Het biedt een visueel gebalanceerde compositie.

- **LINKS**: Door deze waarde te selecteren, wordt de lade aan de linkerkant van het viewport geplaatst, naast de hoofdinhoud.

- **RECHTS**: Door deze waarde te gebruiken, wordt de lade aan de rechterkant van het viewport geplaatst, dicht bij de hoofdinhoud.

De plaatsingseigenschap stelt ontwikkelaars in staat om de meest geschikte positie voor de lade te kiezen, afhankelijk van de specifieke ontwerp- en gebruikerservaringseisen. De enumwaarden bieden verschillende plaatsingsopties om verschillende interface-indelingen en visuele hiërarchieën tegemoet te komen.

Door gebruik te maken van de plaatsingseigenschap kunnen ontwikkelaars intuïtieve en efficiënte gebruikersinterfaces creëren. Bijvoorbeeld, de lade aan de linkerkant of rechterkant plaatsen zorgt voor snelle toegang tot aanvullende functionaliteiten of navigatieopties, terwijl plaatsingen aan de boven- of onderkant goed passen voor contextuele informatie of aanvullende inhoud.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring bij het gebruik van de `Drawer`-component te waarborgen, overweeg de volgende best practices:

1. **Plaatsing**: Beslis of de lade van links, rechts, boven of onder moet schuiven, afhankelijk van de lay-out van uw applicatie en overwegingen voor de gebruikerservaring. Houd rekening met gebruikersvoorkeuren en ontwerprichtlijnen.

2. **Toegankelijkheid**: Besteed speciale aandacht aan toegankelijkheid. Zorg ervoor dat gebruikers de lade kunnen openen en sluiten met toetsenbordbediening en dat schermlezers de aanwezigheid en status ervan kunnen aankondigen. Bied ARIA-rollen en -labels aan indien nodig.

3. **Veeggebaren**: Op apparaten met een aanraakscherm ondersteunt u veeggebaren voor het openen en sluiten van de lade. Dit is een intuïtieve manier voor gebruikers om ermee om te gaan.
