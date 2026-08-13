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

Ein Renderer steuert, wie jede Zelle in einer Spalte angezeigt wird. Anstatt einen Rohwert anzuzeigen, wandelt ein Renderer die Daten jeder Zelle in stilisierten Text, Symbole, Abzeichen, Links, Aktionsschaltflächen oder jede andere visuelle Darstellung um, die es erleichtert, die Daten schnell zu lesen und zu handeln.

Das Rendering erfolgt vollständig im Browser. Der Server sendet Rohdaten und der Client übernimmt die Präsentation, was die 'Tabelle' unabhängig von der Zeilenanzahl schnell macht.

Weisen Sie einem Renderer eine Spalte mit `setRenderer()` zu. Der Renderer wird einheitlich auf jede Zelle in dieser Spalte angewendet:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderer vs. Wertgeber
Wenn Sie lediglich einen Zellwert umwandeln oder formatieren müssen, ohne eine DOM-Struktur zu erzeugen, verwenden Sie stattdessen einen [Wertgeber](/docs/components/table/columns#value-providers). Renderer erzeugen zusätzliche DOM-Elemente für jede gerenderte Zeile, was beim Rendern zusätzliche Kosten verursacht. Reservieren Sie Renderer für visuelle Ausgaben wie Symbole, Abzeichen, Schaltflächen oder jede HTML-basierte Präsentation.
:::

webforJ wird mit integrierten Renderer für die gängigsten Anwendungsfälle geliefert. Für alles, was spezifisch für Ihre App ist, erweitern Sie `Renderer` und implementieren Sie `build()`, um einen lodash-Template-String zurückzugeben, der im Browser für jede Zelle ausgeführt wird.

## Häufige Renderer {#common-renderers}

Die folgenden Beispiele zeigen vier häufig verwendete Renderer und demonstrieren das `setRenderer()`-Muster in der Praxis.

### TextRenderer {#text-renderer}

Stellt den Zellinhalt als einfachen oder stilisierten Text dar. Wenden Sie eine Themenfarbe oder Textdekoration auf eine Spalte an, ohne ihre Struktur zu ändern, wie z.B. das Hervorheben eines Prioritätsfelds in Rot oder das Fettformatieren eines Schlüsselidentifikators.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Umhüllt den Zellwert mit einem Abzeichenelement. Unterstützt Themen, Expanses, Farbseeding (automatisch verschiedene Farben pro eindeutigen Wert) und ein optionales führendes Symbol. Verwenden Sie es für kategoriale Werte wie Tags, Typen oder Etiketten, bei denen unterschiedliche visuelle Chips den Nutzern helfen, Zeilen schnell zu scannen und zu vergleichen.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Ersetzt `true`, `false` und `null` Werte durch Symbole. Verwenden Sie es für jede true/false-Spalte, bei der ein Symbol den Wert schneller als Text kommuniziert, wie z.B. Feature-Flags, aktive/inaktive Zustände oder Opt-in-Felder.

```java
// Standard-Symbole
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Benutzerdefinierte Symbole
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Formatiert einen numerischen Wert als Geldbetrag unter Verwendung der Regeln des angegebenen `Locale`. Verwenden Sie es für jede monetäre Spalte, bei der eine ortskorrekte Formatierung (Symbol, Trennzeichen, Dezimalstellen) wichtig ist.

```java
// US-Dollar
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro mit deutscher Lokalisierung
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Bedingtes Rendering {#conditional-rendering}

`ConditionalRenderer` wählt je nach Wert der Zelle einen anderen Renderer pro Zelle aus. Bedingungen werden nacheinander ausgewertet; der erste Treffer gewinnt. Eine Fallback-Bestimmung kann mit `otherwise()` gesetzt werden.

Das folgende Beispiel zeigt bedingtes Rendering, das auf eine Rechnungsstatusspalte angewendet wird und zwischen `BadgeRenderer`-Varianten je nach Wert wechselt:

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

Es funktioniert auch gut für numerische Schwellenwerte. Dieses Server-Dashboard verwendet `ConditionalRenderer`, um die Themen von `ProgressBarRenderer` basierend auf den CPU- und Speicherauslastungsstufen zu wechseln:

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

### Bedingungs-API {#condition-api}

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

// Boolean/Leere
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// String-Matching
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Kombination
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Überprüfung über Spalten hinweg
Condition.column("status").equalTo("active")

// Roh-JavaScript-Ausdruck
Condition.expression("cell.value % 2 === 0")
```

## Komposit-Rendering {#composite-rendering}

`CompositeRenderer` kombiniert mehrere Renderer nebeneinander in einer einzigen Zelle mithilfe eines Flex-Layouts. Verwenden Sie es, um ein Symbol mit Text zu kombinieren, ein Avatar neben einem Namen anzuzeigen oder ein Abzeichen neben einem Statusindikator zu stapeln.

Das folgende Mitarbeitungsverzeichnis verwendet einen `CompositeRenderer` in der *Mitarbeiter*-Spalte, um ein automatisch generiertes Avatar neben dem Namen jedes Mitarbeiters anzuzeigen:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Benutzerdefinierte Renderer {#custom-renderers}

Wenn kein integrierter Renderer für Ihren Anwendungsfall geeignet ist, erweitern Sie `Renderer` und implementieren Sie `build()`. Die Methode gibt einen lodash-Template-String zurück, der im Browser für jede Zelle in der Spalte ausgeführt wird und als Mischung aus HTML und JavaScript ausgedrückt wird.

### Erstellen eines benutzerdefinierten Renderers {#creating-a-custom-renderer}

**Schritt 1:** Erweitern Sie `Renderer` mit Ihrem Zeilendatentyp.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Schritt 2:** Überschreiben Sie `build()` und geben Sie einen lodash-Template-String zurück.

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
Für weitere Informationen zur Lodash-Syntax, die zum Zugreifen auf Zellinformationen und zum Erstellen informativer Renderer verwendet wird, siehe [diesen Referenzabschnitt](#template-reference).
:::

### Zugriff auf mehrere Spalten {#accessing-multiple-columns}

Verwenden Sie `cell.row.getValue("columnId")`, um auf Nachbarspalten innerhalb der Vorlage zuzugreifen. Dies ist nützlich, um Felder zu kombinieren, Deltas zu berechnen oder verwandte Daten zu quellen.

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

### Klickevents {#click-events}

`IconButtonRenderer` und `ButtonRenderer` stellen `addClickListener()` out of the box zur Verfügung. Das Klick-Ereignis bietet Zugriff auf das Datenobjekt der Zeile über `e.getItem()`.

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

## Leistung: faul Rendering <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Für Spalten, die visuell aufwendige Renderer wie Abzeichen, Fortschrittsbalken, Avatare oder Webkomponenten verwenden, aktivieren Sie faules Rendering, um die Scrollleistung zu verbessern.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wenn `setLazyRender(true)` für eine Spalte gesetzt ist, werden die Zellen während des Scrollens mit einem leichten animierten Platzhalter angezeigt. Der tatsächliche Zellinhalt wird gerendert, sobald das Scrollen stoppt. Dies ist eine Einstellung auf Spaltenebene, sodass Sie sie selektiv nur für die Spalten aktivieren können, die davon profitieren.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Wann faules Rendering aktivieren
Zell-Renderer erzeugen mehr Entitäten im DOM, was während des Renderns mehr CPU-Arbeit bedeutet, unabhängig davon, welcher Renderer es erstellt.

Faules Rendering kann helfen, die Leistungsauswirkungen zu reduzieren, wenn ein Renderer wirklich benötigt wird. Wenn Sie nur den Wert ändern oder formatieren müssen und kein komplexes DOM erzeugen, verwenden Sie stattdessen einen Wertgeber, um den Wert zu transformieren.
:::

## Eingebaute Renderer-Referenz {#built-in-renderers}

webforJ wird mit einem umfassenden Satz an Renderern für die gängigsten Anwendungsfälle ausgeliefert. Weisen Sie jeden von ihnen mit `column.setRenderer(renderer)` einer Spalte zu.

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

### Text und Labels {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  stilisierter Text mit optionalem Thema und Dekorationen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Stellt den Zellinhalt als einfachen oder stilisierten Text dar. Unterstützt Themenfarben und Textdekorationen wie fett, kursiv und unterstrichen.

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
<strong>BadgeRenderer</strong>  -  Wert in einem Abzeichen-Chip dargestellt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wrapt den Zellwert in einem Abzeichenelement. Unterstützt Themen, Expanses, Farbseeding (automatisch verschiedene Farben pro eindeutigen Wert) und ein optionales führendes Symbol.

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
<strong>NullRenderer</strong>  -  Platzhalter für null oder leere Werte
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert eine konfigurierbare Fallback-Zeichenfolge, wenn der Zellwert `null` oder leer ist; andernfalls wird der Wert wie er ist gerendert.

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
<strong>BooleanRenderer</strong>  -  true/false/null als Symbole dargestellt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Ersetzt `true`, `false` und `null` Werte durch Symbole. Standardmäßig wird ein Häkchen, ein Kreuz und ein Strich verwendet.

```java
// Standard-Symbole
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Benutzerdefinierte Symbole
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
<strong>StatusDotRenderer</strong>  -  farbiger Indikatorpunkt neben Zelltext
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert einen kleinen farbigen Punkt links vom Zellwert. Ordnen Sie einzelne Werte Themen, CSS-Farbcodes oder `java.awt.Color`-Instanzen zu.

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

### Zahlen, Währung und Daten {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  ortsbasierte Währungsformatierung
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen numerischen Wert als Geldbetrag unter Verwendung der Regeln des angegebenen `Locale`.

```java
// US-Dollar
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro mit deutscher Lokalisierung
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  Prozentsatz mit optionalem Mini-Fortschrittsbalken
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Stellt einen numerischen Wert als Prozentsatz dar. Setzen Sie das zweite Konstruktorargument auf `false`, um zu verhindern, dass ein dünner Fortschrittsbalken unter dem Text gerendert wird.

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

Rendert einen vollflächigen Fortschrittsbalken mit konfigurierbaren minimalen und maximalen Bereichen, unbestimmtem Modus und gestreifter oder animierter Anzeige. Verwenden Sie `setText()` mit einem lodash-Ausdruck, um benutzerdefinierten Text auf dem Balken zu überlagern.

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
<strong>MaskedTextRenderer</strong>  -  Zeichenfolge mit einer Textmaske formatiert
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wendet eine Zeichenmaske auf einen String-Wert an. `#` entspricht einer beliebigen Ziffer; literale Zeichen werden beibehalten. Siehe [Textmaskenregeln](/docs/components/fields/masked/textfield#mask-rules) für alle unterstützten Maskenzeichen.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numerischer Wert mit einer Zahlenmaske formatiert
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen numerischen Wert mit einer Musterzeichenfolge und ortsabhängigen Trennzeichen. `0` erzwingt eine Ziffer; `#` ist optional. Siehe [Zahlenmaskenregeln](/docs/components/fields/masked/numberfield#mask-rules) für alle unterstützten Maskenzeichen.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  Datum/Uhrzeit-Wert mit einer Datumsmaske
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen Datum- oder Zeitwert mit Muster-Token: `%Mz` (Monat), `%Dz` (Tag), `%Yz` (Jahr) und anderen. Siehe [Datums-Maskenregeln](/docs/components/fields/masked/datefield#mask-rules) für alle verfügbaren Token.

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

Umhüllt den Zellwert in einem `mailto:`-Anker. Ein primär thematisiertes Mailsymbol dient standardmäßig als visuelles Signal.

```java
// Standard-Mail-Symbol
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Benutzerdefiniertes Symbol
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

Umhüllt den Zellwert in einem `tel:`-Anker. Auf mobilen Geräten wird durch Tippen der Wählbildschirm geöffnet. Ein primär thematisiertes Telefonsymbol wird standardmäßig angezeigt.

```java
// Standard-Telefonsymbol
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Benutzerdefiniertes Symbol
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  Zellwert als konfigurierbarer Hyperlink
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert ein klickbares Ankerelement. Das `href` unterstützt lodash-Template-Ausdrücke, sodass Sie URLs dynamisch aus dem Zellwert erstellen können.

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
<strong>ImageRenderer</strong>  -  inline Bild in einer Zelle
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Stellt ein Bild dar. Das `src`-Attribut unterstützt lodash-Template-Ausdrücke, sodass jede Zeile ein anderes Bild anzeigen kann.

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

Rendert eine Avatarkomponente. Die Initialen werden automatisch aus dem Zellwert abgeleitet. Unterstützt Themen und ein Fallback-Symbol.

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

### Symbole und Aktionen {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  eigenständiges Symbol, optional anklickbar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rendert ein einzelnes Symbol. Fügen Sie einen Klicklistener für interaktives Verhalten hinzu.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  interaktiver Icon-Button mit Zeilenzugriff
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert einen klickbaren Icon-Button. Das Klickereignis macht das Zeilenobjekt über `e.getItem()` verfügbar, was es ideal für zeilenbasierte Aktionen macht.

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

Rendert eine vollständige `Button`-Komponente innerhalb der Zelle.

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

Rendert jedes HTML-Element mit einem lodash-Template-Inhalt-String. Dies ist der Notausgang für Situationen, in denen kein integrierter Renderer geeignet ist.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Template-Referenz {#template-reference}

Renderer bieten einen leistungsstarken Mechanismus zur Anpassung der Art und Weise, wie Daten innerhalb einer `Tabelle` angezeigt werden. Die Hauptklasse, `Renderer`, ist dafür ausgelegt, erweitert zu werden, um benutzerdefinierte Renderer auf der Grundlage von lodash-Templates zu erstellen, die das dynamische und interaktive Rendern von Inhalten ermöglichen.

Lodash-Templates ermöglichen das Einfügen von HTML direkt in Tabellenspalten, wodurch sie sehr effektiv für das Rendern komplexer Zellendaten in einer `Tabelle` sind. Dieser Ansatz ermöglicht die dynamische Generierung von HTML basierend auf Zellendaten und erleichtert reichhaltige und interaktive Inhalte in den Tabellenspalten.

### Lodash-Syntax {#lodash-syntax}

Der folgende Abschnitt beschreibt die Grundlagen der Lodash-Syntax. Während dies kein vollständiger oder umfassender Überblick ist, kann er helfen, Lodash innerhalb der `Tabelle`-Komponente zu verwenden.

#### Syntaxübersicht für lodash-Templates: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoliert Werte, indem das Ergebnis des JavaScript-Codes in die Vorlage eingefügt wird.
- `<% ... %>` - Führt JavaScript-Code aus, was Schleifen, bedingte Anweisungen und mehr ermöglicht.
- `<%- ... %>` - Entkommt HTML-Inhalten, um sicherzustellen, dass interpolierte Daten sicher vor HTML-Injection-Angriffen sind.

#### Beispiele unter Verwendung von Zellendaten: {#examples-using-cell-data}

**1. Einfache Wertinterpolation**: Zeigt direkt den Zellwert an.

`<%= cell.value %>`

**2. Bedingtes Rendering**: Verwendet JavaScript-Logik, um Inhalte bedingt zu rendern.

`<% if (cell.value > 100) { %> 'Hoch' <% } else { %> 'Normal' <% } %>`

**3. Kombinieren von Datenfeldern**: Rendert Inhalte unter Verwendung mehrerer Datenfelder aus der Zelle.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Entkommen von HTML-Inhalten**: Rendert sicher benutzergenerierte Inhalte.

Der Renderer hat Zugriff auf detaillierte Zell-, Zeilen- und Spalteneigenschaften auf der Client-Seite:

**Tabellenzellen-Eigenschaften:**

| Eigenschaft | Typ | Beschreibung |
|-------------|-----|--------------|
| column      | `TableColumn` | Das zugehörige Spaltenobjekt. |
| first       | `boolean` | Gibt an, ob die Zelle die erste in der Zeile ist. |
| id          | `String` | Die Zell-ID. |
| index       | `int` | Der Index der Zelle innerhalb ihrer Zeile. |
| last        | `boolean` | Gibt an, ob die Zelle die letzte in der Zeile ist. |
| row         | `TableRow` | Das zugehörige Zeilenobjekt für die Zelle. |
| value       | `Object` | Der Rohwert der Zelle, direkt aus der Datenquelle. |

**Tabellenzeilen-Eigenschaften:**

| Eigenschaft | Typ | Beschreibung |
|-------------|-----|--------------|
| cells       | `TableCell[]` | Die Zellen innerhalb der Zeile. |
| data        | `Object` | Die von der App für die Zeile bereitgestellten Daten. |
| even        | `boolean` | Gibt an, ob die Zeile eine gerade Nummer hat (für Stylingzwecke). |
| first       | `boolean` | Gibt an, ob die Zeile die erste in der Tabelle ist. |
| id          | `String` | Eindeutige ID für die Zeile. |
| index       | `int` | Der Zeilenindex. |
| last        | `boolean` | Gibt an, ob die Zeile die letzte in der Tabelle ist. |
| odd         | `boolean` | Gibt an, ob die Zeile eine ungerade Nummer hat (für Stylingzwecke). |

**Tabellenspalten-Eigenschaften:**

| Eigenschaft | Typ | Beschreibung |
|-------------|-----|--------------|
| align       | ColumnAlignment | Die Ausrichtung der Spalte (links, Mitte, rechts). |
| id          | String | Das Feld des Zeilenobjekts, um die Zellendaten abzurufen. |
| label       | String | Der Name, der in der Spaltenüberschrift gerendert wird. |
| pinned      | ColumnPinDirection | Die Anheftungsrichtung der Spalte (links, rechts, automatisch). |
| sortable    | boolean | Wenn wahr, kann die Spalte sortiert werden. |
| sort        | SortDirection | Die Sortierreihenfolge der Spalte. |
| type        | ColumnType | Der Typ der Spalte (Text, Zahl, Boolean usw.). |
| minWidth    | number | Die minimale Breite der Spalte in Pixel. |
