---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: c59ff0def84230ed79877cba3d5e5aa4
---
Este primer paso sienta las bases para la aplicación de gestión de clientes al crear una interfaz simple e interactiva. Esto demuestra cómo configurar una aplicación básica de webforJ, con un solo botón que abre un diálogo al hacer clic. Es una implementación sencilla que presenta componentes clave y te da una idea de cómo funciona webforJ.

Este paso aprovecha la clase base de la aplicación proporcionada por webforJ para definir la estructura y el comportamiento de la aplicación. Seguir a los pasos posteriores llevará a una configuración más avanzada utilizando el enrutamiento para gestionar múltiples pantallas, que se introducirá en [Escalando con Enrutamiento y Compuestos](./scaling-with-routing-and-composites).

Al final de este paso, tendrás una aplicación funcional que demuestra la interacción básica con componentes y la gestión de eventos en webforJ. Para ejecutar la aplicación:

- Ve al directorio `1-creating-a-basic-app`
- Ejecuta el comando `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Creando una aplicación webforJ {#creating-a-webforj-app}

En webforJ, una `App` representa el centro para definir y gestionar tu proyecto. Cada aplicación webforJ comienza creando una clase que extiende la clase fundamental `App`, que sirve como el marco central para:

- Gestionar el ciclo de vida de la aplicación, incluyendo inicialización y terminación.
- Manejar el enrutamiento y la navegación si está habilitado.
- Definir el tema, la configuración regional y otras configuraciones generales de la aplicación.
- Proporcionar utilidades esenciales para interactuar con el entorno y los componentes.

### Extendiendo la clase `App` {#extending-the-app-class}

Para este paso, se crea una clase llamada `DemoApplication.java`, que extiende la clase `App`.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // La lógica central de la aplicación irá aquí
  }
}
```

:::tip Propiedades de configuración clave

En esta aplicación de demostración, el archivo `webforj.conf` se configura con las siguientes dos propiedades esenciales:

- **`webforj.entry`**: Especifica el nombre completamente calificado de la clase que extiende `App` y que actúa como el punto de entrada principal para tu proyecto. Para este tutorial, configúralo como `com.webforj.demos.DemoApplication` para evitar ambigüedades durante la inicialización.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Habilita el modo de depuración para registros detallados y visibilidad de errores durante el desarrollo. Asegúrate de que esto esté configurado como `true` mientras trabajas en este tutorial:
  ```hocon
  webforj.debug = true
  ```

Para más detalles sobre opciones de configuración adicionales, consulta la [Guía de Configuración](../../configuration/overview).
:::

### Sobrescribiendo el método `run()` {#overriding-the-run-method}

Después de asegurar la configuración correcta para el proyecto, el método `run()` en tu clase `App` es sobrescrito.

El método `run()` es el núcleo de tu aplicación en webforJ. Define lo que sucede después de que la aplicación se inicializa y es el punto de entrada principal para las características de tu aplicación. Al sobrescribir el método `run()`, puedes implementar la lógica que crea y gestiona la interfaz de usuario y el comportamiento de tu aplicación.

