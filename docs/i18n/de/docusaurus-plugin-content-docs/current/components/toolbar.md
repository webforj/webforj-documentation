---
title: Toolbar
sidebar_position: 145
_i18n_hash: 54bfdf481b7e149762dc4544c192c6e6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieten den Benutzern schnellen Zugriff auf grundlegende Aktionen und Navigationselemente. Die webforJ `Toolbar`-Komponente ist ein horizontaler Container, der eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Komponenten aufnehmen kann. Sie eignet sich gut zur Verwaltung von Seitenkontrollen und zur Unterbringung wichtiger Funktionen wie einer Suchleiste oder einer Benachrichtigungsschaltfläche.

<!-- INTRO_END -->

## Anordnung des Toolbar-Inhalts {#organizing-toolbar-content}

Die `Toolbar` organisiert wesentliche Komponenten in einem leicht zugänglichen und konsistenten Layout. Standardmäßig nimmt sie die volle Breite ihres übergeordneten Elements ein und bietet vier Platzierungsbereiche oder _Slots_ zur Anordnung von Komponenten:

- **Start**: Enthält normalerweise einen <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> oder eine Starttaste.
- **Titel**: Wird für App-Namen oder Logos verwendet.
- **Inhalt**: Für Aktionen mit hoher Aufmerksamkeit wie Suche oder Navigation.
- **Ende**: Weniger häufige Aktionen wie Benutzerprofil oder Hilfe.

Jeder Slot hat eine Methode zum Hinzufügen von Komponenten: `addToStart()`, `addToTitle()`, `addToContent()` und `addToEnd()`.

Die folgende Demo zeigt, wie man eine `Toolbar` zu einem [AppLayout](./app-layout) hinzufügt und alle unterstützten Slots effektiv nutzt.
Um mehr über die Implementierung von Toolbars innerhalb eines `AppLayout` zu erfahren, siehe [Sticky toolbars](./app-layout#sticky-toolbars) und [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/resources/static/css/toolbar/toolbar-slots-view.css',
]}
/>

## Kompakter Modus {#compact-mode}

Verwenden Sie `setCompact(true)`, um den Abstand um eine `Toolbar` zu verringern. Dies ist hilfreich, wenn Sie mehr Inhalt auf dem Bildschirm unterbringen müssen, insbesondere in Apps mit gestapelten Toolbars oder begrenztem Platz. Die Toolbar verhält sich dabei immer noch gleich – nur die Höhe wird verringert. Dieser Modus wird häufig in Kopfzeilen, Seitenleisten oder Layouts verwendet, in denen der Platz knapp ist.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` in Toolbars {#progressbar-in-toolbars}

Eine `ProgressBar` dient als visueller Indikator für laufende Prozesse, wie das Laden von Daten, das Hochladen von Dateien oder das Abschließen von Schritten in einem Prozess. Wenn sie in einer `Toolbar` platziert wird, reiht sich die `ProgressBar` ordentlich am unteren Rand an und ist unauffällig, während sie den Benutzern dennoch den Fortschritt klar kommuniziert.

Sie können sie mit anderen Komponenten in der Toolbar wie Schaltflächen oder Labels kombinieren, ohne das Layout zu stören.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Styling {#styling}

### Themen {#themes}

`Toolbar`-Komponenten umfassen <JavadocLink type="foundation" location="com/webforj/component/Theme">sieben integrierte Themen</JavadocLink> für eine schnelle visuelle Anpassung:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='475px'
/>

<TableBuilder name="Toolbar" />
