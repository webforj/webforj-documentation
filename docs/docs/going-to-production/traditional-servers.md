---
sidebar_position: 6
title: Traditional Servers
description: Deploy a webforJ WAR to Tomcat or Jetty, redeploy it on Jetty 12.1, and convert a project, including a Spring Boot app, to WAR packaging.
---

A WAR runs in a servlet container that you install and operate yourself. The container runs on your own servers or on virtual machines. webforJ requires Jakarta Servlet 6.0 or later, see [Packaging Options](./packaging-options#war).

Build the WAR with the production profile first:

```bash
mvn package -Pprod
```

## Deploying to Tomcat {#deploying-to-tomcat}

Tomcat 11 and Tomcat 10.1 run webforJ apps. Prefer Tomcat 11, which implements Servlet 6.1 and supports [`SessionObjectTable`](/docs/advanced/object-string-tables#sessionobjecttable).

1. Install Tomcat and run it on Java 21 or 25. `JAVA_HOME` sets the JDK that Tomcat runs on.
2. Set `CATALINA_HOME` to the installation folder. `CATALINA_BASE` is the folder that holds `webapps`, and equals `CATALINA_HOME` unless you run separate instances. The Tomcat download sets neither variable, so without them the following commands use `/webapps` and `/bin`:

   ```bash
   export CATALINA_HOME=/opt/apache-tomcat-11.0.29
   export CATALINA_BASE=$CATALINA_HOME
   ```

3. Remove the default `webapps/ROOT` folder.
4. Copy the WAR as `ROOT.war` to serve the app at `/`:

   ```bash
   cp target/my-app-1.0-SNAPSHOT.war $CATALINA_BASE/webapps/ROOT.war
   ```

5. Start Tomcat:

   ```bash
   $CATALINA_HOME/bin/startup.sh
   ```

Copying the WAR as `my-app.war` serves the app at `/my-app/` instead. A Spring Boot app needs extra configuration for this, see [Spring Boot apps](#spring-boot-apps).

## Deploying to Jetty {#deploying-to-jetty}

Use Jetty 12.1 with the `ee11` environment, which the archetypes develop against.

1. Download the Jetty home from [jetty.org](https://jetty.org/download.html) and create a Jetty base for the app:

   ```bash
   export JETTY_HOME=/opt/jetty-home-12.1.15
   mkdir -p /opt/my-app-base && cd /opt/my-app-base
   java -jar "$JETTY_HOME/start.jar" --add-modules=server,http,ee11-deploy
   ```

   A [Spring Boot app](#spring-boot-apps) packaged as a WAR also needs the `ee11-annotations` module.

2. Copy the WAR into the `webapps` folder of the base:

   ```bash
   cp ~/my-app/target/my-app-1.0-SNAPSHOT.war webapps/ROOT.war
   ```

3. Start Jetty:

   ```bash
   java -jar "$JETTY_HOME/start.jar"
   ```

Jetty 12.1 deploys the WARs in `webapps` only at startup. To deploy a new version, replace the WAR, then restart Jetty.

To let Jetty pick up a replaced WAR without a restart, turn on the deployment scanner in a file in the `start.d` folder of the base:

```ini title="start.d/redeploy.ini"
jetty.deploy.scanInterval=1
```

Copy the new WAR to a temporary name in `webapps` and rename it to `ROOT.war`, so the scanner never reads a half-written file.

:::warning Redeploying on Jetty 12.1.8 and earlier
Up to version 12.1.8, Jetty can keep serving the previous version after a redeploy, or leave the app unavailable ([jetty#14136](https://github.com/jetty/jetty.project/issues/14136)). Use Jetty 12.1.9 or later, or add `jetty.deploy.atomicRedeploy=false` to the same file.
:::

## Running more than one server {#running-more-than-one-server}

Put a load balancer with [sticky sessions](./preparing-for-production#sticky-sessions) in front of the servers, and give it a timeout longer than the heartbeat interval. The nginx configuration in [Containerization](./containerization#run-more-than-one-container) works for servers as well.

## Converting a project to WAR packaging {#converting-a-project-to-war-packaging}

The quickest way to a WAR project is to create a new project with the `webforj` flavor of the [archetype](/docs/building-ui/archetypes/overview), or **Runs on** set to a servlet container in startforJ, and move your sources into it. To convert a project by hand, it needs these parts:

| Part | Content |
|---|---|
| `pom.xml` packaging | `<packaging>war</packaging>` |
| Dependency | `com.webforj:webforj`, which includes the webforJ engine |
| `src/main/webapp/WEB-INF/web.xml` | Maps `com.webforj.servlet.WebforjServlet` to `/*` |
| Configuration | `webforj-dev.conf` and `webforj-prod.conf`, which the `dev` and `prod` profiles copy to `webforj.conf` |
| License files | `blsclient.conf` and `certificate.bls` in `src/main/resources` |

The deployment descriptor of an archetype project:

```xml title="src/main/webapp/WEB-INF/web.xml"
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
  metadata-complete="true" version="6.0">
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/*</url-pattern>
  </servlet-mapping>
</web-app>
```

[Property Configuration](/docs/configuration/properties) describes `webforj.conf` and `web.xml` in detail.

### Spring Boot apps {#spring-boot-apps}

A Spring Boot app runs as an executable JAR by default. To deploy it to Tomcat 11 or Jetty 12.1 instead, package it as a WAR. Spring Boot 4 requires Servlet 6.1, which Tomcat 10.1 doesn't implement.

1. Set `<packaging>war</packaging>` in `pom.xml`, and declare the embedded Tomcat as provided, so the WAR uses the server of the container:

   ```xml title="pom.xml"
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-tomcat</artifactId>
     <scope>provided</scope>
   </dependency>
   ```

2. Add a class that extends `SpringBootServletInitializer`. The class that extends `App` can't extend another class, so the initializer needs a class of its own:

   ```java title="ServletInitializer.java"
   import org.springframework.boot.builder.SpringApplicationBuilder;
   import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

   public class ServletInitializer extends SpringBootServletInitializer {

     @Override
     protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(Application.class);
     }
   }
   ```

3. Build the WAR with `mvn package`. The app needs no `web.xml` or `webforj.conf`, and keeps its Spring properties files.

The production profile is chosen when the server starts. Set the `SPRING_PROFILES_ACTIVE=prod` environment variable, or pass `-Dspring.profiles.active=prod` in `CATALINA_OPTS` for Tomcat or to `java -jar start.jar` for Jetty.

Keep these differences to a plain webforJ WAR in mind:

- **Jetty modules.** Add the `ee11-annotations` module to the Jetty base. Without it, Jetty doesn't start the app and shows a list of the files in the WAR.
- **Context path.** Deploy the WAR as `ROOT.war`. Under a context path such as `/my-app/`, the app shows only its not-found page, unless you map webforJ to a sub-path with `webforj.servlet-mapping=/app/*`. The app is then served at `/my-app/app/`.
- **Restarts.** Open pages show a link to reload the app after a restart. Set `WEBFORJ_RELOADONSERVERERROR=true` to reload them by themselves, see [Restarts and scaling](./preparing-for-production#restarts-and-scaling).

The WAR still runs with `java -jar` as well.

## BBjServices {#bbjservices}

To run the app in BBjServices instead of a servlet container, BBjServices must be installed and running on the server. Deploy the app to it with BBjShip, see [BBjShip](/docs/configuration/bbjship/overview).
