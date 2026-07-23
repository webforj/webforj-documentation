---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: 1b9e4b7fe64a9bcb0aa2aa16b0866ec9
---
高效的开发工作流依赖于能够检测代码更改并自动实时更新应用程序的工具。持续部署和动态重载协同工作，通过减少手动步骤来简化开发过程，让您快速查看更改而无需手动重启服务器。

## 重新部署 {#redeployment}

在Java开发中，重新部署指的是自动检测和部署代码更改，因此更新可以在应用程序中反映出来，而无需手动重启服务器。此过程通常涉及实时更新Java类和网络资源。

在webforJ应用程序中，这意味着每当对代码进行修改时，都要重新生成WAR文件。

通常，IDE会监控类路径上的Java类和资源的更改。当Java类被修改并保存时，无论是IDE自动保存还是开发者手动保存，这些工具会启动编译并将更新的类文件放入目标目录以应用更改。

为了获得最佳体验，请将自动重新部署与能够自动重载浏览器的工具或设置结合使用。

## 实时重载 {#live-reload}

一旦更改被部署，实时重载会自动重新加载应用程序，使得浏览器立即反映更新，而无需手动刷新浏览器。

在webforJ应用程序中，实时重载可以自动刷新视图，重新渲染组件以显示应用程序的最新状态，或者在需要时按需修补更改。

对于前端源，[frontend watch](/docs/configuration/deploy-reload/frontend-watch)会在每次更改时重建，并在原地修补样式表或图像，只有当脚本发生更改时才会重新加载视图。

## 主题 {#topics}

<DocCardList className="topics-section" />
