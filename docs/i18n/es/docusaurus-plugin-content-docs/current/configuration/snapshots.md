---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: a90b2411def220ffa3a2e78af646cf60
---
Cada versión de webforJ lanzada va acompañada de notas de [lanzamiento detalladas](https://github.com/webforj/webforj/releases) y un [artículo de blog sobre el lanzamiento](/blog/tags/release). Las versiones de instantáneo de webforJ te dan acceso a las últimas características para pruebas mientras continúa el desarrollo de la versión pre-lanzada.

<!-- vale Google.Acronyms = NO -->
Mientras que los instantáneos no se enumeran públicamente en los sitios de los repositorios de Maven como [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) o [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), es fácil acceder al nombre del instantáneo. Para encontrar la versión de instantáneo más reciente, navega hasta el [proyecto webforJ](https://github.com/webforj/webforj) en GitHub. Desde allí, encuentra el [archivo POM del proyecto](https://github.com/webforj/webforj/blob/main/pom.xml) y busca la etiqueta `version`:
<!-- vale Google.Acronyms = YES -->

```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
  ```

Para usar esa versión de instantáneo en tu aplicación, utiliza ese valor como la propiedad `webforj.version` en el archivo POM de tu aplicación:

```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

Alternativamente, si estás creando una nueva aplicación webforJ, ve a [startforJ](https://docs.webforj.com/startforj/) y elige la versión de webforJ que termina con `(pre)`.

:::warning
Las versiones de instantáneo están en desarrollo activo y están subjectas a cambios, por lo que no se recomiendan para su uso en aplicaciones de producción en vivo.
:::
