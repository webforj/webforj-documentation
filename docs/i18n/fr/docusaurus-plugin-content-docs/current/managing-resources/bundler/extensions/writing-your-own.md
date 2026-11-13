---
sidebar_class_name: experimental-content
title: Écrire votre propre extension
sidebar_position: 70
description: >-
  Add a build step by shipping a BundleExtension that declares packages,
  contributes a Bun plugin, and activates on a file type, shown by building a
  Svelte extension end to end.
_i18n_hash: bffbcd5a76f34d542765e3c2b59ed24d
---
<DocChip chip='since' label='26.01' />
<DocChip chip='experimental' />

Vous pouvez ajouter un compilateur pour un autre type de source en fournissant une extension qui respecte le même contrat que les extensions intégrées. Ainsi, une extension personnalisée se présente exactement comme une extension fournie avec webforJ.

Cette page présente une extension **[Svelte](https://svelte.dev/)** complète. Svelte est un framework de composants dont les fichiers `.svelte` sont compilés en JavaScript au moment de la compilation. Le bundler ne traite pas ce type de source de lui-même. Pour utiliser un composant Svelte avec webforJ, il faut donc apprendre au processus de compilation à le compiler : c’est précisément le rôle d’une extension. Cet exemple est donc particulièrement adapté : un nouveau type de source, pris en charge par le processus de compilation grâce à l’extension que vous écrivez, puis associé à une classe comme n’importe quelle autre entrée.

<!-- Do NOT use <ExperimentalWarning /> on this page. -->
:::info API en préversion
L’API des extensions est en préversion et peut évoluer d’une version à l’autre pendant sa stabilisation. Le mécanisme lui-même est là pour durer, et les extensions intégrées reposent sur le même contrat, mais les signatures de ses méthodes peuvent changer. Prévoyez donc d’adapter votre extension personnalisée lors de la mise à niveau de webforJ.
:::

## Dépendance au moment de la compilation {#the-build-time-dependency}

Pour écrire une extension, vous devez compiler votre code avec l’API du bundler. Ajoutez-la avec une portée `provided`, puisque le processus de compilation la fournit et que vous ne l’incluez pas dans votre livrable :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## Enveloppe du plug-in Bun {#the-bun-plugin-wrapper}

Une extension fournit un plug-in Bun. Placez le plug-in dans une petite ressource `.mjs`. Celle-ci exporte par défaut une fabrique que le processus de compilation appelle avec les options définies pour cette extension, et qui renvoie le plug-in Bun :

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## L’extension {#the-extension}

Implémentez `BundleExtension`. L’extension s’identifie à l’aide d’un ID, détermine quand elle s’active et déclare ses packages et fournit son enveloppe dans `onWillBundle` :

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

Dans cet exemple :

- `getId()` renvoie l’ID utilisé pour activer ou désactiver l’extension et définir ses options. Il joue le même rôle que `webforj-scss` pour l’extension SCSS.
- `isEnabledByDefault` active l’extension lorsqu’une source `.svelte` est présente, conformément à la règle [d’activation par type de fichier](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) suivie par les extensions sélectionnées.
- `onWillBundle` déclare les dépendances de compilation requises par le compilateur et fournit le plug-in Bun sous l’ID de l’extension.

## Enregistrement de l’extension {#registering-the-extension}

Le processus de compilation découvre une extension en tant que service présent dans le classpath. Ajoutez un fichier de service portant le nom de `BundleExtension` et contenant le nom de classe complet :

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Une fois le fichier de service en place, les sources `.svelte` sont compilées, et la vue utilise l’élément enregistré par le composant.

## Options {#options}

L’extension transmet directement au plug-in Bun les options que vous définissez sous son ID. Définissez-les dans `bun.config.ts` :

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* bun-plugin-svelte options */ }
};
```
