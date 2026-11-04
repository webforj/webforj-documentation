---
sidebar_position: 20
title: Darstellung
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

Ein Renderer steuert, wie jede Zelle einer Spalte angezeigt wird. Anstatt einen Rohwert anzuzeigen, wandelt ein Renderer die Daten jeder Zelle in formatierte Texte, Symbole, Badges, Links, Aktionsschaltflächen oder andere visuelle Elemente um, die schneller erfassbar und einfacher zu verwenden sind.

Das Rendering findet vollständig im Browser statt. Der Server sendet Rohdaten und der Client übernimmt die Darstellung. Dadurch bleibt die `Table` unabhängig von der Anzahl der Zeilen schnell.

Weisen Sie einer Spalte mit `setRenderer()` einen Renderer zu. Der Renderer wird einheitlich auf jede Zelle dieser Spalte angewendet:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderer im Vergleich zu Wertanbietern
Wenn Sie einen Zellenwert lediglich umwandeln oder formatieren möchten, ohne eine DOM-Struktur zu erzeugen, verwenden Sie stattdessen einen [Wertanbieter](/docs/components/table/columns#value-providers). Renderer erstellen für jede gerenderte Zeile zusätzliche DOM-Elemente, was beim Rendering Aufwand verursacht. Verwenden Sie Renderer daher nur für visuelle Ausgaben wie Symbole, Badges, Schaltflächen oder andere HTML-basierte Darstellungen.
:::

webforJ bietet integrierte Renderer für die gängigsten Anwendungsfälle. Für spezielle Anforderungen Ihrer App können Sie `Renderer` erweitern und `build()` implementieren. Die Methode gibt einen Lodash-Template-String zurück, der im Browser für jede Zelle ausgeführt wird.

## Häufig verwendete Renderer {#common-renderers}

Die folgenden Beispiele stellen vier häufig verwendete Renderer vor und zeigen das Muster mit `setRenderer()` in der Praxis.

### TextRenderer {#text-renderer}

Zeigt Zelleninhalte als einfachen oder formatierten Text an. Wenden Sie eine Designfarbe oder Textdekoration auf eine Spalte an, ohne ihre Struktur zu ändern – beispielsweise, um ein Prioritätsfeld rot hervorzuheben oder eine wichtige Kennung fett darzustellen.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Zeigt den Zellenwert in einem Badge-Element an. Unterstützt Designs, Größen, automatische Farbzuweisung (unterschiedliche Farben für eindeutige Werte) und ein optionales vorangestelltes Symbol. Eignet sich für kategorische Werte wie Tags, Typen oder Beschriftungen, bei denen unterschiedliche visuelle Chips das schnelle Erfassen und Vergleichen von Zeilen erleichtern.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Ersetzt die Werte `true`, `false` und `null` durch Symbole. Verwenden Sie ihn für boolesche Spalten, wenn ein Symbol den Wert schneller vermittelt als Text, etwa bei Funktionskennzeichnungen, Aktiv-/Inaktiv-Zuständen oder Opt-in-Feldern.

```java
// Standardsymbole
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

Formatiert einen Zahlenwert anhand der Regeln des angegebenen `Locale` als Währungsbetrag. Verwenden Sie ihn für Geldbeträge, bei denen eine korrekte lokalisierte Formatierung wichtig ist, beispielsweise bei Symbolen, Trennzeichen und Dezimalstellen.

```java
// US-Dollar
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro mit deutschem Locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Bedingtes Rendering {#conditional-rendering}

`ConditionalRenderer` wählt abhängig vom Zellwert für jede Zelle einen anderen Renderer aus. Die Bedingungen werden der Reihe nach ausgewertet; die erste zutreffende Bedingung wird verwendet. Mit `otherwise()` lässt sich ein allgemeiner Fallback festlegen.


Das folgende Beispiel zeigt bedingtes Rendering für eine Rechnungsspalte mit Statuswerten. Je nach Wert wird eine andere Variante von `BadgeRenderer` verwendet:


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

Das funktioniert auch gut mit numerischen Schwellenwerten. Dieses Server-Dashboard verwendet `ConditionalRenderer`, um je nach CPU- und Speicherauslastung das Design von `ProgressBarRenderer` zu wechseln:

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

Bedingungen werden mit statischen Factory-Methoden erstellt und lassen sich mit `and()`, `or()` und `negate()` kombinieren.

```java
// Wertgleichheit
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numerische Vergleiche
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolesche Werte / Leere
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// Zeichenfolgenvergleich
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Kombinationen
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Prüfung einer anderen Spalte
Condition.column("status").equalTo("active")

// JavaScript-Ausdruck
Condition.expression("cell.value % 2 === 0")
```

## Zusammengesetztes Rendering {#composite-rendering}

`CompositeRenderer` kombiniert mehrere Renderer mithilfe eines Flex-Layouts nebeneinander in einer einzelnen Zelle. Verwenden Sie ihn, um ein Symbol mit Text zu kombinieren, einen Avatar neben einem Namen anzuzeigen oder ein Badge neben einer Statusanzeige zu platzieren.

Das folgende Mitarbeiterverzeichnis verwendet für die Spalte *Employee* einen `CompositeRenderer`, um neben dem Namen jedes Mitarbeiters einen automatisch generierten Avatar anzuzeigen:

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

## Benutzerdefinierte Renderer {#custom-renderers}

Wenn kein integrierter Renderer Ihren Anwendungsfall abdeckt, erweitern Sie `Renderer` und implementieren Sie `build()`. Die Methode gibt einen Lodash-Template-String zurück, der für jede Zelle der Spalte im Browser ausgeführt wird und aus einer Kombination von HTML und JavaScript besteht.

### Benutzerdefinierten Renderer erstellen {#creating-a-custom-renderer}

**Schritt 1:** Erweitern Sie `Renderer` mit Ihrem Zeilendatentyp.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Schritt 2:** Überschreiben Sie `build()` und geben Sie einen Lodash-Template-String zurück.

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
Weitere Informationen zur Lodash-Syntax für den Zugriff auf Zellinformationen und zum Erstellen informativer Renderer finden Sie in [diesem Referenzabschnitt](#template-reference).
:::

### Auf mehrere Spalten zugreifen {#accessing-multiple-columns}

Verwenden Sie `cell.row.getValue("columnId")`, um in der Vorlage auf benachbarte Spalten zuzugreifen. Das ist nützlich, um Felder zu kombinieren, Differenzen zu berechnen oder zusammengehörige Daten miteinander zu vergleichen.

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

`IconButtonRenderer` und `ButtonRenderer` bieten direkt die Methode `addClickListener()`. Über `e.getItem()` kann im Klickereignis auf das Datenobjekt der Zeile zugegriffen werden.

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

## Leistung: verzögertes Rendering <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Aktivieren Sie für Spalten mit aufwendigen Renderern wie Badges, Fortschrittsbalken, Avataren oder Webkomponenten das verzögerte Rendering, um die Scroll-Leistung zu verbessern.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Wenn für eine Spalte `setLazyRender(true)` festgelegt ist, werden Zellen während des Scrollens mit einem leichtgewichtigen animierten Platzhalter angezeigt. Der eigentliche Zelleninhalt wird gerendert, sobald das Scrollen endet. Diese Einstellung gilt auf Spaltenebene. Sie können sie also gezielt nur für die Spalten aktivieren, die davon profitieren.

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

:::tip Wann Sie verzögertes Rendering aktivieren sollten
Zellenrenderer erzeugen zusätzliche Elemente im DOM, wodurch beim Rendering unabhängig vom verwendeten Renderer mehr CPU-Aufwand entsteht.

Verzögertes Rendering kann die Auswirkungen auf die Leistung verringern, wenn ein Renderer tatsächlich benötigt wird. Wenn Sie lediglich den Wert ändern oder formatieren möchten und kein komplexes DOM erstellen, verwenden Sie stattdessen einen Wertanbieter.
:::

## Referenz zu integrierten Renderern {#built-in-renderers}

webforJ bietet eine umfassende Auswahl an Renderern für die gängigsten Anwendungsfälle. Weisen Sie einer Spalte einen Renderer mit `column.setRenderer(renderer)` zu.

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

### Text und Beschriftungen {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  formatierter Text mit optionalem Design und Textdekorationen
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt Zelleninhalte als einfachen oder formatierten Text an. Unterstützt Designfarben und Textdekorationen wie fett, kursiv und unterstrichen.

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
<strong>BadgeRenderer</strong>  -  Wert in einem Badge-Chip angezeigt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt den Zellenwert in einem Badge-Element an. Unterstützt Designs, Größen, automatische Farbzuweisung (unterschiedliche Farben für eindeutige Werte) und ein optionales vorangestelltes Symbol.

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
<strong>NullRenderer</strong>  -  Platzhalter für Null- oder leere Werte
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Gibt eine konfigurierbare Ersatzzeichenfolge aus, wenn der Zellenwert `null` oder leer ist. Andernfalls wird der Wert unverändert angezeigt.

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
<strong>BooleanRenderer</strong>  -  true/false/null als Symbole angezeigt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Ersetzt die Werte `true`, `false` und `null` durch Symbole. Standardmäßig werden ein Häkchen, ein Kreuz und ein Strich verwendet.


```java
// Standardsymbole
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
<strong>StatusDotRenderer</strong>  -  farbiger Punkt neben dem Zelltext
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt links neben dem Zellenwert einen kleinen farbigen Punkt an. Ordnen Sie einzelnen Werten Designs, CSS-Farbzeichenfolgen oder `java.awt.Color`-Instanzen zu.

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

### Zahlen, Währungen und Datumsangaben {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  währungsformatierte Werte gemäß Locale
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen Zahlenwert anhand der Regeln des angegebenen `Locale` als Währungsbetrag.

```java
// US-Dollar
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euro mit deutschem Locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  Prozentwert mit optionalem Mini-Fortschrittsbalken
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt einen Zahlenwert als Prozentwert an. Setzen Sie das zweite Konstruktorargument auf `false`, um den schmalen Fortschrittsbalken unter dem Text zu deaktivieren.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  vollständiger Fortschrittsbalken für Zahlenwerte
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt einen über die gesamte Breite reichenden Fortschrittsbalken mit konfigurierbaren Minimal- und Maximalwerten, unbestimmtem Modus sowie gestreifter oder animierter Darstellung an. Verwenden Sie `setText()` mit einem Lodash-Ausdruck, um benutzerdefinierten Text über dem Balken anzuzeigen.

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
<strong>MaskedTextRenderer</strong>  -  Zeichenfolge mit Textmaske formatiert
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wendet eine Zeichenmaske auf einen Zeichenfolgenwert an. `#` steht für eine beliebige Ziffer; wörtliche Zeichen bleiben erhalten. Unter [Regeln für Textmasken](/docs/components/fields/masked/textfield#mask-rules) finden Sie alle unterstützten Maskenzeichen.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  Zahlenwert mit Zahlenmaske formatiert
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen Zahlenwert mithilfe einer Mustervorlage mit Locale-gerechten Trennzeichen. `0` erzwingt eine Ziffer; `#` ist optional. Unter [Regeln für Zahlenmasken](/docs/components/fields/masked/numberfield#mask-rules) finden Sie alle unterstützten Maskenzeichen.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  Datums-/Uhrzeitwert mit Datumsmaske
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatiert einen Datums- oder Uhrzeitwert mithilfe von Mustertokens: `%Mz` (Monat), `%Dz` (Tag), `%Yz` (Jahr) und weitere. Unter [Regeln für Datumsformate](/docs/components/fields/masked/datefield#mask-rules) finden Sie alle verfügbaren Tokens.

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
<strong>EmailRenderer</strong>  -  E-Mail-Adresse als anklickbarer mailto-Link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Umschließt den Zellenwert mit einem `mailto:`-Ankerelement. Standardmäßig dient ein E-Mail-Symbol im primären Design als visueller Hinweis.

```java
// Standardsymbol für E-Mail
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
<strong>PhoneRenderer</strong>  -  Telefonnummer als anklickbarer tel-Link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Umschließt den Zellenwert mit einem `tel:`-Ankerelement. Auf Mobilgeräten wird durch Antippen die Wählfunktion geöffnet. Standardmäßig wird ein Telefonsymbol im primären Design angezeigt.

```java
// Standardsymbol für Telefon
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
<strong>AnchorRenderer</strong>  -  Zellenwert als konfigurierbarer Hyperlink
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt ein anklickbares Ankerelement an. `href` unterstützt Lodash-Template-Ausdrücke, mit denen sich URLs dynamisch aus dem Zellenwert erstellen lassen.

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
<strong>ImageRenderer</strong>  -  eingebettetes Bild in einer Zelle
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt ein Bild an. Das Attribut `src` unterstützt Lodash-Template-Ausdrücke, sodass für jede Zeile ein anderes Bild angezeigt werden kann.

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

Zeigt eine Avatar-Komponente an. Die Initialen werden automatisch aus dem Zellenwert abgeleitet. Unterstützt Designs und ein Fallback-Symbol.

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
<strong>IconRenderer</strong>  -  einzelnes Symbol, optional anklickbar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Zeigt ein einzelnes Symbol an. Für interaktive Funktionen können Sie einen Klick-Listener hinzufügen.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  Aktionsschaltfläche mit Symbol und Zugriff auf die Zeile
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Zeigt eine anklickbare Symbolschaltfläche an. Über `e.getItem()` ist das Zeilenelement im Klickereignis verfügbar. Dadurch eignet sich der Renderer besonders für Aktionen auf Zeilenebene.

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
<strong>ButtonRenderer</strong>  -  Schaltfläche mit Design in einer Zelle
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Zeigt eine vollständige `Button`-Komponente in der Zelle an.

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
<strong>ElementRenderer</strong>  -  rohes HTML-Element mit Lodash-Inhalt
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Zeigt ein beliebiges HTML-Element mit einer Lodash-Template-Zeichenfolge als Inhalt an. Diese Option eignet sich für Fälle, in denen kein integrierter Renderer passt.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Vorlagenreferenz {#template-reference}

Renderer bieten eine leistungsstarke Möglichkeit, die Darstellung von Daten in einer `Table` anzupassen. Die zentrale Klasse `Renderer` ist dafür vorgesehen, erweitert zu werden. So lassen sich auf Lodash-Vorlagen basierende benutzerdefinierte Renderer erstellen, die dynamische und interaktive Inhalte darstellen.

Mit Lodash-Vorlagen lässt sich HTML direkt in Tabellenzellen einfügen. Dadurch eignen sie sich besonders gut, um komplexe Zelldaten in einer `Table` darzustellen. Mit diesem Ansatz kann HTML dynamisch anhand der Zelldaten generiert werden, was eine reichhaltige und interaktive Darstellung von Tabellenzellen ermöglicht.

### Lodash-Syntax {#lodash-syntax}

Der folgende Abschnitt erläutert die Grundlagen der Lodash-Syntax. Er ist keine vollständige Übersicht, bietet aber einen Einstieg in die Verwendung von Lodash innerhalb der `Table`-Komponente.

#### Syntaxübersicht für Lodash-Vorlagen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` – Interpoliert Werte und fügt das Ergebnis des JavaScript-Codes in die Vorlage ein.
- `<% ... %>` – Führt JavaScript-Code aus und ermöglicht Schleifen, Bedingungen und mehr.
- `<%- ... %>` – Maskiert HTML-Inhalte, damit interpolierte Daten vor HTML-Injection-Angriffen geschützt sind.

#### Beispiele für die Verwendung von Zelldaten: {#examples-using-cell-data}

**1. Einfache Wertinterpolation**: Zeigt den Zellwert direkt an.

`<%= cell.value %>`

**2. Bedingtes Rendering**: Verwendet JavaScript-Logik, um Inhalte bedingt darzustellen.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Datenfelder kombinieren**: Stellt Inhalte anhand mehrerer Datenfelder der Zelle dar.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-Inhalte maskieren**: Stellt von Benutzern erstellte Inhalte sicher dar.

Dem Renderer stehen clientseitig detaillierte Eigenschaften von Zelle, Zeile und Spalte zur Verfügung:

**TableCell-Eigenschaften:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|column|`TableColumn`|Das zugehörige Spaltenobjekt.|
|first|`boolean`|Gibt an, ob die Zelle die erste in der Zeile ist.|
|id|`String`|Die ID der Zelle.|
|index|`int`|Der Index der Zelle innerhalb ihrer Zeile.|
|last|`boolean`|Gibt an, ob die Zelle die letzte in der Zeile ist.|
|row|`TableRow`|Das zugehörige Zeilenobjekt der Zelle.|
|value|`Object`|Der Rohwert der Zelle, direkt aus der Datenquelle.|

**TableRow-Eigenschaften:**

|Eigenschaft|Typ|Beschreibung|
|-|-|-|
|cells|`TableCell[]`|Die Zellen in der Zeile.|
|data|`Object`|Die von der App bereitgestellten Daten für die Zeile.|
|even|`boolean`|Gibt an, ob die Zeile eine gerade Zeilennummer hat (für die Gestaltung).|
|first|`boolean`|Gibt an, ob die Zeile die erste in der Tabelle ist.|
|id|`String`|Eindeutige ID der Zeile.|
|index|`int`|Der Zeilenindex.|
|last|`boolean`|Gibt an, ob die Zeile die letzte in der Tabelle ist.|
|odd|`boolean`|Gibt an, ob die Zeile eine ungerade Zeilennummer hat (für die Gestaltung).|

**TableColumn-Eigenschaften:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|align|ColumnAlignment|Die Ausrichtung der Spalte (links, zentriert, rechts).|
|id|String|Das Feld des Zeilenobjekts, aus dem die Zellendaten stammen.|
|label|String|Der Name, der in der Spaltenüberschrift angezeigt wird.|
|pinned|ColumnPinDirection|Die Fixierungsrichtung der Spalte (links, rechts, automatisch).|
|sortable|boolean|Gibt an, ob die Spalte sortiert werden kann.|
|sort|SortDirection|Die Sortierreihenfolge der Spalte.|
|type|ColumnType|Der Spaltentyp (Text, Zahl, boolescher Wert usw.).|
|minWidth|number|Die Mindestbreite der Spalte in Pixeln.|