:::tip Usando enrutamiento
Al implementar enrutamiento dentro de una aplicación, no es necesario sobrescribir el método `run()`, ya que el marco maneja automáticamente la inicialización de rutas y la creación de la `Frame` inicial. El método `run()` se invoca después de que se resuelve la ruta base, asegurando que el sistema de navegación de la aplicación esté completamente inicializado antes de que se ejecute cualquier lógica. Este tutorial profundizará en la implementación de enrutamiento en [el paso 3](scaling-with-routing-and-composites). También hay más información disponible en el [artículo sobre Enrutamiento](../../routing/overview).
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // Lógica de la aplicación
  }
}
```

## Agregando componentes {#adding-components}

En webforJ, los componentes son los bloques de construcción de la interfaz de usuario de tu aplicación. Estos componentes representan piezas discretas de la interfaz de usuario de tu aplicación, como botones, campos de texto, diálogos o tablas.

Puedes pensar en una interfaz de usuario como un árbol de componentes, con una `Frame` sirviendo como la raíz. Cada componente agregado a la `Frame` se convierte en una rama o hoja en este árbol, contribuyendo a la estructura y el comportamiento general de tu aplicación.

:::tip Catálogo de componentes
Consulta [esta página](../../components/overview) para una lista de los diversos componentes disponibles en webforJ.
:::

### `Frame` de la App {#app-frame}

La clase `Frame` en webforJ representa una ventana de nivel superior no anidable en tu aplicación. Un `Frame` actúa típicamente como el contenedor principal para los componentes de la interfaz de usuario, lo que lo convierte en un bloque de construcción esencial para construir la interfaz de usuario. Cada aplicación comienza con al menos un `Frame`, y puedes agregar componentes como botones, diálogos o formularios a estos frames.

Un `Frame` se crea dentro del método `run()` en este paso; más adelante, se agregarán componentes aquí.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Componentes del lado del servidor y del cliente {#server-and-client-side-components}

Cada componente del lado del servidor en webforJ tiene un componente web del lado del cliente correspondiente. Los componentes del lado del servidor manejan la lógica y las interacciones de backend, mientras que los componentes del lado del cliente como `dwc-button` y `dwc-dialog` gestionan la renderización y el estilo del frontend.

:::tip Componentes compuestos

Junto con los componentes centrales proporcionados por webforJ, puedes diseñar componentes compuestos personalizados agrupando múltiples elementos en una única unidad reutilizable. Este concepto se tratará en este paso del tutorial. Más información está disponible en el [artículo sobre Compuestos](../../building-ui/composite-components)
:::

Los componentes deben ser añadidos a una clase contenedora que implemente la interfaz <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink>. La `Frame` es una de esas clases; para este paso, agrega un `Paragraph` y un `Button` a la `Frame`, que se renderizarán en la interfaz de usuario en el navegador:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("¡Aplicación Demostración!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("¡Esta es una demostración!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

Ejecutar esto debería darte un botón simple estilizado que permite que aparezca un mensaje diciendo "¡Esta es una demostración!"

## Estilizando con CSS {#styling-with-css}

El estilo en webforJ te brinda total flexibilidad para diseñar la apariencia de tu aplicación. Mientras que el marco admite un diseño y estilo cohesivos desde el principio, no impone un enfoque de estilo específico, lo que te permite aplicar estilos personalizados que se alineen con los requisitos de tu aplicación.

Con webforJ, puedes aplicar dinámicamente nombres de clase a los componentes para estilización condicional o interactiva, usar CSS para un sistema de diseño consistente y escalable, e inyectar hojas de estilo en línea o externas completas.

### Agregando clases CSS a los componentes {#adding-css-classes-to-components}

Puedes agregar o quitar dinámicamente nombres de clase a los componentes utilizando los métodos `addClassName()` y `removeClassName()`. Estos métodos te permiten controlar los estilos del componente según la lógica de tu aplicación. Agrega el nombre de clase `mainFrame` a la `Frame` creada en los pasos anteriores incluyendo el siguiente código en el método `run()`:

```java
mainFrame.addClassName("mainFrame");
```

### Adjuntando archivos CSS {#attaching-css-files}

Para estilizar tu aplicación, puedes incluir archivos CSS en tu proyecto ya sea usando anotaciones de activos o utilizando la API de activos de webforJ <JavadocLink type="foundation" location="com/webforj/Page" >en tiempo de ejecución</JavadocLink>. [Consulta este artículo](../../managing-resources/importing-assets) para más información.

Por ejemplo, la anotación @StyleSheet se usa para incluir estilos del directorio resources/static. Genera automáticamente una URL para el archivo especificado e inyecta en el DOM, asegurando que los estilos se apliquen a tu aplicación. Ten en cuenta que los archivos fuera del directorio estático no son accesibles.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // Lógica de la aplicación aquí
  }
}
```
:::tip URLs del servidor web
Para asegurarte de que los archivos estáticos sean accesibles, deben colocarse en la carpeta resources/static. Para incluir un archivo estático, puedes construir su URL utilizando el protocolo del servidor web.
:::

### Ejemplo de código CSS {#sample-css-code}

Se utiliza un archivo CSS en tu proyecto en `resources > static > css > demoApplication.css`, y se utiliza el siguiente CSS para aplicar un estilo básico a la aplicación.

```css
.mainFrame {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

Una vez hecho esto, la siguiente anotación debe ser agregada a tu clase `App`:

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Paso Demostración 1")
public class DemoApplication extends App {
```

Los estilos CSS se aplican al `Frame` principal y proporcionan estructura al organizar los componentes con un [diseño de cuadrícula](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout), además de agregar estilos de margen, padding y border para que la interfaz de usuario esté visualmente organizada.
