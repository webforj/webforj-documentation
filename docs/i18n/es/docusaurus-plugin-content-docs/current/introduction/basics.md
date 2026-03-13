---
title: App Basics
sidebar_position: 3
_i18n_hash: e4eae914f0cbd5c9e5eacb6e681570a9
---
Una vez que webforJ y sus dependencias están configuradas en tu proyecto, estás listo para crear la estructura de la aplicación. Este artículo te guiará a través de los elementos clave de una aplicación webforJ básica, enfocándose específicamente en las clases `Application` y `HomeView`, que son las clases fundamentales en el proyecto inicial `webforj-archetype-hello-world`.

## Clase principal de la aplicación: `Application.java` {#main-app-class-applicationjava}

La clase `Application` sirve como el punto de entrada para tu aplicación webforJ, configurando las configuraciones esenciales y las rutas. Para empezar, observa la declaración de la clase y las anotaciones.

Esta clase extiende la clase central `App` de webforJ, lo que la hace reconocible como una aplicación webforJ. Varias anotaciones configuran el tema, el título y la enrutación de la aplicación.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Especifica que webforJ debe escanear el paquete `com.samples.views` en busca de componentes de ruta.
- `@AppTitle`: Define el título que se muestra en la pestaña del navegador de la aplicación.
- `@StyleSheet`: Enlaza un archivo CSS externo, `app.css`, permitiendo estilos personalizados para la aplicación.

La clase `Application` no contiene métodos adicionales porque las configuraciones se establecen a través de anotaciones, y webforJ maneja la inicialización de la aplicación.

Con `Application.java` configurado, la aplicación ahora está configurada con un título y rutas que apuntan al paquete de vistas. A continuación, un resumen de la clase `HomeView` proporciona información sobre lo que se muestra cuando se ejecuta la aplicación.

### Descubriendo una `App` {#discovering-an-app}

Se impone un límite de una sola <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> en webforJ, lo que transfiere todas las responsabilidades de manejo de errores al lado de Java y otorga a los desarrolladores control total sobre la gestión de errores.

Durante el proceso de arranque de webforJ, se escanean todas las clases que extienden <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>. Si se encuentran múltiples aplicaciones, el sistema busca la anotación <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>. Si alguna de las clases encontradas está anotada con <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink>, la primera encontrada se considera el punto de entrada.

- Si una clase está anotada con `@AppEntry`, esa clase se selecciona como el punto de entrada.
- Si múltiples clases están anotadas con `@AppEntry`, se lanza una excepción que enumera todas las clases descubiertas.
- Si no se anota ninguna clase y se encuentra solo una subclase de `App`, esa clase se selecciona como el punto de entrada.
- Si no se anota ninguna clase y se encuentran múltiples subclases de `App`, se lanza una excepción que detalla cada subclase.

:::tip Manejo de errores
Para más información sobre cómo se manejan los errores en webforJ, consulta [este artículo](../advanced/error-handling).
:::

## Clase principal de la vista: `HomeView.java` {#main-view-class-homeviewjava}

La clase `HomeView` define un componente de vista simple que sirve como la página de inicio de la aplicación. Muestra un campo y un botón para saludar al nombre ingresado por el usuario.

### Declaración de clase y anotaciones {#class-declaration-and-annotations}

`HomeView` extiende `Composite<FlexLayout>`, lo que le permite actuar como un componente reutilizable compuesto de un componente [`FlexLayout`](../components/flex-layout). La [`@Route("/")`](../routing/overview) hace de esto la ruta raíz de la aplicación.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("¿Cuál es tu nombre?");
  private Button btn = new Button("Decir Hola");

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

Dentro de la clase, varios elementos de la interfaz de usuario están inicializados y declarados:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("¿Cuál es tu nombre?");
private Button btn = new Button("Decir Hola");
```

- `self`: El componente de diseño principal que utiliza [`FlexLayout`](../components/flex-layout), configurado como un contenedor para los elementos. Este elemento usa el método `getBoundComponent()` para almacenar el principal `FlexLayout` que contiene la clase.
- `hello`: Un [`TextField`](../components/fields/textfield) etiquetado como `¿Cuál es tu nombre?` para que los usuarios ingresen su nombre.
- `btn`: Un [`Button`](../components/button) de estilo primario etiquetado como `Decir Hola`.

### Configuración del diseño {#layout-configuration}

El diseño `(self)` se configura con algunas propiedades clave de estilo:

- `FlexDirection.COLUMN` apila los elementos verticalmente.
- `setMaxWidth(300)` restringe el ancho a 300 píxeles para un diseño compacto.
- `setStyle("margin", "1em auto")` centra el diseño con un margen a su alrededor.

### Agregando componentes al diseño {#adding-components-to-the-layout}

Finalmente, el campo de texto hello y el botón btn se agregan al contenedor [`FlexLayout`](../components/flex-layout) mediante la llamada `self.add(hello, btn)`. Este arreglo define la estructura de la vista, haciendo que el formulario sea tanto interactivo como visualmente centrado.

## Estilizando la aplicación {#styling-the-app}

El archivo `styles.css` proporciona estilos personalizados para tu aplicación webforJ. Este archivo CSS se referencia en la clase Application utilizando la anotación [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files), que permite a la aplicación aplicar estilos a los componentes dentro de la aplicación.

Este archivo se encuentra en el directorio `resources/static` del proyecto, y se puede referenciar usando la URL del servidor web `ws://app.css`.
