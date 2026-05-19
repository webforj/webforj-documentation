---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 8eb5ec6f614d406b57a70fb7472636d5
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Een renderer bepaalt hoe elke cel in een kolom wordt weergegeven. In plaats van een ruwe waarde te tonen, transformeert een renderer de gegevens van elke cel in gestileerde tekst, pictogrammen, insignes, links, actieknoppen of andere visuele elementen die de gegevens sneller leesbaar en gemakkelijker handelen maken.

Rendering gebeurt volledig in de browser. De server verzendt ruwe data en de client behandelt de presentatie, waardoor de 'Tabel' snel blijft ongeacht het aantal rijen.

Ken een renderer toe aan een kolom met `setRenderer()`. De renderer wordt uniform toegepast op elke cel in die kolom:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderers versus waardeproviders
Als je alleen een celwaarde hoeft te transformeren of formatteren zonder enige DOM-structuur te produceren, gebruik dan in plaats daarvan een [waardeprovider](/docs/components/table/columns#value-providers). Renderers creëren extra DOM-elementen voor elke gerenderde rij, wat kosten met zich meebrengt tijdens het renderen. Reserveer renderers voor visuele output zoals pictogrammen, insignes, knoppen of andere op HTML-gebaseerde presentaties.
:::

webforJ wordt geleverd met ingebouwde renderers voor de meest voorkomende gebruikssituaties. Voor alles wat specifiek is voor jouw applicatie, breid `Renderer` uit en implementeer `build()` om een lodash-template-string te retourneren die in de browser wordt uitgevoerd voor elke cel.

## Gemeenschappelijke renderers {#common-renderers}

De volgende voorbeelden lopen door vier vaak gebruikte renderers en demonstreren het `setRenderer()`-patroon in de praktijk.

### TextRenderer {#text-renderer}

Toont celinhoud als platte of gestileerde tekst. Pas een thema kleur of tekstdecoratie toe op een kolom zonder de structuur te veranderen, zoals het markeren van een prioriteitsveld in het rood of het vet maken van een sleutelidentificator.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Wikkelt de celwaarde in een badge-element. Ondersteunt thema's, expansies, kleurseeding (automatische onderscheidende kleuren per unieke waarde) en een optioneel leidend pictogram. Gebruik het voor categorische waarden zoals tags, types of labels waar onderscheidende visuele chips gebruikers helpen rijen snel te scannen en te vergelijken.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Vervangt `true`, `false` en `null` waarden door pictogrammen. Gebruik het voor elke true/false-kolom waar een pictogram de waarde sneller communiceert dan tekst, zoals functie-vlaggen, actieve/inactieve statussen of opt-in velden.

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

Formatteert een numerieke waarde als een valutabedrag volgens de regels van de geleverde `Locale`. Gebruik het voor elke monetaire kolom waar locale-correcte opmaak (symbool, scheidingstekens, decimalen) belangrijk is.

```java
// Amerikaanse dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro's met Duitse locatie
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Voorwaardelijk renderen {#conditional-rendering}

`ConditionalRenderer` selecteert een andere renderer per cel op basis van de waarde van de cel. Voorwaarden worden op volgorde geëvalueerd; de eerste match wint. Een catch-all fallback kan worden ingesteld met `otherwise()`.


Het volgende voorbeeld toont voorwaardelijk renderen toegepast op een factuurstatuskolom, die wisselt tussen `BadgeRenderer`-varianten op basis van de waarde:

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

Het werkt ook goed voor numerieke drempels. Dit serverdashboard gebruikt `ConditionalRenderer` om de thema's van `ProgressBarRenderer` te wisselen afhankelijk van CPU- en geheugengebruik:

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

### Voorwaarden API {#condition-api}

Voorwaarden worden gebouwd met statische fabrieksmethoden en kunnen worden samengesteld met `and()`, `or()`, en `negate()`.

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

// String matching
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Samenstelling
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Cross-kolomcontrole
Condition.column("status").equalTo("active")

// Roh JavaScript-expressie
Condition.expression("cell.value % 2 === 0")
```

## Samengesteld renderen {#composite-rendering}

`CompositeRenderer` combineert meerdere renderers zij-aan-zij in een enkele cel met behulp van een flexindeling. Gebruik het om een pictogram met tekst te combineren, een avatar naast een naam te tonen, of een badge naast een statusindicator te stapelen.

De medewerkersdirectory hieronder gebruikt een `CompositeRenderer` op de *Employee* kolom om een automatisch gegenereerde avatar naast de naam van elke medewerker weer te geven:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Aangepaste renderers {#custom-renderers}

Wanneer geen ingebouwde renderer past bij jouw gebruiksgeval, breid `Renderer` uit en implementeer `build()`. De methode retourneert een lodash-template-string die in de browser wordt uitgevoerd voor elke cel in de kolom, weergegeven als een mix van HTML en JavaScript.

### Aangemaakte aangepaste renderer {#creating-a-custom-renderer}

**Stap 1:** Breid `Renderer` uit met jouw rijn datatype.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Stap 2:** Overschrijf `build()` en retourneer een lodash-template-string.

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

**Stap 3:** Ken de renderer toe aan een kolom.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Voor meer informatie over hoe de Lodash-syntaxis wordt gebruikt om cell-informatie te verkrijgen en informatieve renderers te maken, zie [deze referentiegedeelte](#template-reference).
:::

### Toegang tot meerdere kolommen {#accessing-multiple-columns}

Gebruik `cell.row.getValue("columnId")` om zusterkolommen binnen de template te lezen. Dit is handig voor het combineren van velden, het berekenen van delta's of het cross-referencen van gerelateerde gegevens.

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

`IconButtonRenderer` en `ButtonRenderer` bieden `addClickListener()` standaard aan. De klikgebeurtenis biedt toegang tot het gegevensobject van de rij via `e.getItem()`.

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

## Prestatie: lui renderen <DocChip chip='since' label='25.12' /> {#lazy-rendering} 

Voor kolommen die visueel dure renderers gebruiken, zoals badges, voortgangsbalken, avatars of webcomponenten, schakel lui renderen in om de scrollprestaties te verbeteren.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wanneer `setLazyRender(true)` op een kolom is ingesteld, tonen cellen een lichtgewicht geanimeerde placeholder terwijl de gebruiker aan het scrollen is. De daadwerkelijke celinhoud wordt gerenderd zodra het scrollen stopt. Dit is een instelling op kolomniveau, zodat je het selectief kunt inschakelen voor alleen de kolommen die er baat bij hebben.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Wanneer je Lui Renderen moet Inschakelen
Celrenderers creëren meer entiteiten binnen de DOM, wat betekent dat er meer CPU-werk komt kijken bij het renderen, ongeacht welke renderer het creëert. 

Lui renderen kan helpen de prestatie-impact te verminderen als een renderer echt nodig is. Als je alleen de waarde hoeft te veranderen of te formatteren, en je geen complexe DOM creëert, gebruik dan in plaats daarvan een waardeprovider om de waarde te transformeren.
:::

## Ingebouwde renderer referentie {#built-in-renderers} 

webforJ wordt geleverd met een uitgebreide set renderers voor de meest voorkomende gebruikssituaties. Ken een van hen toe aan een kolom met `column.setRenderer(renderer)`.

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

Toont celinhoud als platte of gestileerde tekst. Ondersteunt themakleuren en tekstdecoraties zoals vet, cursief en onderstreept.

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

Wikkelt de celwaarde in een badge-element. Ondersteunt thema's, expansies, kleurseeding (automatische onderscheidende kleuren per unieke waarde) en een optioneel leidend pictogram.

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
<strong>NullRenderer</strong>  -  plaatsvervanger voor null of lege waarden
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders een configureerbare fallback-string wanneer de celwaarde `null` of leeg is; anders wordt de waarde zoals deze is weergegeven.

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
<strong>BooleanRenderer</strong>  -  true/false/null getoond als pictogrammen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Vervangt `true`, `false` en `null` waarden door pictogrammen. Standaard naar een vinkje, kruis en streep.

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
<strong>StatusDotRenderer</strong>  -  gekleurde indicatordot naast celtekst
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders een kleine gekleurde dot aan de linkerkant van de celwaarde. Wijs individuele waarden toe aan thema's, CSS-kleurstrings of `java.awt.Color` instanties.

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

### Nummers, valuta en data {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  locale-bewust valutaopmaak
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatteert een numerieke waarde als een valutabedrag volgens de regels van de geleverde `Locale`.

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
<strong>PercentageRenderer</strong>  -  percentage met optionele mini-voortgangsbalk
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
<strong>ProgressBarRenderer</strong>  -  volledige voortgangsbalk voor numerieke waarden
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders een volledige breedte voortgangsbalk met configureerbare minimum- en maximumlimieten, niet-bepaalde modus, en gestreepte of geanimeerde weergave. Gebruik `setText()` met een lodash-expressie om aangepaste tekst op de balk te overlayen.

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

Past een tekenmasker toe op een stringwaarde. `#` komt overeen met elk cijfer; letterlijke tekens worden behouden. Zie [tekstmaskerregels](/docs/components/fields/masked/textfield#mask-rules) voor alle ondersteunde maskertekens.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numerieke waarde opgemaakt met een nummermasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatteert een numerieke waarde met behulp van een patroonstring met locale-bewuste scheidingstekens. `0` dwingt een cijfer af; `#` is optioneel. Zie [nummermaskerregels](/docs/components/fields/masked/numberfield#mask-rules) voor alle ondersteunde maskertekens.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  datum/tijdwaarde met een datummasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatteert een datum- of tijdwaarde met patroon tokens: `%Mz` (maand), `%Dz` (dag), `%Yz` (jaar), en anderen. Zie [datummaskerregels](/docs/components/fields/masked/datefield#mask-rules) voor alle beschikbare tokens.

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
<strong>EmailRenderer</strong>  -  e-mailadres als een klikbare mailto-link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wikkelt de celwaarde in een `mailto:` anker. Een primair-thema mailpictogram dient als visuele aanwijzing standaard.

```java
// Standaard mailpictogram
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
<strong>PhoneRenderer</strong>  -  telefoonnummer als een klikbare tel-link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wikkelt de celwaarde in een `tel:` anker. Op mobiel opent het klikken de telefoon. Een primair-thema telefoonpictogram wordt standaard weergegeven.

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

Renders een klikbare anker-element. De `href` ondersteunt lodash-template-expressies, zodat je URLs dynamisch kunt bouwen op basis van de celwaarde.

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

Toont een afbeelding. De `src`-attribuut ondersteunt lodash-template-expressies, zodat elke rij een andere afbeelding kan tonen.

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

Renders een avatarcomponent. Initialen worden automatisch afgeleid van de celwaarde. Ondersteunt thema's en een fallback-pictogram.

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
<strong>IconRenderer</strong>  -  op zichzelf staand pictogram, optioneel klikbaar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renders een enkel pictogram. Koppel een kliklistener voor interactieve gedragingen.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  actie-pictogramknop met rijtoegang
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders een klikbare pictogramknop. De klikgebeurtenis geeft het rijobject weer via `e.getItem()`, waardoor het ideaal is voor rij-niveau acties.

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
<strong>ButtonRenderer</strong>  -  gethematiseerde knop in een cel
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renders een volledige `Button`-component binnen de cel.

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
<strong>ElementRenderer</strong>  -  ruwe HTML-element met lodash-inhoud
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renders elk HTML-element met een lodash-template-inhoudsstring. Dit is de escape hatch voor situaties waarin geen ingebouwde renderer past.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Template referentie {#template-reference}

Renderers bieden een krachtig mechanisme voor het aanpassen van de manier waarop gegevens worden weergegeven binnen een `Table`. De primaire klasse, `Renderer`, is ontworpen om verder uitgebreid te worden om aangepaste renderers te creëren op basis van lodash-templates, waardoor dynamische en interactieve inhoudsrendering mogelijk is. 

Lodash-templates maken het mogelijk om HTML direct in tabelcellen in te voegen, waardoor ze zeer effectief zijn voor het renderen van complexe celgegevens in een `Table`. Deze aanpak maakt dynamische generatie van HTML op basis van celgegevens mogelijk, waardoor rijke en interactieve inhoud in tabelcellen wordt gefaciliteerd.

### Lodash-syntaxis {#lodash-syntax}

De volgende sectie schetst de basis van de Lodash-syntaxis. Hoewel dit geen uitputtende of uitgebreide uitleg is, kan het helpen om te beginnen met het gebruik van Lodash binnen de `Table`-component. 

#### Syntaxisoverzicht voor lodash-templates: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpolaties waarden, waarbij het resultaat van de JavaScript-code in de template wordt ingevoegd.
- `<% ... %>` - Voert JavaScript-code uit, waardoor lussen, conditionals en meer mogelijk zijn.
- `<%- ... %>` - Ontsnapt HTML-inhoud, zodat geïnterpoleerde gegevens veilig zijn voor HTML-injectieaanvallen.

#### Voorbeelden met celgegevens: {#examples-using-cell-data}

**1. Eenvoudige waarde-interpolatie**: toon de waarde van de cel direct.

`<%= cell.value %>`

**2. Voorwaardelijk renderen**: gebruik JavaScript-logica om inhoud voorwaardelijk weer te geven.

`<% if (cell.value > 100) { %> 'Hoog' <% } else { %> 'Normaal' <% } %>`

**3. Gecombineerde gegevensvelden**: render inhoud met behulp van meerdere gegevensvelden uit de cel.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Ontsnappen van HTML-inhoud**: veilig weergeven van door gebruikers gemaakte inhoud.

De renderer heeft toegang tot gedetailleerde eigenschappen van cel, rij en kolom aan de clientzijde:

**Tabelcel-eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|column|`TableColumn`|Het geassocieerde kolomobject.|
|first|`boolean`|Geeft aan of de cel de eerste in de rij is.|
|id|`String`|De cel-ID.|
|index|`int`|De index van de cel binnen zijn rij.|
|last|`boolean`|Geeft aan of de cel de laatste in de rij is.|
|row|`TableRow`|Het bijbehorende rijobject voor de cel.|
|value|`Object`|De ruwe waarde van de cel, rechtstreeks van de gegevensbron.|

**Tabelrij-eigenschappen:**

|Eigenschap|Type|Beschrijving|
|-|-|-|
|cells|`TableCell[]`|De cellen binnen de rij.
|data|`Object`|De gegevens die de app voor de rij biedt.
|even|`boolean`|Geeft aan of de rij even genummerd is (voor opmaakdoeleinden).
|first|`boolean`|Geeft aan of de rij de eerste in de tabel is.
|id|`String`|Unieke ID voor de rij.
|index|`int`|De rij-index.
|last|`boolean`|Geeft aan of de rij de laatste in de tabel is.
|odd|`boolean`|Geeft aan of de rij oneven genummerd is (voor opmaakdoeleinden).

**Tabelkolom-eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|align|ColumnAlignment|De uitlijning van de kolom (links, center, rechts).
|id|String|Het veld van het rijobject om de gegevens van de cel uit te halen.
|label|String|De naam die in de kolomkop moet worden weergegeven.
|pinned|ColumnPinDirection|De pinrichting van de kolom (links, rechts, automatisch).
|sortable|boolean|Als dit waar is, kan de kolom worden gesorteerd.
|sort|SortDirection|De sorteervolgorde van de kolom.
|type|ColumnType|Het type van de kolom (tekst, nummer, boolean, enz.).
|minWidth|number|De minimale breedte van de kolom in pixels.
