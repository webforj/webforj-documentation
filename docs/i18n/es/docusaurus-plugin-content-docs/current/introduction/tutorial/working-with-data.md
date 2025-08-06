---
title: Working With Data
sidebar_position: 3
_i18n_hash: 3afbf6e4eb4921183cc11d87c8457150
---
Este paso se centra en agregar capacidades de gestión y visualización de datos a la aplicación de demostración. Para hacerlo, se creará datos ficticios sobre varios objetos `Customer`, y la aplicación se actualizará para manejar estos datos y mostrarlos en una [`Table`](../../components/table/overview) añadida a la aplicación anterior.

Se describirá la creación de una clase modelo `Customer` e integrarla con una clase `Service` para acceder y gestionar los datos necesarios utilizando la implementación de un repositorio. Luego, se detallará cómo utilizar los datos recuperados para implementar un componente `Table` en la aplicación, mostrando la información del cliente de forma interactiva y estructurada.

Al final de este paso, la aplicación creada en el [paso anterior](./creating-a-basic-app) mostrará una tabla con los datos creados que luego se podrá ampliar en los pasos siguientes. Para ejecutar la aplicación:

- Ve al directorio `2-working-with-data`
- Ejecuta `mvn jetty:run`

## Creando un modelo de datos {#creating-a-data-model}

Para crear una `Table` que muestre datos en la aplicación principal, es necesario crear una clase bean de Java que se pueda usar con la `Table` para mostrar datos.

En este programa, la clase `Customer` en `src/main/java/com/webforj/demos/data/Customer.java` hace esto. Esta clase sirve como el modelo de datos central para la aplicación, encapsulando atributos relacionados con el cliente como `firstName`, `lastName`, `company` y `country`. Este modelo también contendrá un ID único.

```java title="Customer.java"
public class Customer implements HasEntityKey {
  private String firstName = "";
  private String lastName = "";
  private String company = "";
  private Country country = Country.UNKNOWN;
  private UUID uuid = UUID.randomUUID();

  public enum Country {

    @SerializedName("Germany")
    GERMANY,

    // Países restantes
  }

    // Getters y Setters

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info Uso de `HasEntityKey` para Identificadores Únicos

Implementar la interfaz `HasEntityKey` es crucial para gestionar identificadores únicos en modelos utilizados con una `Table`. Asegura que cada instancia del modelo tenga una clave única, permitiendo que la `Table` identifique y gestione filas de manera efectiva.

Para esta demostración, el método `getEntityKey()` devuelve un UUID para cada cliente, asegurando una identificación única. Si bien se utilizan UUIDs aquí por simplicidad, en aplicaciones del mundo real, una clave primaria de base de datos suele ser una mejor opción para generar claves únicas.

Si no se implementa `HasEntityKey`, la `Table` utilizará de manera predeterminada el código hash de Java como clave. Dado que los códigos hash no garantizan ser únicos, esto puede causar conflictos al gestionar filas en la `Table`.
:::

Con el modelo de datos `Customer` en su lugar, el siguiente paso es gestionar y organizar estos modelos dentro de la aplicación.

## Creando una clase `Service` {#creating-a-service-class}

Actuando como un gestor de datos centralizado, la clase `Service` no solo carga datos de `Customer` sino que también proporciona una interfaz eficiente para acceder e interactuar con ellos.

La clase `Service.java` se crea en `src/main/java/com/webforj/demos/data`. En lugar de pasar datos manualmente entre componentes o clases, el `Service` actúa como un recurso compartido, permitiendo a las partes interesadas recuperar e interactuar con los datos fácilmente.

En esta demostración, la clase `Service` lee datos de cliente de un archivo JSON ubicado en `src/main/resources/data/customers.json`. Los datos se asignan a objetos `Customer` y se almacenan en un `ArrayList`, que forma la base para el `Repository` de la tabla.

En webforJ, la clase `Repository` proporciona una forma estructurada de gestionar y recuperar colecciones de entidades. Actúa como una interfaz entre su aplicación y sus datos, ofreciendo métodos para consultar, contar y actualizar datos mientras se mantiene una estructura limpia y consistente. Es utilizada por la clase `Table` para mostrar los datos almacenados en ella.

Aunque el `Repository` no incluye métodos para actualizar o eliminar entidades, sirve como un envoltorio estructurado alrededor de una colección de objetos. Esto lo hace ideal para proporcionar un acceso organizado y eficiente a los datos.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // Implementación restante
}
```

