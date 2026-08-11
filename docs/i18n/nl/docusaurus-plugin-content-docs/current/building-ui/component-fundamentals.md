---
sidebar_position: 2
title: Understanding Components
description: >-
  Understand the webforJ component hierarchy, composition over inheritance,
  lifecycle stages, and concern interfaces before building custom components.
_i18n_hash: 7eff2c4778d4f2f95f0390d5a4ef7fbd
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Voordat je aangepaste componenten in webforJ bouwt, is het belangrijk om de fundamentele architectuur te begrijpen die bepaalt hoe componenten werken. Dit artikel legt de componenthiërarchie, componentidentiteit, levenscyclusconcepten en hoe concerninterfaces componentmogelijkheden bieden uit.

## Begrijpen van de componenthiërarchie {#understanding-the-component-hierarchy}

webforJ organiseert componenten in een hiërarchie met twee groepen: interne klassen van het framework die je nooit moet uitbreiden, en klassen die specifiek ontworpen zijn voor het bouwen van aangepaste componenten. Dit gedeelte legt uit waarom webforJ compositie boven extensie gebruikt en wat elk niveau van de hiërarchie biedt.

### Waarom compositie in plaats van extensie? {#why-composition-instead-of-extension}

In webforJ zijn ingebouwde componenten zoals [`Button`](../components/button) en [`TextField`](../components/fields/textfield) finale klassen—je kunt ze niet uitbreiden:

```java
// Dit werkt niet in webforJ
public class MyButton extends Button {
  // Button is final - kan niet worden uitgebreid
}
```

webforJ gebruikt **compositie boven extensie**. In plaats van bestaande componenten uit te breiden, maak je een klasse die `Composite` uitbreidt en combineert componenten erin. `Composite` fungeert als een container die een enkele component omhult (de gebonden component genoemd) en stelt je in staat om je eigen componenten en gedrag eraan toe te voegen.

```java
public class SearchBar extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField searchField;
  private Button searchButton;

  public SearchBar() {
    searchField = new TextField("Zoeken");
    searchButton = new Button("Ga");

    self.setDirection(FlexDirection.ROW)
      .add(searchField, searchButton);
  }
}
```

### Waarom je ingebouwde componenten niet kunt uitbreiden {#why-you-cant-extend-built-in-components}

webforJ-componenten zijn gemarkeerd als final om de integriteit van de onderliggende client-side webcomponent te behouden. Het uitbreiden van webforJ-componentklassen zou controle geven over de onderliggende webcomponent, wat onbedoelde gevolgen kan hebben en de consistentie en voorspelbaarheid van het componentgedrag kan verstoren.

