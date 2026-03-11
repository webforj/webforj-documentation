---
sidebar_position: 20
title: Rendering
slug: rendering
---

A renderer controls how every cell in a column is displayed. Instead of showing a raw value, a renderer transforms each cell's data into styled text, icons, badges, links, action buttons, or any other visual that makes the data faster to read and easier to act on.

Renderers are assigned to a column and apply uniformly to every cell in that column:

```java
TextRenderer renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

Rendering happens entirely in the browser. The server sends raw data; the client handles presentation using JavaScript only when needed. This keeps the `Table` fast regardless of row count.

## Built-in renderers {#built-in-renderers}

webforJ ships with a comprehensive set of renderers for the most common use cases. Assign any of them to a column using `column.setRenderer(renderer)`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/productcatalog?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table//renderers/ProductCatalogView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Product.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductService.java']}
height='600px'
/>
<!-- vale on -->

### Text and labels {#text-and-labels}


<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  styled text with optional theme and decorations
</AccordionSummary>
<AccordionDetails>
<div>

Displays cell content as plain or styled text. Supports theme colors and text decorations such as bold, italic, and underline.

```java
TextRenderer renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```



**When to use:** Use when you need to apply a theme color or text decoration to a column without changing its structure. For example, highlighting a priority field in red or making a key identifier bold.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BadgeRenderer</strong>  -  value displayed inside a badge chip
</AccordionSummary>
<AccordionDetails>
<div>

Wraps the cell value in a badge element. Supports themes, expanses, color seeding (automatic distinct colors per unique value), and an optional leading icon.

```java
BadgeRenderer renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```



**When to use:** Use for categorical values such as tags, types, or labels where distinct visual chips help users scan and compare rows quickly.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>NullRenderer</strong>  -  placeholder for null or empty values
</AccordionSummary>
<AccordionDetails>
<div>

Renders a configurable fallback string when the cell value is `null` or empty; otherwise renders the value as-is.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```



**When to use:** Use on any optional column where blank cells would otherwise be confusing. A placeholder like N/A makes it clear the absence of a value is intentional.

</div>
</AccordionDetails>
</Accordion>

### Status and indicators {#status-and-indicators}

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null shown as icons
</AccordionSummary>
<AccordionDetails>
<div>

Replaces `true`, `false`, and `null` values with icons. Defaults to a check, cross, and dash. Fully customizable with any `TablerIcon`.

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



**When to use:** Use for any true/false column such as feature flags, active/inactive states, or opt-in fields where an icon communicates the value faster than text.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>StatusDotRenderer</strong>  -  colored indicator dot beside cell text
</AccordionSummary>
<AccordionDetails>
<div>

Renders a small colored dot to the left of the cell value. Map individual values to themes, CSS color strings, or `java.awt.Color` instances.

```java
StatusDotRenderer renderer = new StatusDotRenderer<>();
renderer.addMapping("Active",    Theme.SUCCESS);
renderer.addMapping("Pending",   Theme.WARNING);
renderer.addMapping("Cancelled", Theme.DANGER);

table.addColumn("status", Order::getStatus).setRenderer(renderer);
```



**When to use:** Use for named status fields with a small fixed set of values, such as order states, health indicators, or priority levels.

</div>
</AccordionDetails>
</Accordion>

### Numbers, currency, and dates {#numbers-currency-and-dates}

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  locale-aware currency formatting
</AccordionSummary>
<AccordionDetails>
<div>

Formats a numeric value as a currency amount using the rules of the supplied `Locale`.

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```



**When to use:** Use for any monetary column where locale-correct formatting (symbol, separators, decimal places) matters.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  percentage with optional mini progress bar
</AccordionSummary>
<AccordionDetails>
<div>

Displays a numeric value as a percentage. Set the second constructor argument to `true` to render a thin progress bar beneath the text.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```



**When to use:** Use for ratio or completion values between 0 and 100. Enable the progress bar variant when a visual indicator helps more than the number alone.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  full progress bar for numeric values
</AccordionSummary>
<AccordionDetails>
<div>

Renders a full-width progress bar. Supports minimum and maximum bounds, indeterminate mode, striped and animated variants, and inline text using the `{{x}}` (percentage) and `{{value}}` placeholders.

