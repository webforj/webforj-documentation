---
title: Alert
sidebar_position: 5
_i18n_hash: d6b9cd03da84860fd2768d9633f3b38a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Die `Alert` Komponente in webforJ bietet kontextbezogene Feedback-Nachrichten für Benutzer. Es ist eine vielseitige Möglichkeit, wichtige Informationen, Warnungen oder Benachrichtigungen in Ihrer Anwendung anzuzeigen.

Alerts helfen dabei, die Aufmerksamkeit auf wichtige Informationen zu lenken, ohne den Arbeitsfluss des Benutzers zu stören. Sie sind perfekt für Systemnachrichten, Rückmeldungen zur Formularvalidierung oder Statusaktualisierungen, die deutlich sichtbar, aber nicht aufdringlich sein müssen.

Hier ist ein Beispiel für eine Alert-Komponente:

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Schließen von Alerts {#dismissing-alerts}

Wenn Sie den Benutzern die Möglichkeit geben möchten, die `Alert` zu schließen, können Sie sie schließbar machen, indem Sie die Methode `setClosable()` aufrufen. 

```java 
Alert alert = new Alert("Achtung! Diese Warnung kann geschlossen werden.");
closableAlert.setClosable(true);
```
Alerts tun oft mehr, als nur Nachrichten anzuzeigen—sie können Folgeaktionen auslösen, wenn sie geschlossen werden. Verwenden Sie das `AlertCloseEvent`, um diese Fälle zu bearbeiten und zu reagieren, wenn der Benutzer die `Alert` schließt.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Wiederverwendbare Alert-Komponente
Das Schließen der Alert versteckt sie nur—es zerstört die Komponente nicht, sodass Sie sie später bei Bedarf wiederverwenden können.
:::


## Styling {#styling}

### Themen {#themes}

Die `Alert` Komponente unterstützt mehrere <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, um verschiedene Arten von Nachrichten visuell zu unterscheiden—wie Erfolg, Fehler, Warnung oder Info. Diese Themen können mit der Methode `setTheme()` oder direkt im Konstruktor angewendet werden.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>

### Größen {#expanses}

Die Größe definiert die visuelle Dimension der `Alert` Komponente. Sie können sie mit der Methode `setExpanse()` festlegen oder direkt an den Konstruktor übergeben. Die verfügbaren Optionen stammen aus dem Expanse-Enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` und `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
