---
sidebar_position: 2
title: Understanding Components
_i18n_hash: 9236dac850f1e56f91cbcada9b6d8921
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Antes de construir componentes personalizados en webforJ, es importante entender la arquitectura fundamental que moldea cómo funcionan los componentes. Este artículo explica la jerarquía de componentes, la identidad del componente, los conceptos del ciclo de vida y cómo las interfaces de preocupación proporcionan capacidades a los componentes.

## Entendiendo la jerarquía de componentes {#understanding-the-component-hierarchy}

webforJ organiza los componentes en una jerarquía con dos grupos: clases internas del framework que nunca deberías extender, y clases diseñadas específicamente para construir componentes personalizados. Esta sección explica por qué webforJ utiliza composición en lugar de herencia y qué proporciona cada nivel de la jerarquía.

### ¿Por qué composición en lugar de extensión? {#why-composition-instead-of-extension}

En webforJ, los componentes integrados como [`Button`](../components/button) y [`TextField`](../components/fields/textfield) son clases finales—no puedes extenderlas:

```java
// Esto no funcionará en webforJ
public class MyButton extends Button {
  // Button es final - no se puede extender 
}
```

webforJ utiliza **composición en lugar de herencia**. En lugar de extender componentes existentes, creas una clase que extiende `Composite` y combina componentes dentro de ella. `Composite` actúa como un contenedor que envuelve un único componente (llamado componente asociado) y te permite añadir tus propios componentes y comportamientos.

```java
public class SearchBar extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField searchField;
  private Button searchButton;
  
  public SearchBar() {
    searchField = new TextField("Buscar");
    searchButton = new Button("Ir");
    
    self.setDirection(FlexDirection.ROW)
      .add(searchField, searchButton);
  }
}
```

### ¿Por qué no puedes extender componentes integrados? {#why-you-cant-extend-built-in-components}

Los componentes de webforJ están marcados como finales para mantener la integridad del componente web subyacente del lado del cliente. Extender las clases de componentes de webforJ otorgaría control sobre el componente web subyacente, lo que podría llevar a consecuencias no deseadas y romper la consistencia y previsibilidad del comportamiento del componente.

