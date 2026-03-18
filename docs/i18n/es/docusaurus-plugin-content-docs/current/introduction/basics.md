---
title: App Basics
sidebar_position: 3
_i18n_hash: 23f93367391ac7cd42c28bf4cd3640ee
---
Una vez que webforJ y sus dependencias estén configurados en su proyecto, está listo para crear la estructura de la aplicación. Este artículo explicará los elementos clave de una aplicación básica de webforJ, enfocándose específicamente en las clases `Application` y `HomeView`, que son las clases fundamentales en el proyecto de inicio `webforj-archetype-hello-world`.

## Clase principal de la aplicación: `Application.java` {#main-app-class-applicationjava}

La clase `Application` sirve como punto de entrada para su aplicación webforJ, configurando configuraciones y rutas esenciales. Para comenzar, observe la declaración de la clase y las anotaciones.

Esta clase extiende la clase central `App` de webforJ, lo que la hace reconocible como una aplicación de webforJ. Varias anotaciones configuran el tema, el título y la estructura de enrutamiento de la aplicación.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Especifica que webforJ debe escanear el paquete `com.samples.views` en busca de componentes de ruta.
- `@AppTitle`: Define el título que se muestra en la pestaña del navegador de la aplicación.
- `@StyleSheet`: Víncula un archivo CSS externo, `app.css`, permitiendo el estilo personalizado para la aplicación.

La clase `Application` no contiene métodos adicionales porque las configuraciones se establecen a través de anotaciones y webforJ se encarga de la inicialización de la aplicación.

Con `Application.java` configurado, la aplicación ahora está configurada con un título y rutas que apuntan al paquete de vistas. A continuación, una visión general de la clase `HomeView` brinda información sobre lo que se muestra cuando se ejecuta la aplicación.

### Descubriendo una `App` {#discovering-an-app}

Se aplica un límite de una sola <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> en webforJ, lo que transfiere todas las responsabilidades de manejo de errores al lado de Java y da a los desarrolladores control total sobre la gestión de errores.

Durante el proceso de arranque de webforJ, se escanean todas las clases que extienden <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>. Si se encuentran múltiples aplicaciones, el sistema busca la anotación <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>. Si alguna de las clases descubiertas está anotada con <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink>, la primera que se encuentre se considera el punto de entrada.

- Si una clase está anotada con `@AppEntry`, esa clase se selecciona como el punto de entrada.
- Si múltiples clases están anotadas con `@AppEntry`, se lanza una excepción, enumerando todas las clases descubiertas.
- Si ninguna clase está anotada y solo se encuentra una subclase de `App`, esa clase se selecciona como el punto de entrada.
- Si ninguna clase está anotada y se encuentran múltiples subclases de `App`, se lanza una excepción que detalla cada subclase.

:::tip Manejo de Errores
Para obtener más información sobre cómo se manejan los errores en webforJ, consulte [este artículo](../advanced/error-handling).
:::

## Clase de vista principal: `HomeView.java` {#main-view-class-homeviewjava}

La clase `HomeView` define un componente de vista simple que sirve como la página de inicio de la aplicación. Muestra un campo y un botón para saludar el nombre ingresado por el usuario.

### Declaración de la clase y anotaciones {#class-declaration-and-annotations}

`HomeView` extiende `Composite<FlexLayout>`, lo que le permite actuar como un componente reutilizable compuesto por un componente [`FlexLayout`](../components/flex-layout). La [`@Route("/")`](../routing/overview) hace que esta sea la ruta raíz de la aplicación.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("¿Cuál es tu nombre?");
  private Button btn = new Button("Di Hola");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("¡Bienvenido a webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### Inicialización del componente {#component-initialization}

Dentro de la clase, se inicializan y declaran varios elementos de la interfaz de usuario:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("¿Cuál es tu nombre?");
private Button btn = new Button("Di Hola");
```

- `self`: El componente principal de diseño que utiliza [`FlexLayout`](../components/flex-layout), configurado como contenedor para los elementos. Este elemento utiliza el método `getBoundComponent()` para almacenar el `FlexLayout` principal que contiene la clase.
- `hello`: Un [`TextField`](../components/fields/textfield) etiquetado como `¿Cuál es tu nombre?` para que los usuarios ingresen su nombre.
- `btn`: Un [`Button`](../components/button) con estilo primario etiquetado como `Di Hola`.

### Configuración del diseño {#layout-configuration}

El diseño `(self)` se configura con algunas propiedades de estilo clave:

- `FlexDirection.COLUMN` apila los elementos verticalmente.
- `setMaxWidth(300)` restringe el ancho a 300 píxeles para un diseño compacto.
- `setStyle("margin", "1em auto")` centra el diseño con un margen a su alrededor.

### Agregando componentes al diseño {#adding-components-to-the-layout}

Finalmente, el campo de texto hello y el botón btn se agregan al contenedor [`FlexLayout`](../components/flex-layout) llamando a `self.add(hello, btn)`. Este arreglo define la estructura de la vista, haciendo que el formulario sea tanto interactivo como visualmente centrado.

## Estilizando la aplicación {#styling-the-app}

El archivo `styles.css` proporciona un estilo personalizado para su aplicación webforJ. Este archivo CSS se referencia en la clase Application utilizando la anotación [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files), lo que permite a la aplicación aplicar estilos a los componentes dentro de la aplicación.

Este archivo se encuentra en el directorio `resources/static` del proyecto y puede ser referenciado utilizando la URL del servidor web `ws://app.css`.
