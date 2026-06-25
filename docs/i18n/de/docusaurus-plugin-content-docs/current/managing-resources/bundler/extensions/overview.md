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

Der Bundler weiß nicht, wie er ein SCSS-Stylesheet von selbst kompilieren kann. Das ist die Aufgabe einer **Erweiterung**: eine Einheit, die den Compiler für eine Art von Quelle bereitstellt, die npm-Pakete erklärt, die der Compiler benötigt, und eigene Einträge generieren kann. webforJ liefert einige Erweiterungen, und das Modell ist offen, sodass du deine eigenen für jede andere Quelle hinzufügen kannst. SCSS, Less und Tailwind sind keine separaten Funktionen, die an den Bundler angehängt sind. Sie sind Erweiterungen und teilen sich ein Modell.

Sobald du weißt, wie eine Erweiterung aktiviert wird und was sie beiträgt, liest sich jede Erweiterung auf die gleiche Weise, die gelieferten und die, die du schreibst.

## Was eine Erweiterung beiträgt {#what-an-extension-contributes}

Eine Erweiterung erhält die Chance zu agieren, bevor die Kompilierung läuft. Wenn sie agiert, kann sie drei Dinge tun:

- **Pakete deklarieren.** Sie fügt die npm-Pakete hinzu, die ihr Compiler benötigt, normalerweise als Build-Abhängigkeiten, damit sie vor der Kompilierung installiert werden.
- **Einen Compiler beitragen.** Sie registriert ein Bun-Plugin, das dem Compiler beibringt, wie er eine Art von Quelle behandelt.
- **Einträge generieren.** Sie kann einen eigenen Frontend-Eintrag schreiben, was der Grund ist, warum Tailwind ein Stylesheet produziert, ohne dass du eines verfassen musst.

Jede Erweiterung trägt eine **ID**, einen kurzen Namen wie `webforj-scss` oder `webforj-less`. Die ID ist die, mit der du in der Konfiguration auf eine Erweiterung verweist und wie ihre Optionen sie erreichen.

## Wie eine Erweiterung aktiviert wird {#how-an-extension-activates}

Eine Erweiterung erledigt ihre Arbeit nur, wenn sie aktiv ist. Es gibt drei Möglichkeiten, wie eine aktiv wird, und zu wissen, welchen Weg eine Integration nutzt, sagt dir genau, wann sie ausgeführt wird.

### Aktiviert durch Dateityp {#activated-by-file-type}

Eine kuratierte Erweiterung erklärt die Dateierweiterungen, die sie verarbeitet, und aktiviert sich, wenn eine Quelle dieses Typs unter `src/main/frontend` vorhanden ist. Du aktivierst sie nicht. Das Verfassen der Datei ist das Signal.

Schreibe eine `.scss`-Datei, und die SCSS-Erweiterung wird aktiv. Schreibe eine `.less`-Datei, und die Less-Erweiterung wird aktiv. Entferne die letzte Quelle dieses Typs, bleibt die Erweiterung deaktiviert, sodass ihre Pakete nie installiert werden und ihr Compiler niemals läuft. Dies hält einen Build schlank: Ein Projekt zahlt nur für die Arten von Quellen, die es tatsächlich verfasst. Eine Erweiterung, die du schreibst, folgt derselben Regel und aktiviert sich beim Deklarieren des Dateityps.

### Aktiviert durch ID {#enabled-by-id}

Jede Erweiterung kann durch ihre ID ein- oder ausgeschaltet werden, die die Standardeinstellung des Dateityps überschreibt. Dies ist in zwei Fällen relevant. Eine Erweiterung, die standardmäßig deaktiviert ist, wie Tailwind, wird durch ID aktiviert. Und eine Erweiterung, die von einer vorhandenen Quelle aktiviert werden würde, kann durch ID deaktiviert werden.

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

