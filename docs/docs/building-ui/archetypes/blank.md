---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Blank archetype

The `blank` archetype is a foundational starter project for webforJ applications. This template provides a clean slate for you to build your app from scratch. It's ideal for developers who want complete control over the structure and components of their app without any predefined constraints.

:::tip Using startforJ
For more control over customization and configuration, you can use [startforJ](https://docs.webforj.com/startforj/) to create your project - just select the `Blank` archetype when choosing configuration options.
:::

## Using the `blank` archetype {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Running the app {#running-the-app}

Before running your app, install the [prerequisites](../../introduction/prerequisites) if you haven't yet. 
Then, navigate to the project's root directory and run the following command:

```bash
# for standard webforJ app
mvn jetty:run

# for webforJ + Spring Boot
mvn spring-boot:run
```

:::tip mvn shorthand
You can use the shorthand `mvn` command instead of the full command above. The archetype's POM file includes a `<defaultGoal>` configuration that automatically runs the appropriate goal for your project type.
:::

Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.
