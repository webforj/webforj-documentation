---
sidebar_position: 1
title: Automated Upgrades
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
sidebar_class_name: new-content
_i18n_hash: 7f4b51fb3daf9ba91f0e755758d8ec52
---
# 使用 OpenRewrite {#using-openrewrite}

OpenRewrite 是一个开源项目，旨在实现自动化源代码重构，使您能够高效且自动地在使用已弃用或删除 API 的项目中迁移不同版本的 webforJ。

## 何时使用 OpenRewrite? {#when-to-use-openrewrite}

您可以在升级时使用 OpenRewrite，以摆脱已弃用的 API，不仅限于主要版本。这可以帮助您在这些 API 在后续版本中被删除之前，提前摆脱新近弃用的方法。

## 设置 {#setup}

要使用 OpenRewrite 升级您的项目，请将 OpenRewrite Maven 插件添加到您的 `pom.xml` 中，并将 [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) 作为依赖项：

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

将 `TARGET_VERSION` 替换为您要升级到的 webforJ 版本，并将 `RECIPE_NAME` 替换为本文 [可用模板](#available-recipes) 部分中的一个模板。

## 预览更改（可选） {#previewing-changes}

如果您希望预览 OpenRewrite 将如何应用 webforJ 模板的更改，可以运行以下命令，这会在 `target/rewrite/rewrite.patch` 中生成一个差异而不修改任何文件。查看该补丁以准确了解模板将更改的内容。

```bash
mvn rewrite:dryRun
```

## 运行 OpenRewrite {#running-openrewrite}

当您准备好使用 OpenRewrite 应用更改时，运行以下命令：

```bash
mvn rewrite:run
```

此模板会自动处理大多数升级，通过更新依赖项、重命名方法、改变类型和调整返回类型来完成。对于少数没有 1:1 替换的情况，它会在您的代码中添加 `TODO` 注释，并提供具体说明。请在您的项目中搜索这些 `TODO` 以找出剩余的工作。

## 清理 {#clean-up}

在使用 webforJ 模板运行 OpenRewrite 并解决所有 `TODO` 注释后，从您的 `pom.xml` 中移除该插件。

## 可用模板 {#available-recipes}

| 版本 | 标准 webforJ 项目 | Spring Boot 项目 |
| ------- | ------- | ------- |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
