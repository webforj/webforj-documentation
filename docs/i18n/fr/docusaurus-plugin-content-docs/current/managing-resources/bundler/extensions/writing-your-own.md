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

Vous ajoutez un compilateur pour un autre type de source en expédiant une extension, sur le même contrat que les extensions intégrées, de sorte qu'une extension personnalisée se lise exactement comme une extension expédiée.

Cette page construit une **[Svelte](https://svelte.dev/)** extension comme exemple complet. Svelte est un cadre de composants dont les fichiers `.svelte` se compilent en JavaScript au moment de la construction. Le bundler ne gère pas cette source par lui-même, donc consommer un composant Svelte depuis webforJ signifie enseigner à la construction comment le compiler, ce que fait exactement une extension. Cela en fait un exemple approprié : un nouveau type de source, enseigné à la construction par l'extension que vous écrivez, puis lié à une classe de la même manière que toute autre entrée.

<!-- Ne pas utiliser <ExperimentalWarning /> sur cette page. -->
:::warning API expérimentale
L'API d'extension est expérimentale et peut changer entre les versions pendant qu'elle se stabilise. Le mécanisme lui-même est là pour rester, les extensions intégrées sont basées sur le même contrat, mais ses signatures de méthode peuvent évoluer, donc attendez-vous à ajuster une extension personnalisée lorsque vous mettez à jour webforJ.
:::

## La dépendance au moment de la construction {#the-build-time-dependency}

Pour écrire une extension, vous compilez contre l'API du bundler. Ajoutez-la avec le scope `provided`, puisque la construction la fournit et que vous ne l'expédiez pas :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-bundle-bun</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

## L'enveloppe du plugin {#the-bun-plugin-wrapper}

Une extension contribue à un plugin Bun. Gardez le plugin dans une petite ressource `.mjs`. Il exporte par défaut une fabrique que la construction appelle avec les options que vous avez définies pour cette extension, et renvoie le plugin Bun :

```js title="src/main/resources/frontend-extensions/svelte.mjs"
import { SveltePlugin } from 'bun-plugin-svelte';

export default (options) => SveltePlugin({ forceSide: 'client', ...options });
```

## L'extension {#the-extension}

Implémentez `BundleExtension`. Elle se nomme avec un id, décide quand elle s'active, et dans `onWillBundle`, déclare ses paquets et contribue à l'enveloppe :

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

- `getId()` retourne l'id utilisé pour activer ou désactiver l'extension et pour clé ses options, le même rôle que `webforj-scss` joue pour l'extension SCSS.
- `isEnabledByDefault` active l'extension quand une source `.svelte` est présente, la règle [activer par type de fichier](/docs/managing-resources/bundler/extensions/overview#activated-by-file-type) que suivent les extensions curées.
- `onWillBundle` déclare les dépendances de construction dont le compilateur a besoin et contribue le plugin Bun sous l'id de l'extension.

## Enregistrement de l'extension {#registering-the-extension}

La construction découvre une extension comme un service sur le classpath. Ajoutez un fichier de service nommé pour `BundleExtension`, avec le nom de classe entièrement qualifié :

```text title="META-INF/services/com.webforj.bundle.bun.BundleExtension"
com.example.SvelteExtension
```

Avec le fichier de service en place, une source `.svelte` se compile maintenant, et la vue consomme l'élément que le composant enregistre.

## Options {#options}

L'extension transmet les options que vous définissez sous son id directement au plugin Bun. Définissez-les dans `bun.config.ts` :

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'svelte': { /* options du bun-plugin-svelte */ }
};
```
