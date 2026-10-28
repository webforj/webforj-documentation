---
sidebar_position: 1
title: 自动升级
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
_i18n_hash: 8c889d8dea5c90aad2ef224d152e611e
---
# 使用 OpenRewrite {#using-openrewrite}

OpenRewrite 是一个开源项目，旨在进行自动化源代码重构，使您能够高效且自动地在使用已弃用或移除的 API 的项目之间迁移不同版本的 webforJ。

## 何时使用 OpenRewrite？ {#when-to-use-openrewrite}

您可以在升级以摆脱已弃用的 API 时使用 OpenRewrite，不仅限于主要版本。这有助于您在新方法被弃用之前，及时处理它们，避免它们在后续版本中被移除。

## 设置 {#setup}

要使用 OpenRewrite 升级您的项目，请在您的 `pom.xml` 中添加 OpenRewrite Maven 插件，并将 [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) 作为依赖项：

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

将 `TARGET_VERSION` 替换为您要升级到的 webforJ 版本，将 `RECIPE_NAME` 替换为本文 [可用食谱](#available-recipes) 部分中的其中一个食谱。

## 预览更改 (可选) {#previewing-changes}

如果您希望预览 OpenRewrite 将对 webforJ 食谱进行的更改，可以运行以下命令，这会在不修改任何文件的情况下在 `target/rewrite/rewrite.patch` 中生成一个 diff。查看补丁以准确了解食谱将更改的内容。

```bash
mvn rewrite:dryRun
```

## 运行 OpenRewrite {#running-openrewrite}

当您准备好使用 OpenRewrite 应用更改时，运行以下命令：

```bash
mvn rewrite:run
```

该食谱会自动处理大部分升级，通过更新依赖项、重命名方法、改变类型和调整返回类型。对于少数没有 1:1 替换的情况，它会在您的代码中添加 `TODO` 注释，并提供具体的说明。搜索您的项目以找到这些 `TODO` 注释，了解剩余的工作。

## 清理 {#clean-up}

运行 OpenRewrite 并使用 webforJ 食谱解决任何 `TODO` 注释后，请从您的 `pom.xml` 中删除该插件。

## 可用食谱 {#available-recipes}

| 版本 | 标准 webforJ 项目 | Spring Boot 项目 |
| ----- | ------------------ | ------------------ |
| v26.03 | `com.webforj.rewrite.v26.UpgradeWebforj_26_03` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
