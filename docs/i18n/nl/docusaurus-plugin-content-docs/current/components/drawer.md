---
title: Drawer
sidebar_position: 35
sidebar_class_name: updated-content
_i18n_hash: a19d1b8c8e0b74cecee529e86649d449
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De `Drawer` component in webforJ creëert een schuifpaneel dat verschijnt vanuit de rand van het scherm en extra inhoud onthult zonder de huidige weergave te verlaten. Het wordt vaak gebruikt voor zij-navigatie, filtermenu's, gebruikersinstellingen of compacte meldingen die tijdelijk moeten verschijnen zonder de hoofdinterface te verstoren.

`Drawers` stapelen automatisch wanneer meerdere zijn geopend, waardoor ze een flexibele keuze zijn voor interfaces met beperkte ruimte.

Het onderstaande voorbeeld toont dit gedrag binnen de [`AppLayout`](../components/app-layout) component. De navigatiedrawer die door het hamburger-menu wordt geactiveerd, is ingebouwd in de [`AppLayout`](../components/app-layout), terwijl de welkomstpopup onderaan een zelfstandige `Drawer`-instantie gebruikt. Beide coëxisteren en stapelen onafhankelijk, waarmee wordt gedemonstreerd hoe `Drawers` kunnen worden geïntegreerd binnen lay-outcomponenten of als zelfstandige elementen kunnen worden gebruikt.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

De `Drawer` component ondersteunt autofocus, dat automatisch de focus instelt op het eerste focusbare element wanneer de `Drawer` opent. Dit verbetert de bruikbaarheid door de aandacht direct naar het eerste actieverelement te brengen.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Voorbeeld -->

## Label {#label}

De `setLabel()` methode kan een betekenisvolle beschrijving geven van de inhoud binnen een `Drawer`. Wanneer een label is ingesteld, kunnen ondersteunende technologieën zoals schermlezers dit aankondigen, wat gebruikers helpt om het doel van de `Drawer` te begrijpen zonder de visuele inhoud te zien.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Taakbeheer");
```

:::tip Beschrijvende Labels
Gebruik beknopte en beschrijvende labels die het doel van de `Drawer` weerspiegelen. Vermijd algemene termen zoals “Menu” of “Paneel” als een specifiekere naam kan worden gebruikt.
:::

## Grootte

Om de grootte van een `Drawer` te beheersen, stel je een waarde in voor de CSS aangepaste eigenschap `--dwc-drawer-size`. Dit stelt de breedte van de `Drawer` in voor links/rechts plaatsing of hoogte voor boven/onder plaatsing.

Je kunt de waarde definiëren met elk geldig CSS-eenheid, zoals een percentage, pixels, of vw/vh, met behulp van Java of CSS:

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

## Plaatsing

De `setPlacement()` methode controleert waar de `Drawer` in het viewport verschijnt.

Beschikbare plaatsingsopties:

<!-- vale off -->
- **BOVEN**: Plaatst de drawer aan de bovenrand van het viewport.
- **BOVEN_CENTER**: Lijnt de drawer horizontaal gecentreerd aan de bovenkant van het viewport.
- **ONDER**: Plaatst de drawer aan de onderkant van het viewport.
- **ONDER_CENTER**: Centreert de drawer horizontaal aan de onderkant van het viewport.
- **LINKS**: Plaatst de drawer langs de linkerrand van het viewport.
- **RECHTS**: Plaatst de drawer langs de rechterrand van het viewport.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Gebeurtenisafhandeling

De `Drawer` component genereert levenscyclusgebeurtenissen die kunnen worden gebruikt om logica in de app te activeren in reactie op veranderingen in de open of gesloten staat.

Ondersteunde gebeurtenissen:

- `DrawerOpenEvent`: Wordt geactiveerd wanneer de drawer volledig is geopend.
- `DrawerCloseEvent`: Wordt geactiveerd wanneer de drawer volledig is gesloten.

Je kunt luisteraars aan deze gebeurtenissen koppelen om logica te draaien wanneer de status van de `Drawer` verandert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Behandel geopende drawer gebeurtenis
});

drawer.addCloseListener(e -> {
  // Behandel gesloten drawer gebeurtenis
});
```

## Voorbeeld: Contactkiezer

De `Drawer` component biedt extra inhoud zonder de huidige weergave te verstoren. Dit voorbeeld plaatst een drawer in het midden onderaan, met een scrollbare contactlijst.

Elke contactpersoon toont een avatar, naam, locatie en actieknop voor snelle toegang tot details of communicatie. Deze aanpak werkt goed bij het bouwen van compacte tools zoals contactpickers, instellingenpanelen of meldingen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Voorbeeld: Taakbeheerder

Dit voorbeeld gebruikt een `Drawer` als taakbeheerder. Je kunt taken toevoegen, afvinken en voltooide taken wissen. De footer van de `Drawer` bevat formulierbedieningen om met de takenlijst te interageren, en de “Taak Toevoegen” [`Button`](../components/button) schakelt zichzelf uit als er 50 taken zijn bereikt.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Stijlen {#styling}

<TableBuilder name="Drawer" />
