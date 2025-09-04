---
title: Spring Boot
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot es una opción popular para construir aplicaciones Java, proporcionando inyección de dependencias, auto-configuración y un modelo de servidor embebido. Al utilizar Spring Boot con webforJ, puedes inyectar servicios, repositorios y otros beans gestionados por Spring directamente en tus componentes de interfaz de usuario a través de la inyección por constructor.

Cuando usas Spring Boot con webforJ, tu aplicación se ejecuta como un JAR ejecutable con un servidor Tomcat embebido en lugar de desplegar un archivo WAR en un servidor de aplicaciones externo. Este modelo de empaquetado simplifica el despliegue y se alinea con las prácticas de despliegue nativas en la nube. El modelo de componentes y enrutamiento de webforJ funciona junto con el contexto de aplicación de Spring para gestionar dependencias y configuraciones.

## Crear una aplicación Spring Boot {#create-a-spring-boot-app}

Tienes dos opciones para crear una nueva aplicación webforJ con Spring Boot: utilizar la herramienta gráfica startforJ o la línea de comandos de Maven.

### Opción 1: Usando startforJ {#option-1-using-startforj}

La forma más simple de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto base mínimo basado en un arquetipo webforJ elegido. Este proyecto base incluye todas las dependencias necesarias, archivos de configuración y un diseño predefinido, para que puedas comenzar a construir sobre él de inmediato.

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la Aplicación, ID del Grupo, ID del Artefacto)  
- Versión de webforJ y versión de Java
- Color del tema e ícono
- Arquetipo
- **Sabor** - Selecciona **webforJ Spring** para crear un proyecto Spring Boot

Utilizando esta información, startforJ creará un proyecto básico a partir de tu arquetipo elegido configurado para Spring Boot.
Puedes elegir descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

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
- Dependencia del iniciador de Spring Boot de webforJ
- Clase principal de la aplicación con `@SpringBootApplication` y `@Routify`
- Vistas de ejemplo
- Archivos de configuración tanto para Spring como para webforJ

## Agregar Spring Boot a proyectos existentes {#add-spring-boot-to-existing-projects}

Si tienes una aplicación webforJ existente, puedes agregar Spring Boot modificando la configuración de tu proyecto. Este proceso implica actualizar tu configuración de Maven, agregar dependencias de Spring y convertir tu clase principal de la aplicación.

:::info[Solo para proyectos existentes]
Salta esta sección si estás creando un nuevo proyecto desde cero.
:::

### Paso 1: Actualizar la configuración de Maven {#step-1-update-maven-configuration}

Realiza los siguientes cambios en tu archivo POM:

1. Cambia el empaquetado de WAR a JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Establece Spring Boot como el POM padre:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Elimina cualquier configuración específica de WAR como:
   - `maven-war-plugin`
   - Referencias al directorio `webapp`
   - Configuración relacionada con `web.xml`

Si ya tienes un POM padre, necesitarás importar el Bill of Materials (BOM) de Spring Boot en su lugar:

```xml title="pom.xml"
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.5.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Paso 2: Agregar dependencias de Spring {#step-2-add-spring-dependencies}

Agrega el iniciador de Spring Boot de webforJ a tus dependencias. Mantén tu dependencia de webforJ existente:

```xml title="pom.xml"
<dependencies>
    <!-- Tu dependencia existente de webforJ -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Agregar el iniciador de Spring Boot -->
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

:::tip[webforJ DevTools para actualización automática del navegador]
La dependencia `webforj-spring-devtools` extiende Spring DevTools con actualización automática del navegador. Cuando guardas cambios en tu IDE, el navegador se recarga automáticamente sin intervención manual. Consulta la guía de [Spring DevTools](spring-devtools) para detalles de configuración.
:::

### Paso 3: Actualizar plugins de construcción {#step-3-update-build-plugins}

Reemplaza el plugin de Jetty con el plugin de Maven de Spring Boot. Elimina cualquier configuración existente de Jetty y añade:

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

La anotación `@SpringBootApplication` habilita la auto-configuración y el escaneo de componentes de Spring. La anotación `@Routify` permanece igual, continuando con el escaneo de tus paquetes de vista para rutas.

### Paso 5: Agregar configuración de Spring {#step-5-add-spring-configuration}

Crea `application.properties` en `src/main/resources`:

```Ini title="application.properties"
# Nombre de la aplicación
spring.application.name=Hello World Spring

# Configuración del servidor
server.port=8080
server.shutdown=immediate

# Configuración de DevTools de webforJ
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Tu archivo `webforj.conf` existente sigue funcionando. Apúntalo a tu clase principal:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Ejecutar la aplicación Spring Boot {#run-the-spring-boot-app}

Una vez configurada, ejecuta tu aplicación usando:

```bash
mvn spring-boot:run
```

La aplicación se inicia con un servidor Tomcat embebido en el puerto 8080 por defecto. Tus vistas y rutas webforJ existentes funcionan exactamente como antes, pero ahora puedes inyectar beans de Spring y utilizar funcionalidades de Spring.

## Diferencias de configuración {#configuration-differences}

Cuando cambias a Spring Boot, varios aspectos de configuración cambian:

| Aspecto | webforJ estándar | webforJ Spring Boot |
|---------|-----------------|---------------------|
| **Empaquetado** | Archivo WAR | JAR ejecutable |
| **Servidor** | Externo (Jetty, Tomcat) | Tomcat embebido |
| **Comando de ejecución** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Config. principal** | Solo `webforj.conf` | `webforj.conf` + `application.properties` |
| **Perfiles** | `webforj-dev.conf`, `webforj-prod.conf` | Perfiles de Spring con `application-{profile}.properties` |
| **Config. de puerto** | En configuración del plugin | `server.port` en propiedades |
