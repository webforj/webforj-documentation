---
sidebar_position: 20
title: Rendering
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 314a210c06d9b920038308ed7c357f97
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Een renderer beheert hoe elke cel in een kolom wordt weergegeven. In plaats van een ruwe waarde weer te geven, transformeert een renderer de gegevens van elke cel in gestyled tekst, pictogrammen, badges, links, actieknoppen of een andere visuele weergave die de gegevens sneller leesbaar en gemakkelijker handelbaar maakt.

Rendering gebeurt volledig in de browser. De server verzendt ruwe gegevens en de client zorgt voor de presentatie, waardoor de 'Tabel' snel blijft, ongeacht het aantal rijen.

Wijs een renderer toe aan een kolom met `setRenderer()`. De renderer wordt uniform toegepast op elke cel in die kolom:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderers vs. waardeproviders
Als je alleen een celwaarde wilt transformeren of formatteren zonder een DOM-structuur te produceren, gebruik dan in plaats daarvan een [waardeprovider](/docs/components/table/columns#value-providers). Renderers creëren extra DOM-elementen voor elke gerenderde rij, wat kosten met zich meebrengt tijdens het renderen. Reserveer renderers voor visuele output zoals pictogrammen, badges, knoppen of andere op HTML gebaseerde presentaties.
:::

webforJ wordt geleverd met ingebouwde renderers voor de meest voorkomende gebruikssituaties. Voor specifieke behoeften van jouw app, breid `Renderer` uit en implementeer `build()` om een lodash-template string terug te geven die in de browser draait voor elke cel.

## Veelvoorkomende renderers {#common-renderers}

De onderstaande voorbeelden behandelen vier vaak gebruikte renderers en demonsteren het `setRenderer()` patroon in de praktijk.

### TextRenderer {#text-renderer}

Toont celleninhoud als gewone of gestylede tekst. Pas een thema kleur of tekstdecoratie toe op een kolom zonder de structuur te veranderen, bijvoorbeeld door een prioriteitsveld in het rood te markeren of een sleutelidentificatie vetgedrukt te maken.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Omvat de celwaarde in een badge-element. Ondersteunt thema's, expansies, kleurzaad (automatische onderscheidende kleuren per unieke waarde) en een optioneel leidend pictogram. Gebruik het voor categorische waarden zoals tags, types of labels waarbij onderscheidende visuele chips gebruikers helpen om rijen snel te scannen en te vergelijken.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Vervangt `true`, `false` en `null` waarden door pictogrammen. Gebruik het voor elke true/false kolom waar een pictogram de waarde sneller communiceert dan tekst, zoals functievlaggen, actieve/inactieve staten of opt-in velden.

```java
// Standaard pictogrammen
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Aangepaste pictogrammen
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Formatteert een numerieke waarde als een geldbedrag volgens de regels van de opgegeven `Locale`. Gebruik het voor elke monetaire kolom waar locale-waardige formatting (symbool, scheidingstekens, decimalen) van belang is.

```java
// Amerikaanse dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro's met Duitse locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Voorwaardelijke rendering {#conditional-rendering}

`ConditionalRenderer` selecteert een andere renderer per cel op basis van de waarde van de cel. Voorwaarden worden in volgorde geëvalueerd; de eerste match wint. Een catch-all fallback kan worden ingesteld met `otherwise()`.

Het volgende voorbeeld toont voorwaardelijke rendering toegepast op een kolom met factuurstatus, waarbij tussen `BadgeRenderer` varianten wordt gewisseld op basis van de waarde:

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

Het werkt ook goed voor numerieke drempels. Dit serverdashboard gebruikt `ConditionalRenderer` om thema's van de `ProgressBarRenderer` te wisselen op basis van CPU- en geheugengebruik niveaus:

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

### Voorwaarde API {#condition-api}

Voorwaarden worden gebouwd met statische fabrieksmethoden en kunnen worden samengevoegd met `and()`, `or()`, en `negate()`.

```java
// Waarde gelijkheid
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numerieke vergelijkingen
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / leegheid
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// String overeenkomsten
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Samenstelling
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Kruiskolom controle
Condition.column("status").equalTo("active")

// Rauwe JavaScript-expressie
Condition.expression("cell.value % 2 === 0")
```

## Samengestelde rendering {#composite-rendering}

`CompositeRenderer` combineert meerdere renderers naast elkaar in een enkele cel met behulp van een flex-layout. Gebruik het om een pictogram met tekst te combineren, een avatar naast een naam te tonen, of een badge naast een statusindicator te stapelen.

Het medewerkersregister hieronder gebruikt een `CompositeRenderer` op de *Employee* kolom om een automatisch gegenereerde avatar naast de naam van elke werknemer weer te geven:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Aangepaste renderers {#custom-renderers}

Wanneer er geen ingebouwde renderer geschikt is voor jouw gebruiksgeval, breid `Renderer` uit en implementeer `build()`. De methode retourneert een lodash-template string die in de browser draait voor elke cel in de kolom, uitgedrukt als een mix van HTML en JavaScript.

### Een aangepaste renderer maken {#creating-a-custom-renderer}

**Stap 1:** Breid `Renderer` uit met jouw rij gegevenstype.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Stap 2:** Overschrijf `build()` en retourneer een lodash-template string.

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
Voor meer informatie over hoe de Lodash-syntaxis gebruikt wordt om informatie uit cellen te halen en informatieve renderers te maken, zie [deze referentiesectie](#template-reference).
:::

### Toegang tot meerdere kolommen {#accessing-multiple-columns}

Gebruik `cell.row.getValue("columnId")` om zusterkolommen binnen de template te lezen. Dit is nuttig voor het combineren van velden, het berekenen van delta's of het kruisrefereren van gerelateerde gegevens.

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

`IconButtonRenderer` en `ButtonRenderer` bieden standaard `addClickListener()` aan. De klikgebeurtenis biedt toegang tot het gegevenobject van de rij via `e.getItem()`.

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

## Prestatie: luie rendering <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Voor kolommen die visueel dure renderers gebruiken, zoals badges, voortgangsbalken, avatars of webcomponenten, stel luie rendering in om de scrollprestatie te verbeteren.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wanneer `setLazyRender(true)` is ingesteld op een kolom, tonen cellen een lichte geanimeerde placeholder terwijl de gebruiker aan het scrollen is. De daadwerkelijke celinhoud wordt gerenderd zodra het scrollen stopt. Dit is een kolom-niveau instelling, dus je kunt het selectief inschakelen voor alleen de kolommen die er baat bij hebben.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Wanneer luie rendering in te schakelen
Cell renderers creëren meer entiteiten binnen de DOM, wat meer CPU-werk betekent tijdens het renderen, ongeacht welke renderer het creëert.

Luie rendering kan helpen de impact op de prestaties te verminderen als een renderer echt nodig is. Als je alleen de waarde hoeft te wijzigen of te formatteren, en je niet een complexe DOM creëert, gebruik dan in plaats daarvan een waardeprovider om de waarde te transformeren.
:::

## Ingebouwde renderer referentie {#built-in-renderers}

webforJ wordt geleverd met een uitgebreide set renderers voor de meest voorkomende gebruikssituaties. Wijs een van deze toe aan een kolom met `column.setRenderer(renderer)`.

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
<strong>TextRenderer</strong>  -  gestylede tekst met optionele thema en decoraties
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont celleninhoud als gewone of gestylede tekst. Ondersteunt thema kleuren en tekstdecoraties zoals vet, cursief en onderstreept.

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
<strong>BadgeRenderer</strong>  -  waarde weergegeven binnen een badge chip
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Omvat de celwaarde in een badge-element. Ondersteunt thema's, expansies, kleurzaad (automatische onderscheidende kleuren per unieke waarde) en een optioneel leidend pictogram.

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
<strong>NullRenderer</strong>  -  placeholder voor null of lege waarden
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Render een configureerbare fallback-string wanneer de celwaarde `null` of leeg is; anders rendert het de waarde zoals deze is.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Status en indicatoren {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null weergegeven als pictogrammen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Vervangt `true`, `false` en `null` waarden door pictogrammen. Standaard is er een vinkje, kruis en streepje.

```java
// Standaard pictogrammen
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Aangepaste pictogrammen
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
<strong>StatusDotRenderer</strong>  -  gekleurde indicator stip naast celtekst
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert een kleine gekleurde stip links van de celwaarde. Map individuele waarden naar thema's, CSS-kleurstrings of `java.awt.Color` instanties.

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

### Nummers, valuta en datums {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  locale-bewuste valutafomattering
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatteert een numerieke waarde als een geldbedrag volgens de regels van de opgegeven `Locale`.

```java
// Amerikaanse dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro's met Duitse locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  percentage met optionele mini voortgangsbalk
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont een numerieke waarde als een percentage. Stel het tweede constructorargument in op `false` om te voorkomen dat er een dunne voortgangsbalk onder de tekst wordt weergegeven.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  volle voortgangsbalk voor numerieke waarden
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert een volle voortgangsbalk met configureerbare minimum- en maximumgrenzen, een onbepaalde modus en een gestreepte of geanimeerde weergave. Gebruik `setText()` met een lodash-expressie om aangepaste tekst op de balk te overlappen.

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
<strong>MaskedTextRenderer</strong>  -  string geformatteerd met een tekstmasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Past een tekenmasker toe op een string waarde. `#` komt overeen met elk cijfer; letterlijke tekens worden behouden. Zie [tekstmaskerregels](/docs/components/fields/masked/textfield#mask-rules) voor alle ondersteunde maskertekens.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numerieke waarde geformatteerd met een nummermasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatteert een numerieke waarde met een patroonstring met locale-waardige scheidingstekens. `0` dwingt een cijfer af; `#` is optioneel. Zie [nummermaskerregels](/docs/components/fields/masked/numberfield#mask-rules) voor alle ondersteunde maskertekens.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  datum/tijd waarde met een datummasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatteert een datum of tijd waarde met patroon tokens: `%Mz` (maand), `%Dz` (dag), `%Yz` (jaar), en anderen. Zie [datum maskerregels](/docs/components/fields/masked/datefield#mask-rules) voor alle beschikbare tokens.

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

Omvat de celwaarde in een `mailto:` anker. Een primair-thema mailpictogram dient standaard als visuele aanwijzing.

```java
// Standaard mail pictogram
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Aangepast pictogram
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

Omvat de celwaarde in een `tel:` anker. Op mobiel opent tappen de kieser. Een primair-thema telefoonpictogram wordt standaard weergegeven.

```java
// Standaard telefoonpictogram
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Aangepast pictogram
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  celwaarde als een configureerbare hyperlink
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Render een klikbaar anker-element. De `href` ondersteunt lodash-template-expressies zodat je URL's dynamisch uit de celwaarde kunt bouwen.

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
<strong>ImageRenderer</strong>  -  inline afbeelding in een cel
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont een afbeelding. De `src`-attribuut ondersteunt lodash-template-expressies zodat elke rij een andere afbeelding kan tonen.

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

### Mensen en avatars {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar met automatisch gegenereerde initialen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert een avatarcomponent. Initialen worden automatisch afgeleid van de celwaarde. Ondersteunt thema's en een fallback-pictogram.

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
<strong>IconRenderer</strong>  -  zelfstandige pictogram, optioneel klikbaar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rendert een enkel pictogram. Bevestig een klikluisteraar voor interactieve functionaliteit.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  actievolle pictogramknop met toegang tot de rij
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert een klikbare pictogramknop. De klikgebeurtenis geeft het rij-item bloot via `e.getItem()`, waardoor het ideaal is voor actieniveau activiteiten.

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
<strong>ButtonRenderer</strong>  -  thema-knop in een cel
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rendert een volledige `Button` component binnen de cel.

```java
ButtonRenderer renderer = new ButtonRenderer<>("Bewerken");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "Bewerken").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ElementRenderer</strong>  -  rauw HTML-element met lodash-inhoud
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rendert elk HTML-element met een lodash-template-inhoudstring. Dit is de ontsnappingsroute voor situaties waarin geen ingebouwde renderer geschikt is.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Template referentie {#template-reference}

Renderers bieden een krachtige methode voor het aanpassen van de manier waarop gegevens worden weergegeven binnen een `Tabel`. De primaire klasse, `Renderer`, is ontworpen om te worden uitgebreid om aangepaste renderers te maken op basis van lodash-templates, waardoor dynamische en interactieve inhoud rendering mogelijk is.

Lodash-templates maken het mogelijk om HTML direct in tabelcellen in te voegen, waardoor ze zeer effectief zijn voor het renderen van complexe celdata in een `Tabel`. Deze aanpak stelt gebruikers in staat om HTML dynamisch te genereren op basis van celdata, wat bijdraagt aan rijke en interactieve tabelcelinhoud.

### Lodash-syntaxis {#lodash-syntax}

De volgende sectie schetst de basisprincipes van de Lodash-syntaxis. Hoewel dit geen uitputtend of uitgebreid overzicht is, kan het worden gebruikt om te beginnen met het gebruik van Lodash binnen de `Tabel` component.

#### Syntaxisoverzicht voor lodash-templates: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpolaties waardes, waarbij het resultaat van de JavaScript-code in de template wordt ingevoegd.
- `<% ... %>` - Voert JavaScript-code uit, waardoor loops, conditionals en meer mogelijk zijn.
- `<%- ... %>` - Ontsnapt HTML-inhoud, zodat de geïnterpoleerde gegevens veilig zijn tegen HTML-injectieaanvallen.

#### Voorbeelden met celdata: {#examples-using-cell-data}

**1. Eenvoudige waarde-interpolatie**: toont direct de waarde van de cel.

`<%= cell.value %>`

**2. Voorwaardelijke rendering**: gebruikt JavaScript-logica om inhoud conditioneel weer te geven.

`<% if (cell.value > 100) { %> 'Hoog' <% } else { %> 'Normaal' <% } %>`

**3. Gecombineerde data-velden**: render inhoud met behulp van meerdere data-velden van de cel.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Ontsnappen van HTML-inhoud**: veilig renderen van door gebruikers gegenereerde inhoud.

De renderer heeft toegang tot gedetailleerde cel-, rij- en kolom-eigenschappen aan de klantzijde:

**TabelCel Eigenschappen:**

| Eigenschap | Type | Beschrijving |
|------------|------|--------------|
| column     | `TableColumn` | Het bijbehorende kolomobject. |
| first      | `boolean` | Geeft aan of de cel de eerste in de rij is. |
| id         | `String` | De cel ID. |
| index      | `int` | De index van de cel binnen zijn rij. |
| last       | `boolean` | Geeft aan of de cel de laatste in de rij is. |
| row        | `TableRow` | Het bijbehorende rijobject voor de cel. |
| value      | `Object` | De ruwe waarde van de cel, rechtstreeks van de gegevensbron. |

**TabelRij Eigenschappen:**

| Eigenschap | Type | Beschrijving |
|------------|------|--------------|
| cells      | `TableCell[]` | De cellen binnen de rij. |
| data       | `Object` | De gegevens die door de app voor de rij worden geleverd. |
| even       | `boolean` | Geeft aan of de rij even genummerd is (voor stijl doeleinden). |
| first      | `boolean` | Geeft aan of de rij de eerste in de tabel is. |
| id         | `String` | Unieke ID voor de rij. |
| index      | `int` | De rij-index. |
| last       | `boolean` | Geeft aan of de rij de laatste in de tabel is. |
| odd        | `boolean` | Geeft aan of de rij oneven genummerd is (voor stijl doeleinden). |

**TabelKolom Eigenschappen:**

| Eigenschap | Type | Beschrijving |
|------------|------|--------------|
| align      | ColumnAlignment | De uitlijning van de kolom (links, midden, rechts). |
| id         | String | Het veld van het rijobject om de gegevens van de cel uit te halen. |
| label      | String | De naam om in de kolomkop weer te geven. |
| pinned     | ColumnPinDirection | De pinrichting van de kolom (links, rechts, auto). |
| sortable    | boolean | Als het waar is, kan de kolom worden gesorteerd. |
| sort       | SortDirection | De sorteerordening van de kolom. |
| type       | ColumnType | Het type van de kolom (tekst, nummer, boolean, enz.). |
| minWidth   | number | De minimum breedte van de kolom in pixels. |
