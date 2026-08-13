---
title: Packages and assets
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Declare npm packages, load a module straight from one, install build-only
  dependencies, rely on tree shaking, and import CSS and assets from a
  component's entry.
_i18n_hash: 3b538e3785ee74397f156dd38ef8506a
---
Een invoer gebruikt meer dan zijn eigen bron. Het importeert pakketten van npm, laadt een module rechtstreeks van een, trekt een stylesheet of een afbeelding binnen, en kan afkomstig zijn van een klasse die je uitbreidt of een bibliotheek waar je van afhankelijk bent. Deze pagina behandelt hoe die onderdelen de build bereiken.

## Een pakket declareren {#declaring-a-package}

`@BundlePackage` verklaart een npm-afhankelijkheid die een invoer importeert. De build verzamelt elke declaratie op het classpath en voegt deze toe aan je project’s [`package.json`](https://docs.npmjs.com/cli/v11/configuring-npm/package-json), vervolgens installeert [Bun](https://bun.sh/) het voordat het compileert, zodat een pakket aanwezig is tegen de tijd dat zijn invoer compileert. Je eigen aanpassingen aan dat bestand worden behouden, en een project dat geen pakketten declareert en geen eigen `package.json` heeft, houdt er ook geen, zodat npm buiten een build blijft die het niet nodig heeft.

Een webcomponentenbibliotheek is het meest voorkomende geval. Declareer het pakket en wijs vervolgens `@BundleEntry` naar de componentmodules die je wilt:

```java title="Ui5View.java"
@Route("/ui5")
@BundlePackage(value = "@ui5/webcomponents", version = "^2.0.0")
@BundleEntry("@ui5/webcomponents/dist/Button.js")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
public class Ui5View extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public Ui5View() {
    self.setAlignment(FlexAlignment.CENTER)
      .setStyle("margin", "1em");

    Element input = new Element("ui5-input");
    input.setProperty("placeholder", "Typ iets");

    Element button = new Element("ui5-button");
    button.setProperty("design", "Emphasized");
    button.setText("Zeg hallo");
    button.addEventListener("click", e ->
      Toast.show("Hallo " + input.getProperty("value"))
    );

    self.add(input, button);
  }
}
```

De `version` volgt de npm-versiesyntaxis, zodat `^2.0.0` compatibele 2.x-releases accepteert. Zowel `@BundlePackage` als `@BundleEntry` zijn herhaalbaar, zodat een klasse zoveel pakketten declareert en zoveel modules laadt als nodig is.

### Een bestandinvoer of een npm-module {#a-file-entry-or-an-npm-module}

De waarde van `@BundleEntry` is een van twee dingen: een pad naar een bestand dat je onder `src/main/frontend` hebt geschreven, of een pad naar een module binnen een npm-pakket. De weergave hierboven geeft modulepaden binnen `@ui5/webcomponents` aan, dus het bevat geen eigen bronbestand. Elk van die modules registreert zijn eigen aangepaste element wanneer het laadt, wat de reden is dat de weergave `ui5-input` en `ui5-button` zonder wrapper consumeert. Een bestandinvoer wijst in plaats daarvan naar een `.ts`, `.js` of `.css` bestand dat je hebt geschreven, en gecompileerd op dezelfde manier.

### Buildafhankelijkheden {#build-dependencies}

Een pakket dat alleen nodig is om de bronnen te compileren, niet tijdens runtime, is een buildafhankelijkheid. Stel `dev = true` in op `@BundlePackage`, en de build installeert het als een `devDependency`:

```java
@BundlePackage(value = "typescript", version = "^5.0.0", dev = true)
```

De gecureerde extensies gebruiken dit voor de pakketten die hun compilers nodig hebben, wat de reden is dat een SCSS-bron `sass` als buildafhankelijkheid binnenhaalt en niets tijdens runtime.

## Alleen wat je importeert {#tree-shaking}

De compiler omvat alleen de code die een invoer daadwerkelijk importeert. Naming `@ui5/webcomponents/dist/Button.js` trekt de Button-component en wat deze nodig heeft binnen, niet de rest van de bibliotheek. Een brede bibliotheek kost alleen de delen waar je naar grijpt, zodat er geen straf is voor het declareren van een groot pakket en het laden van één module eruit.

### Gedeelde code {#shared-code}

Wanneer twee invoeren hetzelfde pakket importeren, faktoriseert de build de gedeelde code in een chunk die beide laden, in plaats van deze in elk te kopiëren. Verschillende componenten die op dezelfde bibliotheek zijn gebouwd, een set Lit-elementen bijvoorbeeld, delen de code van die bibliotheek op een pagina in plaats van ervoor te betalen per element.

## Hoe invoeren laden {#how-entries-load}

Een invoer produceert een script, een stylesheet of beide, en de runtime laadt die output de eerste keer dat een component van zijn klasse wordt gemaakt, waar die component ook wordt gebruikt en hoe diep het ook genest is. Een gerouteerde weergave en een lay-out zijn componenten zoals elke andere, zodat een invoer bindt aan componentcreatie, niet aan routing. Twee details volgen uit de annotatie die op de klasse leeft:

- **Overerving.** `@BundleEntry` en `@BundlePackage` worden geërfd. Een basis klasse declareert de invoer, en een subclass die niets van zijn eigen toevoegt, laadt het nog steeds.
- **Debug-invoeren.** Een invoer die is gedeclareerd als `@BundleEntry(value = "...", debug = true)` laadt alleen wanneer de app in de debugmodus draait, wat geschikt is voor alleen diagnostiek tijdens ontwikkeling.

## CSS en activa importeren {#importing-css-and-assets}

Een invoer van een component behandelt stylesheets en andere activa via imports, zonder annotatie en zonder extensie. Bun lost ze op compileertijd op.

Importeer een stylesheet om de bijwerkingseffecten, en de bundelaar voegt deze toe aan de stijlen van de invoer. Import een niet-code activa, en de import geeft je een URL om te gebruiken:

```ts title="card/card.ts"
import './card.css';
import logoPath from './logo.svg';

const logo = new URL(logoPath, import.meta.url).href;
// gebruik logo als een afbeeldingsbron binnen het element
```

Los een actieve URL op tegen `import.meta.url`, niet het document, zodat het wijst naar de gecompileerde activa, waar de output ook wordt aangeboden.

Importeer een stylesheet als tekst in plaats daarvan, en pas deze aan binnen een schaduworoot om de stijlen aan één element te scopen:

```ts title="swatch/swatch.ts"
import sheet from './swatch.css' with { type: 'text' };

class ColorSwatch extends HTMLElement {
  connectedCallback() {
    const root = this.attachShadow({ mode: 'open' });
    const style = document.createElement('style');
    style.textContent = sheet;
    root.append(style);
  }
}
```

Een invoer kan ook een gewoon `.css`-bestand zijn zonder script, gebonden aan een klasse op dezelfde manier als een scriptinvoer. De runtime laadt het als stijlen voor de weergave:

```java title="ThemeView.java"
@Route("/theme")
@BundleEntry("theme/theme.css")
public class ThemeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ThemeView() {
    self.add(new Div("Gestyled door een alleen CSS bundle invoer")
                 .addClassName("themed-label"));
  }
}
```
