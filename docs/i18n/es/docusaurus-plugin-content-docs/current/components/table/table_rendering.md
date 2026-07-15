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

Un renderizador controla cómo se muestra cada celda en una columna. En lugar de mostrar un valor en bruto, un renderizador transforma los datos de cada celda en texto estilizado, íconos, insignias, enlaces, botones de acción o cualquier otro elemento visual que haga que los datos sean más fáciles de leer y de actuar.

El renderizado ocurre completamente en el navegador. El servidor envía datos en bruto y el cliente maneja la presentación, haciendo que la 'Tabla' sea rápida sin importar la cantidad de filas.

Asigna un renderizador a una columna usando `setRenderer()`. El renderizador se aplica de manera uniforme a cada celda de esa columna:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderizadores vs. proveedores de valores
Si solo necesitas transformar o formatear un valor de celda sin producir ninguna estructura DOM, utiliza un [proveedor de valores](/docs/components/table/columns#value-providers) en su lugar. Los renderizadores crean elementos DOM adicionales para cada fila renderizada, lo que implica un costo en el tiempo de renderizado. Reserva los renderizadores para salidas visuales como íconos, insignias, botones o cualquier presentación basada en HTML.
:::

webforJ incluye renderizadores integrados para los casos de uso más comunes. Para cualquier cosa específica de tu aplicación, extiende `Renderer` e implementa `build()` para devolver una cadena de plantilla de lodash que se ejecute en el navegador para cada celda.

## Renderizadores comunes {#common-renderers}

Los siguientes ejemplos repasan cuatro renderizadores utilizados con frecuencia y demuestran el patrón `setRenderer()` en la práctica.

### TextRenderer {#text-renderer}

Muestra el contenido de la celda como texto plano o estilizado. Aplica un color de tema o decoración de texto a una columna sin cambiar su estructura, como resaltar un campo de prioridad en rojo o hacer que un identificador clave sea negrita.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Envuelve el valor de la celda en un elemento de insignia. Soporta temas, expansiones, coloreado automático (colores distintos automáticos por valor único) y un ícono opcional al principio. Úsalo para valores categóricos como etiquetas, tipos o etiquetas donde chips visuales distintos ayudan a los usuarios a escanear y comparar filas rápidamente.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Reemplaza los valores `true`, `false` y `null` con íconos. Úsalo para cualquier columna de verdadero/falso donde un ícono comunique el valor más rápidamente que el texto, como indicadores de características, estados activos/inactivos o campos de opt-in.

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

Formatea un valor numérico como una cantidad monetaria utilizando las reglas del `Locale` suministrado. Úsalo para cualquier columna monetaria donde el formateo correcto del locale (símbolo, separadores, lugares decimales) sea importante.

```java
// Dólares estadounidenses
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros con locale alemán
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Renderizado condicional {#conditional-rendering}

`ConditionalRenderer` selecciona un renderizador diferente por celda basado en el valor de la celda. Las condiciones se evalúan en orden; la primera coincidencia gana. Se puede establecer un fondo de captura general con `otherwise()`.

El siguiente ejemplo muestra el renderizado condicional aplicado a una columna de estado de factura, alternando entre variantes de `BadgeRenderer` según el valor:

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

También funciona bien para umbrales numéricos. Este panel del servidor utiliza `ConditionalRenderer` para cambiar los temas de `ProgressBarRenderer` según los niveles de uso de CPU y memoria:

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

### API de condición {#condition-api}

Las condiciones se construyen con métodos de fábrica estáticos y pueden combinarse con `and()`, `or()` y `negate()`.

```java
// Igualdad de valor
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Comparaciones numéricas
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Booleano / vacío
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

// Expresión de JavaScript cruda
Condition.expression("cell.value % 2 === 0")
```

## Renderizado compuesto {#composite-rendering}

`CompositeRenderer` combina múltiples renderizadores uno al lado del otro en una única celda utilizando un diseño flexible. Úsalo para emparejar un ícono con texto, mostrar un avatar junto a un nombre o apilar una insignia junto a un indicador de estado.

El directorio de empleados a continuación utiliza un `CompositeRenderer` en la columna *Employee* para mostrar un avatar autogenerado junto al nombre de cada empleado:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Renderizadores personalizados {#custom-renderers}

Cuando ningún renderizador integrado se adapta a tu caso de uso, extiende `Renderer` e implementa `build()`. El método devuelve una cadena de plantilla de lodash que se ejecuta en el navegador para cada celda en la columna, expresada como una mezcla de HTML y JavaScript.

### Creando un renderizador personalizado {#creating-a-custom-renderer}

**Paso 1:** Extiende `Renderer` con tu tipo de dato de fila.

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
Para más información sobre cómo se utiliza la sintaxis de Lodash para acceder a la información de la celda y crear renderizadores informativos, consulta [esta sección de referencia](#template-reference).
:::

### Accediendo a múltiples columnas {#accessing-multiple-columns}

Utiliza `cell.row.getValue("columnId")` para leer columnas hermanas dentro de la plantilla. Esto es útil para combinar campos, calcular diferencias o hacer referencia a datos relacionados.

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

`IconButtonRenderer` y `ButtonRenderer` exponen `addClickListener()` de forma predeterminada. El evento de clic proporciona acceso al objeto de datos de la fila a través de `e.getItem()`.

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

Para columnas que utilizan renderizadores visualmente costosos como insignias, barras de progreso, avatares o componentes web, habilita el renderizado perezoso para mejorar el rendimiento de desplazamiento.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Cuando se establece `setLazyRender(true)` en una columna, las celdas muestran un marcador de posición animado ligero mientras el usuario está desplazándose. El contenido real de la celda se renderiza una vez que se detiene el desplazamiento. Esta es una configuración a nivel de columna, por lo que puedes habilitarla selectivamente solo para las columnas que se benefician.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Cuándo habilitar el renderizado perezoso
Los renderizadores de celda crean más entidades dentro del DOM, lo que significa más trabajo de CPU durante el renderizado, sin importar qué renderizador lo cree.

El renderizado perezoso puede ayudar a reducir el impacto en el rendimiento si un renderizador es realmente necesario. Si solo necesitas cambiar o formatear el valor, y no estás creando un DOM complejo, utiliza un proveedor de valores en su lugar para transformar el valor.
:::

## Referencia de renderizadores integrados {#built-in-renderers}

webforJ incluye un conjunto completo de renderizadores para los casos de uso más comunes. Asigna cualquiera de ellos a una columna utilizando `column.setRenderer(renderer)`.

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

Muestra el contenido de la celda como texto plano o estilizado. Soporta colores de tema y decoraciones de texto como negrita, cursiva y subrayado.

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

Envuelve el valor de la celda en un elemento de insignia. Soporta temas, expansiones, coloreado automático (colores distintos automáticos por valor único) y un ícono opcional al principio.

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

Rinde una cadena de respaldo configurable cuando el valor de la celda es `null` o está vacío; de lo contrario, rinde el valor tal cual.

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
<strong>BooleanRenderer</strong>  -  verdadero/falso/nulo mostrado como íconos
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Reemplaza los valores `true`, `false` y `null` con íconos. Por defecto, utiliza un signo de verificación, una cruz y un guion.

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
<strong>StatusDotRenderer</strong>  -  punto indicador coloreado al lado del texto de la celda
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rinde un pequeño punto coloreado a la izquierda del valor de la celda. Asocia valores individuales a temas, cadenas de color CSS o instancias de `java.awt.Color`.

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

Formatea un valor numérico como una cantidad monetaria utilizando las reglas del `Locale` suministrado.

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

Muestra un valor numérico como un porcentaje. Establece el segundo argumento del constructor en `false` para evitar renderizar una barra de progreso delgada debajo del texto.

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

Rinde una barra de progreso de ancho completo con límites mínimos y máximos configurables, modo indeterminado y visualización a rayas o animada. Usa `setText()` con una expresión de lodash para superponer texto personalizado en la barra.

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

Aplica una máscara de caracteres a un valor de cadena. `#` coincide con cualquier dígito; los caracteres literales se conservan. Consulta [reglas de máscara de texto](/docs/components/fields/masked/textfield#mask-rules) para conocer todos los caracteres de máscara soportados.

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

Formatea un valor numérico utilizando una cadena de patrón con separadores conscientes del locale. `0` fuerza un dígito; `#` es opcional. Consulta [reglas de máscara de número](/docs/components/fields/masked/numberfield#mask-rules) para conocer todos los caracteres de máscara soportados.

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

Formatea un valor de fecha u hora utilizando tokens de patrón: `%Mz` (mes), `%Dz` (día), `%Yz` (año), y otros. Consulta [reglas de máscara de fecha](/docs/components/fields/masked/datefield#mask-rules) para conocer todos los tokens disponibles.

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
<strong>EmailRenderer</strong>  -  dirección de correo electrónico como un enlace mailto clickable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un ancla `mailto:`. Un ícono de correo de tema primario sirve como la pista visual por defecto.

```java
// Ícono de correo predeterminado
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
<strong>PhoneRenderer</strong>  -  número de teléfono como un enlace tel clickable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Envuelve el valor de la celda en un ancla `tel:`. En dispositivos móviles, al tocar se abre el marcador. Un ícono de teléfono de tema primario se muestra por defecto.

```java
// Ícono de teléfono predeterminado
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

Rinde un elemento ancla clickable. El `href` soporta expresiones de plantilla de lodash para que puedas construir URLs dinámicamente a partir del valor de la celda.

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

Muestra una imagen. El atributo `src` soporta expresiones de plantilla de lodash para que cada fila pueda mostrar una imagen diferente.

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

Rinde un componente de avatar. Las iniciales se derivan automáticamente del valor de la celda. Soporta temas y un ícono de respaldo.

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
<strong>IconRenderer</strong>  -  ícono independiente, opcionalmente clickable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rinde un solo ícono. Adjunta un listener de clic para un comportamiento interactivo.

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

Rinde un botón de ícono clickable. El evento de clic expone el elemento de la fila a través de `e.getItem()`, lo que lo hace ideal para acciones a nivel de fila.

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

Rinde un componente `Button` completo dentro de la celda.

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
<strong>ElementRenderer</strong>  -  elemento HTML en bruto con contenido de lodash
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rinde cualquier elemento HTML con una cadena de contenido de plantilla de lodash. Este es el enfoque de escape para situaciones donde ningún renderizador integrado se adapta.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Referencia de plantilla {#template-reference}

Los renderizadores ofrecen un mecanismo poderoso para personalizar la forma en que se muestra la información dentro de una `Tabla`. La clase principal, `Renderer`, está diseñada para ser extendida para crear renderizadores personalizados basados en plantillas de lodash, lo que permite un renderizado dinámico e interactivo de contenido.

Las plantillas de lodash permiten la inserción de HTML directamente en las celdas de la tabla, haciéndolas muy efectivas para renderizar datos de celdas complejos en una `Tabla`. Este enfoque permite la generación dinámica de HTML basado en los datos de la celda, facilitando contenido rico e interactivo en las celdas de la tabla.

### Sintaxis de Lodash {#lodash-syntax}

La siguiente sección describe los conceptos básicos de la sintaxis de Lodash. Si bien no es una descripción exhaustiva o completa, se puede usar para comenzar a utilizar Lodash dentro del componente `Tabla`.

#### Resumen de sintaxis para plantillas de lodash: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpole valores, insertando el resultado del código JavaScript en la plantilla.
- `<% ... %>` - Ejecuta código JavaScript, permitiendo bucles, condicionales y más.
- `<%- ... %>` - Escapa el contenido HTML, asegurando que los datos interpolados sean seguros contra ataques de inyección HTML.

#### Ejemplos utilizando datos de celdas: {#examples-using-cell-data}

**1. Interpolación de valor simple**: muestra directamente el valor de la celda.

`<%= cell.value %>`

**2. Renderizado condicional**: utiliza lógica de JavaScript para renderizar condicionalmente contenido.

`<% if (cell.value > 100) { %> 'Alto' <% } else { %> 'Normal' <% } %>`

**3. Combinando campos de datos**: renderiza contenido usando múltiples campos de datos de la celda.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Escapando contenido HTML**: rinde de forma segura el contenido generado por el usuario.

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
|value|`Object`|El valor en bruto de la celda, directamente de la fuente de datos.|

**Propiedades de TableRow:**

|Propiedad|Tipo|Descripción|
|-|-|-|
|cells|`TableCell[]`|Las celdas dentro de la fila.
|data|`Object`|Los datos proporcionados por la aplicación para la fila.
|even|`boolean`|Indica si la fila es de número par (para propósitos de estilo).
|first|`boolean`|Indica si la fila es la primera en la tabla.
|id|`String`|ID única para la fila.
|index|`int`|El índice de la fila.
|last|`boolean`|Indica si la fila es la última en la tabla.
|odd|`boolean`|Indica si la fila es de número impar (para propósitos de estilo).

**Propiedades de TableColumn:**

|Propiedad	|Tipo	|Descripción|
|-|-|-|
|align|ColumnAlignment|La alineación de la columna (izquierda, centro, derecha).
|id|String|El campo del objeto de fila del que obtener los datos de la celda.
|label|String|El nombre que se renderiza en el encabezado de la columna.
|pinned|ColumnPinDirection|La dirección de fijación de la columna (izquierda, derecha, automática).
|sortable|boolean|Si es verdadero, la columna se puede ordenar.
|sort|SortDirection|El orden de clasificación de la columna.
|type|ColumnType|El tipo de la columna (texto, número, booleano, etc.).
|minWidth|number|El ancho mínimo de la columna en píxeles.
