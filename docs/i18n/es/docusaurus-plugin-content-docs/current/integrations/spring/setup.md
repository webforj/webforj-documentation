---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot es una opción popular para construir aplicaciones Java, ofreciendo inyección de dependencias, configuración automática y un modelo de servidor embebido. Cuando utilizas Spring Boot con webforJ, puedes inyectar servicios, repositorios y otros beans gestionados por Spring directamente en tus componentes de UI a través de la inyección por constructor.

Cuando usas Spring Boot con webforJ, tu aplicación se ejecuta como un JAR ejecutable con un servidor Tomcat embebido en lugar de desplegar un archivo WAR en un servidor de aplicaciones externo. Este modelo de empaquetado simplifica el despliegue y se alinea con las prácticas de despliegue nativo en la nube. El modelo de componentes de webforJ y el enrutamiento funcionan junto al contexto de la aplicación de Spring para gestionar dependencias y configuración.

## Crear una aplicación Spring Boot {#create-a-spring-boot-app}

Tienes dos opciones para crear una nueva aplicación webforJ con Spring Boot: utilizando la herramienta gráfica startforJ o la línea de comandos de Maven.

### Opción 1: Usando startforJ {#option-1-using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto inicial mínimo basado en un arquetipo de webforJ elegido. Este proyecto inicial incluye todas las dependencias requeridas, archivos de configuración y un diseño prehecho, para que puedas comenzar a construir sobre él de inmediato.

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la aplicación, ID de grupo, ID de artefacto)  
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

El parámetro `flavor` le indica al arquetipo que genere un proyecto Spring Boot en lugar de un proyecto estándar de webforJ.

Esto crea un proyecto completo de Spring Boot con:
- Configuración del POM padre de Spring Boot
- Dependencia inicial de Spring Boot de webforJ
- Clase principal de la aplicación con `@SpringBootApplication` y `@Routify`
- Vistas de ejemplo
- Archivos de configuración tanto para Spring como para webforJ

## Agregar Spring Boot a proyectos existentes {#add-spring-boot-to-existing-projects}

Si tienes una aplicación webforJ existente, puedes agregar Spring Boot modificando la configuración de tu proyecto. Este proceso implica actualizar tu configuración de Maven, agregar dependencias de Spring y convertir tu clase principal de la aplicación.

:::info[Para proyectos existentes únicamente]
Sáltate esta sección si estás creando un nuevo proyecto desde cero.
:::

### Paso 1: Actualizar la configuración de Maven {#step-1-update-maven-configuration}

Realiza los siguientes cambios en tu archivo POM:

1. Cambia el empaquetado de WAR a JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Establece a Spring Boot como el POM padre:
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

Si ya tienes un POM padre, deberás importar el Bill of Materials (BOM) de Spring Boot en su lugar:

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

Agrega el inicial de Spring Boot de webforJ a tus dependencias. Mantén tu dependencia existente de webforJ:

```xml title="pom.xml"
<dependencies>
    <!-- Tu dependencia existente de webforJ -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Agrega el inicial de Spring Boot -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-spring-boot-starter</artifactId>
        <version>${webforj.version}</version>
    </dependency>

    <!-- Agrega devtools -->
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

:::tip[webforJ DevTools para recarga automática del navegador]
La dependencia `webforj-spring-devtools` extiende Spring DevTools con recarga automática del navegador. Cuando guardas cambios en tu IDE, el navegador se recarga automáticamente sin intervención manual. Consulta la guía de [Spring DevTools](spring-devtools) para detalles de configuración.
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

Transforma tu clase principal `App` en una aplicación Spring Boot agregando las anotaciones necesarias de Spring y un método main:

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

La anotación `@SpringBootApplication` habilita la configuración automática y el escaneo de componentes de Spring. La anotación `@Routify` permanece igual, continuando con el escaneo de tus paquetes de vista para rutas.

### Paso 5: Agregar configuración de Spring {#step-5-add-spring-configuration}

Crea `application.properties` en `src/main/resources`:

```Ini title="application.properties"
# Nombre de la aplicación
spring.application.name=Hola Mundo Spring

# Configuración del servidor
server.port=8080
server.shutdown=immediate

# Configuración de DevTools de webforJ
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Tu archivo existente `webforj.conf` continúa funcionando. Apúntalo a tu clase principal:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Ejecutar la aplicación Spring Boot {#run-the-spring-boot-app}

Una vez configurada, ejecuta tu aplicación usando:

```bash
mvn spring-boot:run
```

La aplicación se inicia con un servidor Tomcat embebido en el puerto 8080 por defecto. Tus vistas y rutas existentes de webforJ funcionan exactamente igual que antes, pero ahora puedes inyectar beans de Spring y usar funciones de Spring.

## Diferencias de configuración {#configuration-differences}

Cuando cambias a Spring Boot, varios aspectos de configuración cambian:

| Aspecto | webforJ estándar | webforJ Spring Boot |
|--------|-----------------|-------------------|
| **Empaquetado** | Archivo WAR | JAR ejecutable |
| **Servidor** | Externo (Jetty, Tomcat) | Tomcat embebido |
| **Comando de ejecución** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Configuración principal** | Solo `webforj.conf` | `webforj.conf` + `application.properties` |
| **Perfiles** | `webforj-dev.conf`, `webforj-prod.conf` | Perfiles de Spring con `application-{profile}.properties` |
| **Configuración de puerto** | En la configuración del plugin | `server.port` en properties |
