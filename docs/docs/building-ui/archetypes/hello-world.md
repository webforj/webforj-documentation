---
title: HelloWorld
sidebar_position: 4
---

<!-- vale off -->
# HelloWorld archetype
<!-- vale on -->

This archetype creates a simple hello world app to demonstrate the basics of building a UI with webforJ. This template is great for beginners to get started quickly. It provides a straightforward example of how to set up and run a basic webforJ app, making it an excellent starting point for new developers.

:::tip Starting from scratch
This archetype creates a minimalistic app with a few components and some styling. For developers wishing to create a project with minimal scaffolding, see the [`blank` archetype](./blank).
:::

:::tip Using startforJ
For more control over customization and configuration, you can use [startforJ](https://docs.webforj.com/startforj/) to create your project - just select the `HelloWorld` archetype when choosing configuration options.
:::

## Using the `hello-world` archetype

<ComponentArchetype
project="hello-world"
/>

## Running the app

Before running your app, install the [prerequisites](../../introduction/prerequisites) if you haven't yet. 
Then, navigate to the project's root directory and run the following command:

```bash
# for standard webforj app
mvn jetty:run

# for webforj + Spring Boot
mvn spring-boot:run
```

Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.