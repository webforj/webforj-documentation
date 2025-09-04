---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: ec300e413c9fab01c4723f90f0e4c532
---
高效的开发工作流程依赖于能够检测代码更改并自动实时更新应用的工具。持续部署和动态重载协同工作，通过减少手动步骤来简化开发过程，让您能够快速查看更改，而无需手动重启服务器。

## Redeployment {#redeployment}

在 Java 开发中，重新部署指的是自动检测和部署代码更改，因此更新在应用中反映出来，而无需手动重启服务器。这个过程通常涉及即时更新 Java 类和网络资源。

在 webforJ 应用中，这意味着每当对代码进行修改时，都会重新生成 WAR 文件。

IDE 通常监控类路径中 Java 类和资源的更改。当 Java 类被修改并且文件被保存时，无论是由 IDE 自动完成还是由开发者手动完成，这些工具都会启动，编译并将更新后的类文件放入目标目录，以应用这些更改。

可以添加自动化或优化浏览器重载的工具和设置，以获得更流畅的体验。

## Live reload {#live-reload}

实时重载确保一旦更改被部署，浏览器将实时反映这些更新，而无需手动刷新浏览器。

在 webforJ 应用中，实时重载可以自动刷新视图，重新渲染组件以显示应用的最新状态，甚至在需要时按需修补更改。

## Topics {#topics}

<DocCardList className="topics-section" />
