---
sidebar_position: 4
title: Managing Secrets
description: >-
  Keep database passwords, API keys, and other secrets out of your webforJ
  source tree and configuration files by resolving them at runtime.
_i18n_hash: 3c20f1e66f7fb00f96c26f0b00d46f07
---
每个秘密背后的规则，无论是数据库密码、API 密钥还是签名密钥，其真实价值绝不应存在于你的代码、`webforj.conf` 或你的仓库中。应在运行时解决这些值，以便相同的构建能够在每个环境中运行，并且泄露的仓库不会泄露任何信息。

## 从环境中读取秘密 {#read-secrets-from-the-environment}

最便携的方法是在运行应用程序的机器或容器上将每个秘密存储为环境变量，并在启动时读取，而不是将其提交到任何地方。

```bash
# 设置应用程序运行的位置，绝不要在受跟踪的文件中
export DB_PASSWORD=…
```

确保这些值不在 `webforj.conf` 和你提交的任何其他文件中，并确保你的部署在应用程序启动之前设置它们。

## 在 Spring Boot 上 {#on-spring-boot}

如果你的应用程序运行在 Spring Boot 上，依赖其外部化配置功能，而不是自己发明。你可以在 `application.properties` 中通过 `${...}` 占位符引用环境变量，并通过 `spring.config.import` 引入位于项目之外（也就是版本控制之外）的秘密文件。

```properties title="application.properties"
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

这些是 Spring Boot 的特性，而不是 webforJ 的；请参阅 Spring Boot 的 [外部化配置](https://docs.spring.io/spring-boot/reference/features/external-config.html) 文档以获取完整的选项范围。

:::tip 泄露的秘密就是被烧掉的秘密
将 `secrets.properties` 等文件添加到 `.gitignore`，审核历史记录中是否存在泄露的值，并旋转任何曾经暴露的内容。将秘密排除在新的提交之外，并不能撤销已推送的内容。
:::
