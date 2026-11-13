---
sidebar_class_name: experimental-content
title: Escribir tu propia extensión
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: bffbcd5a76f34d542765e3c2b59ed24d
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

Puedes añadir un compilador para otro tipo de código fuente mediante una extensión que respete el mismo contrato que usan las extensiones integradas. Así, una extensión personalizada se integra igual que una incluida de serie.

Esta página desarrolla una extensión para **[Svelte](https://svelte.dev/)** como ejemplo completo. Svelte es un framework de componentes cuyos archivos `.svelte` se compilan a JavaScript durante la compilación. El empaquetador no gestiona ese código fuente por sí solo, así que para usar un componente de Svelte desde webforJ hay que enseñar al proceso de compilación a compilarlo; precisamente eso es lo que hace una extensión. Por eso es un ejemplo adecuado: la extensión que escribes enseña al proceso de compilación a manejar un nuevo tipo de código fuente, que luego se vincula a una clase igual que cualquier otra entrada.

<!-- Do NOT use <ExperimentalWarning /> on this page. -->
:::info API en vista previa
La API de extensiones está en vista previa y puede cambiar entre versiones mientras se estabiliza. El mecanismo en sí ha llegado para quedarse; las extensiones integradas se basan en el mismo contrato. Sin embargo, las firmas de sus métodos pueden cambiar, así que prevé tener que adaptar una extensión personalizada al actualizar webforJ.
:::

## La dependencia de compilación {#the-build-time-dependency}

Para escribir una extensión, debes compilar contra la API del empaquetador. Añádela con el alcance `provided`, ya que la proporciona el proceso de compilación y no debes incluirla en el paquete:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## El envoltorio del plugin {#the-bun-plugin-wrapper}

Una extensión aporta un plugin de Bun. Guarda el plugin en un recurso `.mjs` pequeño. Exporta una fábrica por defecto a la que el proceso de compilación llama con las opciones configuradas para esta extensión y que devuelve el plugin de Bun:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## La extensión {#the-extension}

Implementa `BundleExtension`. La extensión se identifica con un id, decide cuándo activarse y, en `onWillBundle`, declara sus paquetes y aporta el envoltorio:

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

En este ejemplo:

- `getId()` devuelve el id que se usa para activar o desactivar la extensión y para identificar sus opciones. Cumple la misma función que `webforj-scss` para la extensión SCSS.
- `isEnabledByDefault` activa la extensión cuando hay un archivo de código fuente `.svelte`; es la regla de [activación por tipo de archivo](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) que siguen las extensiones seleccionadas.
- `onWillBundle` declara las dependencias de compilación que necesita el compilador y aporta el plugin de Bun con el id de la extensión.

## Registro de la extensión {#registering-the-extension}

El proceso de compilación descubre las extensiones como servicios en el classpath. Añade un archivo de servicio con el nombre de `BundleExtension` y el nombre de clase completo:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Una vez creado el archivo de servicio, el código fuente `.svelte` se compila y la vista utiliza el elemento que registra el componente.

## Opciones {#options}

La extensión reenvía directamente al plugin de Bun las opciones que configures bajo su id. Configúralas en `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
