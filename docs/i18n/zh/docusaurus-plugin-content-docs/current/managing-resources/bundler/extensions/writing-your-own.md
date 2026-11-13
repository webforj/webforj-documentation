---
sidebar_class_name: experimental-content
title: 编写自己的扩展
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: bffbcd5a76f34d542765e3c2b59ed24d
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

通过发布一个扩展，你可以为另一种源文件类型添加编译器。扩展遵循与内置扩展相同的契约，因此自定义扩展的用法与随附扩展完全一致。

本页将构建一个 **[Svelte](https://svelte.dev/)** 扩展作为完整示例。Svelte 是一个组件框架，其 `.svelte` 文件会在构建时编译为 JavaScript。打包器本身不会处理这种源文件，因此要在 webforJ 中使用 Svelte 组件，就需要让构建流程能够编译它，而这正是扩展的作用。这使它成为一个合适的示例：通过你编写的扩展，让构建流程学会处理一种新的源文件类型，然后像绑定其他任何入口一样，将它绑定到一个类。

<!-- Do NOT use <ExperimentalWarning /> on this page. -->
:::info 预览版 API
扩展 API 目前处于预览阶段，在逐步完善的过程中可能会随版本发布而变化。其机制本身会保留，内置扩展也遵循相同的契约，但方法签名可能会调整，因此升级 webforJ 时，请做好相应调整自定义扩展的准备。
:::

## 构建时依赖 {#the-build-time-dependency}

要编写扩展，你需要针对打包器 API 进行编译。请添加 `provided` 作用域，因为构建流程会提供它，无需将其打包发布：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## 插件封装器 {#the-bun-plugin-wrapper}

扩展会贡献一个 Bun 插件。将插件保存在一个精简的 `.mjs` 资源文件中。它默认导出一个工厂函数，构建流程会使用你为此扩展设置的选项调用该函数，并返回 Bun 插件：

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## 扩展 {#the-extension}

实现 `BundleExtension`。扩展通过 id 标识自身，确定何时启用，并在 `onWillBundle` 中声明其依赖包并贡献封装器：

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

- `getId()` 返回用于启用或停用扩展以及索引其选项的 id；对于 SCSS 扩展，这个作用由 `webforj-scss` 承担。
- 当存在 `.svelte` 源文件时，`isEnabledByDefault` 会启用扩展。这是精选扩展遵循的[按文件类型激活](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type)规则。
- `onWillBundle` 会声明编译器所需的构建依赖，并以扩展的 id 为键贡献 Bun 插件。

## 注册扩展 {#registering-the-extension}

构建流程会将类路径上的扩展作为服务发现。添加一个以 `BundleExtension` 命名的服务文件，并在其中写入完全限定类名：

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

添加服务文件后，`.svelte` 源文件即可编译，视图也可以使用该组件注册的元素。

## 选项 {#options}

扩展会将你在其 id 下设置的选项直接转发给 Bun 插件。在 `bun.config.ts` 中设置这些选项：

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
