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

# Frontend-Bundler <DocChip chip='since' label='26.01' /> {#frontend-bundler}

Der Frontend-Bundler ermöglicht es einer webforJ-Klasse, auf das [npm](https://www.npmjs.com/) Ökosystem zuzugreifen, Komponenten in [React](https://react.dev/), [Svelte](https://svelte.dev/) oder [Lit](https://lit.dev/) zu schreiben und [SCSS](https://sass-lang.com/) zu erstellen, alles aus einem Java-Projekt, ohne dass eine Node-Toolchain installiert oder ausgeführt werden muss. Eine Klasse benennt das benötigte Frontend mit einer Annotation, und der Build installiert die Pakete, kompiliert die Quellen und lädt das Ergebnis, wenn eine Komponente dieser Klasse erstellt wird.

Der Bundler läuft als Teil des [webforJ-Build-Plugins](/docs/configuration/build-plugin), das Sie einmal zu Ihrem Maven- oder Gradle-Build hinzufügen. Die Mechanik des Kompilierens einer bestimmten Art von Quelle, SCSS, [Less](https://lesscss.org/), [Tailwind](https://tailwindcss.com/) und den Rest, ist die Aufgabe von [Erweiterungen](/docs/managing-resources/bundler/extensions/overview).

## Wenn Sie den Bundler benötigen {#when-you-need-the-bundler}

webforJ läuft ohne einen Bundler. Wenn Sie bereits ein Skript oder ein Stylesheet haben und es an eine Komponente oder die App anhängen möchten, erledigen die [Asset-Annotationen](/docs/managing-resources/importing-assets) das ohne Build-Schritt, ohne npm und ohne Kompilierung.

Der Bundler erweist sich als nützlich, wenn das Frontend mehr ist als eine statische Datei:

- Sie möchten ein Paket von npm, nach Name und Version, installiert und in Ihre App kompiliert haben.
- Sie möchten eine Komponente in React, Svelte oder Lit schreiben und sie aus Java konsumieren.
- Sie möchten SCSS, Sass oder Less erstellen oder Tailwind-Dienste kompilieren.
- Sie möchten Frontend-Tests als Teil des Builds ausführen.

Der Bundler ist der Standardweg für diese Arbeiten und erledigt alles, was die Asset-Annotationen tun, sodass ein Projekt, das ihn annimmt, keine einfachere Option verliert.

## Hinzufügen zu einem bestehenden Projekt {#adding-it-to-an-existing-project}

Der Bundler ist optional, sodass Sie ihn zu einer App hinzufügen können, die bereits die [Asset-Annotationen](/docs/managing-resources/importing-assets) verwendet, und ihn nur dort verwenden, wo Sie ihn benötigen.

1. Fügen Sie das [webforJ-Build-Plugin](/docs/configuration/build-plugin) zu Ihrem Maven- oder Gradle-Build hinzu. Es verwaltet Bun für Sie, sodass keine Node-Toolchain installiert werden muss.
2. Erstellen Sie Ihre Frontend-Quellen unter `src/main/frontend`.
3. Deklarieren Sie, was eine Klasse benötigt, mit `@BundlePackage` und `@BundleEntry`.

Ihre bestehenden `@StyleSheet`, `@JavaScript` und die anderen Asset-Annotationen funktionieren unverändert weiter, sodass Sie eine Ressource auf den Bundler verschieben können, wenn Sie ein Paket, eine kompilierte Quelle oder einen Test wünschen und den Rest in Ruhe lassen können.

## Binden einer Klasse an einen Eintrag {#binding-a-class-to-an-entry}

`@BundleEntry` verknüpft eine Klasse mit einem Frontend-Eintrag, und `@BundlePackage` deklariert die npm-Pakete, die dieser Eintrag importiert. Beide befinden sich auf der Klasse, sodass das Frontend, das eine Ansicht benötigt, mit der Ansicht mitreist.

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
    return html`<p>Grüße von webforJ</p>`;
  }
}
```

Der Eintrag registriert das benutzerdefinierte Element `hello-greeting` und besitzt das Rendering. Die Java-Seite konsumiert es mit `new Element("hello-greeting")` und hört auf die Ereignisse. Der Build kompiliert den Eintrag, installiert `lit` und lädt die Ausgabe, wenn `/greeting` gerendert wird.

## Themen {#topics}

<DocCardList className="topics-section" />
