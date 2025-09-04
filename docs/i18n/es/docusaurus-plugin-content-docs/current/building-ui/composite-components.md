---
sidebar_position: 2
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: 997bb40968c4f4ede5eccb00c27e5305
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

El componente `Composite` combina componentes existentes de webforJ en componentes reutilizables y auto contenidos con comportamiento personalizado. Úsalo para envolver componentes internos de webforJ en unidades de lógica de negocio reutilizables, reutilizar patrones de componentes en toda tu aplicación y combinar múltiples componentes sin exponer detalles de implementación.

Un componente `Composite` tiene una fuerte asociación con un componente vinculado subyacente. Esto te da control sobre qué métodos y propiedades pueden acceder los usuarios, a diferencia de la herencia tradicional donde todo está expuesto.

Si necesitas integrar componentes web de otra fuente, utiliza alternativas especializadas:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Para componentes web con gestión de propiedades con tipo seguro.
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Para componentes web que aceptan contenido slotado.

## Uso {#usage}

Para definir un componente `Composite`, extiende la clase `Composite` y especifica el tipo de componente que gestiona. Esto se convierte en tu componente vinculado, que es el contenedor raíz que sostiene tu estructura interna:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {

  public BasicComposite() {
    // Accede al componente vinculado para configurarlo
    getBoundComponent()
      .setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Enviar"));
  }
}
```

El método `getBoundComponent()` proporciona acceso a tu componente subyacente, lo que te permite configurar sus propiedades, agregar componentes hijos y gestionar su comportamiento directamente.

El componente vinculado puede ser cualquier [componente de webforJ](../components/overview) o [componente de elemento HTML](/docs/building-ui/web-components/html-elements). Para diseños flexibles, considera usar [`FlexLayout`](../components/flex-layout) o [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) como tu componente vinculado.

:::note Extensión del Componente
Nunca extiendas `Component` o `DwcComponent` directamente. Siempre usa patrones de composición con `Composite` para construir componentes personalizados.
:::

Sobrescribe `initBoundComponent()` cuando necesites mayor flexibilidad para crear y gestionar el componente vinculado, como usar constructores parametrizados en lugar del constructor por defecto sin argumentos. Usa este patrón cuando el componente vinculado requiera que se le pasen componentes a su constructor en lugar de agregarlos después.


```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Nombre");
   emailField = new TextField("Correo electrónico");
   submitButton = new Button("Enviar");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Ciclo de vida del componente {#component-lifecycle}

webforJ maneja automáticamente toda la gestión del ciclo de vida para componentes `Composite`. Al usar el método `getBoundComponent()`, la mayoría del comportamiento personalizado se puede manejar en el constructor, incluyendo la adición de componentes hijos, establecimiento de propiedades, configuración básica de diseño y registro de eventos.

```java
public class UserDashboard extends Composite<FlexLayout> {
 private TextField searchField;
 private Button searchButton;
 private Div resultsContainer;

 public UserDashboard() {
   initializeComponents();
   setupLayout();
   configureEvents();
 }

 private void initializeComponents() {
   searchField = new TextField("Buscar usuarios...");
   searchButton = new Button("Buscar");
   resultsContainer = new Div();
 }

 private void setupLayout() {
   FlexLayout searchRow = new FlexLayout(searchField, searchButton);
   searchRow.setAlignment(FlexAlignment.CENTER);
   searchRow.setSpacing("8px");

   getBoundComponent()
     .setDirection(FlexDirection.COLUMN)
     .add(searchRow, resultsContainer);
 }

 private void configureEvents() {
   searchButton.onClick(event -> performSearch());
 }

 private void performSearch() {
   // Lógica de búsqueda aquí
 }
}
```

Si tienes requisitos específicos adicionales de configuración o limpieza, es posible que necesites usar los ganchos de ciclo de vida opcionales `onDidCreate()` y `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Inicializa componentes que requieren adjunto al DOM
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Limpieza de recursos
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Lógica de actualización de datos
 }
}
```

Si necesitas realizar cualquier acción después de que el componente se adjunta al DOM, usa el método `whenAttached()`:

```java title="InteractiveMap.java"
public class InteractiveMap extends Composite<Div> {
  public InteractiveMap() {
    setupMapContainer();

    whenAttached().thenAccept(component -> {
      initializeMapLibrary();
      loadMapData();
    });
  }
}
```

## Ejemplo de componente `Composite` {#example-composite-component}

El siguiente ejemplo demuestra una aplicación de To-Do donde cada ítem es un componente `Composite` que consiste en un [`RadioButton`](../components/radiobutton) estilizado como un interruptor y un Div con texto: 

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Ejemplo: Agrupamiento de componentes {#example-component-grouping}

A veces, puede que quieras usar un `Composite` para agrupar componentes relacionados en una sola unidad, incluso cuando la reutilización no sea la principal preocupación:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
