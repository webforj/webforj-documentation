---
title: Getting Started
sidebar_position: 2
---

This article outlines the steps to scaffold a new webforJ app using the webforJ archetypes. This archetypes provide a pre-configured project structure and starter code to quickly get a project up and running.

:::tip Prerequisites
Before you begin, ensure you have read about the necessary [prerequisites](./prerequisites) for setting up and using webforJ. 
:::

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->


## Using the `hello-world` archetype

To create and scaffold a new `hello-world` project, follow these steps:

1) **Navigate to the proper directory**:
Open a terminal and move to the folder where you want to create your new project.

2) **Run the `archetype:generate` command**:
Use the Maven command below, and customize the `groupId`, `artifactId`, and `version` as needed for your project.

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
  ```bash
  mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DgroupId=org.example \
  -DarchetypeVersion=LATEST \
  -DartifactId=my-hello-world-app \
  -Dversion=1.0-SNAPSHOT
  ```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
  ```powershell
  mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="org.example" `
  -DartifactId="my-hello-world-app" `
  -Dversion="1.0-SNAPSHOT" 
  ```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
  ```
  mvn -B archetype:generate ^
  -DarchetypeGroupId=com.webforj ^
  -DarchetypeArtifactId=webforj-archetype-hello-world ^
  -DgroupId=org.example ^
  -DarchetypeVersion=LATEST ^
  -DartifactId=my-hello-world-app ^
  -Dversion=1.0-SNAPSHOT
  ```
  </TabItem>
</Tabs>
<!-- vale on -->

| Argument             | Explanation                                                                 |
|----------------------|-----------------------------------------------------------------------------|
| `archetypeGroupId` | The group ID of the archetype is `com.webforj` for webforJ archetypes.|
| `archetypeArtifactId` | Specifies the name of the archetype to use. |
| `archetypeVersion` | Specifies the version of the archetype to use. This ensures that the generated project is compatible with a specific archetype version. Using LATEST selects the most recent version available.|
| `groupId`          | Represents the namespace for the generated project. Typically structured like a Java package, such as `org.example` and is used to uniquely identify your organization or project domain.|
| `artifactId`       | Specifies the name of the generated project. This will be the name of the resulting artifact and the project folder.|
| `version`          | Defines the version of the generated project. A common convention is MAJOR.MINOR-SNAPSHOT, like `1.0-SNAPSHOT`, where SNAPSHOT denotes that the project is still in development.|


After running the command, Maven will generate the project files necessary to run the project.

### Run the app

Navigate into the newly created directory, and run the following command from the project’s root directory:

```bash
mvn jetty:run
```

This command uses the Jetty maven plugin to start a Jetty server. Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.

:::info Licensing and watermark
For information on the licensing and the watermark present in unlicensed projects, see [this article](../configuration/licensing-and-watermark).
:::