Der obige Block aktiviert Tailwind und deaktiviert die SCSS-Kompilierung, selbst wenn eine `.scss`-Quelle vorhanden ist.

### Wenn keine Erweiterung benötigt wird {#when-no-extension-is-needed}

Eine Erweiterung existiert, um einen Compiler bereitzustellen. Ein Framework, das zur Laufzeit rendert, anstatt in etwas Neues zu kompilieren, benötigt keinen Compiler und somit keine Erweiterung. Bun verarbeitet bereits das TypeScript und JSX, in dem ein solches Framework geschrieben ist.

[React](https://react.dev/) ist eine JavaScript-Bibliothek zum Erstellen von Benutzeroberflächen und das klarste Beispiel. Sein Eingang ist einfaches TypeScript, das Bun ohne Plugin kompiliert. Du umschließt die Komponente als benutzerdefiniertes Element mit einer Bibliothek deiner Wahl, wie `@r2wc/react-to-web-component`, deklarierst die React-Pakete mit `@BundlePackage`, und die Ansicht konsumiert das resultierende Element. Es gibt keine React-Erweiterung zu aktivieren, weil es keinen React-Compiler gibt, den man beitragen kann, nur Pakete, die installiert werden müssen.

## Konfiguration einer Erweiterung {#configuring-an-extension}

Einige Erweiterungen benötigen Optionen. Der SCSS-Compiler benötigt beispielsweise einen Ladepfad. Optionen befinden sich in `bun.config.ts`, einer Datei, die du im Stammverzeichnis deiner Frontend-Quellen root, `src/main/frontend/bun.config.ts`, neben den Einträgen, die du verfasst, erstellst. Sie kommen in ein `options`-Objekt, das nach der ID der Erweiterung geordnet ist, und der Build übergibt jeder Erweiterung das unter ihrer eigenen ID gespeicherte Objekt:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

:::tip Woher die Optionen kommen
Eine Erweiterung leitet ihre Optionen direkt an das Tool weiter, das sie umschließt, sodass die von einer Erweiterung akzeptierten Optionen die Optionen dieses Tools sind. Jede Erweiterungsseite unten nennt das Tool, an das sie weiterleitet, und verlinkt auf das Referenzdokument dieses Tools, wo du die vollständige Liste findest.
:::

Die gleiche Datei kann zusätzliche Bun-Plugins über einen Standardexport anhängen, für einen Build-Schritt, den keine kuratierte Erweiterung abdeckt:

```ts title="src/main/frontend/bun.config.ts"
import myPlugin from './my-plugin';

export default [myPlugin()];
```

## Die kuratierten Erweiterungen {#the-curated-extensions}

webforJ stellt eine Erweiterung für SCSS, Less und Tailwind bereit. Jede folgt dem obigen Modell: Sie aktiviert sich, wenn ihr Quellentyp vorhanden ist, erklärt die Pakete, die ihr Compiler benötigt, als Build-Abhängigkeiten und trägt den Compiler bei.

| Erweiterung | ID | Aktiviert bei | Installiert (Build-Abhängigkeit) |
|-------------|----|---------------|-----------------------------------|
| [SCSS und Sass](/docs/managing-resources/bundler/extensions/scss) | `webforj-scss` | eine `.scss` oder `.sass`-Quelle | `sass` |
| [Less](/docs/managing-resources/bundler/extensions/less) | `webforj-less` | eine `.less`-Quelle | `less` |
| [Tailwind](/docs/managing-resources/bundler/extensions/tailwind) | `webforj-tailwind` | standardmäßig deaktiviert, aktiviert durch ID | `tailwindcss`, `bun-plugin-tailwind` |

Um eine andere Art von Quelle zu kompilieren, schreibst du deine eigene Erweiterung nach demselben Vertrag. Siehe [Schreiben deiner eigenen Erweiterung](/docs/managing-resources/bundler/extensions/writing-your-own), die eine End-to-End-Lösung erstellt.
