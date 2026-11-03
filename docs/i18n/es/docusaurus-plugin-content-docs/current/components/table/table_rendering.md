---
sidebar_position: 20
title: Renderizado
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

Un renderizador controla cómo se muestra cada celda de una columna. En lugar de mostrar un valor sin procesar, transforma los datos de cada celda en texto con estilo, iconos, insignias, enlaces, botones de acción o cualquier otro elemento visual que permita leer los datos con mayor rapidez y actuar sobre ellos con más facilidad.

El renderizado se realiza íntegramente en el navegador. El servidor envía los datos sin procesar y el cliente se encarga de la presentación, lo que hace que `Table` sea rápido sin importar la cantidad de filas.

Asigna un renderizador a una columna con `setRenderer()`. El renderizador se aplica de manera uniforme a todas las celdas de esa columna:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderizadores frente a proveedores de valores
Si solo necesitas transformar o dar formato al valor de una celda, sin generar una estructura DOM, usa un [proveedor de valores](/docs/components/table/columns#value-providers). Los renderizadores crean elementos DOM adicionales para cada fila renderizada, lo que tiene un costo durante el renderizado. Reserva los renderizadores para contenido visual como iconos, insignias, botones o cualquier presentación basada en HTML.
:::

webforJ incluye renderizadores integrados para los casos de uso más habituales. Si necesitas algo específico para tu aplicación, amplía `Renderer` e implementa `build()` para devolver una cadena de plantilla de lodash que se ejecuta en el navegador para cada celda.

## Renderizadores habituales {#common-renderers}

En los siguientes ejemplos se presentan cuatro renderizadores de uso frecuente y se muestra en la práctica el patrón `setRenderer()`.

### TextRenderer {#text-renderer}

Muestra el contenido de una celda como texto sin formato o con estilo. Aplica un color de tema o una decoración de texto a una columna sin cambiar su estructura, por ejemplo, para resaltar en rojo un campo de prioridad o poner en negrita un identificador clave.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Envuelve el valor de la celda en un elemento de insignia. Admite temas, tamaños, asignación automática de colores (colores distintos para cada valor único) y un icono inicial opcional. Úsalo para valores categóricos como etiquetas, tipos o rótulos, donde las insignias visuales diferenciadas ayudan a los usuarios a recorrer y comparar filas rápidamente.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Reemplaza los valores `true`, `false` y `null` por iconos. Úsalo en cualquier columna de valores verdadero/falso donde un icono comunique el valor más rápido que el texto, por ejemplo, en indicadores de funciones, estados activos/inactivos o campos de suscripción voluntaria.

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

Da formato a un valor numérico como importe monetario usando las reglas del `Locale` proporcionado. Úsalo en cualquier columna monetaria donde sea importante que el formato respete la configuración regional (símbolo, separadores y decimales).

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Renderizado condicional {#conditional-rendering}

`ConditionalRenderer` selecciona un renderizador diferente para cada celda según su valor. Las condiciones se evalúan en orden; se aplica la primera que coincide. Puedes establecer una condición alternativa para todos los demás casos con `otherwise()`.


En el siguiente ejemplo se muestra el renderizado condicional de una columna de estado de factura, que alterna entre variantes de `BadgeRenderer` según el valor:


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

También funciona bien con umbrales numéricos. Este panel de servidores usa `ConditionalRenderer` para alternar los temas de `ProgressBarRenderer` según los niveles de uso de la CPU y la memoria:

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

### API de condiciones {#condition-api}

Las condiciones se crean con métodos de fábrica estáticos y se pueden combinar con `and()`, `or()` y `negate()`.

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

## Renderizado compuesto {#composite-rendering}

`CompositeRenderer` combina varios renderizadores en una misma celda, uno al lado del otro, mediante un diseño flex. Úsalo para combinar un icono con texto, mostrar un avatar junto a un nombre o colocar una insignia al lado de un indicador de estado.

El directorio de empleados que aparece a continuación usa `CompositeRenderer` en la columna *Employee* para mostrar un avatar generado automáticamente junto al nombre de cada empleado:

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

## Renderizadores personalizados {#custom-renderers}

Si ningún renderizador integrado se ajusta a tu caso de uso, amplía `Renderer` e implementa `build()`. El método devuelve una cadena de plantilla de lodash que se ejecuta en el navegador para cada celda de la columna y que combina HTML y JavaScript.

### Crear un renderizador personalizado {#creating-a-custom-renderer}

**Paso 1:** Amplía `Renderer` con el tipo de datos de tus filas.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Paso 2:** Sobrescribe `build()` y devuelve una cadena de plantilla de lodash.

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

**Paso 3:** Asigna el renderizador a una columna.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Para obtener más información sobre cómo usar la sintaxis de Lodash para acceder a la información de las celdas y crear renderizadores informativos, consulta [esta sección de referencia](#template-reference).
:::

### Acceder a varias columnas {#accessing-multiple-columns}

Usa `cell.row.getValue("columnId")` para leer columnas relacionadas dentro de la plantilla. Esto resulta útil para combinar campos, calcular diferencias o cruzar datos relacionados.

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

### Eventos de clic {#click-events}

`IconButtonRenderer` y `ButtonRenderer` incluyen el método `addClickListener()`. El evento de clic permite acceder al objeto de datos de la fila mediante `e.getItem()`.

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

## Rendimiento: renderizado diferido <DocChip chip='since' label='25.12' /> {#lazy-rendering}

En las columnas que usan renderizadores visualmente costosos, como insignias, barras de progreso, avatares o componentes web, activa el renderizado diferido para mejorar el rendimiento del desplazamiento.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Cuando se establece `setLazyRender(true)` en una columna, las celdas muestran un marcador de posición animado y ligero mientras el usuario se desplaza. El contenido real de la celda se renderiza cuando se detiene el desplazamiento. Esta configuración se aplica a nivel de columna, por lo que puedes activarla selectivamente solo en las columnas que se beneficien de ella.

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

:::tip Cuándo activar el renderizado diferido
Los renderizadores de celdas crean más entidades en el DOM, lo que implica más trabajo para la CPU durante el renderizado, independientemente de lo que cree el renderizador.

El renderizado diferido puede ayudar a reducir el impacto en el rendimiento si realmente necesitas un renderizador. Si solo necesitas cambiar o dar formato al valor y no estás creando un DOM complejo, usa un proveedor de valores para transformar el valor.
:::

## Referencia de renderizadores integrados {#built-in-renderers}

webforJ incluye un conjunto completo de renderizadores para los casos de uso más habituales. Asigna cualquiera de ellos a una columna con `column.setRenderer(renderer)`.

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

### Texto y etiquetas {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  texto con estilo y tema y decoraciones opcionales
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra el contenido de una celda como texto sin formato o con estilo. Admite colores de tema y decoraciones de texto como negrita, cursiva y subrayado.

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
<strong>BadgeRenderer</strong>  -  valor mostrado dentro de una insignia
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un elemento de insignia. Admite temas, tamaños, asignación automática de colores (colores distintos para cada valor único) y un icono inicial opcional.

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
<strong>NullRenderer</strong>  -  marcador de posición para valores nulos o vacíos
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra una cadena alternativa configurable cuando el valor de la celda es `null` o está vacío; en caso contrario, muestra el valor tal cual.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Estados e indicadores {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  valores true/false/null representados como iconos
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Reemplaza los valores `true`, `false` y `null` por iconos. De forma predeterminada, muestra una marca de verificación, una cruz y un guion.


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
<strong>StatusDotRenderer</strong>  -  punto indicador de color junto al texto de la celda
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra un pequeño punto de color a la izquierda del valor de la celda. Asigna valores individuales a temas, cadenas de color CSS o instancias de `java.awt.Color`.

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

### Números, moneda y fechas {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  formato de moneda según la configuración regional
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Da formato a un valor numérico como importe monetario usando las reglas del `Locale` proporcionado.

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
<strong>PercentageRenderer</strong>  -  porcentaje con barra de progreso pequeña opcional
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra un valor numérico como porcentaje. Establece el segundo argumento del constructor en `false` para evitar que se muestre una barra de progreso fina debajo del texto.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  barra de progreso completa para valores numéricos
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra una barra de progreso de ancho completo con límites mínimo y máximo configurables, modo indeterminado y presentación rayada o animada. Usa `setText()` con una expresión de lodash para superponer texto personalizado en la barra.

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
<strong>MaskedTextRenderer</strong>  -  cadena con formato aplicado mediante una máscara de texto
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Aplica una máscara de caracteres a un valor de cadena. `#` coincide con cualquier dígito; los caracteres literales se conservan. Consulta las [reglas de máscaras de texto](/docs/components/fields/masked/textfield#mask-rules) para ver todos los caracteres de máscara admitidos.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  valor numérico con formato aplicado mediante una máscara numérica
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Da formato a un valor numérico mediante una cadena de patrón con separadores que respetan la configuración regional. `0` obliga a mostrar un dígito; `#` es opcional. Consulta las [reglas de máscaras numéricas](/docs/components/fields/masked/numberfield#mask-rules) para ver todos los caracteres de máscara admitidos.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  valor de fecha/hora con una máscara de fecha
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Da formato a un valor de fecha u hora mediante tokens de patrón: `%Mz` (mes), `%Dz` (día), `%Yz` (año), entre otros. Consulta las [reglas de máscaras de fecha](/docs/components/fields/masked/datefield#mask-rules) para ver todos los tokens disponibles.

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Enlaces y medios {#links-and-media}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  dirección de correo electrónico como enlace mailto en el que se puede hacer clic
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un enlace `mailto:`. De forma predeterminada, un icono de correo con el tema principal sirve como indicador visual.

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
<strong>PhoneRenderer</strong>  -  número de teléfono como enlace tel en el que se puede hacer clic
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un enlace `tel:`. En dispositivos móviles, al tocarlo se abre el marcador. De forma predeterminada, se muestra un icono de teléfono con el tema principal.

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
<strong>AnchorRenderer</strong>  -  valor de celda como hipervínculo configurable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra un elemento de anclaje en el que se puede hacer clic. `href` admite expresiones de plantilla de lodash, por lo que puedes crear URL dinámicamente a partir del valor de la celda.

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
<strong>ImageRenderer</strong>  -  imagen integrada en una celda
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra una imagen. El atributo `src` admite expresiones de plantilla de lodash, por lo que cada fila puede mostrar una imagen diferente.

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

### Personas y avatares {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar con iniciales generadas automáticamente
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra un componente de avatar. Las iniciales se obtienen automáticamente a partir del valor de la celda. Admite temas y un icono alternativo.

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

### Iconos y acciones {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  icono independiente, con opción de interacción
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Muestra un único icono. Añade un detector de clics para habilitar interacciones.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  botón de icono con acciones y acceso a la fila
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra un botón de icono en el que se puede hacer clic. El evento de clic proporciona el elemento de la fila mediante `e.getItem()`, por lo que resulta ideal para acciones por fila.

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
<strong>ButtonRenderer</strong>  -  botón con tema dentro de una celda
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Muestra un componente `Button` completo dentro de la celda.

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
<strong>ElementRenderer</strong>  -  elemento HTML sin procesar con contenido de lodash
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Muestra cualquier elemento HTML con una cadena de contenido de plantilla de lodash. Es la opción flexible para los casos en los que ningún renderizador integrado resulta adecuado.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Referencia de plantillas {#template-reference}

Los renderizadores ofrecen un mecanismo eficaz para personalizar la forma en que se muestran los datos en un `Table`. La clase principal, `Renderer`, está diseñada para ampliarse y crear renderizadores personalizados basados en plantillas de lodash, que permiten generar contenido dinámico e interactivo.

Las plantillas de lodash permiten insertar HTML directamente en las celdas de la tabla, lo que las hace muy eficaces para renderizar datos de celda complejos en un `Table`. Este enfoque permite generar HTML dinámicamente a partir de los datos de las celdas y facilita la creación de contenido de celda enriquecido e interactivo.

### Sintaxis de lodash {#lodash-syntax}

En la siguiente sección se describen los fundamentos de la sintaxis de Lodash. Aunque no es un resumen exhaustivo ni completo, puede servirte para empezar a usar Lodash en el componente `Table`.

#### Resumen de la sintaxis de las plantillas de lodash: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpola valores e inserta en la plantilla el resultado del código JavaScript.
- `<% ... %>` - Ejecuta código JavaScript y permite usar bucles, condicionales y más.
- `<%- ... %>` - Escapa el contenido HTML para proteger los datos interpolados frente a ataques de inyección HTML.

#### Ejemplos con datos de celdas: {#examples-using-cell-data}

**1. Interpolación de valores sencilla**: muestra directamente el valor de la celda.

`<%= cell.value %>`

**2. Renderizado condicional**: usa la lógica de JavaScript para mostrar contenido de forma condicional.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Combinar campos de datos**: renderiza contenido usando varios campos de datos de la celda.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Escapar contenido HTML**: muestra de forma segura el contenido generado por el usuario.

El renderizador tiene acceso a propiedades detalladas de la celda, la fila y la columna en el cliente:

**Propiedades de TableCell:**

|Propiedad	|Tipo	|Descripción|
|-|-|-|
|column|`TableColumn`|El objeto de columna asociado.|
|first|`boolean`|Indica si la celda es la primera de la fila.|
|id|`String`|El ID de la celda.|
|index|`int`|El índice de la celda dentro de su fila.|
|last|`boolean`|Indica si la celda es la última de la fila.|
|row|`TableRow`|El objeto de fila asociado a la celda.|
|value|`Object`|El valor sin procesar de la celda, obtenido directamente del origen de datos.|

**Propiedades de TableRow:**

|Propiedad|Tipo|Descripción|
|-|-|-|
|cells|`TableCell[]`|Las celdas de la fila.
|data|`Object`|Los datos que proporciona la aplicación para la fila.
|even|`boolean`|Indica si la fila tiene un número par (para fines de estilo).
|first|`boolean`|Indica si la fila es la primera de la tabla.
|id|`String`|ID único de la fila.
|index|`int`|El índice de la fila.
|last|`boolean`|Indica si la fila es la última de la tabla.
|odd|`boolean`|Indica si la fila tiene un número impar (para fines de estilo).

**Propiedades de TableColumn:**

|Propiedad	|Tipo	|Descripción|
|-|-|-|
|align|ColumnAlignment|La alineación de la columna (izquierda, centro, derecha).|
|id|String|El campo del objeto de fila del que se obtienen los datos de la celda.|
|label|String|El nombre que se muestra en el encabezado de la columna.|
|pinned|ColumnPinDirection|La dirección de fijación de la columna (izquierda, derecha, automática).|
|sortable|boolean|Si es true, la columna se puede ordenar.|
|sort|SortDirection|El orden de clasificación de la columna.|
|type|ColumnType|El tipo de la columna (texto, número, booleano, etc.).|
|minWidth|number|El ancho mínimo de la columna en píxeles.|
