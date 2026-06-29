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

Agregas un compilador para otro tipo de fuente enviando una extensión, en el mismo contrato que las extensiones integradas usan, así que una extensión personalizada se lee exactamente como una enviada.

Esta página construye una **[Svelte](https://svelte.dev/)** extensión como un ejemplo completo. Svelte es un marco de componentes cuyos archivos `.svelte` se compilan a JavaScript en tiempo de construcción. El empaquetador no maneja esa fuente por sí mismo, así que consumir un componente Svelte desde webforJ significa enseñar al sistema de construcción a compilarlo, que es exactamente lo que hace una extensión. Eso lo convierte en un ejemplo adecuado: un nuevo tipo de fuente, enseñado al sistema de construcción por la extensión que escribes, luego vinculado a una clase de la misma manera que cualquier otra entrada.

<!-- No utilices <ExperimentalWarning /> en esta página. -->
:::warning API experimental
La API de extensión es experimental y puede cambiar entre versiones mientras se estabiliza. El mecanismo en sí permanecerá, las extensiones integradas se construyen sobre el mismo contrato, pero sus firmas de métodos pueden cambiar, así que espera tener que ajustar una extensión personalizada cuando actualices webforJ.
:::

## La dependencia en tiempo de construcción {#the-build-time-dependency}

Para escribir una extensión compilas contra la API del empaquetador. Agrégala con el alcance `provided`, ya que la construcción la suministra y tú no la envías:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## El contenedor del plugin {#the-bun-plugin-wrapper}

Una extensión contribuye un plugin de Bun. Mantén el plugin en un pequeño recurso `.mjs`. Exporta por defecto una fábrica que el sistema de construcción llama con las opciones que configuras para esta extensión, y devuelve el plugin de Bun:

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## La extensión {#the-extension}

Implementa `BundleExtension`. Se nombra a sí misma con un id, decide cuándo se activa, y en `onWillBundle` declara sus paquetes y contribuye el contenedor:

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

- `getId()` devuelve el id utilizado para activar o desactivar la extensión y para clavear sus opciones, el mismo rol que `webforj-scss` desempeña para la extensión SCSS.
- `isEnabledByDefault` activa la extensión cuando hay una fuente `.svelte`, la regla [activar por tipo de archivo](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) que siguen las extensiones curadas.
- `onWillBundle` declara las dependencias de construcción que el compilador necesita y contribuye el plugin de Bun bajo el id de la extensión.

## Registrando la extensión {#registering-the-extension}

El sistema de construcción descubre una extensión como un servicio en el classpath. Agrega un archivo de servicio nombrado para `BundleExtension`, con el nombre de la clase completamente calificada:

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Con el archivo de servicio en su lugar, una fuente `.svelte` ahora se compila, y la vista consume el elemento que el componente registra.

## Opciones {#options}

La extensión reenvía las opciones que configuras bajo su id directamente al plugin de Bun. Establece en `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* opciones de bun-plugin-svelte */ }
};
```