Para una explicación detallada, consulta [Clases Finales y Restricciones de Extensión](https://docs.webforj.com/docs/architecture/controls-components#final-classes-and-extension-restrictions) en la documentación de arquitectura.

### La jerarquía de componentes {#the-component-hierarchy}

<div style={{textAlign: 'center'}}>
```mermaid
graph TD
  A[Componente<br/><small>Base abstracta - interna del framework</small>]
  
  A --> B[DwcComponent<br/><small>Componentes integrados webforJ</small>]
  A --> C[Composite<br/><small>Combina componentes webforJ</small>]
  
  B --> E[Button, TextField,<br/>DateField, ComboBox]
  
  C --> D[ElementComposite<br/><small>Envuelve componentes web</small>]
  D --> F[ElementCompositeContainer<br/><small>Componentes con ranuras</small>]

  classDef internal stroke-dasharray:6 4,stroke-width:1px
  classDef primary stroke-width:3px
  classDef secondary stroke-width:2px,stroke-dasharray:2 2
  class A,B,E internal
  class C primary
  class D,F secondary
```
</div>

Clases para desarrolladores (usa estas):
- `Composite`
- `ElementComposite`
- `ElementCompositeContainer`

Clases internas del framework (nunca extender directamente):
- `Component`
- `DwcComponent`

:::warning[Nunca extiendas `Component` o `DwcComponent`]
Nunca extiendas `Component` o `DwcComponent` directamente. Todos los componentes integrados son finales. Siempre usa patrones de composición con `Composite` o `ElementComposite`.

Intentar extender `DwcComponent` generará una excepción en tiempo de ejecución.
:::

## Interfaces de preocupación {#concern-interfaces}

Las interfaces de preocupación son interfaces de Java que proporcionan capacidades específicas a tus componentes. Cada interfaz añade un conjunto de métodos relacionados. Por ejemplo, `HasSize` añade métodos para controlar el ancho y alto, mientras que `HasFocus` añade métodos para gestionar el estado de enfoque.

Cuando implementas una interfaz de preocupación en tu componente, obtienes acceso a esas capacidades sin escribir ningún código de implementación. La interfaz proporciona implementaciones predeterminadas que funcionan automáticamente.

Implementar interfaces de preocupación le da a tus componentes personalizados las mismas APIs que los componentes integrados de webforJ:

```java
// Implementa HasSize para obtener métodos de ancho/alto automáticamente
public class SizedCard extends Composite<Div> implements HasSize<SizedCard> {
  private final Div self = getBoundComponent();
  
  public SizedCard() {
    self.setText("Contenido de la tarjeta");
  }
  
  // No es necesario implementar estos - los obtienes gratis:
  // setWidth(), setHeight(), setSize()
}

// Úsalo como cualquier componente de webforJ
SizedCard card = new SizedCard();
card.setWidth("300px")
  .setHeight("200px");
```

El composite automáticamente reenvía estas llamadas al `Div` subyacente. No se necesita código adicional.

### Apariencia {#concern-interfaces-appearance}

Estas interfaces controlan la presentación visual de un componente, incluyendo sus dimensiones, visibilidad, estilo y tema.

| Interfaz | Descripción |
|---|---|
| `HasSize` | Controla el ancho y alto, incluyendo restricciones mínimas y máximas. Extiende `HasWidth`, `HasHeight` y sus variantes mínimas/máximas. |
| `HasVisibility` | Muestra u oculta el componente sin eliminarlo del diseño. |
| `HasClassName` | Gestiona los nombres de clase CSS en el elemento raíz del componente. |
| `HasStyle` | Aplica y elimina estilos CSS en línea. |
| `HasHorizontalAlignment` | Controla cómo se alinea el contenido horizontalmente dentro del componente. |
| `HasExpanse` | Establece la variante de tamaño del componente utilizando los tokens de expanse estándar (`XSMALL` a `XLARGE`). |
| `HasTheme` | Aplica una variante de tema como `DEFAULT`, `PRIMARY` o `DANGER`. |
| `HasPrefixAndSuffix` | Añade componentes a la ranura de prefijo o sufijo dentro del componente. |

### Contenido {#concern-interfaces-content}

Estas interfaces gestionan lo que un componente muestra, incluyendo texto, HTML, etiquetas, sugerencias y otro contenido descriptivo.

| Interfaz | Descripción |
|---|---|
| `HasText` | Establece y recupera el contenido de texto plano del componente. |
| `HasHtml` | Establece y recupera el HTML interno del componente. |
| `HasLabel` | Añade una etiqueta descriptiva asociada con el componente, utilizada para la accesibilidad. |
| `HasHelperText` | Muestra texto de sugerencia secundario debajo del componente. |
| `HasPlaceholder` | Establece texto de marcador de posición mostrado cuando el componente no tiene valor. |
| `HasTooltip` | Adjunta un tooltip que aparece al pasar el ratón. |

### Estado {#concern-interfaces-state}

Estas interfaces controlan el estado interactivo de un componente, incluyendo si está habilitado, editable, requerido o enfocado al cargar.

| Interfaz | Descripción |
|---|---|
| `HasEnablement` | Habilita o deshabilita el componente. |
| `HasReadOnly` | Coloca al componente en un estado de solo lectura donde el valor es visible pero no se puede cambiar. |
| `HasRequired` | Marca el componente como requerido, típicamente para validación de formularios. |
| `HasAutoFocus` | Mueve el enfoque al componente automáticamente cuando se carga la página. |

### Enfoque {#concern-interfaces-focus}

Estas interfaces gestionan cómo un componente recibe y responde al enfoque del teclado.

| Interfaz | Descripción |
|---|---|
| `HasFocus` | Gestiona el estado de enfoque y si el componente puede recibir enfoque. |
| `HasFocusStatus` | Verifica si el componente actualmente tiene enfoque. Requiere un viaje de ida y vuelta al cliente. |
| `HasHighlightOnFocus` | Controla si el contenido del componente se resalta cuando recibe enfoque, y cómo (`KEY`, `MOUSE`, `KEY_MOUSE`, `ALL`, etc.). |

### Restricciones de entrada {#concern-interfaces-input-constraints}

Estas interfaces definen qué valores acepta un componente, incluyendo el valor actual, rangos permitidos, límites de longitud, máscaras de formato y comportamiento específico de la localidad.

| Interfaz | Descripción |
|---|---|
| `HasValue` | Obtiene y establece el valor actual del componente. |
| `HasMin` | Establece un valor mínimo permitido. |
| `HasMax` | Establece un valor máximo permitido. |
| `HasStep` | Establece el incremento de paso para entradas numéricas o de rango. |
| `HasPattern` | Aplica un patrón de expresión regular para restringir la entrada aceptada. |
| `HasMinLength` | Establece el número mínimo de caracteres requeridos en el valor del componente. |
| `HasMaxLength` | Establece el número máximo de caracteres permitidos en el valor del componente. |
| `HasMask` | Aplica una máscara de formato a la entrada. Usado por componentes de campo enmascarados. |
| `HasTypingMode` | Controla si los caracteres escritos se insertan o sobrescriben caracteres existentes (`INSERT` o `OVERWRITE`). Usado por campos enmascarados y `TextArea`. |
| `HasRestoreValue` | Define un valor al que el componente se restablece cuando el usuario presiona Escape o llama a `restoreValue()`. Usado por campos enmascarados. |
| `HasLocale` | Almacena una localidad por componente para un formato sensible a la localidad. Usado por campos de fecha y hora enmascarados. |
| `HasPredictedText` | Establece un valor de texto predicho o de autocompletar. Usado por `TextArea` para soportar sugerencias en línea. |

### Validación {#concern-interfaces-validation}

Estas interfaces añaden comportamiento de validación del lado del cliente, incluyendo marcar componentes como inválidos, mostrar mensajes de error y controlar cuándo se ejecuta la validación.

| Interfaz | Descripción |
|---|---|
| `HasClientValidation` | Marca un componente como inválido, establece el mensaje de error y adjunta un validador del lado del cliente. |
| `HasClientAutoValidation` | Controla si el componente valida automáticamente a medida que el usuario escribe. |
| `HasClientAutoValidationOnLoad` | Controla si el componente valida al cargarse por primera vez. |
| `HasClientValidationStyle` | Controla cómo se muestran los mensajes de validación: `INLINE` (debajo del componente) o `POPOVER`. |

### Acceso al DOM {#concern-interfaces-dom-access}

Estas interfaces proporcionan acceso de bajo nivel al elemento HTML subyacente del componente y a las propiedades del lado del cliente.

| Interfaz | Descripción |
|---|---|
| `HasAttribute` | Lee y escribe atributos HTML arbitrarios en el elemento del componente. |
| `HasProperty` | Lee y escribe propiedades del componente DWC directamente en el elemento del cliente. |

### i18n {#concern-interfaces-i18n}

Esta interfaz proporciona soporte de traducción para componentes que necesitan mostrar texto localizado.

| Interfaz | Descripción |
|---|---|
| `HasTranslation` | Proporciona el método auxiliar `t()` para resolver claves de traducción a cadenas localizadas utilizando la localidad actual de la aplicación. |

:::warning
Si el componente subyacente no soporta la capacidad de la interfaz, recibirás una excepción en tiempo de ejecución. Proporciona tu propia implementación en ese caso.
:::

Para una lista completa de interfaces de preocupación disponibles, consulta el [JavaDoc de webforJ](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/concern/package-summary.html).

## Identificadores de componentes {#component-identifiers}

Los componentes de webforJ tienen tres tipos distintos de identificadores que sirven para diferentes propósitos:

- **ID de componente del lado del servidor** (`getComponentId()`) - Asignado automáticamente por el framework para el seguimiento interno de componentes. Utiliza esto cuando necesites consultar componentes específicos o implementar registros de componentes personalizados.
- **ID de componente del lado del cliente** (`getClientComponentId()`) - Proporciona acceso al componente web subyacente desde JavaScript. Utiliza esto cuando necesites llamar a métodos nativos de componentes web o integrar con bibliotecas del lado del cliente.
- **Atributo `id` HTML** (`setAttribute("id", "...")`) - Identificador estándar del DOM. Utiliza esto para orientar CSS, selectores de automatización de pruebas y vincular etiquetas de formulario a entradas.

Entender estas diferencias te ayuda a elegir el identificador correcto para tu caso de uso.

### ID de componente del lado del servidor {#server-side-component-id}

Cada componente se asigna un identificador del lado del servidor automáticamente al ser creado. Este identificador es utilizado internamente por el framework para el seguimiento de componentes. Recupéralo con `getComponentId()`:

```java
Button button = new Button("Clic Me");
String serverId = button.getComponentId();
```

El ID del lado del servidor es útil cuando necesitas consultar componentes específicos dentro de un contenedor o implementar lógica de seguimiento de componentes personalizada.

### ID de componente del lado del cliente {#client-side-component-id}

El ID de componente del lado del cliente proporciona acceso al componente web subyacente desde JavaScript. Esto te permite interactuar directamente con el componente del lado del cliente cuando sea necesario:

```java
Button btn = new Button("Clic me");
btn.onClick(e -> {
  OptionDialog.showMessageDialog("El botón fue clickeado", "Ocurrió un evento");
});

btn.whenAttached().thenAccept(e -> {
  Page.getCurrent().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
});
```

Utiliza `getClientComponentId()` con `objects.get()` en JavaScript para acceder a la instancia del componente web.

:::important
El ID de componente del lado del cliente no es el atributo `id` HTML del elemento DOM. Para establecer HTML IDs para pruebas o orientación CSS, consulta [Uso de Componentes](using-components).
:::

## Resumen del ciclo de vida del componente {#component-lifecycle-overview}

webforJ gestiona automáticamente el ciclo de vida del componente. El framework se encarga de la creación, conexión y destrucción de componentes sin requerir intervención manual.

**Ganchos de ciclo de vida** están disponibles cuando los necesitas:
- `onDidCreate(T container)` - Llamado después de que el componente se adjunta al DOM
- `onDidDestroy()` - Llamado cuando el componente es destruido

Estos ganchos son **opcionales**. Úsalos cuando necesites:
- Limpiar recursos (detener intervalos, cerrar conexiones)
- Inicializar componentes que requieren conexión al DOM
- Integrar con JavaScript del lado del cliente

Para la mayoría de los casos simples, puedes inicializar componentes directamente en el constructor. Usa ganchos del ciclo de vida como `onDidCreate()` para diferir trabajo cuando sea necesario.
