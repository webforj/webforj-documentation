---
title: Packages and assets
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Declare npm packages, load a module straight from one, install build-only
  dependencies, rely on tree shaking, and import CSS and assets from a
  component's entry.
_i18n_hash: 3b538e3785ee74397f156dd38ef8506a
---
Ein Eintrag greift auf mehr als seine eigene Quelle zu. Er importiert Pakete von npm, lädt ein Modul direkt von einem, bindet ein Stylesheet oder ein Bild ein und kann aus einer Klasse stammen, die du erweiterst oder von einer Bibliothek, auf die du angewiesen bist. Diese Seite behandelt, wie diese Komponenten in den Build gelangen.

## Deklarieren eines Pakets {#declaring-a-package}

`@BundlePackage` deklariert eine npm-Abhängigkeit, die ein Eintrag importiert. Der Build sammelt jede Deklaration im Klassenpfad und fügt sie der [`package.json`](https://docs.npmjs.com/cli/v11/configuring-npm/package-json) deines Projekts hinzu, bevor [Bun](https://bun.sh/) sie installiert, sodass ein Paket vorhanden ist, bevor sein Eintrag kompiliert wird. Deine eigenen Änderungen an dieser Datei werden beibehalten, und ein Projekt, das keine Pakete deklariert und keine eigene `package.json` hat, behält keine, sodass npm aus einem Build fernbleibt, der es nicht benötigt.

Eine Web-Komponentenbibliothek ist der häufige Fall. Deklariere das Paket und weise dann `@BundleEntry` auf die Komponentenmodule hin, die du möchtest:

```java title="Ui5View.java"
@Route("/ui5")
@BundlePackage(value = "@ui5/webcomponents", version = "^2.0.0")
@BundleEntry("@ui5/webcomponents/dist/Button.js")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
public class Ui5View extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public Ui5View() {
    self.setAlignment(FlexAlignment.CENTER)
      .setStyle("margin", "1em");

    Element input = new Element("ui5-input");
    input.setProperty("placeholder", "Tippe etwas ein");

    Element button = new Element("ui5-button");
    button.setProperty("design", "Emphasized");
    button.setText("Hallo sagen");
    button.addEventListener("click", e ->
      Toast.show("Hallo " + input.getProperty("value"))
    );

    self.add(input, button);
  }
}
```

Die `version` folgt der npm Versionierungssyntax, sodass `^2.0.0` kompatible 2.x Versionen akzeptiert. Sowohl `@BundlePackage` als auch `@BundleEntry` sind wiederholbar, sodass eine Klasse so viele Pakete deklarieren und so viele Module laden kann, wie sie benötigt.

### Eine Datei oder ein npm Modul {#a-file-entry-or-an-npm-module}

Der Wert von `@BundleEntry` ist eines von zwei Dingen: ein Pfad zu einer Datei, die du unter `src/main/frontend` verfasst hast, oder ein Pfad zu einem Modul innerhalb eines npm-Pakets. Die obige Ansicht benennt Modulpfade innerhalb von `@ui5/webcomponents`, sodass sie keine eigene Quelldatei mitführt. Jedes dieser Module registriert sein eigenes benutzerdefiniertes Element, wenn es geladen wird, weshalb die Ansicht `ui5-input` und `ui5-button` ohne Wrapper konsumiert. Ein Datei-Eintrag hingegen verweist auf eine `.ts`, `.js` oder `.css` Datei, die du geschrieben hast und auf die gleiche Weise kompiliert wird.

### Build-Abhängigkeiten {#build-dependencies}

Ein Paket, das nur benötigt wird, um die Quellen zu kompilieren und nicht zur Laufzeit, ist eine Build-Abhängigkeit. Setze `dev = true` auf `@BundlePackage`, und der Build installiert es als `devDependency`:

```java
@BundlePackage(value = "typescript", version = "^5.0.0", dev = true)
```

Die kuratierten Erweiterungen verwenden dies für die Pakete, die ihre Compiler benötigen, weshalb ein SCSS-Quellcode `sass` als Build-Abhängigkeit einbindet und nichts zur Laufzeit.

## Nur das, was du importierst {#tree-shaking}

Der Compiler schließt nur den Code ein, den ein Eintrag tatsächlich importiert. Die Benennung von `@ui5/webcomponents/dist/Button.js` bindet die Schaltflächenkomponente und das, was sie benötigt, nicht den Rest der Bibliothek ein. Eine breite Bibliothek kostet nur die Teile, die du benötigst, sodass es keine Strafe für die Deklaration eines großen Pakets und das Laden eines Moduls daraus gibt.

### Gemeinsamer Code {#shared-code}

Wenn zwei Einträge dasselbe Paket importieren, berücksichtigt der Build den gemeinsamen Code in einem Chunk, den beide laden, anstatt ihn in jeden zu kopieren. Mehrere Komponenten, die auf derselben Bibliothek basieren, ein Set von Lit-Elementen zum Beispiel, teilen den Code dieser Bibliothek auf einer Seite, anstatt ihn einmal pro Element zu bezahlen.

## Wie Einträge geladen werden {#how-entries-load}

Ein Eintrag produziert ein Skript, ein Stylesheet oder beides, und zur Laufzeit lädt das System diese Ausgabe, das erste Mal, wenn ein Element seiner Klasse erstellt wird, wo immer dieses Element verwendet wird und wie tief es auch geschachtelt ist. Eine geroutete Ansicht und ein Layout sind Komponenten wie jede andere, daher bindet ein Eintrag an die Elementerstellung, nicht an das Routing. Zwei Details folgen aus der Annotation, die auf der Klasse lebt:

- **Vererbung.** `@BundleEntry` und `@BundlePackage` werden vererbt. Eine Basisklasse deklariert den Eintrag, und eine Unterklasse, die nichts Eigenes hinzufügt, lädt ihn trotzdem.
- **Debug-Einträge.** Ein als `@BundleEntry(value = "...", debug = true)` deklariertes Element wird nur geladen, wenn die App im Debug-Modus ausgeführt wird, was sich für ausschließlich diagnostische Entwicklungszwecke eignet.

## Importieren von CSS und Assets {#importing-css-and-assets}

Der Eintrag einer Komponente behandelt Stylesheets und andere Assets durch Importe, ohne Annotation und ohne Erweiterung. Bun löst sie zur Kompilierzeit auf.

Importiere ein Stylesheet wegen seiner Nebeneffekte, und der Bundler schließt es in die Stile des Eintrags ein. Importiere ein nicht kodiertes Asset, und der Import gibt dir eine URL zum Verwenden:

```ts title="card/card.ts"
import './card.css';
import logoPath from './logo.svg';

const logo = new URL(logoPath, import.meta.url).href;
// benutze logo als Bildquelle innerhalb des Elements
```

Löse eine Asset-URL gegen `import.meta.url` auf, nicht gegen das Dokument, sodass sie auf das kompilierte Asset zeigt, wo immer die Ausgabe bereitgestellt wird.

Importiere ein Stylesheet stattdessen als Text und wende es innerhalb einer Shadow-Root an, um die Stile auf ein Element zu beschränken:

```ts title="swatch/swatch.ts"
import sheet from './swatch.css' with { type: 'text' };

class ColorSwatch extends HTMLElement {
  connectedCallback() {
    const root = this.attachShadow({ mode: 'open' });
    const style = document.createElement('style');
    style.textContent = sheet;
    root.append(style);
  }
}
```

Ein Eintrag kann auch eine einfache `.css` Datei ohne Skript sein, die auf eine Klasse gebunden ist, ebenso wie ein Skripteintrag. Zur Laufzeit wird sie als Stile für die Ansicht geladen:

```java title="ThemeView.java"
@Route("/theme")
@BundleEntry("theme/theme.css")
public class ThemeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ThemeView() {
    self.add(new Div("Gestylt durch einen CSS-only Bundleeintrag")
                 .addClassName("themed-label"));
  }
}
```
