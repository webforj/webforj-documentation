---
title: Alert
sidebar_position: 5
_i18n_hash: 32072a9b5fdae80b41d77ee1d9742ea5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Die `Alert`-Komponente in webforJ bietet kontextbezogene Feedback-Nachrichten für Benutzer. Sie ist eine vielseitige Möglichkeit, wichtige Informationen, Warnungen oder Benachrichtigungen in Ihrer App anzuzeigen.

Alerts helfen, die Aufmerksamkeit auf wichtige Informationen zu lenken, ohne den Workflow des Benutzers zu stören. Sie sind perfekt für Systemnachrichten, Feedback zur Formularvalidierung oder Statusaktualisierungen, die klar sichtbar, aber nicht aufdringlich sein müssen.

<!-- INTRO_END -->

## Erstellung von Alerts {#creating-alerts}

Ein `Alert` kann reichhaltige Inhalte wie Text, Schaltflächen und andere Komponenten halten. Setzen Sie ein Thema, um den Typ der angezeigten Nachricht visuell zu unterscheiden.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Schließen von Alerts {#dismissing-alerts}

Wenn Sie den Benutzern die Möglichkeit geben möchten, den `Alert` zu schließen, können Sie ihn schließbar machen, indem Sie die Methode `setClosable()` aufrufen. 

```java 
Alert alert = new Alert("Achtung! Dieser Alert kann geschlossen werden.");
closableAlert.setClosable(true);
```
Alerts tun oft mehr, als nur Nachrichten anzuzeigen—sie können nach dem Schließen Folgeaktionen auslösen. Verwenden Sie das `AlertCloseEvent`, um diese Fälle zu behandeln und zu reagieren, wenn der Benutzer den `Alert` schließt.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Wiederverwendbare Alert-Komponente
Das Schließen des Alerts versteckt ihn lediglich—es zerstört die Komponente nicht, sodass Sie sie bei Bedarf später wiederverwenden können.
:::


## Styling {#styling}

### Themen {#themes}

Die `Alert`-Komponente unterstützt mehrere <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, um verschiedene Arten von Nachrichten visuell zu unterscheiden—wie Erfolg, Fehler, Warnung oder Info. Diese Themen können mit der Methode `setTheme()` oder direkt im Konstruktor angewendet werden.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### Größen {#expanses}

Die Größe definiert die visuelle Größe der `Alert`-Komponente. Sie können sie mit der Methode `setExpanse()` festlegen oder direkt an den Konstruktor übergeben. Die verfügbaren Optionen stammen aus der Expanse-Enumeration: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` und `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
