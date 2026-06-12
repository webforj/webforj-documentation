---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 99def78151a30c5c7fef7106b2efcb5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieten Benutzern schnellen Zugang zu grundlegenden Aktionen und Navigationselementen. Die webforJ `Toolbar` Komponente ist ein horizontaler Container, der eine Reihe von Aktionsschaltflächen, Icons oder anderen Komponenten halten kann. Sie eignet sich gut zur Verwaltung von Seitensteuerungen und zur Unterbringung wichtiger Funktionen wie einer Suchleiste oder einer Benachrichtigungsschaltfläche.

<!-- INTRO_END -->

## Inhalt der Werkzeugleiste organisieren {#organizing-toolbar-content}

Die `Toolbar` organisiert wesentliche Komponenten in einem leicht zugänglichen und konsistenten Layout. Standardmäßig nimmt sie die volle Breite ihres übergeordneten Elements ein und bietet vier Platzierungsbereiche, oder _Slots_, zur Organisation von Komponenten:

- **Start**: Enthält normalerweise einen <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> oder eine Home-Schaltfläche.
- **Titel**: Wird für App-Namen oder Logos verwendet.
- **Inhalt**: Für hochgradig beachtete Aktionen wie Suche oder Navigation.
- **Ende**: Weniger häufige Aktionen, wie Benutzerprofil oder Hilfe.

Jeder Slot hat eine Methode zum Hinzufügen von Komponenten: `addToStart()`, `addToTitle()`, `addToContent()` und `addToEnd()`.

Die folgende Demo zeigt, wie man eine `Toolbar` zu einem [AppLayout](./app-layout) hinzufügt und alle unterstützten Slots effektiv nutzt.
Um mehr über die Implementierung von Werkzeugleisten innerhalb eines `AppLayout` zu erfahren, siehe [Sticky toolbars](./app-layout#sticky-toolbars) und [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/resources/static/css/toolbar/toolbar-slots-view.css',
]}
/>

## Kompaktemodus {#compact-mode}

Verwenden Sie `setCompact(true)`, um den Abstand um eine `Toolbar` zu verringern. Dies ist hilfreich, wenn Sie mehr Inhalt auf dem Bildschirm unterbringen müssen, insbesondere in Apps mit gestapelten Werkzeugleisten oder begrenztem Platz. Die Werkzeugleiste verhält sich weiterhin gleich—nur die Höhe wird verringert. Dieser Modus wird häufig in Kopfzeilen, Seitenleisten oder Layouts verwendet, in denen der Platz begrenzt ist.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` in Werkzeugleisten {#progressbar-in-toolbars}

Eine `ProgressBar` dient als visuelle Anzeige für laufende Prozesse, wie das Laden von Daten, das Hochladen von Dateien oder das Abschließen von Schritten in einem Ablauf. Wenn sie in eine `Toolbar` eingebaut ist, wird die `ProgressBar` ordentlich am unteren Rand ausgerichtet, wodurch sie unauffällig bleibt, während sie den Benutzern weiterhin klaren Fortschritt kommuniziert.

Sie können sie mit anderen Komponenten in der Werkzeugleiste wie Schaltflächen oder Beschriftungen kombinieren, ohne das Layout zu stören.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Styling {#styling}

### Themes {#themes}

`Toolbar` Komponenten umfassen <JavadocLink type="foundation" location="com/webforj/component/Theme">sieben integrierte Themes</JavadocLink> für eine schnelle visuelle Anpassung:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
