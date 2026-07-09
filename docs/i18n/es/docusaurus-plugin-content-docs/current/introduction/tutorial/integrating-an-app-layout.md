---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: e56d98e67ff6ee74a4dc1ee81346350d
---
En este paso, reunirás todas las partes de tu aplicación en un diseño cohesivo. Al final de este paso, la estructura de tu aplicación se asemejará al [arquetipo SideMenu](/docs/building-ui/archetypes/sidemenu), y tendrás una mejor comprensión de cómo funcionan los siguientes componentes y conceptos:

- [`FlexLayout`](/docs/components/flex-layout)
- [Puntos de salida de ruta](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Ejecutando la aplicación {#running-the-app}

A medida que desarrollas tu aplicación, puedes usar [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) como comparación. Para ver la aplicación en acción:

1. Navega hasta el directorio de nivel superior que contiene el archivo `pom.xml`, que es `6-integrating-an-app-layout` si estás siguiendo la versión en GitHub.

2. Usa el siguiente comando de Maven para ejecutar la aplicación Spring Boot localmente:
    ```bash
    mvn
    ```

Ejecutar la aplicación abre automáticamente un nuevo navegador en `http://localhost:8080`.

## Creando un componente reutilizable {#creating-a-reusable-component}

En un paso anterior, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), creaste dos componentes compuestos que contenían el contenido de la tabla de clientes y el formulario de clientes. Como parte de este paso, crearás un componente compuesto más pequeño y reutilizable para mostrar el nombre de la aplicación dentro del menú lateral y una página "acerca de". Si decides cambiar el nombre de la aplicación en el futuro, solo necesitarías actualizarlo en este componente.

En `src/main/java/com/webforj/tutorial/components`, crea una clase llamada `AppTitle`. El componente vinculado para `AppTitle` será un `FlexLayout`, un componente contenedor que se usa a lo largo de este paso para mostrarte cómo hacer diseños más complejos. Para este `FlexLayout`, organizarás la dirección de los elementos y el espaciado entre ellos. Eso se hace utilizando los métodos `setDirection()` y `setSpacing()` respectivamente.

```java title='AppTitle.java'
// Haz que el componente vinculado sea un FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Organiza los elementos verticalmente
    self.setDirection(FlexDirection.COLUMN);

    // Establece el espacio entre elementos
    self.setSpacing("0px");
  }
}
```

Luego usa elementos HTML estándar para crear el título y el subtítulo. Establecer el margen inferior de un elemento de encabezado en `0px` acerca los elementos, y puedes estilizar el subtítulo utilizando [variables CSS de DWC](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Gestor de Clientes");
  private Paragraph subTitle = new Paragraph("Un Sistema de Registros Simple");

  public AppTitle() {
    title.setStyle("margin-bottom", "0px");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title, subTitle);
  }
}
```

### Renderizado Opcional {#optional-rendering}

A pesar de que `AppTitle` es simple, agregar un argumento booleano al método constructor te permite controlar cuándo renderizar ciertas partes del componente, como el subtítulo.

```java title='AppTitle.java'
// Agrega un argumento booleano
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Agrega el título por defecto
      .add(title);

  // Opcionalmente muestra el subtítulo
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### `AppTitle` Completado {#completed-app-title}

Todo junto, el componente reutilizable debería verse como lo siguiente:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Gestor de Clientes");
  private Paragraph subTitle = new Paragraph("Un Sistema de Registros Simple");

  public AppTitle(boolean showSubTitle) {
    title.setStyle("margin-bottom", "0");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title);

    if (showSubTitle) {
      self.add(subTitle);
    }
  }
}
```

## Creando una página "Acerca de" {#creating-an-about-page}

El primer lugar para agregar el componente `AppTitle` recién creado será una página "acerca de". Esta página incluye una imagen y el componente `AppTitle`, centrado en la página utilizando otro `FlexLayout`.

### Centrando el contenido usando un `FlexLayout` {#centering-content-using-a-flexlayout}

El objetivo es centrar el contenido de la página "acerca de" utilizando el `FlexLayout`. El componente `FlexLayout` sigue el [modelo de diseño flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Los métodos para el `FlexLayout`, como los utilizados anteriormente para orientar los elementos en una columna, son diferentes maneras de organizar los elementos.

Los métodos para organizar elementos en un `FlexLayout` utilizan un sistema direccional relativo. En lugar de pensar en los ejes horizontal y vertical, es mejor pensar en el eje paralelo a los elementos como el eje principal, y el eje perpendicular a los elementos como el eje cruzado.

Establecer tanto las propiedades `FlexJustifyContent` como `FlexAlignment` en `CENTER` centrará los elementos a lo largo de ambos ejes, principal y cruzado, en el `FlexLayout`, y hacer que el `FlexLayout` ocupe la totalidad de su contenedor padre lo centra en la página.

```java
private final FlexLayout layout = new FlexLayout();

