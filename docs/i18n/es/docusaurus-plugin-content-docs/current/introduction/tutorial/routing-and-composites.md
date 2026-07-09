---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: b6d14f241d64208bfcfff527691bf8e9
---
Hasta ahora, este tutorial solo ha sido una aplicación de una sola página. Este paso cambia eso. 
Moverás la interfaz de usuario que creaste en [Trabajando con Datos](/docs/introduction/tutorial/working-with-data) a su propia página y crearás otra página para agregar nuevos clientes. 
Luego, conectarás estas páginas para que tu aplicación pueda navegar entre ellas aplicando estos conceptos:

- [Enrutamiento](/docs/routing/overview)
- [Composición de componentes](/docs/building-ui/composing-components)
- El componente [`ColumnsLayout`](/docs/components/columns-layout)

Completar este paso crea una versión de [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Insertar video aquí -->

## Ejecutando la aplicación {#running-the-app}

A medida que desarrollas tu aplicación, puedes usar [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) como comparación. Para ver la aplicación en acción:

1. Navega al directorio de nivel superior que contiene el archivo `pom.xml`; este es `3-routing-and-composites` si estás siguiendo la versión en GitHub.

2. Utiliza el siguiente comando de Maven para ejecutar la aplicación Spring Boot localmente:
    ```bash
    mvn
    ```

Ejecutar la aplicación abre automáticamente un nuevo navegador en `http://localhost:8080`.

## Aplicaciones enrutables {#routable-apps}

Anteriormente, tu aplicación tenía una sola función: mostrar una tabla de datos de clientes existentes. 
En este paso, tu aplicación también podrá modificar los datos de los clientes al agregar nuevos clientes. 
Separar las interfaces de usuario para la visualización y modificación es beneficioso para el mantenimiento a largo plazo y las pruebas, por lo que agregarás esta función como una página separada. 
Harás que tu aplicación sea [enrutable](/docs/routing/overview) para que webforJ pueda acceder y cargar las dos interfaces de usuario individualmente.

Una aplicación enrutable renderiza la interfaz de usuario según la URL. Anotar la clase que extiende la clase `App` con [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) habilita el enrutamiento, y el elemento `packages` indica a webforJ qué paquetes contienen componentes de la interfaz de usuario.

Cuando agregues la anotación `@Routify` a `Application`, elimina el método `run()`. Moverás los componentes de ese método a una clase que crearás en el paquete `com.webforj.tutorial.views`. Tu archivo `Application.java` actualizado debería verse así:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

// Añadida anotación @Routify
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Método App.run() sobrescrito eliminado

}
```

:::tip CSS global
Mantener la anotación `@StyleSheet` en `Application` aplica ese CSS globalmente.
:::

### Creando rutas {#creating-routes}

Agregar la anotación `@Routify` hace que tu aplicación sea enrutable. Una vez que sea enrutable, tu aplicación buscará en el paquete `com.webforj.tutorial.views` rutas. 
Necesitarás crear las rutas para tus interfaces de usuario y también especificar sus [Tipos de Ruta](/docs/routing/route-hierarchy/route-types). El tipo de ruta determina cómo mapear el contenido de la interfaz de usuario a la URL.

El primer tipo de ruta es `View`. Este tipo de rutas se mapea directamente a un segmento de URL específico en tu aplicación. Las interfaces de usuario para la tabla y el formulario de nuevos clientes serán rutas de tipo `View`.

El segundo tipo de ruta es `Layout`, que contiene UI que aparece en múltiples páginas, como un encabezado o barra lateral. Las rutas de diseño también envuelven vistas secundarias sin contribuir a la URL.

Para especificar el tipo de ruta de una clase, agrega el tipo a la parte final del nombre de la clase como sufijo. 
Por ejemplo, `MainView` es de tipo ruta `View`.

Para mantener las dos funciones de la aplicación separadas, tu aplicación necesita mapear las interfaces de usuario a dos rutas `View` únicas: una para la tabla y otra para el formulario de clientes. En `/src/main/java/com/webforj/tutorial/views`, crea dos clases con un sufijo `View`:

- **`MainView`**: Esta vista tendrá la `Table` que estaba previamente en la clase `Application`.
- **`FormView`**: Esta vista tendrá un formulario para agregar nuevos clientes.

### Mapeando URLs a componentes {#mapping-urls-to-components}

Tu aplicación es enrutable y sabe buscar dos rutas `View`, `MainView` y `FormView`, pero no tiene una URL específica para cargarles. Utilizando la anotación `@Route` en una clase de vista, puedes decirle a webforJ dónde cargarla según un segmento de URL dado. Por ejemplo, usando `@Route("about")` en una vista mapea localmente la clase a `http://localhost:8080/about`.

