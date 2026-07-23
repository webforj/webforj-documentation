---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 4512bc42001e5f96301c60758cb0ca81
---
Spring Boot es una opción popular para construir aplicaciones Java, proporcionando inyección de dependencias, auto-configuración y un modelo de servidor embebido. Al usar Spring Boot con webforJ, puedes inyectar servicios, repositorios y otros beans administrados por Spring directamente en tus componentes de UI a través de la inyección por constructor.

Cuando utilizas Spring Boot con webforJ, tu aplicación se ejecuta como un JAR ejecutable con un servidor Tomcat embebido en lugar de desplegar un archivo WAR en un servidor de aplicaciones externo. Este modelo de empaquetado simplifica el despliegue y se alinea con las prácticas de despliegue nativas de la nube. El modelo de componentes de webforJ y el enrutamiento trabajan junto con el contexto de la aplicación de Spring para gestionar dependencias y configuración.

## Crear una aplicación Spring Boot {#create-a-spring-boot-app}

Tienes dos opciones para crear una nueva aplicación webforJ con Spring Boot: usar la herramienta gráfica startforJ o la línea de comandos de Maven.

<!-- vale off -->
### Opción 1: Usando startforJ {#option-1-using-startforj}
<!-- vale on -->

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto de inicio mínimo basado en un arquetipo de webforJ elegido. Este proyecto de inicio incluye todas las dependencias requeridas, archivos de configuración y un diseño prehecho, para que puedas comenzar a construir sobre él de inmediato.

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la Aplicación, ID de Grupo, ID del Artefacto)
- versión de webforJ y versión de Java
- Color del Tema e Icono
- Arquetipo
- **Sabor** - Selecciona **webforJ Spring** para crear un proyecto Spring Boot

Usando esta información, startforJ creará un proyecto básico de tu arquetipo elegido configurado para Spring Boot. Puedes elegir descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

### Opción 2: Usando la línea de comandos {#option-2-using-the-command-line}

Si prefieres usar la línea de comandos, genera un proyecto Spring Boot webforJ directamente utilizando los arquetipos oficiales de webforJ:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

El parámetro `flavor` indica al arquetipo que genere un proyecto Spring Boot en lugar de un proyecto webforJ estándar.

Esto crea un proyecto Spring Boot completo con:
- Configuración del POM padre de Spring Boot
- Dependencia del starter de Spring Boot de webforJ
- Clase principal de la aplicación con `@SpringBootApplication` y `@Routify`
- Vistas de ejemplo
- Archivos de configuración tanto para Spring como para webforJ

## Agregar Spring Boot a proyectos existentes {#add-spring-boot-to-existing-projects}

Si tienes una aplicación webforJ existente, puedes agregar Spring Boot modificando la configuración de tu proyecto. Este proceso implica actualizar tu configuración de Maven, agregar dependencias de Spring y convertir tu clase principal de la aplicación.

:::info[Solo para proyectos existentes]
Omitir esta sección si estás creando un nuevo proyecto desde cero. Esta guía asume que es **webforJ versión 25.11 o posterior**.
:::

### Paso 1: Actualizar la configuración de Maven {#step-1-update-maven-configuration}

Realiza los siguientes cambios en tu archivo POM:

1. Cambia la empaquetado de WAR a JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Establece Spring Boot como el POM padre:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>4.0.5</version>
       <relativePath/>
   </parent>
   ```

3. Elimina cualquier configuración específica de WAR, como:
   - `maven-war-plugin`
   - referencias al directorio `webapp`
   - configuración relacionada con `web.xml`

Si ya tienes un POM padre, necesitarás importar los Materiales de Referencia (BOM) de Spring Boot en su lugar:

```xml title="pom.xml"
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>4.0.5</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### Paso 2: Agregar dependencias de Spring {#step-2-add-spring-dependencies}

Agrega el starter de Spring Boot de webforJ a tus dependencias:

:::info[Simplificación de webforJ 25.11+]
A partir de **webforJ versión 25.11**, el `webforj-spring-boot-starter` incluye todas las dependencias centrales de webforJ de manera transitiva. Ya no necesitas agregar explícitamente la dependencia `com.webforj:webforj`.

Para versiones **anteriores a 25.11**, debes incluir ambas dependencias por separado.
:::

**Para webforJ 25.11 y posterior:**

