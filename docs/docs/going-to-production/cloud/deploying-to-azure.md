---
sidebar_position: 3
title: Deploying to Azure
description: Deploy a webforJ app to Azure App Service with the Azure Web App Maven plugin, or to Azure Container Apps, with session affinity and an always-on instance.
---

Azure offers two services that run a webforJ app:

| Service | Use it for |
|---|---|
| [App Service](#app-service) | A JAR or WAR, deployed with the Azure Web App Maven plugin |
| [Azure Container Apps](#azure-container-apps) | A container image |

## App Service {#app-service}

Azure App Service runs Java apps on managed Linux or Windows instances. The Java SE stack runs the Spring Boot JAR, the Tomcat stack runs a WAR. The Azure Web App Maven plugin creates the app and deploys it from the project.

### Prerequisites {#prerequisites}

- An Azure subscription
- The [Azure CLI](https://learn.microsoft.com/cli/azure/install-azure-cli)
- A Spring Boot app built with `mvn package`, see [Preparing for Production](../preparing-for-production)

Sign in once:

```bash
az login
```

### Add the Maven plugin {#add-the-maven-plugin}

Run the configuration goal from the project root. It asks for the subscription, the operating system, the Java version, and the stack, then writes the plugin into `pom.xml`:

```bash
mvn com.microsoft.azure:azure-webapp-maven-plugin:2.14.1:config
```

Choose **Linux**, the Java version the project compiles for (**Java 21** or **Java 25**), and **Java SE** for the JAR. The result looks like this:

```xml title="pom.xml"
<plugin>
  <groupId>com.microsoft.azure</groupId>
  <artifactId>azure-webapp-maven-plugin</artifactId>
  <version>2.14.1</version>
  <configuration>
    <schemaVersion>v2</schemaVersion>
    <resourceGroup>my-app-rg</resourceGroup>
    <appName>my-app</appName>
    <region>westeurope</region>
    <pricingTier>B1</pricingTier>
    <runtime>
      <os>Linux</os>
      <javaVersion>Java 21</javaVersion>
      <webContainer>Java SE</webContainer>
    </runtime>
    <appSettings>
      <property>
        <name>SPRING_PROFILES_ACTIVE</name>
        <value>prod</value>
      </property>
      <property>
        <name>WEBFORJ_RELOADONSERVERERROR</name>
        <value>true</value>
      </property>
    </appSettings>
    <deployment>
      <resources>
        <resource>
          <directory>${project.basedir}/target</directory>
          <includes>
            <include>*.jar</include>
          </includes>
        </resource>
      </resources>
    </deployment>
  </configuration>
</plugin>
```

App Service sets the `SERVER_PORT` environment variable for Java apps, and Spring Boot listens on it without further configuration. `WEBFORJ_RELOADONSERVERERROR` lets open pages reload by themselves after the app restarts, see [restarts and scaling](../preparing-for-production#restarts-and-scaling).

### Deploy {#deploy}

```bash
mvn package azure-webapp:deploy
```

The first run creates the resource group, the App Service plan, and the app. Later runs deploy the new JAR. The plugin prints the address of the app when it finishes.

### Configure the app {#configure-the-app}

Turn on the two settings a webforJ app depends on:

```bash
az webapp config set --resource-group my-app-rg --name my-app --always-on true
az webapp update --resource-group my-app-rg --name my-app --client-affinity-enabled true
```

| Setting | Why |
|---|---|
| Always On | Without it, App Service unloads the app after 20 minutes without requests, which ends all sessions |
| Session affinity | Keeps every user on one instance when the plan runs more than one, see [sticky sessions](../preparing-for-production#sticky-sessions) |

App Service ends requests after about 230 seconds, much longer than the 50-second heartbeat, so the default `webforj.clientHeartbeatRate` works. Changing app settings restarts the app, which ends the sessions on it.

### Deploy a WAR {#deploy-a-war}

For a WAR built from the `webforj` flavor of the archetype, choose the Tomcat stack in the configuration goal and build with `mvn package -Pprod`. Remove the `appSettings`, since a WAR reads `webforj.conf` and reloads open pages by default:

```xml title="pom.xml"
<runtime>
  <os>Linux</os>
  <javaVersion>Java 21</javaVersion>
  <webContainer>Tomcat 10.1</webContainer>
</runtime>
```

Change the `include` pattern of the deployment resource to `*.war`. App Service deploys the WAR as `app.war` and serves it at `/`.

Then build and deploy the WAR:

```bash
mvn package -Pprod azure-webapp:deploy
```

:::info Tomcat 11 and Java 21
On Linux, App Service offers Tomcat 11 only with Java 17, which webforJ 26 doesn't support. Use Tomcat 10.1 on Java 21 or 25, or the Java SE stack. On Tomcat 10.1, [`SessionObjectTable`](/docs/advanced/object-string-tables#sessionobjecttable) isn't available because it needs Servlet 6.1. A [Spring Boot app packaged as a WAR](../traditional-servers#spring-boot-apps) needs Servlet 6.1 and doesn't run on Tomcat 10.1, so deploy Spring Boot as a JAR on the Java SE stack.
:::

### Scaling {#scaling}

- Scale out the App Service plan to run more instances. Session affinity keeps users on their instance.
- The app needs outbound access to TCP port `27000` for the license server, which App Service allows by default.

## Azure Container Apps {#azure-container-apps}

Azure Container Apps runs the container image from [Containerization](../containerization) and offers sticky sessions. Create the app and turn on sticky sessions:

```bash
az containerapp create --name my-app --resource-group my-app-rg --environment my-env \
  --image <registry>/my-app:1 --ingress external --target-port 8080 \
  --min-replicas 1 --max-replicas 3 \
  --scale-rule-name http --scale-rule-type http --scale-rule-http-concurrency 200 \
  --env-vars SPRING_PROFILES_ACTIVE=prod
az containerapp ingress sticky-sessions set --name my-app --resource-group my-app-rg --affinity sticky
```

- Sticky sessions work in single revision mode.
- The HTTP scale rule counts concurrent requests, and each open tab holds one. Raise the concurrency per replica from its default of 10 to the number of tabs one replica serves.
- Ingress ends requests after 240 seconds, longer than the 50-second heartbeat.
