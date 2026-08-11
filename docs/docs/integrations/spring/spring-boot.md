---
title: Spring Boot
sidebar_position: 10
description: Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or convert an existing WAR project to an embedded Tomcat JAR.
---

Spring Boot is a popular choice for building Java apps, providing dependency injection, auto-configuration, and an embedded server model. When using Spring Boot with webforJ, you can inject services, repositories, and other Spring-managed beans directly into your UI components through constructor injection.

When you use Spring Boot with webforJ, your app runs as an executable JAR with an embedded Tomcat server instead of deploying a WAR file to an external app server. This packaging model simplifies deployment and aligns with cloud-native deployment practices. webforJ's component model and routing work alongside Spring's app context for managing dependencies and configuration.

## Create a Spring Boot app {#create-a-spring-boot-app}

You have two options for creating a new webforJ app with Spring Boot: using the graphical startforJ tool or the Maven command line.

<!-- vale off -->
### Option 1: Using startforJ {#option-1-using-startforj}
<!-- vale on -->

The simplest way to create a new webforJ app is [startforJ](https://docs.webforj.com/startforj), which generates a minimal starter project based on a chosen webforJ archetype. This starter project includes all required dependencies, configuration files, and a pre-made layout, so you can start building on it right away.

When you create an app with [startforJ](https://docs.webforj.com/startforj), you can customize it by providing the following information:

- Basic project metadata (App Name, Group ID, Artifact ID)
- webforJ version and Java version
- Theme Color and Icon
- Archetype
- **Flavor** - Select **webforJ Spring** to create a Spring Boot project

Using this information, startforJ will create a basic project from your chosen archetype configured for Spring Boot.
You can choose to download your project as a ZIP file or publish it directly to GitHub.

### Option 2: Using the command line {#option-2-using-the-command-line}

If you prefer to use the command line, generate a Spring Boot webforJ project directly using the official webforJ archetypes:

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

The `flavor` parameter tells the archetype to generate a Spring Boot project instead of a standard webforJ project.

This creates a complete Spring Boot project with:
- Spring Boot parent POM configuration
- webforJ Spring Boot starter dependency
- Main app class with `@SpringBootApplication` and `@Routify`
- Example views
- Configuration files for both Spring and webforJ

## Run the Spring Boot app {#run-the-spring-boot-app}

An archetype project sets its default Maven goal, so `mvn` with no arguments compiles the app, starts the [frontend watch](/docs/configuration/deploy-reload/frontend-watch), and runs the app:

```bash
mvn
```

The app starts with an embedded Tomcat server on port 8080 by default. Your existing webforJ views and routes work exactly as before, but now you can inject Spring beans and use Spring features.

## Configuration {#configuration}

Use the `application.properties` file in `src/main/resources` to configure your app.
 See [Property Configuration](/docs/configuration/properties) for information on webforJ configuration properties.

The following webforJ `application.properties` settings are specific to Spring:

| Property | Type | Description | Default|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | URL mapping pattern for the webforJ servlet. | `/*` |
| **`webforj.exclude-urls`** | List | URL patterns that shouldn't be handled by webforJ when mapped to root. When webforJ is mapped to the root context (`/*`), these URL patterns will be excluded from webforJ handling and can be handled by Spring MVC controllers instead. This allows REST endpoints and other Spring MVC mappings to coexist with webforJ routes. | `[]` |

### Configuration differences {#configuration-differences}

When you switch to Spring Boot, several configuration aspects change:

| Aspect | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Packaging** | WAR file | Executable JAR |
| **Server** | External (Jetty, Tomcat) | Embedded Tomcat |
| **Run command** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Main config** | `webforj.conf` only | `application.properties` + `webforj.conf`  |
| **Profiles** | `webforj-dev.conf`, `webforj-prod.conf` | Spring profiles with `application-{profile}.properties` |
| **Port config** | In plugin configuration | `server.port` in properties |
