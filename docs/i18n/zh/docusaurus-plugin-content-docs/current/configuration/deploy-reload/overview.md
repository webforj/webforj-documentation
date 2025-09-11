---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: 2e8bf7fded04e11ec6bab6d8a7c1c2b5
---
高效的开发工作流依赖于能够检测代码更改并实时自动更新应用的工具。持续部署和动态重载共同简化了开发过程，减少了手动步骤，让您能够快速查看更改，而无需手动重启服务器。

## Redeployment {#redeployment}

Java 开发中的重新部署是指自动检测和部署代码更改，以便更新能在应用中反映而无需手动重启服务器。这个过程通常涉及即时更新 Java 类和网络资源。

在 webforJ 应用中，这意味着每当对代码进行修改时，都会重新生成 WAR 文件。

对类路径上的 Java 类和资源的更改通常由 IDE 监视。当 Java 类被修改并且文件被保存时，无论是由 IDE 自动完成还是开发者手动完成，这些工具便会启动，编译并将更新的类文件放入目标目录中，以应用这些更改。

可以添加自动化或优化浏览器重载的工具和设置，以获得更无缝的体验。

## Live reload {#live-reload}

实时重载确保一旦更改被部署，浏览器会实时反映这些更新，而无需手动刷新浏览器。

在 webforJ 应用中，实时重载可以自动刷新视图，实现重新渲染组件，以显示应用的最新状态，甚至在需要时按需补丁更改。

## Topics {#topics}

<DocCardList className="topics-section" />
