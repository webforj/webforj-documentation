---
title: Build and tests
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  What the bundler does across the build, the development watch, running
  frontend tests, tuning a compiler, and producing a minified production bundle.
_i18n_hash: 0fe6e8ed747a106be1fedf5a2506f803
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

El bundler funciona como objetivos del [plugin de construcción webforJ](/docs/configuration/build-plugin). Agrega el plugin una vez, como se muestra allí, y un `mvn package` o `gradle build` normal produce una aplicación con su frontend compilado, mientras que `mvn test` ejecuta las pruebas del frontend junto con las pruebas de Java. Esta página cubre lo que hace el bundler a través de esos pasos.

## La vigilancia de desarrollo {#the-development-watch}

El paso `watch` es el que ejecutas manualmente durante el desarrollo, junto con la aplicación. Compila el frontend una vez, luego se reconstruye ante cada cambio y actualiza el navegador.

```bash
mvn compile webforj:watch spring-boot:run
```

Un proyecto webforJ establece esto como su objetivo predeterminado, así que `mvn` sin argumentos inicia la vigilancia y la aplicación juntos. El comportamiento de recarga que impulsa, un cambio en la hoja de estilos aplicado en el lugar contra un cambio de script que recarga la vista, se cubre en [Vigilancia del frontend](/docs/configuration/deploy-reload/frontend-watch).

## Pruebas del frontend {#frontend-tests}

El paso `test` ejecuta el corredor de pruebas Bun sobre `src/main/frontend` durante la fase de prueba, así que `mvn test` ejecuta las pruebas del frontend con las pruebas de Java. Cuando la raíz de origen no contiene archivos de prueba, se omite el paso, y una prueba del frontend fallida falla la construcción, por lo que un frontend roto detiene un lanzamiento de la misma manera que lo hace una prueba de Java rota. Para escribir esas pruebas, consulta [Pruebas del frontend](/docs/testing/frontend-testing).

## Ajustando un compilador {#tuning-a-compiler}

Un compilador lee sus configuraciones desde `src/main/frontend/bun.config.ts`, identificadas por el id de la extensión, así que una configuración alcanza el compilador correcto sin bandera en la construcción. Consulta [SCSS y Sass](/docs/managing-resources/bundler/extensions/scss) para un ejemplo trabajado que da al compilador SCSS una ruta de carga.

## El paquete de producción {#the-production-bundle}

El paso `bundle` se ejecuta durante `prepare-package`, así que empaquetar una aplicación compila su frontend para producción. Una construcción de producción difiere de la de desarrollo en dos maneras que importan una vez que una aplicación está desplegada.

- **Nombres de archivos hash.** Cada archivo de salida lleva un hash de su contenido en su nombre. Un navegador puede después almacenar en caché un archivo por un largo tiempo, porque un cambio en el contenido produce un nuevo nombre, y el nuevo nombre obliga a una nueva descarga. El almacenamiento en caché se mantiene seguro sin un incremento de versión manual.
- **Salida minificada.** Se eliminan espacios en blanco y código muerto, así que los archivos que descarga un navegador son lo más pequeños que el compilador puede hacerlos.

Una construcción de desarrollo omite ambos. Mantiene nombres estables y salida legible, así que la vigilancia puede intercambiar un archivo en el lugar y puedes leer lo que se carga mientras depuras.

Dado que la minificación es parte de este paso, un proyecto que usa el bundler no necesita nada más para enviar CSS y JavaScript minificados. Para una aplicación que carga activos a través de las [anotaciones de activos](/docs/managing-resources/importing-assets) sin el bundler, el [plugin minificador](/docs/configuration/minifier-plugin) cubre esa minificación de producción en su lugar.

## Paquete ansioso {#eager-bundle}

Por defecto, cada vista carga solo el frontend que usa, cuando se crea un componente de esa clase, así que una vista no paga por nada que no renderiza.

El modo ansioso carga todo el frontend al inicio de la aplicación como un solo paquete, en lugar de por vista. Actívalo con la opción `eager`:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <eager>true</eager>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  eager = true
}
```

</TabItem>
</Tabs>

El modo ansioso está desactivado por defecto, y el modelo por vista se adapta a la mayoría de las aplicaciones. Alcánzalo cuando desees tener todo el frontend en su lugar desde el principio en lugar de cargado a medida que las vistas se renderizan.
