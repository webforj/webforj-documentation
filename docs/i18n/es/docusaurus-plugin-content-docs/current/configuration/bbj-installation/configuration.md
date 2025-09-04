---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 1a3e48999554631e4f15a67c80385111
---
Puedes configurar webforJ utilizando el archivo POM de un proyecto, que está diseñado para facilitar el despliegue de una aplicación. Las siguientes secciones describen las diversas opciones que puedes cambiar para lograr el resultado deseado.

## Exclusión del motor {#engine-exclusion}

Al ejecutar con `BBjServices`, se debe excluir la dependencia `webforj-engine`, ya que las funcionalidades proporcionadas por el motor ya están disponibles.

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

Las etiquetas dentro de la etiqueta `<configuration>` pueden cambiarse para configurar tu aplicación. Editar las siguientes líneas en el archivo POM predeterminado que viene con el [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) repositorio inicial resultará en estos cambios:

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

- **`<deployurl>`** Esta etiqueta es la URL donde se puede acceder al endpoint de webforJ para la instalación del proyecto. Para los usuarios que ejecutan su aplicación localmente, se utiliza un puerto predeterminado de 8888. Para los usuarios que ejecutan Docker, el puerto debe cambiarse al puerto que se ingresó al [configurar el contenedor Docker](./docker#2-configuration).

- **`<classname>`** Esta etiqueta debe contener el paquete y el nombre de la clase de la aplicación que deseas ejecutar. Esta será la única clase en tu proyecto que extiende la clase `App` y se ejecuta desde la URL base.

- **`<publishname>`** Esta etiqueta especifica el nombre de la aplicación en la URL publicada. Generalmente, para ejecutar tu programa, navegarás a una URL similar a `http://localhost:8888/webapp/<publishname>`, reemplazando `<publishname>` con el valor en la etiqueta `<publishname>`. Luego, se ejecutará el programa especificado por la etiqueta `<classname>`.

- **`<debug>`** La etiqueta de depuración puede establecerse en verdadero o falso, y determinará si la consola del navegador muestra mensajes de error generados por tu programa.

## Ejecutando un programa específico {#running-a-specific-program}

Hay dos formas de ejecutar un programa específico en tu aplicación:

1. Colocar el programa dentro del método `run()` de la clase que extiende `App`.
2. Utilizar [enrutamiento](../../routing/overview) en tu aplicación webforJ para darle al programa una URL dedicada.

## Cómo webforJ selecciona un punto de entrada {#how-webforj-selects-an-entry-point}

El punto de entrada para una aplicación se determina por el `<classname>` especificado en el archivo POM. Si no se especifica un punto de entrada en el archivo POM, el sistema comenzará una búsqueda de puntos de entrada.

### Búsqueda de punto de entrada {#entry-point-search}

1. Si hay una sola clase que extiende la clase `App`, esa se convertirá en el punto de entrada.
2. Si hay múltiples clases que extienden `App`, el sistema verifica si alguna tiene la anotación `com.webforj.annotation.AppEntry`. La única clase anotada con `@AppEntry` se convertirá en el punto de entrada.
    :::warning
    Si múltiples clases están anotadas con `@AppEntry`, se lanza una excepción, listando todas las clases descubiertas.
    :::

Si hay múltiples clases que extienden `App` y ninguna de ellas está anotada con `@AppEntry`, se lanza una excepción, detallando cada subclase.

## Modo de depuración {#debug-mode}

También es posible ejecutar tu aplicación en modo de depuración, lo que permite que la consola imprima mensajes de error comprensivos.

La primera opción es cambiar el archivo `config.bbx`, que se encuentra en el directorio `cfg/` de tu instalación de BBj. Agrega la línea `SET DEBUG=1` al archivo y guarda tus cambios.

Además, en el Enterprise Manager, puedes agregar lo siguiente como argumento del programa: `DEBUG`

Completar cualquiera de estas opciones permite que la consola del navegador imprima mensajes de error.
