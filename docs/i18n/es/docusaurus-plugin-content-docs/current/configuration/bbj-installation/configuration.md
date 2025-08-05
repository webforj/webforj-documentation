---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 3f3e4285abb3b23f9427cdd7b9baa282
---
Puedes configurar webforJ utilizando el archivo POM de un proyecto, que está diseñado para facilitar el despliegue de una aplicación. Las siguientes secciones detallan las diversas opciones que puedes cambiar para lograr el resultado deseado.

## Exclusión del motor {#engine-exclusion}

Al ejecutar con `BBjServices`, se debe excluir la dependencia `webforj-engine`, ya que las características proporcionadas por el motor ya están disponibles.

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

Las etiquetas dentro de la etiqueta `<configuration>` pueden ser cambiadas para configurar tu aplicación. Editar las siguientes líneas en el archivo POM predeterminado que viene con el [repositorio inicial `HelloWorldJava`](https://github.com/webforj/HelloWorldJava) resultará en estos cambios:

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

- **`<deployurl>`** Esta etiqueta es la URL donde se puede acceder al punto de instalación de webforJ para el proyecto. Para los usuarios que ejecutan su aplicación localmente, se utiliza un puerto predeterminado de 8888. Para los usuarios que ejecutan Docker, el puerto debe cambiarse al que se ingresó al [configurar el contenedor de Docker](./docker#2-configuration).

- **`<classname>`** Esta etiqueta debe contener el nombre del paquete y de la clase de la aplicación que deseas ejecutar. Esta será la única clase en tu proyecto que extiende la clase `App` y se ejecuta desde la URL base.

- **`<publishname>`** Esta etiqueta especifica el nombre de la aplicación en la URL publicada. Generalmente, para ejecutar tu programa, navegarás a una URL similar a `http://localhost:8888/webapp/<publishname>`, reemplazando `<publishname>` con el valor en la etiqueta `<publishname>`. Luego, se ejecutará el programa especificado por la etiqueta `<classname>`.

- **`<debug>`** La etiqueta de depuración puede establecerse en verdadero o falso, y determinará si la consola del navegador muestra mensajes de error lanzados por tu programa.

## Ejecutar un programa específico {#running-a-specific-program}

Hay dos formas de ejecutar un programa específico en tu aplicación:

1. Coloca el programa dentro del método `run()` de la clase que extiende `App`.
2. Utiliza [enrutamiento](../../routing/overview) en tu aplicación webforJ para dar al programa una URL dedicada.

## Cómo webforJ selecciona un punto de entrada {#how-webforj-selects-an-entry-point}

El punto de entrada para una aplicación se determina por la `<classname>` especificada en el archivo POM. Si no se especifica un punto de entrada en el archivo POM, el sistema comenzará una búsqueda de punto de entrada.

### Búsqueda de puntos de entrada {#entry-point-search}

1. Si hay una sola clase que extiende la clase `App`, esta se convertirá en el punto de entrada.
2. Si varias clases extienden `App`, el sistema verifica si alguna tiene la anotación `com.webforj.annotation.AppEntry`. La única clase anotada con `@AppEntry` se convertirá en el punto de entrada.
    :::warning
    Si varias clases están anotadas con `@AppEntry`, se lanza una excepción, listando todas las clases descubiertas.
    :::

Si hay varias clases que extienden `App` y ninguna de ellas está anotada con `@AppEntry`, se lanza una excepción que detalla cada subclase.

## Modo de depuración {#debug-mode}

También es posible ejecutar tu aplicación en modo de depuración, lo que permite que la consola imprima mensajes de error detallados.

La primera opción es cambiar el archivo `config.bbx`, que se encuentra en el directorio `cfg/` de tu instalación de BBj. Agrega la línea `SET DEBUG=1` al archivo y guarda los cambios.

Además, en el Enterprise Manager, puedes agregar lo siguiente como un argumento del programa: `DEBUG`

Completar cualquiera de estas opciones permite que la consola del navegador imprima mensajes de error.
