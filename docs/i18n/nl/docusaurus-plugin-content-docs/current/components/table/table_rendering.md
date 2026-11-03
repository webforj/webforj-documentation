---
sidebar_position: 20
title: Renderen
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 7d9beff25d6b372fb4309fe491ecad08
---
<!-- vale Google.Headings = NO -->
<!-- vale Google.Colons = NO -->
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Een renderer bepaalt hoe elke cel in een kolom wordt weergegeven. In plaats van een onbewerkte waarde te tonen, zet een renderer de gegevens van elke cel om in gestileerde tekst, pictogrammen, badges, links, actieknoppen of andere visuele elementen waardoor gegevens sneller te lezen zijn en acties eenvoudiger uit te voeren zijn.

Het renderen gebeurt volledig in de browser. De server verstuurt onbewerkte gegevens en de client verzorgt de presentatie. Daardoor blijft de `Table` snel, ongeacht het aantal rijen.

Wijs een renderer toe aan een kolom met `setRenderer()`. De renderer wordt uniform toegepast op elke cel in die kolom:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderers versus waardeleveranciers
Als je alleen een celwaarde hoeft om te zetten of op te maken zonder een DOM-structuur te maken, gebruik dan in plaats daarvan een [waardeleverancier](/docs/components/table/columns#value-providers). Renderers maken extra DOM-elementen voor elke gerenderde rij, wat renderkosten met zich meebrengt. Gebruik renderers alleen voor visuele uitvoer, zoals pictogrammen, badges, knoppen of andere HTML-presentaties.
:::

webforJ wordt geleverd met ingebouwde renderers voor de meest voorkomende toepassingen. Voor specifieke behoeften van je app kun je `Renderer` uitbreiden en `build()` implementeren, zodat deze een lodash-templatestring retourneert die voor elke cel in de browser wordt uitgevoerd.

## Veelgebruikte renderers {#common-renderers}

De volgende voorbeelden behandelen vier veelgebruikte renderers en laten zien hoe je het patroon `setRenderer()` in de praktijk toepast.

### TextRenderer {#text-renderer}

Toont celinhoud als gewone of gestileerde tekst. Pas een themakleur of tekstopmaak toe op een kolom zonder de structuur ervan te wijzigen, bijvoorbeeld door een prioriteitsveld rood te markeren of een belangrijke identificatie vetgedrukt weer te geven.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Toont de celwaarde in een badge-element. Ondersteunt thema's, expanses, kleurtoewijzing (automatisch verschillende kleuren voor unieke waarden) en een optioneel pictogram aan het begin. Gebruik deze renderer voor categorische waarden, zoals tags, typen of labels, waarbij onderscheidende visuele chips gebruikers helpen rijen snel te scannen en te vergelijken.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Vervangt waarden `true`, `false` en `null` door pictogrammen. Gebruik deze renderer voor elke kolom met waarheidswaarden waarbij een pictogram de waarde sneller duidelijk maakt dan tekst, bijvoorbeeld voor functievlaggen, actieve/inactieve statussen of opt-in-velden.

```java
// Default icons
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Custom icons
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Maakt een numerieke waarde op als een geldbedrag volgens de regels van de opgegeven `Locale`. Gebruik deze renderer voor geldkolommen waarbij opmaak volgens de locale van belang is, zoals het symbool, de scheidingstekens en het aantal decimalen.

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Voorwaardelijk renderen {#conditional-rendering}

`ConditionalRenderer` selecteert per cel een andere renderer op basis van de celwaarde. Voorwaarden worden in volgorde geëvalueerd; de eerste overeenkomst wordt gebruikt. Met `otherwise()` kun je een standaardoptie instellen.


In het volgende voorbeeld wordt voorwaardelijk renderen toegepast op een kolom met factuurstatussen. Op basis van de waarde wordt een andere variant van `BadgeRenderer` gekozen:


<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java',
]}
height='600px'
/>
<!-- vale on -->

Dit werkt ook goed voor numerieke drempelwaarden. Dit serverdashboard gebruikt `ConditionalRenderer` om op basis van het CPU- en geheugengebruik verschillende thema's voor `ProgressBarRenderer` te kiezen:

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

### Condition-API {#condition-api}

Voorwaarden worden gemaakt met statische fabrieksmethoden en kunnen worden gecombineerd met `and()`, `or()` en `negate()`.

```java
// Value equality
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numeric comparisons
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / emptiness
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// String matching
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Composition
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Cross-column check
Condition.column("status").equalTo("active")

// Raw JavaScript expression
Condition.expression("cell.value % 2 === 0")
```

## Samengesteld renderen {#composite-rendering}

`CompositeRenderer` combineert meerdere renderers naast elkaar in één cel met een flexlay-out. Gebruik deze renderer om een pictogram met tekst te combineren, een avatar naast een naam te tonen of een badge naast een statusindicator te plaatsen.

De personeelslijst hieronder gebruikt een `CompositeRenderer` in de kolom *Employee* om naast de naam van elke medewerker een automatisch gegenereerde avatar weer te geven:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Employee.java',
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeService.java',
]}
height='600px'
/>
<!-- vale on -->

## Aangepaste renderers {#custom-renderers}

Als geen ingebouwde renderer geschikt is voor je toepassing, breid je `Renderer` uit en implementeer je `build()`. De methode retourneert een lodash-templatestring die voor elke cel in de kolom in de browser wordt uitgevoerd en bestaat uit een combinatie van HTML en JavaScript.

### Een aangepaste renderer maken {#creating-a-custom-renderer}

**Stap 1:** Breid `Renderer` uit met het gegevenstype van je rij.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Stap 2:** Overschrijf `build()` en retourneer een lodash-templatestring.

```java
  @Override
  public String build() {
    return /* html */"""
      <%
        const rating = Number(cell.value);
        const stars  = Math.round(Math.min(Math.max(rating, 0), 5));
        const full   = '★'.repeat(stars);
        const empty  = '☆'.repeat(5 - stars);
      %>
      <span><%= full %><%= empty %></span>
      <span style="color: var(--dwc-color-body-text)">(<%= rating.toFixed(1) %>)</span>
    """;
  }
}
```

**Stap 3:** Wijs de renderer toe aan een kolom.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Zie [dit referentiegedeelte](#template-reference) voor meer informatie over het gebruik van Lodash-syntaxis om celinformatie op te halen en informatieve renderers te maken.
:::

### Meerdere kolommen gebruiken {#accessing-multiple-columns}

Gebruik `cell.row.getValue("columnId")` om in de template waarden uit andere kolommen op te halen. Dit is handig om velden te combineren, verschillen te berekenen of verwante gegevens met elkaar te vergelijken.

```java
public class ArtistAvatarRenderer extends Renderer<MusicRecord> {
  @Override
  public String build() {
    return /* html */"""
      <%
        const name     = cell.row.getValue("artist");
        const initials = name
          ? name.split(' ').map(w => w.charAt(0)).join('').substring(0, 2).toUpperCase()
          : '?';
      %>
      <div style="display: flex; align-items: center; gap: 8px;">
        <div style="width: 28px; height: 28px; border-radius: 50%;
          background: var(--dwc-color-primary); color: white;
          display: flex; align-items: center; justify-content: center;
          font-size: 11px; font-weight: 600;">
          <%= initials %>
        </div>
        <span><%= name %></span>
      </div>
    """;
  }
}
```

### Klikgebeurtenissen {#click-events}

`IconButtonRenderer` en `ButtonRenderer` bieden standaard `addClickListener()`. Via `e.getItem()` geeft de klikgebeurtenis toegang tot het gegevensobject van de rij.

```java
IconButtonRenderer<MusicRecord> deleteBtn = new IconButtonRenderer<>(
  TablerIcon.create("trash").setTheme(Theme.DANGER)
);
deleteBtn.addClickListener(e -> {
  MusicRecord record = e.getItem();
  repository.delete(record);
  table.refresh();
});

