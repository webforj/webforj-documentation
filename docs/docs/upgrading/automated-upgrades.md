---
sidebar_position: 1
title: Automated Upgrades
sidebar_class_name: new-content
---

# Using OpenRewrite {#using-openrewrite}

OpenRewrite is an open source project designed for automated source code refactoring, allowing you to efficiently and automatically migrate between webforJ versions for projects that use deprecated or removed APIs.

## When to use OpenRewrite? {#when-to-use-openrewrite}

You can use OpenRewrite when upgrading to move away from deprecated APIs, not just in major releases. This helps you move away from newly deprecated methods, before they’re removed in a later version.

## Setup {#setup}

To upgrade your project with OpenWrite, add the OpenRewrite Maven plugin to your `pom.xml`, with [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) as a dependency:

```xml
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>RECIPE_NAME</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>TARGET_VERSION</version>
    </dependency>
  </dependencies>
</plugin>
```

Replace `TARGET_VERSION` with the webforJ version you are upgrading to and `RECIPE_NAME` with one of the recipes from the [Available recipes](#available-recipes) section of this article.

## Previewing changes (optional) {#previewing-changes}

If you’d prefer to preview what changes OpenWrite will do with a webforJ recipe, running the following command generates a diff in `target/rewrite/rewrite.patch` without modifying any files. Review the patch to see exactly what the recipe will change.

```bash
mvn rewrite:dryRun
```

## Running OpenRewrite {#running-openrewrite}

When you're ready to apply changes with OpenRewrite, run the following command:

```bash
mvn rewrite:run
```

The recipe handles the majority of the upgrade automatically by updating dependencies, renaming methods, changing types, and adjusting return types. For the few cases where a 1:1 replacement doesn't exist, it adds `TODO` comments in your code with specific instructions. Search your project for these `TODO`s to find what's left.

## Clean up {#clean-up}

After running OpenRewrite with a webforJ recipe and resolving any `TODO` comments, remove the plugin from your `pom.xml`.

## Available recipes {#available-recipes}

| Version | Standard webforJ Projects | Spring Boot Projects |
| ------- | ------- | ------- |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |