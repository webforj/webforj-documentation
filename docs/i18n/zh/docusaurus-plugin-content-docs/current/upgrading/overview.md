---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 6adbad314378e90356ad6602cc52de5a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ 的发布周期遵循一个结构化和可预测的模型，以确保稳定性、性能和持续创新。本文档提供了关于发布如何规划、预期的发布类型以及用户如何保持信息更新和准备的概述。

## webforJ 发布类型 {#types-of-webforj-releases}

webforJ 遵循一个结构化的发布模型，包括以下类型的发布：

### 1. 主要发布 {#1-major-releases}
- 每年发生一次。
- 引入显著的新功能、改进和增强。
- 可能需要配置更改或对现有应用进行适应。
- 用版本标识，例如 **webforJ 20.00, webforJ 21.00 等。**

### 2. 次要发布 {#2-minor-releases}
- 每年多次发生（大约每六到八周一次）。
- 提供增量改进、优化和少量新功能。
- 用版本标识，例如 **webforJ 20.01, webforJ 20.02 等。**

### 3. 修补程序和错误修复发布 {#3-patches-and-bug-fix-releases}
- 根据需要发布。
- 解决关键错误、性能问题和安全漏洞。
- 用附加编号标识，例如 **webforJ 20.01.1, webforJ 20.01.2 等。**

## 每次发布的预期内容 {#what-to-expect-with-each-release}

### 功能增强 {#feature-enhancements}
- 主要和次要发布引入新能力、优化和集成。
- 功能路线图在发布说明中共享，以帮助用户提前规划。

:::info 向后兼容性
虽然努力保持兼容性，但主要发布可能包含需要对应用进行调整的更改。鼓励用户查看发布说明以了解已弃用的功能。
:::

### 安全更新 {#security-updates}
- 安全是优先事项，关键漏洞将在补丁发布中尽快解决。

:::tip 快照构建
快照构建在大多数发布之前可用。鼓励用户针对它们进行测试，以便及早识别问题并提供反馈。
:::

## 如何保持更新 {#how-to-stay-updated}

### 发布说明和公告 {#release-notes-and-announcements}
- 每次发布都伴随有详细的 [发布说明](https://github.com/webforj/webforj/releases)，概述新功能、错误修复和任何必需的操作。
- 用户应订阅 webforJ [博客](../../blog) 以获取及时更新。

:::tip 升级建议
客户应根据业务需求和稳定性要求进行升级规划。鼓励用户保持在最新版本，以便享受性能增强和新功能。
:::

### 支持和兼容性 {#support-and-compatibility}
- webforJ 提供主要发布的文档和升级指南。
- 可用社区论坛和客户支持渠道进行故障排除和协助。

<DocCardList className="topics-section" />
