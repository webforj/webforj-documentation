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

De `Alert` component in webforJ biedt contextuele feedbackberichten voor gebruikers. Het is een veelzijdige manier om belangrijke informatie, waarschuwingen of notificaties in je app weer te geven.

Alerts helpen om de aandacht te vestigen op belangrijke informatie zonder de workflow van de gebruiker te verstoren. Ze zijn perfect voor systeemberichten, feedback bij formuliervalidatie of statusupdates die duidelijk zichtbaar moeten zijn maar niet opdringerig.

<!-- INTRO_END -->

## Alerts maken {#creating-alerts}

Een `Alert` kan rijke inhoud bevatten zoals tekst, knoppen en andere componenten. Stel een thema in om het type weergegeven bericht visueel te onderscheiden.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Alerts afwijzen {#dismissing-alerts}

Als je gebruikers de optie wilt geven om de `Alert` af te wijzen, kun je deze afsluitbaar maken door de `setClosable()` methode aan te roepen.

```java
Alert alert = new Alert("Let op! Deze melding kan worden afgewezen.");
closableAlert.setClosable(true);
```
Alerts doen vaak meer dan alleen berichten weergeven—ze kunnen vervolgacties activeren wanneer ze worden afgewezen. Gebruik de `AlertCloseEvent` om deze gevallen te verwerken en te reageren wanneer de gebruiker de `Alert` afwijst.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Herbruikbare Alert Component
Het sluiten van de alert verbergt deze alleen—het vernietigt de component niet, zodat je deze later opnieuw kunt gebruiken indien nodig.
:::


## Styling {#styling}

### Thema's {#themes}

De `Alert` component ondersteunt meerdere <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> om verschillende soorten berichten visueel te onderscheiden—zoals succes, fout, waarschuwing of informatie. Deze thema's kunnen worden toegepast met de `setTheme()` methode of rechtstreeks in de constructor.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### Groottes {#expanses}

De grootte definieert de visuele omvang van de `Alert` component. Je kunt deze instellen met de `setExpanse()` methode of het rechtstreeks aan de constructor doorgeven. De beschikbare opties komen van de Expanse enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` en `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
