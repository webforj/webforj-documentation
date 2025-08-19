---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: 66716282278634ab574f3620a2a660ce
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# 路由层次结构

路由被组织成层次树结构，使开发者能够定义布局、管理视图，并在应用程序的各个部分动态渲染组件。

在构建可路由的 webforJ 应用时，你将遇到的关键概念包括：

- **路由层次结构**：将路由组织为父子结构，以便于模块化 UI 开发。
- **路由类型**：路由分为 **视图路由** 或 **布局路由**，各自有不同的目的。
- **嵌套路由**：路由可以相互嵌套，允许父组件在指定的插槽中渲染子组件。
- **插槽**：子视图动态注入到父布局中的组件。
- **布局**：特殊路由，包裹子组件而不向 URL 添加内容，提供诸如页眉、页脚或侧边栏等共享 UI 元素。

## 主题 {#topics}

<DocCardList className="topics-section" />