```xml title="pom.xml"
<dependencies>
  <!-- Agregar starter de Spring Boot (incluye webforJ de manera transitiva) -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Agregar devtools -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-devtools</artifactId>
    <optional>true</optional>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

**Para versiones anteriores a 25.11:**

```xml title="pom.xml"
<dependencies>
  <!-- Agregar explícitamente la dependencia de webforJ -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Agregar starter de Spring Boot -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Agregar devtools -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-devtools</artifactId>
    <optional>true</optional>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

:::tip[DevTools de webforJ para recarga automática del navegador]
La dependencia `webforj-spring-devtools` extiende Spring DevTools con recarga automática del navegador. Cuando guardas cambios en tu IDE, el navegador se recarga automáticamente sin intervención manual. Consulta la guía de [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) para detalles de configuración.
:::

### Paso 3: Actualizar los plugins de construcción {#step-3-update-build-plugins}

Reemplaza el plugin de Jetty con el plugin de Maven de Spring Boot. Elimina cualquier configuración existente de Jetty y agrega:

```xml title="pom.xml"
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
        <excludeDevtools>true</excludeDevtools>
      </configuration>
    </plugin>
  </plugins>
</build>
```

### Paso 4: Convertir tu clase de aplicación {#step-4-convert-your-app-class}

Transforma tu clase principal `App` en una aplicación Spring Boot agregando las anotaciones de Spring necesarias y un método main:

```java title="Application.java"
package com.example;

import com.webforj.App;
import com.webforj.annotation.Routify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify(packages = "com.example.views")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  // Mantén tu método run() existente si tienes uno
  @Override
  public void run() throws WebforjException {
    // Tu código de inicialización existente
  }
}
```

La anotación `@SpringBootApplication` habilita la auto-configuración y el escaneo de componentes de Spring. La anotación `@Routify` permanece igual, continuando con el escaneo de los paquetes de vistas para las rutas.

### Paso 5: Agregar configuración de Spring {#step-5-add-spring-configuration}

Crea `application.properties` en `src/main/resources`:

```Ini title="application.properties"
# Nombre de clase completamente calificado del punto de entrada de la aplicación
webforj.entry = org.example.Application

# Nombre de la Aplicación
spring.application.name=Hola Mundo Spring

# Configuración del servidor
server.port=8080
server.shutdown=immediate

# Configuración de DevTools de webforJ
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Ejecutar la aplicación Spring Boot {#run-the-spring-boot-app}

Una vez configurada, ejecuta tu aplicación usando:

```bash
mvn spring-boot:run
```

La aplicación se inicia con un servidor Tomcat embebido en el puerto 8080 por defecto. Tus vistas y rutas webforJ existentes funcionan exactamente como antes, pero ahora puedes inyectar beans de Spring y usar las características de Spring.

## Configuración {#configuration}

Usa el archivo `application.properties` en `src/main/resources` para configurar tu aplicación. Consulta [Configuración de Propiedades](/docs/configuration/properties) para obtener información sobre las propiedades de configuración de webforJ.

Las siguientes configuraciones de `application.properties` de webforJ son específicas para Spring:

| Propiedad | Tipo | Descripción | Por defecto|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | Patrón de mapeo de URL para el servlet de webforJ. | `/*` |
| **`webforj.exclude-urls`** | List | Patrones de URL que no deberían ser manejados por webforJ cuando se mapean a la raíz. Cuando webforJ está mapeado al contexto raíz (`/*`), estos patrones de URL serán excluidos del manejo de webforJ y pueden ser manejados por controladores de Spring MVC en su lugar. Esto permite que los endpoints REST y otros mapeos de Spring MVC coexistan con las rutas de webforJ. | `[]` |

### Diferencias en la configuración {#configuration-differences}

Cuando cambias a Spring Boot, varios aspectos de la configuración cambian:

| Aspecto | webforJ estándar | webforJ Spring Boot |
|--------|-----------------|-------------------|
| **Empaquetado** | Archivo WAR | JAR ejecutable |
| **Servidor** | Externo (Jetty, Tomcat) | Tomcat embebido |
| **Comando de ejecución** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Configuración principal** | Solo `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Perfiles** | `webforj-dev.conf`, `webforj-prod.conf` | Perfiles de Spring con `application-{profile}.properties` |
| **Configuración de puertos** | En configuración de plugins | `server.port` en propiedades |
