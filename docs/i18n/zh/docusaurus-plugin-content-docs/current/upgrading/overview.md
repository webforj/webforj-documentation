---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 1bdddfccaece385582aecb1b63967611
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ 的发布周期遵循一个结构化且可预测的模型，以确保稳定性、性能和持续创新。本文件提供了对于发布计划的概述、预期的发布类型，以及用户如何保持知情和准备。

## webforJ 发布类型 {#types-of-webforj-releases}

webforJ 遵循结构化的发布模型，包括以下类型的发布：

### 1. 主要发布 {#1-major-releases}
- 每年发生一次。
- 引入显著的新功能、改进和增强。
- 可能需要对现有应用进行配置更改或适应。
- 通过版本号识别，如 **webforJ 20.00, webforJ 21.00 等。**

### 2. 次要发布 {#2-minor-releases}
- 每年发生多次（大约每六到八周）。
- 提供增量改进、优化和小幅新功能。
- 通过版本号识别，如 **webforJ 20.01, webforJ 20.02 等。**

### 3. 补丁和错误修复发布 {#3-patches-and-bug-fix-releases}
- 如有需要则发布。
- 解决关键的错误、性能问题和安全漏洞。
- 通过额外的编号识别，如 **webforJ 20.01.1, webforJ 20.01.2 等。**

## 每个发布的预期内容 {#what-to-expect-with-each-release}

### 功能增强 {#feature-enhancements}
- 主要和次要发布引入新的功能、优化和集成。
- 功能路线图在发布说明中共享，以帮助用户提前规划。

:::info 向后兼容性
尽管努力保持兼容性，但主要发布可能包括需要调整应用的更改。用户被鼓励查看发布说明，以了解已弃用的功能。
:::

### 安全更新 {#security-updates}
- 安全性是优先事项，关键漏洞将在补丁发布中尽快解决。

:::tip 快照构建
快照构建在大多数发布之前提供。鼓励用户提前对其进行测试，以便及早识别问题并提供反馈。
:::

## 如何保持更新 {#how-to-stay-updated}

### 发布说明和公告 {#release-notes-and-announcements}
- 每次发布都会附带详细的 [发布说明](https://github.com/webforj/webforj/releases)，概述新功能、错误修复及任何必要的操作。
- 用户应订阅 webforJ [博客](../../blog)，以获取及时更新。

:::tip 升级建议
客户应根据业务需求和稳定性要求计划升级。用户被鼓励保持在最新版本，以享受性能增强和新功能。
:::

### 支持和兼容性 {#support-and-compatibility}
- webforJ 提供主要发布的文档和升级指南。
- 社区论坛和客户支持渠道可用于故障排除和帮助。

<DocCardList className="topics-section" />
