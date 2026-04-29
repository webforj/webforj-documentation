---
title: Alert
sidebar_position: 5
_i18n_hash: b69c428a86cd23667ade00afb734aeec
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

De `Alert` component in webforJ biedt contextuele feedbackberichten voor gebruikers. Het is een veelzijdige manier om belangrijke informatie, waarschuwingen of meldingen in uw app weer te geven.

Alerts helpen de aandacht te vestigen op belangrijke informatie zonder de workflow van de gebruiker te verstoren. Ze zijn perfect voor systeemmeldingen, feedback over formuliervalidatie of statusupdates die duidelijk zichtbaar maar niet opdringerig moeten zijn.

<!-- INTRO_END -->

## Alerts maken {#creating-alerts}

Een `Alert` kan rijke inhoud bevatten zoals tekst, knoppen en andere componenten. Stel een thema in om het type weergegeven bericht visueel te onderscheiden.

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '110px'
/>

## Alerts afwijzen {#dismissing-alerts}

Als u gebruikers de optie wilt geven om de `Alert` af te wijzen, kunt u het sluitbaar maken door de `setClosable()`-methode aan te roepen. 

```java 
Alert alert = new Alert("Let op! Deze alert kan worden afgewezen.");
closableAlert.setClosable(true);
```
Alerts doen vaak meer dan alleen berichten weergeven—ze kunnen vervolgacties veroorzaken wanneer ze worden afgewezen. Gebruik de `AlertCloseEvent` om deze gevallen af te handelen en te reageren wanneer de gebruiker de `Alert` afwijst.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Herbruikbare Alert Component
Het sluiten van de alert verbergt deze alleen—het vernietigt de component niet, dus u kunt deze later indien nodig opnieuw gebruiken.
:::


## Stijlen {#styling}

### Thema's {#themes}

De `Alert` component ondersteunt meerdere <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> om verschillende soorten berichten visueel te onderscheiden—zoals succes, fout, waarschuwing of info. Deze thema's kunnen worden toegepast met de `setTheme()`-methode of direct in de constructor.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '650px'
/>


### Uitbreidingen {#expanses}

De uitbreiding definieert de visuele grootte van de `Alert` component. U kunt deze instellen met de `setExpanse()`-methode of deze direct aan de constructor doorgeven. De beschikbare opties komen van de Expanse enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` en `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '600px'
/>

<TableBuilder name="Alert" />
