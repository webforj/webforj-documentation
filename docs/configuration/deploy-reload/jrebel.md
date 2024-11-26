---
title: JRebel
---

JRebel is a Java development tool that integrates with the JVM to detect code changes and replace modified classes directly in memory, allowing developers to see code changes immediately without restarting the server. 

When a change is made to a class, method, or field, JRebel compiles and injects the updated bytecode on the fly, eliminating the need for a full server restart. By applying changes directly to the running app, JRebel streamlines the development workflow, saving time and preserving app state, including user sessions.

## Installation

The official JRebel site provides [quick start instructions](https://www.jrebel.com/products/jrebel/learn) to get the product up and running in various popular IDEs. Follow these instructions to integrate JRebel into your development environment.

After setup is complete, open a webforJ project, and ensure that the jetty `scan` property in the `pom.xml` file is set to `0` to disable the automatic restart of the server. Once this is done, use the following command:

```bash
mvn jetty:run
```

If done properly, JRebel will output logging information to the terminal, and changes made to your program should reflect on demand.

:::info Seeing your changes
If a change is made to a view or component that's already being displayed, JRebel won't force a reload of the page, as the server isn't restarted.
:::