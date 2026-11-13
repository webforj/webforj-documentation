---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 98535c8b91fd041be0dcb32840ef9302
---
Spring Boot es una opción popular para crear aplicaciones Java, ya que proporciona inyección de dependencias, configuración automática y un modelo de servidor integrado. Al usar Spring Boot con webforJ, puedes inyectar servicios, repositorios y otros beans administrados por Spring directamente en tus componentes de interfaz de usuario mediante la inyección por constructor.

Cuando usas Spring Boot con webforJ, tu aplicación se ejecuta como un JAR ejecutable con un servidor Tomcat integrado, en lugar de implementar un archivo WAR en un servidor de aplicaciones externo. Este modelo de empaquetado simplifica la implementación y se alinea con las prácticas de implementación nativas de la nube. El modelo de componentes y el enrutamiento de webforJ funcionan junto con el contexto de la aplicación de Spring para administrar dependencias y configuración.

## Crear una aplicación de Spring Boot {#create-a-spring-boot-app}

Tienes dos opciones para crear una nueva aplicación de webforJ con Spring Boot: usar la herramienta gráfica startforJ o la línea de comandos de Maven.

<!-- vale off -->
### Opción 1: Usar startforJ {#option-1-using-startforj}
<!-- vale on -->

La forma más sencilla de crear una nueva aplicación de webforJ es usar [startforJ](https://docs.webforj.com/startforj), que genera un proyecto inicial mínimo basado en el arquetipo de webforJ que elijas. Este proyecto inicial incluye todas las dependencias necesarias, los archivos de configuración y un diseño prediseñado, para que puedas empezar a trabajar en él de inmediato.

Al crear una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (nombre de la aplicación, ID de grupo, ID de artefacto)
- Versión de webforJ y versión de Java
- Color del tema e ícono
- Arquetipo
- **Se ejecuta en**: selecciona **Spring Boot** para crear un proyecto de Spring Boot

Con esta información, startforJ creará un proyecto básico basado en el arquetipo que hayas elegido y configurado para Spring Boot.
Puedes descargar el proyecto como archivo ZIP o publicarlo directamente en GitHub.

### Opción 2: Usar la línea de comandos {#option-2-using-the-command-line}

Si prefieres usar la línea de comandos, genera directamente un proyecto de webforJ con Spring Boot mediante los arquetipos oficiales de webforJ:

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

El parámetro `flavor` indica al arquetipo que genere un proyecto de Spring Boot en lugar de un proyecto estándar de webforJ.

Esto crea un proyecto completo de Spring Boot con:
- Configuración del POM principal de Spring Boot
- Dependencia del starter de Spring Boot para webforJ
- Clase principal de la aplicación con `@SpringBootApplication` y `@Routify`
- Vistas de ejemplo
- Archivos de configuración tanto para Spring como para webforJ

## Ejecutar la aplicación de Spring Boot {#run-the-spring-boot-app}

Un proyecto de arquetipo establece su objetivo predeterminado de Maven, por lo que `mvn` sin argumentos compila la aplicación, inicia el [observador del frontend](/docs/configuration/deploy-reload/frontend-watch) y ejecuta la aplicación:

```bash
mvn
```

De forma predeterminada, la aplicación se inicia con un servidor Tomcat integrado en el puerto 8080. Las vistas y rutas existentes de webforJ funcionan exactamente igual que antes, pero ahora puedes inyectar beans de Spring y usar las funcionalidades de Spring.

## Configuración {#configuration}

Usa el archivo `application.properties` en `src/main/resources` para configurar tu aplicación.
Consulta [Configuración de propiedades](/docs/configuration/properties) para obtener información sobre las propiedades de configuración de webforJ.

Las siguientes opciones de `application.properties` de webforJ son específicas de Spring:

| Propiedad | Tipo | Descripción | Valor predeterminado|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | Patrón de asignación de URL para el servlet de webforJ. | `/*` |
| **`webforj.exclude-urls`** | List | Patrones de URL que webforJ no debe gestionar cuando está asignado a la raíz. Cuando webforJ está asignado al contexto raíz (`/*`), estos patrones de URL se excluyen de la gestión de webforJ y pueden ser gestionados por los controladores de Spring MVC. Esto permite que los endpoints REST y otras asignaciones de Spring MVC coexistan con las rutas de webforJ. | `[]` |

### Diferencias de configuración {#configuration-differences}

Al cambiar a Spring Boot, varios aspectos de la configuración cambian:

| Aspecto | webforJ estándar | webforJ con Spring Boot |
|--------|-----------------|-------------------|
| **Empaquetado** | Archivo WAR | JAR ejecutable |
| **Servidor** | Externo (Jetty, Tomcat) | Tomcat integrado |
| **Comando de ejecución** | `mvn` | `mvn` |
| **Configuración principal** | Solo `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Perfiles** | `webforj-dev.conf`, `webforj-prod.conf` | Perfiles de Spring con `application-{profile}.properties` |
| **Configuración del puerto** | En la configuración del complemento | `server.port` en las propiedades |

:::info Ejecuta con `mvn`, no con `mvn spring-boot:run`
`mvn spring-boot:run` solo inicia la aplicación. No inicia el [observador del frontend](/docs/configuration/deploy-reload/frontend-watch), por lo que las fuentes de `src/main/frontend`, si tu proyecto tiene alguna, no se compilan ni se reconstruyen mientras se ejecuta la aplicación. `mvn` sin argumentos ejecuta el objetivo predeterminado del proyecto, que inicia el observador y luego la aplicación.
:::
