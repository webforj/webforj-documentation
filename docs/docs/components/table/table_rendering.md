---
sidebar_position: 20
title: Rendering
slug: rendering
sidebar_class_name: new-content
---

<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

A renderer controls how every cell in a column is displayed. Instead of showing a raw value, a renderer transforms each cell's data into styled text, icons, badges, links, action buttons, or any other visual that makes the data faster to read and easier to act on.

Rendering happens entirely in the browser. The server sends raw data; the client handles presentation. This makes the `Table` fast regardless of row count — rich visuals without extra network overhead.

Assign a renderer to a column using `setRenderer()`. The renderer applies uniformly to every cell in that column:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderers vs. value providers
If you only need to transform or format a cell value without producing any DOM structure, use a [value provider](/docs/components/table/columns#value-providers) instead. Renderers create additional DOM elements for every rendered row, which carries a cost at render time. Reserve renderers for visual output — icons, badges, buttons, or any HTML-based presentation.
:::

webforJ ships with built-in renderers for the most common use cases. For anything specific to your application, extend `Renderer` and implement `build()` to return a lodash template string that runs in the browser for each cell.

## Common renderers {#common-renderers}

The following examples walk through four frequently used renderers and demonstrate the `setRenderer()` pattern in practice.

### TextRenderer {#text-renderer}

Displays cell content as plain or styled text. Apply a theme color or text decoration to a column without changing its structure — for example, highlight a priority field in red or make a key identifier bold.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Wraps the cell value in a badge element. Supports themes, expanses, color seeding (automatic distinct colors per unique value), and an optional leading icon. Use it for categorical values such as tags, types, or labels where distinct visual chips help users scan and compare rows quickly.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Replaces `true`, `false`, and `null` values with icons. Use it for any true/false column — feature flags, active/inactive states, or opt-in fields — where an icon communicates the value faster than text.

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

Formats a numeric value as a currency amount using the rules of the supplied `Locale`. Use it for any monetary column where locale-correct formatting (symbol, separators, decimal places) matters.

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Conditional rendering {#conditional-rendering}

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

### Condition API {#condition-api}

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

## Composite rendering {#composite-rendering}

`CompositeRenderer` combines multiple renderers side-by-side in a single cell using a flex layout. Use it to pair an icon with text, show an avatar alongside a name, or stack a badge next to a status indicator.

The employee directory below uses a `CompositeRenderer` on the *Employee* column to display an auto-generated avatar next to each employee's name:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java'
height='600px'
/>
<!-- vale on -->

## Custom renderers {#custom-renderers}

When no built-in renderer fits your use case, extend `Renderer` and implement `build()`. The method returns a lodash template string that runs in the browser for every cell in the column, expressed as a mix of HTML and JavaScript.

### Creating a custom renderer {#creating-a-custom-renderer}

**Step 1:** Extend `Renderer` with your row data type.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Step 2:** Override `build()` and return a lodash template string.

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

**Step 3:** Assign the renderer to a column.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
For more information on how Lodash syntax used to access cell information and create informative renderers, see [this reference section](#template-reference).
:::

### Accessing multiple columns {#accessing-multiple-columns}

Use `cell.row.getValue("columnId")` to read sibling columns inside the template. This is useful for combining fields, computing deltas, or cross-referencing related data.

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

### Click events {#click-events}

`IconButtonRenderer` and `ButtonRenderer` expose `addClickListener()` out of the box. The click event provides access to the row's data object via `e.getItem()`.

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

## Performance: lazy rendering {#lazy-rendering} <DocChip chip='since' label='25.12' />

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
Cell renderers create more entities within the DOM, meaning more CPU work during rendering, no matter what renderer creates it. 

Lazy rendering can help reduce the performance impact if a renderer is truly needed. If you only need to change or format the value, and you are not creating a complex DOM, use a value provider instead to transform the value.
:::

## Built-in renderer reference {#built-in-renderers} 

webforJ ships with a comprehensive set of renderers for the most common use cases. Assign any of them to a column using `column.setRenderer(renderer)`.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Product.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductService.java']}
height='600px'
/>
<!-- vale on -->

### Text and labels {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  styled text with optional theme and decorations
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Displays cell content as plain or styled text. Supports theme colors and text decorations such as bold, italic, and underline.

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
<strong>BadgeRenderer</strong>  -  value displayed inside a badge chip
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wraps the cell value in a badge element. Supports themes, expanses, color seeding (automatic distinct colors per unique value), and an optional leading icon.

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
<strong>NullRenderer</strong>  -  placeholder for null or empty values
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders a configurable fallback string when the cell value is `null` or empty; otherwise renders the value as-is.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Status and indicators {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null shown as icons
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Replaces `true`, `false`, and `null` values with icons. Defaults to a check, cross, and dash.

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
<strong>StatusDotRenderer</strong>  -  colored indicator dot beside cell text
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders a small colored dot to the left of the cell value. Map individual values to themes, CSS color strings, or `java.awt.Color` instances.

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

### Numbers, currency, and dates {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  locale-aware currency formatting
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formats a numeric value as a currency amount using the rules of the supplied `Locale`.

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
<strong>PercentageRenderer</strong>  -  percentage with optional mini progress bar
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Displays a numeric value as a percentage.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  full progress bar for numeric values
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders a full-width progress bar with configurable minimum and maximum bounds, indeterminate mode, and striped or animated display. Use `setText()` with a lodash expression to overlay custom text on the bar.

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
<strong>MaskedTextRenderer</strong>  -  string formatted with a text mask
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Applies a character mask to a string value. `#` matches any digit; literal characters are preserved. See [text mask rules](/docs/components/fields/masked/textfield#mask-rules) for all supported mask characters.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numeric value formatted with a number mask
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formats a numeric value using a pattern string with locale-aware separators. `0` forces a digit; `#` is optional. See [number mask rules](/docs/components/fields/masked/numberfield#mask-rules) for all supported mask characters.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  date/time value with a date mask
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formats a date or time value using pattern tokens: `%Mz` (month), `%Dz` (day), `%Yz` (year), and others. See [date mask rules](/docs/components/fields/masked/datefield#mask-rules) for all available tokens.

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Links and media {#links-and-media}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  email address as a clickable mailto link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wraps the cell value in a `mailto:` anchor. A primary-themed mail icon serves as the visual cue by default.

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
<strong>PhoneRenderer</strong>  -  phone number as a clickable tel link
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Wraps the cell value in a `tel:` anchor. On mobile, tapping opens the dialer. A primary-themed phone icon is shown by default.

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
<strong>AnchorRenderer</strong>  -  cell value as a configurable hyperlink
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders a clickable anchor element. The `href` supports lodash template expressions so you can build URLs dynamically from the cell value.

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
<strong>ImageRenderer</strong>  -  inline image in a cell
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Displays an image. The `src` attribute supports lodash template expressions so each row can show a different image.

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

### People and avatars {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar with auto-generated initials
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders an avatar component. Initials are automatically derived from the cell value. Supports themes and a fallback icon.

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

### Icons and actions {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  standalone icon, optionally clickable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renders a single icon. Attach a click listener for interactive behavior.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  actionable icon button with row access
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renders a clickable icon button. The click event exposes the row item via `e.getItem()`, making it ideal for row-level actions.

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
<strong>ButtonRenderer</strong>  -  themed button in a cell
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renders a full `Button` component inside the cell.

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
<strong>ElementRenderer</strong>  -  raw HTML element with lodash content
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renders any HTML element with a lodash template content string. This is the escape hatch for situations where no built-in renderer fits.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Template reference {#template-reference}

Renderers offer a powerful mechanism for customizing the way data is displayed within a `Table`. The primary class, `Renderer`, is designed to be extended to create custom renderers based on lodash templates, enabling dynamic and interactive content rendering. 

Lodash templates enable the insertion of HTML directly into table cells, making them highly effective for rendering complex cell data in a `Table`. This approach allows for the dynamic generation of HTML based on cell data, facilitating rich and interactive table cell content.

### Lodash syntax {#lodash-syntax}

The following section outlines the basics of Lodash syntax. While this is not an exhaustive or comprehensive overview, it can be used to help start using Lodash within the `Table` component. 

#### Syntax overview for lodash templates: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpolates values, inserting the JavaScript code's result into the template.
- `<% ... %>` - Executes JavaScript code, allowing loops, conditionals, and more.
- `<%- ... %>` - Escapes HTML content, ensuring interpolated data is safe from HTML injection attacks.

#### Examples using cell data: {#examples-using-cell-data}

**1. Simple value interpolation**: directly display the cell's value.

`<%= cell.value %>`

**2. Conditional rendering**: use JavaScript logic to conditionally render content.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Combining data fields**: render content using multiple data fields from the cell.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Escaping HTML content**: safely render user-generated content.

The renderer has access to detailed cell, row, and column properties in the client side:

**TableCell Properties:**

|Property	|Type	|Description|
|-|-|-|
|column|`TableColumn`|The associated column object.|
|first|`boolean`|Indicates if the cell is the first in the row.|
|id|`String`|The cell ID.|
|index|`int`|The cell's index within its row.|
|last|`boolean`|Indicates if the cell is the last in the row.|
|row|`TableRow`|The associated row object for the cell.|
|value|`Object`|The raw value of the cell, directly from the data source.|

**TableRow Properties:**

|Property|Type|Description|
|-|-|-|
|cells|`TableCell[]`|The cells within the row.
|data|`Object`|The data provided by the application for the row.
|even|`boolean`|Indicates if the row is even-numbered (for styling purposes).
|first|`boolean`|Indicates if the row is the first in the table.
|id|`String`|Unique ID for the row.
|index|`int`|The row index.
|last|`boolean`|Indicates if the row is the last in the table.
|odd|`boolean`|Indicates if the row is odd-numbered (for styling purposes).

**TableColumn Properties:**

|Property	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|The alignment of the column (left, center, right).
|id|String|The field of the row object to get the cell's data from.
|label|String|The name to render in the column header.
|pinned|ColumnPinDirection|The pin direction of the column (left, right, auto).
|sortable|boolean|If true, the column can be sorted.
|sort|SortDirection|The sort order of the column.
|type|ColumnType|The type of the column (text, number, boolean, etc.).
|minWidth|number|The minimum width of the column in pixels.
