---
sidebar_position: 4
title: Containerization
description: Write a Dockerfile for a webforJ app, pick a base image, and build and run the container image for a Spring Boot JAR or a WAR on Jetty.
---

A container image packages the app with the Java runtime it needs, so the same image runs on your machine, on a container platform, and on most cloud services. The Dockerfiles in this article build the app inside the image, so the build machine only needs Docker.

## Write a Dockerfile {#write-a-dockerfile}

Put the `Dockerfile` in the root folder of the project, next to `pom.xml`. Both variants use a multi-stage build: the first stage runs Maven, and the second stage contains only the runtime and the packaged app.

<!-- vale off -->
<Tabs>
  <TabItem value="spring" label="Spring Boot JAR" default>
    ```dockerfile title="Dockerfile"
    # syntax=docker/dockerfile:1
    FROM maven:3.9-eclipse-temurin-21 AS build
    WORKDIR /src
    COPY pom.xml .
    RUN mvn -B -q dependency:go-offline
    COPY src ./src
    RUN mvn -B -q package -DskipTests \
     && java -Djarmode=tools -jar target/*.jar extract --layers --launcher --destination /extracted

    FROM eclipse-temurin:21-jre
    WORKDIR /app
    RUN useradd --system --uid 10001 webforj
    COPY --from=build /extracted/dependencies/ ./
    COPY --from=build /extracted/spring-boot-loader/ ./
    COPY --from=build /extracted/snapshot-dependencies/ ./
    COPY --from=build /extracted/application/ ./
    USER webforj
    ENV SPRING_PROFILES_ACTIVE=prod \
        WEBFORJ_RELOADONSERVERERROR=true \
        JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"
    EXPOSE 8080
    ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
    ```
  </TabItem>
  <TabItem value="jetty" label="WAR on Jetty">
    ```dockerfile title="Dockerfile"
    # syntax=docker/dockerfile:1
    FROM maven:3.9-eclipse-temurin-21 AS build
    WORKDIR /src
    COPY pom.xml .
    RUN mvn -B -q -Pprod dependency:go-offline
    COPY src ./src
    RUN mvn -B -q -Pprod package -DskipTests

    FROM jetty:12.1-jdk21
    RUN java -jar "$JETTY_HOME/start.jar" --add-modules=ee11-deploy
    COPY --from=build --chown=jetty:jetty /src/target/*.war /var/lib/jetty/webapps/ROOT.war
    ENV JAVA_OPTIONS="-XX:MaxRAMPercentage=75"
    EXPOSE 8080
    ```
  </TabItem>
</Tabs>
<!-- vale on -->

Details that matter:

