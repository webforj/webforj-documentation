---
title: Styling languages
sidebar_position: 11
sidebar_class_name: new-content
description: >-
  Author your styles in CSS, compile them from Sass or Less, or generate them
  with Tailwind, and load the result into a webforJ app.
_i18n_hash: 98eca77023e33bac367a1a250da900d7
---
Ihre Stile erreichen die Seite als CSS, aber Sie müssen sie nicht als CSS schreiben. webforJ lädt ein Stylesheet, das Sie erstellen, kompiliert eines aus einem Präprozessor wie Sass oder Less oder generiert eines aus Tailwind, und das Ergebnis stylt Ihre Ansichten auf die gleiche Weise, egal woher es stammt. Die DWC-Tokens, [CSS-Custom-Properties](/docs/styling/css-variables) und [Shadow Parts](/docs/styling/shadow-parts), die im Rest dieses Abschnitts behandelt werden, gelten in allen diesen.

## Plain CSS {#plain-css}

Ein Stylesheet, das Sie schreiben, benötigt keinen Build. Hängen Sie es an eine Komponente oder die App mit [`@StyleSheet`](/docs/managing-resources/importing-assets#importing-css-files). Wenn Sie bereits den [Frontend-Bundler](/docs/managing-resources/bundler/overview) ausführen, können Sie stattdessen eine `.css`-Datei an eine Klasse mit `@BundleEntry` binden, was sie als Stile für diese Ansicht lädt.

## Sass und Less {#sass-and-less}

Um Ihre Stile in [Sass](https://sass-lang.com/) oder [Less](https://lesscss.org/) mit Variablen, Verschachtelung und Funktionen zu schreiben, erstellen Sie die Quelle und lassen den [Frontend-Bundler](/docs/managing-resources/bundler/overview) sie in CSS kompilieren. Der Compiler ist eine [Erweiterung](/docs/managing-resources/bundler/extensions/overview), die sich aktiviert, wenn eine Quelle seines Typs vorhanden ist. Das Erstellen einer `.scss`, `.sass` oder `.less`-Datei ist das einzige Signal, das er benötigt. Binden Sie die Quelle auf die gleiche Weise an eine Klasse, wie Sie ein Stylesheet binden:

```java title="StyledView.java"
@Route("/styled")
@BundleEntry("styles/view.scss")
public class StyledView extends Composite<FlexLayout> {
  // bauen Sie die Ansicht
}
```

Die Erweiterung kompiliert `view.scss` zu CSS und lädt es für die Ansicht. Siehe [SCSS und Sass](/docs/managing-resources/bundler/extensions/scss) und [Less](/docs/managing-resources/bundler/extensions/less) für die Dateistruktur, Ladepfade und Optionen, die jeweils akzeptiert werden.

## Tailwind {#tailwind}

[Tailwind](https://tailwindcss.com/) generiert ein Stylesheet aus den Utility- Klassennamen, die Ihre Ansichten verwenden, anstatt aus einer von Ihnen erstellten Datei. Aktivieren Sie die Erweiterung, und fügen Sie dann Utilities als Klassennamen hinzu, ohne etwas zu importieren. webforJ lässt die Basiseinstellung von Tailwind weg, damit sie nicht mit dem Styling Ihrer Komponenten in Konflikt gerät, und eine Utility erreicht das Element, auf das Sie es anwenden, nicht das Innere einer Komponente. Siehe [Tailwind-Erweiterung](/docs/managing-resources/bundler/extensions/tailwind) dafür, wie es sein Stylesheet generiert und einschränkt und wo Utility-Klassen angewendet werden und wo nicht.

## Eine andere Sprache {#another-language}

Der Compiler für jede Sprache ist eine Bundler-Erweiterung, und das Modell ist offen. Um Ihre Stile in einer Sprache zu erstellen, für die webforJ keinen Compiler bereitstellt, schreiben Sie eine kleine Erweiterung, die diesen Compiler bereitstellt, nach dem gleichen Vertrag, den Sass und Less verwenden. Siehe [Schreiben Ihrer eigenen Erweiterung](/docs/managing-resources/bundler/extensions/writing-your-own).
