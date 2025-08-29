---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
---

Efficient development workflows rely on tools that detect code changes and automatically update the app in real time. Continuous Deployment and Dynamic Reload work together to simplify the development process by reducing manual steps, allowing you to see your changes quickly without needing to manually restart the server.

## Redeployment {#redeployment}

Redeployment in Java development refers to automatically detecting and deploying code changes, so updates are reflected in the app without a manual server restart. This process typically involves updating Java classes and web resources on the fly. 

In a webforJ app, this means regenerating the WAR file whenever modifications are made to the code.

Changes to Java classes and resources on the classpath are typically monitored by the IDE. When a Java class is modified and the file is saved, either by the IDE automatically or manually by the developer, these tools kick in to compile and place the updated class files in the target directory to apply these changes.

Tools and settings that automate or optimize browser reloading can be added for a more seamless experience.

## Live reload {#live-reload}

Live reload ensures that once changes are deployed, the browser reflects those updates in real-time without needing a manual browser refresh. 

In a webforJ app, live reload can automatically refresh the view, re-rendering components to show the latest state of the app, or even patch changes as they're needed on demand.

## Integration options

You can implement redeployment and live reload with any of the following integrations:

- The [Maven Jetty Plugin](maven-jetty-plugin.md) launches an embedded Jetty server that monitors the app’s files for changes and automatically redeploys the app.
- [Spring DevTools](../../integrations/spring/spring-devtools.md) provides automatic redeployment when Java or JavaScript files change, and integrates with webforJ DevTools for live reloading, displaying styling and image changes without a full page reload.
- [JRebel](jrebel.md) integrates with the JVM to detect code changes and replace modified classes directly in memory, without restarting the server.

## Topics {#topics}

<DocCardList className="topics-section" />