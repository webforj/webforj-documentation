---
sidebar_position: 4
title: Managing secrets
description: Keep database passwords, API keys, and other secrets out of your webforJ source tree and configuration files by resolving them at runtime.
---

The rule behind every secret, a database password, an API key, a signing key, is that its real value never lives in your code, your `webforj.conf`, or your repository. Resolve it at runtime instead, so the same build runs in every environment and a leaked repository gives nothing away.

## Read secrets from the environment {#read-secrets-from-the-environment}

The most portable approach is to store each secret as an environment variable on the machine or container that runs the app, and read it at startup rather than committing it anywhere.

```bash
# set where the app runs, never in a tracked file
export DB_PASSWORD=…
```

Keep these values out of `webforj.conf` and any other file you commit, and make sure your deployment sets them before the app starts.

## On Spring Boot {#on-spring-boot}

If your app runs on Spring Boot, lean on its externalized configuration rather than inventing your own. You can reference an environment variable from `application.properties` with a `${...}` placeholder, and pull in a secrets file that lives outside the project (and outside version control) with `spring.config.import`.

```properties title="application.properties"
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

These are Spring Boot features, not webforJ's; see Spring Boot's [externalized configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html) documentation for the full range of options.

:::tip A leaked secret is a burned secret
Add files like `secrets.properties` to `.gitignore`, audit your history for values that slipped in, and rotate anything that was ever exposed. Keeping secrets out of new commits doesn't undo the ones already pushed.
:::
