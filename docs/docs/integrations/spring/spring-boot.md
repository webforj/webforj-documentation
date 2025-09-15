---
title: Spring Boot
sidebar_position: 10
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

## Add Spring Boot to existing projects {#add-spring-boot-to-existing-projects}

If you have an existing webforJ app, you can add Spring Boot by modifying your project configuration. This process involves updating your Maven configuration, adding Spring dependencies, and converting your main app class.

:::info[For existing projects only]
Skip this section if you're creating a new project from scratch.
:::

### Step 1: Update Maven configuration {#step-1-update-maven-configuration}

Make the following changes to your POM file:

1. Change the packaging from WAR to JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Set Spring Boot as the parent POM:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Remove any WAR-specific configuration such as:
   - `maven-war-plugin`
   - `webapp` directory references
   - `web.xml` related configuration

If you already have a parent POM, you'll need to import the Spring Boot Bill of Materials (BOM) instead:

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

### Step 2: Add Spring dependencies {#step-2-add-spring-dependencies}

Add the webforJ Spring Boot starter to your dependencies. Keep your existing webforJ dependency:

```xml title="pom.xml"
<dependencies>
    <!-- Your existing webforJ dependency -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Add Spring Boot starter -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-spring-boot-starter</artifactId>
        <version>${webforj.version}</version>
    </dependency>

    <!-- Add devtools -->
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

:::tip[webforJ DevTools for automatic browser refresh]
The `webforj-spring-devtools` dependency extends Spring DevTools with automatic browser refresh. When you save changes in your IDE, the browser automatically reloads without manual intervention. See the [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) guide for configuration details.
:::

### Step 3: Update build plugins {#step-3-update-build-plugins}

Replace the Jetty plugin with the Spring Boot Maven plugin. Remove any existing Jetty configuration and add:

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

### Step 4: Convert your app class {#step-4-convert-your-app-class}

Transform your main `App` class into a Spring Boot app by adding the necessary Spring annotations and a main method:

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
    
    // Keep your existing run() method if you have one
    @Override
    public void run() throws WebforjException {
      // Your existing initialization code 
    }
}
```

The `@SpringBootApplication` annotation enables Spring's auto-configuration and component scanning. The `@Routify` annotation remains the same, continuing to scan your view packages for routes.

### Step 5: Add Spring configuration {#step-5-add-spring-configuration}

Create `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Fully qualified class name of application entry point
webforj.entry = org.example.Application

# App Name
spring.application.name=Hello World Spring

# Server configuration
server.port=8080
server.shutdown=immediate

# webforJ DevTools configuration
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Run the Spring Boot app {#run-the-spring-boot-app}

Once configured, run your app using:

```bash
mvn spring-boot:run
```

The app starts with an embedded Tomcat server on port 8080 by default. Your existing webforJ views and routes work exactly as before, but now you can inject Spring beans and use Spring features.

## Configuration

Use the `application.properties` file in `src/main/resources` to configure your app. 
 See [Property Configuration](/docs/configuration/properties.md) for information on webforJ configuration properties.

The following webforJ `application.properties` settings are specific to the Spring framework:

| Property | Type | Description | Default|
|----------|------|-------------|--------|
| **`webforj.servletMapping`** | String | URL mapping pattern for the webforJ servlet. | `/*` |
| **`webforj.excludeUrls`** | List | URL patterns that shouldn't be handled by webforJ when mapped to root. When webforJ is mapped to the root context (`/*`), these URL patterns will be excluded from webforJ handling and can be handled by Spring MVC controllers instead. This allows REST endpoints and other Spring MVC mappings to coexist with webforJ routes. | `[]` |

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
