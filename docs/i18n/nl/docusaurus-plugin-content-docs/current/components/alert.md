---
title: Alert
sidebar_position: 5
_i18n_hash: 32072a9b5fdae80b41d77ee1d9742ea5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Het `Alert`-component in webforJ biedt contextuele feedbackberichten voor gebruikers. Het is een veelzijdige manier om belangrijke informatie, waarschuwingen of meldingen in uw app weer te geven.

Alerts helpen de aandacht te vestigen op belangrijke informatie zonder de workflow van de gebruiker te verstoren. Ze zijn perfect voor systeemmeldingen, feedback van formuliervalidatie of statusupdates die goed zichtbaar moeten zijn maar niet opdringerig mogen zijn.

<!-- INTRO_END -->

## Alerts maken {#creating-alerts}

Een `Alert` kan rijke inhoud bevatten, zoals tekst, knoppen en andere componenten. Stel een thema in om het type weergegeven bericht visueel te onderscheiden.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Alerts sluiten {#dismissing-alerts}

Als u gebruikers de optie wilt geven om de `Alert` te sluiten, kunt u deze sluitbaar maken door de `setClosable()`-methode aan te roepen.

```java 
Alert alert = new Alert("Let op! Deze melding kan gesloten worden.");
closableAlert.setClosable(true);
```
Alerts doen vaak meer dan alleen berichten weergeven—ze kunnen vervolgacties triggeren wanneer ze worden gesloten. Gebruik het `AlertCloseEvent` om deze gevallen te behandelen en te reageren wanneer de gebruiker de `Alert` sluit.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Herbruikbare Alert Component
Het sluiten van de alert verbergt deze alleen—het vernietigt de component niet, zodat u deze later opnieuw kunt gebruiken indien nodig.
:::

## Stijlen {#styling}

### Themas {#themes}

Het `Alert`-component ondersteunt meerdere <JavadocLink type="foundation" location="com/webforj/component/Theme"> theams </JavadocLink> om verschillende soorten berichten visueel te onderscheiden—zoals succes, fout, waarschuwing of info. Deze thema's kunnen worden toegepast met de `setTheme()`-methode of rechtstreeks in de constructor.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>

### Groottes {#expanses}

De grootte definieert de visuele maat van het `Alert`-component. U kunt deze instellen met de `setExpanse()`-methode of deze direct aan de constructor doorgeven. De beschikbare opties komen uit de Expanse enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, en `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
