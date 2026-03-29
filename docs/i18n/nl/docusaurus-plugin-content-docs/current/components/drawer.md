---
title: Drawer
sidebar_position: 35
_i18n_hash: d0c9ff09e591673c99918aa6875af28a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

De `Drawer` component in webforJ creëert een schuifpaneel dat verschijnt vanaf de rand van het scherm, waardoor aanvullende inhoud zichtbaar wordt zonder de huidige weergave te verlaten. Het wordt vaak gebruikt voor zij-navigatie, filtermenu's, gebruikersinstellingen of compacte meldingen die tijdelijk moeten verschijnen zonder de hoofdinterface te verstoren.

<!-- INTRO_END -->

## Stapelen {#stacking}

Lades stapelen automatisch wanneer meerdere zijn geopend, wat ze een flexibele keuze maakt voor ruimtestrikte interfaces.

Het onderstaande voorbeeld laat dit gedrag zien binnen de [`AppLayout`](../components/app-layout) component. De navigatielade die door het hamburgermenu wordt geactiveerd, is ingebouwd in [`AppLayout`](../components/app-layout), terwijl de welkomstpopup onderaan een standalone `Drawer` instantie gebruikt. Beide bestaan naast elkaar en stapelen onafhankelijk, wat demonstreert hoe Lades kunnen worden geïntegreerd binnen lay-outcomponenten of als standalone elementen kunnen worden gebruikt.

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

De `setLabel()` methode kan een betekenisvolle omschrijving van de inhoud binnen een `Drawer` geven. Wanneer een label is ingesteld, kunnen ondersteunende technologieën zoals schermlezers het aankondigen, wat gebruikers helpt de bedoeling van de `Drawer` te begrijpen zonder de visuele inhoud ervan te zien.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Taakbeheerder");
```

:::tip Beschrijvende Labels
Gebruik beknopte en beschrijvende labels die de bedoeling van de `Drawer` weerspiegelen. Vermijd generieke termen zoals "Menu" of "Paneel" wanneer een specifieker naam kan worden gebruikt.
:::

## Grootte

Om de grootte van een `Drawer` te beheersen, stel een waarde in voor de CSS aangepaste eigenschap `--dwc-drawer-size`. Dit stelt de breedte van de `Drawer` in voor links/rechts plaatsing of hoogte voor boven/onder plaatsing.

Je kunt de waarde definiëren met behulp van elke geldige CSS-eenheid, zoals een percentage, pixels of vw/vh, met behulp van Java of CSS:

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

Om te voorkomen dat de `Drawer` te groot wordt, gebruik `--dwc-drawer-max-size` naast het volgende:

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

De `setPlacement()` methode bepaalt waar de `Drawer` in de viewport verschijnt.

Beschikbare plaatsingsopties:

<!-- vale off -->
- **BOVEN**: Plaatst de lade aan de bovenrand van de viewport.
- **BOVEN_CENTRAAL**: Stelt de lade horizontaal gecentreerd aan de bovenkant van de viewport.
- **ONDER**: Plaatst de lade aan de onderkant van de viewport.
- **ONDER_CENTRAAL**: Centrert de lade horizontaal aan de onderkant van de viewport.
- **LINKS**: Plaatst de lade langs de linkerrand van de viewport.
- **RECHTS**: Plaatst de lade langs de rechterrand van de viewport.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Evenementafhandeling

De `Drawer` component genereert levenscyclusgebeurtenissen die kunnen worden gebruikt om logica in de app te activeren in reactie op wijzigingen in de open of gesloten status. 

Ondersteunde gebeurtenissen:

- `DrawerOpenEvent`: Wordt geactiveerd wanneer de lade volledig is geopend.
- `DrawerCloseEvent`: Wordt geactiveerd wanneer de lade volledig is gesloten.

Je kunt listeners aan deze gebeurtenissen koppelen om logica uit te voeren wanneer de status van de `Drawer` verandert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Verwerk evenement 'lade geopend'
});

drawer.addCloseListener(e -> {
  // Verwerk evenement 'lade gesloten'
});
```

## Voorbeeld: Contactkiezer

De `Drawer` component biedt extra inhoud zonder de huidige weergave te verstoren. Dit voorbeeld plaatst een lade in het midden onderaan, met een scrollbare contactlijst.

Elke contactpersoon toont een avatar, naam, locatie en actieknop voor snelle toegang tot details of communicatie. Deze aanpak werkt goed voor het bouwen van compacte hulpmiddelen zoals contactpickers, instellingenpanelen of meldingen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Voorbeeld: Taakbeheerder

Dit voorbeeld gebruikt een `Drawer` als taakbeheerder. Je kunt taken toevoegen, afvinken en voltooide taken wissen. De voettekst van de `Drawer` bevat formulierbedieningen om met de takenlijst te interageren, en de “Taak Toevoegen” [`Button`](../components/button) schakelt zichzelf uit als er 50 taken zijn bereikt.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Stijling {#styling}

<TableBuilder name="Drawer" />
