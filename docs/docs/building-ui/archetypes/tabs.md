---
title: Tabs
sidebar_position: 2
---

# Tabs archetype

The `tabs` starting project generates an app with a simple tabbed interface. Ideal for projects that require multiple views or sections accessible via tabs, this archetype provides a clean and organized way to manage different parts of your app, making it easy to navigate between various sections without cluttering the user interface.

:::tip Using startforJ
For more control over customization and configuration, you can use [startforJ](https://docs.webforj.com/startforj/) to create your project - just select the `Tabs` archetype when choosing configuration options.
:::

## Using the `tabs` archetype

<ComponentArchetype
project="tabs"
/>

## Running the app

Before running your app, install the [prerequisites](./prerequisites.md) if you haven't yet. 
Then, navigate to the project's root directory and run the following command:

```bash
# for standard webforj app
mvn jetty:run

# for webforj + Spring Boot
mvn spring-boot:run
```

Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.