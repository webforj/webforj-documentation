---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9b0d2672241250200ed14343e57d3926
---
高效的开发工作流程依赖于能够检测代码更改并实时自动更新应用程序的工具。持续部署和动态重载共同简化了开发过程，减少了手动步骤，使您能够快速查看更改，而无需手动重启服务器。

## 重新部署 {#redeployment}

Java开发中的重新部署是指自动检测和部署代码更改，以便更新能够在应用程序中反映，而无需手动重启服务器。此过程通常涉及按需实时更新Java类和网络资源。

在一个webforJ应用中，这意味着每当代码进行修改时，都会重新生成WAR文件。

Java类和类路径上的资源通常由IDE监控。当Java类被修改并且文件被保存时，无论是通过IDE自动保存还是由开发者手动保存，这些工具都会启动编译并将更新后的类文件放置在目标目录中以应用更改。

为了获得最佳体验，请结合使用自动重新部署和自动化浏览器重载的工具或设置。

## 实时重载 {#live-reload}

一旦更改被部署，实时重载会自动重新加载应用程序，使浏览器立即反映更新，而无需手动刷新浏览器。

在一个webforJ应用中，实时重载可以自动刷新视图，重新渲染组件以显示应用程序的最新状态，或者根据需要及时补丁更改。

## 主题 {#topics}

<DocCardList className="topics-section" />
