---
title: Drawer
sidebar_position: 35
_i18n_hash: 51577f27568214c5d39e43b7e6ce42d0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De `Drawer` component in webforJ creëert een schuifpaneel dat verschijnt vanuit de rand van het scherm, waardoor extra inhoud zichtbaar wordt zonder de huidige weergave te verlaten. Het wordt vaak gebruikt voor zij-navigatie, filtermenu's, gebruikersinstellingen of compacte meldingen die tijdelijk moeten verschijnen zonder de hoofdinterface te verstoren.

<!-- INTRO_END -->

## Stapelen {#stacking}

Drawers stapelen automatisch wanneer er meerdere geopend zijn, waardoor ze een flexibele keuze zijn voor ruimte-beperkte interfaces.

Het voorbeeld hieronder toont dit gedrag binnen de [`AppLayout`](../components/app-layout) component. De navigatiedrawer die door het hamburger-menu wordt geactiveerd is ingebouwd in [`AppLayout`](../components/app-layout), terwijl de welkomstpopup onderaan een standalone `Drawer` instantie gebruikt. Beide coëxisteren en stapelen onafhankelijk, wat demonstreert hoe Drawers geïntegreerd kunnen worden binnen lay-outcomponenten of als standalone elementen gebruikt kunnen worden.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

De `Drawer` component ondersteunt autofocus, wat automatisch de focus instelt op het eerste focusbare element wanneer de `Drawer` opent. Dit verbetert de bruikbaarheid door de aandacht direct te vestigen op het eerste actie-element.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Voorbeeld -->

## Label {#label}

De `setLabel()` methode kan een betekenisvolle beschrijving geven van de inhoud binnen een `Drawer`. Wanneer een label is ingesteld, kunnen ondersteunende technologieën zoals schermlezers het aankondigen, waardoor gebruikers het doel van de `Drawer` kunnen begrijpen zonder de visuele inhoud te zien.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Taakbeheer");
```

:::tip Beschrijvende Labels
Gebruik kernachtige en beschrijvende labels die het doel van de `Drawer` reflecteren. Vermijd algemene termen zoals “Menu” of “Paneel” wanneer een specifiekere naam kan worden gebruikt.
:::

## Grootte

Om de grootte van een `Drawer` te beheersen, stel je een waarde in voor de CSS aangepaste eigenschap `--dwc-drawer-size`. Dit stelt de breedte van de `Drawer` in voor links/rechts plaatsing of hoogte voor boven/beneden plaatsing.

Je kunt de waarde definiëren met elke geldige CSS-eenheid zoals een percentage, pixels of vw/vh, met Java of CSS:

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

Om te voorkomen dat de `Drawer` te groot wordt, gebruik je `--dwc-drawer-max-size` samen met deze:

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

De `setPlacement()` methode controleert waar de `Drawer` verschijnt in de viewport.

Beschikbare plaatsingsopties:

<!-- vale off -->
- **BOVEN**: Plaatst de drawer aan de bovenrand van de viewport.
- **BOVEN_CENTRAAL**: Centreert de drawer horizontaal aan de bovenkant van de viewport.
- **ONDER**: Plaatst de drawer aan de onderkant van de viewport.
- **ONDER_CENTRAAL**: Centreert de drawer horizontaal aan de onderkant van de viewport.
- **LINKS**: Plaatst de drawer langs de linkerrand van de viewport.
- **RECHTS**: Plaatst de drawer langs de rechterrand van de viewport.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Evenementverwerking

De `Drawer` component genereert levenscyclusgebeurtenissen die kunnen worden gebruikt om app-logica te triggeren in reactie op veranderingen in zijn open of gesloten status.

Ondersteunde evenementen:

- `DrawerOpenEvent`: Geactiveerd wanneer de drawer volledig geopend is.
- `DrawerCloseEvent`: Geactiveerd wanneer de drawer volledig gesloten is.

Je kunt luisteraars aan deze evenementen toevoegen om logica uit te voeren wanneer de status van de `Drawer` verandert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Verwerk geopend evenement van de drawer
});

drawer.addCloseListener(e -> {
  // Verwerk gesloten evenement van de drawer
});
```

## Voorbeeld: Contactpicker

De `Drawer` component toont extra inhoud zonder de huidige weergave te verstoren. Dit voorbeeld plaatst een drawer in het onderste midden, met een scrollbare contactenlijst.

Elke contact toont een avatar, naam, locatie en actieknop voor snelle toegang tot details of communicatie. Deze aanpak werkt goed voor het bouwen van compacte tools zoals contactpickers, instellingenpanelen of meldingen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Voorbeeld: Taakbeheerder

Dit voorbeeld gebruikt een `Drawer` als taakbeheerder. Je kunt taken toevoegen, afvinken en voltooide verwijderen. De footer van de `Drawer` bevat formulierelementen om interactie te hebben met de takenlijst, en de “Taak Toevoegen” [`Button`](../components/button) schakelt zichzelf uit als er 50 taken zijn bereikt.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
cssURL='/css/drawer/drawer-task-view.css'
height='600px'
/>

## Stijling {#styling}

<TableBuilder name="Drawer" />
