---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 6acac582ce905eb255ee09e499fd561f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

De `ProgressBar` component vertegenwoordigt visueel de voltooiingsstatus van een bewerking. Terwijl het werk vordert, vullen rechthoeken geleidelijk om het huidige percentage weer te geven. De balk kan ook een tekstuele weergave van zijn waarde tonen en ondersteunt zowel bepaalde als onbepaaldeStatussen voor taken met bekende of onbekende duur.

<!-- INTRO_END -->

## Gebruiken {#usages}

De `ProgressBar` component is nuttig voor het visualiseren van de voltooiingsstatus van taken. Het ondersteunt:

- Instelbare minimum- en maximumwaarden.
- Onbepaalde modus voor lopende taken zonder definitief einde.
- Opties voor tekstzichtbaarheid, animatie en gestreepte ontwerpen voor betere visuele feedback.

Het volgende voorbeeld toont een gestreepte, geanimeerde voortgangsbalk met start-, pauze- en resetknoppen:

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Waarden instellen {#setting-values}

De ProgressBar component maakt het mogelijk om de huidige waarde, minimum- en maximumlimieten in te stellen en op te halen.

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

De `ProgressBar` ondersteunt een onbepaalde staat voor taken met een onbekende voltooiingstijd.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Tekst en tekstzichtbaarheid {#text-and-text-visibility}

Standaard toont de voortgangsbalk bij het aanmaken het percentage voltooid in het formaat `XX%`. Met de `setText()` methode kun je de placeholder `{{x}}` gebruiken om de huidige waarde als percentage te krijgen. Daarnaast kun je de placeholder 
`{{value}}` gebruiken om de rauwe huidige waarde te verkrijgen.

```java
ProgressBar bar = new ProgressBar(15, "Downloaden: {{x}}%");
```

## Styling {#styling}

### Thema's {#themes}

De `ProgressBar` component wordt geleverd met <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te wijzigen. 
Ze bieden een snelle en consistente manier om de uitstraling van ProgressBars in een app te personaliseren. 

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Beste praktijken {#best-practices}

- **Gebruik geschikte minimum- en maximumwaarden**: Stel de minimum- en maximumwaarden in om het bereik van de taak nauwkeurig weer te geven.
- **Werk de voortgang regelmatig bij**: Werk de voortgangswaarde continu bij om realtime feedback aan gebruikers te geven.
- **Maak gebruik van onbepaalde staat voor onbekende duur**: Gebruik de onbepaalde staat voor taken met onvoorspelbare duur om aan te geven dat de voortgang aan de gang is.
- **Toon tekst voor betere gebruikersfeedback**: Toon tekst op de voortgangsbalk om extra context te bieden over de voortgang van de taak.
