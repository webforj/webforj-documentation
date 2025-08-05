---
title: Alert
sidebar_position: 5
_i18n_hash: e876e23a7ee171611e8747deef02d93c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Die `Alert`-Komponente in webforJ bietet kontextuelle Rückmeldungen für Benutzer. Es ist eine vielseitige Möglichkeit, wichtige Informationen, Warnungen oder Benachrichtigungen in Ihrer App anzuzeigen.

Alerts helfen dabei, die Aufmerksamkeit auf wichtige Informationen zu lenken, ohne den Arbeitsfluss des Benutzers zu stören. Sie sind perfekt für Systemmeldungen, Feedback zur Formularvalidierung oder Statusaktualisierungen, die klar sichtbar, aber nicht aufdringlich sein müssen.

Hier ist ein Beispiel für eine Alert-Komponente:

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Schließen von Alerts {#dismissing-alerts}

Wenn Sie den Benutzern die Möglichkeit geben möchten, den `Alert` zu schließen, können Sie ihn schließbar machen, indem Sie die Methode `setClosable()` aufrufen.

```java 
Alert alert = new Alert("Achtung! Dieser Alert kann geschlossen werden.");
closableAlert.setClosable(true);
```
Alerts leisten oft mehr, als nur Nachrichten anzuzeigen – sie können Folgeaktionen auslösen, wenn sie geschlossen werden. Verwenden Sie das `AlertCloseEvent`, um diese Fälle zu behandeln und darauf zu reagieren, wenn der Benutzer den `Alert` schließt.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Wiederverwendbare Alert-Komponente
Das Schließen des Alerts blendet ihn nur aus – es zerstört die Komponente nicht, sodass Sie sie bei Bedarf später wiederverwenden können.
:::


## Styling {#styling}

### Themen {#themes}

Die `Alert`-Komponente unterstützt mehrere <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, um verschiedene Arten von Nachrichten visuell zu unterscheiden – wie Erfolg, Fehler, Warnung oder Information. Diese Themen können mit der Methode `setTheme()` oder direkt im Konstruktor angewendet werden.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>


### Größen {#expanses}

Die Größe definiert die visuelle Größe der `Alert`-Komponente. Sie können sie mit der Methode `setExpanse()` festlegen oder direkt an den Konstruktor übergeben. Die verfügbaren Optionen stammen aus der Enums Expanse: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` und `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
