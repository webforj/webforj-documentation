---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: f6ca2e9ca82e9592c4e0c8b7726164ce
---
Puedes configurar webforJ utilizando el archivo POM de un proyecto, que está diseñado para facilitar la implementación de una aplicación. Las siguientes secciones describen las diversas opciones que puedes cambiar para lograr un resultado deseado.

## Exclusión del motor {#engine-exclusion}

Cuando se ejecuta con `BBjServices`, la dependencia `webforj-engine` debe ser excluida, ya que las características proporcionadas por el motor ya están disponibles.

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions> 
  </dependency>
</dependencies>
```

## Etiquetas del archivo POM {#pom-file-tags}

Las etiquetas dentro de la etiqueta `<configuration>` pueden ser cambiadas para configurar tu aplicación. Editar las siguientes líneas en el archivo POM predeterminado que viene con el [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) repositorio de inicio resultará en estos cambios:

```xml {13-16} showLineNumbers
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-install-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>install</goal>
      </goals>
  </execution>
  </executions>
  <configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
  </configuration>
</plugin>
```

- **`<deployurl>`** Esta etiqueta es la URL donde se puede acceder al punto final de webforJ para la instalación del proyecto. Para los usuarios que ejecutan su aplicación localmente, se utiliza un puerto predeterminado de 8888. Para los usuarios que ejecutan Docker, el puerto debe ser cambiado al puerto que se ingresó al [configurar el contenedor de Docker](./docker#2-configuration).

- **`<classname>`** Esta etiqueta debe contener el paquete y el nombre de clase de la aplicación que deseas ejecutar. Esta será la única clase en tu proyecto que extiende la clase `App` y se ejecuta desde la URL base.

- **`<publishname>`** Esta etiqueta especifica el nombre de la aplicación en la URL publicada. Generalmente, para ejecutar tu programa, navegarás a una URL similar a `http://localhost:8888/webapp/<publishname>`, reemplazando `<publishname>` con el valor en la etiqueta `<publishname>`. Luego, se ejecuta el programa especificado por la etiqueta `<classname>`.

- **`<debug>`** La etiqueta de depuración puede ser establecida en verdadero o falso, y determinará si la consola del navegador muestra mensajes de error lanzados por tu programa.

## Ejecutando un programa específico {#running-a-specific-program}

Hay dos maneras de ejecutar un programa específico en tu aplicación:

1. Colocar el programa dentro del método `run()` de la clase que extiende `App`.
2. Utilizar [rutas](../../routing/overview) en tu aplicación webforJ para dar al programa una URL dedicada.

## Cómo webforJ selecciona un punto de entrada {#how-webforj-selects-an-entry-point}

El punto de entrada para una aplicación se determina por la `<classname>` especificada en el archivo POM. Si no se especifica un punto de entrada en el archivo POM, el sistema comenzará una búsqueda del punto de entrada.

### Búsqueda del punto de entrada {#entry-point-search}

1. Si hay una sola clase que extiende la clase `App`, esa se convertirá en el punto de entrada.
2. Si múltiples clases extienden `App`, el sistema verifica si una tiene la anotación `com.webforj.annotation.AppEntry`. La única clase anotada con `@AppEntry` se convertirá en el punto de entrada.
    :::warning
    Si múltiples clases están anotadas con `@AppEntry`, se lanzará una excepción, listando todas las clases descubiertas.
    :::

Si hay múltiples clases que extienden `App` y ninguna de ellas está anotada con `@AppEntry`, se lanzará una excepción, detallando cada subclase.

## Modo de depuración {#debug-mode}

También es posible ejecutar tu aplicación en modo de depuración, lo que permite que la consola imprima mensajes de error detallados.

La primera opción es cambiar el archivo `config.bbx`, que se encuentra en el directorio `cfg/` de tu instalación de BBj. Añade la línea `SET DEBUG=1` al archivo y guarda tus cambios.

Además, en el Enterprise Manager, puedes agregar lo siguiente como un argumento del programa: `DEBUG`

Completar cualquiera de estas opciones permite que la consola del navegador imprima mensajes de error.
