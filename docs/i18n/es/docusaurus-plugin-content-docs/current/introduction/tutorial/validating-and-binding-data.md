---
title: Validación y Vinculación de Datos
sidebar_position: 5
pagination_next: null
_i18n_hash: 11d03e09c4c37172713713649c920e9e
---
El enlace de datos es un mecanismo que conecta los componentes de la interfaz de usuario de tu aplicación directamente con el modelo de datos subyacente, lo que permite la sincronización automática de valores entre ambos. Esto elimina la necesidad de llamadas repetitivas de getter y setter, reduciendo el tiempo de desarrollo y mejorando la confiabilidad del código.

La validación, en este contexto, asegura que los datos ingresados en el formulario cumplan con reglas predefinidas, como no estar vacíos o seguir un formato específico. Al combinar el enlace de datos con la validación, puedes optimizar la experiencia del usuario mientras mantienes la integridad de los datos sin escribir extensas verificaciones manuales.

Para más información sobre el enlace de datos, consulta [este artículo.](../../data-binding/overview) Para ejecutar la aplicación:

- Ve al directorio `4-validating-and-binding-data`
- Ejecuta el comando `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Vinculando los campos {#binding-the-fields}

La configuración del enlace de datos comienza con la inicialización de un `BindingContext` para el modelo `Customer`. El `BindingContext` vincula las propiedades del modelo a los campos del formulario, permitiendo la sincronización automática de datos. Esto se configura en el constructor de `FormView`.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` inicializa el contexto de enlace para la clase `Customer`. El tercer parámetro, `true`, habilita [jakarta validation](https://beanvalidation.org/).

:::info
Esta implementación utiliza auto-vinculación como se describe en el [Artículo de Enlace de Datos](../../data-binding/automatic-binding). Esto funciona si los campos en el modelo de datos `Customer` tienen el mismo nombre que los campos correspondientes en el `FormView`.

Si los campos no tienen el mismo nombre, puedes agregar la anotación `UseProperty` en el formulario sobre el campo que deseas vincular para que sepan a qué campos de datos referirse.
:::

### Enlace de datos con `onDidEnter()` {#data-binding-with-ondidenter}

El método `onDidEnter` aprovecha la configuración del enlace de datos para optimizar el proceso de llenado de los campos del formulario. En lugar de establecer manualmente valores para cada campo, los datos ahora se sincronizan automáticamente con el `BindingContext`.

```java {7}
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
```

El método `context.read` en el sistema de enlace de datos de webforJ sincroniza los campos de un componente de interfaz de usuario con los valores de un modelo de datos. Se utiliza en este caso para llenar los campos del formulario con datos de un modelo existente, asegurando que la interfaz refleje el estado actual de los datos.

## Validando datos {#validating-data}

La validación asegura que los datos ingresados en el formulario cumplan con reglas específicas, mejorando la calidad de los datos y previniendo envíos inválidos. Con el enlace de datos, la validación ya no necesita implementarse manualmente, sino que simplemente se configura, permitiendo retroalimentación en tiempo real sobre las entradas del usuario.

### Definiendo reglas de validación {#defining-validation-rules}

Usando [Jakarta](https://beanvalidation.org) y expresiones regulares, puedes hacer cumplir una multitud de reglas en un campo. Ejemplos comunes serían asegurarte de que el campo no esté vacío o nulo, o siga un cierto patrón. A través de anotaciones en la clase customer puedes otorgar parámetros de validación jakarta al campo.

:::info
Más detalles sobre la configuración de la validación están disponibles [aquí](../../data-binding/validation/jakarta-validation.md#installation).
:::

```java
  @NotEmpty(message = "El nombre no puede estar vacío")
  @Pattern(regexp = "[a-zA-Z]*", message = "Caracteres inválidos")
  private String firstName = "";
```

El método `onValidate` se agrega para controlar el estado del botón `Submit` basado en la validez de los campos del formulario. Esto asegura que solo se puedan enviar datos válidos.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` devuelve verdadero si todos los campos son válidos, y falso si no lo son. Esto significa que el botón `Submit` está habilitado mientras todos los campos sean válidos. De lo contrario, permanece desactivado, impidiendo el envío hasta que se realicen las correcciones necesarias.

### Agregando y editando entradas con validación {#adding-and-editing-entries-with-validation}

El método `submitCustomer()` ahora valida los datos utilizando el `BindingContext` antes de realizar operaciones de adición o edición. Este enfoque elimina la necesidad de verificaciones de validación manuales, aprovechando los mecanismos integrados del contexto para asegurar que solo se procesen datos válidos.

- **Modo Agregar**: Si no se proporciona un `id`, el formulario está en modo de agregar. Los datos validados se escriben en el modelo `Customer` y se agregan al repositorio a través de `Service.getCurrent().addCustomer(customer)`.
- **Modo Editar**: Si se presenta un `id`, el método recupera los datos del cliente correspondiente, los actualiza con las entradas validadas y confirma los cambios en el repositorio.

Llamar a `context.write(customer)` retornará una instancia de `ValidationResult`. Esta clase indica si la validación fue exitosa o no, y almacena cualquier mensaje asociado con este resultado.

Este código asegura que todos los cambios sean validados y aplicados automáticamente al modelo antes de agregar un nuevo cliente o editar uno existente.

```java title="FormView.java"
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerId.isEmpty()) {
      Service.getCurrent().addCustomer(customer);
    }
    Router.getCurrent().navigate(DemoView.class);
  }
}
```

Al completar este paso, la aplicación ahora soporta el enlace de datos y la validación, asegurando que las entradas del formulario estén sincronizadas con el modelo y cumplan con las reglas predefinidas.
