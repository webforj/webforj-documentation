---
title: Escalado con enrutamiento y compuestos
sidebar_position: 4
_i18n_hash: 50cd3b00cb1fb7731b6328708d6d45ba
---
Este paso se centra en implementar enrutamiento para mejorar la escalabilidad y organización de la estructura de la aplicación. Para lograr esto, la aplicación se actualizará para manejar múltiples vistas, permitiendo la navegación entre diferentes funcionalidades, como editar y crear entradas de clientes. Se describirá la creación de vistas para estas funcionalidades, utilizando componentes como `Composite` para construir diseños modulares y reutilizables.

La aplicación creada en el [paso anterior](./working-with-data) tendrá una configuración de enrutamiento que soporta múltiples vistas, permitiendo a los usuarios gestionar los datos de los clientes de manera más efectiva mientras se mantiene una base de código limpia y escalable. Para ejecutar la aplicación:

- Ve al directorio `3-scaling-with-routing-and-composites`
- Ejecuta el comando `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Enrutamiento {#routing}

[El enrutamiento](../../routing/overview) es el mecanismo que permite a tu aplicación gestionar la navegación entre diferentes vistas o páginas. En lugar de mantener toda la lógica y comportamiento en una sola ubicación, el enrutamiento te permite descomponer tu aplicación en componentes más pequeños y enfocados.

En su núcleo, el enrutamiento conecta URLs específicas a las vistas o componentes que manejan esas URLs. Cuando un usuario interactúa con tu aplicación —como hacer clic en un botón o introducir una URL directamente en su navegador— el enrutador resuelve la URL a la vista apropiada, la inicializa y la muestra en la pantalla. Este enfoque facilita la gestión de la navegación y el mantenimiento del estado de la aplicación.

Este paso se centra en cambiar la clase `App`, crear archivos para las vistas y configurar rutas para permitir una navegación fluida entre las diferentes partes de tu aplicación.

En lugar de colocar toda la lógica dentro del método `run()` de `App`, las vistas como `DemoView` y `FormView` se implementan como clases separadas. Este enfoque se alinea más estrechamente con las prácticas estándar de Java.

- **DemoView**: Se encarga de mostrar la tabla y navegar a `FormView`.
- **FormView**: Gestiona la adición y edición de datos de clientes.

### Cambiando la clase `App` {#changing-the-app-class}

Para habilitar el enrutamiento en tu aplicación, actualiza la clase `App` con la anotación `@Routify`. Esto le indica a webforJ que active el enrutamiento y escanee los paquetes especificados en busca de vistas habilitadas para rutas.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Especifica qué paquetes se escanean en busca de vistas que definen rutas.
- **`debug`**: Habilita el modo de depuración para facilitar la solución de problemas durante el desarrollo.

### Creando archivos para las vistas y configurando rutas {#creating-files-for-the-views-and-configuring-routes}

Una vez que se ha habilitado el enrutamiento, se crean archivos Java separados para cada vista que contendrá la aplicación, en este caso, `DemoView.java` y `FormView.java`. Se asignan rutas únicas a estas vistas utilizando la anotación `@Route`. Esto asegura que cada vista sea accesible a través de una URL específica.

Cuando la anotación `@Route` asociada con una clase con uno de estos sufijos no tiene valor, webforJ asigna automáticamente el nombre de la clase sin el sufijo como la ruta. Por ejemplo, `DemoView` mapeará por defecto la ruta `/demo`. Dado que en este caso `DemoView` se supone que debe ser la ruta predeterminada, le asignarás una ruta.

La ruta `/` sirve como el punto de entrada predeterminado para tu aplicación. Asignar esta ruta a una vista asegura que sea la primera página que los usuarios ven al acceder a la aplicación. En la mayoría de los casos, se asigna un tablero o vista de resumen a `/`.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // Lógica de DemoView
}
```

:::info 
Más información sobre los diferentes tipos de rutas está disponible [aquí](../../routing/defining-routes).
:::

Para `FormView`, la ruta `customer/:id?` utiliza un parámetro opcional `id` para determinar el modo de `FormView`. 

- **Modo de Agregar**: Cuando no se proporciona `id`, `FormView` se inicializa con un formulario en blanco para agregar nuevos datos de clientes.
- **Modo de Edición**: Cuando se proporciona `id`, `FormView` obtiene los datos del cliente correspondiente utilizando `Service` y pre-llena el formulario, permitiendo realizar ediciones en la entrada existente.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Formulario de Cliente")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // Lógica de FormView
}
```

:::info 
Más información sobre las diferentes formas de implementar esos patrones de ruta está disponible [aquí](../../routing/route-patterns).
:::

## Usando componentes `Composite` para mostrar páginas {#using-composite-components-to-display-pages}

Los componentes `Composite` en webforJ, como `Composite<Div>`, te permiten encapsular la lógica y estructura de la UI dentro de un contenedor reutilizable. Al extender `Composite`, limitas los métodos y datos expuestos al resto de la aplicación, asegurando un código más limpio y mejor encapsulamiento.

Por ejemplo, `DemoView` extiende `Composite<Div>` en lugar de extender directamente `Div`:

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Agregar Cliente", ButtonTheme.PRIMARY);  

  public DemoView() {
    setupLayout();
  }

  private void setupLayout() {
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    getBoundComponent().add(layout);
  }
}
```

