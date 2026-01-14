---
title: Project Setup
sidebar_position: 1
description: Discover where to download the tutorial project, how to navigate it, and run the apps within.
---

This tutorial is split up into **four steps**:

1. [Creating a Basic App](/docs/introduction/tutorial/creating-a-basic-app)
2. [Working with Data](/docs/introduction/tutorial/working-with-data)
3. [Scaling with Routing and Composites](/docs/introduction/tutorial/scaling-with-routing-and-composites)
4. [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data)

Each step introduces new features as the project progresses. By following along, you’ll gain a clear understanding of how the app evolves and how each feature is implemented.

To begin, you need to establish a designated location for your project where you can manage your classes and resources. The following sections are different ways you can create your webforJ project for this tutorial:

---

## Using the source code {#using-source-code}

The easiest way to follow this tutorial is to refer to its source code. You can download the entire project or clone it from GitHub:

<!-- vale off -->
- Download ZIP: [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub Repository: Clone the project [directly from GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

### Project structure {#project-structure}

The project has four subdirectories, one for each step of the tutorial, and each contains a runnable app. Following along allows you to see how the app progresses from a basic setup to a fully functional customer management system.

```
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app  
├───2-working-with-data
├───3-scaling-with-routing-and-composites
└───4-validating-and-binding-data
```

---
<!-- vale off -->
## Using startforJ {#using-startforj}
<!-- vale on -->

If you’d prefer to create a new project, you can use [startforJ](https://docs.webforj.com/startforj) to generate a minimal starter project.

:::note Required settings
- In the **webforJ version** dropdown, choose a version of webforJ that's 25.10 or higher.
- In the **Flavor** dropdown, choose the "webforJ + Spring Boot" option. 
:::

---

## Using the command line {#using-command-line}

You can also generate a new project by using the following command line:

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.demos \
  -DartifactId=my-app \
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
  -DgroupId="com.webforj.demos" `
  -DartifactId="my-app" `
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
  -DgroupId="com.webforj.demos" ^
  -DartifactId="my-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->
---

## Configurations

Using webforJ [archetypes](/docs/building-ui/archetypes/overview) will automatically add the needed configurations to your project, like Spring [dependencies](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) to your POM and the following properties in `src/main/resources/application.properties`:

```
spring.application.name=DemoApplication
server.port=8080
webforj.entry = com.webforj.demos.Application
webforj.debug=true
```

## Running the app {#running-the-app}

To see the app in action as you progress through the tutorial:

1. Navigate to the directory for the desired step. This should be the top level directory for that step, containing the `pom.xml`.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```
<!-- vale Google.WordList = NO -->
3. Open your browser and go to http://localhost:8080 to view the app.
:::tip faster deployment
Adding `webforj.devtools.browser.open=true` to `application.properties` automically opens a browser when running the `mvn` command.
:::
<!-- vale Google.WordList = YES -->
