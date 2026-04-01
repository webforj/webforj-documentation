---
sidebar_position: 20
title: Rendering
slug: rendering
sidebar_class_name: new-content
_i18n_hash: c6f33a66de68ddcd600382bf0dc449f2
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Een renderer controleert hoe elke cel in een kolom wordt weergegeven. In plaats van een ruwe waarde te tonen, transformeert een renderer de gegevens van elke cel in gestileerde tekst, pictogrammen, badges, links, actieknoppen of andere visuals die de gegevens sneller leesbaar en gemakkelijker te verwerken maken.

Rendering gebeurt volledig in de browser. De server stuurt ruwe gegevens en de client behandelt de presentatie, waardoor de 'Tabel' snel is, ongeacht het aantal rijen.

Wijs een renderer toe aan een kolom met `setRenderer()`. De renderer wordt uniform toegepast op elke cel in die kolom:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderers vs. value providers
Als je alleen een celwaarde wilt transformeren of formatteren zonder een DOM-structuur te produceren, gebruik dan in plaats daarvan een [value provider](/docs/components/table/columns#value-providers). Renderers creëren extra DOM-elementen voor elke gerenderde rij, wat een kost met zich meebrengt op render-tijd. Behoud renderers voor visuele output zoals pictogrammen, badges, knoppen of andere HTML-gebaseerde presentaties.
:::

webforJ wordt geleverd met ingebouwde renderers voor de meest voorkomende gebruiksgevallen. Voor alles wat specifiek is voor jouw applicatie, breid je `Renderer` uit en implementeer je `build()` om een lodash sjabloonstring terug te geven die in de browser wordt uitgevoerd voor elke cel.

## Veelvoorkomende renderers {#common-renderers}

De volgende voorbeelden lopen vier veelgebruikte renderers door en demonstreren het `setRenderer()`-patroon in de praktijk.

### TextRenderer {#text-renderer}

Toont de inhoud van de cel als platte of gestileerde tekst. Pas een themakleur of tekstdecoratie toe op een kolom zonder de structuur te wijzigen, bijvoorbeeld door een prioriteitsveld in het rood te markeren of een sleutelidentificator vet te maken.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Wikkelt de celwaarde in een badge-element. Ondersteunt thema's, uitbreidingen, kleurseeding (automatisch onderscheidende kleuren per unieke waarde) en een optioneel leidend pictogram. Gebruik het voor categorische waarden zoals tags, types of labels waarbij onderscheidende visuele chips gebruikers helpen om rijen snel te scannen en te vergelijken.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Vervangt `true`, `false` en `null` waarden door pictogrammen. Gebruik het voor elke true/false-kolom waar een pictogram de waarde sneller communiceert dan tekst, zoals feature flags, actieve/inactieve toestanden of opt-in velden.

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

Formateert een numerieke waarde als een valutabedrag volgens de regels van de meegeleverde `Locale`. Gebruik het voor elke monetiaire kolom waar lokaal correcte opmaak (symbool, scheidingstekens, decimalen) belangrijk is.

```java
// Amerikaanse dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro's met Duitse lokale instellingen
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Voorwaardelijke rendering {#conditional-rendering}

`ConditionalRenderer` selecteert een andere renderer per cel op basis van de waarde van de cel. Voorwaarden worden op volgorde geëvalueerd; de eerste match wint. Een catch-all fallback kan worden ingesteld met `otherwise()`.

Het volgende voorbeeld toont voorwaardelijke rendering toegepast op een factuurstatuskolom, waarbij wordt gewisseld tussen `BadgeRenderer`-varianten op basis van de waarde:

<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java']}
height='600px'
/>
<!-- vale on -->

Het werkt ook goed voor numerieke drempels. Dit serverdashboard maakt gebruik van `ConditionalRenderer` om de thema's van `ProgressBarRenderer` te wisselen op basis van CPU- en geheugengebruik niveaus:

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Server.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerService.java']}
height='600px'
/>
<!-- vale on -->

### Voorwaarde API {#condition-api}

Voorwaarden worden opgebouwd met statische fabrieksmethoden en kunnen worden samengesteld met `and()`, `or()`, en `negate()`.

```java
// Waarde gelijkheid
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numerieke vergelijkingen
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / leegte
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

// Controle over meerdere kolommen
Condition.column("status").equalTo("active")

// Ruwe JavaScript-expressie
Condition.expression("cell.value % 2 === 0")
```

## Composite rendering {#composite-rendering}

`CompositeRenderer` combineert meerdere renderers zij aan zij in een enkele cel met behulp van een flex-layout. Gebruik het om een pictogram met tekst te koppelen, een avatar naast een naam te tonen of een badge naast een statusindicator te stapelen.

De medewerkersdirectory hieronder gebruikt een `CompositeRenderer` op de *Medewerker* kolom om een automatisch gegenereerde avatar naast de naam van elke medewerker weer te geven:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java'
height='600px'
/>
<!-- vale on -->

## Aangepaste renderers {#custom-renderers}

Wanneer geen ingebouwde renderer past bij jouw gebruiksgeval, breid je `Renderer` uit en implementeer je `build()`. De methode geeft een lodash sjabloonstring terug die in de browser wordt uitgevoerd voor elke cel in de kolom, uitgedrukt als een mix van HTML en JavaScript.

### Een aangepaste renderer maken {#creating-a-custom-renderer}

**Stap 1:** Breid `Renderer` uit met jouw rijdgegevens type.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Stap 2:** Overschrijf `build()` en geef een lodash sjabloonstring terug.

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
Voor meer informatie over hoe de Lodash-syntax gebruikt wordt om celinformatie te benaderen en informatieve renderers te creëren, zie [deze referentiegedeelte](#template-reference).
:::

### Toegang tot meerdere kolommen {#accessing-multiple-columns}

Gebruik `cell.row.getValue("columnId")` om broederkolommen binnen het sjabloon te lezen. Dit is nuttig voor het combineren van velden, berekenen van deltas of kruisverwijzing van gerelateerde gegevens.

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

### Klik evenementen {#click-events}

`IconButtonRenderer` en `ButtonRenderer` bieden `addClickListener()` direct aan. Het klik evenement biedt toegang tot het gegevensobject van de rij via `e.getItem()`.

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

Voor kolommen die visueel dure renderers gebruiken, zoals badges, voortgangsbalken, avatars of webcomponenten, schakel je lui renderen in om de scrollprestaties te verbeteren.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wanneer `setLazyRender(true)` is ingesteld op een kolom, tonen cellen een lichte geanimeerde tijdelijke aanduiding terwijl de gebruiker aan het scrollen is. De daadwerkelijke celinhoud wordt gerenderd zodra het scrollen stopt. Dit is een kolomniveau-instelling, dus je kunt het selectief inschakelen voor alleen de kolommen die er voordeel van hebben.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java'
height='600px'
/>
<!-- vale on -->

:::tip Wanneer lui renderen in te schakelen
Celrenderers creëren meer entiteiten binnen de DOM, wat betekent dat er meer CPU-werk is tijdens het renderen, ongeacht welke renderer het creëert. 

Lui renderen kan helpen om de prestatielast te verminderen als een renderer echt nodig is. Als je alleen de waarde hoeft te veranderen of te formatteren, en je geen complexe DOM aanmaakt, gebruik dan in plaats daarvan een value provider om de waarde te transformeren.
:::

## Ingebouwde renderer referentie {#built-in-renderers} 

webforJ wordt geleverd met een uitgebreid scala aan renderers voor de meest voorkomende gebruiksgevallen. Wijs een van hen toe aan een kolom met `column.setRenderer(renderer)`.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Product.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductService.java']}
height='600px'
/>
<!-- vale on -->

### Tekst en labels {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  gestileerde tekst met optionele thema- en decoraties
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Toont de inhoud van de cel als platte of gestileerde tekst. Ondersteunt themakleuren en tekstdecoraties zoals vet, cursief en onderstreept.

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

Wikkelt de celwaarde in een badge-element. Ondersteunt thema's, uitbreidingen, kleurseeding (automatisch onderscheidende kleuren per unieke waarde) en een optioneel leidend pictogram.

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

Geeft een configureerbare fallback-string weer wanneer de celwaarde `null` of leeg is; anders wordt de waarde zoals-is weergegeven.

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

Vervangt `true`, `false` en `null` waarden door pictogrammen. Standaard is dit een vinkje, kruis en liggend streepje.

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
<strong>StatusDotRenderer</strong>  -  gekleurde indicatorpunt naast celtekst
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Geeft een kleine gekleurde stip weer links van de celwaarde. Map individuele waarden naar thema's, CSS-kleurstrings of `java.awt.Color` instanties.

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

### Getallen, valuta en data {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  locale-bewuste valutafomatering
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formateert een numerieke waarde als een valutabedrag volgens de regels van de meegeleverde `Locale`.

```java
// Amerikaanse dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro's met Duitse lokale instellingen
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

Geeft een numerieke waarde weer als percentage. Stel het tweede constructorargument in op `false` om te voorkomen dat er een dunne voortgangsbalk onder de tekst wordt weergegeven.

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

Geeft een volledige voortgangsbalk weer met configureerbare minimum- en maximumgrenzen, onbepaalde modus, en gestreepte of geanimeerde weergave. Gebruik `setText()` met een lodash-expressie om aangepaste tekst op de balk te leggen.

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
<strong>MaskedTextRenderer</strong>  -  teken geformatteerd met een tekstmasker
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Past een tekenmasker toe op een stringwaarde. `#` komt overeen met een cijfer; letterlijke tekens worden behouden. Zie [tekstmaskerregels](/docs/components/fields/masked/textfield#mask-rules) voor alle ondersteunde maskerkarakters.

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

Formateert een numerieke waarde met behulp van een patroonstring met locale-bewuste scheidingstekens. `0` dwingt een cijfer; `#` is optioneel. Zie [nummermaskerregels](/docs/components/fields/masked/numberfield#mask-rules) voor alle ondersteunde maskerkarakters.

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

Formateert een datum of tijdwaarde met behulp van patroon tokens: `%Mz` (maand), `%Dz` (dag), `%Yz` (jaar) en anderen. Zie [datum maskregels](/docs/components/fields/masked/datefield#mask-rules) voor alle beschikbare tokens.

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

Wikkelt de celwaarde in een `mailto:`-anchor. Een primair-thema mailpictogram dient als visuele aanwijzing standaard.

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

Wikkelt de celwaarde in een `tel:`-anchor. Op mobiele apparaten opent het indrukken de telefoon. Een primair-thema telefoonpictogram wordt standaard weergegeven.

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

Geeft een klikbare anchor-element weer. De `href` ondersteunt lodash-sjabloonexpressies, zodat je URL's dynamisch vanuit de celwaarde kunt opbouwen.

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

Geeft een afbeelding weer. De `src`-attribuut ondersteunt lodash-sjabloonexpressies, zodat elke rij een andere afbeelding kan tonen.

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

Geeft een avatarcomponent weer. Initialen worden automatisch afgeleid van de celwaarde. Ondersteunt thema's en een fallback-pictogram.

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
<strong>IconRenderer</strong>  -  zelfstandig pictogram, optioneel klikbaar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Geeft een enkel pictogram weer. Bevestig een kliklistener voor interactieve gedrag.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  actie-pictogrambutton met toegang tot de rij
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Geeft een klikbare pictogrambutton weer. Het klik evenement geeft het rij-item weer via `e.getItem()`, waardoor het ideaal is voor acties op rij-niveau. 

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
<strong>ButtonRenderer</strong>  -  themaknop in een cel
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Geeft een volledige `Button`-component binnen de cel weer.

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
<strong>ElementRenderer</strong>  -  ruwe HTML-element met lodash-inhoud
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Geeft elk HTML-element weer met een lodash-sjablooninhoudstring. Dit is de ontsnapping voor situaties waarin geen ingebouwde renderer past.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Sjabloonreferentie {#template-reference}

Renderers bieden een krachtig mechanisme voor het aanpassen van de manier waarop gegevens worden weergegeven binnen een `Tabel`. De primaire klasse, `Renderer`, is ontworpen om uitgebreid te worden om aangepaste renderers op basis van lodash-sjablonen te creëren, waardoor dynamische en interactieve inhoudsweergave mogelijk is. 

Lodash-sjablonen stellen de invoeging van HTML rechtstreeks in tabelcellen mogelijk, waardoor ze zeer effectief zijn voor het weergeven van complexe celgegevens in een `Tabel`. Deze aanpak maakt dynamische generatie van HTML op basis van celdgegevens mogelijk, waardoor rijke en interactieve tabelcelinhoud wordt mogelijk gemaakt.

### Lodash-syntaxis {#lodash-syntax}

De volgende sectie schetst de basis van de Lodash-syntaxis. Hoewel dit geen uitputtend of uitgebreid overzicht is, kan het gebruikt worden om te helpen starten met het gebruik van Lodash binnen de `Tabel`-component. 

#### Syntaxis overzicht voor lodash-sjablonen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoleert waarden en voegt het resultaat van de JavaScript-code in de sjabloon in.
- `<% ... %>` - Voert JavaScript-code uit, waardoor lussen, voorwaardelijke instructies en meer mogelijk zijn.
- `<%- ... %>` - Ontsnapt HTML-inhoud, zodat geïnterpoleerde gegevens veilig zijn voor HTML-injectie-aanvallen.

#### Voorbeelden met celgegevens: {#examples-using-cell-data}

**1. Eenvoudige waarde-interpolatie**: toon de waarde van de cel direct.

`<%= cell.value %>`

**2. Voorwaardelijke rendering**: gebruik JavaScript-logica om inhoud voorwaardelijk weer te geven.

`<% if (cell.value > 100) { %> 'Hoog' <% } else { %> 'Normaal' <% } %>`

**3. Combineren van gegevensvelden**: geef inhoud weer met behulp van meerdere gegevensvelden uit de cel.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Ontsnappen van HTML-inhoud**: veilig weergeven van door de gebruiker gegenereerde inhoud.

De renderer heeft toegang tot gedetailleerde cel-, rij- en kolom-eigenschappen aan de clientzijde:

**TabelCel-Eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|column|`TableColumn`|Het bijbehorende kolomobject.|
|first|`boolean`|Geeft aan of de cel de eerste in de rij is.|
|id|`String`|De cel-ID.|
|index|`int`|De index van de cel binnen zijn rij.|
|last|`boolean`|Geeft aan of de cel de laatste in de rij is.|
|row|`TableRow`|Het bijbehorende rijobject voor de cel.|
|value|`Object`|De ruwe waarde van de cel, rechtstreeks van de gegevensbron.|

**TabelRij-Eigenschappen:**

|Eigenschap|Type|Beschrijving|
|-|-|-|
|cells|`TableCell[]`|De cellen binnen de rij.
|data|`Object`|De gegevens die door de app voor de rij zijn verstrekt.
|even|`boolean`|Geeft aan of de rij even genummerd is (voor opmaakdoeleinden).
|first|`boolean`|Geeft aan of de rij de eerste in de tabel is.
|id|`String`|Unieke ID voor de rij.
|index|`int`|De rij-index.
|last|`boolean`|Geeft aan of de rij de laatste in de tabel is.
|odd|`boolean`|Geeft aan of de rij oneven genummerd is (voor opmaakdoeleinden).

**TabelKolom-Eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|align|ColumnAlignment|De uitlijning van de kolom (links, midden, rechts).
|id|String|Het veld van het rijobject om de gegevens van de cel te verkrijgen.
|label|String|De naam die in de kolomkop moet worden weergegeven.
|pinned|ColumnPinDirection|De pinrichting van de kolom (links, rechts, automatisch).
|sortable|boolean|Als het waar is, kan de kolom gesorteerd worden.
|sort|SortDirection|De sorteervolgorde van de kolom.
|type|ColumnType|Het type van de kolom (tekst, nummer, boolean, enzovoort).
|minWidth|number|De minimale breedte van de kolom in pixels.
