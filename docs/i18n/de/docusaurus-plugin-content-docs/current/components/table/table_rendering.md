---
sidebar_position: 20
title: Rendering
slug: rendering
sidebar_class_name: new-content
_i18n_hash: c6f33a66de68ddcd600382bf0dc449f2
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Ein Renderer steuert, wie jede Zelle in einer Spalte angezeigt wird. Anstatt einen Rohwert anzuzeigen, transformiert ein Renderer die Daten jeder Zelle in formatisierten Text, Symbole, Abzeichen, Links, Aktionsschaltflächen oder andere visuelle Elemente, die es einfacher machen, die Daten schnell zu lesen und zu verarbeiten.

Die Darstellung erfolgt vollständig im Browser. Der Server sendet Rohdaten und der Client kümmert sich um die Präsentation, wodurch die 'Tabelle' unabhängig von der Zeilenzahl schnell bleibt.

Weisen Sie einer Spalte einen Renderer mit `setRenderer()` zu. Der Renderer wird einheitlich auf jede Zelle in dieser Spalte angewendet:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderer vs. Wertanbieter
Wenn Sie nur einen Zellwert transformieren oder formatieren müssen, ohne eine DOM-Struktur zu erstellen, verwenden Sie stattdessen einen [Wertanbieter](/docs/components/table/columns#value-providers). Renderer erzeugen zusätzliche DOM-Elemente für jede gerenderte Zeile, was beim Rendern Kosten verursacht. Reservieren Sie Renderer für visuelle Ausgaben wie Symbole, Abzeichen, Schaltflächen oder jede HTML-basierte Präsentation.
:::

webforJ liefert eingebaute Renderer für die häufigsten Anwendungsfälle. Für alles, was spezifisch für Ihre Anwendung ist, erweitern Sie `Renderer` und implementieren Sie `build()`, um eine lodash-Vorlagensitzzeichenfolge zurückzugeben, die im Browser für jede Zelle ausgeführt wird.

## Häufige Renderer {#common-renderers}

Die folgenden Beispiele erläutern vier häufig verwendete Renderer und demonstrieren das `setRenderer()`-Muster in der Praxis.

### TextRenderer {#text-renderer}

Stellt den Inhalt der Zelle als normalen oder formatierten Text dar. Wenden Sie eine Themenfarbe oder Textdekoration auf eine Spalte an, ohne ihre Struktur zu ändern, beispielsweise indem Sie ein Prioritätsfeld rot hervorheben oder einen Schlüsselidentifikator fett machen.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Umhüllt den Zellwert in einem Abzeichen-Element. Unterstützt Themen, Erweiterungen, Farbsaat (automatische unterschiedliche Farben pro eindeutigen Wert) und ein optionales führendes Symbol. Verwenden Sie es für kategorische Werte wie Tags, Typen oder Labels, bei denen verschiedene visuelle Chips den Benutzern helfen, Zeilen schnell zu scannen und zu vergleichen.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Ersetzt `true`, `false` und `null`-Werte durch Symbole. Verwenden Sie es für jede Spalte mit Wahr/Falsch, bei der ein Symbol den Wert schneller kommuniziert als Text, wie z.B. Feature-Flags, aktive/inaktive Zustände oder Opt-in-Felder.

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

Formatiert einen numerischen Wert als Währungsbetrag unter Verwendung der Regeln des angegebenen `Locale`. Verwenden Sie es für jede monetäre Spalte, bei der eine lokale korrekte Formatierung (Symbol, Trennzeichen, Dezimalstellen) von Bedeutung ist.

```java
// US-Dollar
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro mit deutscher Lokalisierung
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Bedingte Darstellung {#conditional-rendering}

`ConditionalRenderer` wählt einen anderen Renderer für jede Zelle basierend auf dem Zellwert aus. Bedingungen werden der Reihe nach ausgewertet; das erste zutreffende Ergebnis gewinnt. Ein Auffangbecken kann mit `otherwise()` festgelegt werden.

Das folgende Beispiel zeigt die bedingte Darstellung, die auf eine Rechnungsstatusspalte angewendet wird, und zwischen `BadgeRenderer`-Varianten basierend auf dem Wert wechselt:

<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java']}
height='600px'
/>
<!-- vale on -->

Es funktioniert auch gut für numerische Schwellenwerte. Dieses Server-Dashboard verwendet `ConditionalRenderer`, um die Themen von `ProgressBarRenderer` basierend auf CPU- und Speicherauslastungsniveaus zu wechseln:

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Server.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerService.java']}
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

// Wahrheits-/Leeres
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

// Spaltenüberprüfung
Condition.column("status").equalTo("active")

// Roh-JavaScript-Ausdruck
Condition.expression("cell.value % 2 === 0")
```

## Komposite Darstellung {#composite-rendering}

`CompositeRenderer` kombiniert mehrere Renderer nebeneinander in einer einzelnen Zelle mit einem Flex-Layout. Verwenden Sie es, um ein Symbol mit Text zu kombinieren, ein Avatar neben einem Namen anzuzeigen oder ein Abzeichen neben einem Statusindikator zu stapeln.

Das Mitarbeiterverzeichnis verwendet einen `CompositeRenderer` in der *Mitarbeiter* Spalte, um jedes Mitarbeiteravatar neben dem Namen anzuzeigen:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java'
height='600px'
/>
<!-- vale on -->

## Benutzerdefinierte Renderer {#custom-renderers}

Wenn kein eingebauter Renderer zu Ihrem Anwendungsfall passt, erweitern Sie `Renderer` und implementieren Sie `build()`. Die Methode gibt eine lodash-Vorlagensitzzeichnenfolge zurück, die im Browser für jede Zelle in der Spalte ausgeführt wird, ausgedrückt als Mischung aus HTML und JavaScript.

### Erstellen eines benutzerdefinierten Renderers {#creating-a-custom-renderer}

**Schritt 1:** Erweitern Sie `Renderer` mit Ihrem Zeilendatentyp.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Schritt 2:** Überschreiben Sie `build()` und geben Sie eine lodash-Vorlagensitzzeichenfolge zurück.

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
Weitere Informationen zur Verwendung der Lodash-Syntax zum Zugreifen auf Zellinformationen und zum Erstellen informativer Renderer finden Sie in [diesem Referenzabschnitt](#template-reference).
:::

### Zugriff auf mehrere Spalten {#accessing-multiple-columns}

Verwenden Sie `cell.row.getValue("columnId")`, um Geschwisterspalten innerhalb der Vorlage zu lesen. Dies ist nützlich, um Felder zu kombinieren, Deltas zu berechnen oder verwandte Daten zu referenzieren.

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

`IconButtonRenderer` und `ButtonRenderer` bieten standardmäßig `addClickListener()` an. Das Klickereignis bietet Zugriff auf das Datenobjekt der Zeile über `e.getItem()`.

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

## Leistung: Lazy Rendering <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Für Spalten, die visuell aufwendige Renderer wie Abzeichen, Fortschrittsbalken, Avatare oder Webkomponenten verwenden, aktivieren Sie das Lazy Rendering, um die Scroll-Leistung zu verbessern.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wenn `setLazyRender(true)` für eine Spalte festgelegt ist, wird in den Zellen ein leichter animierter Platzhalter angezeigt, während der Benutzer scrollt. Der tatsächliche Zellinhalt wird gerendert, sobald das Scrollen stoppt. Dies ist eine spaltenbezogene Einstellung, die Sie selektiv nur für die Spalten aktivieren können, die davon profitieren.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java'
height='600px'
/>
<!-- vale on -->

:::tip Wann man Lazy Rendering aktivieren sollte
Zellrenderer erzeugen mehr Entitäten im DOM, was während des Renderns mehr CPU-Arbeit bedeutet, egal welcher Renderer es erzeugt. 

Das Lazy Rendering kann helfen, die Leistungsbeeinträchtigung zu reduzieren, wenn ein Renderer wirklich nötig ist. Wenn Sie nur den Wert ändern oder formatieren müssen und kein komplexes DOM erstellen, verwenden Sie stattdessen einen Wertanbieter, um den Wert zu transformieren.
:::

## Eingebaute Renderer-Referenz {#built-in-renderers}

webforJ liefert ein umfassendes Set von Renderern für die häufigsten Anwendungsfälle. Weisen Sie jeden von ihnen einer Spalte mit `column.setRenderer(renderer)` zu.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Product.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductService.java']}
height='600px'
/>
<!-- vale on -->

### Text und Beschriftungen {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  formatierter Text mit optionalem Thema und Dekorationen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Stellt den Zellinhalt als einfachen oder formatierten Text dar. Unterstützt Themenfarben und Textdekorationen wie fett, kursiv und unterstrichen.

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
<strong>BadgeRenderer</strong>  -  Wert wird in einem Abzeichen-Chip angezeigt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Umhüllt den Zellwert in einem Abzeichen-Element. Unterstützt Themen, Erweiterungen, Farbsaat (automatische unterschiedliche Farben pro eindeutigen Wert) und ein optionales führendes Symbol.

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

Zeigt eine konfigurierbare Fallback-Zeichenfolge an, wenn der Zellwert `null` oder leer ist; andernfalls rendert es den Wert unverändert.

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
<strong>BooleanRenderer</strong>  -  true/false/null wird als Symbole angezeigt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Ersetzt `true`, `false` und `null`-Werte durch Symbole. Standardmäßig ein Häkchen, ein Kreuz und ein Bindestrich.

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
<strong>StatusDotRenderer</strong>  -  farbiger Indikatorpunkt neben dem Zelltext
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt einen kleinen farbigen Punkt links vom Zellwert an. Ordnen Sie einzelne Werte Themen, CSS-Farbzeichenfolgen oder `java.awt.Color`-Instanzen zu.

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

Formatiert einen numerischen Wert als Währungsbetrag unter Verwendung der Regeln des angegebenen `Locale`.

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

Zeigt einen numerischen Wert als Prozentsatz an. Setzen Sie das zweite Konstruktionsargument auf `false`, um zu verhindern, dass ein dünner Fortschrittsbalken unter dem Text gerendert wird.

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

zeigt einen vollflächigen Fortschrittsbalken mit konfigurierbaren Mindest- und Höchstgrenzen, unbestimmtem Modus und gestreifter oder animierter Anzeige an. Verwenden Sie `setText()` mit einem lodash-Ausdruck, um benutzerdefinierten Text auf dem Balken zu überlagern.

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

Wendet eine Zeichenmaske auf einen Zeichenfolgewert an. `#` entspricht jeder Ziffer; literale Zeichen werden beibehalten. Siehe [Regeln zur Zeichenmaske](/docs/components/fields/masked/textfield#mask-rules) für alle unterstützten Maskenzeichen.

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

Formatiert einen numerischen Wert mithilfe einer Musterzeichenfolge mit lokalisierungsabhängigen Trennzeichen. `0` zwingt eine Ziffer; `#` ist optional. Siehe [Regeln zur Zahlenmaske](/docs/components/fields/masked/numberfield#mask-rules) für alle unterstützten Maskenzeichen.

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

Formatiert einen Daten- oder Zeitwert mit Mustertokens: `%Mz` (Monat), `%Dz` (Tag), `%Yz` (Jahr) und anderen. Siehe [Regeln zur Datumsmaske](/docs/components/fields/masked/datefield#mask-rules) für alle verfügbaren Tokens.

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

Umhüllt den Zellwert in einem `mailto:`-Anchor. Ein primär thematisiertes Mail-Symbol dient standardmäßig als visuelle Hinweis.

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

Umhüllt den Zellwert in einem `tel:`-Anchor. Auf mobilen Geräten öffnet ein Antippen den Wähler. Ein primär thematisiertes Telefonsymbol wird standardmäßig angezeigt.

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

Rendert ein klickbares Anker-Element. Das `href` unterstützt lodash-Vorlagen-Ausdrücke, sodass Sie URLs dynamisch aus dem Zellwert erstellen können.

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

Zeigt ein Bild an. Das `src`-Attribut unterstützt lodash-Vorlagen-Ausdrücke, sodass jede Zeile ein anderes Bild anzeigen kann.

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

Rendert eine Avatar-Komponente. Die Initialen werden automatisch aus dem Zellwert abgeleitet. Unterstützt Themen und ein Fallback-Symbol.

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
<strong>IconRenderer</strong>  -  eigenständiges Symbol, optional klickbar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rendert ein einzelnes Symbol. Fügen Sie einen Klicklistener hinzu, um interaktive Funktionen zu ermöglichen.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  aktionierbarer Icon-Button mit Zeilenzugriff
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rendert einen klickbaren Icon-Button. Das Klickereignis gibt das Zeilenobjekt über `e.getItem()` frei, was es ideal für zeilenbezogene Aktionen macht.

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

Rendert eine vollständige `Button`-Komponente in der Zelle.

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

Rendert jedes HTML-Element mit einer lodash-Vorlagen-Inhaltzeichenfolge. Dies ist der Ausweg für Situationen, in denen kein integrierter Renderer passt.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Vorlagenreferenz {#template-reference}

Renderer bieten einen leistungsstarken Mechanismus zur Anpassung der Art und Weise, wie Daten innerhalb einer `Tabelle` angezeigt werden. Die Hauptklasse, `Renderer`, wurde entwickelt, um erweitert zu werden, um benutzerdefinierte Renderer basierend auf lodash-Vorlagen zu erstellen, die eine dynamische und interaktive Inhaltserstellung ermöglicht. 

Lodash-Vorlagen ermöglichen das Einfügen von HTML direkt in Tabellenzellen, was sie sehr effektiv für die Darstellung komplexer Zellendaten in einer `Tabelle` macht. Mit diesem Ansatz ist eine dynamische Generierung von HTML basierend auf Zellendaten möglich, was eine reichhaltige und interaktive Tabellenzell-Inhaltserstellung erleichtert.

### Lodash-Syntax {#lodash-syntax}

Der folgende Abschnitt beschreibt die Grundlagen der Lodash-Syntax. Während dies keine umfassende Übersicht ist, kann es Ihnen helfen, Lodash innerhalb der `Tabelle`-Komponente zu verwenden.

#### Syntaxübersicht für lodash-Vorlagen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoliert Werte und fügt das Ergebnis des JavaScript-Codes in die Vorlage ein.
- `<% ... %>` - Führt JavaScript-Code aus und ermöglicht Schleifen, Bedingungsanweisungen und mehr.
- `<%- ... %>` - Entkommt HTML-Inhalt und sorgt dafür, dass interpolierte Daten vor HTML-Injection-Angriffen sicher sind.

#### Beispiele zur Verwendung von Zellendaten: {#examples-using-cell-data}

**1. Einfache Wertinterpolation**: zeigt direkt den Wert der Zelle an.

`<%= cell.value %>`

**2. Bedingte Darstellung**: verwendet JavaScript-Logik, um Inhalte bedingt darzustellen.

`<% if (cell.value > 100) { %> 'Hoch' <% } else { %> 'Normal' <% } %>`

**3. Kombinieren von Datenfeldern**: rendert Inhalte unter Verwendung mehrerer Datenfelder aus der Zelle.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Entkommen von HTML-Inhalten**: sicherer Bezug auf von Benutzern generierte Inhalte.

Der Renderer hat Zugriff auf detaillierte Zell-, Zeilen- und Spaltenattribute auf der Client-Seite:

**Eigenschaften der TableCell:**

|Eigenschaft|Typ|Beschreibung|
|-|-|-|
|column|`TableColumn`|Das zugehörige Spaltenobjekt.|
|first|`boolean`|Gibt an, ob die Zelle die erste in der Zeile ist.|
|id|`String`|Die Zell-ID.|
|index|`int`|Der Index der Zelle innerhalb ihrer Zeile.|
|last|`boolean`|Gibt an, ob die Zelle die letzte in der Zeile ist.|
|row|`TableRow`|Das zugehörige Zeilenobjekt für die Zelle.|
|value|`Object`|Der Rohwert der Zelle, direkt aus der Datenquelle.|

**Eigenschaften der TableRow:**

|Eigenschaft|Typ|Beschreibung|
|-|-|-|
|cells|`TableCell[]`|Die Zellen innerhalb der Zeile.|
|data|`Object`|Die von der App bereitgestellten Daten für die Zeile.|
|even|`boolean`|Gibt an, ob die Zeile gerade nummeriert ist (zum Styling).|
|first|`boolean`|Gibt an, ob die Zeile die erste in der Tabelle ist.|
|id|`String`|Eindeutige ID für die Zeile.|
|index|`int`|Der Zeilenindex.|
|last|`boolean`|Gibt an, ob die Zeile die letzte in der Tabelle ist.|
|odd|`boolean`|Gibt an, ob die Zeile ungerade nummeriert ist (zum Styling).|

**Eigenschaften der TableColumn:**

|Eigenschaft|Typ|Beschreibung|
|-|-|-|
|align|ColumnAlignment|Die Ausrichtung der Spalte (links, zentriert, rechts).|
|id|String|Das Feld des Zeilenobjekts, von dem die Zellendaten abgerufen werden.|
|label|String|Der Name, der in der Spaltenüberschrift angezeigt wird.|
|pinned|ColumnPinDirection|Die Pin-Ausrichtung der Spalte (links, rechts, automatisch).|
|sortable|boolean|Wenn true, kann die Spalte sortiert werden.|
|sort|SortDirection|Die Sortierreihenfolge der Spalte.|
|type|ColumnType|Der Typ der Spalte (Text, Zahl, Boolean usw.).|
|minWidth|number|Die minimale Breite der Spalte in Pixeln.
