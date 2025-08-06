---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: a384d1b849730a301f5bc1d0a20e9c41
---
import DocCardList from '@theme/DocCardList';

# 路由层次结构

路由以层次树结构组织，使开发人员能够定义布局、管理视图，并在应用程序的不同部分动态渲染组件。

在构建可以路由的webforJ应用程序时，你将遇到的关键概念包括：

- **路由层次**：将路由组织成父子结构，以便进行模块化的UI开发。
- **路由类型**：路由分为**视图路由**和**布局路由**，每种类型有不同的用途。
- **嵌套路由**：路由可以相互嵌套，允许父组件在指定的插口中渲染子组件。
- **插口**：组件，其中子视图动态注入到父布局中。
- **布局**：特殊路由，可包装子组件而不对URL产生影响，提供共享的UI元素，如页眉、页脚或侧边栏。

## 主题 {#topics}

<DocCardList className="topics-section" />
