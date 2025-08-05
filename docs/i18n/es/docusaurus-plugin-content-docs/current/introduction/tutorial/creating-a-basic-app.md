---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: 9bd91b6d733198a2c16c9377029e8162
---
Este primer paso establece la base para la aplicación de gestión de clientes al crear una interfaz simple e interactiva. Esto demuestra cómo configurar una aplicación básica de webforJ, con un solo botón que abre un diálogo al hacer clic. Es una implementación sencilla que presenta componentes clave y te da una idea de cómo funciona webforJ.

Este paso aprovecha la clase base de la aplicación proporcionada por webforJ para definir la estructura y el comportamiento de la aplicación. Continuar hasta los pasos posteriores transitará a una configuración más avanzada utilizando enrutamiento para gestionar múltiples pantallas, introducido en [Escalando con Enrutamiento y Componentes](./scaling-with-routing-and-composites).

Al final de este paso, tendrás una aplicación en funcionamiento que demuestra la interacción básica con componentes y el manejo de eventos en webforJ. Para ejecutar la aplicación:

- Ve al directorio `1-creating-a-basic-app`
- Ejecuta el comando `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Creando una aplicación webforJ {#creating-a-webforj-app}

En webforJ, un `App` representa el centro para definir y gestionar tu proyecto. Cada aplicación webforJ comienza creando una clase que extiende la clase fundamental `App`, que sirve como el marco central para:

- Gestionar el ciclo de vida de la aplicación, incluyendo la inicialización y la terminación.
- Manejar el enrutamiento y la navegación si se habilita.
- Definir el tema, la localidad y otras configuraciones generales de la aplicación.
- Proporcionar utilidades esenciales para interactuar con el entorno y los componentes.

### Extensiones de la clase `App` {#extending-the-app-class}

Para este paso, se crea una clase llamada `DemoApplication.java`, que extiende la clase `App`.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // La lógica principal de la aplicación irá aquí
  }
}
```

:::tip Propiedades de Configuración Clave

En esta aplicación de demostración, el archivo `webforj.conf` se configura con las siguientes dos propiedades esenciales:

- **`webforj.entry`**: Especifica el nombre completamente calificado de la clase que extiende `App` que actúa como el punto de entrada principal para tu proyecto. Para este tutorial, configúralo a `com.webforj.demos.DemoApplication` para evitar ambigüedades durante la inicialización.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Habilita el modo de depuración para logs detallados y visibilidad de errores durante el desarrollo. Asegúrate de que esto esté configurado como `true` mientras trabajas en este tutorial:
  ```hocon
  webforj.debug = true
  ```

Para más detalles sobre opciones de configuración adicionales, consulta la [Guía de Configuración](../../configuration/overview).
:::

### Sobreescribiendo el método `run()` {#overriding-the-run-method}

Después de asegurar la configuración correcta para el proyecto, se sobreescribe el método `run()` en tu clase `App`.

El método `run()` es el núcleo de tu aplicación en webforJ. Define lo que sucede después de que la aplicación se inicializa y es el punto de entrada principal para las características de tu aplicación. Al sobreescribir el método `run()`, puedes implementar la lógica que crea y gestiona la interfaz de usuario y el comportamiento de tu aplicación.

:::tip Uso de enrutamiento
Al implementar enrutamiento dentro de una aplicación, no es necesario sobreescribir el método `run()`, ya que el marco maneja automáticamente la inicialización de rutas y la creación del `Frame` inicial. El método `run()` se invoca después de que se resuelve la ruta base, asegurando que el sistema de navegación de la aplicación esté completamente inicializado antes de que se ejecute cualquier lógica. Este tutorial profundizará más en la implementación del enrutamiento en [el paso 3](scaling-with-routing-and-composites). Más información también está disponible en el [Artículo de Enrutamiento](../../routing/overview).
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

En webforJ, los componentes son los bloques de construcción de la interfaz de usuario de tu aplicación. Estos componentes representan piezas discretas de la UI de tu aplicación, como botones, campos de texto, diálogos o tablas.

Puedes pensar en una UI como un árbol de componentes, siendo un `Frame` la raíz. Cada componente agregado al `Frame` se convierte en una rama o hoja en este árbol, contribuyendo a la estructura y comportamiento general de tu aplicación.