Voor een gedetailleerde uitleg, zie [Finale Klassen en Uitbreidingsbeperkingen](https://docs.webforj.com/docs/architecture/controls-components#final-classes-and-extension-restrictions) in de architectuurdocumentatie.

### De componenthiërarchie {#the-component-hierarchy}

<div style={{textAlign: 'center'}}>
```mermaid
graph TD
  A[Component<br/><small>Abstracte basis - interne framework</small>]

  A --> B[DwcComponent<br/><small>Ingebouwde webforJ-componenten</small>]
  A --> C[Composite<br/><small>Combineer webforJ-componenten</small>]

  B --> E[Button, TextField,<br/>DateField, ComboBox]

  C --> D[ElementComposite<br/><small>Wrap web-componenten</small>]
  D --> F[ElementCompositeContainer<br/><small>Componenten met slots</small>]

  classDef internal stroke-dasharray:6 4,stroke-width:1px
  classDef primary stroke-width:3px
  classDef secondary stroke-width:2px,stroke-dasharray:2 2
  class A,B,E internal
  class C primary
  class D,F secondary
```
</div>

Klassen voor ontwikkelaars (gebruik deze):
- `Composite`
- `ElementComposite`
- `ElementCompositeContainer`

Interne frameworkklassen (nooit direct uitbreiden):
- `Component`
- `DwcComponent`

:::warning[Never extend `Component` or `DwcComponent`]
Verleng `Component` of `DwcComponent` nooit direct. Alle ingebouwde componenten zijn final. Gebruik altijd compositiepatronen met `Composite` of `ElementComposite`.

Proberen `DwcComponent` uit te breiden zal een runtime-exceptie opleveren.
:::

## Concerninterfaces {#concern-interfaces}

Concerninterfaces zijn Java-interfaces die specifieke mogelijkheden aan je componenten bieden. Elke interface voegt een set gerelateerde methoden toe. Bijvoorbeeld, `HasSize` voegt methoden toe voor het beheersen van breedte en hoogte, terwijl `HasFocus` methoden toevoegt voor het beheren van de focusstatus.

Wanneer je een concerninterface op je component implementeert, krijg je toegang tot die mogelijkheden zonder implementatiecode te hoeven schrijven. De interface biedt standaardimplementaties die automatisch werken.

Het implementeren van concerninterfaces geeft je aangepaste componenten dezelfde API's als ingebouwde webforJ-componenten:

```java
// Implementeer HasSize om breedte/hoge methoden automatisch te krijgen
public class SizedCard extends Composite<Div> implements HasSize<SizedCard> {
  private final Div self = getBoundComponent();

  public SizedCard() {
    self.setText("Inhoud kaart");
  }

  // Geen behoefte om deze te implementeren - je krijgt ze gratis:
  // setWidth(), setHeight(), setSize()
}

// Gebruik het zoals elke webforJ-component
SizedCard card = new SizedCard();
card.setWidth("300px")
  .setHeight("200px");
```

De samenstelling stuurt deze oproepen automatisch door naar de onderliggende `Div`. Geen extra code nodig.

### Uiterlijk {#concern-interfaces-appearance}

Deze interfaces regelen de visuele presentatie van een component, inclusief zijn afmetingen, zichtbaarheid, stijl en thema.

| Interface | Beschrijving |
|---|---|
| `HasSize` | Beheert breedte en hoogte, inclusief min- en maxbeperkingen. Extends `HasWidth`, `HasHeight` en hun min/max varianten. |
| `HasVisibility` | Toont of verbergt de component zonder deze uit de lay-out te verwijderen. |
| `HasClassName` | Beheert CSS-klassennamen op het root-element van de component. |
| `HasStyle` | Past inline CSS-stijlen toe en verwijdert deze. |
| `HasHorizontalAlignment` | Beheert hoe de inhoud horizontaal binnen de component is uitgelijnd. |
| `HasExpanse` | Stelt de groottevariant van de component in met de standaard expanse-tokens (`XSMALL` tot `XLARGE`). |
| `HasTheme` | Past een themavariant toe zoals `DEFAULT`, `PRIMARY` of `DANGER`. |
| `HasPrefixAndSuffix` | Voegt componenten toe aan de prefix- of suffix-slot binnen de component. |

### Inhoud {#concern-interfaces-content}

Deze interfaces beheren wat een component weergeeft, inclusief tekst, HTML, labels, hints en andere beschrijvende inhoud.

| Interface | Beschrijving |
|---|---|
| `HasText` | Stelt de platte tekstinhoud van de component in en haalt deze op. |
| `HasHtml` | Stelt de binnenste HTML van de component in en haalt deze op. |
| `HasLabel` | Voegt een beschrijvende label toe die aan de component is gekoppeld, gebruikt voor toegankelijkheid. |
| `HasHelperText` | Toont secundaire hinttekst onder de component. |
| `HasPlaceholder` | Stelt placeholder-tekst in die wordt weergegeven wanneer de component geen waarde heeft. |
| `HasTooltip` | Bevestigt een tooltip die verschijnt bij hover. |

### Status {#concern-interfaces-state}

Deze interfaces regelen de interactieve status van een component, inclusief of deze is ingeschakeld, bewerkbaar, vereist of gefocust is bij het laden.

| Interface | Beschrijving |
|---|---|
| `HasEnablement` | Zet de component aan of uit. |
| `HasReadOnly` | Zet de component in een alleen-lezen status waar de waarde zichtbaar is maar niet kan worden gewijzigd. |
| `HasRequired` | Merkt de component als vereist, typisch voor formuliervalidatie. |
| `HasAutoFocus` | Verplaatst automatisch de focus naar de component wanneer de pagina laadt. |

### Focus {#concern-interfaces-focus}

Deze interfaces beheren hoe een component de focus krijgt en hierop reageert.

| Interface | Beschrijving |
|---|---|
| `HasFocus` | Beheert de focusstatus en of de component focus kan krijgen. |
| `HasFocusStatus` | Controleert of de component momenteel focus heeft. Vereist een round-trip naar de client. |
| `HasHighlightOnFocus` | Beheert of de inhoud van de component wordt gemarkeerd wanneer deze focus krijgt, en hoe (`KEY`, `MOUSE`, `KEY_MOUSE`, `ALL`, enzovoorts). |

### Invoerspecificaties {#concern-interfaces-input-constraints}

Deze interfaces definiëren welke waarden een component accepteert, inclusief de huidige waarde, toegestane reeksen, lengtebeperkingen, formatteringsmaskers en lokale specifieke gedragingen.

| Interface | Beschrijving |
|---|---|
| `HasValue` | Haalt de huidige waarde van de component op en stelt deze in. |
| `HasMin` | Stelt een minimum toegestane waarde in. |
| `HasMax` | Stelt een maximum toegestane waarde in. |
| `HasStep` | Stelt de stapgrootte in voor numerieke of bereik invoeren. |
| `HasPattern` | Past een reguliere expressie patroon toe om geaccepteerde invoer te beperken. |
| `HasMinLength` | Stelt het minimum aantal vereiste karakters in de waarde van de component in. |
| `HasMaxLength` | Stelt het maximum aantal toegestane karakters in de waarde van de component in. |
| `HasMask` | Past een formatmasker toe op de invoer. Gebruikt door gemaskerde veldcomponenten. |
| `HasTypingMode` | Beheert of getypte karakters worden ingevoegd of bestaande karakters overschrijven (`INSERT` of `OVERWRITE`). Gebruikt door gemaskerde velden en `TextArea`. |
| `HasRestoreValue` | Definieert een waarde waar de component naar terugkeert wanneer de gebruiker Escape indrukt of `restoreValue()` oproept. Gebruikt door gemaskerde velden. |
| `HasLocale` | Bewaart een per-component locale voor lokale gevoelige formatting. Gebruikt door gemaskerde datum- en tijdvelden. |
| `HasPredictedText` | Stelt een voorspeld of auto-aanvult tekstwaarde in. Gebruikt door `TextArea` om inline suggesties te ondersteunen. |

### Validatie {#concern-interfaces-validation}

Deze interfaces voegen client-side validatiegedrag toe, inclusief het markeren van componenten als ongeldig, het weergeven van foutmeldingen en het regelen wanneer validatie wordt uitgevoerd.

| Interface | Beschrijving |
|---|---|
| `HasClientValidation` | Merkt een component als ongeldig, stelt de foutmelding in en bevestigt een client-side validator. |
| `HasClientAutoValidation` | Beheert of de component automatisch valideert terwijl de gebruiker typt. |
| `HasClientAutoValidationOnLoad` | Beheert of de component valideert wanneer deze voor het eerst laadt. |
| `HasClientValidationStyle` | Beheert hoe validatiemeldingen worden weergegeven: `INLINE` (onder de component) of `POPOVER`. |

### DOM-toegang {#concern-interfaces-dom-access}

Deze interfaces bieden laagdrempelige toegang tot het onderliggende HTML-element van de component en client-side eigenschappen.

| Interface | Beschrijving |
|---|---|
| `HasAttribute` | Leest en schrijft willekeurige HTML-attributen op het element van de component. |
| `HasProperty` | Leest en schrijft DWC-componenteigenschappen direct op het clientelement. |

### i18n {#concern-interfaces-i18n}

Deze interface biedt vertaalondersteuning voor componenten die gelokaliseerde tekst moeten weergeven.

| Interface | Beschrijving |
|---|---|
| `HasTranslation` | Biedt de `t()` helper-methode voor het oplossen van vertaalkeys naar gelokaliseerde strings met behulp van de huidige locale van de app. |

:::warning
Als de onderliggende component de interface-mogelijkheid niet ondersteunt, krijg je een runtime-exceptie. Bied in dat geval je eigen implementatie aan.
:::

Voor een complete lijst van beschikbare concerninterfaces, zie de [webforJ JavaDoc](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/concern/package-summary.html).

## Componentidentificatoren {#component-identifiers}

webforJ-componenten hebben drie verschillende soorten identificatoren die verschillende doeleinden dienen:

- **Server-side component-ID** (`getComponentId()`) - Automatisch toegewezen door het framework voor interne componenttracking. Gebruik dit wanneer je specifieke componenten moet opvragen of aangepaste componentregistraties moet implementeren.
- **Client-side component-ID** (`getClientComponentId()`) - Biedt toegang tot de onderliggende webcomponent vanuit JavaScript. Gebruik dit wanneer je native webcomponentmethoden moet aanroepen of moet integreren met client-side bibliotheken.
- **HTML `id` attribuut** (`setAttribute("id", "...")`) - Standaard DOM-identificator. Gebruik dit voor CSS-targeting, testautomatiseringseletors en het koppelen van formulierlabels aan invoeren.

Het begrijpen van deze verschillen helpt je de juiste identificator voor je gebruiksgeval te kiezen.

### Server-side component-ID {#server-side-component-id}

Elke component krijgt automatisch een server-side identificator toegewezen wanneer deze wordt aangemaakt. Deze identificator wordt intern door het framework gebruikt voor het volgen van componenten. Haal het op met `getComponentId()`:

```java
Button button = new Button("Klik op mij");
String serverId = button.getComponentId();
```

De server-side ID is nuttig wanneer je specifieke componenten binnen een container moet opvragen of aangepaste componenttrackinglogica moet implementeren.

### Client-side component-ID {#client-side-component-id}

De client-side component-ID biedt toegang tot de onderliggende webcomponent vanuit JavaScript. Dit stelt je in staat om direct met de client-side component te interageren wanneer dat nodig is:

```java
Button btn = new Button("Klik op mij");
btn.onClick(e -> {
  OptionDialog.showMessageDialog("De knop is aangeklikt", "Er is een gebeurtenis opgetreden");
});

btn.whenAttached().thenAccept(e -> {
  Page.getCurrent().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
});
```

Gebruik `getClientComponentId()` met `objects.get()` in JavaScript om toegang te krijgen tot de webcomponentinstantie.

:::important
De client-side component-ID is niet het HTML `id` attribuut van het DOM-element. Voor het instellen van HTML-ID's voor testen of CSS-targeting, zie [Gebruik van Componenten](using-components).
:::

## Overzicht van de componentlevenscyclus {#component-lifecycle-overview}

webforJ beheert de componentlevenscyclus automatisch. Het framework handelt componentcreatie, -bevestiging en -vernietiging af zonder handmatige tussenkomst.

**Levenscyclushooks** zijn beschikbaar wanneer je ze nodig hebt:
- `onDidCreate(T container)` - Wordt aangeroepen nadat de component aan de DOM is gehecht
- `onDidDestroy()` - Wordt aangeroepen wanneer de component wordt vernietigd

Deze hooks zijn **optioneel**. Gebruik ze wanneer je moet:
- Hulpbronnen opruimen (stoppen met intervallen, verbindingen sluiten)
- Componenten initialiseren die DOM-hechting vereisen
- Integreren met client-side JavaScript

Voor de meeste eenvoudige gevallen kun je componenten rechtstreeks in de constructor initialiseren. Gebruik levenscyclushooks zoals `onDidCreate()` om werk uit te stellen wanneer dat nodig is.
