---
title: Drawer
sidebar_position: 35
_i18n_hash: 1ac0b2efc50e748c9fd18f92de8d0e6e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De `Drawer` component in webforJ creëert een schuifpaneel dat verschijnt vanaf de rand van het scherm, waardoor extra inhoud zichtbaar wordt zonder de huidige weergave te verlaten. Het wordt vaak gebruikt voor zij-navigatie, filtermenu's, gebruikersinstellingen of compacte meldingen die tijdelijk moeten verschijnen zonder de hoofdinvoer te verstoren.

`Drawers` stapelen automatisch wanneer er meerdere geopend zijn, wat ze een flexibele keuze maakt voor interfaces met beperkte ruimte.

Het onderstaande voorbeeld toont dit gedrag binnen de [`AppLayout`](../components/app-layout) component. De navigatie-drawer die door het hamburger-menu wordt geactiveerd, is ingebouwd in [`AppLayout`](../components/app-layout), terwijl de welkomstpop-up onderaan een zelfstandige `Drawer` instantie gebruikt. Beide bestaan naast elkaar en stapelen onafhankelijk, wat demonstreert hoe `Drawers` geïntegreerd kunnen worden binnen lay-outcomponenten of als zelfstandige elementen gebruikt kunnen worden.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

De `Drawer` component ondersteunt autofocus, wat automatisch de focus op het eerste focusbare element instelt wanneer de `Drawer` opent. Dit verbetert de bruikbaarheid door de aandacht direct naar het eerste actie-element te brengen.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Voorbeeld -->

## Label {#label}

De `setLabel()` methode kan een betekenisvolle beschrijving geven van de inhoud binnen een `Drawer`. Wanneer er een label is ingesteld, kunnen ondersteunende technologieën zoals schermlezers dit aankondigen, waardoor gebruikers de bedoeling van de `Drawer` begrijpen zonder de visuele inhoud te zien.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Taakmanager");
```

:::tip Beschrijvende Labels
Gebruik beknopte en beschrijvende labels die het doel van de `Drawer` reflecteren. Vermijd algemene termen zoals “Menu” of “Paneel” wanneer er een specifiekere naam kan worden gebruikt.
:::

## Grootte

Om de grootte van een `Drawer` te beheersen, stel je een waarde in voor de CSS aangepaste eigenschap `--dwc-drawer-size`. Dit stelt de breedte van de `Drawer` in voor links/rechts plaatsing of de hoogte voor boven/onder plaatsing.

Je kunt de waarde definiëren met elke geldige CSS-eenheid zoals een percentage, pixels of vw/vh, met behulp van Java of CSS:

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

Om te voorkomen dat de `Drawer` te groot wordt, gebruik je `--dwc-drawer-max-size` ernaast:

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

De `setPlacement()` methode bepaalt waar de `Drawer` in het viewport verschijnt.

Beschikbare plaatsingsopties:

<!-- vale off -->
- **TOP**: Plaatst de drawer aan de bovenrand van het viewport.
- **TOP_CENTER**: Centreert de drawer horizontaal aan de bovenkant van het viewport.
- **BOTTOM**: Plaatst de drawer aan de onderkant van het viewport.
- **BOTTOM_CENTER**: Centreert de drawer horizontaal aan de onderkant van het viewport.
- **LEFT**: Plaatst de drawer langs de linkerrand van het viewport.
- **RIGHT**: Plaatst de drawer langs de rechterrand van het viewport.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Evenementafhandeling

De `Drawer` component geeft lifecycle-evenementen uit die gebruikt kunnen worden om app-logica te activeren in reactie op veranderingen in zijn open of gesloten status.

Ondersteunde evenementen:

- `DrawerOpenEvent`: Geactiveerd wanneer de drawer volledig is geopend.
- `DrawerCloseEvent`: Geactiveerd wanneer de drawer volledig is gesloten.

Je kunt luisteraars aan deze evenementen koppelen om logica uit te voeren wanneer de status van de `Drawer` verandert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Behandel drawer geopend evenement
});

drawer.addCloseListener(e -> {
  // Behandel drawer gesloten evenement
});
```

## Voorbeeld: Contactkeuze

De `Drawer` component biedt extra inhoud zonder de huidige weergave te verstoren. Dit voorbeeld plaatst een drawer in het midden van de onderkant, met een scrollbare contactenlijst.

Elke contactpersoon toont een avatar, naam, locatie en actieknop voor snelle toegang tot details of communicatie. Deze aanpak werkt goed voor het bouwen van compacte hulpmiddelen zoals contactkeuzers, instellingenpanelen of meldingen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Voorbeeld: Taakmanager

Dit voorbeeld gebruikt een `Drawer` als taakmanager. Je kunt taken toevoegen, afvinken en voltooide taken wissen. De voettekst van de `Drawer` bevat formulierbedieningselementen om met de takenlijst te interageren, en de “Taak Toevoegen” [`Button`](../components/button) schakelt zichzelf uit als er 50 taken zijn bereikt.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
