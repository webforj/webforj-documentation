---
title: Instalar Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: 1019285125db3439621951fe22047837
---
:::danger Deprecado y será eliminado
El plugin de instalación de webforJ está obsoleto y será eliminado en una futura versión. No comience nuevos proyectos con él. Mueva los proyectos existentes a [BBjShip](/docs/configuration/bbjship/overview), que instala, despliega y ejecuta aplicaciones webforJ en BBjServices. [Proyectos existentes](/docs/configuration/bbjship/existing-projects) enumera los cambios que necesita un proyecto.
:::

Puede configurar webforJ utilizando el archivo POM de un proyecto, que está diseñado para facilitar el despliegue de una aplicación. Las siguientes secciones describen las diversas opciones que puede cambiar para lograr el resultado deseado.

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

Las etiquetas dentro de la etiqueta `<configuration>` pueden ser cambiadas para configurar su aplicación. Editar las siguientes líneas en el archivo POM por defecto que viene con el repositorio inicial [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) resultará en estos cambios:

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

- **`<deployurl>`** Esta etiqueta es la URL donde se puede acceder al endpoint de webforJ para la instalación del proyecto. Para los usuarios que ejecutan su aplicación localmente, se utiliza un puerto por defecto de 8888. Para los usuarios que ejecutan Docker, el puerto debe ser cambiado al puerto que se ingresó al [configurar el contenedor Docker](./docker#2-configuration).

- **`<classname>`** Esta etiqueta debe contener el nombre del paquete y de la clase de la aplicación que desea ejecutar. Esta será la única clase en su proyecto que extiende la clase `App` y se ejecuta desde la URL base.

- **`<publishname>`** Esta etiqueta especifica el nombre de la aplicación en la URL publicada. Generalmente, para ejecutar su programa, navegará a una URL similar a `http://localhost:8888/webapp/<publishname>`, reemplazando `<publishname>` con el valor en la etiqueta `<publishname>`. Luego, se ejecutará el programa especificado por la etiqueta `<classname>`.

- **`<debug>`** La etiqueta de depuración puede configurarse en verdadero o falso, y determinará si la consola del navegador muestra mensajes de error lanzados por su programa.

## Ejecución de un programa específico {#running-a-specific-program}

Hay dos formas de ejecutar un programa específico en su aplicación:

1. Coloque el programa dentro del método `run()` de la clase que extiende `App`.
2. Utilice [rutas](../../routing/overview) en su aplicación webforJ para dar al programa una URL dedicada.

## Cómo webforJ selecciona un punto de entrada {#how-webforj-selects-an-entry-point}

El punto de entrada para una aplicación está determinado por la `<classname>` especificada en el archivo POM. Si no se especifica un punto de entrada en el archivo POM, el sistema comenzará una búsqueda de puntos de entrada.

### Búsqueda de punto de entrada {#entry-point-search}

1. Si hay una única clase que extiende la clase `App`, esta se convertirá en el punto de entrada.
2. Si múltiples clases extienden `App`, el sistema verifica si alguna tiene la anotación `com.webforj.annotation.AppEntry`. La única clase anotada con `@AppEntry` se convertirá en el punto de entrada.
    :::warning
    Si múltiples clases están anotadas con `@AppEntry`, se lanza una excepción, enumerando todas las clases descubiertas.
    :::

Si hay múltiples clases que extienden `App` y ninguna de ellas está anotada con `@AppEntry`, se lanza una excepción, detallando cada subclase.

## Modo de depuración {#debug-mode}

También es posible ejecutar su aplicación en modo de depuración, lo que permite que la consola imprima mensajes de error detallados.

La primera opción es cambiar el archivo `config.bbx`, que se encuentra en el directorio `cfg/` de su instalación de BBj. Agregue la línea `SET DEBUG=1` al archivo y guarde sus cambios.

Además, en el Administrador Empresarial, puede agregar lo siguiente como un argumento del programa: `DEBUG`

Completar cualquiera de estas opciones permite que la consola del navegador imprima mensajes de error.
