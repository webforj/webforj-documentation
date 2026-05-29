---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: ac74bc5c04bce477a7407c9ff94323a4
---
En [Configuración del Proyecto](/docs/introduction/tutorial/project-setup), generaste un proyecto webforJ. Ahora es momento de crear la clase principal para el proyecto y agregar una interfaz interactiva utilizando componentes de webforJ. En este paso, aprenderás sobre:

- El punto de entrada para aplicaciones que utilizan webforJ y Spring Boot
- Componentes de webforJ y elementos HTML
- Uso de CSS para estilizar componentes

Completar este paso crea una versión de [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insertar video aquí -->

## Ejecutando la app {#running-the-app}

A medida que desarrollas tu aplicación, puedes utilizar [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) como comparación. Para ver la aplicación en acción:

1. Navega al directorio de nivel superior que contiene el archivo `pom.xml`, que es `1-creating-a-basic-app` si estás siguiendo la versión en GitHub.

2. Utiliza el siguiente comando de Maven para ejecutar la aplicación de Spring Boot localmente:
    ```bash
    mvn
    ```

Ejecutar la aplicación abre automáticamente un nuevo navegador en `http://localhost:8080`.

## El punto de entrada {#entry-point}

Cada aplicación de webforJ contiene una sola clase que extiende <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. Para este tutorial, y otros proyectos publicados de webforJ, comúnmente se llama `Application`. Esta clase está dentro de un paquete que lleva el nombre del `groupId` que usaste en [Configuración del Proyecto](/docs/introduction/tutorial/project-setup):

```
1-creating-a-basic-app 
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// resaltar-siguiente-línea
│   └──com/webforj/tutorial
// resaltar-siguiente-línea
│       └──Application.java
└───target
```

Dentro de la clase `Application`, el método `SpringApplication.run()` utiliza las configuraciones para lanzar la aplicación. Las diversas anotaciones son para las configuraciones de la aplicación.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Aplicación del Cliente", shortName = "ClienteApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Anotaciones {#annotations}

La [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) es una anotación clave en Spring Boot. Colocas esta anotación en la clase principal para marcarla como el punto de inicio de tu aplicación.

`@StyleSheet`, `@AppTheme` y `@AppProfile` son solo algunas de las muchas <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">anotaciones de webforJ</JavadocLink> disponibles cuando deseas establecer configuraciones de manera explícita.

- **`@StyleSheet`** incrusta un archivo CSS en la página web. Más detalles sobre cómo interactuar con un archivo CSS específico se encuentran más adelante en [Estilizando con CSS](#styling-with-css).

- **`@AppTheme`** gestiona el tema visual de la aplicación. Si se establece en `system`, la aplicación adopta automáticamente el tema preferido del usuario: `light`, `dark` o `dark-pure`. Para información sobre cómo crear temas personalizados o anular los temas predeterminados, consulta el artículo sobre [Temas](/docs/styling/themes).

- **`@AppProfile`** ayuda a configurar cómo la aplicación se presenta al usuario como una [aplicación instalable](/docs/configuration/installable-apps). Como mínimo, esta anotación necesita un `name` para el nombre completo de la aplicación y un `shortName` para usar cuando el espacio es limitado. El `shortName` no debe exceder los 12 caracteres.  

## Creando una interfaz de usuario {#creating-a-ui}

Para crear tu UI, necesitarás agregar [componentes de elementos HTML](/docs/components/html-elements) y [componentes de webforJ](/docs/components/overview). Por ahora, solo tienes una aplicación de una sola página, así que agregarás componentes directamente en la clase `Application`. 
Para hacerlo, sobreescribe el método `App.run()` y crea un `Frame` para agregar componentes.

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Crea componentes de UI y agrégales al frame

}
```

### Usando elementos HTML {#using-html-elements}

Puedes agregar elementos HTML estándar a tu aplicación con [componentes de elementos HTML](/docs/components/html-elements).
Crea una nueva instancia del componente y utiliza el método `add()` para agregarlo al `Frame`:

```java
// Crea el contenedor para los elementos de UI
Frame mainFrame = new Frame();

// Crea el componente HTML
Paragraph tutorial = new Paragraph("¡Aplicación Tutorial!");

// Agrega el componente al contenedor
mainFrame.add(tutorial);
```

### Usando componentes de webforJ {#webforj-components-and-html-elements}

Mientras que los elementos HTML son útiles para la estructura, la semántica y necesidades ligeras de UI, los [componentes de webforJ](/docs/components/overview) ofrecen un comportamiento más complejo y dinámico.

El siguiente código agrega un componente [Button](/docs/components/button), cambia su apariencia con el método `setTheme()`, y agrega un listener para crear un componente [Message Dialog](/docs/components/option-dialogs/message) cuando se hace clic en el botón.
La mayoría de los métodos de los componentes de webforJ que modifican un componente devuelven el componente mismo, por lo que puedes encadenar múltiples métodos para un código más compacto.

```java
// Crea el contenedor para los elementos de UI
Frame mainFrame = new Frame();

// Crea el componente de webforJ
Button btn = new Button("Info");

// Modifica el componente de webforJ y agrega un listener
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("¡Esto es un tutorial!", "Info"));

// Agrega el componente al contenedor
mainFrame.add(btn);
```

## Estilizando con CSS {#styling-with-css}

La mayoría de los componentes de webforJ tienen métodos incorporados para realizar cambios de estilo comunes, como el tamaño y el tema.

```java
//Establece el ancho del Frame usando una palabra clave de CSS
mainFrame.setWidth("fit-content");

//Establece el max-width del botón usando píxeles
btn.setMaxWidth(200);

//Establece el tema del botón a PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

Además de estos métodos, puedes estilizar tu aplicación utilizando CSS. La sección **Estilizando** de la página de documentación de cualquier componente tiene detalles específicos sobre las propiedades CSS relevantes.

webforJ también viene con un conjunto de variables CSS diseñadas llamadas tokens DWC. Consulta la documentación de [Estilizando](/docs/styling/overview) para obtener información detallada sobre cómo estilizar componentes de webforJ y cómo utilizar los tokens.

### Referenciando un archivo CSS {#referencing-a-css-file} 

Es mejor tener un archivo CSS separado para mantener todo organizado y mantenible. Crea un archivo llamado `card.css` dentro de `src/main/resources/static/css`, con la siguiente definición de clase CSS:

```css title="card.css"
.card {
  display: grid;
  gap: var(--dwc-space-l);
  padding: var(--dwc-space-l);
  margin: var(--dwc-space-l) auto;
  border: thin solid var(--dwc-color-default);
  border-radius: 16px;
  background-color: var(--dwc-surface-3);
  box-shadow: var(--dwc-shadow-xs);
}
```

Luego, referencia el archivo en `Application.java` utilizando la anotación `@StyleSheet` con el nombre del archivo CSS. Para este paso, es `@StyleSheet("ws://css/card.css")`.

:::tip Protocolo del servidor web
Este tutorial utiliza el protocolo del servidor web para referenciar el archivo CSS. Para aprender más sobre cómo funciona esto, consulta [Manejando Recursos](/docs/managing-resources/overview).
:::

### Agregando clases CSS a componentes {#adding-css-classes-to-components}

Puedes agregar o eliminar dinámicamente nombres de clases a los componentes utilizando los métodos `addClassName()` y `removeClassName()`. Para este tutorial, solo se utiliza una clase CSS:

```java
mainFrame.addClassName("card");
```

## `Application` completada {#completed-application}

Tu clase `Application` ahora debería verse similar a lo siguiente:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Aplicación del Cliente", shortName = "ClienteApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("¡Aplicación Tutorial!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("¡Esto es un tutorial!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Múltiples páginas
Para una aplicación más compleja, puedes dividir la interfaz de usuario en múltiples páginas para una mejor organización. Este concepto se cubre más adelante en este tutorial en [Enrutamiento y Compuestos](/docs/introduction/tutorial/routing-and-composites).
:::

## Próximo paso {#next-step}

Después de crear una aplicación funcional con una interfaz de usuario básica, el próximo paso es agregar un modelo de datos y mostrar los resultados en un componente `Table` en [Trabajando con Datos](/docs/introduction/tutorial/working-with-data).
