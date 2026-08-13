---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: b99d289f94de614d85524e9846bdcd92
---
Tu aplicación de [Observers y Parámetros de Ruta](/docs/introduction/tutorial/observers-and-route-parameters) puede usar `FormView` para editar datos de clientes existentes. Este paso utiliza [Vinculación de Datos](/docs/data-binding/overview), que conecta componentes de la interfaz de usuario directamente al modelo de datos para la sincronización automática de valores. Esto reduce el código repetitivo en tu aplicación y permite agregar comprobaciones de validación a la entidad de Spring `Customer`, haciendo que tus usuarios proporcionen información completa y precisa al completar los formularios. Este paso cubre los siguientes conceptos:

- [Validación de Jakarta](https://beanvalidation.org)
- Uso de la clase [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html)

Completar este paso crea una versión de [5-validando-y-vinculando-datos](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Ejecución de la aplicación {#running-the-app}

A medida que desarrollas tu aplicación, puedes usar [5-validando-y-vinculando-datos](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) como referencia. Para ver la aplicación en acción:

1. Navega al directorio de nivel superior que contiene el archivo `pom.xml`, este es `5-validando-y-vinculando-datos` si estás siguiendo la versión en GitHub.

2. Usa el siguiente comando de Maven para ejecutar la aplicación de Spring Boot localmente:
    ```bash
    mvn
    ```

Ejecutar la aplicación abre automáticamente un nuevo navegador en `http://localhost:8080`.

## Definiendo reglas de validación {#defining-validation-rules}

Desarrollar una aplicación con datos editables debe incluir validación. Las comprobaciones de validación ayudan a mantener datos significativos y precisos enviados por los usuarios. Si se las deja sin chequear, esto podría generar problemas, por lo que es importante detectar los tipos de errores que los usuarios pueden cometer al completar un formulario en tiempo real.

Dado que lo que se considera válido puede diferir entre propiedades, necesitarás definir qué hace que cada propiedad sea válida e informar al usuario si hay algo que no es válido. Afortunadamente, puedes hacer esto fácilmente con [Validación de Jakarta](https://beanvalidation.org). La validación de Jakarta te permite agregar restricciones a las propiedades como anotaciones.

Este tutorial utiliza dos anotaciones de Jakarta, `@NotEmpty` y `@Pattern`. `@NotEmpty` verifica cadenas nulas y vacías, mientras que `@Pattern` verifica si la propiedad coincide con una expresión regular que estableces. Ambas anotaciones te permiten agregar un mensaje para mostrar cuando la propiedad se vuelve inválida.

Para requerir que tanto el nombre como el apellido sean obligatorios y contengan solo letras, mientras que el nombre de la empresa es opcional y permite letras, números y espacios, aplica las siguientes anotaciones a la entidad `Customer`:

<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>
{`@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // highlight-next-line
    @NotEmpty(message = "Se requiere el nombre del cliente")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Caracteres inválidos")
    private String firstName = "";

  // highlight-next-line
    @NotEmpty(message = "Se requiere el apellido del cliente")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Caracteres inválidos")
    private String lastName = "";

  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Caracteres inválidos")
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
`}
</ExpandableCode>

Consulta la [referencia de restricciones de Validación de Bean de Jakarta](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) para obtener una lista completa de validaciones, o aprende más en el [artículo de Validación de Jakarta de webforJ](/docs/data-binding/validation/jakarta-validation).

## Vinculando los campos {#binding-the-fields}

Para usar las comprobaciones de validación en `Customer` para la interfaz de usuario en `FormView`, crearás un `BindingContext` para la vinculación de datos. Antes de la vinculación de datos, cada campo en `FormView` requería un oyente de eventos para sincronizarse manualmente con la entidad de Spring `Customer`. Crear un `BindingContext` en `FormView` vincula y sincroniza automáticamente el modelo de datos `Customer` a los componentes de la interfaz de usuario.

### Creando un `BindingContext` {#creating-a-bindingcontext}

Una instancia de `BindingContext` necesita el bean de Spring con el que se sincronizan las vinculaciones. En `FormView`, declara un `BindingContext` usando la entidad `Customer`:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Luego, para vincular automáticamente los componentes de la interfaz de usuario a las propiedades del bean según sus nombres, usa `BindingContext.of()` con los siguientes parámetros:

- **`this`** : Anteriormente, declaraste `context` como el `BindingContext`. El primer parámetro establece qué objeto contiene los componentes vinculables.
- **`Customer.class`** : El segundo parámetro es la clase del bean a utilizar para la vinculación.
- **`true`** : El tercer parámetro habilita la validación de Jakarta, permitiendo que el contexto use las validaciones que configuraste para `Customer`. Hacer esto cambiará el estilo de los componentes inválidos y mostrará los mensajes establecidos.

Todo junto, se verá como la siguiente línea de código:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Haciendo el formulario receptivo {#making-the-form-responsive}

Con la vinculación de datos, tu aplicación ahora realiza automáticamente comprobaciones de validación. Al agregar un oyente de eventos a las comprobaciones, puedes evitar que los usuarios envíen un formulario inválido. Agrega lo siguiente para que el botón de envío esté activo solo cuando el formulario sea válido:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Eliminando oyentes de eventos para componentes {#removing-event-listeners-for-components}

Cada cambio en la interfaz de usuario ahora se sincroniza automáticamente con el `BindingContext`. Esto significa que ahora puedes eliminar los oyentes de eventos de cada campo:

**Antes**
```java title="FormView.java"
// Sin vinculación de datos
TextField firstName = new TextField("Nombre", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Apellido", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Empresa", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("País",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Después**
```java title="FormView.java"
// Con vinculación de datos
TextField firstName = new TextField("Nombre");
TextField lastName = new TextField("Apellido");
TextField company = new TextField("Empresa");
ChoiceBox country = new ChoiceBox("País");
```

### Vinculando por nombres de propiedad {#binding-by-property-names}

Dado que el nombre de cada componente se corresponde con el modelo de datos, webforJ aplicó [Vinculación Automática](/docs/data-binding/automatic-binding). Si los nombres no coincidían, podrías usar la anotación `@UseProperty` para mapealos.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Nombre");
```

### Leyendo datos en el método `fillForm()` {#reading-data-in-the-fillForm()-method}

Anteriormente, en el método `fillForm()`, inicializabas el valor de cada componente recuperando manualmente los datos de la copia de `Customer`. Pero ahora, como estás usando un `BindingContext`, puedes usar el método `read()`. Este método llena cada componente vinculado con la propiedad asociada de los datos en la copia de `Customer`.

En el método `fillForm()`, reemplaza los métodos `setValue()` con `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Eliminados cada setValue() para los componentes de la interfaz

    context.read(customer);
  }
```

### Agregando validación a `submitCustomer()` {#adding-validation-to-submitcustomer}

El último cambio en `FormView` para este paso será agregar una salvaguarda al método `submitCustomer()`. Antes de confirmar cambios en la base de datos H2, la aplicación realizará una validación final sobre los resultados del contexto vinculado usando el método `write()`.

El método `write()` actualiza las propiedades de un bean usando los componentes de la interfaz vinculados en el `BindingContext` y devuelve un `ValidationResult`.

Usa el método `write()` para escribir en la copia de `Customer` usando los componentes vinculados en `FormView`. Luego, si el `ValidationResult` devuelto es válido, actualiza la base de datos H2 usando los datos escritos.

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

Con estos cambios, así es como se ve `FormView`. La aplicación ahora soporta vinculación de datos y validación utilizando Spring Boot y webforJ. Las entradas de formulario se sincronizan automáticamente con el modelo y se comprueban contra las reglas de validación.

<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer/:id?<[0-9]+>")
  @FrameTitle("Formulario del Cliente")
  public class FormView extends Composite<Div> implements WillEnterObserver {
    private final CustomerService customerService;
    private BindingContext<Customer> context;
    private Customer customer = new Customer();
    private Long customerId = 0L;
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("Nombre");
    private TextField lastName = new TextField("Apellido");
    private TextField company = new TextField("Empresa");
    private ChoiceBox country = new ChoiceBox("País");
    private Button submit = new Button("Enviar", ButtonTheme.PRIMARY, e -> submitCustomer());
    private Button cancel = new Button("Cancelar", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
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
`}
</ExpandableCode>

## Próximo paso {#next-step}

El siguiente paso, [Integrando un Diseño de Aplicación](/docs/introduction/tutorial/integrating-an-app-layout), se centra en usar un `AppLayout` para agregar un menú lateral que esté disponible para los usuarios tanto en la tabla de clientes como en las páginas del formulario del cliente. También aprenderás sobre otra herramienta de diseño, el componente `FlexLayout`.