table.addColumn("delete", r -> "").setRenderer(deleteBtn);
```

## Prestaties: lazy rendering <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Schakel lazy rendering in voor kolommen met visueel intensieve renderers, zoals badges, voortgangsbalken, avatars of webcomponenten, om de prestaties tijdens het scrollen te verbeteren.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wanneer je `setLazyRender(true)` instelt voor een kolom, tonen cellen een lichte, geanimeerde tijdelijke aanduiding terwijl de gebruiker scrollt. De celinhoud wordt pas gerenderd wanneer het scrollen stopt. Dit is een instelling op kolomniveau, dus je kunt deze alleen inschakelen voor de kolommen die er baat bij hebben.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Wanneer lazy rendering inschakelen
Celrenderers maken extra elementen in de DOM aan, wat tijdens het renderen meer CPU-werk met zich meebrengt, ongeacht welke renderer die elementen aanmaakt.

Lazy rendering kan de impact op de prestaties beperken als een renderer echt nodig is. Als je alleen de waarde wilt wijzigen of opmaken en geen complexe DOM-structuur maakt, gebruik dan in plaats daarvan een waardeleverancier om de waarde om te zetten.
:::

## Referentie voor ingebouwde renderers {#built-in-renderers}

webforJ wordt geleverd met een uitgebreide set renderers voor de meest voorkomende toepassingen. Wijs ze aan een kolom toe met `column.setRenderer(renderer)`.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Product.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ProductService.java',
]}
height='600px'
/>
<!-- vale on -->

### Tekst en labels {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  gestileerde tekst met optioneel thema en decoraties
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont celinhoud als gewone of gestileerde tekst. Ondersteunt themakleuren en tekstopmaak, zoals vet, cursief en onderstrepen.

```java
TextRenderer renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BadgeRenderer</strong>  -  waarde weergegeven in een badgechip
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont de celwaarde in een badge-element. Ondersteunt thema's, expanses, kleurtoewijzing (automatisch verschillende kleuren voor unieke waarden) en een optioneel pictogram aan het begin.

