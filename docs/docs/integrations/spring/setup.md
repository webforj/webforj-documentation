---
title: Spring Boot setup
sidebar_position: 10
---

import ComponentArchetype from '@site/src/components/DocsTools/ComponentArchetype';

This article explains how to create a new webforJ app with Spring Boot integration or add Spring Boot to an existing webforJ project.

## Create a Spring Boot app

You have two options for creating a new webforJ app with Spring Boot: using the graphical startforJ tool or the Maven command line.

### Option 1: Using startforJ

The simplest way to create a new webforJ app is [startforJ](https://docs.webforj.com/startforj), which generates a minimal starter project based on a chosen webforJ archetype. This starter project includes all required dependencies, configuration files, and a pre-made layout, so you can start building on it right away.

When you create an app with [startforJ](https://docs.webforj.com/startforj), you can customize it by providing the following information:

- Basic project metadata (App Name, Group ID, Artifact ID)  
- webforJ version and Java version
- Theme Color and Icon
- Archetype
- **Flavor** - Select **webforJ Spring** to create a Spring Boot project

Using this information, startforJ will create a basic project from your chosen archetype with Spring Boot integration.
You can choose to download your project as a ZIP file or publish it directly to GitHub.

### Option 2: Using the command line

If you prefer to use the command line, generate a Spring Boot webforJ project directly using the [webforJ archetypes](../../building-ui/archetypes/overview):


```bash
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
- Example views showing Spring integration
- Configuration files for both Spring and webforJ

## Add Spring Boot to existing projects

If you have an existing webforJ app, you can add Spring Boot integration by modifying your project configuration. This process involves updating your Maven configuration, adding Spring dependencies, and converting your main app class.

### Step 1: Update Maven configuration

First, change your POM to use Spring Boot as the parent. Replace any existing parent configuration with:

```xml title="pom.xml"
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.3</version>
    <relativePath/>
</parent>
```

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

### Step 2: Add Spring dependencies

Add the webforJ Spring Boot starter to your dependencies. Keep your existing webforJ dependency:

:::tip
After configuring Spring Boot, you can use [Spring Data JPA](spring-data-jpa) for database integration and [Spring DevTools](spring-devtools) for automatic browser refresh during development.
:::

```xml title="pom.xml"
<dependencies>
    <!-- Your existing webforJ dependency -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Add Spring Boot integration -->
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

### Step 3: Update build plugins

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

### Step 4: Convert your app class

Transform your main App class into a Spring Boot app. Add the necessary Spring annotations and a main method:

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
    public void run() {
        // Your existing initialization code
    }
}
```

The `@SpringBootApplication` annotation enables Spring's auto-configuration and component scanning. The `@Routify` annotation remains the same, continuing to scan your view packages for routes.

### Step 5: Add Spring configuration

Create `application.properties` in `src/main/resources`:

```Ini application.properties
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

Your existing `webforj.conf` file continues to work. Point it to your main class:

```Ini webforj.conf
webforj.entry = org.example.Application
```

### Step 6: Update packaging configuration

Change your packaging from WAR to JAR in your POM:
 
```xml title="pom.xml"
<packaging>jar</packaging>
```

Remove any WAR-specific configuration like the `maven-war-plugin` and `webapp` directory references.

## Run the Spring Boot app

Once configured, run your app using:

```bash
mvn spring-boot:run
```

The app starts with an embedded Tomcat server on port 8080 by default. Your existing webforJ views and routes work exactly as before, but now you can inject Spring beans and use Spring features.

## Configuration differences

When you switch to Spring Boot, several configuration aspects change:

| Aspect | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Packaging** | WAR file | Executable JAR |
| **Server** | External (Jetty, Tomcat) | Embedded Tomcat |
| **Run command** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Main config** | `webforj.conf` only | `webforj.conf` + `application.properties` |
| **Profiles** | `webforj-dev.conf`, `webforj-prod.conf` | Spring profiles with `application-{profile}.properties` |
| **Port config** | In plugin configuration | `server.port` in properties |

## Using Spring features

With Spring Boot integration active, your webforJ components can now:

- Inject Spring beans
- Access Spring-managed services and repositories  
- Use Spring's transaction management
- Integrate with Spring Security (when added)
- Benefit from Spring's extensive ecosystem

:::tip
For comprehensive Spring Boot features beyond basic dependency injection, refer to [Spring Boot's official documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/).
:::

Example of a webforJ view with Spring dependency injection:

```java title="RandomNumberService.java"
@Service
public class RandomNumberService {

  public Integer getRandomNumber() {
    return (int) (Math.random() * 100);
  }
}
```

```java title="HelloWorldView.java"
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private Button btn = new Button("Generate Random Number");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("The new random number is " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

