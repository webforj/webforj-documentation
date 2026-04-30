---
title: Alert
sidebar_position: 5
_i18n_hash: b69c428a86cd23667ade00afb734aeec
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Die `Alert`-Komponente in webforJ bietet kontextbezogene Rückmeldungsnachrichten für Benutzer. Sie ist eine vielseitige Möglichkeit, wichtige Informationen, Warnungen oder Benachrichtigungen in Ihrer App anzuzeigen.

Alerts helfen, die Aufmerksamkeit auf wichtige Informationen zu lenken, ohne den Arbeitsablauf des Benutzers zu stören. Sie sind perfekt für Systemnachrichten, Rückmeldungen zur Formularvalidierung oder Statusaktualisierungen, die deutlich sichtbar, aber nicht aufdringlich sein müssen.

<!-- INTRO_END -->

## Erstellen von Alerts {#creating-alerts}

Ein `Alert` kann reichhaltige Inhalte wie Texte, Schaltflächen und andere Komponenten enthalten. Setzen Sie ein Thema, um den Typ der angezeigten Nachricht visuell zu unterscheiden.

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '110px'
/>

## Schließen von Alerts {#dismissing-alerts}

Wenn Sie den Benutzern die Möglichkeit geben möchten, den `Alert` zu schließen, können Sie ihn schließbar machen, indem Sie die Methode `setClosable()` aufrufen. 

```java 
Alert alert = new Alert("Achtung! Dieser Alert kann geschlossen werden.");
closableAlert.setClosable(true);
```
Alerts tun oft mehr als nur Nachrichten anzeigen—sie können Folgeaktionen auslösen, wenn sie geschlossen werden. Verwenden Sie das `AlertCloseEvent`, um diese Fälle zu behandeln und zu reagieren, wenn der Benutzer den `Alert` schließt.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Wiederverwendbare Alert-Komponente
Das Schließen des Alerts blendet ihn nur aus—es zerstört die Komponente nicht, sodass Sie sie bei Bedarf später wiederverwenden können.
:::

## Styling {#styling}

### Themen {#themes}

Die `Alert`-Komponente unterstützt mehrere <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, um verschiedene Nachrichtentypen visuell zu unterscheiden—wie Erfolg, Fehler, Warnung oder Information. Diese Themen können mit der Methode `setTheme()` oder direkt im Konstruktor angewendet werden.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '650px'
/>

### Größen {#expanses}

Die Größe definiert die visuelle Größe der `Alert`-Komponente. Sie können sie mit der Methode `setExpanse()` festlegen oder direkt an den Konstruktor übergeben. Die verfügbaren Optionen stammen aus dem Expanse-Enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` und `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '600px'
/>

<TableBuilder name="Alert" />
