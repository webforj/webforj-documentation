---
title: Toolbar
sidebar_position: 145
_i18n_hash: 446d71b3e376810254bbbf6ffee43aa9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars bieten Benutzern schnellen Zugriff auf zentrale Aktionen und Navigationselemente. Die webforJ `Toolbar`-Komponente ist ein horizontaler Container, der eine Gruppe von Aktionsschaltflächen, Symbolen oder anderen Komponenten aufnehmen kann. Sie eignet sich gut zur Verwaltung von Seitensteuerelementen und zur Unterbringung wichtiger Funktionen wie einer Suchleiste oder einer Benachrichtigungsschaltfläche.

## Organisieren von Toolbar-Inhalten {#organizing-toolbar-content}

Die `Toolbar` organisiert wesentliche Komponenten in einem leicht zugänglichen und konsistenten Layout. Standardmäßig nimmt sie die volle Breite ihres übergeordneten Elements ein und bietet vier Platzierungsbereiche oder _Slots_ zum Organisieren von Komponenten:

- **Start**: Enthält normalerweise einen <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> oder einen Home-Button.
- **Titel**: Wird für App-Namen oder Logos verwendet.
- **Inhalt**: Für hochpriorisierte Aktionen wie Suche oder Navigation.
- **Ende**: Weniger häufige Aktionen, wie Benutzerprofil oder Hilfe.

Jeder Slot hat eine Methode zum Hinzufügen von Komponenten: `addToStart()`, `addToTitle()`, `addToContent()` und `addToEnd()`.

Die folgende Demo zeigt, wie man eine `Toolbar` zu einem [AppLayout](./app-layout) hinzufügt und alle unterstützten Slots effektiv nutzt. Um mehr über die Implementierung von Toolbars innerhalb eines `AppLayout` zu erfahren, siehe [Sticky toolbars](./app-layout#sticky-toolbars) und [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Kompakter Modus {#compact-mode}

Verwenden Sie `setCompact(true)`, um den Abstand um eine `Toolbar` zu reduzieren. Dies ist hilfreich, wenn Sie mehr Inhalt auf dem Bildschirm unterbringen müssen, insbesondere in Apps mit gestapelten Toolbars oder begrenztem Platz. Die Toolbar verhält sich gleich – lediglich die Höhe wird reduziert. Dieser Modus wird häufig in Kopfzeilen, Seitenleisten oder Layouts verwendet, in denen der Platz knapp ist.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` in Toolbars {#progressbar-in-toolbars}

Eine `ProgressBar` dient als visuelle Anzeige für laufende Prozesse, wie das Laden von Daten, das Hochladen von Dateien oder das Abschließen von Schritten in einem Workflow. Wenn sie in eine `Toolbar` eingefügt wird, reiht sich die `ProgressBar` ordentlich an der unteren Kante aus, wodurch sie unauffällig bleibt, während sie den Benutzern dennoch den Fortschritt klar kommuniziert.

Sie können sie mit anderen Komponenten in der Toolbar wie Schaltflächen oder Beschriftungen kombinieren, ohne das Layout zu stören.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Stil {#styling}

### Themen {#themes}

`Toolbar`-Komponenten umfassen <JavadocLink type="foundation" location="com/webforj/component/Theme">sieben integrierte Themen</JavadocLink> zur schnellen visuellen Anpassung:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