Para poblar el `Repository` con datos, la clase `Service` actúa como el gestor central, manejando la carga y organización de activos en la aplicación. Los datos de clientes se leen de un archivo JSON y se asignan a los objetos `Customer` en el `Repository`.

La utilidad `Assets` en webforJ facilita la carga dinámica de estos datos utilizando URLs de contexto. Para cargar activos y datos en webforJ, la clase `Service` utiliza URLs de contexto con la utilidad `Assets`. Por ejemplo, los datos de clientes se pueden cargar desde el archivo JSON de la siguiente manera:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Uso de la `ObjectTable`
La clase `Service` utiliza la `ObjectTable` para gestionar instancias dinámicamente, en lugar de depender de campos estáticos. Este enfoque aborda una limitación clave al utilizar servlets: los campos estáticos están atados al ciclo de vida del servidor y pueden provocar problemas en entornos con múltiples solicitudes o sesiones concurrentes. La `ObjectTable` está limitada a la sesión del usuario, y su uso garantiza un comportamiento similar a un singleton sin estas limitaciones, permitiendo una gestión de datos consistente y escalable.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Constructor privado para forzar una instanciación controlada
  private Service() {
    // implementación
  }

  // Recupera la instancia actual de Service o crea una si no existe
  public static Service getCurrent() {
    // implementación
  }

  // Carga datos de clientes desde el archivo JSON y los asigna a objetos Customer
  private List<Customer> buildDemoList() {
    // implementación
  }

  // Getter...
}
```

## Creando y usando una `Table` {#creating-and-using-a-table}

Ahora que los datos necesarios se han creado correctamente a través de la clase `Customer`, y se devuelven como un `Repository` a través de la clase `Service`, la tarea final en este paso es integrar el componente `Table` en la aplicación para mostrar los datos del cliente.

:::tip Más sobre la `Table`
Para obtener una visión más detallada de las diversas características y comportamientos de la `Table`, consulta [este artículo](../../components/table/overview).
:::

La `Table` proporciona una forma dinámica y flexible de mostrar datos estructurados en tu aplicación. Está diseñada para integrarse con la clase `Repository`, habilitando características como consultas de datos, paginación y actualizaciones eficientes. Una `Table` es altamente configurable, permitiéndote definir columnas, controlar su apariencia y vincularla a repositorios de datos con un esfuerzo mínimo.

### Implementando la `Table` en la aplicación {#implementing-the-table-in-the-app}

Dado que los datos para la `Table` se gestionan completamente a través de la clase `Service`, la tarea principal en `DemoApplication.java` es configurar la `Table` y vincularla al `Repository` proporcionado por el `Service`.

Para configurar la `Table`:

- Establece su ancho y alto para fines de diseño utilizando los métodos `setHeight()` y `setWidth()`.
- Define las columnas, especificando sus nombres y los métodos para recuperar los datos de cada una.
- Asigna el `Repository` para proporcionar datos dinámicamente.

Después de hacer esto, el código se verá similar al siguiente fragmento:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Otros componentes del paso uno

  // El componente Table para mostrar datos de Customer
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // Implementación anterior del paso uno
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Establece la altura de la tabla a 300 píxeles
    table.setHeight("300px");
    // Establece el ancho de la tabla a 1000 píxeles
    table.setWidth(1000);

    // Agrega los diversos títulos de columnas y asigna los getters apropiados
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // Vincula la Table a un Repository que contiene datos de Customer
    // El Repository se recupera a través de la clase Service
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Con los cambios completados en la aplicación implementados, los siguientes pasos sucederán cuando se ejecute la aplicación:

1. La clase `Service` recupera datos de `Customer` del archivo JSON y los almacena en un `Repository`.
2. La `Table` integra el `Repository` para datos y llena dinámicamente sus filas.

Con la `Table` ahora mostrando datos de `Customer`, el siguiente paso se centrará en crear una nueva pantalla para modificar los detalles del cliente e integrar el enrutamiento en la aplicación.

Esto permitirá organizar la lógica de la aplicación de manera más efectiva al trasladarla fuera de la clase principal `App`, y dentro de pantallas constituyentes accesibles a través de rutas.
