---
title: Alert
sidebar_position: 5
description: >-
  Display contextual feedback messages with the Alert component, including
  themes, expanses, dismissible close events, and rich content.
_i18n_hash: ad90f6abef16b17547ddcb2a612f4050
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Die `Alert`-Komponente in webforJ bietet kontextbezogene Rückmeldungsnachrichten für Benutzer. Es ist eine vielseitige Möglichkeit, wichtige Informationen, Warnungen oder Benachrichtigungen in Ihrer App anzuzeigen.

Alerts helfen dabei, die Aufmerksamkeit auf wichtige Informationen zu lenken, ohne den Arbeitsablauf des Benutzers zu stören. Sie sind perfekt für Systemmeldungen, Rückmeldungen zur Formularvalidierung oder Statusaktualisierungen, die deutlich sichtbar, aber nicht aufdringlich sein müssen.

<!-- INTRO_END -->

## Erstellen von Alerts {#creating-alerts}

Ein `Alert` kann reichhaltige Inhalte wie Text, Schaltflächen und andere Komponenten enthalten. Setzen Sie ein Thema, um den Typ der angezeigten Nachricht visuell zu unterscheiden.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Schließen von Alerts {#dismissing-alerts}

Wenn Sie den Benutzern die Möglichkeit geben möchten, das `Alert` zu schließen, können Sie es schließbar machen, indem Sie die Methode `setClosable()` aufrufen.

```java
Alert alert = new Alert("Achtung! Dieses Alert kann geschlossen werden.");
closableAlert.setClosable(true);
```
Alerts tun oft mehr, als nur Nachrichten anzuzeigen – sie können Nachfolgeaktionen auslösen, wenn sie geschlossen werden. Verwenden Sie das `AlertCloseEvent`, um diese Fälle zu behandeln und zu reagieren, wenn der Benutzer das `Alert` schließt.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Wiederverwendbare Alert-Komponente
Das Schließen des Alerts blendet ihn nur aus – es zerstört die Komponente nicht, sodass Sie sie später bei Bedarf wiederverwenden können.
:::


## Styling {#styling}

### Themen {#themes}

Die `Alert`-Komponente unterstützt mehrere <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, um visuell unterschiedliche Arten von Nachrichten zu unterscheiden – wie Erfolg, Fehler, Warnung oder Info. Diese Themen können mit der Methode `setTheme()` oder direkt im Konstruktor angewendet werden.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>

### Ausdehnungen {#expanses}

Die Ausdehnung definiert die visuelle Größe der `Alert`-Komponente. Sie können sie mit der Methode `setExpanse()` festlegen oder direkt an den Konstruktor übergeben. Die verfügbaren Optionen stammen aus dem Expanse-Enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` und `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