// Llenar todo el espacio del elemento padre
layout.setSize("100%", "100%");

// Hacer que el eje principal sea vertical
layout.setDirection(FlexDirection.COLUMN);

// Centrar los elementos a lo largo del eje cruzado
layout.setAlignment(FlexAlignment.CENTER);

// Centrar los elementos a lo largo del eje principal
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Para ayudar a visualizar cómo funcionan los diferentes métodos, echa un vistazo a la publicación del blog [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Agregando recursos {#adding-resources}

Uno de los elementos que irá dentro del `FlexLayout` centrado es una imagen. Para este tutorial, puedes ver y descargar la [imagen de la página "acerca de"](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) en GitHub. Una vez descargada, agrégala a la carpeta estática de tu proyecto en `src/main/resources/static/images` y nómbrala `Files.svg`.

Colocar esta imagen en la carpeta estática te permite hacer referencia a ella utilizando el protocolo del servidor web, como lo hiciste al hacer referencia al archivo CSS en el primer paso, [Creando una Aplicación Básica](/docs/introduction/tutorial/creating-a-basic-app). Luego, puedes usarla dentro de tu aplicación como un elemento HTML, de esta manera:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### Creando `AboutView` {#creating-about-view}

Al igual que las otras dos páginas de la aplicación, la página "acerca de" será una vista con ruta. En `src/main/java/com/webforj/tutorial/views`, agrega una clase llamada `AboutView`. Usa un `FlexLayout` para el componente vinculado, como hiciste para `AppTitle`.

Dado que has nombrado la clase `AboutView`, no hay necesidad de dar un valor personalizado para la asignación de URL; esta página se renderiza en `http://localhost:8080/about` por defecto.

Así es como se verá cuando uses los conceptos de los pasos anteriores con los componentes recién creados para crear una nueva vista con contenido centrado:

```java title='AboutView.java'
@Route()
@FrameTitle("Acerca de")
public class AboutView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Img fileImg = new Img("ws://images/Files.svg");

  public AboutView() {
    fileImg.setWidth(250);
    self.setSize("100%", "100%")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(fileImg, new AppTitle(false));
  }
}
```

## Creando la ruta `Layout` {#creating-the-layout-route}

Se menciona brevemente en el paso [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), pero hay dos [tipos de ruta](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` y `AboutView` son todas rutas de tipo `View`, mientras que el tipo de ruta que usarás para crear el menú lateral de la aplicación es una ruta `Layout`.

Las rutas de diseño envuelven vistas secundarias y permiten que ciertas partes de la interfaz de usuario persistan a través de vistas, como un menú lateral. En `src/main/java/com/webforj/tutorial/layouts`, crea una clase llamada `MainLayout`.

### Puntos de salida de ruta {#route-outlets}

Al igual que las rutas de vista, `MainLayout` necesita una anotación `@Route`. Sin embargo, dado que tiene `Layout` como sufijo y las rutas de diseño no contribuyen a la URL, esta anotación no necesita argumentos.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

La aplicación sabe qué vistas renderizar dentro de `MainLayout` al declarar la clase de diseño como la [salida de ruta](/docs/routing/route-hierarchy/route-outlets) en cada vista. Los pasos anteriores solo tienen una propiedad `value` establecida en las anotaciones `@Route`, así que ahora necesitarás declarar explícitamente cuáles son las propiedades `value` y `outlet` para las clases de vista.

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java
  @Route(value = "/", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="AboutView" label="AboutView">
  ```java
  @Route(outlet = MainLayout.class)
  ```
  </TabItem>
</Tabs>

:::note Toques finales
Esta es la última modificación requerida para `FormView` y `AboutView` en este paso, así que recuerda actualizar la anotación `@Route` para esas vistas antes de ejecutar tu aplicación.
:::

## Usando el componente `AppLayout` {#using-the-app-layout-component}

Ahora que tu aplicación renderiza las vistas dentro de `MainLayout`, puedes elegir dónde se renderizan esos componentes. Elegir el `AppLayout` como el componente vinculado para `MainLayout` te permite almacenar las vistas en un área de contenido principal por defecto, al mismo tiempo que te brinda diferentes áreas para agregar elementos para el encabezado y el menú lateral.

### Slots {#slots}

Para muchos contenedores de webforJ, usar los métodos `add()` agrega componentes de UI al área de contenido principal. En el componente `AppLayout`, hay múltiples áreas para agregar componentes, cada una en un slot separado. Al marcar `MainLayout` como una ruta de diseño y establecer su componente vinculado como un `AppLayout`, las vistas se renderizan automáticamente en el slot de contenido principal.

En este paso, utilizarás los slots `drawer-title` y `drawer` para crear un menú lateral, y el slot `header` para mostrar en qué página se encuentra el usuario y un botón para alternar el menú lateral.

### Creando un menú lateral {#making-a-side-menu}

Cuando hay suficiente espacio en la pantalla del dispositivo, el componente `AppLayout` muestra un panel deslizante. Aquí es donde agregarás el `AppTitle` nuevamente y elementos que permitirán a los usuarios navegar por la aplicación.

Por defecto, `AppLayout` no muestra un encabezado del panel deslizante, pero usar el método `setDrawerHeaderVisible()` te permite mostrar elementos que están dentro del slot `drawer-title`, que será el `AppTitle` con su subtítulo mostrado.

```java
private AppLayout appLayout = new AppLayout();

// Mostrar el encabezado del Drawer
appLayout.setDrawerHeaderVisible(true);

// Agregar el AppTitle al encabezado del Drawer con su subtítulo
appLayout.addToDrawerTitle(new AppTitle(true));
```

El slot `drawer` debe contener los componentes que permiten a los usuarios navegar en la aplicación. Usar el componente [`AppNav`](/docs/components/appnav) facilita la creación de nuevas opciones de navegación. Para cada enlace, solo necesitas crear un `AppNavItem`. Los componentes `AppNavItem` en este tutorial utilizan tres parámetros:

- La etiqueta para el enlace
- La vista objetivo
- Un componente opcional [`Icon`](/docs/components/icon), usando imágenes de [Tabler](https://tabler.io/icons)

Agrupar todas las configuraciones del panel deslizante en `MainLayout` se ve de la siguiente manera:

```java title="MainLayout"
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setDrawer();
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("Dashboard", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("Acerca de", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
}
```

### Creando un encabezado {#making-a-header}

El slot `header` debe incluir dos elementos: un interruptor para mostrar u ocultar el menú lateral y una forma de mostrar el título del marco. Ambos elementos estarán dentro de un componente [Toolbar](/docs/components/toolbar), otra forma de organizar componentes.

Puedes incluir el interruptor para el panel deslizante de `AppLayout` con el componente `AppDrawerToggle`. Este componente ya está estilizado con un icono comúnmente utilizado para opciones de menú ocultas, y apunta al panel deslizante para abrirlo y cerrarlo.

```java
// Crear los componentes contenedores
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Agregar la Toolbar al encabezado del AppLayout
appLayout.addToHeader(toolbar);

// Agregar el AppDrawerToggle a la barra de herramientas
toolbar.addToStart(new AppDrawerToggle());
```

El encabezado también puede mostrar el título del marco utilizando el evento de navegación para recuperar detalles sobre el componente entrante, mientras que tiene un listener de evento para eliminar el registro y prevenir fugas de memoria.

```java
// Crear el elemento H1 y el registro de navegación
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Registrar el evento al navegar
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Eliminar listeners antes de que MainLayout sea destruido
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Recuperar el título del marco de la clase de vista entrante
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## `MainLayout` Completado {#completed-mainlayout}

Aquí está `MainLayout` con el contenido creado para el panel y el encabezado dentro de un `AppLayout`:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route
  public class MainLayout extends Composite<AppLayout> {
    private AppLayout self = getBoundComponent();
    private H1 title = new H1("");
    private ListenerRegistration<NavigateEvent> navigateRegistration;
    private Toolbar toolbar = new Toolbar();
    private AppNav appNav = new AppNav();

    public MainLayout() {
      setHeader();
      setDrawer();
      navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);
    }

    private void setHeader() {
      self.addToHeader(toolbar);

      toolbar.addToStart(new AppDrawerToggle());
      toolbar.addToTitle(title);
    }

    private void setDrawer() {
      self.setDrawerHeaderVisible(true)
          .addToDrawerTitle(new AppTitle(true));

      appNav.addItem(new AppNavItem("Dashboard", MainView.class,
          TablerIcon.create("archive")));
      appNav.addItem(new AppNavItem("Acerca de", AboutView.class,
          TablerIcon.create("info-circle")));
      self.addToDrawer(appNav);
    }

    @Override
    protected void onDidDestroy() {
      if (navigateRegistration != null) {
        navigateRegistration.remove();
      }
    }

    private void onNavigate(NavigateEvent ev) {
      Component component = ev.getContext().getComponent();
      if (component != null) {
        FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
        title.setText(frameTitle != null ? frameTitle.value() : "");
      }
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Actualizando `FormView` {#updating-form-view}

Como se mencionó anteriormente, el único cambio en `FormView` fue a la anotación `@Route`.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## Actualizando `MainView` {#updating-main-view}

Para `MainView`, cambiarás el componente vinculado de un `Div` a un `FlexLayout`. Esto te permitirá centrar la tabla, al mismo tiempo que moverás componentes específicos dentro del diseño. Usar el método `setItemAlignment()` te permite elegir un componente en el diseño y moverlo, para que puedas mantener la tabla centrada mientras anclas el botón de agregar cliente a la parte superior derecha del diseño.

```java
// Cambiar el componente vinculado a un FlexLayout
private FlexLayout self = getBoundComponent();

// Alinear el botón al final del eje cruzado
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Otra mejora que puedes hacer aquí es el ancho de la tabla. En lugar de un ancho fijo, puedes establecerlo para que coincida con su contenedor padre, el `FlexLayout`. Luego, ese `FlexLayout` puede tener un ancho máximo para que no se estire demasiado en pantallas más grandes.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Poniendo estos elementos juntos y creando otro método para centrar el `FlexLayout` como los anteriores hace que `MainView` con los cambios resaltados sea:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Tabla de Clientes")
  // highlight-next-line
  public class MainView extends Composite<FlexLayout> {
    private final CustomerService customerService;
    // highlight-next-line
    private FlexLayout self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Agregar Cliente", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      // highlight-next-line
      setFlexLayout();
      // highlight-next-line
      self.add(addCustomer, table);
      // highlight-next-line
      self.setItemAlignment(FlexAlignment.END, addCustomer);
    }

    private void buildTable() {
      // highlight-next-line
      table.setSize("100%", "294px");
      table.addColumn("firstName", Customer::getFirstName).setLabel("Nombre");
      table.addColumn("lastName", Customer::getLastName).setLabel("Apellido");
      table.addColumn("company", Customer::getCompany).setLabel("Compañía");
      table.addColumn("country", Customer::getCountry).setLabel("País");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
      table.setKeyProvider(Customer::getId);
      table.addItemClickListener(this::editCustomer);
    }

    // highlight-next-line
    private void setFlexLayout() {
      // highlight-next-line
      self.setSize("100%", "100%")
          // highlight-next-line
          .setMargin("auto")
          // highlight-next-line
          .setMaxWidth(2000)
          // highlight-next-line
          .setDirection(FlexDirection.COLUMN)
          // highlight-next-line
          .setAlignment(FlexAlignment.CENTER);
          // highlight-next-line
    }

    private void editCustomer(TableItemClickEvent<Customer> e) {
      Router.getCurrent().navigate(FormView.class,
          ParametersBag.of("id=" + e.getItemKey()));
    }
  }
`}
</ExpandableCode>
<!-- vale on -->
