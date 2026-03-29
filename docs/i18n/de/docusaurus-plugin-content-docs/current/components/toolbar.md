---
title: Toolbar
sidebar_position: 145
_i18n_hash: e3329a54fd8feccd96e15e1b19c3c97d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieten Benutzern schnellen Zugriff auf grundlegende Aktionen und Navigationselemente. Die webforJ `Toolbar`-Komponente ist ein horizontaler Container, der eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Komponenten aufnehmen kann. Sie eignet sich gut für die Verwaltung von Seitensteuerelementen und das Unterbringen von wichtigen Funktionen wie einer Suchleiste oder einer Benachrichtigungsschaltfläche.

<!-- INTRO_END -->

## Organisieren von Toolbar-Inhalten {#organizing-toolbar-content}

Die `Toolbar` organisiert wesentliche Komponenten in einem leicht zugänglichen und konsistenten Layout. Standardmäßig nimmt sie die volle Breite des übergeordneten Elements ein und bietet vier Platzierungsbereiche oder _Slots_, um Komponenten zu organisieren:

- **Start**: Enthält normalerweise einen <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> oder einen Home-Button.
- **Titel**: Wird für App-Namen oder Logos verwendet.
- **Inhalt**: Für hochpriorisierte Aktionen wie Suche oder Navigation.
- **Ende**: Weniger häufige Aktionen, wie Benutzerprofil oder Hilfe.

Jeder Slot hat eine Methode zum Hinzufügen von Komponenten: `addToStart()`, `addToTitle()`, `addToContent()` und `addToEnd()`.

Das folgende Demo zeigt, wie man eine `Toolbar` zu einem [AppLayout](./app-layout) hinzufügt und alle unterstützten Slots effektiv nutzt. Um mehr über die Implementierung von Toolbars innerhalb eines `AppLayout` zu erfahren, siehe [Sticky Toolbars](./app-layout#sticky-toolbars) und [Mobile Navigation Layout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Kompakter Modus {#compact-mode}

Verwende `setCompact(true)`, um den Abstand um eine `Toolbar` zu reduzieren. Dies ist hilfreich, wenn du mehr Inhalt auf dem Bildschirm unterbringen musst, insbesondere in Apps mit gestapelten Toolbars oder begrenztem Platz. Die Toolbar verhält sich weiterhin gleich – nur die Höhe wird reduziert. Dieser Modus wird häufig in Kopfzeilen, Seitenleisten oder Layouts verwendet, in denen der Platz knapp ist.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` in Toolbars {#progressbar-in-toolbars}

Eine `ProgressBar` dient als visuelles Indiz für laufende Prozesse, wie das Laden von Daten, das Hochladen von Dateien oder das Abschließen von Schritten in einem Ablauf. Wenn sie innerhalb einer `Toolbar` platziert wird, fügt sich die `ProgressBar` sauber an der unteren Kante ein, sodass sie unauffällig bleibt, während sie den Benutzern den Fortschritt klar kommuniziert.

Du kannst sie mit anderen Komponenten in der Toolbar wie Schaltflächen oder Labels kombinieren, ohne das Layout zu stören.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Styling {#styling}

### Themen {#themes}

`Toolbar`-Komponenten enthalten <JavadocLink type="foundation" location="com/webforj/component/Theme">sieben integrierte Themen</JavadocLink> für eine schnelle visuelle Anpassung:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
