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


## Using the `hello-world` archetype

<ComponentArchetype
project="hello-world"
/>

:::tip
webforJ comes with several predefined archetypes that help you quickly start your webforJ development. To see a complete list of available archetypes, please refer to the [archetypes catalog](../building-ui/archetypes/overview).
:::

### Run the app

Navigate into the newly created directory, and run the following command from the project’s root directory:

```bash
mvn jetty:run
```

This command uses the Jetty maven plugin to start a Jetty server. Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.

:::info Licensing and watermark
For information on the licensing and the watermark present in unlicensed projects, see [this article](../configuration/licensing-and-watermark).
:::
