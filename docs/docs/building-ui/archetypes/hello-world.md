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

## Using the `hello-world` archetype

<ComponentArchetype
project="hello-world"
/>

### Run the app

Navigate into the newly created directory, and run the following command from the project’s root directory:

```bash
mvn jetty:run
```

This command uses the Jetty maven plugin to start a Jetty server. Once the server is running, open your browser and go to [http://localhost:8080](http://localhost:8080) to view the app.
