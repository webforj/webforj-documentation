---
title: Snapshots
sidebar_position: 30
hide_table_of_contents: true
description: >-
  Locate the latest webforJ snapshot version and add the Central Portal
  Snapshots repository to consume pre-release builds.
_i18n_hash: 646ace835d5ba39ed182935e8d7f33fb
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Cada versión de webforJ lanzada está acompañada de notas de [lanzamiento](https://github.com/webforj/webforj/releases) y un artículo en el [blog de lanzamiento](/blog/tags/release).
Las versiones snapshot de webforJ te dan acceso a las últimas características para pruebas mientras el desarrollo continúa en la versión no publicada.

<!-- vale Google.Acronyms = NO -->
Aunque los snapshots no están listados públicamente en sitios de repositorios de Maven como [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) o [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), es fácil acceder al nombre del snapshot. Para encontrar la versión snapshot más nueva, navega al [proyecto de webforJ](https://github.com/webforj/webforj) en GitHub. Desde allí, busca el [archivo POM](https://github.com/webforj/webforj/blob/main/pom.xml) del proyecto y busca la etiqueta `version`:
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Para usar esa versión snapshot en tu aplicación, usa ese valor como la propiedad `webforj.version` en el archivo POM de tu aplicación:
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## Configurar el repositorio de snapshots {#configure-the-snapshot-repository}

Debido a que los snapshots no se publican en Maven Central, necesitas agregar el repositorio de Snapshots de Central Portal al `pom.xml` de tu aplicación para que Maven pueda resolverlos. Necesitas dos entradas: un `<repository>` para los artefactos de tiempo de ejecución de webforJ, y un `<pluginRepository>` para sus plugins de Maven (como los plugins de instalación y minificación), que también se publican como snapshots. Ambas entradas desactivan la resolución de versiones de lanzamiento para que Maven solo use este repositorio para artefactos snapshot.

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
Las versiones snapshot están en desarrollo activo y están sujetas a cambios, por lo que no se recomiendan para su uso en aplicaciones de producción en vivo.
:::
