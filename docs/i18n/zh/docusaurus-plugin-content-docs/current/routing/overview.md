---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 115816519ca0212b84eb27923a74ca53
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

在现代 web 应用中，**路由**是指根据当前 URL 或路径管理不同视图或组件之间导航的过程。在 webforJ 中，路由建立了一个复杂的 **客户端导航** 框架，UI 更新在不需要完全重新加载页面的情况下动态发生，从而增强了应用的性能。

## 传统路由 vs 客户端路由 {#traditional-vs-client-side-routing}

在传统的服务器端路由中，当用户点击链接时，浏览器向服务器发送请求以获取新文档。服务器通过发送一个新的 HTML 页面作出响应，这迫使浏览器重新评估 CSS 和 JavaScript，重新渲染整个文档并重置应用状态。这个循环引入了延迟和低效，因为浏览器必须重新加载资源和页面状态。这个过程通常包括：

1. **请求**：用户导航到一个新的 URL，触发对服务器的请求。
2. **响应**：服务器发送一个新的 HTML 文档以及相关资产（CSS，JS）。
3. **渲染**：浏览器重新渲染整个页面，通常会丢失之前加载页面的状态。

这种方法可能导致性能瓶颈和不理想的用户体验，因为会重复执行完整的页面重载。

在 webforJ 中的**客户端路由**通过允许在浏览器中直接导航来解决这个问题，动态更新 UI 而无需向服务器发送新请求。它的工作原理如下：

1. **单一初始请求**：浏览器首次加载应用，包括所有必需的资产（HTML、CSS、JavaScript）。
2. **URL 管理**：路由器监听 URL 更改并根据当前路由更新视图。
3. **动态组件渲染**：路由器将 URL 映射到一个组件并动态渲染，而无需刷新页面。
4. **状态保持**：两个导航之间，应用的状态保持不变，确保视图之间的平滑过渡。

这种设计使得 **深链接** 和 **基于 URL 的状态管理** 成为可能，允许用户书签和分享应用内的特定页面，同时享受平滑的单页体验。

## 核心原则 {#core-principles}

- **基于 URL 的组件映射**：在 webforJ 中，路由直接与 UI 组件绑定。URL 模式映射到特定组件，决定了根据当前路径显示的内容。
- **声明式路由**：路由是声明性定义的，通常使用注释。每个路由对应一个组件，在匹配路由时渲染。
- **动态导航**：路由器在不重新加载页面的情况下动态切换视图，使应用保持响应快速。

## webforJ 中客户端路由的示例 {#example-of-client-side-routing-in-webforj}

以下是定义一个 `UserProfileView` 组件的路由示例，以根据 URL 中的 `id` 参数显示用户详细信息：

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

- 导航到 `/user/john` 会渲染 `UserProfileView` 组件。
- `id` 参数将捕获 URL 中的 `john`，允许你在组件内部使用它来获取和显示用户数据。

## 主题 {#topics}

<DocCardList className="topics-section" />
