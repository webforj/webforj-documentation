---
title: Redeployment and Live Reload
hide_table_of_contents: false
---

Efficient development workflows rely on tools that detect code changes and automatically update the app in real time. Continuous Deployment and Dynamic Reload work together to simplify the development process by reducing manual steps, allowing you to see your changes quickly without needing to manually restart the server.

## Redeployment

Redeployment in Java development refers to automatically detecting and deploying code changes, so updates are reflected in the app without a manual server restart. This process typically involves updating Java classes and web resources on the fly. 

In a webforJ app, this means regenerating the WAR file whenever modifications are made to the code.

Changes to Java classes and resources on the classpath are typically monitored by the IDE. When a Java class is modified and the file is saved, either by the IDE automatically or manually by the developer, these tools kick in to compile and place the updated class files in the target directory to apply these changes.

Tools and settings that automate or optimize browser reloading can be added for a more seamless experience.

## Live reload

Live reload ensures that once changes are deployed, the browser reflects those updates in real-time without needing a manual browser refresh. 

In a webforJ app, live reload can automatically refreshes the view, re-rendering components to show the latest state of the app, or even patch changes as they're needed on demand.

## Topics

<DocCardList className="topics-section" />