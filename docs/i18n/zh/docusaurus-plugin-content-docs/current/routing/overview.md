---
sidebar_position: 1
title: 路由
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ca4837305e1ca2ca2b6a4a244c8103f1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

在现代 web 应用程序中，**路由**是指根据当前 URL 或路径管理不同视图或组件之间导航的过程。在 webforJ 中，路由建立了一个复杂的框架，以实现 **客户端导航**，UI 更新动态发生，无需进行完整的页面重新加载，从而提高了应用的性能。

## 传统路由与客户端路由 {#traditional-vs-client-side-routing}

在传统的服务器端路由中，当用户点击链接时，浏览器向服务器发送请求以获取新文档。服务器通过发送一个新的 HTML 页面进行响应，这迫使浏览器重新评估 CSS 和 JavaScript，重新渲染整个文档，并重置应用状态。这个循环引入了延迟和低效率，因为浏览器必须重新加载资源和页面状态。这个过程通常包含：

1. **请求**：用户导航到一个新 URL，触发了一个请求到服务器。
2. **响应**：服务器返回一个新的 HTML 文档以及相关资源（CSS、JS）。
3. **渲染**：浏览器重新渲染整个页面，通常会失去之前加载页面的状态。

这种方法可能导致性能瓶颈和次优的用户体验，因为需要重复的完整页面重新加载。

**客户端路由**在 webforJ 中通过直接在浏览器中启用导航解决了这个问题，动态更新 UI 而无需向服务器发送新的请求。以下是其工作原理：

1. **单次初始请求**：浏览器加载一次应用，包括所有必需的资源（HTML、CSS、JavaScript）。
2. **URL 管理**：路由器监听 URL 更改并根据当前路由更新视图。
3. **动态组件渲染**：路由器将 URL 映射到一个组件并动态渲染，而无需刷新页面。
4. **状态保持**：应用状态在导航之间保持，确保视图之间的平滑过渡。

这种设计实现了 **深度链接** 和 **基于 URL 的状态管理**，允许用户在应用中书签和分享特定页面，同时享受顺畅的单页面体验。

## 核心原则 {#core-principles}

- **基于 URL 的组件映射**：在 webforJ 中，路由直接与 UI 组件相关联。一个 URL 模式映射到一个特定组件，决定根据当前路径显示什么内容。
- **声明式路由**：路由以声明的方式定义，通常使用注释。每个路由对应一个在匹配路由时渲染的组件。
- **动态导航**：路由器动态切换视图而不重新加载页面，使应用保持响应快速。

## webforJ 中的客户端路由示例 {#example-of-client-side-routing-in-webforj}

以下是为 `UserProfileView` 组件定义路由的示例，以根据 URL 中的 `id` 参数显示用户详细信息：

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

在这个设置中：

- 导航到 `/user/john` 将渲染 `UserProfileView` 组件。
- `id` 参数将从 URL 捕获 `john`，并允许你在组件中使用它来获取和显示用户数据。

## 主题 {#topics}

<DocCardList className="topics-section" />
