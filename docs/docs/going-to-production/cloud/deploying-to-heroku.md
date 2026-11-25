---
sidebar_position: 5
title: Deploying to Heroku
description: Deploy a webforJ Spring Boot app to Heroku with the Java version, Procfile, session affinity, and a heartbeat interval that fits the Heroku router.
---

Heroku builds a Spring Boot app from its Git repository with the Java buildpack and runs it on dynos.

## Prerequisites {#prerequisites}

- A Heroku account with a payment method, since Heroku has no free tier
- The [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli)
- A Spring Boot app in a Git repository

Sign in once:

```bash
heroku login
```

## Set the Java version {#set-the-java-version}

Heroku reads the Java version from `system.properties` in the project root. Set it to the version the project compiles for, here Java 21:

```properties title="system.properties"
java.runtime.version=21
```

## Tell Heroku how to start the app {#tell-heroku-how-to-start-the-app}

Heroku passes the port in the `PORT` environment variable. Add a `Procfile` to the project root:

```text title="Procfile"
web: java -Dserver.port=$PORT -jar target/my-app-1.0-SNAPSHOT.jar
```

## Fit the heartbeat to the router {#fit-the-heartbeat-to-the-router}

The Heroku router ends a request that hasn't started its response after 30 seconds. The browser holds its update request open for `webforj.clientHeartbeatRate`, 50 seconds by default, so lower the interval below 30 seconds with `WEBFORJ_CLIENTHEARTBEATRATE=25s`, as the commands in [Deploy](#deploy) do.

Without this setting, users see errors every 30 seconds. See [requests that stay open](../preparing-for-production#requests-that-stay-open).

## Deploy {#deploy}

Heroku builds the app from Git, so commit the project first. The commands push the `main` branch; push another branch with `git push heroku <branch>:main`.

```bash
heroku create my-app
heroku config:set SPRING_PROFILES_ACTIVE=prod WEBFORJ_CLIENTHEARTBEATRATE=25s WEBFORJ_RELOADONSERVERERROR=true
heroku features:enable http-session-affinity
git push heroku main
```

`WEBFORJ_RELOADONSERVERERROR=true` lets open pages reload by themselves after a dyno restarts. The build runs `mvn package` and starts the app with the `Procfile`. `heroku open` opens the app, and `heroku logs --tail` follows the logs.

## Restarts and scaling {#restarts-and-scaling}

| Topic | What to know |
|---|---|
| Session affinity | `http-session-affinity` keeps users on one dyno when you run more than one |
| Daily restarts | Heroku restarts every dyno once a day, which ends the sessions on it, see [restarts and scaling](../preparing-for-production#restarts-and-scaling) |
| Eco dynos | Sleep after 30 minutes without traffic. Use Basic or Standard dynos in production |
| Scaling out | Eco and Basic dynos run one instance only. Scaling out needs Standard dynos or higher |

Dynos allow outbound connections, including TCP port `27000` for the license server.
