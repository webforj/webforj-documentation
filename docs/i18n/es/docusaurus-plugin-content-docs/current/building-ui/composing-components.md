---
sidebar_position: 4
title: Composición de Componentes
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: 7ca404aa73a9fd445ce7cd3da09b8155
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

El componente `Composite` combina componentes existentes de webforJ en componentes reutilizables y autónomos con comportamiento personalizado. Utilízalo para envolver componentes internos de webforJ en unidades de lógica empresarial reutilizables, reutilizar patrones de componentes a lo largo de tu aplicación y combinar múltiples componentes sin exponer detalles de implementación.

Un componente `Composite` tiene una fuerte asociación con un componente de soporte subyacente. Esto te da control sobre qué métodos y propiedades pueden acceder los usuarios, a diferencia de la herencia tradicional donde todo está expuesto.

Si necesitas integrar componentes web de otra fuente, utiliza alternativas especializadas:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Para componentes web con gestión de propiedades tipo segura.
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Para componentes web que aceptan contenido en ranuras.

<AISkillTip skill="webforj-creating-components" />

## Uso {#usage}

Para definir un componente `Composite`, extiende la clase `Composite` y especifica el tipo de componente que gestiona. Este se convierte en tu componente de soporte, que es el contenedor raíz que mantiene tu estructura interna:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Accede al componente de soporte para configurarlo
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Enviar"));
  }
}
```

El método `getBoundComponent()` proporciona acceso a tu componente subyacente, permitiéndote configurar sus propiedades, añadir componentes secundarios y gestionar su comportamiento directamente.

El componente de soporte puede ser cualquier [componente de webforJ](/docs/components/overview) o [componente de elemento HTML](/docs/components/html-elements). Para diseños flexibles, considera usar [`FlexLayout`](/docs/components/flex-layout) o [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) como tu componente de soporte.

:::note Extensión de Componentes
Nunca extiendas `Component` o `DwcComponent` directamente. Siempre utiliza patrones de composición con `Composite` para construir componentes personalizados.
:::

Sobrescribe `initBoundComponent()` cuando necesites mayor flexibilidad en la creación y gestión del componente de soporte, como utilizar constructores parametrizados en lugar del constructor por defecto sin argumentos. Usa este patrón cuando el componente de soporte requiere que se pasen componentes a su constructor en lugar de añadirse posteriormente.

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

webforJ maneja automáticamente toda la gestión del ciclo de vida para los componentes `Composite`. Al utilizar el método `getBoundComponent()`, la mayoría del comportamiento personalizado puede gestionarse en el constructor, incluyendo la adición de componentes secundarios, la configuración de propiedades, la configuración básica del diseño y el registro de eventos.

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

Si tienes requisitos específicos adicionales para configurar o limpiar, es posible que necesites usar los ganchos de ciclo de vida opcionales `onDidCreate()` y `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Inicializa componentes que requieren conexión al DOM
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Limpia recursos
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Lógica de actualización de datos
 }
}
```

Si necesitas realizar alguna acción después de que el componente se haya adjuntado al DOM, utiliza el método `whenAttached()`:

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

El siguiente ejemplo demuestra una lista de tareas donde cada elemento es un componente `Composite` que contiene un [`RadioButton`](/docs/components/radiobutton), un `Div` con texto y un [`Button`](/docs/components/button).

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/frontend/composite/composite.css',
]}
height='500px'
/>

## Ejemplo: Agrupación de componentes {#example-component-grouping}

A veces puedes querer usar un `Composite` para agrupar componentes relacionados en una sola unidad, incluso cuando la reutilización no es la principal preocupación:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/frontend/composite/analyticscomposite.css',
]}
height='550px'
/>
