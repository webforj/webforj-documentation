---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: 5234e12882e2652d440f8861a6341cef
---
Cada versión de webforJ que se lanza viene acompañada de notas de [lanza detalladas](https://github.com/webforj/webforj/releases) y un artículo de blog sobre el [lanzamiento](/blog/tags/release). Las versiones de instantáneas de webforJ te dan acceso a las últimas características para pruebas mientras se continúa el desarrollo de la versión pre-liberada.

<!-- vale Google.Acronyms = NO -->
Si bien las instantáneas no están listadas públicamente en los sitios del repositorio de Maven como [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) o [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), es fácil acceder al nombre de la instantánea. Para encontrar la versión de instantánea más reciente, navega al [proyecto webforJ](https://github.com/webforj/webforj) en GitHub. Desde allí, busca el [archivo POM del proyecto](https://github.com/webforj/webforj/blob/main/pom.xml) y busca la etiqueta `version`:
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Para utilizar esa versión de instantánea en tu aplicación, usa ese valor como la propiedad `webforj.version` en el archivo POM de tu aplicación:
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## Configurar el repositorio de instantáneas {#configure-the-snapshot-repository}

Debido a que las instantáneas no se publican en Maven Central, necesitas agregar el repositorio Central Portal Snapshots al `pom.xml` de tu aplicación para que Maven pueda resolverlas. Necesitas dos entradas: un `<repository>` para los artefactos de tiempo de ejecución de webforJ, y un `<pluginRepository>` para sus plugins de Maven (como los plugins de instalación y minificación), que también se publican como instantáneas. Ambas entradas desactivan la resolución de lanzamientos para que Maven solo use este repositorio para artefactos de instantánea.

```xml title="pom.xml"
<repositories>
  <repository>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
    <id>central-portal-snapshots</id>
    <name>Central Portal Snapshots</name>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
  </repository>
</repositories>
<pluginRepositories>
  <pluginRepository>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
    <id>central-portal-snapshots</id>
    <name>Central Portal Snapshots</name>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
  </pluginRepository>
</pluginRepositories>
```

Alternativamente, si estás creando una nueva aplicación webforJ, ve a [startforJ](https://docs.webforj.com/startforj/) y elige la versión de webforJ que termina con `(pre)`.

:::warning
Las versiones de instantáneas están en desarrollo activo y están sujetas a cambios, por lo que no se recomiendan para su uso en aplicaciones de producción en vivo.
:::