Como su nombre indica, `MainView` es la clase que deseas cargar inicialmente cuando la aplicación se ejecuta. Para lograr esto, agrega una anotación `@Route` que mapea `MainView` a la URL raíz de tu aplicación:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Para `FormView`, mapea la vista para que se cargue cuando un usuario vaya a `http://localhost:8080/customer`:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Comportamiento predeterminado
Si no asignas explícitamente un valor para la anotación `@Route`, el segmento de URL es el nombre de la clase convertido a minúsculas, con el sufijo `View` eliminado.

- `MainView` se mapearía a `/main`
- `FormView` se mapearía a `/form`
:::

## Características compartidas {#shared-characteristics}

Además de ser ambas rutas de vista, `MainView` y `FormView` comparten características adicionales. Algunas de estas características compartidas, como el uso de componentes `Composite`, son fundamentales para usar aplicaciones webforJ, mientras que otras solo facilitan la gestión de tu aplicación.

### Usando componentes `Composite` {#using-composite-components}

Cuando la aplicación era de una sola página, almacenabas los componentes dentro de un `Frame`. A partir de ahora, con una aplicación de múltiples vistas, necesitarás envolver esos componentes de la interfaz de usuario dentro de componentes [`Composite`](/docs/building-ui/composing-components).

Los componentes `Composite` son envoltorios que facilitan la creación de componentes reutilizables. Para crear un componente `Composite`, extiende la clase `Composite` con un componente vinculado especificado que sirva como la base de la clase, por ejemplo, `Composite<FlexLayout>`.

Este tutorial utiliza elementos `Div` como los componentes vinculados, pero pueden ser cualquier componente, como [`FlexLayout`](/docs/components/flex-layout) o [`AppLayout`](/docs/components/app-layout). Usando el método `getBoundComponent()`, puedes referenciar el componente vinculado y tener acceso a sus métodos. Esto te permite establecer el tamaño, agregar un nombre de clase CSS, agregar componentes que deseas mostrar en el componente `Composite` y acceder a métodos específicos del componente.

Para `MainView` y `FormView`, extiende `Composite` con `Div` como el componente vinculado. Luego, referencia ese componente vinculado para que puedas agregar las interfaces de usuario más adelante. Ambas vistas deberían parecerse a la siguiente estructura:

```java
// Extiende Composite con un componente vinculado
public class MainView extends Composite<Div> {

  // Accede al componente vinculado
  private Div self = getBoundComponent();

  // Crea un componente de interfaz de usuario
  private Button submit = new Button("Enviar");

  public MainView() {

    // Agrega el componente de interfaz de usuario al componente vinculado
    self.add(submit);
  }
}
```

### Estableciendo el título del marco {#setting-the-frame-tile}

Cuando un usuario tiene múltiples pestañas en su navegador, un título de marco único ayuda a identificar rápidamente qué parte de la aplicación tiene abierta.

