---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# SideMenu archetype
<!-- vale on -->

For projects that need a structured navigation system, the `sidemenu` archetype is a great starting place. This archetype contains a side menu and a content area, and is designed to help you create apps with a clear and intuitive navigation structure, making it easier for users to find and access different parts of your app.

:::tip Using startforJ
For more control over customization and configuration, you can use [startforJ](https://docs.webforj.com/startforj/) to create your project - just select the `SideMenu` archetype when choosing configuration options.
:::

## Using the `sidemenu` archetype {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
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

Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.