:::tip Catálogo de componentes
Consulta [esta página](../../components/overview) para una lista de los diversos componentes disponibles en webforJ.
:::

### `Frame` de la aplicación {#app-frame}

La clase `Frame` en webforJ representa una ventana de nivel superior que no se puede anidar en tu aplicación. Un `Frame` normalmente actúa como el contenedor principal para los componentes de UI, convirtiéndolo en un bloque de construcción esencial para construir la interfaz de usuario. Cada aplicación comienza con al menos un `Frame`, y puedes agregar componentes como botones, diálogos o formularios a estos frames.

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

Cada componente del lado del servidor en webforJ tiene un componente web del lado del cliente correspondiente. Los componentes del lado del servidor manejan la lógica y las interacciones del backend, mientras que los componentes del lado del cliente como `dwc-button` y `dwc-dialog` gestionan la renderización y el estilo en el frontend.

:::tip Componentes compuestos

Junto con los componentes centrales proporcionados por webforJ, puedes diseñar componentes compuestos personalizados al agrupar múltiples elementos en una única unidad reutilizable. Este concepto se cubrirá en este paso del tutorial. Más información está disponible en el [Artículo Compuesto](../../building-ui/composite-components).
:::

Los componentes deben ser agregados a una clase contenedora que implemente la <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink> interfaz. El `Frame` es una de esas clases; para este paso, agrega un `Paragraph` y un `Button` al `Frame`, que se renderizarán en la UI en el navegador:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("¡Aplicación Demo!");
  Button btn = new Button("Información");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("¡Esta es una demo!", "Información"));
    mainFrame.add(demo, btn);
  }
}
```

Ejecutar esto debería darte un botón estilizado simple que habilita la aparición de un mensaje que dice "¡Esta es una demo!".

## Estilizando con CSS {#styling-with-css}

El estilizado en webforJ te da total flexibilidad para diseñar la apariencia de tu aplicación. Si bien el marco admite un diseño y estilo cohesivos de forma predeterminada, no impone un enfoque de estilización específico, lo que te permite aplicar estilos personalizados que se alineen con los requisitos de tu aplicación.

Con webforJ, puedes aplicar dinámicamente nombres de clase a los componentes para estilización condicional o interactiva, usar CSS para un sistema de diseño consistente y escalable, e inyectar hojas de estilo completas en línea o externas.

### Agregando clases CSS a los componentes {#adding-css-classes-to-components}

Puedes agregar o eliminar dinámicamente nombres de clase a los componentes usando los métodos `addClassName()` y `removeClassName()`. Estos métodos te permiten controlar los estilos del componente en función de la lógica de tu aplicación. Agrega el nombre de clase `mainFrame` al `Frame` creado en los pasos anteriores incluyendo el siguiente código en el método `run()`:

```java
mainFrame.addClassName("mainFrame");
```

### Adjuntando archivos CSS {#attaching-css-files}

Para estilizar tu aplicación, puedes incluir archivos CSS en tu proyecto ya sea usando anotaciones de activos o utilizando la <JavadocLink type="foundation" location="com/webforj/Page" >API de activos</JavadocLink> de webforJ en tiempo de ejecución. [Consulta este artículo](../../managing-resources/importing-assets) para más información.

Por ejemplo, la anotación @StyleSheet se usa para incluir estilos desde el directorio resources/static. Genera automáticamente una URL para el archivo especificado e inyecta en el DOM, asegurando que los estilos se apliquen a tu aplicación. Ten en cuenta que los archivos fuera del directorio estático no son accesibles.

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
Para garantizar que los archivos estáticos sean accesibles, deben colocarse en la carpeta resources/static. Para incluir un archivo estático, puedes construir su URL usando el protocolo del servidor web.
:::

### Código CSS de muestra {#sample-css-code}

Se utiliza un archivo CSS en tu proyecto en `resources > static > css > demoApplication.css`, y el siguiente CSS se utiliza para aplicar un estilizado básico a la aplicación.

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

Una vez hecho esto, se debe agregar la siguiente anotación a tu clase `App`:

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Paso de Demo 1")
public class DemoApplication extends App {
```

Los estilos CSS se aplican al `Frame` principal y proporcionan estructura organizando los componentes con un [diseño en cuadrícula](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout), y añadiendo estilos de margen, padding y borde para hacer que la UI esté visualmente organizada.
