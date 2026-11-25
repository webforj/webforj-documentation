---
sidebar_position: 3
title: Packaging Options
description: Compare the executable JAR, the WAR for servlet containers, and BBjServices deployments, and pick the packaging that fits your servers.
---

A webforJ app packages in one of three ways. The **Runs on** choice in [startforJ](https://docs.webforj.com/startforj), or the `flavor` of the [archetype](/docs/building-ui/archetypes/overview), sets the packaging when you create the project.

| | Executable JAR | WAR | BBjServices |
|---|---|---|---|
| Archetype flavor | `webforj-spring` | `webforj` | `webforj-bbj` |
| Server | Embedded Tomcat, started by Spring Boot | External servlet container | BBjServices |
| Build | `mvn package` | `mvn package -Pprod` | `mvn install -Pprod` |
| Start | `java -jar` | The container deploys the WAR | BBjShip deploys the app into the running server |
| Configuration | `application.properties` and profiles | `webforj.conf` | `webforj.conf` and Enterprise Manager |
| Runs on | Any server or platform with Java 21 or higher | Jetty 12.1, Tomcat 11, Tomcat 10.1 | BBjServices of the same version as webforJ |

## Executable JAR {#executable-jar}

The JAR contains the app, its dependencies, and an embedded Tomcat server. It runs anywhere a Java 21 or 25 runtime runs, which makes it the default for containers and cloud platforms. Every [cloud guide](./cloud/overview) starts from it.

The [Spring Boot](/docs/integrations/spring/spring-boot) article explains the Spring side of the project.

## WAR {#war}

The WAR contains the app and its dependencies, including the webforJ engine, and runs in an external servlet container. `src/main/webapp/WEB-INF/web.xml` maps `WebforjServlet` to `/*`.

webforJ requires a container that implements Jakarta Servlet 6.0 or later:

| Container | Servlet version | Notes |
|---|---|---|
| Jetty 12.1 with the `ee11` environment | 6.1 | The archetypes develop against this environment |
| Tomcat 11 | 6.1 | |
| Tomcat 10.1 | 6.0 | Runs the app, but [`SessionObjectTable`](/docs/advanced/object-string-tables#sessionobjecttable) needs Servlet 6.1 |

A Spring Boot app can also be packaged as a WAR. It needs Servlet 6.1, so it runs on Jetty 12.1 and Tomcat 11, but not on Tomcat 10.1. See [Spring Boot apps](./traditional-servers#spring-boot-apps).

Use a WAR when your team already runs servlet containers, or when a cloud platform offers a managed Tomcat that you want to use. [Traditional Servers](./traditional-servers) covers the deployment.

## BBjServices {#bbjservices}

The app runs inside BBjServices, which provides the webforJ engine, and you manage it in Enterprise Manager next to other BBj apps. BBjServices must be installed and running on the server. The webforJ build plugin deploys the app to it with BBjShip. Deploys run from your machine or from a CI server. See [BBjShip](/docs/configuration/bbjship/overview).

Choose BBjServices when the app belongs to an existing BBj installation.

## Choosing a packaging {#choosing-a-packaging}

| Situation | Packaging |
|---|---|
| New app for containers or a cloud platform | Executable JAR |
| Your servers already run Jetty or Tomcat | WAR |
| A cloud platform with a managed Tomcat (AWS Elastic Beanstalk, Azure App Service) | WAR or executable JAR |
| The app runs next to BBj programs and you manage it in Enterprise Manager | BBjServices |
