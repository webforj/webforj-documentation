---
title: Writing your own extension
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: fb042c0be37d53bf5f46eb1536056275
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

您可以通过发布扩展来为另一种源添加编译器，在与内置扩展相同的契约上，自定义扩展的读取方式与已发布的扩展完全相同。

本页以 **[Svelte](https://svelte.dev/)** 扩展为完整示例。Svelte 是一个组件框架，其 `.svelte` 文件在构建时编译为 JavaScript。打包器无法单独处理该源，因此从 webforJ 使用 Svelte 组件意味着需要教会构建如何编译它，这正是扩展的作用。这使得它成为一个合适的示例：一种新源类型，通过您编写的扩展教学构建，然后以任何其他条目的相同方式绑定到一个类。

<!-- 此页面上请勿使用 <ExperimentalWarning />。 -->
:::warning 实验性 API
扩展 API 是实验性的，可能会在发布之间发生变化，直到稳定。该机制本身是会保留的，内置扩展也是基于相同的契约构建的，但其方法签名可能会发生变化，因此在升级 webforJ 时，请期待调整自定义扩展。
:::

## 构建时依赖 {#the-build-time-dependency}

要编写扩展，您需要针对打包器 API 进行编译。使用 `provided` 范围将其添加，因为构建提供了它，而您并不打包它：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## 插件包装器 {#the-bun-plugin-wrapper}

扩展贡献一个 Bun 插件。将插件保留在一个小的 `.mjs` 资源中。它默认导出一个工厂，构建会用您为此扩展设置的选项调用它，并返回 Bun 插件：

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## 扩展 {#the-extension}

实现 `BundleExtension`。它用 id 命名自己，决定何时激活，并在 `onWillBundle` 中声明其包并贡献包装器：

```java title="SvelteExtension.java"
public class SvelteExtension implements BundleExtension {

  @Override
  public String getId() {
    return "svelte";
  }

  @Override
  public boolean isEnabledByDefault(BundleContext context) {
    return context.getSourceExtensions().contains("svelte");
  }

  @Override
  public void onWillBundle(BundleContext context) {
    context.addPackage(new BundlePackageDeclaration()
        .setName("bun-plugin-svelte")
        .setVersion("^0.0.6")
        .setDev(true));
    context.addPackage(new BundlePackageDeclaration()
        .setName("svelte")
        .setVersion("^5.56.2")
        .setDev(true));

    context.addPlugin(getId(), readWrapper("/frontend-extensions/svelte.mjs"));
  }

  private String readWrapper(String path) {
    try (InputStream in = getClass().getResourceAsStream(path)) {
      if (in == null) {
        throw new IOException("missing wrapper " + path);
      }

      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
```

在此示例中：

- `getId()` 返回用于开启或关闭扩展的 id，并作为关键选项，和 `webforj-scss` 对 SCSS 扩展的角色相同。
- `isEnabledByDefault` 当存在 `.svelte` 源时激活扩展，这是策划扩展遵循的 [按文件类型激活](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) 规则。
- `onWillBundle` 声明编译器所需的构建依赖关系，并在扩展的 id 下贡献 Bun 插件。

## 注册扩展 {#registering-the-extension}

构建将在类路径上将扩展发现为服务。添加一个以 `BundleExtension` 命名的服务文件，包含完全限定的类名：

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

服务文件就位后，`.svelte` 源现在可以编译，视图会使用组件注册的元素。

## 选项 {#options}

扩展将您在其 id 下设置的选项直接转发给 Bun 插件。在 `bun.config.ts` 中进行设置：

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte 选项 */ }
};
```
