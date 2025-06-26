---
title: Getting Started
sidebar_position: 2
---


This article outlines the steps to scaffold a new webforJ app using the webforJ archetypes. This archetypes provide a pre-configured project structure and starter code to quickly get a project up and running.

:::tip Prerequisites
Before you begin, make sure you have reviewed the necessary [prerequisites](./prerequisites) for setting up and using webforJ. This will ensure that you have all the required tools and configurations in place before starting your project.
:::

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

## Using startforJ

The simplest way to set up a new webforJ app is by utilizing [startforJ](https://docs.webforj.com/startforj). It generates a minimal starter project based on various available archetypes that includes all required dependencies, configuration files, and a pre-wired layout - ready to build on.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/starforj.mov" type="video/mp4" />
  </video>
</div>

Using startforJ:

- Generates a project with all necessary configuration  
- Prompts you for basic project metadata (e.g., `groupId`, `artifactId`, project name)  
- Allows you to choose your desired webforJ and Java version
- Lets you select a **theme color**, **icon**, and **archetype**  
- Produces a downloadable ZIP, or publishes the project directly to GitHub  

Visit: [https://docs.webforj.com/startforj](https://docs.webforj.com/startforj)

Once downloaded, unzip the project and open the folder in your IDE.

:::tip Available Archetypes
webforJ comes with several predefined archetypes that help you quickly start your webforJ development. To see a complete list of available archetypes, please refer to the [archetypes catalog](../building-ui/archetypes/overview).
:::

## Using the command line

For those who prefer using the command line, you can also generate a project directly using the Maven archetype:

<ComponentArchetype
project="hello-world"
/>

## Run the app

Navigate into the newly created directory, and run the following command from the project’s root directory:

```bash
mvn jetty:run
```

This command uses the Jetty maven plugin to start a Jetty server. Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.

:::info Licensing and watermark
For information on the licensing and the watermark present in unlicensed projects, see [this article](../configuration/licensing-and-watermark).
:::