```java
ProgressBarRenderer renderer = new ProgressBarRenderer<>();
renderer.setMax(100);
renderer.setTextVisible(true);
renderer.setTheme(Theme.SUCCESS);

table.addColumn("progress", Task::getProgress).setRenderer(renderer);
```



**When to use:** Use for numeric progress, capacity, or load values where the relative magnitude is as important as the exact number.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedTextRenderer</strong>  -  string formatted with a text mask
</AccordionSummary>
<AccordionDetails>
<div>

Applies a character mask to a string value. `#` matches any digit; literal characters are preserved.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```



**When to use:** Use for structured strings that are meaningless without formatting, such as phone numbers, SSNs, serial codes, or postal codes.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numeric value formatted with a number mask
</AccordionSummary>
<AccordionDetails>
<div>

Formats a numeric value using a pattern string with locale-aware separators. `0` forces a digit; `#` is optional.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```



**When to use:** Use when you need precise control over how a number is displayed. Fixed decimal places, thousands separators, or locale-specific formatting beyond what `CurrencyRenderer` provides.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  date/time value with a date mask
</AccordionSummary>
<AccordionDetails>
<div>

Formats a date or time value using pattern tokens: `%Mz` (month), `%Dz` (day), `%Yz` (year), and others.

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```



**When to use:** Use when dates or times are stored as raw values and need to be presented in a specific regional or organizational format.

</div>
</AccordionDetails>
</Accordion>

### Links and media {#links-and-media}

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  email address as a clickable mailto link
</AccordionSummary>
<AccordionDetails>
<div>

Wraps the cell value in a `mailto:` anchor. A primary-themed mail icon serves as the visual cue by default.

```java
// Default mail icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Custom icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```



**When to use:** Use for any email address column where one-click compose is useful. Prefer this over `AnchorRenderer` for email specifically, as it adds the mail icon automatically.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  phone number as a clickable tel link
</AccordionSummary>
<AccordionDetails>
<div>

Wraps the cell value in a `tel:` anchor. On mobile, tapping opens the dialer. A primary-themed phone icon is shown by default.

```java
// Default phone icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Custom icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```



**When to use:** Use for phone number columns, especially in contact or CRM tables where mobile users benefit from tap-to-call.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  cell value as a configurable hyperlink
</AccordionSummary>
<AccordionDetails>
<div>

Renders a clickable anchor element. The `href` supports lodash template expressions so you can build URLs dynamically from the cell value.

```java
AnchorRenderer renderer = new AnchorRenderer<>();
renderer.setHref("https://www.google.com/search?q=<%= cell.value %>");
renderer.setTarget("_blank");

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```



**When to use:** Use when cell values should link out to external URLs, or when you need to construct a URL dynamically from the cell value using a template expression.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ImageRenderer</strong>  -  inline image in a cell
</AccordionSummary>
<AccordionDetails>
<div>

Displays an image. The `src` attribute supports lodash template expressions so each row can show a different image.

```java
ImageRenderer renderer = new ImageRenderer<>();
renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
renderer.setAlt("Cover");

table.addColumn("cover", MusicRecord::getArtist).setRenderer(renderer);
```



**When to use:** Use for thumbnail columns such as product images or avatars from a CDN where a visual preview is more useful than text.

</div>
</AccordionDetails>
</Accordion>

### People and avatars {#people-and-avatars}

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar with auto-generated initials
</AccordionSummary>
<AccordionDetails>
<div>

Renders an avatar component. Initials are automatically derived from the cell value. Supports themes and a fallback icon.

```java
AvatarRenderer renderer = new AvatarRenderer<>();
renderer.setTheme(AvatarTheme.PRIMARY);
renderer.setIcon(TablerIcon.create("user"));

table.addColumn("artist", MusicRecord::getArtist).setRenderer(renderer);
```



**When to use:** Use for name or user columns where a visual identity marker helps, especially in people directories, assignee columns, or chat-style tables.

</div>
</AccordionDetails>
</Accordion>

### Icons and actions {#icons-and-actions}

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  standalone icon, optionally clickable
</AccordionSummary>
<AccordionDetails>
<div>

