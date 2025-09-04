---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 9b2f9ec23124d60ab5f8fca18e561acb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

ProgressBar is een component dat visueel de voortgang van een taak weergeeft. Naarmate de taak dichter bij voltooiing komt, toont de voortgangsbalk het percentage van de voltooiing van de taak. Dit percentage wordt visueel weergegeven door een rechthoek die in het begin leeg is en geleidelijk gevuld raakt naarmate de taak vordert. Bovendien kan de voortgangsbalk een tekstuele weergave van dit percentage tonen.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Usages {#usages}

De `ProgressBar` component is nuttig voor het visualiseren van de voltooiingsstatus van taken. Het ondersteunt:

- Configureerbare minimum- en maximumwaarden.
- Onbepaalde modus voor lopende taken zonder definitief einde.
- Opties voor tekstzichtbaarheid, animatie en gestreepte ontwerpen voor betere visuele feedback.

## Setting values {#setting-values}

De ProgressBar-component maakt het mogelijk om de huidige waarde, minimum- en maximumlimieten in te stellen en op te halen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientation {#orientation}

De `ProgressBar` kan horizontaal of verticaal worden georiënteerd.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Indeterminate state {#indeterminate-state}

De `ProgressBar` ondersteunt een onbepaalde staat voor taken met onbekende voltooiingstijd.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Text and text visibility {#text-and-text-visibility}

Standaard toont de voortgangsbalk bij het aanmaken het percentage dat voltooid is in de indeling `XX%`. Met de `setText()`-methode kun je de tijdelijke aanduiding `{{x}}` gebruiken om de huidige waarde als percentage te krijgen. Bovendien kun je de tijdelijke aanduiding 
`{{value}}` gebruiken om de ruwe huidige waarde te krijgen.

```java
ProgressBar bar = new ProgressBar(15, "Downloading: {{x}}%");
```

## Styling {#styling}

### Themes {#themes}

De `ProgressBar` component wordt geleverd met <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> die ingebouwd zijn voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. 
Ze bieden een snelle en consistente manier om de uitstraling van ProgressBars in een app aan te passen. 

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best practices {#best-practices}

- **Gebruik geschikte minimum- en maximumwaarden**: Stel de minimum- en maximumwaarden in om de actierange van de taak nauwkeurig weer te geven.
- **Werk voortgang regelmatig bij**: Update continu de voortgangswaarde om realtime feedback aan gebruikers te bieden.
- **Gebruik onbepaalde staat voor onvoorspelbare duur**: Gebruik de onbepaalde staat voor taken met onvoorspelbare duur om voortgang aan te geven.
- **Toon tekst voor betere gebruikersfeedback**: Toon tekst op de voortgangsbalk om extra context over de voortgang van de taak te bieden.
