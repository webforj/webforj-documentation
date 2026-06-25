---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: dc1833dbce97bdbf387e98fab07967ca
---
高效的开发工作流程依赖于能够检测代码更改并实时自动更新应用的工具。持续部署和动态重载共同简化了开发过程，通过减少手动步骤，让您能够快速查看更改，而无需手动重启服务器。

## 重新部署 {#redeployment}

Java 开发中的重新部署指的是自动检测和部署代码更改，这样更新就可以在应用中反映出来，而无需手动重启服务器。这个过程通常涉及在飞行中更新 Java 类和网络资源。

在 webforJ 应用中，这意味着每当对代码进行修改时，都会重新生成 WAR 文件。

通常，IDE 会监控类路径上的 Java 类和资源。当 Java 类被修改并保存时，无论是由 IDE 自动保存还是由开发者手动保存，这些工具会启动以编译并将更新的类文件放入目标目录以应用更改。

为了获得最佳体验，请将自动重新部署与能够自动加载浏览器的工具或设置结合使用。

## 实时重载 {#live-reload}

一旦更改被部署，实时重载会自动重新加载应用，使得浏览器立即反映更新，而无需手动刷新浏览器。

在 webforJ 应用中，实时重载可以自动刷新视图，重新渲染组件，以显示应用的最新状态，甚至根据需要动态修补更改。

对于前端源，[frontend watch](/docs/configuration/deploy-reload/frontend-watch) 会在每次更改时重建，并在原地修补样式表或图像，仅在脚本更改时重新加载视图。

## 主题 {#topics}

<DocCardList className="topics-section" />
