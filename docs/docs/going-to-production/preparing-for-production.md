---
sidebar_position: 2
title: Preparing for Production
description: Build a webforJ app for production, check the build locally, and learn what a production host must provide, from request timeouts to sticky sessions.
---

A production build of a webforJ app drops the development tools, bundles the frontend for production, and reads the production configuration. This article shows how to build it, how to check it before you deploy, and which properties of the app shape the choice of host.

## Build the production artifact {#build-the-production-artifact}

The command depends on where the app runs, which you chose with the **Runs on** option in [startforJ](https://docs.webforj.com/startforj) or the `flavor` of the [archetype](/docs/building-ui/archetypes/overview):

| Runs on | Command | Result |
|---|---|---|
| Spring Boot | `mvn package` | Executable JAR in `target/`, with an embedded Tomcat server |
| Servlet container (Jetty, Tomcat) | `mvn package -Pprod` | WAR in `target/` |
| BBjServices | `mvn install -Pprod` | Deployment to BBjServices through [BBjShip](/docs/configuration/bbjship/overview) |

:::warning Use the `prod` profile for a WAR or a BBjServices deployment
WAR and BBjServices projects activate their `dev` profile by default. Without `-Pprod`, the build copies `webforj-dev.conf` into the app as `webforj.conf`, which turns on debug output and live reload. A WAR also packages the development tools.
:::

A Spring Boot app needs no profile at build time. `mvn package` leaves the development tools out on its own, see [production builds](/docs/configuration/deploy-reload/spring-devtools#production-builds).

## Development and production mode {#development-and-production-mode}

| Aspect | Development run (`mvn`) | Production build |
|---|---|---|
| Development tools, live reload | On | Not packaged |
| Debug mode | On (`webforj.debug`) | Off |
| Frontend files | Development build from the watch | Minified, with hashed file names (the `bundle` goal of the [build plugin](/docs/configuration/build-plugin)) |
| Configuration | `application-dev.properties` or `webforj-dev.conf` | `application-prod.properties` or `webforj-prod.conf` |

A Spring Boot app reads `application-prod.properties` only when the `prod` profile is active. Start it with `--spring.profiles.active=prod` or set the `SPRING_PROFILES_ACTIVE=prod` environment variable.

## Verify the build locally {#verify-the-build-locally}

Run the packaged app the way production runs it before you deploy:

<!-- vale off -->
<Tabs>
  <TabItem value="spring" label="Spring Boot" default>
    ```bash
    java -jar target/my-app-1.0-SNAPSHOT.jar --spring.profiles.active=prod
    ```
  </TabItem>
  <TabItem value="war" label="WAR">
    Deploy the WAR to a local Jetty or Tomcat as described in [Traditional Servers](./traditional-servers), or run it in a container as described in [Containerization](./containerization).
  </TabItem>
</Tabs>
<!-- vale on -->

Then check three things in the browser:

1. The app opens at `http://localhost:8080/` and reacts to clicks.
2. In the **Network** tab of the developer tools, frontend files carry a hash in their names.
3. One request to `webapprmi` stays pending for about 50 seconds, then completes and starts again. That's the connection the server uses to send UI updates.

## Production requirements {#production-requirements}

Five properties of a webforJ app decide which hosts fit and how to set them up.

### Requests that stay open {#requests-that-stay-open}

The browser keeps one HTTP request to the server open at all times. The server answers it when it has UI updates, otherwise after the interval set by `webforj.clientHeartbeatRate`, and the browser then opens the next one. webforJ doesn't need WebSockets for this.

Every load balancer and proxy between the browser and the app must allow a request to stay open longer than that interval, which defaults to `50s`. When a host cuts requests earlier, lower the interval below its limit:

<!-- vale off -->
<Tabs>
  <TabItem value="spring" label="application-prod.properties" default>
    ```properties
    webforj.client-heartbeat-rate=25s
    ```
  </TabItem>
  <TabItem value="conf" label="webforj-prod.conf">
    ```hocon
    webforj.clientHeartbeatRate = 25s
    ```
  </TabItem>
  <TabItem value="env" label="Environment variable (Spring Boot)">
    ```bash
    WEBFORJ_CLIENTHEARTBEATRATE=25s
    ```
  </TabItem>
</Tabs>
<!-- vale on -->

| Host or proxy | Default limit | What to do |
|---|---|---|
| Heroku router | 30 seconds until the first byte | Set the interval to `25s` |
| Red Hat OpenShift router | 30 seconds | Raise the route timeout, or set the interval to `25s` |
| AWS Application Load Balancer | 60 seconds idle | Raise the idle timeout to 120 seconds |
| nginx `proxy_read_timeout` | 60 seconds | Raise it to 120 seconds |
| Scalingo router | 59 seconds without data | Set the interval to `45s` or lower |

Keep the interval below `webforj.sessionTimeout`, which defaults to 60 seconds. See [Property Configuration](/docs/configuration/properties) for both properties.

### Sticky sessions {#sticky-sessions}

The UI of each browser tab lives in the memory of the server that created it. When more than one instance runs, the load balancer must send every request of a user to the same instance, which is called sticky sessions or session affinity.

Without sticky sessions the app breaks when any request reaches another instance, often while the page is still loading. The page then shows only a link to reload the app. Turn on stickiness in the load balancer, as every cloud guide in this section shows. Prefer cookie-based stickiness where the load balancer offers it.

### Restarts and scaling {#restarts-and-scaling}

When the instance that holds a session stops, the session ends with it. When a server answers again, the browser reloads the page and the server builds a fresh UI. The user keeps the tab and the URL, but loses what the UI held, such as unsaved form input.

A WAR reloads by default. A Spring Boot app turns this reload off by default and shows a link to reload the app instead. Turn it on in `application.properties`, or with the `WEBFORJ_RELOADONSERVERERROR=true` environment variable:

```properties title="application.properties"
webforj.reload-on-server-error=true
```

To keep the loss small:

- **Keep one instance running.** Turn off scale to zero and idle shutdown on the host.
- **Drain before stopping.** Spring Boot projects created from an archetype set `server.shutdown=graceful`. Give load balancers a deregistration delay longer than the heartbeat interval.
- **Put state where it survives.** Keep the current view in the URL with [routing](/docs/routing/overview), and save data the user entered to a database early.
- **Count tabs.** Each open tab holds one request. Autoscalers that scale on concurrent requests need a per-instance limit that matches the number of tabs one instance can serve.

### License server {#license-server}

Apps that run on Spring Boot or a servlet container check out their license from the license server named in `src/main/resources/blsclient.conf`, by default `license.webforj.com` on TCP port `27000`. The app keeps that connection open, so the host must allow outbound TCP traffic to port `27000`. Apps on BBjServices use the license service that BBjServices is registered with. To run your own license service, see [Local BLS License](/docs/configuration/local-bls-license).

When the license server is unreachable, the app still starts and runs in trial mode:

- **Slower start.** The app waits about 30 seconds for the license server before it answers HTTP requests. Give health checks a grace period or startup probe of at least 45 seconds, otherwise the platform can restart the container before it finishes starting.
- **Slower new tabs.** Each new tab takes a few seconds longer to appear and shows a **For Trial Use Only** notice.
- **Log message.** The server log shows `[E1013] License connection problem` for each new session.

A [local license service](/docs/configuration/local-bls-license) removes the dependency on the outside server.

### Memory {#memory}

Every open tab holds its components and session data on the server. Size the server for the number of tabs open at the same time, and in containers let the heap follow the memory limit with `-XX:MaxRAMPercentage`, as the [Containerization](./containerization) examples do.

## Common build issues {#common-build-issues}

| Symptom | Cause | Fix |
|---|---|---|
| The WAR contains development tools or debug output | Built without `-Pprod` | Run `mvn package -Pprod` |
| Frontend files have no hashes and aren't minified | Built with `mvn compile` | Run `mvn package`, only the package phase runs the `bundle` goal |
| `invalid target release` or `release version not supported` during the build | The JDK that runs Maven is older than the Java version of the project | Build with a JDK of that version, in a container with the matching `maven` image |
| `UnsupportedClassVersionError` in the log, and a 500 error page when the app opens | The server runs an older Java version than the project, for example a Java 25 build on Java 21 | Run the server on the Java version of the project, or build the project for the Java version of the server |
| The WAR doesn't start on Jetty | The Jetty base lacks the `ee11-deploy` module | Add it with `--add-modules=ee11-deploy` |
| A Spring Boot app ignores `application-prod.properties` | The `prod` profile isn't active | Set `SPRING_PROFILES_ACTIVE=prod` |