- **Java version.** The examples use Java 21. For another version, change both `FROM` lines, see [Choose a base image](#choose-a-base-image).
- **Layers.** The Spring Boot image splits the JAR into [layers](https://docs.spring.io/spring-boot/reference/packaging/container-images/efficient-images.html) with the `tools` jar mode. The dependencies layer stays cached, so a code change rebuilds and uploads only the small layer with your code.
- **Reload after a restart.** `WEBFORJ_RELOADONSERVERERROR=true` lets open pages of a Spring Boot app reload by themselves when the container is replaced. A WAR does this by default, so the Jetty image needs no setting. See [Restarts and scaling](./preparing-for-production#restarts-and-scaling).
- **Non-root user.** The Spring Boot image creates the `webforj` user. The official Jetty image already runs as `jetty`.
- **Memory.** `-XX:MaxRAMPercentage=75` sizes the heap from the memory limit of the container. Without a limit, it uses the memory of the host, so start the container with one, for example `docker run --memory=1g`.
- **Jetty modules.** The official Jetty image turns on no deploy module. webforJ projects target `ee11`, so the Dockerfile adds `ee11-deploy`.
- **Context path.** Copying the WAR as `ROOT.war` serves the app at `/`.

Add a `.dockerignore` file so the build context stays small:

```text title=".dockerignore"
target/
node_modules/
.git/
.idea/
```

## Build an image without a Dockerfile {#build-an-image-without-a-dockerfile}

A Spring Boot app can also build its image with the Spring Boot Maven plugin, which uses [buildpacks](https://docs.spring.io/spring-boot/maven-plugin/build-image.html). The build machine needs Docker and Maven, but no Dockerfile. The image runs as a non-root user and sizes the Java heap from the memory limit of the container, when you start it with one.

Add the `<image>` element to the `<configuration>` of the plugin in `pom.xml`. Projects created from an archetype already have this element with `excludeDevtools`:

```xml title="pom.xml"
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
    <excludeDevtools>true</excludeDevtools>
    <image>
      <env>
        <BP_JVM_VERSION>21</BP_JVM_VERSION>
        <BPE_DEFAULT_SPRING_PROFILES_ACTIVE>prod</BPE_DEFAULT_SPRING_PROFILES_ACTIVE>
        <BPE_DEFAULT_WEBFORJ_RELOADONSERVERERROR>true</BPE_DEFAULT_WEBFORJ_RELOADONSERVERERROR>
      </env>
    </image>
  </configuration>
</plugin>
```

| Setting | Effect |
|---|---|
| `BP_JVM_VERSION` | Java version of the image. Set it to the version the project compiles for. Without it, the image uses the Java version of the JDK that built the JAR |
| `BPE_DEFAULT_SPRING_PROFILES_ACTIVE` | Sets `SPRING_PROFILES_ACTIVE=prod` in the image. A value passed with `docker run -e` overrides it |
| `BPE_DEFAULT_WEBFORJ_RELOADONSERVERERROR` | Lets open pages reload by themselves after a restart |

Then build the image:

```bash
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=my-app
```

The goal runs the tests of the project first. A failing test stops the build, add `-DskipTests` to skip them.

## Choose a base image {#choose-a-base-image}

Use a Java 21 or 25 image. Both stages of the Dockerfile must use the Java version the project compiles for, otherwise Maven stops with `invalid target release`. A Spring Boot project sets this version with the `java.version` property in `pom.xml`. Projects created from an archetype set only `maven.compiler.source` and `maven.compiler.target`, which the Spring Boot parent overrides with its own default, so add the property, for example `<java.version>25</java.version>`. A runtime-only image (JRE) is enough for the Spring Boot JAR and keeps the image small.

| Image | Use for |
|---|---|
| `maven:3.9-eclipse-temurin-21`, `maven:3.9-eclipse-temurin-25` | Build stage |
| `eclipse-temurin:21-jre`, `eclipse-temurin:25-jre` | Spring Boot JAR |
| `amazoncorretto:21`, `amazoncorretto:25` | Spring Boot JAR on AWS, if you standardize on Corretto |
| `jetty:12.1-jdk21`, `jetty:12.1-jdk25` | WAR on Jetty. The Jetty images have no `jre` variants |
| `tomcat:11.0-jdk21`, `tomcat:11.0-jdk25` | WAR on Tomcat. Remove the default `webapps` content and copy the WAR as `webapps/ROOT.war` |

Pin a full version tag or an image digest when you need reproducible builds, and update it regularly for security fixes.

## Build and run the image {#build-and-run-the-image}

```bash
docker build -t my-app .
docker run --rm -p 8080:8080 my-app
```

Open `http://localhost:8080/` and check the app as described in [Verify the build locally](./preparing-for-production#verify-the-build-locally).

Pass settings as environment variables instead of rebuilding the image. A Spring Boot app maps them to properties:

```bash
docker run --rm -p 8080:8080 \
  -e WEBFORJ_CLIENTHEARTBEATRATE=25s \
  -e SERVER_PORT=8080 \
  my-app
```

## Run more than one container {#run-more-than-one-container}

Behind a load balancer, more than one container needs [sticky sessions](./preparing-for-production#sticky-sessions). With nginx, the open source edition offers `ip_hash`. The file holds directives for the `http` block, so mount it as `/etc/nginx/conf.d/default.conf` in the official nginx image:

```nginx title="default.conf"
upstream app {
  ip_hash;
  server app1:8080;
  server app2:8080;
}

server {
  listen 80;
  location / {
    proxy_pass http://app;
    proxy_http_version 1.1;
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_read_timeout 120s;
    proxy_buffering off;
  }
}
```

Start the app containers before nginx. nginx stops at startup when it can't resolve `app1` or `app2`.

`ip_hash` uses the first three parts of an IPv4 address. All users in the same `/24` network, or behind the same NAT or proxy, reach one container. Managed load balancers offer cookie-based stickiness, which spreads such users and is the better choice in the cloud.