## Conectando las rutas {#connecting-the-routes}

Después de configurar el enrutamiento y establecer vistas, conecta las vistas y los datos utilizando oyentes de eventos y métodos de servicio. El primer paso es agregar uno o más elementos de UI para navegar de una vista a otra.

### Navegación con botón {#button-navigation}

El componente `Button` activa un evento de navegación para realizar la transición de una vista a otra utilizando la clase `Router`. Por ejemplo:

```java title="DemoView.java"
private Button add = new Button("Agregar Cliente", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
La clase Router utiliza la clase dada para resolver la ruta y construir una URL para navegar. Todo el manejo de la navegación del navegador se gestiona para que la gestión del historial y la inicialización de vistas no sean de preocupación.
Para más detalles sobre la navegación, consulta el [Artículo de Navegación de Rutas](../../routing/route-navigation).
:::

### Edición de tabla {#table-editing}

Además de la navegación mediante clic en botón, muchas aplicaciones también permiten navegar a otras partes de una aplicación cuando una `Table` es doble clicada. Se realizan los siguientes cambios para permitir a los usuarios hacer doble clic en un elemento de la tabla para navegar a un formulario pre-llenado con los detalles del elemento.

Una vez que se han editado los detalles en la pantalla apropiada, los cambios se guardan y la `Table` se actualiza para mostrar los datos cambiados del elemento seleccionado.

Para facilitar esta navegación, los clics en los elementos de la tabla son gestionados por el oyente `TableItemClickEvent<Customer>`. El evento contiene el `id` del cliente clicado, que se pasa a `FormView` utilizando el método `navigate()` con un `ParametersBag`:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Manejo de la inicialización con `onDidEnter` {#handling-initialization-with-ondidenter}

El método `onDidEnter` en webforJ es parte del ciclo de vida del enrutamiento y se activa cuando una vista se vuelve activa.

Cuando el `Router` navega a una vista, se activa `onDidEnter` como parte del ciclo de vida para:
- **Cargar Datos**: Inicializar o buscar datos requeridos para la vista basándose en los parámetros de la ruta.
- **Configurar la Vista**: Actualizar dinámicamente los elementos de la UI según el contexto.
- **Reaccionar a Cambios de Estado**: Realizar acciones que dependen de que la vista esté activa, como restablecer formularios o resaltar componentes.

El método `onDidEnter` en `FormView` verifica la presencia de un parámetro `id` en la ruta y ajusta el comportamiento del formulario en consecuencia:

- **Modo de Edición**: Si se proporciona un `id`, el método obtiene los datos del cliente correspondiente utilizando `Service` y pre-llena los campos del formulario. El botón `Enviar` está configurado para actualizar la entrada existente.
- **Modo de Agregar**: Si no se presenta un `id`, el formulario permanece en blanco, y el botón `Enviar` está configurado para crear un nuevo cliente.

```java
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }
```

### Enviando datos {#submitting-data}

Cuando se finaliza la edición de los datos, es necesario enviarlos al servicio que maneja el repositorio. Por lo tanto, la clase `Service` que ya se ha configurado en el paso anterior de este tutorial ahora necesita ser mejorada con métodos adicionales, que permitan a los usuarios agregar y editar clientes.

El fragmento a continuación muestra cómo lograr esto:

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### Usando `commit()` {#using-commit}

El método `commit()` en la clase `Repository` mantiene los datos y la UI de la aplicación sincronizados. Proporciona un mecanismo para actualizar los datos almacenados en el `Repository`, asegurando que el estado más reciente se refleje en la aplicación.

Este método se puede utilizar de dos maneras:

1) **Refrescando todos los datos:**
  Llamar a `commit()` sin argumentos recarga todas las entidades desde la fuente de datos subyacente del repositorio, como una base de datos o una clase de servicio.

2) **Refrescando una sola entidad:**
  Llamar a `commit(T entity)` recarga una entidad específica, asegurando que su estado coincida con los cambios más recientes en la fuente de datos.

Llama a `commit()` cuando los datos en el `Repository` cambian, como después de agregar o modificar entidades en la fuente de datos.

```java
// Refrescar todos los datos de cliente en el repositorio
customerRepository.commit();

// Refrescar una sola entidad de cliente
Customer updatedCustomer = ...; // Actualizado desde una fuente externa
customerRepository.commit(updatedCustomer);
```

Con estos cambios, se han logrado los siguientes objetivos:

  1. Se ha implementado el enrutamiento y se ha configurado de tal manera que las vistas futuras puedan integrarse con poco esfuerzo.
  2. Se han retirado las implementaciones de UI de `App` y se han colocado en una vista separada.
  3. Se ha añadido una vista adicional para manipular los datos que se muestran en la tabla de clientes.

Con la modificación de los detalles del cliente y el enrutamiento completados, el siguiente paso se centrará en implementar la vinculación de datos y usarla para facilitar la validación.