```java
BadgeRenderer renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>NullRenderer</strong>  -  tijdelijke aanduiding voor null- of lege waarden
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Geeft een configureerbare terugvaltekst weer wanneer de celwaarde `null` of leeg is; anders wordt de waarde ongewijzigd weergegeven.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Statussen en indicatoren {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null weergegeven als pictogrammen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Vervangt waarden `true`, `false` en `null` door pictogrammen. Standaard worden een vinkje, een kruis en een streepje gebruikt.


```java
// Default icons
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Custom icons
BooleanRenderer custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>StatusDotRenderer</strong>  -  gekleurde stip naast de celtekst
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont een kleine gekleurde stip links van de celwaarde. Koppel afzonderlijke waarden aan thema's, CSS-kleurstrings of `java.awt.Color`-instanties.

```java
StatusDotRenderer renderer = new StatusDotRenderer<>();
renderer.addMapping("Active",    Theme.SUCCESS);
renderer.addMapping("Pending",   Theme.WARNING);
renderer.addMapping("Cancelled", Theme.DANGER);

table.addColumn("status", Order::getStatus).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Getallen, valuta en datums {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  valutaopmaak volgens de locale
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Maakt een numerieke waarde op als een geldbedrag volgens de regels van de opgegeven `Locale`.

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  percentage met optionele kleine voortgangsbalk
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont een numerieke waarde als percentage. Stel het tweede constructorargument in op `false` om te voorkomen dat er een smalle voortgangsbalk onder de tekst wordt weergegeven.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  volledige voortgangsbalk voor numerieke waarden
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Geeft een voortgangsbalk over de volledige breedte weer met configureerbare minimum- en maximumwaarden, een onbepaalde modus en een gestreepte of geanimeerde weergave. Gebruik `setText()` met een lodash-expressie om aangepaste tekst over de balk weer te geven.

```java
ProgressBarRenderer renderer = new ProgressBarRenderer<>();
renderer.setMax(100);
renderer.setTheme(Theme.SUCCESS);
renderer.setTextVisible(true);
renderer.setText("<%= cell.value %>/100");