Renders a single icon. Attach a click listener for interactive behavior.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```



**When to use:** Use for type or category columns where an icon alone communicates the meaning, for example, a file-type column or a severity indicator.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  actionable icon button with row access
</AccordionSummary>
<AccordionDetails>
<div>

Renders a clickable icon button. The click event exposes the row item via `e.getItem()`, making it ideal for row-level actions.

```java
IconButtonRenderer renderer = new IconButtonRenderer<>(TablerIcon.create("edit"));
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("actions", r -> "").setRenderer(renderer);
```



**When to use:** Use for row action columns (edit, delete, view detail) where a compact icon button is preferable to a full labeled button.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ButtonRenderer</strong>  -  themed button in a cell
</AccordionSummary>
<AccordionDetails>
<div>

Renders a full `Button` component inside the cell.

```java
ButtonRenderer renderer = new ButtonRenderer<>("Edit");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "Edit").setRenderer(renderer);
```



**When to use:** Use when the action needs a visible label, or when the column is part of a form-like table where button affordance matters more than compactness.

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ElementRenderer</strong>  -  raw HTML element with lodash content
</AccordionSummary>
<AccordionDetails>
<div>

Renders any HTML element with a lodash template content string. This is the escape hatch for situations where no built-in renderer fits.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```



**When to use:** Use as a last resort when no built-in renderer covers your case. Prefer a custom `Renderer` subclass for anything non-trivial.

</div>
</AccordionDetails>
</Accordion>


---

## Advanced rendering patterns {#advanced-rendering-patterns}

### Conditional rendering {#conditional-rendering}

`ConditionalRenderer` selects a different renderer per cell based on the cell's value. Conditions are evaluated in order; the first match wins. A catch-all fallback can be set with `otherwise()`.


The following example shows conditional rendering applied to an invoice status column, switching between `BadgeRenderer` variants based on the value:


<!-- vale off -->
<ComponentDemo 
path='/webforj/invoicelist' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java']}
height='600px'
/>
<!-- vale on -->

It also works well for numeric thresholds. This server dashboard uses `ConditionalRenderer` to switch `ProgressBarRenderer` themes based on CPU and memory usage levels:

<!-- vale off -->
<ComponentDemo 
path='/webforj/serverdashboard?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Server.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerService.java']}
height='600px'
/>
<!-- vale on -->

#### Condition API {#condition-api}

Conditions are built with static factory methods and can be composed with `and()`, `or()`, and `negate()`.

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

### Composite rendering {#composite-rendering}

`CompositeRenderer` combines multiple renderers side-by-side in a single cell using a flex layout. Use it to pair an icon with text, show an avatar alongside a name, or stack a badge next to a status indicator.

The employee directory below uses a `CompositeRenderer` on the name column to display an auto-generated avatar next to each employee's name:

<!-- vale off -->
<ComponentDemo 
path='/webforj/employeedirectory?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java'
height='600px'
/>
<!-- vale on -->

```java
CompositeRenderer renderer = new CompositeRenderer<>(
    new IconRenderer<>(TablerIcon.create("music")),
    new TextRenderer<>()
);
renderer.setSpacing("8px");

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```
---

## Custom renderers {#custom-renderers}

When no built-in renderer fits your use case, extend `Renderer` and implement `build()`. The method returns a lodash template string that runs in the browser for every cell in the column, expressed as a mix of HTML and JavaScript.

### Lodash template syntax {#lodash-template-syntax}

| Syntax | Purpose |
|--------|---------|
| `<%= expr %>` | Interpolate: inserts the expression's value into the output |
| `<% code %>` | Execute: runs arbitrary JavaScript (loops, conditionals, variable declarations) |
| `<%- expr %>` | Escape: like `<%= %>` but HTML-encodes the result to prevent injection attacks |

Inside a template, three objects are always available:

| Object | Description |
|--------|-------------|
| `cell` | The current cell, including its `value` |
| `cell.row` | The parent row; call `cell.row.getValue("columnId")` to read sibling columns |
| `cell.column` | The parent column, including its `id` and `label` |

### Creating a custom renderer {#creating-a-custom-renderer}

**Step 1:** Extend `Renderer` with your row data type.

