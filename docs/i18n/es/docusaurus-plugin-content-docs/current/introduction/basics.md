---
title: App Basics
description: >-
  Walk through the Application and HomeView classes of the hello-world archetype
  to see how routing, annotations, and views shape a webforJ app.
sidebar_position: 3
_i18n_hash: 2ebddfe300802013e4376681bc2ccf04
---
Una vez que webforJ y sus dependencias estén configurados en tu proyecto, estarás listo para crear la estructura de la aplicación. Este artículo recorrerá los elementos clave de una aplicación básica de webforJ, centrándose específicamente en las clases `Application` y `HomeView`, que son las clases fundamentales en el proyecto inicial `webforj-archetype-hello-world`.

## Clase principal de la app: `Application.java` {#main-app-class-applicationjava}

La clase `Application` sirve como el punto de entrada para tu aplicación de webforJ, configurando las configuraciones y rutas esenciales. Para comenzar, nota la declaración de la clase y las anotaciones.

Esta clase extiende la clase principal `App` de webforJ, haciéndola reconocible como una aplicación de webforJ. Varias anotaciones configuran el tema, el título y la ruta de la aplicación.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Especifica que webforJ debe escanear el paquete `com.samples.views` en busca de componentes de ruta.
- `@AppTitle`: Define el título que se muestra en la pestaña del navegador de la aplicación.
- `@StyleSheet`: Enlaza un archivo CSS externo, `app.css`, permitiendo un estilo personalizado para la aplicación.

La clase `Application` no contiene métodos adicionales porque las configuraciones se establecen a través de anotaciones, y webforJ maneja la inicialización de la aplicación.

Con `Application.java` configurada, la aplicación ahora está configurada con un título y rutas que apuntan al paquete de vistas. A continuación, una visión general de la clase `HomeView` ofrece una idea de lo que se muestra cuando se ejecuta la aplicación.

### Descubriendo una `App` {#discovering-an-app}

Se aplica un límite de una única <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> en webforJ, lo que transfiere todas las responsabilidades de manejo de errores al lado de Java y otorga a los desarrolladores control total sobre la gestión de errores.

Durante el proceso de inicio de webforJ, se escanean todas las clases que extienden <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>. Si se encuentran múltiples apps, el sistema busca la anotación <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>. Si alguna de las clases descubiertas está anotada con <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink>, la primera que se encuentra se considera el punto de entrada.

- Si una clase está anotada con `@AppEntry`, esa clase se selecciona como el punto de entrada.
- Si varias clases están anotadas con `@AppEntry`, se lanza una excepción que lista todas las clases descubiertas.
- Si ninguna clase está anotada y solo se encuentra una subclase de `App`, esa clase se selecciona como el punto de entrada.
- Si ninguna clase está anotada y se encuentran múltiples subclases de `App`, se lanza una excepción detallando cada subclase.

:::tip Manejo de Errores
Para más información sobre cómo se manejan los errores en webforJ, consulta [este artículo](../advanced/error-handling).
:::

## Clase de vista principal: `HomeView.java` {#main-view-class-homeviewjava}

La clase `HomeView` define un componente de vista simple que sirve como la página de inicio para la aplicación. Muestra un campo y un botón para saludar al nombre ingresado por el usuario.

### Declaración de la clase y anotaciones {#class-declaration-and-annotations}

`HomeView` extiende `Composite<FlexLayout>`, lo que le permite actuar como un componente reutilizable compuesto por un componente [`FlexLayout`](../components/flex-layout). La anotación [`@Route("/")`](../routing/overview) convierte esto en la ruta raíz de la aplicación.

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

### Inicialización de componentes {#component-initialization}

Dentro de la clase, se inicializan y declaran varios elementos de la interfaz de usuario:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("¿Cuál es tu nombre?");
private Button btn = new Button("Di Hola");
```

- `self`: El componente principal de diseño que usa [`FlexLayout`](../components/flex-layout), configurado como un contenedor para los elementos. Este elemento utiliza el método `getBoundComponent()` para almacenar el principal `FlexLayout` que contiene la clase.
- `hello`: Un [`TextField`](../components/fields/textfield) etiquetado como `¿Cuál es tu nombre?` para que los usuarios ingresen su nombre.
- `btn`: Un [`Button`](../components/button) estilizado como primario y etiquetado con `Di Hola`.

### Configuración del diseño {#layout-configuration}

El diseño `(self)` se configura con algunas propiedades de estilo clave:

- `FlexDirection.COLUMN` apila los elementos verticalmente.
- `setMaxWidth(300)` restringe el ancho a 300 píxeles para un diseño compacto.
- `setStyle("margin", "1em auto")` centra el diseño con un margen alrededor.

### Agregando componentes al diseño {#adding-components-to-the-layout}
Finalmente, el campo de texto hello y el botón btn se agregan al contenedor [`FlexLayout`](../components/flex-layout) llamando a `self.add(hello, btn)`. Este arreglo define la estructura de la vista, haciendo que el formulario sea tanto interactivo como visualmente centrado.

## Estilizando la aplicación {#styling-the-app}

El archivo `styles.css` proporciona un estilo personalizado para tu aplicación de webforJ. Este archivo CSS es referenciado en la clase Application usando la anotación [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files), que permite que la aplicación aplique estilos a los componentes dentro de la aplicación.

Este archivo se encuentra en el directorio `resources/static` del proyecto, y puede ser referenciado utilizando la URL del servidor web `ws://app.css`.
