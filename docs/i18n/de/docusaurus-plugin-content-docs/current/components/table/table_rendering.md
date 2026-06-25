---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 8eb5ec6f614d406b57a70fb7472636d5
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Ein Renderer steuert, wie jede Zelle in einer Spalte angezeigt wird. Anstatt einen Rohwert anzuzeigen, transformiert ein Renderer die Daten jeder Zelle in formatierte Texte, Icons, Abzeichen, Links, Aktionsknöpfe oder jede andere Visualisierung, die die Daten schneller lesbar und einfacher umsetzbar macht.

Das Rendering erfolgt vollständig im Browser. Der Server sendet Rohdaten und der Client übernimmt die Präsentation, wodurch die 'Tabelle' unabhängig von der Zeilenanzahl schnell bleibt.

Weisen Sie einem Renderer eine Spalte mit `setRenderer()` zu. Der Renderer wird einheitlich auf jede Zelle in dieser Spalte angewendet:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderer vs. Wertanbieter
Wenn Sie nur einen Zellwert transformieren oder formatieren müssen, ohne eine DOM-Struktur zu erzeugen, verwenden Sie stattdessen einen [Wertanbieter](/docs/components/table/columns#value-providers). Renderer erzeugen zusätzliche DOM-Elemente für jede gerenderte Zeile, was während des Renderns Kosten verursacht. Reservieren Sie Renderer für visuelle Ausgaben wie Icons, Abzeichen, Schaltflächen oder andere HTML-basierte Präsentationen.
:::

webforJ verfügt über integrierte Renderer für die häufigsten Anwendungsfälle. Für alles, was spezifisch für Ihre App ist, erweitern Sie `Renderer` und implementieren Sie `build()`, um einen lodash-Vorlagenstring zurückzugeben, der im Browser für jede Zelle ausgeführt wird.

## Häufig verwendete Renderer {#common-renderers}

Die folgenden Beispiele erläutern vier häufig verwendete Renderer und zeigen das `setRenderer()`-Muster in der Praxis.

### TextRenderer {#text-renderer}

Zeigt den Zellinhalt als einfachen oder formatierten Text an. Wenden Sie eine Themenfarbe oder Textdekoration auf eine Spalte an, ohne deren Struktur zu ändern, wie z.B. Hervorhebung eines Prioritätsfeldes in Rot oder Fettdruck eines Schlüsselbezeichners.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Umhüllt den Zellwert in einem Abzeichen-Element. Unterstützt Themen, Ausdehnungen, Farben (automatische unterschiedliche Farben pro einzigartigem Wert) und ein optionales führendes Icon. Verwenden Sie es für kategoriale Werte wie Tags, Typen oder Bezeichnungen, bei denen unterschiedliche visuelle Chips den Benutzern helfen, Zeilen schnell zu scannen und zu vergleichen.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Ersetzt `true`, `false` und `null`-Werte durch Icons. Verwenden Sie es für jede true/false-Spalte, bei der ein Icon den Wert schneller kommuniziert als Text, wie z.B. bei Feature-Flags, aktiven/inaktiven Zuständen oder Opt-in-Feldern.

```java
// Standard-Icons
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Benutzerdefinierte Icons
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Formatiert einen numerischen Wert als Währungsbetrag unter Verwendung der Regeln der angegebenen `Locale`. Verwenden Sie es für jede monetäre Spalte, bei der die lokalisierte Formatierung (Symbol, Trennzeichen, Dezimalstellen) wichtig ist.

```java
// US-Dollar
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro mit deutscher Locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Bedingtes Rendering {#conditional-rendering}

`ConditionalRenderer` wählt einen anderen Renderer pro Zelle basierend auf dem Wert der Zelle aus. Die Bedingungen werden in der Reihenfolge ausgewertet; der erste Treffer gewinnt. Ein Auffangbecken kann mit `otherwise()` festgelegt werden.

Das folgende Beispiel zeigt bedingtes Rendering, das auf eine Rechnungsstatusspalte angewendet wird, und wechselt zwischen `BadgeRenderer`-Varianten abhängig vom Wert:

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

Es funktioniert auch gut für numerische Schwellenwerte. Dieses Server-Dashboard verwendet `ConditionalRenderer`, um die Themen des `ProgressBarRenderer` basierend auf CPU- und Speicherverbrauchsniveaus zu wechseln:

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

### Bedingungen API {#condition-api}

Bedingungen werden mit statischen Fabrikmethoden erstellt und können mit `and()`, `or()` und `negate()` kombiniert werden.

```java
// Wertgleichheit
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numerische Vergleiche
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / Leere
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// Zeichenfolgenabgleich
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Zusammensetzung
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Überprüfung über Spalten hinweg
Condition.column("status").equalTo("active")

// Raw JavaScript-Ausdruck
Condition.expression("cell.value % 2 === 0")
```

## Komposit-Rendering {#composite-rendering}

`CompositeRenderer` kombiniert mehrere Renderer nebeneinander in einer einzigen Zelle unter Verwendung eines Flex-Layouts. Verwenden Sie es, um ein Icon mit Text zu kombinieren, einen Avatar neben einem Namen anzuzeigen oder ein Abzeichen neben einem Statusindikator zu stapeln.

Das Mitarbeitereinverzeichnis unten verwendet einen `CompositeRenderer` in der *Mitarbeiter*-Spalte, um einen automatisch generierten Avatar neben dem Namen jedes Mitarbeiters anzuzeigen:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Benutzerdefinierte Renderer {#custom-renderers}

Wenn kein integrierter Renderer zu Ihrem Anwendungsfall passt, erweitern Sie `Renderer` und implementieren Sie `build()`. Die Methode gibt einen lodash-Vorlagenstring zurück, der im Browser für jede Zelle in der Spalte ausgeführt wird und eine Mischung aus HTML und JavaScript ausdrückt.

### Erstellen eines benutzerdefinierten Renderers {#creating-a-custom-renderer}

**Schritt 1:** Erweitern Sie `Renderer` mit Ihrem Zeilendatentyp.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Schritt 2:** Überschreiben Sie `build()` und geben Sie einen lodash-Vorlagenstring zurück.

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

**Schritt 3:** Weisen Sie den Renderer einer Spalte zu.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Für weitere Informationen zur Verwendung von Lodash-Syntax zum Zugriff auf Zellinformationen und zur Erstellung informativer Renderer siehe [diesen Referenzabschnitt](#template-reference).
:::

### Zugriff auf mehrere Spalten {#accessing-multiple-columns}

Verwenden Sie `cell.row.getValue("columnId")`, um Geschwisterspalten innerhalb der Vorlage auszulesen. Dies ist nützlich, um Felder zu kombinieren, Deltas zu berechnen oder verwandte Daten abzugleichen.

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

### Klickereignisse {#click-events}

`IconButtonRenderer` und `ButtonRenderer` bieten `addClickListener()` standardmäßig an. Das Klickereignis bietet über `e.getItem()` Zugriff auf das Datenobjekt der Zeile.

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

## Leistung: Faules Rendering <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Für Spalten, die visuell aufwändige Renderer wie Abzeichen, Fortschrittsbalken, Avatare oder Webkomponenten verwenden, aktivieren Sie faules Rendering, um die Scroll-Leistung zu verbessern.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wenn `setLazyRender(true)` für eine Spalte gesetzt ist, zeigen Zellen einen leichten animierten Platzhalter an, während der Benutzer scrollt. Der eigentliche Zellinhalt wird gerendert, sobald das Scrollen stoppt. Dies ist eine spaltenweite Einstellung, sodass Sie sie selektiv nur für die Spalten aktivieren können, die davon profitieren.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Wann faules Rendering aktivieren
Zellen-Renderer erstellen mehr Entitäten innerhalb des DOMs, was mehr CPU-Arbeit während des Renderns bedeutet, unabhängig davon, welcher Renderer es erzeugt.

Faules Rendering kann helfen, die Leistungseinbußen zu reduzieren, wenn ein Renderer wirklich benötigt wird. Wenn Sie nur den Wert ändern oder formatieren müssen und kein komplexes DOM erstellen, verwenden Sie stattdessen einen Wertanbieter, um den Wert zu transformieren.
:::

## Referenz der integrierten Renderer {#built-in-renderers}

webforJ wird mit einem umfassenden Satz von Renderern für die häufigsten Anwendungsfälle geliefert. Weisen Sie einen beliebigen davon einer Spalte mit `column.setRenderer(renderer)` zu.

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

### Texte und Bezeichnungen {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  formatierter Text mit optionalem Thema und Dekorationen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt den Zellinhalt als einfachen oder formatierten Text an. Unterstützt Themenfarben und Textdekorationen wie fett, kursiv und unterstrichen.

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
<strong>BadgeRenderer</strong>  -  Wert, der in einem Abzeichen-Chip angezeigt wird
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Umhüllt den Zellwert in einem Abzeichen-Element. Unterstützt Themen, Ausdehnungen, Farbseeding (automatisch unterschiedliche Farben pro einzigartigem Wert) und ein optionales führendes Icon.

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
<strong>NullRenderer</strong>  -  Platzhalter für null- oder leere Werte
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Gerenderte eine konfigurierbare Rückfallzeichenfolge, wenn der Zellwert `null` oder leer ist; andernfalls wird der Wert unverändert gerendert.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Status und Indikatoren {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null als Icons angezeigt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Ersetzt `true`, `false` und `null`-Werte durch Icons. Standardmäßig ein Häkchen, ein Kreuz und ein Strich.


```java
// Standard-Icons
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Benutzerdefinierte Icons
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
<strong>StatusDotRenderer</strong>  -  farbiger Indikatorpunkt neben dem Zelltext
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt einen kleinen farbigen Punkt links vom Zellwert an. Ordnen Sie einzelne Werte Themen, CSS-Farbcodes oder `java.awt.Color`-Instanzen zu.

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

### Zahlen, Währungen und Daten {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  lokalisierte Währungsformatierung
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen numerischen Wert als Währungsbetrag unter Verwendung der Regeln der angegebenen `Locale`.

```java
// US-Dollar
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro mit deutscher Locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  Prozentsatz mit optionalem Minifortschrittsbalken
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt einen numerischen Wert als Prozentsatz an. Setzen Sie das zweite Konstruktorargument auf `false`, um das Rendern eines dünnen Fortschrittbalkens unter dem Text zu verhindern.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  vollständiger Fortschrittsbalken für numerische Werte
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Gerendert einen vollflächigen Fortschrittsbalken mit konfigurierbaren minimalen und maximalen Werten, unbestimmtem Modus und gestreiftem oder animiertem Display. Verwenden Sie `setText()` mit einem lodash-Ausdruck, um benutzerdefinierten Text auf dem Balken zu überlagern.

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
<strong>MaskedTextRenderer</strong>  -  Zeichenfolge, die mit einer Textmaske formatiert ist
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wendet eine Zeichenmaskierung auf einen Zeichenfolgewert an. `#` entspricht jeder Ziffer; literale Zeichen werden beibehalten. Siehe [Textmaskenregeln](/docs/components/fields/masked/textfield#mask-rules) für alle unterstützten Maskenzeichen.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numerischer Wert, der mit einer Zahlenmaske formatiert ist
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen numerischen Wert mithilfe eines Musters mit lokalisierten Trennzeichen. `0` zwingt eine Ziffer; `#` ist optional. Siehe [Zahlenmaskenregeln](/docs/components/fields/masked/numberfield#mask-rules) für alle unterstützten Maskenzeichen.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  Datums-/Zeitwert mit einer Datumsmaske
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen Datum- oder Zeitwert mithilfe von Mustertokens: `%Mz` (Monat), `%Dz` (Tag), `%Yz` (Jahr) und anderen. Siehe [Datumsmaskenregeln](/docs/components/fields/masked/datefield#mask-rules) für alle verfügbaren Tokens.

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Links und Medien {#links-and-media}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  E-Mail-Adresse als klickbarer mailto-Link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Umhüllt den Zellwert in einem `mailto:`-Link. Ein primär thematisches Mail-Icon dient standardmäßig als visuelles Element.

```java
// Standardmail-Icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Benutzerdefiniertes Icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  Telefonnummer als klickbarer tel-Link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Umhüllt den Zellwert in einem `tel:`-Link. Auf mobilen Geräten wird beim Tippen die Wählfunktion geöffnet. Ein primär thematisches Telefon-Icon wird standardmäßig angezeigt.

```java
// Standardtelefon-Icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Benutzerdefiniertes Icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  Zellwert als konfigurierter Hyperlink
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Gerendert ein klickbares Anker-Element. Das `href` unterstützt lodash-Vorlagen-Ausdrücke, sodass Sie URLs dynamisch aus dem Zellwert erstellen können.

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
<strong>ImageRenderer</strong>  -  Inline-Bild in einer Zelle
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt ein Bild an. Das `src`-Attribut unterstützt lodash-Vorlagen-Ausdrücke, sodass jede Zeile ein unterschiedliches Bild anzeigen kann.

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

### Personen und Avatare {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  Avatar mit automatisch generierten Initialen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Gerendert eine Avatar-Komponente. Die Initialen werden automatisch aus dem Zellwert abgeleitet. Unterstützt Themen und ein Fallback-Icon.

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

### Icons und Aktionen {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  eigenständiges Icon, optional anklickbar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Gerendert ein einzelnes Icon. Fügen Sie einen Klicklistener für interaktive Funktionen hinzu.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  anklickbarer Icon-Button mit Zeilenzugriff
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Gerendert ein klickbarer Icon-Button. Das Klickereignis gibt das Zeilenobjekt über `e.getItem()` frei, was es ideal für zeilenbezogene Aktionen macht.

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
<strong>ButtonRenderer</strong>  -  thematisierter Button in einer Zelle
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Gerendert eine vollständige `Button`-Komponente innerhalb der Zelle.

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
<strong>ElementRenderer</strong>  -  rohes HTML-Element mit lodash-Inhalt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Gerendert ein beliebiges HTML-Element mit einem lodash-Vorlageninhalt. Dies ist der Fluchtweg für Situationen, in denen kein integrierter Renderer passt.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Vorlagenreferenz {#template-reference}

Renderer bieten einen leistungsstarken Mechanismus zur Anpassung der Art und Weise, wie Daten in einer `Tabelle` angezeigt werden. Die Hauptklasse `Renderer` ist dafür ausgelegt, erweitert zu werden, um benutzerdefinierte Renderer basierend auf lodash-Vorlagen zu erstellen, die die dynamische und interaktive Inhaltserstellung ermöglichen.

Lodash-Vorlagen ermöglichen das Einfügen von HTML direkt in Tabellenzellen, wodurch sie äußerst effektiv zur Darstellung komplexer Zellendaten in einer `Tabelle` werden. Dieser Ansatz erlaubt die dynamische Generierung von HTML basierend auf Zellendaten und erleichtert einen reichen und interaktiven Tabellenzellinhalt.

### Lodash-Syntax {#lodash-syntax}

Der folgende Abschnitt beschreibt die Grundlagen der Lodash-Syntax. Während dies keine vollständige oder umfassende Übersicht ist, kann es hilfreich sein, um mit Lodash innerhalb der `Tabelle`-Komponente zu beginnen.

#### Syntaxüberblick für lodash-Vorlagen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoliert Werte, indem das Ergebnis des JavaScript-Codes in die Vorlage eingefügt wird.
- `<% ... %>` - Führt JavaScript-Code aus und erlaubt Schleifen, Bedingungsaussprüche und mehr.
- `<%- ... %>` - Entkommt HTML-Inhalten, um sicherzustellen, dass interpolierte Daten vor HTML-Injection-Angriffen sicher sind.

#### Beispiele mit Zellendaten: {#examples-using-cell-data}

**1. Einfache Wertinterpolation**: zeigt direkt den Wert der Zelle an.

`<%= cell.value %>`

**2. Bedingte Darstellung**: verwendet JavaScript-Logik, um Inhalte bedingt darzustellen.

`<% if (cell.value > 100) { %> 'Hoch' <% } else { %> 'Normal' <% } %>`

**3. Kombination von Datenfeldern**: rendert Inhalte unter Verwendung mehrerer Datenfelder aus der Zelle.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Entkommen von HTML-Inhalten**: sicherstellen, dass benutzergenerierte Inhalte sicher gerendert werden.

Der Renderer hat Zugriff auf detaillierte Zell-, Zeilen- und Spalteneigenschaften auf der Clientseite:

**Eigenschaften der Tabelle-Zelle:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|column|`TableColumn`|Das zugehörige Spaltenobjekt.|
|first|`boolean`|Gibt an, ob die Zelle die erste in der Zeile ist.|
|id|`String`|Die Zell-ID.|
|index|`int`|Der Index der Zelle innerhalb ihrer Zeile.|
|last|`boolean`|Gibt an, ob die Zelle die letzte in der Zeile ist.|
|row|`TableRow`|Das zugehörige Zeilenobjekt für die Zelle.|
|value|`Object`|Der Rohwert der Zelle, direkt aus der Datenquelle.|

**Eigenschaften der Tabelle-Zeile:**

|Eigenschaft|Typ|Beschreibung|
|-|-|-|
|cells|`TableCell[]`|Die Zellen innerhalb der Zeile.
|data|`Object`|Die vom App für die Zeile bereitgestellten Daten.
|even|`boolean`|Gibt an, ob die Zeile gerade nummeriert ist (zum Zwecke des Stylings).
|first|`boolean`|Gibt an, ob die Zeile die erste in der Tabelle ist.
|id|`String`|Eindeutige ID für die Zeile.
|index|`int`|Der Zeilenindex.
|last|`boolean`|Gibt an, ob die Zeile die letzte in der Tabelle ist.
|odd|`boolean`|Gibt an, ob die Zeile ungerade nummeriert ist (zum Zwecke des Stylings).

**Eigenschaften der Tabelle-Spalte:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|align|ColumnAlignment|Die Ausrichtung der Spalte (links, zentriert, rechts).
|id|String|Das Feld des Zeilenobjekts, um die Zellendaten abzurufen.
|label|String|Der Name, der in der Spaltenüberschrift gerendert wird.
|pinned|ColumnPinDirection|Die Pinnrichtung der Spalte (links, rechts, automatisch).
|sortable|boolean|Wenn wahr, kann die Spalte sortiert werden.
|sort|SortDirection|Die Sortierreihenfolge der Spalte.
|type|ColumnType|Der Typ der Spalte (Text, Zahl, Boolean usw.).
|minWidth|number|Die Mindestbreite der Spalte in Pixel.
