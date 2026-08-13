---
title: Frontend bundler
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Understand how the webforJ frontend bundler turns class-level annotations into
  compiled, per-route frontend assets, when to reach for it, and how a class
  binds to the entry it needs.
_i18n_hash: 1276a78ae5197924d6577eae5bafe73b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Regroupement frontend <DocChip chip='since' label='26.01' /> {#frontend-bundler}

Le regroupement frontend permet à une classe webforJ de s'appuyer sur l'écosystème [npm](https://www.npmjs.com/), d'écrire des composants en [React](https://react.dev/), [Svelte](https://svelte.dev/) ou [Lit](https://lit.dev/), et d'écrire du [SCSS](https://sass-lang.com/), le tout depuis un projet Java sans installer ni exécuter de chaîne d'outils Node. Une classe nomme le frontend dont elle a besoin avec une annotation, et la construction installe les paquets, compile les sources et charge le résultat lorsqu'un composant de cette classe est créé.

Le regroupement fonctionne dans le cadre du [plugin de construction webforJ](/docs/configuration/build-plugin), que vous ajoutez une fois à votre construction Maven ou Gradle. Les mécanismes de compilation d'un type spécifique de source, SCSS, [Less](https://lesscss.org/), [Tailwind](https://tailwindcss.com/) et autres, relèvent de la responsabilité des [extensions](/docs/managing-resources/bundler/extensions/overview).

## Quand vous avez besoin du regroupement {#when-you-need-the-bundler}

webforJ fonctionne sans regroupement. Si vous avez déjà un script ou une feuille de style et que vous souhaitez l'attacher à un composant ou à l'application, les [annotations d'actifs](/docs/managing-resources/importing-assets) le font sans étape de construction, sans npm et sans compilation.

Le regroupement trouve sa place lorsque le frontend est plus qu'un fichier statique :

- Vous voulez un package de npm, par nom et version, installé et compilé dans votre application.
- Vous voulez écrire un composant en React, Svelte ou Lit et l'utiliser depuis Java.
- Vous souhaitez rédiger du SCSS, Sass ou Less, ou compiler des utilitaires Tailwind.
- Vous voulez exécuter des tests frontend dans le cadre de la construction.

Le regroupement est le chemin par défaut pour ce travail, et il fait tout ce que les annotations d'actifs font, de sorte qu'un projet qui l'adopte ne perde pas l'option plus simple.

## Ajout à un projet existant {#adding-it-to-an-existing-project}

Le regroupement est optionnel, vous pouvez donc l'ajouter à une application qui utilise déjà les [annotations d'actifs](/docs/managing-resources/importing-assets) et n'y recourir que lorsque vous en avez besoin.

1. Ajoutez le [plugin de construction webforJ](/docs/configuration/build-plugin) à votre construction Maven ou Gradle. Il gère Bun pour vous, il n'y a donc pas de chaîne d'outils Node à installer.
2. Rédigez vos sources frontend sous `src/main/frontend`.
3. Déclarez ce dont une classe a besoin avec `@BundlePackage` et `@BundleEntry`.

Vos `@StyleSheet`, `@JavaScript` et les autres annotations d'actifs existantes continuent de fonctionner sans changement, vous pouvez donc transférer une ressource vers le regroupement lorsque vous voulez un package, une source compilée ou un test, et laisser le reste intact.

## Lier une classe à une entrée {#binding-a-class-to-an-entry}

`@BundleEntry` lie une classe à une entrée frontend, et `@BundlePackage` déclare les paquets npm que cette entrée importe. Les deux vivent sur la classe, de sorte que le frontend dont une vue a besoin accompagne la vue.

```java title="GreetingView.java"
@Route("/greeting")
@BundlePackage(value = "lit", version = "^2.0.0")
@BundleEntry("greeting/hello-greeting.ts")
public class GreetingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public GreetingView() {
    self.add(new Element("hello-greeting"));
  }
}
```

```ts title="greeting/hello-greeting.ts"
import { LitElement, html } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement("hello-greeting")
class HelloGreeting extends LitElement {
  render() {
    return html`<p>Salutations de webforJ</p>`;
  }
}
```

L'entrée enregistre l'élément personnalisé `hello-greeting` et possède son rendu. Le côté Java l'utilise avec `new Element("hello-greeting")` et écoute ses événements. La construction compile l'entrée, installe `lit`, et charge la sortie lorsque `/greeting` se rend.

## Sujets {#topics}

<DocCardList className="topics-section" />
