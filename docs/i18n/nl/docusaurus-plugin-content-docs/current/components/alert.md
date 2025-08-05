---
title: Alert
sidebar_position: 5
_i18n_hash: e876e23a7ee171611e8747deef02d93c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

De `Alert` component in webforJ biedt contextuele feedbackberichten voor gebruikers. Het is een veelzijdige manier om belangrijke informatie, waarschuwingen of meldingen in uw app weer te geven.

Alerts helpen om aandacht te vestigen op belangrijke informatie zonder de workflow van de gebruiker te verstoren. Ze zijn perfect voor systeemmeldingen, feedback bij formulier validatie of statusupdates die duidelijk zichtbaar, maar niet opdringerig moeten zijn.

Hier is een voorbeeld van een alert-component:

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Alerts afwijzen {#dismissing-alerts}

Als u gebruikers de mogelijkheid wilt geven om de `Alert` af te wijzen, kunt u deze sluitbaar maken door de methode `setClosable()` aan te roepen. 

```java 
Alert alert = new Alert("Let op! Deze alert kan worden afgewezen.");
closableAlert.setClosable(true);
```
Alerts doen vaak meer dan alleen berichten weergeven—ze kunnen vervolgacties triggeren wanneer ze worden afgewezen. Gebruik de `AlertCloseEvent` om deze gevallen te verwerken en te reageren wanneer de gebruiker de `Alert` afwijst.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Herbruikbare Alert Component
Het sluiten van de alert verbergt deze alleen—het vernietigt de component niet, zodat u deze later indien nodig kunt hergebruiken.
:::

## Stylen {#styling}

### Thema's {#themes}

De `Alert` component ondersteunt meerdere <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> om verschillende soorten berichten visueel te onderscheiden—zoals succes, fout, waarschuwing of info. Deze thema's kunnen worden toegepast met behulp van de methode `setTheme()` of direct in de constructor.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>

### Uiterruimtes {#expanses}

De uitgestrektheid definieert de visuele grootte van de `Alert` component. U kunt deze instellen met de methode `setExpanse()` of deze direct aan de constructor doorgeven. De beschikbare opties komen vanuit de Expanse enum: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, en `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
