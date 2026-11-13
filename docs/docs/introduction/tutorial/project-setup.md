---
title: Project Setup
sidebar_position: 1
description: Discover where to download the tutorial project, how to navigate it, and run the apps within.
---

To begin this tutorial, you need a location for your project where you can manage your classes and resources. The following sections describe the different ways you can create your webforJ project for this tutorial.

## Using the source code {#using-source-code}

The easiest way to follow this tutorial is to refer to its source code. You can download the entire project or clone it from GitHub:

<!-- vale off -->
- Download ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub Repository: Clone the project [directly from GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div> -->

### Project structure {#project-structure}

The project has six subdirectories, one for each step of the tutorial, and each contains a runnable app. Following along allows you to see how the app progresses from a basic setup to a fully functional customer management system.

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

<!-- vale off -->
## Using startforJ {#using-startforj}
<!-- vale on -->

If you’d prefer to create a new project, you can use [startforJ](https://docs.webforj.com/startforj) to generate a minimal starter project. See [Getting Started](/docs/introduction/getting-started) for more detailed information about using startforJ.

:::note Required settings
- In the **webforJ version** dropdown, choose webforJ version **26.01 or higher**.
- Under **Runs on**, choose **Spring Boot**.

## Using the command line {#using-command-line}

You can also generate a new project with the following command:

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## Configurations {#configurations}

The two mentioned ways of creating a new project use webforJ [archetypes](/docs/building-ui/archetypes/overview), which automatically add the needed configurations to your project. This includes Spring [dependencies](/docs/integrations/spring/spring-boot), the webforJ Maven plugin that builds and watches frontend sources, and the following properties in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Testing setup {#testbed-setup}

The testing sections use [TestBed](/docs/testing/testbed/overview), available since webforJ **26.03**. The tutorial's [testbed branch](https://github.com/webforj/webforj-tutorial/tree/testbed) includes a complete test and its configuration for each step. To download those examples into a separate directory:

```bash
git clone --branch testbed https://github.com/webforj/webforj-tutorial.git webforj-tutorial-testbed
```

If you're building your own project, use webforJ 26.03 or higher and add the Spring TestBed module to your `pom.xml`. Its version comes from the webforJ bill of materials already imported by the generated project:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

Keep the generated `spring-boot-starter-test` dependency as well; it provides Spring's testing support and JUnit. Put test classes in `src/test/java/com/webforj/tutorial` and create a test profile to turn off development tools:

```properties title="src/test/resources/application-test.properties"
spring.devtools.livereload.enabled=false
webforj.devtools.browser.open=false
webforj.devtools.livereload.enabled=false
webforj.devtools.livereload.static-resources-enabled=false
webforj.devtools.craftforj.enabled=false
webforj.debug=false
```

For steps 2 through 6, also add `spring.jpa.hibernate.ddl-auto=create-drop` to this test profile. These steps use the tutorial's H2 database; Spring creates the schema for the test context and loads the initial customer records through `DataInitializer`.

Each test class combines `@SpringBootTest`, `@ActiveProfiles("test")`, and `@WebforjTest`. Spring starts the app context and injects services; TestBed creates a fresh page for each test method. The Spring context remains cached across methods. The database examples also use `@Transactional` so each test's database changes roll back when it finishes.

From the directory containing the current step's `pom.xml`, run:

```bash
mvn test
```

This runs the JUnit tests without launching the development server. For more details about the annotations and lifecycle, see [Spring testing](/docs/integrations/spring/testing).

## Optional AI and craftforJ setup {#optional-ai-and-craftforj-setup}

For the optional AI checkpoints, open the current step's project directory in Claude Code and install the [webforJ AI plugin](/docs/ai-tooling#installation). Follow that guide to verify the plugin is connected before asking it to work on your app. Give Claude the current lesson and ask it to use the webforJ documentation when explaining or changing code. Review its changes and run the app to check the result.

The craftforJ checkpoints require webforJ **26.02 or higher**, a developer license, and a browser on the machine running your app. You can complete the tutorial without these optional checkpoints on an earlier supported version. Follow [Getting Started with craftforJ](/docs/craftforj/getting-started#requirements) to check the `webforj-devtools` dependency and enable both debug mode and craftforJ in `application.properties`.

New projects generated with current startforJ or archetypes include craftforJ setup. For cloned tutorial examples, check the POM and properties in each step's directory before using it. Restart the app after changing its configuration, then open craftforJ with <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>.

craftforJ also has its own [AI assistant](/docs/craftforj/ai). To use the assistant prompts in this tutorial, configure a supported model first. This is separate from Claude Code and doesn't require choosing Claude as the model.

## Running the app {#running-the-app}

To see the app in action as you progress through the tutorial:

1. Navigate to the directory for the desired step. This should be the top-level directory for that step, containing the `pom.xml`.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

   The generated POM configures this default command to compile the app, start the webforJ frontend watcher, and run Spring Boot.

<!-- vale Google.WordList = NO -->
Running the app automatically opens a new browser at `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
