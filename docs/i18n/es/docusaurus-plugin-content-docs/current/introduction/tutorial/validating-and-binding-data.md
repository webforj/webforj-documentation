---
title: Validación y vinculación de datos
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 97f423188eb353ac27b8e9179e93b224
---
Tu aplicación de [Observadores y parámetros de ruta](/docs/introduction/tutorial/observers-and-route-parameters) puede usar `FormView` para editar los datos de clientes existentes. Este paso usa [Vinculación de datos](/docs/data-binding/overview), que conecta directamente los componentes de la interfaz de usuario con el modelo de datos para sincronizar los valores automáticamente. Esto reduce el código repetitivo de la aplicación y permite añadir comprobaciones de validación a la entidad de Spring `Customer`, para que los usuarios proporcionen información completa y precisa al rellenar los formularios. En este paso se abordan los siguientes conceptos:

- [Validación de Jakarta](https://beanvalidation.org)
- Uso de la clase [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html)

Al completar este paso, se crea una versión de [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

<!-- <VideoPlayer video="tutorial/validating-and-binding-data" width={1920} height={1080} title="Validating and binding data" /> -->

## Ejecutar la aplicación {#running-the-app}

Mientras desarrollas la aplicación, puedes usar [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) como referencia. Para ver la aplicación en acción:

1. Ve al directorio de nivel superior que contiene el archivo `pom.xml`; si sigues la versión de GitHub, se trata de `5-validating-and-binding-data`.

2. Usa el siguiente comando de Maven para ejecutar la aplicación de Spring Boot localmente:
    ```bash
    mvn
    ```

Al ejecutar la aplicación, se abre automáticamente una nueva ventana del navegador en `http://localhost:8080`.

## Definir reglas de validación {#defining-validation-rules}

El desarrollo de una aplicación con datos editables debe incluir validación. Las comprobaciones de validación ayudan a mantener la información enviada por los usuarios significativa y precisa. Si no se validan los datos, pueden surgir problemas, por lo que es importante detectar en tiempo real los errores que los usuarios pueden cometer al rellenar un formulario.

Como lo que se considera válido puede variar según la propiedad, tendrás que definir qué hace que cada propiedad sea válida e informar al usuario si algo no lo es. Por suerte, puedes hacerlo fácilmente con [Jakarta Validation](https://beanvalidation.org). Jakarta Validation permite añadir restricciones a las propiedades mediante anotaciones.

En este tutorial se usan dos anotaciones de Jakarta: `@NotEmpty` y `@Pattern`. `@NotEmpty` comprueba si hay valores nulos o cadenas vacías, mientras que `@Pattern` comprueba si la propiedad coincide con una expresión regular que hayas definido. Ambas anotaciones permiten añadir un mensaje para mostrar cuando la propiedad no sea válida.

Para exigir que el nombre y el apellido sean obligatorios y contengan solo letras, y que el nombre de la empresa sea opcional y permita letras, números y espacios, aplica las siguientes anotaciones a la entidad `Customer`:

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

// highlight-next-line
  @NotEmpty(message = "Customer first name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";

// highlight-next-line
  @NotEmpty(message = "Customer last name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String lastName = "";

// highlight-next-line
  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
  private String company = "";

  private Country country = Country.UNKNOWN;

  public enum Country {
    UNKNOWN,
    GERMANY,
    ENGLAND,
    ITALY,
    USA
  }

  public Customer(String firstName, String lastName, String company, Country country) {
    setFirstName(firstName);
    setLastName(lastName);
    setCompany(company);
    setCountry(country);
  }

  public Customer(String firstName, String lastName, String company) {
    this(firstName, lastName, company, Country.UNKNOWN);
  }

  public Customer(String firstName, String lastName) {
    this(firstName, lastName, "");
  }

  public Customer(String firstName) {
    this(firstName, "");
  }

  public Customer() {
  }

  public void setFirstName(String newName) {
    firstName = newName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String newName) {
    lastName = newName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setCompany(String newCompany) {
    company = newCompany;
  }

  public String getCompany() {
    return company;
  }

  public void setCountry(Country newCountry) {
    country = newCountry;
  }

  public Country getCountry() {
    return country;
  }

  public Long getId() {
    return id;
  }

}
```

</ExpandableCode>
<!-- vale on -->

Consulta la [referencia de restricciones de Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) para ver la lista completa de validaciones, o descubre más en el [artículo de webforJ sobre Jakarta Validation](/docs/data-binding/validation/jakarta-validation).

## Vincular los campos {#binding-the-fields}

Para usar en la interfaz de `FormView` las comprobaciones de validación de `Customer`, crearás un `BindingContext` para la vinculación de datos. Antes de vincular los datos, cada campo de `FormView` necesitaba un listener de eventos para sincronizar manualmente los datos con la entidad de Spring `Customer`. Al crear un `BindingContext` en `FormView`, se vinculan y sincronizan automáticamente los datos del modelo `Customer` con los componentes de la interfaz de usuario.

### Crear un `BindingContext` {#creating-a-bindingcontext}

Una instancia de `BindingContext` necesita el bean de Spring con el que se sincronizan las vinculaciones. En `FormView`, declara un `BindingContext` que use la entidad `Customer`:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Después, para vincular automáticamente los componentes de la interfaz de usuario a las propiedades del bean según sus nombres, usa `BindingContext.of()` con los siguientes parámetros:

- **`this`**: anteriormente, declaraste `context` como el `BindingContext`. El primer parámetro establece qué objeto contiene los componentes que se pueden vincular.
- **`Customer.class`**: el segundo parámetro es la clase del bean que se usará para la vinculación.
- **`true`**: el tercer parámetro habilita Jakarta Validation, lo que permite que el contexto use las validaciones que definiste para `Customer`. Esto cambiará el estilo de los componentes no válidos y mostrará los mensajes configurados.

En conjunto, la línea de código será la siguiente:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Hacer que el formulario sea adaptable {#making-the-form-responsive}

Con la vinculación de datos, la aplicación ahora realiza automáticamente las comprobaciones de validación. Si añades un listener de eventos a las comprobaciones, puedes impedir que los usuarios envíen un formulario no válido. Añade lo siguiente para que el botón de envío solo esté activo cuando el formulario sea válido:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Eliminar los listeners de eventos de los componentes {#removing-event-listeners-for-components}

Ahora, todos los cambios en la interfaz de usuario se sincronizan automáticamente con `BindingContext`. Esto significa que puedes eliminar los listeners de eventos de cada campo:

**Antes**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Después**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### Vincular por nombres de propiedades {#binding-by-property-names}

Como el nombre de cada componente coincidía con el del modelo de datos, webforJ aplicó la [vinculación automática](/docs/data-binding/automatic-binding). Si los nombres no coincidieran, podrías usar la anotación `@UseProperty` para asignarlos.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### Leer datos en el método `fillForm()` {#reading-data-in-the-fillForm()-method}

Antes, en el método `fillForm()`, inicializabas el valor de cada componente recuperando manualmente los datos de la copia de `Customer`. Pero ahora, como usas un `BindingContext`, puedes usar el método `read()`. Este método rellena cada componente vinculado con la propiedad correspondiente de los datos de la copia de `Customer`.

En el método `fillForm()`, reemplaza los métodos `setValue()` por `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Removed each setValue() method for the UI components

    context.read(customer);
  }
```

### Añadir validación a `submitCustomer()` {#adding-validation-to-submitcustomer}

El último cambio de este paso en `FormView` consiste en añadir una protección al método `submitCustomer()`. Antes de confirmar los cambios en la base de datos H2, la aplicación realizará una validación final de los resultados del contexto vinculado mediante el método `write()`.

El método `write()` actualiza las propiedades de un bean usando los componentes de la interfaz de usuario vinculados en `BindingContext` y devuelve un `ValidationResult`.

Usa el método `write()` para escribir en la copia de `Customer` mediante los componentes vinculados en `FormView`. Después, si el `ValidationResult` devuelto es válido, actualiza la base de datos H2 con los datos escritos.


```java title="FormView.java" {2-3}
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToMain();
  }
}
```

### `FormView` completado {#completed-formview}

Con estos cambios, así queda `FormView`. La aplicación ahora admite la vinculación de datos y la validación mediante Spring Boot y webforJ. Las entradas del formulario se sincronizan automáticamente con el modelo y se comprueban según las reglas de validación.

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField company = new TextField("Company");
  private ChoiceBox country = new ChoiceBox("Country");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
  private ColumnsLayout layout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));
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
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
      if (customerService.doesCustomerExist(customerId)) {
        customerService.updateCustomer(customer);
      } else {
        customerService.createCustomer(customer);
      }
      navigateToMain();
    }
  }

  private void navigateToMain() {
    Router.getCurrent().navigate(MainView.class);
  }

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(id -> {
      customerId = Long.valueOf(id);
      if (customerService.doesCustomerExist(customerId)) {
        event.accept();
        fillForm(customerId);
      } else {
        event.reject();
        navigateToMain();
      }

    }, () -> event.accept());
  }

  public void fillForm(Long customerId) {
    customer = customerService.getCustomerByKey(customerId);
    context.read(customer);
  }
}
```

</ExpandableCode>
<!-- vale on -->

## Siguiente paso {#next-step}

El siguiente paso, [Integrar un diseño de aplicación](/docs/introduction/tutorial/integrating-an-app-layout), se centra en usar `AppLayout` para añadir un menú lateral disponible para los usuarios tanto en la tabla de clientes como en las páginas de formularios de clientes. También aprenderás a usar otra herramienta de diseño: el componente `FlexLayout`.