```java
public class RatingRenderer extends Renderer {
```

**Step 2:** Override `build()` and return a lodash template string.

```java
    @Override
    public String build() {
        return /* html */"""
            <%
              const rating = cell.value;
              const stars  = Math.round(Math.min(Math.max(rating, 0), 5));
              const full   = '★'.repeat(stars);
              const empty  = '☆'.repeat(5 - stars);
            %>
            
              <%= full %><%= empty %>
            
            
              (<%= rating.toFixed(1) %>)
            
        """;
    }
}
```

**Step 3:** Assign the renderer to a column.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

### Accessing multiple columns {#accessing-multiple-columns}

Use `cell.row.getValue("columnId")` to read sibling columns inside the template. This is useful for combining fields, computing deltas, or cross-referencing related data.

```java
public class ArtistAvatarRenderer extends Renderer {
    @Override
    public String build() {
        return /* html */"""
            <%
              const name     = cell.row.getValue("artist");
              const initials = name
                  ? name.split(' ').map(w => w.charAt(0)).join('').substring(0, 2).toUpperCase()
                  : '?';
            %>
            
              
                <%= initials %>
              
              <%= name %>
            
        """;
    }
}
```

### Click events {#click-events}

`IconButtonRenderer` and `ButtonRenderer` expose `addClickListener()` out of the box. The click event provides access to the row's data object via `e.getItem()`.

```java
IconButtonRenderer deleteBtn = new IconButtonRenderer<>(
    TablerIcon.create("trash").setTheme(Theme.DANGER)
);
deleteBtn.addClickListener(e -> {
    MusicRecord record = e.getItem();
    repository.delete(record);
    table.refresh();
});

table.addColumn("delete", r -> "").setRenderer(deleteBtn);
```

## Performance: lazy rendering {#lazy-rendering}

<DocChip chip='since' label='25.12' />

For columns that use visually expensive renderers such as badges, progress bars, avatars, or web components, enable lazy rendering to improve scroll performance.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

When `setLazyRender(true)` is set on a column, cells display a lightweight animated placeholder while the user is scrolling. The actual cell content renders once scrolling stops. This is a column-level setting, so you can enable it selectively for only the columns that benefit.

<!-- vale off -->
<ComponentDemo 
path='/webforj/lazyrender?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java'
height='600px'
/>
<!-- vale on -->

:::tip When to Enable Lazy Rendering
Enable lazy rendering on columns whose renderer instantiates a web component, such as `BadgeRenderer`, `ProgressBarRenderer`, `AvatarRenderer`, or `IconButtonRenderer`. Leave it off for simpler renderers like `TextRenderer` or `CurrencyRenderer` where the render cost is negligible.
:::

## Cell and row context reference {#cell-and-row-context-reference}

The following properties are available inside every lodash template.

### `cell` properties {#cell-properties}

| Property | Type | Description |
|----------|------|-------------|
| `value` | `Object` | The raw cell value from the data source |
| `index` | `int` | The cell's position within its row |
| `first` | `boolean` | `true` if this is the first cell in the row |
| `last` | `boolean` | `true` if this is the last cell in the row |
| `id` | `String` | The cell's unique ID |
| `row` | `TableRow` | The parent row object |
| `column` | `TableColumn` | The parent column object |

### `cell.row` properties {#cellrow-properties}

| Property | Type | Description |
|----------|------|-------------|
| `getValue(id)` | `Function` | Returns the value of another column in this row |
| `index` | `int` | Row position in the table |
| `first` | `boolean` | `true` for the first row |
| `last` | `boolean` | `true` for the last row |
| `even` / `odd` | `boolean` | Row parity, useful for alternating styles |
| `id` | `String` | Unique row ID |
| `data` | `Object` | The full row data object provided by the application |

### `cell.column` properties {#cellcolumn-properties}

| Property | Type | Description |
|----------|------|-------------|
| `id` | `String` | Column identifier (matches the field name) |
| `label` | `String` | The column's header text |
| `align` | `ColumnAlignment` | `left`, `center`, or `right` |
| `sortable` | `boolean` | Whether the column can be sorted |
| `sort` | `SortDirection` | Current sort direction |
| `minWidth` | `number` | Minimum column width in pixels |