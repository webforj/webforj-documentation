---
title: Getting Started
sidebar_position: 2
---

This article outlines the steps to create a new webforJ app using webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes provide pre-configured project structures and starter code so you can get a project up and running quickly.
To create a new webforJ app from an archetype, you can use [startforJ](#using-startforj) or the [command line](#using-the-command-line). 

:::tip Prerequisites
Before you begin, review the necessary [prerequisites](./prerequisites) for setting up and using webforJ.
:::


## Using startforJ {#using-startforj}

The simplest way to create a new webforJ app is [startforJ](https://docs.webforj.com/startforj), which generates a minimal starter project based on a chosen webforJ archetype. This starter project includes all required dependencies, configuration files, and a pre-made layout, so you can start building on it right away.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Customizing with startforJ {#customizing-with-startforj}

When you create an app with [startforJ](https://docs.webforj.com/startforj), you can customize it by providing the following information:

- Basic project metadata (App Name, Group ID, Artifact ID)  
- webforJ version and Java version
- Theme Color and Icon
- Archetype
- Flavor

There are two flavor options to choose from with "webforJ Only" being the default:
  - **webforJ Only**: Standard webforJ app
  - **webforJ + Spring Boot**: webforJ app with Spring Boot support

:::caution Spring Boot support
Spring Boot flavor is only available in webforJ version 25.02 and higher. If you select this option, make sure to choose a compatible version.
:::

:::tip Available Archetypes
webforJ comes with several predefined archetypes to help you get started quickly. For a complete list of available archetypes, see the [archetypes catalog](../building-ui/archetypes/overview).
:::

Using this information, startforJ will create a basic project from your chosen archetype with your chosen customizations.
You can choose to download your project as a ZIP file or publish it directly to GitHub.

Once you have downloaded your project, open the project folder in your IDE and move on to [running the app](#running-the-app).

## Using the command line {#using-the-command-line}


If you prefer to use the command line, you can generate a project directly using the Maven archetype:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>