La anotación [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) define lo que aparece en el título del navegador o en la pestaña de la página. Para ambas vistas, agrega un título de marco usando la anotación `@FrameTitle`:

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Tabla de Clientes")
  public class MainView extends Composite<Div> {

    private Div self = getBoundComponent();

    public MainView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java title="FormView.java" {2}
  @Route("customer")
  @FrameTitle("Formulario de Clientes")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### CSS compartido {#shared-css}

Con un componente vinculado que puedes referenciar en `MainView` y `FormView`, puedes estilizarlo con CSS. 
Puedes usar el CSS del primer paso, [Creando una Aplicación Básica](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), para dar a ambas vistas estilos de contenedor de interfaz de usuario idénticos. 
Agrega el nombre de clase CSS `card` al componente vinculado en cada vista:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Tabla de Clientes")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {9} title="FormView.java"
    @Route("customer")
    @FrameTitle("Formulario de Clientes")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### Usando `CustomerService` {#using-customerservice}

La última característica compartida para las vistas es usar la clase `CustomerService`. 
La `Table` en `MainView` muestra cada cliente, mientras que `FormView` agrega nuevos clientes. Dado que ambas vistas interactúan con los datos de los clientes, necesitan acceso a la lógica de negocio de la aplicación.

Las vistas obtienen acceso a través del servicio de Spring creado en [Trabajando con Datos](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Para usar el servicio de Spring en cada vista, haz que `CustomerService` sea un parámetro del constructor:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Tabla de Clientes")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {7-8} title="FormView.java"
    @Route("customer")
    @FrameTitle("Formulario de Clientes")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

## Creando `MainView` {#creating-mainview}

Después de hacer que tu aplicación sea enrutable, dar a las vistas envoltorios de componentes `Composite` e incluir el `CustomerService`, estás listo para construir las interfaces de usuario únicas para cada vista. 
Como se mencionó anteriormente, `MainView` contiene los componentes de la interfaz de usuario que originalmente estaban en `Application`. Esta clase también necesita una forma de navegar a `FormView`.

### Agrupando los métodos de la `Table` {#grouping-the-table-methods}

Mientras mueves los componentes de `Application` a `MainView`, es una buena idea comenzar a seccionar partes de tu aplicación, para que un método personalizado pueda realizar cambios en la `Table` a la vez. Seccionar tu código ahora lo hace más manejable a medida que la aplicación crece en complejidad.

Ahora, el constructor de tu `MainView` debería llamar solo a un método `buildTable()` que agrega las columnas, establece el tamaño y referencia el repositorio:

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("Nombre");
  table.addColumn("lastName", Customer::getLastName).setLabel("Apellido");
  table.addColumn("company", Customer::getCompany).setLabel("Compañía");
  table.addColumn("country", Customer::getCountry).setLabel("País");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### Navegando a `FormView`{#navigating-to-formview}

Los usuarios necesitan una manera de navegar de `MainView` a `FormView` usando la interfaz de usuario.

En webforJ, puedes navegar a una nueva vista directamente utilizando la clase de la vista. Enrutar a través de una clase en lugar de un segmento de URL garantiza que webforJ tomará el camino correcto para cargar la vista.

Para navegar a una vista diferente, usa la clase [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) para obtener la ubicación actual con `getCurrent()`, luego usa el método `navigate()` con la clase de la vista como parámetro:

```java
Router.getCurrent().navigate(FormView.class);
```

Este código enviará programáticamente a los usuarios al formulario de nuevos clientes, pero la navegación debe estar conectada a una acción del usuario. 
Para permitir a los usuarios agregar un nuevo cliente, puedes modificar o reemplazar el botón de información de `Application`. En lugar de abrir un diálogo de mensajes, el botón puede navegar a la clase `FormView`:

```java
private Button addCustomer = new Button("Agregar Cliente", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## `MainView` completado {#completed-mainview}

Con la navegación a `FormView` y los métodos de la tabla agrupados, aquí está lo que debería verse `MainView` antes de pasar a crear `FormView`:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
  @FrameTitle("Tabla de Clientes")
  public class MainView extends Composite<Div> {
    private final CustomerService customerService;
    private Div self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Agregar Cliente", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      self.setWidth("fit-content")
          .addClassName("card")
          .add(table, addCustomer);
    }

    private void buildTable() {
      table.setSize("1000px", "294px");
      table.setMaxWidth("90vw");
      table.addColumn("firstName", Customer::getFirstName).setLabel("Nombre");
      table.addColumn("lastName", Customer::getLastName).setLabel("Apellido");
      table.addColumn("company", Customer::getCompany).setLabel("Compañía");
      table.addColumn("country", Customer::getCountry).setLabel("País");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Creando `FormView` {#creating-formview}

`FormView` mostrará un formulario para agregar nuevos clientes. Para cada propiedad del cliente, `FormView` tendrá un componente editable para que los usuarios interactúen. Además, tendrá un botón para que los usuarios envíen los datos y un botón de cancelar para descartarlos.

### Creando una instancia de `Customer` {#creating-a-customer-instance}

Cuando un usuario edita datos para un nuevo cliente, los cambios solo deben aplicarse al repositorio cuando estén listos para enviar el formulario. Usar una instancia del objeto `Customer` es una forma conveniente de editar y mantener los nuevos datos sin modificar directamente el repositorio. Crea un nuevo `Customer` dentro de `FormView` para usar en el formulario:

```java
private Customer customer = new Customer();
```

Para hacer que la instancia de `Customer` sea editable, cada propiedad, excepto por el `id`, debe estar asociada con un componente editable. Los cambios que realice un usuario en la interfaz de usuario deben reflejarse en la instancia de `Customer`.

### Agregando componentes `TextField` {#adding-textfield-components}

Las primeras tres propiedades editables en `Customer` (`firstName`, `lastName` y `company`) son todos valores `String`, y deben representarse con un editor de texto de una sola línea. Los componentes [`TextField`](/docs/components/fields/textfield) son una excelente opción para representar estas propiedades.

Con el componente `TextField`, puedes agregar una etiqueta y un oyente de eventos que se dispara cada vez que cambia el valor del campo. Cada oyente de eventos debe actualizar la instancia de `Customer` para la propiedad correspondiente.

Agrega tres componentes `TextField` que actualicen la instancia de `Customer`:

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("Nombre", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Apellido", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Compañía", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip Convención de nombres compartidos
Nombrar los componentes igual que las propiedades que están representando para la entidad `Customer` facilita la vinculación de datos en un paso futuro, [Validando y Vinculando Datos](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Agregando un componente `ChoiceBox` {#adding-a-choicebox-component}

Usar un `TextField` para la propiedad `country` no sería ideal, porque la propiedad solo puede ser uno de cinco valores enum: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` y `USA`.

Un mejor componente para seleccionar de una lista de opciones predefinidas es el [`ChoiceBox`](/docs/components/lists/choicebox).

Cada opción para un componente `ChoiceBox` está representada como un `ListItem`. Cada `ListItem` tiene dos valores, una clave `Object` y un texto `String` para mostrar en la interfaz de usuario. Tener dos valores para cada opción te permite manejar el `Object` internamente mientras presentas simultáneamente una opción más legible para los usuarios en la interfaz de usuario.

Por ejemplo, la clave `Object` podría ser un Número Internacional Normalizado de Libros (ISBN), mientras que el texto `String` es el título del libro, que es más legible para los humanos.

```java
new ListItem(isbn, bookTitle);
```

Sin embargo, esta aplicación trata con una lista de nombres de países, no libros. Para cada `ListItem`, deseas que el `Object` sea el enum `Customer.Country`, mientras que el texto puede ser su representación de `String`.

Para agregar todas las opciones de `country` en un `ChoiceBox`, puedes usar un iterador para crear un `ListItem` para cada enum `Customer.Country`, y ponerlos en un `ArrayList<ListItem>`. Luego, puedes insertar ese `ArrayList<ListItem>` en un componente `ChoiceBox`:

```java
// Crear el componente ChoiceBox
private ChoiceBox country = new ChoiceBox("País");

// Crear un ArrayList de objetos ListItem
ArrayList<ListItem> listCountries = new ArrayList<>();

// Agregar un iterador que crea un ListItem para cada opción de Customer.Country
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Insertar el ArrayList lleno en el ChoiceBox
country.insert(listCountries);

// Hace que el primer `ListItem` sea el predeterminado cuando se carga el formulario
country.selectIndex(0);
```

Luego, cuando el usuario selecciona una opción en el `ChoiceBox`, la instancia de `Customer` debería actualizarse con la clave del elemento seleccionado, que es un valor de `Customer.Country`.

```java
private ChoiceBox country = new ChoiceBox("País",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Para mantener el código limpio, el iterador que crea el `ArrayList<ListItem>` y lo agrega al `ChoiceBox` debe estar en un método separado. 
Después de agregar un `ChoiceBox` que permite al usuario elegir la propiedad `country`, `FormView` debería verse así:

```java title="FormView.java" {9-10,15,18-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Nombre", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Apellido", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Compañía", e -> customer.setCompany(e.getValue()));

  private ChoiceBox country = new ChoiceBox("País",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
    fillCountries();
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

}
```

### Agregando componentes `Button` {#adding-button-components}

Al usar el formulario de nuevos clientes, los usuarios deberían poder guardar o descartar sus cambios. 
Crea dos componentes `Button` para implementar esta característica:

```java
private Button submit = new Button("Enviar");
private Button cancel = new Button("Cancelar");
```

Tanto el botón de enviar como el botón de cancelar deberían regresar al usuario a `MainView`. 
Esto permite al usuario ver inmediatamente los resultados de su acción, ya sea que vean un nuevo cliente en la tabla o permanezca sin cambios. 
Dado que múltiples entradas en `FormView` llevan a los usuarios a `MainView`, la navegación debe ser puesta en un método reutilizable:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Botón de Cancelar**

Descartar los cambios en el formulario no requiere ningún código adicional para el evento más allá de regresar a `MainView`. Sin embargo, dado que cancelar no es una acción primaria, establecer el tema del botón en un contorno le da más prominencia al botón de enviar. 
La sección [Temas](/docs/components/button#themes) de la página del componente `Button` enumera todos los temas disponibles.

```java
private Button cancel = new Button("Cancelar", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Botón de Enviar**

Cuando un usuario presiona el botón de enviar, los valores en la instancia de `Customer` deberían usarse para crear una nueva entrada en el repositorio.

Utilizando el `CustomerService`, puedes tomar la instancia de `Customer` para actualizar la base de datos H2. Cuando esto sucede, se asigna un `id` nuevo y único a ese `Customer`. Después de actualizar el repositorio, puedes redirigir a los usuarios a `MainView`, donde pueden ver al nuevo cliente en la tabla.

```java
private Button submit = new Button("Enviar", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Usando un `ColumnsLayout` {#using-a-columnslayout}

Al agregar los componentes `TextField`, `ChoiceBox` y `Button`, ahora tienes todas las partes interactivas del formulario. 
La última mejora a `FormView` en este paso es organizar visualmente los seis componentes.

Este formulario puede usar un [`ColumnsLayout`](/docs/components/columns-layout) para separar los componentes en dos columnas sin tener que establecer el ancho de ningún componente interactivo. 
Para crear un `ColumnsLayout`, especifica cada componente que debería estar dentro del diseño:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Para establecer el número de columnas de un `ColumnsLayout`, usa una `List` de objetos `Breakpoint`. Cada `Breakpoint` le dice al `ColumnsLayout` el ancho mínimo que debe tener para aplicar un número especificado de columnas. Al usar el `ColumnsLayout`, puedes hacer un formulario con dos columnas, pero solo si la pantalla es lo suficientemente ancha para mostrar dos columnas. En pantallas más pequeñas, los componentes se muestran en una sola columna.

La sección [Breakpoints](/docs/components/columns-layout#breakpoints) en el artículo de `ColumnsLayout` explica los breakpoints con más detalle.

Para mantener el código mantenible, establece los breakpoints en un método separado. En ese método, también puedes controlar el espaciado horizontal y vertical entre los componentes dentro del `ColumnsLayout` con el método `setSpacing()`.

```java
private void setColumnsLayout() {

  // Tener dos columnas en el ColumnsLayout si es más ancho que 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Agregar la lista de breakpoints
  layout.setBreakpoints(breakpoints);

  // Establecer el espaciado entre componentes utilizando una variable CSS de DWC
  layout.setSpacing("var(--dwc-space-l)")
}
```

Finalmente, puedes agregar el `ColumnsLayout` recién creado al componente vinculado de `FormView`, mientras también estableces el ancho máximo y agregas el nombre de clase de antes:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## `FormView` completado {#completed-formview}

Después de agregar una instancia de `Customer`, los componentes interactivos y el `ColumnsLayout`, tu `FormView` debería verse como lo siguiente:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
  @FrameTitle("Formulario de Clientes")
  public class FormView extends Composite<Div> {
    private final CustomerService customerService;
    private Customer customer = new Customer();
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("Nombre", e -> customer.setFirstName(e.getValue()));
    private TextField lastName = new TextField("Apellido", e -> customer.setLastName(e.getValue()));
    private TextField company = new TextField("Compañía", e -> customer.setCompany(e.getValue()));
    private ChoiceBox country = new ChoiceBox("País",
        e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
    private Button submit = new Button("Enviar", ButtonTheme.PRIMARY, e -> submitCustomer());
    private Button cancel = new Button("Cancelar", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
    private ColumnsLayout layout = new ColumnsLayout(
        firstName, lastName,
        company, country,
        submit, cancel);

    public FormView(CustomerService customerService) {
      this.customerService = customerService;
      fillCountries();
      setColumnsLayout();
      self.setMaxWidth(600)
          .addClassName("card")
          .add(layout);
      submit.setStyle("margin-top", "var(--dwc-space-l)");
      cancel.setStyle("margin-top", "var(--dwc-space-l)");
    }

    private void setColumnsLayout() {
      List<Breakpoint> breakpoints = List.of(
          new Breakpoint(600, 2));
      layout.setSpacing("var(--dwc-space-l)")
          .setBreakpoints(breakpoints);
    }

    private void fillCountries() {
      ArrayList<ListItem> listCountries = new ArrayList<>();
      for (Country countryItem : Customer.Country.values()) {
        listCountries.add(new ListItem(countryItem, countryItem.toString()));
      }
      country.insert(listCountries);
      country.selectIndex(0);
    }

    private void submitCustomer() {
      customerService.createCustomer(customer);
      navigateToMain();
    }

    private void navigateToMain() {
      Router.getCurrent().navigate(MainView.class);
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Siguiente paso {#next-step}

Dado que los usuarios ahora pueden agregar clientes, tu aplicación debería poder editar clientes existentes utilizando el mismo formulario. En el siguiente paso, [Observadores y Parámetros de Ruta](/docs/introduction/tutorial/observers-and-route-parameters), permitirás que el `id` del cliente sea un parámetro inicial para `FormView`, para que pueda llenar el formulario con los datos de ese cliente y permitir que los usuarios cambien las propiedades.