table.addColumn("progress", Task::getProgress).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedTextRenderer</strong>  -  string opgemaakt met een tekstmasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Past een tekenmasker toe op een stringwaarde. `#` staat voor elk cijfer; letterlijke tekens blijven behouden. Zie [regels voor tekstmaskers](/docs/components/fields/masked/textfield#mask-rules) voor alle ondersteunde maskertekens.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numerieke waarde opgemaakt met een getallenmasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Maakt een numerieke waarde op met een patroonstring en scheidingstekens volgens de locale. `0` verplicht een cijfer; `#` is optioneel. Zie [regels voor getallenmaskers](/docs/components/fields/masked/numberfield#mask-rules) voor alle ondersteunde maskertekens.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  datum-/tijdwaarde met een datummasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Maakt een datum- of tijdwaarde op met patroontokens: `%Mz` (maand), `%Dz` (dag), `%Yz` (jaar) en andere. Zie [regels voor datummaskers](/docs/components/fields/masked/datefield#mask-rules) voor alle beschikbare tokens.

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Links en media {#links-and-media}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  e-mailadres als klikbare mailto-link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wikkelt de celwaarde in een `mailto:`-ankerelement. Standaard dient een e-mailpictogram met het primaire thema als visuele aanduiding.

```java
// Default mail icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Custom icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  telefoonnummer als klikbare tel-link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wikkelt de celwaarde in een `tel:`-ankerelement. Op mobiele apparaten opent een tik de kiezer. Standaard wordt een telefoonpictogram met het primaire thema weergegeven.

```java
// Default phone icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Custom icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  celwaarde als configureerbare hyperlink
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Geeft een klikbaar ankerelement weer. `href` ondersteunt lodash-template-expressies, zodat je URL's dynamisch kunt opbouwen op basis van de celwaarde.

```java
AnchorRenderer renderer = new AnchorRenderer<>();
renderer.setHref("https://www.google.com/search?q=<%= cell.value %>");
renderer.setTarget("_blank");

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ImageRenderer</strong>  -  afbeelding in een cel
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont een afbeelding. Het kenmerk `src` ondersteunt lodash-template-expressies, zodat elke rij een andere afbeelding kan tonen.

```java
ImageRenderer renderer = new ImageRenderer<>();
renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
renderer.setAlt("Cover");

table.addColumn("cover", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Personen en avatars {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar met automatisch gegenereerde initialen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Geeft een avatarcomponent weer. De initialen worden automatisch afgeleid van de celwaarde. Ondersteunt thema's en een terugvalpictogram.

```java
AvatarRenderer renderer = new AvatarRenderer<>();
renderer.setTheme(AvatarTheme.PRIMARY);
renderer.setIcon(TablerIcon.create("user"));

table.addColumn("artist", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Pictogrammen en acties {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  zelfstandig pictogram, eventueel klikbaar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Geeft één pictogram weer. Voeg een kliklistener toe voor interactieve functionaliteit.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  actieknop met pictogram en toegang tot de rij
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Geeft een klikbare pictogramknop weer. Via `e.getItem()` geeft de klikgebeurtenis toegang tot het rijitem, waardoor deze renderer ideaal is voor acties op rijniveau.

```java
IconButtonRenderer renderer = new IconButtonRenderer<>(TablerIcon.create("edit"));
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("actions", r -> "").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ButtonRenderer</strong>  -  knop met thema in een cel
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Geeft een volledige `Button`-component weer in de cel.

```java
ButtonRenderer renderer = new ButtonRenderer<>("Edit");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "Edit").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ElementRenderer</strong>  -  onbewerkt HTML-element met Lodash-inhoud
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Geeft elk HTML-element weer met een Lodash-template als inhoud. Dit is de terugvaloptie voor situaties waarin geen ingebouwde renderer geschikt is.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Templatereferentie {#template-reference}

Renderers bieden een krachtige manier om aan te passen hoe gegevens in een `Table` worden weergegeven. De hoofdklasse, `Renderer`, is bedoeld om te worden uitgebreid met aangepaste renderers op basis van lodash-templates, zodat dynamische en interactieve inhoud kan worden weergegeven.

Met lodash-templates kun je HTML rechtstreeks in tabelcellen invoegen. Daardoor zijn ze zeer geschikt om complexe celgegevens in een `Table` weer te geven. Deze aanpak maakt het mogelijk om dynamisch HTML te genereren op basis van celgegevens, voor rijke en interactieve tabelinhoud.

### Lodash-syntaxis {#lodash-syntax}

In het volgende gedeelte worden de basisprincipes van de Lodash-syntaxis beschreven. Dit is geen volledige of uitputtende uitleg, maar helpt je op weg met Lodash in de `Table`-component.

#### Overzicht van de syntaxis voor lodash-templates: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoleert waarden en voegt het resultaat van de JavaScript-code in de template in.
- `<% ... %>` - Voert JavaScript-code uit, zoals lussen en voorwaarden.
- `<%- ... %>` - Escapet HTML-inhoud, zodat geïnterpoleerde gegevens beschermd zijn tegen HTML-injectieaanvallen.

#### Voorbeelden met celgegevens: {#examples-using-cell-data}

**1. Eenvoudige waarde-interpolatie**: geef de waarde van de cel rechtstreeks weer.

`<%= cell.value %>`

**2. Voorwaardelijk renderen**: gebruik JavaScript-logica om inhoud voorwaardelijk weer te geven.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Gegevensvelden combineren**: geef inhoud weer met meerdere gegevensvelden uit de cel.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-inhoud escapen**: geef door gebruikers gegenereerde inhoud veilig weer.

De renderer heeft aan de clientzijde toegang tot gedetailleerde eigenschappen van cellen, rijen en kolommen:

**Eigenschappen van TableCell:**

|Property	|Type	|Description|
|-|-|-|
|column|`TableColumn`|Het bijbehorende kolomobject.|
|first|`boolean`|Geeft aan of de cel de eerste in de rij is.|
|id|`String`|De cel-ID.|
|index|`int`|De index van de cel binnen de rij.|
|last|`boolean`|Geeft aan of de cel de laatste in de rij is.|
|row|`TableRow`|Het bijbehorende rijobject van de cel.|
|value|`Object`|De onbewerkte waarde van de cel, rechtstreeks afkomstig uit de gegevensbron.|

**Eigenschappen van TableRow:**

|Property|Type|Description|
|-|-|-|
|cells|`TableCell[]`|De cellen in de rij.|
|data|`Object`|De gegevens die de app voor de rij heeft aangeleverd.|
|even|`boolean`|Geeft aan of het rijnummer even is (voor opmaakdoeleinden).|
|first|`boolean`|Geeft aan of de rij de eerste in de tabel is.|
|id|`String`|Unieke ID voor de rij.|
|index|`int`|De rij-index.|
|last|`boolean`|Geeft aan of de rij de laatste in de tabel is.|
|odd|`boolean`|Geeft aan of het rijnummer oneven is (voor opmaakdoeleinden).|

**Eigenschappen van TableColumn:**

|Property	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|De uitlijning van de kolom (links, midden, rechts).|
|id|String|Het veld van het rijobject waaruit de celgegevens worden opgehaald.|
|label|String|De naam die in de kolomkop wordt weergegeven.|
|pinned|ColumnPinDirection|De vastzettingsrichting van de kolom (links, rechts, automatisch).|
|sortable|boolean|Als deze waarde waar is, kan de kolom worden gesorteerd.|
|sort|SortDirection|De sorteervolgorde van de kolom.|
|type|ColumnType|Het type van de kolom (tekst, getal, boolean enzovoort).|
|minWidth|number|De minimale breedte van de kolom in pixels.|
