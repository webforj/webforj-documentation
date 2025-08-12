---
title: Drawer
sidebar_position: 35
_i18n_hash: 73da264dca1e3f8cfd58b697e3e9d0dc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De drawer is een container die in het viewport schuift om extra opties en informatie bloot te stellen. Meerdere drawers kunnen in een applicatie worden gemaakt en zullen boven elkaar worden gestapeld.

De Drawer-component kan in veel verschillende situaties worden gebruikt, zoals het bieden van een navigatiemenu dat kan worden in- en uitgeschakeld, een paneel dat aanvullende of contextuele informatie weergeeft, of om het gebruik op een mobiel apparaat te optimaliseren. Het volgende voorbeeld toont een mobiele applicatie die de webforJ AppLayout-component gebruikt en onderaan een "Welkom Pop-up" drawer weergeeft wanneer deze voor het eerst wordt geladen. Bovendien kan een navigational Drawer-component in de applicatie worden in- en uitgeschakeld door op het hamburger-menu te klikken.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Usages {#usages}

1. **Navigatiemenu**: Een veelvoorkomende toepassing van een drawer-component is als navigatiemenu. Het biedt een ruimte-efficiënte manier om links naar verschillende secties of pagina's van uw applicatie weer te geven, vooral in mobiele of responsieve lay-outs. Gebruikers kunnen de drawer openen en sluiten om toegang te krijgen tot navigatieopties zonder het hoofdinhoudgebied te rommelen.

2. **Filter en zijbalk**: Een drawer kan worden gebruikt als filter of zijbalk in applicaties die een lijst van items weergeven. Gebruikers kunnen de drawer uitbreiden om filteropties, sorteervakken of aanvullende informatie met betrekking tot de lijstitems te onthullen. Dit houdt de hoofdinhoud gefocust op de lijst terwijl geavanceerde functies op een toegankelijke manier worden geboden.

3. **Gebruikersprofiel of instellingen**: U kunt een drawer gebruiken om gebruikersprofielinformatie of applicatie-instellingen te tonen. Dit houdt dergelijke informatie gemakkelijk toegankelijk maar verborgen wanneer deze niet nodig is, wat zorgt voor een schone en opgeruimde interface. Gebruikers kunnen de drawer openen om hun profielen bij te werken of instellingen aan te passen.

4. **Meldingen**: Voor applicaties met meldingen of waarschuwingen kan een drawer naar binnen schuiven om nieuwe berichten of updates weer te geven. Gebruikers kunnen snel meldingen controleren en afwijzen zonder hun huidige weergave te verlaten.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Customization {#customization}

Er zijn verschillende eigenschappen die de aanpassing van verschillende attributen van de Drawer-component mogelijk maken. Deze sectie schetst die eigenschappen met voorbeelden voor hun wijziging.

## Autofocus {#autofocus}

De Autofocus-eigenschap is ontworpen om de toegankelijkheid en bruikbaarheid te verbeteren door automatisch de focus te leggen op het eerste item binnen een drawer wanneer deze wordt geopend. Deze functie elimineert de noodzaak voor gebruikers om handmatig naar het gewenste item te navigeren, waardoor tijd en moeite worden bespaard.

Wanneer de drawer wordt geopend, hetzij via een gebeurtenis, standaard of enige andere interactie, wordt de focus van de gebruiker gericht op het eerste item binnen de drawer. Dit eerste item kan een knop, een link, een menu-optie of een ander focusbaar element zijn.

:::tip
Door automatisch de focus op het eerste item te leggen, zorgt de ontwikkelaar ervoor dat gebruikers onmiddellijk betrokken kunnen raken bij de meest relevante of vaak gebruikte optie zonder door de hele drawer te hoeven tabben of scrollen. Dit gedrag stroomlijnt de gebruikerservaring en bevordert efficiënte navigatie binnen de UI.
:::

Deze eigenschap kan ook bijzonder nuttig zijn voor individuen die afhankelijk zijn van toetsenbordnavigatie of ondersteunende technologieën zoals schermlezers. Het biedt een duidelijk startpunt binnen de drawer en stelt gebruikers in staat om de gewenste functionaliteit te bereiken zonder onnodige handmatige invoer.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## Label {#label}

De Drawer Label-eigenschap is een functie die is ontworpen om de toegankelijkheid te verbeteren en een beschrijvende context voor een drawer binnen een gebruikersinterface te bieden. Deze eigenschap stelt ontwikkelaars in staat een label aan een drawer toe te wijzen, voornamelijk voor toegankelijkheidsdoeleinden, zodat schermlezers en andere ondersteunende technologieën de bedoeling en inhoud van de drawer nauwkeurig aan gebruikers kunnen overbrengen.

Wanneer de Drawer Label-eigenschap wordt gebruikt, wordt het toegewezen label een integraal onderdeel van de toegankelijkheidsinfrastructuur van de drawer. Het stelt gebruikers die afhankelijk zijn van ondersteunende technologieën in staat om de functie van de drawer te begrijpen en effectiever door de interface te navigeren.

Door een label aan de drawer te bieden, zorgen ontwikkelaars ervoor dat schermlezers de bedoeling van de drawer aankondigen aan visueel gehandicapte gebruikers. Deze informatie stelt individuen in staat om weloverwogen beslissingen te nemen over het interageren met de drawer, omdat ze de inhoud en relevantie binnen de bredere gebruikersinterface kunnen begrijpen.

De Label-eigenschap kan worden aangepast om te voldoen aan de specifieke context en ontwerpvereisten van de applicatie. Ontwikkelaars hebben de flexibiliteit om beknopte en beschrijvende labels te bieden die de inhoud of functionaliteit van de drawer nauwkeurig weergeven.

## Placement {#placement}

De plaatsingsproperty van de Drawer UI-component stelt ontwikkelaars in staat om de positie en uitlijning van de drawer binnen het viewport op te geven. Deze eigenschap biedt een reeks enum-waarden die flexibiliteit bieden bij het bepalen waar de drawer verschijnt in relatie tot de hoofdinhoud.

De beschikbare enum-waarden voor de plaatsingsproperty zijn als volgt:

- **TOP**: Deze waarde plaatst de drawer aan de bovenkant van het viewport, zodat deze het bovenste gebied kan innemen.

- **TOP_CENTER**: Met deze waarde wordt de drawer in het midden van het bovenste deel van het viewport gepositioneerd. Het is horizontaal in het midden uitgelijnd, wat een evenwichtige lay-out creëert.

- **BOTTOM**: Wanneer deze waarde wordt gebruikt, bevindt de drawer zich aan de onderkant van het viewport, onder de hoofdinhoud.

- **BOTTOM_CENTER**: Deze waarde centreert de drawer horizontaal aan de onderkant van het viewport. Het biedt een visueel evenwichtige compositie.

- **LEFT**: Door deze waarde te selecteren, wordt de drawer aan de linkerkant van het viewport geplaatst, naast de hoofdinhoud.

- **RIGHT**: Door deze waarde te gebruiken, wordt de drawer aan de rechterkant van het viewport geplaatst, dicht bij de hoofdinhoud.

De plaatsingsproperty stelt ontwikkelaars in staat om de meest geschikte positie voor de drawer te kiezen op basis van de specifieke ontwerp- en gebruikerservaringseisen. De enum-waarden bieden een verscheidenheid aan plaatsingsopties om verschillende interface-lay-outs en visuele hiërarchieën te accommoderen.

Door gebruik te maken van de plaatsingsproperty, kunnen ontwikkelaars intuïtieve en efficiënte gebruikersinterfaces creëren. Het plaatsen van de drawer aan de linker- of rechterkant zorgt bijvoorbeeld voor snelle toegang tot aanvullende functionaliteiten of navigatieopties, terwijl boven- of onderplaatsingen goed geschikt zijn voor contextuele informatie of aanvullende inhoud.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Drawer`-component, overweeg de volgende beste praktijken:

1. **Plaatsing**: Bepaal of de drawer van links, rechts, boven of beneden moet schuiven, afhankelijk van de lay-out van uw applicatie en overwegingen voor de gebruikerservaring. Overweeg gebruikersvoorkeuren en ontwerpconventies.

2. **Toegankelijkheid**: Besteed speciale aandacht aan toegankelijkheid. Zorg ervoor dat gebruikers de drawer kunnen openen en sluiten met toetsenbordbediening en dat schermlezers de aanwezigheid en staat ervan kunnen aankondigen. Bied ARIA-rollen en -labels indien nodig.

3. **Veeggebaren**: Ondersteun veeggebaren voor het openen en sluiten van de drawer op aanraakgevoelige apparaten. Dit is een intuïtieve manier voor gebruikers om ermee te interageren.
