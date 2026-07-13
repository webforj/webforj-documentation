---
title: Drawer
sidebar_position: 35
description: >-
  Slide panels in from any screen edge with the Drawer component, ideal for side
  navigation, filter menus, settings, and stackable popovers.
_i18n_hash: c04ddb6b8576656535eb1fa09ea587e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De `Drawer`-component in webforJ creëert een schuifpaneel dat verschijnt vanuit de rand van het scherm, waardoor extra inhoud zichtbaar wordt zonder de huidige weergave te verlaten. Het wordt vaak gebruikt voor zij-navigatie, filtermenu's, gebruikersinstellingen of compacte meldingen die tijdelijk moeten verschijnen zonder de hoofdinterface te verstoren.

<!-- INTRO_END -->

## Stacking {#stacking}

Laden stapelen automatisch wanneer meerdere geopend zijn, waardoor ze een flexibele keuze zijn voor interfaces met beperkte ruimte.

Het onderstaande voorbeeld laat dit gedrag zien binnen de [`AppLayout`](../components/app-layout) component. De navigatielade die door het hamburger-menu wordt geactiveerd, is ingebouwd in [`AppLayout`](../components/app-layout), terwijl de welkomstpopup onderaan een zelfstandige `Drawer`-instantie gebruikt. Beide bestaan naast elkaar en stapelen zich onafhankelijk, wat aantoont hoe Laden geïntegreerd kunnen worden binnen lay-outcomponenten of gebruikt kunnen worden als zelfstandige elementen.

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/frontend/css/drawer/drawerWelcome.css',
]}
/>

## Autofocus {#autofocus}

De `Drawer`-component ondersteunt autofocus, die automatisch de focus instelt op het eerste focusbare element wanneer de `Drawer` opent. Dit verbetert de bruikbaarheid door de aandacht direct te vestigen op het eerste actievere element.

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- Voorbeeld -->

## Label {#label}

De `setLabel()`-methode kan een betekenisvolle beschrijving geven van de inhoud binnen een `Drawer`. Wanneer er een label is ingesteld, kunnen ondersteunende technologieën zoals schermlezers dit aankondigen, waardoor gebruikers de functie van de `Drawer` kunnen begrijpen zonder de visuele inhoud te zien.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Taakbeheer");
```

:::tip Beschrijvende Labels
Gebruik beknopte en beschrijvende labels die de functie van de `Drawer` weerspiegelen. Vermijd generieke termen zoals “Menu” of “Paneel” wanneer een specifiekere naam kan worden gebruikt.
:::

## Grootte {#size}

Om de grootte van een `Drawer` te regelen, stel je een waarde in voor de CSS-aangepaste eigenschap `--dwc-drawer-size`. Dit stelt de breedte van de `Drawer` in voor links/rechts plaatsing of hoogte voor boven/beneden plaatsing.

Je kunt de waarde definiëren met behulp van een geldige CSS-eenheid zoals een percentage, pixels of vw/vh, zowel met Java als CSS:

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
- **TOP**: Plaatst de lade aan de bovenrand van de viewport.
- **TOP_CENTER**: Lijnt de lade horizontaal gecentreerd aan de bovenkant van de viewport.
- **BOTTOM**: Plaatst de lade aan de onderkant van de viewport.
- **BOTTOM_CENTER**: Centreert de lade horizontaal aan de onderkant van de viewport.
- **LEFT**: Plaatst de lade langs de linker rand van de viewport.
- **RIGHT**: Plaatst de lade langs de rechter rand van de viewport.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Evenementafhandeling {#event-handling}

De `Drawer`-component zendt lifecycle-gebeurtenissen uit die gebruikt kunnen worden om app-logica te activeren in reactie op wijzigingen in de open of gesloten status.

Ondersteunde evenementen:

- `DrawerOpenEvent`: Vuur als de lade volledig is geopend.
- `DrawerCloseEvent`: Vuur als de lade volledig is gesloten.

Je kunt luisteraars aan deze evenementen koppelen om logica uit te voeren wanneer de status van de `Drawer` verandert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Behandel evenement lade geopend
});

drawer.addCloseListener(e -> {
  // Behandel evenement lade gesloten
});
```

## Voorbeeld: Contactkiezer {#example-contact-picker}

De `Drawer`-component exposeert extra inhoud zonder de huidige weergave te storen. Dit voorbeeld plaatst een lade in het midden onderaan, met een scrollbare contactlijst.

Elke contactpersoon toont een avatar, naam, locatie en actieknop voor snelle toegang tot details of communicatie. Deze aanpak werkt goed voor het bouwen van compacte hulpmiddelen zoals contactpickers, instellingenpanelen of meldingen.

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## Voorbeeld: Taakbeheer {#example-task-manager}

Dit voorbeeld gebruikt een `Drawer` als taakbeheerder. Je kunt taken toevoegen, afvinken en voltooide taken wissen. De voettekst van de `Drawer` bevat formulierbesturingselementen om met de takenlijst te interageren, en de knop “Taak Toevoegen” [`Button`](../components/button) schakelt zichzelf uit als er 50 taken zijn bereikt.

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/frontend/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## Stijlgeving {#styling}

<TableBuilder name="Drawer" />
