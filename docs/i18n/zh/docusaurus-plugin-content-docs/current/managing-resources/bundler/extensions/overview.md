---
title: Extensions
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Understand how a bundler extension contributes a compiler, the three ways an
  extension activates, how to configure one through bun.config.ts, and how to
  write your own.
_i18n_hash: 00c5421ebf8023e2d273f3836176733e
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

打包工具无法独立编译 SCSS 样式表。这是一个 **扩展** 的工作：一个为某种源贡献编译器的单元，声明编译器所需的 npm 软件包，并且可以生成自己的条目。webforJ 提供了一些扩展，并且模型是开放的，因此您可以为其他源添加自己的扩展。SCSS、Less 和 Tailwind 并不是附加于打包工具的独立功能。它们是扩展，它们共享一个模型。

一旦您知道一个扩展如何激活以及它贡献了什么，每个扩展都以相同的方式读取，不论是已提供的还是您自己编写的。

## 扩展贡献的内容 {#what-an-extension-contributes}

在编译运行之前，扩展有机会进行操作。当它操作时，可以做三件事：

- **声明软件包。** 它添加编译器所需的 npm 软件包，通常作为构建依赖项，以便在编译之前安装。
- **贡献编译器。** 它注册一个 Bun 插件，教会编译如何处理某种源。
- **生成条目。** 它可以编写自己的前端条目，这就是 Tailwind 如何在您不编写样式表的情况下生成样式表的方式。

每个扩展都有一个 **id**，一个简短的名称，例如 `webforj-scss` 或 `webforj-less`。id 是您在配置中引用扩展的方式，以及其选项如何传递给它。

## 扩展如何激活 {#how-an-extension-activates}

扩展只有在激活时才会工作。有三种方式可以使其激活，了解集成使用哪个路径可以准确告知它何时运行。

### 通过文件类型激活 {#activated-by-file-type}

一个经过策划的扩展声明它所处理的文件扩展名，当在 `src/main/frontend` 下存在该类型的源时，它会自动开启。您并不需要手动启用它。编写文件就是该信号。

编写一个 `.scss` 文件，则 SCSS 扩展会激活。编写一个 `.less` 文件，则 Less 扩展会激活。移除最后一个该类型的源，扩展则保持关闭，因此它的包不会被安装，编译器也不会运行。这使得构建保持精简：一个项目只为其实际编写的源类型付费。您编写的扩展遵循相同的规则，基于其声明的文件类型进行激活。

### 通过 id 启用 {#enabled-by-id}

每个扩展均可通过其 id 开启或关闭，这会覆盖文件类型的默认设置。这在两种情况下很重要。一个默认关闭的扩展，例如 Tailwind，可以通过 id 启用。而一个可以从存在的源启用的扩展，亦可以通过 id 强制关闭。

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <plugins>
      <webforj-tailwind>true</webforj-tailwind>
      <webforj-scss>false</webforj-scss>
    </plugins>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  plugins.put('webforj-tailwind', 'true')
  plugins.put('webforj-scss', 'false')
}
```

</TabItem>
</Tabs>

上述代码启用 Tailwind，并关闭 SCSS 编译，即使存在 `.scss` 源。

### 无需扩展时 {#when-no-extension-is-needed}

扩展的存在是为了贡献一个编译器。一个在运行时渲染的框架，而不是编译成新内容，就不需要编译器，因此不需要扩展。Bun 已经处理了编写这种框架的 TypeScript 和 JSX。

[React](https://react.dev/) 是一个用于构建用户界面的 JavaScript 库，是最清晰的案例。它的入口是普通的 TypeScript，Bun 无需插件即可编译。您可以用您选择的库，如 `@r2wc/react-to-web-component` 来包装组件，使用 `@BundlePackage` 声明 React 软件包，视图则消费生成的元素。不需要启用 React 扩展，因为没有可以贡献的 React 编译器，只有需安装的软件包。

## 配置扩展 {#configuring-an-extension}

某些扩展接受选项。例如，SCSS 编译器接受加载路径。选项存在于 `bun.config.ts` 文件中，这是您在前端源根目录 `src/main/frontend/bun.config.ts` 下创建的文件，位于您所编写条目的旁边。它们放在一个以扩展 id 为键的 `options` 对象中，构建将每个扩展的对象传递给其相应 id：

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

:::tip 选项来源
扩展将其选项直接转发给它包装的工具，因此扩展接受的选项就是该工具的选项。下面的每个扩展页面都列出了它转发的工具，并链接到该工具的参考文档，在那里您可以找到完整的选项列表。
:::

同一个文件可以通过默认导出附加额外的 Bun 插件，以进行没有经过策划的扩展覆盖的构建步骤：

```ts title="src/main/frontend/bun.config.ts"
import myPlugin from './my-plugin';

export default [myPlugin()];
```

## 策划的扩展 {#the-curated-extensions}

webforJ 提供了 SCSS、Less 和 Tailwind 的扩展。每个扩展遵循上述模型：当其源类型存在时激活，声明其编译器所需的软件包作为构建依赖，并贡献编译器。

| 扩展 | Id | 激活于 | 安装（构建依赖） |
|------|----|---------|-------------------|
| [SCSS 和 Sass](/docs/managing-resources/bundler/extensions/scss) | `webforj-scss` | 一个 `.scss` 或 `.sass` 源 | `sass` |
| [Less](/docs/managing-resources/bundler/extensions/less) | `webforj-less` | 一个 `.less` 源 | `less` |
| [Tailwind](/docs/managing-resources/bundler/extensions/tailwind) | `webforj-tailwind` | 默认为关闭，通过 id 启用 | `tailwindcss`, `bun-plugin-tailwind` |

要编译另一种源类型，您需要根据相同的协议编写自己的扩展。请参见 [编写自己的扩展](/docs/managing-resources/bundler/extensions/writing-your-own)，其中构建了一个完整的端到端示例。
