---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 855d2d04609e239338e5a18ebe1b02c4
---
Spring Boot es una opción popular para construir aplicaciones Java, proporcionando inyección de dependencias, auto-configuración y un modelo de servidor embebido. Cuando usas Spring Boot con webforJ, puedes inyectar servicios, repositorios y otros beans gestionados por Spring directamente en tus componentes de UI a través de la inyección por constructor.

Cuando usas Spring Boot con webforJ, tu aplicación se ejecuta como un JAR ejecutable con un servidor Tomcat embebido en lugar de desplegar un archivo WAR en un servidor de aplicaciones externo. Este modelo de empaquetado simplifica el despliegue y se alinea con las prácticas de despliegue nativas de la nube. El modelo de componentes y la enrutación de webforJ trabajan junto con el contexto de la aplicación de Spring para gestionar dependencias y configuración.

## Crear una aplicación Spring Boot {#create-a-spring-boot-app}

Tienes dos opciones para crear una nueva aplicación webforJ con Spring Boot: usar la herramienta gráfica startforJ o la línea de comandos de Maven.

<!-- vale off -->
### Opción 1: Usando startforJ {#option-1-using-startforj}
<!-- vale on -->

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto base mínimo basado en un arquetipo webforJ elegido. Este proyecto base incluye todas las dependencias requeridas, archivos de configuración y un diseño predefinido, para que puedas comenzar a construir sobre él de inmediato.

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la App, ID del Grupo, ID del Artifacto)
- versión de webforJ y versión de Java
- Color y Icono del Tema
- Arquetipo
- **Se ejecuta en** - Selecciona **Spring Boot** para crear un proyecto de Spring Boot

Usando esta información, startforJ creará un proyecto básico a partir de tu arquetipo elegido configurado para Spring Boot. Puedes elegir descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

### Opción 2: Usando la línea de comandos {#option-2-using-the-command-line}

Si prefieres usar la línea de comandos, genera un proyecto webforJ de Spring Boot directamente utilizando los arquetipos oficiales de webforJ:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=mi-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

El parámetro `flavor` le indica al arquetipo que genere un proyecto de Spring Boot en lugar de un proyecto estándar de webforJ.

Esto crea un proyecto completo de Spring Boot con:
- Configuración de POM padre de Spring Boot
- Dependencia de inicio de Spring Boot de webforJ
- Clase principal de la aplicación con `@SpringBootApplication` y `@Routify`
- Vistas de ejemplo
- Archivos de configuración tanto para Spring como para webforJ

## Ejecutar la aplicación Spring Boot {#run-the-spring-boot-app}

Un proyecto de arquetipo establece su objetivo predeterminado de Maven, por lo que `mvn` sin argumentos compila la aplicación, inicia el [frontend watch](/docs/configuration/deploy-reload/frontend-watch) y ejecuta la aplicación:

```bash
mvn
```

La aplicación se inicia con un servidor Tomcat embebido en el puerto 8080 por defecto. Tus vistas y rutas webforJ existentes funcionan exactamente igual que antes, pero ahora puedes inyectar beans de Spring y utilizar funciones de Spring.

## Configuración {#configuration}

Utiliza el archivo `application.properties` en `src/main/resources` para configurar tu aplicación. Consulta [Configuración de Propiedades](/docs/configuration/properties) para obtener información sobre las propiedades de configuración de webforJ.

Las siguientes configuraciones de `application.properties` de webforJ son específicas para Spring:

| Propiedad | Tipo | Descripción | Predeterminado |
|-----------|------|-------------|----------------|
| **`webforj.servlet-mapping`** | String | Patrón de mapeo de URL para el servlet de webforJ. | `/*` |
| **`webforj.exclude-urls`** | Lista | Patrones de URL que no deberían ser manejados por webforJ cuando están mapeados a la raíz. Cuando webforJ está mapeado al contexto raíz (`/*`), estos patrones de URL serán excluidos del manejo de webforJ y pueden ser gestionados por controladores de Spring MVC en su lugar. Esto permite que los endpoints REST y otros mapeos de Spring MVC coexistan con las rutas de webforJ. | `[]` |

### Diferencias en la Configuración {#configuration-differences}

Cuando cambias a Spring Boot, varios aspectos de configuración cambian:

| Aspecto | webforJ estándar | webforJ con Spring Boot |
|---------|------------------|--------------------------|
| **Empaquetado** | Archivo WAR | JAR ejecutable |
| **Servidor** | Externo (Jetty, Tomcat) | Tomcat embebido |
| **Comando de ejecución** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Configuración principal** | Solo `webforj.conf` | `application.properties` + `webforj.conf` |
| **Perfiles** | `webforj-dev.conf`, `webforj-prod.conf` | Perfiles de Spring con `application-{profile}.properties` |
| **Configuración de puerto** | En la configuración del plugin | `server.port` en propiedades |
