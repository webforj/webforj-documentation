---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 166b39dabe94c73ecf6310dfdc1ba626
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieten Benutzern schnellen Zugriff auf zentrale Aktionen und Navigationselemente. Die webforJ `Toolbar`-Komponente ist ein horizontaler Container, der eine Reihe von Aktionsschaltflächen, Icons oder anderen Komponenten aufnehmen kann. Sie eignet sich gut zur Verwaltung von Seitensteuerelementen und zur Unterbringung von wichtigen Funktionen wie einer Suchleiste oder einem Benachrichtigungsknopf.

<!-- INTRO_END -->

## Organisieren des Toolbar-Inhalts {#organizing-toolbar-content}

Die `Toolbar` organisiert wesentliche Komponenten in einem leicht zugänglichen und konsistenten Layout. Standardmäßig nimmt sie die volle Breite ihres übergeordneten Elements ein und bietet vier Platzierungsbereiche oder _Slots_ zum Organisieren der Komponenten:

- **Start**: Enthält normalerweise einen <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> oder einen Home-Button.
- **Titel**: Wird für App-Namen oder Logos verwendet.
- **Inhalt**: Für hochpriorisierte Aktionen wie Suche oder Navigation.
- **Ende**: Weniger häufige Aktionen, wie Benutzerprofil oder Hilfe.

Jeder Slot hat eine Methode zum Hinzufügen von Komponenten: `addToStart()`, `addToTitle()`, `addToContent()` und `addToEnd()`.

Die folgende Demo zeigt, wie man eine `Toolbar` zu einem [AppLayout](./app-layout) hinzufügt und alle unterstützten Slots effektiv nutzt. Um mehr über die Implementierung von Toolbars innerhalb eines `AppLayout` zu erfahren, siehe [Sticky toolbars](./app-layout#sticky-toolbars) und [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java']}
/>

## Kompakter Modus {#compact-mode}

Verwenden Sie `setCompact(true)`, um die Polsterung um eine `Toolbar` zu reduzieren. Dies ist hilfreich, wenn Sie mehr Inhalte auf dem Bildschirm unterbringen müssen, insbesondere in Apps mit gestapelten Toolbars oder begrenztem Platz. Die Toolbar verhält sich weiterhin gleich – nur die Höhe wird reduziert. Dieser Modus wird häufig in Kopfzeilen, Seitenleisten oder Layouts verwendet, in denen der Platz knapp ist.

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

Eine `ProgressBar` dient als visuelle Anzeige für laufende Prozesse, wie das Laden von Daten, das Hochladen von Dateien oder das Durchführen von Schritten in einem Workflow. Wenn sie innerhalb einer `Toolbar` platziert wird, fügt sich die `ProgressBar` ordentlich an der unteren Kante ein und ist unauffällig, während sie den Benutzern dennoch klaren Fortschritt kommuniziert.

Sie können sie mit anderen Komponenten in der Toolbar wie Schaltflächen oder Labels kombinieren, ohne das Layout zu stören.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Styling {#styling}

### Themen {#themes}

`Toolbar`-Komponenten enthalten <JavadocLink type="foundation" location="com/webforj/component/Theme">sieben integrierte Themen</JavadocLink> für eine schnelle visuelle Anpassung:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
