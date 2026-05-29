---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 8eb5ec6f614d406b57a70fb7472636d5
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Un renderizador controla cómo se muestra cada celda en una columna. En lugar de mostrar un valor en crudo, un renderizador transforma los datos de cada celda en texto estilizado, íconos, insignias, enlaces, botones de acción o cualquier otro visual que haga que los datos sean más rápidos de leer y más fáciles de actuar.

El renderizado ocurre completamente en el navegador. El servidor envía datos en crudo y el cliente maneja la presentación, haciendo que la 'Tabla' sea rápida sin importar la cantidad de filas.

Asigne un renderizador a una columna usando `setRenderer()`. El renderizador se aplica uniformemente a cada celda en esa columna:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderizadores vs. proveedores de valor
Si solo necesita transformar o formatear el valor de una celda sin producir ninguna estructura DOM, use un [proveedor de valor](/docs/components/table/columns#value-providers) en su lugar. Los renderizadores crean elementos DOM adicionales para cada fila renderizada, lo que conlleva un costo en tiempo de renderizado. Reserve los renderizadores para la salida visual como íconos, insignias, botones o cualquier presentación basada en HTML.
:::

webforJ se envía con renderizadores integrados para los casos de uso más comunes. Para cualquier cosa específica de su aplicación, extienda `Renderer` e implemente `build()` para devolver una cadena de plantilla lodash que se ejecute en el navegador para cada celda.

## Renderizadores comunes {#common-renderers}

Los siguientes ejemplos recorren cuatro renderizadores de uso frecuente y demuestran el patrón `setRenderer()` en la práctica.

### TextRenderer {#text-renderer}

Muestra el contenido de la celda como texto en crudo o estilizado. Aplique un color de tema o una decoración de texto a una columna sin cambiar su estructura, como resaltar un campo de prioridad en rojo o hacer que un identificador clave esté en negrita.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Envuelve el valor de la celda en un elemento de insignia. Soporta temas, expansiones, siembra de colores (colores distintos automáticos por valor único) y un ícono opcional al principio. Úselo para valores categóricos como etiquetas, tipos o etiquetas donde pequeños elementos visuales ayudan a los usuarios a escanear y comparar filas rápidamente.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Reemplaza los valores `true`, `false` y `null` con íconos. Úselo para cualquier columna de verdadero/falso donde un ícono comunique el valor más rápido que el texto, como banderas de características, estados activo/inactivo o campos de opción.

```java
// Íconos predeterminados
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Íconos personalizados
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Formatea un valor numérico como una cantidad en moneda utilizando las reglas del `Locale` proporcionado. Úselo para cualquier columna monetaria donde el formato correcto del locale (símbolo, separadores, lugares decimales) sea importante.

```java
// Dólares estadounidenses
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros con locale alemán
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Renderizado condicional {#conditional-rendering}

`ConditionalRenderer` selecciona un renderizador diferente por celda basado en el valor de la celda. Las condiciones se evalúan en orden; la primera coincidencia gana. Se puede establecer un fallback general con `otherwise()`.


El siguiente ejemplo muestra el renderizado condicional aplicado a una columna de estado de factura, cambiando entre variantes de `BadgeRenderer` según el valor:

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

También funciona bien para umbrales numéricos. Este panel de control del servidor utiliza `ConditionalRenderer` para cambiar los temas de `ProgressBarRenderer` según los niveles de uso de CPU y memoria:

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

### API de Condición {#condition-api}

Las condiciones se construyen con métodos de fábrica estáticos y se pueden componer con `and()`, `or()`, y `negate()`.

```java
// Igualdad de valor
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Comparaciones numéricas
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Booleano / vacuidad
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// Coincidencia de cadena
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Composición
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Verificación entre columnas
Condition.column("status").equalTo("active")

// Expresión JavaScript en crudo
Condition.expression("cell.value % 2 === 0")
```

## Renderizado compuesto {#composite-rendering}

`CompositeRenderer` combina múltiples renderizadores uno al lado del otro en una sola celda utilizando un layout de flex. Úselo para acoplar un ícono con texto, mostrar un avatar junto a un nombre, o apilar una insignia junto a un indicador de estado.

El directorio de empleados a continuación utiliza un `CompositeRenderer` en la columna *Empleado* para mostrar un avatar autogenerado junto al nombre de cada empleado:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Renderizadores personalizados {#custom-renderers}

Cuando ningún renderizador integrado se ajusta a su caso de uso, extienda `Renderer` e implemente `build()`. El método devuelve una cadena de plantilla lodash que se ejecuta en el navegador para cada celda en la columna, expresada como una mezcla de HTML y JavaScript.

### Creando un renderizador personalizado {#creating-a-custom-renderer}

**Paso 1:** Extienda `Renderer` con su tipo de datos de fila.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Paso 2:** Sobrescriba `build()` y devuelva una cadena de plantilla lodash.

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

**Paso 3:** Asigne el renderizador a una columna.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Para obtener más información sobre cómo se utiliza la sintaxis de Lodash para acceder a la información de la celda y crear renderizadores informativos, consulte [esta sección de referencia](#template-reference).
:::

### Accediendo a múltiples columnas {#accessing-multiple-columns}

Utilice `cell.row.getValue("columnId")` para leer columnas hermanas dentro de la plantilla. Esto es útil para combinar campos, calcular deltas o hacer referencia cruzada a datos relacionados.

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

`IconButtonRenderer` y `ButtonRenderer` exponen `addClickListener()` por defecto. El evento de clic proporciona acceso al objeto de datos de la fila a través de `e.getItem()`.

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

## Rendimiento: renderizado perezoso <DocChip chip='since' label='25.12' /> {#lazy-rendering} 

Para columnas que usan renderizadores visualmente costosos como insignias, barras de progreso, avatares o componentes web, habilite el renderizado perezoso para mejorar el rendimiento de desplazamiento.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Cuando `setLazyRender(true)` está establecido en una columna, las celdas muestran un marcador de posición animado ligero mientras el usuario se desplaza. El contenido real de la celda se renderiza una vez que se detiene el desplazamiento. Esta es una configuración a nivel de columna, por lo que puede habilitarla selectivamente solo para las columnas que se benefician.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Cuándo habilitar el renderizado perezoso
Los renderizadores de celdas crean más entidades dentro del DOM, lo que significa más trabajo de CPU durante el renderizado, sin importar qué renderizador lo cree. 

El renderizado perezoso puede ayudar a reducir el impacto en el rendimiento si un renderizador es realmente necesario. Si solo necesita cambiar o formatear el valor y no está creando un DOM complejo, use un proveedor de valor en su lugar para transformar el valor.
:::

## Referencia de renderizadores integrados {#built-in-renderers} 

webforJ se envía con un conjunto completo de renderizadores para los casos de uso más comunes. Asigne cualquiera de ellos a una columna usando `column.setRenderer(renderer)`.

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
<strong>TextRenderer</strong>  -  texto estilizado con tema y decoraciones opcionales
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra el contenido de la celda como texto en crudo o estilizado. Soporta colores de tema y decoraciones de texto como negrita, cursiva y subrayado.

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
<strong>BadgeRenderer</strong>  -  valor mostrado dentro de un chip de insignia
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un elemento de insignia. Soporta temas, expansiones, siembra de colores (colores distintos automáticos por valor único) y un ícono opcional al principio.

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

Renderiza una cadena de fallback configurable cuando el valor de la celda es `null` o vacío; de lo contrario, renderiza el valor tal cual.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Estado e indicadores {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null mostrado como íconos
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Reemplaza los valores `true`, `false` y `null` con íconos. Por defecto, es un símbolo de verificación, una cruz y un guion.

```java
// Íconos predeterminados
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Íconos personalizados
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

Renderiza un pequeño punto de color a la izquierda del valor de la celda. Asigne valores individuales a temas, cadenas de color CSS o instancias de `java.awt.Color`.

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
<strong>CurrencyRenderer</strong>  -  formateo de moneda consciente del locale
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatea un valor numérico como una cantidad en moneda utilizando las reglas del `Locale` proporcionado.

```java
// Dólares estadounidenses
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros con locale alemán
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  porcentaje con barra de progreso opcional
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra un valor numérico como un porcentaje. Establezca el segundo argumento del constructor en `false` para evitar renderizar una delgada barra de progreso debajo del texto.

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

Renderiza una barra de progreso de ancho completo con límites mínimos y máximos configurables, modo indeterminado, y visualización de rayas o animada. Use `setText()` con una expresión lodash para superponer texto personalizado en la barra.

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
<strong>MaskedTextRenderer</strong>  -  cadena formateada con una máscara de texto
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Aplica una máscara de caracteres a un valor de cadena. `#` coincide con cualquier dígito; los caracteres literales se conservan. Consulte [reglas de máscara de texto](/docs/components/fields/masked/textfield#mask-rules) para todos los caracteres de máscara admitidos.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  valor numérico formateado con una máscara de número
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formatea un valor numérico utilizando una cadena de patrón con separadores conscientes del locale. `0` obliga a un dígito; `#` es opcional. Consulte [reglas de máscara de número](/docs/components/fields/masked/numberfield#mask-rules) para todos los caracteres de máscara admitidos.

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

Formatea un valor de fecha u hora utilizando tokens de patrón: `%Mz` (mes), `%Dz` (día), `%Yz` (año), y otros. Consulte [reglas de máscara de fecha](/docs/components/fields/masked/datefield#mask-rules) para todos los tokens disponibles.

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
<strong>EmailRenderer</strong>  -  dirección de correo electrónico como un enlace mailto clickeable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un ancla `mailto:`. Un ícono de correo de tema primario sirve como la señal visual por defecto.

```java
// Ícono de correo por defecto
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Ícono personalizado
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  número de teléfono como un enlace tel clickeable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un ancla `tel:`. En dispositivos móviles, al tocar se abre el marcador. Se muestra un ícono de teléfono de tema primario por defecto.

```java
// Ícono de teléfono por defecto
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Ícono personalizado
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  valor de celda como un hipervínculo configurable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderiza un elemento de ancla clickeable. El `href` admite expresiones de plantilla lodash para que pueda construir URLs dinámicamente a partir del valor de la celda.

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
<strong>ImageRenderer</strong>  -  imagen en línea en una celda
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muestra una imagen. El atributo `src` admite expresiones de plantilla lodash para que cada fila pueda mostrar una imagen diferente.

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
<strong>AvatarRenderer</strong>  -  avatar con iniciales autogeneradas
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderiza un componente de avatar. Las iniciales se derivan automáticamente del valor de la celda. Soporte para temas y un ícono de respaldo.

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

### Íconos y acciones {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  ícono independiente, opcionalmente clickeable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderiza un solo ícono. Adjunte un listener de clic para un comportamiento interactivo.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  botón de ícono accionable con acceso a la fila
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderiza un botón de ícono clickeable. El evento de clic expone el elemento de la fila a través de `e.getItem()`, lo que lo hace ideal para acciones a nivel de fila.

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
<strong>ButtonRenderer</strong>  -  botón temático en una celda
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderiza un componente completo `Button` dentro de la celda.

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
<strong>ElementRenderer</strong>  -  elemento HTML en bruto con contenido lodash
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderiza cualquier elemento HTML con una cadena de contenido de plantilla lodash. Esta es la salida de escape para situaciones donde ningún renderizador integrado se ajusta.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Referencia de plantilla {#template-reference}

Los renderizadores ofrecen un mecanismo poderoso para personalizar la forma en que se muestra los datos dentro de una `Tabla`. La clase principal, `Renderer`, está diseñada para ser extendida para crear renderizadores personalizados basados en plantillas lodash, lo que permite la renderización de contenido dinámico e interactivo.

Las plantillas lodash permiten la inserción de HTML directamente en las celdas de la tabla, lo que las hace muy efectivas para renderizar datos complejos de celdas en una `Tabla`. Este enfoque permite la generación dinámica de HTML basada en datos de celdas, facilitando un contenido rico e interactivo en las celdas de la tabla.

### Sintaxis de Lodash {#lodash-syntax}

La siguiente sección describe lo básico de la sintaxis de Lodash. Aunque no es una visión general exhaustiva o completa, puede usarse para ayudar a comenzar a usar Lodash dentro del componente `Tabla`. 

#### Resumen de sintaxis para plantillas lodash: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpela valores, insertando el resultado del código JavaScript en la plantilla.
- `<% ... %>` - Ejecuta código JavaScript, permitiendo bucles, condicionales y más.
- `<%- ... %>` - Escapa contenido HTML, asegurando que los datos interpolados sean seguros contra ataques de inyección HTML.

#### Ejemplos usando datos de la celda: {#examples-using-cell-data}

**1. Interpolación de valor simple**: mostrar directamente el valor de la celda.

`<%= cell.value %>`

**2. Renderizado condicional**: usar lógica de JavaScript para renderizar contenido condicionalmente.

`<% if (cell.value > 100) { %> 'Alto' <% } else { %> 'Normal' <% } %>`

**3. Combinando campos de datos**: renderizar contenido usando múltiples campos de datos de la celda.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Escapando contenido HTML**: renderizar de manera segura contenido generado por el usuario.

El renderizador tiene acceso a propiedades detalladas de la celda, fila y columna en el lado del cliente:

**Propiedades de TableCell:**

|Propiedad	|Tipo	|Descripción|
|-|-|-|
|column|`TableColumn`|El objeto de columna asociado.|
|first|`boolean`|Indica si la celda es la primera en la fila.|
|id|`String`|La ID de la celda.|
|index|`int`|El índice de la celda dentro de su fila.|
|last|`boolean`|Indica si la celda es la última en la fila.|
|row|`TableRow`|El objeto de fila asociado para la celda.|
|value|`Object`|El valor en crudo de la celda, directamente de la fuente de datos.|

**Propiedades de TableRow:**

|Propiedad|Tipo|Descripción|
|-|-|-|
|cells|`TableCell[]`|Las celdas dentro de la fila.
|data|`Object`|Los datos proporcionados por la aplicación para la fila.
|even|`boolean`|Indica si la fila es un número par (para propósitos de estilización).
|first|`boolean`|Indica si la fila es la primera en la tabla.
|id|`String`|ID única para la fila.
|index|`int`|El índice de la fila.
|last|`boolean`|Indica si la fila es la última en la tabla.
|odd|`boolean`|Indica si la fila es un número impar (para propósitos de estilización).

**Propiedades de TableColumn:**

|Propiedad	|Tipo	|Descripción|
|-|-|-|
|align|ColumnAlignment|La alineación de la columna (izquierda, centro, derecha).
|id|String|El campo del objeto de fila para obtener los datos de la celda.
|label|String|El nombre que se renderiza en el encabezado de la columna.
|pinned|ColumnPinDirection|La dirección de fijación de la columna (izquierda, derecha, automática).
|sortable|boolean|Si es verdadero, la columna se puede ordenar.
|sort|SortDirection|El orden de clasificación de la columna.
|type|ColumnType|El tipo de la columna (texto, número, booleano, etc.).
|minWidth|number|El ancho mínimo de la columna en píxeles.
