---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 3c76010e8bda96d8694bffa5a260b851
---
```jsx
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

ProgressBar is een component dat visueel de voortgang van een taak weergeeft. Terwijl de taak naar voltooiing vordert, toont de voortgangsbalk het percentage van de voltooiing van de taak. Dit percentage wordt visueel weergegeven door een rechthoek die aanvankelijk leeg is en geleidelijk wordt gevuld naarmate de taak vordert. Bovendien kan de voortgangsbalk een tekstuele weergave van dit percentage tonen.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Usages {#usages}

De `ProgressBar` component is nuttig voor het visualiseren van de voltooiingsstatus van taken. Het ondersteunt:

- Instelbare minimum- en maximumwaarden.
- Onbepaalde modus voor doorlopende taken zonder definitief einde.
- Opties voor tekstzichtbaarheid, animatie en gestreepte ontwerpen voor betere visuele feedback.

## Setting values {#setting-values}

De ProgressBar-component staat het instellen en ophalen van de huidige waarde, minimum en maximumlimieten toe.

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

De `ProgressBar` ondersteunt een onbepaalde toestand voor taken met onbekende voltooiingstijd.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Text and text visibility {#text-and-text-visibility}

Standaard toont de voortgangsbalk, wanneer deze is aangemaakt, het percentage dat voltooid is in het formaat `XX%`. Met de `setText()`-methode kun je de placeholder `{{x}}` gebruiken om de huidige waarde als percentage te krijgen. Daarnaast kun je de placeholder `{{value}}` gebruiken om de ruwe huidige waarde te krijgen.

```java
ProgressBar bar = new ProgressBar(15, "Downloading: {{x}}%");
```

## Styling {#styling}

### Themes {#themes}

De `ProgressBar` component komt met <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te wijzigen. 
Ze bieden een snelle en consistente manier om het uiterlijk van ProgressBars door een app heen aan te passen.

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best practices {#best-practices}

- **Gebruik geschikte minimum- en maximumwaarden**: Stel de minimum- en maximumwaarden in om het bereik van de taak nauwkeurig weer te geven.
- **Update de voortgang regelmatig**: Werk de voortgangswaarde continu bij om real-time feedback aan gebruikers te bieden.
- **Gebruik de onbepaalde staat voor onbekende duur**: Gebruik de onbepaalde staat voor taken met onvoorspelbare duur om voortdurende voortgang aan te geven.
- **Toon tekst voor betere gebruikersfeedback**: Toon tekst op de voortgangsbalk om extra context te bieden over de voortgang van de taak.
```
