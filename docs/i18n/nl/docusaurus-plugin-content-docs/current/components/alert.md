---
title: Alert
sidebar_position: 5
description: >-
  Display contextual feedback messages with the Alert component, including
  themes, expanses, dismissible close events, and rich content.
_i18n_hash: 1008b6fbbcfab4361660689ceb08135c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

De `Alert` component in webforJ biedt contextuele feedbackberichten voor gebruikers. Het is een veelzijdige manier om belangrijke informatie, waarschuwingen of meldingen in uw app weer te geven.

Alerts helpen de aandacht te vestigen op belangrijke informatie zonder de workflow van de gebruiker te verstoren. Ze zijn perfect voor systeemberichten, feedback over formuliervalidatie of statusupdates die duidelijk zichtbaar moeten zijn maar niet opdringerig zijn.

<!-- INTRO_END -->

## Alerts maken {#creating-alerts}

Een `Alert` kan rijke inhoud bevatten, zoals tekst, knoppen en andere componenten. Stel een thema in om visueel te onderscheiden welk type bericht wordt weergegeven.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Alerts verbergen {#dismissing-alerts}

Als u gebruikers de optie wilt geven om de `Alert` te verbergen, kunt u deze sluitbaar maken door de `setClosable()` methode aan te roepen.

```java
Alert alert = new Alert("Let op! Deze melding kan worden weggeklikt.");
alert.setClosable(true);
```
Alerts doen vaak meer dan alleen berichten weergeven—ze kunnen vervolgacties triggeren wanneer ze worden gesloten. Gebruik de `AlertCloseEvent` om deze gevallen af te handelen en te reageren wanneer de gebruiker de `Alert` verbergt.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Herbruikbare Alert Component
Het sluiten van de alert verbergt deze alleen—het vernietigt de component niet, zodat u deze later opnieuw kunt gebruiken indien nodig.
:::


## Styling {#styling}

### Thema's {#themes}

De `Alert` component ondersteunt meerdere <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> om visueel onderscheid te maken tussen verschillende soorten berichten—zoals succes, fout, waarschuwing of info. Deze thema's kunnen worden toegepast met de `setTheme()` methode of rechtstreeks in de constructor.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### Uitzichten {#expanses}

Het uitzicht definieert de visuele grootte van de `Alert` component. U kunt dit instellen met de `setExpanse()` methode. De beschikbare opties komen uit de Expanse enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` en `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
