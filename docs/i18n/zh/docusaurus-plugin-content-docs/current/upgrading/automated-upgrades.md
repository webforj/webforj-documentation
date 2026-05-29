---
sidebar_position: 2
title: Automated Upgrades
sidebar_class_name: new-content
_i18n_hash: 1681300490f592540c6d96fbdbfd8ee4
---
# 使用 OpenRewrite {#using-openrewrite}

OpenRewrite 是一个开源项目，旨在实现自动的源代码重构，使您能够高效且自动地在使用已弃用或已移除的 API 的项目中迁移不同版本的 webforJ。

## 何时使用 OpenRewrite？ {#when-to-use-openrewrite}

您可以在升级以摆脱已弃用的 API 时使用 OpenRewrite，不仅限于主要版本更新。这可以帮助您在这些方法在后续版本中被移除之前，提前摆脱新近弃用的方法。

## 设置 {#setup}

要使用 OpenRewrite 升级您的项目，将 OpenRewrite Maven 插件添加到您的 `pom.xml`，并将 [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) 作为依赖项：

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

将 `TARGET_VERSION` 替换为您要升级到的 webforJ 版本，将 `RECIPE_NAME` 替换为本文 [可用的食谱](#available-recipes) 部分中的一个食谱。

## 预览更改（可选） {#previewing-changes}

如果您希望预览 OpenRewrite 将如何处理某个 webforJ 食谱，可以运行以下命令生成 `target/rewrite/rewrite.patch` 中的差异，而不修改任何文件。审查该补丁以查看该食谱将确切更改什么。

```bash
mvn rewrite:dryRun
```

## 运行 OpenRewrite {#running-openrewrite}

当您准备好使用 OpenRewrite 应用更改时，运行以下命令：

```bash
mvn rewrite:run
```

该食谱通过更新依赖项、重命名方法、改变类型和调整返回类型，自动处理大多数升级。对于那些不存在一对一替换的少数情况，它会在您的代码中添加 `TODO` 注释，并提供具体的说明。搜索您的项目以查找这些 `TODO`，以找出剩下的工作。

## 清理 {#clean-up}

在使用 webforJ 食谱运行 OpenRewrite 并解决任何 `TODO` 注释之后，从 `pom.xml` 中移除该插件。

## 可用食谱 {#available-recipes}

| 版本 | 标准 webforJ 项目 | Spring Boot 项目 |
| ----- | ------------------ | ----------------- |
| v26  | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
