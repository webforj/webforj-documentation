---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
description: >-
  Organize webforJ routes into parent-child trees with view routes, layout
  routes, outlets, and nested components.
_i18n_hash: 4bfc9c9d46d57c866c67a2baaf2e3c3a
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# 路由层级

路由被组织成一个分层的树结构，使开发者能够定义布局、管理视图，并在应用的各个部分动态渲染组件。

在构建一个 webforJ 可路由应用时，你将遇到的关键概念包括：

- **路由层级**：将路由组织为父子结构，以便进行模块化 UI 开发。
- **路由类型**：路由分为 **视图路由** 或 **布局路由**，每种路由具有不同的目的。
- **嵌套路由**：路由可以相互嵌套，允许父组件在指定的插槽中渲染子组件。
- **插槽**：组件，其中子视图被动态注入到父布局中。
- **布局**：特殊路由，包裹子组件而不影响 URL，提供共享的 UI 元素，如页眉、页脚或侧边栏。

## 主题 {#topics}

<DocCardList className="topics-section" />
