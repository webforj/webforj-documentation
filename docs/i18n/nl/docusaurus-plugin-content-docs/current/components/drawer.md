---
title: Drawer
sidebar_position: 35
_i18n_hash: 7edd08525f20625cb8d891316111ebb3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De `Drawer`-component in webforJ creëert een schuifpaneel dat verschijnt vanaf de rand van het scherm en extra inhoud onthult zonder de huidige weergave te verlaten. Het wordt vaak gebruikt voor zij-navigatie, filtermenu's, gebruikersinstellingen of compacte meldingen die tijdelijk moeten verschijnen zonder de hoofdinterface te verstoren.

<!-- INTRO_END -->

## Stacking {#stacking}

Lades stapelen automatisch wanneer er meerdere zijn geopend, waardoor ze een flexibele keuze zijn voor interfaces met beperkte ruimte.

Het onderstaande voorbeeld toont dit gedrag binnen de [`AppLayout`](../components/app-layout) component. De navigatiedrawer die door het hamburgermenu wordt geactiveerd, is ingebouwd in [`AppLayout`](../components/app-layout), terwijl de welkomstpopup onderaan een zelfstandige `Drawer`-instantie gebruikt. Beide bestaan naast elkaar en stapelen onafhankelijk, waardoor wordt gedemonstreerd hoe Drawers kunnen worden geïntegreerd binnen lay-outcomponenten of als zelfstandige elementen kunnen worden gebruikt.

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/resources/static/css/drawer/drawerWelcome.css',
]}
/>

## Autofocus

De `Drawer`-component ondersteunt autofocus, wat automatisch de focus stelt op het eerste focusbare element wanneer de `Drawer` opent. Dit verbetert de bruikbaarheid door de aandacht direct naar het eerste actie-element te brengen.

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- Voorbeeld -->

## Label {#label}

De `setLabel()`-methode kan een betekenisvolle beschrijving geven van de inhoud binnen een `Drawer`. Wanneer een label wordt ingesteld, kunnen hulpmiddelen zoals schermlezers het aankondigen, waardoor gebruikers de functie van de `Drawer` begrijpen zonder de visuele inhoud te zien.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Taakbeheer");
```

:::tip Beschrijvende Labels
Gebruik beknopte en beschrijvende labels die het doel van de `Drawer` weerspiegelen. Vermijd generieke termen zoals “Menu” of “Paneel” wanneer een specifiekere naam kan worden gebruikt. 
:::

## Grootte

Om de grootte van een `Drawer` te regelen, stel je een waarde in voor de CSS-variabele `--dwc-drawer-size`. Dit stelt de breedte van de `Drawer` in voor links/rechts plaatsing of hoogte voor boven/onder plaatsing.

Je kunt de waarde definiëren met een geldige CSS-eenheid zoals een percentage, pixels of vw/vh, met Java of CSS:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
}
```

Om te voorkomen dat de `Drawer` te groot wordt, gebruik je `--dwc-drawer-max-size` naast deze:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
drawer.setStyle("--dwc-drawer-max-size", "800px");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
  --dwc-drawer-max-size: 800px;
}
```

## Plaatsing {#placement}

De `setPlacement()`-methode bepaalt waar de `Drawer` verschijnt in de viewport.

Beschikbare plaatsingsopties:

<!-- vale off -->
- **BOVEN**: Plaatst de lade aan de bovenrand van de viewport.
- **BOVEN_CENTRAAL**: Centreert de lade horizontaal aan de bovenkant van de viewport.
- **ONDER**: Plaatst de lade onderaan de viewport.
- **ONDER_CENTRAAL**: Centreert de lade horizontaal onderaan de viewport.
- **LINKS**: Plaatst de lade langs de linkerkant van de viewport.
- **RECHTS**: Plaatst de lade langs de rechterkant van de viewport.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Evenementafhandeling

De `Drawer`-component genereert levenscyclusgebeurtenissen die kunnen worden gebruikt om app-logica uit te voeren als reactie op wijzigingen in de open of gesloten staat. 

Ondersteunde gebeurtenissen:

- `DrawerOpenEvent`: Afgevuurd wanneer de lade volledig is geopend.
- `DrawerCloseEvent`: Afgevuurd wanneer de lade volledig is gesloten.

Je kunt luisteraars aan deze evenementen koppelen om logica uit te voeren wanneer de staat van de `Drawer` verandert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Behandel het geopend evenement van de lade
});

drawer.addCloseListener(e -> {
  // Behandel het gesloten evenement van de lade
});
```

## Voorbeeld: Contactkiezer

De `Drawer`-component toont extra inhoud zonder de huidige weergave te verstoren. Dit voorbeeld plaatst een lade in het midden onderaan, met een scrollbare contactlijst.

Iedere contactpersoon toont een avatar, naam, locatie en actieknop voor snelle toegang tot details of communicatie. Deze aanpak is goed voor het bouwen van compacte tools zoals contactkeizers, instellingenpanelen of meldingen.

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## Voorbeeld: Taakbeheerder

Dit voorbeeld gebruikt een `Drawer` als taakbeheerder. Je kunt taken toevoegen, markeren en voltooide taken wissen. De voettekst van de `Drawer` bevat formulierbedieningselementen om met de takenlijst te interageren, en de “Taak Toevoegen” [`Button`](../components/button) schakelt zichzelf uit als er 50 taken zijn bereikt.

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/resources/static/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## Stijling {#styling}

<TableBuilder name="Drawer" />
