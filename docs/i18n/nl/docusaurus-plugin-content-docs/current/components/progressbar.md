---
title: ProgressBar
sidebar_position: 90
description: >-
  Visualize task completion with the ProgressBar component, supporting
  determinate and indeterminate modes, orientation, and themes.
_i18n_hash: 47c51276d2b1da6c6bef337f76403515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

De `ProgressBar` component vertegenwoordigt visueel de voltooiingsstatus van een operatie. Naarmate het werk vordert, vult een rechthoek geleidelijk om het huidige percentage weer te geven. De balk kan ook een tekstuele weergave van zijn waarde weergeven en ondersteunt zowel bepaalde als onbepaalde toestanden voor taken met een bekende of onbekende duur.

<!-- INTRO_END -->

## Usages {#usages}

De `ProgressBar` component is nuttig voor het visualiseren van de voltooiingsstatus van taken. Het ondersteunt:

- Configureerbare minimum- en maximumwaarden.
- Onbepaalde modus voor lopende taken zonder definitief einde.
- Opties voor tekstzichtbaarheid, animatie en gestreepte ontwerpen voor betere visuele feedback.

Het volgende voorbeeld toont een gestreepte, geanimeerde voortgangsbalk met start-, pauze- en resetbesturingselementen:

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Waarden instellen {#setting-values}

De ProgressBar component maakt het mogelijk om de huidige waarde, minimum en maximum limieten in te stellen en op te halen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Oriëntatie {#orientation}

De `ProgressBar` kan horizontaal of verticaal worden georiënteerd.

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## Onbepaalde staat {#indeterminate-state}

De `ProgressBar` ondersteunt een onbepaalde staat voor taken met onbekende voltooiingstijd.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Tekst en tekstzichtbaarheid {#text-and-text-visibility}

Standaard toont de voortgangsbalk bij het maken het procentuele voltooiingspercentage in het formaat `XX%`. Met de `setText()` methode kunt u de placeholder `{{x}}` gebruiken om de huidige waarde als percentage te krijgen. Daarnaast kunt u de placeholder
`{{value}}` gebruiken om de rauwe huidige waarde te krijgen.

```java
ProgressBar bar = new ProgressBar(15, "Aan het downloaden: {{x}}%");
```

## Styling {#styling}

### Thema's {#themes}

De `ProgressBar` component is voorzien van <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> die ingebouwd zijn voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te wijzigen.
Ze bieden een snelle en consistente manier om de uitstraling van ProgressBars door een applicatie heen aan te passen.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Beste praktijken {#best-practices}

- **Gebruik Geschikte Minimum- en Maximumwaarden**: Stel de minimum- en maximumwaarden in om het bereik van de taak nauwkeurig weer te geven.
- **Update Voortgang Regelmatig**: Werk de voortgangswaarde continu bij om realtime feedback aan gebruikers te bieden.
- **Gebruik Onbepaalde Staat voor Onbekende Duren**: Gebruik de onbepaalde staat voor taken met onvoorspelbare duur om voortgang aan te geven.
- **Toon Tekst voor Betere User Feedback**: Toon tekst op de voortgangsbalk om extra context over de voortgang van de taak te bieden.
