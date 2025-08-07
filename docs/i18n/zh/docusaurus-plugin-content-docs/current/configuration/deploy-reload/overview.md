---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: d98e2e989be9fbc147c128f8c46f905e
---
高效的开发工作流依赖于能够检测代码更改并实时自动更新应用的工具。持续部署和动态重新加载共同简化了开发过程，通过减少手动步骤，使您能够快速看到更改，而无需手动重新启动服务器。

## 重新部署 {#redeployment}

在Java开发中，重新部署是指自动检测和部署代码更改，以便更新在应用中反映，无需手动重启服务器。这个过程通常涉及即时更新Java类和网页资源。

在webforJ应用中，这意味着每当对代码进行修改时，都要重新生成WAR文件。

Java类和类路径上资源的更改通常由IDE监控。当Java类被修改并保存文件时，无论是由IDE自动保存还是开发者手动保存，这些工具会自动编译并将更新后的类文件放入目标目录，以应用这些更改。

可以添加自动化或优化浏览器重新加载的工具和设置，以获得更无缝的体验。

## 实时重新加载 {#live-reload}

实时重新加载确保一旦更改被部署，浏览器能够实时反映这些更新，而无需手动刷新浏览器。

在webforJ应用中，实时重新加载可以自动刷新的视图，重新渲染组件以显示应用的最新状态，甚至按需修补更改。

## 主题 {#topics}

<DocCardList className="topics-section" />
