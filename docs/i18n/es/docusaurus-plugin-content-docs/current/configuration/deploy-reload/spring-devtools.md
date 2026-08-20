---
title: Spring Boot
sidebar_position: 30
description: >-
  Set up live reload in a Spring Boot webforJ app, with the development tools
  delivered by the webforJ build plugin.
_i18n_hash: 2fa5b74377a864e82b67db98ee8c9c04
---
En una aplicación de Spring Boot, el [plugin de construcción webforJ](/docs/configuration/build-plugin) entrega las herramientas de desarrollo para las ejecuciones de desarrollo. El proyecto no declara ninguna dependencia para ellas, y nunca forman parte de la aplicación empaquetada.

## Requisitos {#requirements}

La dependencia inicial y el plugin de construcción. Un proyecto creado a partir de un [arquetipo](/docs/introduction/getting-started) tiene ambos.

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-boot-starter</artifactId>
</dependency>
```

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-spring-boot-starter'
}
```

con el [plugin de webforJ aplicado a la construcción](/docs/configuration/build-plugin#adding-the-plugin).

</TabItem>
</Tabs>

## Activar la recarga en vivo {#turning-live-reload-on}

```Ini title="application.properties"
webforj.devtools.livereload.enabled=true
server.shutdown=immediate
```

Inicie la aplicación como de costumbre, `mvn` con Maven o `./gradlew bootRun` con Gradle. Los cambios en Java se aplican después de una compilación, los cambios en estilos y en imágenes se aplican en su lugar, y las fuentes bajo `src/main/frontend` se reconstruyen a través de la [observación del frontend](/docs/configuration/deploy-reload/frontend-watch). Las claves restantes se enumeran en los [ajustes](/docs/configuration/deploy-reload/overview#settings).

## Spring DevTools {#spring-devtools}

Spring DevTools es opcional, la recarga en vivo funciona sin él. Para usar su modelo de reinicio, agregue su dependencia:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  developmentOnly 'org.springframework.boot:spring-boot-devtools'
}
```

</TabItem>
</Tabs>

Con Spring DevTools presente, un cambio compilado reinicia el contexto de Spring y el navegador se actualiza cuando el reinicio se completa. Con una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) configurada también, la herramienta aplica las actualizaciones de clases y el reinicio se mantiene apagado.

## Construcciones de producción {#production-builds}

`mvn package` y `./gradlew bootJar` producen una aplicación sin herramientas de desarrollo, sin exclusiones, perfiles o propiedades requeridas. La propiedad `webforj.devtools.livereload.enabled` no tiene efecto en una aplicación empaquetada.
