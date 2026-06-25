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

Le bundler ne sait pas comment compiler une feuille de style SCSS de lui-même. C'est le travail d'une **extension** : une unité qui contribue au compilateur pour un type de source, déclare les packages npm dont ce compilateur a besoin, et peut générer ses propres entrées. webforJ expédie quelques extensions, et le modèle est ouvert, donc vous pouvez ajouter les vôtres pour toute autre source. SCSS, Less et Tailwind ne sont pas des fonctionnalités séparées ajoutées au bundler. Ce sont des extensions, et elles partagent un seul modèle.

Une fois que vous savez comment une extension s'active et ce qu'elle contribue, chaque extension se lit de la même manière, celles fournies et celles que vous écrivez.

## Ce qu'une extension contribue {#what-an-extension-contributes}

Une extension a la possibilité d'agir avant que la compilation ne s'effectue. Lorsqu'elle agit, elle peut faire trois choses :

- **Déclarer des packages.** Elle ajoute les packages npm dont son compilateur a besoin, généralement comme dépendances de construction, afin qu'ils soient installés avant la compilation.
- **Contribuer un compilateur.** Elle enregistre un plugin Bun qui apprend au compilateur comment gérer un type de source.
- **Générer des entrées.** Elle peut écrire une entrée frontend de sa propre, ce qui est comment Tailwind produit une feuille de style sans que vous en écriviez une.

Chaque extension porte un **id**, un nom court tel que `webforj-scss` ou `webforj-less`. L'id est la façon dont vous vous référez à une extension dans la configuration, et comment ses options l'atteignent.

## Comment une extension s'active {#how-an-extension-activates}

Une extension effectue son travail uniquement lorsqu'elle est active. Il existe trois façons dont une extension devient active, et savoir quel chemin une intégration utilise vous dit exactement quand elle s'exécute.

### Activée par type de fichier {#activated-by-file-type}

Une extension curée déclare les extensions de fichier qu'elle gère, et elle s'active elle-même lorsqu'une source de ce type est présente sous `src/main/frontend`. Vous ne l'activez pas. L'écriture du fichier est le signal.

Écrivez un fichier `.scss`, et l'extension SCSS s'active. Écrivez un fichier `.less`, et l'extension Less s'active. Supprimez la dernière source de ce type, et l'extension reste désactivée, donc ses packages ne sont jamais installés et son compilateur ne s'exécute jamais. Cela garde une construction légère : un projet ne paie que pour les types de source qu'il écrit réellement. Une extension que vous écrivez suit la même règle, s'activant sur le type de fichier qu'elle déclare.

### Activée par id {#enabled-by-id}

Chaque extension peut être activée ou désactivée par son id, qui remplace le réglage par défaut du type de fichier. Cela a de l'importance dans deux cas. Une extension qui est par défaut désactivée, comme Tailwind, est activée par id. Et une extension qui s'activerait à partir d'une source présente peut être forcée à rester désactivée par id.

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

Le bloc ci-dessus active Tailwind et désactive la compilation SCSS, même si une source `.scss` est présente.

### Quand aucune extension n'est nécessaire {#when-no-extension-is-needed}

Une extension existe pour contribuer un compilateur. Un framework qui s'exécute à l'exécution, plutôt que de compiler quelque chose de nouveau, n'a pas besoin de compilateur, donc n'a pas besoin d'extension. Bun gère déjà le TypeScript et JSX dont un tel framework est écrit.

[React](https://react.dev/) est une bibliothèque JavaScript pour construire des interfaces utilisateur, et le cas le plus clair. Son entrée est un plain TypeScript que Bun compile sans plugin. Vous encapsulez le composant en tant qu'élément personnalisé avec une bibliothèque de votre choix, comme `@r2wc/react-to-web-component`, déclarez les packages React avec `@BundlePackage`, et la vue consomme l'élément résultant. Il n'y a pas d'extension React à activer, car il n'y a pas de compilateur React à contribuer, juste des packages à installer.

## Configurer une extension {#configuring-an-extension}

Certaines extensions prennent des options. Le compilateur SCSS prend un chemin de chargement, par exemple. Les options se trouvent dans `bun.config.ts`, un fichier que vous créez à la racine de votre source frontend, `src/main/frontend/bun.config.ts`, à côté des entrées que vous rédigez. Elles vont dans un objet `options` indexé par l'id de l'extension, et la construction remet à chaque extension l'objet stocké sous son propre id :

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

:::tip D'où viennent les options
Une extension transmet ses options directement à l'outil qu'elle enveloppe, donc les options qu'une extension accepte sont les options de cet outil. Chaque page d'extension ci-dessous nomme l'outil vers lequel elle renvoie et lie à la référence de cet outil, où vous trouverez la liste complète.
:::

Le même fichier peut ajouter des plugins Bun supplémentaires via une exportation par défaut, pour une étape de construction qu'aucune extension curée ne couvre :

```ts title="src/main/frontend/bun.config.ts"
import myPlugin from './my-plugin';

export default [myPlugin()];
```

## Les extensions curées {#the-curated-extensions}

webforJ expédie une extension pour SCSS, Less et Tailwind. Chacune suit le modèle ci-dessus : elle s'active lorsque son type de source est présent, déclare les packages dont son compilateur a besoin comme dépendances de construction, et contribue au compilateur.

| Extension | Id | S'active sur | Installe (dépendance de construction) |
|-----------|----|--------------|--------------------------------------|
| [SCSS et Sass](/docs/managing-resources/bundler/extensions/scss) | `webforj-scss` | une source `.scss` ou `.sass` | `sass` |
| [Less](/docs/managing-resources/bundler/extensions/less) | `webforj-less` | une source `.less` | `less` |
| [Tailwind](/docs/managing-resources/bundler/extensions/tailwind) | `webforj-tailwind` | désactivée par défaut, activée par id | `tailwindcss`, `bun-plugin-tailwind` |

Pour compiler un autre type de source, vous écrivez votre propre extension selon le même contrat. Voir [Écrire votre propre extension](/docs/managing-resources/bundler/extensions/writing-your-own), qui en construit une de bout en bout.
