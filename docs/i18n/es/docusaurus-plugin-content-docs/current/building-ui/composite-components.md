---
sidebar_position: 4
title: Composite Components
_i18n_hash: fb15eb19cfe0ca1aebb77a67b10c9ecd
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

El componente `Composite` combina componentes existentes de webforJ en componentes reutilizables y autónomos con comportamiento personalizado. Úsalo para envolver componentes internos de webforJ en unidades de lógica de negocio reutilizables, reutilizar patrones de componentes en toda tu aplicación y combinar múltiples componentes sin exponer detalles de implementación.

Un componente `Composite` tiene una fuerte asociación con un componente subyacente vinculado. Esto te da control sobre qué métodos y propiedades pueden acceder los usuarios, a diferencia de la herencia tradicional donde todo está expuesto.

Si necesitas integrar componentes web de otra fuente, utiliza alternativas especializadas:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Para componentes web con gestión de propiedades tipo segura
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Para componentes web que aceptan contenido con ranuras

<AISkillTip skill="webforj-creating-components" />

## Uso {#usage}

Para definir un componente `Composite`, extiende la clase `Composite` y especifica el tipo de componente que gestiona. Este se convierte en tu componente vinculado, que es el contenedor raíz que sostiene tu estructura interna:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Accede al componente vinculado para configurarlo
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Enviar"));
  }
}
```

El método `getBoundComponent()` proporciona acceso a tu componente subyacente, permitiéndote configurar sus propiedades, agregar componentes hijos y gestionar su comportamiento directamente.

El componente vinculado puede ser cualquier [componente de webforJ](/docs/components/overview) o [elemento de componente HTML](/docs/components/html-elements). Para diseños flexibles, considera usar [`FlexLayout`](/docs/components/flex-layout) o [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) como tu componente vinculado.

:::note Extensión de Componente
Nunca extiendas `Component` o `DwcComponent` directamente. Siempre utiliza patrones de composición con `Composite` para construir componentes personalizados.
:::

Sobrescribe `initBoundComponent()` cuando necesites mayor flexibilidad en la creación y gestión del componente vinculado, como usar constructores parametrizados en lugar del constructor por defecto sin argumentos. Usa este patrón cuando el componente vinculado requiera que los componentes sean pasados a su constructor en lugar de añadirse posteriormente.

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

webforJ maneja automáticamente toda la gestión del ciclo de vida para los componentes `Composite`. Al utilizar el método `getBoundComponent()`, la mayor parte del comportamiento personalizado se puede manejar en el constructor, incluyendo agregar componentes hijos, establecer propiedades, configuración básica del diseño y registro de eventos.

```java
public class UserDashboard extends Composite<FlexLayout> {
 private final FlexLayout self = getBoundComponent();
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

Si tienes requisitos específicos adicionales de configuración o limpieza, es posible que necesites utilizar los ganchos de ciclo de vida opcionales `onDidCreate()` y `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Inicializar componentes que requieren adjunto al DOM
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Limpiar recursos
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Lógica de actualización de datos
 }
}
```

Si necesitas realizar alguna acción después de que el componente esté adjunto al DOM, utiliza el método `whenAttached()`:

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

El siguiente ejemplo demuestra una aplicación de Todo donde cada ítem es un componente `Composite` que consiste en un [`RadioButton`](../components/radiobutton) estilizado como un interruptor y un Div con texto:

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Ejemplo: Agrupación de componentes {#example-component-grouping}

A veces puedes querer usar un `Composite` para agrupar componentes relacionados en una sola unidad, incluso cuando la reutilización no es la principal preocupación:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
