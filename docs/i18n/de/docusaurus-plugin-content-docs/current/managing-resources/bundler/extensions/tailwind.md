---
title: Tailwind
sidebar_position: 60
sidebar_class_name: new-content
description: >-
  Turn on the webforj-tailwind extension, apply utility classes from a view, and
  understand how it generates and scans its own stylesheet.
_i18n_hash: f588624ebd738977bb8be4e9887141d1
---
[Tailwind CSS](https://tailwindcss.com/) ist ein Utility-First-CSS-Framework, dessen Klassennamen jeweils einer kleinen Menge von CSS-Deklarationen zugeordnet sind. Es ist die einzige kuratierte Erweiterung, die mitgeliefert wird, da die meisten Projekte es nicht verwenden. Sie aktivieren es nach ID, genau wie Sie jede andere Erweiterung aktivieren, siehe [Aktiviert nach ID](/docs/managing-resources/bundler/extensions/overview#enabled-by-id). Wenn es aktiviert ist, tut es etwas, was die anderen nicht tun: Es generiert seinen eigenen Eintrag.

## Wie es funktioniert {#how-it-works}

Anstatt eine Datei zu kompilieren, die Sie geschrieben haben, scannt die Tailwind-Erweiterung die Quellcodes Ihrer App nach den verwendeten Utility-Klassennamen, generiert ein Stylesheet, das nur diese Utilities enthält, und lädt es für jede Ansicht. Eine Ansicht wendet dann Utilities als Klassennamen an, ohne dass etwas importiert werden muss:

```java title="TailwindView.java"
@Route("/tailwind")
public class TailwindView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TailwindView() {
    Div card = new Div("Gestylt durch die kompilierten Tailwind-Utilities");
    card.addClassName("flex", "items-center", "gap-4", "p-8", "rounded-lg",
        "bg-blue-500", "text-white");
    self.add(card);
  }
}
```

Das generierte Stylesheet importiert das Thema und die Utilities von Tailwind, jedoch nicht den Basis-Reset. Der Reset, das Preflight von Tailwind, gestaltet jedes nackte Element auf der Seite neu, wodurch das Styling, das webforJ bereits auf seine Komponenten anwendet, überschrieben werden würde. Es wegzulassen, sorgt dafür, dass die Utility-Klassen, die Sie hinzufügen, funktionieren, ohne die Komponenten, die Sie nicht geändert haben, zu stören.

Da die Utilities von den Klassennamen stammen, die Ihre Ansichten verwenden, folgt die [Frontend-Überwachung](/docs/configuration/deploy-reload/frontend-watch) Ihren App-Quellen sowie `src/main/frontend`. Fügen Sie eine Utility-Klasse in einer Ansicht hinzu oder entfernen Sie sie und speichern Sie, und das Stylesheet wird regeneriert und direkt in die Seite eingefügt, genau wie das Bearbeiten eines Stylesheets, das Sie geschrieben haben.

## Wo Utility-Klassen wirken {#where-utility-classes-reach}

:::warning Eine Utility-Klasse gestaltet das Element, nicht das Innere einer Komponente
webforJ-Komponenten werden mit einem Shadow-DOM gerendert, der ihre interne Struktur privat hält. Eine Utility-Klasse, die zu einer Komponente hinzugefügt wird, gestaltet nur deren äußere Box, ihren Abstand, ihre Größe und ihren Platz in einem Layout und erreicht niemals die Elemente, die darin gezeichnet sind. Utilities werden so angewendet, wie ihre Klassennamen auf einem Layout-Container oder einem einfachen `Div` dargestellt werden, den Sie erstellen, wo es keine Grenze zu überschreiten gibt, jedoch nicht auf das Innere einer aufgebauten Komponente.

Um das Innere einer Komponente zu stylen, verwenden Sie die Stile, die die Komponente stattdessen bereitstellt: [shadow parts](/docs/styling/shadow-parts) über `::part()` und die [CSS-Custom-Properties](/docs/styling/css-variables) der Komponente, die alles in den Styling-Referenzen jeder Komponente aufgelistet sind. Verwenden Sie Utilities für Layout und für Ihre eigenen Elemente, und nutzen Sie das eigene Styling einer Komponente, um die Komponente neu abzustimmen.
:::

Das Stylesheet enthält die Utility-Klassennamen, die beim Scan in Ihren Quellen gefunden wurden, und nur diese. Eine Klasse, die Sie im Browser-Inspektor eingeben, um eine Idee auszuprobieren, wird nicht angewendet, da sie nie kompiliert wurde. Geben Sie die Klasse in einer Ansicht an und speichern Sie, und die Überwachung regeneriert das Stylesheet damit.

Wenn dieselbe Gruppe von Utilities in vielen Ansichten wiederholt wird, benennen Sie sie: Definieren Sie eine CSS-Klasse einmal und fügen Sie diese stattdessen hinzu. Einige Utilities inline bleiben lesbar, eine lange Reihe, die von Hand wiederholt wird, driftet während Sie bearbeiten.

## Optionen {#options}

Die Tailwind-Erweiterung nimmt keine Optionen aus `bun.config.ts` entgegen. Es generiert und scannt sein eigenes Stylesheet, und Tailwind selbst wird über dieses Stylesheet konfiguriert, nicht über die Erweiterung.
